package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.HUD;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;

public class Engines extends FMMath
{
  float[] Arm;
  float[] Power;
  float Power100;
  float PowerSET;
  float Nominal;
  float Reductor;
  public float W0;
  public float Wx;
  public float propAOA;
  float Mp;
  float Ix;
  float Kh0 = 0.0F;
  float[] kh;
  float HofVmax = 100.0F;
  float[] khAlt;
  public float PropDiam;
  public int PropDir;
  public boolean RadiatorOK = true;
  public boolean JET;
  public boolean bInline = false;
  public boolean bAutonomous = false;
  public boolean bRan = false;

  public byte magneto = 0;

  private byte stage = 0;

  private byte oldStage = 0;
  private long timer = 0L;
  private long given = 4611686018427387903L;
  private static boolean bTFirst;
  private float boostFactor = 1.0F;
  private FlightModel fmdreference = null;

  public float TOilIn = 0.0F;
  public float TOilOut = 0.0F;
  public float TWaterOut = 0.0F;
  private float tWaterCritMin;
  private float tWaterCritMax;
  private float tOilCritMin;
  private float tOilCritMax;
  private float tWaterMaxRPM;
  public float tOilOutMaxRPM;
  private float tOilInMaxRPM;
  private float tChangeSpeed;
  private float timeOverheat;
  private float timeUnderheat;
  private float timeCounter;
  private static int heatStringID = -1;

  public FmSounds isnd = null;
  public static final int PROP_LEFT = 1;
  public static final int PROP_RIGHT = 2;
  public static final int PROP_AUTO = 0;
  private static Vector3f L = new Vector3f(0.0F, 0.0F, 0.0F);
  private static float k;
  private static float ktmp;
  private static float P;
  private static float Pow;

  public void set(Engines paramEngines)
  {
    this.Arm = new float[paramEngines.Arm.length];
    this.Power = new float[paramEngines.Arm.length];
    for (int i = 0; i < paramEngines.Arm.length; i++) {
      this.Arm[i] = paramEngines.Arm[i];
      this.Power[i] = paramEngines.Power[i];
    }
    this.Power100 = paramEngines.Power100;
    this.PowerSET = paramEngines.PowerSET;
    this.Nominal = paramEngines.Nominal;
    this.Reductor = paramEngines.Reductor;
    this.W0 = paramEngines.W0;
    this.Mp = paramEngines.Mp;
    this.Ix = paramEngines.Ix;
    this.PropDiam = paramEngines.PropDiam;
    this.PropDir = paramEngines.PropDir;
    this.Wx = paramEngines.Wx;
    this.Kh0 = paramEngines.Kh0;
    this.kh = paramEngines.kh;
    this.HofVmax = paramEngines.HofVmax;
    this.khAlt = paramEngines.khAlt;
  }

