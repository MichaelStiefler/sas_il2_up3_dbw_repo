package com.maddox.il2.game.order;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;
import java.io.PrintStream;

class OrderRequest_For_Landing extends Order
{
  private static Point3d WP = new Point3d();

  public OrderRequest_For_Landing()
  {
    super("Request_For_Landing");
  }

  public void run()
  {
    try
    {
      FlightModel localFlightModel = Player().FM;

      Airport localAirport = null;
      Way localWay = null;

      if (Main.cur().netServerParams.isDogfight())
      {
        WP = Player().FM.actor.pos.getAbsPoint();

        BornPlace localBornPlace = ZutiSupportMethods.getPlayerBornPlace(WP, Player().getArmy());
        if (localBornPlace != null)
          localAirport = ZutiSupportMethods.getAirport(localBornPlace.place.x, localBornPlace.place.y);
      }
      else
      {
        localWay = localFlightModel.AP.way;
        localWay.get(localWay.size() - 1).getP(WP);
      }

      localFlightModel.BarometerZ = (float)World.land().HQ(WP.x, WP.y);

      int i = 0;

      if ((Main.cur().netServerParams.isDogfight()) && (localAirport != null)) {
        i = localAirport.landingFeedback(WP, (Aircraft)localFlightModel.actor);
      }
      else if (Actor.isAlive(localWay.landingAirport)) {
        i = localWay.landingAirport.landingFeedback(WP, (Aircraft)localFlightModel.actor);
      }
      if (i == 0)
        Voice.speakLandingPermited((Aircraft)localFlightModel.actor);
      if (i == 1)
        Voice.speakLandingDenied((Aircraft)localFlightModel.actor);
      if (i == 2)
        Voice.speakWaveOff((Aircraft)localFlightModel.actor);
    } catch (Exception localException) {
      System.out.println("OrderRequest_For_Landing error, ID_01: " + localException.toString());
    }
  }
}