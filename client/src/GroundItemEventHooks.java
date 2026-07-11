final class GroundItemEventHooks {
    private GroundItemEventHooks() {
    }

    static void spawned(int id, int quantity, int sceneX, int sceneY, int plane) {
        ObjType definition = definition(id);
        net.runelite.client.game.GameEventBridgeHooks.postGroundItemSpawned(
            id,
            quantity,
            name(id, definition),
            price(definition),
            sceneX << 7,
            sceneY << 7,
            plane
        );
    }

    static void despawned(int id, int quantity, int sceneX, int sceneY, int plane) {
        ObjType definition = definition(id);
        net.runelite.client.game.GameEventBridgeHooks.postGroundItemDespawned(
            id,
            quantity,
            name(id, definition),
            price(definition),
            sceneX << 7,
            sceneY << 7,
            plane
        );
    }

    static void quantityChanged(int id, int oldQuantity, int newQuantity, int sceneX, int sceneY, int plane) {
        ObjType definition = definition(id);
        net.runelite.client.game.GameEventBridgeHooks.postGroundItemQuantityChanged(
            id,
            oldQuantity,
            newQuantity,
            name(id, definition),
            price(definition),
            sceneX << 7,
            sceneY << 7,
            plane
        );
    }

    private static ObjType definition(int id) {
        return Exception_Sub1.aClass255_112 == null ? null : Exception_Sub1.aClass255_112.getItemDefinitions(-115, id);
    }

    private static String name(int id, ObjType definition) {
        return definition == null || definition.name == null ? "Item " + id : definition.name;
    }

    private static int price(ObjType definition) {
        return definition == null ? 0 : Math.max(0, definition.cost);
    }
}
