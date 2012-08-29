package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

public class CockpitJU_87B2 extends CockpitPilot
{
  protected SoundFX buzzerFX;
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, -12.333333F, 18.5F, 37.0F, 62.5F, 90.0F, 110.5F, 134.0F, 158.5F, 186.0F, 212.5F, 238.5F, 265.0F, 289.5F, 315.0F, 339.5F, 346.0F, 346.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 54.0F, 111.0F, 171.5F, 229.5F, 282.5F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 11.5F, 24.5F, 46.5F, 67.0F, 88.0F };

  private static final float[] temperatureScale = { 0.0F, 15.5F, 35.0F, 50.0F, 65.0F, 79.0F, 92.0F, 117.5F, 141.5F, 178.5F, 222.5F, 261.5F, 329.0F, 340.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitJU_87B2()
  {
    super("3DO/Cockpit/Ju-87B-2/hier.him", "bf109");
    this.setNew.dimPosition = 1.0F;

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "87DClocks1", "87DClocks2", "87DClocks3", "87DClocks4", "87DClocks5", "87DPlanks2" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    this.buzzerFX = aircraft().newSound("models.buzzthru", false);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm == null) return;

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAltCtr1", ((JU_87)aircraft()).fDiveRecoveryAlt * 360.0F / 6000.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAltCtr2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 6000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -30.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 40.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zBoost1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFuel1", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 250.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCoolant1", floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 130.0F, 0.0F, 13.0F), temperatureScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOilTemp1", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 130.0F, 0.0F, 13.0F), temperatureScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFuelPrs1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOilPrs1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zTurnBank", cvt(this.setNew.turn, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zBall", cvt(getBall(6.0D), -6.0F, 6.0F, -10.0F, 10.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("zRepeater", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zCompass", 0.0F, this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("zCompassOil2", -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("zCompass", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("zRepeater", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zCompassOil2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("zCompassOil1", this.fm.Or.getTangage(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCompassOil3", this.fm.Or.getKren(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zVSI", cvt(this.setNew.vspeed, -15.0F, 15.0F, -135.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMinute", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zColumn1", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("zPedalL", -this.fm.CT.getRudder() * 10.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zPedalR", this.fm.CT.getRudder() * 10.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zThrottle1", interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 80.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zPitch1", (this.fm.CT.getStepControl() >= 0.0F ? this.fm.CT.getStepControl() : interp(this.setNew.throttle, this.setOld.throttle, paramFloat)) * 45.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFlaps1", 55.0F * this.fm.CT.FlapsControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zPipka1", 60.0F * this.fm.CT.AirBrakeControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zBrake1", 46.5F * this.fm.CT.AirBrakeControl, 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.fm.EI.engines[0].getControlCompressor() > 0) {
      xyz[0] = 0.155F;
      ypr[2] = 22.0F;
    }
    this.mesh.chunkSetLocate("zBoostCrank1", xyz, ypr);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.005F, 0.5F);
      this.light2.light.setEmit(0.005F, 0.5F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x8) != 0))
    {
      this.mesh.chunkVisible("Radio_D0", false);
      this.mesh.chunkVisible("Radio_D1", true);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x10) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("Radio_D0", false);
      this.mesh.chunkVisible("Radio_D1", true);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) != 0) || ((this.fm.AS.astateCockpitState & 0x2) != 0))
    {
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Z_ReviTint", false);
      this.mesh.chunkVisible("Z_ReviTinter", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("PoppedPanel_D0", false);
      this.mesh.chunkVisible("Z_Repeater1", false);
      this.mesh.chunkVisible("Z_Azimuth1", false);
      this.mesh.chunkVisible("Z_Compass1", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("PoppedPanel_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitJU_87B2.this.fm = World.getPlayerFM();
      if (CockpitJU_87B2.this.fm != null)
      {
        CockpitJU_87B2.access$102(CockpitJU_87B2.this, CockpitJU_87B2.this.setOld); CockpitJU_87B2.access$202(CockpitJU_87B2.this, CockpitJU_87B2.this.setNew); CockpitJU_87B2.access$302(CockpitJU_87B2.this, CockpitJU_87B2.this.setTmp);

        CockpitJU_87B2.this.setNew.altimeter = CockpitJU_87B2.this.fm.getAltitude();
        if (CockpitJU_87B2.this.cockpitDimControl) {
          if (CockpitJU_87B2.this.setNew.dimPosition > 0.0F) CockpitJU_87B2.this.setNew.dimPosition = (CockpitJU_87B2.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitJU_87B2.this.setNew.dimPosition < 1.0F) CockpitJU_87B2.this.setNew.dimPosition = (CockpitJU_87B2.this.setOld.dimPosition + 0.05F);

        CockpitJU_87B2.this.setNew.throttle = ((10.0F * CockpitJU_87B2.this.setOld.throttle + CockpitJU_87B2.this.fm.CT.PowerControl) / 11.0F);

        float f = CockpitJU_87B2.this.waypointAzimuth();
        if (CockpitJU_87B2.this.useRealisticNavigationInstruments())
        {
          CockpitJU_87B2.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitJU_87B2.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitJU_87B2.this.setNew.waypointAzimuth.setDeg(CockpitJU_87B2.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitJU_87B2.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitJU_87B2.this.setNew.azimuth.setDeg(CockpitJU_87B2.this.setOld.azimuth.getDeg(1.0F), CockpitJU_87B2.this.fm.Or.azimut());

        CockpitJU_87B2.this.w.set(CockpitJU_87B2.this.fm.getW());
        CockpitJU_87B2.this.fm.Or.transform(CockpitJU_87B2.this.w);
        CockpitJU_87B2.this.setNew.turn = ((12.0F * CockpitJU_87B2.this.setOld.turn + CockpitJU_87B2.this.w.z) / 13.0F);

        CockpitJU_87B2.this.setNew.vspeed = ((499.0F * CockpitJU_87B2.this.setOld.vspeed + CockpitJU_87B2.this.fm.getVertSpeed()) / 500.0F);
        if (CockpitJU_87B2.this.buzzerFX != null) {
          if ((CockpitJU_87B2.this.fm.Loc.z < 750.0D) && (((JU_87)(JU_87)CockpitJU_87B2.this.fm.actor).diveMechStage == 1))
            CockpitJU_87B2.this.buzzerFX.play();
          else if (CockpitJU_87B2.this.buzzerFX.isPlaying()) {
            CockpitJU_87B2.this.buzzerFX.stop();
          }
        }
      }
      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float turn;
    float vspeed;
    private final CockpitJU_87B2 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitJU_87B2.1 arg2)
    {
      this();
    }
  }
}