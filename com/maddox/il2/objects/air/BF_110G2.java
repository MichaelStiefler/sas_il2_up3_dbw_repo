// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_110G2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_110, PaintSchemeBMPar03, NetAircraft

public class BF_110G2 extends com.maddox.il2.objects.air.BF_110
{

    public BF_110G2()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f1 < -19F)
            {
                f1 = -19F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            float f2;
            if(f1 < 0.0F)
                f2 = com.maddox.il2.objects.air.BF_110G2.cvt(f1, -19F, 0.0F, 20F, 30F);
            else
            if(f1 < 12F)
                f2 = com.maddox.il2.objects.air.BF_110G2.cvt(f1, 0.0F, 12F, 30F, 35F);
            else
                f2 = com.maddox.il2.objects.air.BF_110G2.cvt(f1, 12F, 30F, 35F, 40F);
            if(f < 0.0F)
            {
                if(f < -f2)
                {
                    f = -f2;
                    flag = false;
                }
            } else
            if(f > f2)
            {
                f = f2;
                flag = false;
            }
            if(java.lang.Math.abs(f) > 17.8F && java.lang.Math.abs(f) < 25F && f1 < -12F)
                flag = false;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_110G2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf-110");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-110G-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-110G-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_110G.class, com.maddox.il2.objects.air.CockpitBF_110G_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        com.maddox.il2.objects.air.BF_110G2.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 
            10, 10, 9, 9, 9, 3, 3, 3, 3, 3, 
            3, 0, 0, 1, 9, 9, 9, 9, 2, 2, 
            2, 2, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.BF_110G2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_CANNON01", "_CANNON02", "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", 
            "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", 
            "_ExternalBomb06", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalRock03", "_ExternalRock04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "m1", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, 
            null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sc500", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC500", "BombGunSC500", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2ab500", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB500", "BombGunAB500", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sd500", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSD500", "BombGunSD500", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "4sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sc2504sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2ab2504sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB250", "BombGunAB250", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sc5004sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC500", "BombGunSC500", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2ab5004sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB500", "BombGunAB500", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "2sd5004sc50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSD500", "BombGunSD500", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "m5", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_WfrGr21Dual", null, null, "PylonRO_WfrGr21Dual", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "m1m3", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", "PylonBF110R3", null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "m1m5", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, 
            null, "MGunMG15120k 200", "MGunMG15120k 200", null, "PylonRO_WfrGr21Dual", null, null, "PylonRO_WfrGr21Dual", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r4", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", null, null, null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, 
            null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r1r7", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", null, null, null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, 
            null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGk 350", "MGunMG15120MGk 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3m1", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, 
            null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3m2sc250", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3m2ab250", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3m2sc500", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC500", "BombGunSC500", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3m2sd500", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSD500", "BombGunSD500", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3r7m1", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, 
            null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3r7m2sc250", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3r7m2ab250", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r3r7m5", new java.lang.String[] {
            null, null, null, null, null, null, "MGunMG15120MGk 350", "MGunMG15120MGk 300", "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_WfrGr21Dual", null, null, "PylonRO_WfrGr21Dual", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", "RocketGunWfrGr21", "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r4_", new java.lang.String[] {
            null, null, null, null, null, null, null, null, "MGunMK108k 135", "MGunMK108k 135", 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, 
            null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m1", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, 
            null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m2ab250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m3", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m2m3sc", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "r7m2m3ab", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, 
            "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB250", "BombGunAB250", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "BombGunSC50", null, null, null, null, null, null, null, null, null, 
            null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D"
        });
        com.maddox.il2.objects.air.BF_110G2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