  public void load(SectFile paramSectFile)
  {
    String str1 = "Engine";

    if (paramSectFile.get(str1, "Jet", 0) == 1) this.JET = true;

    this.Power = new float[this.Arm.length];
    this.Power100 = paramSectFile.get(str1, this.JET ? "THRUST" : "POWER", 120.0F);
    if (this.JET) this.Power100 *= 9.81F;
    this.Nominal = paramSectFile.get(str1, "NOMINAL", 0.0F);
    for (int i = 0; i < this.Arm.length; i++) this.Power[i] = 1.0F;
    if (!this.JET) {
      if (paramSectFile.get(str1, "Inline", 0) == 1) this.bInline = true;
      if (paramSectFile.get(str1, "Autonomous", 0) == 1) this.bAutonomous = true;
      this.Reductor = paramSectFile.get(str1, "REDUCTOR", 1.0F);
      this.PropDiam = paramSectFile.get(str1, "PROPELLER_DIAM", 3.0F);
      String str2 = paramSectFile.get(str1, "PROPELLER_DIR");
      if (str2.endsWith("LEFT")) this.PropDir = 1;
      else if (str2.endsWith("RIGHT")) this.PropDir = 2; else {
        this.PropDir = 0;
      }

      this.W0 = (6.283186F * this.Nominal * this.Reductor / 60.0F);
      this.Mp = (this.PropDiam * this.PropDiam * this.PropDiam);
      this.Ix = (this.Mp * this.PropDiam * this.PropDiam);
    }
    this.PowerSET = 0.0F;

    this.boostFactor = paramSectFile.get(str1, "BOOSTFACTOR", 1.0F);
    this.tChangeSpeed = paramSectFile.get(str1, "TESPEED", 0.01F);
    this.tWaterMaxRPM = paramSectFile.get(str1, "TWATERMAXRPM", 95.0F);
    this.tOilInMaxRPM = paramSectFile.get(str1, "TOILINMAXRPM", 70.0F);
    this.tOilOutMaxRPM = paramSectFile.get(str1, "TOILOUTMAXRPM", 107.0F);
    this.timeOverheat = paramSectFile.get(str1, "MAXRPMTIME", 120.0F);
    this.timeUnderheat = paramSectFile.get(str1, "MINRPMTIME", 999.0F);
    this.tWaterCritMax = paramSectFile.get(str1, "TWATERMAX", 115.0F);
    this.tWaterCritMin = paramSectFile.get(str1, "TWATERMIN", 60.0F);
    this.tOilCritMax = paramSectFile.get(str1, "TOILMAX", 132.0F);
    this.tOilCritMin = paramSectFile.get(str1, "TOILMIN", 40.0F);
    this.timeCounter = 0.0F;

    str1 = "Params";

    int j = 1;
    float f = paramSectFile.get(str1, "Vmax", 0.0F) * 0.2777778F;
    int m = 2; for (goto 458; ; m++) {
      if (paramSectFile.get(str1, "VmaxH" + m, -1.0F) == -1.0F) {
        j = m - 1;
        break;
      }
    }

    if (j == 1) {
      this.kh = new float[1];
      this.khAlt = new float[1];
      this.khAlt[0] = paramSectFile.get(str1, "HofVmax", 100.0F);
      this.kh[0] = getKforH(f, paramSectFile.get(str1, "VmaxH", 0.0F) * 0.2777778F, this.khAlt[0]);
    }
    else {
      this.kh = new float[j];
      this.khAlt = new float[j];
      for (int n = 0; n < this.kh.length; n++) {
        this.khAlt[n] = paramSectFile.get(str1, "HofVmax" + (n + 1), 100.0F);
        this.kh[n] = getKforH(f, paramSectFile.get(str1, "VmaxH" + (n + 1), 0.0F) * 0.2777778F, this.khAlt[n]);
      }
    }
  }

  public void setP(float paramFloat)
  {
    this.PowerSET = (this.stage == 6 ? paramFloat : paramFloat);
  }

  public void setPowerReadyness(int paramInt, float paramFloat)
  {
    this.Power[paramInt] = paramFloat;
  }

  public float getPower()
  {
    float f = 0.0F;
    if (this.Power.length < 1) return 0.0F;
    for (int i = 0; i < this.Power.length; i++) f += this.Power[i];
    return (this.stage == 6 ? this.PowerSET : 0.0F) * f / this.Power.length;
  }

  public float getRPM(int paramInt)
  {
    if (this.JET) {
      if (this.stage == 6) {
        f = this.PowerSET;
        if (f < 0.1F) f = 0.1F;
        return f * this.Nominal;
      }
      return 0.0F;
    }
    float f = this.Wx / this.Reductor * 9.55F;
    if ((this.stage == 6) && 
      (f < 200.0F)) f = 200.0F;

    if (this.Power[paramInt] > 0.0F) return f; return 0.0F;
  }

  public float getWx(int paramInt)
  {
    if (this.JET) {
      return getRPM(paramInt);
    }
    float f = this.Wx;
    if ((this.stage == 6) && 
      (f < 20.0F)) f = 20.0F;

    if (this.Power[paramInt] > 0.0F) return f; return 0.0F;
  }

