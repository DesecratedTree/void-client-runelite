package net.runelite.client.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.client.config.RuneScapeProfile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileChanged
{
	private RuneScapeProfile profile;
}
