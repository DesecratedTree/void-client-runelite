/* Class243 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

final class Class243 {
    static int anInt3157;
    static int anInt3158;
    static int anInt3159;
    static int anInt3160;
    static int anInt3161;
    static int anInt3162;
    static int anInt3163;
    static int anInt3164;
    static Class351 aClass351_3165 = new Class351(60, 8);
    private final Linkable aClass318_3166 = new Linkable();
    private Linkable aClass318_3167;
    static int anInt3168;

    final void method1869(int i, Linkable Linkable) {
        if (Linkable.previousLink != null) Linkable.method2373(false);
        anInt3158++;
        Linkable.previousLink = aClass318_3166.previousLink;
        if (i > -81) aClass318_3167 = null;
        Linkable.nextLink = aClass318_3166;
        Linkable.previousLink.nextLink = Linkable;
        Linkable.nextLink.previousLink = Linkable;
    }

    final Linkable method1870(int i) {
        if (i > -103) aClass318_3167 = null;
        anInt3162++;
        Linkable Linkable = aClass318_3166.previousLink;
        if (aClass318_3166 == Linkable) {
            aClass318_3167 = null;
            return null;
        }
        aClass318_3167 = Linkable.previousLink;
        return Linkable;
    }

    final boolean method1871(byte i) {
        anInt3157++;
        if (i <= 98) method1879(true);
        return aClass318_3166 == aClass318_3166.nextLink;
    }

    final Linkable method1872(int i) {
        anInt3163++;
        Linkable Linkable = aClass318_3166.nextLink;
        if (i != 8) method1878((byte) 126);
        if (Linkable == aClass318_3166) {
            aClass318_3167 = null;
            return null;
        }
        aClass318_3167 = Linkable.nextLink;
        return Linkable;
    }

    public static void method1873(byte i) {
        if (i > -111) aClass351_3165 = null;
        aClass351_3165 = null;
    }

    final int method1874(int i) {
        anInt3161++;
        int i_0_ = i;
        for (Linkable Linkable = aClass318_3166.nextLink; aClass318_3166 != Linkable; Linkable = Linkable.nextLink)
            i_0_++;
        return i_0_;
    }

    final Linkable method1875(int i) {
        anInt3160++;
        Linkable Linkable = aClass318_3166.nextLink;
        if (Linkable == aClass318_3166) return null;
        Linkable.method2373(false);
        if (i != 60) method1878((byte) 16);
        return Linkable;
    }

    final void method1876(byte i) {
        if (i == -45) {
            anInt3168++;
            for (; ; ) {
                Linkable Linkable = aClass318_3166.nextLink;
                if (Linkable == aClass318_3166) break;
                Linkable.method2373(false);
            }
            aClass318_3167 = null;
        }
    }

    static final void method1877(ha var_ha, int i) {
        anInt3164++;
        if (i >= -20) method1877(null, -112);
        for (Class318_Sub10 class318_sub10 = (Class318_Sub10) Class152.aClass243_2077.method1872(8); class318_sub10 != null; class318_sub10 = (Class318_Sub10) Class152.aClass243_2077.method1878((byte) 124)) {
            if (class318_sub10.aBoolean6482) class318_sub10.method2528(var_ha);
        }
    }

    final Linkable method1878(byte i) {
        anInt3159++;
        Linkable Linkable = aClass318_3167;
        int i_1_ = -59 % ((67 - i) / 55);
        if (Linkable == aClass318_3166) {
            aClass318_3167 = null;
            return null;
        }
        aClass318_3167 = Linkable.nextLink;
        return Linkable;
    }

    static final void method1879(boolean bool) {
        if (bool) {
            ChatMessage.aClass357ArrayArrayArray2029 = Class348_Sub31_Sub2.aClass357ArrayArrayArray9082;
            aa_Sub1.aSArray5191 = Class332.aSArray4142;
        } else {
            ChatMessage.aClass357ArrayArrayArray2029 = Class65.aClass357ArrayArrayArray1148;
            aa_Sub1.aSArray5191 = Class348_Sub1_Sub1.aSArray8801;
        }
        Class189.anInt2524 = ChatMessage.aClass357ArrayArrayArray2029.length;
    }

    public Class243() {
        aClass318_3166.previousLink = aClass318_3166;
        aClass318_3166.nextLink = aClass318_3166;
    }
}
