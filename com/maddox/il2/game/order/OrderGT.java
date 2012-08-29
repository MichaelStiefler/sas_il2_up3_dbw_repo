package com.maddox.il2.game.order;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;

class OrderGT extends Order
{
  private Point3d Pd = new Point3d();

  public OrderGT(String paramString)
  {
    super(paramString);
  }

  public void run(int paramInt) {
    Voice.setSyncMode(1);
    for (int i = 0; i < CommandSet().length; i++) {
      Aircraft localAircraft = CommandSet()[i];
      if ((!Actor.isAlive(localAircraft)) || 
        (!(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor))) continue;
      Pilot localPilot = (Pilot)localAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      localPilot.attackGround(paramInt);
      int j = 0;
      if (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup != null) {
        this.Pd.set(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.Pos);
        if ((OrdersTree.curOrdersTree.alone()) && ((localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.grTask != 4) || (localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.gTargetPreference != paramInt)) && (((Maneuver)Player().jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup == localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup))
        {
          localObject = new AirGroup(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup);
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.delAircraft(localAircraft);
          ((AirGroup)localObject).addAircraft(localAircraft);
        }
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(paramInt);
        localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGTargMode(this.Pd, 3000.0F);
        Object localObject = localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGAttackObject(localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.numInGroup(localAircraft));
        if (localObject != null) {
          if ((isEnableVoice()) && (CommandSet()[i] != Player()))
            if ((CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0))
              Voice.speakAttackGround(CommandSet()[i]);
            else
              Voice.speakOk(CommandSet()[i]);
          localPilot.target_ground = null;
          localPilot.jdField_Group_of_type_ComMaddoxIl2AiAirAirGroup.setGroupTask(4);
          j = 1;
        }
      }
      if ((isEnableVoice()) && (CommandSet()[i] != Player()) && (j == 0)) {
        Voice.speakUnable(CommandSet()[i]);
      }
    }
    Voice.setSyncMode(0);
  }
}