// Source File Name: Missile.java
// Author:           Storebror
// Last Modified by: Storebror 2011-06-03
package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Geom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Selector;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.NetSafeLog;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.rts.Time;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Message;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetUpdate;
import java.io.IOException;
//import java.text.DecimalFormat; // only required for debugging
import java.security.SecureRandom;

public class Missile extends Rocket {

  /**
   * Helper Class for a chained list of active Nav Lights for a particular Missile.
   * This class is necessary since missiles can have different numbers of active Nav Lights.
   */
  private class MissileNavLight {

    public Eff3DActor theNavLight;                      // The Nav Light Object in the Chain
    public MissileNavLight nextNavLight;                // Pointer to the next chain element

    public MissileNavLight(Eff3DActor theEff3DActor) {  // Element constructor.
      this.theNavLight = theEff3DActor;
      this.nextNavLight = null;
    }
  }
//  DecimalFormat twoPlaces = new DecimalFormat("+000.00;-000.00"); // only required for debugging
  private FlightModel fm = null;
  private Orient or = new Orient();
  private Point3d p = new Point3d();
  private Orient orVictimOffset = null;
  private Point3f pVictimOffset = null;
  private Point3d pT = null;
  private Vector3d v = null;
  private Vector3d victimSpeed = null;
  private long tStart = 0L;
  private float prevd = 0.0F;
  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;
  private double d = 0.0D;
  private Actor victim = null;
  private float fMissileBaseSpeed = 0.0F;
  private double launchKren = 0.0D;
  private double launchPitch = 0.0D;
  private double launchYaw = 0.0D;
  private float oldDeltaAzimuth;
  private float oldDeltaTangage;
  private boolean endedFlame = false;
  private boolean endedSmoke = false;
  private boolean endedSprite = false;
  private boolean flameActive = true;
  private boolean smokeActive = true;
  private boolean spriteActive = true;
  private int iCounter = 0;
  private int iStepMode = 0;
  private float fMaxFOVfrom = 0.0F;
  private float fLeadPercent = 0.0F; // 0 means tail chasing, 100 means full lead tracking
  private float fMaxG = 12F; // maximum G-Force during flight
  private float fStepsForFullTurn = 10; // update steps for maximum control surface output, higher value means slower reaction and smoother flight, lower value means higher agility
  private float fTimeLife = 30F;
  private float fTimeFire = 2.2F;
  private float fForce = 18712F;
  private long lTimeToFailure = 0L;
  private float fSunRayAngle = 0.0F;
  private float fGroundTrackFactor = 0.0F;
  private float fMaxLaunchG = 2.0F;
  private int iLaunchType = 0;
  private float fKalibr = 0.2F;
  private float fMassa = 86.2F;
  private float fMassaEnd = 86.2F;
  private float fSquare = 1.0F;
  private float fCw = 0.3F;
  private long lTrackDelay = 1000L;
  private long lLastTimeNoFlare = 0L;
  private long lFlareLockTime = 1000L;
  private float fDiffM = 0F;
  private float fT1 = 0F;
  private float fP1 = 0F;
  private float fT2 = 0F;
  private float fP2 = 0F;
  private int iFailState = FAIL_TYPE_NONE;
  private float fIvanTimeLeft = 0.0F;
  private Eff3DActor[] smokes = null;
  private Eff3DActor[] sprites = null;
  private Actor[] flames = null;
  private String sEffSmoke = null;
  private String sEffSprite = null;
  private String sSimFlame = null;
  private int iExhausts = 1;
  private MissileNavLight firstNavLight = null; // First Nav Light Element in the chained list of Nav Lights.
  private MissileNavLight lastNavLight = null;  // Last  Nav Light Element in the chained list of Nav Lights.
  protected static final int LAUNCH_TYPE_STRAIGHT = 0;
  protected static final int LAUNCH_TYPE_QUICK = 1;
  protected static final int LAUNCH_TYPE_DROP = 2;
  protected static final int STEP_MODE_HOMING = 0;
  protected static final int STEP_MODE_BEAMRIDER = 1;
  protected static final int DETECTOR_TYPE_MANUAL = 0;
  protected static final int DETECTOR_TYPE_INFRARED = 1;
  protected static final int DETECTOR_TYPE_RADAR_HOMING = 2;
  protected static final int DETECTOR_TYPE_RADAR_BEAMRIDING = 3;
  protected static final int DETECTOR_TYPE_RADAR_TRACK_VIA_MISSILE = 4;
  protected static final int FAIL_TYPE_NONE = 0;
  protected static final int FAIL_TYPE_ELECTRONICS = 1;
  protected static final int FAIL_TYPE_MIRROR = 2;
  protected static final int FAIL_TYPE_ENGINE = 3;
  protected static final int FAIL_TYPE_REFLEX = 4;
  protected static final int FAIL_TYPE_IVAN = 5;
  protected static final int FAIL_TYPE_CONTROL_BLOCKED = 6;
  protected static final int FAIL_TYPE_WARHEAD = 7;
  protected static final int FAIL_TYPE_NUMBER = 7;
  private static final float IVAN_TIME_MIN = 1.0F;
  private static final float IVAN_TIME_MAX = 2.0F;
  private static RangeRandom theRangeRandom;

  public double getLaunchTimeFactor() {
    return (Time.current() - this.tStart) / 1000.0D * 6.0D;
  }

  public double getLaunchYaw() {
    double launchYawTimeFactor = getLaunchTimeFactor();
    double theLaunchYaw = 2.0D * (Math.cos(launchYawTimeFactor + 0.4D) - 1.02D + launchYawTimeFactor / 5.0D);
    theLaunchYaw *= Math.sin(this.launchKren);
    theLaunchYaw += this.launchYaw;
    return theLaunchYaw;
  }

  public double getLaunchPitch() {
    double launchPitchTimeFactor = getLaunchTimeFactor();
    double theLaunchPitch = 2.0D * (Math.cos(launchPitchTimeFactor + 0.4D) - 1.02D + launchPitchTimeFactor / 5.0D);
    theLaunchPitch *= Math.cos(this.launchKren);
    theLaunchPitch += this.launchPitch;
    return theLaunchPitch;
  }

  private void endAllSmoke() {
    if (this.iExhausts < 2) {
      Eff3DActor.finish(smoke);
    } else {
      for (int i = 0; i < this.iExhausts; i++) {
        if (this.smokes[i] == null) {
          continue;
        }
        Eff3DActor.finish(this.smokes[i]);
      }
    }
    this.endedSmoke = true;
  }

  private void endAllFlame() {
    if (this.iExhausts < 2) {
      ObjState.destroy(flame);
    } else {
      for (int i = 0; i < this.iExhausts; i++) {
        if (this.flames[i] == null) {
          continue;
        }
        ObjState.destroy(this.flames[i]);
      }
    }
    if (light != null) {
      light.light.setEmit(0.0F, 1.0F);
    }
    stopSounds();
    this.endedFlame = true;
  }

