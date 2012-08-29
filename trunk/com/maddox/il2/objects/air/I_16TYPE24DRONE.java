package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class I_16TYPE24DRONE extends I_16
  implements MsgCollisionRequestListener, TypeTNBFighter, TypeStormovik, TypeDockable
{
  private Actor queen_last = null;
  private long queen_time = 0L;

  private boolean bNeedSetup = true;
  private long dtime = -1L;

  private Actor target_ = null;

  private Actor queen_ = null;
  private int dockport_;

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    super.msgCollisionRequest(paramActor, paramArrayOfBoolean);
    if ((this.queen_last != null) && (this.queen_last == paramActor) && ((this.queen_time == 0L) || (Time.current() < this.queen_time + 5000L)))
    {
      paramArrayOfBoolean[0] = false;
    }
    else paramArrayOfBoolean[0] = true;
  }

  public void update(float paramFloat)
  {
    if (this.bNeedSetup) {
      checkAsDrone();
    }
    int i = aircIndex();
    if ((this.FM instanceof Maneuver)) {
      if (typeDockableIsDocked()) {
        if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
          ((Maneuver)this.FM).unblock();
          ((Maneuver)this.FM).set_maneuver(48);
          for (int j = 0; j < i; j++) ((Maneuver)this.FM).push(48);
          if (this.FM.AP.way.curr().Action != 3)
            ((Maneuver)this.FM).AP.way.setCur(((Aircraft)this.queen_).FM.AP.way.Cur());
          ((Pilot)this.FM).setDumbTime(3000L);
        }
        if (this.FM.M.fuel < this.FM.M.maxFuel) {
          this.FM.M.fuel += 0.06F * paramFloat;
        }
      }
      else if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
        if (this.FM.EI.engines[0].getStage() == 0) {
          this.FM.EI.setEngineRunning();
        }
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

        }
        else if (this.FM.AP.way.curr().Action == 0) {
          Maneuver localManeuver = (Maneuver)this.FM;
          if ((localManeuver.Group != null) && (localManeuver.Group.airc[0] == this) && (localManeuver.Group.clientGroup != null)) {
            localManeuver.Group.setGroupTask(2);
          }
        }
      }

    }

    super.update(paramFloat);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((paramBoolean) && 
      (this.FM.AP.way.curr().Action == 3) && (typeDockableIsDocked()) && (Math.abs(((Aircraft)this.queen_).FM.Or.getKren()) < 3.0F))
    {
      if (this.FM.isPlayers()) {
        if (((this.FM instanceof RealFlightModel)) && (!((RealFlightModel)this.FM).isRealMode())) {
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

  public void missionStarting()
  {
    checkAsDrone();
  }

  private void checkAsDrone()
  {
    if (this.target_ == null) {
      if (this.FM.AP.way.curr().getTarget() == null) this.FM.AP.way.next();
      this.target_ = this.FM.AP.way.curr().getTarget();
      if ((Actor.isValid(this.target_)) && ((this.target_ instanceof Wing))) {
        Wing localWing = (Wing)this.target_;
        int i = aircIndex();
        if (Actor.isValid(localWing.airc[(i / 2)]))
          this.target_ = localWing.airc[(i / 2)];
        else this.target_ = null;
      }
    }
    if ((Actor.isValid(this.target_)) && ((this.target_ instanceof TB_3_4M_34R_SPB))) {
      this.queen_last = this.target_;
      this.queen_time = Time.current();
      if (isNetMaster())
      {
        ((TypeDockable)this.target_).typeDockableRequestAttach(this, aircIndex() % 2, true);
      }
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
      if ((localAircraft instanceof TB_3_4M_34R_SPB))
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
    this.queen_last = this.queen_;
    this.queen_time = 0L;

    this.FM.EI.setEngineRunning();
    this.FM.CT.setGearAirborne();
    moveGear(0.0F);

    FlightModel localFlightModel = ((Aircraft)this.queen_).FM;
    if ((aircIndex() == 0) && ((this.FM instanceof Maneuver)) && ((localFlightModel instanceof Maneuver))) {
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

  public void typeDockableDoDetachFromQueen(int paramInt)
  {
    if (this.dockport_ != paramInt) {
      return;
    }
    this.queen_last = this.queen_;
    this.queen_time = Time.current();
    this.queen_ = null;
    this.dockport_ = 0;
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
    Class localClass = I_16TYPE24DRONE.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type24(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type24/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/I-16type24.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_16TYPE24_SPB.class);
    Property.set(localClass, "LOSElevation", 0.82595F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab250", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}