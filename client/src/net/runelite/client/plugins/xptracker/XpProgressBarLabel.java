package net.runelite.client.plugins.xptracker;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.util.QuantityFormatter;

@RequiredArgsConstructor
public enum XpProgressBarLabel
{
	PERCENTAGE(xp -> XpInfoBox.TWO_DECIMAL_FORMAT.format(xp.getSkillProgressToGoal()) + "%"),
	XP_LEFT(xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getXpRemainingToGoal(), true)),
	XP_GAINED(xp -> QuantityFormatter.quantityToRSDecimalStack(xp.getXpGainedInSession(), true)),
	TIME_LEFT(XpSnapshotSingle::getTimeTillGoal);

	@Getter
	private final Function<XpSnapshotSingle, String> valueFunc;
}
