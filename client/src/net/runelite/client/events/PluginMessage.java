package net.runelite.client.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PluginMessage
{
	private String namespace;
	private String name;
	private Object payload;
}