  private void endAllSprites() {
    if (this.iExhausts < 2) {
      Eff3DActor.finish(sprite);
    } else {
      for (int i = 0; i < this.iExhausts; i++) {
        if (this.sprites[i] == null) {
          continue;
        }
        Eff3DActor.finish(this.sprites[i]);
      }
    }
    this.endedSprite = true;
  }

  protected void endSmoke() {
    if (!this.smokeActive && !this.endedSmoke) {
      this.endAllSmoke();
    }
    if (!this.spriteActive && !this.endedSprite) {
      this.endAllSprites();
    }
    if (!this.flameActive && !this.endedFlame) {
      this.endAllFlame();
    }
  }

  public boolean stepTargetHoming() {
    float fTick = Time.tickLenFs();
    int theFailState = this.getFailState();
    float fSpeed = (float) this.getSpeed((Vector3d) null);

    if (theFailState == FAIL_TYPE_ENGINE) {
      this.fTimeFire = 0.0F;
    }

    this.pos.getAbs(p, or);
    or.wrap();

    float theForce = this.fForce;
    float millisecondsFromStart = (float) (Time.current() - tStart);


    if (millisecondsFromStart > this.fTimeFire) {
      this.flameActive = false;
      this.smokeActive = false;
      this.endSmoke();
      this.fMassa = this.fMassaEnd;
      this.fForce = 0.0F;
    } else {
      if (this.fT1 > 0.001F) {
        if (millisecondsFromStart < this.fT1) {
          theForce *= (this.fP1 + ((100.0F - this.fP1) * millisecondsFromStart / this.fT1)) / 100.0F;
        }
      }
      float millisecondsToEnd = this.fTimeFire - millisecondsFromStart;
      if (this.fT2 > 0.001F) {
        if (millisecondsToEnd < this.fT2) {
          theForce *= (this.fP2 + ((100.0F - this.fP2) * (this.fT2 - millisecondsToEnd) / this.fT2)) / 100.0F;
        }
      }
      this.fMassa -= this.fDiffM;
    }
    float fForceAzimuth = MissilePhysics.getGForce(fSpeed, this.oldDeltaAzimuth / fTick);
    float fForceTangage = MissilePhysics.getGForce(fSpeed, this.oldDeltaTangage / fTick);
    float fTurnForce = (float) Math.sqrt((fForceAzimuth * fForceAzimuth) + (fForceTangage * fForceTangage))
            * MissilePhysics.G_CONST * this.fMassa;
    fTurnForce *= this.fCw;

    float fResForce = (float) Math.sqrt(Math.abs((theForce * theForce) - (fTurnForce * fTurnForce)));
    if (fTurnForce > theForce) {
      fResForce *= -1F;
    }
    float fAccelForce = fResForce - MissilePhysics.getDragInGravity(this.fSquare, this.fCw, (float) this.p.z, fSpeed, or.getTangage(), this.fMassa);
    float fAccel = fAccelForce / this.fMassa;
    fSpeed += fAccel * fTick;

    if (fSpeed < 3F) {
      theFailState = FAIL_TYPE_WARHEAD; // let missile detonate when speed is too low
    }
    v.set(1.0D, 0.0D, 0.0D);
    or.transform(v);
    v.scale(fSpeed);

    this.setSpeed(v);
    p.x += v.x * (double) fTick;
    p.y += v.y * (double) fTick;
    p.z += v.z * (double) fTick;

    if (this.isNet() && this.isNetMirror()) {
      this.pos.setAbs(p, or);
      return false;
    }

    if (theFailState == FAIL_TYPE_WARHEAD) {
      this.doExplosionAir();
      this.postDestroy();
      this.collide(false);
      this.drawing(false);
      return false;
    }

    if (victim != null) {
      victim.pos.getAbs(pT, orVictimOffset);
      // Calculate future victim position

      victim.getSpeed(victimSpeed);   // target movement vector
      double victimDistance = GuidedMissileUtils.distanceBetween(this, victim);    // distance missile -> target
      double theVictimSpeed = victimSpeed.length();   // target speed
      double speedRel = fSpeed / theVictimSpeed;     // relation missile speed / target speed
      double gamma = (double) (GuidedMissileUtils.angleActorBetween(victim, this));     // angle offset missile vector -> target vector
      double alpha = Geom.RAD2DEG((float) (Math.asin(Math.sin(Geom.DEG2RAD((float) gamma)) / speedRel)));   // angle offset target vector -> target interception path vector
      double beta = 180.0D - gamma - alpha;   // angle offset missile vector -> target interception path vector
      double victimAdvance = victimDistance * Math.sin(Geom.DEG2RAD((float) alpha)) / Math.sin(Geom.DEG2RAD((float) beta));     // track made good by target until impact
      victimAdvance -= 5.0D; // impact point 10m aft of engine (track exhaust).
      double timeToTarget = victimAdvance / theVictimSpeed;   // time until calculated impact

      victimSpeed.scale(timeToTarget * (double) (this.fLeadPercent / 100.0F));
      pT.add(victimSpeed);
      Orient orientTarget = new Orient();
      orientTarget.set(this.victim.pos.getAbsOrient());
      v.set(pVictimOffset);       // take victim Offset into account
      orientTarget.transform(v);  // take victim Offset into account
      pT.add(v);                  // take victim Offset into account

      pT.sub(p);                  // relative Position to target
      or.transformInv(pT);        // set coordinate system according to A/C POV

      // Calculate angle to target.
      // This is required in order to respect the IR Seeker FOV.
      double angleAzimuth = Math.toDegrees(Math.atan(pT.y / pT.x));
      double angleTangage = Math.toDegrees(Math.atan(pT.z / pT.x));

      if (theFailState == FAIL_TYPE_REFLEX) {
        angleAzimuth += 180D;
        angleTangage += 180D;
        if (angleAzimuth > 180D) {
          angleAzimuth = 180D - angleAzimuth;
        }
        if (angleTangage > 180D) {
          angleTangage = 180D - angleTangage;
        }
      }

      if (Time.current() > tStart + this.lTrackDelay) // Start tracking x sec. after launch
      {
        float turnStepMax = MissilePhysics.getDegPerSec(/*this.fMissileMaxSpeed*/fSpeed, this.fMaxG) * fTick * MissilePhysics.getAirDensityFactor((float) this.p.z); // turn limit, results in 12G accelleration at Mach 1.7
        float turnDiffMax = turnStepMax / this.fStepsForFullTurn; // turn rate change limit, smoothen the turns.

        if (theFailState == FAIL_TYPE_IVAN) {
          if (this.fIvanTimeLeft < fTick) {
            if (this.nextRandomFloat() < 0.5F) {
              if (this.nextRandomFloat() < 0.5F) {
                deltaAzimuth = turnStepMax;
              } else {
                deltaAzimuth = -turnStepMax;
              }
              deltaTangage = this.nextRandomFloat(-turnStepMax, turnStepMax);
            } else {
              if (this.nextRandomFloat() < 0.5F) {
                deltaTangage = turnStepMax;
              } else {
                deltaTangage = -turnStepMax;
              }
              deltaAzimuth = this.nextRandomFloat(-turnStepMax, turnStepMax);
            }
            this.fIvanTimeLeft = this.nextRandomFloat(IVAN_TIME_MIN, IVAN_TIME_MAX);
          } else {
            deltaAzimuth = oldDeltaAzimuth;
            deltaTangage = oldDeltaTangage;
            this.fIvanTimeLeft -= fTick;
            if (this.fIvanTimeLeft < fTick) {
              this.iFailState = FAIL_TYPE_NONE;
              this.fIvanTimeLeft = 0.0F;
            }
          }
        } else if (theFailState == FAIL_TYPE_MIRROR) {
          if (this.nextRandomFloat() < 0.5F) {
            deltaAzimuth = turnStepMax;
          } else {
            deltaAzimuth = -turnStepMax;
          }
          if (this.nextRandomFloat() < 0.5F) {
            deltaTangage = turnStepMax;
          } else {
            deltaTangage = -turnStepMax;
          }
        } else if (theFailState == FAIL_TYPE_CONTROL_BLOCKED) {
          deltaAzimuth = oldDeltaAzimuth;
          deltaTangage = oldDeltaTangage;
        } else {
          if (pT.x > -10D) { // don't track if target has been passed
            deltaAzimuth = (float) -angleAzimuth;
            if (deltaAzimuth > turnStepMax) {
              deltaAzimuth = turnStepMax;     // limit turn
            }
            if (deltaAzimuth < -turnStepMax) {
              deltaAzimuth = -turnStepMax;   // limit turn
            }
            deltaTangage = (float) angleTangage;
            if (deltaTangage > turnStepMax) {
              deltaTangage = turnStepMax;     // limit turn
            }
            if (deltaTangage < -turnStepMax) {
              deltaTangage = -turnStepMax;   // limit turn
            }
          }
          if (Math.abs(oldDeltaAzimuth - deltaAzimuth) > turnDiffMax) // limit turn rate change
          {
            if (oldDeltaAzimuth < deltaAzimuth) {
              deltaAzimuth = oldDeltaAzimuth + turnDiffMax;
            } else {
              deltaAzimuth = oldDeltaAzimuth - turnDiffMax;
            }
          }
          if (Math.abs(oldDeltaTangage - deltaTangage) > turnDiffMax) // limit turn rate change
          {
            if (oldDeltaTangage < deltaTangage) {
              deltaTangage = oldDeltaTangage + turnDiffMax;
            } else {
              deltaTangage = oldDeltaTangage - turnDiffMax;
            }
          }
          oldDeltaAzimuth = deltaAzimuth;
          oldDeltaTangage = deltaTangage;
        }
        or.increment(deltaAzimuth, deltaTangage, 0.0F);
        or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
      } else {// in the first second after launch, recover from possible launch pitch.
        if (this.iLaunchType == Missile.LAUNCH_TYPE_DROP) {
          or.setYPR((float) this.getLaunchYaw(), (float) this.getLaunchPitch(), 0.0F);
        } else // fly straight if no launch pitch.
        {
          or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        }
      }
    } else {
      if ((Time.current() < tStart + this.lTrackDelay) && (this.iLaunchType == Missile.LAUNCH_TYPE_DROP)) // recover from launch pitch even if there's no target.
      {
        or.setYPR((float) this.getLaunchYaw(), (float) this.getLaunchPitch(), 0.0F);
      } else // fly straight on after 1 sec. if no target available.
      {
        or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
      }
    }
    deltaAzimuth = deltaTangage = 0.0F;
    this.pos.setAbs(p, or);
    if (Time.current() > tStart + (2 * this.lTrackDelay)) {
      if (Actor.isValid(victim)) {
        float f2 = (float) p.distance(victim.pos.getAbsPoint());
        if ((victim instanceof Aircraft) && f2 > prevd && prevd != 1000F) {
          if (f2 < 30F) {
            this.doExplosionAir();
            this.postDestroy();
            this.collide(false);
            this.drawing(false);
          }
          if (this.getSpeed((Vector3d) null) > victim.getSpeed((Vector3d) null)) {
            victim = null;
          }
        }
        prevd = f2;
      } else {
        prevd = 1000F;
      }
    }
    if (!Actor.isValid(this.getOwner()) || !(this.getOwner() instanceof Aircraft)) {
      this.doExplosionAir();
      this.postDestroy();
      this.collide(false);
      this.drawing(false);
      return false;
    } else {
      return false;
    }

  }

