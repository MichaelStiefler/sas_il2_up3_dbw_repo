// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Mig_17AS.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
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
//            Mig_17, Aircraft, PaintSchemeFCSPar1956, PaintSchemeFMPar1956, 
//            PaintSchemeFMPar06, TypeGuidedMissileCarrier, TypeCountermeasure, TypeThreatDetector, 
//            TypeGSuit, NetAircraft

public class Mig_17AS extends com.maddox.il2.objects.air.Mig_17
    implements com.maddox.il2.objects.air.TypeGuidedMissileCarrier, com.maddox.il2.objects.air.TypeCountermeasure, com.maddox.il2.objects.air.TypeThreatDetector, com.maddox.il2.objects.air.TypeGSuit
{

    public Mig_17AS()
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
        guidedMissileUtils = new GuidedMissileUtils(((com.maddox.il2.engine.Actor) (this)));
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
        gfactors.setGFactors(1.0F, 1.0F, 1.0F, 1.8F, 1.5F, 1.0F);
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
        if(super.FM.isPlayers())
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasCockpitDoorControl = true;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.dvCockpitDoor = 0.5F;
        }
        rocketsList.clear();
        guidedMissileUtils.createMissileList(rocketsList);
    }

    public void typeFighterAceMakerRangeFinder()
    {
        if(super.k14Mode == 0)
            return;
        hunted = com.maddox.il2.game.Main3D.cur3D().getViewPadlockEnemy();
        if(hunted == null)
            hunted = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.War.GetNearestEnemyAircraft(((com.maddox.il2.engine.Interpolate) (super.FM)).actor, 2000F, 9)));
        if(hunted != null)
        {
            super.k14Distance = (float)((com.maddox.il2.engine.Interpolate) (super.FM)).actor.pos.getAbsPoint().distance(hunted.pos.getAbsPoint());
            if(super.k14Distance > 1700F)
                super.k14Distance = 1700F;
            else
            if(super.k14Distance < 200F)
                super.k14Distance = 200F;
        }
    }

    public void update(float f)
    {
        super.update(f);
        typeFighterAceMakerRangeFinder();
        trgtPk = getMissilePk();
        guidedMissileUtils.checkLockStatus();
        checkAIlaunchMissile();
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
    private static com.maddox.il2.engine.Actor hunted = null;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "MiG-17AS");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ru", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ru", ((java.lang.Object) (new PaintSchemeFCSPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_sk", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_sk", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ro", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ro", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_hu", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_hu", ((java.lang.Object) (new PaintSchemeFMPar1956())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/MiG-17F(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1952.11F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1960.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/MiG-17F.fmd");
        try
        {
            com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitMig_17")
            })));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in MiG-17AS Cockpit init, " + exception.getMessage());
        }
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.725F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 0, 0, 9, 9, 9, 9, 9, 9, 2, 
            2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 9, 9, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 
            9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 9, 9, 3, 3, 3, 3, 3, 
            3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock04", "_ExternalRock04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock05", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock06", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", 
            "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", 
            "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", 
            "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalRock37", "_ExternalRock38", "_ExternalDev13", 
            "_ExternalDev14", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalRock43", "_ExternalRock44", "_ExternalRock45", "_ExternalRock46", "_ExternalRock47", 
            "_ExternalRock48", "_ExternalRock49", "_ExternalRock50", "_ExternalRock51", "_ExternalRock52", "_ExternalRock53", "_ExternalRock54", "_ExternalRock55", "_ExternalRock56", "_ExternalRock57", 
            "_ExternalRock58", "_ExternalRock59", "_ExternalRock60", "_ExternalRock61", "_ExternalRock62", "_ExternalRock63", "_ExternalRock64", "_ExternalRock65", "_ExternalRock66", "_ExternalRock67", 
            "_ExternalRock68", "_ExternalRock69", "_ExternalRock70", "_ExternalDev15", "_ExternalDev16", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1", 
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1", 
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2x250m46", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1", 
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x100", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1", 
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", 
            "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250m46", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMARS2", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMARS2 1", 
            "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMARS2+2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonMARS2 1", 
            "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xORO57", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xORO57+2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xORO57", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xORO57+2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", 
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xK13A", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonK13A 1", "PylonK13A 1", "RocketGunK13A 1", 
            "BombGunNull 1", "RocketGunK13A 1", "BombGunNull 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xK13A+2xdrops", new java.lang.String[] {
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonK13A 1", "PylonK13A 1", "RocketGunK13A 1", 
            "BombGunNull 1", "RocketGunK13A 1", "BombGunNull 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
