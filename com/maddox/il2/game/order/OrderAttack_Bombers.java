package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderAttack_Bombers extends Order
{
  public OrderAttack_Bombers()
  {
    super("Attack_Bombers");
  }
  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      localPilot.targetBombers();
      int j = 0;
      if (localPilot.Group != null) {
        if (OrdersTree.curOrdersTree.alone())
          if (((localPilot.Group.grTask != 3) || (localPilot.Group.aTargetPreference != 8)) && (((Maneuver)Player().FM).Group == localPilot.Group))
          {
            localAirGroup = new AirGroup(localPilot.Group);
            localPilot.Group.delAircraft(localAircraft);
            localAirGroup.addAircraft(localAircraft);
          } else {
            localPilot.aggressiveWingman = true;
          }
        localPilot.Group.setATargMode(8);
        AirGroup localAirGroup = localPilot.Group.chooseTargetGroup();
        if (localAirGroup != null) {
          localPilot.Group.targetGroup = localAirGroup;
          localPilot.target = null;
          localPilot.Group.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackBombers(localAircraft);
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