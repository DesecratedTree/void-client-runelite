package net.runelite.client.game;

import com.GameClient;
import java.util.ArrayList;
import java.util.List;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Skill;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.*;
import net.runelite.client.RuneLite;
import net.runelite.client.events.NetworkPacketQueued;
import net.runelite.client.events.NetworkPacketReceived;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.util.DeferredEventBus;

public final class GameEventBridgeHooks
{
	private static volatile boolean directGroundItemHooks;
	private static volatile boolean directNpcHooks;
	private static volatile boolean directStatHooks;
	private static final List<MenuEntry> menuEntries = new ArrayList<>();

	private GameEventBridgeHooks()
	{
	}

	public static boolean hasDirectGroundItemHooks()
	{
		return directGroundItemHooks;
	}

	public static boolean hasDirectStatHooks()
	{
		return directStatHooks;
	}

	public static boolean hasDirectNpcHooks()
	{
		return directNpcHooks;
	}

	public static void postStatChanged(int skillIndex, int boostedLevel, int xp, int level)
	{
		Skill[] skills = Skill.values();
		if (skillIndex < 0 || skillIndex >= skills.length || skills[skillIndex] == Skill.OVERALL)
		{
			return;
		}

		directStatHooks = true;
		post(new StatChanged(skills[skillIndex], xp, level, boostedLevel));
	}

	public static void postGroundItemSpawned(int id, int quantity, String name, int price, int localX, int localY, int plane)
	{
		directGroundItemHooks = true;
		GameClient.GroundItemInfo item = new GameClient.GroundItemInfo(id, quantity, name, price, localX, localY, plane);
		post(new ItemSpawned(tileKey(item), item));
	}

	public static void postGroundItemDespawned(int id, int quantity, String name, int price, int localX, int localY, int plane)
	{
		directGroundItemHooks = true;
		GameClient.GroundItemInfo item = new GameClient.GroundItemInfo(id, quantity, name, price, localX, localY, plane);
		post(new ItemDespawned(tileKey(item), item));
	}

	public static void postGroundItemQuantityChanged(int id, int oldQuantity, int newQuantity, String name, int price, int localX, int localY, int plane)
	{
		directGroundItemHooks = true;
		GameClient.GroundItemInfo item = new GameClient.GroundItemInfo(id, newQuantity, name, price, localX, localY, plane);
		post(new ItemQuantityChanged(item, tileKey(item), oldQuantity, newQuantity));
	}

	public static void postNpcSpawned(GameClient.NpcInfo npc)
	{
		directNpcHooks = true;
		post(new NpcSpawned(npc));
	}

	public static void postNpcDespawned(GameClient.NpcInfo npc)
	{
		directNpcHooks = true;
		post(new NpcDespawned(npc));
	}

	public static void postAccountHashChanged(long accountHash)
	{
		post(new AccountHashChanged(accountHash));
	}

	public static void postAnimationChanged(Object actor)
	{
		post(new AnimationChanged(actor));
	}

	public static void postPostAnimation(Object actor)
	{
		post(new PostAnimation(actor));
	}

	public static void postGraphicChanged(Object actor)
	{
		post(new GraphicChanged(actor));
	}

	public static void postActorDeath(Object actor)
	{
		post(new ActorDeath(actor));
	}

	public static void postInteractingChanged(Object source, Object target)
	{
		post(new InteractingChanged(source, target));
	}

	public static void postOverheadTextChanged(Object actor, String overheadText)
	{
		post(new OverheadTextChanged(actor, overheadText));
	}

	public static void postHitsplatApplied(Object actor, Object hitsplat)
	{
		post(new HitsplatApplied(actor, hitsplat));
	}

	public static void postGraphicsObjectCreated(Object graphicsObject)
	{
		post(new GraphicsObjectCreated(graphicsObject));
	}

	public static void postProjectileMoved(Object projectile, int localX, int localY, int z)
	{
		post(new ProjectileMoved(projectile, new LocalPoint(localX, localY), z));
	}

