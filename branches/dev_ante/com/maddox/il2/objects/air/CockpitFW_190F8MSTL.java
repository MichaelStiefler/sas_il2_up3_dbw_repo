package com.maddox.il2.objects.air;

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
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitFW_190F8MSTL extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null, null, null, null };
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private boolean bNeedSetUp;
  private BulletEmitter[] bomb = { null, null, null, null, null };
  private long t1;
  private float pictAiler;
  private float pictElev;
  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 53.0F, 108.0F, 170.0F, 229.0F, 282.0F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 16.0F, 35.0F, 52.5F, 72.0F, 72.0F };

  private static final float[] manPrsScale = { 0.0F, 0.0F, 0.0F, 15.5F, 71.0F, 125.0F, 180.0F, 235.0F, 290.0F, 245.0F, 247.0F, 247.0F };

  private static final float[] oilfuelNeedleScale = { 0.0F, 38.0F, 84.0F, 135.5F, 135.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] oilTempScale = { 0.0F, 23.0F, 52.0F, 81.0F, 81.0F };

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

    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc);
    return (float)(57.295779513082323D * Math.atan2(Cockpit.V.y, Cockpit.V.x));
  }

  public CockpitFW_190F8MSTL()
  {
    super("3DO/Cockpit/FW-190F-8Mistel/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
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
    this.cockpitNightMats = new String[] { "D9GP1", "A8GP2", "D9GP3", "F8SWGP4", "F8SWGP5", "A4GP6", "A5GP3Km", "DA8GP1", "DA8GP2", "DA8GP3", "DF8SWGP4" };

    setNightMats(false);
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null)
    {
      this.gun[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_MGUN02");
      this.gun[2] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON03");
      this.gun[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON04");
      this.gun[4] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON01");
      this.gun[5] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getGunByHookName("_CANNON02");
    }
    if (this.bomb[0] == null)
    {
      for (int i = 0; i < this.bomb.length; i++) {
        this.bomb[i] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).getBulletEmitterByHookName("_ExternalBomb0" + (i + 1));
      }
      this.t1 = Time.current();
    }
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.43F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Glass", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -0.33F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RearArmorPlate", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleALT", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, -180.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleManPress", -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.getNum() > 1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleMP88_L", -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleMP88_R", -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.getNum() > 1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleRPM88_L", -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleRPM88_R", -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleFuel", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 4.0F), fuelScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleOilTemp", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 3.0F), oilTempScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleFuelPress", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    float f;
    if (aircraft().isFMTrackMirror())
    {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    }
    else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, 20.0F, -20.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHTurn", f, 0.0F, 0.0F);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) == 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHBank", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -11.0F, 11.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getKren());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage(), -45.0F, 45.0F, 12.0F, -12.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleCD", this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RepeaterOuter", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("RepeaterPlane", interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBSmall", -105.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBLarge", -270.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.getNum() > 1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBS88_L", -105.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBL88_L", -270.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBS88_R", -105.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHBL88_R", -270.0F + (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x1) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x4) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) == 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleTrimmung", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getTrimElevatorControl() * 25.0F, 0.0F, 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleHClock", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleMClock", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("NeedleSClock", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    }
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
    if (this.gun[4] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[4].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG151_L", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[5] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[5].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("RC_MG151_R", Cockpit.xyz, Cockpit.ypr);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampDrop", ((TypeDockable)aircraft()).typeDockableIsDocked());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("IgnitionSwitch", 24.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Revi16Tinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.ElevatorControl) * 20.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("SteeringHatUD", 30.0F * this.pictElev, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("SteeringHatLR", 30.0F * this.pictAiler, 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.WeaponControl[1] != 0 ? -0.004F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("SecTrigger", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.ypr[0] = (interp(this.setNew.throttle0, this.setOld.throttle0, paramFloat) * 34.0F * 0.91F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ThrottleQuad", Cockpit.xyz, Cockpit.ypr);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.getNum() > 1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ju88TQ_L", 0.0F, -30.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ju88TQ_R", 0.0F, -30.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F);
    }
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
      this.light1.light.setEmit(0.0012F, 0.75F);
      this.light2.light.setEmit(0.0012F, 0.75F);
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
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x1) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x4) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x10) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP1", "DA8GP1");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP1_night", "DA8GP1_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("A8GP4", "DA8GP4");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleManPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleRPM", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RepeaterOuter", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RepeaterPlane", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleHBLarge", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleHBSmall", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleFuel", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("A8GP2", "DA8GP2");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("A8GP2_night", "DA8GP2_night");
      resetYPRmodifier();
      Cockpit.xyz[0] = 0.0F;
      Cockpit.xyz[1] = 0.003F;
      Cockpit.xyz[2] = 0.012F;
      Cockpit.ypr[0] = -3.0F;
      Cockpit.ypr[1] = -3.0F;
      Cockpit.ypr[2] = 9.0F;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("IPCentral", Cockpit.xyz, Cockpit.ypr);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHCyl", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHBar", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAHTurn", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleFuelPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleOilPress", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleOilTemp", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x8) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x20) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP3", "DA8GP3");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("D9GP3_night", "DA8GP3_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleKMH", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleCD", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAlt", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("NeedleAltKM Kill", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
    }
    retoggleLight();
  }

  static
  {
    Property.set(CockpitFW_190F8MSTL.class, "normZN", 0.72F);
    Property.set(CockpitFW_190F8MSTL.class, "gsZN", 0.66F);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitFW_190F8MSTL.access$002(CockpitFW_190F8MSTL.this, CockpitFW_190F8MSTL.this.setOld);
        CockpitFW_190F8MSTL.access$102(CockpitFW_190F8MSTL.this, CockpitFW_190F8MSTL.this.setNew);
        CockpitFW_190F8MSTL.access$202(CockpitFW_190F8MSTL.this, CockpitFW_190F8MSTL.this.setTmp);
        CockpitFW_190F8MSTL.this.setNew.altimeter = CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (CockpitFW_190F8MSTL.this.cockpitDimControl)
        {
          if (CockpitFW_190F8MSTL.this.setNew.dimPosition > 0.0F)
            CockpitFW_190F8MSTL.this.setNew.dimPosition = (CockpitFW_190F8MSTL.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitFW_190F8MSTL.this.setNew.dimPosition < 1.0F)
          CockpitFW_190F8MSTL.this.setNew.dimPosition = (CockpitFW_190F8MSTL.this.setOld.dimPosition + 0.05F);
        CockpitFW_190F8MSTL.this.setNew.throttle0 = (0.9F * CockpitFW_190F8MSTL.this.setOld.throttle0 + 0.1F * CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlThrottle());
        if (CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.getNum() > 1)
        {
          CockpitFW_190F8MSTL.this.setNew.throttle1 = (0.9F * CockpitFW_190F8MSTL.this.setOld.throttle1 + 0.1F * CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[1].getControlThrottle());
          CockpitFW_190F8MSTL.this.setNew.throttle2 = (0.9F * CockpitFW_190F8MSTL.this.setOld.throttle2 + 0.1F * CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[2].getControlThrottle());
        }
        CockpitFW_190F8MSTL.this.setNew.vspeed = ((499.0F * CockpitFW_190F8MSTL.this.setOld.vspeed + CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 500.0F);
        CockpitFW_190F8MSTL.this.setNew.azimuth = CockpitFW_190F8MSTL.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
        if ((CockpitFW_190F8MSTL.this.setOld.azimuth > 270.0F) && (CockpitFW_190F8MSTL.this.setNew.azimuth < 90.0F))
          CockpitFW_190F8MSTL.this.setOld.azimuth -= 360.0F;
        if ((CockpitFW_190F8MSTL.this.setOld.azimuth < 90.0F) && (CockpitFW_190F8MSTL.this.setNew.azimuth > 270.0F))
          CockpitFW_190F8MSTL.this.setOld.azimuth += 360.0F;
        CockpitFW_190F8MSTL.this.setNew.waypointAzimuth = ((10.0F * CockpitFW_190F8MSTL.this.setOld.waypointAzimuth + (CockpitFW_190F8MSTL.this.waypointAzimuth() - CockpitFW_190F8MSTL.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
      }
      return true;
    }

    Interpolater()
    {
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle0;
    float throttle1;
    float throttle2;
    float dimPosition;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    private final CockpitFW_190F8MSTL this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitFW_190F8MSTL.1 arg2)
    {
      this();
    }
  }
}