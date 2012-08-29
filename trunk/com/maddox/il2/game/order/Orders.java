package com.maddox.il2.game.order;

import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;

public class Orders
{
  protected Order[] order;
  protected Orders upOrders = null;
  private Aircraft lastPlayer = null;

  public void run()
  {
    if ((this.lastPlayer == null) || (World.getPlayerAircraft() != this.lastPlayer))
    {
      Mission.addHayrakesToOrdersTree();
      this.lastPlayer = World.getPlayerAircraft();
    }

    if ((this.order.length <= 7) || (!(this.order[7] instanceof OrderAnyone_Help_Me)))
    {
      if (Main3D.cur3D().ordersTree.frequency() == null) {
        Main3D.cur3D().ordersTree.Player.FM.AS.setBeacon(0);
      }
    }

    if (OrdersTree.curOrdersTree.isLocal())
      HUD.order(this.order);
    OrdersTree.curOrdersTree.cur = this;
  }

  public Orders(Order[] paramArrayOfOrder) {
    this.order = paramArrayOfOrder;
    for (int i = 0; i < paramArrayOfOrder.length; i++)
      if (paramArrayOfOrder[i] != null)
        paramArrayOfOrder[i].orders = this;
  }
}