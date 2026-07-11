package net.runelite.client.plugins.mousehighlight;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("mousehighlight")
public interface MouseHighlightConfig extends Config
{
	@ConfigItem(
		keyName = "uiTooltip",
		name = "Interface tooltips",
		description = "Displays interface action tooltips."
	)
	default boolean uiTooltip()
	{
		return true;
	}

	@ConfigItem(
		keyName = "chatboxTooltip",
		name = "Chatbox tooltips",
		description = "Displays chatbox action tooltips."
	)
	default boolean chatboxTooltip()
	{
		return true;
	}

	@Range(
		min = -200,
		max = 200
	)
	@ConfigItem(
		keyName = "tooltipOffsetX",
		name = "Tooltip X offset",
		description = "Configures the horizontal tooltip offset from the mouse cursor. Positive values move right.",
		position = 10
	)
	@Units(Units.PIXELS)
	default int tooltipOffsetX()
	{
		return 16;
	}

	@Range(
		min = -200,
		max = 200
	)
	@ConfigItem(
		keyName = "tooltipOffsetY",
		name = "Tooltip Y offset",
		description = "Configures the vertical tooltip offset from the mouse cursor. Positive values move down.",
		position = 11
	)
	@Units(Units.PIXELS)
	default int tooltipOffsetY()
	{
		return 0;
	}
}
