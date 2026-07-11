package net.runelite.client.events;

import lombok.Value;

@Value
public class NetworkPacketReceived
{
	private final int opcode;
	private final int declaredLength;
	private final int payloadLength;
}
