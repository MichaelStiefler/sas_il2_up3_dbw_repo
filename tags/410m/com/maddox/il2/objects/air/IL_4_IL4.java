// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_4_IL4.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_4_DB3F, PaintSchemeBMPar04, TypeBomber, NetAircraft

public class IL_4_IL4 extends com.maddox.il2.objects.air.IL_4_DB3F
    implements com.maddox.il2.objects.air.TypeBomber
{

    public IL_4_IL4()
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
            if(f < -27F)
            {
                f = -27F;
                flag = false;
            }
            if(f > 27F)
            {
                f = 27F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < 2.0F && f > -2F && f1 < 25F)
                flag = false;
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 99F)
            {
                f1 = 99F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -25F)
            {
                f = -25F;
                flag = false;
            }
            if(f > 25F)
            {
                f = 25F;
                flag = false;
            }
            if(f1 < -90F)
            {
                f1 = -90F;
                flag = false;
            }
            if(f1 > 6F)
            {
                f1 = 6F;
                flag = false;
            }
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_4_IL4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "DB-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Il-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-4.fmd");
        com.maddox.il2.objects.air.IL_4_IL4.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "10fab50", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "10fab100", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB100 5", "BombGunFAB100 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "3fab250", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "3fab25010fab50", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB50 5", "BombGunFAB50 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "3fab25010fab100", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB100 5", "BombGunFAB100 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "1fab500", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "1fab5002fab250", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "3fab500", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "3fab50010fab50", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", "BombGunFAB50 5", "BombGunFAB50 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "1fab1000", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "1fab100010fab50", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", "BombGunNull", null, null, "BombGunFAB50 5", "BombGunFAB50 5"
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "torp1", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512", null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "1x53mmCirc", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunTorp45_36AV_A", null, null, null, null, null
        });
        com.maddox.il2.objects.air.IL_4_IL4.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
