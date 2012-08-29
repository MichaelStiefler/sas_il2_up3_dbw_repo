package com.maddox.il2.game.order;

public class OrderBack extends Order
{
  public void preRun()
  {
    if (this.jdField_orders_of_type_ComMaddoxIl2GameOrderOrders.upOrders != null)
      this.jdField_orders_of_type_ComMaddoxIl2GameOrderOrders.upOrders.run();
    else
      OrdersTree.curOrdersTree.unactivate();
  }

  public OrderBack()
  {
    this.attrib = 2;
    this.name = "back";
    this.nameDE = "back";
  }
}