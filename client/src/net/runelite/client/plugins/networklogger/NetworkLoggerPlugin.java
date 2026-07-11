package net.runelite.client.plugins.networklogger;

import com.google.common.collect.EvictingQueue;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NetworkPacketQueued;
import net.runelite.client.events.NetworkPacketReceived;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.devtools.DevToolsPlugin;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

@PluginDescriptor(
	name = "Network Logger",
	description = "Sample in-memory packet and tick event logger for development",
	tags = {"network", "packets", "logger", "developer", "tick"},
	enabledByDefault = false,
	loadWhenOutdated = true
)
public class NetworkLoggerPlugin extends Plugin
{
	private static final int MAX_TICKS = 200;
	private static final int MAX_PRE_TICK_EVENTS = 400;
	private static final long FALLBACK_TICK_MILLIS = 600L;
	private static final DateTimeFormatter TICK_TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm:ss a");

	private final EvictingQueue<TickLog> completedTicks = EvictingQueue.create(MAX_TICKS);
	private final EvictingQueue<String> pendingEvents = EvictingQueue.create(MAX_PRE_TICK_EVENTS);
	private int tickCounter;
	private boolean paused;
	private long lastBoundaryMillis;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private NetworkLoggerPanel panel;

	private NavigationButton navButton;

	@Override
	protected void startUp()
	{
		lastBoundaryMillis = System.currentTimeMillis();
		BufferedImage icon = ImageUtil.loadImageResource(DevToolsPlugin.class, "devtools_icon.png");
		navButton = NavigationButton.builder()
			.tooltip("Network Logger")
			.icon(icon)
			.priority(2)
			.panel(panel)
			.build();
		clientToolbar.addNavigation(navButton);
		panel.refreshLater();
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		synchronized (this)
		{
			completedTicks.clear();
			pendingEvents.clear();
			tickCounter = 0;
			paused = false;
		}
		panel.refreshLater();
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		recordTickBoundary("GameTick");
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		recordFallbackTickBoundary();
	}

	@Subscribe
	public void onOutgoingPacket(NetworkPacketQueued event)
	{
		record(formatOutgoing(event));
	}

	@Subscribe
	public void onIncomingPacket(NetworkPacketReceived event)
	{
		record(formatIncoming(event));
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		record("state " + event.getGameState());
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		record("menu " + Text.removeTags(event.getMenuOption()) + " -> " + Text.removeTags(event.getMenuTarget()));
	}

	synchronized void clear()
	{
		completedTicks.clear();
		pendingEvents.clear();
		tickCounter = 0;
		lastBoundaryMillis = System.currentTimeMillis();
		panel.refreshLater();
	}

	synchronized void togglePaused()
	{
		paused = !paused;
		panel.refreshLater();
	}

	synchronized boolean isPaused()
	{
		return paused;
	}

	synchronized List<TickLog> getTickLogs()
	{
		List<TickLog> logs = new ArrayList<>(completedTicks.size() + 1);
		logs.addAll(completedTicks);
		if (!pendingEvents.isEmpty())
		{
			logs.add(new TickLog(tickCounter + 1, currentTimeLabel(), new ArrayList<>(pendingEvents)));
		}
		Collections.reverse(logs);
		return logs;
	}

	private synchronized void record(String line)
	{
		if (paused)
		{
			return;
		}

		pendingEvents.add(line);
		panel.refreshLater();
	}

	private synchronized void recordTickBoundary(String source)
	{
		if (paused)
		{
			return;
		}

		flushPending(source);
	}

	private synchronized void recordFallbackTickBoundary()
	{
		if (paused || pendingEvents.isEmpty())
		{
			return;
		}

		long now = System.currentTimeMillis();
		if (now - lastBoundaryMillis >= FALLBACK_TICK_MILLIS)
		{
			flushPending("ClientTick");
		}
	}

	private void flushPending(String source)
	{
		List<String> events = new ArrayList<>(pendingEvents);
		events.add("boundary=" + source);
		completedTicks.add(new TickLog(++tickCounter, currentTimeLabel(), events));
		pendingEvents.clear();
		lastBoundaryMillis = System.currentTimeMillis();
		panel.refreshLater();
	}

	private static String formatOutgoing(NetworkPacketQueued event)
	{
		return "out opcode=" + event.getOpcode()
			+ " expected=" + event.getExpectedLength()
			+ " bytes=" + event.getPayloadLength()
			+ " source=" + event.getSource();
	}

	private static String formatIncoming(NetworkPacketReceived event)
	{
		return "in opcode=" + event.getOpcode()
			+ " declared=" + event.getDeclaredLength()
			+ " bytes=" + event.getPayloadLength();
	}

	private static String currentTimeLabel()
	{
		return LocalTime.now().format(TICK_TIME_FORMAT);
	}

	static final class TickLog
	{
		private final int tick;
		private final String marker;
		private final List<String> events;

		private TickLog(int tick, String marker, List<String> events)
		{
			this.tick = tick;
			this.marker = marker;
			this.events = Collections.unmodifiableList(events);
		}

		int getTick()
		{
			return tick;
		}

		String getMarker()
		{
			return marker;
		}

		List<String> getEvents()
		{
			return events;
		}

		int getEventCount()
		{
			return events.size();
		}
	}
}
