package net.runelite.client.game;

import com.GameClient;

public interface ItemPriceProvider
{
	int getPrice(GameClient.GroundItemInfo item);

	int getHighAlchemyPrice(GameClient.GroundItemInfo item);
}
