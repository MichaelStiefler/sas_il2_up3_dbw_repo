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
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      Maneuver localManeuver = (Maneuver)Player().FM;
      if (localPilot.Group != null)
      {
        Object localObject;
        if (OrdersTree.curOrdersTree.alone()) {
          if (localPilot.Group.grTask != 3) {
            if (((Maneuver)Player().FM).Group == localPilot.Group) {
              localObject = new AirGroup(localPilot.Group);
              localPilot.Group.delAircraft(localAircraft);
              ((AirGroup)localObject).addAircraft(localAircraft);
            }
          } else {
            if ((localPilot.Group != localManeuver.Group) && (localManeuver.Group != null)) localPilot.Group.rejoinToGroup(localManeuver.Group);
            localPilot.aggressiveWingman = false;
          }
        }
        else if ((localAircraft == Wingman()) && (localPilot.Group != localManeuver.Group) && (localManeuver.Group != null)) localPilot.Group.rejoinToGroup(localManeuver.Group);

        localPilot.airClient = Player().FM;
        if (localManeuver.danger != null) {
          localObject = (Maneuver)localManeuver.danger;
          localPilot.target = ((FlightModel)localObject);
          if (((Maneuver)localObject).Group != null) {
            localPilot.Group.targetGroup = ((Maneuver)localObject).Group;
            localPilot.Group.setGroupTask(3);
          }

        }
        else if ((localManeuver.Group != localPilot.Group) && (localManeuver.Group != null)) {
          localPilot.Group.clientGroup = localManeuver.Group;
          localPilot.Group.setGroupTask(2);
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