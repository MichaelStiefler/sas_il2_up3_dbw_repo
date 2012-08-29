package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitFw189 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private LightPointActor light1;
  private Loc tmpLoc;
  private Point3d tmpP;
  private Vector3d tmpV;
  private float pictAiler;
  private float pictElev;
  private static final float[] speedometerScale = { 0.0F, 0.1F, 19.0F, 37.25F, 63.5F, 91.5F, 112.0F, 135.5F, 159.5F, 186.5F, 213.0F, 238.0F, 264.0F, 289.0F, 314.5F, 339.5F, 359.5F, 360.0F, 360.0F, 360.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 54.0F, 111.0F, 171.5F, 229.5F, 282.5F, 334.0F, 342.5F, 342.5F };

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
    {
      return 0.0F;
    }

    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(this.tmpV.y, this.tmpV.x));
  }

  public CockpitFw189()
  {
    super("3DO/Cockpit/Fw189/CockpitFw189.him", "he111");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.tmpLoc = new Loc();
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(218.0F, 143.0F, 128.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.cockpitNightMats = new String[] { "clocks1", "clocks2", "clocks2DMG", "clocks3", "clocks3DMG", "clocks4", "clocks5", "clocks6" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("CF_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("CF_D0", true);
    super.doFocusLeave();
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.isTick(44, 0))
    {
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) == 0)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLRed1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isAnyDamaged()));
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRRed1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isAnyDamaged()));
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLGreen1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear));
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRGreen1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
      }
      else {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLRed1", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRRed1", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLGreen1", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRGreen1", false);
      }
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFuelWarning1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 50.0F);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFuelWarning2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 50.0F);
      }
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl));
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() > 0.0F)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPedalL", 0.0F, 0.0F, -10.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPedalR", 0.0F, 0.0F, -45.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    }
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPedalL", 0.0F, 0.0F, -45.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPedalR", 0.0F, 0.0F, -10.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilFlap1", 0.0F, 0.0F, -50.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilFlap2", 0.0F, 0.0F, -50.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMix1", 0.0F, 0.0F, -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMix2", 0.0F, 0.0F, -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlMix());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFlaps1", 0.0F, 0.0F, -45.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() >= 0.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPitch1", 0.0F, 0.0F, -65.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp());
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlProp() >= 0.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPitch2", 0.0F, 0.0F, -65.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlProp());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zThrottle1", 0.0F, 0.0F, -33.599998F * interp(this.setNew.throttlel, this.setOld.throttlel, paramFloat));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zThrottle2", 0.0F, 0.0F, -33.599998F * interp(this.setNew.throttler, this.setOld.throttler, paramFloat));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCompressor1", 0.0F, 0.0F, -25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlCompressor());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCompressor2", 0.0F, 0.0F, -25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlCompressor());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMinute", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSecond", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAH1", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAH2", 0.0F, 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -30.0F, 30.0F, -6.5F, 6.5F));
    float f1;
    if (aircraft().isFMTrackMirror())
    {
      f1 = aircraft().fmTrack().getCockpitAzimuthSpeed();
    }
    else {
      f1 = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -3.0F, 3.0F, 30.0F, -30.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f1);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurnBank", f1, 0.0F, 0.0F);
    float f2 = getBall(4.5D);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zBall", cvt(f2, -4.0F, 4.0F, -8.0F, 8.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zBall2", cvt(f2, -4.5F, 4.5F, -9.0F, 9.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zVSI", cvt(this.setNew.vspeed, -15.0F, 15.0F, -160.0F, 160.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 400.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRepeater", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCompass", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zNavP", waypointAzimuth() - this.setNew.azimuth, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zBlip1", 0.0F, 3.0F * this.setNew.blindDotY, 3.0F * this.setNew.blindDotX);
    if ((Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 20.0F) && (Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) < 20.0F))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMagnetic", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPM1", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPM2", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zBoost1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zBoost2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilTemp1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 130.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilTemp2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 100.0F, 0.0F, 130.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCoolant1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 100.0F, 0.0F, 126.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCoolant2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tWaterOut, 0.0F, 100.0F, 0.0F, 126.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP1-1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP1-2", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP1-3", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP1-4", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP2-1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP2-2", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP2-3", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOFP2-4", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFuel1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / 0.72F, 0.0F, 330.0F, 0.0F, 140.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFuel2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / 0.72F, 0.0F, 330.0F, 0.0F, 140.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zExtT", cvt(Atmosphere.temperature((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double), 223.09F, 323.09F, -145.0F, 145.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPatin", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    float f3 = (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin());
    f3 = (int)(-f3 / 0.2F) * 0.2F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zProp1-1", f3 * 60.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zProp1-2", f3 * 5.0F, 0.0F, 0.0F);
    f3 = (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin());
    f3 = (int)(-f3 / 0.2F) * 0.2F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zProp2-1", f3 * 60.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zProp2-2", f3 * 5.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFlapsIL", 145.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFlapsIR", 145.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap(), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesL_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelL_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelL_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zVSI", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zBlip1", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesL_D2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelFloat_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelFloat_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zProp1-1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zProp1-2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zProp2-1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zProp2-2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFlapsIL", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFlapsIR", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelR_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zRPM1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zBoost2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOilTemp2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zCoolant1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOFP1-1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOFP1-2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFlapsIR", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesR_D2", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesF_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelT_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelT_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zFuel2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOFP1-3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOFP1-4", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOil_D1", true);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.0032F, 7.2F);
      setNightMats(true);
    }
    else {
      this.light1.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private class Variables
  {
    float altimeter;
    float throttlel;
    float throttler;
    float azimuth;
    float waypointAzimuth;
    float blindDotX;
    float blindDotY;
    float vspeed;
    private final CockpitFw189 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitFw189.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      CockpitFw189.access$002(CockpitFw189.this, CockpitFw189.this.setOld);
      CockpitFw189.access$102(CockpitFw189.this, CockpitFw189.this.setNew);
      CockpitFw189.access$202(CockpitFw189.this, CockpitFw189.this.setTmp);
      CockpitFw189.this.setNew.altimeter = CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
      CockpitFw189.this.setNew.throttlel = ((10.0F * CockpitFw189.this.setOld.throttlel + CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle()) / 11.0F);
      CockpitFw189.this.setNew.throttler = ((10.0F * CockpitFw189.this.setOld.throttler + CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle()) / 11.0F);
      CockpitFw189.this.setNew.azimuth = CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
      if ((CockpitFw189.this.setOld.azimuth > 270.0F) && (CockpitFw189.this.setNew.azimuth < 90.0F))
        CockpitFw189.this.setOld.azimuth -= 360.0F;
      if ((CockpitFw189.this.setOld.azimuth < 90.0F) && (CockpitFw189.this.setNew.azimuth > 270.0F))
        CockpitFw189.this.setOld.azimuth += 360.0F;
      CockpitFw189.this.setNew.waypointAzimuth = ((10.0F * CockpitFw189.this.setOld.waypointAzimuth + (CockpitFw189.this.waypointAzimuth() - CockpitFw189.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
      CockpitFw189.this.setNew.vspeed = ((499.0F * CockpitFw189.this.setOld.vspeed + CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 500.0F);
      if (CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.isTick(12, 0))
      {
        Airport localAirport = Airport.nearest(CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, -1, 1);
        if ((localAirport != null) && (localAirport.nearestRunway(CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, CockpitFw189.this.tmpLoc)))
        {
          CockpitFw189.this.tmpLoc.transformInv(CockpitFw189.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, CockpitFw189.this.tmpP);
          CockpitFw189.this.tmpV.jdField_x_of_type_Double = CockpitFw189.this.tmpP.jdField_x_of_type_Double;
          CockpitFw189.this.tmpV.jdField_y_of_type_Double = CockpitFw189.this.tmpP.jdField_y_of_type_Double;
          CockpitFw189.this.tmpV.jdField_z_of_type_Double = CockpitFw189.this.tmpP.jdField_z_of_type_Double;
          CockpitFw189.this.tmpV.normalize();
          CockpitFw189.this.tmpV.jdField_z_of_type_Double -= 0.03489900007843971D;
          if (CockpitFw189.this.tmpV.jdField_x_of_type_Double > 0.9599999785423279D)
          {
            if (CockpitFw189.this.tmpV.jdField_y_of_type_Double > 0.0D)
              CockpitFw189.this.setNew.blindDotX = (1.889F * (float)Math.sqrt(CockpitFw189.this.tmpV.jdField_y_of_type_Double));
            else
              CockpitFw189.this.setNew.blindDotX = (-1.889F * (float)Math.sqrt(-CockpitFw189.this.tmpV.jdField_y_of_type_Double));
            if (CockpitFw189.this.tmpV.jdField_z_of_type_Double > 0.0D)
              CockpitFw189.this.setNew.blindDotY = (1.889F * (float)Math.sqrt(CockpitFw189.this.tmpV.jdField_z_of_type_Double));
            else
              CockpitFw189.this.setNew.blindDotY = (-1.889F * (float)Math.sqrt(-CockpitFw189.this.tmpV.jdField_z_of_type_Double));
          }
          else {
            CockpitFw189.this.setNew.blindDotX = 0.0F;
            CockpitFw189.this.setNew.blindDotY = 0.0F;
          }
        }
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}