	public static void postProjectileMoved(Object projectile, LocalPoint position, int z)
	{
		post(new ProjectileMoved(projectile, position, z));
	}

	public static void postAmbientSoundEffectCreated(Object ambientSoundEffect)
	{
		post(new AmbientSoundEffectCreated(ambientSoundEffect));
	}

	public static void postVarbitChanged(int varpId, int varbitId, int value)
	{
		post(new VarbitChanged(varpId, varbitId, value));
	}

	public static void postVarClientIntChanged(int index)
	{
		post(new VarClientIntChanged(index));
	}

	public static void postVarClientStrChanged(int index)
	{
		post(new VarClientStrChanged(index));
	}

	public static void postWidgetLoaded(int groupId)
	{
		post(new WidgetLoaded(groupId));
	}

	public static void postWidgetClosed(int groupId, int modalMode, boolean unload)
	{
		post(new WidgetClosed(groupId, modalMode, unload));
	}

	public static void postWidgetDrag(Object source, Object target)
	{
		post(new WidgetDrag(source, target));
	}

	public static void postWorldChanged(int world)
	{
		post(new WorldChanged(world));
	}

	public static void postGameStateChanged(GameState gameState)
	{
		GameStateChanged event = new GameStateChanged();
		event.setGameState(gameState);
		post(event);
	}

	public static void postDeobGameStateChanged(int state)
	{
		postGameStateChanged(mapDeobGameState(state));
	}

	public static void postFocusChanged(boolean focused)
	{
		FocusChanged event = new FocusChanged();
		event.setFocused(focused);
		post(event);
	}

	public static void postResizeableChanged(boolean resizable)
	{
		post(new ResizeableChanged(resizable));
	}

	public static void postVolumeChanged(Object type, int volume)
	{
		post(new VolumeChanged(type, volume));
	}

	public static void postSoundEffectPlayed(Object source, int soundId, int delay)
	{
		post(new SoundEffectPlayed(source, soundId, delay, false));
	}

	public static void postAreaSoundEffectPlayed(int soundId, int sceneX, int sceneY, int range, int delay)
	{
		post(new AreaSoundEffectPlayed(soundId, sceneX, sceneY, range, delay, false));
	}

	public static void postItemContainerChanged(int containerId, Object itemContainer)
	{
		post(new ItemContainerChanged(containerId, itemContainer));
	}

	public static void postChatMessage(Object type, String name, String message, String sender, int timestamp)
	{
		post(new ChatMessage(null, type, name, message, sender, timestamp));
	}

	public static void postChatMessage(Object messageNode, Object type, String name, String message, String sender, int timestamp)
	{
		post(new ChatMessage(messageNode, type, name, message, sender, timestamp));
	}

	public static void postCommandExecuted(String command, String[] arguments)
	{
		post(new CommandExecuted(command, arguments));
	}

	public static void postBeforeMenuRender()
	{
		post(new BeforeMenuRender());
	}

	public static void postPostMenuSort()
	{
		post(new PostMenuSort());
	}

	public static void postMenuOpened(MenuEntry[] menuEntries)
	{
		post(new MenuOpened(menuEntries));
	}

	public static boolean postMenuOptionClicked(int param0, int param1, String menuOption, String menuTarget, MenuAction menuAction, int id, int selectedItemIndex)
	{
		MenuOptionClicked event = new MenuOptionClicked();
		event.setParam0(param0);
		event.setParam1(param1);
		event.setMenuOption(menuOption);
		event.setMenuTarget(menuTarget);
		event.setMenuAction(menuAction);
		event.setId(id);
		event.setSelectedItemIndex(selectedItemIndex);
		post(event);
		return event.isConsumed();
	}

	public static void postMenuShouldLeftClick(boolean forceRightClick)
	{
		MenuShouldLeftClick event = new MenuShouldLeftClick();
		event.setForceRightClick(forceRightClick);
		post(event);
	}

