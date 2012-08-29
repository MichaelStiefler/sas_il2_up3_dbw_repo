package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;

public class CockpitP_47D25 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictTurba;
  private static final float[] fuelGallonsScale = { 0.0F, 8.25F, 17.5F, 36.5F, 54.0F, 90.0F, 108.0F };

  private static final float[] fuelGallonsAuxScale = { 0.0F, 38.0F, 62.5F, 87.0F, 104.0F };

  private static final float[] speedometerScale = { 0.0F, 5.0F, 47.5F, 92.0F, 134.0F, 180.0F, 227.0F, 241.0F, 255.0F, 262.5F, 270.0F, 283.0F, 296.0F, 312.0F, 328.0F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };
  private Point3d tmpP;
  private Vector3d tmpV;

  protected float waypointAzimuth()
  {
    WayPoint waypoint = this.fm.AP.way.curr();
    if (waypoint == null)
    {
      return 0.0F;
    }

    waypoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitP_47D25()
  {
    super("3DO/Cockpit/P-47D-25/hier.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.cockpitNightMats = new String[] { "prib1", "prib2", "prib3", "prib4", "prib5", "prib6", "shkala", "prib1_d1", "prib2_d1", "prib3_d1", "prib4_d1", "prib5_d1", "prib6_d1" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float f)
  {
    int i = ((P_47D)aircraft()).k14Mode;
    boolean flag = i < 2;
    this.mesh.chunkVisible("Z_Z_RETICLE", flag);
    flag = i > 0;
    this.mesh.chunkVisible("Z_Z_RETICLE1", flag);
    this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);
    resetYPRmodifier();
    Cockpit.xyz[0] = this.setNew.k14w;
    for (int j = 1; j < 7; j++)
    {
      this.mesh.chunkVisible("Z_Z_AIMMARK" + j, flag);
      this.mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
    }

    this.mesh.chunkSetAngles("armPedalL", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("armPedalR", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 16.0F);
    this.mesh.chunkSetAngles("Z_Target1", this.setNew.k14wingspan, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("supercharge", 0.0F, 70.0F * this.setNew.stage, 0.0F);
    this.mesh.chunkSetAngles("throtly", 0.0F, 62.700001F * this.setNew.throttle, 0.0F);
    this.mesh.chunkSetAngles("prop", 0.0F, 70.0F * this.setNew.prop, 0.0F);
    this.mesh.chunkSetAngles("mixtura", 0.0F, 55.0F * this.setNew.mix, 0.0F);
    this.mesh.chunkSetAngles("flaplever", 0.0F, 0.0F, 70.0F * this.fm.CT.FlapsControl);
    this.mesh.chunkSetAngles("zfuelR", 0.0F, floatindex(cvt(this.fm.M.fuel, 0.0F, 981.0F, 0.0F, 6.0F), fuelGallonsScale), 0.0F);
    this.mesh.chunkSetAngles("zfuelL", 0.0F, -floatindex(cvt(this.fm.M.fuel, 0.0F, 981.0F, 0.0F, 4.0F), fuelGallonsAuxScale), 0.0F);
    this.mesh.chunkSetAngles("zacceleration", 0.0F, cvt(this.fm.getOverload(), -4.0F, 12.0F, -77.0F, 244.0F), 0.0F);
    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zclimb", 0.0F, floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F);
    this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -16.0F, 16.0F), 0.0F);
    this.mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.338638F, 2.370465F, 0.0F, 320.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4500.0F, 0.0F, 316.0F), 0.0F);
    this.mesh.chunkSetAngles("zoiltemp1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 300.0F, 0.0F, 84.0F), 0.0F);
    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zhorizont1a", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0328F, -0.0328F);
    this.mesh.chunkSetLocate("zhorizont1b", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("zturborpm1a", 0.0F, cvt(this.pictTurba, 0.0F, 2.0F, 0.0F, 207.5F), 0.0F);
    this.mesh.chunkSetAngles("zpressfuel1a", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.4F, 0.0F, -154.0F), 0.0F);
    this.mesh.chunkSetAngles("zpressoil1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -5.0F, 5.0F, -5.0F, 5.0F), 0.0F);
    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, 90.0F - this.setNew.azimuth, 0.0F);
    this.mesh.chunkSetAngles("zMagAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -65.0F, 65.0F, -65.0F, 65.0F), 0.0F);
    this.mesh.chunkSetAngles("zMagAzimuth1b", -90.0F + this.setNew.azimuth, 0.0F, 0.0F);
    this.mesh.chunkVisible("Z_Red1", (this.fm.CT.getGear() < 0.05F) || (!this.fm.Gears.lgear) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getGear() > 0.95F);
    this.mesh.chunkVisible("Z_Red2", this.fm.M.fuel / this.fm.M.maxFuel < 0.15F);
    this.mesh.chunkVisible("Z_Red3", this.pictTurba > 1.0485F);
    this.mesh.chunkVisible("Z_Green2", this.fm.AS.bNavLightsOn);
    this.mesh.chunkVisible("Z_Red4", this.fm.AS.bNavLightsOn);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
    {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("pricel", false);
      this.mesh.chunkVisible("pricel_d1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("zamper", false);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("zacceleration", false);
      this.mesh.chunkVisible("zMagAzimuth1a", false);
      this.mesh.chunkVisible("zMagAzimuth1b", false);
      this.mesh.chunkVisible("zpresswater1a", false);
      this.mesh.chunkVisible("zclimb", false);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zoiltemp1a", false);
      this.mesh.chunkVisible("zturbormp1a", false);
      this.mesh.chunkVisible("zfas1a", false);
      this.mesh.chunkVisible("zoxipress1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x8) != 0))
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    if (((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("zClock1b", false);
      this.mesh.chunkVisible("zClock1a", false);
      this.mesh.chunkVisible("zfuelR", false);
      this.mesh.chunkVisible("zfuelL", false);
      this.mesh.chunkVisible("zsuction1a", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zSlide1a", false);
      this.mesh.chunkVisible("zhorizont1a", false);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zpressfuel1a", false);
      this.mesh.chunkVisible("zpressoil1a", false);
      this.mesh.chunkVisible("ztempoil1a", false);
      this.mesh.chunkVisible("zManifold1a", false);
    }
    retoggleLight();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
    if (this.cockpitLightControl)
    {
      setNightMats(false);
      setNightMats(true);
    }
    else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitP_47D25.this.fm != null)
      {
        CockpitP_47D25.access$002(CockpitP_47D25.this, CockpitP_47D25.this.setOld);
        CockpitP_47D25.access$102(CockpitP_47D25.this, CockpitP_47D25.this.setNew);
        CockpitP_47D25.access$202(CockpitP_47D25.this, CockpitP_47D25.this.setTmp);
        CockpitP_47D25.this.setNew.throttle = (0.85F * CockpitP_47D25.this.setOld.throttle + CockpitP_47D25.this.fm.CT.PowerControl * 0.15F);
        CockpitP_47D25.this.setNew.prop = (0.85F * CockpitP_47D25.this.setOld.prop + CockpitP_47D25.this.fm.CT.getStepControl() * 0.15F);
        CockpitP_47D25.this.setNew.stage = (0.85F * CockpitP_47D25.this.setOld.stage + CockpitP_47D25.this.fm.EI.engines[0].getControlCompressor() * 0.15F);
        CockpitP_47D25.this.setNew.mix = (0.85F * CockpitP_47D25.this.setOld.mix + CockpitP_47D25.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitP_47D25.this.setNew.altimeter = CockpitP_47D25.this.fm.getAltitude();
        if (Math.abs(CockpitP_47D25.this.fm.Or.getKren()) < 45.0F)
          CockpitP_47D25.this.setNew.azimuth = ((35.0F * CockpitP_47D25.this.setOld.azimuth + -CockpitP_47D25.this.fm.Or.getYaw()) / 36.0F);
        if ((CockpitP_47D25.this.setOld.azimuth > 270.0F) && (CockpitP_47D25.this.setNew.azimuth < 90.0F))
          CockpitP_47D25.this.setOld.azimuth -= 360.0F;
        if ((CockpitP_47D25.this.setOld.azimuth < 90.0F) && (CockpitP_47D25.this.setNew.azimuth > 270.0F))
          CockpitP_47D25.this.setOld.azimuth += 360.0F;
        CockpitP_47D25.this.setNew.waypointAzimuth = ((10.0F * CockpitP_47D25.this.setOld.waypointAzimuth + (CockpitP_47D25.this.waypointAzimuth() - CockpitP_47D25.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        CockpitP_47D25.this.setNew.vspeed = ((199.0F * CockpitP_47D25.this.setOld.vspeed + CockpitP_47D25.this.fm.getVertSpeed()) / 200.0F);
        CockpitP_47D25.access$302(CockpitP_47D25.this, 0.97F * CockpitP_47D25.this.pictTurba + 0.03F * (0.5F * CockpitP_47D25.this.fm.EI.engines[0].getPowerOutput() + 0.5F * CockpitP_47D25.this.cvt(CockpitP_47D25.this.fm.EI.engines[0].getRPM(), 0.0F, 2000.0F, 0.0F, 1.0F)));
        float f = ((P_47D)CockpitP_47D25.this.aircraft()).k14Distance;
        CockpitP_47D25.this.setNew.k14w = (5.0F * CockpitP_47D25.k14TargetWingspanScale[((P_47D)CockpitP_47D25.this.aircraft()).k14WingspanType] / f);
        CockpitP_47D25.this.setNew.k14w = (0.9F * CockpitP_47D25.this.setOld.k14w + 0.1F * CockpitP_47D25.this.setNew.k14w);
        CockpitP_47D25.this.setNew.k14wingspan = (0.9F * CockpitP_47D25.this.setOld.k14wingspan + 0.1F * CockpitP_47D25.k14TargetMarkScale[((P_47D)CockpitP_47D25.this.aircraft()).k14WingspanType]);
        CockpitP_47D25.this.setNew.k14mode = (0.8F * CockpitP_47D25.this.setOld.k14mode + 0.2F * ((P_47D)CockpitP_47D25.this.aircraft()).k14Mode);
        Vector3d vector3d = CockpitP_47D25.this.aircraft().FM.getW();
        double d = 0.00125D * f;
        float f1 = (float)Math.toDegrees(d * vector3d.z);
        float f2 = -(float)Math.toDegrees(d * vector3d.y);
        float f3 = CockpitP_47D25.this.floatindex((f - 200.0F) * 0.04F, CockpitP_47D25.k14BulletDrop) - CockpitP_47D25.k14BulletDrop[0];
        f2 += (float)Math.toDegrees(Math.atan(f3 / f));
        CockpitP_47D25.this.setNew.k14x = (0.92F * CockpitP_47D25.this.setOld.k14x + 0.08F * f1);
        CockpitP_47D25.this.setNew.k14y = (0.92F * CockpitP_47D25.this.setOld.k14y + 0.08F * f2);
        if (CockpitP_47D25.this.setNew.k14x > 7.0F)
          CockpitP_47D25.this.setNew.k14x = 7.0F;
        if (CockpitP_47D25.this.setNew.k14x < -7.0F)
          CockpitP_47D25.this.setNew.k14x = -7.0F;
        if (CockpitP_47D25.this.setNew.k14y > 7.0F)
          CockpitP_47D25.this.setNew.k14y = 7.0F;
        if (CockpitP_47D25.this.setNew.k14y < -7.0F)
          CockpitP_47D25.this.setNew.k14y = -7.0F;
      }
      return true;
    }

    Interpolater()
    {
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float stage;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitP_47D25 this$0;

    private Variables()
    {
      this.this$0 = ???;
    }

    Variables(CockpitP_47D25.1 x1)
    {
      this();
    }
  }
}