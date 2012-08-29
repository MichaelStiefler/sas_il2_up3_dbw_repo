package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class I_16TYPE5_SPB extends I_16
  implements MsgCollisionRequestListener, TypeTNBFighter, TypeStormovik, TypeDockable
{
  private Actor queen_last = null;
  private long queen_time = 0L;
  private boolean bNeedSetup = true;
  private long dtime = -1L;
  private Actor target_ = null;
  private Actor queen_ = null;
  private int dockport_;
  private boolean bailingOut = false;
  private boolean canopyForward = false;
  private boolean okToJump = false;
  private float flaperonAngle = 0.0F;
  private float aileronsAngle = 0.0F;
  private boolean oneTimeCheckDone = false;
  private boolean sideDoorOpened = false;

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    super.msgCollisionRequest(paramActor, paramArrayOfBoolean);
    if ((this.queen_last != null) && (this.queen_last == paramActor) && ((this.queen_time == 0L) || (Time.current() < this.queen_time + 5000L)))
    {
      paramArrayOfBoolean[0] = false;
    }
    else paramArrayOfBoolean[0] = true;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      this.FM.Gears.hitCentreGear();
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xxtank1")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.3F))
    {
      if (this.FM.AS.astateTankStates[0] == 0) {
        Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");

        this.FM.AS.hitTank(paramShot.initiator, 0, 2);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.75F))
      {
        this.FM.AS.hitTank(paramShot.initiator, 0, 2);
        Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
      }
    }
    else {
      super.hitBone(paramString, paramShot, paramPoint3d);
    }
  }

  public void moveCockpitDoor(float paramFloat) {
    if ((this.bailingOut) && (paramFloat >= 1.0F) && (!this.canopyForward))
    {
      this.canopyForward = true;
      this.FM.CT.forceCockpitDoor(0.0F);
      this.FM.AS.setCockpitDoor(this, 1);
    }
    else if (this.canopyForward)
    {
      hierMesh().chunkSetAngles("Blister2_D0", 0.0F, 160.0F * paramFloat, 0.0F);
      if (paramFloat >= 1.0F)
      {
        this.okToJump = true;
        super.hitDaSilk();
      }
    }
    else
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = (paramFloat * 0.548F);
      hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    }

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void hitDaSilk()
  {
    if (this.okToJump)
    {
      super.hitDaSilk();
    }
    else if ((this.FM.isPlayers()) || (isNetPlayer()))
    {
      if ((this.FM.CT.getCockpitDoor() == 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.okToJump = true;
        this.canopyForward = true;
        super.hitDaSilk();
      }
    }
    else if (!this.FM.AS.isPilotDead(0))
    {
      if ((this.FM.CT.getCockpitDoor() < 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.FM.AS.setCockpitDoor(this, 1);
      }
      else if ((this.FM.CT.getCockpitDoor() == 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.okToJump = true;
        this.canopyForward = true;
        super.hitDaSilk();
      }

    }

    if ((!this.sideDoorOpened) && (this.FM.AS.bIsAboutToBailout) && (!this.FM.AS.isPilotDead(0)))
    {
      this.sideDoorOpened = true;
      this.FM.CT.forceCockpitDoor(0.0F);
      this.FM.AS.setCockpitDoor(this, 1);
    }
  }

  public void moveGear(float paramFloat)
  {
    super.moveGear(paramFloat);

    if (paramFloat > 0.5F)
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.5F, 1.0F, 14.5F, -8.0F), Aircraft.cvt(paramFloat, 0.5F, 1.0F, 44.0F, 62.5F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.5F, 1.0F, -14.5F, 8.0F), Aircraft.cvt(paramFloat, 0.5F, 1.0F, -44.0F, -62.5F), 0.0F);
    }
    else if (paramFloat > 0.25F)
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.25F, 0.5F, 33.0F, 14.5F), Aircraft.cvt(paramFloat, 0.25F, 0.5F, 38.0F, 44.0F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.25F, 0.5F, -33.0F, -14.5F), Aircraft.cvt(paramFloat, 0.25F, 0.5F, -38.0F, -44.0F), 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, 33.0F), Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, 38.0F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, -33.0F), Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, -38.0F), 0.0F);
    }
    if (paramFloat > 0.5F)
    {
      hierMesh().chunkVisible("GearWireR2_D0", true);
      hierMesh().chunkVisible("GearWireL2_D0", true);
    }
    else
    {
      hierMesh().chunkVisible("GearWireR2_D0", false);
      hierMesh().chunkVisible("GearWireL2_D0", false);
    }
  }

  public void update(float paramFloat)
  {
    if (this.bNeedSetup)
      checkAsDrone();
    int i = aircIndex();
    if ((this.FM instanceof Maneuver))
    {
      if (typeDockableIsDocked()) {
        if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode()))
        {
          ((Maneuver)this.FM).unblock();
          ((Maneuver)this.FM).set_maneuver(48);
          for (int j = 0; j < i; j++)
            ((Maneuver)this.FM).push(48);
          if (this.FM.AP.way.curr().Action != 3) {
            ((Maneuver)this.FM).AP.way.setCur(((Aircraft)this.queen_).FM.AP.way.Cur());
          }
          ((Pilot)this.FM).setDumbTime(3000L);
        }
        if (this.FM.M.fuel < this.FM.M.maxFuel)
          this.FM.M.fuel += 0.06F * paramFloat;
      } else if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode()))
      {
        if (this.FM.EI.engines[0].getStage() == 0)
          this.FM.EI.setEngineRunning();
        if ((this.dtime > 0L) && (((Maneuver)this.FM).Group != null)) {
          ((Maneuver)this.FM).Group.leaderGroup = null;
          ((Maneuver)this.FM).set_maneuver(22);
          ((Pilot)this.FM).setDumbTime(3000L);
          if (Time.current() > this.dtime + 3000L) {
            this.dtime = -1L;
            ((Maneuver)this.FM).clear_stack();
            ((Maneuver)this.FM).set_maneuver(0);
            ((Pilot)this.FM).setDumbTime(0L);
          }
        } else if (this.FM.AP.way.curr().Action == 0) {
          Maneuver localManeuver = (Maneuver)this.FM;
          if ((localManeuver.Group != null) && (localManeuver.Group.airc[0] == this) && (localManeuver.Group.clientGroup != null))
          {
            localManeuver.Group.setGroupTask(2);
          }
        }
      }
    }

    super.update(paramFloat);
  }

  protected void moveAileron(float paramFloat) {
    this.aileronsAngle = paramFloat;
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat + this.flaperonAngle, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    this.flaperonAngle = (paramFloat * 17.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * this.aileronsAngle - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * this.aileronsAngle + this.flaperonAngle, 0.0F);
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);
      float f1 = this.FM.CT.getAileron();
      float f2 = this.FM.CT.getElevator();
      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 12.0F * f1, cvt(f2, -1.0F, 1.0F, -12.0F, 18.0F));
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - (cvt(f2, -1.0F, 0.0F, -36.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 32.0F)));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f2, -1.0F, 0.0F, -62.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 44.0F));
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if ((!this.oneTimeCheckDone) && (!this.FM.isPlayers()) && (!isNetPlayer()))
    {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
      {
        this.oneTimeCheckDone = true;
        if (World.cur().camouflage == 1)
        {
          if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)
          {
            this.FM.CT.cockpitDoorControl = 1.0F;
            this.FM.AS.setCockpitDoor(this, 1);
          }

        }
        else if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
        {
          this.FM.CT.cockpitDoorControl = 1.0F;
          this.FM.AS.setCockpitDoor(this, 1);
        }
      }

    }

    if ((paramBoolean) && (this.FM.AP.way.curr().Action == 3) && (typeDockableIsDocked()) && (Math.abs(((Aircraft)this.queen_).FM.Or.getKren()) < 3.0F))
    {
      if (this.FM.isPlayers()) {
        if (((this.FM instanceof RealFlightModel)) && (!((RealFlightModel)this.FM).isRealMode()))
        {
          typeDockableAttemptDetach();
          ((Maneuver)this.FM).set_maneuver(22);
          ((Maneuver)this.FM).setCheckStrike(false);
          this.FM.Vwld.z -= 5.0D;
          this.dtime = Time.current();
        }
      } else {
        typeDockableAttemptDetach();
        ((Maneuver)this.FM).set_maneuver(22);
        ((Maneuver)this.FM).setCheckStrike(false);
        this.FM.Vwld.z -= 5.0D;
        this.dtime = Time.current();
      }
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D1", true);
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilotarm2_d0", false);
    hierMesh().chunkVisible("pilotarm1_d0", false);
  }

  public void missionStarting()
  {
    super.missionStarting();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
    checkAsDrone();
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    hierMesh().chunkVisible("GearWireR1_D0", true);
    hierMesh().chunkVisible("GearWireL1_D0", true);
  }

  private void checkAsDrone() {
    if (this.target_ == null) {
      if (this.FM.AP.way.curr().getTarget() == null)
        this.FM.AP.way.next();
      this.target_ = this.FM.AP.way.curr().getTarget();
      if ((Actor.isValid(this.target_)) && ((this.target_ instanceof Wing))) {
        Wing localWing = (Wing)this.target_;
        int i = aircIndex();
        if (Actor.isValid(localWing.airc[(i / 2)]))
          this.target_ = localWing.airc[(i / 2)];
        else
          this.target_ = null;
      }
    }
    if ((Actor.isValid(this.target_)) && ((this.target_ instanceof TB_3_4M_34R_SPB))) {
      this.queen_last = this.target_;
      this.queen_time = Time.current();
      if (isNetMaster()) {
        ((TypeDockable)this.target_).typeDockableRequestAttach(this, aircIndex() % 2, true);
      }
    }
    this.bNeedSetup = false;
    this.target_ = null;
  }

  public int typeDockableGetDockport() {
    if (typeDockableIsDocked())
      return this.dockport_;
    return -1;
  }

  public Actor typeDockableGetQueen() {
    return this.queen_;
  }

  public boolean typeDockableIsDocked() {
    return Actor.isValid(this.queen_);
  }

  public void typeDockableAttemptAttach() {
    if ((this.FM.AS.isMaster()) && 
      (!typeDockableIsDocked())) {
      Aircraft localAircraft = War.getNearestFriend(this);
      if ((localAircraft instanceof TB_3_4M_34R_SPB))
        ((TypeDockable)localAircraft).typeDockableRequestAttach(this);
    }
  }

  public void typeDockableAttemptDetach()
  {
    if ((this.FM.AS.isMaster()) && (typeDockableIsDocked()) && (Actor.isValid(this.queen_)))
    {
      ((TypeDockable)this.queen_).typeDockableRequestDetach(this);
    }
  }

  public void typeDockableRequestAttach(Actor paramActor)
  {
  }

  public void typeDockableRequestDetach(Actor paramActor)
  {
  }

  public void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
  }

  public void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
  }

  public void typeDockableDoAttachToDrone(Actor paramActor, int paramInt)
  {
  }

  public void typeDockableDoDetachFromDrone(int paramInt) {
  }

  public void typeDockableDoAttachToQueen(Actor paramActor, int paramInt) {
    this.queen_ = paramActor;
    this.dockport_ = paramInt;
    this.queen_last = this.queen_;
    this.queen_time = 0L;
    this.FM.EI.setEngineRunning();
    this.FM.CT.setGearAirborne();
    moveGear(0.0F);
    FlightModel localFlightModel = ((Aircraft)this.queen_).FM;
    if ((aircIndex() == 0) && ((this.FM instanceof Maneuver)) && ((localFlightModel instanceof Maneuver)))
    {
      Maneuver localManeuver1 = (Maneuver)localFlightModel;
      Maneuver localManeuver2 = (Maneuver)this.FM;
      if ((localManeuver1.Group != null) && (localManeuver2.Group != null) && (localManeuver2.Group.numInGroup(this) == localManeuver2.Group.nOfAirc - 1))
      {
        AirGroup localAirGroup = new AirGroup(localManeuver2.Group);
        localManeuver2.Group.delAircraft(this);
        localAirGroup.addAircraft(this);
        localAirGroup.attachGroup(localManeuver1.Group);
        localAirGroup.rejoinGroup = null;
        localAirGroup.leaderGroup = null;
        localAirGroup.clientGroup = localManeuver1.Group;
      }
    }
  }

  public void typeDockableDoDetachFromQueen(int paramInt) {
    if (this.dockport_ == paramInt) {
      this.queen_last = this.queen_;
      this.queen_time = Time.current();
      this.queen_ = null;
      this.dockport_ = 0;
    }
  }

  public void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException
  {
    if (typeDockableIsDocked()) {
      paramNetMsgGuaranted.writeByte(1);
      ActorNet localActorNet = null;
      if (Actor.isValid(this.queen_)) {
        localActorNet = this.queen_.net;
        if (localActorNet.countNoMirrors() > 0)
          localActorNet = null;
      }
      paramNetMsgGuaranted.writeByte(this.dockport_);
      paramNetMsgGuaranted.writeNetObj(localActorNet);
    } else {
      paramNetMsgGuaranted.writeByte(0);
    }
  }

  public void typeDockableReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    if (paramNetMsgInput.readByte() == 1) {
      this.dockport_ = paramNetMsgInput.readByte();
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      if (localNetObj != null) {
        Actor localActor = (Actor)localNetObj.superObj();
        ((TypeDockable)localActor).typeDockableDoAttachToDrone(this, this.dockport_);
      }
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type5(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar07());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type5/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar07());
    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-16type5.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitI_16TYPE5_SPB.class });

    Property.set(localClass, "LOSElevation", 0.82595F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 9, 9 });

    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASk 900", "MGunShKASk 900", null, null, null, null });

    weaponsRegister(localClass, "2xFAB250", new String[] { "MGunShKASk 900", "MGunShKASk 900", "BombGunFAB250 1", "BombGunFAB250 1", null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}