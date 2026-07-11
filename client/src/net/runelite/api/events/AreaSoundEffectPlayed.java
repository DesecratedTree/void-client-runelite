package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaSoundEffectPlayed
{
	private int soundId;
	private int sceneX;
	private int sceneY;
	private int range;
	private int delay;
	private boolean consumed;

	public void consume()
	{
		consumed = true;
	}
}
