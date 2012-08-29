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

class OrderVector_To_Home_Base extends Order
{
  private static Point3d V1 = new Point3d();
  private static Point3d V2 = new Point3d();

  public OrderVector_To_Home_Base()
  {
    super("Vector_To_Home_Base");
  }
  public void run() {
    Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.moveCarrier();
    Way localWay = Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AP.way;
    WayPoint localWayPoint = localWay.get(localWay.size() - 1);
    localWayPoint.getP(V2);
    Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor.pos.getAbs(V1);
    V1.sub(V2);
    if (isEnableVoice())
      Voice.speakHeadingToHome(Player(), V1);
  }
}