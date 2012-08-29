package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
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
import com.maddox.sound.ReverbFXRoom;

public class CockpitMC_200_VII_new extends CockpitPilot
{
  private boolean bNeedSetUp = true;
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictMix = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf = 1.0F;
  private float pictFuel = 0.0F;
  private static final float[] speedometerScale = { 0.0F, 29.0F, 63.5F, 98.5F, 115.5F, 132.5F, 165.5F, 202.5F, 241.0F, 280.0F, 316.0F };

  private static final float[] oilTempScale = { 0.0F, 33.0F, 80.0F, 153.0F, 301.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitMC_200_VII_new()
  {
    super("3DO/Cockpit/MC-200_VII_new/hier.him", "i16");
    this.cockpitNightMats = new String[] { "mat2_tr", "strum1dmg", "strum1", "strum2dmg", "strum2", "strum4dmg", "strum4", "strumsxdmg", "strumsx" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.mesh.chunkSetAngles("Z_Column", 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("Pedals", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle", 46.360001F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 96.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Mix", 46.25F * (this.pictMix = 0.85F * this.pictMix + 0.15F * this.fm.EI.engines[0].getControlMix()), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_PropPitch1", 54.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Flap1", 40.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.25F * this.fm.CT.FlapsControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Gear1", 90.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Trim1", -76.5F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_OilRad1", 91.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_OilRad2", 111.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 50.0F, 550.0F, 0.0F, 10.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer2", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 50.0F, 550.0F, 0.0F, 10.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(this.setNew.vspeed, -25.0F, 25.0F, 180.0F, -180.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3000.0F, 0.0F, -327.0F), 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Turn1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -21.0F, 21.0F), 0.0F);

    this.mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -12.0F, 12.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_ManfoldPress", 0.0F, this.pictManf = 0.9F * this.pictManf + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, -317.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_OilTemp1", 0.0F, -floatindex(cvt(this.fm.EI.engines[0].tOilOut, 30.0F, 150.0F, 0.0F, 4.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("Z_OilPress1", 0.0F, -cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 301.5F), 0.0F);

    this.mesh.chunkSetAngles("Z_OilPress2", 0.0F, -cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_FuelPress1", 0.0F, -cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, 301.5F), 0.0F);

    float f = cvt(Math.abs(this.fm.Or.getTangage()), 0.0F, 12.0F, 1.0F, 0.0F);

    this.pictFuel = (0.92F * this.pictFuel + 0.08F * cvt(this.fm.M.fuel, 0.0F, 234.0F, cvt(f, 0.0F, 1.0F, 0.0F, 24.5F), cvt(f, 0.0F, 1.0F, 215.0F, 205.0F)));

    this.mesh.chunkSetAngles("Z_FuelQuantity1", -this.pictFuel, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_EngTemp1", 0.0F, -cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 74.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_AirPress1", 0.0F, -135.0F, 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkVisible("XLampGearUpR", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));

      this.mesh.chunkVisible("XLampGearUpL", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));

      this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));

      this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    }
    else {
      this.mesh.chunkVisible("XLampGearUpR", false);
      this.mesh.chunkVisible("XLampGearUpL", false);
      this.mesh.chunkVisible("XLampGearDownR", false);
      this.mesh.chunkVisible("XLampGearDownL", false);
    }
  }

  protected float waypointAzimuth() {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);

    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) f += 180.0F;

    while (f > 180.0F) f -= 180.0F;

    return f;
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    retoggleLight();
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void toggleLight() {
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

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    private final CockpitMC_200_VII_new this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitMC_200_VII_new.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitMC_200_VII_new.this.fm != null) {
        CockpitMC_200_VII_new.access$102(CockpitMC_200_VII_new.this, CockpitMC_200_VII_new.this.setOld);
        CockpitMC_200_VII_new.access$202(CockpitMC_200_VII_new.this, CockpitMC_200_VII_new.this.setNew);
        CockpitMC_200_VII_new.access$302(CockpitMC_200_VII_new.this, CockpitMC_200_VII_new.this.setTmp);
        CockpitMC_200_VII_new.this.setNew.throttle = (0.85F * CockpitMC_200_VII_new.this.setOld.throttle + CockpitMC_200_VII_new.this.fm.CT.PowerControl * 0.15F);

        CockpitMC_200_VII_new.this.setNew.prop = (0.85F * CockpitMC_200_VII_new.this.setOld.prop + CockpitMC_200_VII_new.this.fm.CT.getStepControl() * 0.15F);

        CockpitMC_200_VII_new.this.setNew.altimeter = CockpitMC_200_VII_new.this.fm.getAltitude();
        float f = CockpitMC_200_VII_new.this.waypointAzimuth();
        CockpitMC_200_VII_new.this.setNew.waypointAzimuth.setDeg(CockpitMC_200_VII_new.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitMC_200_VII_new.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));

        CockpitMC_200_VII_new.this.setNew.azimuth.setDeg(CockpitMC_200_VII_new.this.setOld.azimuth.getDeg(1.0F), CockpitMC_200_VII_new.this.fm.Or.azimut());

        CockpitMC_200_VII_new.this.setNew.vspeed = ((199.0F * CockpitMC_200_VII_new.this.setOld.vspeed + CockpitMC_200_VII_new.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }
}