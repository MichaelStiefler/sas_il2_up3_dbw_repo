// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   PE_3M.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            PE_2, PaintSchemeFMPar01, TypeFighter, NetAircraft, 
//            Aircraft

public class PE_3M extends com.maddox.il2.objects.air.PE_2
    implements com.maddox.il2.objects.air.TypeFighter
{

    public PE_3M()
    {
        tme = 0L;
        tpos = 0.0F;
        tlim = 0.0F;
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Turtle_D0", 0.0F, 0.0F, -2F);
        super.update(f);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -1F)
            {
                f1 = -1F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -2F)
            {
                f = -2F;
                flag = false;
            }
            if(f > 2.0F)
            {
                f = 2.0F;
                flag = false;
            }
            if(f1 < -2F)
            {
                f1 = -2F;
                flag = false;
            }
            if(f1 > 2.0F)
            {
                f1 = 2.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            FM.turret[1].bIsOperable = false;
            break;
        }
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

    private long tme;
    private float tpos;
    private float tlim;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_3M.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Pe-3");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Pe-3/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Pe-3M.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitPE3_1.class, com.maddox.il2.objects.air.CockpitPE2_84_TGunner.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.76315F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 0, 10, 11, 3, 3, 3, 3, 3, 
            3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 
            9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON05", "_MGUN01", "_MGUN02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04", "_BombSpawn05", 
            "_BombSpawn05", "_BombSpawn06", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", 
            "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev15", "_ExternalDev16", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", 
            "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock15", "_ExternalRock16", "_ExternalRock15", "_ExternalRock16"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", null, null, null, null, null, 
            null, null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, null, 
            null, null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab100", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, null, 
            null, null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB250 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB250 1", null, 
            null, null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, null, 
            null, null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250_2fab100", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB250 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB250 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, null, 
            null, null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab100_8rs82", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab100_8rs82_2rs132", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250_8rs82_2rs132", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB250 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB250 1", null, 
            null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab250_2fab100_8rs82", new java.lang.String[] {
            "MGunUBk 250", "MGunShVAKk 250", null, "MGunUBt 200", "MGunShKASki 750", "BombGunFAB250 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB250 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
