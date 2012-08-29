package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitFW_190D9LATE extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null, null };
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private boolean bNeedSetUp;
  private BulletEmitter[] bomb = { null, null, null, null };
  private float pictAiler;
  private float pictElev;
  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 53.0F, 108.0F, 170.0F, 229.0F, 282.0F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 16.0F, 35.0F, 52.5F, 72.0F, 72.0F };

  private static final float[] manPrsScale = { 0.0F, 0.0F, 0.0F, 15.5F, 71.0F, 125.0F, 180.0F, 235.0F, 290.0F, 245.0F, 247.0F, 247.0F };

  private static final float[] oilfuelNeedleScale = { 0.0F, 38.0F, 84.0F, 135.5F, 135.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };
  private Point3d tmpP;
  private Vector3d tmpV;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Blister1_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Blister1_D0", true);
    super.doFocusLeave();
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
    {
      return 0.0F;
    }

    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc);
    return (float)(57.295779513082323D * Math.atan2(this.tmpV.y, this.tmpV.x));
  }

  public CockpitFW_190D9LATE()
  {
    super("3DO/Cockpit/FW-190D-9Late/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.setNew.dimPosition = 1.0F;
    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LIGHTHOOK_L");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LIGHTHOOK_R");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK2", this.light2);
    this.cockpitNightMats = new String[] { "D9GP1", "D9GP2", "D9GP3", "A5GP3Km", "D9GP4_MW50", "D9GP5", "A4GP6", "D9Trans2", "DD9GP1", "D9GP2", "DD9GP3", "DD9GP4", "DD9Trans2" };

    setNightMats(false);
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    int i = ((FW_190D9LATE)aircraft()).k14Mode;
    boolean bool = i < 2;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", bool);
    bool = i > 0;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE1", bool);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);
    resetYPRmodifier();
    Cockpit.xyz[0] = this.setNew.k14w;
    for (int j = 1; j < 7; j++)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_AIMMARK" + j, bool);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
    }

    if (this.gun[0] == null)
    {
      this.gun[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_MGUN02");
      this.gun[2] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON03");
      this.gun[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON04");
    }
    if (this.bomb[0] == null)
    {
      for (int k = 0; k < this.bomb.length; k++) {
        this.bomb[k] = GunEmpty.get();
      }
      if (((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb05") != GunEmpty.get())
      {
        this.bomb[1] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb05");
        this.bomb[2] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb05");
      }
      else if (((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev02") != GunEmpty.get())
      {
        this.bomb[1] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev02");
        this.bomb[2] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev02");
      }
      if (((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalRock01") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalRock01");
        this.bomb[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalRock02");
      }
      else if (((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb03") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb03");
        this.bomb[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb04");
      }
      else if (((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev05") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev05");
        this.bomb[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalDev06");
      }
    }
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.43F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Glass", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -0.33F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RearArmorPlate", Cockpit.xyz, Cockpit.ypr);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x80) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleALT", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, -180.0F));
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleManPress", -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) == 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleFuel", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 4.0F), fuelScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleWaterTemp", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleOilTemp", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x1) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x4) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleFuelPress", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) == 0))
    {
      if (aircraft().isFMTrackMirror())
      {
        f = aircraft().fmTrack().getCockpitAzimuthSpeed();
      }
      else {
        f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, 20.0F, -20.0F);
        if (aircraft().fmTrack() != null)
          aircraft().fmTrack().setCockpitAzimuthSpeed(f);
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHTurn", 0.0F, f, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHBank", 0.0F, -cvt(getBall(7.0D), -7.0F, 7.0F, 11.0F, -11.0F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getKren());
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage(), -45.0F, 45.0F, 12.0F, -12.0F));
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x80) == 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleCD", this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RepeaterOuter", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RepeaterPlane", interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) == 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleTrimmung", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getTrimElevatorControl() * 25.0F, 0.0F, 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x80) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHClock", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleMClock", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleSClock", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    }
    float f = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.nitro / this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.maxNitro;
    f = (float)Math.sqrt(f);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleMW50Press", cvt(f, 0.0F, 0.5F, 0.0F, 260.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.gun[0] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG17_L", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[1] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG17_R", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[2] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[2].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG151_L", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[3] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[3].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG151_R", Cockpit.xyz, Cockpit.ypr);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("IgnitionSwitch", 24.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Revi16Tinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.ElevatorControl) * 20.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.WeaponControl[1] != 0 ? -0.004F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("SecTrigger", Cockpit.xyz, Cockpit.ypr);
    Cockpit.ypr[0] = (interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 34.0F * 0.91F);
    Cockpit.xyz[2] = (Cockpit.ypr[0] > 7.0F ? -0.006F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ThrottleQuad", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getBrake() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("LPedal", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getRudder() * 15.0F - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getBrake() * 15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampTankSwitch", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel > 144.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampFuelLow", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel < 43.200001F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearL_1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.05F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearL_2", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() > 0.95F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Gears.lgear));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearR_1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.05F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearR_2", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() > 0.95F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Gears.rgear));
  }

  public void toggleDim()
  {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.005F, 0.75F);
      this.light2.light.setEmit(0.005F, 0.75F);
      setNightMats(true);
    }
    else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private void retoggleLight()
  {
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      setNightMats(false);
      setNightMats(true);
    }
    else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x1) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x4) != 0))
    {
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) != 0)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi16", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi16Tinter", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_MASK", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("DRevi16", true);
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP1", "DD9GP1");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP1_night", "DD9GP1_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleManPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleRPM", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RepeaterOuter", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RepeaterPlane", false);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP2", "DD9GP2");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP2_night", "DD9GP2_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHCyl", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHBank", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHBar", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHTurn", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleFuelPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleOilPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP4_MW50", "DD9GP4_MW50");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP4_MW50_night", "DD9GP4_MW50_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleFuel", false);
      resetYPRmodifier();
      Cockpit.xyz[0] = -0.001F;
      Cockpit.xyz[1] = 0.008F;
      Cockpit.xyz[2] = 0.025F;
      Cockpit.ypr[0] = 3.0F;
      Cockpit.ypr[1] = -6.0F;
      Cockpit.ypr[2] = 1.5F;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("IPCentral", Cockpit.xyz, Cockpit.ypr);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x80) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP3", "DD9GP3");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP3_night", "DD9GP3_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleKMH", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleCD", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAlt", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAltKM", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9Trans2", "DD9Trans2");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9Trans2_night", "DD9Trans2_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleWaterTemp", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleOilTemp", false);
    }
    retoggleLight();
  }

  static
  {
    Property.set(CockpitFW_190D9LATE.class, "normZN", 0.72F);
    Property.set(CockpitFW_190D9LATE.class, "gsZN", 0.66F);
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitFW_190D9LATE this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitFW_190D9LATE.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitFW_190D9LATE.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitFW_190D9LATE.access$002(CockpitFW_190D9LATE.this, CockpitFW_190D9LATE.this.setOld);
        CockpitFW_190D9LATE.access$102(CockpitFW_190D9LATE.this, CockpitFW_190D9LATE.this.setNew);
        CockpitFW_190D9LATE.access$202(CockpitFW_190D9LATE.this, CockpitFW_190D9LATE.this.setTmp);
        CockpitFW_190D9LATE.this.setNew.altimeter = CockpitFW_190D9LATE.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (CockpitFW_190D9LATE.this.cockpitDimControl)
        {
          if (CockpitFW_190D9LATE.this.setNew.dimPosition > 0.0F)
            CockpitFW_190D9LATE.this.setNew.dimPosition = (CockpitFW_190D9LATE.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitFW_190D9LATE.this.setNew.dimPosition < 1.0F)
          CockpitFW_190D9LATE.this.setNew.dimPosition = (CockpitFW_190D9LATE.this.setOld.dimPosition + 0.05F);
        CockpitFW_190D9LATE.this.setNew.throttle = ((10.0F * CockpitFW_190D9LATE.this.setOld.throttle + CockpitFW_190D9LATE.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl) / 11.0F);
        CockpitFW_190D9LATE.this.setNew.vspeed = ((499.0F * CockpitFW_190D9LATE.this.setOld.vspeed + CockpitFW_190D9LATE.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 500.0F);
        CockpitFW_190D9LATE.this.setNew.azimuth = CockpitFW_190D9LATE.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
        if ((CockpitFW_190D9LATE.this.setOld.azimuth > 270.0F) && (CockpitFW_190D9LATE.this.setNew.azimuth < 90.0F))
          CockpitFW_190D9LATE.this.setOld.azimuth -= 360.0F;
        if ((CockpitFW_190D9LATE.this.setOld.azimuth < 90.0F) && (CockpitFW_190D9LATE.this.setNew.azimuth > 270.0F))
          CockpitFW_190D9LATE.this.setOld.azimuth += 360.0F;
        CockpitFW_190D9LATE.this.setNew.waypointAzimuth = ((10.0F * CockpitFW_190D9LATE.this.setOld.waypointAzimuth + (CockpitFW_190D9LATE.this.waypointAzimuth() - CockpitFW_190D9LATE.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        float f1 = ((FW_190D9LATE)CockpitFW_190D9LATE.this.aircraft()).k14Distance;
        CockpitFW_190D9LATE.this.setNew.k14w = (5.0F * CockpitFW_190D9LATE.k14TargetWingspanScale[((FW_190D9LATE)CockpitFW_190D9LATE.this.aircraft()).k14WingspanType] / f1);
        CockpitFW_190D9LATE.this.setNew.k14w = (0.9F * CockpitFW_190D9LATE.this.setOld.k14w + 0.1F * CockpitFW_190D9LATE.this.setNew.k14w);
        CockpitFW_190D9LATE.this.setNew.k14wingspan = (0.9F * CockpitFW_190D9LATE.this.setOld.k14wingspan + 0.1F * CockpitFW_190D9LATE.k14TargetMarkScale[((FW_190D9LATE)CockpitFW_190D9LATE.this.aircraft()).k14WingspanType]);
        CockpitFW_190D9LATE.this.setNew.k14mode = (0.8F * CockpitFW_190D9LATE.this.setOld.k14mode + 0.2F * ((FW_190D9LATE)CockpitFW_190D9LATE.this.aircraft()).k14Mode);
        Vector3d localVector3d = CockpitFW_190D9LATE.this.aircraft().FM.getW();
        double d = 0.00125D * f1;
        float f2 = (float)Math.toDegrees(d * localVector3d.z);
        float f3 = -(float)Math.toDegrees(d * localVector3d.y);
        float f4 = CockpitFW_190D9LATE.this.floatindex((f1 - 200.0F) * 0.04F, CockpitFW_190D9LATE.k14BulletDrop) - CockpitFW_190D9LATE.k14BulletDrop[0];
        f3 += (float)Math.toDegrees(Math.atan(f4 / f1));
        CockpitFW_190D9LATE.this.setNew.k14x = (0.92F * CockpitFW_190D9LATE.this.setOld.k14x + 0.08F * f2);
        CockpitFW_190D9LATE.this.setNew.k14y = (0.92F * CockpitFW_190D9LATE.this.setOld.k14y + 0.08F * f3);
        if (CockpitFW_190D9LATE.this.setNew.k14x > 7.0F)
          CockpitFW_190D9LATE.this.setNew.k14x = 7.0F;
        if (CockpitFW_190D9LATE.this.setNew.k14x < -7.0F)
          CockpitFW_190D9LATE.this.setNew.k14x = -7.0F;
        if (CockpitFW_190D9LATE.this.setNew.k14y > 7.0F)
          CockpitFW_190D9LATE.this.setNew.k14y = 7.0F;
        if (CockpitFW_190D9LATE.this.setNew.k14y < -7.0F)
          CockpitFW_190D9LATE.this.setNew.k14y = -7.0F;
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}