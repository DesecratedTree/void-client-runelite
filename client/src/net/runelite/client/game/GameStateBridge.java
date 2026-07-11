package net.runelite.client.game;

import com.GameClient;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Value;
import net.runelite.api.Skill;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemQuantityChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

@Singleton
public class GameStateBridge
{
	private static final long RECENT_NPC_TTL_MILLIS = 10000L;

	private final GameClient client;
	private final EventBus eventBus;
	private final Map<String, GameClient.GroundItemInfo> groundItems = new HashMap<>();
	private final Map<String, GameClient.NpcInfo> npcs = new HashMap<>();
	private final Map<Skill, GameClient.SkillSnapshot> skills = new HashMap<>();
	private final ArrayDeque<RecentNpc> recentNpcDespawns = new ArrayDeque<>();
	private boolean groundItemsInitialized;
	private boolean npcsInitialized;
	private boolean skillsInitialized;

	@Inject
	private GameStateBridge(GameClient client, EventBus eventBus)
	{
		this.client = client;
		this.eventBus = eventBus;
	}

	@Subscribe
	public void onClientTick(ClientTick tick)
	{
		if (client == null)
		{
			return;
		}

		if (!GameEventBridgeHooks.hasDirectNpcHooks())
		{
			updateNpcs();
		}
		else
		{
			seedNpcs();
		}
		if (!GameEventBridgeHooks.hasDirectGroundItemHooks())
		{
			updateGroundItems();
		}
		else
		{
			seedGroundItems();
		}
		if (!GameEventBridgeHooks.hasDirectStatHooks())
		{
			updateSkills();
		}
		else
		{
			seedSkills();
		}
		expireRecentNpcs();
	}

	public Collection<RecentNpc> getRecentNpcDespawns()
	{
		return new ArrayList<>(recentNpcDespawns);
	}

	public RecentNpc findRecentNpcSource(GameClient.GroundItemInfo item)
	{
		RecentNpc nearest = null;
		int nearestDistance = Integer.MAX_VALUE;
		long now = System.currentTimeMillis();
		for (RecentNpc npc : recentNpcDespawns)
		{
			if (npc.getPlane() != item.getPlane() || now - npc.getTime() > RECENT_NPC_TTL_MILLIS)
			{
				continue;
			}

			int distance = Math.abs(npc.getLocalX() - item.getLocalX()) + Math.abs(npc.getLocalY() - item.getLocalY());
			if (distance < nearestDistance)
			{
				nearest = npc;
				nearestDistance = distance;
			}
		}

		return nearest != null && nearestDistance <= 1536 ? nearest : null;
	}

	private void updateGroundItems()
	{
		List<GameClient.GroundItemInfo> currentItems = client.getGroundItems();
		Map<String, GameClient.GroundItemInfo> current = new HashMap<>(currentItems.size());
		for (GameClient.GroundItemInfo item : currentItems)
		{
			String key = groundItemKey(item);
			current.put(key, item);

			GameClient.GroundItemInfo previous = groundItems.get(key);
			if (!groundItemsInitialized)
			{
				continue;
			}

			if (previous == null)
			{
				eventBus.post(new ItemSpawned(tileKey(item), item));
			}
			else if (previous.getQuantity() != item.getQuantity())
			{
				eventBus.post(new ItemQuantityChanged(item, tileKey(item), previous.getQuantity(), item.getQuantity()));
			}
		}

		if (groundItemsInitialized)
		{
			for (Map.Entry<String, GameClient.GroundItemInfo> previous : groundItems.entrySet())
			{
				if (!current.containsKey(previous.getKey()))
				{
					GameClient.GroundItemInfo item = previous.getValue();
					eventBus.post(new ItemDespawned(tileKey(item), item));
				}
			}
		}

		groundItems.clear();
		groundItems.putAll(current);
		groundItemsInitialized = true;
	}

	private void seedGroundItems()
	{
		List<GameClient.GroundItemInfo> currentItems = client.getGroundItems();
		groundItems.clear();
		for (GameClient.GroundItemInfo item : currentItems)
		{
			groundItems.put(groundItemKey(item), item);
		}
		groundItemsInitialized = true;
	}

