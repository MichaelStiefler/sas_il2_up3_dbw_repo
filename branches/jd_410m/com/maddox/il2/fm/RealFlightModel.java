package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.NetAircraft.Mirror;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import java.io.PrintStream;

public class RealFlightModel extends Pilot
{
  public boolean RealMode = true;

  public float indSpeed = 0.0F;
  private static int stallStringID = HUD.makeIdLog();
  public DifficultySettings Realism;
  Vector3d Cwl = new Vector3d();
  Vector3d Cwr = new Vector3d();
  Vector3d Chl = new Vector3d();
  Vector3d Chr = new Vector3d();
  Vector3d Cv = new Vector3d();
  Vector3d Fwl = new Vector3d();
  Vector3d Fwr = new Vector3d();
  Vector3d Fhl = new Vector3d();
  Vector3d Fhr = new Vector3d();
  Vector3d Fv = new Vector3d();

  private float superFuel = 10.0F;
  private long lastDangerTick;
  public float shakeLevel = 0.0F;
  public float producedShakeLevel = 0.0F;

  private float lastAcc = 1.0F;
  private float ailerInfluence = 1.0F;
  private float rudderInfluence = 1.0F;
  private float oldTime;
  private float deep;
  private float currDeep;
  private float indiffDnTime = 4.0F;
  private float knockDnTime = 0.0F;
  private float indiffUpTime = 4.0F;
  private float knockUpTime = 0.0F;
  private final float MAX_DN_OL = 3.5F;
  private final float MAX_UP_OL = 0.88F;
  public float saveDeep = 0.0F;
  private double su26add = 0.0D;
  private double spinCoeff = 0.0D;
  private SoundFX structuralFX;
  private boolean bSound = true;
  private float rD;
  public float Current_G_Limit = 8.0F;
  private int cycleCounter = 0;
  private float timeCounter = 0.0F;
  private int gearCutCounter = 0;
  private boolean bGearCut = false;
  private float max_G_Cycle = 1.0F;
  private float maxSpeed = 0.0F;
  private float hpOld;
  private int airborneState = 0;
  private Point3d airborneStartPoint = new Point3d();

  private Point3d TmpP = new Point3d();
  private Vector3d Vn = new Vector3d();
  private Vector3d TmpV = new Vector3d();
  private Vector3d TmpVd = new Vector3d();
  private Vector3d plAccel = new Vector3d();

  public RealFlightModel(String paramString)
  {
    super(paramString);
    this.AP = new Autopilot(this);
    this.Realism = World.cur().diffCur;

    this.maxSpeed = this.VmaxAllowed;
  }

  public Vector3d getW()
  {
    return this.RealMode ? this.W : this.Wtrue;
  }

  private void flutter()
  {
    if (this.Realism.Flutter_Effect)
      ((Aircraft)this.actor).msgCollision(this.actor, "CF_D0", "CF_D0");
  }

  private void flutterDamage()
  {
    if (this.Realism.Flutter_Effect)
    {
      switch (World.Rnd().nextInt(0, 29)) { case 0:
      case 1:
      case 2:
      case 3:
      case 20:
        str = "AroneL";
        break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 21:
        str = "AroneR";
        break;
      case 8:
      case 9:
      case 10:
      case 22:
        str = "VatorL";
        break;
      case 11:
      case 12:
      case 13:
      case 23:
        str = "VatorR";
        break;
      case 24:
      case 25:
      case 26:
        str = "Rudder1";
        break;
      case 27:
      case 28:
      case 29:
        str = "Rudder2";
        break;
      case 14:
        str = "WingLOut";
        break;
      case 15:
        str = "WingROut";
        break;
      case 16:
        str = "WingLMid";
        break;
      case 17:
        str = "WingRMid";
        break;
      case 18:
        str = "WingLIn";
        break;
      case 19:
        str = "WingRIn";
        break;
      default:
        str = "CF";
      }

      String str = str + "_D0";
      ((Aircraft)this.actor).msgCollision(this.actor, str, str);
    }
  }

  private void cutWing()
  {
    if (this.Realism.Flutter_Effect)
    {
      switch (World.Rnd().nextInt(0, 8))
      {
      case 0:
        str = "Tail1";
        break;
      case 1:
      case 2:
        str = "WingRMid";
        break;
      case 3:
      case 4:
        str = "WingLMid";
        break;
      case 5:
      case 6:
        str = "WingLIn";
        break;
      default:
        str = "WingRIn";
      }

      String str = str + "_D0";
      ((Aircraft)this.actor).msgCollision(this.actor, str, str);
    }
  }

  private void cutPart(int paramInt)
  {
    if (this.Realism.Flutter_Effect)
    {
      switch (paramInt) {
      case 0:
        str = "WingLOut";
        break;
      case 1:
        str = "WingLMid";
        break;
      case 2:
        str = "WingLIn";
        break;
      case 3:
        str = "WingRIn";
        break;
      case 4:
        str = "WingRMid";
        break;
      case 5:
        str = "WingROut";
        break;
      case 6:
        str = "Tail1";
        break;
      default:
        str = "Tail1";
      }

      String str = str + "_D0";
      ((Aircraft)this.actor).msgCollision(this.actor, str, str);
    }
  }

  private void dangerEM()
  {
    if (Time.tickCounter() < this.lastDangerTick + 1L) return;
    this.lastDangerTick = Time.tickCounter();
    Actor localActor = War.GetNearestEnemy(this.actor, -1, 700.0F);
    if (!(localActor instanceof Aircraft)) return;
    Aircraft localAircraft = (Aircraft)localActor;
    this.TmpVd.set(localAircraft.FM.Loc); this.TmpVd.sub(this.Loc); this.Or.transformInv(this.TmpVd); this.TmpVd.normalize();
    if (this.TmpVd.x < 0.98D) return;
    if (!(localAircraft.FM instanceof Pilot)) return;
    Pilot localPilot = (Pilot)localAircraft.FM;
    localPilot.setAsDanger(this.actor);
  }

  private void dangerEMAces()
  {
    Actor localActor = War.GetNearestEnemy(this.actor, -1, 300.0F);
    if (!(localActor instanceof Aircraft)) return;
    Aircraft localAircraft = (Aircraft)localActor;
    this.TmpVd.set(localAircraft.FM.Loc); this.TmpVd.sub(this.Loc); this.Or.transformInv(this.TmpVd); this.TmpVd.normalize();
    if (this.TmpV.x < 0.98D) return;
    if (!(localAircraft.FM instanceof Pilot)) return;
    Pilot localPilot = (Pilot)localAircraft.FM;
    localPilot.setAsDanger(this.actor);
  }

  private float MulForce(float paramFloat)
  {
    if ((paramFloat < 40.0F) || (paramFloat > 180.0F)) return 1.0F;

    return 1.0F + (70.0F - Math.abs(paramFloat - 110.0F)) * 0.04F;
  }

  public boolean isRealMode()
  {
    return this.RealMode;
  }
  public void setRealMode(boolean paramBoolean) { if (this.RealMode == paramBoolean) return;
    this.RealMode = paramBoolean;
    if (this.RealMode) this.AP.setStabAll(false);
  }

