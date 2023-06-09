package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
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

public class CockpitHURRII extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private boolean bFAF;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 1.0F, 3.0F, 7.5F, 34.5F, 46.0F, 63.0F, 76.0F, 94.0F, 112.5F, 131.0F, 150.0F, 168.5F, 187.0F, 203.0F, 222.0F, 242.5F, 258.5F, 277.0F, 297.0F, 315.5F, 340.0F, 360.0F, 376.5F, 392.0F, 407.0F, 423.5F, 442.0F, 459.0F, 476.0F, 492.5F, 513.0F, 534.5F, 552.0F, 569.5F };

  private static final float[] speedometerScaleFAF = { 0.0F, 0.0F, 2.0F, 6.0F, 21.0F, 40.0F, 56.0F, 72.5F, 89.5F, 114.0F, 135.5F, 157.0F, 182.5F, 205.0F, 227.5F, 246.5F, 265.5F, 286.0F, 306.0F, 326.0F, 345.5F, 363.0F, 385.0F, 401.0F, 414.5F, 436.5F, 457.0F, 479.0F, 496.5F, 515.0F, 539.0F, 559.5F, 576.5F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F, 125.0F, 161.0F, 202.5F, 253.0F, 315.5F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 22.0F, 58.0F, 103.5F, 152.5F, 193.5F, 245.0F, 281.5F, 311.5F };

  private static final float[] variometerScale = { -158.0F, -110.0F, -63.5F, -29.0F, 0.0F, 29.0F, 63.5F, 110.0F, 158.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitHURRII()
  {
    super("3DO/Cockpit/HurricaneMkII/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "BORT2", "BORT4", "COMPASS", "prib_five_fin", "prib_five", "prib_four", "prib_one_fin", "prib_one", "prib_six", "prib_three", "prib_two", "pricel" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkVisible("XLampEngHeat", (this.fm.EI.engines[0].tOilOut > 105.5F ? 1 : 0) | (this.fm.EI.engines[0].tWaterOut > 135.5F ? 1 : 0));
    this.mesh.chunkVisible("XLampGearUpL", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearUpR", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));

    resetYPRmodifier();
    this.mesh.chunkSetAngles("RUSBase", 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F);
    this.mesh.chunkSetAngles("RUS", -35.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkVisible("RUS_GUN", this.fm.CT.WeaponControl[0] == 0);
    this.mesh.chunkSetAngles("RUS_TORM", 0.0F, 0.0F, -40.0F * this.fm.CT.getBrake());
    xyz[2] = (0.01625F * this.fm.CT.getAileron());
    this.mesh.chunkSetLocate("RUS_TAND_L", xyz, ypr);
    xyz[2] = (-xyz[2]);
    this.mesh.chunkSetLocate("RUS_TAND_R", xyz, ypr);

    this.mesh.chunkSetAngles("RUD", 0.0F, -81.809998F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);

    resetYPRmodifier();
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F)) {
      xyz[0] = 0.05F;
      xyz[2] = 0.0F;
    } else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F)) {
      xyz[0] = -0.05F;
      xyz[2] = 0.0F;
    } else if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F) {
        xyz[0] = -0.05F;
        xyz[2] = 0.0345F;
      } else {
        xyz[0] = 0.05F;
        xyz[2] = 0.0345F;
      }
    }
    this.mesh.chunkSetLocate("SHAS_RUCH_T", xyz, ypr);

    this.mesh.chunkSetAngles("PROP_CONTR", (this.fm.CT.getStepControl() >= 0.0F ? this.fm.CT.getStepControl() : 1.0F - interp(this.setNew.throttle, this.setOld.throttle, paramFloat)) * -60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("PEDALY", 9.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("COMPASS_M", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F));
    this.mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F));
    this.mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -108.0F));

    this.mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.5173668F, 2.72369F, -70.0F, 250.0F));

    this.mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 307.70001F, 0.0F, 60.0F));

    this.mesh.chunkSetAngles("STRELK_FUEL_LB", 0.0F, -cvt(this.fm.M.fuel > 1.0F ? 10.0F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 10.0F, 0.0F, 10.0F), 0.0F);

    this.mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 1000.0F, 5000.0F, 2.0F, 10.0F), rpmScale));

    this.mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 100.0F, 0.0F, 270.0F));
    this.mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale));
    resetYPRmodifier();
    xyz[2] = (0.05865F * this.fm.EI.engines[0].getControlRadiator());
    this.mesh.chunkSetLocate("zRadFlap", xyz, ypr);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F));
    this.mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F));

    this.mesh.chunkVisible("STRELK_V_SHORT", false);
    if (this.bFAF)
    {
      this.mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 143.05283F, 0.0F, 32.0F), speedometerScaleFAF));
    }
    else {
      this.mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 180.05551F, 0.0F, 35.0F), speedometerScale));
    }

    this.mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale));

    this.mesh.chunkSetAngles("STRELKA_GOR_FAF", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.032F, -0.032F);
    this.mesh.chunkSetLocate("STRELKA_GOR_RAF", xyz, ypr);
    this.mesh.chunkSetLocate("STRELKA_GOS_FAF", xyz, ypr);

    this.mesh.chunkSetAngles("STRELKA_HOUR", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("STRELKA_MINUTE", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("STRELKA_SECUND", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.materialReplace("prib_four", "prib_four_damage");
      this.mesh.materialReplace("prib_four_night", "prib_four_damage_night");
      this.mesh.materialReplace("prib_three", "prib_three_damage");
      this.mesh.materialReplace("prib_three_night", "prib_three_damage_night");
      this.mesh.chunkVisible("STRELK_TEMP_OIL", false);
      this.mesh.chunkVisible("STRELK_TEMP_RAD", false);
      this.mesh.chunkVisible("STRELKA_BOOST", false);
      this.mesh.chunkVisible("STRELKA_FUEL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
      this.mesh.chunkVisible("HullDamage2_RAF", true);
      this.mesh.chunkVisible("HullDamage2_FAF", true);
      this.mesh.materialReplace("prib_three", "prib_three_damage");
      this.mesh.materialReplace("prib_three_night", "prib_three_damage_night");
      this.mesh.chunkVisible("STRELK_TEMP_OIL", false);
      this.mesh.chunkVisible("STRELK_TEMP_RAD", false);
      this.mesh.chunkVisible("STRELKA_BOOST", false);
      this.mesh.chunkVisible("STRELKA_FUEL", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("HullDamage6", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.materialReplace("prib_two", "prib_two_damage");
      this.mesh.materialReplace("prib_two_night", "prib_two_damage_night");
      this.mesh.chunkVisible("STREL_ALT_LONG", false);
      this.mesh.chunkVisible("STREL_ALT_SHORT", false);
      this.mesh.chunkVisible("STRELKA_RPM", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("HullDamage5", true);
      this.mesh.chunkVisible("HullDamage6", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);
      if (this.bFAF)
        this.mesh.chunkVisible("HullDamage2_FAF", true);
      else {
        this.mesh.chunkVisible("HullDamage2_RAF", true);
      }
      this.mesh.materialReplace("prib_one", "prib_one_damage");
      this.mesh.materialReplace("prib_one_fin", "prib_one_fin_damage");
      this.mesh.materialReplace("prib_one_night", "prib_one_damage_night");
      this.mesh.materialReplace("prib_one_fin_night", "prib_one_fin_damage_night");
      this.mesh.chunkVisible("STRELK_V_LONG", false);
      this.mesh.chunkVisible("STRELK_V_SHORT", false);
      this.mesh.chunkVisible("STRELKA_GOR_FAF", false);
      this.mesh.chunkVisible("STRELKA_GOR_RAF", false);
      this.mesh.chunkVisible("STRELKA_VY", false);
      this.mesh.chunkVisible("STREL_TURN_DOWN", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    if (aircraft().getRegiment().country().equals("fi")) {
      this.bFAF = true;
      this.mesh.chunkVisible("PRIBORY_RAF", false);
      this.mesh.chunkVisible("PRIBORY_FAF", true);
      this.mesh.chunkVisible("STRELKA_GOR_RAF", false);
      this.mesh.chunkVisible("STRELKA_GOR_FAF", true);
      this.mesh.chunkVisible("STRELKA_GOS_FAF", true);
    } else {
      this.bFAF = false;
      this.mesh.chunkVisible("PRIBORY_RAF", true);
      this.mesh.chunkVisible("PRIBORY_FAF", false);
      this.mesh.chunkVisible("STRELKA_GOR_RAF", true);
      this.mesh.chunkVisible("STRELKA_GOR_FAF", false);
      this.mesh.chunkVisible("STRELKA_GOS_FAF", false);
    }

    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
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
      if (CockpitHURRII.this.bNeedSetUp) {
        CockpitHURRII.this.reflectPlaneMats();
        CockpitHURRII.access$102(CockpitHURRII.this, false);
      }
      if (CockpitHURRII.this.fm != null) {
        CockpitHURRII.access$202(CockpitHURRII.this, CockpitHURRII.this.setOld); CockpitHURRII.access$302(CockpitHURRII.this, CockpitHURRII.this.setNew); CockpitHURRII.access$402(CockpitHURRII.this, CockpitHURRII.this.setTmp);

        CockpitHURRII.this.setNew.throttle = ((10.0F * CockpitHURRII.this.setOld.throttle + CockpitHURRII.this.fm.CT.PowerControl) / 11.0F);
        CockpitHURRII.this.setNew.altimeter = CockpitHURRII.this.fm.getAltitude();
        if (Math.abs(CockpitHURRII.this.fm.Or.getKren()) < 30.0F) {
          CockpitHURRII.this.setNew.azimuth = ((35.0F * CockpitHURRII.this.setOld.azimuth + -CockpitHURRII.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitHURRII.this.setOld.azimuth > 270.0F) && (CockpitHURRII.this.setNew.azimuth < 90.0F)) CockpitHURRII.this.setOld.azimuth -= 360.0F;
        if ((CockpitHURRII.this.setOld.azimuth < 90.0F) && (CockpitHURRII.this.setNew.azimuth > 270.0F)) CockpitHURRII.this.setOld.azimuth += 360.0F;
        CockpitHURRII.this.setNew.waypointAzimuth = ((10.0F * CockpitHURRII.this.setOld.waypointAzimuth + (CockpitHURRII.this.waypointAzimuth() - CockpitHURRII.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        CockpitHURRII.this.setNew.vspeed = ((199.0F * CockpitHURRII.this.setOld.vspeed + CockpitHURRII.this.fm.getVertSpeed()) / 200.0F);
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
    private final CockpitHURRII this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitHURRII.1 arg2) { this();
    }
  }
}