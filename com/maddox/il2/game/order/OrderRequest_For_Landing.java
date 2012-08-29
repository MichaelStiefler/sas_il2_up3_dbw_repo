package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderRequest_For_Landing extends Order
{
  private static Point3d WP = new Point3d();

  public OrderRequest_For_Landing()
  {
    super("Request_For_Landing");
  }
  public void run() {
    FlightModel localFlightModel = Player().FM;
    Way localWay = localFlightModel.AP.way;

    localWay.get(localWay.size() - 1).getP(WP);
    localFlightModel.BarometerZ = (float)World.land().HQ(WP.x, WP.y);
    int i = 0;
    if (Actor.isAlive(localWay.landingAirport)) {
      i = localWay.landingAirport.landingFeedback(WP, (Aircraft)localFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
    if (i == 0) Voice.speakLandingPermited((Aircraft)localFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    if (i == 1) Voice.speakLandingDenied((Aircraft)localFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    if (i == 2) Voice.speakWaveOff((Aircraft)localFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
  }
}