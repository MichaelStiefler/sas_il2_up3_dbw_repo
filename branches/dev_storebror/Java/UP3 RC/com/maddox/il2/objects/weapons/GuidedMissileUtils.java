// Source File Name: GuidedMissileUtils.java
// Author:           Storebror
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;
// <editor-fold defaultstate="collapsed" desc="Imports">
import java.util.List;
//import java.text.DecimalFormat;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3f;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelMainEx;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.sound.SoundFX;
import com.maddox.sound.Sample;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.game.NetSafeLog;
import com.maddox.il2.game.Selector;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Time;
import com.maddox.rts.Property;
import com.maddox.il2.engine.Actor;// </editor-fold>

public class GuidedMissileUtils {
// <editor-fold defaultstate="collapsed" desc="Parameter Fields">

//  private DecimalFormat twoPlaces = new DecimalFormat("+000.00;-000.00");
  private Point3f selectedActorOffset = null;
  private SoundFX fxMissileToneLock;
  private SoundFX fxMissileToneNoLock;
  private Sample smplMissileLock;
  private Sample smplMissileNoLock;
  private int iMissileLockState = 0;
  private int iMissileTone = 0;
  private long tLastSeenEnemy = 0L;
  private final int ENGAGE_OFF = -1;
  private final int ENGAGE_AUTO = 0;
  private final int ENGAGE_ON = 1;
  private int engageMode;
  private Actor missileOwner = null;
  private boolean oldBreakControl;
  private Actor trgtMissile = null;
  private String missileName;
  protected FlightModel fm = null;
  protected Eff3DActor fl1 = null;
  protected Eff3DActor fl2 = null;
  protected Orient or = new Orient();
  protected Point3d p = new Point3d();
  protected Orient orVictimOffset = null;
  protected Point3f pVictimOffset = null;
  protected Point3d pT = null;
  protected Vector3d v = null;
  protected Vector3d victimSpeed = null;
  protected long tStart = 0L;
  protected float prevd = 0.0F;
  protected float deltaAzimuth = 0.0F;
  protected float deltaTangage = 0.0F;
  protected double d = 0.0D;
  protected Actor victim = null;
  protected float fMissileMaxSpeedKmh = 2000.0F;
  protected float fMissileBaseSpeedKmh = 0.0F;
  protected double launchKren = 0.0D;
  protected double launchPitch = 0.0D;
  protected double launchYaw = 0.0D;
  protected float oldDeltaAzimuth;
  protected float oldDeltaTangage;
  private float fLeadPercent = 0.0F; // 0 means tail chasing, 100 means full lead tracking
  private float fPkMaxAngle = 30.0F; // maximum Angle (from launching A/C POV) for Pk calculation
  private float fPkMaxAngleAft = 70.0F; // maximum Angle (from target back POV) for Pk calculation
  private float fPkMinDist = 400.0F; // minimum Distance for Pk calculation
  private float fPkOptDist = 1500.0F; // optimum Distance for Pk calculation
  private float fPkMaxDist = 4500.0F; // maximum Distance for Pk calculation
  private float fPkMaxG = 2.0F; // maximum G-Force for Pk calculation
  private float fMaxG = 12F; // maximum G-Force during flight
  private float fStepsForFullTurn = 10F; // update steps for maximum control surface output, higher value means slower reaction and smoother flight
  private float fMaxPOVfrom = 25.0F; // maximum Angle (from launching A/C POV) for lockon
  private float fMaxPOVto = 60.0F; // maximum Angle (from target back POV) for lockon
  private float fMaxDistance = 4500.0F; // maximum Distance for lockon
  private int iDetectorMode = 0;
  public static int HEAT_SPREAD_NONE = 0; // Engine produces no heat, i.e. Tow Gliders
  public static int HEAT_SPREAD_AFT = 1; // Engine emits heat aft of A/C, i.e. Jet/Rocket engines
  public static int HEAT_SPREAD_360 = 2; // Engine emits heat in all directions, i.e. Piston engines
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructors">

  public GuidedMissileUtils(Actor owner) {
    this.initParams(owner);
  }

