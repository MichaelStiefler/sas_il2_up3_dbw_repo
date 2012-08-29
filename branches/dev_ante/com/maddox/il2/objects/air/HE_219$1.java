package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.MsgAction;

class HE_219$1 extends MsgAction
{
  private final HE_219 this$0;

  public void doAction(Object paramObject)
  {
    Aircraft localAircraft = (Aircraft)paramObject;
    if (!Actor.isValid(localAircraft))
    {
      return;
    }

    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10.0D);
    HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
    localAircraft.pos.getAbs(localLoc2);
    localHookNamed.computePos(localAircraft, localLoc2, localLoc1);
    localLoc1.transform(localVector3d);
    localVector3d.x += localAircraft.FM.Vwld.x;
    localVector3d.y += localAircraft.FM.Vwld.y;
    localVector3d.z += localAircraft.FM.Vwld.z;
    new EjectionSeat(2, localLoc1, localVector3d, localAircraft);
  }
}