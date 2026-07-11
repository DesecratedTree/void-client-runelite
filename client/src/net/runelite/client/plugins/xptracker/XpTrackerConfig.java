package net.runelite.client.plugins.xptracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("xptracker")
public interface XpTrackerConfig extends Config
{
	@ConfigItem(
		keyName = "showOverlay",
		name = "Show Overlay",
		description = "Shows the XP tracker panel.",
		hidden = true
	)
	default boolean showOverlay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showXpPerHour",
		name = "XP/Hr",
		description = "Shows an estimated XP per hour total.",
		hidden = true
	)
	default boolean showXpPerHour()
	{
		return true;
	}

	@Range(
		min = 1,
		max = 12
	)
	@ConfigItem(
		keyName = "maxRows",
		name = "Rows",
		description = "Limits how many skill rows are shown.",
		hidden = true
	)
	default int maxRows()
	{
		return 6;
	}

	@ConfigItem(
		position = 3,
		keyName = "saveState",
		name = "Save State",
		description = "Saves the current XP tracker state between client sessions."
	)
	default boolean saveState()
	{
		return true;
	}

	@ConfigItem(
		position = 4,
		keyName = "showIntermediateLevels",
		name = "Show intermediate level markers",
		description = "Marks intermediate levels on the progress bar."
	)
	default boolean showIntermediateLevels()
	{
		return false;
	}

	@Units(Units.MINUTES)
	@ConfigItem(
		position = 5,
		keyName = "pauseSkillAfter",
		name = "Auto pause after",
		description = "Pauses skills after the configured number of minutes without XP gain. 0 disables this."
	)
	default int pauseSkillAfter()
	{
		return 0;
	}

	@ConfigItem(
		position = 6,
		keyName = "prioritizeRecentXpSkills",
		name = "Prioritize recent skills",
		description = "Moves recently updated XP trackers to the top."
	)
	default boolean prioritizeRecentXpSkills()
	{
		return true;
	}

	@ConfigItem(
		position = 7,
		keyName = "xpPanelLabel1",
		name = "XP panel label 1",
		description = "First label shown in each XP tracker card."
	)
	default XpPanelLabel xpPanelLabel1()
	{
		return XpPanelLabel.XP_GAINED;
	}

	@ConfigItem(
		position = 8,
		keyName = "xpPanelLabel2",
		name = "XP panel label 2",
		description = "Second label shown in each XP tracker card."
	)
	default XpPanelLabel xpPanelLabel2()
	{
		return XpPanelLabel.XP_HOUR;
	}

	@ConfigItem(
		position = 9,
		keyName = "xpPanelLabel3",
		name = "XP panel label 3",
		description = "Third label shown in each XP tracker card."
	)
	default XpPanelLabel xpPanelLabel3()
	{
		return XpPanelLabel.TIME_TILL_LEVEL;
	}

	@ConfigItem(
		position = 10,
		keyName = "xpPanelLabel4",
		name = "XP panel label 4",
		description = "Fourth label shown in each XP tracker card."
	)
	default XpPanelLabel xpPanelLabel4()
	{
		return XpPanelLabel.ACTIONS_LEFT;
	}

	@ConfigItem(
		position = 11,
		keyName = "progressBarLabel",
		name = "Progress bar label",
		description = "Label shown on each XP progress bar."
	)
	default XpProgressBarLabel progressBarLabel()
	{
		return XpProgressBarLabel.PERCENTAGE;
	}

	@ConfigItem(
		position = 12,
		keyName = "progressBarTooltipLabel",
		name = "Progress tooltip label",
		description = "Label shown in each XP progress bar tooltip."
	)
	default XpProgressBarLabel progressBarTooltipLabel()
	{
		return XpProgressBarLabel.XP_LEFT;
	}

	@ConfigItem(
		position = 13,
		keyName = "onScreenDisplayMode",
		name = "Overlay label 1",
		description = "First label shown on the on-screen XP tracker."
	)
	default XpPanelLabel onScreenDisplayMode()
	{
		return XpPanelLabel.XP_GAINED;
	}

	@ConfigItem(
		position = 14,
		keyName = "onScreenDisplayModeBottom",
		name = "Overlay label 2",
		description = "Second label shown on the on-screen XP tracker."
	)
	default XpPanelLabel onScreenDisplayModeBottom()
	{
		return XpPanelLabel.XP_HOUR;
	}
}
