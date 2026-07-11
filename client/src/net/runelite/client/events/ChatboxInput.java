package net.runelite.client.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatboxInput
{
	private String value;
	private boolean consumed;

	public void consume()
	{
		consumed = true;
	}
}
