// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PE_3SERIES1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            PE_2, PaintSchemeFMPar01, TypeFighter, NetAircraft

public class PE_3SERIES1 extends com.maddox.il2.objects.air.PE_2
    implements com.maddox.il2.objects.air.TypeFighter
{

    public PE_3SERIES1()
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

    private long tme;
    private float tpos;
    private float tlim;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_3SERIES1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Pe-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Pe-3/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Pe-3series1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitPE3_1.class, com.maddox.il2.objects.air.CockpitPE3_1_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.76315F);
        com.maddox.il2.objects.air.PE_3SERIES1.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 11, 3, 3
        });
        com.maddox.il2.objects.air.PE_3SERIES1.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_BombSpawn05", "_BombSpawn06"
        });
        com.maddox.il2.objects.air.PE_3SERIES1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", null, null
        });
        com.maddox.il2.objects.air.PE_3SERIES1.weaponsRegister(class1, "2fab50", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.PE_3SERIES1.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 350", "MGunShKASt 750", "MGunShKASki 750", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.PE_3SERIES1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