  public GuidedMissileUtils(
          Actor theOwner,
          float theMissileMaxSpeedMeterPerSecond,
          float theLeadPercent,
          float theMaxG,
          float theStepsForFullTurn,
          float thePkMaxAngle,
          float thePkMaxAngleAft,
          float thePkMinDist,
          float thePkOptDist,
          float thePkMaxDist,
          float thePkMaxG,
          float theMaxPOVfrom,
          float theMaxPOVto,
          float theMaxDistance,
          String theLockFx,
          String theNoLockFx,
          String theLockSmpl,
          String theNoLockSmpl) {
    this.initParams(
            theOwner,
            theMissileMaxSpeedMeterPerSecond,
            theLeadPercent,
            theMaxG,
            theStepsForFullTurn,
            thePkMaxAngle,
            thePkMaxAngleAft,
            thePkMinDist,
            thePkOptDist,
            thePkMaxDist,
            thePkMaxG,
            theMaxPOVfrom,
            theMaxPOVto,
            theMaxDistance,
            theLockFx,
            theNoLockFx,
            theLockSmpl,
            theNoLockSmpl);
  }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Parameter Set Functions">

  public void setMissileOwner(Actor value) {
    this.missileOwner = value;
  }

  public void setMissileMaxSpeedKmh(float value) {
    this.fMissileMaxSpeedKmh = value;
  }

  public void setLeadPercent(float value) {
    this.fLeadPercent = value;
  }

  public void setMaxG(float value) {
    this.fMaxG = value;
  }

  public void setStepsForFullTurn(float value) {
    this.fStepsForFullTurn = value;
  }

  public void setPkMaxAngle(float value) {
    this.fPkMaxAngle = value;
  }

  public void setPkMaxAngleAft(float value) {
    this.fPkMaxAngleAft = value;
  }

  public void setPkMinDist(float value) {
    this.fPkMinDist = value;
  }

  public void setPkOptDist(float value) {
    this.fPkOptDist = value;
  }

  public void setPkMaxDist(float value) {
    this.fPkMaxDist = value;
  }

  public void setPkMaxG(float value) {
    this.fPkMaxG = value;
  }

  public void setMaxPOVfrom(float value) {
    this.fMaxPOVfrom = value;
  }

  public void setMaxPOVto(float value) {
    this.fMaxPOVto = value;
  }

  public void setMaxDistance(float value) {
    this.fMaxDistance = value;
  }

  public void setFxMissileToneLock(String value) {
    this.fxMissileToneLock = missileOwner.newSound(value, false);
  }

  public void setFxMissileToneNoLock(String value) {
    this.fxMissileToneNoLock = missileOwner.newSound(value, false);
  }

  public void setSmplMissileLock(String value) {
    this.smplMissileLock = new Sample(value, 256, 65535);
    this.smplMissileLock.setInfinite(true);
  }

  public void setSmplMissileNoLock(String value) {
    this.smplMissileNoLock = new Sample(value, 256, 65535);
    this.smplMissileNoLock.setInfinite(true);
  }

  public void setLockTone(String theLockPrs, String theNoLockPrs, String theLockWav, String theNoLockWav) {
    this.fxMissileToneLock = missileOwner.newSound(theLockPrs, false);
    this.fxMissileToneNoLock = missileOwner.newSound(theNoLockPrs, false);
    this.smplMissileLock = new Sample(theLockWav, 256, 65535);
    this.smplMissileLock.setInfinite(true);
    this.smplMissileNoLock = new Sample(theNoLockWav, 256, 65535);
    this.smplMissileNoLock.setInfinite(true);
  }

  public void setMissileName(String theMissileName) {
    this.missileName = theMissileName;
  }

  public void setMissileGrowl(int growl) {
    this.iMissileTone = growl;
  }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Parameter Get Functions">

  public Actor getMissileOwner() {
    return this.missileOwner;
  }

  public float getMissileMaxSpeedKmh() {
    return this.fMissileMaxSpeedKmh;
  }

  public float getLeadPercent() {
    return this.fLeadPercent;
  }

  public float getMaxG() {
    return this.fMaxG;
  }

  public float getStepsForFullTurn() {
    return this.fStepsForFullTurn;
  }

  public float getPkMaxAngle() {
    return this.fPkMaxAngle;
  }

  public float getPkMaxAngleAft() {
    return this.fPkMaxAngleAft;
  }

