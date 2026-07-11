/* ObjType - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

final class ObjType {
    static int anInt2751;
    int cursor1op = -1;
    private int anInt2753;
    static int anInt2754;
    boolean stockmarket;
    private int mesh;
    IterableHashTable params;
    int certlink;
    int cursor1;
    private int womanwear2;
    ObjTypeList loader;
    int[] countobj;
    String[] iop;
    int cursor2op;
    private int resizez;
    int anInt2766;
    private int manhead2;
    static int anInt2768;
    int id;
    private int womanwear3 = -1;
    private short[] recol_d;
    int[] quests;
    static Class238 aClass238_2773;
    int anInt2774;
    private int womanhead2;
    static int anInt2776;
    private short[] recol_s;
    int lentlink;
    int xof2d;
    static int anInt2780;
    int yan2d;
    static int anInt2782;
    boolean members;
    int anInt2784;
    private short[] retex_s;
    private int resizex;
    int xan2d;
    int womanwear;
    static long aLong2789;
    static int anInt2790;
    private int ambient;
    private int manhead;
    static int anInt2793;
    private int resizey;
    String name;
    static int anInt2796;
    private int anInt2797;
    static int anInt2798 = 0;
    int anInt2799;
    static int anInt2800;
    private short[] retex_d;
    int anInt2802;
    private int anInt2803;
    private int manwear2;
    private int manwear3;
    static int anInt2806;
    private int anInt2807;
    private int anInt2808;
    static int anInt2809;
    int zan2d;
    String[] op;
    int lenttemplate;
    static int anInt2813;
    static int anInt2814;
    int manwear;
    static int anInt2816;
    int anInt2817;
    int anInt2818;
    int cost;
    int stackable;
    private byte[] recol_d_palette;
    private int womanhead;
    private int anInt2823;
    private int contrast;
    int zoom2d;
    int yof2d;
    int team;
    static int anInt2828;
    static int anInt2829;
    int cursor2;
    int[] countco;
    static int anInt2832;
    int certtemplate;

    final Class124 getHeadModelData(boolean bool, int i) {
        anInt2796++;
        int i_0_ = manhead;
        int i_1_ = manhead2;
        if (bool) {
            i_1_ = womanhead2;
            i_0_ = womanhead;
        }
        if (i_0_ == -1) return null;
        Class124 class124 = Class300.method2277(0, this.loader.aClass45_3268, i_0_, -1);
        if ((~class124.anInt1830) > i) class124.method1092(2, 54);
        if (i_1_ != -1) {
            Class124 class124_2_ = Class300.method2277(0, (this.loader.aClass45_3268), i_1_, -1);
            if (class124_2_.anInt1830 < 13) class124_2_.method1092(2, i ^ ~0x78);
            Class124[] class124s = {class124, class124_2_};
            class124 = new Class124(class124s, 2);
        }
        if (recol_s != null) {
            for (int i_3_ = 0; i_3_ < recol_s.length; i_3_++)
                class124.method1098(recol_s[i_3_], (byte) 126, recol_d[i_3_]);
        }
        if (retex_s != null) {
            for (int i_4_ = 0; i_4_ < retex_s.length; i_4_++)
                class124.method1095(retex_s[i_4_], 0, retex_d[i_4_]);
        }
        return class124;
    }

    final boolean hasHeadModels(byte i, boolean bool) {
        anInt2828++;
        int i_5_ = manhead;
        int i_6_ = -15 / ((28 - i) / 55);
        int i_7_ = manhead2;
        if (bool) {
            i_5_ = womanhead;
            i_7_ = womanhead2;
        }
        if (i_5_ == -1) return true;
        boolean bool_8_ = true;
        if (!this.loader.aClass45_3268.method420(-10499, i_5_, 0)) bool_8_ = false;
        if (i_7_ != -1 && !this.loader.aClass45_3268.method420(-10499, i_7_, 0)) bool_8_ = false;
        return bool_8_;
    }

    final void generateLend(ObjType baseItem, byte i, ObjType templateItem) {
        try {
            anInt2808 = baseItem.anInt2808;
            this.xof2d = templateItem.xof2d;
            this.cost = 0;
            retex_s = baseItem.retex_s;
            this.womanwear = baseItem.womanwear;
            this.yan2d = templateItem.yan2d;
            this.op = baseItem.op;
            anInt2797 = baseItem.anInt2797;
            recol_d_palette = baseItem.recol_d_palette;
            recol_d = baseItem.recol_d;
            this.name = baseItem.name;
            mesh = templateItem.mesh;
            womanwear3 = baseItem.womanwear3;
            recol_s = baseItem.recol_s;
            this.manwear = baseItem.manwear;
            womanhead = baseItem.womanhead;
            manwear2 = baseItem.manwear2;
            this.zoom2d = templateItem.zoom2d;
            manhead2 = baseItem.manhead2;
            retex_d = baseItem.retex_d;
            this.members = baseItem.members;
            this.zan2d = templateItem.zan2d;
            this.iop = new String[5];
            if (i > -5) getHeadModelData(false, -92);
            anInt2823 = baseItem.anInt2823;
            womanhead2 = baseItem.womanhead2;
            anInt2832++;
            anInt2807 = baseItem.anInt2807;
            manhead = baseItem.manhead;
            this.params = baseItem.params;
            manwear3 = baseItem.manwear3;
            this.team = baseItem.team;
            this.xan2d = templateItem.xan2d;
            womanwear2 = baseItem.womanwear2;
            anInt2753 = baseItem.anInt2753;
            anInt2803 = baseItem.anInt2803;
            this.yof2d = templateItem.yof2d;
            if (baseItem.iop != null) {
                for (int i_11_ = 0; i_11_ < 4; i_11_++)
                    this.iop[i_11_] = baseItem.iop[i_11_];
            }
            this.iop[4] = Class274.aClass274_3489.method2063((this.loader.anInt3286), 544);
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.G(" + (baseItem != null ? "{...}" : "null") + ',' + i + ',' + (templateItem != null ? "{...}" : "null") + ')'));
        }
    }

    private final String formatItemStackAmount(int i, int i_12_) {
        anInt2816++;
        if (i_12_ != -11619) this.womanwear = -113;
        if (i < 100000) return "<col=ffff00>" + i + "</col>";
        if (i < 10000000) return ("<col=ffffff>" + i / 1000 + Class274.aClass274_3519.method2063((this.loader.anInt3286), 544) + "</col>");
        return ("<col=00ff80>" + i / 1000000 + Class274.aClass274_3517.method2063((this.loader.anInt3286), 544) + "</col>");
    }

    final Class124 getEquipmentModelData(boolean bool, boolean bool_13_) {
        anInt2809++;
        int i = this.manwear;
        int i_14_ = manwear2;
        if (bool_13_ != false) this.params = null;
        int i_15_ = manwear3;
        if (bool) {
            i = this.womanwear;
            i_15_ = womanwear3;
            i_14_ = womanwear2;
        }
        if (i == -1) return null;
        Class124 class124 = Class300.method2277(0, this.loader.aClass45_3268, i, -1);
        if (class124.anInt1830 < 13) class124.method1092(2, 64);
        if (i_14_ != -1) {
            Class124 class124_16_ = Class300.method2277(0, (this.loader.aClass45_3268), i_14_, -1);
            if (class124_16_.anInt1830 < 13) class124_16_.method1092(2, 89);
            if (i_15_ == -1) {
                Class124[] class124s = {class124, class124_16_};
                class124 = new Class124(class124s, 2);
            } else {
                Class124 class124_17_ = Class300.method2277(0, (this.loader.aClass45_3268), i_15_, -1);
                if (class124_17_.anInt1830 < 13) class124_17_.method1092(2, 109);
                Class124[] class124s = {class124, class124_16_, class124_17_};
                class124 = new Class124(class124s, 3);
            }
        }
        if (class124 == null) return null;
        if (!bool && (anInt2807 != 0 || anInt2797 != 0 || anInt2808 != 0)) class124.method1099((byte) 110, anInt2808, anInt2807, anInt2797);
        if (bool && (anInt2803 != 0 || anInt2753 != 0 || anInt2823 != 0)) class124.method1099((byte) 24, anInt2823, anInt2803, anInt2753);
        if (recol_s != null) {
            for (int i_18_ = 0; recol_s.length > i_18_; i_18_++)
                class124.method1098(recol_s[i_18_], (byte) 126, recol_d[i_18_]);
        }
        if (retex_s != null) {
            for (int i_19_ = 0; retex_s.length > i_19_; i_19_++)
                class124.method1095(retex_s[i_19_], 0, retex_d[i_19_]);
        }
        return class124;
    }

    final Class64 getModel(Class154 class154, Class17 class17, ha var_ha, int i, int i_20_, int i_21_, int i_22_, byte i_23_, int i_24_) {
        try {
            anInt2813++;
            if (this.countobj != null && i_21_ > 1) {
                int i_25_ = -1;
                for (int i_26_ = 0; i_26_ < 10; i_26_++) {
                    if (i_21_ >= this.countco[i_26_] && this.countco[i_26_] != 0) i_25_ = this.countobj[i_26_];
                }
                if (i_25_ != -1) return (this.loader.getItemDefinitions(103, i_25_).getModel(class154, class17, var_ha, i, i_20_, 1, i_22_, (byte) 88, i_24_));
            }
            if (i_23_ != 88) this.name = null;
            int i_27_ = i;
            if (class17 != null) i_27_ |= class17.method263(i_24_, 105, i_22_, true);
            Class64 class64;
            synchronized (this.loader.aClass60_3287) {
                class64 = (Class64) (this.loader.aClass60_3287.method583(var_ha.anInt4567 << 29 | this.id, 69));
            }
            if (class64 == null || var_ha.method3667(class64.ua(), i_27_) != 0) {
                if (class64 != null) i_27_ = var_ha.method3679(i_27_, class64.ua());
                int i_28_ = i_27_;
                if (retex_s != null) i_28_ |= 0x8000;
                if (recol_s != null || class154 != null) i_28_ |= 0x4000;
                if (resizex != 128) i_28_ |= 0x1;
                if (resizex != 128) i_28_ |= 0x2;
                if (resizex != 128) i_28_ |= 0x4;
                Class124 class124 = Class300.method2277(0, (this.loader.aClass45_3268), mesh, -1);
                if (class124 == null) return null;
                if (class124.anInt1830 < 13) class124.method1092(2, 97);
                class64 = var_ha.method3625(class124, i_28_, (this.loader.anInt3291), ambient + 64, 850 - -contrast);
                if (resizex != 128 || resizey != 128 || resizez != 128) class64.O(resizex, resizey, resizez);
                if (recol_s != null) {
                    for (int i_29_ = 0; (i_29_ < recol_s.length); i_29_++) {
                        if (recol_d_palette == null || recol_d_palette.length <= i_29_) class64.ia(recol_s[i_29_], recol_d[i_29_]);
                        else class64.ia(recol_s[i_29_], (Class336.aShortArray4172[recol_d_palette[i_29_] & 0xff]));
                    }
                }
                if (retex_s != null) {
                    for (int i_30_ = 0; retex_s.length > i_30_; i_30_++)
                        class64.aa(retex_s[i_30_], retex_d[i_30_]);
                }
                if (class154 != null) {
                    for (int i_31_ = 0; i_31_ < 5; i_31_++) {
                        for (int i_32_ = 0; (Class367_Sub2.aShortArrayArrayArray7290.length > i_32_); i_32_++) {
                            if (class154.anIntArray2095[i_31_] < (Class367_Sub2.aShortArrayArrayArray7290[i_32_][i_31_]).length) class64.ia((Class136.aShortArrayArray4791[i_32_][i_31_]), (Class367_Sub2.aShortArrayArrayArray7290[i_32_][i_31_][(class154.anIntArray2095[i_31_])]));
                        }
                    }
                }
                class64.s(i_27_);
                synchronized (this.loader.aClass60_3287) {
                    this.loader.aClass60_3287.method582(class64, var_ha.anInt4567 << 29 | this.id, (byte) -111);
                }
            }
            if (class17 != null) class64 = class17.method269(116, class64, i_24_, i_20_, i_27_, i_22_);
            class64.s(i);
            return class64;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.S(" + (class154 != null ? "{...}" : "null") + ',' + (class17 != null ? "{...}" : "null") + ',' + (var_ha != null ? "{...}" : "null") + ',' + i + ',' + i_20_ + ',' + i_21_ + ',' + i_22_ + ',' + i_23_ + ',' + i_24_ + ')'));
        }
    }

    final ObjType getStackVariant(int i, byte i_33_) {
        if (i_33_ != 97) this.countobj = null;
        anInt2768++;
        if (this.countobj != null && i > 1) {
            int i_34_ = -1;
            for (int i_35_ = 0; i_35_ < 10; i_35_++) {
                if (i >= this.countco[i_35_] && this.countco[i_35_] != 0) i_34_ = this.countobj[i_35_];
            }
            if (i_34_ != -1) return this.loader.getItemDefinitions(95, i_34_);
        }
        return this;
    }

    final String getStringParam(String string, int i, int i_36_) {
        try {
            anInt2751++;
            if (this.params == null) return string;
            if (i_36_ != -1511086397) decode(25, null);
            StringNode StringNode = ((StringNode) this.params.method3480(i, i_36_ ^ 0x5a114e4b));
            if (StringNode == null) return string;
            return StringNode.aString7211;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.N(" + (string != null ? "{...}" : "null") + ',' + i + ',' + i_36_ + ')'));
        }
    }

    final int[] renderSprite(int i, boolean bool, int i_37_, ha var_ha, ha var_ha_38_, Class324 class324, Class154 class154, int i_39_, byte i_40_, int i_41_) {
        try {
            anInt2806++;
            Class124 class124 = Class300.method2277(0, (this.loader.aClass45_3268), mesh, i_40_ ^ 0x65);
            if (class124 == null) return null;
            if (class124.anInt1830 < 13) class124.method1092(2, i_40_ ^ ~0xb);
            if (recol_s != null) {
                for (int i_42_ = 0; (recol_s.length > i_42_); i_42_++) {
                    if (recol_d_palette == null || i_42_ >= recol_d_palette.length) class124.method1098(recol_s[i_42_], (byte) 126, recol_d[i_42_]);
                    else class124.method1098(recol_s[i_42_], (byte) 126, (Class336.aShortArray4172[recol_d_palette[i_42_] & 0xff]));
                }
            }
            if (retex_s != null) {
                for (int i_43_ = 0; (retex_s.length > i_43_); i_43_++)
                    class124.method1095(retex_s[i_43_], 0, retex_d[i_43_]);
            }
            if (class154 != null) {
                for (int i_44_ = 0; i_44_ < 5; i_44_++) {
                    for (int i_45_ = 0; (i_45_ < Class367_Sub2.aShortArrayArrayArray7290.length); i_45_++) {
                        if ((Class367_Sub2.aShortArrayArrayArray7290[i_45_][i_44_]).length > class154.anIntArray2095[i_44_]) class124.method1098((Class136.aShortArrayArray4791[i_45_][i_44_]), (byte) 126, (Class367_Sub2.aShortArrayArrayArray7290[i_45_][i_44_][(class154.anIntArray2095[i_44_])]));
                    }
                }
            }
            int i_46_ = 2048;
            boolean bool_47_ = false;
            if (resizex != 128 || resizey != 128 || resizez != 128) {
                i_46_ |= 0x7;
                bool_47_ = true;
            }
            Class64 class64 = var_ha_38_.method3625(class124, i_46_, 64, ambient + 64, 768 + contrast);
            if (!class64.method618()) return null;
            if (bool_47_) class64.O(resizex, resizey, resizez);
            Class105 class105 = null;
            if (this.certtemplate == -1) {
                if (this.lenttemplate != -1) {
                    class105 = (this.loader.method1932(var_ha_38_, i_37_, i, class324, class154, 0, true, (byte) 83, var_ha, this.lentlink, false, i_41_));
                    if (class105 == null) return null;
                }
            } else {
                class105 = (this.loader.method1932(var_ha_38_, 0, 10, class324, class154, 0, true, (byte) 83, var_ha, this.certlink, true, 1));
                if (class105 == null) return null;
            }
            int i_48_;
            if (!bool) {
                if (i_41_ == 2) i_48_ = ((int) (1.04 * (double) this.zoom2d) << 2);
                else i_48_ = this.zoom2d << 2;
            } else i_48_ = ((int) (1.5 * (double) this.zoom2d) << 2);
            var_ha_38_.DA(16, 16, 512, 512);
            Class101 class101 = var_ha_38_.method3654();
            class101.method910();
            var_ha_38_.method3638(class101);
            var_ha_38_.xa(1.0F);
            var_ha_38_.ZA(16777215, 1.0F, 1.0F, -50.0F, -10.0F, -50.0F);
            Class101 class101_49_ = var_ha_38_.method3705();
            class101_49_.method902(-this.zan2d << 3);
            class101_49_.method896(this.yan2d << 3);
            class101_49_.method891(this.xof2d << 2, ((i_48_ * (Class70.anIntArray1207[this.xan2d << 3]) >> 14) - class64.fa() / 2 + (this.yof2d << 2)), ((i_48_ * (Class70.anIntArray1204[this.xan2d << 3]) >> 14) - -(this.yof2d << 2)));
            class101_49_.method900(this.xan2d << 3);
            int i_50_ = var_ha_38_.i();
            int i_51_ = var_ha_38_.XA();
            var_ha_38_.f(50, 2147483647);
            var_ha_38_.ya();
            var_ha_38_.la();
            var_ha_38_.aa(0, 0, 36, 32, 0, 0);
            class64.method615(class101_49_, null, 1);
            var_ha_38_.f(i_50_, i_51_);
            int[] is = var_ha_38_.na(0, 0, 36, 32);
            if (i_40_ != -102) getHeadModelData(false, 37);
            if (i_41_ >= 1) {
                is = applySpriteOutline(-16777214, -1, is);
                if (i_41_ >= 2) is = applySpriteOutline(-1, -1, is);
            }
            if (i_37_ != 0) applySpriteShadow(i_37_, is, (byte) 119);
            var_ha_38_.method3662(36, is, (byte) 94, 0, 36, 32).method974(0, 0);
            if (this.certtemplate == -1) {
                if (this.lenttemplate != -1) class105.method974(0, 0);
            } else class105.method974(0, 0);
            if (i_39_ == 1 || (i_39_ == 2 && (this.stackable == 1 || i != 1) && i != -1)) class324.method2576(formatItemStackAmount(i, i_40_ + -11517), -256, 9, 0, -16777215, i_40_ + -15);
            is = var_ha_38_.na(0, 0, 36, 32);
            for (int i_52_ = 0; i_52_ < is.length; i_52_++) {
                if ((0xffffff & is[i_52_]) != 0) is[i_52_] = Class273.method2057(is[i_52_], -16777216);
                else is[i_52_] = 0;
            }
            return is;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.O(" + i + ',' + bool + ',' + i_37_ + ',' + (var_ha != null ? "{...}" : "null") + ',' + (var_ha_38_ != null ? "{...}" : "null") + ',' + (class324 != null ? "{...}" : "null") + ',' + (class154 != null ? "{...}" : "null") + ',' + i_39_ + ',' + i_40_ + ',' + i_41_ + ')'));
        }
    }

    final void postDecode(byte i) {
        if (i < 32) this.cursor1op = -31;
        anInt2814++;
    }

    public static void clearItemDefinitionSprites(int i) {
        aClass238_2773 = null;
        if (i <= 54) aLong2789 = -74L;
    }

    final boolean hasEquipmentModels(boolean bool, int i) {
        anInt2780++;
        int i_53_ = this.manwear;
        int i_54_ = manwear2;
        int i_55_ = manwear3;
        if (bool) {
            i_55_ = womanwear3;
            i_53_ = this.womanwear;
            i_54_ = womanwear2;
        }
        if (i_53_ == -1) return true;
        boolean bool_56_ = true;
        if (!this.loader.aClass45_3268.method420(-10499, i_53_, 0)) bool_56_ = false;
        if (i_54_ != -1 && !this.loader.aClass45_3268.method420(i ^ 0x2902, i_54_, 0)) bool_56_ = false;
        if (i != i_55_ && !this.loader.aClass45_3268.method420(-10499, i_55_, 0)) bool_56_ = false;
        return bool_56_;
    }

    private final void readValues(int i, int opcode, Packet Packet) {
        try {
            if (i != 4) clearItemDefinitionSprites(9);
            if (opcode != 1) {
                if (opcode != 2) {
                    if (opcode == 4) this.zoom2d = Packet.readUnsignedShort(i ^ 0x3235f8fc);
                    else if (opcode == 5) this.xan2d = Packet.readUnsignedShort(842397944);
                    else if (opcode == 6) this.yan2d = Packet.readUnsignedShort(i ^ 0x3235f8fc);
                    else if (opcode == 7) {
                        this.xof2d = Packet.readUnsignedShort(842397944);
                        if (this.xof2d > 32767) this.xof2d -= 65536;
                    } else if (opcode == 8) {
                        this.yof2d = Packet.readUnsignedShort(842397944);
                        if (this.yof2d > 32767) this.yof2d -= 65536;
                    } else if (opcode != 11) {
                        if (opcode != 12) {
                            if (opcode == 16) this.members = true;
                            else if (opcode == 18) this.anInt2802 = Packet.readUnsignedShort(i ^ 0x3235f8fc);
                            else if (opcode == 23) this.manwear = Packet.readUnsignedShort(842397944);
                            else if (opcode == 24) manwear2 = Packet.readUnsignedShort(i ^ 0x3235f8fc);
                            else if (opcode == 25) this.womanwear = Packet.readUnsignedShort(i + 842397940);
                            else if (opcode == 26) womanwear2 = Packet.readUnsignedShort(i + 842397940);
                            else if (opcode < 30 || opcode >= 35) {
                                if (opcode >= 35 && opcode < 40) this.iop[-35 + opcode] = Packet.readString((byte) 103);
                                else if (opcode == 40) {
                                    int i_58_ = Packet.readUnsignedByte(255);
                                    recol_s = new short[i_58_];
                                    recol_d = new short[i_58_];
                                    for (int i_59_ = 0; i_58_ > i_59_; i_59_++) {
                                        recol_s[i_59_] = (short) (Packet.readUnsignedShort(842397944));
                                        recol_d[i_59_] = (short) (Packet.readUnsignedShort(842397944));
                                    }
                                } else if (opcode == 41) {
                                    int i_68_ = Packet.readUnsignedByte(i + 251);
                                    retex_d = new short[i_68_];
                                    retex_s = new short[i_68_];
                                    for (int i_69_ = 0; i_69_ < i_68_; i_69_++) {
                                        retex_s[i_69_] = (short) (Packet.readUnsignedShort(842397944));
                                        retex_d[i_69_] = (short) (Packet.readUnsignedShort(842397944));
                                    }
                                } else if (opcode == 42) {
                                    int i_60_ = Packet.readUnsignedByte(i + 251);
                                    recol_d_palette = new byte[i_60_];
                                    for (int i_61_ = 0; i_61_ < i_60_; i_61_++)
                                        recol_d_palette[i_61_] = Packet.readByte(-114);
                                } else if (opcode == 65) this.stockmarket = true;
                                else if (opcode == 78) manwear3 = Packet.readUnsignedShort(842397944);
                                else if (opcode == 79) womanwear3 = (Packet.readUnsignedShort(i ^ 0x3235f8fc));
                                else if (opcode == 90) manhead = Packet.readUnsignedShort(842397944);
                                else if (opcode == 91) womanhead = Packet.readUnsignedShort(842397944);
                                else if (opcode != 92) {
                                    if (opcode != 93) {
                                        if (opcode != 95) {
                                            if (opcode != 96) {
                                                if (opcode == 97) this.certlink = (Packet.readUnsignedShort(842397944));
                                                else if (opcode == 98) this.certtemplate = (Packet.readUnsignedShort(842397944));
                                                else if ((opcode >= 100) && (opcode < 110)) {
                                                    if ((this.countobj) == null) {
                                                        this.countco = (new int
                                                                [10]);
                                                        this.countobj = (new int
                                                                [10]);
                                                    }
                                                    this.countobj[opcode - 100] = (Packet.readUnsignedShort(842397944));
                                                    this.countco[opcode + -100] = (Packet.readUnsignedShort(842397944));
                                                } else if (opcode == 110) resizex = (Packet.readUnsignedShort(842397944));
                                                else if (opcode != 111) {
                                                    if (opcode == 112) resizez = (Packet.readUnsignedShort(842397944));
                                                    else if (opcode != 113) {
                                                        if (opcode == 114) contrast = ((Packet.readByte(-90)) * 5);
                                                        else if (opcode == 115) this.team = (Packet.readUnsignedByte(255));
                                                        else if (opcode != 121) {
                                                            if (opcode != 122) {
                                                                if (opcode == 125) {
                                                                    anInt2807 = Packet.readByte(-99) << 2;
                                                                    anInt2797 = Packet.readByte(i + -99) << 2;
                                                                    anInt2808 = Packet.readByte(-111) << 2;
                                                                } else if (opcode == 126) {
                                                                    anInt2803 = Packet.readByte(-121) << 2;
                                                                    anInt2753 = Packet.readByte(-92) << 2;
                                                                    anInt2823 = Packet.readByte(-93) << 2;
                                                                } else if (opcode == 127) {
                                                                    this.cursor1op = Packet.readUnsignedByte(255);
                                                                    this.cursor1 = Packet.readUnsignedShort(842397944);
                                                                } else if (opcode == 128) {
                                                                    this.cursor2op = Packet.readUnsignedByte(255);
                                                                    this.cursor2 = Packet.readUnsignedShort(842397944);
                                                                } else if (opcode == 129) {
                                                                    this.anInt2766 = Packet.readUnsignedByte(i ^ 0xfb);
                                                                    this.anInt2818 = Packet.readUnsignedShort(842397944);
                                                                } else if (opcode == 130) {
                                                                    this.anInt2774 = Packet.readUnsignedByte(255);
                                                                    this.anInt2817 = Packet.readUnsignedShort(842397944);
                                                                } else if (opcode == 132) {
                                                                    int i_62_ = Packet.readUnsignedByte(i ^ 0xfb);
                                                                    this.quests = new int[i_62_];
                                                                    for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
                                                                        this.quests[i_63_] = Packet.readUnsignedShort(842397944);
                                                                } else if (opcode == 134) this.anInt2784 = Packet.readUnsignedByte(255);
                                                                else if (opcode == 249) {
                                                                    int i_64_ = Packet.readUnsignedByte(255);
                                                                    if (this.params == null) {
                                                                        int i_65_ = Class33.method340(i_64_, (byte) 108);
                                                                        this.params = new IterableHashTable(i_65_);
                                                                    }
                                                                    for (int i_66_ = 0; i_66_ < i_64_; i_66_++) {
                                                                        boolean bool = Packet.readUnsignedByte(255) == 1;
                                                                        int i_67_ = Packet.readMedium(-1);
                                                                        Node Node;
                                                                        if (bool) Node = new StringNode(Packet.readString((byte) 107));
                                                                        else Node = new IntNode(Packet.readInt((byte) -126));
                                                                        this.params.method3483((byte) 76, i_67_, Node);
                                                                    }
                                                                }
                                                            } else this.lenttemplate = Packet.readUnsignedShort(i + 842397940);
                                                        } else this.lentlink = (Packet.readUnsignedShort(842397944));
                                                    } else ambient = (Packet.readByte(-88));
                                                } else resizey = (Packet.readUnsignedShort(842397944));
                                            } else this.anInt2799 = (Packet.readUnsignedByte(255));
                                        } else this.zan2d = (Packet.readUnsignedShort(i + 842397940));
                                    } else womanhead2 = (Packet.readUnsignedShort(i + 842397940));
                                } else manhead2 = Packet.readUnsignedShort(842397944);
                            } else this.op[-30 + opcode] = Packet.readString((byte) 98);
                        } else this.cost = Packet.readInt((byte) -126);
                    } else this.stackable = 1;
                } else this.name = Packet.readString((byte) -42);
            } else mesh = Packet.readUnsignedShort(i + 842397940);
            anInt2754++;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.L(" + i + ',' + opcode + ',' + (Packet != null ? "{...}" : "null") + ')'));
        }
    }

    final int getIntParam(int i, int i_70_, int i_71_) {
        anInt2793++;
        if (this.params == null) return i;
        IntNode IntNode = ((IntNode) this.params.method3480(i_71_, -6008));
        if (IntNode == null) return i;
        int i_72_ = 56 % ((-32 - i_70_) / 50);
        return IntNode.anInt6976;
    }

    static final void method1568(int[] is, int i, int i_73_, int i_74_, float[] fs, int[] is_75_, int i_76_, int i_77_, int i_78_, int i_79_, int i_80_, int i_81_, float[] fs_82_) {
        try {
            if (i_77_ <= 112) aLong2789 = 95L;
            anInt2782++;
            int i_83_ = i * i_80_ - -i_73_;
            int i_84_ = i_81_ + i_79_ * i_74_;
            int i_85_ = -i_76_ + i_80_;
            int i_86_ = -i_76_ + i_79_;
            if (is_75_ == null) {
                for (int i_87_ = 0; i_78_ > i_87_; i_87_++) {
                    int i_88_ = i_83_ - -i_76_;
                    while (i_83_ < i_88_) fs[i_84_++] = fs_82_[i_83_++];
                    i_83_ += i_85_;
                    i_84_ += i_86_;
                }
            } else if (fs_82_ == null) {
                for (int i_91_ = 0; i_91_ < i_78_; i_91_++) {
                    int i_92_ = i_76_ + i_83_;
                    while (i_83_ < i_92_) is[i_84_++] = is_75_[i_83_++];
                    i_83_ += i_85_;
                    i_84_ += i_86_;
                }
            } else {
                for (int i_89_ = 0; i_78_ > i_89_; i_89_++) {
                    int i_90_ = i_83_ - -i_76_;
                    while (i_83_ < i_90_) {
                        is[i_84_] = is_75_[i_83_];
                        fs[i_84_++] = fs_82_[i_83_++];
                    }
                    i_83_ += i_85_;
                    i_84_ += i_86_;
                }
            }
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.C(" + (is != null ? "{...}" : "null") + ',' + i + ',' + i_73_ + ',' + i_74_ + ',' + (fs != null ? "{...}" : "null") + ',' + (is_75_ != null ? "{...}" : "null") + ',' + i_76_ + ',' + i_77_ + ',' + i_78_ + ',' + i_79_ + ',' + i_80_ + ',' + i_81_ + ',' + (fs_82_ != null ? "{...}" : "null") + ')'));
        }
    }

    final void decode(int i, Packet Packet) {
        try {
            if (i != 768) hasEquipmentModels(true, -71);
            for (; ; ) {
                int i_93_ = Packet.readUnsignedByte(i + -513);
                if (i_93_ == 0) break;
                readValues(4, i_93_, Packet);
            }
            anInt2800++;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.I(" + i + ',' + (Packet != null ? "{...}" : "null") + ')'));
        }
    }

    final void generateCertificate(int i, ObjType class213_94_, ObjType class213_95_) {
        try {
            recol_d = class213_95_.recol_d;
            retex_s = class213_95_.retex_s;
            recol_d_palette = class213_95_.recol_d_palette;
            this.members = class213_94_.members;
            this.xan2d = class213_95_.xan2d;
            this.zan2d = class213_95_.zan2d;
            anInt2776++;
            this.xof2d = class213_95_.xof2d;
            retex_d = class213_95_.retex_d;
            this.yan2d = class213_95_.yan2d;
            this.cost = class213_94_.cost;
            this.yof2d = class213_95_.yof2d;
            this.stackable = i;
            recol_s = class213_95_.recol_s;
            this.zoom2d = class213_95_.zoom2d;
            this.name = class213_94_.name;
            mesh = class213_95_.mesh;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.F(" + i + ',' + (class213_94_ != null ? "{...}" : "null") + ',' + (class213_95_ != null ? "{...}" : "null") + ')'));
        }
    }

    private final void applySpriteShadow(int i, int[] is, byte i_96_) {
        try {
            if (i_96_ <= 81) manwear3 = -46;
            for (int i_97_ = 31; i_97_ > 0; i_97_--) {
                int i_98_ = 36 * i_97_;
                for (int i_99_ = 35; i_99_ > 0; i_99_--) {
                    if (is[i_99_ - -i_98_] == 0 && is[i_99_ + i_98_ - 1 + -36] != 0) is[i_98_ + i_99_] = i;
                }
            }
            anInt2790++;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.M(" + i + ',' + (is != null ? "{...}" : "null") + ',' + i_96_ + ')'));
        }
    }

    public ObjType() {
        this.anInt2766 = -1;
        womanhead2 = -1;
        resizez = 128;
        this.lentlink = -1;
        this.certlink = -1;
        this.name = "null";
        resizey = 128;
        this.anInt2799 = 0;
        this.womanwear = -1;
        this.anInt2784 = 0;
        manwear2 = -1;
        manhead = -1;
        this.stockmarket = false;
        this.xan2d = 0;
        this.lenttemplate = -1;
        this.zan2d = 0;
        manhead2 = -1;
        womanwear2 = -1;
        this.anInt2817 = -1;
        this.xof2d = 0;
        resizex = 128;
        anInt2808 = 0;
        anInt2803 = 0;
        womanhead = -1;
        this.manwear = -1;
        anInt2823 = 0;
        this.anInt2818 = -1;
        this.yan2d = 0;
        this.anInt2774 = -1;
        this.yof2d = 0;
        anInt2807 = 0;
        ambient = 0;
        manwear3 = -1;
        this.cursor2op = -1;
        this.stackable = 0;
        this.cursor1 = -1;
        this.anInt2802 = -1;
        anInt2797 = 0;
        this.cursor2 = -1;
        this.team = 0;
        contrast = 0;
        this.cost = 1;
        anInt2753 = 0;
        this.zoom2d = 2000;
        this.certtemplate = -1;
        this.members = false;
    }

    private final int[] applySpriteOutline(int i, int i_100_, int[] is) {
        try {
            anInt2829++;
            if (i_100_ != -1) return null;
            int[] is_101_ = new int[1152];
            int i_102_ = 0;
            for (int i_103_ = 0; i_103_ < 32; i_103_++) {
                for (int i_104_ = 0; i_104_ < 36; i_104_++) {
                    int i_105_ = is[i_102_];
                    if (i_105_ == 0) {
                        if (i_104_ <= 0 || is[i_102_ - 1] == 0) {
                            if (i_103_ > 0 && is[-36 + i_102_] != 0) i_105_ = i;
                            else if (i_104_ < 35 && is[i_102_ + 1] != 0) i_105_ = i;
                            else if (i_103_ < 31 && is[i_102_ + 36] != 0) i_105_ = i;
                        } else i_105_ = i;
                    }
                    is_101_[i_102_++] = i_105_;
                }
            }
            return is_101_;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("rq.K(" + i + ',' + i_100_ + ',' + (is != null ? "{...}" : "null") + ')'));
        }
    }
}
