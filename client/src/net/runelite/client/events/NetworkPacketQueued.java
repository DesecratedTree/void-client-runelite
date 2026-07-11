package net.runelite.client.events;

import lombok.Value;

@Value
public class NetworkPacketQueued
{
	private final int opcode;
	private final int expectedLength;
	private final int payloadLength;
	private final String source;
}
