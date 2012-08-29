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
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if ((isEnableVoice()) && (CommandSet()[i] != Player()) && (
        (CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0)))
        Voice.speakLoosenFormation(CommandSet()[i]);
      if ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) && (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.nOfAirc > 0) && (localPilot == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel)) {
        Maneuver localManeuver = (Maneuver)localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.airc[(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.nOfAirc - 1)].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        float f = localManeuver.formationScale * 1.333F;
        if (f > 20.0F) f = 20.0F;
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setFormationAndScale(localManeuver.formationType, f, true);
      }
    }
    Voice.setSyncMode(0);
  }
}