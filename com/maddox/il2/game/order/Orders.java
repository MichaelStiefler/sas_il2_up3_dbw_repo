package com.maddox.il2.game.order;

import com.maddox.il2.game.HUD;

public class Orders
{
  protected Order[] order;
  protected Orders upOrders = null;

  public void run() {
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