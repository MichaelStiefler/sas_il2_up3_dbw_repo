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
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitSea3 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictBrake = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf = 1.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 7.5F, 17.5F, 37.0F, 63.0F, 88.5F, 114.5F, 143.0F, 171.5F, 202.5F, 228.5F, 255.5F, 282.0F, 309.0F, 336.5F, 366.5F, 394.0F, 421.0F, 448.5F, 474.5F, 500.5F, 530.0F, 557.5F, 584.0F, 609.0F, 629.0F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F, 125.0F, 161.0F, 202.5F, 253.0F, 315.5F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 22.0F, 58.0F, 103.5F, 152.5F, 193.5F, 245.0F, 281.5F, 311.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitSea3()
  {
    super("3DO/Cockpit/SeafireMkIII/hier.him", "bf109");

    if ((aircraft() instanceof SEAFIRE3F)) {
      this.mesh.materialReplace("BORT2", "BORT2b");
      this.mesh.materialReplace("text14", "text14b");
    }

    this.cockpitNightMats = new String[] { "COMPASS", "BORT2", "prib_five", "prib_five_damage", "prib_one", "prib_one_damage", "prib_three", "prib_three_damage", "prib_two", "prib_two_damage", "text13", "text15" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.2F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    if ((aircraft() instanceof SEAFIRE3)) {
      ((SEAFIRE3)aircraft()); if (SEAFIRE3.bChangedPit) {
        reflectPlaneToModel();
        ((SEAFIRE3)aircraft()); SEAFIRE3.bChangedPit = false;
      }
    } else if ((aircraft() instanceof SEAFIRE3F)) {
      ((SEAFIRE3F)aircraft()); if (SEAFIRE3F.bChangedPit) {
        reflectPlaneToModel();
        ((SEAFIRE3F)aircraft()); SEAFIRE3F.bChangedPit = false;
      }

    }

    float f = this.fm.CT.getWing();
    this.mesh.chunkSetAngles("WingLMid_D0", 0.0F, -112.0F * f, 0.0F);
    this.mesh.chunkSetAngles("WingLOut_D0", 0.0F, -112.0F * f, 0.0F);
    this.mesh.chunkSetAngles("WingRMid_D0", 0.0F, -112.0F * f, 0.0F);
    this.mesh.chunkSetAngles("WingROut_D0", 0.0F, -112.0F * f, 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.arrestorControl, 0.01F, 0.99F, 0.0F, 0.03F);
    this.mesh.chunkSetLocate("Arrester1", xyz, ypr);
    this.mesh.chunkVisible("XLampArrest", this.fm.CT.getArrestor() > 0.9F);

    f = this.fm.CT.getAileron();
    this.mesh.chunkSetAngles("AroneL_D0", 0.0F, -30.0F * f, 0.0F);
    this.mesh.chunkSetAngles("AroneR_D0", 0.0F, -30.0F * f, 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
    this.mesh.chunkSetLocate("Blister_D0", xyz, ypr);

    this.mesh.chunkVisible("XLampGearUpL", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("XLampGearDownL", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("XLampFuel", this.fm.M.fuel < 0.25F * this.fm.M.maxFuel);

    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Column", -30.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01b", -9.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01c", -12.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02b", -7.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02c", -15.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03b", -8.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03c", -18.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Stick_Shtok01", 0.0F, 0.0F, 8.0F * this.pictElev);
    this.mesh.chunkSetAngles("Z_ColumnSwitch", -18.0F * (this.pictBrake = 0.89F * this.pictBrake + 0.11F * this.fm.CT.BrakeControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", -64.545403F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_BasePedal", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = (0.0578F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_LeftPedal", xyz, ypr);
    xyz[2] = (-0.0578F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Gear1", -160.0F + 160.0F * (this.pictGear = 0.89F * this.pictGear + 0.11F * this.fm.CT.GearControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 160.0F * (this.pictFlap = 0.89F * this.pictFlap + 0.11F * this.fm.CT.FlapsControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim1", 1000.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 1000.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Prop1", -90.0F * this.setNew.prop, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", -60.0F * this.setNew.mix, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("COMPASS_M", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F));
    this.mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F));
    this.mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -108.0F));

    this.mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -cvt(this.pictManf = 0.91F * this.pictManf + 0.09F * this.fm.EI.engines[0].getManifoldPressure(), 0.5173668F, 2.72369F, -70.0F, 250.0F));

    this.mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 378.54001F, 0.0F, 68.0F));

    this.mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 1000.0F, 5000.0F, 2.0F, 10.0F), rpmScale));

    this.mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 100.0F, 0.0F, 271.0F));
    this.mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale));

    this.mesh.chunkSetAngles("STR_OIL_LB", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -37.0F), 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F));
    this.mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F));

    this.mesh.chunkVisible("STRELK_V_SHORT", false);

    this.mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 257.22217F, 0.0F, 25.0F), speedometerScale));

    this.mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale));

    this.mesh.chunkSetAngles("STRELKA_GOR", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.032F, -0.032F);
    this.mesh.chunkSetLocate("STRELKA_GOS", xyz, ypr);

    this.mesh.chunkSetAngles("STRELKA_HOUR", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("STRELKA_MINUTE", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("STRELKA_SECUND", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("STR_CLIMB", 0.0F, 0.0F, cvt(this.fm.CT.trimElevator, -0.5F, 0.5F, -35.23F, 35.23F));
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.mesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);

    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D2o"));
    this.mesh.materialReplace("Matt1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);

    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay6"));
    this.mesh.materialReplace("Overlay6", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay7"));
    this.mesh.materialReplace("Overlay7", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD1o"));
    this.mesh.materialReplace("OverlayD1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD2o"));
    this.mesh.materialReplace("OverlayD2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLIn_D0", localHierMesh.isChunkVisible("WingLIn_D0"));
    this.mesh.chunkVisible("WingLIn_D1", localHierMesh.isChunkVisible("WingLIn_D1"));
    this.mesh.chunkVisible("WingLIn_D2", localHierMesh.isChunkVisible("WingLIn_D2"));
    this.mesh.chunkVisible("WingLIn_D3", localHierMesh.isChunkVisible("WingLIn_D3"));
    this.mesh.chunkVisible("WingLIn_CAP", localHierMesh.isChunkVisible("WingLIn_CAP"));
    this.mesh.chunkVisible("WingRIn_D0", localHierMesh.isChunkVisible("WingRIn_D0"));
    this.mesh.chunkVisible("WingRIn_D1", localHierMesh.isChunkVisible("WingRIn_D1"));
    this.mesh.chunkVisible("WingRIn_D2", localHierMesh.isChunkVisible("WingRIn_D2"));
    this.mesh.chunkVisible("WingRIn_D3", localHierMesh.isChunkVisible("WingRIn_D3"));
    this.mesh.chunkVisible("WingRIn_CAP", localHierMesh.isChunkVisible("WingRIn_CAP"));

    this.mesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.mesh.chunkVisible("WingLMid_D1", localHierMesh.isChunkVisible("WingLMid_D1"));
    this.mesh.chunkVisible("WingLMid_D2", localHierMesh.isChunkVisible("WingLMid_D2"));
    this.mesh.chunkVisible("WingLMid_D3", localHierMesh.isChunkVisible("WingLMid_D3"));
    this.mesh.chunkVisible("WingLMid_CAP", localHierMesh.isChunkVisible("WingLMid_CAP"));
    this.mesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
    this.mesh.chunkVisible("WingRMid_D1", localHierMesh.isChunkVisible("WingRMid_D1"));
    this.mesh.chunkVisible("WingRMid_D2", localHierMesh.isChunkVisible("WingRMid_D2"));
    this.mesh.chunkVisible("WingRMid_D3", localHierMesh.isChunkVisible("WingRMid_D3"));
    this.mesh.chunkVisible("WingRMid_CAP", localHierMesh.isChunkVisible("WingRMid_CAP"));

    this.mesh.chunkVisible("WingLOut_D0", localHierMesh.isChunkVisible("WingLOut_D0"));
    this.mesh.chunkVisible("WingLOut_D1", localHierMesh.isChunkVisible("WingLOut_D1"));
    this.mesh.chunkVisible("WingLOut_D2", localHierMesh.isChunkVisible("WingLOut_D2"));
    this.mesh.chunkVisible("WingLOut_D3", localHierMesh.isChunkVisible("WingLOut_D3"));
    this.mesh.chunkVisible("WingROut_D0", localHierMesh.isChunkVisible("WingROut_D0"));
    this.mesh.chunkVisible("WingROut_D1", localHierMesh.isChunkVisible("WingROut_D1"));
    this.mesh.chunkVisible("WingROut_D2", localHierMesh.isChunkVisible("WingROut_D2"));
    this.mesh.chunkVisible("WingROut_D3", localHierMesh.isChunkVisible("WingROut_D3"));

    this.mesh.chunkVisible("AroneL_D0", localHierMesh.isChunkVisible("AroneL_D0"));
    this.mesh.chunkVisible("AroneL_D1", localHierMesh.isChunkVisible("AroneL_D1"));
    this.mesh.chunkVisible("AroneR_D0", localHierMesh.isChunkVisible("AroneR_D0"));
    this.mesh.chunkVisible("AroneR_D1", localHierMesh.isChunkVisible("AroneR_D1"));
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
      if (CockpitSea3.this.fm != null) {
        CockpitSea3.access$102(CockpitSea3.this, CockpitSea3.this.setOld); CockpitSea3.access$202(CockpitSea3.this, CockpitSea3.this.setNew); CockpitSea3.access$302(CockpitSea3.this, CockpitSea3.this.setTmp);

        CockpitSea3.this.setNew.throttle = (0.92F * CockpitSea3.this.setOld.throttle + 0.08F * CockpitSea3.this.fm.CT.PowerControl);
        CockpitSea3.this.setNew.prop = (0.92F * CockpitSea3.this.setOld.prop + 0.08F * CockpitSea3.this.fm.EI.engines[0].getControlProp());
        CockpitSea3.this.setNew.mix = (0.92F * CockpitSea3.this.setOld.mix + 0.08F * CockpitSea3.this.fm.EI.engines[0].getControlMix());
        CockpitSea3.this.setNew.altimeter = CockpitSea3.this.fm.getAltitude();
        if (Math.abs(CockpitSea3.this.fm.Or.getKren()) < 30.0F) {
          CockpitSea3.this.setNew.azimuth = (0.97F * CockpitSea3.this.setOld.azimuth + 0.03F * -CockpitSea3.this.fm.Or.getYaw());
        }
        if ((CockpitSea3.this.setOld.azimuth > 270.0F) && (CockpitSea3.this.setNew.azimuth < 90.0F)) CockpitSea3.this.setOld.azimuth -= 360.0F;
        if ((CockpitSea3.this.setOld.azimuth < 90.0F) && (CockpitSea3.this.setNew.azimuth > 270.0F)) CockpitSea3.this.setOld.azimuth += 360.0F;
        CockpitSea3.this.setNew.waypointAzimuth = (0.91F * CockpitSea3.this.setOld.waypointAzimuth + 0.09F * (CockpitSea3.this.waypointAzimuth() - CockpitSea3.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitSea3.this.setNew.vspeed = (0.99F * CockpitSea3.this.setOld.vspeed + 0.01F * CockpitSea3.this.fm.getVertSpeed());
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitSea3 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitSea3.1 arg2) { this();
    }
  }
}