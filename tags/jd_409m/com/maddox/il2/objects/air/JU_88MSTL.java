package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.io.IOException;

public class JU_88MSTL extends JU_88
  implements TypeDockable
{
  private Actor[] drones = { null };
  private Actor droneInitiator = null;

  public void msgEndAction(Object paramObject, int paramInt)
  {
    super.msgEndAction(paramObject, paramInt);
    switch (paramInt) {
    case 2:
      MsgExplosion.send(this, null, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.droneInitiator, 0.0F, 4550.0F, 0, 890.0F);
    }
  }

  protected void doExplosion()
  {
    super.doExplosion();
    World.cur(); if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z - 300.0D < World.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double))
      if (Engine.land().isWater(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double))
        Explosions.bomb1000_water(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, 1.0F, 1.0F);
      else
        Explosions.bomb1000_land(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, 1.0F, 1.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("WingLMid")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRMid")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 1);
    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    }
    super.msgShot(paramShot);
  }

  public boolean typeDockableIsDocked()
  {
    return true;
  }

  public void typeDockableAttemptAttach() {
  }

  public void typeDockableAttemptDetach() {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster())
    {
      for (int i = 0; i < this.drones.length; i++) {
        if (!Actor.isValid(this.drones[i]))
          continue;
        typeDockableRequestDetach(this.drones[i], i, true);
      }
    }
  }

  public void typeDockableRequestAttach(Actor paramActor)
  {
  }

  public void typeDockableRequestDetach(Actor paramActor)
  {
    for (int i = 0; i < this.drones.length; i++) {
      if (paramActor != this.drones[i])
        continue;
      Aircraft localAircraft = (Aircraft)paramActor;
      if (localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster())
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster())
        {
          typeDockableRequestDetach(paramActor, i, true);
        }
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMaster(33, i, 1, paramActor);
    }
  }

  public void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
    if (paramInt != 0) {
      return;
    }
    if (paramBoolean) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMirrors(34, paramInt, 1, paramActor);
        typeDockableDoAttachToDrone(paramActor, paramInt);
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMaster(34, paramInt, 1, paramActor);
      }
    }
    else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) {
      if (!Actor.isValid(this.drones[paramInt])) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMirrors(34, paramInt, 1, paramActor);
        typeDockableDoAttachToDrone(paramActor, paramInt);
      }
    }
    else this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMaster(34, paramInt, 0, paramActor);
  }

  public void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMirrors(35, paramInt, 1, paramActor);
        typeDockableDoDetachFromDrone(paramInt);
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.netToMaster(35, paramInt, 1, paramActor);
      }
  }

  public void typeDockableDoAttachToDrone(Actor paramActor, int paramInt)
  {
    if (!Actor.isValid(this.drones[paramInt])) {
      HookNamed localHookNamed = new HookNamed(this, "_Dockport" + paramInt);
      Loc localLoc1 = new Loc();
      Loc localLoc2 = new Loc();
      this.pos.getAbs(localLoc2);
      localHookNamed.computePos(this, localLoc2, localLoc1);
      paramActor.pos.setAbs(localLoc1);
      paramActor.pos.setBase(this, null, true);

      paramActor.pos.resetAsBase();
      this.drones[paramInt] = paramActor;
      this.droneInitiator = paramActor;
      ((TypeDockable)this.drones[paramInt]).typeDockableDoAttachToQueen(this, paramInt);
    }
  }

  public void typeDockableDoDetachFromDrone(int paramInt) {
    if (!Actor.isValid(this.drones[paramInt])) {
      return;
    }
    this.drones[paramInt].pos.setBase(null, null, true);
    ((TypeDockable)this.drones[paramInt]).typeDockableDoDetachFromQueen(paramInt);
    this.drones[paramInt] = null;
  }
  public void typeDockableDoAttachToQueen(Actor paramActor, int paramInt) {
  }
  public void typeDockableDoDetachFromQueen(int paramInt) {
  }
  public void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    for (int i = 0; i < this.drones.length; i++)
    {
      if (Actor.isValid(this.drones[i]))
      {
        paramNetMsgGuaranted.writeByte(1);
        ActorNet localActorNet = this.drones[i].net;

        if (localActorNet.countNoMirrors() == 0)
        {
          paramNetMsgGuaranted.writeNetObj(localActorNet);
        }
        else
          paramNetMsgGuaranted.writeNetObj(null);
      }
      else
      {
        paramNetMsgGuaranted.writeByte(0);
      }
    }
  }

  public void typeDockableReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    for (int i = 0; i < this.drones.length; i++)
      if (paramNetMsgInput.readByte() == 1) {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj != null)
          typeDockableDoAttachToDrone((Actor)localNetObj.superObj(), i);
      }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && 
      (paramInt1 == 2)) {
      typeDockableRequestDetach(this.drones[0], 0, true);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) {
      ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setDumbTime(9999L);
    }
    if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6))
    {
      ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).set_maneuver(44);
      ((Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setSpeedMode(-1);
    }

    super.update(paramFloat);
  }

  static
  {
    Class localClass = JU_88MSTL.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ju-88");
    Property.set(localClass, "meshName", "3DO/Plane/Ju-88MSTL/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Ju-88A-4Mistel.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_Dockport0" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null });
  }
}