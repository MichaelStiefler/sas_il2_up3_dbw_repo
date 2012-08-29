// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.game.HUD;

// Referenced classes of package com.maddox.il2.game.order:
//            OrdersTree, Order

public class Orders
{

    public void run()
    {
        if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.isLocal())
            com.maddox.il2.game.HUD.order(order);
        com.maddox.il2.game.order.OrdersTree.curOrdersTree.cur = this;
    }

    public Orders(com.maddox.il2.game.order.Order aorder[])
    {
        upOrders = null;
        order = aorder;
        for(int i = 0; i < aorder.length; i++)
            if(aorder[i] != null)
                aorder[i].orders = this;

    }

    protected com.maddox.il2.game.order.Order order[];
    protected com.maddox.il2.game.order.Orders upOrders;
}
