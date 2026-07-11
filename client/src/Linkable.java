/* Linkable - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Linkable {
    Linkable nextLink;
    static int anInt3971;
    static int anInt3972;
    static int anInt3973;
    static Class243[] aClass243Array3974 = new Class243[5];
    static int anInt3975;
    Linkable previousLink;
    static Class304 aClass304_3977;

    final void method2373(boolean bool) {
        anInt3975++;
        if (this.previousLink != null) {
            this.previousLink.nextLink = this.nextLink;
            this.nextLink.previousLink = this.previousLink;
            this.nextLink = null;
            if (bool == false) this.previousLink = null;
        }
    }

    public static void method2374(byte i) {
        aClass304_3977 = null;
        int i_0_ = 108 / ((i - -83) / 41);
        aClass243Array3974 = null;
    }

    static final void method2375(int i) {
        anInt3972++;
        Class202.aClass60_2671.method590(0);
        if (i != 16127) anInt3971 = -113;
    }

    public Linkable() {
        /* empty */
    }

    static {
        for (int i = 0; aClass243Array3974.length > i; i++)
            aClass243Array3974[i] = new Class243();
        aClass304_3977 = new Class304(1);
    }
}
