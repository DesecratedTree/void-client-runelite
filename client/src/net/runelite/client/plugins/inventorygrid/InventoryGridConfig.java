package net.runelite.client.plugins.inventorygrid;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("inventorygrid")
public interface InventoryGridConfig extends Config
{
	@ConfigItem(
		keyName = "showItem",
		name = "Item Preview",
		description = "Renders a transparent item preview underneath the cursor while dragging."
	)
	default boolean showItem()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showHighlight",
		name = "Highlight",
		description = "Highlights the inventory slot currently under the cursor while dragging."
	)
	default boolean showHighlight()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showGrid",
		name = "Grid",
		description = "Shows the inventory grid while dragging."
	)
	default boolean showGrid()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "highlightColor",
		name = "Highlight Color",
		description = "Configures the inventory slot highlight color."
	)
	default Color highlightColor()
	{
		return new Color(255, 255, 255, 80);
	}

	@Alpha
	@ConfigItem(
		keyName = "gridColor",
		name = "Grid Color",
		description = "Configures the inventory grid color."
	)
	default Color gridColor()
	{
		return new Color(255, 255, 255, 35);
	}
}
