package com.maddox.il2.game.order;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderCover_Me extends Order
{
  public OrderCover_Me()
  {
    super("Cover_Me");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      Maneuver localManeuver = (Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)
      {
        Object localObject;
        if (OrdersTree.curOrdersTree.alone()) {
          if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 3) {
            if (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup) {
              localObject = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
              localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
              ((AirGroup)localObject).addAircraft(localAircraft);
            }
          } else {
            if ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup) && (localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.rejoinToGroup(localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
            localPilot.aggressiveWingman = false;
          }
        }
        else if ((localAircraft == Wingman()) && (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup) && (localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.rejoinToGroup(localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);

        localPilot.airClient = Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        if (localManeuver.danger != null) {
          localObject = (Maneuver)localManeuver.danger;
          localPilot.target = ((FlightModel)localObject);
          if (((Maneuver)localObject).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = ((Maneuver)localObject).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup;
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
          }

        }
        else if ((localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup) && (localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null)) {
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.clientGroup = localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(2);
        } else if ((!localPilot.isBusy()) && (localAircraft != Player())) {
          localPilot.followOffset.set(300.0D, (localAircraft.aircIndex() - 2) * 50.0F, 20.0D);
          localPilot.set_task(5);
          localPilot.clear_stack();
          localPilot.set_maneuver(65);
          localPilot.setDumbTime(60000L);
        }

        if ((!isEnableVoice()) || (localAircraft == Player()) || (
          (localAircraft.getWing() != Player().getWing()) && (localAircraft.aircIndex() != 0))) continue;
        if ((localPilot.canAttack()) || (localAircraft == Wingman())) Voice.speakCoverMe(localAircraft); else
          Voice.speakUnable(localAircraft);
      }
    }
    Voice.setSyncMode(0);
  }
}