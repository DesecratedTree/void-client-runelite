/* Class328_Sub2 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Class328_Sub2 extends Class328 {
    static Class56[] aClass56Array6515;
    static int anInt6516;
    static ReferenceCache aClass60_6517 = new ReferenceCache(4);
    static int anInt6518;

    static final Class120 method2612(Packet Packet, boolean bool) {
        anInt6518++;
        int i = Packet.readUnsignedShort(842397944);
        if (bool != true) method2613(-27);
        return new Class120(i);
    }

    public static void method2613(int i) {
        aClass56Array6515 = null;
        aClass60_6517 = null;
        if (i != -1) method2613(58);
    }
}
