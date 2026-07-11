package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage
{
	private Object messageNode;
	private Object type;
	private String name;
	private String message;
	private String sender;
	private int timestamp;
}
