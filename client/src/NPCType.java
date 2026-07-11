/* NPCType - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

final class NPCType {
    byte movementCapabilities;
    static int anInt1326;
    int runSound;
    private short[] recol_s;
    int yawSpeed;
    private byte colourLightness;
    boolean crawl;
    static int anInt1332;
    int anInt6706 = -1;
    static int anInt1334;
    int cursor1Op;
    int mobilisingArmiesIcon;
    int pickSizeShift;
    int cursor2;
    short shadowOuterColour;
    int soundRateMin;
    static int anInt1341;
    int[] quests;
    int readySound;
    int id;
    boolean renderHighPriority;
    private int scaleH;
    byte shadowInnerAlpha;
    NPCTypeList loader;
    String[] op;
    short shadowInnerColour = 0;
    static int anInt1351;
    private short[] recol_d;
    byte shadowOuterAlpha;
    private int multinpcVarp;
    byte spawnDirection;
    int soundVolume;
    static int anInt1357;
    private int scaleV;
    static int anInt1359 = 0;
    private byte colourSaturation;
    int combatLevel;
    boolean isFollower;
    int soundRateMax;
    int walkSound;
    static Class105 aClass105_1365;
    int basId;
    static client aClient1367;
    private int multinpcVarbit;
    boolean hasShadow;
    boolean vorbis;
    int cursor1;
    String name;
    int healthBarSprite;
    private byte[] recol_d_palette;
    int headIcon;
    private byte colourHue;
    int[] multinpcs;
    static int anInt1378;
    static int anInt1379;
    private int[] headModels;
    boolean aBoolean503;
    int timerbarSprite;
    int mapElement;
    byte aByte107;
    int cursor2Op;
    private IterableHashTable params;
    static int anInt1387;
    private int[][] translations;
    static int anInt1389;
    int height;
    private short[] retex_d;
    int soundRangeMax;
    private short[] retex_s;
    static int anInt1394;
    int crawlSound;
    boolean interactive;
    boolean displayOnMiniMap;
    private int ambient;
    int anInt1399;
    static int anInt1400;
    int attackCursor;
    private int[] models;
    static int anInt1403;
    static int anInt1404;
    private byte colourScale;
    private int diffusion;

    final boolean method793(int i) {
        anInt1403++;
        if (this.multinpcs == null) {
            return this.readySound != -1 || this.walkSound != -1 || this.runSound != -1;
        }
        for (int i_0_ = i; this.multinpcs.length > i_0_; i_0_++) {
            if (this.multinpcs[i_0_] != -1) {
                NPCType class79_1_ = (this.loader.method2079(this.multinpcs[i_0_], -1));
                if (class79_1_.readySound != -1 || class79_1_.walkSound != -1 || class79_1_.runSound != -1) return true;
            }
        }
        return false;
    }

    final NPCType method794(Interface17 interface17, int i) {
        anInt1394++;
        int i_2_ = i;
        if (multinpcVarbit == -1) {
            if (multinpcVarp != -1) i_2_ = interface17.method61(multinpcVarp, (byte) -16);
        } else i_2_ = interface17.method62(multinpcVarbit, -65536);
        if (i_2_ < 0 || (-1 + this.multinpcs.length <= i_2_) || this.multinpcs[i_2_] == -1) {
            int i_3_ = (this.multinpcs[this.multinpcs.length - 1]);
            if (i_3_ == -1) return null;
            return this.loader.method2079(i_3_, i);
        }
        return this.loader.method2079(this.multinpcs[i_2_], -1);
    }

    private final void method795(Packet Packet, int i, int i_4_) {
        if (i_4_ != 127) this.aBoolean503 = true;
        if (i == 1) {
            int i_5_ = Packet.readUnsignedByte(255);
            models = new int[i_5_];
            for (int i_6_ = 0; i_5_ > i_6_; i_6_++) {
                models[i_6_] = Packet.readUnsignedShort(842397944);
                if (models[i_6_] == 65535) models[i_6_] = -1;
            }
        } else if (i != 2) {
            if (i != 12) {
                if (i < 30 || i >= 35) {
                    if (i == 40) {
                        int i_25_ = Packet.readUnsignedByte(255);
                        recol_s = new short[i_25_];
                        recol_d = new short[i_25_];
                        for (int i_26_ = 0; i_26_ < i_25_; i_26_++) {
                            recol_s[i_26_] = (short) Packet.readUnsignedShort(842397944);
                            recol_d[i_26_] = (short) Packet.readUnsignedShort(842397944);
                        }
                    } else if (i == 41) {
                        int i_23_ = Packet.readUnsignedByte(255);
                        retex_s = new short[i_23_];
                        retex_d = new short[i_23_];
                        for (int i_24_ = 0; i_24_ < i_23_; i_24_++) {
                            retex_s[i_24_] = (short) Packet.readUnsignedShort(842397944);
                            retex_d[i_24_] = (short) Packet.readUnsignedShort(842397944);
                        }
                    } else if (i == 42) {
                        int i_7_ = Packet.readUnsignedByte(i_4_ ^ 0x80);
                        recol_d_palette = new byte[i_7_];
                        for (int i_8_ = 0; i_8_ < i_7_; i_8_++)
                            recol_d_palette[i_8_] = Packet.readByte(-88);
                    } else if (i == 60) {
                        int i_9_ = Packet.readUnsignedByte(255);
                        headModels = new int[i_9_];
                        for (int i_10_ = 0; i_9_ > i_10_; i_10_++)
                            headModels[i_10_] = Packet.readUnsignedShort(842397944);
                    } else if (i != 93) {
                        if (i != 95) {
                            if (i != 97) {
                                if (i != 98) {
                                    if (i == 99) this.renderHighPriority = true;
                                    else if (i != 100) {
                                        if (i == 101) diffusion = 5 * (Packet.readByte(i_4_ ^ ~0x27));
                                        else if (i == 102) this.headIcon = (Packet.readUnsignedShort(i_4_ + 842397817));
                                        else if (i == 103) this.yawSpeed = (Packet.readUnsignedShort(842397944));
                                        else if ((i == 106) || i == 118) {
                                            multinpcVarbit = (Packet.readUnsignedShort(842397944));
                                            if (multinpcVarbit == 65535) multinpcVarbit = -1;
                                            multinpcVarp = (Packet.readUnsignedShort(842397944));
                                            if (multinpcVarp == 65535) multinpcVarp = -1;
                                            int i_11_ = -1;
                                            if (i == 118) {
                                                i_11_ = (Packet.readUnsignedShort(842397944));
                                                if (i_11_ == 65535) i_11_ = -1;
                                            }
                                            int i_12_ = Packet.readUnsignedByte(255);
                                            this.multinpcs = new int[2 + i_12_];
                                            for (int i_13_ = 0; i_13_ <= i_12_; i_13_++) {
                                                this.multinpcs[i_13_] = (Packet.readUnsignedShort(842397944));
                                                if ((this.multinpcs[i_13_]) == 65535) this.multinpcs[i_13_] = -1;
                                            }
                                            this.multinpcs[1 + i_12_] = i_11_;
                                        } else if (i == 107) this.interactive = false;
                                        else if (i != 109) {
                                            if (i == 111) this.hasShadow = false;
                                            else if (i == 113) {
                                                this.shadowOuterColour = (short) (Packet.readUnsignedShort(842397944));
                                                this.shadowInnerColour = (short) (Packet.readUnsignedShort(842397944));
                                            } else if (i == 114) {
                                                this.shadowOuterAlpha = (Packet.readByte(-110));
                                                this.shadowInnerAlpha = (Packet.readByte(-85));
                                            } else if (i != 119) {
                                                if (i == 121) {
                                                    translations = (new int
                                                            [models.length]
                                                            []);
                                                    int i_14_ = (Packet.readUnsignedByte(255));
                                                    for (int i_15_ = 0; (i_14_ > i_15_); i_15_++) {
                                                        int i_16_ = (Packet.readUnsignedByte(255));
                                                        int[] is = (translations[i_16_] = new int[3]);
                                                        is[0] = (Packet.readByte(Class348_Sub21.method2955(i_4_, -50)));
                                                        is[1] = (Packet.readByte(-113));
                                                        is[2] = (Packet.readByte(-84));
                                                    }
                                                } else if (i == 122) this.healthBarSprite = (Packet.readUnsignedShort(842397944));
                                                else if (i != 123) {
                                                    if (i == 125) this.spawnDirection = (Packet.readByte(-95));
                                                    else if (i == 127) this.basId = (Packet.readUnsignedShort(i_4_ + 842397817));
                                                    else if (i != 128) {
                                                        if (i == 134) {
                                                            this.readySound = Packet.readUnsignedShort(842397944);
                                                            if (this.readySound == 65535) this.readySound = -1;
                                                            this.crawlSound = Packet.readUnsignedShort(842397944);
                                                            if (this.crawlSound == 65535) this.crawlSound = -1;
                                                            this.walkSound = Packet.readUnsignedShort(842397944);
                                                            if (this.walkSound == 65535) this.walkSound = -1;
                                                            this.runSound = Packet.readUnsignedShort(i_4_ ^ 0x3235f887);
                                                            if (this.runSound == 65535) this.runSound = -1;
                                                            this.soundRangeMax = Packet.readUnsignedByte(255);
                                                        } else if (i == 135) {
                                                            this.cursor1Op = Packet.readUnsignedByte(255);
                                                            this.cursor1 = Packet.readUnsignedShort(842397944);
                                                        } else if (i == 136) {
                                                            this.cursor2Op = Packet.readUnsignedByte(255);
                                                            this.cursor2 = Packet.readUnsignedShort(842397944);
                                                        } else if (i != 137) {
                                                            if (i != 138) {
                                                                if (i != 139) {
                                                                    if (i != 140) {
                                                                        if (i == 141) this.isFollower = true;
                                                                        else if (i == 142) this.mapElement = Packet.readUnsignedShort(842397944);
                                                                        else if (i != 143) {
                                                                            if (i >= 150 && i < 155) {
                                                                                this.op[-150 + i] = Packet.readString((byte) -73);
                                                                                if (!this.loader.aBoolean3583) this.op[i + -150] = null;
                                                                            } else if (i == 155) {
                                                                                colourHue = Packet.readByte(i_4_ ^ ~0x16);
                                                                                colourSaturation = Packet.readByte(-113);
                                                                                colourLightness = Packet.readByte(-112);
                                                                                colourScale = Packet.readByte(-87);
                                                                            } else if (i == 158) this.aByte107 = (byte) 1;
                                                                            else if (i != 159) {
                                                                                if (i == 160) {
                                                                                    int i_21_ = Packet.readUnsignedByte(255);
                                                                                    this.quests = new int[i_21_];
                                                                                    for (int i_22_ = 0; i_22_ < i_21_; i_22_++)
                                                                                        this.quests[i_22_] = Packet.readUnsignedShort(i_4_ + 842397817);
                                                                                } else if (i == 162) this.vorbis = true;
                                                                                else if (i != 163) {
                                                                                    if (i == 164) {
                                                                                        this.soundRateMin = Packet.readUnsignedShort(842397944);
                                                                                        this.soundRateMax = Packet.readUnsignedShort(842397944);
                                                                                    } else if (i != 165) {
                                                                                        if (i == 249) {
                                                                                            int i_17_ = Packet.readUnsignedByte(255);
                                                                                            if (params == null) {
                                                                                                int i_18_ = Class33.method340(i_17_, (byte) 108);
                                                                                                params = new IterableHashTable(i_18_);
                                                                                            }
                                                                                            for (int i_19_ = 0; i_17_ > i_19_; i_19_++) {
                                                                                                boolean bool = Packet.readUnsignedByte(255) == 1;
                                                                                                int i_20_ = Packet.readMedium(-1);
                                                                                                Node Node;
                                                                                                if (!bool) Node = new IntNode(Packet.readInt((byte) -126));
                                                                                                else Node = new StringNode(Packet.readString((byte) -120));
                                                                                                params.method3483((byte) 61, i_20_, Node);
                                                                                            }
                                                                                        }
                                                                                    } else this.pickSizeShift = Packet.readUnsignedByte(255);
                                                                                } else this.anInt6706 = Packet.readUnsignedByte(i_4_ + 128);
                                                                            } else this.aByte107 = (byte) 0;
                                                                        } else this.aBoolean503 = true;
                                                                    } else this.soundVolume = Packet.readUnsignedByte(i_4_ + 128);
                                                                } else this.timerbarSprite = Packet.readUnsignedShort(i_4_ + 842397817);
                                                            } else this.mobilisingArmiesIcon = Packet.readUnsignedShort(842397944);
                                                        } else this.attackCursor = Packet.readUnsignedShort(842397944);
                                                    } else Packet.readUnsignedByte(255);
                                                } else this.height = (Packet.readUnsignedShort(i_4_ ^ 0x3235f887));
                                            } else this.movementCapabilities = (Packet.readByte(i_4_ + -245));
                                        } else this.crawl = false;
                                    } else ambient = Packet.readByte(-123);
                                } else scaleV = (Packet.readUnsignedShort(i_4_ + 842397817));
                            } else scaleH = Packet.readUnsignedShort(842397944);
                        } else this.combatLevel = Packet.readUnsignedShort(842397944);
                    } else this.displayOnMiniMap = false;
                } else this.op[-30 + i] = Packet.readString((byte) 124);
            } else this.anInt1399 = Packet.readUnsignedByte(i_4_ + 128);
        } else this.name = Packet.readString((byte) 122);
        anInt1357++;
    }

    final boolean method796(Interface17 interface17, int i) {
        anInt1351++;
        if (this.multinpcs == null) return true;
        int i_27_ = -1;
        if (multinpcVarbit != -1) i_27_ = interface17.method62(multinpcVarbit, -65536);
        else if (multinpcVarp != -1) i_27_ = interface17.method61(multinpcVarp, (byte) -16);
        if (i_27_ < 0 || (this.multinpcs.length - 1 <= i_27_) || this.multinpcs[i_27_] == -1) {
            int i_28_ = (this.multinpcs[this.multinpcs.length - 1]);
            return i_28_ != -1;
        }
        return i == 18627;
    }

    static final void method797(int i, int i_29_, byte i_30_) {
        anInt1379++;
        if (Class348_Sub40_Sub6.anInt9139 != i_29_) {
            Class318_Sub6.anIntArray6432 = new int[i_29_];
            for (int i_31_ = 0; i_31_ < i_29_; i_31_++)
                Class318_Sub6.anIntArray6432[i_31_] = (i_31_ << 12) / i_29_;
            Class239_Sub22.anInt6076 = i_29_ + -1;
            Class348_Sub40_Sub6.anInt9139 = i_29_;
            Class248.anInt3201 = 32 * i_29_;
        }
        if (i_30_ <= 108) aClient1367 = null;
        if (Class286_Sub2.anInt6212 != i) {
            if (Class348_Sub40_Sub6.anInt9139 != i) {
                Class239_Sub18.anIntArray6035 = new int[i];
                for (int i_32_ = 0; i_32_ < i; i_32_++)
                    Class239_Sub18.anIntArray6035[i_32_] = (i_32_ << 12) / i;
            } else Class239_Sub18.anIntArray6035 = Class318_Sub6.anIntArray6432;
            Class286_Sub2.anInt6212 = i;
            Class299_Sub2.anInt6325 = -1 + i;
        }
    }

    final void method798(int i, Packet Packet) {
        for (; ; ) {
            int i_33_ = Packet.readUnsignedByte(255);
            if (i_33_ == 0) break;
            method795(Packet, i_33_, 127);
        }
        anInt1400++;
        if (i <= 35) method798(-16, null);
    }

    final void method799(int i) {
        if (models == null) models = new int[0];
        if (i >= -75) colourHue = (byte) 102;
        anInt1334++;
        if (this.aByte107 == -1) {
            if (Class10.aClass230_186 == this.loader.aClass230_3578) this.aByte107 = (byte) 1;
            else this.aByte107 = (byte) 0;
        }
    }

    final Class64 method800(int i, Class182[] class182s, Class87 class87, boolean bool, Class17 class17, int i_34_, Class261 class261, int i_35_, Class17 class17_36_, Interface17 interface17, ha var_ha, int i_37_, int[] is, int i_38_, int i_39_, int i_40_, int i_41_) {
        try {
            anInt1341++;
            if (this.multinpcs != null) {
                NPCType class79_42_ = method794(interface17, -1);
                if (class79_42_ == null) return null;
                return class79_42_.method800(i, class182s, class87, false, class17, i_34_, class261, i_35_, class17_36_, interface17, var_ha, i_37_, is, i_38_, i_39_, i_40_, i_41_);
            }
            int i_43_ = i_40_;
            if (scaleV != 128) i_43_ |= 0x2;
            if (scaleH != 128) i_43_ |= 0x5;
            boolean bool_44_ = class17_36_ != null || class17 != null;
            boolean bool_45_ = false;
            boolean bool_46_ = false;
            boolean bool_47_ = false;
            boolean bool_48_ = bool;
            int i_49_ = class182s == null ? 0 : class182s.length;
            for (int i_50_ = 0; i_50_ < i_49_; i_50_++) {
                Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_] = null;
                if (class182s[i_50_] != null) {
                    Class17 class17_51_ = class87.method835((class182s[i_50_].anInt2454), 7);
                    if (class17_51_.anIntArray237 != null) {
                        bool_44_ = true;
                        Class163.aClass17Array2169[i_50_] = class17_51_;
                        int i_52_ = class182s[i_50_].anInt2451;
                        int i_53_ = class182s[i_50_].anInt2455;
                        int i_54_ = class17_51_.anIntArray237[i_52_];
                        Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_] = class87.method839(i_54_ >>> 16, 3);
                        i_54_ &= 0xffff;
                        Class90.anIntArray1518[i_50_] = i_54_;
                        if ((Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_]) != null) {
                            bool_46_ |= Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_].method3272(i_54_, 0);
                            bool_45_ |= Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_].method3271(i_54_, 14);
                            bool_48_ |= Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_50_].method3267((byte) -92, i_54_);
                            bool_47_ |= class17_51_.aBoolean242;
                        }
                        if ((class17_51_.aBoolean241 || Class28.aBoolean5002) && i_53_ != -1 && i_53_ < (class17_51_.anIntArray237).length) {
                            Class348_Sub23_Sub4.anIntArray9050[i_50_] = class17_51_.anIntArray267[i_52_];
                            Class67.anIntArray4648[i_50_] = class182s[i_50_].anInt2456;
                            int i_55_ = class17_51_.anIntArray237[i_53_];
                            Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_] = class87.method839(i_55_ >>> 16, 3);
                            i_55_ &= 0xffff;
                            Class183.anIntArray2466[i_50_] = i_55_;
                            if ((Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_]) != null) {
                                bool_46_ |= Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_].method3272(i_55_, 0);
                                bool_45_ |= Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_].method3271(i_55_, 14);
                                bool_48_ |= Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_].method3267((byte) -99, i_55_);
                            }
                        } else {
                            Class348_Sub23_Sub4.anIntArray9050[i_50_] = 0;
                            Class67.anIntArray4648[i_50_] = 0;
                            Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_50_] = null;
                            Class183.anIntArray2466[i_50_] = -1;
                        }
                    }
                }
            }
            int i_56_ = -1;
            int i_57_ = -1;
            int i_58_ = 0;
            Class348_Sub42_Sub17 class348_sub42_sub17 = null;
            Class348_Sub42_Sub17 class348_sub42_sub17_59_ = null;
            int i_60_ = -1;
            int i_61_ = -1;
            int i_62_ = 0;
            Class348_Sub42_Sub17 class348_sub42_sub17_63_ = null;
            Class348_Sub42_Sub17 class348_sub42_sub17_64_ = null;
            if (bool_44_) {
                i_43_ |= 0x20;
                if (class17_36_ != null) {
                    i_56_ = class17_36_.anIntArray237[i_35_];
                    int i_65_ = i_56_ >>> 16;
                    i_56_ &= 0xffff;
                    class348_sub42_sub17 = class87.method839(i_65_, 3);
                    if (class348_sub42_sub17 != null) {
                        bool_46_ |= class348_sub42_sub17.method3272(i_56_, 0);
                        bool_45_ |= class348_sub42_sub17.method3271(i_56_, 14);
                        bool_48_ |= class348_sub42_sub17.method3267((byte) -125, i_56_);
                        bool_47_ |= class17_36_.aBoolean242;
                    }
                    if ((class17_36_.aBoolean241 || Class28.aBoolean5002) && i_38_ != -1 && (class17_36_.anIntArray237.length > i_38_)) {
                        i_57_ = class17_36_.anIntArray237[i_38_];
                        i_58_ = class17_36_.anIntArray267[i_35_];
                        int i_66_ = i_57_ >>> 16;
                        if (i_65_ == i_66_) class348_sub42_sub17_59_ = class348_sub42_sub17;
                        else class348_sub42_sub17_59_ = class87.method839(i_66_, 3);
                        i_57_ &= 0xffff;
                        if (class348_sub42_sub17_59_ != null) {
                            bool_46_ |= class348_sub42_sub17_59_.method3272(i_57_, 0);
                            bool_45_ |= class348_sub42_sub17_59_.method3271(i_57_, 14);
                            bool_48_ |= class348_sub42_sub17_59_.method3267((byte) -122, i_57_);
                        }
                    }
                }
                if (class17 != null) {
                    i_60_ = class17.anIntArray237[i_39_];
                    int i_67_ = i_60_ >>> 16;
                    class348_sub42_sub17_63_ = class87.method839(i_67_, 3);
                    i_60_ &= 0xffff;
                    if (class348_sub42_sub17_63_ != null) {
                        bool_46_ |= class348_sub42_sub17_63_.method3272(i_60_, 0);
                        bool_45_ |= class348_sub42_sub17_63_.method3271(i_60_, 14);
                        bool_48_ |= class348_sub42_sub17_63_.method3267((byte) -102, i_60_);
                        bool_47_ |= class17.aBoolean242;
                    }
                    if ((class17.aBoolean241 || Class28.aBoolean5002) && i_37_ != -1 && class17.anIntArray237.length > i_37_) {
                        i_62_ = class17.anIntArray267[i_39_];
                        i_61_ = class17.anIntArray237[i_37_];
                        int i_68_ = i_61_ >>> 16;
                        if (i_68_ == i_67_) class348_sub42_sub17_64_ = class348_sub42_sub17_63_;
                        else class348_sub42_sub17_64_ = class87.method839(i_68_, 3);
                        i_61_ &= 0xffff;
                        if (class348_sub42_sub17_64_ != null) {
                            bool_46_ |= class348_sub42_sub17_64_.method3272(i_61_, 0);
                            bool_45_ |= class348_sub42_sub17_64_.method3271(i_61_, 14);
                            bool_48_ |= class348_sub42_sub17_64_.method3267((byte) -104, i_61_);
                        }
                    }
                }
                if (bool_46_) i_43_ |= 0x80;
                if (bool_45_) i_43_ |= 0x100;
                if (bool_47_) i_43_ |= 0x200;
                if (bool_48_) i_43_ |= 0x400;
            }
            long l = var_ha.anInt4567 << 16 | this.id;
            Class64 class64;
            synchronized (this.loader.aClass60_3590) {
                class64 = (Class64) this.loader.aClass60_3590.method583(l, 80);
            }
            Class225 class225 = null;
            if (this.basId != -1) class225 = class261.method1983(this.basId, 32);
            if (class64 == null || i_43_ != (i_43_ & class64.ua())) {
                if (class64 != null) i_43_ |= class64.ua();
                int i_69_ = i_43_;
                boolean bool_70_ = false;
                synchronized (this.loader.aClass45_3576) {
                    for (int i_71_ = 0; (models.length > i_71_); i_71_++) {
                        if (models[i_71_] != -1 && !(this.loader.aClass45_3576.method420(-10499, models[i_71_], 0))) bool_70_ = true;
                    }
                }
                if (bool_70_) return null;
                Class124[] class124s = new Class124[models.length];
                for (int i_72_ = 0; i_72_ < models.length; i_72_++) {
                    if (models[i_72_] != -1) {
                        synchronized (this.loader.aClass45_3576) {
                            class124s[i_72_] = Class300.method2277(0, (this.loader.aClass45_3576), models[i_72_], -1);
                        }
                        if (class124s[i_72_] != null) {
                            if (class124s[i_72_].anInt1830 < 13) class124s[i_72_].method1092(2, 115);
                            if (translations != null && translations[i_72_] != null) class124s[i_72_].method1099((byte) 44, translations[i_72_][2], translations[i_72_][0], translations[i_72_][1]);
                        }
                    }
                }
                if (class225 != null && class225.anIntArrayArray2939 != null) {
                    for (int i_73_ = 0; (i_73_ < class225.anIntArrayArray2939.length); i_73_++) {
                        if (class124s.length > i_73_ && class124s[i_73_] != null) {
                            int i_74_ = 0;
                            int i_75_ = 0;
                            int i_76_ = 0;
                            int i_77_ = 0;
                            int i_78_ = 0;
                            int i_79_ = 0;
                            if ((class225.anIntArrayArray2939[i_73_]) != null) {
                                i_78_ = ((class225.anIntArrayArray2939[i_73_][4]) << 3);
                                i_79_ = ((class225.anIntArrayArray2939[i_73_][5]) << 3);
                                i_75_ = (class225.anIntArrayArray2939[i_73_][1]);
                                i_76_ = (class225.anIntArrayArray2939[i_73_][2]);
                                i_77_ = ((class225.anIntArrayArray2939[i_73_][3]) << 3);
                                i_74_ = (class225.anIntArrayArray2939[i_73_][0]);
                            }
                            if (i_77_ != 0 || i_78_ != 0 || i_79_ != 0) class124s[i_73_].method1107(6875, i_78_, i_79_, i_77_);
                            if (i_74_ != 0 || i_75_ != 0 || i_76_ != 0) class124s[i_73_].method1099((byte) 93, i_76_, i_74_, i_75_);
                        }
                    }
                }
                Class124 class124;
                if (class124s.length == 1) class124 = class124s[0];
                else class124 = new Class124(class124s, class124s.length);
                if (recol_s != null) i_69_ |= 0x4000;
                if (retex_s != null) i_69_ |= 0x8000;
                if (colourScale != 0) i_69_ |= 0x80000;
                class64 = var_ha.method3625(class124, i_69_, (this.loader.anInt3593), 64 + ambient, 850 + diffusion);
                if (recol_s != null) {
                    for (int i_80_ = 0; (i_80_ < recol_s.length); i_80_++) {
                        if (recol_d_palette == null || recol_d_palette.length <= i_80_) class64.ia(recol_s[i_80_], recol_d[i_80_]);
                        else class64.ia(recol_s[i_80_], (Class348_Sub42_Sub3.aShortArray9502[recol_d_palette[i_80_] & 0xff]));
                    }
                }
                if (retex_s != null) {
                    for (int i_81_ = 0; retex_s.length > i_81_; i_81_++)
                        class64.aa(retex_s[i_81_], retex_d[i_81_]);
                }
                if (colourScale != 0) class64.method624(colourHue, colourSaturation, colourLightness, colourScale & 0xff);
                class64.s(i_43_);
                synchronized (this.loader.aClass60_3590) {
                    this.loader.aClass60_3590.method582(class64, this.id | var_ha.anInt4567 << 16, (byte) -125);
                }
            }
            Class64 class64_82_ = class64.method614((byte) 4, i_43_, true);
            boolean bool_83_ = false;
            if (is != null) {
                for (int i_84_ = 0; i_84_ < 12; i_84_++) {
                    if (is[i_84_] != -1) bool_83_ = true;
                }
            }
            if (!bool_44_ && !bool_83_) return class64_82_;
            Class101[] class101s = null;
            if (class225 != null) class101s = class225.method1618(var_ha, 0);
            if (bool_83_ && class101s != null) {
                for (int i_85_ = 0; i_85_ < 12; i_85_++) {
                    if (class101s[i_85_] != null) class64_82_.method610(class101s[i_85_], 1 << i_85_, true);
                }
            }
            int i_86_ = 0;
            int i_87_ = 1;
            while (i_86_ < i_49_) {
                if ((Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_86_]) != null)
                    class64_82_.method603((byte) -55, -1 + Class67.anIntArray4648[i_86_], null, i_87_, Class90.anIntArray1518[i_86_], Class183.anIntArray2466[i_86_], (Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_86_]), 0, (Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_86_]), (Class163.aClass17Array2169[i_86_].aBoolean242), Class348_Sub23_Sub4.anIntArray9050[i_86_]);
                i_86_++;
                i_87_ <<= 1;
            }
            if (bool_83_) {
                for (int i_88_ = 0; i_88_ < 12; i_88_++) {
                    if (is[i_88_] != -1) {
                        int i_89_ = -i + is[i_88_];
                        i_89_ &= 0x3fff;
                        Class101 class101 = var_ha.method3654();
                        class101.method895(i_89_);
                        class64_82_.method610(class101, 1 << i_88_, false);
                    }
                }
            }
            if (bool_83_ && class101s != null) {
                for (int i_90_ = 0; i_90_ < 12; i_90_++) {
                    if (class101s[i_90_] != null) class64_82_.method610(class101s[i_90_], 1 << i_90_, false);
                }
            }
            if (class348_sub42_sub17 != null && class348_sub42_sub17_63_ != null)
                class64_82_.method625(class348_sub42_sub17_59_, i_58_, (byte) 122, i_62_, class348_sub42_sub17_63_, i_56_, i_60_, -1 + i_41_, class348_sub42_sub17, (class17_36_.aBoolean242 | class17.aBoolean242), i_57_, class348_sub42_sub17_64_, -1 + i_34_, class17_36_.aBooleanArray263, i_61_);
            else if (class348_sub42_sub17 != null) class64_82_.method617(i_56_, i_58_, class348_sub42_sub17_59_, 0, class348_sub42_sub17, bool, class17_36_.aBoolean242, i_57_, i_41_ + -1);
            else if (class348_sub42_sub17_63_ != null) class64_82_.method617(i_60_, i_62_, class348_sub42_sub17_64_, 0, class348_sub42_sub17_63_, false, class17.aBoolean242, i_61_, i_34_ - 1);
            for (int i_91_ = 0; i_49_ > i_91_; i_91_++) {
                Class318_Sub1_Sub3_Sub1.aClass348_Sub42_Sub17Array10010[i_91_] = null;
                Class348_Sub42_Sub17.aClass348_Sub42_Sub17Array9672[i_91_] = null;
                Class163.aClass17Array2169[i_91_] = null;
            }
            if (scaleH != 128 || scaleV != 128) class64_82_.O(scaleH, scaleV, scaleH);
            class64_82_.s(i_40_);
            return class64_82_;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("bb.F(" + i + ',' + (class182s != null ? "{...}" : "null") + ',' + (class87 != null ? "{...}" : "null") + ',' + bool + ',' + (class17 != null ? "{...}" : "null") + ',' + i_34_ + ',' + (class261 != null ? "{...}" : "null") + ',' + i_35_ + ',' + (class17_36_ != null ? "{...}" : "null") + ',' + (interface17 != null ? "{...}" : "null") + ',' + (var_ha != null ? "{...}" : "null") + ',' + i_37_ + ',' + (is != null ? "{...}" : "null") + ',' + i_38_ + ',' + i_39_ + ',' + i_40_ + ',' + i_41_ + ')'));
        }
    }

    final String method801(byte i, int i_92_, String string) {
        anInt1378++;
        if (params == null) return string;
        if (i != 17) method801((byte) -115, -68, null);
        StringNode StringNode = ((StringNode) params.method3480(i_92_, i + -6025));
        if (StringNode == null) return string;
        return StringNode.aString7211;
    }

    static final boolean method802(int i, int i_93_, boolean bool) {
        if (bool != true) return false;
        anInt1332++;
        if (i_93_ < 0 || i < 0 || i_93_ >= Class348_Sub33.aByteArrayArrayArray6962[1].length || i >= Class348_Sub33.aByteArrayArrayArray6962[1][i_93_].length) return false;
        return (Class348_Sub33.aByteArrayArrayArray6962[1][i_93_][i] & 0x2) != 0;
    }

    final Class64 method803(Interface17 interface17, ha var_ha, int i, int i_94_, Class17 class17, Class87 class87, int i_95_, int i_96_, int i_97_) {
        try {
            anInt1389++;
            if (this.multinpcs != null) {
                NPCType class79_98_ = method794(interface17, -1);
                if (class79_98_ == null) return null;
                return class79_98_.method803(interface17, var_ha, i, i_94_, class17, class87, i_95_, 104, i_97_);
            }
            if (i_96_ <= 98) method796(null, -10);
            if (headModels == null) return null;
            int i_99_ = i_97_;
            if (class17 != null && i_95_ != -1) i_99_ |= class17.method263(i_94_, 97, i_95_, true);
            Class64 class64;
            synchronized (this.loader.aClass60_3592) {
                class64 = ((Class64) (this.loader.aClass60_3592.method583(var_ha.anInt4567 << 16 | this.id, 64)));
            }
            if (class64 == null || i_99_ != (class64.ua() & i_99_)) {
                if (class64 != null) i_99_ |= class64.ua();
                int i_100_ = i_99_;
                boolean bool = false;
                synchronized (this.loader.aClass45_3576) {
                    for (int i_101_ = 0; (headModels.length > i_101_); i_101_++) {
                        if (!this.loader.aClass45_3576.method420(-10499, headModels[i_101_], 0)) bool = true;
                    }
                }
                if (bool) return null;
                Class124[] class124s = new Class124[headModels.length];
                synchronized (this.loader.aClass45_3576) {
                    for (int i_102_ = 0; (headModels.length > i_102_); i_102_++)
                        class124s[i_102_] = Class300.method2277(0, (this.loader.aClass45_3576), headModels[i_102_], -1);
                }
                for (int i_103_ = 0; (i_103_ < headModels.length); i_103_++) {
                    if (class124s[i_103_] != null && class124s[i_103_].anInt1830 < 13) class124s[i_103_].method1092(2, 66);
                }
                Class124 class124;
                if (class124s.length == 1) class124 = class124s[0];
                else class124 = new Class124(class124s, class124s.length);
                if (recol_s != null) i_100_ |= 0x4000;
                if (retex_s != null) i_100_ |= 0x8000;
                if (colourScale != 0) i_100_ |= 0x80000;
                class64 = var_ha.method3625(class124, i_100_, (this.loader.anInt3593), 64, 768);
                if (recol_s != null) {
                    for (int i_104_ = 0; (recol_s.length > i_104_); i_104_++) {
                        if (recol_d_palette != null && recol_d_palette.length > i_104_) class64.ia(recol_s[i_104_], (Class348_Sub42_Sub3.aShortArray9502[0xff & recol_d_palette[i_104_]]));
                        else class64.ia(recol_s[i_104_], recol_d[i_104_]);
                    }
                }
                if (retex_s != null) {
                    for (int i_105_ = 0; retex_s.length > i_105_; i_105_++)
                        class64.aa(retex_s[i_105_], retex_d[i_105_]);
                }
                if (colourScale != 0) class64.method624(colourHue, colourSaturation, colourLightness, colourScale & 0xff);
                class64.s(i_99_);
                synchronized (this.loader.aClass60_3592) {
                    this.loader.aClass60_3592.method582(class64, var_ha.anInt4567 << 16 | this.id, (byte) -96);
                }
            }
            if (class17 != null && i_95_ != -1) class64 = class17.method269(-9, class64, i_94_, i, i_99_, i_95_);
            class64.s(i_97_);
            return class64;
        } catch (RuntimeException runtimeexception) {
            throw Class348_Sub17.method2929(runtimeexception, ("bb.H(" + (interface17 != null ? "{...}" : "null") + ',' + (var_ha != null ? "{...}" : "null") + ',' + i + ',' + i_94_ + ',' + (class17 != null ? "{...}" : "null") + ',' + (class87 != null ? "{...}" : "null") + ',' + i_95_ + ',' + i_96_ + ',' + i_97_ + ')'));
        }
    }

    public static void method804(int i) {
        aClass105_1365 = null;
        aClient1367 = null;
        if (i != -3752) anInt1387 = 14;
    }

    final int method805(int i, int i_106_, int i_107_) {
        anInt1326++;
        if (params == null) return i;
        if (i_107_ <= 12) method805(46, 116, 126);
        IntNode IntNode = (IntNode) params.method3480(i_106_, -6008);
        if (IntNode == null) return i;
        return IntNode.anInt6976;
    }

    public NPCType() {
        scaleH = 128;
        this.cursor1Op = -1;
        this.pickSizeShift = 0;
        multinpcVarp = -1;
        this.renderHighPriority = false;
        this.mobilisingArmiesIcon = -1;
        this.spawnDirection = (byte) 4;
        this.soundVolume = 255;
        this.soundRateMin = 256;
        this.healthBarSprite = -1;
        this.headIcon = -1;
        this.cursor2 = -1;
        this.soundRateMax = 256;
        this.shadowOuterColour = (short) 0;
        this.op = new String[5];
        scaleV = 128;
        this.shadowOuterAlpha = (byte) -96;
        this.isFollower = false;
        this.readySound = -1;
        this.aByte107 = (byte) -1;
        this.aBoolean503 = false;
        this.mapElement = -1;
        this.name = "null";
        this.timerbarSprite = -1;
        this.cursor1 = -1;
        this.movementCapabilities = (byte) 0;
        multinpcVarbit = -1;
        this.height = -1;
        this.crawl = true;
        this.shadowInnerAlpha = (byte) -16;
        this.basId = -1;
        ambient = 0;
        this.soundRangeMax = 0;
        this.walkSound = -1;
        this.runSound = -1;
        this.combatLevel = -1;
        this.yawSpeed = 32;
        colourScale = (byte) 0;
        this.cursor2Op = -1;
        this.crawlSound = -1;
        this.hasShadow = true;
        this.anInt1399 = 1;
        this.attackCursor = -1;
        this.interactive = true;
        this.displayOnMiniMap = true;
        diffusion = 0;
    }
}
