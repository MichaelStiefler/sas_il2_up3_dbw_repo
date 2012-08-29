package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderChange_Formation_To_4 extends Order
{
  public OrderChange_Formation_To_4()
  {
    super("C_F_FINGERFOUR");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
        if ((isEnableVoice()) && (CommandSet()[i] != Player()) && (
          (CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0)))
          Voice.speakOk(CommandSet()[i]);
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setFormationAndScale(7, localPilot.formationScale, true);
      }
    }
    Voice.setSyncMode(0);
  }
}