// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Artillery.java

package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.TgtVehicle;

// Referenced classes of package com.maddox.il2.objects.vehicles.artillery:
//            ArtilleryGeneric, AAA, STank

public abstract class Artillery
{
    public static class Battery88mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Battery88mm()
        {
        }
    }

    public static class Battery85mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Battery85mm()
        {
        }
    }

    public static class Battery75mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Battery75mm()
        {
        }
    }

    public static class WWIBattery75mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public WWIBattery75mm()
        {
        }
    }

    public static class Arty_Barrage extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Arty_Barrage()
        {
        }
    }

    public static class Zenit3K extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Zenit3K()
        {
        }
    }

    public static class Zenit85mm_1939 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Zenit85mm_1939()
        {
        }
    }

    public static class Flak18_88mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Flak18_88mm()
        {
        }
    }

    public static class Type88_75mm_JA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Type88_75mm_JA()
        {
        }
    }

    public static class Bofors_40mm_UK extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Bofors_40mm_UK()
        {
        }
    }

    public static class Bofors_40mm_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Bofors_40mm_US()
        {
        }
    }

    public static class StBofors_40mm_UK extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public StBofors_40mm_UK()
        {
        }
    }

    public static class StBofors_40mm_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public StBofors_40mm_US()
        {
        }
    }

    public static class Maxime extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Maxime()
        {
        }
    }

    public static class Maxime4 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Maxime4()
        {
        }
    }

    public static class Zenit25mm_1940 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Zenit25mm_1940()
        {
        }
    }

    public static class Zenit61K extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Zenit61K()
        {
        }
    }

    public static class _50calMG_water_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public _50calMG_water_US()
        {
        }
    }

    public static class Flak30_20mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Flak30_20mm()
        {
        }
    }

    public static class Flak38_20mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Flak38_20mm()
        {
        }
    }

    public static class Flak18_37mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Flak18_37mm()
        {
        }
    }

    public static class Type98_20mm_JA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Type98_20mm_JA()
        {
        }
    }

    public static class Twin25mm_JA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Twin25mm_JA()
        {
        }
    }

    public static class Trip25mm_JA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public Trip25mm_JA()
        {
        }
    }

    public static class DShKAA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.AAA
    {

        public DShKAA()
        {
        }
    }

    public static class ML20 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public ML20()
        {
        }
    }

    public static class M30_122mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public M30_122mm()
        {
        }
    }

    public static class Howitzer_150mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Howitzer_150mm()
        {
        }
    }

    public static class M2A1_105mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public M2A1_105mm()
        {
        }
    }

    public static class Type91_105mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Type91_105mm()
        {
        }
    }

    public static class ATG38_45mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public ATG38_45mm()
        {
        }
    }

    public static class ZIS3 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public ZIS3()
        {
        }
    }

    public static class M5_75mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public M5_75mm()
        {
        }
    }

    public static class PaK35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public PaK35()
        {
        }
    }

    public static class PaK38 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public PaK38()
        {
        }
    }

    public static class PaK40 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public PaK40()
        {
        }
    }

    public static class Type94_37mm_JA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Type94_37mm_JA()
        {
        }
    }

    public static class Type38_75mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public Type38_75mm()
        {
        }
    }

    public static class BunkerA_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerA_gun()
        {
        }
    }

    public static class BunkerA2_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerA2_gun()
        {
        }
    }

    public static class BunkerD_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerD_gun()
        {
        }
    }

    public static class BunkerS2_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerS2_gun()
        {
        }
    }

    public static class MG42 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public MG42()
        {
        }
    }

    public static class BunkerG1_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerG1_gun()
        {
        }
    }

    public static class BunkerR1_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerR1_gun()
        {
        }
    }

    public static class BunkerR2_gun extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public BunkerR2_gun()
        {
        }
    }

    public static class DShK extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public DShK()
        {
        }
    }

    public static class _50calMG_US_M2HB extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public _50calMG_US_M2HB()
        {
        }
    }

    public static class HoRo_MG extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public HoRo_MG()
        {
        }
    }

    public static class PzIIF extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIIF()
        {
        }
    }

    public static class LT_35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_35()
        {
        }
    }

    public static class LT_35Ro extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_35Ro()
        {
        }
    }

    public static class LT_35Sl extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_35Sl()
        {
        }
    }

    public static class LT_38 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_38()
        {
        }
    }

    public static class LT_38Sl extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_38Sl()
        {
        }
    }

    public static class LT_40 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_40()
        {
        }
    }

    public static class LT_40MG extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public LT_40MG()
        {
        }
    }

    public static class HaGo extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public HaGo()
        {
        }
    }

    public static class HaGoRadio extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public HaGoRadio()
        {
        }
    }

    public static class ChiHa extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ChiHa()
        {
        }
    }

    public static class ChiHa_alt extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ChiHa_alt()
        {
        }
    }

    public static class PzIIIG extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIIIG()
        {
        }
    }

    public static class PzIIIJ extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIIIJ()
        {
        }
    }

    public static class PzIIIM extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIIIM()
        {
        }
    }

    public static class PzIIIN extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIIIN()
        {
        }
    }

    public static class TuranI extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public TuranI()
        {
        }
    }

    public static class TuranII extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public TuranII()
        {
        }
    }

    public static class PzIVE extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIVE()
        {
        }
    }

    public static class PzIVF extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIVF()
        {
        }
    }

    public static class PzIVF2 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIVF2()
        {
        }
    }

    public static class PzIVJ extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzIVJ()
        {
        }
    }

    public static class PzVA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzVA()
        {
        }
    }

    public static class PzV_II extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzV_II()
        {
        }
    }

    public static class PzVIE extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzVIE()
        {
        }
    }

    public static class PzVIB extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public PzVIB()
        {
        }
    }

    public static class Maus extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Maus()
        {
        }
    }

    public static class ZrinyiII extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ZrinyiII()
        {
        }
    }

    public static class Hetzer extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Hetzer()
        {
        }
    }

    public static class Jagdpanther extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Jagdpanther()
        {
        }
    }

    public static class Ferdinand extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Ferdinand()
        {
        }
    }

    public static class StuGIIIG extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public StuGIIIG()
        {
        }
    }

    public static class StuGIV extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public StuGIV()
        {
        }
    }

    public static class MarderIII extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public MarderIII()
        {
        }
    }

    public static class Karl_600mm extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Karl_600mm()
        {
        }
    }

    public static class HoRo extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public HoRo()
        {
        }
    }

    public static class SdKfz251 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SdKfz251()
        {
        }
    }

    public static class DemagFlak extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public DemagFlak()
        {
        }
    }

    public static class SdKfz6Flak37 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SdKfz6Flak37()
        {
        }
    }

    public static class OpelBlitzMaultierAA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public OpelBlitzMaultierAA()
        {
        }
    }

    public static class Nimrod extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Nimrod()
        {
        }
    }

    public static class Wirbelwind extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Wirbelwind()
        {
        }
    }

    public static class Coelian extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Coelian()
        {
        }
    }

    public static class Tatra_OA30 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public Tatra_OA30()
        {
        }
    }

    public static class OpelBlitzMaultierRocket extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public OpelBlitzMaultierRocket()
        {
        }
    }

    public static class BA_64 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public BA_64()
        {
        }
    }

    public static class BA_10 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public BA_10()
        {
        }
    }

    public static class BA_6 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public BA_6()
        {
        }
    }

    public static class FAIM extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public FAIM()
        {
        }
    }

    public static class LVT_2 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public LVT_2()
        {
        }
    }

    public static class M8_Greyhound extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public M8_Greyhound()
        {
        }
    }

    public static class BT7 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public BT7()
        {
        }
    }

    public static class T40S extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T40S()
        {
        }
    }

    public static class T60 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T60()
        {
        }
    }

    public static class _7TP extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public _7TP()
        {
        }
    }

    public static class M3A1Stuart extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M3A1Stuart()
        {
        }
    }

    public static class M5A1Stuart extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M5A1Stuart()
        {
        }
    }

    public static class T70M extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T70M()
        {
        }
    }

    public static class T26_Early extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T26_Early()
        {
        }
    }

    public static class T26_Late extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T26_Late()
        {
        }
    }

    public static class ValentineII extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ValentineII()
        {
        }
    }

    public static class MatildaMkII_UK extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public MatildaMkII_UK()
        {
        }
    }

    public static class MatildaMkII_AU extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public MatildaMkII_AU()
        {
        }
    }

    public static class M4A2 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M4A2()
        {
        }
    }

    public static class M4A2_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M4A2_US()
        {
        }
    }

    public static class M4A2_76W extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M4A2_76W()
        {
        }
    }

    public static class M4A2_76W_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M4A2_76W_US()
        {
        }
    }

    public static class T34 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T34()
        {
        }
    }

    public static class T44 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T44()
        {
        }
    }

    public static class T34_85 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T34_85()
        {
        }
    }

    public static class T28 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T28()
        {
        }
    }

    public static class KV1 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public KV1()
        {
        }
    }

    public static class IS2 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public IS2()
        {
        }
    }

    public static class IS3 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public IS3()
        {
        }
    }

    public static class T35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public T35()
        {
        }
    }

    public static class ZSU37 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ZSU37()
        {
        }
    }

    public static class SU76M extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SU76M()
        {
        }
    }

    public static class SU85 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SU85()
        {
        }
    }

    public static class SU100 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SU100()
        {
        }
    }

    public static class ISU152 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ISU152()
        {
        }
    }

    public static class Katyusha extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public Katyusha()
        {
        }
    }

    public static class StudebeckerRocket extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle
    {

        public StudebeckerRocket()
        {
        }
    }

    public static class ZIS5_AA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ZIS5_AA()
        {
        }
    }

    public static class MaximeGAZ extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public MaximeGAZ()
        {
        }
    }

    public static class M3A1_APC extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M3A1_APC()
        {
        }
    }

    public static class M16 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M16()
        {
        }
    }

    public static class M16_US extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M16_US()
        {
        }
    }

    public static class GBBazooka extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public GBBazooka()
        {
        }
    }

    public static class USBazooka extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public USBazooka()
        {
        }
    }

    public static class GBInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public GBInfantry()
        {
        }
    }

    public static class RusBazooka extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public RusBazooka()
        {
        }
    }

    public static class USInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public USInfantry()
        {
        }
    }

    public static class AxisBazooka extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public AxisBazooka()
        {
        }
    }

    public static class JapInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public JapInfantry()
        {
        }
    }

    public static class RusInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public RusInfantry()
        {
        }
    }

    public static class AxisInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public AxisInfantry()
        {
        }
    }

    public static class CoastBunker_PTO extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public CoastBunker_PTO()
        {
        }
    }

    public static class AxisPnzrInfantry extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public AxisPnzrInfantry()
        {
        }
    }

    public static class CoastBunker_ETO1 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public CoastBunker_ETO1()
        {
        }
    }

    public static class CoastBunker_ETO2 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
    {

        public CoastBunker_ETO2()
        {
        }
    }

    public static class M3Lee extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M3Lee()
        {
        }
    }

    public static class Crusader extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Crusader()
        {
        }
    }

    public static class Fiat_M13 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Fiat_M13()
        {
        }
    }

    public static class Fiat_M15 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Fiat_M15()
        {
        }
    }

    public static class Fiat_M40 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Fiat_M40()
        {
        }
    }

    public static class Fiat_M42 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Fiat_M42()
        {
        }
    }

    public static class SomuaS35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public SomuaS35()
        {
        }
    }

    public static class RenaultR35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public RenaultR35()
        {
        }
    }

    public static class VickersMKVI extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public VickersMKVI()
        {
        }
    }

    public static class HotchkissH35 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public HotchkissH35()
        {
        }
    }

    public static class Bishop extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public Bishop()
        {
        }
    }

    public static class StuartM3a1 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public StuartM3a1()
        {
        }
    }

    public static class ShermanM4 extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ShermanM4()
        {
        }
    }

    public static class CromwellMkIV extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public CromwellMkIV()
        {
        }
    }

    public static class CometMkI extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public CometMkI()
        {
        }
    }

    public static class ChurchillMkVII extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ChurchillMkVII()
        {
        }
    }

    public static class ChurchillMkIVA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ChurchillMkIVA()
        {
        }
    }

    public static class ChallengerMkI extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public ChallengerMkI()
        {
        }
    }

    public static class CrusaderAAA extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtTank, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public CrusaderAAA()
        {
        }
    }

    public static class M3_75mm_GMC extends com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric
        implements com.maddox.il2.ai.ground.TgtVehicle, com.maddox.il2.ai.ground.TgtFlak, com.maddox.il2.objects.vehicles.artillery.STank
    {

        public M3_75mm_GMC()
        {
        }
    }


    public Artillery()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Battery88mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Battery85mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Battery75mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$WWIBattery75mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Arty_Barrage.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ChallengerMkI.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ChurchillMkIVA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ChurchillMkVII.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CometMkI.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CromwellMkIV.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ShermanM4.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StuartM3a1.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Bishop.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CrusaderAAA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$HotchkissH35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$VickersMKVI.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$RenaultR35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SomuaS35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Fiat_M40.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Fiat_M42.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Fiat_M15.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Fiat_M13.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Crusader.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M3Lee.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CoastBunker_ETO2.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CoastBunker_ETO1.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$AxisPnzrInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$CoastBunker_PTO.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$AxisInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$RusInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$JapInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$AxisBazooka.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$USInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$RusBazooka.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$GBInfantry.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$USBazooka.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$GBBazooka.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Zenit3K.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Zenit85mm_1939.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Flak18_88mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Type88_75mm_JA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Bofors_40mm_UK.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Bofors_40mm_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StBofors_40mm_UK.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StBofors_40mm_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Maxime.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Maxime4.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Zenit25mm_1940.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Zenit61K.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$_50calMG_water_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Flak30_20mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Flak38_20mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Flak18_37mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Type98_20mm_JA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Twin25mm_JA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Trip25mm_JA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$DShKAA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ML20.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M30_122mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Howitzer_150mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M2A1_105mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Type91_105mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ATG38_45mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ZIS3.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M5_75mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PaK35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PaK38.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PaK40.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Type94_37mm_JA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Type38_75mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerA_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerA2_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerD_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerS2_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$MG42.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerG1_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerR1_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BunkerR2_gun.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$DShK.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$_50calMG_US_M2HB.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$HoRo_MG.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIIF.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_35Ro.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_35Sl.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_38.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_38Sl.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_40.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LT_40MG.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$HaGo.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$HaGoRadio.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ChiHa.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ChiHa_alt.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIIIG.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIIIJ.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIIIM.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIIIN.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$TuranI.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$TuranII.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIVE.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIVF.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIVF2.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzIVJ.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzVA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzV_II.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzVIE.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$PzVIB.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Maus.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ZrinyiII.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Hetzer.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Jagdpanther.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Ferdinand.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StuGIIIG.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StuGIV.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$MarderIII.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Karl_600mm.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$HoRo.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SdKfz251.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$DemagFlak.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SdKfz6Flak37.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$OpelBlitzMaultierAA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Nimrod.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Wirbelwind.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Coelian.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Tatra_OA30.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$OpelBlitzMaultierRocket.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BA_64.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BA_10.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BA_6.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$FAIM.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$LVT_2.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M8_Greyhound.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$BT7.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T40S.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T60.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$_7TP.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M3A1Stuart.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M5A1Stuart.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T70M.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T26_Early.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T26_Late.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ValentineII.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$MatildaMkII_UK.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$MatildaMkII_AU.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M4A2.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M4A2_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M4A2_76W.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M4A2_76W_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T34.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T44.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T34_85.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T28.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$KV1.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$IS2.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$IS3.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$T35.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ZSU37.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SU76M.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SU85.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$SU100.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ISU152.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$Katyusha.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$StudebeckerRocket.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$ZIS5_AA.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$MaximeGAZ.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M3A1_APC.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M16.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M16_US.class);
        new ArtilleryGeneric.SPAWN(com.maddox.il2.objects.vehicles.artillery.Artillery$M3_75mm_GMC.class);
    }
}