  public float getPkMinDist() {
    return this.fPkMinDist;
  }

  public float getPkOptDist() {
    return this.fPkOptDist;
  }

  public float getPkMaxDist() {
    return this.fPkMaxDist;
  }

  public float getPkMaxG() {
    return this.fPkMaxG;
  }

  public float getMaxPOVfrom() {
    return this.fMaxPOVfrom;
  }

  public float getMaxPOVto() {
    return this.fMaxPOVto;
  }

  public float getMaxDistance() {
    return this.fMaxDistance;
  }

  public SoundFX getFxMissileToneLock() {
    return this.fxMissileToneLock;
  }

  public SoundFX getFxMissileToneNoLock() {
    return this.fxMissileToneNoLock;
  }

  public Sample getSmplMissileLock() {
    return this.smplMissileLock;
  }

  public Sample getSmplMissileNoLock() {
    return this.smplMissileNoLock;
  }

  public Actor getMissileTarget() {
    return this.trgtMissile;
  }

  public void setMissileTarget(Actor theTarget) {
    this.trgtMissile = theTarget;
  }

  public int getMissileLockState() {
    return this.iMissileLockState;
  }

  public int getMissileGrowl() {
    return this.iMissileTone;
  }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Class Init">

  private void initParams(
          Actor theOwner,
          float theMissileMaxSpeedMeterPerSecond,
          float theLeadPercent,
          float theMaxG,
          float theStepsForFullTurn,
          float thePkMaxAngle,
          float thePkMaxAngleAft,
          float thePkMinDist,
          float thePkOptDist,
          float thePkMaxDist,
          float thePkMaxG,
          float theMaxPOVfrom,
          float theMaxPOVto,
          float theMaxDistance,
          String theLockFx,
          String theNoLockFx,
          String theLockSmpl,
          String theNoLockSmpl) {
    this.initCommon();
    this.missileOwner = theOwner;
    this.fMissileMaxSpeedKmh = theMissileMaxSpeedMeterPerSecond;
    this.fLeadPercent = theLeadPercent;
    this.fMaxG = theMaxG;
    this.fStepsForFullTurn = theStepsForFullTurn;
    this.fPkMaxAngle = thePkMaxAngle;
    this.fPkMaxAngleAft = thePkMaxAngleAft;
    this.fPkMinDist = thePkMinDist;
    this.fPkOptDist = thePkOptDist;
    this.fPkMaxDist = thePkMaxDist;
    this.fPkMaxG = thePkMaxG;
    this.fMaxPOVfrom = theMaxPOVfrom;
    this.fMaxPOVto = theMaxPOVto;
    this.fMaxDistance = theMaxDistance;
    this.fxMissileToneLock = missileOwner.newSound(theLockFx, false);
    this.fxMissileToneNoLock = missileOwner.newSound(theNoLockFx, false);
    this.smplMissileLock = new Sample(theLockSmpl, 256, 65535);
    this.smplMissileLock.setInfinite(true);
    this.smplMissileNoLock = new Sample(theNoLockSmpl, 256, 65535);
    this.smplMissileNoLock.setInfinite(true);

  }

  private void initParams(Actor theOwner) {
    this.initCommon();
    if (theOwner instanceof Aircraft) {
      this.missileOwner = theOwner;
    }
  }

  private void initCommon() {
    this.selectedActorOffset = new Point3f();
    this.engageMode = ENGAGE_AUTO;
    this.iMissileLockState = 0;
    this.iMissileTone = 0;
    this.tLastSeenEnemy = Time.current() - 20000;
    this.oldBreakControl = true;
  }

