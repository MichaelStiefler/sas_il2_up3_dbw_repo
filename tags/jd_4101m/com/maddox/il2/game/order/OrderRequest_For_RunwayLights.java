package com.maddox.il2.game.order;

import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportGround;
import com.maddox.il2.ai.War;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

public class OrderRequest_For_RunwayLights extends Order
{
  public OrderRequest_For_RunwayLights()
  {
    super("Request_Runway_Lights");
  }

  public void run()
  {
    Aircraft localAircraft = Player();
    Airport localAirport = Airport.nearest(localAircraft.FM.Loc, -1, 1);
    if ((localAirport != null) && ((localAirport instanceof AirportGround)) && (localAirport.isAlive()) && (((AirportGround)localAirport).hasLights()) && (GUI.pad.getAirportArmy(localAirport) == localAircraft.getArmy()))
    {
      if (localAirport.distance(localAircraft) < 10000.0D)
      {
        Actor localActor = War.GetNearestEnemy(localAircraft, -1, 12000.0F, 16);

        if (localActor == null)
        {
          Voice.speakRooger(localAircraft);
          ((AirportGround)localAirport).turnOnLights(localAircraft);
        }
        else if (localActor.getArmy() == localAircraft.getArmy())
        {
          Voice.speakRooger(localAircraft);
          ((AirportGround)localAirport).turnOnLights(localAircraft);
        }
        else
        {
          Voice.speakNegative(localAircraft);
        }
      }
    }
  }
}