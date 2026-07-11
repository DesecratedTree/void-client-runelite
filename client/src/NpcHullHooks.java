import com.GameClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class NpcHullHooks {
    private static final Map<Integer, GameClient.NpcHullInfo> HULLS = new ConcurrentHashMap<>();

    private NpcHullHooks() {
    }

    static void updated(Npc npc, Class318_Sub4 renderable) {
        if (npc == null || renderable == null || renderable.aClass318_Sub3Array6414 == null) {
            return;
        }

        int pickShift = Math.max(0, npc.method2393(-117));
        java.util.ArrayList<GameClient.HullSegment> segments = new java.util.ArrayList<>();
        for (Class318_Sub3 segment : renderable.aClass318_Sub3Array6414) {
            if (segment == null || !segment.aBoolean6401) {
                continue;
            }

            int radius = Math.max(2, segment.anInt6403 << pickShift);
            segments.add(new GameClient.HullSegment(
                segment.anInt6405,
                segment.anInt6402,
                segment.anInt6406,
                segment.anInt6404,
                radius
            ));
        }

        if (segments.isEmpty()) {
            HULLS.remove(npc.anInt10290);
            return;
        }

        HULLS.put(npc.anInt10290, new GameClient.NpcHullInfo(
            Class367_Sub11.anInt7396,
            segments.toArray(new GameClient.HullSegment[0])
        ));
    }

    static GameClient.NpcHullInfo get(int npcIndex) {
        GameClient.NpcHullInfo hull = HULLS.get(npcIndex);
        if (hull == null || Class367_Sub11.anInt7396 - hull.getCycle() > 2) {
            return null;
        }
        return hull;
    }
}