  public void createMissileList(java.util.ArrayList theMissileList) {
    Aircraft theMissileCarrier = (Aircraft) this.missileOwner;
    Class theMissileClass = null;
    try {
      for (int l = 0; l < theMissileCarrier.FM.CT.Weapons.length; l++) {
        if (theMissileCarrier.FM.CT.Weapons[l] != null) {
          for (int l1 = 0; l1 < theMissileCarrier.FM.CT.Weapons[l].length; l1++) {
            if (theMissileCarrier.FM.CT.Weapons[l][l1] != null) {
              if (theMissileCarrier.FM.CT.Weapons[l][l1] instanceof RocketGun) {
                Class theBulletClass = ((RocketGun) theMissileCarrier.FM.CT.Weapons[l][l1]).bulletClass();
                if (Missile.class.isAssignableFrom(theBulletClass)) { // We've found the first missile!
                  theMissileClass = theBulletClass;
                  theMissileList.add(theMissileCarrier.FM.CT.Weapons[l][l1]);
                }
              }
            }
          }
        }
      }
    } catch (Exception exception) {
      EventLog.type("Exception in initParams: " + exception.getMessage());
    }
    if (theMissileClass == null) {
      return;
    }
    this.fPkMaxG = Property.floatValue(theMissileClass, "maxLockGForce", 99.9F);
    this.fMaxPOVfrom = Property.floatValue(theMissileClass, "maxFOVfrom", 99.9F);
    this.fMaxPOVto = Property.floatValue(theMissileClass, "maxFOVto", 99.9F);
    this.fPkMaxAngle = Property.floatValue(theMissileClass, "PkMaxFOVfrom", 99.9F);
    this.fPkMaxAngleAft = Property.floatValue(theMissileClass, "PkMaxFOVto", 99.9F);
    this.fPkMinDist = Property.floatValue(theMissileClass, "PkDistMin", 99.9F);
    this.fPkOptDist = Property.floatValue(theMissileClass, "PkDistOpt", 99.9F);
    this.fPkMaxDist = Property.floatValue(theMissileClass, "PkDistMax", 99.9F);
    this.fMissileMaxSpeedKmh = Property.floatValue(theMissileClass, "maxSpeed", 99.9F);
    this.fLeadPercent = Property.floatValue(theMissileClass, "leadPercent", 99.9F);
    this.fMaxG = Property.floatValue(theMissileClass, "maxGForce", 99.9F);
    this.iDetectorMode = Property.intValue(theMissileClass, "detectorType", Missile.DETECTOR_TYPE_MANUAL);

    this.fxMissileToneLock = missileOwner.newSound(Property.stringValue(theMissileClass, "fxLock", "weapon.AIM9.lock"), false);
    this.fxMissileToneNoLock = missileOwner.newSound(Property.stringValue(theMissileClass, "fxNoLock", "weapon.AIM9.nolock"), false);

    this.smplMissileLock = new Sample(Property.stringValue(theMissileClass, "smplLock", "AIM9_lock.wav"), 256, 65535);
    this.smplMissileLock.setInfinite(true);
    this.smplMissileNoLock = new Sample(Property.stringValue(theMissileClass, "smplNoLock", "AIM9_no_lock.wav"), 256, 65535);
    this.smplMissileNoLock.setInfinite(true);

    this.missileName = Property.stringValue(theMissileClass, "friendlyName", "Missile");

  }

  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Static Methods">
  public static float angleBetween(Actor actorFrom, Actor actorTo) {
    float angleRetVal = 180.1F;
    if (!((actorFrom instanceof Aircraft) && (actorTo instanceof Aircraft))) {
      return angleRetVal;
    }
    double angleDoubleTemp = 0.0D;
    Loc angleActorLoc = new Loc();
    Point3d angleActorPos = new Point3d();
    Point3d angleTargetPos = new Point3d();
    Vector3d angleTargRayDir = new Vector3d();
    Vector3d angleNoseDir = new Vector3d();
    actorFrom.pos.getAbs(angleActorLoc);
    angleActorLoc.get(angleActorPos);
    actorTo.pos.getAbs(angleTargetPos);
    angleTargRayDir.sub(angleTargetPos, angleActorPos);
    angleDoubleTemp = angleTargRayDir.length();
    angleTargRayDir.scale(1.0D / angleDoubleTemp);
    angleNoseDir.set(1.0D, 0.0D, 0.0D);
    angleActorLoc.transform(angleNoseDir);
    angleDoubleTemp = angleNoseDir.dot(angleTargRayDir);
    angleRetVal = Geom.RAD2DEG((float) Math.acos(angleDoubleTemp));
    return angleRetVal;
  }

  public static float angleBetween(Actor actorFrom, Vector3f targetVector) {
    return angleBetween(actorFrom, new Vector3d(targetVector));
  }

