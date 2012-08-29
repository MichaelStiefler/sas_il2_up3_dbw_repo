package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

public class OrderAnyone_Help_Me extends Order
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
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      if (localPilot.Group != null) {
        Maneuver localManeuver = (Maneuver)Player().FM;
        localPilot.airClient = Player().FM;
        int j = 0;
        Object localObject;
        if (localManeuver.danger != null) {
          localObject = (Maneuver)localManeuver.danger;
          localPilot.target = ((FlightModel)localObject);
          if (((Maneuver)localObject).Group != null) {
            if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()) && (
              (localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0)))
              Voice.speakAttackFighters(localAircraft);
            localPilot.Group.targetGroup = ((Maneuver)localObject).Group;
            localPilot.Group.setGroupTask(3);
            j = 1;
          }

        }
        else if (localManeuver.Group != null) {
          localManeuver.Group.setATargMode(7);
          localObject = localManeuver.Group.chooseTargetGroup();
          if (localObject != null) {
            if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()) && (
              (localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0)))
              Voice.speakAttackFighters(localAircraft);
            localPilot.Group.targetGroup = ((AirGroup)localObject);
            localPilot.Group.setGroupTask(3);
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