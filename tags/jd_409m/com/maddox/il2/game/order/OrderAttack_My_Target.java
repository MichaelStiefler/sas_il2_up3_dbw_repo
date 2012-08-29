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
      Maneuver localManeuver = (Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (localManeuver.target != null) localActor = localManeuver.target.jdField_actor_of_type_ComMaddoxIl2EngineActor;
      else if (localManeuver.target_ground != null) localActor = localManeuver.target_ground;
    }

    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      int j = 0;
      Object localObject;
      if ((localActor != null) && ((localActor instanceof Aircraft))) {
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setATargMode(9);
        localObject = ((Maneuver)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup;
        if ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) && (localObject != null)) {
          if (OrdersTree.curOrdersTree.alone())
            if ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 3) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup)) {
              AirGroup localAirGroup = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
              localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
              localAirGroup.addAircraft(localAircraft);
            } else {
              localPilot.aggressiveWingman = true;
            }
          localPilot.targetAll();
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = ((AirGroup)localObject);
          localPilot.target = null;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackMyTarget(localAircraft);
            else
              Voice.speakOk(localAircraft);
        }
      } else if (localActor != null) {
        if ((OrdersTree.curOrdersTree.alone()) && 
          (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 4) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup)) {
          localObject = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
          ((AirGroup)localObject).addAircraft(localAircraft);
        }

        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(0);
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(localActor);
        localObject = localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGAttackObject(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup(localAircraft));
        if (localObject != null) {
          if ((isEnableVoice()) && (localAircraft != Player()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakAttackMyTarget(localAircraft);
            else
              Voice.speakOk(localAircraft);
          localPilot.target_ground = null;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(4);
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