  public static float angleBetween(Actor actorFrom, Vector3d targetVector) {
    Vector3d theTargetVector = new Vector3d();
    theTargetVector.set(targetVector);
    double angleDoubleTemp = 0.0D;
    Loc angleActorLoc = new Loc();
    Point3d angleActorPos = new Point3d();
    Vector3d angleNoseDir = new Vector3d();
    actorFrom.pos.getAbs(angleActorLoc);
    angleActorLoc.get(angleActorPos);
    angleDoubleTemp = theTargetVector.length();
    theTargetVector.scale(1.0D / angleDoubleTemp);
    angleNoseDir.set(1.0D, 0.0D, 0.0D);
    angleActorLoc.transform(angleNoseDir);
    angleDoubleTemp = angleNoseDir.dot(theTargetVector);
    return Geom.RAD2DEG((float) Math.acos(angleDoubleTemp));
  }

  public static float angleActorBetween(Actor actorFrom, Actor actorTo) {
    float angleRetVal = 180.1F;
    double angleDoubleTemp = 0.0D;
    Loc angleActorLoc = new Loc();
    Point3d angleActorPos = new Point3d();
    Point3d angleTargetPos = new Point3d();
    Vector3d angleTargRayDir = new Vector3d();
    Vector3d angleNoseDir = new Vector3d();
    actorFrom.pos.getAbs(angleActorLoc);
    angleActorLoc.get(angleActorPos);
    actorTo.pos.getAbs(angleTargetPos);
    angleTargRayDir.sub(angleTargetPos, angleActorPos);
    angleDoubleTemp = angleTargRayDir.length();
    angleTargRayDir.scale(1.0D / angleDoubleTemp);
    angleNoseDir.set(1.0D, 0.0D, 0.0D);
    angleActorLoc.transform(angleNoseDir);
    angleDoubleTemp = angleNoseDir.dot(angleTargRayDir);
    angleRetVal = Geom.RAD2DEG((float) Math.acos(angleDoubleTemp));
    return angleRetVal;
  }

  public static double distanceBetween(Actor actorFrom, Actor actorTo) {
    double distanceRetVal = 99999.999D;
    if (!(Actor.isValid(actorFrom)) || !(Actor.isValid(actorTo))) {
      return distanceRetVal;
    }
    Loc distanceActorLoc = new Loc();
    Point3d distanceActorPos = new Point3d();
    Point3d distanceTargetPos = new Point3d();
    actorFrom.pos.getAbs(distanceActorLoc);
    distanceActorLoc.get(distanceActorPos);
    actorTo.pos.getAbs(distanceTargetPos);
    distanceRetVal = distanceActorPos.distance(distanceTargetPos);
    return distanceRetVal;
  }
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Method Members">

  public void playMissileGrowlLock(boolean isLocked) {
    if (isLocked) {
      fxMissileToneNoLock.cancel();
      fxMissileToneLock.play(smplMissileLock);
    } else {
      fxMissileToneLock.cancel();
      fxMissileToneNoLock.play(smplMissileNoLock);
    }
  }

  public void cancelMissileGrowl() {
    fxMissileToneLock.cancel();
    fxMissileToneNoLock.cancel();
  }

  private void changeMissileGrowl(int iMode) {
    if ((this.missileOwner != World.getPlayerAircraft() || !((RealFlightModel) ((Aircraft) this.missileOwner).FM).isRealMode()) && (((Aircraft) this.missileOwner).FM instanceof Pilot)) {
      return;
    }
    this.setMissileGrowl(iMode);
    switch (iMode) {
      case 1: {
        this.playMissileGrowlLock(false);
        break;
      }
      case 2: {
        this.playMissileGrowlLock(true);
        break;
      }
      default: {
        this.cancelMissileGrowl();
        break;
      }
    }
  }