  private void checkAirborneState()
  {
    if (World.getPlayerFM() != this) return;
    if (!Actor.isAlive(this.actor)) return;
    switch (this.airborneState) {
    case 0:
      if (getAltitude() - Engine.land().HQ_Air(this.Loc.x, this.Loc.y) > 40.0D) {
        this.airborneState = 2;
        setWasAirborne(true);
        setStationedOnGround(false);
        EventLog.onAirInflight((Aircraft)this.actor);
        if (!Mission.hasRadioStations) {
          CmdEnv.top().exec("music RAND music/inflight");
        }

      }
      else
      {
        this.airborneState = 1;
        setStationedOnGround(true);
        if (!Mission.hasRadioStations)
          CmdEnv.top().exec("music RAND music/takeoff");
      }
      setCrossCountry(false);
      break;
    case 1:
      if (this.Vrel.length() > this.Vmin) {
        setStationedOnGround(false);
      }
      if ((getAltitude() - Engine.land().HQ_Air(this.Loc.x, this.Loc.y) <= 40.0D) || (this.Vrel.length() <= this.Vmin * 1.15F))
        break;
      this.airborneState = 2;
      setStationedOnGround(false);
      setNearAirdrome(false);
      setWasAirborne(true);
      this.airborneStartPoint.set(this.Loc);
      World.cur().scoreCounter.playerTakeoff();
      EventLog.onAirInflight((Aircraft)this.actor);
      if (Mission.hasRadioStations) break;
      CmdEnv.top().exec("music RAND music/inflight"); break;
    case 2:
      if ((!isCrossCountry()) && (this.Loc.distance(this.airborneStartPoint) > 50000.0D)) {
        setCrossCountry(true);
        World.cur().scoreCounter.playerDoCrossCountry();
      }
      if ((!this.Gears.onGround) || (this.Vrel.length() >= 1.0D))
        break;
      this.airborneState = 1;
      setStationedOnGround(true);
      if (!Mission.hasRadioStations)
        CmdEnv.top().exec("music RAND music/takeoff");
      if (Airport.distToNearestAirport(this.Loc) > 1500.0D)
      {
        World.cur().scoreCounter.playerLanding(true);
        setNearAirdrome(false);
      }
      else {
        World.cur().scoreCounter.playerLanding(false);
        setNearAirdrome(true);
      }

    }

    Mission.initRadioSounds();
  }

  private void initSound(Actor paramActor)
  {
    this.structuralFX = ((Aircraft)paramActor).newSound("models.structuralFX", false);
    setSound(false);
  }

  private void setSound(boolean paramBoolean) {
    this.bSound = paramBoolean;
  }

  private boolean getSound() {
    return this.bSound;
  }

