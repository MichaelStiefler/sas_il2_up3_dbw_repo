// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CAC_Sabre_Mk32.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            F_86F, Aircraft, PaintSchemeFMPar1956, PaintSchemeFMPar06, 
//            TypeGuidedMissileCarrier, TypeCountermeasure, TypeThreatDetector, TypeGSuit, 
//            NetAircraft

public class CAC_Sabre_Mk32 extends com.maddox.il2.objects.air.F_86F
    implements com.maddox.il2.objects.air.TypeGuidedMissileCarrier, com.maddox.il2.objects.air.TypeCountermeasure, com.maddox.il2.objects.air.TypeThreatDetector, com.maddox.il2.objects.air.TypeGSuit
{

    public CAC_Sabre_Mk32()
    {
        guidedMissileUtils = null;
        trgtPk = 0.0F;
        trgtAI = null;
        hasChaff = false;
        hasFlare = false;
        lastChaffDeployed = 0L;
        lastFlareDeployed = 0L;
        lastCommonThreatActive = 0L;
        intervalCommonThreat = 1000L;
        lastRadarLockThreatActive = 0L;
        intervalRadarLockThreat = 1000L;
        lastMissileLaunchThreatActive = 0L;
        intervalMissileLaunchThreat = 1000L;
        rocketsList = new ArrayList();
        bToFire = false;
        tX4Prev = 0L;
        guidedMissileUtils = new GuidedMissileUtils(this);
    }

    public long getChaffDeployed()
    {
        if(hasChaff)
            return lastChaffDeployed;
        else
            return 0L;
    }

    public long getFlareDeployed()
    {
        if(hasFlare)
            return lastFlareDeployed;
        else
            return 0L;
    }

    public void setCommonThreatActive()
    {
        long curTime = com.maddox.rts.Time.current();
        if(curTime - lastCommonThreatActive > intervalCommonThreat)
        {
            lastCommonThreatActive = curTime;
            doDealCommonThreat();
        }
    }

    public void setRadarLockThreatActive()
    {
        long curTime = com.maddox.rts.Time.current();
        if(curTime - lastRadarLockThreatActive > intervalRadarLockThreat)
        {
            lastRadarLockThreatActive = curTime;
            doDealRadarLockThreat();
        }
    }

    public void setMissileLaunchThreatActive()
    {
        long curTime = com.maddox.rts.Time.current();
        if(curTime - lastMissileLaunchThreatActive > intervalMissileLaunchThreat)
        {
            lastMissileLaunchThreatActive = curTime;
            doDealMissileLaunchThreat();
        }
    }

    private void doDealCommonThreat()
    {
    }

    private void doDealRadarLockThreat()
    {
    }

    private void doDealMissileLaunchThreat()
    {
    }

    public void getGFactors(com.maddox.il2.objects.air.TypeGSuit.GFactors theGFactors)
    {
        theGFactors.setGFactors(1.5F, 1.5F, 1.0F, 2.0F, 2.0F, 2.0F);
    }

    public com.maddox.il2.engine.Actor getMissileTarget()
    {
        return guidedMissileUtils.getMissileTarget();
    }

    public com.maddox.JGP.Point3f getMissileTargetOffset()
    {
        return guidedMissileUtils.getSelectedActorOffset();
    }

    public boolean hasMissiles()
    {
        return !rocketsList.isEmpty();
    }

    public void shotMissile()
    {
        if(hasMissiles())
        {
            if(com.maddox.il2.net.NetMissionTrack.isPlaying() && (!(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode()))
                ((com.maddox.il2.objects.weapons.RocketGun)rocketsList.get(0)).loadBullets(0);
            if(com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo || this != com.maddox.il2.ai.World.getPlayerAircraft())
                rocketsList.remove(0);
        }
    }

    public int getMissileLockState()
    {
        return guidedMissileUtils.getMissileLockState();
    }

    private float getMissilePk()
    {
        float thePk = 0.0F;
        guidedMissileUtils.setMissileTarget(guidedMissileUtils.lookForGuidedMissileTarget(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, guidedMissileUtils.getMaxPOVfrom(), guidedMissileUtils.getMaxPOVto(), guidedMissileUtils.getPkMaxDist()));
        if(com.maddox.il2.engine.Actor.isValid(guidedMissileUtils.getMissileTarget()))
            thePk = guidedMissileUtils.Pk(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, guidedMissileUtils.getMissileTarget());
        return thePk;
    }

    private void checkAIlaunchMissile()
    {
        if((super.FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode() || !(super.FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        if(rocketsList.isEmpty())
            return;
        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)super.FM;
        if((pilot.get_maneuver() == 27 || pilot.get_maneuver() == 62 || pilot.get_maneuver() == 63) && ((com.maddox.il2.ai.air.Maneuver) (pilot)).target != null)
        {
            trgtAI = ((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.ai.air.Maneuver) (pilot)).target)).actor;
            if(!com.maddox.il2.engine.Actor.isValid(trgtAI) || !(trgtAI instanceof com.maddox.il2.objects.air.Aircraft))
                return;
            bToFire = false;
            if(trgtPk > 25F && com.maddox.il2.engine.Actor.isValid(guidedMissileUtils.getMissileTarget()) && (guidedMissileUtils.getMissileTarget() instanceof com.maddox.il2.objects.air.Aircraft) && guidedMissileUtils.getMissileTarget().getArmy() != ((com.maddox.il2.engine.Interpolate) (super.FM)).actor.getArmy() && com.maddox.rts.Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = com.maddox.rts.Time.current();
                shootRocket();
                bToFire = false;
            }
        }
    }

    public void shootRocket()
    {
        if(rocketsList.isEmpty())
        {
            return;
        } else
        {
            ((com.maddox.il2.objects.weapons.RocketGun)rocketsList.get(0)).shots(1);
            return;
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(super.FM.isPlayers())
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.dvCockpitDoor = 0.5F;
        }
        rocketsList.clear();
        guidedMissileUtils.createMissileList(rocketsList);
    }

    public void update(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[1] && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[0][0].countBullets() != 0)
        {
            origThrl = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setControlThrottle(origThrl - 0.05F);
            surgeControl = 1;
        } else
        if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[1] && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && surgeControl == 1)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setControlThrottle(origThrl);
            surgeControl = 0;
        }
        super.update(f);
        trgtPk = getMissilePk();
        guidedMissileUtils.checkLockStatus();
        checkAIlaunchMissile();
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    private com.maddox.il2.objects.weapons.GuidedMissileUtils guidedMissileUtils;
    private float trgtPk;
    private com.maddox.il2.engine.Actor trgtAI;
    private boolean hasChaff;
    private boolean hasFlare;
    private long lastChaffDeployed;
    private long lastFlareDeployed;
    private long lastCommonThreatActive;
    private long intervalCommonThreat;
    private long lastRadarLockThreatActive;
    private long intervalRadarLockThreat;
    private long lastMissileLaunchThreatActive;
    private long intervalMissileLaunchThreat;
    private static final float NEG_G_TOLERANCE_FACTOR = 1.5F;
    private static final float NEG_G_TIME_FACTOR = 1.5F;
    private static final float NEG_G_RECOVERY_FACTOR = 1F;
    private static final float POS_G_TOLERANCE_FACTOR = 2F;
    private static final float POS_G_TIME_FACTOR = 2F;
    private static final float POS_G_RECOVERY_FACTOR = 2F;
    private java.util.ArrayList rocketsList;
    public boolean bToFire;
    private long tX4Prev;
    private float origThrl;
    private int surgeControl;

    static 
    {
        java.lang.Class localClass = com.maddox.il2.objects.air.CAC_Sabre_Mk32.class;
        new NetAircraft.SPAWN(localClass);
        com.maddox.rts.Property.set(localClass, "iconFar_shortClassName", "CAC_Sabre");
        com.maddox.rts.Property.set(localClass, "meshName_gb", "3DO/Plane/CAC_Sabre_Mk32(Multi1)/hier.him");
        com.maddox.rts.Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(localClass, "meshName", "3DO/Plane/CAC_Sabre_Mk32(Multi1)/hier.him");
        com.maddox.rts.Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(localClass, "yearService", 1949.9F);
        com.maddox.rts.Property.set(localClass, "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(localClass, "FlightModel", "FlightModels/CAC_Sabre_Mk32.fmd");
        try
        {
            com.maddox.rts.Property.set(localClass, "cockpitClass", new java.lang.Class[] {
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitF_86Flate")
            });
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in CAC Mk32 Cockpit init, " + exception.getMessage());
        }
        com.maddox.rts.Property.set(localClass, "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(localClass, new int[] {
            0, 0, 9, 9, 9, 9, 9, 3, 3, 9, 
            3, 3, 9, 2, 2, 9, 2, 2, 9, 9, 
            9, 9, 9, 3, 3, 9, 3, 3, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(localClass, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalBomb01", "_ExternalBomb01", "_ExternalDev06", 
            "_ExternalBomb02", "_ExternalBomb02", "_ExternalDev07", "_ExternalRock01", "_ExternalRock01", "_ExternalDev08", "_ExternalRock02", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", 
            "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev14", "_ExternalBomb04", "_ExternalBomb04", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", 
            "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", 
            "_ExternalRock25", "_ExternalRock26", "_ExternalDev17", "_ExternalDev18", "_ExternalDev19", "_ExternalDev20", "_ExternalDev21", "_ExternalDev22", "_ExternalDev23", "_ExternalDev24", 
            "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", 
            "_ExternalRock37", "_ExternalRock38", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalDev25", "_ExternalDev26", "_ExternalDev27", "_ExternalDev28", 
            "_ExternalDev29", "_ExternalDev30", "_ExternalDev31", "_ExternalDev32"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "default", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "4x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonF86_Outboard 1", "PylonF86_Outboard 1", 
            "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x75nap", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x75nap+2x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun75Napalm 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x500", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "4x500", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbsMC 1", "BombGunNull 1", null, 
            "BombGun500lbsMC 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun500lbsMC 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x500+2x1000", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", null, null, null, "BombGun500lbsMC 1", "BombGunNull 1", null, 
            "BombGun500lbsMC 1", "BombGunNull 1", null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x1000", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2x1000+2x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", "PylonF86_Outboard 1", "BombGun1000lbsMC 1", "BombGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "16xRP3", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", 
            "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", 
            "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "8xRP3+2x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", 
            "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", null, null, null, null, 
            "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1", "PylonSpitROCK 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "24xSURA_D_HE", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", 
            "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", 
            "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", 
            "RocketGunSURA_HE 1", "RocketGunSURA_HE 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "24xSURA_D_AP", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", 
            "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", 
            "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", 
            "RocketGunSURA_AP 1", "RocketGunSURA_AP 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", "PylonSURA_Launcher 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2xAIM9B", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", null, null, null, null, null, null, null, null, 
            null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "2xAIM9B+2x200dt", new java.lang.String[] {
            "MGunADEN30ki 150", "MGunADEN30ki 150", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, null, null, 
            null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "RocketGunNull 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(localClass, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
