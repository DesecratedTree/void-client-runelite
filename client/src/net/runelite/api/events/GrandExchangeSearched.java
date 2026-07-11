package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrandExchangeSearched
{
	private String searchTerm;
	private boolean consumed;

	public void consume()
	{
		consumed = true;
	}
}
