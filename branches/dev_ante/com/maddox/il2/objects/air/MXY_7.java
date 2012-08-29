package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
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
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class MXY_7 extends Scheme2a
  implements MsgCollisionRequestListener, TypeDockable
{
  private Eff3DActor[] flame = { null, null, null };
  private Eff3DActor[] dust = { null, null, null };
  private Eff3DActor[] trail = { null, null, null };
  private Eff3DActor[] sprite = { null, null, null };
  private boolean bNeedSetup = true;
  private long dtime = -1L;
  private Actor queen_last = null;
  private long queen_time = 0L;
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

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (Config.isUSE_RENDER())
      for (int i = 0; i < 3; i++) {
        this.flame[i] = Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1.0F);

        this.dust[i] = Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100D.eff", -1.0F);

        this.trail[i] = Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100T.eff", -1.0F);

        this.sprite[i] = Eff3DActor.New(this, findHook("_Engine" + (i + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100S.eff", -1.0F);

        Eff3DActor.setIntesity(this.flame[i], 0.0F);
        Eff3DActor.setIntesity(this.dust[i], 0.0F);
        Eff3DActor.setIntesity(this.trail[i], 0.0F);
        Eff3DActor.setIntesity(this.sprite[i], 0.0F);
      }
  }

  public void doMurderPilot(int paramInt)
  {
    if (paramInt == 0) {
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d) {
    if (paramString.startsWith("xcf")) {
      hitChunk("CF", paramShot);
    } else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 1)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel1")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xkeel2")) {
      hitChunk("Keel2", paramShot);
    } else if (paramString.startsWith("xrudder1")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xrudder2")) {
      hitChunk("Rudder2", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xvator")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xwing")) {
      if (paramString.startsWith("xwinglin"))
        hitChunk("WingLIn", paramShot);
      if (paramString.startsWith("xwingrin"))
        hitChunk("WingRIn", paramShot);
    } else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      int i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }hitFlesh(j, paramShot, i);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 3:
    case 19:
      this.FM.AS.setEngineDies(this, 0);
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void msgEndAction(Object paramObject, int paramInt) {
    super.msgEndAction(paramObject, paramInt);
    switch (paramInt) {
    case 2:
      Object localObject = null;
      Actor localActor;
      if (Actor.isValid(this.queen_last))
        localActor = this.queen_last;
      else
        localActor = Engine.cur.actorLand;
      MsgExplosion.send(this, null, this.FM.Loc, localActor, 0.0F, 600.0F, 0, 600.0F);
    }
  }

  protected void doExplosion()
  {
    super.doExplosion();
    double d = this.FM.Loc.z - 10.0D;
    World.cur();
    if (d < World.land().HQ_Air(this.FM.Loc.x, this.FM.Loc.y))
      if (Engine.land().isWater(this.FM.Loc.x, this.FM.Loc.y))
        Explosions.BOMB250_Water(this.FM.Loc, 1.0F, 1.0F);
      else
        Explosions.BOMB250_Land(this.FM.Loc, 1.0F, 1.0F);
  }

  public void update(float paramFloat)
  {
    if (this.bNeedSetup)
      checkAsDrone();
    if ((this.FM instanceof Maneuver)) {
      if (typeDockableIsDocked()) {
        if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode()))
        {
          ((Maneuver)this.FM).unblock();
          ((Maneuver)this.FM).set_maneuver(48);
          ((Maneuver)this.FM).AP.way.setCur(((Aircraft)this.queen_).FM.AP.way.Cur());

          ((Pilot)this.FM).setDumbTime(3000L);
        }
      } else if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode()))
      {
        if (this.FM.EI.engines[0].getStage() == 0)
          this.FM.EI.setEngineRunning();
        if (this.dtime > 0L) {
          ((Maneuver)this.FM).setBusy(false);
          ((Maneuver)this.FM).Group.leaderGroup = null;
          ((Maneuver)this.FM).set_maneuver(22);
          ((Pilot)this.FM).setDumbTime(3000L);
          if (Time.current() > this.dtime + 3000L) {
            this.dtime = -1L;
            ((Maneuver)this.FM).clear_stack();
            ((Maneuver)this.FM).pop();
            ((Pilot)this.FM).setDumbTime(0L);
          }
        }
      }
    }
    super.update(paramFloat);
    if (this.FM.AS.isMaster()) {
      for (int i = 0; i < 3; i++) {
        if ((this.FM.CT.PowerControl > 0.77F) && (this.FM.EI.engines[i].getStage() == 0) && (this.FM.M.fuel > 0.0F) && (!typeDockableIsDocked()))
        {
          this.FM.EI.engines[i].setStage(this, 6);
        }if (((this.FM.CT.PowerControl >= 0.77F) || (this.FM.EI.engines[i].getStage() <= 0)) && (this.FM.M.fuel != 0.0F)) {
          continue;
        }
        this.FM.EI.engines[i].setEngineStops(this);
      }
      if (Config.isUSE_RENDER())
        for (i = 0; i < 3; i++)
          if ((this.FM.EI.engines[i].getw() > 50.0F) && (this.FM.EI.engines[i].getStage() == 6))
          {
            this.FM.AS.setSootState(this, i, 1);
          }
          else this.FM.AS.setSootState(this, i, 0);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
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

  public void missionStarting() {
    checkAsDrone();
  }

  private void checkAsDrone() {
    if (this.target_ == null) {
      if (this.FM.AP.way.curr().getTarget() == null)
        this.FM.AP.way.next();
      this.target_ = this.FM.AP.way.curr().getTarget();
      if ((Actor.isValid(this.target_)) && ((this.target_ instanceof Wing))) {
        Wing localWing = (Wing)this.target_;
        int i = aircIndex();
        if (Actor.isValid(localWing.airc[i]))
          this.target_ = localWing.airc[i];
        else
          this.target_ = null;
      }
    }
    if ((Actor.isValid(this.target_)) && ((this.target_ instanceof G4M2E))) {
      this.queen_last = this.target_;
      this.queen_time = Time.current();
      if (isNetMaster()) {
        ((TypeDockable)this.target_).typeDockableRequestAttach(this, 0, true);
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
      if ((localAircraft instanceof G4M2E))
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

  public void doSetSootState(int paramInt1, int paramInt2)
  {
    switch (paramInt2) {
    case 0:
      Eff3DActor.setIntesity(this.flame[paramInt1], 0.0F);
      Eff3DActor.setIntesity(this.dust[paramInt1], 0.0F);
      Eff3DActor.setIntesity(this.trail[paramInt1], 0.0F);
      Eff3DActor.setIntesity(this.sprite[paramInt1], 0.0F);
      break;
    case 1:
      Eff3DActor.setIntesity(this.flame[paramInt1], 1.0F);
      Eff3DActor.setIntesity(this.dust[paramInt1], 0.5F);
      Eff3DActor.setIntesity(this.trail[paramInt1], 1.0F);
      Eff3DActor.setIntesity(this.sprite[paramInt1], 1.0F);
    }
  }

  static
  {
    Class localClass = MXY_7.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MXY");
    Property.set(localClass, "meshName", "3DO/Plane/MXY-7-11(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());
    Property.set(localClass, "originCountry", PaintScheme.countryJapan);
    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/MXY-7-11.fmd");
    Property.set(localClass, "cockpitClass", CockpitMXY_7.class);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_Clip00" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { null });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null });
  }
}