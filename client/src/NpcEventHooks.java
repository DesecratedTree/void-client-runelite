final class NpcEventHooks {
    private NpcEventHooks() {
    }

    static void spawned(Npc npc) {
        com.GameClient.NpcInfo info = info(npc);
        if (info != null) {
            net.runelite.client.game.GameEventBridgeHooks.postNpcSpawned(info);
        }
    }

    static void despawned(Npc npc) {
        com.GameClient.NpcInfo info = info(npc);
        if (info != null) {
            net.runelite.client.game.GameEventBridgeHooks.postNpcDespawned(info);
        }
    }

    private static com.GameClient.NpcInfo info(Npc npc) {
        if (npc == null || npc.definition == null) {
            return null;
        }

        NPCType definition = npc.definition.multinpcs == null ? npc.definition : npc.definition.method794(Class318_Sub1_Sub3_Sub3.aClass170_10209, -1);
        if (definition == null || definition.name == null || definition.name.length() == 0 || "null".equalsIgnoreCase(definition.name)) {
            return null;
        }

        int size = Math.max(1, definition.anInt1399);
        int height = Math.max(128, npc.method2426(200));
        int trueLocalX = (npc.anIntArray10320 != null && npc.anIntArray10320.length > 0)
            ? ((npc.anIntArray10320[0] << 9) + (size << 8)) >> 2
            : npc.x >> 2;
        int trueLocalY = (npc.anIntArray10317 != null && npc.anIntArray10317.length > 0)
            ? ((npc.anIntArray10317[0] << 9) + (size << 8)) >> 2
            : npc.y >> 2;
        int localX = npc.x <= 0 ? trueLocalX : npc.x >> 2;
        int localY = npc.y <= 0 ? trueLocalY : npc.y >> 2;

        return new com.GameClient.NpcInfo(
            npc.anInt10290,
            definition.id,
            definition.name,
            localX,
            localY,
            trueLocalX,
            trueLocalY,
            npc.plane,
            height,
            size,
            definition.combatLevel,
            definition.isFollower,
            false
        );
    }
}