  public void update(float paramFloat, FlightModel paramFlightModel)
  {
    float f3 = Pitot.Indicator((float)paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, paramFlightModel.getSpeedKMH());

    int j = 1;
    float f1 = Atmosphere.temperature((float)paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 273.14999F;

    if (this.stage == 6) {
      f2 = 1.05F * (float)Math.sqrt(Math.sqrt(getPower() > 0.2F ? getPower() + paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateOilStates[0] * 0.33F : 0.2F)) * (float)Math.sqrt(this.Wx / this.W0 > 0.75F ? this.Wx / this.W0 : 0.75D) * this.tOilOutMaxRPM * (j != 0 ? 0.9F : 1.0F) * (1.0F - f3 * 0.0002F) + 22.0F;

      if (getPower() > 1.0F) f2 *= getPower();
      this.TOilOut += (f2 - this.TOilOut) * paramFloat * this.tChangeSpeed;
    } else {
      f2 = this.Wx / this.W0 * this.tOilOutMaxRPM * (j != 0 ? 0.8F : 1.0F) + f1;
      this.TOilOut += (f2 - this.TOilOut) * paramFloat * this.tChangeSpeed * (this.bInline ? 0.42F : 1.07F);
    }

    if (j != 0) f2 = this.TOilOut * (0.75F - f3 * 0.0005F) + f1 * (0.25F + f3 * 0.0005F); else {
      f2 = this.TOilOut * (0.8F - f3 * 0.0005F) + f1 * (0.2F + f3 * 0.0005F);
    }
    this.TOilIn += (f2 - this.TOilIn) * paramFloat * this.tChangeSpeed * 0.5F;

    float f2 = 1.05F * (float)Math.sqrt(getPower()) * (1.0F - f3 * 0.0002F) * this.tWaterMaxRPM + f1;
    this.TWaterOut += (f2 - this.TWaterOut) * paramFloat * this.tChangeSpeed * (this.TWaterOut < 50.0F ? 0.4F : 1.0F) * (1.0F - f3 * 0.0006F);

    if (this.TOilOut < f1) this.TOilOut = f1;
    if (this.TOilIn < f1) this.TOilIn = f1;
    if (this.TWaterOut < f1) this.TWaterOut = f1;

    if ((World.cur().diffCur.Engine_Overheat) && ((this.TWaterOut > this.tWaterCritMax) || (this.TOilOut > this.tOilCritMax)))
    {
      if (heatStringID == -1) heatStringID = HUD.makeIdLog();
      HUD.log(heatStringID, "EngineOverheat");
      this.timeCounter += paramFloat;
      if (this.timeCounter > this.timeOverheat) {
        for (int i = 0; i < this.Power.length; i++) {
          if (this.Power[i] > 0.33F) {
            this.Power[i] -= 0.00666F * paramFloat;
            this.tOilCritMax -= 0.00666F * paramFloat * (this.tOilCritMax - this.tOilOutMaxRPM);
          }
          else {
            this.Power[i] = 0.33F;
            this.stage = 7;
          }
        }

      }

    }
    else if (this.timeCounter > 0.0F) {
      this.timeCounter = 0.0F;
      if (heatStringID == -1) heatStringID = HUD.makeIdLog();
      HUD.log(heatStringID, "EngineRestored");
    }
  }

  public void update(float paramFloat)
  {
    if (this.stage == 6) return;
    bTFirst = false;
    float f = 20.0F;

    long l = Time.current() - this.timer;
    if ((this.stage > 0) && (this.stage < 6) && (l > this.given)) {
      this.stage = (byte)(this.stage + 1);
      if ((this.fmdreference.isPlayers()) && (this.stage == 1)) {
        HUD.log("Starting_Engine");
      }
      this.timer = Time.current();
    }
    if (this.oldStage != this.stage) {
      bTFirst = true;
      this.oldStage = this.stage;
    }
    if ((this.stage > 0) && (this.stage < 6) && (this.fmdreference != null))
      this.fmdreference.CT.PowerControl = 0.2F;
    int i;
    switch (this.stage) {
    case 0:
      if (bTFirst) {
        this.given = 4611686018427387903L;
        this.timer = Time.current();
      }
      setP(0.0F);

      break;
    case 1:
      if (bTFirst) {
        this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));
      }

      this.magneto = 2;
      this.Wx = (0.1047F * (20.0F * (float)l / (float)this.given));
      setP(0.0F);
      break;
    case 2:
      if (bTFirst) {
        this.given = ()(4000.0F * World.Rnd().nextFloat(1.0F, 2.0F));
        if (this.bRan) {
          this.given = ()(100.0F + (this.tOilOutMaxRPM - this.TOilOut) / (this.tOilOutMaxRPM - f) * 7900.0F * World.Rnd().nextFloat(2.0F, 4.2F));
          if (this.given > 9000L) this.given = World.Rnd().nextLong(7800L, 9600L);
          if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (World.Rnd().nextFloat() < 0.5F)) {
            this.stage = 0;
            this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(0);
          }
        }
      }
      this.Wx = (0.1047F * (20.0F + 15.0F * (float)l / (float)this.given));
      setP(0.0F);
      break;
    case 3:
      if (bTFirst)
      {
        this.given = ()(50.0F * World.Rnd().nextFloat(1.0F, 2.0F));
        if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (World.Rnd().nextFloat() < 0.12F) && ((this.tOilOutMaxRPM - this.TOilOut) / (this.tOilOutMaxRPM - f) < 0.75F)) {
          this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(0);
        }
      }
      this.Wx = (0.1047F * (60.0F + 60.0F * (float)l / (float)this.given));
      setP(0.0F);

      if ((this.fmdreference == null) || ((this.tOilOutMaxRPM - this.TOilOut) / (this.tOilOutMaxRPM - f) <= 0.75F)) break;
      for (i = 1; i < 4; i++)
        for (int j = 1; j < 64; j++)
          try {
            Hook localHook = this.fmdreference.jdField_actor_of_type_ComMaddoxIl2EngineActor.findHook("_Engine" + i + "EF_" + (j < 10 ? "0" + j : new StringBuffer().append("").append(j).toString()));
            if (localHook != null) Eff3DActor.New(this.fmdreference.jdField_actor_of_type_ComMaddoxIl2EngineActor, localHook, null, 1.0F, "3DO/Effects/Aircraft/EngineStart" + World.Rnd().nextInt(1, 3) + ".eff", -1.0F);
          }
          catch (Exception localException)
          {
          }
      break;
    case 4:
      if (bTFirst) {
        this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));
        if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (World.Rnd().nextFloat() < 0.05F))
          setPowerReadyness(0, getEngineDamageFactor(0) - 0.1F);
      }
      this.Wx = 12.564F;
      setP(0.0F);
      break;
    case 5:
      if (bTFirst) {
        this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));
        if (this.bRan) {
          if ((this.tOilOutMaxRPM - this.TOilOut) / (this.tOilOutMaxRPM - f) > 0.75F) {
            if (this.bInline) {
              if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (getEngineDamageFactor(0) > 0.75F) && (World.Rnd().nextFloat() < 0.25F))
                setPowerReadyness(0, getEngineDamageFactor(0) - 0.05F);
            }
            else if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (World.Rnd().nextFloat() < 0.1F)) {
              this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this.fmdreference.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
            }
          }
          if ((this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (World.Rnd().nextFloat() < 0.1F))
            this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(0);
        }
        this.bRan = true;
      }
      this.Wx = (0.1047F * (120.0F + 120.0F * (float)l / (float)this.given));
      setP(0.2F);
      break;
    case 6:
      if (!bTFirst) break;
      this.given = -1L;
      this.fmdreference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineRunning(0); break;
    case 7:
      if (bTFirst) {
        this.given = -1L;
      }
      if (this.Power.length > 0) for (i = 0; i < this.Power.length; i++) setPowerReadyness(i, 0.0F);


      this.magneto = 0;
      break;
    default:
      return;
    }
  }

  public void torque(float paramFloat1, Vector3f paramVector3f1, Vector3f paramVector3f2, float paramFloat2, float paramFloat3)
  {
    if (this.JET) { paramVector3f2.set(0.0F, 0.0F, 0.0F); return;
    }
    float f2;
    float f1 = f2 = 0.0F;
    if (this.Wx < 0.0F) this.Wx = 0.0F;
    float f3 = this.Wx;

    for (int i = 0; i < this.Power.length; i++) f2 = Math.max(f2, this.Power[i]);

    for (i = 0; i < this.Power.length; i++) f1 += this.Power[i];

    if (paramFloat2 < 20.0F) paramFloat2 = 0.0F; else paramFloat2 -= 20.0F;

    if (paramFloat3 < -1.0F)
    {
      calcPropAOA(paramFloat2, paramFloat3);

      if ((this.stage == 6) || (this.stage == 7))
        this.Wx = ((this.W0 * (float)Math.sqrt(this.PowerSET * f2) + paramFloat2 * 0.2F) * (1.0F - 0.35F * this.propAOA));
      else if (this.stage == 0) {
        this.Wx = 0.0F;
      }
      this.Wx = (f3 + (this.Wx - f3) * (this.Wx < f3 ? 0.5F : 0.2658F / this.Reductor / this.Reductor) * paramFloat1 * 0.333F);
      if (f2 < 0.01F) {
        this.Wx *= 0.99F;
      }

      paramVector3f2.set(0.0F, 0.0F, 0.0F);
      return;
    }

    calcPropAOA(paramFloat2, paramFloat3);

    if ((this.stage == 6) || (this.stage == 7))
      this.Wx = ((this.W0 * (float)Math.sqrt(this.PowerSET * f2) + paramFloat2 * 0.2F) * (1.0F - 0.35F * this.propAOA));
    else if (this.stage == 0) {
      this.Wx = 0.0F;
    }
    this.Wx = (f3 + (this.Wx - f3) * (this.Wx < f3 ? 0.5F : 0.2658F / this.Reductor / this.Reductor) * paramFloat1);
    if (f2 < 0.01F) {
      this.Wx *= 0.99F;
    }

    if (World.cur().diffCur.Torque_N_Gyro_Effects) {
      L.jdField_x_of_type_Float = (this.Ix * this.Wx * f1 * 0.15F);
      paramVector3f2.cross(paramVector3f1, L);
      float f4 = this.Ix * (this.Wx - f3) / paramFloat1 * 0.8F;
      paramVector3f2.jdField_x_of_type_Float += (f1 > 0.0F ? f4 : -f4);
      paramVector3f2.jdField_x_of_type_Float *= (this.PropDir == 1 ? 1.0F : -1.0F);
    } else {
      paramVector3f2.set(0.0F, 0.0F, 0.0F);
    }
  }

  private void calcPropAOA(float paramFloat1, float paramFloat2)
  {
    float f1 = (float)Math.atan2(paramFloat1, 0.375F * this.PropDiam * (this.Wx + 1.0F));
    float f2;
    if (paramFloat2 < 0.0F) f2 = f1 + 0.2F; else {
      f2 = 0.35F + paramFloat2 * 0.65F;
    }
    if (f2 < 0.35F) f2 = 0.35F; else if (f2 > 1.0F) f2 = 1.0F;
    this.propAOA = (f2 - f1);
  }

  public void propSpeed(float paramFloat)
  {
    float f1 = this.Wx;
    float f2 = 0.0F;
    for (int i = 0; i < this.Power.length; i++) f2 = Math.max(f2, this.Power[i]);
    if (this.stage == 6)
      this.Wx = (this.W0 * (float)Math.sqrt(this.PowerSET * f2));
    if (this.stage == 0) {
      this.Wx = 0.0F;
      this.Wx = (f1 + (this.Wx - f1) * (this.Wx < f1 ? 0.03F : 0.01F));
      if (f2 < 0.01F)
        this.Wx *= 0.99F;
    }
  }

  public float forcePropAOA(float paramFloat1, float paramFloat2)
  {
    this.Wx = this.W0;
    calcPropAOA(paramFloat1, -2.0F);
    this.Wx = 0.0F;
    if (this.propAOA > 0.2F) this.propAOA = (0.2F + (this.propAOA - 0.2F) * 2.0F);
    return force(paramFloat1, paramFloat2);
  }

  private float forceJet(float paramFloat1, float paramFloat2) {
    float f1 = 0.0F;
    for (int i = 0; i < this.Power.length; i++) f1 += this.Power[i];
    f1 *= this.PowerSET * this.Power100;

    float f2 = paramFloat2 / this.khAlt[0];
    if (f2 < 1.0F) f2 = FMMath.interpolate(1.0F, this.kh[0], f2); else
      f2 = this.kh[0] / f2;
    return f1 * f2;
  }

  private float kV(float paramFloat)
  {
    return 1.0F - 0.0032F * paramFloat;
  }

  public float force(float paramFloat1, float paramFloat2)
  {
    if (this.JET) return forceJet(paramFloat1, paramFloat2);

    if (this.propAOA < 0.0F) return 0.0F;

    Pow = 0.0F;

    for (int i = 0; i < this.Power.length; i++) Pow += this.Power[i];
    Pow *= this.PowerSET * this.Power100 * 9.6F;
    if (this.PowerSET > 1.0F) Pow *= this.boostFactor;

    k = 0.0F;
    for (i = 0; i < this.kh.length; i++) {
      ktmp = FMMath.interpolate(this.khAlt[0] / this.khAlt[i], this.kh[i], paramFloat2 / this.khAlt[i]);
      if (paramFloat2 > this.khAlt[i]) ktmp = this.kh[i] * (Atmosphere.density(paramFloat2) / Atmosphere.density(this.khAlt[i]));

      if (ktmp <= k) continue; k = ktmp;
    }

    P = Pow * k * kV(paramFloat1);

    if (this.propAOA <= 0.2F) return P * (this.propAOA * 5.0F);
    return P * (0.2F / this.propAOA);
  }

  void setAltSpeed(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.HofVmax = paramFloat3;

    float f = Atmosphere.density(this.HofVmax) * (paramFloat2 * paramFloat2) / (Atmosphere.density(0.0F) * (paramFloat1 * paramFloat1));

    if (this.JET) this.Kh0 = f; else
      this.Kh0 = (f * kV(paramFloat1) / kV(paramFloat2));
  }

  protected float getKforH(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f = Atmosphere.density(paramFloat3) * (paramFloat2 * paramFloat2) / (Atmosphere.density(0.0F) * (paramFloat1 * paramFloat1));

    if (!this.JET) f = f * kV(paramFloat1) / kV(paramFloat2);

    return f;
  }

  public void doStartEngine(FlightModel paramFlightModel)
  {
    this.fmdreference = paramFlightModel;
    if ((Airport.distToNearestAirport(this.fmdreference.jdField_Loc_of_type_ComMaddoxJGPPoint3d) < 1000.0D) && (this.fmdreference.isStationedOnGround())) {
      this.stage = 1;
      this.bRan = false;
      this.timer = Time.current();
      paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStarts(0);
      return;
    }
    if (this.stage == 0)
      if (this.bAutonomous) {
        this.stage = 1;
        this.timer = Time.current();
        paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStarts(0);
      } else {
        doStopEngine(paramFlightModel);
        this.magneto = 0;
      }
  }

  public void doStopEngine(FlightModel paramFlightModel)
  {
    this.fmdreference = paramFlightModel;
    if (this.stage != 0) {
      this.stage = 0;
      this.magneto = 0;
      this.timer = Time.current();
      paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(0);
    }
  }

  public void doKillEngine(FlightModel paramFlightModel, int paramInt) {
    this.fmdreference = paramFlightModel;
    paramFlightModel.setCapableOfTaxiing(false);
    paramFlightModel.setCapableOfACM(false);
    if (this.stage != 7) {
      setPowerReadyness(paramInt, 0.0F);
      if ((this.PowerSET != 0.0F) && (getPower() == 0.0F)) {
        this.stage = 7;
        if (World.getPlayerAircraft() == paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor) HUD.log("FailedEngine");
        this.magneto = 0;
      }
      this.timer = Time.current();
    }
  }

  public void doKillCompressor(FlightModel paramFlightModel, int paramInt) {
    this.fmdreference = paramFlightModel;
    if ((this.kh.length > 1) && (this.khAlt[0] != 55.0F)) {
      this.kh = new float[] { 1.0F };
      this.khAlt = new float[] { 55.0F };
      if (World.getPlayerAircraft() == paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor) HUD.log("FailedCompressor"); 
    }
  }

  public void toggleMagnetos(FlightModel paramFlightModel)
  {
    this.fmdreference = paramFlightModel;
    if (this.stage == 0) {
      if (paramFlightModel.getAltitude() - Engine.land().HQ(paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.x, paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.y) > 25.0D)
      {
        if (this.bRan) {
          this.magneto = 2;
          doStartEngine(paramFlightModel);
        } else {
          setMagnetos(paramFlightModel, 2);
        }
        return;
      }
      this.magneto = 2;
      doStartEngine(paramFlightModel);
    }
    else {
      doStopEngine(paramFlightModel);
    }
  }

  public void setMagnetos(FlightModel paramFlightModel, byte paramByte) {
    this.fmdreference = paramFlightModel;
    switch (paramByte) {
    case 2:
      this.magneto = 2;
      this.stage = 6;
      this.bRan = true;
      this.timer = Time.current();
      paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineRunning(0);
      break;
    default:
      return;
    }
  }

  public byte getMagnetos() {
    return this.magneto;
  }

  public int getEngineNumber()
  {
    return this.Power.length; } 
  public float getReductor() { return this.Reductor; }

  public float getFanSpeed(int paramInt) {
    return this.Wx * 0.15915F * this.Power[paramInt];
  }
  public float getEngineDamageFactor(int paramInt) {
    return this.Power[paramInt];
  }
  public float getPropellerMoment(int paramInt) {
    return this.PowerSET * this.Power[paramInt];
  }
  public int getStage(int paramInt) {
    return this.stage;
  }
}