	private void updateNpcs()
	{
		List<GameClient.NpcInfo> currentNpcs = client.getNpcs();
		Map<String, GameClient.NpcInfo> current = new HashMap<>(currentNpcs.size());
		for (GameClient.NpcInfo npc : currentNpcs)
		{
			String key = npcKey(npc);
			current.put(key, npc);

			if (npcsInitialized && !npcs.containsKey(key))
			{
				eventBus.post(new NpcSpawned(npc));
			}
		}

		if (npcsInitialized)
		{
			for (Map.Entry<String, GameClient.NpcInfo> previous : npcs.entrySet())
			{
				if (!current.containsKey(previous.getKey()))
				{
					GameClient.NpcInfo npc = previous.getValue();
					recentNpcDespawns.addLast(new RecentNpc(npc.getId(), npc.getName(), npc.getLocalX(), npc.getLocalY(), npc.getPlane(), System.currentTimeMillis()));
					eventBus.post(new NpcDespawned(npc));
				}
			}
		}

		npcs.clear();
		npcs.putAll(current);
		npcsInitialized = true;
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		if (!GameEventBridgeHooks.hasDirectNpcHooks() || !(event.getNpc() instanceof GameClient.NpcInfo))
		{
			return;
		}

		GameClient.NpcInfo npc = (GameClient.NpcInfo) event.getNpc();
		recentNpcDespawns.addLast(new RecentNpc(npc.getId(), npc.getName(), npc.getLocalX(), npc.getLocalY(), npc.getPlane(), System.currentTimeMillis()));
	}

	private void seedNpcs()
	{
		List<GameClient.NpcInfo> currentNpcs = client.getNpcs();
		npcs.clear();
		for (GameClient.NpcInfo npc : currentNpcs)
		{
			npcs.put(npcKey(npc), npc);
		}
		npcsInitialized = true;
	}

	private void updateSkills()
	{
		for (GameClient.SkillSnapshot snapshot : client.getSkillSnapshots())
		{
			Skill skill = snapshot.getSkill();
			if (skill == null || skill == Skill.OVERALL)
			{
				continue;
			}

			GameClient.SkillSnapshot previous = skills.get(skill);
			if (skillsInitialized && changed(previous, snapshot))
			{
				eventBus.post(new StatChanged(skill, snapshot.getXp(), snapshot.getLevel(), snapshot.getBoostedLevel()));
			}
			skills.put(skill, snapshot);
		}
		skillsInitialized = true;
	}

	private void seedSkills()
	{
		for (GameClient.SkillSnapshot snapshot : client.getSkillSnapshots())
		{
			Skill skill = snapshot.getSkill();
			if (skill != null && skill != Skill.OVERALL)
			{
				skills.put(skill, snapshot);
			}
		}
		skillsInitialized = true;
	}

	private void expireRecentNpcs()
	{
		long now = System.currentTimeMillis();
		for (Iterator<RecentNpc> it = recentNpcDespawns.iterator(); it.hasNext(); )
		{
			if (now - it.next().getTime() > RECENT_NPC_TTL_MILLIS)
			{
				it.remove();
			}
		}
	}

	private static boolean changed(GameClient.SkillSnapshot previous, GameClient.SkillSnapshot current)
	{
		return previous == null
			|| previous.getXp() != current.getXp()
			|| previous.getLevel() != current.getLevel()
			|| previous.getBoostedLevel() != current.getBoostedLevel();
	}

	private static String groundItemKey(GameClient.GroundItemInfo item)
	{
		return tileKey(item) + ":" + item.getId();
	}

	private static String tileKey(GameClient.GroundItemInfo item)
	{
		return item.getPlane() + ":" + item.getLocalX() + ":" + item.getLocalY();
	}

	private static String npcKey(GameClient.NpcInfo npc)
	{
		if (npc.getIndex() >= 0)
		{
			return Integer.toString(npc.getIndex());
		}
		return npc.getId() + ":" + npc.getPlane() + ":" + npc.getLocalX() + ":" + npc.getLocalY() + ":" + npc.getName();
	}

	@Value
	public static class RecentNpc
	{
		int id;
		String name;
		int localX;
		int localY;
		int plane;
		long time;
	}
}
