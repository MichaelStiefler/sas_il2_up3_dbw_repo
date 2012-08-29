package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class A6M7_62 extends A6M
{

    public A6M7_62()
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.equals("xxarmorg"))
            {
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(44F, 46F), shot);
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
                if(shot.power <= 0.0F)
                {
                    Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        doRicochetBack(shot);
                }
                return;
            }
            if(s.equals("xxarmors"))
            {
                getEnergyPastArmor(8D / (java.lang.Math.abs(Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
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
            if(s.startsWith("xxammowmgl"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                {
                    debuggunnery("Armament: Machine Gun Chain Broken..");
                    FM.AS.setJamBullets(0, 1);
                }
                return;
            }
            if(s.startsWith("xxammowmgr"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                {
                    debuggunnery("Armament: Machine Gun Chain Broken..");
                    FM.AS.setJamBullets(0, 2);
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

    static 
    {
        java.lang.Class class1 = A6M7_62.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M7(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M7(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M7_Model62.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitA6M7_62.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 1, 1, 9, 3, 9, 9, 2, 
            2, 2, 2, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", 
            "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131s 230", "MGunMG131s 240", "MGunMG131s 240", "MGunType99No2s 125", "MGunType99No2s 125", null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x150dt", new java.lang.String[] {
            "MGunMG131s 230", "MGunMG131s 240", "MGunMG131s 240", "MGunType99No2s 125", "MGunType99No2s 125", null, null, "FuelTankGun_Tank0Underwing 1", "FuelTankGun_Tank0Underwing 1", null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xwdt4s", new java.lang.String[] {
            "MGunMG131s 230", "MGunMG131s 240", "MGunMG131s 240", "MGunType99No2s 125", "MGunType99No2s 125", "FuelTankGun_Tank0Centre 1", null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xtype3", new java.lang.String[] {
            "MGunMG131s 230", "MGunMG131s 240", "MGunMG131s 240", "MGunType99No2s 125", "MGunType99No2s 125", null, null, null, null, "RocketGunType3Mk27_2 1", 
            "RocketGunType3Mk27_2 1", "RocketGunType3Mk27_2 1", "RocketGunType3Mk27_2 1", "PylonA6MPLN3 1", "PylonA6MPLN3 1", "PylonA6MPLN3 1", "PylonA6MPLN3 1"
        });
        Aircraft.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunMG131s 230", "MGunMG131s 240", "MGunMG131s 240", "MGunType99No2s 125", "MGunType99No2s 125", null, "BombGun250kg 1", null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