  public boolean stepBeamRider() {
    float fTick = Time.tickLenFs();
    float fSpeed = (float) this.getSpeed(null);
    int theFailState = this.getFailState();
    if (theFailState == FAIL_TYPE_ENGINE) {
      this.fTimeFire = 0.0F;
    }
    this.pos.getAbs(p, or);
    or.wrap();

    float theForce = this.fForce;
    float millisecondsFromStart = (float) (Time.current() - tStart);


    if (millisecondsFromStart > this.fTimeFire) {
      this.flameActive = false;
      this.smokeActive = false;
      this.endSmoke();
      this.fMassa = this.fMassaEnd;
      this.fForce = 0.0F;
    } else {
      if (this.fT1 > 0.001F) {
        if (millisecondsFromStart < this.fT1) {
          theForce *= (this.fP1 + ((100.0F - this.fP1) * millisecondsFromStart / this.fT1)) / 100.0F;
        }
      }
      float millisecondsToEnd = this.fTimeFire - millisecondsFromStart;
      if (this.fT2 > 0.001F) {
        if (millisecondsToEnd < this.fT2) {
          theForce *= (this.fP2 + ((100.0F - this.fP2) * (this.fT2 - millisecondsToEnd) / this.fT2)) / 100.0F;
        }
      }
      this.fMassa -= this.fDiffM;
    }
    float fForceAzimuth = MissilePhysics.getGForce(fSpeed, this.oldDeltaAzimuth / fTick);
    float fForceTangage = MissilePhysics.getGForce(fSpeed, this.oldDeltaTangage / fTick);
    float fTurnForce = (float) Math.sqrt((fForceAzimuth * fForceAzimuth) + (fForceTangage * fForceTangage))
            * MissilePhysics.G_CONST * this.fMassa;
    fTurnForce *= this.fCw;

    float fResForce = (float) Math.sqrt(Math.abs((theForce * theForce) - (fTurnForce * fTurnForce)));
    if (fTurnForce > theForce) {
      fResForce *= -1F;
    }
    float fAccelForce = fResForce - MissilePhysics.getDragInGravity(this.fSquare, this.fCw, (float) this.p.z, fSpeed, or.getTangage(), this.fMassa);
    float fAccel = fAccelForce / this.fMassa;
    fSpeed += fAccel * fTick;

    if (fSpeed < 3F) {
      theFailState = FAIL_TYPE_WARHEAD; // let missile detonate when speed is too low
    }
    this.v.set(1.0D, 0.0D, 0.0D);
    or.transform(this.v);
    this.v.scale(fSpeed);
    this.setSpeed(this.v);
    p.x += this.v.x * fTick;
    p.y += this.v.y * fTick;
    p.z += this.v.z * fTick;
    if ((this.isNet()) && (this.isNetMirror())) {
      this.pos.setAbs(p, or);
      return false;
    }
    if (theFailState == FAIL_TYPE_WARHEAD) {
      this.doExplosionAir();
      this.postDestroy();
      this.collide(false);
      this.drawing(false);
      return false;
    }
    Actor myOwner = this.getOwner();
    if (this.victim != null) {
      if (GuidedMissileUtils.angleBetween(myOwner, victim) > this.fMaxFOVfrom) {
        this.victim = null;
      }
    }
    if (this.victim != null) {
      if (myOwner != null) {

        float hTurn = 0.0F;
        float vTurn = 0.0F;


        Point3d pointAC = new Point3d();
        pointAC.set(myOwner.pos.getAbsPoint());
        Orient orientAC = new Orient();
        orientAC.set(myOwner.pos.getAbsOrient());
        Point3d pointTarget = new Point3d();
        pointTarget.set(this.victim.pos.getAbsPoint());
        Vector3d vectorOffset = new Vector3d();
        Orient orientTarget = new Orient();
        orientTarget.set(this.victim.pos.getAbsOrient());
        vectorOffset.set(pVictimOffset);       // take victim Offset into account
        orientTarget.transform(vectorOffset);            // take victim Offset into account
        pointTarget.add(vectorOffset);                  // take victim Offset into account

        Point3d pointMissile = new Point3d();
        pointMissile.set(this.pos.getAbsPoint());

        Point3d pointMissileAft = new Point3d();
        pointMissileAft.set(pointMissile);
        Orient orientMissileAft = new Orient();
        orientMissileAft.set(this.pos.getAbsOrient());
        Point3d pointACAft = new Point3d();
        pointACAft.set(pointAC);

        pointTarget.sub(pointAC);
        orientAC.transformInv(pointTarget);
        float targetAzimuth = (float) Math.toDegrees(Math.atan(-pointTarget.y / pointTarget.x));
        float targetElevation = (float) Math.toDegrees(Math.atan(pointTarget.z / pointTarget.x));
        if (theFailState == FAIL_TYPE_REFLEX) {
          targetAzimuth += 180F;
          targetElevation += 180F;
          if (targetAzimuth > 180F) {
            targetAzimuth = 180F - targetAzimuth;
          }
          if (targetElevation > 180F) {
            targetElevation = 180F - targetElevation;
          }
        }

        pointMissile.sub(pointAC);
        orientAC.transformInv(pointMissile);
        float missileAzimuth = (float) Math.toDegrees(Math.atan(-pointMissile.y / pointMissile.x));
        float missileElevation = (float) Math.toDegrees(Math.atan(pointMissile.z / pointMissile.x));

        float missileOffsetAzimuth = missileAzimuth - targetAzimuth;
        float missileOffsetElevation = missileElevation - targetElevation;

        pointACAft.sub(pointMissileAft);
        orientMissileAft.transformInv(pointACAft);
        float missileAzimuthAft = (float) Math.toDegrees(Math.atan(-pointACAft.y / pointACAft.x));
        float missileElevationAft = (float) Math.toDegrees(Math.atan(pointACAft.z / pointACAft.x));

        float missileTrackOffsetAzimuth = missileOffsetAzimuth - missileAzimuthAft;
        float missileTrackOffsetElevation = missileOffsetElevation - missileElevationAft;

        float closingFactor = -5.0F;
        float maxClosing = 60.0F;
        float fastClosingMax = 3.0F;

        float turnNormal = 1.0F;
        float turnQuick = 1.5F;
        float turnSharp = 2.0F;


        if (missileOffsetAzimuth < 0) { // left of beam
          if (missileTrackOffsetAzimuth < 0) { // heading left
            hTurn = turnSharp; // turn right sharp
          } else if (missileTrackOffsetAzimuth > maxClosing) { // heading right more than 45° towards beam
            hTurn = -turnNormal; // turn left
          } else if ((missileTrackOffsetAzimuth > fastClosingMax) && (missileTrackOffsetAzimuth > (closingFactor * missileOffsetAzimuth))) { // fast closing in on beam from left
            hTurn = -turnQuick; // turn left quick
          } else {
            hTurn = turnNormal; // turn right
          }
        } else { // right of beam
          if (missileTrackOffsetAzimuth > 0) { // heading right
            hTurn = -turnSharp; // turn left sharply
          } else if (missileTrackOffsetAzimuth < -maxClosing) { // heading left more than 45° towards beam
            hTurn = turnNormal; // turn right
          } else if ((missileTrackOffsetAzimuth < -fastClosingMax) && (missileTrackOffsetAzimuth < (closingFactor * missileOffsetAzimuth))) { // fast closing in on beam from right
            hTurn = turnQuick; // turn right quick
          } else {
            hTurn = -turnNormal; // turn left
          }
        }

        if (missileOffsetElevation < 0) { // below beam
          if (missileTrackOffsetElevation < 0) { // heading down
            vTurn = turnSharp; // turn up sharp
          } else if (missileTrackOffsetElevation > maxClosing) { // heading up more than 45° towards beam
            vTurn = -turnNormal; // turn down
          } else if ((missileTrackOffsetElevation > fastClosingMax) && (missileTrackOffsetElevation > (closingFactor * missileOffsetElevation))) { // fast closing in on beam from below
            vTurn = -turnQuick; // turn down quick
          } else {
            vTurn = turnNormal; // turn up
          }
        } else { // above beam
          if (missileTrackOffsetElevation > 0) { // heading up
            vTurn = -turnSharp; // turn down sharp
          } else if (missileTrackOffsetElevation < -maxClosing) { // heading down more than 45° towards beam
            vTurn = turnNormal; // turn up
          } else if ((missileTrackOffsetElevation < -fastClosingMax) && (missileTrackOffsetElevation < (closingFactor * missileOffsetElevation))) { // fast closing in on beam from above
            vTurn = turnQuick; // turn up quick
          } else {
            vTurn = -turnNormal; // turn down
          }
        }

        this.iCounter++;

        if (Time.current() > this.tStart + (long) this.lTrackDelay) {
          float turnStepMax = MissilePhysics.getDegPerSec(/*this.fMissileMaxSpeed*/fSpeed, this.fMaxG) * fTick * MissilePhysics.getAirDensityFactor((float) this.p.z); // turn limit, results in 12G accelleration at Mach 1.7
          float turnDiffMax = turnStepMax / (float) this.fStepsForFullTurn; // turn rate change limit, smoothen the turns.

          if (theFailState == FAIL_TYPE_IVAN) {
            if (this.fIvanTimeLeft < fTick) {
              if (this.nextRandomFloat() < 0.5F) {
                if (this.nextRandomFloat() < 0.5F) {
                  deltaAzimuth = turnStepMax;
                } else {
                  deltaAzimuth = -turnStepMax;
                }
                deltaTangage = this.nextRandomFloat(-turnStepMax, turnStepMax);
              } else {
                if (this.nextRandomFloat() < 0.5F) {
                  deltaTangage = turnStepMax;
                } else {
                  deltaTangage = -turnStepMax;
                }
                deltaAzimuth = this.nextRandomFloat(-turnStepMax, turnStepMax);
              }
              this.fIvanTimeLeft = this.nextRandomFloat(IVAN_TIME_MIN, IVAN_TIME_MAX);
            } else {
              deltaAzimuth = oldDeltaAzimuth;
              deltaTangage = oldDeltaTangage;
              this.fIvanTimeLeft -= fTick;
              if (this.fIvanTimeLeft < fTick) {
                this.iFailState = FAIL_TYPE_NONE;
                this.fIvanTimeLeft = 0.0F;
              }
            }
          } else if (theFailState == FAIL_TYPE_MIRROR) {
            if (this.nextRandomFloat() < 0.5F) {
              deltaAzimuth = turnStepMax;
            } else {
              deltaAzimuth = -turnStepMax;
            }
            if (this.nextRandomFloat() < 0.5F) {
              deltaTangage = turnStepMax;
            } else {
              deltaTangage = -turnStepMax;
            }
          } else if (theFailState == FAIL_TYPE_CONTROL_BLOCKED) {
            deltaAzimuth = oldDeltaAzimuth;
            deltaTangage = oldDeltaTangage;
          } else {


            do {
              if (Math.abs(targetAzimuth) > 90) {
                break; // Target outside Radar coverage
              }
              if (Math.abs(targetElevation) > 90) {
                break; // Target outside Radar coverage
              }

              if ((hTurn * this.oldDeltaAzimuth) < 0.0F) {
                this.deltaAzimuth = hTurn * turnDiffMax;
              } else {
                this.deltaAzimuth = this.oldDeltaAzimuth + (hTurn * turnDiffMax);
                if (this.deltaAzimuth < -turnStepMax) {
                  this.deltaAzimuth = -turnStepMax;
                }
                if (this.deltaAzimuth > turnStepMax) {
                  this.deltaAzimuth = turnStepMax;
                }
              }

              if ((vTurn * this.oldDeltaTangage) < 0.0F) {
                this.deltaTangage = vTurn * turnDiffMax;
              } else {
                this.deltaTangage = this.oldDeltaTangage + (vTurn * turnDiffMax);
                if (this.deltaTangage < -turnStepMax) {
                  this.deltaTangage = -turnStepMax;
                }
                if (this.deltaTangage > turnStepMax) {
                  this.deltaTangage = turnStepMax;
                }
              }

            } while (false);
            this.oldDeltaAzimuth = this.deltaAzimuth;
            this.oldDeltaTangage = this.deltaTangage;
          }
          or.increment(this.deltaAzimuth, this.deltaTangage, 0.0F);
          or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        } else {// in the first second after launch, recover from possible launch pitch.
          if (this.iLaunchType == Missile.LAUNCH_TYPE_DROP) {
            or.setYPR((float) this.getLaunchYaw(), (float) this.getLaunchPitch(), 0.0F);
          } else // fly straight if no launch pitch.
          {
            or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
          }
        }
      }
    } else {
      if ((Time.current() < tStart + this.lTrackDelay) && (this.iLaunchType == Missile.LAUNCH_TYPE_DROP)) // recover from launch pitch even if there's no target.
      {
        or.setYPR((float) this.getLaunchYaw(), (float) this.getLaunchPitch(), 0.0F);
      } else // fly straight on after 1 sec. if no target available.
      {
        or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
      }
    }

    this.deltaAzimuth = (this.deltaTangage = 0.0F);
    this.pos.setAbs(p, or);
    if (Time.current() > this.tStart + (2 * this.lTrackDelay)) {
      if (Actor.isValid(this.victim)) {
        float f2 = (float) p.distance(this.victim.pos.getAbsPoint());
        if ((this.victim instanceof Aircraft) && (f2 > this.prevd) && (this.prevd != 1000.0F)) {
          if (f2 < 30.0F) {
            this.doExplosionAir();
            this.postDestroy();
            this.collide(false);
            this.drawing(false);
          }
          if (this.getSpeed((Vector3d) null) > victim.getSpeed((Vector3d) null)) {
            victim = null;
          }
        }
        this.prevd = f2;
      } else {
        this.prevd = 1000.0F;
      }
    }
    if ((!Actor.isValid(this.getOwner())) || (!(this.getOwner() instanceof Aircraft))) {
      this.doExplosionAir();
      this.postDestroy();
      this.collide(false);
      this.drawing(false);
      return false;
    }
    return false;
  }