  public void checkLockStatus() {
    if (!Config.isUSE_RENDER()) {
      return;
    }
    int iOldLockState = this.iMissileLockState;
    boolean bEnemyInSight = false;
    boolean bSidewinderLocked = false;
    boolean bNoEnemyTimeout = false;
    try {
      if (((Aircraft) this.missileOwner).FM.CT.BrakeControl == 1.0F) {
        if (!this.oldBreakControl) {
          this.oldBreakControl = true;
          if (!((Aircraft) this.missileOwner).FM.Gears.onGround()) {
            this.engageMode--;
            if (this.engageMode < ENGAGE_OFF) {
              this.engageMode = ENGAGE_ON;
            }
            switch (this.engageMode) {
              case ENGAGE_OFF:
                if (missileName == null) {
                } else {
                  NetSafeLog.log(this.missileOwner, missileName + " Engagement OFF");
                }
                break;
              case ENGAGE_AUTO:
                if (missileName == null) {
                } else {
                  NetSafeLog.log(this.missileOwner, missileName + " Engagement AUTO");
                }
                break;
              case ENGAGE_ON:
                if (missileName == null) {
                } else {
                  NetSafeLog.log(this.missileOwner, missileName + " Engagement ON");
                }
                break;
            }
          }
        }
      } else {
        this.oldBreakControl = false;
      }
    } catch (Exception exception) {
    }
    try {
      if (this.missileOwner != World.getPlayerAircraft()) {
        if (this.iMissileLockState != 0) {
          changeMissileGrowl(0);
          this.iMissileLockState = 0;
        }
        return;
      }
      if (!((TypeGuidedMissileCarrier) this.missileOwner).hasMissiles()) {
        if (this.iMissileLockState != 0) {
          changeMissileGrowl(0);
          NetSafeLog.log(this.missileOwner, missileName + " missiles depleted");
          this.iMissileLockState = 0;
        }
        return;
      }
      if (this.engageMode == ENGAGE_OFF) {
        if (this.iMissileLockState != 0) {
          changeMissileGrowl(0);
          NetSafeLog.log(this.missileOwner, missileName + " disengaged");
          this.iMissileLockState = 0;
        }
        return;
      }

      if (Actor.isValid(this.trgtMissile)) {
        bSidewinderLocked = true;
        if (this.trgtMissile.getArmy() != World.getPlayerAircraft().getArmy()) {
          bEnemyInSight = true;
        }
      }

      if (Config.isUSE_RENDER()) {
        if (Actor.isValid(Main3D.cur3D().viewActor())) {
          if (Main3D.cur3D().viewActor() == this.missileOwner) {
            Actor theEnemy = Selector.look(true, false, Main3D.cur3D()._camera3D[Main3D.cur3D().getRenderIndx()], World.getPlayerAircraft().getArmy(), -1, World.getPlayerAircraft(), false);
            if (Actor.isValid(theEnemy)) {
              bEnemyInSight = true;
            }
          }
        }
      }

      if (bEnemyInSight) {
        this.tLastSeenEnemy = Time.current();
      } else {
        if (Time.current() - this.tLastSeenEnemy > 10000) {
          bNoEnemyTimeout = true;
        }
      }

      if (bSidewinderLocked) {
        if (bEnemyInSight) {
          this.iMissileLockState = 2;
        } else {
          if (this.engageMode == ENGAGE_ON) {
            this.iMissileLockState = 2;
          } else {
            if (bNoEnemyTimeout) {
              this.iMissileLockState = 0;
            } else {
              this.iMissileLockState = 2;
            }
          }
        }
      } else {
        if (bNoEnemyTimeout) {
          this.iMissileLockState = 0;
        } else {
          this.iMissileLockState = 1;
        }
      }

      if ((this.engageMode == ENGAGE_ON) && (this.iMissileLockState == 0)) {
        this.iMissileLockState = 1;
      }
      if ((((Aircraft) this.missileOwner).FM.getOverload() > this.fPkMaxG) && (this.iMissileLockState == 2)) {
        this.iMissileLockState = 1;
      }

      switch (this.iMissileLockState) {
        case 1: {
          if (iOldLockState != 1) {
            changeMissileGrowl(1);
          }
          if (iOldLockState == 0) {
            NetSafeLog.log(this.missileOwner, missileName + " engaged");
          }
          break;
        }
        case 2: {
          if (iOldLockState != 2) {
            changeMissileGrowl(2);
          }
          if (iOldLockState == 0) {
            NetSafeLog.log(this.missileOwner, missileName + " engaged");
          }
          break;
        }
        case 0: {
          if (iOldLockState != 0) {
            changeMissileGrowl(0);
            NetSafeLog.log(this.missileOwner, missileName + " disengaged");
          }
          break;
        }
        default: {
          if (iOldLockState != 0) {
            changeMissileGrowl(0);
            NetSafeLog.log(this.missileOwner, missileName + " disengaged");
          }
          break;
        }
      }

    } catch (Exception exception) {
    }
  }

