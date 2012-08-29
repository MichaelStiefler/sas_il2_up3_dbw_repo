package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderAttack_Fighters extends Order
{
  public OrderAttack_Fighters()
  {
    super("Attack_Fighters");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      localPilot.targetFighters();
      int j = 0;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
        if (OrdersTree.curOrdersTree.alone())
          if (((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 3) || (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.aTargetPreference != 7)) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup))
          {
            localAirGroup = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
            localAirGroup.addAircraft(localAircraft);
          } else {
            localPilot.aggressiveWingman = true;
          }
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setATargMode(7);
        AirGroup localAirGroup = localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.chooseTargetGroup();
        if (localAirGroup != null) {
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = localAirGroup;
          localPilot.target = null;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackFighters(localAircraft);
            else
              Voice.speakOk(localAircraft);
        }
      }
      if ((isEnableVoice()) && (localAircraft != Player()) && (j == 0)) {
        Voice.speakUnable(localAircraft);
      }
    }
    Voice.setSyncMode(0);
  }
}