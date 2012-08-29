package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitBI_6 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictKg1 = 0.0F;
  private float pictKg2 = 0.0F;
  private float pictT1 = 0.0F;
  private float pictT2 = 0.0F;

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(P1);
    V.sub(P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-V.y, V.x));
  }

  public CockpitBI_6()
  {
    super("3DO/Cockpit/BI-6/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "ONE", "TWO", "THREE", "FOUR" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    resetYPRmodifier();
    ypr[1] = (-80.080002F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    xyz[1] = (ypr[1] < -33.0F ? -0.0065F : 0.0F);
    this.mesh.chunkSetLocate("Throttle", xyz, ypr);

    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("Lever_Gear", -17.0F, 0.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("Lever_Gear", 15.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("Lever_Gear", 0.0F, 0.0F, 0.0F);
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("Lever_Flaps", 15.0F, 0.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("Lever_Flaps", -20.0F, 0.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("Lever_Flaps", 0.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1200.0F, 0.0F, 360.0F), 0.0F);

    this.w.set(this.fm.getW());

    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.47124F, 0.47124F, 40.0F, -40.0F), 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", cvt(getBall(8.0D), -8.0F, 8.0F, 22.5F, -22.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zGas1a", 0.0F, cvt(this.fm.M.fuel / 0.72F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkVisible("Z_Red8", (this.fm.CT.getGear() > 0.05F) && (this.fm.CT.getGear() < 0.95F));
    this.mesh.chunkVisible("Z_Red5", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Red7", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Red4", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Red6", this.fm.CT.getGear() == 1.0F);

    this.mesh.chunkVisible("Z_Red15", this.fm.EI.engines[1].getPowerOutput() > 0.5F);
    this.mesh.chunkVisible("Z_Red16", this.fm.EI.engines[2].getPowerOutput() > 0.5F);
    this.mesh.chunkSetAngles("Switch1_1", 0.0F, 40.0F, 0.0F);
    this.mesh.chunkSetAngles("Switch1_2", 0.0F, 40.0F, 0.0F);
    this.mesh.chunkSetAngles("Switch2_1", 0.0F, 40.0F, 0.0F);
    this.mesh.chunkSetAngles("Switch2_2", 0.0F, 40.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.EI.engines[1].getControlThrottle(), 0.8F, 1.0F, 0.0F, -0.09F);
    this.mesh.chunkSetLocate("Throttle2", xyz, ypr);
    xyz[0] = cvt(this.fm.EI.engines[2].getControlThrottle(), 0.8F, 1.0F, 0.0F, -0.09F);
    this.mesh.chunkSetLocate("Throttle3", xyz, ypr);
    this.mesh.chunkSetAngles("zPVRDFuel", 0.0F, cvt(this.fm.M.nitro, 0.0F, 432.0F, 6.5F, 282.0F), 0.0F);

    float f = 0.0F;
    if (this.fm.EI.engines[1].getControlThrottle() > 0.8F) {
      f = 20.0F * this.fm.EI.engines[1].getReadyness();
    }
    this.pictKg1 = (0.93F * this.pictKg1 + 0.07F * f);
    this.mesh.chunkSetAngles("zPVRD1_1", 0.0F, cvt(this.pictKg1, 0.0F, 25.0F, 0.0F, 275.0F), 0.0F);
    f = 0.0F;
    if (this.fm.EI.engines[2].getControlThrottle() > 0.8F) {
      f = 20.0F * this.fm.EI.engines[2].getReadyness();
    }
    this.pictKg2 = (0.93F * this.pictKg2 + 0.07F * f);
    this.mesh.chunkSetAngles("zPVRD1_2", 0.0F, cvt(this.pictKg1, 0.0F, 25.0F, 0.0F, 275.0F), 0.0F);

    this.mesh.chunkSetAngles("zPVRD2_1", 0.0F, cvt(this.pictT1, 0.0F, 1000.0F, 0.0F, 120.0F), 0.0F);
    this.mesh.chunkSetAngles("zPVRD2_2", 0.0F, cvt(this.pictT2, 0.0F, 1000.0F, 0.0F, 120.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x40) != 0) || ((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0) || ((this.fm.AS.astateCockpitState & 0x2) != 0))
    {
      this.mesh.materialReplace("ONE", "ONE_D1");
      this.mesh.materialReplace("ONE_night", "ONE_D1_night");
      this.mesh.materialReplace("Dash", "Dash_D1");
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zSpeed1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0) || ((this.fm.AS.astateCockpitState & 0x80) != 0))
    {
      this.mesh.materialReplace("THREE", "THREE_D1");
      this.mesh.materialReplace("THREE_night", "THREE_D1_night");
      this.mesh.chunkVisible("zSlide1a", false);
    }
    retoggleLight();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      setNightMats(true);
      this.mesh.chunkVisible("Z_Red11", true);
      this.mesh.chunkVisible("Z_Red14", true);
    } else {
      setNightMats(false);
      this.mesh.chunkVisible("Z_Red11", false);
      this.mesh.chunkVisible("Z_Red14", false);
    }
  }

  private void retoggleLight()
  {
    if (this.cockpitLightControl) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitBI_6.this.fm != null) {
        CockpitBI_6.access$102(CockpitBI_6.this, CockpitBI_6.this.setOld); CockpitBI_6.access$202(CockpitBI_6.this, CockpitBI_6.this.setNew); CockpitBI_6.access$302(CockpitBI_6.this, CockpitBI_6.this.setTmp);

        CockpitBI_6.this.setNew.throttle = ((10.0F * CockpitBI_6.this.setOld.throttle + CockpitBI_6.this.fm.CT.PowerControl) / 11.0F);
        CockpitBI_6.this.setNew.altimeter = CockpitBI_6.this.fm.getAltitude();

        if (CockpitBI_6.this.fm.AS.astateSootStates[1] > 3)
          CockpitBI_6.access$402(CockpitBI_6.this, 0.993F * CockpitBI_6.this.pictT1 + 0.007F * World.Rnd().nextFloat(600.0F, 1000.0F));
        else {
          CockpitBI_6.access$402(CockpitBI_6.this, 0.9992F * CockpitBI_6.this.pictT1 + 0.0008F * (Atmosphere.temperature((float)CockpitBI_6.this.fm.Loc.z) - 200.0F));
        }

        if (CockpitBI_6.this.fm.AS.astateSootStates[2] > 3)
          CockpitBI_6.access$502(CockpitBI_6.this, 0.993F * CockpitBI_6.this.pictT2 + 0.007F * World.Rnd().nextFloat(600.0F, 1000.0F));
        else {
          CockpitBI_6.access$502(CockpitBI_6.this, 0.9992F * CockpitBI_6.this.pictT2 + 0.0008F * (Atmosphere.temperature((float)CockpitBI_6.this.fm.Loc.z) - 200.0F));
        }
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    private final CockpitBI_6 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitBI_6.1 arg2) { this();
    }
  }
}