  public Point3f getSelectedActorOffset() {
    return this.selectedActorOffset;
  }

  public float Pk(Actor actorFrom, Actor actorTo) {
    float fPkRet = 0.0F;
    float fTemp = 0.0F;
    if (!((actorFrom instanceof Aircraft) && (actorTo instanceof Aircraft))) {
      return fPkRet;
    }
    float angleToTarget = angleBetween(actorFrom, actorTo);
    float angleFromTarget = 180.0F - angleBetween(actorTo, actorFrom);
    float distanceToTarget = (float) distanceBetween(actorFrom, actorTo);
    float gForce = ((Aircraft) actorFrom).FM.getOverload();
    float fMaxLaunchLoad = this.fPkMaxG;
    if (!(((Aircraft) actorFrom).FM instanceof RealFlightModel) || !((RealFlightModel) (((Aircraft) actorFrom).FM)).isRealMode()) {
      fMaxG *= 2; // double Max. Launch load for AI.
    }
    fPkRet = 100.0F;
    if ((distanceToTarget > fPkMaxDist)
            || (distanceToTarget < fPkMinDist)
            || (angleToTarget > fPkMaxAngle)
            || (angleFromTarget > fPkMaxAngleAft)
            || (gForce > fMaxLaunchLoad)) {
      return 0.0F;
    }
    if (distanceToTarget > fPkOptDist) {
      fTemp = (distanceToTarget - fPkOptDist);
      fTemp /= (fPkMaxDist - fPkOptDist);
      fPkRet -= (fTemp * fTemp * 20);
    } else {
      fTemp = (fPkOptDist - distanceToTarget);
      fTemp /= (fPkOptDist - fPkMinDist);
      fPkRet -= (fTemp * fTemp * 60);
    }
    fTemp = angleToTarget / fPkMaxAngle;
    fPkRet -= (fTemp * fTemp * 30);
    fTemp = angleFromTarget / fPkMaxAngleAft;
    fPkRet -= (fTemp * fTemp * 50);
    fTemp = gForce / fMaxLaunchLoad;
    fPkRet -= (fTemp * fTemp * 30);
    if (fPkRet < 0.0F) {
      fPkRet = 0.0F;
    }
    return fPkRet;
  }

  private int engineHeatSpreadType(Actor theActor) {
    EnginesInterface checkEI = ((FlightModelMain) (((SndAircraft) (theActor)).FM)).EI;
    int iRetVal = HEAT_SPREAD_NONE;
    for (int i = 0; i < checkEI.getNum(); i++) {
      int iMotorType = checkEI.engines[i].getType();
      if (iMotorType == Motor._E_TYPE_JET
              || iMotorType == Motor._E_TYPE_ROCKET
              || iMotorType == Motor._E_TYPE_ROCKETBOOST
              || iMotorType == Motor._E_TYPE_PVRD) {
        iRetVal |= HEAT_SPREAD_AFT;
      }
      if (iMotorType == Motor._E_TYPE_INLINE
              || iMotorType == Motor._E_TYPE_RADIAL
              || iMotorType == Motor._E_TYPE_HELO_INLINE
              || iMotorType == Motor._E_TYPE_UNIDENTIFIED) {
        iRetVal |= HEAT_SPREAD_360;
      }
    }
    return iRetVal;
  }

  private int engineHeatSpreadType(Motor theMotor) {
    int iRetVal = HEAT_SPREAD_NONE;
    int iMotorType = theMotor.getType();
    if (iMotorType == Motor._E_TYPE_JET
            || iMotorType == Motor._E_TYPE_ROCKET
            || iMotorType == Motor._E_TYPE_ROCKETBOOST
            || iMotorType == Motor._E_TYPE_PVRD) {
      iRetVal |= HEAT_SPREAD_AFT;
    }
    if (iMotorType == Motor._E_TYPE_INLINE
            || iMotorType == Motor._E_TYPE_RADIAL
            || iMotorType == Motor._E_TYPE_HELO_INLINE
            || iMotorType == Motor._E_TYPE_UNIDENTIFIED) {
      iRetVal |= HEAT_SPREAD_360;
    }
    return iRetVal;
  }

