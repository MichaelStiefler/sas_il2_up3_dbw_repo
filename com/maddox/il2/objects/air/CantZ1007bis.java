// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CantZ1007bis.java

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            CantZ1007, PaintSchemeBMPar09, TypeBomber, TypeTransport, 
//            NetAircraft

public class CantZ1007bis extends com.maddox.il2.objects.air.CantZ1007
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeTransport
{

    public CantZ1007bis()
    {
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 3: // '\003'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 60F)
            {
                f = 60F;
                flag = false;
            }
            if(f1 < -35F)
            {
                f1 = -35F;
                flag = false;
            }
            if(f1 > 35F)
            {
                f1 = 35F;
                flag = false;
            }
            break;

        case 0: // '\0'
            if(f1 < -7F)
            {
                f1 = -7F;
                flag = false;
            }
            if(f1 > 80F)
            {
                f1 = 80F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -40F)
            {
                f = -40F;
                flag = false;
            }
            if(f > 40F)
            {
                f = 40F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -60F)
            {
                f = -60F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -35F)
            {
                f1 = -35F;
                flag = false;
            }
            if(f1 > 35F)
            {
                f1 = 35F;
                flag = false;
            }
            break;
        }
        af[0] = f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle += 0.4F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.4F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip += 0.5D;
        if(thisWeaponsName.startsWith("1x"))
        {
            if(fSightCurSideslip > 40F)
                fSightCurSideslip = 40F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + fSightCurSideslip);
        } else
        {
            if(fSightCurSideslip > 10F)
                fSightCurSideslip = 10F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + fSightCurSideslip);
        }
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip -= 0.5D;
        if(thisWeaponsName.startsWith("1x"))
        {
            if(fSightCurSideslip < -40F)
                fSightCurSideslip = -40F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  " + fSightCurSideslip);
        } else
        {
            if(fSightCurSideslip < -10F)
                fSightCurSideslip = -10F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip " + fSightCurSideslip);
        }
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 300F)
            fSightCurAltitude = 300F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 50F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 650F)
            fSightCurSpeed = 650F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 50F)
            fSightCurSpeed = 50F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        d -= (double)(fSightCurAltitude * fSightCurAltitude) * 1.419E-005D;
        fSightSetForwardAngle = (float)java.lang.Math.atan(d / (double)fSightCurAltitude);
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
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

    public float fSightCurAltitude;
    public float fSightCurSpeed;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "CantZ");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_it", "3do/plane/CantZ1007bis(it)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_it", ((java.lang.Object) (new PaintSchemeBMPar09())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/CantZ1007bis(multi)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar09())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1940F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/CantZ1007.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitCant.class, com.maddox.il2.objects.air.CockpitCant_Bombardier.class, com.maddox.il2.objects.air.CockpitCant_TGunner.class, com.maddox.il2.objects.air.CockpitCant_BGunner.class
        })));
        com.maddox.il2.objects.air.CantZ1007bis.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", 
            "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14", "_BombSpawn15", "_BombSpawn16", 
            "_BombSpawn17", "_BombSpawn18", "_BombSpawn19", "_BombSpawn20", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", 
            "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10"
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "12x50", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", 
            "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "12x100", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", 
            "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "6x100", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", 
            "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg", "BombGunNull", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x250+3x100", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x250+3x100+3x100x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", 
            null, null, null, null, "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x250+3x100+3x50x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, "BombGunNull", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", 
            null, null, null, null, "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x250+1x250x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_250Kg", "BombGunIT_250Kg", null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x250+2x250x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull", "BombGunIT_250Kg", 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_250Kg", "BombGunIT_250Kg", "BombGunIT_250Kg", "BombGunIT_250Kg"
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x500+3x50x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x500+3x100x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2x500+1x250x2wing", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunIT_500Kg", "BombGunNull", "BombGunNull", "BombGunIT_500Kg", null, null, null, null, null, null, 
            "BombGunIT_250Kg", "BombGunIT_250Kg", null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "2xMotobombaFFF", new java.lang.String[] {
            "MGunScotti127s 350", "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunTorpFFF", "BombGunNull", "BombGunNull", "BombGunTorpFFF1", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.CantZ1007bis.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
