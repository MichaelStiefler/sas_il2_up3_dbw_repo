package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderBreak extends Order
{
  public OrderBreak()
  {
    super("Break");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((Actor.isAlive(localAircraft)) && ((localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot))) {
        Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        if (OrdersTree.curOrdersTree.alone()) localPilot.aggressiveWingman = true;
        if (localPilot.get_task() == 2) {
          localPilot.Leader = null;
          localPilot.set_task(3);
          localPilot.push(8);
          localPilot.push(8);
          localPilot.push(8);
          localPilot.push(8);
          for (int j = 0; j < 3 - localAircraft.aircIndex(); j++) {
            localPilot.push(48);
          }
          localPilot.pop();
          localPilot.setDumbTime(0L);
          if ((!isEnableVoice()) || (localAircraft == Player()) || (
            (localAircraft.getWing() != Player().getWing()) && (localAircraft.aircIndex() != 0))) continue;
          Voice.speakBreak(localAircraft);
        }
      }
    }
    Voice.setSyncMode(0);
  }
}