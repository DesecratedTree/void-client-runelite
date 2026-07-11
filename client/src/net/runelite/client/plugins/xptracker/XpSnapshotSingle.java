package net.runelite.client.plugins.xptracker;

import net.runelite.api.Experience;

class XpSnapshotSingle
{
	private final XpTrackerPlugin.XpState state;
	private final long elapsedMillis;

	XpSnapshotSingle(XpTrackerPlugin.XpState state)
	{
		this.state = state;
		this.elapsedMillis = Math.max(1L, System.currentTimeMillis() - state.getLastUpdated() + 60000L);
	}

	int getXpGainedInSession()
	{
		return state.getGained();
	}

	int getXpPerHour()
	{
		return XpTrackerPlugin.xpPerHour(state);
	}

	int getActionsInSession()
	{
		return state.getActions();
	}

	int getActionsPerHour()
	{
		return (int) Math.min(Integer.MAX_VALUE, state.getActions() * 3600000L / elapsedMillis);
	}

	int getActionsRemainingToGoal()
	{
		int xpPerAction = state.getXpPerAction();
		return xpPerAction <= 0 ? 0 : (int) Math.ceil((double) getXpRemainingToGoal() / xpPerAction);
	}

	int getStartLevel()
	{
		return Math.max(1, Experience.getLevelForXp(Math.max(0, state.getCurrentXp())));
	}

	int getEndLevel()
	{
		return Math.min(Experience.MAX_REAL_LEVEL, getStartLevel() + 1);
	}

	int getStartGoalXp()
	{
		return Experience.getXpForLevel(getStartLevel());
	}

	int getEndGoalXp()
	{
		return getEndLevel() > getStartLevel()
			? Experience.getXpForLevel(getEndLevel())
			: Experience.getXpForLevel(getStartLevel());
	}

	int getXpRemainingToGoal()
	{
		if (getStartLevel() >= Experience.MAX_REAL_LEVEL)
		{
			return 0;
		}
		return Math.max(0, getEndGoalXp() - state.getCurrentXp());
	}

	double getSkillProgressToGoal()
	{
		int startXp = getStartGoalXp();
		int endXp = getEndGoalXp();
		int needed = Math.max(1, endXp - startXp);
		int gained = Math.max(0, state.getCurrentXp() - startXp);
		return Math.min(100.0d, gained * 100.0d / needed);
	}

	String getTimeTillGoal()
	{
		int xpPerHour = getXpPerHour();
		int remaining = getXpRemainingToGoal();
		if (xpPerHour <= 0 || remaining <= 0)
		{
			return "--:--";
		}

		long totalSeconds = (long) remaining * 3600L / xpPerHour;
		long hours = totalSeconds / 3600L;
		long minutes = (totalSeconds % 3600L) / 60L;
		long seconds = totalSeconds % 60L;
		if (hours > 99)
		{
			return "99h+";
		}
		return hours > 0 ? String.format("%dh %02dm", hours, minutes) : String.format("%dm %02ds", minutes, seconds);
	}

	boolean isCompactView()
	{
		return false;
	}
}
