package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VarbitChanged
{
	private int varpId = -1;
	private int varbitId = -1;
	private int value;

	@Deprecated
	public int getIndex()
	{
		return varpId;
	}
}
