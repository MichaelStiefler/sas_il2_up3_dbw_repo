package com.maddox.il2.game.order;

import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderAttack_My_Target extends Order
{
  public OrderAttack_My_Target()
  {
    super("Attack_My_Target");
  }
  public void run() {
    Actor localActor = Selector.getTarget();
    if (localActor == null) {
      Maneuver localManeuver = (Maneuver)Player().FM;
      if (localManeuver.target != null) localActor = localManeuver.target.actor;
      else if (localManeuver.target_ground != null) localActor = localManeuver.target_ground;
    }

    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      int j = 0;
      Object localObject;
      if ((localActor != null) && ((localActor instanceof Aircraft))) {
        localPilot.Group.setATargMode(9);
        localObject = ((Maneuver)((Aircraft)localActor).FM).Group;
        if ((localPilot.Group != null) && (localObject != null)) {
          if (OrdersTree.curOrdersTree.alone())
            if ((localPilot.Group.grTask != 3) && (((Maneuver)Player().FM).Group == localPilot.Group)) {
              AirGroup localAirGroup = new AirGroup(localPilot.Group);
              localPilot.Group.delAircraft(localAircraft);
              localAirGroup.addAircraft(localAircraft);
            } else {
              localPilot.aggressiveWingman = true;
            }
          localPilot.targetAll();
          localPilot.Group.targetGroup = ((AirGroup)localObject);
          localPilot.target = null;
          localPilot.Group.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackMyTarget(localAircraft);
            else
              Voice.speakOk(localAircraft);
        }
      } else if (localActor != null) {
        if ((OrdersTree.curOrdersTree.alone()) && 
          (localPilot.Group.grTask != 4) && (((Maneuver)Player().FM).Group == localPilot.Group)) {
          localObject = new AirGroup(localPilot.Group);
          localPilot.Group.delAircraft(localAircraft);
          ((AirGroup)localObject).addAircraft(localAircraft);
        }

        localPilot.Group.setGTargMode(0);
        localPilot.Group.setGTargMode(localActor);
        localObject = localPilot.Group.setGAttackObject(localPilot.Group.numInGroup(localAircraft));
        if (localObject != null) {
          if ((isEnableVoice()) && (localAircraft != Player()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackMyTarget(localAircraft);
            else
              Voice.speakOk(localAircraft);
          localPilot.target_ground = null;
          localPilot.Group.setGroupTask(4);
          j = 1;
        }
      }
      if ((isEnableVoice()) && (localAircraft != Player()) && (j == 0)) {
        Voice.speakUnable(localAircraft);
      }
    }
    Voice.setSyncMode(0);
  }
}