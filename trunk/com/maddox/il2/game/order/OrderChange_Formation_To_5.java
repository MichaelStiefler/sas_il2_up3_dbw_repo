package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderChange_Formation_To_5 extends Order
{
  public OrderChange_Formation_To_5()
  {
    super("C_F_DIAMOND");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      if (localPilot.Group != null) {
        if ((isEnableVoice()) && (CommandSet()[i] != Player()) && (
          (CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0)))
          Voice.speakOk(CommandSet()[i]);
        localPilot.Group.setFormationAndScale(8, localPilot.formationScale, true);
      }
    }
    Voice.setSyncMode(0);
  }
}