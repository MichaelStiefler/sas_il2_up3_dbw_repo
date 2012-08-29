package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitA6M2N extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private LightPointActor light1;
  private LightPointActor light2;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 7.5F, 32.5F, 80.5F, 147.5F, 212.0F, 288.0F, 370.5F, 450.5F, 533.5F, 594.0F, 673.5F };

  private static final float[] oilScale = { 0.0F, -27.5F, 12.0F, 59.5F, 127.0F, 212.5F, 311.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(5.0F);
  }

  public CockpitA6M2N()
  {
    super("3DO/Cockpit/A6M2-N/hier.him", "bf109");

    this.light1 = new LightPointActor(new LightPoint(), new Point3d(-0.1756D, 0.3924D, 0.5913000000000001D));
    this.light2 = new LightPointActor(new LightPoint(), new Point3d(-0.1479D, -0.3612D, 0.5913000000000001D));
    this.light1.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
    this.light2.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.setNew.dimPosition = 1.0F;

    this.cockpitNightMats = new String[] { "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1", "turnbank" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    AircraftLH.printCompassHeading = true;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.6F);
    this.mesh.chunkSetLocate("Blister_D0", xyz, ypr);

    this.mesh.chunkSetAngles("sunOFF", 0.0F, cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -77.0F), 0.0F);
    this.mesh.chunkSetAngles("sight_rev", 0.0F, cvt(interp(this.setNew.stbyPosition, this.setOld.stbyPosition, paramFloat), 0.0F, 1.0F, 0.0F, -115.0F), 0.0F);
    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 15.5F);
    this.mesh.chunkSetAngles("Stick_tube", 0.0F, -15.5F * this.pictElev, 0.0F);
    if ((this.fm.CT.Weapons[3] != null) && (!this.fm.CT.Weapons[3][0].haveBullets())) {
      this.mesh.chunkSetAngles("Turn1", 0.0F, 53.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Turn2", 0.0F, this.fm.Gears.bTailwheelLocked ? 53.0F : 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turn3", 0.0F, 68.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Turn3_rod", 0.0F, -68.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Turn5", 0.0F, 75.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Turn6", 0.0F, 68.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Turn6_rod", 0.0F, -68.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Turn7", 0.0F, this.fm.CT.saveWeaponControl[1] != 0 ? 26.0F : 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turn8", 0.0F, 20.0F - 40.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Pedals", 11.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_trossL", -11.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_trossR", -11.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCompassOil1", cvt(this.fm.Or.getTangage(), -10.0F, 10.0F, -10.0F, 10.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zCompassOil3", cvt(this.fm.Or.getKren(), -10.0F, 10.0F, -10.0F, 10.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zCompassOil2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Azimuth1a", 0.0F, -this.setNew.waypointDirection.getDeg(paramFloat * 0.1F), 0.0F);
    this.mesh.chunkSetAngles("Z_Navigation", 0.0F, cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.2F), -25.0F, 25.0F, -45.0F, 45.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Clock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Clock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Magneto", 0.0F, cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, -104.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_AirSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 814.87976F, 0.0F, 11.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("Z_Alt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 14400.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Alt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 1440.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Horison1a", 0.0F, -this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("Z_Horison1b", 0.0F, cvt(this.fm.Or.getTangage(), -33.0F, 33.0F, 33.0F, -33.0F), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_Horison1c", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Turn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Turn1b", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -14.0F, 14.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Vspeed", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TempCilinder", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TempOil", 0.0F, floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 160.0F, 0.0F, 7.0F), oilScale), 0.0F);
    this.mesh.chunkSetAngles("Z_pressFuel", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 8.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_pressOil", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 5.0F, 0.0F, -180.0F), 0.0F);
    float f = this.fm.EI.engines[0].getRPM();
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      f = 2100.0F;
    }
    this.mesh.chunkSetAngles("Z_Revolution", 0.0F, cvt(f, 500.0F, 4500.0F, 0.0F, 720.0F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x4) == 0) {
      this.mesh.chunkSetAngles("Z_Manifold", 0.0F, cvt(this.setNew.man, 0.400051F, 1.333305F, -202.5F, 112.5F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_FuelWing", 0.0F, cvt(this.fm.M.fuel * 1.388F, 0.0F, 250.0F, 0.0F, 235.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelFuse", 0.0F, cvt(this.fm.M.fuel * 1.388F, 0.0F, 80.0F, 0.0F, 264.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes4_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("Z_Clock1a", false);
      this.mesh.chunkVisible("Z_Clock1b", false);
      this.mesh.chunkVisible("Z_TempOil", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("Z_Horison1c", false);
      this.mesh.chunkVisible("Z_AirSpeed", false);
      this.mesh.chunkVisible("Z_pressFuel", false);
      this.mesh.chunkVisible("Z_pressOil", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("Z_Holes3_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("Z_Holes3_D1", true);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.5F, 0.25F);
      this.light2.light.setEmit(1.0F, 0.25F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
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
      if (CockpitA6M2N.this.fm != null) {
        CockpitA6M2N.access$102(CockpitA6M2N.this, CockpitA6M2N.this.setOld); CockpitA6M2N.access$202(CockpitA6M2N.this, CockpitA6M2N.this.setNew); CockpitA6M2N.access$302(CockpitA6M2N.this, CockpitA6M2N.this.setTmp);

        if (CockpitA6M2N.this.cockpitDimControl) {
          if (CockpitA6M2N.this.setNew.dimPosition > 0.0F) CockpitA6M2N.this.setNew.dimPosition = (CockpitA6M2N.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitA6M2N.this.setNew.dimPosition < 1.0F) CockpitA6M2N.this.setNew.dimPosition = (CockpitA6M2N.this.setOld.dimPosition + 0.05F);

        if ((CockpitA6M2N.this.fm.AS.astateCockpitState & 0x2) != 0) {
          if (CockpitA6M2N.this.setNew.stbyPosition > 0.0F) CockpitA6M2N.this.setNew.stbyPosition = (CockpitA6M2N.this.setOld.stbyPosition - 0.025F);
        }
        else if (CockpitA6M2N.this.setNew.stbyPosition < 1.0F) CockpitA6M2N.this.setNew.stbyPosition = (CockpitA6M2N.this.setOld.stbyPosition + 0.025F);

        CockpitA6M2N.this.setNew.throttle = (0.9F * CockpitA6M2N.this.setOld.throttle + 0.1F * CockpitA6M2N.this.fm.CT.PowerControl);
        CockpitA6M2N.this.setNew.prop = (0.9F * CockpitA6M2N.this.setOld.prop + 0.1F * CockpitA6M2N.this.fm.EI.engines[0].getControlProp());
        CockpitA6M2N.this.setNew.mix = (0.8F * CockpitA6M2N.this.setOld.mix + 0.2F * CockpitA6M2N.this.fm.EI.engines[0].getControlMix());
        CockpitA6M2N.this.setNew.man = (0.92F * CockpitA6M2N.this.setOld.man + 0.08F * CockpitA6M2N.this.fm.EI.engines[0].getManifoldPressure());
        CockpitA6M2N.this.setNew.altimeter = CockpitA6M2N.this.fm.getAltitude();

        CockpitA6M2N.this.setNew.azimuth.setDeg(CockpitA6M2N.this.setOld.azimuth.getDeg(1.0F), CockpitA6M2N.this.fm.Or.azimut());

        CockpitA6M2N.this.setNew.vspeed = ((199.0F * CockpitA6M2N.this.setOld.vspeed + CockpitA6M2N.this.fm.getVertSpeed()) / 200.0F);
        float f = CockpitA6M2N.this.waypointAzimuth();

        if (CockpitA6M2N.this.useRealisticNavigationInstruments())
        {
          CockpitA6M2N.this.setNew.waypointAzimuth.setDeg(CockpitA6M2N.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitA6M2N.this.getBeaconDirection());
          CockpitA6M2N.this.setNew.waypointDirection.setDeg(CockpitA6M2N.this.setOld.waypointDirection.getDeg(1.0F), f);
        }
        else
        {
          CockpitA6M2N.this.setNew.waypointAzimuth.setDeg(CockpitA6M2N.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitA6M2N.this.setOld.azimuth.getDeg(1.0F));
          CockpitA6M2N.this.setNew.waypointDirection.setDeg(CockpitA6M2N.this.setOld.waypointDirection.getDeg(0.1F), CockpitA6M2N.this.fm.Or.azimut() + 90.0F);
        }

      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float man;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork waypointDirection;
    float vspeed;
    float dimPosition;
    float stbyPosition;
    private final CockpitA6M2N this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDirection = new AnglesFork();
    }

    Variables(CockpitA6M2N.1 arg2)
    {
      this();
    }
  }
}