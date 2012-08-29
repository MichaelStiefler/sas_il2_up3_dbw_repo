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
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      localPilot.targetAll();
      int j = 0;
      if (localPilot.Group != null) {
        this.Pd.set(localPilot.Group.Pos);
        if (OrdersTree.curOrdersTree.alone())
          if ((localPilot.Group.grTask != 3) && (((Maneuver)Player().FM).Group == localPilot.Group)) {
            localAirGroup = new AirGroup(localPilot.Group);
            localPilot.Group.delAircraft(localAircraft);
            localAirGroup.addAircraft(localAircraft);
          } else {
            localPilot.aggressiveWingman = true;
          }
        localPilot.Group.setATargMode(9);
        AirGroup localAirGroup = localPilot.Group.chooseTargetGroup();
        if (localAirGroup != null) {
          localPilot.Group.targetGroup = localAirGroup;
          localPilot.Group.setGroupTask(3);
          j = 1;
          if ((isEnableVoice()) && (localAircraft != Player()) && (localPilot.canAttack()))
            if ((localAircraft.getWing() == Player().getWing()) || (localAircraft.aircIndex() == 0))
              Voice.speakTargetAll(localAircraft);
            else
              Voice.speakOk(localAircraft);
        }
        if (j == 0) {
          if ((OrdersTree.curOrdersTree.alone()) && 
            (localPilot.Group.grTask != 4) && (((Maneuver)Player().FM).Group == localPilot.Group)) {
            localObject = new AirGroup(localPilot.Group);
            localPilot.Group.delAircraft(localAircraft);
            ((AirGroup)localObject).addAircraft(localAircraft);
          }

          localPilot.Group.setGTargMode(0);
          localPilot.Group.setGTargMode(this.Pd, 5000.0F);
          Object localObject = localPilot.Group.setGAttackObject(localPilot.Group.numInGroup(localAircraft));
          if (localObject != null) {
            localPilot.Group.setGroupTask(4);
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