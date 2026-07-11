package net.runelite.client.plugins.animationsmoothing;

import com.GameClient;
import javax.inject.Inject;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Animation Smoothing",
	description = "Smooths model animations between game ticks.",
	tags = {"animation", "smoothing", "interpolation", "model"},
	enabledByDefault = false,
	loadWhenOutdated = true
)
public class AnimationSmoothingPlugin extends Plugin
{
	@Inject
	private GameClient client;

	@Override
	protected void startUp()
	{
		client.setAnimationSmoothingEnabled(true);
	}

	@Override
	protected void shutDown()
	{
		client.setAnimationSmoothingEnabled(false);
	}
}
