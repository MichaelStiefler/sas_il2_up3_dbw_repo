package com.maddox.il2.net;

import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;

public class NetWing extends Wing
{
  private int indexInSquadron;

  public int aircReady()
  {
    if (Actor.isValid(this.airc[0])) return 1;
    return 0;
  }

  public int aircIndex(Aircraft paramAircraft)
  {
    if (this.airc[0] == paramAircraft) return 0;
    return -1;
  }

  public int indexInSquadron()
  {
    return this.indexInSquadron;
  }

  public void setPlane(NetAircraft paramNetAircraft, int paramInt)
  {
    int i = paramNetAircraft.getArmy();

    Aircraft localAircraft = (Aircraft)paramNetAircraft;
    this.airc[0] = localAircraft;

    localAircraft.setArmy(i);

    localAircraft.setOwner(this);
    if (localAircraft == World.getPlayerAircraft())
      World.setPlayerRegiment();
    localAircraft.preparePaintScheme(paramInt);
    localAircraft.prepareCamouflage();

    if ((Mission.isDogfight()) && (Mission.isServer())) {
      AirGroup localAirGroup = null;

      int j = AirGroupList.length(War.Groups[(i - 1 & 0x1)]);

      for (int k = 0; k < j; k++) {
        localAirGroup = AirGroupList.getGroup(War.Groups[(i - 1 & 0x1)], k);
        if (!(localAirGroup instanceof NetAirGroup)) {
          continue;
        }
        if ((localAirGroup == null) || (localAirGroup.nOfAirc >= 16))
          continue;
        localAirGroup.addAircraft(localAircraft);

        return;
      }

      if ((!(localAirGroup instanceof NetAirGroup)) || (localAirGroup.nOfAirc >= 16))
      {
        NetAirGroup localNetAirGroup = new NetAirGroup();
        localNetAirGroup.addAircraft(localAircraft);
        AirGroupList.addAirGroup(War.Groups, i - 1 & 0x1, localNetAirGroup);
      }
    }
  }

  public void destroy()
  {
    destroy(squadron());
    super.destroy();
  }

  public NetWing(Squadron paramSquadron, int paramInt) {
    this.indexInSquadron = paramInt;
    setOwner(paramSquadron);
    paramSquadron.wing[paramInt] = this;
    setArmy(paramSquadron.getArmy());
  }
}