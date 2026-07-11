package net.runelite.client.plugins.npchighlight;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup(NpcIndicatorsConfig.GROUP)
public interface NpcIndicatorsConfig extends Config
{
	String GROUP = "npcindicators";

	@ConfigSection(
		name = "NPCs",
		description = "NPCs selected for highlighting.",
		position = 0
	)
	String npcsSection = "npcsSection";

	@ConfigSection(
		name = "Hull",
		description = "Hull highlight settings.",
		position = 1
	)
	String hullSection = "hullSection";

	@ConfigSection(
		name = "Tile",
		description = "Current tile highlight settings.",
		position = 2
	)
	String tileSection = "tileSection";

	@ConfigSection(
		name = "True tile",
		description = "True tile highlight settings.",
		position = 3
	)
	String trueTileSection = "trueTileSection";

	@ConfigSection(
		name = "South west tile",
		description = "South west tile highlight settings.",
		position = 4
	)
	String southWestTileSection = "southWestTileSection";

	@ConfigSection(
		name = "South west true tile",
		description = "South west true tile highlight settings.",
		position = 5
	)
	String southWestTrueTileSection = "southWestTrueTileSection";

	@ConfigSection(
		name = "Names",
		description = "NPC name drawing settings.",
		position = 6
	)
	String namesSection = "namesSection";

	@ConfigSection(
		name = "Menu",
		description = "NPC menu highlight settings.",
		position = 7
	)
	String menuSection = "menuSection";

	@ConfigSection(
		name = "Behavior",
		description = "NPC highlight behavior settings.",
		position = 8
	)
	String behaviorSection = "behaviorSection";

	@ConfigSection(
		name = "Debug",
		description = "NPC Indicators diagnostics.",
		position = 9
	)
	String debugSection = "debugSection";

	@ConfigItem(
		position = 0,
		keyName = "npcToHighlight",
		name = "NPCs to highlight",
		description = "List of NPC names to highlight. Format: npc1, npc2, npc3",
		section = npcsSection
	)
	default String getNpcToHighlight()
	{
		return "";
	}

	@ConfigItem(
		keyName = "npcToHighlight",
		name = "",
		description = ""
	)
	void setNpcToHighlight(String npcsToHighlight);

