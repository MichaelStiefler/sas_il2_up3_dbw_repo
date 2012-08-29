// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   B_17D.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            B_17, PaintSchemeBMPar01, PaintSchemeFMPar06, TypeBomber, 
//            NetAircraft

public class B_17D extends com.maddox.il2.objects.air.B_17
    implements com.maddox.il2.objects.air.TypeBomber
{

    public B_17D()
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85F * f, 0.0F);
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].setHealth(f);
            FM.turret[1].setHealth(f);
            FM.turret[2].setHealth(f);
            break;

        case 6: // '\006'
            FM.turret[3].setHealth(f);
            break;

        case 7: // '\007'
            FM.turret[4].setHealth(f);
            break;

        case 8: // '\b'
            FM.turret[5].setHealth(f);
            break;
        }
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
            if(f < -11F)
            {
                f = -11F;
                flag = false;
            }
            if(f > 11F)
            {
                f = 11F;
                flag = false;
            }
            if(f1 < -14F)
            {
                f1 = -14F;
                flag = false;
            }
            if(f1 > 14F)
            {
                f1 = 14F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -26F)
            {
                f = -26F;
                flag = false;
            }
            if(f > 0.0F)
            {
                f = 0.0F;
                flag = false;
            }
            if(f1 < -14F)
            {
                f1 = -14F;
                flag = false;
            }
            if(f1 > 14F)
            {
                f1 = 14F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -11F)
            {
                f = -11F;
                flag = false;
            }
            if(f > 11F)
            {
                f = 11F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -12F)
            {
                f = -12F;
                flag = false;
            }
            if(f > 12F)
            {
                f = 12F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 2.0F)
            {
                f1 = 2.0F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -41F)
            {
                f = -41F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 5: // '\005'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 53F)
            {
                f = 53F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.B_17D.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "B-17");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/B-17D(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/B-17D(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2800.9F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-17D.fmd");
        com.maddox.il2.objects.air.B_17D.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 13, 14, 15, 3, 3
        });
        com.maddox.il2.objects.air.B_17D.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", null, null
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "20x100", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGunFAB50 10", "BombGunFAB50 10"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "14x300", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun300lbs 7", "BombGun300lbs 7"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "4x1000", new java.lang.String[] {
            "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun1000lbs 2", "BombGun1000lbs 2"
        });
        com.maddox.il2.objects.air.B_17D.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
