// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Do217_K2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Do217, PaintSchemeBMPar02, TypeX4Carrier, TypeGuidedBombCarrier, 
//            NetAircraft, Aircraft

public class Do217_K2 extends com.maddox.il2.objects.air.Do217
    implements com.maddox.il2.objects.air.TypeX4Carrier, com.maddox.il2.objects.air.TypeGuidedBombCarrier
{

    public Do217_K2()
    {
        bToFire = false;
        tX4Prev = 0L;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        isGuidingBomb = false;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.startsWith("1xFritzX") || thisWeaponsName.startsWith("2xFritzX"))
        {
            hierMesh().chunkVisible("WingRackR_D0", true);
            hierMesh().chunkVisible("WingRackL_D0", true);
        }
        if(thisWeaponsName.startsWith("1xHS293") || thisWeaponsName.startsWith("2xHS293"))
        {
            hierMesh().chunkVisible("WingRackR1_D0", true);
            hierMesh().chunkVisible("WingRackL1_D0", true);
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        if(i == 5)
        {
            if(f1 > 5F)
            {
                f1 = 5F;
                flag = false;
            }
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f > 5F)
            {
                f = 5F;
                flag = false;
            }
            if(f < -5F)
            {
                f = -5F;
                flag = false;
            }
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
    }

    public boolean typeGuidedBombCisMasterAlive()
    {
        return isMasterAlive;
    }

    public void typeGuidedBombCsetMasterAlive(boolean flag)
    {
        isMasterAlive = flag;
    }

    public boolean typeGuidedBombCgetIsGuiding()
    {
        return isGuidingBomb;
    }

    public void typeGuidedBombCsetIsGuiding(boolean flag)
    {
        isGuidingBomb = flag;
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 0.002F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -0.002F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 0.002F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -0.002F;
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

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle++;
        if(fSightCurForwardAngle > 85F)
            fSightCurForwardAngle = 85F;
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        if(!isGuidingBomb)
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
        if(!isGuidingBomb)
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
                new Integer((int)fSightCurForwardAngle)
            });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjSideslipPlus()
    {
        if(!isGuidingBomb)
        {
            fSightCurSideslip += 0.1F;
            if(fSightCurSideslip > 3F)
                fSightCurSideslip = 3F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
                new Integer((int)(fSightCurSideslip * 10F))
            });
        }
    }

    public void typeBomberAdjSideslipMinus()
    {
        if(!isGuidingBomb)
        {
            fSightCurSideslip -= 0.1F;
            if(fSightCurSideslip < -3F)
                fSightCurSideslip = -3F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
                new Integer((int)(fSightCurSideslip * 10F))
            });
        }
    }

    public void typeBomberAdjAltitudePlus()
    {
        if(!isGuidingBomb)
        {
            fSightCurAltitude += 10F;
            if(fSightCurAltitude > 10000F)
                fSightCurAltitude = 10000F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        }
    }

    public void typeBomberAdjAltitudeMinus()
    {
        if(!isGuidingBomb)
        {
            fSightCurAltitude -= 10F;
            if(fSightCurAltitude < 850F)
                fSightCurAltitude = 850F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fSightCurAltitude)
            });
            fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        }
    }

    protected void mydebug(java.lang.String s)
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public boolean bToFire;
    private long tX4Prev;
    private float deltaAzimuth;
    private float deltaTangage;
    private boolean isGuidingBomb;
    private boolean isMasterAlive;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Do-217");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/Do217_K2/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Do217K-2.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitDo217_K1.class, com.maddox.il2.objects.air.CockpitDo217_Bombardier.class, com.maddox.il2.objects.air.CockpitDo217_NGunner.class, com.maddox.il2.objects.air.CockpitDo217_TGunner.class, com.maddox.il2.objects.air.CockpitDo217_BGunner.class, com.maddox.il2.objects.air.CockpitDo217_LGunner.class, com.maddox.il2.objects.air.CockpitDo217_RGunner.class, com.maddox.il2.objects.air.CockpitDo217_PGunner.class
        })));
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 12, 13, 14, 15, 15, 15, 15, 
            3, 3, 3, 3, 3, 3, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN10", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "2xFritzX", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            "RocketGunFritzX 1", "BombGunNull 1", "BombGunNull 1", "RocketGunFritzX 1", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "2xHS293", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            null, null, null, null, "RocketGunHS_293 1", "BombGunNull 1", "BombGunNull 1", "RocketGunHS_293 1", null, null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "1xFritzX", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            "RocketGunFritzX 1", null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "1xFritzX+1x300LTank", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            "RocketGunFritzX 1", null, null, null, null, null, null, null, "FuelTankGun_Type_D 1", null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "1xHS293+1x300LTank", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            null, null, null, null, "RocketGunHS_293 1", null, null, null, null, "FuelTankGun_Type_D 1"
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "1xFritzX+1x900LTank", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            "RocketGunFritzX 1", null, null, null, null, null, null, null, "FuelTankGun_Tank900 1", null
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "1xHS293+1x900LTank", new java.lang.String[] {
            "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG131tu 750", "MGunMG131t 1000", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", "MGunMG81t 300", 
            null, null, null, null, "RocketGunHS_293 1", null, null, null, null, "FuelTankGun_Tank900 1"
        });
        com.maddox.il2.objects.air.Do217_K2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
