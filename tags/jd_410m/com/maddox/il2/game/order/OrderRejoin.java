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
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      if (localPilot.Group != null) {
        Maneuver localManeuver = (Maneuver)Player().FM;
        if ((localAircraft.getWing() == Player().getWing()) && (localPilot.Group != localManeuver.Group) && (localManeuver.Group != null)) localPilot.Group.rejoinToGroup(localManeuver.Group);
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
          localPilot.Group.setFormationAndScale(0, 1.0F, false);
          localPilot.Group.setGroupTask(1);
          localPilot.Group.timeOutForTaskSwitch = 480;
        }
      }
    }
    Voice.setSyncMode(0);
  }
}