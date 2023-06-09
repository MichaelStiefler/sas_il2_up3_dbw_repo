package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitIL_2_1942 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  boolean roControl;
  long roControlTime = 0L;
  boolean bombControl;
  long bombControlTime = 0L;
  long previous = 0L;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  public CockpitIL_2_1942()
  {
    super("3DO/Cockpit/Il-2-Late/hier.him", "il2");

    HookNamed localHookNamed = new HookNamed(this.mesh, "_LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(245.0F, 238.0F, 126.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("_LAMPHOOK1", this.light1);

    localHookNamed = new HookNamed(this.mesh, "_LAMPHOOK2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(245.0F, 238.0F, 126.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("_LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "prib_one", "prib_two", "prib_three", "prib_four", "prib_six", "prib_six_na", "shkala", "prib_one_dd", "prib_two_dd", "prib_three_dd" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("richag", 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 15.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl));

    this.mesh.chunkSetAngles("os_V", 0.0F, 0.0F, 10.0F * this.fm.CT.getElevator());
    this.mesh.chunkSetAngles("tyga_V", 0.0F, -12.0F * this.fm.CT.getElevator(), 0.0F);
    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("ruchkaGaza", 0.0F, -60.0F + 120.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("ruchkaShassi", 0.0F, 85.0F * interp(this.setNew.undercarriage, this.setOld.undercarriage, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("ruchkaShitkov", 0.0F, 85.0F - 85.0F * interp(this.setNew.flaps, this.setOld.flaps, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("r_one", 0.0F, -20.0F * (this.fm.CT.saveWeaponControl[0] != 0 ? 1 : 0), 0.0F);
    this.mesh.chunkSetAngles("r_two", 0.0F, -20.0F * (this.fm.CT.saveWeaponControl[1] != 0 ? 1 : 0), 0.0F);
    this.mesh.chunkSetAngles("r_RO", 0.0F, this.setNew.roPos, 0.0F);
    this.mesh.chunkSetAngles("r_bomb", 0.0F, this.setNew.bombPos, 0.0F);
    this.mesh.chunkSetAngles("r_turn", 0.0F, 20.0F * this.fm.CT.BrakeControl, 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -30.0F, 30.0F, -30.0F, 30.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.setOld.xyz[2] = cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 0.03F, -0.03F);
    this.mesh.chunkSetLocate("zHorizon1a", this.setOld.xyz, this.setOld.ypr);
    this.mesh.chunkSetAngles("zHorizon1b", 0.0F, -this.fm.Or.getKren(), 0.0F);

    this.mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);

    this.mesh.chunkSetAngles("zGas1a", 0.0F, cvt(this.fm.M.fuel / 0.72F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x2) == 0) && ((this.fm.AS.astateCockpitState & 0x4) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F);

      this.mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);

      this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F);

      this.mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 8.0F, 0.0F, -180.0F), 0.0F);
    }

    this.w.set(this.fm.getW());

    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zTWater1a", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, -0.0F, -86.0F), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x1) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 120.0F, -0.0F, -86.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Green2", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Red1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.CT.getGear() == 0.0F);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.005F, 0.6F);
      this.light2.light.setEmit(0.005F, 0.6F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("zManifold1a", false);
      this.mesh.chunkVisible("zHorizon1a", false);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) != 0) || ((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d2", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zRPM1b", false);
      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zSlide1a", false);
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
      if (CockpitIL_2_1942.this.fm != null) {
        CockpitIL_2_1942.access$102(CockpitIL_2_1942.this, CockpitIL_2_1942.this.setOld); CockpitIL_2_1942.access$202(CockpitIL_2_1942.this, CockpitIL_2_1942.this.setNew); CockpitIL_2_1942.access$302(CockpitIL_2_1942.this, CockpitIL_2_1942.this.setTmp);

        CockpitIL_2_1942.this.setNew.throttle = ((10.0F * CockpitIL_2_1942.this.setOld.throttle + CockpitIL_2_1942.this.fm.CT.PowerControl) / 11.0F);
        if (CockpitIL_2_1942.this.fm.Gears.isHydroOperable()) CockpitIL_2_1942.this.setNew.undercarriage = ((10.0F * CockpitIL_2_1942.this.setOld.undercarriage + CockpitIL_2_1942.this.fm.CT.GearControl) / 11.0F);
        CockpitIL_2_1942.this.setNew.flaps = ((10.0F * CockpitIL_2_1942.this.setOld.flaps + (CockpitIL_2_1942.this.fm.CT.FlapsControl > 0.0F ? 0.0F : 1.0F)) / 11.0F);

        CockpitIL_2_1942.this.setNew.altimeter = CockpitIL_2_1942.this.fm.getAltitude();
        if (Math.abs(CockpitIL_2_1942.this.fm.Or.getKren()) < 30.0F) {
          CockpitIL_2_1942.this.setNew.azimuth = ((35.0F * CockpitIL_2_1942.this.setOld.azimuth + CockpitIL_2_1942.this.fm.Or.azimut()) / 36.0F);
        }
        if ((CockpitIL_2_1942.this.setOld.azimuth > 270.0F) && (CockpitIL_2_1942.this.setNew.azimuth < 90.0F)) CockpitIL_2_1942.this.setOld.azimuth -= 360.0F;
        if ((CockpitIL_2_1942.this.setOld.azimuth < 90.0F) && (CockpitIL_2_1942.this.setNew.azimuth > 270.0F)) CockpitIL_2_1942.this.setOld.azimuth += 360.0F;
        CockpitIL_2_1942.this.setNew.vspeed = ((199.0F * CockpitIL_2_1942.this.setOld.vspeed + CockpitIL_2_1942.this.fm.getVertSpeed()) / 200.0F);

        if (CockpitIL_2_1942.this.fm.CT.saveWeaponControl[2] != 0) {
          CockpitIL_2_1942.this.roControl = true;
          CockpitIL_2_1942.this.roControlTime = Time.current();
        }
        if (CockpitIL_2_1942.this.roControl) {
          CockpitIL_2_1942.this.setNew.roPos = ((2.0F * CockpitIL_2_1942.this.setOld.roPos - 230.0F) / 3.0F);
          if (CockpitIL_2_1942.this.roControlTime < Time.current() - 2210L) CockpitIL_2_1942.this.roControl = false; 
        }
        else {
          CockpitIL_2_1942.this.setNew.roPos = ((14.0F * CockpitIL_2_1942.this.setOld.roPos - 0.0F) / 15.0F);
        }
        if (CockpitIL_2_1942.this.fm.CT.saveWeaponControl[3] != 0) {
          CockpitIL_2_1942.this.bombControl = true;
          CockpitIL_2_1942.this.bombControlTime = Time.current();
        }
        if (CockpitIL_2_1942.this.bombControl) {
          CockpitIL_2_1942.this.setNew.bombPos = ((2.0F * CockpitIL_2_1942.this.setOld.bombPos - 220.0F) / 3.0F);
          if (CockpitIL_2_1942.this.bombControlTime < Time.current() - 2210L) CockpitIL_2_1942.this.bombControl = false; 
        }
        else {
          CockpitIL_2_1942.this.setNew.bombPos = ((14.0F * CockpitIL_2_1942.this.setOld.bombPos - 0.0F) / 15.0F);
        }
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float undercarriage;
    float flaps;
    float altimeter;
    float azimuth;
    float vspeed;
    float roPos;
    float bombPos;
    float[] xyz;
    float[] ypr;
    private final CockpitIL_2_1942 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.roPos = 0.0F;
      this.bombPos = 0.0F;
      this.xyz = new float[] { 0.0F, 0.0F, 0.0F };
      this.ypr = new float[] { 0.0F, 0.0F, 0.0F };
    }

    Variables(CockpitIL_2_1942.1 arg2)
    {
      this();
    }
  }
}