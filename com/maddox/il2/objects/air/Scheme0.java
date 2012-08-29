package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.ManString;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public abstract class Scheme0 extends Aircraft
{
  protected ActorSimpleMesh towString;
  private static Orient o = new Orient();
  protected static Vector3d v = new Vector3d();
  protected Vector3d traktor = new Vector3d();
  private Vector3f offsetReference = new Vector3f();
  protected boolean bGliderSetUp = false;
  protected boolean bHasCart = true;
  protected boolean bHasBoosters = true;
  protected float towStringCutFactor;
  protected long boosterFireOutTime;
  protected long fireOutTime = 0L;
  protected float boostThrust = 0.0F;
  protected float stringLength = 0.0F;
  protected float stringKx = 0.0F;
  private FlightModel traktorFM;
  private FlightModel ploughFM;
  private boolean bAttached = false;

  private static Vector3d tmpv = new Vector3d();

  public void hitProp(int paramInt1, int paramInt2, Actor paramActor)
  {
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  public void setGliderToTraktor()
  {
    if (!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) {
      this.bGliderSetUp = true;
      return;
    }

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() == null) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() != null) && (Actor.isValid(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget())) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom() instanceof Aircraft)))
    {
      Actor localActor = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTargetActorRandom();
      FlightModel localFlightModel = ((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (localFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double == 0.0D) return;

      double d = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
      if (d < 2.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double = (Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H + 0.0D);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll());
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.wrap();
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(26);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
      } else {
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(24);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(localFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      }

      Maneuver localManeuver1 = (Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      Maneuver localManeuver2 = (Maneuver)localFlightModel;
      if (localManeuver1.Group != null) localManeuver1.Group.delAircraft(this);
      if (localManeuver2.Group != null) localManeuver2.Group.addAircraft(this);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = localFlightModel;

      if (((localActor instanceof HE_111Z)) || ((localActor instanceof LI_2))) {
        ((ActorHMesh)localActor).hierMesh().chunkVisible("Cart_D0", true);
      }

      this.traktor.set(-3.0D, 0.0D, 0.0D);
      Hook localHook = null;
      String str = "";
      if (aircNumber() > 1) {
        if (((localActor instanceof PE_8)) && (hierMesh() != null) && (hierMesh().visibilityR() < 11.0F)) {
          switch (aircIndex()) {
          case 0:
            str = "";
            this.stringLength = 70.0F;

            break;
          case 1:
            str = "02";
            this.stringLength = 150.0F;

            break;
          case 2:
            str = "01";
            this.stringLength = 220.0F;

            break;
          case 3:
            str = "ANameYouCanNeverFind";
          }

        }
        else if (aircIndex() > 0) str = "ANameYouCanNeverFind";
      }
      try
      {
        localHook = localActor.findHook("_TailHook" + str); } catch (Exception localException) {
      }
      if (localHook != null)
      {
        Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        localHook.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
        this.traktor.jdField_x_of_type_Double = localLoc.getPoint().jdField_x_of_type_Double;
        this.traktor.jdField_y_of_type_Double = localLoc.getPoint().jdField_y_of_type_Double;
        this.traktor.jdField_z_of_type_Double = localLoc.getPoint().jdField_z_of_type_Double;

        Vector3f localVector3f = new Vector3f();
        localVector3f.set((float)this.traktor.jdField_x_of_type_Double - this.stringLength, (float)this.traktor.jdField_y_of_type_Double, (float)this.traktor.jdField_z_of_type_Double);
        localFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(localVector3f);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(localFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d); this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double += localVector3f.x; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double += localVector3f.y; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double += localVector3f.z;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(localFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

        d = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
        if (d < 2.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double = (Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H + 0.0D);
          debugprintln("FM.z=" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double + " Land.z=" + Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) + " Gear.h=" + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll());
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.wrap();
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(26);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        } else {
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(24);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(localFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        }

        this.offsetReference.set(this.stringLength - (float)this.traktor.jdField_x_of_type_Double, (float)(-this.traktor.jdField_y_of_type_Double), (float)(-this.traktor.jdField_z_of_type_Double));
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d.set(this.offsetReference);
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).followOffset.set(this.offsetReference);
      }
      else {
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_task(3);
        debugprintln("Glider attached to a vessel of unsupported type.. Glider autolands..");
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(49);
      }
    } else {
      ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_task(3);
      debugprintln("Incorrect glider way setup.. Glider autolands..");
      ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(49);
    }
    this.bGliderSetUp = true;
    this.bAttached = true;
  }

  public void onAircraftLoaded()
  {
    this.towString = new ActorSimpleMesh(Property.stringValue(getClass(), "gliderString", "3DO/Arms/TowCable/mono.sim"));
    this.towString.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this, findHook("_TowHook"), false);
    this.towString.drawing(false);

    this.boostThrust = Property.floatValue(getClass(), "gliderBoostThrust", 0.0F);
    this.stringLength = Property.floatValue(getClass(), "gliderStringLength", 140.0F);
    this.stringKx = Property.floatValue(getClass(), "gliderStringKx", 88.0F);

    this.towStringCutFactor = Property.floatValue(getClass(), "gliderStringFactor", 1.89F);
    this.bHasCart = Property.containsValue(getClass(), "gliderCart");
    this.bHasBoosters = Property.containsValue(getClass(), "gliderBoosters");
    this.boosterFireOutTime = ()(Property.floatValue(getClass(), "gliderFireOut", 0.0F) * 1000.0F);
    super.onAircraftLoaded();
  }

  public void destroy()
  {
    if ((Actor.isValid(this.towString)) && (!this.towString.isDestroyed())) this.towString.destroy();
    super.destroy();
  }

  public void update(float paramFloat)
  {
    if (!this.bGliderSetUp) {
      setGliderToTraktor();
      this.ploughFM = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    }

    super.update(paramFloat);

    if (!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) return;

    if (((((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 24) || (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 53) || (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 26) || (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 64) || (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 0)) && (this.bAttached))
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider))) {
        this.traktorFM = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
        this.ploughFM = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Offset_of_type_ComMaddoxJGPVector3d.set(this.offsetReference);

        this.towString.drawing(true);

        v.set(this.traktor); this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(v); v.add(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

        v.jdField_x_of_type_Double -= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * Time.tickConstLenFs();
        v.jdField_y_of_type_Double -= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * Time.tickConstLenFs();
        v.jdField_z_of_type_Double -= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * Time.tickConstLenFs();
        v.sub(this.towString.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
        this.towString.mesh().setScaleXYZ((float)v.length(), 1.0F, 1.0F);
        if (v.length() > this.stringLength * this.towStringCutFactor) {
          debugprintln("Glider string overstressed (" + (int)v.length() + "metres) and tears off..");
          ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(49);
        }

        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(v);
        o.setAT0(v);
        this.towString.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setRel(o);

        v.normalize();
        if (v.jdField_x_of_type_Double < 0.9961950182914734D)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment((float)v.jdField_y_of_type_Double, (float)v.jdField_z_of_type_Double, 0.0F);
        }

        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].initializeTowString(force());

        if (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 26) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.setYPR(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getPitch(), 0.0F);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.wrap();
          if ((this.fireOutTime == 0L) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > 40.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl > 0.99F)) {
            this.fireOutTime = (Time.current() + this.boosterFireOutTime);
            debugprintln("Firing up take-off rockets!.");
            doFireBoosters();

            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setGliderBoostOn();
          }
        }
      }
      else {
        this.towString.drawing(false);
        this.bAttached = false;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].initializeTowString(0.0F);
      }
    }
    else {
      if ((((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 21) && 
        (v.length() < this.stringLength * this.towStringCutFactor)) {
        ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(24);
        ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_task(2);
        return;
      }

      this.towString.drawing(false);
      this.bAttached = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].initializeTowString(0.0F);
      if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isReadyToDie()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToDieSoftly(true);
        debugprintln("Craft out of control (MAN=" + ManString.name(((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver()) + "), abandoning flight.");
        this.traktorFM = null;
        if (((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).get_maneuver() == 44) ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).bombsOut = true;
      }

    }

    if ((this.bHasCart) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 45.0D)) {
      debugprintln("Detaching take-off cart!.");
      doCutCart();

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setGliderCutCart();
      this.bHasCart = false;
    }
    if ((this.bHasBoosters) && 
      (Time.current() > this.fireOutTime + 6000L) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 202.0D)) {
      debugprintln("Dropping used boosters!.");
      doCutBoosters();

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setGliderBoostOff();
      this.bHasBoosters = false;
    }
  }

  private float force()
  {
    float f = 0.0F;
    if (Time.current() < this.fireOutTime) f += this.boostThrust;
    if (this.traktor == null) return 0.0F;
    tmpv.sub(this.traktorFM.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.ploughFM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    this.ploughFM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpv);
    if (tmpv.jdField_x_of_type_Double > this.stringLength) {
      f += ((float)tmpv.jdField_x_of_type_Double - this.stringLength) * ((float)tmpv.jdField_x_of_type_Double - this.stringLength) * this.stringKx;
    }
    return f;
  }

  public void doCutCart()
  {
  }

  public void doCutBoosters()
  {
  }

  public void doFireBoosters()
  {
  }
}