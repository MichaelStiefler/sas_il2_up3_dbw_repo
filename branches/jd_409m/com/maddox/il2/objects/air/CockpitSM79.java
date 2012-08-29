package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitSM79 extends CockpitPilot
{
  private LightPointActor light1;
  private LightPointActor light2;
  private boolean firstEnter = true;

  private static final float[] speedometerScale = { 0.0F, 10.0F, 20.0F, 30.0F, 50.0F, 68.0F, 88.0F, 109.0F, 126.0F, 142.0F, 159.0F, 176.0F, 190.0F, 206.0F, 220.0F, 238.0F, 253.0F, 270.0F, 285.0F, 300.0F, 312.0F, 325.0F, 337.0F, 350.0F, 360.0F };

  private static final float[] oilTempScale = { 0.0F, 26.0F, 54.0F, 95.0F, 154.0F, 244.0F, 359.0F };
  private boolean bNeedSetUp;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictManf1;
  private float pictManf2;
  private Point3d tmpP;
  private Vector3d tmpV;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = false;

      aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", false);

      aircraft().hierMesh().chunkVisible("fakePilot3_D0", true);

      if (!((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot3Killed)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TailGunner", true);
      }

      if (this.firstEnter)
      {
        if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
        {
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Torpsight_Base", true);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Torpsight_Knob", true);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Torpsight_Rot", true);
        }
        this.firstEnter = false;
      }

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door1_Int", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door2_Int", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door3_Int", true);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);
      aircraft().hierMesh().chunkVisible("WingWireL_D0", false);

      aircraft().hierMesh().chunkVisible("Interior1_D0", false);

      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = true;

      boolean bool = aircraft().isChunkAnyDamageVisible("Tail1_D");

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TailGunner", false);
      if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(2))
      {
        aircraft().hierMesh().chunkVisible("Pilot3_D0", !((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot3Killed);
        aircraft().hierMesh().chunkVisible("Pilot3_D1", ((SM79)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPilot3Killed);
      }

      if (bool)
      {
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));

        aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));
      }

      aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", bool);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);
      aircraft().hierMesh().chunkVisible("WingWireL_D0", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door1_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door2_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_Door3_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_open_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int", false);

      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

      super.doFocusLeave();
    }
  }

  public void toggleDim() {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  protected void mydebugcockpit(String paramString)
  {
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

    for (float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x)); f <= -180.0F; f += 180.0F);
    while (f > 180.0F) f -= 180.0F;
    return f;
  }

  public CockpitSM79()
  {
    super("3DO/Cockpit/SM79Pilot/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictManf1 = 1.0F;
    this.pictManf2 = 1.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();

    HookNamed localHookNamed1 = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK1");
    Loc localLoc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed1.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc1);

    this.light1 = new LightPointActor(new LightPoint(), localLoc1.getPoint());
    this.light1.light.setColor(109.0F, 99.0F, 90.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    HookNamed localHookNamed2 = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK2");
    Loc localLoc2 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed2.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc2);

    this.light2 = new LightPointActor(new LightPoint(), localLoc2.getPoint());
    this.light2.light.setColor(109.0F, 99.0F, 90.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "SM79_gauges_1", "SM79_gauges_2", "SM79_gauges_3", "SM79_gauges_1_dmg", "Ita_Needles" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    loadBuzzerFX();
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (aircraft().jdField_thisWeaponsName_of_type_JavaLangString.startsWith("1x"))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Torpsight_Rot", 0.0F, ((SM79)aircraft()).fSightCurSideslip, 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Torpsight_Knob", 0.0F, 2.0F * ((SM79)aircraft()).fSightCurSideslip, 0.0F);
    }

    aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);

    float f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor();
    if (f1 < 0.99F)
    {
      if (!this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.isChunkVisible("Tur2_DoorR_Int"))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_open_Int", false);
      }
      f2 = 13.8F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -f2, 0.0F);
      f2 = 8.8F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -f2, 0.0F);
      f2 = 3.1F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -f2, 0.0F);
      f2 = 14.0F * f1;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorL_Int", 0.0F, -f2, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_DoorR_Int", 0.0F, f2, 0.0F);
    }
    else
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorR_open_Int", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_Int", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tur2_DoorL_open_Int", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -13.8F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -8.8F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -3.1F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Mirino_02", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZSpeedL", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZSpeedR", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZlampRedL", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getPowerControl() < 0.15F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.99F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZlampRedR", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getPowerControl() < 0.15F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.99F));
    buzzerFX((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getPowerControl() < 0.15F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.99F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZlampGreenL", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getPowerControl() < 0.15F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZlampGreenR", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getPowerControl() < 0.15F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Mplane_LandingGear_L", 0.0F, 0.0F, 10.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Mplane_LandingGear_R", 0.0F, 0.0F, 10.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Mplane_LandingGear_L1", 0.0F, 90.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Mplane_LandingGear_R1", 0.0F, -90.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear(), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zgear", 30.0F * (this.pictGear = 0.89F * this.pictGear + 0.11F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl), 0.0F, 0.0F);

    float f2 = interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat) * 0.036F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zalt1L", 0.0F, f2 * 10.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zalt1R", 0.0F, f2 * 10.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zalt2L", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zalt2R", 0.0F, f2, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZclockHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZclockMin", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    float f3 = 0.104F;

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZRPML", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM() * f3, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZRPMC", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM() * f3, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZRPMR", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getRPM() * f3, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrL", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrC", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrR", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrEL", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrUL", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrEC", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrUC", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrER", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZbarometrUR", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZoilL", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZoilC", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZoilR", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZfuelL", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZfuelC", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZfuelR", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);

    float f4 = this.setNew.vspeed * 18.0F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZclimbL", 0.0F, f4, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZclimbR", 0.0F, f4, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZThrottleL", 0.0F, -49.0F * interp(this.setNew.throttleL, this.setOld.throttleL, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZThrottleC", 0.0F, -49.0F * interp(this.setNew.throttleC, this.setOld.throttleC, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZThrottleR", 0.0F, -49.0F * interp(this.setNew.throttleR, this.setOld.throttleR, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("MixturreL", 0.0F, 35.0F * interp(this.setNew.MixL, this.setOld.MixL, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("MixturreC", 0.0F, 35.0F * interp(this.setNew.MixC, this.setOld.MixC, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("MixturreR", 0.0F, 35.0F * interp(this.setNew.MixR, this.setOld.MixR, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PitchL", 0.0F, -40.0F * interp(this.setNew.propL, this.setOld.propL, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PitchC", 0.0F, -40.0F * interp(this.setNew.propC, this.setOld.propC, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PitchR", 0.0F, -40.0F * interp(this.setNew.propR, this.setOld.propR, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Wheel_PilotL", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 68.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Wheel_PilotR", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 68.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Wheel_Stick", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 8.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_BrkpresL", 0.0F, 7.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_BrkpresR", 0.0F, -7.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake(), 0.0F);

    resetYPRmodifier();
    float f5 = 0.07F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder();
    Cockpit.xyz[1] = f5;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Pedal_LeftL", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Pedal_LeftR", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[1] = (-f5);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Pedal_RightL", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Pedal_RightR", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZmagnetoL", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZmagnetoC", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZmagnetoR", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zdirectional_GiroC", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zdirectional_GiroL", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zdirectional_GiroR", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zbank2L", 0.0F, cvt(this.w.jdField_z_of_type_Float, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zbank1L", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -20.5F, 20.5F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zbank2R", 0.0F, cvt(this.w.jdField_z_of_type_Float, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Zbank1R", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -20.5F, 20.5F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ZAH1R", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.011F, -0.015F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ZcompassR", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAH1L", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.02F, -0.02F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("ZcompassL", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_plane_FuelGaugeL", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 1720.0F, 0.0F, 245.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_plane_FuelGaugeR", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 1720.0F, 0.0F, 245.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ztrim1", 0.0F, cvt(interp(this.setNew.elevTrim, this.setOld.elevTrim, paramFloat), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = (-cvt(interp(this.setNew.elevTrim, this.setOld.elevTrim, paramFloat), -0.5F, 0.5F, -0.057F, 0.058F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Trim_Indicator", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OAT", 0.0F, cvt(Atmosphere.temperature((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 273.14999F, -40.0F, 40.0F, 0.0F, 93.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(2))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TailGunner", true);
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TailGunner", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Line01", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PressL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPMC_dmg", true);
    }

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Line01", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("AltL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPMC_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("OilTempC_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Line01", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ClockL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles1", true);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PressL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("OilTempC_dmg", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Line01", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ClockL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("AltL_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassHoles1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPMC_dmg", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PressL_dmg", true);
    }
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.0032F, 7.2F);
      this.light2.light.setEmit(0.003F, 7.0F);
      setNightMats(true);
    }
    else
    {
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
  }

  private class Variables
  {
    float dimPos;
    float throttleC;
    float throttleR;
    float throttleL;
    float propC;
    float propL;
    float propR;
    float MixC;
    float MixL;
    float MixR;
    float altimeter;
    float elevTrim;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    AnglesFork waypointDeviation;
    private final CockpitSM79 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.dimPos = 0.0F;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDeviation = new AnglesFork();
    }

    Variables(CockpitSM79.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitSM79.access$002(CockpitSM79.this, CockpitSM79.this.setOld);
        CockpitSM79.access$102(CockpitSM79.this, CockpitSM79.this.setNew);
        CockpitSM79.access$202(CockpitSM79.this, CockpitSM79.this.setTmp);

        if (CockpitSM79.this.cockpitDimControl)
        {
          if (CockpitSM79.this.setNew.dimPos < 1.0F)
            CockpitSM79.this.setNew.dimPos = (CockpitSM79.this.setOld.dimPos + 0.03F);
        } else if (CockpitSM79.this.setNew.dimPos > 0.0F) {
          CockpitSM79.this.setNew.dimPos = (CockpitSM79.this.setOld.dimPos - 0.03F);
        }
        CockpitSM79.this.setNew.throttleC = (0.85F * CockpitSM79.this.setOld.throttleC + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.throttleL = (0.85F * CockpitSM79.this.setOld.throttleL + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.throttleR = (0.85F * CockpitSM79.this.setOld.throttleR + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.propC = (0.85F * CockpitSM79.this.setOld.propC + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.propL = (0.85F * CockpitSM79.this.setOld.propL + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.propR = (0.85F * CockpitSM79.this.setOld.propR + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.MixC = (0.85F * CockpitSM79.this.setOld.MixC + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.MixL = (0.85F * CockpitSM79.this.setOld.MixL + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.MixR = (0.85F * CockpitSM79.this.setOld.MixR + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.elevTrim = (0.85F * CockpitSM79.this.setOld.elevTrim + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.getTrimElevatorControl() * 0.15F);
        CockpitSM79.this.setNew.altimeter = CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        float f = CockpitSM79.this.waypointAzimuth();
        CockpitSM79.this.setNew.azimuth.setDeg(CockpitSM79.this.setOld.azimuth.getDeg(1.0F), 90.0F + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
        CockpitSM79.this.setNew.waypointAzimuth.setDeg(CockpitSM79.this.setOld.waypointAzimuth.getDeg(1.0F), f);
        CockpitSM79.this.setNew.waypointDeviation.setDeg(CockpitSM79.this.setOld.waypointDeviation.getDeg(0.1F), f - CockpitSM79.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitSM79.this.setNew.vspeed = ((199.0F * CockpitSM79.this.setOld.vspeed + CockpitSM79.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}