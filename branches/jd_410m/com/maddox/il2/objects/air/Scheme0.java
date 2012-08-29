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

public abstract class Scheme0 extends AircraftLH
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
    if (!(this.FM instanceof Pilot)) {
      this.bGliderSetUp = true;
      return;
    }

    if (this.FM.AP.way.curr().getTarget() == null) this.FM.AP.way.next();
    if ((this.FM.AP.way.curr().getTarget() != null) && (Actor.isValid(this.FM.AP.way.curr().getTarget())) && ((this.FM.AP.way.curr().getTargetActorRandom() instanceof Aircraft)))
    {
      Actor localActor = this.FM.AP.way.curr().getTargetActorRandom();
      FlightModel localFlightModel = ((Aircraft)localActor).FM;
      if (localFlightModel.Loc.x == 0.0D) return;

      double d = this.FM.Loc.z - Engine.land().HQ(this.FM.Loc.x, this.FM.Loc.y);
      if (d < 2.0F * this.FM.Gears.H) {
        this.FM.Loc.z = (Engine.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y) + this.FM.Gears.H + 0.0D);
        this.FM.Or.set(this.FM.Or.getYaw(), this.FM.Gears.Pitch, this.FM.Or.getRoll());
        this.FM.Or.wrap();
        ((Maneuver)this.FM).set_maneuver(26);
        this.FM.Vwld.set(0.0D, 0.0D, 0.0D);
      } else {
        ((Maneuver)this.FM).set_maneuver(24);
        this.FM.Vwld.set(localFlightModel.Vwld);
      }

      Maneuver localManeuver1 = (Maneuver)this.FM;
      Maneuver localManeuver2 = (Maneuver)localFlightModel;
      if (localManeuver1.Group != null) localManeuver1.Group.delAircraft(this);
      if (localManeuver2.Group != null) localManeuver2.Group.addAircraft(this);
      this.FM.Leader = localFlightModel;

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
        this.traktor.x = localLoc.getPoint().x;
        this.traktor.y = localLoc.getPoint().y;
        this.traktor.z = localLoc.getPoint().z;

        Vector3f localVector3f = new Vector3f();
        localVector3f.set((float)this.traktor.x - this.stringLength, (float)this.traktor.y, (float)this.traktor.z);
        localFlightModel.Or.transform(localVector3f);
        this.FM.Loc.set(localFlightModel.Loc); this.FM.Loc.x += localVector3f.x; this.FM.Loc.y += localVector3f.y; this.FM.Loc.z += localVector3f.z;
        this.FM.Or.set(localFlightModel.Or);
        this.FM.actor.pos.setAbs(this.FM.Loc, this.FM.Or);
        this.FM.actor.pos.reset();

        d = this.FM.Loc.z - Engine.land().HQ(this.FM.Loc.x, this.FM.Loc.y);
        if (d < 2.0F * this.FM.Gears.H) {
          this.FM.Loc.z = (Engine.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y) + this.FM.Gears.H + 0.0D);
          debugprintln("FM.z=" + this.FM.Loc.z + " Land.z=" + Engine.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y) + " Gear.h=" + this.FM.Gears.H);
          this.FM.Or.set(this.FM.Or.getYaw(), this.FM.Gears.Pitch, this.FM.Or.getRoll());
          this.FM.Or.wrap();
          ((Maneuver)this.FM).set_maneuver(26);
          this.FM.Vwld.set(0.0D, 0.0D, 0.0D);
          this.FM.actor.pos.setAbs(this.FM.Loc, this.FM.Or);
          this.FM.actor.pos.reset();
        } else {
          ((Maneuver)this.FM).set_maneuver(24);
          this.FM.Vwld.set(localFlightModel.Vwld);
        }

        this.offsetReference.set(this.stringLength - (float)this.traktor.x, (float)(-this.traktor.y), (float)(-this.traktor.z));
        this.FM.Offset.set(this.offsetReference);
        ((Maneuver)this.FM).followOffset.set(this.offsetReference);
      }
      else {
        ((Maneuver)this.FM).set_task(3);
        debugprintln("Glider attached to a vessel of unsupported type.. Glider autolands..");
        ((Maneuver)this.FM).set_maneuver(49);
      }
    } else {
      ((Maneuver)this.FM).set_task(3);
      debugprintln("Incorrect glider way setup.. Glider autolands..");
      ((Maneuver)this.FM).set_maneuver(49);
    }
    this.bGliderSetUp = true;
    this.bAttached = true;
  }

  public void onAircraftLoaded()
  {
    this.towString = new ActorSimpleMesh(Property.stringValue(getClass(), "gliderString", "3DO/Arms/TowCable/mono.sim"));
    this.towString.pos.setBase(this, findHook("_TowHook"), false);
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
      this.ploughFM = this.FM;
    }

    super.update(paramFloat);

    if (!(this.FM instanceof Pilot)) return;

    if (((((Maneuver)this.FM).get_maneuver() == 24) || (((Maneuver)this.FM).get_maneuver() == 53) || (((Maneuver)this.FM).get_maneuver() == 26) || (((Maneuver)this.FM).get_maneuver() == 64) || (((Maneuver)this.FM).get_maneuver() == 0)) && (this.bAttached))
    {
      if ((this.FM.Leader != null) && (!(this.FM.Leader.actor instanceof TypeGlider))) {
        this.traktorFM = this.FM.Leader;
        this.ploughFM = this.FM;
        this.FM.Offset.set(this.offsetReference);

        this.towString.drawing(true);

        v.set(this.traktor); this.FM.Leader.Or.transform(v); v.add(this.FM.Leader.Loc);

        v.x -= this.FM.Leader.Vwld.x * Time.tickConstLenFs();
        v.y -= this.FM.Leader.Vwld.y * Time.tickConstLenFs();
        v.z -= this.FM.Leader.Vwld.z * Time.tickConstLenFs();
        v.sub(this.towString.pos.getAbsPoint());
        this.towString.mesh().setScaleXYZ((float)v.length(), 1.0F, 1.0F);
        if (v.length() > this.stringLength * this.towStringCutFactor) {
          debugprintln("Glider string overstressed (" + (int)v.length() + "metres) and tears off..");
          ((Maneuver)this.FM).set_maneuver(49);
        }

        this.FM.Or.transformInv(v);
        o.setAT0(v);
        this.towString.pos.setRel(o);

        v.normalize();
        if (v.x < 0.9961950182914734D)
        {
          this.FM.Or.increment((float)v.y, (float)v.z, 0.0F);
        }

        this.FM.EI.engines[0].initializeTowString(force());

        if (((Maneuver)this.FM).get_maneuver() == 26) {
          this.FM.Or.setYPR(this.FM.Leader.Or.getYaw(), this.FM.Or.getPitch(), 0.0F);
          this.FM.Or.wrap();
          if ((this.fireOutTime == 0L) && (this.FM.Leader.getSpeedKMH() > 40.0F) && (this.FM.Leader.CT.PowerControl > 0.99F)) {
            this.fireOutTime = (Time.current() + this.boosterFireOutTime);
            debugprintln("Firing up take-off rockets!.");
            doFireBoosters();

            this.FM.AS.setGliderBoostOn();
          }
        }
      }
      else {
        this.towString.drawing(false);
        this.bAttached = false;
        this.FM.EI.engines[0].initializeTowString(0.0F);
      }
    }
    else {
      if ((((Maneuver)this.FM).get_maneuver() == 21) && 
        (v.length() < this.stringLength * this.towStringCutFactor)) {
        ((Maneuver)this.FM).set_maneuver(24);
        ((Pilot)this.FM).set_task(2);
        return;
      }

      this.towString.drawing(false);
      this.bAttached = false;
      this.FM.EI.engines[0].initializeTowString(0.0F);
      if (!this.FM.isReadyToDie()) {
        this.FM.setReadyToDieSoftly(true);
        debugprintln("Craft out of control (MAN=" + ManString.name(((Maneuver)this.FM).get_maneuver()) + "), abandoning flight.");
        this.traktorFM = null;
        if (((Maneuver)this.FM).get_maneuver() == 44) ((Maneuver)this.FM).bombsOut = true;
      }

    }

    if ((this.bHasCart) && (this.FM.Loc.z - Engine.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y) > 45.0D)) {
      debugprintln("Detaching take-off cart!.");
      doCutCart();

      this.FM.AS.setGliderCutCart();
      this.bHasCart = false;
    }
    if ((this.bHasBoosters) && 
      (Time.current() > this.fireOutTime + 6000L) && (this.FM.Loc.z - Engine.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y) > 202.0D)) {
      debugprintln("Dropping used boosters!.");
      doCutBoosters();

      this.FM.AS.setGliderBoostOff();
      this.bHasBoosters = false;
    }
  }

  private float force()
  {
    float f = 0.0F;
    if (Time.current() < this.fireOutTime) f += this.boostThrust;
    if (this.traktor == null) return 0.0F;
    tmpv.sub(this.traktorFM.Loc, this.ploughFM.Loc);
    this.ploughFM.Or.transformInv(tmpv);
    if (tmpv.x > this.stringLength) {
      f += ((float)tmpv.x - this.stringLength) * ((float)tmpv.x - this.stringLength) * this.stringKx;
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