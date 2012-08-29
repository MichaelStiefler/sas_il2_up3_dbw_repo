package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderAnyone_Help_Me extends Order
{
  public OrderAnyone_Help_Me()
  {
    super("Anyone_Help_Me");
  }
  public void run() {
    cset(PlayerSquad());
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
        Maneuver localManeuver = (Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        localPilot.airClient = Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
        int j = 0;
        Object localObject;
        if (localManeuver.danger != null) {
          localObject = (Maneuver)localManeuver.danger;
          localPilot.target = ((FlightModel)localObject);
          if (((Maneuver)localObject).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
            if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()) && (
              (localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0)))
              Voice.speakAttackFighters(localAircraft);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = ((Maneuver)localObject).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup;
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
            j = 1;
          }

        }
        else if (localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
          localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setATargMode(7);
          localObject = localManeuver.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.chooseTargetGroup();
          if (localObject != null) {
            if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()) && (
              (localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0)))
              Voice.speakAttackFighters(localAircraft);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = ((AirGroup)localObject);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
            j = 1;
          }
        }

        if ((isEnableVoice()) && (localAircraft != Player()) && (j == 0))
          Voice.speakYouAreClear(CommandSet()[i]);
      }
    }
    Voice.setSyncMode(0);
  }
}