// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mig_17PF.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.GunNull;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            Mig_17, Aircraft, PaintSchemeFCSPar1956, PaintSchemeFMPar1956, 
//            PaintSchemeFMPar06, TypeGuidedMissileCarrier, TypeCountermeasure, TypeThreatDetector, 
//            TypeGSuit, NetAircraft

public class Mig_17PF extends com.maddox.il2.objects.air.Mig_17
    implements com.maddox.il2.objects.air.TypeGuidedMissileCarrier, com.maddox.il2.objects.air.TypeCountermeasure, com.maddox.il2.objects.air.TypeThreatDetector, com.maddox.il2.objects.air.TypeGSuit
{

    public Mig_17PF()
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
        theGFactors.setGFactors(1.0F, 1.0F, 1.0F, 1.8F, 1.5F, 1.0F);
    }

    private void setGunNullOwner()
    {
        try
        {
            for(int l = 0; l < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons.length; l++)
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[l] != null)
                {
                    for(int l1 = 0; l1 < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[l].length; l1++)
                        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[l][l1] != null && (((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[l][l1] instanceof com.maddox.il2.objects.weapons.GunNull))
                            ((com.maddox.il2.objects.weapons.GunNull)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[l][l1]).setOwner(this);

                }

        }
        catch(java.lang.Exception exception) { }
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
        setGunNullOwner();
    }

    public void update(float f1)
    {
        super.update(f1);
        typeFighterAceMakerRangeFinder();
        trgtPk = getMissilePk();
        guidedMissileUtils.checkLockStatus();
        checkAIlaunchMissile();
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F)
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 1.001F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 5);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 3);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
            }
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
    private static final float NEG_G_TOLERANCE_FACTOR = 1F;
    private static final float NEG_G_TIME_FACTOR = 1F;
    private static final float NEG_G_RECOVERY_FACTOR = 1F;
    private static final float POS_G_TOLERANCE_FACTOR = 1.8F;
    private static final float POS_G_TIME_FACTOR = 1.5F;
    private static final float POS_G_RECOVERY_FACTOR = 1F;
    private java.util.ArrayList rocketsList;
    public boolean bToFire;
    private long tX4Prev;

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.air.Mig_17PF.class;
        new NetAircraft.SPAWN(var_class);
        com.maddox.rts.Property.set(var_class, "iconFar_shortClassName", "MiG-17PF");
        com.maddox.rts.Property.set(var_class, "meshName_ru", "3DO/Plane/MiG-17PF(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme_ru", new PaintSchemeFCSPar1956());
        com.maddox.rts.Property.set(var_class, "meshName_sk", "3DO/Plane/MiG-17PF(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme_sk", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(var_class, "meshName_ro", "3DO/Plane/MiG-17PF(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme_ro", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(var_class, "meshName_hu", "3DO/Plane/MiG-17PF(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme_hu", new PaintSchemeFMPar1956());
        com.maddox.rts.Property.set(var_class, "meshName", "3DO/Plane/MiG-17PF(Multi1)/hier.him");
        com.maddox.rts.Property.set(var_class, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(var_class, "yearService", 1952.11F);
        com.maddox.rts.Property.set(var_class, "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(var_class, "FlightModel", "FlightModels/MiG-17PF.fmd");
        com.maddox.rts.Property.set(var_class, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMig_17PF.class
        });
        com.maddox.rts.Property.set(var_class, "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(var_class, new int[] {
            0, 0, 0, 9, 9, 9, 9, 9, 9, 2, 
            2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 9, 9, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(var_class, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock04", "_ExternalRock04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock05", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock06", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", 
            "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", 
            "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", 
            "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalRock37", "_ExternalRock38", "_ExternalDev17", 
            "_ExternalDev13", "_ExternalDev14", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalRock43", "_ExternalRock44", "_ExternalRock45", "_ExternalRock46", 
            "_ExternalRock47", "_ExternalRock48", "_ExternalRock49", "_ExternalRock50", "_ExternalRock51", "_ExternalRock52", "_ExternalRock53", "_ExternalRock54", "_ExternalRock55", "_ExternalRock56", 
            "_ExternalRock57", "_ExternalRock58", "_ExternalRock59", "_ExternalRock60", "_ExternalRock61", "_ExternalRock62", "_ExternalRock63", "_ExternalRock64", "_ExternalRock65", "_ExternalRock66", 
            "_ExternalRock67", "_ExternalRock68", "_ExternalRock69", "_ExternalRock70"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "default", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xdrops", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xORO57", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xORO57+2xdrops", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xORO57", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5 1", "RocketGunS5 1", "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xORO57+2xdrops", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", 
            "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5Salvo 1", "RocketGunS5 1", "RocketGunS5 1", "PylonMiG_Cannons 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xMARS2", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            "PylonMARS2 1", "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xMARS2+2xdrops", new java.lang.String[] {
            "MGunNR23ki 100", "MGunNR23ki 100", "MGunNR23ki 100", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG_Cannons 1", 
            "PylonMARS2 1", "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xK5M", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", null, null, "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunK5M 1", 
            "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xK5M+2xdrops", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", "FTGunL 1", "FTGunR 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunK5M 1", 
            "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", "RocketGunK5M 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xK5Mf", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", null, null, "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunK5Mf 1", 
            "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xK5Mf+2xdrops", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", "FTGunL 1", "FTGunR 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunK5Mf 1", 
            "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", "RocketGunK5Mf 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xR55", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", null, null, "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunR55 1", 
            "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xR55+2xdrops", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", "FTGunL 1", "FTGunR 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunR55 1", 
            "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", "RocketGunR55 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xR55f", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", null, null, "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunR55f 1", 
            "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xR55f+2xdrops", new java.lang.String[] {
            "MGunNull 1", "MGunNull 1", "MGunNull 1", "FTGunL 1", "FTGunR 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "PylonK5M 1", "RocketGunR55f 1", 
            "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", "RocketGunR55f 1", "RocketGunNull 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
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
