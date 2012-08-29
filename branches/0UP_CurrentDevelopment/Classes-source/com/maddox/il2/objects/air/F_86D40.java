// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F_86D40.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.weapons.GunNull;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGunK5M;
import com.maddox.rts.Property;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            F_86D, PaintSchemeFMPar06, Aircraft, NetAircraft

public class F_86D40 extends com.maddox.il2.objects.air.F_86D
{

    public F_86D40()
    {
        rocketsList = new ArrayList();
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasBayDoors = true;
        setGunNullOwner();
        rocketsList.clear();
        createRocketsList();
    }

    protected void moveBayDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 1.0F, 0.0F, -0.22F);
        hierMesh().chunkSetLocate("Launcher_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    private void setGunNullOwner()
    {
        try
        {
            for(int i = 0; i < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons.length; i++)
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i] != null)
                {
                    for(int j = 0; j < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i].length; j++)
                        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j] != null && (((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.GunNull))
                            ((com.maddox.il2.objects.weapons.GunNull)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j]).setOwner(this);

                }

        }
        catch(java.lang.Exception exception) { }
    }

    public void createRocketsList()
    {
        for(int i = 0; i < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons.length; i++)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i] != null)
            {
                for(int j = 0; j < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i].length; j++)
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.RocketGun)
                        rocketsList.add(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[i][j]);

            }

    }

    public void shootRocket()
    {
        if(rocketsList.isEmpty())
        {
            return;
        } else
        {
            ((com.maddox.il2.objects.weapons.RocketGunK5M)rocketsList.remove(0)).shots(1);
            return;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)super.FM;
        if((!super.FM.isPlayers() || !(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode()) && (super.FM instanceof com.maddox.il2.ai.air.Maneuver))
            if(((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 3 || ((com.maddox.il2.ai.air.Maneuver)super.FM).get_maneuver() == 63 && maneuver.target != null)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.BayDoorControl = 1.0F;
            else
            if(maneuver.target == null || rocketsList.isEmpty())
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.BayDoorControl = 0.0F;
                maneuver.set_maneuver(3);
            }
    }

    public void update(float f)
    {
        super.update(f);
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

    private java.util.ArrayList rocketsList;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F_86D40.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F-86D");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/F_86D(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/F_86D(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1949.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F-86D-40.fmd:F-86D");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitF_86K.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 0, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", 
            "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null, "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4 1", "RocketGunFFARMk4 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4 1", "RocketGunFFARMk4 1", "MGunNull 1", "MGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x120", new java.lang.String[] {
            "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_TankC120galL 1", "FuelTankGun_TankC120galR 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "MGunNull 1", "MGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x200", new java.lang.String[] {
            "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_Tank207galL 1", "FuelTankGun_Tank207galR 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", 
            "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "RocketGunFFARMk4Salvo 1", "MGunNull 1", "MGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
