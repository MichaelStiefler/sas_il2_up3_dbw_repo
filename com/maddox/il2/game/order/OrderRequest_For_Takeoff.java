package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderRequest_For_Takeoff extends Order
{
  public OrderRequest_For_Takeoff()
  {
    super("Request_For_Takeoff");
  }
  public void run() {
    Maneuver localManeuver = (Maneuver)Player().FM;
    if (localManeuver.canITakeoff()) Voice.speakTakeoffPermited((Aircraft)localManeuver.jdField_actor_of_type_ComMaddoxIl2EngineActor); else
      Voice.speakTakeoffDenied((Aircraft)localManeuver.jdField_actor_of_type_ComMaddoxIl2EngineActor);
  }
}