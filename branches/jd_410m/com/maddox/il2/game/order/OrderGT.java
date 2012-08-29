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
        (!(localAircraft.FM instanceof Pilot)) || 
        (!Actor.isAlive(localAircraft.FM.actor))) continue;
      Pilot localPilot = (Pilot)(Pilot)localAircraft.FM;
      localPilot.attackGround(paramInt);
      int j = 0;
      if (localPilot.Group != null) {
        this.Pd.set(localPilot.Group.Pos);
        if ((OrdersTree.curOrdersTree.alone()) && ((localPilot.Group.grTask != 4) || (localPilot.Group.gTargetPreference != paramInt)) && (((Maneuver)Player().FM).Group == localPilot.Group))
        {
          localObject = new AirGroup(localPilot.Group);
          localPilot.Group.delAircraft(localAircraft);
          ((AirGroup)localObject).addAircraft(localAircraft);
        }
        localPilot.Group.setGTargMode(paramInt);
        localPilot.Group.setGTargMode(this.Pd, 3000.0F);
        Object localObject = localPilot.Group.setGAttackObject(localPilot.Group.numInGroup(localAircraft));
        if (localObject != null) {
          if ((isEnableVoice()) && (CommandSet()[i] != Player()))
            if ((CommandSet()[i].getWing() == Player().getWing()) || (CommandSet()[i].aircIndex() == 0))
              Voice.speakAttackGround(CommandSet()[i]);
            else
              Voice.speakOk(CommandSet()[i]);
          localPilot.target_ground = null;
          localPilot.Group.setGroupTask(4);
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