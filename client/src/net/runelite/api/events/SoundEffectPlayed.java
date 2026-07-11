package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoundEffectPlayed
{
	private Object source;
	private int soundId;
	private int delay;
	private boolean consumed;

	public void consume()
	{
		consumed = true;
	}
}
