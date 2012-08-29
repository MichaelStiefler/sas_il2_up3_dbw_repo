// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orders.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;

// Referenced classes of package com.maddox.il2.game.order:
//            OrderAnyone_Help_Me, OrdersTree, Order

public class Orders
{

    public void run()
    {
        if(lastPlayer == null || com.maddox.il2.ai.World.getPlayerAircraft() != lastPlayer)
        {
            com.maddox.il2.game.Mission.addHayrakesToOrdersTree();
            lastPlayer = com.maddox.il2.ai.World.getPlayerAircraft();
        }
        if((order.length <= 7 || !(order[7] instanceof com.maddox.il2.game.order.OrderAnyone_Help_Me)) && com.maddox.il2.game.Main3D.cur3D().ordersTree.frequency() == null)
            com.maddox.il2.game.Main3D.cur3D().ordersTree.Player.FM.AS.setBeacon(0);
        if(com.maddox.il2.game.order.OrdersTree.curOrdersTree.isLocal())
            com.maddox.il2.game.HUD.order(order);
        com.maddox.il2.game.order.OrdersTree.curOrdersTree.cur = this;
    }

    public Orders(com.maddox.il2.game.order.Order aorder[])
    {
        upOrders = null;
        lastPlayer = null;
        order = aorder;
        for(int i = 0; i < aorder.length; i++)
            if(aorder[i] != null)
                aorder[i].orders = this;

    }

    protected com.maddox.il2.game.order.Order order[];
    protected com.maddox.il2.game.order.Orders upOrders;
    private com.maddox.il2.objects.air.Aircraft lastPlayer;
}
