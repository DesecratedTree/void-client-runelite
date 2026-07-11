package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.api.Skill;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FakeXpDrop
{
	private Skill skill;
}
