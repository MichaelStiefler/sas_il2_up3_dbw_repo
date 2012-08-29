package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderTarget_All extends Order
{
  private Point3d Pd = new Point3d();

  public OrderTarget_All()
  {
    super("Target_All");
  }

  public void run() {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      localPilot.targetAll();
      int j = 0;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
        this.Pd.set(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.Pos);
        if (OrdersTree.curOrdersTree.alone())
          if ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 3) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup)) {
            localAirGroup = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
            localAirGroup.addAircraft(localAircraft);
          } else {
            localPilot.aggressiveWingman = true;
          }
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setATargMode(9);
        AirGroup localAirGroup = localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.chooseTargetGroup();
        if (localAirGroup != null) {
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.targetGroup = localAirGroup;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakTargetAll(localAircraft);
            else
              Voice.speakOk(localAircraft);
        }
        if (j == 0) {
          if ((OrdersTree.curOrdersTree.alone()) && 
            (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 4) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup)) {
            localObject = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
            ((AirGroup)localObject).addAircraft(localAircraft);
          }

          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(0);
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(this.Pd, 5000.0F);
          Object localObject = localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGAttackObject(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup(localAircraft));
          if (localObject != null) {
            localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(4);
            j = 1;
            if ((isEnableVoice()) && (localAircraft != Player()))
              if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
                Voice.speakTargetAll(localAircraft);
              else
                Voice.speakOk(localAircraft);
          }
        }
      }
      if ((isEnableVoice()) && (localAircraft != Player()) && (j == 0)) {
        Voice.speakUnable(localAircraft);
      }
    }
    Voice.setSyncMode(0);
  }
}