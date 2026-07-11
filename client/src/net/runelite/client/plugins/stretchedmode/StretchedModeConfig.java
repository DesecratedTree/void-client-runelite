package net.runelite.client.plugins.stretchedmode;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("stretchedmode")
public interface StretchedModeConfig extends Config
{
	@ConfigItem(
		keyName = "increasedPerformance",
		name = "Increased Performance Mode",
		description = "Uses faster stretching."
	)
	default boolean increasedPerformance()
	{
		return false;
	}

	@ConfigItem(
		keyName = "integerScaling",
		name = "Integer Scaling",
		description = "Uses a whole-number scale factor."
	)
	default boolean integerScaling()
	{
		return false;
	}

	@ConfigItem(
		keyName = "keepAspectRatio",
		name = "Keep Aspect Ratio",
		description = "Keeps the game aspect ratio while stretching."
	)
	default boolean keepAspectRatio()
	{
		return true;
	}

	@Range(
		min = 1,
		max = 8
	)
	@ConfigItem(
		keyName = "scalingFactor",
		name = "Resizable Scale Factor",
		description = "Scales the game view by this whole-number factor (1x, 2x, 3x, ...)."
	)
	default int scaling()
	{
		return 1;
	}
}
