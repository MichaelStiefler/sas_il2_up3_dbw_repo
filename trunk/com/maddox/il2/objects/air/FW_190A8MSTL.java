package com.maddox.il2.objects.air;

import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public class FW_190A8MSTL extends FW_190
  implements TypeDockable
{
  private boolean bNeedSetup = true;
  private long dtime = -1L;

  private Actor target_ = null;

  private Actor queen_ = null;
  private int dockport_;

  protected void moveFan(float paramFloat)
  {
    int i = 0;

    for (int j = 0; j < 1; j++) {
      if (this.oldProp[j] < 2)
      {
        i = Math.abs((int)(this.FM.EI.engines[j].getw() * 0.12F * 1.5F));
        if (i >= 1) i = 1;
        if (i != this.oldProp[j]) {
          hierMesh().chunkVisible(Props[j][this.oldProp[j]], false);
          this.oldProp[j] = i;
          hierMesh().chunkVisible(Props[j][i], true);
        }
      }

      if (i == 0) {
        this.propPos[j] = ((this.propPos[j] + 57.299999F * this.FM.EI.engines[j].getw() * paramFloat) % 360.0F);
      } else {
        float f = 57.299999F * this.FM.EI.engines[j].getw();
        f %= 2880.0F;
        f /= 2880.0F;
        if (f <= 0.5F)
          f *= 2.0F;
        else {
          f = f * 2.0F - 2.0F;
        }
        f *= 1200.0F;
        this.propPos[j] = ((this.propPos[j] + f * paramFloat) % 360.0F);
      }
      hierMesh().chunkSetAngles(Props[j][0], 0.0F, -this.propPos[j], 0.0F);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) {
    if (typeDockableIsDocked())
      moveGear(hierMesh(), 0.0F);
    else
      moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    if (this.bNeedSetup) {
      checkAsDrone();
    }
    if ((this.FM instanceof Maneuver)) {
      if (typeDockableIsDocked()) {
        if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
          ((Maneuver)this.FM).set_maneuver(48);
          ((Maneuver)this.FM).AP.way.setCur(((Aircraft)this.queen_).FM.AP.way.Cur());
          ((Pilot)this.FM).setDumbTime(3000L);
        }
      }
      else if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
        if (this.dtime > 0L) {
          ((Maneuver)this.FM).set_maneuver(22);
          ((Pilot)this.FM).setDumbTime(3000L);
          if (Time.current() > this.dtime + 3000L) {
            this.dtime = -1L;
            ((Maneuver)this.FM).clear_stack();
            ((Maneuver)this.FM).pop();
            ((Pilot)this.FM).setDumbTime(0L);
          }
        }
        else if ((this.FM.AP.way.curr().Action == 3) && (((Maneuver)this.FM).get_maneuver() == 24)) {
          ((Maneuver)this.FM).set_maneuver(21);
          ((Pilot)this.FM).setDumbTime(30000L);
        }
      }

    }

    if (typeDockableIsDocked()) {
      Aircraft localAircraft = (Aircraft)typeDockableGetQueen();
      localAircraft.FM.CT.AileronControl = this.FM.CT.AileronControl;
      localAircraft.FM.CT.ElevatorControl = this.FM.CT.ElevatorControl;
      localAircraft.FM.CT.RudderControl = this.FM.CT.RudderControl;
      localAircraft.FM.CT.GearControl = this.FM.CT.GearControl;
    }

    if (this.FM.CT.saveWeaponControl[3] != 0) {
      typeDockableAttemptDetach();
    }
    super.update(paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((Mission.isCoop()) && (!Mission.isServer()) && (!isSpawnFromMission()) && (this.net.isMaster()))
    {
      new MsgAction(64, 0.0D, this) {
        public void doAction() { FW_190A8MSTL.this.onCoopMasterSpawned(); } } ;
    }
  }

  private void onCoopMasterSpawned() {
    Actor localActor = null;
    String str1 = null;
    if (this.FM.AP.way.curr().getTargetName() == null) this.FM.AP.way.next();
    str1 = this.FM.AP.way.curr().getTargetName();
    if (str1 != null)
      localActor = Actor.getByName(str1);
    if ((Actor.isValid(localActor)) && ((localActor instanceof Wing)) && (localActor.getOwnerAttachedCount() > 0))
      localActor = (Actor)localActor.getOwnerAttached(0);
    this.FM.AP.way.setCur(0);
    if ((Actor.isValid(localActor)) && ((localActor instanceof JU_88MSTL)))
      try {
        Aircraft localAircraft = (Aircraft)localActor;
        float f = 100.0F;
        if (localAircraft.FM.M.maxFuel > 0.0F)
          f = localAircraft.FM.M.fuel / localAircraft.FM.M.maxFuel * 100.0F;
        String str2 = "spawn " + localActor.getClass().getName() + " NAME net" + localActor.name() + " FUEL " + f + " WEAPONS " + localAircraft.thisWeaponsName + (localAircraft.bPaintShemeNumberOn ? "" : " NUMBEROFF") + " OVR";

        CmdEnv.top().exec(str2);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  public void missionStarting()
  {
    checkAsDrone();
  }

  private void checkAsDrone() {
    if (this.target_ == null) {
      if (this.FM.AP.way.curr().getTarget() == null) this.FM.AP.way.next();
      this.target_ = this.FM.AP.way.curr().getTarget();
      if (Actor.isValid(this.target_))
        this.target_ = this.FM.AP.way.curr().getTargetActorRandom();
    }
    if ((Actor.isValid(this.target_)) && ((this.target_ instanceof JU_88MSTL)) && 
      (isNetMaster()))
    {
      ((TypeDockable)this.target_).typeDockableRequestAttach(this, 0, true);
    }

    this.bNeedSetup = false;
    this.target_ = null;
  }

  public int typeDockableGetDockport()
  {
    if (typeDockableIsDocked()) {
      return this.dockport_;
    }
    return -1;
  }
  public Actor typeDockableGetQueen() {
    return this.queen_;
  }

  public boolean typeDockableIsDocked()
  {
    return Actor.isValid(this.queen_);
  }

  public void typeDockableAttemptAttach()
  {
    if (!this.FM.AS.isMaster()) {
      return;
    }

    if (!typeDockableIsDocked())
    {
      Aircraft localAircraft = War.getNearestFriend(this);
      if ((localAircraft instanceof JU_88MSTL))
      {
        ((TypeDockable)localAircraft).typeDockableRequestAttach(this);
      }
    }
  }

  public void typeDockableAttemptDetach() {
    if (this.FM.AS.isMaster())
    {
      if (typeDockableIsDocked())
      {
        if (Actor.isValid(this.queen_))
          ((TypeDockable)this.queen_).typeDockableRequestDetach(this); 
      }
    }
  }

  public void typeDockableRequestAttach(Actor paramActor) {
  }

  public void typeDockableRequestDetach(Actor paramActor) {
  }

  public void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean) {
  }

  public void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean) {
  }

  public void typeDockableDoAttachToDrone(Actor paramActor, int paramInt) {
  }

  public void typeDockableDoDetachFromDrone(int paramInt) {
  }

  public void typeDockableDoAttachToQueen(Actor paramActor, int paramInt) {
    this.queen_ = paramActor;
    this.dockport_ = paramInt;
    Object localObject2;
    if (this.FM.EI.getNum() == 1) {
      this.FM.Scheme = 2;
      localObject1 = (Aircraft)paramActor;
      this.FM.EI.setNum(3);
      localObject2 = this.FM.EI.engines[0];
      this.FM.EI.engines = new Motor[3];
      this.FM.EI.engines[0] = localObject2;
      this.FM.EI.engines[1] = localObject1.FM.EI.engines[0];
      this.FM.EI.engines[2] = localObject1.FM.EI.engines[1];
      this.FM.EI.bCurControl = new boolean[] { true, true, true };
      localObject1.FM.EI.bCurControl[0] = false;
      localObject1.FM.EI.bCurControl[1] = false;
    }

    this.FM.EI.setEngineRunning();
    this.FM.CT.setGearAirborne();
    moveGear(0.0F);
    this.FM.CT.GearControl = ((Aircraft)paramActor).FM.CT.GearControl;

    Object localObject1 = ((Aircraft)this.queen_).FM;
    if (((this.FM instanceof Maneuver)) && ((localObject1 instanceof Maneuver))) {
      localObject2 = (Maneuver)localObject1;
      Maneuver localManeuver = (Maneuver)this.FM;
      if ((((Maneuver)localObject2).Group != null) && (localManeuver.Group != null) && (localManeuver.Group.numInGroup(this) == localManeuver.Group.nOfAirc - 1))
      {
        AirGroup localAirGroup = new AirGroup(localManeuver.Group);
        localManeuver.Group.delAircraft(this);
        localAirGroup.addAircraft(this);
        localAirGroup.attachGroup(((Maneuver)localObject2).Group);
        localAirGroup.rejoinGroup = null;
      }
    }
  }

  public void typeDockableDoDetachFromQueen(int paramInt)
  {
    if (this.dockport_ != paramInt) {
      return;
    }

    this.queen_ = null;
    this.dockport_ = 0;

    this.FM.CT.setTrimElevatorControl(0.51F);
    this.FM.CT.trimElevator = 0.51F;

    this.FM.CT.setGearAirborne();
    if (this.FM.EI.getNum() == 3) {
      this.FM.Scheme = 1;
      this.FM.EI.setNum(1);
      Motor localMotor = this.FM.EI.engines[0];
      this.FM.EI.engines = new Motor[1];
      this.FM.EI.engines[0] = localMotor;
      this.FM.EI.bCurControl = new boolean[] { true };
      for (int i = 1; i < 3; i++) {
        if (this.FM.Gears.clpEngineEff[i][0] != null) {
          Eff3DActor.finish(this.FM.Gears.clpEngineEff[i][0]);
          this.FM.Gears.clpEngineEff[i][0] = null;
        }
        if (this.FM.Gears.clpEngineEff[i][1] != null) {
          Eff3DActor.finish(this.FM.Gears.clpEngineEff[i][1]);
          this.FM.Gears.clpEngineEff[i][1] = null;
        }
      }
    }
  }

  public void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException
  {
    if (typeDockableIsDocked())
    {
      paramNetMsgGuaranted.writeByte(1);
      ActorNet localActorNet = null;
      if (Actor.isValid(this.queen_))
      {
        localActorNet = this.queen_.net;

        if (localActorNet.countNoMirrors() > 0)
        {
          localActorNet = null;
        }
      }
      paramNetMsgGuaranted.writeByte(this.dockport_);

      paramNetMsgGuaranted.writeNetObj(localActorNet);
    }
    else
    {
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
    Class localClass = FW_190A8MSTL.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-8(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fw-190A-8.fmd");
    Property.set(localClass, "cockpitClass", CockpitFW_190F8MSTL.class);
    Property.set(localClass, "LOSElevation", 0.764106F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 3, 9, 9, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 9, 9, 1, 1, 9, 9, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalDev02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev05", "_ExternalDev06", "_CANNON09", "_CANNON10", "_ExternalDev07", "_ExternalDev08", "_CANNON11", "_CANNON12", "_ExternalDev09", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal", "PylonMG15120Internal", "MGunMG15120kh 125", "MGunMG15120kh 125", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}