  public Actor lookForGuidedMissileTarget(Actor actor, float maxFOVfrom, float maxFOVto, double maxDistance) {
    double targetDistance = 0.0D;
    float targetAngle = 0.0F;
    float targetAngleAft = 0.0F;
    float targetBait = 0.0F;
    float maxTargetBait = 0.0F;
    Actor selectedActor = null;
    Point3f theSelectedActorOffset = new Point3f(0.0F, 0.0F, 0.0F);

    if (!(actor instanceof Aircraft) || (this.iDetectorMode == Missile.DETECTOR_TYPE_MANUAL)) {
      return selectedActor;
    }
    try {
      List list = Engine.targets();
      int k = list.size();
      for (int i1 = 0; i1 < k; i1++) {
        Actor theTarget1 = (Actor) list.get(i1);
        if (theTarget1 instanceof Aircraft) {
          targetDistance = distanceBetween(actor, theTarget1);
          if (targetDistance > maxDistance) {
            continue;
          }
          targetAngle = angleBetween(actor, theTarget1);
          if (targetAngle > maxFOVfrom) {
            continue;
          }
          targetAngleAft = 180.0F - angleBetween(theTarget1, actor);
          if (targetAngleAft > maxFOVto) {
            if (this.iDetectorMode == Missile.DETECTOR_TYPE_INFRARED) {
              if ((engineHeatSpreadType(theTarget1) & HEAT_SPREAD_360) == 0) {
                continue;
              }
            }
          }

          switch (this.iDetectorMode) {
            case Missile.DETECTOR_TYPE_INFRARED: {
              EnginesInterface theEI = ((FlightModelMain) (((SndAircraft) (theTarget1)).FM)).EI;
              int iNumEngines = theEI.getNum();
              float maxEngineForce = 0.0F;
              int maxEngineForceEngineNo = 0;
              for (int i = 0; i < iNumEngines; i++) {
                Motor theMotor = theEI.engines[i];
                float theEngineForce = theMotor.getEngineForce().length();
                if (engineHeatSpreadType(theMotor) == HEAT_SPREAD_NONE) {
                  theEngineForce = 0F;
                }
                if (engineHeatSpreadType(theMotor) == HEAT_SPREAD_360) {
                  theEngineForce /= 10F;
                }
                if (theEngineForce > maxEngineForce) {
                  maxEngineForce = theEngineForce;
                  maxEngineForceEngineNo = i;
                }
              }
              targetBait = maxEngineForce / targetAngle / (float) (targetDistance * targetDistance);
              if (!theTarget1.isAlive()) {
                targetBait /= 10F;
              }
              if (targetBait > maxTargetBait) {
                maxTargetBait = targetBait;
                selectedActor = theTarget1;
                theSelectedActorOffset = theEI.engines[maxEngineForceEngineNo].getEnginePos();
              }
              break;
            }
            case Missile.DETECTOR_TYPE_RADAR_BEAMRIDING:
            case Missile.DETECTOR_TYPE_RADAR_HOMING:
            case Missile.DETECTOR_TYPE_RADAR_TRACK_VIA_MISSILE: {
              Mass theM = ((FlightModelMain) (((SndAircraft) (theTarget1)).FM)).M;
              float fACMass = theM.getFullMass();
              targetBait = fACMass / targetAngle / (float) (targetDistance * targetDistance);
              if (!theTarget1.isAlive()) {
                targetBait /= 10F;
              }
              if (targetBait > maxTargetBait) {
                maxTargetBait = targetBait;
                selectedActor = theTarget1;
                float fGC = FlightModelMainEx.getFmGCenter((FlightModelMain) (((SndAircraft) (theTarget1)).FM));
                theSelectedActorOffset.set(fGC, 0, 0);
              }
              break;
            }
            default: {
              break;
            }
          }

        }
      }
    } catch (Exception e) {
      EventLog.type("Exception in selectedActor");
      EventLog.type(e.toString());
      EventLog.type(e.getMessage());
    }
    this.selectedActorOffset.set(theSelectedActorOffset);
    return selectedActor;
  }
// </editor-fold>
}