	public static void postMenuEntryAdded(String option, String target, int type, int identifier, int actionParam0, int actionParam1)
	{
		MenuEntry entry = new MenuEntry();
		entry.setOption(option == null ? "" : option);
		entry.setTarget(target == null ? "" : target);
		entry.setType(type);
		entry.setIdentifier(identifier);
		entry.setParam0(actionParam0);
		entry.setParam1(actionParam1);
		synchronized (menuEntries)
		{
			menuEntries.add(entry);
		}
		post(new MenuEntryAdded(option, target, type, identifier, actionParam0, actionParam1));
	}

	public static void clearMenuEntries()
	{
		synchronized (menuEntries)
		{
			menuEntries.clear();
		}
	}

	public static MenuEntry[] getMenuEntries()
	{
		synchronized (menuEntries)
		{
			return menuEntries.toArray(new MenuEntry[0]);
		}
	}

	public static void postClanChannelChanged(int clanChannelIndex)
	{
		post(new ClanChannelChanged(clanChannelIndex));
	}

	public static void postClanMemberJoined(Object clanMember)
	{
		post(new ClanMemberJoined(clanMember));
	}

	public static void postClanMemberLeft(Object clanMember)
	{
		post(new ClanMemberLeft(clanMember));
	}

	public static void postFriendsChatChanged()
	{
		post(new FriendsChatChanged());
	}

	public static void postFriendsChatMemberJoined(Object member)
	{
		post(new FriendsChatMemberJoined(member));
	}

	public static void postFriendsChatMemberLeft(Object member)
	{
		post(new FriendsChatMemberLeft(member));
	}

	public static void postNameableNameChanged(Object nameable, String oldName, String newName)
	{
		post(new NameableNameChanged(nameable, oldName, newName));
	}

	public static void postRemovedFriend(String name)
	{
		post(new RemovedFriend(name));
	}

	public static void postGrandExchangeOfferChanged(Object offer, int slot)
	{
		post(new GrandExchangeOfferChanged(offer, slot));
	}

	public static void postGrandExchangeSearched(String searchTerm)
	{
		post(new GrandExchangeSearched(searchTerm, false));
	}

	public static void postNpcChanged(Object npc, Object oldComposition)
	{
		post(new NpcChanged(npc, oldComposition));
	}

	public static void postPlayerSpawned(Object player)
	{
		post(new PlayerSpawned(player));
	}

	public static void postPlayerDespawned(Object player)
	{
		post(new PlayerDespawned(player));
	}

	public static void postPlayerChanged(Object player)
	{
		post(new PlayerChanged(player));
	}

	public static void postPlayerMenuOptionsChanged(int index)
	{
		post(new PlayerMenuOptionsChanged(index));
	}

	public static void postFakeXpDrop(Skill skill)
	{
		post(new FakeXpDrop(skill));
	}

	public static void postPreMapLoad()
	{
		post(new PreMapLoad());
	}

	public static void postScriptCallbackEvent(Object script, String eventName)
	{
		post(new ScriptCallbackEvent(script, eventName));
	}

	public static void postScriptPreFired(int scriptId)
	{
		post(new ScriptPreFired(scriptId));
	}

	public static void postScriptPostFired(int scriptId)
	{
		post(new ScriptPostFired(scriptId));
	}

	public static void postPostHealthBarConfig(Object healthBarConfig)
	{
		post(new PostHealthBarConfig(healthBarConfig));
	}

	public static void postPostItemComposition(Object itemComposition)
	{
		post(new PostItemComposition(itemComposition));
	}

	public static void postPostObjectComposition(Object objectComposition)
	{
		post(new PostObjectComposition(objectComposition));
	}

	public static void postPostStructComposition(Object structComposition)
	{
		post(new PostStructComposition(structComposition));
	}

	public static void postWorldEntitySpawned(Object worldEntity)
	{
		post(new WorldEntitySpawned(worldEntity));
	}