  public void update(float paramFloat)
  {
    if (this.actor.isNetMirror()) {
      ((NetAircraft.Mirror)this.actor.net).fmUpdate(paramFloat);
      return;
    }

    if (getSound()) {
      initSound(this.actor);
    }
    this.V2 = (float)this.Vflow.lengthSquared();
    this.V = (float)Math.sqrt(this.V2);
    if (this.V * paramFloat > 5.0F) { update(paramFloat * 0.5F); update(paramFloat * 0.5F); return;
    }

    float f2 = 0.0F;
    float f3 = 0.0F;
    float f4 = 0.0F;
    if (!this.RealMode) {
      this.shakeLevel = 0.0F;
      super.update(paramFloat);

      if (isTick(44, 0)) {
        checkAirborneState();
      }
      if (World.cur().diffCur.Blackouts_N_Redouts) {
        calcOverLoad(paramFloat, false);
      }
      this.producedAM.set(0.0D, 0.0D, 0.0D);
      this.producedAF.set(0.0D, 0.0D, 0.0D);
      return;
    }
    moveCarrier();

    decDangerAggressiveness();
    if (this.Loc.z < -20.0D) ((Aircraft)this.actor).postEndAction(0.0D, this.actor, 4, null);
    if ((!isOk()) && (this.Group != null)) this.Group.delAircraft((Aircraft)this.actor);
    float f7;
    if ((Config.isUSE_RENDER()) && 
      (showFM) && (this.actor == Main3D.cur3D().viewActor())) {
      f5 = (float)this.W.x / (this.CT.getAileron() * 111.111F * this.SensRoll) * this.Sq.squareWing / 0.8F;
      if (Math.abs(f5) > 50.0F) f5 = 0.0F;
      f6 = (float)this.W.y / (-this.CT.getElevator() * 111.111F * this.SensPitch) * this.Sq.squareWing / 0.27F;
      if (Math.abs(f6) > 50.0F) f6 = 0.0F;
      f7 = (float)this.W.z / ((this.AOS - this.CT.getRudder() * 12.0F) * 111.111F * this.SensYaw) * this.Sq.squareWing / 0.15F;
      if (Math.abs(f7) > 50.0F) f7 = 0.0F;
      TextScr.output(5, 60, "~S RUDDR = " + (int)(f7 * 100.0F) / 100.0F);
      TextScr.output(5, 80, "~S VATOR = " + (int)(f6 * 100.0F) / 100.0F);
      TextScr.output(5, 100, "~S AERON = " + (int)(f5 * 100.0F) / 100.0F);
      String str = "";
      for (int i = 0; i < this.shakeLevel * 10.5F; i++) str = str + ">";
      TextScr.output(5, 120, "SHAKE LVL -" + this.shakeLevel);

      TextScr.output(5, 670, "Pylon = " + this.M.pylonCoeff);
      TextScr.output(5, 640, "WIND = " + (int)(this.Vwind.length() * 10.0D) / 10.0F + " " + (int)(this.Vwind.z * 10.0D) / 10.0F + " m/s");
      TextScr.output(5, 140, "BRAKE = " + this.CT.getBrake());

      i = 0;
      TextScr.output(225, 140, "---ENGINES (" + this.EI.getNum() + ")---" + this.EI.engines[i].getStage());
      TextScr.output(245, 120, "THTL " + (int)(100.0F * this.EI.engines[i].getControlThrottle()) + "%" + (this.EI.engines[i].getControlAfterburner() ? " (NITROS)" : ""));
      TextScr.output(245, 100, "PROP " + (int)(100.0F * this.EI.engines[i].getControlProp()) + "%" + (this.CT.getStepControlAuto() ? " (AUTO)" : ""));
      TextScr.output(245, 80, "MIX " + (int)(100.0F * this.EI.engines[i].getControlMix()) + "%");
      TextScr.output(245, 60, "RAD " + (int)(100.0F * this.EI.engines[i].getControlRadiator()) + "%" + (this.CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
      TextScr.output(245, 40, "SUPC " + this.EI.engines[i].getControlCompressor() + "x");
      TextScr.output(245, 20, "PropAoA :" + (int)Math.toDegrees(this.EI.engines[i].getPropAoA()));
      TextScr.output(245, 0, "PropPhi :" + (int)Math.toDegrees(this.EI.engines[i].getPropPhi()));

      TextScr.output(455, 120, "Cyls/Cams " + this.EI.engines[i].getCylindersOperable() + "/" + this.EI.engines[i].getCylinders());
      TextScr.output(455, 100, "Readyness " + (int)(100.0F * this.EI.engines[i].getReadyness()) + "%");
      TextScr.output(455, 80, "PRM " + (int)((int)(this.EI.engines[i].getRPM() * 0.02F) * 50.0F) + " rpm");
      TextScr.output(455, 60, "Thrust " + (int)this.EI.engines[i].getEngineForce().x + " N");
      TextScr.output(455, 40, "Fuel " + (int)(100.0F * this.M.fuel / this.M.maxFuel) + "% Nitro " + (int)(100.0F * this.M.nitro / this.M.maxNitro) + "%");
      TextScr.output(455, 20, "MPrs " + (int)(1000.0F * this.EI.engines[i].getManifoldPressure()) + " mBar");

      TextScr.output(640, 140, "---Controls---");
      TextScr.output(640, 120, "A/C: " + (this.CT.bHasAileronControl ? "" : "AIL ") + (this.CT.bHasElevatorControl ? "" : "ELEV ") + (this.CT.bHasRudderControl ? "" : "RUD ") + (this.Gears.bIsHydroOperable ? "" : "GEAR "));
      TextScr.output(640, 100, "ENG: " + (this.EI.engines[i].isHasControlThrottle() ? "" : "THTL ") + (this.EI.engines[i].isHasControlProp() ? "" : "PROP ") + (this.EI.engines[i].isHasControlMix() ? "" : "MIX ") + (this.EI.engines[i].isHasControlCompressor() ? "" : "SUPC ") + (this.EI.engines[i].isPropAngleDeviceOperational() ? "" : "GVRNR "));
      TextScr.output(640, 80, "PIL: (" + (int)(this.AS.getPilotHealth(0) * 100.0F) + "%)");
      TextScr.output(640, 60, "Sens: " + this.CT.Sensitivity);

      TextScr.output(400, 500, "+");
      TextScr.output(400, 400, "|");
      TextScr.output((int)(400.0F + 200.0F * this.CT.AileronControl), (int)(500.0F - 200.0F * this.CT.ElevatorControl), "+");
      TextScr.output((int)(400.0F + 200.0F * this.CT.RudderControl), 400, "|");

      TextScr.output(5, 200, "AOA = " + this.AOA);
      TextScr.output(5, 220, "Mass = " + this.M.getFullMass());
      TextScr.output(5, 320, "AERON TR = " + this.CT.trimAileron);
      TextScr.output(5, 300, "VATOR TR = " + this.CT.trimElevator);
      TextScr.output(5, 280, "RUDDR TR = " + this.CT.trimRudder);

      TextScr.output(245, 160, " pF = " + this.EI.engines[0].zatizeni * 100.0D + "%/hr");
      this.hpOld = (this.hpOld * 0.95F + 0.05F * this.EI.engines[0].w * this.EI.engines[0].engineMoment / 746.0F);
      TextScr.output(245, 180, " hp = " + this.hpOld);
      TextScr.output(245, 200, " eMoment = " + this.EI.engines[0].engineMoment);
      TextScr.output(245, 220, " pMoment = " + this.EI.engines[0].propMoment);
    }

    if (!this.Realism.Limited_Fuel) {
      this.superFuel = (this.M.fuel = Math.max(this.superFuel, this.M.fuel));
    }

    this.AP.update(paramFloat);
    ((Aircraft)this.actor).netUpdateWayPoint();

    this.CT.update(paramFloat, (float)this.Vflow.x, this.EI, true);

    float f5 = (float)(this.Vflow.x * this.Vflow.x) / 11000.0F; if (f5 > 1.0F) f5 = 1.0F;
    ForceFeedback.fxSetSpringGain(f5);

    if ((this.CT.saveWeaponControl[0] != 0) || (this.CT.saveWeaponControl[1] != 0) || (this.CT.saveWeaponControl[2] != 0)) {
      dangerEM();
    }
    this.Wing.setFlaps(this.CT.getFlap());
    FMupdate(paramFloat);
    this.EI.update(paramFloat);

    this.Gravity = (this.M.getFullMass() * Atmosphere.g());
    this.M.computeFullJ(this.J, this.J0);

    if (this.Realism.G_Limits) {
      if ((this.G_ClassCoeff < 0.0F) || (!((Aircraft)this.actor instanceof TypeBomber)))
      {
        this.Current_G_Limit = (this.ReferenceForce / this.M.getFullMass() - this.M.pylonCoeff);
      }
      else
      {
        this.Current_G_Limit = (this.ReferenceForce / this.M.getFullMass());
      }

      setLimitLoad(this.Current_G_Limit);
    }

    if (isTick(44, 0)) {
      this.AS.update(paramFloat * 44.0F);
      ((Aircraft)this.actor).rareAction(paramFloat * 44.0F, true);
      this.M.computeParasiteMass(this.CT.Weapons);
      this.Sq.computeParasiteDrag(this.CT, this.CT.Weapons);
      checkAirborneState();
      putScareShpere();
      dangerEMAces();
      if ((this.turnOffCollisions) && (!this.Gears.onGround) && (getAltitude() - Engine.land().HQ_Air(this.Loc.x, this.Loc.y) > 30.0D)) {
        this.turnOffCollisions = false;
      }

    }

    this.Or.wrap();
    if (this.Realism.Wind_N_Turbulence) World.wind().getVector(this.Loc, this.Vwind); else
      this.Vwind.set(0.0D, 0.0D, 0.0D);
    this.Vair.sub(this.Vwld, this.Vwind);
    this.Or.transformInv(this.Vair, this.Vflow);

    this.Density = Atmosphere.density((float)this.Loc.z);

    this.AOA = RAD2DEG(-(float)Math.atan2(this.Vflow.z, this.Vflow.x));
    this.AOS = RAD2DEG((float)Math.atan2(this.Vflow.y, this.Vflow.x));
    this.indSpeed = (getSpeed() * (float)Math.sqrt(this.Density / 1.225F));

    this.Mach = (this.V / Atmosphere.sonicSpeed((float)this.Loc.z));

    if (this.Mach > 0.8F) this.Mach = 0.8F;
    this.Kq = (1.0F / (float)Math.sqrt(1.0F - this.Mach * this.Mach));

    this.q_ = (this.Density * this.V2 * 0.5F);

    double d2 = this.Loc.z - this.Gears.screenHQ;
    if (d2 < 0.0D) d2 = 0.0D;

    float f1 = this.CT.getAileron() * 14.0F;
    f1 = this.Arms.WING_V * (float)Math.sin(DEG2RAD(this.AOS)) + this.SensRoll * this.ailerInfluence * (1.0F - 0.1F * this.CT.getFlap()) * f1;

    double d3 = 0.0D;
    double d4 = 0.0D;
    if (this.EI.engines[0].getType() < 2) {
      d3 = this.EI.engines[0].addVflow;

      if (this.Realism.Torque_N_Gyro_Effects) d4 = 0.5D * this.EI.engines[0].addVside;
    }

    this.Vn.set(-this.Arms.GCENTER, 0.85D * this.Arms.WING_END, -0.5D);
    this.Vn.cross(this.W, this.Vn);
    this.Vn.add(this.Vflow);
    float f8 = f1 - RAD2DEG((float)Math.atan2(this.Vn.z, this.Vn.x));
    this.Vn.x += 0.07000000000000001D * d3;

    double d1 = this.Vn.lengthSquared();
    d1 *= 0.5F * this.Density;

    f5 = f1 - RAD2DEG((float)Math.atan2(this.Vn.z + 0.07000000000000001D * d4 * this.EI.getPropDirSign(), this.Vn.x));
    float f10 = 0.015F * f1;
    if (f10 < 0.0F) f10 *= 0.18F;

    this.Cwl.x = (-d1 * (this.Wing.new_Cx(f5) + f10 + this.GearCX * this.CT.getGear() + this.radiatorCX * (this.EI.getRadiatorPos() + this.CT.getCockpitDoor()) + this.Sq.dragAirbrakeCx * this.CT.getAirBrake()));

    this.Cwl.z = (d1 * this.Wing.new_Cy(f5) * this.Kq);

    if (this.fmsfxCurrentType != 0) {
      if (this.fmsfxCurrentType == 1) {
        this.Cwl.z *= Aircraft.cvt(this.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
      }
      if (this.fmsfxCurrentType == 2) {
        this.Cwl.z = 0.0D;

        if (Time.current() >= this.fmsfxTimeDisable) {
          doRequestFMSFX(0, 0);
        }
      }

    }

    this.Vn.set(-this.Arms.GCENTER, -this.Arms.WING_END, -0.5D);
    this.Vn.cross(this.W, this.Vn);
    this.Vn.add(this.Vflow);
    float f9 = -f1 - RAD2DEG((float)Math.atan2(this.Vn.z, this.Vn.x));
    this.Vn.x += 0.07000000000000001D * d3;

    d1 = this.Vn.lengthSquared();
    d1 *= 0.5F * this.Density;

    float f6 = -f1 - RAD2DEG((float)Math.atan2(this.Vn.z - 0.07000000000000001D * d4 * this.EI.getPropDirSign(), this.Vn.x));
    f10 = -0.015F * f1;
    if (f10 < 0.0F) f10 *= 0.18F;

    this.Cwr.x = (-d1 * (this.Wing.new_Cx(f6) + f10 + this.GearCX * this.CT.getGear() + this.radiatorCX * this.EI.getRadiatorPos() + this.Sq.dragAirbrakeCx * this.CT.getAirBrake()));

    this.Cwr.z = (d1 * this.Wing.new_Cy(f6) * this.Kq);
    if (this.fmsfxCurrentType != 0) {
      if (this.fmsfxCurrentType == 1) {
        this.Cwr.z *= Aircraft.cvt(this.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
      }
      if (this.fmsfxCurrentType == 3) {
        this.Cwr.z = 0.0D;

        if (Time.current() >= this.fmsfxTimeDisable) {
          doRequestFMSFX(0, 0);
        }
      }

    }

    this.Cwl.y = (-d1 * this.Fusel.new_Cy(this.AOS));
    this.Cwl.x -= d1 * this.Fusel.new_Cx(this.AOS);

    this.Cwr.y = (-d1 * this.Fusel.new_Cy(this.AOS));
    this.Cwr.x -= d1 * this.Fusel.new_Cx(this.AOS);

    float f11 = this.Wing.get_AOA_CRYT();

    double d6 = 1.0D;
    double d7 = 0.5D + 0.4D * this.EI.getPowerOutput();
    double d8 = 1.2D + 0.4D * this.EI.getPowerOutput();
    if (this.spinCoeff < d7) this.spinCoeff = d7;
    if (this.spinCoeff > d8) this.spinCoeff = d8;

    f5 = f8;
    f6 = f9;

    if ((!this.Realism.Stalls_N_Spins) || (this.Gears.isUnderDeck()))
    {
      double d5;
      if (f5 > f6) {
        if (this.Cwl.z < this.Cwr.z) { d5 = this.Cwl.z; this.Cwl.z = this.Cwr.z; this.Cwr.z = d5; }
      }
      else if (this.Cwl.z > this.Cwr.z) { d5 = this.Cwl.z; this.Cwl.z = this.Cwr.z; this.Cwr.z = d5;
      }
    }
    else if ((f5 > f11) || (f6 > f11)) {
      this.spinCoeff += 0.2D * paramFloat;
      if ((this.Sq.squareRudders > 0.0D) && 
        (Math.abs(this.CT.RudderControl) > 0.5D) && (this.CT.RudderControl * this.W.z > 0.0D))
        this.spinCoeff -= 0.3D * paramFloat;
      float f12;
      if (f5 > f6) f12 = f5; else f12 = f6;
      this.turbCoeff = (0.8F * (f12 - f11));
      if (this.turbCoeff < 1.0F) this.turbCoeff = 1.0F;
      if (this.turbCoeff > 15.0F) this.turbCoeff = 15.0F;
      d6 = 1.0D - 0.2D * (f12 - f11);
      if (d6 < 0.2D) d6 = 0.2D;
      d6 /= this.turbCoeff;
      double d10 = d1 * this.turbCoeff * this.spinCoeff;
      float f13 = getAltitude() - (float)Engine.land().HQ_Air(this.Loc.x, this.Loc.y);
      if (f13 < 10.0F) d10 *= 0.1F * f13;

      if (f5 > f6) {
        this.Cwr.x += 0.01999999955296516D * d10 * this.Sq.spinCxloss;
        this.Cwl.x -= 0.25D * d10 * this.Sq.spinCxloss;
        this.Cwr.z += 0.01999999955296516D * d10 * this.Sq.spinCyloss;
        this.Cwl.z -= 0.1000000014901161D * d10 * this.Sq.spinCyloss;
      } else {
        this.Cwl.x += 0.01999999955296516D * d10 * this.Sq.spinCxloss;
        this.Cwr.x -= 0.25D * d10 * this.Sq.spinCxloss;
        this.Cwl.z += 0.01999999955296516D * d10 * this.Sq.spinCyloss;
        this.Cwr.z -= 0.1000000014901161D * d10 * this.Sq.spinCyloss;
      }
      this.rudderInfluence = (1.0F + 0.035F * this.turbCoeff);
    }
    else
    {
      this.turbCoeff = 1.0F;
      d6 = 1.0D;
      this.spinCoeff -= 0.2D * paramFloat;
      this.ailerInfluence = 1.0F;
      this.rudderInfluence = 1.0F;
    }

    if (isTick(15, 0))
      if (Math.abs(f5 - f6) > 5.0F)
        ForceFeedback.fxSetSpringZero((f6 - f5) * 0.04F, 0.0F);
      else
        ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
    double d12;
    if (d2 < 0.4D * this.Length) {
      d9 = 1.0D - d2 / (0.4D * this.Length);
      d11 = 1.0D + 0.2D * d9;
      d12 = 1.0D + 0.2D * d9;
      this.Cwl.z *= d11;
      this.Cwl.x *= d12;
      this.Cwr.z *= d11;
      this.Cwr.x *= d12;
    }

    f1 = this.CT.getElevator() * (this.CT.getElevator() > 0.0F ? 28.0F : 20.0F);

    this.Vn.set(-this.Arms.VER_STAB, 0.0D, 0.0D);
    this.Vn.cross(this.W, this.Vn);
    this.Vn.add(this.Vflow);
    double d9 = Math.sqrt(this.Vn.y * this.Vn.y + this.Vn.z * this.Vn.z);

    d3 = 0.0D;
    d4 = 0.0D;
    if (this.EI.engines[0].getType() < 2) {
      d11 = 1.0D + 0.04D * this.Arms.RUDDER;
      d11 = 1.0D / (d11 * d11);
      d12 = this.Vn.x + d11 * this.EI.engines[0].addVflow;
      if (d12 < 0.2D) d12 = 0.2D;
      d14 = 1.0D - 1.5D * d9 / d12;
      if (d14 < 0.0D) d14 = 0.0D;
      d3 = d14 * d11 * this.EI.engines[0].addVflow;
      this.Vn.x += d3;
      d15 = Math.min(0.0011D * this.Vn.x * this.Vn.x, 1.0D);
      if (this.Vn.x < 0.0D) d15 = 0.0D;

      if (this.Realism.Torque_N_Gyro_Effects) {
        d4 = d14 * d15 * this.EI.engines[0].addVside;
      }
    }
    double d11 = this.Density * this.Vn.lengthSquared() * 0.5D;

    if ((this.EI.getNum() == 1) && (this.EI.engines[0].getType() < 2)) {
      f5 = -RAD2DEG((float)Math.atan2(this.Vn.z - 0.36D * d4 * this.EI.getPropDirSign(), this.Vn.x)) - 2.0F - 0.002F * this.V - this.SensPitch * f1;

      f6 = -RAD2DEG((float)Math.atan2(this.Vn.z + 0.36D * d4 * this.EI.getPropDirSign(), this.Vn.x)) - 2.0F - 0.002F * this.V - this.SensPitch * f1;
    }
    else {
      f5 = f6 = -RAD2DEG((float)Math.atan2(this.Vn.z, this.Vn.x)) - 2.0F - 0.002F * this.V - this.SensPitch * f1;
    }

    this.Chl.x = (-d11 * this.Tail.new_Cx(f5));
    this.Chl.z = (d11 * this.Tail.new_Cy(f5));
    this.Chr.x = (-d11 * this.Tail.new_Cx(f6));
    this.Chr.z = (d11 * this.Tail.new_Cy(f6));
    this.Chl.y = (this.Chr.y = 0.0D);

    f1 = this.CT.getRudder() * (this.Sq.squareRudders < 0.05F ? 0.0F : 28.0F);

    if (this.EI.engines[0].getType() < 2) {
      f7 = -RAD2DEG((float)Math.atan2(this.Vn.y - 0.5D * d4 * this.EI.getPropDirSign(), this.Vn.x)) + this.SensYaw * this.rudderInfluence * f1;
    }
    else
    {
      f7 = -RAD2DEG((float)Math.atan2(this.Vn.y, this.Vn.x)) + this.SensYaw * this.rudderInfluence * f1;
    }

    this.Cv.x = (-d11 * this.Tail.new_Cx(f7));
    this.Cv.y = (d11 * this.Tail.new_Cy(f7));
    this.Cv.z = 0.0D;

    if (!this.Realism.Stalls_N_Spins) this.Cv.y += this.Cv.y;

    this.Vn.set(this.Vflow);
    d1 = this.Density * this.Vn.lengthSquared() * 0.5D;

    this.Fwl.scale(this.Sq.liftWingLIn + this.Sq.liftWingLMid + this.Sq.liftWingLOut, this.Cwl);
    this.Fwr.scale(this.Sq.liftWingRIn + this.Sq.liftWingRMid + this.Sq.liftWingROut, this.Cwr);
    this.Fwl.x -= d1 * (this.Sq.dragParasiteCx + this.Sq.dragProducedCx) * 0.5D;
    this.Fwr.x -= d1 * (this.Sq.dragParasiteCx + this.Sq.dragProducedCx) * 0.5D;

    this.Fhl.scale((this.Sq.liftStab + this.Sq.squareElevators) * 0.5F, this.Chl);
    this.Fhr.scale((this.Sq.liftStab + this.Sq.squareElevators) * 0.5F, this.Chr);
    this.Fv.scale(0.2F + this.Sq.liftKeel * 1.5F + this.Sq.squareRudders, this.Cv);

    this.AF.set(this.Fwl);
    this.AF.add(this.Fwr);

    if (isNAN(this.AF)) { this.AF.set(0.0D, 0.0D, 0.0D); flutter(); if (World.cur().isDebugFM()) System.out.println("AF isNAN"); 
    }
    else if (this.AF.length() > this.Gravity * 50.0F) {
      flutter();
      if (World.cur().isDebugFM()) System.out.println("A > 50.0");
      this.AF.normalize();
      this.AF.scale(this.Gravity * 50.0F);
    }
    else
    {
      if (this.Realism.G_Limits)
      {
        if (((getOverload() > getUltimateLoad() + World.Rnd().nextFloat(0.0F, 1.0F)) || (getOverload() < this.Negative_G_Limit - World.Rnd().nextFloat(0.0F, 0.5F))) && (!this.Gears.onGround()) && (World.Rnd().nextInt(0, 100) > 98)) {
          if (this.cutPart < 0)
            cutWing();
          else {
            cutPart(this.cutPart);
          }
        }

        if ((getOverload() > this.Current_G_Limit) || (getOverload() < this.Negative_G_Limit))
        {
          float f15 = Math.abs(getOverload());
          if (f15 > this.max_G_Cycle) {
            this.max_G_Cycle = f15;
          }
          this.timeCounter += paramFloat;
          if (this.timeCounter > 0.75F) {
            this.cycleCounter += 1;
            if (this.cycleCounter > 1)
            {
              float f14;
              if (getOverload() > 1.0F)
                f14 = (this.max_G_Cycle - this.Current_G_Limit) / this.Current_G_Limit;
              else
                f14 = (this.max_G_Cycle + this.Negative_G_Limit) / this.Negative_G_Limit;
              f14 *= f14;
              setSafetyFactor(f14);

              if (this.structuralFX != null) {
                this.structuralFX.play();
              }
              this.VmaxAllowed = (this.maxSpeed * (getSafetyFactor() * 0.3F + 0.55F));

              this.rD = World.Rnd().nextFloat();
              if (this.rD < 0.001F) {
                if (this.CT.bHasGearControl) {
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearR2_D0", "GearR2_D0");
                  this.gearCutCounter += 1;
                }
                this.Wing.CxMin_0 += 6.0F * this.rD;
                setSafetyFactor(250.0F * this.rD);
                this.CT.bHasGearControl = false;
              } else if (this.rD < 0.002F) {
                if (this.CT.bHasGearControl) {
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearL2_D0", "GearL2_D0");
                  this.gearCutCounter += 2;
                }
                this.Wing.CxMin_0 += 3.0F * this.rD;
                setSafetyFactor(125.0F * this.rD);
                this.CT.bHasGearControl = false;
              } else if (this.rD < 0.0025F) {
                if (this.CT.bHasGearControl) {
                  this.CT.GearControl = 1.0F;
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearL2_D0", "GearL2_D0");
                  this.CT.forceGear(1.0F);
                  this.gearCutCounter += 2;
                }
                this.Wing.CxMin_0 += 3.0F * this.rD;
                setSafetyFactor(125.0F * this.rD);
                this.CT.bHasGearControl = false;
              } else if (this.rD < 0.003F) {
                if (this.CT.bHasGearControl) {
                  this.CT.GearControl = 1.0F;
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearR2_D0", "GearR2_D0");
                  this.CT.forceGear(1.0F);
                  this.gearCutCounter += 1;
                }
                this.Wing.CxMin_0 += 3.0F * this.rD;
                setSafetyFactor(125.0F * this.rD);
                this.CT.bHasGearControl = false;
              } else if (this.rD < 0.0035F) {
                if (this.CT.bHasGearControl) {
                  this.CT.dvGear = 1.0F;
                  this.CT.forceGear(1.0F);
                  this.CT.GearControl = 1.0F;
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearR2_D0", "GearR2_D0");
                  ((Aircraft)this.actor).msgCollision(this.actor, "GearL2_D0", "GearL2_D0");
                  this.gearCutCounter += 3;
                }
                this.Wing.CxMin_0 += 8.0F * this.rD;
                setSafetyFactor(125.0F * this.rD);
                this.CT.bHasGearControl = false;
              } else if (this.rD < 0.04F) {
                this.SensYaw *= 0.68F;
              } else if (this.rD < 0.05F) {
                this.SensPitch *= 0.68F;
              } else if (this.rD < 0.06F) {
                this.SensRoll *= 0.68F;
              } else if (this.rD < 0.061F) {
                this.CT.dropFuelTanks();
              } else if (this.rD < 0.065F) {
                this.CT.bHasFlapsControl = false;
              } else if (this.rD >= 0.5F)
              {
                if (this.rD < 0.6F) {
                  this.Wing.CxMin_0 += 0.011F * this.rD;
                }
                else if ((int)this.M.getFullMass() % 2 == 0) {
                  this.Sq.getClass();
                  this.Sq.liftWingROut *= (0.95F - 0.2F * this.rD);
                  this.Wing.CxMin_0 += 0.011F * this.rD;
                } else {
                  this.Sq.getClass();
                  this.Sq.liftWingLOut *= (0.95F - 0.2F * this.rD);
                  this.Wing.CxMin_0 += 0.011F * this.rD;
                }
              }
            }
            this.timeCounter = 0.0F;
            this.max_G_Cycle = 1.0F;
          }
        } else {
          this.timeCounter = 0.0F;
          this.max_G_Cycle = 1.0F;
        }
      }
      else if ((getOverload() > 13.5F) && (!this.Gears.onGround()) && (World.Rnd().nextInt(0, 100) > 98)) { cutWing();
      }

      if ((this.indSpeed > 112.5F) && (World.Rnd().nextInt(0, 100) > 98))
      {
        if ((this.CT.getGear() >= 0.1F) && (this.CT.GearControl != 0.0F) && (!this.bGearCut)) {
          if (!(this.actor instanceof F4U)) {
            if ((World.Rnd().nextInt(0, 100) > 76) && (this.gearCutCounter != 1)) {
              ((Aircraft)this.actor).msgCollision(this.actor, "GearR2_D0", "GearR2_D0");
              this.gearCutCounter += 1;
            }
            if ((World.Rnd().nextInt(0, 100) > 76) && (this.gearCutCounter != 2)) {
              ((Aircraft)this.actor).msgCollision(this.actor, "GearL2_D0", "GearL2_D0");
              this.gearCutCounter += 2;
            }
          } else if (this.indSpeed > 180.0F) {
            if ((World.Rnd().nextInt(0, 100) > 76) && (this.gearCutCounter != 1)) {
              ((Aircraft)this.actor).msgCollision(this.actor, "GearR2_D0", "GearR2_D0");
              this.gearCutCounter += 1;
            }
            if ((World.Rnd().nextInt(0, 100) > 76) && (this.gearCutCounter != 2)) {
              ((Aircraft)this.actor).msgCollision(this.actor, "GearL2_D0", "GearL2_D0");
              this.gearCutCounter += 2;
            }
          }
        }
        if (this.gearCutCounter > 2) {
          this.bGearCut = true;
          this.CT.bHasGearControl = false;
        }
      }
      if ((this.indSpeed > 60.5F) && (this.CT.getWing() > 0.1F)) {
        if ((World.Rnd().nextInt(0, 100) > 90) && (((Aircraft)this.actor).isChunkAnyDamageVisible("WingLMid")))
          ((Aircraft)this.actor).msgCollision(this.actor, "WingLMid_D0", "WingLMid_D0");
        if ((World.Rnd().nextInt(0, 100) > 90) && (((Aircraft)this.actor).isChunkAnyDamageVisible("WingRMid")))
          ((Aircraft)this.actor).msgCollision(this.actor, "WingRMid_D0", "WingRMid_D0");
      }
      if ((this.indSpeed > 81.0F) && (this.CT.bHasFlapsControl) && (this.CT.FlapsControl > 0.21F) && ((this.indSpeed - 81.0F) * this.CT.getFlap() > 8.0F))
      {
        if ((World.getPlayerAircraft() == this.actor) && 
          (this.CT.bHasFlapsControl)) HUD.log("FailedFlaps");

        this.CT.bHasFlapsControl = false;
        this.CT.FlapsControl = 0.0F;
      }
      if ((this.indSpeed > this.VmaxAllowed) && (World.Rnd().nextFloat(0.0F, 16.0F) < this.indSpeed - this.VmaxAllowed) && (World.Rnd().nextInt(0, 99) < 2))
      {
        flutterDamage();
      }
      if (this.indSpeed > 310.0F) {
        if (World.cur().isDebugFM()) System.out.println("*** Sonic overspeed....");
        flutter();
      }

    }

    this.AM.set(0.0D, 0.0D, 0.0D);

    if (Math.abs(this.AOA) < 12.0F) {
      f1 = this.Or.getKren();
      if (f1 > 30.0F) f1 = 30.0F; else if (f1 < -30.0F) f1 = -30.0F;
      f1 = (float)(f1 * (Math.min(this.Vflow.x - 50.0D, 50.0D) * 0.003000000026077032D));
      this.AM.add(-f1 * 0.01F * this.Gravity, 0.0D, 0.0D);
    }

    if (!getOp(19)) {
      this.AM.y += 8.0F * this.Sq.squareWing * this.Vflow.x;
      this.AM.z += 200.0F * this.Sq.squareWing * this.EI.getPropDirSign();
    }

    double d13 = this.CT.getFlap() * 3.0D;
    if (d13 > 1.0D) d13 = 1.0D;
    double d14 = 0.0111D * Math.abs(this.AOA);
    if ((this.Wing.AOACritL < this.AOA) && (this.AOA < this.Wing.AOACritH)) d14 = 0.0D;
    else if (this.AOA >= this.Wing.AOACritH) d14 = Math.min(d14, 0.3D * (this.AOA - this.Wing.AOACritH));
    else if (this.Wing.AOACritL <= this.AOA) d14 = Math.min(d14, 0.3D * (this.Wing.AOACritL - this.AOA));
    double d15 = this.Arms.GCENTER + this.Arms.GC_FLAPS_SHIFT * d13 * (1.0D - d14) + this.Arms.GC_AOA_SHIFT * d14;

    this.TmpV.set(-d15, this.Arms.WING_MIDDLE * (1.3D + 1.0D * Math.sin(DEG2RAD(this.AOS))), -this.Arms.GCENTER_Z);
    this.TmpV.cross(this.TmpV, this.Fwl);
    this.AM.add(this.TmpV);
    this.TmpV.set(-d15, -this.Arms.WING_MIDDLE * (1.3D - 1.0D * Math.sin(DEG2RAD(this.AOS))), -this.Arms.GCENTER_Z);
    this.TmpV.cross(this.TmpV, this.Fwr);
    this.AM.add(this.TmpV);
    this.AM.x += this.su26add;

    this.TmpV.set(-this.Arms.HOR_STAB, 1.0D, 0.0D);
    this.TmpV.cross(this.TmpV, this.Fhl);
    this.AM.add(this.TmpV);
    this.TmpV.set(-this.Arms.HOR_STAB, -1.0D, 0.0D);
    this.TmpV.cross(this.TmpV, this.Fhr);
    this.AM.add(this.TmpV);
    this.TmpV.set(-this.Arms.VER_STAB, 0.0D, 1.0D);
    this.TmpV.cross(this.TmpV, this.Fv);
    this.AM.add(this.TmpV);

    double d16 = 1.0D - 1.E-005D * this.indSpeed;
    if (d16 < 0.8D) d16 = 0.8D;
    this.W.scale(d16);

    if (!this.Realism.Stalls_N_Spins) {
      this.AM.y += this.AF.z * 0.5D * Math.sin(DEG2RAD(Math.abs(this.AOA)));
    }

    if (this.W.lengthSquared() > 25.0D) {
      this.W.scale(5.0D / this.W.length());
    }
    if ((!this.Realism.Stalls_N_Spins) && (this.Vflow.x > 20.0D)) {
      this.W.z += this.AOS * paramFloat;
    }

    this.AF.add(this.producedAF);
    this.AM.add(this.producedAM);
    this.producedAF.set(0.0D, 0.0D, 0.0D);
    this.producedAM.set(0.0D, 0.0D, 0.0D);

    this.AF.add(this.EI.producedF);
    this.AM.add(this.EI.producedM);

    if (World.cur().diffCur.Torque_N_Gyro_Effects) {
      this.GM.set(this.EI.getGyro());
      this.GM.scale(d6);
      this.AM.add(this.GM);
    }

    this.GF.set(0.0D, 0.0D, 0.0D);
    this.GM.set(0.0D, 0.0D, 0.0D);
    if (Time.tickCounter() % 2 != 0) this.Gears.roughness = this.Gears.plateFriction(this);
    this.Gears.ground(this, true);
    int j = 5;
    if ((this.GF.lengthSquared() == 0.0D) && (this.GM.lengthSquared() == 0.0D)) j = 1;

    this.SummF.add(this.AF, this.GF);
    this.ACmeter.set(this.SummF);
    this.ACmeter.scale(1.0F / this.Gravity);

    this.TmpV.set(0.0D, 0.0D, -this.Gravity);
    this.Or.transformInv(this.TmpV);
    this.GF.add(this.TmpV);

    this.SummF.add(this.AF, this.GF);
    this.SummM.add(this.AM, this.GM);
    double d17 = 1.0D / this.M.mass;
    this.LocalAccel.scale(d17, this.SummF);

    if (Math.abs(getRollAcceleration()) > 50000.5F) {
      ForceFeedback.fxPunch(this.SummM.x > 0.0D ? 0.9F : -0.9F, 0.0F, 1.0F);
      if (World.cur().isDebugFM()) System.out.println("Punched (Axial = " + this.SummM.x + ")");
    }
    if (Math.abs(getOverload() - this.lastAcc) > 0.5F) {
      ForceFeedback.fxPunch(World.Rnd().nextFloat(-0.5F, 0.5F), -0.9F, getSpeed() * 0.05F);
      if (World.cur().isDebugFM()) System.out.println("Punched (Lat = " + Math.abs(getOverload() - this.lastAcc) + ")");
    }
    this.lastAcc = getOverload();

    if (isNAN(this.AM)) { this.AM.set(0.0D, 0.0D, 0.0D); flutter(); if (World.cur().isDebugFM()) System.out.println("AM isNAN"); 
    }
    else if (this.AM.length() > this.Gravity * 150.0F) {
      flutter();
      if (World.cur().isDebugFM()) System.out.println("SummM > 150g");
      this.AM.normalize();
      this.AM.scale(this.Gravity * 150.0F);
    }

    this.dryFriction = (float)(this.dryFriction - 0.01D);
    if (this.Gears.gearsChanged) this.dryFriction = 1.0F;
    if (this.Gears.nOfPoiOnGr > 0) this.dryFriction += 0.02F;
    if (this.dryFriction < 1.0F) this.dryFriction = 1.0F;
    if (this.dryFriction > 32.0F) this.dryFriction = 32.0F;
    float f16 = 4.0F * (0.25F - this.EI.getPowerOutput());
    if (f16 < 0.0F) f16 = 0.0F;
    f16 *= f16;
    f16 *= this.dryFriction;
    float f17 = f16 * this.M.mass * this.M.mass;
    if ((!this.brakeShoe) && (
      ((this.Gears.nOfPoiOnGr == 0) && (this.Gears.nOfGearsOnGr < 3)) || (f16 == 0.0F) || (this.SummM.lengthSquared() > 2.0F * f17) || (this.SummF.lengthSquared() > 80.0F * f17) || (this.W.lengthSquared() > 0.00014F * f16) || (this.Vwld.lengthSquared() > 0.09F * f16)))
    {
      double d18 = 1.0D / j;
      for (int k = 0; k < j; k++) {
        this.SummF.add(this.AF, this.GF);
        this.SummM.add(this.AM, this.GM);

        this.AW.x = (((this.J.y - this.J.z) * this.W.y * this.W.z + this.SummM.x) / this.J.x);
        this.AW.y = (((this.J.z - this.J.x) * this.W.z * this.W.x + this.SummM.y) / this.J.y);
        this.AW.z = (((this.J.x - this.J.y) * this.W.x * this.W.y + this.SummM.z) / this.J.z);
        this.TmpV.scale(d18 * paramFloat, this.AW);
        this.W.add(this.TmpV);

        this.Or.transform(this.W, this.Vn);
        this.TmpV.scale(d18 * paramFloat, this.W);
        this.Or.increment((float)(-RAD2DEG(this.TmpV.z)), (float)(-RAD2DEG(this.TmpV.y)), (float)RAD2DEG(this.TmpV.x));

        this.Or.transformInv(this.Vn, this.W);

        this.TmpV.scale(d17, this.SummF);
        this.Or.transform(this.TmpV);
        this.Accel.set(this.TmpV);
        this.TmpV.scale(d18 * paramFloat);
        this.Vwld.add(this.TmpV);

        this.TmpV.scale(d18 * paramFloat, this.Vwld);
        this.TmpP.set(this.TmpV);
        this.Loc.add(this.TmpP);

        this.GF.set(0.0D, 0.0D, 0.0D);
        this.GM.set(0.0D, 0.0D, 0.0D);
        if (k < j - 1) {
          this.Gears.ground(this, true);

          this.TmpV.set(0.0D, 0.0D, -this.Gravity);
          this.Or.transformInv(this.TmpV);
          this.GF.add(this.TmpV);
        }

      }

      for (k = 0; k < 3; k++) {
        this.Gears.gWheelAngles[k] = ((this.Gears.gWheelAngles[k] + (float)Math.toDegrees(Math.atan(this.Gears.gVelocity[k] * paramFloat / 0.375D))) % 360.0F);

        this.Gears.gVelocity[k] *= 0.949999988079071D;
      }

      this.HM.chunkSetAngles("GearL1_D0", 0.0F, -this.Gears.gWheelAngles[0], 0.0F);
      this.HM.chunkSetAngles("GearR1_D0", 0.0F, -this.Gears.gWheelAngles[1], 0.0F);
      this.HM.chunkSetAngles("GearC1_D0", 0.0F, -this.Gears.gWheelAngles[2], 0.0F);
    }
    float f18;
    if ((this.Leader != null) && (isTick(128, 97)) && 
      (Actor.isAlive(this.Leader.actor)) && (!this.Gears.onGround)) {
      f18 = (float)this.Loc.distance(this.Leader.Loc);
      if (f18 > 3000.0F) Voice.speakDeviateBig((Aircraft)this.Leader.actor);
      else if (f18 > 1700.0F) Voice.speakDeviateSmall((Aircraft)this.Leader.actor);

    }

    this.shakeLevel = 0.0F;
    if (this.Gears.onGround())
    {
      this.shakeLevel = (float)(this.shakeLevel + 30.0D * this.Gears.roughness * this.Vrel.length() / this.M.mass);
    } else {
      if (this.indSpeed > 10.0F) {
        f18 = (float)Math.sin(Math.toRadians(Math.abs(this.AOA)));
        if (f18 > 0.02F) {
          f18 *= f18;
          this.shakeLevel += 0.07F * (f18 - 0.0004F) * (this.indSpeed - 10.0F);
          if ((isTick(30, 0)) && (this.shakeLevel > 0.6F)) {
            HUD.log(stallStringID, "Stall");
          }

        }

      }

      if (this.indSpeed > 35.0F) {
        if ((this.CT.bHasGearControl) && ((this.Gears.lgear) || (this.Gears.rgear)) && (this.CT.getGear() > 0.0F))
          this.shakeLevel += 0.004F * this.CT.getGear() * (this.indSpeed - 35.0F);
        if (this.CT.getFlap() > 0.0F) this.shakeLevel += 0.004F * this.CT.getFlap() * (this.indSpeed - 35.0F);
      }
    }

    if (this.indSpeed > this.VmaxAllowed * 0.8F) this.shakeLevel = (0.01F * (this.indSpeed - this.VmaxAllowed * 0.8F));

    if (World.cur().diffCur.Head_Shake) {
      this.shakeLevel += this.producedShakeLevel;
      this.producedShakeLevel *= 0.9F;
    }

    if (this.shakeLevel > 1.0F) this.shakeLevel = 1.0F;
    ForceFeedback.fxShake(this.shakeLevel);

    if (World.cur().diffCur.Blackouts_N_Redouts) calcOverLoad(paramFloat, true);
  }

  private void calcOverLoad(float paramFloat, boolean paramBoolean)
  {
    if (paramFloat > 1.0F) paramFloat = 1.0F;

    if ((this.Gears.onGround()) || (!paramBoolean)) { this.plAccel.set(0.0D, 0.0D, 0.0D);
    } else {
      this.plAccel.set(getAccel());
      this.plAccel.scale(0.1019999980926514D);
    }
    this.plAccel.z += 0.5D;
    this.Or.transformInv(this.plAccel);
    float f = -0.5F + (float)this.plAccel.z;
    this.deep = 0.0F;
    if (f < -0.6F) this.deep = (f + 0.6F);
    if (f > 2.2F) this.deep = (f - 2.2F);

    if (this.knockDnTime > 0.0F) this.knockDnTime -= paramFloat;
    if (this.knockUpTime > 0.0F) this.knockUpTime -= paramFloat;
    if (this.indiffDnTime < 4.0F) this.indiffDnTime += paramFloat;
    if (this.indiffUpTime < 4.0F) this.indiffUpTime += 0.3F * paramFloat;

    if (this.deep > 0.0F) {
      if (this.indiffDnTime > 0.0F) this.indiffDnTime -= 0.8F * this.deep * paramFloat;
      if ((this.deep > 2.3F) && (this.knockDnTime < 18.4F)) this.knockDnTime += 0.75F * this.deep * paramFloat;
      if (this.indiffDnTime > 0.1F) {
        this.currDeep = 0.0F;
      } else {
        this.currDeep = (this.deep * 0.08F * 3.5F);
        if (this.currDeep > 0.8F) this.currDeep = 0.8F; 
      }
    }
    else if (this.deep < 0.0F) {
      this.deep = (-this.deep);
      if (this.deep < 0.84F) this.deep = 0.84F;
      if (this.indiffUpTime > 0.0F) this.indiffUpTime -= 1.2F * this.deep * paramFloat;
      if ((this.deep > 2.3F) && (this.knockUpTime < 16.1F)) this.knockUpTime += this.deep * paramFloat;
      if (this.indiffUpTime > 0.1F)
        this.currDeep = 0.0F;
      else {
        this.currDeep = (this.deep * 0.42F * 0.88F);
      }
      this.currDeep = (-this.currDeep);
    } else {
      this.currDeep = 0.0F;
    }
    if (this.knockUpTime > 10.809999F) this.currDeep = -0.88F;
    if (this.knockDnTime > 14.03F) this.currDeep = 3.5F;
    if (this.currDeep > 3.5F) this.currDeep = 3.5F;
    if (this.currDeep < -0.88F) this.currDeep = -0.88F;

    if (this.saveDeep > 0.8F) {
      this.CT.Sensitivity = (1.0F - (this.saveDeep - 0.8F));
      if (this.CT.Sensitivity < 0.0F) this.CT.Sensitivity = 0.0F;
    }
    else if (this.saveDeep < -0.4F) {
      this.CT.Sensitivity = (1.0F + (this.saveDeep + 0.4F));
      if (this.CT.Sensitivity < 0.0F) this.CT.Sensitivity = 0.0F; 
    } else {
      this.CT.Sensitivity = 1.0F;
    }this.CT.Sensitivity *= this.AS.getPilotHealth(0);

    if (this.saveDeep < this.currDeep) {
      this.saveDeep += 0.3F * paramFloat;
      if (this.saveDeep > this.currDeep) this.saveDeep = this.currDeep; 
    }
    else {
      this.saveDeep -= 0.2F * paramFloat;
      if (this.saveDeep < this.currDeep) this.saveDeep = this.currDeep;
    }
  }

  public void gunMomentum(Vector3d paramVector3d, boolean paramBoolean)
  {
    this.producedAM.x += paramVector3d.x;
    this.producedAM.y += paramVector3d.y;
    this.producedAM.z += paramVector3d.z;
    float f = (float)paramVector3d.length() * 3.5E-005F;
    if ((paramBoolean) && (f > 0.5F)) f *= 0.05F;
    if (this.producedShakeLevel < f) this.producedShakeLevel = f;
  }
}