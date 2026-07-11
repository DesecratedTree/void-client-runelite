package net.runelite.client.plugins.inventorygrid;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Inventory Grid",
	description = "Adds a grid and drop preview to the inventory when dragging.",
	tags = {"inventory", "grid", "drag", "items"},
	enabledByDefault = true,
	loadWhenOutdated = true
)
public class InventoryGridPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private InventoryGridOverlay overlay;

	@Provides
	InventoryGridConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InventoryGridConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		mouseManager.registerMouseListener(overlay);
	}

	@Override
	protected void shutDown()
	{
		mouseManager.unregisterMouseListener(overlay);
		overlay.resetDrag();
		overlayManager.remove(overlay);
	}
}
