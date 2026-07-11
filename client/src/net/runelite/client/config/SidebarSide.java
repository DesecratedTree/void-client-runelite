/*
 * Copyright (c) 2026
 * All rights reserved.
 */
package net.runelite.client.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SidebarSide
{
	LEFT("Left"),
	RIGHT("Right");

	private final String side;

	@Override
	public String toString()
	{
		return side;
	}
}
