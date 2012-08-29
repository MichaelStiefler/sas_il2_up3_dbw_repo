package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderLoosen_Formation extends Order
{
  public OrderLoosen_Formation()
  {
    super("Loosen_Formation");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      if ((isEnableVoice()) && (CommandSet()[i] != Player()) && (
        (CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0)))
        Voice.speakLoosenFormation(CommandSet()[i]);
      if ((localPilot.Group != null) && (localPilot.Group.nOfAirc > 0) && (localPilot == localPilot.Group.airc[0].FM)) {
        Maneuver localManeuver = (Maneuver)localPilot.Group.airc[(localPilot.Group.nOfAirc - 1)].FM;
        float f = localManeuver.formationScale * 1.333F;
        if (f > 20.0F) f = 20.0F;
        localPilot.Group.setFormationAndScale(localManeuver.formationType, f, true);
      }
    }
    Voice.setSyncMode(0);
  }
}