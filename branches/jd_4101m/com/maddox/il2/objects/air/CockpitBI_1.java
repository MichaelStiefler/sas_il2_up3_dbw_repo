package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitBI_1 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(P1);
    V.sub(P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-V.y, V.x));
  }

  public CockpitBI_1()
  {
    super("3DO/Cockpit/BI-1/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "ONE", "TWO", "THREE" };
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
      if (CockpitBI_1.this.fm != null) {
        CockpitBI_1.access$102(CockpitBI_1.this, CockpitBI_1.this.setOld); CockpitBI_1.access$202(CockpitBI_1.this, CockpitBI_1.this.setNew); CockpitBI_1.access$302(CockpitBI_1.this, CockpitBI_1.this.setTmp);

        CockpitBI_1.this.setNew.throttle = ((10.0F * CockpitBI_1.this.setOld.throttle + CockpitBI_1.this.fm.CT.PowerControl) / 11.0F);
        CockpitBI_1.this.setNew.altimeter = CockpitBI_1.this.fm.getAltitude();
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    private final CockpitBI_1 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitBI_1.1 arg2) { this();
    }
  }
}