	@ConfigItem(
		keyName = "npcIdsToHighlight",
		name = "NPC IDs to highlight",
		description = "",
		hidden = true
	)
	default String npcIdsToHighlight()
	{
		return "";
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightHull",
		name = "Highlight hull",
		description = "Configures whether or not NPCs should be highlighted by hull.",
		section = hullSection
	)
	default boolean highlightHull()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "hullOutline",
		name = "Outline",
		description = "Draws an outline around the NPC hull.",
		section = hullSection
	)
	default boolean hullOutline()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "highlightOutline",
		name = "Model outline",
		description = "Uses the NPC model hull hook when available.",
		section = hullSection
	)
	default boolean highlightOutline()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		position = 3,
		keyName = "hullColor",
		name = "Outline color",
		description = "Outline color of the NPC hull highlight.",
		section = hullSection
	)
	default Color hullColor()
	{
		return Color.CYAN;
	}

	@Alpha
	@ConfigItem(
		position = 4,
		keyName = "hullFillColor",
		name = "Fill color",
		description = "Fill color of the NPC hull highlight.",
		section = hullSection
	)
	default Color hullFillColor()
	{
		return new Color(0, 255, 255, 20);
	}

	@Range(
		min = 0,
		max = 4
	)
	@ConfigItem(
		position = 5,
		keyName = "outlineFeather",
		name = "Outline feather",
		description = "Specify between 0-4 how much of the model outline should be faded.",
		section = hullSection
	)
	default int outlineFeather()
	{
		return 0;
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightTile",
		name = "Highlight tile",
		description = "Configures whether or not NPCs should be highlighted by tile.",
		section = tileSection
	)
	default boolean highlightTile()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "tileOutline",
		name = "Outline",
		description = "Draws an outline around the NPC tile.",
		section = tileSection
	)
	default boolean tileOutline()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		position = 2,
		keyName = "npcColor",
		name = "Outline color",
		description = "Outline color of the tile highlight, menu, and text.",
		section = tileSection
	)
	default Color highlightColor()
	{
		return Color.CYAN;
	}

	@Alpha
	@ConfigItem(
		position = 3,
		keyName = "fillColor",
		name = "Fill color",
		description = "Fill color of the NPC tile highlight.",
		section = tileSection
	)
	default Color fillColor()
	{
		return new Color(0, 255, 255, 20);
	}

	@ConfigItem(
		position = 4,
		keyName = "borderWidth",
		name = "Outline width",
		description = "Width of highlighted NPC outlines.",
		section = tileSection
	)
	default double borderWidth()
	{
		return 2;
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightTrueTile",
		name = "Highlight true tile",
		description = "Configures whether or not NPCs should be highlighted by true tile.",
		section = trueTileSection
	)
	default boolean highlightTrueTile()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "trueTileOutline",
		name = "Outline",
		description = "Draws an outline around the NPC true tile.",
		section = trueTileSection
	)
	default boolean trueTileOutline()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		position = 2,
		keyName = "trueTileColor",
		name = "Outline color",
		description = "Outline color of the NPC true tile highlight.",
		section = trueTileSection
	)
	default Color trueTileColor()
	{
		return new Color(18, 44, 92, 180);
	}

	@Alpha
	@ConfigItem(
		position = 3,
		keyName = "trueTileFillColor",
		name = "Fill color",
		description = "Fill color of the NPC true tile highlight.",
		section = trueTileSection
	)
	default Color trueTileFillColor()
	{
		return new Color(18, 44, 92, 60);
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightSouthWestTile",
		name = "Highlight south west tile",
		description = "Configures whether or not NPCs should be highlighted by south western tile.",
		section = southWestTileSection
	)
	default boolean highlightSouthWestTile()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "southWestTileOutline",
		name = "Outline",
		description = "Draws an outline around the NPC south west tile.",
		section = southWestTileSection
	)
	default boolean southWestTileOutline()
	{
		return true;
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightSouthWestTrueTile",
		name = "Highlight south west true tile",
		description = "Configures whether or not NPCs should be highlighted by south western true tile.",
		section = southWestTrueTileSection
	)
	default boolean highlightSouthWestTrueTile()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "southWestTrueTileOutline",
		name = "Outline",
		description = "Draws an outline around the NPC south west true tile.",
		section = southWestTrueTileSection
	)
	default boolean southWestTrueTileOutline()
	{
		return true;
	}

	@ConfigItem(
		position = 0,
		keyName = "drawNames",
		name = "Draw names above NPC",
		description = "Configures whether or not NPC names should be drawn above the NPC.",
		section = namesSection
	)
	default boolean drawNames()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "drawCombatLevel",
		name = "Draw combat level",
		description = "Shows NPC combat level in the drawn NPC name.",
		section = namesSection
	)
	default boolean drawCombatLevel()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "highlightColorRelativeToCombatLevel",
		name = "Highlight color relative to combat level",
		description = "Colors drawn NPC names using the client's relative combat level colors.",
		section = namesSection
	)
	default boolean highlightColorRelativeToCombatLevel()
	{
		return false;
	}

	enum MenuHighlightStyle
	{
		NONE,
		LEVEL,
		NAME,
		BOTH
	}

	@ConfigItem(
		position = 0,
		keyName = "highlightMenuStyle",
		name = "Highlight menu style",
		description = "Highlight style in the right-click menu.",
		section = menuSection
	)
	default MenuHighlightStyle highlightMenuStyle()
	{
		return MenuHighlightStyle.NONE;
	}

	@ConfigItem(
		position = 1,
		keyName = "deadNpcMenuColor",
		name = "Dead NPC menu color",
		description = "Color of the NPC menus for dead NPCs.",
		section = menuSection
	)
	Color deadNpcMenuColor();

	@ConfigItem(
		position = 0,
		keyName = "ignoreDeadNpcs",
		name = "Ignore dead NPCs",
		description = "Prevents highlighting NPCs after they are dead.",
		section = behaviorSection
	)
	default boolean ignoreDeadNpcs()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "ignorePets",
		name = "Ignore pets",
		description = "Excludes pets from being highlighted.",
		section = behaviorSection
	)
	default boolean ignorePets()
	{
		return true;
	}

	@ConfigItem(
		position = 0,
		keyName = "debugOverlay",
		name = "Debug overlay",
		description = "Shows NPC Indicators startup, NPC, tag, and projection diagnostics.",
		section = debugSection
	)
	default boolean debugOverlay()
	{
		return false;
	}
}
