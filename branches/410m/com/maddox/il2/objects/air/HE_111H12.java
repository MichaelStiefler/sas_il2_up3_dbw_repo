// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111H12.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_111, PaintSchemeBMPar02, TypeX4Carrier, TypeGuidedBombCarrier, 
//            NetAircraft, Aircraft

public class HE_111H12 extends com.maddox.il2.objects.air.HE_111
    implements com.maddox.il2.objects.air.TypeX4Carrier, com.maddox.il2.objects.air.TypeGuidedBombCarrier
{

    public HE_111H12()
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
        if(thisWeaponsName.endsWith("X"))
            hierMesh().chunkVisible("Bombsight_D0", true);
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            if(bPitUnfocused && !FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_FAK", false);
                hierMesh().chunkVisible("Pilot1_FAL", true);
                hierMesh().chunkVisible("Head1_FAK", false);
            }
            if(FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_FAK", false);
                hierMesh().chunkVisible("Pilot1_FAL", false);
                hierMesh().chunkVisible("Head1_FAK", false);
            }
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(bPitUnfocused && !FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot2_FAK", false);
                hierMesh().chunkVisible("Pilot2_FAL", true);
                hierMesh().chunkVisible("HMask2_D0", false);
            }
            if(FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot2_FAK", false);
                hierMesh().chunkVisible("Pilot2_FAL", false);
                hierMesh().chunkVisible("HMask2_D0", false);
            }
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("HMask3_D0", false);
            FM.turret[1].setHealth(f);
            if(FM.turret.length == 6)
                FM.turret[5].setHealth(f);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 1, 1);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 2, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 3, 1);
            }
            if(FM.AS.astateTankStates[0] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

        mydebug("========================== isGuidingBomb = " + isGuidingBomb);
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
        mydebug("Chimata typeX4CAdjSidePlus, deltaAzimuth = " + deltaAzimuth);
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -0.002F;
        mydebug("Chimata typeX4CAdjSideMinus, deltaAzimuth = " + deltaAzimuth);
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 0.002F;
        mydebug("Chimata typeX4CAdjAttitudePlus, deltaTangage = " + deltaTangage);
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -0.002F;
        mydebug("Chimata typeX4CAdjAttitudeMinus, deltaTangage = " + deltaTangage);
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

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.equals("xxarmorg1"))
            getEnergyPastArmor(5F, shot);
        else
            super.hitBone(s, shot, point3d);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111H12.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-12/hier_h12.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111H-12.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111H12.class, com.maddox.il2.objects.air.CockpitHE_111H12_Bombardier.class, com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, com.maddox.il2.objects.air.CockpitHE_111H12_TGunner.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.HE_111H12.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 600", null, null
        });
        com.maddox.il2.objects.air.HE_111H12.weaponsRegister(class1, "1xHS-293", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 600", "RocketGunHS_293", null
        });
        com.maddox.il2.objects.air.HE_111H12.weaponsRegister(class1, "1xFritzX", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG131t 600", null, "RocketGunFritzX"
        });
        com.maddox.il2.objects.air.HE_111H12.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
