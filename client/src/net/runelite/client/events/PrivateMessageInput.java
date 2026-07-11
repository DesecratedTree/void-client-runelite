package net.runelite.client.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageInput
{
	private String target;
	private String message;
	private boolean consumed;

	public void consume()
	{
		consumed = true;
	}
}
