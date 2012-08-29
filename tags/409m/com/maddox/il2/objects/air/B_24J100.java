// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_24J100.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_24, PaintSchemeBMPar05, PaintSchemeFMPar06, TypeBomber, 
//            NetAircraft, Aircraft

public class B_24J100 extends com.maddox.il2.objects.air.B_24
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_24J100()
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
            if(f < -85F)
            {
                f = -85F;
                flag = false;
            }
            if(f > 85F)
            {
                f = 85F;
                flag = false;
            }
            if(f1 < -32F)
            {
                f1 = -32F;
                flag = false;
            }
            if(f1 > 46F)
            {
                f1 = 46F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f1 < -0F)
            {
                f1 = -0F;
                flag = false;
            }
            if(f1 > 20F)
            {
                f1 = 20F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f1 < -70F)
            {
                f1 = -70F;
                flag = false;
            }
            if(f1 > 7F)
            {
                f1 = 7F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 64F)
            {
                f = 64F;
                flag = false;
            }
            if(f1 < -37F)
            {
                f1 = -37F;
                flag = false;
            }
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -67F)
            {
                f = -67F;
                flag = false;
            }
            if(f > 34F)
            {
                f = 34F;
                flag = false;
            }
            if(f1 < -37F)
            {
                f1 = -37F;
                flag = false;
            }
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            break;

        case 5: // '\005'
            if(f < -85F)
            {
                f = -85F;
                flag = false;
            }
            if(f > 85F)
            {
                f = 85F;
                flag = false;
            }
            if(f1 < -32F)
            {
                f1 = -32F;
                flag = false;
            }
            if(f1 > 46F)
            {
                f1 = 46F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.air.B_24J100.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-24");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-24J-100-CF(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-24J-100-CF(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2800.9F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-24J.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 11, 12, 12, 13, 14, 15, 15, 
            3, 3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", 
            "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16x500", new java.lang.String[] {
            "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", 
            "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