  public boolean interpolateStep() {
    if (Time.current() > tStart + this.lTrackDelay) {
      if (this.isSunTracking() || this.isGroundTracking()) {
        this.victim = null;
      }
    }
    switch (this.iStepMode) {
      case STEP_MODE_HOMING: {
        return this.stepTargetHoming();
      }
      case STEP_MODE_BEAMRIDER: {
        return this.stepBeamRider();
      }
      default:
        break;
    }
    return true;
  }

  private int getFailState() {
    if (this.lTimeToFailure == 0L) {
      return FAIL_TYPE_NONE;
    }
    long millisecondsFromStart = Time.current() - tStart;
    if (millisecondsFromStart < this.lTimeToFailure) {
      return FAIL_TYPE_NONE;
    }
    if (this.iFailState == FAIL_TYPE_ELECTRONICS) {
      float fRand = this.nextRandomFloat();
      if (fRand < 0.01) {
        return FAIL_TYPE_WARHEAD;
      }
      if (fRand < 0.02) {
        return FAIL_TYPE_REFLEX;
      }
      if (fRand < 0.2) {
        return FAIL_TYPE_MIRROR;
      }
      if (fRand < 0.5) {
        return FAIL_TYPE_CONTROL_BLOCKED;
      }
      return FAIL_TYPE_IVAN;
    }
    return this.iFailState;
  }

