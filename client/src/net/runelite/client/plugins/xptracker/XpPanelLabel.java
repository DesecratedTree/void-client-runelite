package net.runelite.client.plugins.xptracker;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.util.QuantityFormatter;

@RequiredArgsConstructor
public enum XpPanelLabel
{
	XP_GAINED("Gained", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getXpGainedInSession(), true)),
	XP_HOUR("Per hour", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getXpPerHour(), true)),
	XP_LEFT("XP left", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getXpRemainingToGoal(), true)),
	TIME_TILL_LEVEL("TTL", XpSnapshotSingle::getTimeTillGoal),
	ACTIONS_LEFT("Actions", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getActionsRemainingToGoal(), true)),
	ACTIONS_HOUR("Actions/hr", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getActionsPerHour(), true)),
	ACTIONS_DONE("Actions done", xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getActionsInSession(), true));

	@Getter
	private final String key;

	@Getter
	private final Function<XpSnapshotSingle, String> valueFunc;
}
