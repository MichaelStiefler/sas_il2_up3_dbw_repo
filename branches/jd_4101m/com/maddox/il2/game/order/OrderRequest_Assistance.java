package com.maddox.il2.game.order;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.sounds.Voice;

class OrderRequest_Assistance extends Order
{
  Vector3d tmpV = new Vector3d();

  public OrderRequest_Assistance()
  {
    super("Request_Assistance");
  }

  public void run() {
    int i = Player().getArmy();
    Maneuver localManeuver = (Maneuver)Player().FM;
    boolean bool = false;
    if (localManeuver.Group != null) {
      int j = AirGroupList.length(com.maddox.il2.ai.War.Groups[(i - 1 & 0x1)]);
      for (int k = 0; k < j; k++) {
        AirGroup localAirGroup = AirGroupList.getGroup(com.maddox.il2.ai.War.Groups[(i - 1 & 0x1)], k);
        if ((localAirGroup == null) || (localAirGroup.nOfAirc <= 0) || 
          (!(localAirGroup.airc[0] instanceof TypeFighter)) || (localAirGroup.grTask != 1)) continue;
        this.tmpV.sub(localManeuver.Group.Pos, localAirGroup.Pos);
        if (this.tmpV.lengthSquared() < 1000000000.0D) {
          bool = true;
          localAirGroup.clientGroup = localManeuver.Group;
          localAirGroup.setGroupTask(2);
        }
      }

    }

    Voice.setSyncMode(0);
    Voice.speakHelpNeededFromBase(Player(), bool);
  }
}