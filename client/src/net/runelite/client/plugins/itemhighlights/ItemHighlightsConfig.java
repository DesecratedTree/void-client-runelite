package net.runelite.client.plugins.itemhighlights;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(ItemHighlightsConfig.GROUP)
public interface ItemHighlightsConfig extends Config
{
	String GROUP = "itemhighlights";

	@ConfigSection(
		name = "Items",
		description = "Ground items selected for highlighting.",
		position = 0
	)
	String itemsSection = "itemsSection";

	@ConfigSection(
		name = "Tile",
		description = "Ground item tile highlight settings.",
		position = 1
	)
	String tileSection = "tileSection";

	@ConfigSection(
		name = "True tile",
		description = "Ground item true tile highlight settings.",
		position = 2
	)
	String trueTileSection = "trueTileSection";

	@ConfigSection(
		name = "Names",
		description = "Ground item name drawing settings.",
		position = 3
	)
	String namesSection = "namesSection";

	@ConfigSection(
		name = "Menu",
		description = "Ground item menu highlight settings.",
		position = 4
	)
	String menuSection = "menuSection";

	@ConfigSection(
		name = "Behavior",
		description = "Ground item highlight behavior settings.",
		position = 5
	)
	String behaviorSection = "behaviorSection";

	@ConfigSection(
		name = "Debug",
		description = "Ground item diagnostics.",
		position = 6
	)
	String debugSection = "debugSection";

	@ConfigItem(
		position = 0,
		keyName = "itemsToHighlight",
		name = "Items to highlight",
		description = "List of ground item names to highlight. Format: item1, item2, item3",
		section = itemsSection
	)
	default String getItemsToHighlight()
	{
		return "";
	}

	@ConfigItem(
		keyName = "itemsToHighlight",
		name = "",
		description = ""
	)
	void setItemsToHighlight(String itemsToHighlight);

	@ConfigItem(
		position = 0,
		keyName = "highlightTile",
		name = "Highlight tile",
		description = "Configures whether or not ground items should be highlighted by tile.",
		section = tileSection
	)
	default boolean highlightTile()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "tileOutline",
		name = "Outline",
		description = "Draws an outline around the ground item tile.",
		section = tileSection
	)
	default boolean tileOutline()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		position = 2,
		keyName = "highlightColor",
		name = "Outline color",
		description = "Outline color of the highlighted ground item tile, menu, and text.",
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
		description = "Fill color of the highlighted ground item tile.",
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
		description = "Width of highlighted ground item outlines.",
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
		description = "Configures whether or not ground items should be highlighted by true tile.",
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
		description = "Draws an outline around the ground item true tile.",
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
		description = "Outline color of the ground item true tile highlight.",
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
		description = "Fill color of the ground item true tile highlight.",
		section = trueTileSection
	)
	default Color trueTileFillColor()
	{
		return new Color(18, 44, 92, 60);
	}

	@ConfigItem(
		position = 0,
		keyName = "drawNames",
		name = "Draw names above item",
		description = "Configures whether or not ground item names should be drawn above the item.",
		section = namesSection
	)
	default boolean drawNames()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "showQuantity",
		name = "Show quantity",
		description = "Shows stack quantity in the drawn ground item name.",
		section = namesSection
	)
	default boolean showQuantity()
	{
		return true;
	}

	enum MenuHighlightStyle
	{
		NONE,
		NAME
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
		return MenuHighlightStyle.NAME;
	}

	@ConfigItem(
		position = 0,
		keyName = "showMenuHighlight",
		name = "Highlight menu entries",
		description = "Recolors right-click menu entries for highlighted ground items.",
		section = behaviorSection
	)
	default boolean showMenuHighlight()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "onlyShowLoot",
		name = "Only show named loot",
		description = "Skip unnamed fallback items such as generic Item 123 labels.",
		section = behaviorSection
	)
	default boolean onlyShowNamedLoot()
	{
		return true;
	}

	@ConfigItem(
		position = 0,
		keyName = "debugOverlay",
		name = "Debug overlay",
		description = "Shows ground item startup, tag, menu, and render diagnostics.",
		section = debugSection
	)
	default boolean debugOverlay()
	{
		return false;
	}
}
