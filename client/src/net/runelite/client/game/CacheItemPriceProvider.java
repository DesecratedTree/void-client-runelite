package net.runelite.client.game;

import com.GameClient;
import javax.inject.Singleton;

@Singleton
public class CacheItemPriceProvider implements ItemPriceProvider
{
	@Override
	public int getPrice(GameClient.GroundItemInfo item)
	{
		return item == null ? 0 : Math.max(0, item.getPrice());
	}

	@Override
	public int getHighAlchemyPrice(GameClient.GroundItemInfo item)
	{
		return (int) (getPrice(item) * 0.6D);
	}
}
