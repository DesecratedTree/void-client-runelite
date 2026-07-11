package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrandExchangeOfferChanged
{
	private Object offer;
	private int slot;
}
