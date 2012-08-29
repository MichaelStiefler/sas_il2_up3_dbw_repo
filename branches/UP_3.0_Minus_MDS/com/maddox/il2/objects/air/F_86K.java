// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F_86K.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            F_86F, Aircraft, PaintSchemeFMPar06, PaintSchemeFMPar1956, 
//            TypeGuidedMissileCarrier, TypeCountermeasure, TypeThreatDetector, TypeGSuit, 
//            Cockpit, NetAircraft

public class F_86K extends com.maddox.il2.objects.air.F_86F
    implements com.maddox.il2.objects.air.TypeGuidedMissileCarrier, com.maddox.il2.objects.air.TypeCountermeasure, com.maddox.il2.objects.air.TypeThreatDetector, com.maddox.il2.objects.air.TypeGSuit
{

    public F_86K()
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
        turbo = null;
        turbosmoke = null;
        afterburner = null;
        guidedMissileUtils = new GuidedMissileUtils(((com.maddox.il2.engine.Actor) (this)));
        super.skipSurgeStateUpdate = true;
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
        long l = com.maddox.rts.Time.current();
        if(l - lastCommonThreatActive > intervalCommonThreat)
        {
            lastCommonThreatActive = l;
            doDealCommonThreat();
        }
    }

    public void setRadarLockThreatActive()
    {
        long l = com.maddox.rts.Time.current();
        if(l - lastRadarLockThreatActive > intervalRadarLockThreat)
        {
            lastRadarLockThreatActive = l;
            doDealRadarLockThreat();
        }
    }

    public void setMissileLaunchThreatActive()
    {
        long l = com.maddox.rts.Time.current();
        if(l - lastMissileLaunchThreatActive > intervalMissileLaunchThreat)
        {
            lastMissileLaunchThreatActive = l;
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

    public void getGFactors(com.maddox.il2.objects.air.TypeGSuit.GFactors gfactors)
    {
        gfactors.setGFactors(1.5F, 1.5F, 1.0F, 2.0F, 2.0F, 2.0F);
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
        float f = 0.0F;
        guidedMissileUtils.setMissileTarget(guidedMissileUtils.lookForGuidedMissileTarget(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, guidedMissileUtils.getMaxPOVfrom(), guidedMissileUtils.getMaxPOVto(), guidedMissileUtils.getPkMaxDist()));
        if(com.maddox.il2.engine.Actor.isValid(guidedMissileUtils.getMissileTarget()))
            f = guidedMissileUtils.Pk(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, guidedMissileUtils.getMissileTarget());
        return f;
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
        rocketsList.clear();
        guidedMissileUtils.createMissileList(rocketsList);
    }

    public void destroy()
    {
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (turbo))))
            turbo.destroy();
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (turbosmoke))))
            turbosmoke.destroy();
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (afterburner))))
            afterburner.destroy();
        super.destroy();
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((!super.FM.isPlayers() || !(super.FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode()) && (super.FM instanceof com.maddox.il2.ai.air.Maneuver) && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.nOfGearsOnGr == 0)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.cockpitDoorControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = false;
        }
    }

    public void update(float f)
    {
        super.skipSurgeStateUpdate = true;
        super.update(f);
        trgtPk = getMissilePk();
        guidedMissileUtils.checkLockStatus();
        checkAIlaunchMissile();
        if(super.FM.getSpeed() > 5F)
        {
            moveSlats(f);
            super.bSlatsOff = false;
        } else
        {
            slatsOff();
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.45F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 1.001F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 5);
                else
                if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.65F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() < 1.001F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 3);
                else
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 2);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 0);
            }
            setExhaustFlame(java.lang.Math.round(com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getThrustOutput(), 0.7F, 0.87F, 0.0F, 12F)), 0);
        }
    }

    protected void moveSlats(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.15F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 0.1F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.065F);
        hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 8.5F), 0.0F);
        hierMesh().chunkSetLocate("SlatL_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, -0.1F);
        hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(super.FM.getAOA(), 6.8F, 15F, 0.0F, 8.5F), 0.0F);
        hierMesh().chunkSetLocate("SlatR_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void slatsOff()
    {
        if(!super.bSlatsOff)
        {
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.15F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.1F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = -0.065F;
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 8.5F, 0.0F);
            hierMesh().chunkSetLocate("SlatL_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            com.maddox.il2.objects.air.Aircraft.xyz[1] = -0.1F;
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 8.5F, 0.0F);
            hierMesh().chunkSetLocate("SlatR_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            super.bSlatsOff = true;
        }
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.95F, 0.0F, 0.9F);
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 0.0F, 45F * f);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    private com.maddox.il2.objects.weapons.GuidedMissileUtils guidedMissileUtils;
    private float trgtPk;
    private com.maddox.il2.engine.Actor trgtAI;
    private com.maddox.il2.engine.Eff3DActor turbo;
    private com.maddox.il2.engine.Eff3DActor turbosmoke;
    private com.maddox.il2.engine.Eff3DActor afterburner;
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

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F_86K");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/F_86K(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_de", "3DO/Plane/F_86K(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_de", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_it", "3DO/Plane/F_86K(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_it", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_du", "3DO/Plane/F_86K(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_du", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1949.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F-86K.fmd");
        try
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitF_86K")
            })));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in F-86K Cockpit init, " + exception.getMessage());
        }
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 9, 9, 9, 2, 
            2, 9, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev07", "_ExternalRock01", 
            "_ExternalRock01", "_ExternalDev08", "_ExternalRock02", "_ExternalRock02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9B", new java.lang.String[] {
            "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", null, null, null, null, "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", 
            "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAIM9B+2x200dt", new java.lang.String[] {
            "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "MGunColtMk12ki 132", "PylonF86_Outboard 1", "PylonF86_Outboard 1", "FuelTankGun_200galL 1", "FuelTankGun_200galR 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", 
            "BombGunNull 1", "PylonF86_Sidewinder 1", "RocketGunAIM9B 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
