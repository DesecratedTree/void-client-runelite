package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.api.MenuEntry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuOpened
{
	private MenuEntry[] menuEntries;
}