	public static void postWorldEntityDespawned(Object worldEntity)
	{
		post(new WorldEntityDespawned(worldEntity));
	}

	public static void postWorldListLoad(Object worlds)
	{
		post(new WorldListLoad(worlds));
	}

	public static void postWorldViewLoaded(Object worldView)
	{
		post(new WorldViewLoaded(worldView));
	}

	public static void postWorldViewUnloaded(Object worldView)
	{
		post(new WorldViewUnloaded(worldView));
	}

	public static void postOutgoingPacket(int opcode, int expectedLength, int payloadLength, String source)
	{
		post(new NetworkPacketQueued(opcode, expectedLength, payloadLength, source));
	}

	public static void postIncomingPacket(int opcode, int declaredLength, int payloadLength)
	{
		post(new NetworkPacketReceived(opcode, declaredLength, payloadLength));
	}

	public static void postGameObjectSpawned(Object tile, Object gameObject)
	{
		GameObjectSpawned event = new GameObjectSpawned();
		event.setTile(tile);
		event.setGameObject(gameObject);
		post(event);
	}

	public static void postGameObjectChanged(Object tile, Object previous, Object gameObject)
	{
		GameObjectChanged event = new GameObjectChanged();
		event.setTile(tile);
		event.setPrevious(previous);
		event.setGameObject(gameObject);
		post(event);
	}

	public static void postGameObjectDespawned(Object tile, Object gameObject)
	{
		GameObjectDespawned event = new GameObjectDespawned();
		event.setTile(tile);
		event.setGameObject(gameObject);
		post(event);
	}

	public static void postWallObjectSpawned(Object tile, Object wallObject)
	{
		WallObjectSpawned event = new WallObjectSpawned();
		event.setTile(tile);
		event.setWallObject(wallObject);
		post(event);
	}

	public static void postWallObjectChanged(Object tile, Object previous, Object wallObject)
	{
		WallObjectChanged event = new WallObjectChanged();
		event.setTile(tile);
		event.setPrevious(previous);
		event.setWallObject(wallObject);
		post(event);
	}

	public static void postWallObjectDespawned(Object tile, Object wallObject)
	{
		WallObjectDespawned event = new WallObjectDespawned();
		event.setTile(tile);
		event.setWallObject(wallObject);
		post(event);
	}

	public static void postGroundObjectSpawned(Object tile, Object groundObject)
	{
		GroundObjectSpawned event = new GroundObjectSpawned();
		event.setTile(tile);
		event.setGroundObject(groundObject);
		post(event);
	}

	public static void postGroundObjectChanged(Object tile, Object previous, Object groundObject)
	{
		GroundObjectChanged event = new GroundObjectChanged();
		event.setTile(tile);
		event.setPrevious(previous);
		event.setGroundObject(groundObject);
		post(event);
	}

	public static void postGroundObjectDespawned(Object tile, Object groundObject)
	{
		GroundObjectDespawned event = new GroundObjectDespawned();
		event.setTile(tile);
		event.setGroundObject(groundObject);
		post(event);
	}

	public static void postDecorativeObjectSpawned(Object tile, Object decorativeObject)
	{
		DecorativeObjectSpawned event = new DecorativeObjectSpawned();
		event.setTile(tile);
		event.setDecorativeObject(decorativeObject);
		post(event);
	}

	public static void postDecorativeObjectChanged(Object tile, Object previous, Object decorativeObject)
	{
		DecorativeObjectChanged event = new DecorativeObjectChanged();
		event.setTile(tile);
		event.setPrevious(previous);
		event.setDecorativeObject(decorativeObject);
		post(event);
	}

	public static void postDecorativeObjectDespawned(Object tile, Object decorativeObject)
	{
		DecorativeObjectDespawned event = new DecorativeObjectDespawned();
		event.setTile(tile);
		event.setDecorativeObject(decorativeObject);
		post(event);
	}

