// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SM79i.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            SM79, PaintSchemeBMPar09, TypeBomber, TypeTransport, 
//            NetAircraft, Aircraft

public class SM79i extends com.maddox.il2.objects.air.SM79
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeTransport
{

    public SM79i()
    {
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.AS.wantBeaconsNet(true);
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
            if(f1 < -4F)
            {
                f1 = -4F;
                flag = false;
            }
            if(f1 > 70F)
            {
                f1 = 70F;
                flag = false;
            }
            break;

        case 1: // '\001'
            float f2 = 10F;
            float f3 = 1.0F;
            float f4 = 15F;
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
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
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
        if(fSightCurForwardAngle < -16F)
            fSightCurForwardAngle = -16F;
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
            if(fSightCurSideslip > 12F)
                fSightCurSideslip = 12F;
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
            if(fSightCurSideslip < -12F)
                fSightCurSideslip = -12F;
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
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public float fSightCurAltitude;
    public float fSightCurSpeed;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SM.79");
        com.maddox.rts.Property.set(class1, "meshName_it", "3do/plane/SM79-I(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/SM79-I(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SM79.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSM79.class, com.maddox.il2.objects.air.CockpitSM79_Bombardier.class, com.maddox.il2.objects.air.CockpitSM79_TGunner.class, com.maddox.il2.objects.air.CockpitSM79_BGunner.class, com.maddox.il2.objects.air.CockpitSM79_LGunner.class, com.maddox.il2.objects.air.CockpitSM79_RGunner.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 10, 13, 12, 11, 9, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_12,7_01", "_12,7_02", "_12,7_00", "_12,7_04", "_12,7_03", "_ExternalDev01", "_ExternalBomb01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", 
            "_BombSpawnC01", "_BombSpawnC02", "_BombSpawnC03", "_BombSpawnC04", "_BombSpawnC05", "_BombSpawnC06", "_BombSpawnC07", "_BombSpawnC08", "_BombSpawnC09", "_BombSpawnC10", 
            "_BombSpawnC11", "_BombSpawnC12"
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "12x100_delay_drop", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_100_M 12", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "12x50_delay_drop", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_50_M 12", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "12xCassette", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, null, 
            "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", "BombGunSpezzoniera 56", 
            "BombGunSpezzoniera 56", "BombGunSpezzoniera 56"
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "6x100_delay_drop", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, "BombGunIT_100_M 6", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "5x250_delay_drop", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, "BombGunIT_250_T 5", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "2x500_delay_drop", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, null, null, null, "BombGunIT_500_T 2", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "1xLTW_Torp", new java.lang.String[] {
            "MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM127s 500", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM77s 350", "MGunBredaSAFATSM127s 500", null, "BombGunTorpFiume 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.SM79i.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
