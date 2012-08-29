package com.maddox.il2.game.order;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderRejoin extends Order
{
  public OrderRejoin()
  {
    super("Rejoin");
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
        Maneuver localManeuver = (Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        if ((localAircraft.getWing() == Player().getWing()) && (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup) && (localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.rejoinToGroup(localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
        if ((isEnableVoice()) && (localAircraft != Player()) && (
          (localAircraft.getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0)))
          Voice.speakRejoin(localAircraft);
        if (OrdersTree.curOrdersTree.alone()) {
          localPilot.aggressiveWingman = false;
          localPilot.followOffset.set(200.0D, 0.0D, 20.0D);
          localPilot.set_task(5);
          localPilot.clear_stack();
          localPilot.set_maneuver(65);
        } else {
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setFormationAndScale(0, 1.0F, false);
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(1);
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.timeOutForTaskSwitch = 480;
        }
      }
    }
    Voice.setSyncMode(0);
  }
}