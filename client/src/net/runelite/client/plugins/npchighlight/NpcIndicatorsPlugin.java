package net.runelite.client.plugins.npchighlight;

import com.GameClient;
import com.google.inject.Provides;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;
import net.runelite.client.util.WildcardMatcher;

@PluginDescriptor(
	name = "NPC Indicators",
	description = "Highlight NPCs on-screen and/or on the minimap",
	tags = {"highlight", "minimap", "npcs", "overlay", "respawn", "tags"},
	enabledByDefault = true,
	loadWhenOutdated = true
)
public class NpcIndicatorsPlugin extends Plugin
{
	private static final String LEGACY_GROUP = "npchighlight";
	private static final int EXAMINE_NPC = 1008;
	private static final String TAG = "Tag";
	private static final String UNTAG = "Un-tag";
	private static final String TAG_ALL = "Tag-All";
	private static final String UNTAG_ALL = "Un-tag-All";

	private final Set<Integer> taggedNpcIndexes = new HashSet<>();
	private final Set<String> taggedNpcNames = new HashSet<>();
	private volatile boolean started;
	private volatile int lastNpcCount;
	private volatile int lastHighlightedCount;
	private volatile int lastRenderedCount;
	private volatile String lastTagClick = "none";
	private volatile String lastMenuEntry = "none";

	@Inject
	private GameClient client;

	@Inject
	private NpcIndicatorsOverlay overlay;

	@Inject
	private NpcIndicatorsConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private OverlayManager overlayManager;

