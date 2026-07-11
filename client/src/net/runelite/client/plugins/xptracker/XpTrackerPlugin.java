package net.runelite.client.plugins.xptracker;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import lombok.Value;
import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@PluginDescriptor(
	name = "XP Tracker",
	description = "Tracks XP gained by skill.",
	tags = {"xp", "experience", "levels", "tracker"},
	enabledByDefault = true,
	loadWhenOutdated = true
)
public class XpTrackerPlugin extends Plugin
{
	private static final String CONFIG_GROUP = "xptracker";
	private static final String STATE_KEY = "state";
	private static final String ENTRY_SEPARATOR = ";";
	private static final String FIELD_SEPARATOR = ",";

	@Inject
	private XpTrackerConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ClientToolbar clientToolbar;

	private XpPanel panel;
	private NavigationButton navButton;
	private final Map<Skill, XpState> skills = new EnumMap<>(Skill.class);
	private long startTime;

	@Provides
	XpTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(XpTrackerConfig.class);
	}

	@Override
	protected void startUp()
	{
		startTime = System.currentTimeMillis();
		loadState();
		panel = injector.getInstance(XpPanel.class);
		navButton = NavigationButton.builder()
			.tooltip("XP Tracker")
			.icon(icon())
			.priority(5)
			.panel(panel)
			.build();
		clientToolbar.addNavigation(navButton);
		refreshPanel();
	}

	@Override
	protected void shutDown()
	{
		saveState();
		clientToolbar.removeNavigation(navButton);
		panel = null;
		navButton = null;
		skills.clear();
		startTime = System.currentTimeMillis();
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		record(event.getSkill(), event.getXp(), event.getLevel(), event.getBoostedLevel());
	}

	Collection<XpState> getSkills()
	{
		if (skills.isEmpty())
		{
			return Collections.emptyList();
		}

		List<XpState> entries = new ArrayList<>(skills.values());
		entries.removeIf(state -> state.getGained() <= 0);
		entries.sort((left, right) -> Long.compare(right.getLastUpdated(), left.getLastUpdated()));
		return entries;
	}

	int getTotalGained()
	{
		int total = 0;
		for (XpState state : skills.values())
		{
			total += state.getGained();
		}
		return total;
	}

	int getXpPerHour()
	{
		long total = 0L;
		for (XpState state : getSkills())
		{
			total += xpPerHour(state);
		}
		return (int) Math.min(Integer.MAX_VALUE, total);
	}

	static int xpPerHour(XpState state)
	{
		long elapsed = Math.max(1L, System.currentTimeMillis() - state.getLastUpdated() + 60000L);
		return (int) Math.min(Integer.MAX_VALUE, state.getGained() * 3600000L / elapsed);
	}

	private void record(Skill skill, int xp, int level, int boostedLevel)
	{
		if (skill == null || skill == Skill.OVERALL || xp < 0)
		{
			return;
		}

		XpState state = skills.get(skill);
		if (state == null)
		{
			skills.put(skill, new XpState(skill, xp, xp, 0, level, boostedLevel, 0, 0, startTime));
			saveState();
			refreshPanel();
			return;
		}

		int delta = Math.max(0, xp - state.getCurrentXp());
		int gained = Math.max(0, xp - state.getStartXp());
		int currentXp = Math.max(state.getCurrentXp(), xp);
		int actions = state.getActions() + (delta > 0 ? 1 : 0);
		int xpPerAction = actions <= 0 ? state.getXpPerAction() : Math.max(1, gained / actions);
		long updated = delta > 0 ? System.currentTimeMillis() : state.getLastUpdated();
		if (gained != state.getGained() || currentXp != state.getCurrentXp() || level != state.getLevel() || boostedLevel != state.getBoostedLevel())
		{
			skills.put(skill, new XpState(skill, state.getStartXp(), currentXp, gained, level, boostedLevel, actions, xpPerAction, updated));
			saveState();
			refreshPanel();
		}
	}

	void reset()
	{
		skills.clear();
		startTime = System.currentTimeMillis();
		saveState();
		refreshPanel();
	}

	private void loadState()
	{
		skills.clear();
		if (!config.saveState())
		{
			return;
		}

		String saved = configManager.getConfiguration(CONFIG_GROUP, STATE_KEY);
		if (saved == null || saved.isEmpty())
		{
			return;
		}

		for (String entry : saved.split(ENTRY_SEPARATOR))
		{
			String[] fields = entry.split(FIELD_SEPARATOR);
			if (fields.length != 9)
			{
				continue;
			}

			try
			{
				Skill skill = Skill.valueOf(fields[0]);
				if (skill == Skill.OVERALL)
				{
					continue;
				}

				skills.put(skill, new XpState(
					skill,
					Integer.parseInt(fields[1]),
					Integer.parseInt(fields[2]),
					Integer.parseInt(fields[3]),
					Integer.parseInt(fields[4]),
					Integer.parseInt(fields[5]),
					Integer.parseInt(fields[6]),
					Integer.parseInt(fields[7]),
					Long.parseLong(fields[8])));
			}
			catch (IllegalArgumentException ex)
			{
			}
		}
	}

	private void saveState()
	{
		if (!config.saveState())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, STATE_KEY);
			return;
		}

		if (skills.isEmpty())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, STATE_KEY);
			return;
		}

		StringBuilder builder = new StringBuilder();
		for (XpState state : skills.values())
		{
			if (builder.length() > 0)
			{
				builder.append(ENTRY_SEPARATOR);
			}

			builder.append(state.getSkill().name()).append(FIELD_SEPARATOR)
				.append(state.getStartXp()).append(FIELD_SEPARATOR)
				.append(state.getCurrentXp()).append(FIELD_SEPARATOR)
				.append(state.getGained()).append(FIELD_SEPARATOR)
				.append(state.getLevel()).append(FIELD_SEPARATOR)
				.append(state.getBoostedLevel()).append(FIELD_SEPARATOR)
				.append(state.getActions()).append(FIELD_SEPARATOR)
				.append(state.getXpPerAction()).append(FIELD_SEPARATOR)
				.append(state.getLastUpdated());
		}

		configManager.setConfiguration(CONFIG_GROUP, STATE_KEY, builder.toString());
	}

	private void refreshPanel()
	{
		if (panel == null)
		{
			return;
		}

		Collection<XpState> entries = getSkills();
		int totalGained = getTotalGained();
		int xpPerHour = getXpPerHour();
		panel.rebuild(entries, totalGained, xpPerHour);
	}

	void resetSkillState(Skill skill)
	{
		if (skills.remove(skill) != null)
		{
			saveState();
			refreshPanel();
		}
	}

	void resetOtherSkillState(Skill skill)
	{
		skills.keySet().removeIf(existing -> existing != skill);
		saveState();
		refreshPanel();
	}

	void resetSkillPerHourState(Skill skill)
	{
		XpState state = skills.get(skill);
		if (state == null)
		{
			return;
		}

		skills.put(skill, new XpState(
			skill,
			state.getCurrentXp(),
			state.getCurrentXp(),
			0,
			state.getLevel(),
			state.getBoostedLevel(),
			0,
			0,
			System.currentTimeMillis()));
		saveState();
		refreshPanel();
	}

	private static BufferedImage icon()
	{
        final BufferedImage icon = ImageUtil.loadImageResource(XpTrackerPlugin.class, "/skill_icons/overall.png");

        return icon;
	}

	@Value
	static class XpState
	{
		Skill skill;
		int startXp;
		int currentXp;
		int gained;
		int level;
		int boostedLevel;
		int actions;
		int xpPerAction;
		long lastUpdated;
	}
}
