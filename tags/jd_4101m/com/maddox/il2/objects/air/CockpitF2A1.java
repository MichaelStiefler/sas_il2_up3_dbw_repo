package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitF2A1 extends CockpitPilot
{
  private Gun[] gun = { null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitF2A1()
  {
    super("3DO/Cockpit/F2A-1/hier.him", "bf109");
    this.cockpitNightMats = new String[] { "F2ABoxes", "F2Acables", "F2AWindshields", "F2Agauges1", "F2Agauges3", "F2Agauges", "F2Azegary4" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
    }

    this.mesh.chunkVisible("XLampGear_1", (!this.fm.Gears.lgear) || (!this.fm.Gears.lgear));
    this.mesh.chunkSetAngles("Z_Gear", 32.0F * this.fm.CT.getGear(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap", 48.0F * this.fm.CT.getFlap(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Pedal_L", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal1_L", 0.0F, (this.fm.CT.getRudder() > 0.0F ? 30.0F : 25.0F) * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal2_L", 0.0F, (this.fm.CT.getRudder() > 0.0F ? 25.0F : 20.0F) * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal1_R", 0.0F, (this.fm.CT.getRudder() < 0.0F ? -30.0F : -25.0F) * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal2_R", 0.0F, (this.fm.CT.getRudder() < 0.0F ? -25.0F : -20.0F) * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("Columnbase", 0.0F, -10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F);
    this.mesh.chunkSetAngles("Column", 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F);
    this.mesh.chunkSetAngles("ColumnL", 0.0F, 10.0F * this.pictAiler, 0.0F);
    this.mesh.chunkSetAngles("ColumnR", 0.0F, -10.0F * this.pictAiler, 0.0F);

    this.mesh.chunkSetAngles("Z_Manifold", this.fm.EI.engines[0].getManifoldPressure() < 0.399966F ? cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.0F, 0.399966F, 0.0F, 34.0F) : cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.399966F, 1.599864F, 34.0F, 326.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Alt_Large", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 21600.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt_Small", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 2160.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speed", Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()) < 41.155544F ? cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 41.155544F, 0.0F, 30.0F) : cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 41.155544F, 246.93327F, 30.0F, 340.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass", 90.0F + interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Magn_Compas", 90.0F + interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Comp_Handle", cvt(interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Bank", cvt(getBall(8.0D), -8.0F, 8.0F, 14.0F, -14.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_Turn", cvt(this.w.z, -0.23562F, 0.23562F, 34.0F, -34.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb", cvt(this.setNew.vspeed, -20.0F, 20.0F, -180.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Clock_H", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Clock_Min", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    if ((this.gun[0] != null) && (this.gun[0].haveBullets())) {
      this.mesh.chunkSetAngles("Z_Ammo_W1", 0.36F * this.gun[0].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W2", 3.6F * this.gun[0].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W3", 36.0F * this.gun[0].countBullets(), 0.0F, 0.0F);
    }
    if ((this.gun[1] != null) && (this.gun[1].haveBullets())) {
      this.mesh.chunkSetAngles("Z_Ammo_W4", 0.36F * this.gun[1].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W5", 3.6F * this.gun[1].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W6", 36.0F * this.gun[1].countBullets(), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Temp_Handle", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 400.0F, 0.0F, 70.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp_Eng", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil_Eng", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_Eng", cvt(this.fm.M.fuel > 1.0F ? 10.0F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 20.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 2000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Ign_Switch", 0.0F, this.fm.EI.engines[0].getStage() == 0 ? 0.0F : -60.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_1", cvt(this.fm.M.fuel, 0.0F, 504.0F, 0.0F, 155.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_2", cvt(this.fm.M.fuel, 0.0F, 504.0F, 0.0F, 155.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Rudder_1", 0.0F, -60.0F * this.fm.CT.getTrimRudderControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Elevator_2", 0.0F, -60.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Tail_W_Lock", 0.0F, this.fm.Gears.bTailwheelLocked ? -40.0F : 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Booster_Level", 0.0F, -38.0F * this.fm.EI.engines[0].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle", 0.0F, -77.269997F * this.fm.EI.engines[0].getControlThrottle(), 0.0F);
    this.mesh.chunkSetAngles("Z_Pitch", 0.0F, -68.0F * this.fm.EI.engines[0].getControlProp(), 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps_Lever", 0.0F, -45.0F * this.fm.CT.FlapsControl, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear_Lever", 0.0F, -45.0F * this.fm.CT.GearControl, 0.0F);

    this.mesh.chunkSetAngles("Z_Hor_Handle", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.019F, -0.019F);
    this.mesh.chunkSetLocate("Z_Hor_Handle2", xyz, ypr);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Revi_D1", true);
      this.mesh.chunkVisible("Z_Hullhole_3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Bullethole_1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Manifold", false);
      this.mesh.chunkVisible("Z_Speed", false);
      this.mesh.chunkVisible("Z_Alt_Large", false);
      this.mesh.chunkVisible("Z_Alt_Small", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Z_Bullethole_2", true);
      this.mesh.chunkVisible("Z_Hullhole_1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("Z_Hullhole_2", true);
      this.mesh.chunkVisible("Z_Hullhole_3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("Z_Bullethole_2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("Z_Hullhole_1", true);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitF2A1.this.fm != null) {
        CockpitF2A1.access$102(CockpitF2A1.this, CockpitF2A1.this.setOld);
        CockpitF2A1.access$202(CockpitF2A1.this, CockpitF2A1.this.setNew);
        CockpitF2A1.access$302(CockpitF2A1.this, CockpitF2A1.this.setTmp);
        CockpitF2A1.this.setNew.throttle = ((10.0F * CockpitF2A1.this.setOld.throttle + CockpitF2A1.this.fm.CT.PowerControl) / 11.0F);
        CockpitF2A1.this.setNew.altimeter = CockpitF2A1.this.fm.getAltitude();
        if (Math.abs(CockpitF2A1.this.fm.Or.getKren()) < 30.0F) {
          CockpitF2A1.this.setNew.azimuth = ((35.0F * CockpitF2A1.this.setOld.azimuth + -CockpitF2A1.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitF2A1.this.setOld.azimuth > 270.0F) && (CockpitF2A1.this.setNew.azimuth < 90.0F)) CockpitF2A1.this.setOld.azimuth -= 360.0F;
        if ((CockpitF2A1.this.setOld.azimuth < 90.0F) && (CockpitF2A1.this.setNew.azimuth > 270.0F)) CockpitF2A1.this.setOld.azimuth += 360.0F;

        if (CockpitF2A1.this.useRealisticNavigationInstruments())
        {
          CockpitF2A1.this.setNew.waypointAzimuth = ((10.0F * CockpitF2A1.this.setOld.waypointAzimuth + CockpitF2A1.this.getBeaconDirection()) / 11.0F);
        }
        else
        {
          CockpitF2A1.this.setNew.waypointAzimuth = ((10.0F * CockpitF2A1.this.setOld.waypointAzimuth + (CockpitF2A1.this.waypointAzimuth() - CockpitF2A1.this.setOld.azimuth)) / 11.0F);
        }

        CockpitF2A1.this.setNew.vspeed = ((199.0F * CockpitF2A1.this.setOld.vspeed + CockpitF2A1.this.fm.getVertSpeed()) / 200.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitF2A1 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitF2A1.1 arg2) { this();
    }
  }
}