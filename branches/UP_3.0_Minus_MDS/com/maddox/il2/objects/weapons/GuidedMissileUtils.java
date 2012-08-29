// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   GuidedMissileUtils.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelMainEx;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.NetSafeLog;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class GuidedMissileUtils
{

    public GuidedMissileUtils(com.maddox.il2.engine.Actor owner)
    {
        selectedActorOffset = null;
        iMissileLockState = 0;
        iMissileTone = 0;
        tLastSeenEnemy = 0L;
        missileOwner = null;
        trgtMissile = null;
        fm = null;
        fl1 = null;
        fl2 = null;
        or = new Orient();
        p = new Point3d();
        orVictimOffset = null;
        pVictimOffset = null;
        pT = null;
        v = null;
        victimSpeed = null;
        tStart = 0L;
        prevd = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        d = 0.0D;
        victim = null;
        fMissileMaxSpeedKmh = 2000F;
        fMissileBaseSpeedKmh = 0.0F;
        launchKren = 0.0D;
        launchPitch = 0.0D;
        launchYaw = 0.0D;
        fLeadPercent = 0.0F;
        fPkMaxAngle = 30F;
        fPkMaxAngleAft = 70F;
        fPkMinDist = 400F;
        fPkOptDist = 1500F;
        fPkMaxDist = 4500F;
        fPkMaxG = 2.0F;
        fMaxG = 12F;
        fStepsForFullTurn = 10F;
        fMaxPOVfrom = 25F;
        fMaxPOVto = 60F;
        fMaxDistance = 4500F;
        iDetectorMode = 0;
        initParams(owner);
    }

    public GuidedMissileUtils(com.maddox.il2.engine.Actor theOwner, float theMissileMaxSpeedMeterPerSecond, float theLeadPercent, float theMaxG, float theStepsForFullTurn, float thePkMaxAngle, float thePkMaxAngleAft, 
            float thePkMinDist, float thePkOptDist, float thePkMaxDist, float thePkMaxG, float theMaxPOVfrom, float theMaxPOVto, float theMaxDistance, 
            java.lang.String theLockFx, java.lang.String theNoLockFx, java.lang.String theLockSmpl, java.lang.String theNoLockSmpl)
    {
        selectedActorOffset = null;
        iMissileLockState = 0;
        iMissileTone = 0;
        tLastSeenEnemy = 0L;
        missileOwner = null;
        trgtMissile = null;
        fm = null;
        fl1 = null;
        fl2 = null;
        or = new Orient();
        p = new Point3d();
        orVictimOffset = null;
        pVictimOffset = null;
        pT = null;
        v = null;
        victimSpeed = null;
        tStart = 0L;
        prevd = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        d = 0.0D;
        victim = null;
        fMissileMaxSpeedKmh = 2000F;
        fMissileBaseSpeedKmh = 0.0F;
        launchKren = 0.0D;
        launchPitch = 0.0D;
        launchYaw = 0.0D;
        fLeadPercent = 0.0F;
        fPkMaxAngle = 30F;
        fPkMaxAngleAft = 70F;
        fPkMinDist = 400F;
        fPkOptDist = 1500F;
        fPkMaxDist = 4500F;
        fPkMaxG = 2.0F;
        fMaxG = 12F;
        fStepsForFullTurn = 10F;
        fMaxPOVfrom = 25F;
        fMaxPOVto = 60F;
        fMaxDistance = 4500F;
        iDetectorMode = 0;
        initParams(theOwner, theMissileMaxSpeedMeterPerSecond, theLeadPercent, theMaxG, theStepsForFullTurn, thePkMaxAngle, thePkMaxAngleAft, thePkMinDist, thePkOptDist, thePkMaxDist, thePkMaxG, theMaxPOVfrom, theMaxPOVto, theMaxDistance, theLockFx, theNoLockFx, theLockSmpl, theNoLockSmpl);
    }

    public void setMissileOwner(com.maddox.il2.engine.Actor value)
    {
        missileOwner = value;
    }

    public void setMissileMaxSpeedKmh(float value)
    {
        fMissileMaxSpeedKmh = value;
    }

    public void setLeadPercent(float value)
    {
        fLeadPercent = value;
    }

    public void setMaxG(float value)
    {
        fMaxG = value;
    }

    public void setStepsForFullTurn(float value)
    {
        fStepsForFullTurn = value;
    }

    public void setPkMaxAngle(float value)
    {
        fPkMaxAngle = value;
    }

    public void setPkMaxAngleAft(float value)
    {
        fPkMaxAngleAft = value;
    }

    public void setPkMinDist(float value)
    {
        fPkMinDist = value;
    }

    public void setPkOptDist(float value)
    {
        fPkOptDist = value;
    }

    public void setPkMaxDist(float value)
    {
        fPkMaxDist = value;
    }

    public void setPkMaxG(float value)
    {
        fPkMaxG = value;
    }

    public void setMaxPOVfrom(float value)
    {
        fMaxPOVfrom = value;
    }

    public void setMaxPOVto(float value)
    {
        fMaxPOVto = value;
    }

    public void setMaxDistance(float value)
    {
        fMaxDistance = value;
    }

    public void setFxMissileToneLock(java.lang.String value)
    {
        fxMissileToneLock = missileOwner.newSound(value, false);
    }

    public void setFxMissileToneNoLock(java.lang.String value)
    {
        fxMissileToneNoLock = missileOwner.newSound(value, false);
    }

    public void setSmplMissileLock(java.lang.String value)
    {
        smplMissileLock = new Sample(value, 256, 65535);
        smplMissileLock.setInfinite(true);
    }

    public void setSmplMissileNoLock(java.lang.String value)
    {
        smplMissileNoLock = new Sample(value, 256, 65535);
        smplMissileNoLock.setInfinite(true);
    }

    public void setLockTone(java.lang.String theLockPrs, java.lang.String theNoLockPrs, java.lang.String theLockWav, java.lang.String theNoLockWav)
    {
        fxMissileToneLock = missileOwner.newSound(theLockPrs, false);
        fxMissileToneNoLock = missileOwner.newSound(theNoLockPrs, false);
        smplMissileLock = new Sample(theLockWav, 256, 65535);
        smplMissileLock.setInfinite(true);
        smplMissileNoLock = new Sample(theNoLockWav, 256, 65535);
        smplMissileNoLock.setInfinite(true);
    }

    public void setMissileName(java.lang.String theMissileName)
    {
        missileName = theMissileName;
    }

    public void setMissileGrowl(int growl)
    {
        iMissileTone = growl;
    }

    public com.maddox.il2.engine.Actor getMissileOwner()
    {
        return missileOwner;
    }

    public float getMissileMaxSpeedKmh()
    {
        return fMissileMaxSpeedKmh;
    }

    public float getLeadPercent()
    {
        return fLeadPercent;
    }

    public float getMaxG()
    {
        return fMaxG;
    }

    public float getStepsForFullTurn()
    {
        return fStepsForFullTurn;
    }

    public float getPkMaxAngle()
    {
        return fPkMaxAngle;
    }

    public float getPkMaxAngleAft()
    {
        return fPkMaxAngleAft;
    }

    public float getPkMinDist()
    {
        return fPkMinDist;
    }

    public float getPkOptDist()
    {
        return fPkOptDist;
    }

    public float getPkMaxDist()
    {
        return fPkMaxDist;
    }

    public float getPkMaxG()
    {
        return fPkMaxG;
    }

    public float getMaxPOVfrom()
    {
        return fMaxPOVfrom;
    }

    public float getMaxPOVto()
    {
        return fMaxPOVto;
    }

    public float getMaxDistance()
    {
        return fMaxDistance;
    }

    public com.maddox.sound.SoundFX getFxMissileToneLock()
    {
        return fxMissileToneLock;
    }

    public com.maddox.sound.SoundFX getFxMissileToneNoLock()
    {
        return fxMissileToneNoLock;
    }

    public com.maddox.sound.Sample getSmplMissileLock()
    {
        return smplMissileLock;
    }

    public com.maddox.sound.Sample getSmplMissileNoLock()
    {
        return smplMissileNoLock;
    }

    public com.maddox.il2.engine.Actor getMissileTarget()
    {
        return trgtMissile;
    }

    public void setMissileTarget(com.maddox.il2.engine.Actor theTarget)
    {
        trgtMissile = theTarget;
    }

    public int getMissileLockState()
    {
        return iMissileLockState;
    }

    public int getMissileGrowl()
    {
        return iMissileTone;
    }

    private void initParams(com.maddox.il2.engine.Actor theOwner, float theMissileMaxSpeedMeterPerSecond, float theLeadPercent, float theMaxG, float theStepsForFullTurn, float thePkMaxAngle, float thePkMaxAngleAft, 
            float thePkMinDist, float thePkOptDist, float thePkMaxDist, float thePkMaxG, float theMaxPOVfrom, float theMaxPOVto, float theMaxDistance, 
            java.lang.String theLockFx, java.lang.String theNoLockFx, java.lang.String theLockSmpl, java.lang.String theNoLockSmpl)
    {
        initCommon();
        missileOwner = theOwner;
        fMissileMaxSpeedKmh = theMissileMaxSpeedMeterPerSecond;
        fLeadPercent = theLeadPercent;
        fMaxG = theMaxG;
        fStepsForFullTurn = theStepsForFullTurn;
        fPkMaxAngle = thePkMaxAngle;
        fPkMaxAngleAft = thePkMaxAngleAft;
        fPkMinDist = thePkMinDist;
        fPkOptDist = thePkOptDist;
        fPkMaxDist = thePkMaxDist;
        fPkMaxG = thePkMaxG;
        fMaxPOVfrom = theMaxPOVfrom;
        fMaxPOVto = theMaxPOVto;
        fMaxDistance = theMaxDistance;
        fxMissileToneLock = missileOwner.newSound(theLockFx, false);
        fxMissileToneNoLock = missileOwner.newSound(theNoLockFx, false);
        smplMissileLock = new Sample(theLockSmpl, 256, 65535);
        smplMissileLock.setInfinite(true);
        smplMissileNoLock = new Sample(theNoLockSmpl, 256, 65535);
        smplMissileNoLock.setInfinite(true);
    }

    private void initParams(com.maddox.il2.engine.Actor theOwner)
    {
        initCommon();
        if(theOwner instanceof com.maddox.il2.objects.air.Aircraft)
            missileOwner = theOwner;
    }

    private void initCommon()
    {
        selectedActorOffset = new Point3f();
        engageMode = 0;
        iMissileLockState = 0;
        iMissileTone = 0;
        tLastSeenEnemy = com.maddox.rts.Time.current() - 20000L;
        oldBreakControl = true;
    }

    public void createMissileList(java.util.ArrayList theMissileList)
    {
        com.maddox.il2.objects.air.Aircraft theMissileCarrier = (com.maddox.il2.objects.air.Aircraft)missileOwner;
        java.lang.Class theMissileClass = null;
        try
        {
            for(int l = 0; l < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons.length; l++)
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l] != null)
                {
                    for(int l1 = 0; l1 < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l].length; l1++)
                    {
                        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l][l1] == null || !(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l][l1] instanceof com.maddox.il2.objects.weapons.RocketGun))
                            continue;
                        java.lang.Class theBulletClass = ((com.maddox.il2.objects.weapons.RocketGun)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l][l1]).bulletClass();
                        if((com.maddox.il2.objects.weapons.Missile.class).isAssignableFrom(theBulletClass))
                        {
                            theMissileClass = theBulletClass;
                            theMissileList.add(((java.lang.Object) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (theMissileCarrier)).FM)).CT.Weapons[l][l1])));
                        }
                    }

                }

        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in initParams: " + ((java.lang.Throwable) (exception)).getMessage());
        }
        if(theMissileClass == null)
        {
            return;
        } else
        {
            fPkMaxG = com.maddox.rts.Property.floatValue(theMissileClass, "maxLockGForce", 99.9F);
            fMaxPOVfrom = com.maddox.rts.Property.floatValue(theMissileClass, "maxFOVfrom", 99.9F);
            fMaxPOVto = com.maddox.rts.Property.floatValue(theMissileClass, "maxFOVto", 99.9F);
            fPkMaxAngle = com.maddox.rts.Property.floatValue(theMissileClass, "PkMaxFOVfrom", 99.9F);
            fPkMaxAngleAft = com.maddox.rts.Property.floatValue(theMissileClass, "PkMaxFOVto", 99.9F);
            fPkMinDist = com.maddox.rts.Property.floatValue(theMissileClass, "PkDistMin", 99.9F);
            fPkOptDist = com.maddox.rts.Property.floatValue(theMissileClass, "PkDistOpt", 99.9F);
            fPkMaxDist = com.maddox.rts.Property.floatValue(theMissileClass, "PkDistMax", 99.9F);
            fMissileMaxSpeedKmh = com.maddox.rts.Property.floatValue(theMissileClass, "maxSpeed", 99.9F);
            fLeadPercent = com.maddox.rts.Property.floatValue(theMissileClass, "leadPercent", 99.9F);
            fMaxG = com.maddox.rts.Property.floatValue(theMissileClass, "maxGForce", 99.9F);
            iDetectorMode = com.maddox.rts.Property.intValue(theMissileClass, "detectorType", 0);
            fxMissileToneLock = missileOwner.newSound(com.maddox.rts.Property.stringValue(theMissileClass, "fxLock", "weapon.AIM9.lock"), false);
            fxMissileToneNoLock = missileOwner.newSound(com.maddox.rts.Property.stringValue(theMissileClass, "fxNoLock", "weapon.AIM9.nolock"), false);
            smplMissileLock = new Sample(com.maddox.rts.Property.stringValue(theMissileClass, "smplLock", "AIM9_lock.wav"), 256, 65535);
            smplMissileLock.setInfinite(true);
            smplMissileNoLock = new Sample(com.maddox.rts.Property.stringValue(theMissileClass, "smplNoLock", "AIM9_no_lock.wav"), 256, 65535);
            smplMissileNoLock.setInfinite(true);
            missileName = com.maddox.rts.Property.stringValue(theMissileClass, "friendlyName", "Missile");
            return;
        }
    }

    public static float angleBetween(com.maddox.il2.engine.Actor actorFrom, com.maddox.il2.engine.Actor actorTo)
    {
        float angleRetVal = 180.1F;
        if(!(actorFrom instanceof com.maddox.il2.objects.air.Aircraft) || !(actorTo instanceof com.maddox.il2.objects.air.Aircraft))
        {
            return angleRetVal;
        } else
        {
            double angleDoubleTemp = 0.0D;
            com.maddox.il2.engine.Loc angleActorLoc = new Loc();
            com.maddox.JGP.Point3d angleActorPos = new Point3d();
            com.maddox.JGP.Point3d angleTargetPos = new Point3d();
            com.maddox.JGP.Vector3d angleTargRayDir = new Vector3d();
            com.maddox.JGP.Vector3d angleNoseDir = new Vector3d();
            actorFrom.pos.getAbs(angleActorLoc);
            angleActorLoc.get(((com.maddox.JGP.Tuple3d) (angleActorPos)));
            actorTo.pos.getAbs(angleTargetPos);
            ((com.maddox.JGP.Tuple3d) (angleTargRayDir)).sub(((com.maddox.JGP.Tuple3d) (angleTargetPos)), ((com.maddox.JGP.Tuple3d) (angleActorPos)));
            angleDoubleTemp = angleTargRayDir.length();
            ((com.maddox.JGP.Tuple3d) (angleTargRayDir)).scale(1.0D / angleDoubleTemp);
            ((com.maddox.JGP.Tuple3d) (angleNoseDir)).set(1.0D, 0.0D, 0.0D);
            angleActorLoc.transform(angleNoseDir);
            angleDoubleTemp = angleNoseDir.dot(((com.maddox.JGP.Tuple3d) (angleTargRayDir)));
            angleRetVal = com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.acos(angleDoubleTemp));
            return angleRetVal;
        }
    }

    public static float angleBetween(com.maddox.il2.engine.Actor actorFrom, com.maddox.JGP.Vector3f targetVector)
    {
        return com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(actorFrom, new Vector3d(((com.maddox.JGP.Tuple3f) (targetVector))));
    }

    public static float angleBetween(com.maddox.il2.engine.Actor actorFrom, com.maddox.JGP.Vector3d targetVector)
    {
        com.maddox.JGP.Vector3d theTargetVector = new Vector3d();
        ((com.maddox.JGP.Tuple3d) (theTargetVector)).set(((com.maddox.JGP.Tuple3d) (targetVector)));
        double angleDoubleTemp = 0.0D;
        com.maddox.il2.engine.Loc angleActorLoc = new Loc();
        com.maddox.JGP.Point3d angleActorPos = new Point3d();
        com.maddox.JGP.Vector3d angleNoseDir = new Vector3d();
        actorFrom.pos.getAbs(angleActorLoc);
        angleActorLoc.get(((com.maddox.JGP.Tuple3d) (angleActorPos)));
        angleDoubleTemp = theTargetVector.length();
        ((com.maddox.JGP.Tuple3d) (theTargetVector)).scale(1.0D / angleDoubleTemp);
        ((com.maddox.JGP.Tuple3d) (angleNoseDir)).set(1.0D, 0.0D, 0.0D);
        angleActorLoc.transform(angleNoseDir);
        angleDoubleTemp = angleNoseDir.dot(((com.maddox.JGP.Tuple3d) (theTargetVector)));
        return com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.acos(angleDoubleTemp));
    }

    public static float angleActorBetween(com.maddox.il2.engine.Actor actorFrom, com.maddox.il2.engine.Actor actorTo)
    {
        float angleRetVal = 180.1F;
        double angleDoubleTemp = 0.0D;
        com.maddox.il2.engine.Loc angleActorLoc = new Loc();
        com.maddox.JGP.Point3d angleActorPos = new Point3d();
        com.maddox.JGP.Point3d angleTargetPos = new Point3d();
        com.maddox.JGP.Vector3d angleTargRayDir = new Vector3d();
        com.maddox.JGP.Vector3d angleNoseDir = new Vector3d();
        actorFrom.pos.getAbs(angleActorLoc);
        angleActorLoc.get(((com.maddox.JGP.Tuple3d) (angleActorPos)));
        actorTo.pos.getAbs(angleTargetPos);
        ((com.maddox.JGP.Tuple3d) (angleTargRayDir)).sub(((com.maddox.JGP.Tuple3d) (angleTargetPos)), ((com.maddox.JGP.Tuple3d) (angleActorPos)));
        angleDoubleTemp = angleTargRayDir.length();
        ((com.maddox.JGP.Tuple3d) (angleTargRayDir)).scale(1.0D / angleDoubleTemp);
        ((com.maddox.JGP.Tuple3d) (angleNoseDir)).set(1.0D, 0.0D, 0.0D);
        angleActorLoc.transform(angleNoseDir);
        angleDoubleTemp = angleNoseDir.dot(((com.maddox.JGP.Tuple3d) (angleTargRayDir)));
        angleRetVal = com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.acos(angleDoubleTemp));
        return angleRetVal;
    }

    public static double distanceBetween(com.maddox.il2.engine.Actor actorFrom, com.maddox.il2.engine.Actor actorTo)
    {
        double distanceRetVal = 99999.998999999996D;
        if(!com.maddox.il2.engine.Actor.isValid(actorFrom) || !com.maddox.il2.engine.Actor.isValid(actorTo))
        {
            return distanceRetVal;
        } else
        {
            com.maddox.il2.engine.Loc distanceActorLoc = new Loc();
            com.maddox.JGP.Point3d distanceActorPos = new Point3d();
            com.maddox.JGP.Point3d distanceTargetPos = new Point3d();
            actorFrom.pos.getAbs(distanceActorLoc);
            distanceActorLoc.get(((com.maddox.JGP.Tuple3d) (distanceActorPos)));
            actorTo.pos.getAbs(distanceTargetPos);
            distanceRetVal = distanceActorPos.distance(distanceTargetPos);
            return distanceRetVal;
        }
    }

    public void playMissileGrowlLock(boolean isLocked)
    {
        if(isLocked)
        {
            ((com.maddox.sound.AudioStream) (fxMissileToneNoLock)).cancel();
            fxMissileToneLock.play(smplMissileLock);
        } else
        {
            ((com.maddox.sound.AudioStream) (fxMissileToneLock)).cancel();
            fxMissileToneNoLock.play(smplMissileNoLock);
        }
    }

    public void cancelMissileGrowl()
    {
        ((com.maddox.sound.AudioStream) (fxMissileToneLock)).cancel();
        ((com.maddox.sound.AudioStream) (fxMissileToneNoLock)).cancel();
    }

    private void changeMissileGrowl(int iMode)
    {
        if((missileOwner != com.maddox.il2.ai.World.getPlayerAircraft() || !((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)missileOwner)).FM).isRealMode()) && (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)missileOwner)).FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        setMissileGrowl(iMode);
        switch(iMode)
        {
        case 1: // '\001'
            playMissileGrowlLock(false);
            break;

        case 2: // '\002'
            playMissileGrowlLock(true);
            break;

        default:
            cancelMissileGrowl();
            break;
        }
    }

    public void checkLockStatus()
    {
        int iOldLockState;
        boolean bEnemyInSight;
        boolean bSidewinderLocked;
        boolean bNoEnemyTimeout;
        iOldLockState = iMissileLockState;
        bEnemyInSight = false;
        bSidewinderLocked = false;
        bNoEnemyTimeout = false;
        try
        {
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)missileOwner)).FM)).CT.BrakeControl == 1.0F)
            {
                if(!oldBreakControl)
                {
                    oldBreakControl = true;
                    if(!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)missileOwner)).FM)).Gears.onGround())
                    {
                        engageMode--;
                        if(engageMode < -1)
                            engageMode = 1;
                        switch(engageMode)
                        {
                        case -1: 
                            if(missileName != null)
                                com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " Engagement OFF");
                            break;

                        case 0: // '\0'
                            if(missileName != null)
                                com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " Engagement AUTO");
                            break;

                        case 1: // '\001'
                            if(missileName != null)
                                com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " Engagement ON");
                            break;
                        }
                    }
                }
            } else
            {
                oldBreakControl = false;
            }
        }
        catch(java.lang.Exception exception) { }
        if(missileOwner != com.maddox.il2.ai.World.getPlayerAircraft())
        {
            if(iMissileLockState != 0)
            {
                changeMissileGrowl(0);
                iMissileLockState = 0;
            }
            return;
        }
        if(!((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)missileOwner).hasMissiles())
        {
            if(iMissileLockState != 0)
            {
                changeMissileGrowl(0);
                com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " missiles depleted");
                iMissileLockState = 0;
            }
            return;
        }
        if(engageMode == -1)
        {
            if(iMissileLockState != 0)
            {
                changeMissileGrowl(0);
                com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " disengaged");
                iMissileLockState = 0;
            }
            return;
        }
        try
        {
            if(com.maddox.il2.engine.Actor.isValid(trgtMissile))
            {
                bSidewinderLocked = true;
                if(trgtMissile.getArmy() != ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).getArmy())
                    bEnemyInSight = true;
            }
            if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().viewActor()) && com.maddox.il2.game.Main3D.cur3D().viewActor() == missileOwner)
            {
                com.maddox.il2.engine.Actor theEnemy = com.maddox.il2.game.Selector.look(true, false, com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()], ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).getArmy(), -1, ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())), false);
                if(com.maddox.il2.engine.Actor.isValid(theEnemy))
                    bEnemyInSight = true;
            }
            if(bEnemyInSight)
                tLastSeenEnemy = com.maddox.rts.Time.current();
            else
            if(com.maddox.rts.Time.current() - tLastSeenEnemy > 10000L)
                bNoEnemyTimeout = true;
            if(bSidewinderLocked)
            {
                if(bEnemyInSight)
                    iMissileLockState = 2;
                else
                if(engageMode == 1)
                    iMissileLockState = 2;
                else
                if(bNoEnemyTimeout)
                    iMissileLockState = 0;
                else
                    iMissileLockState = 2;
            } else
            if(bNoEnemyTimeout)
                iMissileLockState = 0;
            else
                iMissileLockState = 1;
            if(engageMode == 1 && iMissileLockState == 0)
                iMissileLockState = 1;
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)missileOwner)).FM)).getOverload() > fPkMaxG && iMissileLockState == 2)
                iMissileLockState = 1;
            switch(iMissileLockState)
            {
            case 1: // '\001'
                if(iOldLockState != 1)
                    changeMissileGrowl(1);
                if(iOldLockState == 0)
                    com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " engaged");
                break;

            case 2: // '\002'
                if(iOldLockState != 2)
                    changeMissileGrowl(2);
                if(iOldLockState == 0)
                    com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " engaged");
                break;

            case 0: // '\0'
                if(iOldLockState != 0)
                {
                    changeMissileGrowl(0);
                    com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " disengaged");
                }
                break;

            default:
                if(iOldLockState != 0)
                {
                    changeMissileGrowl(0);
                    com.maddox.il2.game.NetSafeLog.log(missileOwner, missileName + " disengaged");
                }
                break;
            }
        }
        catch(java.lang.Exception exception) { }
        return;
    }

    public com.maddox.JGP.Point3f getSelectedActorOffset()
    {
        return selectedActorOffset;
    }

    public float Pk(com.maddox.il2.engine.Actor actorFrom, com.maddox.il2.engine.Actor actorTo)
    {
        float fPkRet = 0.0F;
        float fTemp = 0.0F;
        if(!(actorFrom instanceof com.maddox.il2.objects.air.Aircraft) || !(actorTo instanceof com.maddox.il2.objects.air.Aircraft))
            return fPkRet;
        float angleToTarget = com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(actorFrom, actorTo);
        float angleFromTarget = 180F - com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(actorTo, actorFrom);
        float distanceToTarget = (float)com.maddox.il2.objects.weapons.GuidedMissileUtils.distanceBetween(actorFrom, actorTo);
        float gForce = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)actorFrom)).FM)).getOverload();
        float fMaxLaunchLoad = fPkMaxG;
        if(!(((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)actorFrom)).FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)(com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)actorFrom)).FM).isRealMode())
            fMaxG *= 2.0F;
        fPkRet = 100F;
        if(distanceToTarget > fPkMaxDist || distanceToTarget < fPkMinDist || angleToTarget > fPkMaxAngle || angleFromTarget > fPkMaxAngleAft || gForce > fMaxLaunchLoad)
            return 0.0F;
        if(distanceToTarget > fPkOptDist)
        {
            fTemp = distanceToTarget - fPkOptDist;
            fTemp /= fPkMaxDist - fPkOptDist;
            fPkRet -= fTemp * fTemp * 20F;
        } else
        {
            fTemp = fPkOptDist - distanceToTarget;
            fTemp /= fPkOptDist - fPkMinDist;
            fPkRet -= fTemp * fTemp * 60F;
        }
        fTemp = angleToTarget / fPkMaxAngle;
        fPkRet -= fTemp * fTemp * 30F;
        fTemp = angleFromTarget / fPkMaxAngleAft;
        fPkRet -= fTemp * fTemp * 50F;
        fTemp = gForce / fMaxLaunchLoad;
        fPkRet -= fTemp * fTemp * 30F;
        if(fPkRet < 0.0F)
            fPkRet = 0.0F;
        return fPkRet;
    }

    private int engineHeatSpreadType(com.maddox.il2.engine.Actor theActor)
    {
        com.maddox.il2.fm.EnginesInterface checkEI = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)(com.maddox.il2.objects.sounds.SndAircraft)theActor).FM)).EI;
        int iRetVal = HEAT_SPREAD_NONE;
        for(int i = 0; i < checkEI.getNum(); i++)
        {
            int iMotorType = checkEI.engines[i].getType();
            if(iMotorType == 2 || iMotorType == 3 || iMotorType == 4 || iMotorType == 6)
                iRetVal |= HEAT_SPREAD_AFT;
            if(iMotorType == 0 || iMotorType == 1 || iMotorType == 7 || iMotorType == 8)
                iRetVal |= HEAT_SPREAD_360;
        }

        return iRetVal;
    }

    private int engineHeatSpreadType(com.maddox.il2.fm.Motor theMotor)
    {
        int iRetVal = HEAT_SPREAD_NONE;
        int iMotorType = theMotor.getType();
        if(iMotorType == 2 || iMotorType == 3 || iMotorType == 4 || iMotorType == 6)
            iRetVal |= HEAT_SPREAD_AFT;
        if(iMotorType == 0 || iMotorType == 1 || iMotorType == 7 || iMotorType == 8)
            iRetVal |= HEAT_SPREAD_360;
        return iRetVal;
    }

    public com.maddox.il2.engine.Actor lookForGuidedMissileTarget(com.maddox.il2.engine.Actor actor, float maxFOVfrom, float maxFOVto, double maxDistance)
    {
        double targetDistance = 0.0D;
        float targetAngle = 0.0F;
        float targetAngleAft = 0.0F;
        float targetBait = 0.0F;
        float maxTargetBait = 0.0F;
        com.maddox.il2.engine.Actor selectedActor = null;
        com.maddox.JGP.Point3f theSelectedActorOffset = new Point3f(0.0F, 0.0F, 0.0F);
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft) || iDetectorMode == 0)
            return selectedActor;
        try
        {
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int k = list.size();
            for(int i1 = 0; i1 < k; i1++)
            {
                com.maddox.il2.engine.Actor theTarget1 = (com.maddox.il2.engine.Actor)list.get(i1);
                if(theTarget1 instanceof com.maddox.il2.objects.air.Aircraft)
                {
                    targetDistance = com.maddox.il2.objects.weapons.GuidedMissileUtils.distanceBetween(actor, theTarget1);
                    if(targetDistance <= maxDistance)
                    {
                        targetAngle = com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(actor, theTarget1);
                        if(targetAngle <= maxFOVfrom)
                        {
                            targetAngleAft = 180F - com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(theTarget1, actor);
                            if(targetAngleAft <= maxFOVto || iDetectorMode != 1 || (engineHeatSpreadType(theTarget1) & HEAT_SPREAD_360) != 0)
                                switch(iDetectorMode)
                                {
                                default:
                                    break;

                                case 1: // '\001'
                                    com.maddox.il2.fm.EnginesInterface theEI = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)(com.maddox.il2.objects.sounds.SndAircraft)theTarget1).FM)).EI;
                                    int iNumEngines = theEI.getNum();
                                    float maxEngineForce = 0.0F;
                                    int maxEngineForceEngineNo = 0;
                                    for(int i = 0; i < iNumEngines; i++)
                                    {
                                        com.maddox.il2.fm.Motor theMotor = theEI.engines[i];
                                        float theEngineForce = theMotor.getEngineForce().length();
                                        if(engineHeatSpreadType(theMotor) == HEAT_SPREAD_NONE)
                                            theEngineForce = 0.0F;
                                        if(engineHeatSpreadType(theMotor) == HEAT_SPREAD_360)
                                            theEngineForce /= 10F;
                                        if(theEngineForce > maxEngineForce)
                                        {
                                            maxEngineForce = theEngineForce;
                                            maxEngineForceEngineNo = i;
                                        }
                                    }

                                    targetBait = maxEngineForce / targetAngle / (float)(targetDistance * targetDistance);
                                    if(!theTarget1.isAlive())
                                        targetBait /= 10F;
                                    if(targetBait > maxTargetBait)
                                    {
                                        maxTargetBait = targetBait;
                                        selectedActor = theTarget1;
                                        theSelectedActorOffset = theEI.engines[maxEngineForceEngineNo].getEnginePos();
                                    }
                                    break;

                                case 2: // '\002'
                                case 3: // '\003'
                                case 4: // '\004'
                                    com.maddox.il2.fm.Mass theM = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)(com.maddox.il2.objects.sounds.SndAircraft)theTarget1).FM)).M;
                                    float fACMass = theM.getFullMass();
                                    targetBait = fACMass / targetAngle / (float)(targetDistance * targetDistance);
                                    if(!theTarget1.isAlive())
                                        targetBait /= 10F;
                                    if(targetBait > maxTargetBait)
                                    {
                                        maxTargetBait = targetBait;
                                        selectedActor = theTarget1;
                                        float fGC = com.maddox.il2.fm.FlightModelMainEx.getFmGCenter(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)(com.maddox.il2.objects.sounds.SndAircraft)theTarget1).FM)));
                                        ((com.maddox.JGP.Tuple3f) (theSelectedActorOffset)).set(fGC, 0.0F, 0.0F);
                                    }
                                    break;
                                }
                        }
                    }
                }
            }

        }
        catch(java.lang.Exception e)
        {
            com.maddox.il2.ai.EventLog.type("Exception in selectedActor");
            com.maddox.il2.ai.EventLog.type(((java.lang.Throwable) (e)).toString());
            com.maddox.il2.ai.EventLog.type(((java.lang.Throwable) (e)).getMessage());
        }
        ((com.maddox.JGP.Tuple3f) (selectedActorOffset)).set(((com.maddox.JGP.Tuple3f) (theSelectedActorOffset)));
        return selectedActor;
    }

    private com.maddox.JGP.Point3f selectedActorOffset;
    private com.maddox.sound.SoundFX fxMissileToneLock;
    private com.maddox.sound.SoundFX fxMissileToneNoLock;
    private com.maddox.sound.Sample smplMissileLock;
    private com.maddox.sound.Sample smplMissileNoLock;
    private int iMissileLockState;
    private int iMissileTone;
    private long tLastSeenEnemy;
    private final int ENGAGE_OFF = -1;
    private final int ENGAGE_AUTO = 0;
    private final int ENGAGE_ON = 1;
    private int engageMode;
    private com.maddox.il2.engine.Actor missileOwner;
    private boolean oldBreakControl;
    private com.maddox.il2.engine.Actor trgtMissile;
    private java.lang.String missileName;
    protected com.maddox.il2.fm.FlightModel fm;
    protected com.maddox.il2.engine.Eff3DActor fl1;
    protected com.maddox.il2.engine.Eff3DActor fl2;
    protected com.maddox.il2.engine.Orient or;
    protected com.maddox.JGP.Point3d p;
    protected com.maddox.il2.engine.Orient orVictimOffset;
    protected com.maddox.JGP.Point3f pVictimOffset;
    protected com.maddox.JGP.Point3d pT;
    protected com.maddox.JGP.Vector3d v;
    protected com.maddox.JGP.Vector3d victimSpeed;
    protected long tStart;
    protected float prevd;
    protected float deltaAzimuth;
    protected float deltaTangage;
    protected double d;
    protected com.maddox.il2.engine.Actor victim;
    protected float fMissileMaxSpeedKmh;
    protected float fMissileBaseSpeedKmh;
    protected double launchKren;
    protected double launchPitch;
    protected double launchYaw;
    protected float oldDeltaAzimuth;
    protected float oldDeltaTangage;
    private float fLeadPercent;
    private float fPkMaxAngle;
    private float fPkMaxAngleAft;
    private float fPkMinDist;
    private float fPkOptDist;
    private float fPkMaxDist;
    private float fPkMaxG;
    private float fMaxG;
    private float fStepsForFullTurn;
    private float fMaxPOVfrom;
    private float fMaxPOVto;
    private float fMaxDistance;
    private int iDetectorMode;
    public static int HEAT_SPREAD_NONE = 0;
    public static int HEAT_SPREAD_AFT = 1;
    public static int HEAT_SPREAD_360 = 2;

}
