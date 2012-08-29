package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitKI_100 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictGear = 0.0F;
  private float pictFlaps = 0.0F;
  private float pictADI = 0.0F;
  private int ordnanceCounter = 0;

  private static final float[] vsiNeedleScale = { -200.0F, -160.0F, -125.0F, -90.0F, 90.0F, 125.0F, 160.0F, 200.0F };

  private static final float[] speedometerScale = { 0.0F, 10.0F, 35.0F, 70.0F, 105.0F, 145.0F, 190.0F, 230.0F, 275.0F, 315.0F, 360.0F, 397.5F, 435.0F, 470.0F, 505.0F, 537.5F, 570.0F, 600.0F, 630.0F, 655.0F, 680.0F };

  private static final float[] revolutionsScale = { 0.0F, 20.0F, 75.0F, 125.0F, 180.0F, 220.0F, 285.0F, 335.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) {
      f += 180.0F;
    }
    while (f > 180.0F) {
      f -= 180.0F;
    }
    return f;
  }

  public CockpitKI_100()
  {
    super("3DO/Cockpit/Ki-100/hier.him", "bf109");

    this.mesh.chunkVisible("FlarePilonEn_L", false);
    this.mesh.chunkVisible("FlarePilonEn_R", false);
    if (this.fm.CT.Weapons[3] != null) {
      this.ordnanceCounter = this.fm.CT.Weapons[3].length;
      if (this.fm.CT.Weapons[9] != null)
        this.ordnanceCounter = 3;
    } else if (this.fm.CT.Weapons[9] != null) {
      this.ordnanceCounter = (3 + this.fm.CT.Weapons[9].length);
    }

    this.cockpitNightMats = new String[] { "GP_I", "GP_II", "GP_III", "GP_IV", "GP_V", "GP_VI", "GP_II_D", "GP_III_D", "GP_IV_D", "GP_V_D", "GP_VI_D", "GP_VII", "GP_VIII" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.16F, 0.98F, 0.0F, -0.65F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);
    this.mesh.chunkSetAngles("Z_canopylock_R", 0.0F, cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.12F, 0.0F, -105.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_canopylock_L", 0.0F, cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.12F, 0.0F, -105.0F), 0.0F);

    this.mesh.chunkSetAngles("GSDimmArm", -55.0F * this.setNew.dimPosition, 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = (this.setNew.dimPosition * 0.05245F);
    this.mesh.chunkSetLocate("GSDimmBase", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Gear1", 0.0F, -57.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl), 0.0F);
    this.mesh.chunkSetAngles("Z_stickmount", 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 12.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 12.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_elevshtck", 0.0F, this.pictElev * 12.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal_L", 0.0F, -18.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal_R", 0.0F, 18.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_WlBreak_L", 0.0F, -30.0F * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("Z_WlBreak_R", 0.0F, -30.0F * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("Z_ElevTrimHandl", 0.0F, -45.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_FlapLever", 0.0F, -57.0F * (this.pictFlaps = 0.8F * this.pictFlaps + 0.2F * this.fm.CT.FlapsControl), 0.0F);
    this.mesh.chunkSetAngles("Z_OilCoolerLvr", 0.0F, -57.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);
    this.mesh.chunkSetAngles("Z_TQHandle", 0.0F, 45.450001F * this.setNew.throttle, 0.0F);
    this.mesh.chunkSetAngles("Z_PropPitchLvr", 0.0F, 60.0F * this.setNew.pitch, 0.0F);
    this.mesh.chunkSetAngles("Z_ADI", 0.0F, -70.0F * (this.pictADI = 0.8F * this.pictADI + 0.2F * (this.fm.EI.engines[0].getControlAfterburner() ? 1.0F : 0.0F)), 0.0F);
    this.mesh.chunkSetAngles("Z_MixLvr", 0.0F, 22.083F * this.setNew.mix, 0.0F);
    this.mesh.chunkSetAngles("Z_MagSwitchSel", 0.0F, cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, -74.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_NeedElevTrim", 0.0F, -90.0F * this.fm.CT.trimElevator, 0.0F);
    this.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 18.0F, -18.0F));
    this.mesh.chunkSetAngles("NeedCompass_B", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedCompass_C", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_Km", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_M", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedBank", cvt(getBall(8.0D), -8.0F, 8.0F, 10.0F, -10.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("NeedTurn", cvt(this.w.z, -0.23562F, 0.23562F, -25.0F, 25.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedClimb", floatindex(cvt(this.setNew.vspeed, -30.0F, 30.0F, 0.5F, 6.5F), vsiNeedleScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedFuel", cvt(this.fm.M.fuel, 0.0F, 525.0F, 0.0F, 328.0F), 0.0F, 0.0F);
    float f = cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.6F, 0.0F, 1.0F);
    this.mesh.chunkSetAngles("NeedFuelPress", f * 308.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedMin", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedManPress", cvt(this.setNew.manifold, 0.2000681F, 2.066576F, -144.75F, 193.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedOilPress", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedRPM", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 7.0F), revolutionsScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1000.0F, 0.0F, 20.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedExhTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 324.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedVMPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedOilTemp", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 130.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedCylTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 360.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, -0.07975F);
    this.mesh.chunkSetLocate("NeedFlap1", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.07975F);
    this.mesh.chunkSetLocate("NeedFlap2", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, 0.07975F);
    this.mesh.chunkSetLocate("NeedRadiator", xyz, ypr);

    this.mesh.chunkVisible("FlareGearDn_A", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearDn_B", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearUp_A", (this.fm.CT.getGear() < 0.01F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearUp_B", (this.fm.CT.getGear() < 0.01F) && (this.fm.Gears.rgear));

    switch (this.ordnanceCounter) {
    case 1:
      if (this.fm.CT.Weapons[3][0].haveBullets())
        this.mesh.chunkVisible("FlarePilonEn_R", true);
      else {
        this.mesh.chunkVisible("FlarePilonEn_R", false);
      }
      break;
    case 2:
      if (this.fm.CT.Weapons[3][0].haveBullets()) {
        this.mesh.chunkVisible("FlarePilonEn_R", true);
        this.mesh.chunkVisible("FlarePilonEn_L", true);
      } else {
        this.mesh.chunkVisible("FlarePilonEn_R", false);
        this.mesh.chunkVisible("FlarePilonEn_L", false);
      }
      break;
    case 3:
      if (this.fm.CT.Weapons[3][0].haveBullets())
        this.mesh.chunkVisible("FlarePilonEn_R", true);
      else {
        this.mesh.chunkVisible("FlarePilonEn_R", false);
      }
      if (this.fm.CT.Weapons[9][0].haveBullets())
        this.mesh.chunkVisible("FlarePilonEn_L", true);
      else {
        this.mesh.chunkVisible("FlarePilonEn_L", false);
      }
      break;
    case 4:
      if (this.fm.CT.Weapons[9][0].haveBullets())
        this.mesh.chunkVisible("FlarePilonEn_R", true);
      else {
        this.mesh.chunkVisible("FlarePilonEn_R", false);
      }
      break;
    case 5:
      if (this.fm.CT.Weapons[9][0].haveBullets()) {
        this.mesh.chunkVisible("FlarePilonEn_R", true);
        this.mesh.chunkVisible("FlarePilonEn_L", true);
      } else {
        this.mesh.chunkVisible("FlarePilonEn_R", false);
        this.mesh.chunkVisible("FlarePilonEn_L", false);
      }
      break;
    }

    this.mesh.chunkVisible("FlareFuelPress", f < 0.25F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x20) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0)) {
      this.mesh.chunkVisible("OilSplats", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_wndshld_holes", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_rrgls_hole", true);
      this.mesh.chunkVisible("Z_canopy_holes", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Gages", false);
      this.mesh.chunkVisible("Gages_D1", true);
      this.mesh.chunkVisible("NeedBank", false);
      this.mesh.chunkVisible("NeedSpeed", false);
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("NeedAlt_M", false);
      this.mesh.chunkVisible("NeedManPress", false);
      this.mesh.chunkVisible("NeedExhTemp", false);
      this.mesh.chunkVisible("NeedRPM", false);
      this.mesh.chunkVisible("NeedOilPress", false);
      this.mesh.chunkVisible("NeedFuel", false);
    }
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight() {
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
      if (CockpitKI_100.this.fm != null) {
        CockpitKI_100.access$102(CockpitKI_100.this, CockpitKI_100.this.setOld); CockpitKI_100.access$202(CockpitKI_100.this, CockpitKI_100.this.setNew); CockpitKI_100.access$302(CockpitKI_100.this, CockpitKI_100.this.setTmp);

        if (CockpitKI_100.this.cockpitDimControl) {
          if (CockpitKI_100.this.setNew.dimPosition < 1.0F) CockpitKI_100.this.setNew.dimPosition += 0.05F;
        }
        else if (CockpitKI_100.this.setNew.dimPosition > 0.0F) CockpitKI_100.this.setNew.dimPosition -= 0.05F;

        CockpitKI_100.this.setNew.throttle = (0.9F * CockpitKI_100.this.setOld.throttle + 0.1F * CockpitKI_100.this.fm.CT.PowerControl);
        CockpitKI_100.this.setNew.manifold = (0.8F * CockpitKI_100.this.setOld.manifold + 0.2F * CockpitKI_100.this.fm.EI.engines[0].getManifoldPressure());
        CockpitKI_100.this.setNew.pitch = (0.9F * CockpitKI_100.this.setOld.pitch + 0.1F * CockpitKI_100.this.fm.EI.engines[0].getControlProp());
        CockpitKI_100.this.setNew.mix = (0.9F * CockpitKI_100.this.setOld.mix + 0.1F * CockpitKI_100.this.fm.EI.engines[0].getControlMix());
        CockpitKI_100.this.setNew.altimeter = CockpitKI_100.this.fm.getAltitude();

        CockpitKI_100.this.setNew.azimuth.setDeg(CockpitKI_100.this.setOld.azimuth.getDeg(1.0F), CockpitKI_100.this.fm.Or.azimut());

        CockpitKI_100.this.setNew.vspeed = ((199.0F * CockpitKI_100.this.setOld.vspeed + CockpitKI_100.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float pitch;
    float mix;
    float altimeter;
    float manifold;
    AnglesFork azimuth;
    float vspeed;
    float dimPosition;
    private final CockpitKI_100 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();

      this.dimPosition = 1.0F;
    }

    Variables(CockpitKI_100.1 arg2)
    {
      this();
    }
  }
}