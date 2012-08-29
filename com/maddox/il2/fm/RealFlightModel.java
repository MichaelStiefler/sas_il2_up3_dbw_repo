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
import com.maddox.il2.ai.air.Maneuver;
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
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.F84G1;
import com.maddox.il2.objects.air.F84G2;
import com.maddox.il2.objects.air.F9F;
import com.maddox.il2.objects.air.F_86A;
import com.maddox.il2.objects.air.F_86F;
import com.maddox.il2.objects.air.Mig_15F;
import com.maddox.il2.objects.air.Mig_17;
import com.maddox.il2.objects.air.NetAircraft.Mirror;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class RealFlightModel extends Pilot
{
  public boolean RealMode;
  public float indSpeed;
  private static int stallStringID = HUD.makeIdLog();
  public DifficultySettings Realism;
  Vector3d Cwl;
  Vector3d Cwr;
  Vector3d Chl;
  Vector3d Chr;
  Vector3d Cv;
  Vector3d Fwl;
  Vector3d Fwr;
  Vector3d Fhl;
  Vector3d Fhr;
  Vector3d Fv;
  private float superFuel;
  private long lastDangerTick;
  public float shakeLevel;
  public float producedShakeLevel;
  private float lastAcc;
  private float ailerInfluence;
  private float rudderInfluence;
  private float oldTime;
  private float deep;
  private float currDeep;
  private float indiffDnTime;
  private float knockDnTime;
  private float indiffUpTime;
  private float knockUpTime;
  private final float MAX_DN_OL = 3.5F;
  private final float MAX_UP_OL = 0.88F;
  public float saveDeep;
  private double su26add;
  private double spinCoeff;
  private int airborneState;
  private Point3d airborneStartPoint;
  private Point3d TmpP;
  private Vector3d Vn;
  private Vector3d TmpV;
  private Vector3d TmpVd;
  private Vector3d plAccel;

  public RealFlightModel(String paramString)
  {
    super(paramString);
    this.RealMode = true;
    this.indSpeed = 0.0F;
    this.Cwl = new Vector3d();
    this.Cwr = new Vector3d();
    this.Chl = new Vector3d();
    this.Chr = new Vector3d();
    this.Cv = new Vector3d();
    this.Fwl = new Vector3d();
    this.Fwr = new Vector3d();
    this.Fhl = new Vector3d();
    this.Fhr = new Vector3d();
    this.Fv = new Vector3d();
    this.superFuel = 10.0F;
    this.shakeLevel = 0.0F;
    this.producedShakeLevel = 0.0F;
    this.lastAcc = 1.0F;
    this.ailerInfluence = 1.0F;
    this.rudderInfluence = 1.0F;
    this.indiffDnTime = 4.0F;
    this.knockDnTime = 0.0F;
    this.indiffUpTime = 4.0F;
    this.knockUpTime = 0.0F;
    this.saveDeep = 0.0F;
    this.su26add = 0.0D;
    this.spinCoeff = 0.0D;
    this.airborneState = 0;
    this.airborneStartPoint = new Point3d();
    this.TmpP = new Point3d();
    this.Vn = new Vector3d();
    this.TmpV = new Vector3d();
    this.TmpVd = new Vector3d();
    this.plAccel = new Vector3d();
    this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage = new Autopilot(this);
    this.Realism = World.cur().diffCur;
  }

  public Vector3d getW()
  {
    return this.RealMode ? this.jdField_W_of_type_ComMaddoxJGPVector3d : this.Wtrue;
  }

  private void flutter()
  {
    if (this.Realism.Flutter_Effect)
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "CF_D0", "CF_D0");
  }

  private void flutterDamage()
  {
    if (this.Realism.Flutter_Effect)
    {
      switch (World.Rnd().nextInt(0, 29))
      {
      case 0:
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
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, str, str);
    }
  }

  private void cutWing()
  {
    if (this.Realism.Flutter_Effect)
    {
      switch (World.Rnd().nextInt(0, 4))
      {
      case 0:
        str = "WingLMid";
        break;
      case 1:
        str = "WingRMid";
        break;
      case 2:
        str = "WingLIn";
        break;
      default:
        str = "WingRIn";
      }

      String str = str + "_D0";
      ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, str, str);
    }
  }

  private void dangerEM()
  {
    if (Time.tickCounter() >= this.lastDangerTick + 1L)
    {
      this.lastDangerTick = Time.tickCounter();
      Actor localActor = War.GetNearestEnemy(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, -1, 700.0F);
      if ((localActor instanceof Aircraft))
      {
        Aircraft localAircraft = (Aircraft)localActor;
        this.TmpVd.set(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.TmpVd.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpVd);
        this.TmpVd.normalize();
        if ((this.TmpVd.jdField_x_of_type_Double >= 0.98D) && ((localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)))
        {
          Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
          localPilot.setAsDanger(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
      }
    }
  }

  private void dangerEMAces()
  {
    Actor localActor = War.GetNearestEnemy(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, -1, 300.0F);
    if ((localActor instanceof Aircraft))
    {
      Aircraft localAircraft = (Aircraft)localActor;
      this.TmpVd.set(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.TmpVd.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpVd);
      this.TmpVd.normalize();
      if ((this.TmpV.jdField_x_of_type_Double >= 0.98D) && ((localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)))
      {
        Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        localPilot.setAsDanger(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      }
    }
  }

  private float MulForce(float paramFloat)
  {
    if ((paramFloat < 40.0F) || (paramFloat > 180.0F)) {
      return 1.0F;
    }
    return 1.0F + (70.0F - Math.abs(paramFloat - 110.0F)) * 0.04F;
  }

  public boolean isRealMode()
  {
    return this.RealMode;
  }

  public void setRealMode(boolean paramBoolean)
  {
    if (this.RealMode != paramBoolean)
    {
      this.RealMode = paramBoolean;
      if (this.RealMode)
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
    }
  }

  private void checkAirborneState()
  {
    if ((World.getPlayerFM() == this) && (Actor.isAlive(this.jdField_actor_of_type_ComMaddoxIl2EngineActor)))
      switch (this.airborneState)
      {
      default:
        break;
      case 0:
        if (getAltitude() - Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 40.0D)
        {
          this.airborneState = 2;
          setWasAirborne(true);
          setStationedOnGround(false);
          EventLog.onAirInflight((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          CmdEnv.top().exec("music RAND music/inflight");
        }
        else {
          this.airborneState = 1;
          setStationedOnGround(true);
          CmdEnv.top().exec("music RAND music/takeoff");
        }
        setCrossCountry(false);
        break;
      case 1:
        if (this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() > this.jdField_Vmin_of_type_Float)
          setStationedOnGround(false);
        if ((getAltitude() - Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) <= 40.0D) || (this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() <= this.jdField_Vmin_of_type_Float * 1.15F))
          break;
        this.airborneState = 2;
        setStationedOnGround(false);
        setNearAirdrome(false);
        setWasAirborne(true);
        this.airborneStartPoint.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        World.cur().scoreCounter.playerTakeoff();
        EventLog.onAirInflight((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        CmdEnv.top().exec("music RAND music/inflight"); break;
      case 2:
        if ((!isCrossCountry()) && (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.airborneStartPoint) > 50000.0D))
        {
          setCrossCountry(true);
          World.cur().scoreCounter.playerDoCrossCountry();
        }
        if ((!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround) || (this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() >= 1.0D))
          break;
        this.airborneState = 1;
        setStationedOnGround(true);
        CmdEnv.top().exec("music RAND music/takeoff");
        if (Airport.distToNearestAirport(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 1500.0D)
        {
          World.cur().scoreCounter.playerLanding(true);
          setNearAirdrome(false);
        }
        else {
          World.cur().scoreCounter.playerLanding(false);
          setNearAirdrome(true);
        }
      }
  }

  public void update(float paramFloat)
  {
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isNetMirror())
    {
      ((NetAircraft.Mirror)this.jdField_actor_of_type_ComMaddoxIl2EngineActor.net).fmUpdate(paramFloat);
    }
    else {
      this.jdField_V2_of_type_Float = (float)this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.lengthSquared();
      this.jdField_V_of_type_Float = (float)Math.sqrt(this.jdField_V2_of_type_Float);
      if (this.jdField_V_of_type_Float * paramFloat > 5.0F)
      {
        update(paramFloat * 0.5F);
        update(paramFloat * 0.5F);
      }
      else {
        float f1 = 0.0F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        if (!this.RealMode)
        {
          this.shakeLevel = 0.0F;
          super.update(paramFloat);
          if (isTick(44, 0))
            checkAirborneState();
          if (World.cur().diffCur.Blackouts_N_Redouts)
            calcOverLoad(paramFloat, false);
          this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_producedAF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
        }
        else {
          moveCarrier();
          decDangerAggressiveness();
          if (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double < -20.0D)
            ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(0.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 4, null);
          if ((!isOk()) && (this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null))
            this.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          if ((Config.isUSE_RENDER()) && (Maneuver.showFM) && (this.jdField_actor_of_type_ComMaddoxIl2EngineActor == Main3D.cur3D().viewActor()))
          {
            f4 = (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double / (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron() * 111.111F * this.jdField_SensRoll_of_type_Float) * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing / 0.8F;
            if (Math.abs(f4) > 50.0F)
              f4 = 0.0F;
            float f5 = (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double / (-this.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator() * 111.111F * this.jdField_SensPitch_of_type_Float) * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing / 0.27F;
            if (Math.abs(f5) > 50.0F)
              f5 = 0.0F;
            float f6 = (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double / ((this.jdField_AOS_of_type_Float - this.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 12.0F) * 111.111F * this.jdField_SensYaw_of_type_Float) * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing / 0.15F;
            if (Math.abs(f6) > 50.0F)
              f6 = 0.0F;
            TextScr.output(5, 60, "~S RUDDR = " + (int)(f6 * 100.0F) / 100.0F);
            TextScr.output(5, 80, "~S VATOR = " + (int)(f5 * 100.0F) / 100.0F);
            TextScr.output(5, 100, "~S AERON = " + (int)(f4 * 100.0F) / 100.0F);
            String str = "";
            for (int i = 0; i < this.shakeLevel * 10.5F; i++) {
              str = str + ">";
            }
            TextScr.output(5, 120, "SHAKE LVL -" + this.shakeLevel);
            TextScr.output(5, 140, "BRAKE = " + this.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake());
            int j = 0;
            TextScr.output(225, 140, "---ENGINES (" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() + ")---" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getStage());
            TextScr.output(245, 120, "THTL " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlThrottle()) + "%" + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
            TextScr.output(245, 100, "PROP " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlProp()) + "%" + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getStepControlAuto() ? " (AUTO)" : ""));
            TextScr.output(245, 80, "MIX " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlMix()) + "%");
            TextScr.output(245, 60, "RAD " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlRadiator()) + "%" + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getRadiatorControlAuto() ? " (AUTO)" : ""));
            TextScr.output(245, 40, "SUPC " + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getControlCompressor() + "x");
            TextScr.output(245, 20, "PropAoA :" + (int)Math.toDegrees(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getPropAoA()));
            TextScr.output(245, 0, "PropPhi :" + (int)Math.toDegrees(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getPropPhi()));
            TextScr.output(455, 120, "Cyls/Cams " + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getCylindersOperable() + "/" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getCylinders());
            TextScr.output(455, 100, "Readyness " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getReadyness()) + "%");
            TextScr.output(455, 80, "PRM " + (int)((int)(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getRPM() * 0.02F) * 50.0F) + " rpm");
            TextScr.output(455, 60, "Thrust " + (int)this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getEngineForce().x + " N");
            TextScr.output(455, 40, "Fuel " + (int)(100.0F * this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel) + "% Nitro " + (int)(100.0F * this.jdField_M_of_type_ComMaddoxIl2FmMass.nitro / this.jdField_M_of_type_ComMaddoxIl2FmMass.maxNitro) + "%");
            TextScr.output(455, 20, "MPrs " + (int)(1000.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getManifoldPressure()) + " mBar");
            TextScr.output(640, 140, "---Controls---");
            TextScr.output(640, 120, "A/C: " + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasAileronControl ? "" : "AIL ") + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasElevatorControl ? "" : "ELEV ") + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasRudderControl ? "" : "RUD ") + (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.bIsHydroOperable ? "" : "GEAR "));
            TextScr.output(640, 100, "ENG: " + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].isHasControlThrottle() ? "" : "THTL ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].isHasControlProp() ? "" : "PROP ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].isHasControlMix() ? "" : "MIX ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
            TextScr.output(640, 80, "PIL: (" + (int)(this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.getPilotHealth(0) * 100.0F) + "%)");
            TextScr.output(640, 60, "Sens: " + this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity);
            TextScr.output(400, 500, "+");
            TextScr.output(400, 400, "|");
            TextScr.output((int)(400.0F + 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), (int)(500.0F - 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), "+");
            TextScr.output((int)(400.0F + 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl), 400, "|");
            TextScr.output(5, 200, "AOA = " + this.jdField_AOA_of_type_Float);
            TextScr.output(5, 220, "Mass = " + this.jdField_M_of_type_ComMaddoxIl2FmMass.getFullMass());
            TextScr.output(5, 320, "AERON TR = " + this.jdField_CT_of_type_ComMaddoxIl2FmControls.trimAileron);
            TextScr.output(5, 300, "VATOR TR = " + this.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator);
            TextScr.output(5, 280, "RUDDR TR = " + this.jdField_CT_of_type_ComMaddoxIl2FmControls.trimRudder);
          }
          if (!this.Realism.Limited_Fuel)
            this.superFuel = (this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel = Math.max(this.superFuel, this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel));
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.update(paramFloat);
          ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).netUpdateWayPoint();
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.update(paramFloat, (float)this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double, this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface, true);
          float f4 = (float)(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) / 11000.0F;
          if (f4 > 1.0F)
            f4 = 1.0F;
          ForceFeedback.fxSetSpringGain(f4);
          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[0] != 0) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[1] != 0) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[2] != 0))
            dangerEM();
          this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.setFlaps(this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
          FMupdate(paramFloat);
          this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.update(paramFloat);
          this.jdField_Gravity_of_type_Float = (this.jdField_M_of_type_ComMaddoxIl2FmMass.getFullMass() * Atmosphere.g());
          this.jdField_M_of_type_ComMaddoxIl2FmMass.computeFullJ(this.jdField_J_of_type_ComMaddoxJGPVector3d, this.J0);
          if (isTick(44, 0))
          {
            this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.update(paramFloat * 44.0F);
            ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).rareAction(paramFloat * 44.0F, true);
            this.jdField_M_of_type_ComMaddoxIl2FmMass.computeParasiteMass(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons);
            this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.computeParasiteDrag(this.jdField_CT_of_type_ComMaddoxIl2FmControls, this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons);
            checkAirborneState();
            putScareShpere();
            dangerEMAces();
            if ((this.jdField_turnOffCollisions_of_type_Boolean) && (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround) && (getAltitude() - Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 30.0D))
              this.jdField_turnOffCollisions_of_type_Boolean = false;
          }
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.wrap();
          if (this.Realism.Wind_N_Turbulence)
            World.wind().getVector(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Vwind_of_type_ComMaddoxJGPVector3d);
          else
            this.jdField_Vwind_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_Vair_of_type_ComMaddoxJGPVector3d.sub(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwind_of_type_ComMaddoxJGPVector3d);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.jdField_Vair_of_type_ComMaddoxJGPVector3d, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
          this.jdField_Density_of_type_Float = Atmosphere.density((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
          this.jdField_AOA_of_type_Float = FMMath.RAD2DEG(-(float)Math.atan2(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double));
          this.jdField_AOS_of_type_Float = FMMath.RAD2DEG((float)Math.atan2(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double, this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double));
          this.indSpeed = (getSpeed() * (float)Math.sqrt(this.jdField_Density_of_type_Float / 1.225F));
          this.jdField_Mach_of_type_Float = (this.jdField_V_of_type_Float / Atmosphere.sonicSpeed((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double));
          if (this.jdField_Mach_of_type_Float > 0.8F)
            this.jdField_Mach_of_type_Float = 0.8F;
          this.jdField_Kq_of_type_Float = (1.0F / (float)Math.sqrt(1.0F - this.jdField_Mach_of_type_Float * this.jdField_Mach_of_type_Float));
          this.q_ = (this.jdField_Density_of_type_Float * this.jdField_V2_of_type_Float * 0.5F);
          double d1 = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - this.jdField_Gears_of_type_ComMaddoxIl2FmGear.screenHQ;
          if (d1 < 0.0D)
            d1 = 0.0D;
          float f7 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron() * 14.0F;
          f7 = this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_V * (float)Math.sin(FMMath.DEG2RAD(this.jdField_AOS_of_type_Float)) + this.jdField_SensRoll_of_type_Float * this.ailerInfluence * (1.0F - 0.1F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap()) * f7;
          double d2 = 0.0D;
          double d3 = 0.0D;
          if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getType() < 2)
          {
            d2 = this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].addVflow;
            if (this.Realism.Torque_N_Gyro_Effects)
              d3 = 0.5D * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].addVside;
          }
          this.Vn.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GCENTER, 0.85D * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END, -0.5D);
          this.Vn.cross(this.jdField_W_of_type_ComMaddoxJGPVector3d, this.Vn);
          this.Vn.add(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
          float f8 = f7 - FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double, this.Vn.jdField_x_of_type_Double));
          this.Vn.jdField_x_of_type_Double += 0.07000000000000001D * d2;
          double d4 = this.Vn.lengthSquared();
          d4 *= 0.5F * this.jdField_Density_of_type_Float;
          f4 = f7 - FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double + 0.07000000000000001D * d3 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign(), this.Vn.jdField_x_of_type_Double));
          float f9 = 0.015F * f7;
          if (f9 < 0.0F)
            f9 *= 0.18F;
          this.Cwl.jdField_x_of_type_Double = (-d4 * (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cx(f4) + f9 + this.jdField_GearCX_of_type_Float * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() + this.jdField_radiatorCX_of_type_Float * (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getRadiatorPos() + this.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor()) + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragAirbrakeCx * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAirBrake()));
          this.Cwl.jdField_z_of_type_Double = (d4 * this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cy(f4) * this.jdField_Kq_of_type_Float);
          if (this.jdField_fmsfxCurrentType_of_type_Int != 0)
          {
            if (this.jdField_fmsfxCurrentType_of_type_Int == 1)
              this.Cwl.jdField_z_of_type_Double *= Aircraft.cvt(this.jdField_fmsfxPrevValue_of_type_Float, 0.003F, 0.8F, 1.0F, 0.0F);
            if (this.jdField_fmsfxCurrentType_of_type_Int == 2)
            {
              this.Cwl.jdField_z_of_type_Double = 0.0D;
              if (Time.current() >= this.jdField_fmsfxTimeDisable_of_type_Long)
                doRequestFMSFX(0, 0);
            }
          }
          this.Vn.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GCENTER, -this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END, -0.5D);
          this.Vn.cross(this.jdField_W_of_type_ComMaddoxJGPVector3d, this.Vn);
          this.Vn.add(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
          float f10 = -f7 - FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double, this.Vn.jdField_x_of_type_Double));
          this.Vn.jdField_x_of_type_Double += 0.07000000000000001D * d2;
          d4 = this.Vn.lengthSquared();
          d4 *= 0.5F * this.jdField_Density_of_type_Float;
          float f11 = -f7 - FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double - 0.07000000000000001D * d3 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign(), this.Vn.jdField_x_of_type_Double));
          f9 = -0.015F * f7;
          if (f9 < 0.0F)
            f9 *= 0.18F;
          this.Cwr.jdField_x_of_type_Double = (-d4 * (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cx(f11) + f9 + this.jdField_GearCX_of_type_Float * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() + this.jdField_radiatorCX_of_type_Float * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getRadiatorPos() + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragAirbrakeCx * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getAirBrake()));
          this.Cwr.jdField_z_of_type_Double = (d4 * this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cy(f11) * this.jdField_Kq_of_type_Float);
          if (this.jdField_fmsfxCurrentType_of_type_Int != 0)
          {
            if (this.jdField_fmsfxCurrentType_of_type_Int == 1)
              this.Cwr.jdField_z_of_type_Double *= Aircraft.cvt(this.jdField_fmsfxPrevValue_of_type_Float, 0.003F, 0.8F, 1.0F, 0.0F);
            if (this.jdField_fmsfxCurrentType_of_type_Int == 3)
            {
              this.Cwr.jdField_z_of_type_Double = 0.0D;
              if (Time.current() >= this.jdField_fmsfxTimeDisable_of_type_Long)
                doRequestFMSFX(0, 0);
            }
          }
          this.Cwl.jdField_y_of_type_Double = (-d4 * this.jdField_Fusel_of_type_ComMaddoxIl2FmPolares.new_Cy(this.jdField_AOS_of_type_Float));
          this.Cwl.jdField_x_of_type_Double -= d4 * this.jdField_Fusel_of_type_ComMaddoxIl2FmPolares.new_Cx(this.jdField_AOS_of_type_Float);
          this.Cwr.jdField_y_of_type_Double = (-d4 * this.jdField_Fusel_of_type_ComMaddoxIl2FmPolares.new_Cy(this.jdField_AOS_of_type_Float));
          this.Cwr.jdField_x_of_type_Double -= d4 * this.jdField_Fusel_of_type_ComMaddoxIl2FmPolares.new_Cx(this.jdField_AOS_of_type_Float);
          float f12 = this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.get_AOA_CRYT();
          double d5 = 1.0D;
          double d6 = 0.5D + 0.4D * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPowerOutput();
          double d7 = 1.2D + 0.4D * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPowerOutput();
          if (this.spinCoeff < d6)
            this.spinCoeff = d6;
          if (this.spinCoeff > d7)
            this.spinCoeff = d7;
          f4 = f8;
          f11 = f10;
          if ((!this.Realism.Stalls_N_Spins) || (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck()))
          {
            double d8;
            if (f4 > f11)
            {
              if (this.Cwl.jdField_z_of_type_Double < this.Cwr.jdField_z_of_type_Double)
              {
                d8 = this.Cwl.jdField_z_of_type_Double;
                this.Cwl.jdField_z_of_type_Double = this.Cwr.jdField_z_of_type_Double;
                this.Cwr.jdField_z_of_type_Double = d8;
              }
            }
            else if (this.Cwl.jdField_z_of_type_Double > this.Cwr.jdField_z_of_type_Double)
            {
              d8 = this.Cwl.jdField_z_of_type_Double;
              this.Cwl.jdField_z_of_type_Double = this.Cwr.jdField_z_of_type_Double;
              this.Cwr.jdField_z_of_type_Double = d8;
            }
          }
          else if ((f4 > f12) || (f11 > f12))
          {
            this.spinCoeff += 0.2D * paramFloat;
            if ((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareRudders > 0.0D) && (Math.abs(this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl) > 0.5D) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D))
              this.spinCoeff -= 0.3D * paramFloat;
            float f13;
            if (f4 > f11)
              f13 = f4;
            else
              f13 = f11;
            this.jdField_turbCoeff_of_type_Float = (0.8F * (f13 - f12));
            if (this.jdField_turbCoeff_of_type_Float < 1.0F)
              this.jdField_turbCoeff_of_type_Float = 1.0F;
            if (this.jdField_turbCoeff_of_type_Float > 15.0F)
              this.jdField_turbCoeff_of_type_Float = 15.0F;
            d5 = 1.0D - 0.2D * (f13 - f12);
            if (d5 < 0.2D)
              d5 = 0.2D;
            d5 /= this.jdField_turbCoeff_of_type_Float;
            double d10 = d4 * this.jdField_turbCoeff_of_type_Float * this.spinCoeff;
            float f14 = getAltitude() - (float)Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
            if (f14 < 10.0F)
              d10 *= 0.1F * f14;
            if (f4 > f11)
            {
              this.Cwr.jdField_x_of_type_Double += 0.01999999955296516D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCxloss;
              this.Cwl.jdField_x_of_type_Double -= 0.25D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCxloss;
              this.Cwr.jdField_z_of_type_Double += 0.01999999955296516D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCyloss;
              this.Cwl.jdField_z_of_type_Double -= 0.1000000014901161D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCyloss;
            }
            else {
              this.Cwl.jdField_x_of_type_Double += 0.01999999955296516D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCxloss;
              this.Cwr.jdField_x_of_type_Double -= 0.25D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCxloss;
              this.Cwl.jdField_z_of_type_Double += 0.01999999955296516D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCyloss;
              this.Cwr.jdField_z_of_type_Double -= 0.1000000014901161D * d10 * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.spinCyloss;
            }
            this.rudderInfluence = (1.0F + 0.035F * this.jdField_turbCoeff_of_type_Float);
          }
          else {
            this.jdField_turbCoeff_of_type_Float = 1.0F;
            d5 = 1.0D;
            this.spinCoeff -= 0.2D * paramFloat;
            this.ailerInfluence = 1.0F;
            this.rudderInfluence = 1.0F;
          }
          if (isTick(15, 0))
            if (Math.abs(f4 - f11) > 5.0F)
              ForceFeedback.fxSetSpringZero((f11 - f4) * 0.04F, 0.0F);
            else
              ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
          double d12;
          if (d1 < 0.4D * this.jdField_Length_of_type_Float)
          {
            d9 = 1.0D - d1 / (0.4D * this.jdField_Length_of_type_Float);
            d11 = 1.0D + 0.2D * d9;
            d12 = 1.0D + 0.2D * d9;
            this.Cwl.jdField_z_of_type_Double *= d11;
            this.Cwl.jdField_x_of_type_Double *= d12;
            this.Cwr.jdField_z_of_type_Double *= d11;
            this.Cwr.jdField_x_of_type_Double *= d12;
          }
          f7 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator() * (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator() > 0.0F ? 28.0F : 20.0F);
          this.Vn.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.VER_STAB, 0.0D, 0.0D);
          this.Vn.cross(this.jdField_W_of_type_ComMaddoxJGPVector3d, this.Vn);
          this.Vn.add(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
          double d9 = Math.sqrt(this.Vn.jdField_y_of_type_Double * this.Vn.jdField_y_of_type_Double + this.Vn.jdField_z_of_type_Double * this.Vn.jdField_z_of_type_Double);
          d2 = 0.0D;
          d3 = 0.0D;
          if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getType() < 2)
          {
            d11 = 1.0D + 0.04D * this.jdField_Arms_of_type_ComMaddoxIl2FmArm.RUDDER;
            d11 = 1.0D / (d11 * d11);
            d12 = this.Vn.jdField_x_of_type_Double + d11 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].addVflow;
            if (d12 < 0.2D)
              d12 = 0.2D;
            double d14 = 1.0D - 1.5D * d9 / d12;
            if (d14 < 0.0D)
              d14 = 0.0D;
            double d16 = d14 * d11 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].addVflow;
            this.Vn.jdField_x_of_type_Double += d16;
            double d18 = Math.min(0.0011D * this.Vn.jdField_x_of_type_Double * this.Vn.jdField_x_of_type_Double, 1.0D);
            if (this.Vn.jdField_x_of_type_Double < 0.0D)
              d18 = 0.0D;
            if (this.Realism.Torque_N_Gyro_Effects)
              d3 = d14 * d18 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].addVside;
          }
          double d11 = this.jdField_Density_of_type_Float * this.Vn.lengthSquared() * 0.5D;
          if ((this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() == 1) && (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getType() < 2))
          {
            f4 = -FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double - 0.36D * d3 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign(), this.Vn.jdField_x_of_type_Double)) - 2.0F - 0.002F * this.jdField_V_of_type_Float - this.jdField_SensPitch_of_type_Float * f7;
            f11 = -FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double + 0.36D * d3 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign(), this.Vn.jdField_x_of_type_Double)) - 2.0F - 0.002F * this.jdField_V_of_type_Float - this.jdField_SensPitch_of_type_Float * f7;
          }
          else {
            f4 = f11 = -FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_z_of_type_Double, this.Vn.jdField_x_of_type_Double)) - 2.0F - 0.002F * this.jdField_V_of_type_Float - this.jdField_SensPitch_of_type_Float * f7;
          }
          this.Chl.jdField_x_of_type_Double = (-d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cx(f4));
          this.Chl.jdField_z_of_type_Double = (d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cy(f4));
          this.Chr.jdField_x_of_type_Double = (-d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cx(f11));
          this.Chr.jdField_z_of_type_Double = (d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cy(f11));
          this.Chl.jdField_y_of_type_Double = (this.Chr.jdField_y_of_type_Double = 0.0D);
          f7 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareRudders < 0.05F ? 0.0F : 28.0F);
          float f15;
          if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getType() < 2)
            f15 = -FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_y_of_type_Double - 0.5D * d3 * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign(), this.Vn.jdField_x_of_type_Double)) + this.jdField_SensYaw_of_type_Float * this.rudderInfluence * f7;
          else
            f15 = -FMMath.RAD2DEG((float)Math.atan2(this.Vn.jdField_y_of_type_Double, this.Vn.jdField_x_of_type_Double)) + this.jdField_SensYaw_of_type_Float * this.rudderInfluence * f7;
          this.Cv.jdField_x_of_type_Double = (-d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cx(f15));
          this.Cv.jdField_y_of_type_Double = (d11 * this.jdField_Tail_of_type_ComMaddoxIl2FmPolares.new_Cy(f15));
          this.Cv.jdField_z_of_type_Double = 0.0D;
          if (!this.Realism.Stalls_N_Spins)
            this.Cv.jdField_y_of_type_Double += this.Cv.jdField_y_of_type_Double;
          this.Vn.set(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
          d4 = this.jdField_Density_of_type_Float * this.Vn.lengthSquared() * 0.5D;
          this.Fwl.scale(this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut, this.Cwl);
          this.Fwr.scale(this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut, this.Cwr);
          this.Fwl.jdField_x_of_type_Double -= d4 * (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragParasiteCx + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragProducedCx) * 0.5D;
          this.Fwr.jdField_x_of_type_Double -= d4 * (this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragParasiteCx + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.dragProducedCx) * 0.5D;
          this.Fhl.scale((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftStab + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareElevators) * 0.5F, this.Chl);
          this.Fhr.scale((this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftStab + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareElevators) * 0.5F, this.Chr);
          this.Fv.scale(0.2F + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftKeel * 1.5F + this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareRudders, this.Cv);
          this.jdField_AF_of_type_ComMaddoxJGPVector3d.set(this.Fwl);
          this.jdField_AF_of_type_ComMaddoxJGPVector3d.add(this.Fwr);
          if (FMMath.isNAN(this.jdField_AF_of_type_ComMaddoxJGPVector3d))
          {
            this.jdField_AF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
            flutter();
            if (World.cur().isDebugFM())
              System.out.println("AF isNAN");
          }
          else if (this.jdField_AF_of_type_ComMaddoxJGPVector3d.length() > this.jdField_Gravity_of_type_Float * 50.0F)
          {
            flutter();
            if (World.cur().isDebugFM())
              System.out.println("A > 50.0");
            this.jdField_AF_of_type_ComMaddoxJGPVector3d.normalize();
            this.jdField_AF_of_type_ComMaddoxJGPVector3d.scale(this.jdField_Gravity_of_type_Float * 50.0F);
          }
          else {
            if ((getOverload() > 13.5F) && (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (World.Rnd().nextInt(0, 100) > 98))
              cutWing();
            if ((this.indSpeed > 112.5F) && (World.Rnd().nextInt(0, 100) > 98) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.3F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl == 1.0F))
              if (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F4U))
              {
                if (World.Rnd().nextInt(0, 100) > 76)
                  ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "GearR2_D0", "GearR2_D0");
                if (World.Rnd().nextInt(0, 100) > 76)
                  ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "GearL2_D0", "GearL2_D0");
              }
              else if (this.indSpeed > 180.0F)
              {
                if (World.Rnd().nextInt(0, 100) > 76)
                  ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "GearR2_D0", "GearR2_D0");
                if (World.Rnd().nextInt(0, 100) > 76)
                  ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "GearL2_D0", "GearL2_D0");
              }
            if ((this.indSpeed > 60.5F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWing() > 0.1F))
            {
              if ((World.Rnd().nextInt(0, 100) > 90) && (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).isChunkAnyDamageVisible("WingLMid")))
                ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "WingLMid_D0", "WingLMid_D0");
              if ((World.Rnd().nextInt(0, 100) > 90) && (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).isChunkAnyDamageVisible("WingRMid")))
                ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).msgCollision(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "WingRMid_D0", "WingRMid_D0");
            }
            if ((this.indSpeed > 81.0F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasFlapsControl) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl > 0.21F) && ((this.indSpeed - 81.0F) * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 8.0F))
            {
              if ((World.getPlayerAircraft() == this.jdField_actor_of_type_ComMaddoxIl2EngineActor) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasFlapsControl))
                HUD.log("FailedFlaps");
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasFlapsControl = false;
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
            }
            if ((this.indSpeed > this.jdField_VmaxAllowed_of_type_Float) && (World.Rnd().nextFloat(0.0F, 16.0F) < this.indSpeed - this.jdField_VmaxAllowed_of_type_Float) && (World.Rnd().nextInt(0, 99) < 2))
              flutterDamage();
            if ((((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F_86F)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F_86A)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Mig_15F)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Mig_17)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F9F)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F84G1)) || (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof F84G2)))
            {
              if (this.indSpeed > 610.0F)
              {
                if (World.cur().isDebugFM())
                  System.out.println("*** Sonic overspeed....");
                flutter();
              }

            }
            else if (this.indSpeed > 310.0F)
            {
              if (World.cur().isDebugFM())
                System.out.println("*** Sonic overspeed....");
              flutter();
            }

          }

          this.jdField_AM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          if (Math.abs(this.jdField_AOA_of_type_Float) < 12.0F)
          {
            float f16 = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
            if (f16 > 30.0F) {
              f16 = 30.0F;
            }
            else if (f16 < -30.0F)
              f16 = -30.0F;
            f16 = (float)(f16 * (Math.min(this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double - 50.0D, 50.0D) * 0.003000000026077032D));
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(-f16 * 0.01F * this.jdField_Gravity_of_type_Float, 0.0D, 0.0D);
          }
          if (!getOp(19))
          {
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += 8.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 200.0F * this.jdField_Sq_of_type_ComMaddoxIl2FmSquares.squareWing * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPropDirSign();
          }
          double d13 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() * 3.0D;
          if (d13 > 1.0D)
            d13 = 1.0D;
          double d15 = 0.0111D * Math.abs(this.jdField_AOA_of_type_Float);
          if ((this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritL < this.jdField_AOA_of_type_Float) && (this.jdField_AOA_of_type_Float < this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritH)) {
            d15 = 0.0D;
          }
          else if (this.jdField_AOA_of_type_Float >= this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritH) {
            d15 = Math.min(d15, 0.3D * (this.jdField_AOA_of_type_Float - this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritH));
          }
          else if (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritL <= this.jdField_AOA_of_type_Float)
            d15 = Math.min(d15, 0.3D * (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.AOACritL - this.jdField_AOA_of_type_Float));
          double d17 = this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GCENTER + this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GC_FLAPS_SHIFT * d13 * (1.0D - d15) + this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GC_AOA_SHIFT * d15;
          this.TmpV.set(-d17, this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE * (1.3D + 1.0D * Math.sin(FMMath.DEG2RAD(this.jdField_AOS_of_type_Float))), -this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GCENTER_Z);
          this.TmpV.cross(this.TmpV, this.Fwl);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          this.TmpV.set(-d17, -this.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_MIDDLE * (1.3D - 1.0D * Math.sin(FMMath.DEG2RAD(this.jdField_AOS_of_type_Float))), -this.jdField_Arms_of_type_ComMaddoxIl2FmArm.GCENTER_Z);
          this.TmpV.cross(this.TmpV, this.Fwr);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += this.su26add;
          this.TmpV.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.HOR_STAB, 1.0D, 0.0D);
          this.TmpV.cross(this.TmpV, this.Fhl);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          this.TmpV.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.HOR_STAB, -1.0D, 0.0D);
          this.TmpV.cross(this.TmpV, this.Fhr);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          this.TmpV.set(-this.jdField_Arms_of_type_ComMaddoxIl2FmArm.VER_STAB, 0.0D, 1.0D);
          this.TmpV.cross(this.TmpV, this.Fv);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          double d19 = 1.0D - 1.E-005D * this.indSpeed;
          if (d19 < 0.8D)
            d19 = 0.8D;
          this.jdField_W_of_type_ComMaddoxJGPVector3d.scale(d19);
          if (!this.Realism.Stalls_N_Spins)
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += this.jdField_AF_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * 0.5D * Math.sin(FMMath.DEG2RAD(Math.abs(this.jdField_AOA_of_type_Float)));
          if (this.jdField_W_of_type_ComMaddoxJGPVector3d.lengthSquared() > 25.0D)
            this.jdField_W_of_type_ComMaddoxJGPVector3d.scale(5.0D / this.jdField_W_of_type_ComMaddoxJGPVector3d.length());
          if ((!this.Realism.Stalls_N_Spins) && (this.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double > 20.0D))
            this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += this.jdField_AOS_of_type_Float * paramFloat;
          this.jdField_AF_of_type_ComMaddoxJGPVector3d.add(this.jdField_producedAF_of_type_ComMaddoxJGPVector3d);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.jdField_producedAM_of_type_ComMaddoxJGPVector3d);
          this.jdField_producedAF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_AF_of_type_ComMaddoxJGPVector3d.add(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.producedF);
          this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.producedM);
          if (World.cur().diffCur.Torque_N_Gyro_Effects)
          {
            this.jdField_GM_of_type_ComMaddoxJGPVector3d.set(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getGyro());
            this.jdField_GM_of_type_ComMaddoxJGPVector3d.scale(d5);
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.add(this.jdField_GM_of_type_ComMaddoxJGPVector3d);
          }
          this.jdField_GF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_GM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          if (Time.tickCounter() % 2 != 0)
            this.jdField_Gears_of_type_ComMaddoxIl2FmGear.roughness = this.jdField_Gears_of_type_ComMaddoxIl2FmGear.plateFriction(this);
          this.jdField_Gears_of_type_ComMaddoxIl2FmGear.ground(this, true);
          int k = 5;
          if ((this.jdField_GF_of_type_ComMaddoxJGPVector3d.lengthSquared() == 0.0D) && (this.jdField_GM_of_type_ComMaddoxJGPVector3d.lengthSquared() == 0.0D))
            k = 1;
          this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
          this.jdField_ACmeter_of_type_ComMaddoxJGPVector3d.set(this.jdField_SummF_of_type_ComMaddoxJGPVector3d);
          this.jdField_ACmeter_of_type_ComMaddoxJGPVector3d.scale(1.0F / this.jdField_Gravity_of_type_Float);
          this.TmpV.set(0.0D, 0.0D, -this.jdField_Gravity_of_type_Float);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpV);
          this.jdField_GF_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
          this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
          this.jdField_SummM_of_type_ComMaddoxJGPVector3d.add(this.jdField_AM_of_type_ComMaddoxJGPVector3d, this.jdField_GM_of_type_ComMaddoxJGPVector3d);
          double d20 = 1.0D / this.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
          this.LocalAccel.scale(d20, this.jdField_SummF_of_type_ComMaddoxJGPVector3d);
          if (Math.abs(getRollAcceleration()) > 50000.5F)
          {
            ForceFeedback.fxPunch(this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double > 0.0D ? 0.9F : -0.9F, 0.0F, 1.0F);
            if (World.cur().isDebugFM())
              System.out.println("Punched (Axial = " + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + ")");
          }
          if (Math.abs(getOverload() - this.lastAcc) > 0.5F)
          {
            ForceFeedback.fxPunch(World.Rnd().nextFloat(-0.5F, 0.5F), -0.9F, getSpeed() * 0.05F);
            if (World.cur().isDebugFM())
              System.out.println("Punched (Lat = " + Math.abs(getOverload() - this.lastAcc) + ")");
          }
          this.lastAcc = getOverload();
          if (FMMath.isNAN(this.jdField_AM_of_type_ComMaddoxJGPVector3d))
          {
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
            flutter();
            if (World.cur().isDebugFM())
              System.out.println("AM isNAN");
          }
          else if (this.jdField_AM_of_type_ComMaddoxJGPVector3d.length() > this.jdField_Gravity_of_type_Float * 150.0F)
          {
            flutter();
            if (World.cur().isDebugFM())
              System.out.println("SummM > 150g");
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.normalize();
            this.jdField_AM_of_type_ComMaddoxJGPVector3d.scale(this.jdField_Gravity_of_type_Float * 150.0F);
          }
          this.jdField_dryFriction_of_type_Float = (float)(this.jdField_dryFriction_of_type_Float - 0.01D);
          if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gearsChanged)
            this.jdField_dryFriction_of_type_Float = 1.0F;
          if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfPoiOnGr > 0)
            this.jdField_dryFriction_of_type_Float += 0.02F;
          if (this.jdField_dryFriction_of_type_Float < 1.0F)
            this.jdField_dryFriction_of_type_Float = 1.0F;
          if (this.jdField_dryFriction_of_type_Float > 32.0F)
            this.jdField_dryFriction_of_type_Float = 32.0F;
          float f17 = 4.0F * (0.25F - this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPowerOutput());
          if (f17 < 0.0F)
            f17 = 0.0F;
          f17 *= f17;
          f17 *= this.jdField_dryFriction_of_type_Float;
          float f18 = f17 * this.jdField_M_of_type_ComMaddoxIl2FmMass.mass * this.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
          if ((!this.brakeShoe) && (((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfPoiOnGr == 0) && (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.nOfGearsOnGr < 3)) || (f17 == 0.0F) || (this.jdField_SummM_of_type_ComMaddoxJGPVector3d.lengthSquared() > 2.0F * f18) || (this.jdField_SummF_of_type_ComMaddoxJGPVector3d.lengthSquared() > 80.0F * f18) || (this.jdField_W_of_type_ComMaddoxJGPVector3d.lengthSquared() > 0.00014F * f17) || (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.lengthSquared() > 0.09F * f17)))
          {
            double d21 = 1.0D / k;
            for (int m = 0; m < k; m++)
            {
              this.jdField_SummF_of_type_ComMaddoxJGPVector3d.add(this.jdField_AF_of_type_ComMaddoxJGPVector3d, this.jdField_GF_of_type_ComMaddoxJGPVector3d);
              this.jdField_SummM_of_type_ComMaddoxJGPVector3d.add(this.jdField_AM_of_type_ComMaddoxJGPVector3d, this.jdField_GM_of_type_ComMaddoxJGPVector3d);
              this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
              this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
              this.jdField_AW_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = (((this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double - this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double) * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double + this.jdField_SummM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) / this.jdField_J_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double);
              this.TmpV.scale(d21 * paramFloat, this.jdField_AW_of_type_ComMaddoxJGPVector3d);
              this.jdField_W_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
              this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.jdField_W_of_type_ComMaddoxJGPVector3d, this.Vn);
              this.TmpV.scale(d21 * paramFloat, this.jdField_W_of_type_ComMaddoxJGPVector3d);
              this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment((float)(-FMMath.RAD2DEG(this.TmpV.jdField_z_of_type_Double)), (float)(-FMMath.RAD2DEG(this.TmpV.jdField_y_of_type_Double)), (float)FMMath.RAD2DEG(this.TmpV.jdField_x_of_type_Double));
              this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vn, this.jdField_W_of_type_ComMaddoxJGPVector3d);
              this.TmpV.scale(d20, this.jdField_SummF_of_type_ComMaddoxJGPVector3d);
              this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.TmpV);
              this.Accel.set(this.TmpV);
              this.TmpV.scale(d21 * paramFloat);
              this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
              this.TmpV.scale(d21 * paramFloat, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
              this.TmpP.set(this.TmpV);
              this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.add(this.TmpP);
              this.jdField_GF_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
              this.jdField_GM_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
              if (m >= k - 1)
                continue;
              this.jdField_Gears_of_type_ComMaddoxIl2FmGear.ground(this, true);
              this.TmpV.set(0.0D, 0.0D, -this.jdField_Gravity_of_type_Float);
              this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.TmpV);
              this.jdField_GF_of_type_ComMaddoxJGPVector3d.add(this.TmpV);
            }

            for (int n = 0; n < 3; n++)
            {
              this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[n] = ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[n] + (float)Math.toDegrees(Math.atan(this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[n] * paramFloat / 0.375D))) % 360.0F);
              this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[n] *= 0.949999988079071D;
            }

            this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearL1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[0], 0.0F);
            this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearR1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[1], 0.0F);
            this.jdField_HM_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("GearC1_D0", 0.0F, -this.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[2], 0.0F);
          }
          float f19;
          if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (isTick(128, 97)) && (Actor.isAlive(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) && (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround))
          {
            f19 = (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
            if (f19 > 3000.0F) {
              Voice.speakDeviateBig((Aircraft)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            }
            else if (f19 > 1700.0F)
              Voice.speakDeviateSmall((Aircraft)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          }
          this.shakeLevel = 0.0F;
          if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())
          {
            this.shakeLevel = (float)(this.shakeLevel + 30.0D * this.jdField_Gears_of_type_ComMaddoxIl2FmGear.roughness * this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() / this.jdField_M_of_type_ComMaddoxIl2FmMass.mass);
          }
          else {
            if (this.indSpeed > 10.0F)
            {
              f19 = (float)Math.sin(Math.toRadians(Math.abs(this.jdField_AOA_of_type_Float)));
              if (f19 > 0.02F)
              {
                f19 *= f19;
                this.shakeLevel += 0.07F * (f19 - 0.0004F) * (this.indSpeed - 10.0F);
                if ((isTick(30, 0)) && (this.shakeLevel > 0.6F))
                  HUD.log(stallStringID, "Stall");
              }
            }
            if (this.indSpeed > 35.0F)
            {
              if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasGearControl) && ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear) || (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear)) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.0F))
                this.shakeLevel += 0.004F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() * (this.indSpeed - 35.0F);
              if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.0F)
                this.shakeLevel += 0.004F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() * (this.indSpeed - 35.0F);
            }
          }
          if (this.indSpeed > this.jdField_VmaxAllowed_of_type_Float * 0.8F)
            this.shakeLevel = (0.01F * (this.indSpeed - this.jdField_VmaxAllowed_of_type_Float * 0.8F));
          if (World.cur().diffCur.Head_Shake)
          {
            this.shakeLevel += this.producedShakeLevel;
            this.producedShakeLevel *= 0.9F;
          }
          if (this.shakeLevel > 1.0F)
            this.shakeLevel = 1.0F;
          ForceFeedback.fxShake(this.shakeLevel);
          if (World.cur().diffCur.Blackouts_N_Redouts)
            calcOverLoad(paramFloat, true);
        }
      }
    }
  }

  private void calcOverLoad(float paramFloat, boolean paramBoolean)
  {
    if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    if ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) || (!paramBoolean))
    {
      this.plAccel.set(0.0D, 0.0D, 0.0D);
    }
    else {
      this.plAccel.set(getAccel());
      this.plAccel.scale(0.1019999980926514D);
    }
    this.plAccel.jdField_z_of_type_Double += 0.5D;
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.plAccel);
    float f = -0.5F + (float)this.plAccel.jdField_z_of_type_Double;
    this.deep = 0.0F;
    if (f < -0.6F)
      this.deep = (f + 0.6F);
    if (f > 2.2F)
      this.deep = (f - 2.2F);
    if (this.knockDnTime > 0.0F)
      this.knockDnTime -= paramFloat;
    if (this.knockUpTime > 0.0F)
      this.knockUpTime -= paramFloat;
    if (this.indiffDnTime < 4.0F)
      this.indiffDnTime += paramFloat;
    if (this.indiffUpTime < 4.0F)
      this.indiffUpTime += 0.3F * paramFloat;
    if (this.deep > 0.0F)
    {
      if (this.indiffDnTime > 0.0F)
        this.indiffDnTime -= 0.8F * this.deep * paramFloat;
      if ((this.deep > 2.3F) && (this.knockDnTime < 18.4F))
        this.knockDnTime += 0.75F * this.deep * paramFloat;
      if (this.indiffDnTime > 0.1F)
      {
        this.currDeep = 0.0F;
      }
      else {
        this.currDeep = (this.deep * 0.08F * 3.5F);
        if (this.currDeep > 0.8F)
          this.currDeep = 0.8F;
      }
    }
    else if (this.deep < 0.0F)
    {
      this.deep = (-this.deep);
      if (this.deep < 0.84F)
        this.deep = 0.84F;
      if (this.indiffUpTime > 0.0F)
        this.indiffUpTime -= 1.2F * this.deep * paramFloat;
      if ((this.deep > 2.3F) && (this.knockUpTime < 16.1F))
        this.knockUpTime += this.deep * paramFloat;
      if (this.indiffUpTime > 0.1F)
        this.currDeep = 0.0F;
      else
        this.currDeep = (this.deep * 0.42F * 0.88F);
      this.currDeep = (-this.currDeep);
    }
    else {
      this.currDeep = 0.0F;
    }
    if (this.knockUpTime > 10.81F)
      this.currDeep = -0.88F;
    if (this.knockDnTime > 14.03F)
      this.currDeep = 3.5F;
    if (this.currDeep > 3.5F)
      this.currDeep = 3.5F;
    if (this.currDeep < -0.88F)
      this.currDeep = -0.88F;
    if (this.saveDeep > 0.8F)
    {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity = (1.0F - (this.saveDeep - 0.8F));
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity < 0.0F)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity = 0.0F;
    }
    else if (this.saveDeep < -0.4F)
    {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity = (1.0F + (this.saveDeep + 0.4F));
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity < 0.0F)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity = 0.0F;
    }
    else {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity = 1.0F;
    }
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.Sensitivity *= this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.getPilotHealth(0);
    if (this.saveDeep < this.currDeep)
    {
      this.saveDeep += 0.3F * paramFloat;
      if (this.saveDeep > this.currDeep)
        this.saveDeep = this.currDeep;
    }
    else {
      this.saveDeep -= 0.2F * paramFloat;
      if (this.saveDeep < this.currDeep)
        this.saveDeep = this.currDeep;
    }
  }

  public void gunMomentum(Vector3d paramVector3d, boolean paramBoolean)
  {
    this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double += paramVector3d.jdField_x_of_type_Double;
    this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double += paramVector3d.jdField_y_of_type_Double;
    this.jdField_producedAM_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += paramVector3d.jdField_z_of_type_Double;
    float f = (float)paramVector3d.length() * 3.5E-005F;
    if ((paramBoolean) && (f > 0.5F))
      f *= 0.05F;
    if (this.producedShakeLevel < f)
      this.producedShakeLevel = f;
  }
}