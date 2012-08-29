package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderVector_To_Target extends Order
{
  private static Point3d V1 = new Point3d();
  private static Point3d V2 = new Point3d();

  public OrderVector_To_Target()
  {
    super("Vector_To_Target");
  }
  public void run() {
    Way localWay = Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AP.way;
    WayPoint localWayPoint = localWay.curr();
    localWayPoint.getP(V2);
    for (int i = localWay.Cur(); i < localWay.size(); i++) {
      localWayPoint = localWay.get(i);
      if ((localWayPoint.Action == 3) || ((localWayPoint.getTarget() != null) && (localWayPoint.Action != 2))) {
        localWayPoint.getP(V2);
      }
    }
    Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor.pos.getAbs(V1);
    V1.sub(V2);
    if (isEnableVoice())
      Voice.speakHeadingToTarget(Player(), V1);
  }
}