package net.runelite.client.events;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLootReceived
{
	private Object player;
	private Collection<?> items;
}
