// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OrderBack.java

package com.maddox.il2.game.order;


// Referenced classes of package com.maddox.il2.game.order:
//            Order, Orders, OrdersTree

public class OrderBack extends com.maddox.il2.game.order.Order
{

    public void preRun()
    {
        if(orders.upOrders != null)
            orders.upOrders.run();
        else
            com.maddox.il2.game.order.OrdersTree.curOrdersTree.unactivate();
    }

    public OrderBack()
    {
        attrib = 2;
        name = "back";
        nameDE = "back";
    }
}