  private void setFailState() {
    if (this.iFailState == FAIL_TYPE_NONE) {
      this.iFailState = this.nextRandomInt(1, FAIL_TYPE_NUMBER);
    }
  }

  public Missile() {
    this.pT = new Point3d();
    this.v = new Vector3d();
    this.victimSpeed = new Vector3d();
    this.orVictimOffset = new Orient();
    this.pVictimOffset = new Point3f();
    this.MissileInit();
    return;
  }

  public final void MissileInit() {
    this.d = 0.10000000000000001D;
    this.victim = null;
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000F;
    this.deltaAzimuth = 0.0F;
    this.deltaTangage = 0.0F;
    this.oldDeltaAzimuth = 0.0F;
    this.oldDeltaTangage = 0.0F;
    this.endedFlame = false;
    this.endedSmoke = false;
    this.endedSprite = false;
    this.flameActive = true;
    this.smokeActive = true;
    this.spriteActive = true;
  }

  public Missile(Actor actor, NetChannel netchannel, int i, Point3d point3d, Orient orient, float f) {
    this.MissileInit(actor, netchannel, i, point3d, orient, f);
  }

  public final void MissileInit(Actor actor, NetChannel netchannel, int i, Point3d point3d, Orient orient, float f) {
    if (Actor.isValid(actor)) {
      if (!(Actor.isValid(this.getOwner()))) {
        this.setOwner(actor);
      }
    }
    this.d = 0.10000000000000001D;
    this.victim = null;
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000F;
    this.oldDeltaAzimuth = 0.0F;
    this.oldDeltaTangage = 0.0F;
    this.endedFlame = false;
    this.endedSmoke = false;
    this.endedSprite = false;
    this.flameActive = true;
    this.smokeActive = true;
    this.spriteActive = true;
    net = new Mirror(this, netchannel, i);
    pos.setAbs(point3d, orient);
    pos.reset();
    pos.setBase(actor, null, true);
    doStart(-1F);
    this.v.set(1.0D, 0.0D, 0.0D);
    orient.transform(this.v);
    this.v.scale(f);
    setSpeed(this.v);
    collide(false);
  }

