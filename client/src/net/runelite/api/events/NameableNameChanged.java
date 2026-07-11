package net.runelite.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameableNameChanged
{
	private Object nameable;
	private String oldName;
	private String newName;
}
