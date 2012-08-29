// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PE_3BIS.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            PE_2, PaintSchemeFMPar04, TypeFighter, NetAircraft

public class PE_3BIS extends com.maddox.il2.objects.air.PE_2
    implements com.maddox.il2.objects.air.TypeFighter
{

    public PE_3BIS()
    {
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
            if(f < -110F)
            {
                f = -110F;
                flag = false;
            }
            if(f > 88F)
            {
                f = 88F;
                flag = false;
            }
            if(f1 < -1F)
            {
                f1 = -1F;
                flag = false;
            }
            if(f1 > 55F)
            {
                f1 = 55F;
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

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            FM.turret[1].setHealth(f);
            break;
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_3BIS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Pe-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Pe-3bis/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1941.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Pe-3bis.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitPE3bis.class, com.maddox.il2.objects.air.CockpitPE3bis_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.76315F);
        com.maddox.il2.objects.air.PE_3BIS.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 10, 11, 3, 3, 3, 3, 
            3, 3
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON03", "_CANNON04", "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_BombSpawn05", "_BombSpawn06"
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponsRegister(class1, "2fab50", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, 
            "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", null, null, null, null, 
            "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponsRegister(class1, "2fab1002fab50", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "MGunShVAKk 140", "MGunShVAKk 140", "MGunUBt 200", "MGunShKASki 750", "BombGunFAB50", "BombGunFAB50", null, null, 
            "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.PE_3BIS.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