  private void initRandom() {
    if (theRangeRandom != null) {
      return;
    }
    long lTime = System.currentTimeMillis();
    SecureRandom secRandom = new SecureRandom();
    secRandom.setSeed(lTime);
    long lSeed1 = (long) secRandom.nextInt();
    long lSeed2 = (long) secRandom.nextInt();
    long lSeed = (lSeed1 << 32) + lSeed2;
    theRangeRandom = new RangeRandom(lSeed);

  }

  private float nextRandomFloat(float fMin, float fMax) {
    this.initRandom();
    return theRangeRandom.nextFloat(fMin, fMax);
  }

  private float nextRandomFloat() {
    this.initRandom();
    return theRangeRandom.nextFloat();
  }

  private int nextRandomInt(int iMin, int iMax) {
    this.initRandom();
    return theRangeRandom.nextInt(iMin, iMax);
  }

  public void start(float f) {
    start(f, 0);
  }

  public void start(float f, int paramInt) {
    Actor actor = pos.base();
    try {
      if (Actor.isValid(actor) && (actor instanceof Aircraft)) {
        if (actor.isNetMirror()) {
          destroy();
          return;
        }
        net = new Master(this);
      }
    } catch (Exception exception) {
      NetSafeLog.log(this.getOwner(), "Missile launch cancelled (system error):"
              + exception.getMessage());
      destroy();
    }
    doStart(f);
  }

  private void getMissileProperties() {
    Class localClass = super.getClass();
    float f = Time.tickLenFs();
    this.iLaunchType = Property.intValue(localClass, "launchType", 0);
    this.iStepMode = Property.intValue(localClass, "stepMode", 0);
    this.fMaxFOVfrom = Property.floatValue(localClass, "maxFOVfrom", 180F);
    this.fMaxLaunchG = Property.floatValue(localClass, "maxLockGForce", 99.9F);
    this.fMaxG = Property.floatValue(localClass, "maxGForce", 12F);
    this.fStepsForFullTurn = Property.floatValue(localClass, "stepsForFullTurn", 10);
    this.fTimeFire = Property.floatValue(localClass, "timeFire", 2.2F) * 1000F;
    this.fTimeLife = Property.floatValue(localClass, "timeLife", 30F) * 1000F;
    super.timeFire = (long) this.fTimeFire;
    super.timeLife = (long) this.fTimeLife;
    this.fForce = Property.floatValue(localClass, "force", 18712F);
    this.fLeadPercent = Property.floatValue(localClass, "leadPercent", 0.0F);
    this.fKalibr = Property.floatValue(localClass, "kalibr", 0.2F);
    this.fMassa = Property.floatValue(localClass, "massa", 86.2F);
    this.fMassaEnd = Property.floatValue(localClass, "massaEnd", 80.0F);
    this.fSunRayAngle = Property.floatValue(localClass, "sunRayAngle", 0.0F);
    this.fGroundTrackFactor = Property.floatValue(localClass, "groundTrackFactor", 0.0F);
    this.lFlareLockTime = Property.longValue(localClass, "flareLockTime", 1000L);
    this.lTrackDelay = Property.longValue(localClass, "trackDelay", 1000L);
    this.fSquare = 3.141592653589793F * this.fKalibr * this.fKalibr / 4.0F;
    this.fT1 = Property.floatValue(localClass, "forceT1", 0.0F) * 1000F;
    this.fP1 = Property.floatValue(localClass, "forceP1", 0.0F);
    this.fT2 = Property.floatValue(localClass, "forceT2", 0.0F) * 1000F;
    this.fP2 = Property.floatValue(localClass, "forceP2", 0.0F);
    this.fCw = Property.floatValue(localClass, "dragCoefficient", 0.3F);
    this.iExhausts = this.getNumExhausts();
    this.sEffSmoke = Property.stringValue(localClass, "smoke", null);
    this.sEffSprite = Property.stringValue(localClass, "sprite", null);
    this.sSimFlame = Property.stringValue(localClass, "flame", null);
    float fFailureRate = Property.floatValue(localClass, "failureRate", 10.0F);
    if (this.nextRandomFloat(0, 100.0F) < fFailureRate) {
      this.setFailState();
      float fRand = this.nextRandomFloat();
      fRand = fRand * fRand * fRand * fRand;
      long lBaseFailTime = this.lTrackDelay;
      if (this.iFailState == FAIL_TYPE_WARHEAD) {
        lBaseFailTime += lBaseFailTime;
      }
      this.lTimeToFailure = lBaseFailTime + (long) ((this.fTimeLife - (float) lBaseFailTime) * fRand);
    } else {
      this.iFailState = FAIL_TYPE_NONE;
      this.lTimeToFailure = 0L;
    }
    if (this.fTimeFire > 0.0F) {
      this.fDiffM = ((this.fMassa - this.fMassaEnd) / (this.fTimeFire / 1000F / Time.tickConstLenFs()));
    } else {
      this.fDiffM = 0.0F;
    }
  }

  private void createAdditionalSmokes() {
    this.smokes = new Eff3DActor[this.iExhausts];
    this.smokes[0] = this.smoke;
    if (this.sEffSmoke == null) {
      return;
    }
    Hook theHook = null;
    for (int i = 1; i < this.iExhausts; i++) {
      theHook = findHook("_SMOKE" + i);
      if (theHook == null) {
        this.smokes[i] = null;
        continue;
      }
      this.smokes[i] = Eff3DActor.New(this, theHook, null, 1.0F, this.sEffSmoke, -1F);
      if (this.smokes[i] != null) {
        this.smokes[i].pos.changeHookToRel();
      }
    }
  }

  private void createAdditionalSprites() {
    this.sprites = new Eff3DActor[this.iExhausts];
    this.sprites[0] = this.sprite;
    if (this.sEffSprite == null) {
      return;
    }
    Hook theHook = null;
    for (int i = 1; i < this.iExhausts; i++) {
      theHook = findHook("_SMOKE" + i);
      if (theHook == null) {
        this.sprites[i] = null;
        continue;
      }
      this.sprites[i] = Eff3DActor.New(this, theHook, null, this.fKalibr, this.sEffSprite, -1F);
      if (this.sprites[i] != null) {
        this.sprites[i].pos.changeHookToRel();
      } else {
      }
    }
  }

