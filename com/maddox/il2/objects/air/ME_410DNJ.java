// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ME_410DNJ.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_210, PaintSchemeFMPar05, TypeFighter, TypeBNZFighter, 
//            TypeStormovik, TypeStormovikArmored, TypeX4Carrier, NetAircraft, 
//            Aircraft

public class ME_410DNJ extends com.maddox.il2.objects.air.ME_210
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeStormovikArmored, com.maddox.il2.objects.air.TypeX4Carrier
{

    public ME_410DNJ()
    {
        bToFire = false;
        tX4Prev = 0L;
        kangle = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
        fSightCurReadyness = 0.0F;
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

    public boolean typeBomberToggleAutomation()
    {
        bSightAutomation = !bSightAutomation;
        bSightBombDump = false;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (bSightAutomation ? "ON" : "OFF"));
        return bSightAutomation;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle++;
        if(fSightCurForwardAngle > 85F)
            fSightCurForwardAngle = 85F;
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle--;
        if(fSightCurForwardAngle < 0.0F)
            fSightCurForwardAngle = 0.0F;
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip += 0.1F;
        if(fSightCurSideslip > 3F)
            fSightCurSideslip = 3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip -= 0.1F;
        if(fSightCurSideslip < -3F)
            fSightCurSideslip = -3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 850F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 850F)
            fSightCurAltitude = 850F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 250F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 10F;
        if(fSightCurSpeed > 900F)
            fSightCurSpeed = 900F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 10F;
        if(fSightCurSpeed < 150F)
            fSightCurSpeed = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        if((double)java.lang.Math.abs(FM.Or.getKren()) > 4.5D)
        {
            fSightCurReadyness -= 0.0666666F * f;
            if(fSightCurReadyness < 0.0F)
                fSightCurReadyness = 0.0F;
        }
        if(fSightCurReadyness < 1.0F)
            fSightCurReadyness += 0.0333333F * f;
        else
        if(bSightAutomation)
        {
            fSightCurDistance -= (fSightCurSpeed / 3.6F) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / fSightCurAltitude));
            if((double)fSightCurDistance < (double)(fSightCurSpeed / 3.6F) * java.lang.Math.sqrt(fSightCurAltitude * 0.2038736F))
                bSightBombDump = true;
            if(bSightBombDump)
                if(FM.isTick(3, 0))
                {
                    if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets())
                    {
                        FM.CT.WeaponControl[3] = true;
                        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
                    }
                } else
                {
                    FM.CT.WeaponControl[3] = false;
                }
        }
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte((bSightAutomation ? 1 : 0) | (bSightBombDump ? 2 : 0));
        netmsgguaranted.writeFloat(fSightCurDistance);
        netmsgguaranted.writeByte((int)fSightCurForwardAngle);
        netmsgguaranted.writeByte((int)((fSightCurSideslip + 3F) * 33.33333F));
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeByte((int)(fSightCurReadyness * 200F));
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        bSightAutomation = (i & 1) != 0;
        bSightBombDump = (i & 2) != 0;
        fSightCurDistance = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readUnsignedByte();
        fSightCurSideslip = -3F + (float)netmsginput.readUnsignedByte() / 33.33333F;
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurReadyness = (float)netmsginput.readUnsignedByte() / 200F;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode()) && flag && (FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)FM;
            if(pilot.get_maneuver() == 63 && pilot.target != null)
            {
                com.maddox.JGP.Point3d point3d = new Point3d(pilot.target.Loc);
                point3d.sub(((com.maddox.JGP.Tuple3d) (FM.Loc)));
                FM.Or.transformInv(((com.maddox.JGP.Tuple3d) (point3d)));
                if((point3d.x > 4000D && point3d.x < 5500D || point3d.x > 100D && point3d.x < 5000D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F) && com.maddox.rts.Time.current() > tX4Prev + 10000L)
                {
                    bToFire = true;
                    tX4Prev = com.maddox.rts.Time.current();
                }
            }
        }
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 1.0F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -1F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 1.0F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -1F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
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

    public boolean bToFire;
    private long tX4Prev;
    private float kangle;
    private float deltaAzimuth;
    private float deltaTangage;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_410DNJ.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Me-410");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/ME-410-DNJ/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Me-410D.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitME_410DNJ.class, com.maddox.il2.objects.air.Cockpit410DNJ_Bombardier.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.66895F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 10, 10, 3, 3, 3, 0, 
            0, 9, 0, 9, 9, 1, 9, 1, 9, 1, 
            9, 1, 9, 1, 9, 1, 9, 1, 1, 9, 
            1, 1, 9, 9, 9, 9, 2, 2, 2, 2, 
            9, 2, 9, 2, 9, 2, 2, 9, 2, 2, 
            9, 3, 9, 3, 9, 3, 9, 3, 9, 3, 
            9, 1, 9, 1, 9, 1, 9, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_CANNON03", 
            "_CANNON04", "_ExternalDev03", "_CANNON05", "_ExternalDev04", "_ExternalDev05", "_MGUN05", "_ExternalDev06", "_MGUN06", "_ExternalDev07", "_CANNON07", 
            "_ExternalDev08", "_CANNON08", "_ExternalDev09", "_MGUN09", "_ExternalDev10", "_MGUN10", "_ExternalDev11", "_MGUN11", "_MGUN12", "_ExternalDev12", 
            "_MGUN13", "_MGUN14", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_ExternalRock01", "_ExternalRock03", "_ExternalRock02", "_ExternalRock04", 
            "_ExternalDev17", "_ExternalRock05", "_ExternalDev18", "_ExternalRock06", "_ExternalDev19", "_ExternalRock07", "_ExternalRock07", "_ExternalDev20", "_ExternalRock08", "_ExternalRock08", 
            "_ExternalDev21", "_ExternalBomb01", "_ExternalDev22", "_ExternalBomb02", "_ExternalDev23", "_ExternalBomb03", "_ExternalDev24", "_ExternalBomb04", "_ExternalDev25", "_ExternalBomb05", 
            "_ExternalDev26", "_MGUN15", "_ExternalDev27", "_MGUN16", "_ExternalDev28", "_MGUN17", "_ExternalDev29", "_MGUN18"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U2", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "Pylon410GUNPOD 1", "MGunMG15120MGki 250", "Pylon410GUNPOD 1", "MGunMG15120MGki 250", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, "Pylon410GUNPOD 1", "MGunMK108ki 100", "Pylon410GUNPOD 1", "MGunMK108ki 100", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, "Pylon410Mk103 1", "MGunMK103kh 100", 
            "Pylon410Mk103 1", "MGunMK103kh 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "Pylon410DGUNPOD 1", "MGunMG15120MGki 100", "MGunMG15120MGki 100", "Pylon410DGUNPOD 1", 
            "MGunMG15120MGki 100", "MGunMG15120MGki 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R11", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "Pylon410MG213 1", "MGunMG213MGki 270", "Pylon410MG213 1", "MGunMG213MGki 270", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U5R12", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG213MGki 270", "MGunMG213MGki 270", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "Pylon410MG213 1", "MGunMK213ki 110", "Pylon410MG213 1", "MGunMK213ki 110"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Tanks", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "Pylon410MG213 1", "MGunMK213ki 110", "Pylon410MG213 1", "MGunMK213ki 110"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "U2T", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "Pylon410GUNPOD 1", "MGunMG15120MGki 250", "Pylon410GUNPOD 1", "MGunMG15120MGki 250", null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2T", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, "Pylon410GUNPOD 1", "MGunMK108ki 100", "Pylon410GUNPOD 1", "MGunMK108ki 100", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3T", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, "Pylon410Mk103 1", "MGunMK103kh 100", 
            "Pylon410Mk103 1", "MGunMK103kh 100", null, null, null, null, null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R5T", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "Pylon410DGUNPOD 1", "MGunMG15120MGki 100", "MGunMG15120MGki 100", "Pylon410DGUNPOD 1", 
            "MGunMG15120MGki 100", "MGunMG15120MGki 100", null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R11T", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunMG15120k 250", "MGunMG15120k 250", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1", null, null, null, null, 
            null, null, null, null, "PylonETC250 1", null, null, "PylonETC250 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "Pylon410MG213 1", "MGunMG213MGki 270", "Pylon410MG213 1", "MGunMG213MGki 270", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
