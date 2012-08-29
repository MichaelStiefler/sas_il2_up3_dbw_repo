// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_110G4.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_110, PaintSchemeBMPar03, Aircraft, NetAircraft

public class BF_110G4 extends com.maddox.il2.objects.air.BF_110
{

    public BF_110G4()
    {
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
                f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, -19F, 0.0F, 20F, 30F);
            else
            if(f1 < 12F)
                f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 0.0F, 12F, 30F, 35F);
            else
                f2 = com.maddox.il2.objects.air.Aircraft.cvt(f1, 12F, 30F, 35F, 40F);
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
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_110G4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf-110");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-110G-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-110G-4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_110G4.class, com.maddox.il2.objects.air.CockpitBF_110G4_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 0, 0, 10, 10, 9, 1, 
            1, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_MGUN05", "_MGUN06", "_ExternalDev03", "_CANNON06", 
            "_CANNON07", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3cmJazz", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMK108ki 110", "MGunMK108ki 110", "MGunMG15t 1200", "MGunMG15t 1200", null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2cmJazz_Tanks", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", null, null, 
            null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3cmJazz_Tanks", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMK108ki 110", "MGunMK108ki 110", "MGunMG15t 1200", "MGunMG15t 1200", null, null, 
            null, "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2cmJazz_Nag", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3cmJazz_Nag", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMK108ki 110", "MGunMK108ki 110", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2cmJazz_Tanks_Nag", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3cmJazz_Tanks_Nag", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMK108ki 110", "MGunMK108ki 110", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2cm_Full", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3cm_Full", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMK108ki 110", "MGunMK108ki 110", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2cm_Full_Tanks", new java.lang.String[] {
            "MGunMK108ki 137", "MGunMK108ki 137", "MGunMG15120NAG2 250", "MGunMG15120NAG2 250", "MGunMGFFk 200", "MGunMGFFk 200", "MGunMG15t 1200", "MGunMG15t 1200", "PylonBF110R3 1", "MGunMG15120NAG 200", 
            "MGunMG15120NAG 200", "PylonETC250 1", "PylonETC250 1", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