  private void createAdditionalFlames() {
    this.flames = new Actor[this.iExhausts];
    this.flames[0] = this.flame;
    if (this.sSimFlame == null) {
      return;
    }
    Hook theHook = null;
    for (int i = 1; i < this.iExhausts; i++) {
      theHook = findHook("_SMOKE" + i);
      this.flames[i] = new ActorSimpleMesh(this.sSimFlame);
      if (this.flames[i] != null) {
        ((ActorSimpleMesh) this.flames[i]).mesh().setScale(1);
        this.flames[i].pos.setBase(this, theHook, false);
        this.flames[i].pos.changeHookToRel();
        this.flames[i].pos.resetAsBase();
      }
    }
  }

  /**
   * Finish all Nav Lights in the chained list.
   * No action required if there are no Nav Lights.
   */
  private void endNavLights() {
    MissileNavLight theNavLight = this.firstNavLight;
    while (theNavLight != null) {
      Eff3DActor.finish(theNavLight.theNavLight);
      theNavLight = theNavLight.nextNavLight;
    }
  }

  /**
   * Creates Nav Lights according to the missile's mesh.
   * Three Nav Light Types exist:
   * "_NavLightR" : Red Flare
   * "_NavLightG" : Green Flare
   * "_NavLightW" : White Flare (very bright one)
   * "_NavLightP" : Black Ball (currently useless)
   * 
   * First Nav Light has no index, following Nav Lights start with Index "1".
   * 
   * Example: A Green and a Red Nav Light are set through following Hooks:
   * _NavLightG
   * _NavLightR
   * 
   * Example2: 2 Green Flares and 3 Black Balls are set through following Hooks:
   * _NavLightG
   * _NavLightG1
   * _NavLightP
   * _NavLightP1
   * _NavLightP2
   */
  private void createNavLights() {
    this.createNamedNavLights("_NavLightR", "3DO/Effects/Fireworks/FlareRed.eff");
    this.createNamedNavLights("_NavLightG", "3DO/Effects/Fireworks/FlareGreen.eff");
    this.createNamedNavLights("_NavLightW", "3DO/Effects/Fireworks/FlareWhite.eff");
    this.createNamedNavLights("_NavLightP", "3DO/Effects/Fireworks/PhosfourousBall.eff");
  }

  /**
   * Creates The Nav Lights according to Base Hook Name and Effect Name
   * @param theNavLightHookName
   * @param theEffectName 
   */
  private void createNamedNavLights(String theNavLightHookName, String theEffectName) {
    int numNavLights = getNumNavLights(theNavLightHookName);
    if (numNavLights == 0) {
      return;
    }
    Hook theHook = null;
    for (int i = 0; i < numNavLights; i++) {
      if (i == 0) {
        theHook = findHook(theNavLightHookName);
      } else {
        theHook = findHook(theNavLightHookName + i);
      }
      MissileNavLight theNavLight = new MissileNavLight(Eff3DActor.New(this, theHook, null, 1.0F, theEffectName, -1.0F));
      if (this.firstNavLight == null) {
        this.firstNavLight = theNavLight;
        this.lastNavLight = theNavLight;
      } else {
        this.lastNavLight.nextNavLight = theNavLight;
        this.lastNavLight = theNavLight;
      }
    }
  }

  /**
   * Counts Number of Nav Lights for Base Hook Name
   * @param theNavLightHookName
   * @return Number of Nav Lights for Base Hook Name
   */
  private int getNumNavLights(String theNavLightHookName) {
    if (this.mesh.hookFind(theNavLightHookName) == -1) {
      return 0;
    }
    int retVal = 1;
    while (this.mesh.hookFind(theNavLightHookName + retVal) != -1) {
      retVal++;
    }
    return retVal;
  }

  private int getNumExhausts() {
    if (this.mesh.hookFind("_SMOKE") == -1) {
      return 0;
    }
    int retVal = 1;
    while (this.mesh.hookFind("_SMOKE" + retVal) != -1) {
      retVal++;
    }
    return retVal;
  }

