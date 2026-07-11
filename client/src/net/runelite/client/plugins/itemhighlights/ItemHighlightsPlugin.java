package net.runelite.client.plugins.itemhighlights;

import com.GameClient;
import com.google.inject.Provides;
import java.util.ArrayList;
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
	name = "Item Highlights",
	description = "Highlight ground items on-screen and in the menu",
	tags = {"ground", "item", "highlight", "loot", "overlay", "tags"},
	enabledByDefault = true,
	loadWhenOutdated = true
)
public class ItemHighlightsPlugin extends Plugin
{
	private static final String EXAMINE = "Examine";
	private static final String TAG = "Tag";
	private static final String UNTAG = "Un-tag";
	private static final String TAG_ALL = "Tag-All";
	private static final String UNTAG_ALL = "Un-tag-All";

	private final Set<String> taggedItemKeys = new HashSet<>();
	private final Set<String> taggedItemNames = new HashSet<>();
	private volatile boolean started;
	private volatile int lastItemCount;
	private volatile int lastHighlightedCount;
	private volatile int lastRenderedCount;
	private volatile String lastTagClick = "none";
	private volatile String lastMenuEntry = "none";

	@Inject
	private GameClient client;

	@Inject
	private ItemHighlightsOverlay overlay;

	@Inject
	private ItemHighlightsConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Provides
	ItemHighlightsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemHighlightsConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		started = true;
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		taggedItemKeys.clear();
		taggedItemNames.clear();
		started = false;
		lastItemCount = 0;
		lastHighlightedCount = 0;
		lastRenderedCount = 0;
		lastTagClick = "none";
		lastMenuEntry = "none";
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		GameClient.GroundItemInfo item = findGroundItem(event.getIdentifier(), event.getActionParam0(), event.getActionParam1(), event.getTarget());
		boolean isExamine = EXAMINE.equals(Text.removeTags(event.getOption()));
		if (isExamine)
		{
			lastMenuEntry = "examine id=" + event.getIdentifier() + " item=" + itemName(item, event.getTarget()) + " shift=" + client.isShiftPressed();
		}

		if (item != null && isExamine && client.isShiftPressed())
		{
			addTagMenuEntries(event, item);
		}

		if (!config.showMenuHighlight() || config.highlightMenuStyle() == ItemHighlightsConfig.MenuHighlightStyle.NONE)
		{
			return;
		}

		String itemName = itemNameFromTarget(event.getTarget());
		if (itemName == null || !highlightMatchesItemName(itemName))
		{
			return;
		}

		MenuEntry[] entries = client.getMenuEntries();
		if (entries.length == 0)
		{
			return;
		}

		MenuEntry entry = entries[entries.length - 1];
		entry.setTarget(ColorUtil.prependColorTag(Text.removeTags(entry.getTarget()), config.highlightColor()));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		String option = Text.removeTags(event.getMenuOption());
		if (!TAG.equals(option) && !UNTAG.equals(option) && !TAG_ALL.equals(option) && !UNTAG_ALL.equals(option))
		{
			return;
		}

		GameClient.GroundItemInfo item = findGroundItem(event.getId(), event.getParam0(), event.getParam1(), event.getMenuTarget());
		String itemName = item == null ? itemNameFromTarget(event.getMenuTarget()) : item.getName();
		lastTagClick = option + " id=" + event.getId() + " item=" + (itemName == null ? "unknown" : itemName);

		if (TAG.equals(option))
		{
			if (item != null)
			{
				taggedItemKeys.add(tileItemKey(item));
			}
			if (itemName != null)
			{
				taggedItemNames.add(itemName.toLowerCase());
			}
		}
		else if (UNTAG.equals(option))
		{
			if (item != null)
			{
				taggedItemKeys.remove(tileItemKey(item));
			}
			if (itemName != null)
			{
				taggedItemNames.remove(itemName.toLowerCase());
			}
		}
		else
		{
			if (itemName == null)
			{
				return;
			}
			if (TAG_ALL.equals(option))
			{
				taggedItemNames.add(itemName.toLowerCase());
			}
			else
			{
				taggedItemNames.remove(itemName.toLowerCase());
			}
			toggleItemName(itemName);
		}