	@Provides
	NpcIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NpcIndicatorsConfig.class);
	}

	@Override
	protected void startUp()
	{
		migrateLegacyConfig();
		overlayManager.add(overlay);
		started = true;
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		taggedNpcIndexes.clear();
		taggedNpcNames.clear();
		started = false;
		lastNpcCount = 0;
		lastHighlightedCount = 0;
		lastRenderedCount = 0;
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		GameClient.NpcInfo npc = findNpc(event.getIdentifier());
		if (EXAMINE_NPC == event.getType())
		{
			lastMenuEntry = "examine id=" + event.getIdentifier() + " npc=" + npcName(npc) + " shift=" + client.isShiftPressed();
		}

		if (npc != null && EXAMINE_NPC == event.getType() && client.isShiftPressed())
		{
			addTagMenuEntries(event, npc);
		}

		if (config.highlightMenuStyle() == NpcIndicatorsConfig.MenuHighlightStyle.NONE)
		{
			return;
		}

		String npcName = npcNameFromTarget(event.getTarget());
		if (npcName == null || !highlightMatchesNPCName(npcName))
		{
			return;
		}

		MenuEntry[] entries = client.getMenuEntries();
		if (entries.length == 0)
		{
			return;
		}

		MenuEntry entry = entries[entries.length - 1];
		entry.setTarget(recolorMenuTarget(entry.getTarget(), config.highlightMenuStyle(), config.highlightColor()));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		String option = Text.removeTags(event.getMenuOption());
		if (!TAG.equals(option) && !UNTAG.equals(option) && !TAG_ALL.equals(option) && !UNTAG_ALL.equals(option))
		{
			return;
		}

		GameClient.NpcInfo npc = findNpc(event.getId());
		String npcName = npc == null ? npcNameFromTarget(event.getMenuTarget()) : npc.getName();
		lastTagClick = option + " id=" + event.getId() + " npc=" + (npcName == null ? "unknown" : npcName);

		if (TAG.equals(option))
		{
			if (npc != null)
			{
				taggedNpcIndexes.add(npc.getIndex());
			}
			if (npcName != null)
			{
				taggedNpcNames.add(npcName.toLowerCase());
			}
		}
		else if (UNTAG.equals(option))
		{
			if (npc != null)
			{
				taggedNpcIndexes.remove(npc.getIndex());
			}
			if (npcName != null)
			{
				taggedNpcNames.remove(npcName.toLowerCase());
			}
		}
		else
		{
			if (npcName == null)
			{
				return;
			}
			if (TAG_ALL.equals(option))
			{
				taggedNpcNames.add(npcName.toLowerCase());
			}
			else
			{
				taggedNpcNames.remove(npcName.toLowerCase());
			}
			toggleNpcName(npcName);
		}

		event.consume();
	}

	void recordRenderStats(int npcCount, int highlightedCount, int renderedCount)
	{
		lastNpcCount = npcCount;
		lastHighlightedCount = highlightedCount;
		lastRenderedCount = renderedCount;
	}

	String getDebugLine()
	{
		return "NPC Indicators: started=" + started
			+ " npcs=" + lastNpcCount
			+ " highlighted=" + lastHighlightedCount
			+ " drawn=" + lastRenderedCount
			+ " taggedIndexes=" + taggedNpcIndexes.size()
			+ " taggedNames=" + taggedNpcNames.size()
			+ " menu=" + lastMenuEntry
			+ " click=" + lastTagClick;
	}

	boolean isHighlighted(GameClient.NpcInfo npc)
	{
		if (!shouldRender(npc))
		{
			return false;
		}

		if (taggedNpcIndexes.contains(npc.getIndex()))
		{
			return true;
		}

		if (npc.getName() != null && taggedNpcNames.contains(npc.getName().toLowerCase()))
		{
			return true;
		}

		if (parseIds(config.npcIdsToHighlight()).contains(npc.getId()))
		{
			return true;
		}

		return highlightMatchesNPCName(npc.getName());
	}

	boolean shouldRender(GameClient.NpcInfo npc)
	{
		if (npc == null)
		{
			return false;
		}

		if (config.ignoreDeadNpcs() && npc.isDying())
		{
			return false;
		}

		return !config.ignorePets() || !npc.isFollower();
	}

	List<String> getHighlights()
	{
		String configNpcs = config.getNpcToHighlight();
		if (configNpcs == null || configNpcs.isEmpty())
		{
			return Collections.emptyList();
		}

		return Text.fromCSV(configNpcs);
	}

	private boolean highlightMatchesNPCName(String npcName)
	{
		if (npcName == null)
		{
			return false;
		}

		for (String highlight : getHighlights())
		{
			if (WildcardMatcher.matches(highlight, npcName))
			{
				return true;
			}
		}

		return false;
	}

	private void addTagMenuEntries(MenuEntryAdded event, GameClient.NpcInfo npc)
	{
		String target = event.getTarget();
		boolean indexTagged = taggedNpcIndexes.contains(npc.getIndex());
		boolean nameTagged = getHighlights().stream().anyMatch(npc.getName()::equalsIgnoreCase);
		boolean wildcardMatch = getHighlights().stream()
			.filter(highlight -> !highlight.equalsIgnoreCase(npc.getName()))
			.anyMatch(highlight -> WildcardMatcher.matches(highlight, npc.getName()));

		client.addMenuEntry(indexTagged ? UNTAG : TAG, target, MenuAction.RUNELITE.getId(), npc.getIndex(), event.getActionParam0(), event.getActionParam1());
		if (!wildcardMatch)
		{
			client.addMenuEntry(nameTagged ? UNTAG_ALL : TAG_ALL, target, MenuAction.RUNELITE.getId(), npc.getIndex(), event.getActionParam0(), event.getActionParam1());
		}
	}

	private GameClient.NpcInfo findNpc(int index)
	{
		for (GameClient.NpcInfo npc : client.getNpcs())
		{
			if (npc.getIndex() == index)
			{
				return npc;
			}
		}

		return null;
	}

	private void toggleNpcName(String npcName)
	{
		List<String> highlights = new java.util.ArrayList<>(getHighlights());
		if (!highlights.removeIf(npcName::equalsIgnoreCase))
		{
			highlights.add(npcName);
		}

		config.setNpcToHighlight(Text.toCSV(highlights));
	}

	private static String recolorMenuTarget(String target, NpcIndicatorsConfig.MenuHighlightStyle style, Color color)
	{
		if (style == NpcIndicatorsConfig.MenuHighlightStyle.NONE)
		{
			return target;
		}

		if (style == NpcIndicatorsConfig.MenuHighlightStyle.BOTH)
		{
			return ColorUtil.prependColorTag(Text.removeTags(target), color);
		}

		String name;
		String level;
		if (target.contains(" (level-"))
		{
			int levelStart = target.lastIndexOf(" (level-");
			name = target.substring(0, levelStart);
			level = target.substring(levelStart);
		}
		else
		{
			name = target;
			level = null;
		}

		if (style == NpcIndicatorsConfig.MenuHighlightStyle.NAME)
		{
			String recolored = ColorUtil.prependColorTag(Text.removeTags(name), color);
			return level == null ? recolored : recolored + level;
		}

		if (style == NpcIndicatorsConfig.MenuHighlightStyle.LEVEL)
		{
			return level == null ? target : name + ColorUtil.prependColorTag(Text.removeTags(level), color);
		}

		throw new IllegalArgumentException("Unknown NPC menu highlight style " + style);
	}

	private static String npcNameFromTarget(String target)
	{
		if (target == null || target.isEmpty())
		{
			return null;
		}

		String cleaned = Text.removeTags(target).trim();
		if (cleaned.contains(" -> "))
		{
			cleaned = cleaned.substring(cleaned.lastIndexOf(" -> ") + 4).trim();
		}

		int levelStart = cleaned.lastIndexOf(" (level-");
		if (levelStart >= 0)
		{
			cleaned = cleaned.substring(0, levelStart).trim();
		}

		return cleaned.isEmpty() ? null : cleaned;
	}

	private static Set<Integer> parseIds(String value)
	{
		Set<Integer> ids = new HashSet<>();
		if (value == null)
		{
			return ids;
		}

		for (String token : value.split("[,\\n]"))
		{
			try
			{
				ids.add(Integer.parseInt(token.trim()));
			}
			catch (NumberFormatException ignored)
			{
			}
		}
		return ids;
	}

	private static String npcName(GameClient.NpcInfo npc)
	{
		return npc == null || npc.getName() == null ? "none" : npc.getName();
	}

	private void migrateLegacyConfig()
	{
		copyLegacy("npcToHighlight", "npcToHighlight");
		copyLegacy("npcIdsToHighlight", "npcIdsToHighlight");
		copyLegacy("highlightHull", "highlightHull");
		copyLegacy("highlightTile", "highlightTile");
		copyLegacy("drawNames", "drawNames");
		copyLegacy("highlightColor", "npcColor");
	}

	private void copyLegacy(String oldKey, String newKey)
	{
		if (configManager.getConfiguration(NpcIndicatorsConfig.GROUP, newKey) != null)
		{
			return;
		}

		String legacy = configManager.getConfiguration(LEGACY_GROUP, oldKey);
		if (legacy != null)
		{
			configManager.setConfiguration(NpcIndicatorsConfig.GROUP, newKey, legacy);
		}
	}
}
