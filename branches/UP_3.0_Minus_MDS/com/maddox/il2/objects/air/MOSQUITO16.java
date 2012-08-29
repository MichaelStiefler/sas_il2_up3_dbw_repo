// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MOSQUITO16.java

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            MOSQUITO, PaintSchemeFMPar04, PaintSchemeFMPar06, TypeBomber, 
//            NetAircraft, Aircraft

public class MOSQUITO16 extends com.maddox.il2.objects.air.MOSQUITO
    implements com.maddox.il2.objects.air.TypeBomber
{

    public MOSQUITO16()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
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
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 50000F)
            fSightCurAltitude = 50000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 100F)
            fSightCurAltitude = 100F;
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
        if(fSightCurSpeed > 520F)
            fSightCurSpeed = 520F;
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
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
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
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.MOSQUITO16.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Mosquito");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Mosquito_B_MkXVI(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_gb", "3DO/Plane/Mosquito_B_MkXVI(GB)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_gb", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Mosquito-BMkXVI.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMosquito16.class, com.maddox.il2.objects.air.CockpitMOSQUITO16_Bombardier.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.76315F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 3, 3, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_Clip04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_ExternalBomb02", "_ExternalBomb03"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null, null, "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiRedND", new java.lang.String[] {
            null, "BombGunTIRed 1", "BombGunTIRed 1", "BombGunTIRed 2", "BombGunTIRed 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiGreenND", new java.lang.String[] {
            null, "BombGunTIGreen 1", "BombGunTIGreen 1", "BombGunTIGreen 2", "BombGunTIGreen 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiBlueND", new java.lang.String[] {
            null, "BombGunTIBlue 1", "BombGunTIBlue 1", "BombGunTIBlue 2", "BombGunTIBlue 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiYellowND", new java.lang.String[] {
            null, "BombGunTIYellow 1", "BombGunTIYellow 1", "BombGunTIYellow 2", "BombGunTIYellow 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiOrangeND", new java.lang.String[] {
            null, "BombGunTIOrange 1", "BombGunTIOrange 1", "BombGunTIOrange 2", "BombGunTIOrange 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiPinkND", new java.lang.String[] {
            null, "BombGunTIPink 1", "BombGunTIPink 1", "BombGunTIPink 2", "BombGunTIPink 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiRed", new java.lang.String[] {
            null, "BombGunTIRed 1", "BombGunTIRed 1", "BombGunTIRed 2", "BombGunTIRed 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiGreen", new java.lang.String[] {
            null, "BombGunTIGreen 1", "BombGunTIGreen 1", "BombGunTIGreen 2", "BombGunTIGreen 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiBlue", new java.lang.String[] {
            null, "BombGunTIBlue 1", "BombGunTIBlue 1", "BombGunTIBlue 2", "BombGunTIBlue 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiYellow", new java.lang.String[] {
            null, "BombGunTIYellow 1", "BombGunTIYellow 1", "BombGunTIYellow 2", "BombGunTIYellow 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiOrange", new java.lang.String[] {
            null, "BombGunTIOrange 1", "BombGunTIOrange 1", "BombGunTIOrange 2", "BombGunTIOrange 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "TiPink", new java.lang.String[] {
            null, "BombGunTIPink 1", "BombGunTIPink 1", "BombGunTIPink 2", "BombGunTIPink 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x250ND", new java.lang.String[] {
            null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x250ND", new java.lang.String[] {
            null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 2", "BombGun250lbsE 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500ND", new java.lang.String[] {
            null, "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x500ND", new java.lang.String[] {
            null, "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 2", "BombGun500lbsE 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xhc4000ND", new java.lang.String[] {
            null, "BombGunHC4000 1", null, null, null, "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x250", new java.lang.String[] {
            null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x250", new java.lang.String[] {
            null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 2", "BombGun250lbsE 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            null, "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x500", new java.lang.String[] {
            null, "BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 2", "BombGun500lbsE 2", "FuelTankGun_Tank100gal 1", "FuelTankGun_Tank100gal 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}