  private void doStart(float f) {
    super.start(-1F, 0);
    this.getMissileProperties();
    if (this.iExhausts > 1) {
      this.createAdditionalSmokes();
      this.createAdditionalSprites();
      this.createAdditionalFlames();
    }
    this.firstNavLight = null;
    this.lastNavLight = null;

    this.prevd = 1000.0F;
    pos.getRelOrient().transformInv(speed);
    speed.y *= 3.0D;
    speed.z *= 3.0D;
    speed.x -= 198.0D;
    pos.getRelOrient().transform(speed);
    this.tStart = Time.current();
    pos.getAbs(this.p, this.or);

    if (Config.isUSE_RENDER()) {
      this.createNavLights();
      ((ActorSimpleMesh) this.flame).mesh().setScale(1);
      flame.drawing(true);
    }

    this.fMissileBaseSpeed = (float) getSpeed((Vector3d) null);
    if (isNet() && isNetMirror()) {
      return;
    }
    this.fm = ((Aircraft) getOwner()).FM;

    switch (this.iLaunchType) {
      default:
      case LAUNCH_TYPE_STRAIGHT: // simply ignite and leave rail
      {
        break;
      }
      case LAUNCH_TYPE_QUICK: // "swoosh" off rocket rail
      {
        this.fMissileBaseSpeed += 20.0F;
        this.v.set(1.0D, 0.0D, 0.0D);
        this.or.transform(this.v);
        this.v.scale(this.fMissileBaseSpeed);
        setSpeed(this.v);
        this.launchKren = Math.toRadians((double) this.or.getKren());
        this.launchYaw = this.or.getYaw();
        this.launchPitch = this.or.getPitch();
        this.or.setYPR(
                (float) this.launchYaw,
                (float) this.launchPitch,
                0.0F);
        pos.setAbs(this.p, this.or);
        break;
      }
      case LAUNCH_TYPE_DROP: // drop pattern
      {
        // start with -6° pitch.
        this.launchKren = Math.toRadians((double) this.or.getKren());
        this.launchYaw = this.or.getYaw() + (this.fm.getAOA() * (float) Math.sin(this.launchKren));
        this.launchPitch = this.or.getPitch() - (this.fm.getAOA() * (float) Math.cos(this.launchKren));
        this.or.setYPR(
                (float) this.launchYaw + (0.5F * (float) Math.sin(this.launchKren)),
                (float) this.launchPitch - (0.5F * (float) Math.cos(this.launchKren)),
                0.0F);
        pos.setAbs(this.p, this.or);
        break;
      }
    }
    this.victim = null;
    try {
      if ((getOwner() != World.getPlayerAircraft() || !((RealFlightModel) this.fm).isRealMode()) && (this.fm instanceof Pilot)) {
        if (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) {
          this.victim = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier) getOwner()).getMissileTarget();
          this.pVictimOffset = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier) getOwner()).getMissileTargetOffset();
        } else if (getOwner() instanceof TypeFighter) {
          Pilot pilot = (Pilot) this.fm;
          this.victim = pilot.target.actor;
        }
      } else {
        if (this.fm.getOverload() > this.fMaxLaunchG) {
          this.victim = null;
          return;
        }
        if (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) {
          this.victim = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier) getOwner()).getMissileTarget();
          this.pVictimOffset = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier) getOwner()).getMissileTargetOffset();
        } else {
          if (Config.isUSE_RENDER()) {
            this.victim = Selector.look(true, false, Main3D.cur3D()._camera3D[Main3D.cur3D().getRenderIndx()], World.getPlayerAircraft().getArmy(), -1, World.getPlayerAircraft(), false);
            if (this.victim == null) {
              this.victim = Main3D.cur3D().getViewPadlockEnemy();
            }
          }
        }
      }
    } catch (Exception exception) {
      EventLog.type("Missile doStart Exception: " + exception.getMessage());
    }
  }

  public void destroy() {
    if (isNet() && isNetMirror()) {
      doExplosionAir();
    }
    this.endNavLights();
    this.flameActive = false;
    this.smokeActive = false;
    this.spriteActive = false;
    this.endSmoke();
    this.victim = null;
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000F;
    super.destroy();
  }

  private boolean isSunTracking() {
    if (this.fSunRayAngle == 0.0F) {
      return false; // No Sun Ray Tracking possible
    }
    float sunAngle = GuidedMissileUtils.angleBetween(this, World.Sun().ToSun);

    if (sunAngle < this.fSunRayAngle) {
      return true;
    }
    return false;
  }

  private boolean isGroundTracking() {
    if (this.fGroundTrackFactor == 0.0F) {
      return false; // No Ground Clutter Tracking possible
    }
    this.pos.getAbs(p, or);
    or.wrap();
    float ttG = or.getTangage() * -1.0F;
    float missileAlt = (float) (p.z - Engine.land().HQ_Air(p.x, p.y));
    missileAlt /= 1000.0F;
    float groundFactor = ttG / (missileAlt * missileAlt);
    long lTimeCurrent = Time.current();
    if (this.lLastTimeNoFlare == 0L) {
      this.lLastTimeNoFlare = lTimeCurrent;
    }
    if (groundFactor > this.fGroundTrackFactor) {
      if (lTimeCurrent < this.lLastTimeNoFlare + this.lFlareLockTime) {
        return false;
      }
      return true;
    }
    this.lLastTimeNoFlare = lTimeCurrent;
    return false;
  }

  public NetMsgSpawn netReplicate(NetChannel netchannel)
          throws IOException {
    NetMsgSpawn netmsgspawn = super.netReplicate(netchannel);
    netmsgspawn.writeNetObj(getOwner().net);
    Point3d point3d = pos.getAbsPoint();
    netmsgspawn.writeFloat((float) point3d.x);
    netmsgspawn.writeFloat((float) point3d.y);
    netmsgspawn.writeFloat((float) point3d.z);
    Orient orient = pos.getAbsOrient();
    netmsgspawn.writeFloat(orient.azimut());
    netmsgspawn.writeFloat(orient.tangage());
    float f = (float) getSpeed(null);
    netmsgspawn.writeFloat(f);
    return netmsgspawn;
  }

  static class SPAWN
          implements NetSpawn {

    public void netSpawn(int i, NetMsgInput netmsginput) {
      NetObj netobj;
      netobj = netmsginput.readNetObj();
      if (netobj == null) {
        return;
      }
      try {
        Actor actor = (Actor) netobj.superObj();
        Point3d point3d = new Point3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
        Orient orient = new Orient(netmsginput.readFloat(), netmsginput.readFloat(), 0.0F);
        float f = netmsginput.readFloat();
        this.doSpawn(actor, netmsginput.channel(), i, point3d, orient, f);
        if (actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) {
          ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier) actor).shotMissile();
        }
      } catch (Exception exception) {
        System.out.println(exception.getMessage());
        exception.printStackTrace();
      }
      return;
    }

    public void doSpawn(com.maddox.il2.engine.Actor actor, NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f) {
    }

    SPAWN() {
    }
  }

  class Mirror extends ActorNet {

    public void msgNetNewChannel(NetChannel netchannel) {
      if (!Actor.isValid(actor())) {
        return;
      }
      if (netchannel.isMirrored(this)) {
        return;
      }
      try {
        if (netchannel.userState == 0) {
          NetMsgSpawn netmsgspawn = actor().netReplicate(netchannel);
          if (netmsgspawn != null) {
            postTo(netchannel, netmsgspawn);
            actor().netFirstUpdate(netchannel);
          }
        }
      } catch (Exception exception) {
        NetObj.printDebug(exception);
      }
      return;
    }

    public boolean netInput(NetMsgInput netmsginput)
            throws IOException {
      if (netmsginput.isGuaranted()) {
        return false;
      }
      if (isMirrored()) {
        out.unLockAndSet(netmsginput, 0);
        postReal(Message.currentTime(true), out);
      }
      theMissilePoint3d.set(netmsginput.readFloat(),
              netmsginput.readFloat(),
              netmsginput.readFloat());

      int i = netmsginput.readShort();
      int j = netmsginput.readShort();
      float f = -(((float) i * 180F) / 32000F);
      float f1 = ((float) j * 90F) / 32000F;
      theMissileOrient.set(f, f1, 0.0F);
      actor().pos.setAbs(theMissilePoint3d, theMissileOrient);
      return true;
    }
    NetMsgFiltered out;
    Point3d theMissilePoint3d = new Point3d();
    Orient theMissileOrient = new Orient();

    public Mirror(Actor actor, NetChannel netchannel, int i) {
      super(actor, netchannel, i);
      out = new NetMsgFiltered();
    }
  }

  class Master extends ActorNet
          implements NetUpdate {

    public void msgNetNewChannel(NetChannel netchannel) {
      if (!Actor.isValid(actor())) {
        return;
      }
      try {
        if (netchannel.isMirrored(this)) {
          return;
        }
        if (netchannel.userState == 0) {
          NetMsgSpawn netmsgspawn = actor().netReplicate(netchannel);
          if (netmsgspawn != null) {
            postTo(netchannel, netmsgspawn);
            actor().netFirstUpdate(netchannel);
          }
        }
      } catch (Exception exception) {
        NetObj.printDebug(exception);
      }
    }

    public boolean netInput(NetMsgInput netmsginput)
            throws IOException {
      return false;
    }

    public void netUpdate() {
      try {
        this.out.unLockAndClear();
        actor().pos.getAbs(theMissilePoint3d, theMissileOrient);
        this.out.writeFloat((float) theMissilePoint3d.x);
        this.out.writeFloat((float) theMissilePoint3d.y);
        this.out.writeFloat((float) theMissilePoint3d.z);
        theMissileOrient.wrap();
        int i = (int) ((theMissileOrient.getYaw() * 32000F) / 180F);
        int j = (int) ((theMissileOrient.tangage() * 32000F) / 90F);
        this.out.writeShort(i);
        this.out.writeShort(j);
        post(Time.current(), this.out);
      } catch (Exception exception) {
        NetObj.printDebug(exception);
      }
    }
    NetMsgFiltered out;
    Point3d theMissilePoint3d = new Point3d();
    Orient theMissileOrient = new Orient();

    public Master(Actor actor) {
      super(actor);
      out = new NetMsgFiltered();
    }
  }
}