	public static void postTileObjectSpawned(int id, int localX, int localY, int plane, int type, int orientation, int layer)
	{
		TileObjectInfo object = new TileObjectInfo(id, localX, localY, plane, type, orientation, layer);
		Object tile = tileKey(plane, localX, localY);
		switch (layer)
		{
			case 0:
				postWallObjectSpawned(tile, object);
				break;
			case 1:
				postDecorativeObjectSpawned(tile, object);
				break;
			case 3:
				postGroundObjectSpawned(tile, object);
				break;
			default:
				postGameObjectSpawned(tile, object);
				break;
		}
	}

	public static void postTileObjectDespawned(int id, int localX, int localY, int plane, int type, int orientation, int layer)
	{
		TileObjectInfo object = new TileObjectInfo(id, localX, localY, plane, type, orientation, layer);
		Object tile = tileKey(plane, localX, localY);
		switch (layer)
		{
			case 0:
				postWallObjectDespawned(tile, object);
				break;
			case 1:
				postDecorativeObjectDespawned(tile, object);
				break;
			case 3:
				postGroundObjectDespawned(tile, object);
				break;
			default:
				postGameObjectDespawned(tile, object);
				break;
		}
	}

	public static void postEvent(Object event)
	{
		post(event);
	}

	public static void postDeferredEvent(Object event)
	{
		postDeferred(event);
	}

	private static String tileKey(GameClient.GroundItemInfo item)
	{
		return item.getPlane() + ":" + item.getLocalX() + ":" + item.getLocalY();
	}

	private static String tileKey(int plane, int localX, int localY)
	{
		return plane + ":" + localX + ":" + localY;
	}

	private static GameState mapDeobGameState(int state)
	{
		switch (state)
		{
			case 1:
			case 2:
				return GameState.STARTING;
			case 3:
			case 7:
				return GameState.LOGIN_SCREEN;
			case 4:
			case 5:
			case 6:
			case 8:
			case 9:
			case 11:
			case 12:
				return GameState.LOGGING_IN;
			case 10:
				return GameState.LOGGED_IN;
			case 13:
				return GameState.CONNECTION_LOST;
			default:
				return GameState.UNKNOWN;
		}
	}

	public static final class TileObjectInfo
	{
		private final int id;
		private final int localX;
		private final int localY;
		private final int plane;
		private final int type;
		private final int orientation;
		private final int layer;

		private TileObjectInfo(int id, int localX, int localY, int plane, int type, int orientation, int layer)
		{
			this.id = id;
			this.localX = localX;
			this.localY = localY;
			this.plane = plane;
			this.type = type;
			this.orientation = orientation;
			this.layer = layer;
		}

		public int getId()
		{
			return id;
		}

		public int getLocalX()
		{
			return localX;
		}

		public int getLocalY()
		{
			return localY;
		}

		public int getPlane()
		{
			return plane;
		}

		public int getType()
		{
			return type;
		}

		public int getOrientation()
		{
			return orientation;
		}

		public int getLayer()
		{
			return layer;
		}

		@Override
		public String toString()
		{
			return "TileObjectInfo{" +
				"id=" + id +
				", localX=" + localX +
				", localY=" + localY +
				", plane=" + plane +
				", type=" + type +
				", orientation=" + orientation +
				", layer=" + layer +
				'}';
		}
	}

	private static void post(Object event)
	{
		try
		{
			if (RuneLite.getInjector() != null)
			{
				RuneLite.getInjector().getInstance(EventBus.class).post(event);
			}
		}
		catch (Throwable ignored)
		{
			// Packet handlers must not fail because the RuneLite shell is still starting.
		}
	}

	private static void postDeferred(Object event)
	{
		try
		{
			if (RuneLite.getInjector() != null)
			{
				RuneLite.getInjector().getInstance(DeferredEventBus.class).post(event);
			}
		}
		catch (Throwable ignored)
		{
			// Packet handlers must not fail because the RuneLite shell is still starting.
		}
	}
}
