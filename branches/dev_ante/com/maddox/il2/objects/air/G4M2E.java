package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.io.IOException;

public class G4M2E extends G4M
  implements TypeBomber, TypeTransport, TypeDockable
{
  private Actor[] drones = { null };

  public boolean typeDockableIsDocked()
  {
    return true;
  }

  public Actor typeDockableGetDrone() {
    return this.drones[0];
  }

  public void typeDockableAttemptAttach()
  {
  }

  public void typeDockableAttemptDetach() {
    if (this.FM.AS.isMaster())
      for (int i = 0; i < this.drones.length; i++)
        if (Actor.isValid(this.drones[i]))
          typeDockableRequestDetach(this.drones[i], i, true);
  }

  public void typeDockableRequestAttach(Actor paramActor)
  {
    if ((paramActor instanceof Aircraft)) {
      Aircraft localAircraft = (Aircraft)paramActor;
      if ((localAircraft.FM.AS.isMaster()) && (localAircraft.FM.Gears.onGround()) && (localAircraft.FM.getSpeedKMH() < 10.0F) && (this.FM.getSpeedKMH() < 10.0F))
      {
        for (int i = 0; i < this.drones.length; i++)
          if (!Actor.isValid(this.drones[i])) {
            HookNamed localHookNamed = new HookNamed(this, "_Dockport" + i);

            Loc localLoc1 = new Loc();
            Loc localLoc2 = new Loc();
            this.pos.getAbs(localLoc2);
            localHookNamed.computePos(this, localLoc2, localLoc1);
            paramActor.pos.getAbs(localLoc2);
            if (localLoc1.getPoint().distance(localLoc2.getPoint()) < 5.0D) {
              if (this.FM.AS.isMaster()) {
                typeDockableRequestAttach(paramActor, i, true); break;
              }
              this.FM.AS.netToMaster(32, i, 0, paramActor);
              break;
            }
          }
      }
    }
  }

  public void typeDockableRequestDetach(Actor paramActor)
  {
    for (int i = 0; i < this.drones.length; i++)
      if (paramActor == this.drones[i]) {
        Aircraft localAircraft = (Aircraft)paramActor;
        if (localAircraft.FM.AS.isMaster())
          if (this.FM.AS.isMaster())
            typeDockableRequestDetach(paramActor, i, true);
          else
            this.FM.AS.netToMaster(33, i, 1, paramActor);
      }
  }

  public void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
    if ((paramInt >= 0) && (paramInt <= 1))
      if (paramBoolean) {
        if (this.FM.AS.isMaster()) {
          this.FM.AS.netToMirrors(34, paramInt, 1, paramActor);
          typeDockableDoAttachToDrone(paramActor, paramInt);
        } else {
          this.FM.AS.netToMaster(34, paramInt, 1, paramActor);
        }
      } else if (this.FM.AS.isMaster()) {
        if (!Actor.isValid(this.drones[paramInt])) {
          this.FM.AS.netToMirrors(34, paramInt, 1, paramActor);
          typeDockableDoAttachToDrone(paramActor, paramInt);
        }
      }
      else this.FM.AS.netToMaster(34, paramInt, 0, paramActor);
  }

  public void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.FM.AS.isMaster()) {
        this.FM.AS.netToMirrors(35, paramInt, 1, paramActor);
        typeDockableDoDetachFromDrone(paramInt);
      } else {
        this.FM.AS.netToMaster(35, paramInt, 1, paramActor);
      }
  }

  public void typeDockableDoAttachToDrone(Actor paramActor, int paramInt) {
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
      ((TypeDockable)this.drones[paramInt]).typeDockableDoAttachToQueen(this, paramInt);
    }
  }

  public void typeDockableDoDetachFromDrone(int paramInt) {
    if (Actor.isValid(this.drones[paramInt])) {
      this.drones[paramInt].pos.setBase(null, null, true);
      ((TypeDockable)this.drones[paramInt]).typeDockableDoDetachFromQueen(paramInt);
      this.drones[paramInt] = null;
    }
  }

  public void typeDockableDoAttachToQueen(Actor paramActor, int paramInt)
  {
  }

  public void typeDockableDoDetachFromQueen(int paramInt)
  {
  }

  public void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException
  {
    for (int i = 0; i < this.drones.length; i++)
      if (Actor.isValid(this.drones[i])) {
        paramNetMsgGuaranted.writeByte(1);
        ActorNet localActorNet = this.drones[i].net;
        if (localActorNet.countNoMirrors() == 0)
          paramNetMsgGuaranted.writeNetObj(localActorNet);
        else
          paramNetMsgGuaranted.writeNetObj(null);
      } else {
        paramNetMsgGuaranted.writeByte(0);
      }
  }

  public void typeDockableReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    for (int i = 0; i < this.drones.length; i++)
      if (paramNetMsgInput.readByte() == 1) {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj != null)
          typeDockableDoAttachToDrone((Actor)localNetObj.superObj(), i);
      }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -20.0F) {
        f1 = -20.0F;
        bool = false;
      }
      if (f1 > 20.0F) {
        f1 = 20.0F;
        bool = false;
      }
      if (f2 < -20.0F) {
        f2 = -20.0F;
        bool = false;
      }
      if (f2 <= 20.0F) break;
      f2 = 20.0F;
      bool = false; break;
    case 1:
      if (f1 < -30.0F) {
        f1 = -30.0F;
        bool = false;
      }
      if (f1 > 30.0F) {
        f1 = 30.0F;
        bool = false;
      }
      if (f2 < -30.0F) {
        f2 = -30.0F;
        bool = false;
      }
      if (f2 <= 30.0F) break;
      f2 = 30.0F;
      bool = false; break;
    case 2:
      if (f2 < -3.0F) {
        f2 = -3.0F;
        bool = false;
      }
      if (f2 <= 70.0F) break;
      f2 = 70.0F;
      bool = false; break;
    case 3:
      if (f1 < -10.0F) {
        f1 = -10.0F;
        bool = false;
      }
      if (f1 > 70.0F) {
        f1 = 70.0F;
        bool = false;
      }
      if (f2 < -40.0F) {
        f2 = -40.0F;
        bool = false;
      }
      if (f2 <= 25.0F) break;
      f2 = 25.0F;
      bool = false; break;
    case 4:
      if (f1 < -70.0F) {
        f1 = -70.0F;
        bool = false;
      }
      if (f1 > 45.0F) {
        f1 = 45.0F;
        bool = false;
      }
      if (f2 < -40.0F) {
        f2 = -40.0F;
        bool = false;
      }
      if (f2 <= 25.0F) break;
      f2 = 25.0F;
      bool = false; break;
    case 5:
      if (f1 < -30.0F) {
        f1 = -30.0F;
        bool = false;
      }
      if (f1 > 30.0F) {
        f1 = 30.0F;
        bool = false;
      }
      if (f2 < -70.0F) {
        f2 = -70.0F;
        bool = false;
      }
      if (f2 <= 70.0F) break;
      f2 = 70.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = G4M2E.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "G4M");
    Property.set(localClass, "meshName", "3DO/Plane/G4M2E(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_ja", "3DO/Plane/G4M2E(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());
    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1946.0F);
    Property.set(localClass, "FlightModel", "FlightModels/G4M1-11.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitG4M2E.class, CockpitG4M2E_TGunner.class, CockpitG4M2E_AGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunHo5t 500", "MGunHo5t 500", "MGunHo5t 500", "MGunHo5t 500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}