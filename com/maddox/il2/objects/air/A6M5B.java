// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A6M5B.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A6M, PaintSchemeFMPar02, PaintSchemeFCSPar02, NetAircraft

public class A6M5B extends com.maddox.il2.objects.air.A6M
{

    public A6M5B()
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmorg"))
            {
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(44F, 46F), shot);
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                com.maddox.il2.objects.air.A6M5B.debugprintln(this, "*** Armor Glass: Hit..");
                if(shot.power <= 0.0F)
                {
                    com.maddox.il2.objects.air.A6M5B.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        doRicochetBack(shot);
                }
                return;
            }
            if(s.startsWith("xxammor"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                {
                    debuggunnery("Armament: Machine Gun Chain Broken..");
                    FM.AS.setJamBullets(0, 0);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int i = s.charAt(6) - 49;
                if(i < 3)
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        if(FM.AS.astateTankStates[i] == 0)
                        {
                            debuggunnery("Fuel Tank (" + i + "): Pierced..");
                            FM.AS.hitTank(shot.initiator, i, 2);
                            FM.AS.doSetTankState(shot.initiator, i, 2);
                        }
                        if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.995F)
                        {
                            FM.AS.hitTank(shot.initiator, i, 1);
                            debuggunnery("Fuel Tank (" + i + "): Hit..");
                        }
                    }
                    return;
                }
            }
        }
        super.hitBone(s, shot, point3d);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.A6M5B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M5b(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M5b(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M5b.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitA6M5b.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.A6M5B.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 9, 9, 3, 9, 9, 3, 3
        });
        com.maddox.il2.objects.air.A6M5B.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "1xwdt", new java.lang.String[] {
            "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", "FuelTankGun_Tank0Wooden", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", "FuelTankGun_Tank0", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, null
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", "BombGun50kg"
        });
        com.maddox.il2.objects.air.A6M5B.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
