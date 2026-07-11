package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.api.coords.LocalPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectileMoved
{
	private Object projectile;
	private LocalPoint position;
	private int z;
}
