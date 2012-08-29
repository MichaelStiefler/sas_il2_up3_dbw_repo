package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitYAK_9D extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private LightPointActor light1;
  private LightPointActor light2;
  private LightPointActor light3;
  private LightPointActor light4;
  private LightPointActor light5;
  private LightPointActor light6;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private Orient tmpOr = new Orient();

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    this.tmpOr.setAT0(this.tmpV);
    this.tmpOr.wrap();
    return this.tmpOr.azimut();
  }

  public CockpitYAK_9D()
  {
    super("3DO/Cockpit/Yak-9D/hier.him", "bf109");

    this.light1 = new LightPointActor(new LightPoint(), new Point3d(-0.8479D, 0.3392D, 0.3119D));
    this.light2 = new LightPointActor(new LightPoint(), new Point3d(-0.7585D, 0.2842D, 0.2718D));
    this.light3 = new LightPointActor(new LightPoint(), new Point3d(-0.586D, 0.201D, 0.2108D));
    this.light4 = new LightPointActor(new LightPoint(), new Point3d(-0.8479D, -0.3392D, 0.3119D));
    this.light5 = new LightPointActor(new LightPoint(), new Point3d(-0.7585D, -0.2842D, 0.2718D));
    this.light6 = new LightPointActor(new LightPoint(), new Point3d(-0.586D, -0.201D, 0.2108D));
    this.light1.light.setColor(245.0F, 221.0F, 189.0F);
    this.light2.light.setColor(245.0F, 221.0F, 189.0F);
    this.light3.light.setColor(245.0F, 221.0F, 189.0F);
    this.light4.light.setColor(245.0F, 221.0F, 189.0F);
    this.light5.light.setColor(245.0F, 221.0F, 189.0F);
    this.light6.light.setColor(245.0F, 221.0F, 189.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.light3.light.setEmit(0.0F, 0.0F);
    this.light4.light.setEmit(0.0F, 0.0F);
    this.light5.light.setEmit(0.0F, 0.0F);
    this.light6.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);
    this.pos.base().draw.lightMap().put("LAMPHOOK3", this.light3);
    this.pos.base().draw.lightMap().put("LAMPHOOK4", this.light4);
    this.pos.base().draw.lightMap().put("LAMPHOOK5", this.light5);
    this.pos.base().draw.lightMap().put("LAMPHOOK6", this.light6);

    this.cockpitNightMats = new String[] { "prib_one", "prib_two", "prib_four", "prib_five", "shkala", "prib_one_dd", "prib_two_dd" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm == null) return;

    this.mesh.chunkSetAngles("richag", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("norm_gaz", 0.0F, -13.0F + interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 33.799999F, 0.0F);
    this.mesh.chunkSetAngles("shag_vinta", 0.0F, interp(this.setNew.prop, this.setOld.prop, paramFloat) * 33.799999F - 13.0F, 0.0F);

    this.mesh.chunkSetAngles("nadduv", 0.0F, -19.0F + 39.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("shassy", 0.0F, 24.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("shassy", 0.0F, -24.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("shassy", 0.0F, 0.0F, 0.0F);
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("shitki", 0.0F, -24.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("shitki", 0.0F, 24.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("shitki", 0.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x4) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.w.set(this.fm.getW());

      this.fm.Or.transform(this.w);
      this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);

      this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);

      this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

      this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 8.0F, 0.0F, -180.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPK10", 0.0F, cvt(interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), -25.0F, 25.0F, 35.0F, -35.0F), 0.0F);

    this.mesh.chunkVisible("Z_Red1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.EI.engines[0].getControlMagnetos() < 3);
    this.mesh.chunkVisible("Z_Green1", false);
    this.mesh.chunkVisible("Z_Green2", false);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("zManifold1a", false);
      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0) || ((this.fm.AS.astateCockpitState & 0x40) != 0))
    {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zRPM1b", false);
      this.mesh.chunkVisible("zTOilOut1a", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
      this.mesh.chunkVisible("zGasPrs1a", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zSlide1a", false);
      this.mesh.chunkVisible("panel", false);
      this.mesh.chunkVisible("panel_d1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0))
    {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.09F, 0.1F);
      this.light2.light.setEmit(0.02F, 0.2F);
      this.light3.light.setEmit(0.02F, 0.5F);
      this.light4.light.setEmit(0.09F, 0.1F);
      this.light5.light.setEmit(0.02F, 0.2F);
      this.light6.light.setEmit(0.02F, 0.5F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      this.light3.light.setEmit(0.0F, 0.0F);
      this.light4.light.setEmit(0.0F, 0.0F);
      this.light5.light.setEmit(0.0F, 0.0F);
      this.light6.light.setEmit(0.0F, 0.0F);
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
      CockpitYAK_9D.this.fm = World.getPlayerFM();
      if (CockpitYAK_9D.this.fm != null) {
        CockpitYAK_9D.access$102(CockpitYAK_9D.this, CockpitYAK_9D.this.setOld); CockpitYAK_9D.access$202(CockpitYAK_9D.this, CockpitYAK_9D.this.setNew); CockpitYAK_9D.access$302(CockpitYAK_9D.this, CockpitYAK_9D.this.setTmp);

        CockpitYAK_9D.this.setNew.throttle = ((10.0F * CockpitYAK_9D.this.setOld.throttle + CockpitYAK_9D.this.fm.CT.PowerControl) / 11.0F);
        CockpitYAK_9D.this.setNew.prop = ((10.0F * CockpitYAK_9D.this.setOld.prop + CockpitYAK_9D.this.fm.EI.engines[0].getControlProp()) / 11.0F);
        CockpitYAK_9D.this.setNew.altimeter = CockpitYAK_9D.this.fm.getAltitude();
        if (Math.abs(CockpitYAK_9D.this.fm.Or.getKren()) < 30.0F) {
          CockpitYAK_9D.this.setNew.azimuth = ((35.0F * CockpitYAK_9D.this.setOld.azimuth + CockpitYAK_9D.this.fm.Or.azimut()) / 36.0F);
        }
        if ((CockpitYAK_9D.this.setOld.azimuth > 270.0F) && (CockpitYAK_9D.this.setNew.azimuth < 90.0F)) CockpitYAK_9D.this.setOld.azimuth -= 360.0F;
        if ((CockpitYAK_9D.this.setOld.azimuth < 90.0F) && (CockpitYAK_9D.this.setNew.azimuth > 270.0F)) CockpitYAK_9D.this.setOld.azimuth += 360.0F;
        CockpitYAK_9D.this.setNew.vspeed = ((199.0F * CockpitYAK_9D.this.setOld.vspeed + CockpitYAK_9D.this.fm.getVertSpeed()) / 200.0F);
        CockpitYAK_9D.this.setNew.waypointAzimuth = ((10.0F * CockpitYAK_9D.this.setOld.waypointAzimuth + (CockpitYAK_9D.this.waypointAzimuth() - CockpitYAK_9D.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F)) / 11.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitYAK_9D this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitYAK_9D.1 arg2) { this();
    }
  }
}