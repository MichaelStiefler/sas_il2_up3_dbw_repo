package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderDrop_Tanks extends Order
{
  public OrderDrop_Tanks()
  {
    super("Drop_Tanks");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((Actor.isAlive(localAircraft)) && ((localAircraft.FM instanceof Pilot))) {
        if (((Pilot)localAircraft.FM).CT.dropFuelTanks()) {
          if ((!isEnableVoice()) || (localAircraft == Player()) || (
            (localAircraft.getWing() != Player().getWing()) && (localAircraft.aircIndex() != 0))) continue;
          Voice.speakDropTanks(localAircraft);
        } else {
          if ((!isEnableVoice()) || (localAircraft == Player()) || (
            (localAircraft.getWing() != Player().getWing()) && (localAircraft.aircIndex() != 0))) continue;
          Voice.speakUnable(localAircraft);
        }
      }
    }
    Voice.setSyncMode(0);
  }
}