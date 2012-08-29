package com.maddox.il2.net;

import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.rts.ObjState;

public class NetWing extends Wing
{
  private int indexInSquadron;

  public int aircReady()
  {
    if (Actor.isValid(this.jdField_airc_of_type_ArrayOfComMaddoxIl2ObjectsAirAircraft[0])) return 1;
    return 0;
  }

  public int aircIndex(Aircraft paramAircraft)
  {
    if (this.jdField_airc_of_type_ArrayOfComMaddoxIl2ObjectsAirAircraft[0] == paramAircraft) return 0;
    return -1;
  }

  public int indexInSquadron()
  {
    return this.indexInSquadron;
  }

  public void setPlane(NetAircraft paramNetAircraft, int paramInt) {
    Aircraft localAircraft = (Aircraft)paramNetAircraft;
    this.jdField_airc_of_type_ArrayOfComMaddoxIl2ObjectsAirAircraft[0] = localAircraft;
    localAircraft.setArmy(getArmy());
    localAircraft.setOwner(this);
    if (localAircraft == World.getPlayerAircraft())
      World.setPlayerRegiment();
    localAircraft.preparePaintScheme(paramInt);
    localAircraft.prepareCamouflage();
  }

  public void destroy() {
    ObjState.destroy(squadron());
    super.destroy();
  }

  public NetWing(Squadron paramSquadron, int paramInt) {
    this.indexInSquadron = paramInt;
    setOwner(paramSquadron);
    paramSquadron.wing[paramInt] = this;
    setArmy(paramSquadron.getArmy());
  }
}