		event.consume();
	}

	boolean isHighlighted(GameClient.GroundItemInfo item)
	{
		if (!shouldRender(item))
		{
			return false;
		}

		if (taggedItemKeys.contains(tileItemKey(item)))
		{
			return true;
		}

		if (item.getName() != null && taggedItemNames.contains(item.getName().toLowerCase()))
		{
			return true;
		}

		return highlightMatchesItemName(item.getName());
	}

	List<String> getHighlights()
	{
		String configItems = config.getItemsToHighlight();
		if (configItems == null || configItems.isEmpty())
		{
			return Collections.emptyList();
		}

		return Text.fromCSV(configItems);
	}

	void recordRenderStats(int itemCount, int highlightedCount, int renderedCount)
	{
		lastItemCount = itemCount;
		lastHighlightedCount = highlightedCount;
		lastRenderedCount = renderedCount;
	}

	String getDebugLine()
	{
		return "Item Highlights: started=" + started
			+ " items=" + lastItemCount
			+ " highlighted=" + lastHighlightedCount
			+ " drawn=" + lastRenderedCount
			+ " taggedTiles=" + taggedItemKeys.size()
			+ " taggedNames=" + taggedItemNames.size()
			+ " menu=" + lastMenuEntry
			+ " click=" + lastTagClick;
	}

	private boolean shouldRender(GameClient.GroundItemInfo item)
	{
		if (item == null)
		{
			return false;
		}

		return !config.onlyShowNamedLoot() || !isGenericItemName(item.getName(), item.getId());
	}

	private boolean highlightMatchesItemName(String itemName)
	{
		if (itemName == null)
		{
			return false;
		}

		for (String highlight : getHighlights())
		{
			if (WildcardMatcher.matches(highlight, itemName))
			{
				return true;
			}
		}

		return false;
	}

	private void addTagMenuEntries(MenuEntryAdded event, GameClient.GroundItemInfo item)
	{
		String target = event.getTarget();
		boolean itemTagged = taggedItemKeys.contains(tileItemKey(item));
		boolean nameTagged = getHighlights().stream().anyMatch(item.getName()::equalsIgnoreCase);
		boolean wildcardMatch = getHighlights().stream()
			.filter(highlight -> !highlight.equalsIgnoreCase(item.getName()))
			.anyMatch(highlight -> WildcardMatcher.matches(highlight, item.getName()));

		client.addMenuEntry(itemTagged ? UNTAG : TAG, target, MenuAction.RUNELITE.getId(), item.getId(), event.getActionParam0(), event.getActionParam1());
		if (!wildcardMatch)
		{
			client.addMenuEntry(nameTagged ? UNTAG_ALL : TAG_ALL, target, MenuAction.RUNELITE.getId(), item.getId(), event.getActionParam0(), event.getActionParam1());
		}
	}

	private GameClient.GroundItemInfo findGroundItem(int id, int actionParam0, int actionParam1, String target)
	{
		String targetName = itemNameFromTarget(target);
		int localX = actionParam0 << 7;
		int localY = actionParam1 << 7;

		for (GameClient.GroundItemInfo item : client.getGroundItems())
		{
			if (item.getId() != id)
			{
				continue;
			}

			if (item.getLocalX() == localX && item.getLocalY() == localY && item.getPlane() == client.getPlane())
			{
				return item;
			}

			if (targetName != null && targetName.equalsIgnoreCase(item.getName()))
			{
				return item;
			}
		}

		return null;
	}

	private void toggleItemName(String itemName)
	{
		List<String> highlights = new ArrayList<>(getHighlights());
		if (!highlights.removeIf(itemName::equalsIgnoreCase))
		{
			highlights.add(itemName);
		}

		config.setItemsToHighlight(Text.toCSV(highlights));
	}

	private static String itemNameFromTarget(String target)
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

		int quantityStart = cleaned.lastIndexOf(" x");
		if (quantityStart > 0 && cleaned.substring(quantityStart + 2).chars().allMatch(Character::isDigit))
		{
			cleaned = cleaned.substring(0, quantityStart).trim();
		}

		return cleaned.isEmpty() ? null : cleaned;
	}

	private static boolean isGenericItemName(String name, int id)
	{
		return name == null || name.equals("Item " + id);
	}

	private static String tileItemKey(GameClient.GroundItemInfo item)
	{
		return item.getId() + ":" + item.getPlane() + ":" + item.getLocalX() + ":" + item.getLocalY();
	}

	private static String itemName(GameClient.GroundItemInfo item, String fallbackTarget)
	{
		if (item != null && item.getName() != null)
		{
			return item.getName();
		}

		String targetName = itemNameFromTarget(fallbackTarget);
		return targetName == null ? "none" : targetName;
	}
}
