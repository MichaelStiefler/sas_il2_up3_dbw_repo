package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Message;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Time;

public class EjectionSeat extends ActorHMesh
{
  private Vector3d v = new Vector3d();
  private Loc l = new Loc();
  private boolean bPilotAttached = true;
  private Aircraft ownerAircraft;
  private long timeStart;

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private void doRemovePilot()
  {
    hierMesh().chunkVisible("Pilot1_D0", false);
    hierMesh().chunkVisible("Head1_D0", false);
    hierMesh().chunkVisible("HMask1_D0", false);
  }

  public EjectionSeat(int paramInt, Loc paramLoc, Vector3d paramVector3d, Aircraft paramAircraft)
  {
    switch (paramInt) {
    case 1:
    default:
      setMesh("3DO/Plane/He-162-ESeat/hier.him");
      drawing(true);
      break;
    case 2:
      setMesh("3DO/Plane/Do-335A-0-ESeat/hier.him");
      drawing(true);
      break;
    case 3:
      setMesh("3DO/Plane/Ar-234-ESeat/hier.him");
      drawing(true);
    }

    this.l.set(paramLoc);
    this.v.set(paramVector3d);
    this.v.scale(Time.tickConstLenFs());
    this.pos.setAbs(this.l);
    interpPut(new Interpolater(), null, Time.current(), null);

    this.ownerAircraft = paramAircraft;

    this.timeStart = Time.current();
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      float f = Time.tickLenFs();
      EjectionSeat.this.v.z -= 9.810000000000001D * f * f;
      EjectionSeat.this.v.x *= 0.9900000095367432D;
      EjectionSeat.this.v.y *= 0.9900000095367432D;

      EjectionSeat.this.l.add(EjectionSeat.this.v);
      EjectionSeat.this.pos.setAbs(EjectionSeat.this.l);
      World.cur(); double d = World.land().HQ_Air(EjectionSeat.this.l.getPoint().x, EjectionSeat.this.l.getPoint().y);
      if (EjectionSeat.this.l.getPoint().z < d) {
        MsgDestroy.Post(Time.current(), this.actor);
      }
      if ((EjectionSeat.this.bPilotAttached) && ((EjectionSeat.this.l.getPoint().z < d) || (Time.current() > EjectionSeat.this.timeStart + 3000L)))
      {
        if ((!EjectionSeat.this.ownerAircraft.isNet()) || (EjectionSeat.this.ownerAircraft.isNetMaster())) {
          Vector3d localVector3d = new Vector3d(EjectionSeat.this.v);
          localVector3d.scale(1.0F / Time.tickLenFs());
          if (Actor.isValid(EjectionSeat.this.ownerAircraft)) {
            Paratrooper localParatrooper = new Paratrooper(EjectionSeat.this.ownerAircraft, EjectionSeat.this.ownerAircraft.getArmy(), 0, EjectionSeat.this.l, localVector3d);
            EjectionSeat.this.doRemovePilot();
            EjectionSeat.access$202(EjectionSeat.this, false);
            EjectionSeat.this.ownerAircraft.FM.AS.astateBailoutStep = 12;
            EventLog.onBailedOut(EjectionSeat.this.ownerAircraft, 0);
            EjectionSeat.this.ownerAircraft.FM.AS.setPilotState(EjectionSeat.this.ownerAircraft, 0, 100, false);
          }
        } else {
          EjectionSeat.this.doRemovePilot();
          EjectionSeat.access$202(EjectionSeat.this, false);
        }
      }
      return true;
    }
  }
}