package net.runelite.client.plugins.stretchedmode;

import com.GameClient;
import com.google.inject.Provides;
import javax.inject.Inject;
import com.google.inject.name.Named;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Stretched Mode - Beta",
	description = "Stretches the game in fixed and resizable modes.",
	tags = {"resize", "ui", "interface", "stretch", "scaling", "fixed"},
	hidden = true,
	enabledByDefault = false,
	loadInSafeMode = false,
	loadWhenOutdated = true
)
public class StretchedModePlugin extends Plugin
{
	@Inject
	private GameClient client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private StretchedModeConfig config;

	@Inject
	@Named("safeMode")
	private boolean safeMode;

	@Provides
	StretchedModeConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(StretchedModeConfig.class);
	}

	@Override
	protected void startUp()
	{
		if (safeMode)
		{
			clientThread.invoke(() ->
			{
				client.setStretchedEnabled(false);
				client.invalidateStretching(true);
			});
			return;
		}

		clientThread.invoke(() ->
		{
			applyConfig();
			client.setStretchedEnabled(true);
			client.invalidateStretching(true);
		});
	}

	@Override
	protected void shutDown()
	{
		clientThread.invoke(() ->
		{
			client.setStretchedEnabled(false);
			client.invalidateStretching(true);
		});
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!"stretchedmode".equals(event.getGroup()))
		{
			return;
		}

		updateConfig();
	}

	private void updateConfig()
	{
		if (safeMode)
		{
			clientThread.invoke(() ->
			{
				client.setStretchedEnabled(false);
				client.invalidateStretching(true);
			});
			return;
		}

		clientThread.invoke(() ->
		{
			applyConfig();
			client.invalidateStretching(true);
		});
	}

	private void applyConfig()
	{
		client.setStretchedFast(config.increasedPerformance());
		client.setStretchedIntegerScaling(config.integerScaling());
		client.setStretchedKeepAspectRatio(config.keepAspectRatio());
		client.setScalingFactor(config.scaling() * 100);
	}
}
