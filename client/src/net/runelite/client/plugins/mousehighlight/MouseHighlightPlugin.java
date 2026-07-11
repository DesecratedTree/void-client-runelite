package net.runelite.client.plugins.mousehighlight;

import com.GameClient;
import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;

@PluginDescriptor(
	name = "Mouse Tooltips",
	description = "Render default actions as a tooltip",
	tags = {"actions", "overlay", "tooltip"},
	enabledByDefault = true,
	loadWhenOutdated = true
)
public class MouseHighlightPlugin extends Plugin
{
	private static final String WALK_HERE = "Walk here";
	private static final String CANCEL = "Cancel";
	private static final String CONTINUE = "Continue";
	private static final String EXAMINE = "Examine";
	private static final String DROP = "Drop";
	private static final String MOVE = "Move";

	@Inject
	private GameClient client;

	@Inject
	private MouseHighlightConfig config;

	@Inject
	private TooltipManager tooltipManager;

	@Provides
	MouseHighlightConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MouseHighlightConfig.class);
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (!config.uiTooltip() && !config.chatboxTooltip())
		{
			return;
		}

		MenuEntry[] entries = client.getMenuEntries();
		if (entries.length == 0)
		{
			return;
		}

		MenuEntry entry = getTooltipEntry(entries);
		if (entry == null)
		{
			return;
		}

		String option = clean(entry.getOption());
		String target = clean(entry.getTarget());
		String text = target.isEmpty()
			? option
			: option + " " + ColorUtil.wrapWithColorTag(target, JagexColors.MENU_TARGET);
		tooltipManager.add(new Tooltip(text));
	}

	private MenuEntry getTooltipEntry(MenuEntry[] entries)
	{
		if (client.isShiftClickEnabled() && client.isShiftPressed())
		{
			MenuEntry shifted = getShiftTooltipEntry(entries);
			if (shifted != null)
			{
				return shifted;
			}
		}

		return getFirstVisibleEntry(entries);
	}

	private static MenuEntry getFirstVisibleEntry(MenuEntry[] entries)
	{
		for (int i = entries.length - 1; i >= 0; i--)
		{
			if (isVisible(entries[i]))
			{
				return entries[i];
			}
		}

		return null;
	}

	private static MenuEntry getShiftTooltipEntry(MenuEntry[] entries)
	{
		MenuEntry top = getFirstVisibleEntry(entries);
		if (top != null && isItemEntry(top))
		{
			MenuEntry drop = findSameTargetOption(entries, top, DROP);
			if (drop != null)
			{
				return drop;
			}
		}

		int visible = 0;
		for (int i = entries.length - 1; i >= 0; i--)
		{
			if (!isVisible(entries[i]))
			{
				continue;
			}

			visible++;
			if (visible == 2)
			{
				return entries[i];
			}
		}

		return top;
	}

	private static MenuEntry findSameTargetOption(MenuEntry[] entries, MenuEntry base, String option)
	{
		String target = clean(base.getTarget());
		for (int i = entries.length - 1; i >= 0; i--)
		{
			MenuEntry entry = entries[i];
			if (option.equalsIgnoreCase(clean(entry.getOption())) && target.equals(clean(entry.getTarget())))
			{
				return entry;
			}
		}

		return null;
	}

	private static boolean isVisible(MenuEntry entry)
	{
		return entry != null && !shouldHide(clean(entry.getOption()), entry.getType());
	}

	private static boolean shouldHide(String option, int type)
	{
		return option.isEmpty()
			|| WALK_HERE.equalsIgnoreCase(option)
			|| CANCEL.equalsIgnoreCase(option)
			|| CONTINUE.equalsIgnoreCase(option)
			|| MOVE.equalsIgnoreCase(option)
			|| EXAMINE.equalsIgnoreCase(option)
			|| type == 1005
			|| type == 1008
			|| type == 1010;
	}

	private static boolean isItemEntry(MenuEntry entry)
	{
		int type = entry.getType();
		if (type >= 2000)
		{
			type -= 2000;
		}

		return type == 5
			|| type == 10
			|| type == 13
			|| type == 16
			|| type == 18
			|| type == 21
			|| type == 22
			|| type == 31
			|| type == 32
			|| type == 33
			|| type == 34
			|| type == 35
			|| type == 36
			|| type == 37
			|| type == 38
			|| type == 47
			|| type == 49
			|| type == 1005
			|| type == 1010
			|| type == 1011;
	}

	private static String clean(String value)
	{
		return value == null ? "" : value.trim();
	}
}
