// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Order.java

package com.maddox.il2.game.order;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;

// Referenced classes of package com.maddox.il2.game.order:
//            OrdersTree, Orders

public class Order
{

    public int attrib()
    {
        return attrib & 3;
    }

    public java.lang.String name(int i)
    {
        return i != 2 ? name : nameDE;
    }

    public com.maddox.il2.objects.air.Aircraft Player()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree.Player;
    }

    public com.maddox.il2.ai.Wing PlayerWing()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree.PlayerWing;
    }

    public com.maddox.il2.ai.Squadron PlayerSquad()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree.PlayerSquad;
    }

    public com.maddox.il2.ai.Regiment PlayerRegiment()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree.PlayerRegiment;
    }

    public com.maddox.il2.objects.air.Aircraft[] CommandSet()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree.CommandSet;
    }

    public com.maddox.il2.objects.air.Aircraft Wingman()
    {
        com.maddox.il2.objects.air.Aircraft aircraft = Player();
        com.maddox.il2.objects.air.Aircraft aircraft1;
        if(aircraft.aircIndex() < 3)
            aircraft1 = aircraft.getWing().airc[aircraft.aircIndex() + 1];
        else
            aircraft1 = null;
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft1) || !(aircraft1.FM instanceof com.maddox.il2.ai.air.Maneuver) || !((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).isOk())
            return null;
        else
            return aircraft1;
    }

    public void cset(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.game.order.OrdersTree.curOrdersTree._cset(actor);
    }

    public boolean isEnableVoice()
    {
        return com.maddox.il2.game.order.OrdersTree.curOrdersTree._isEnableVoice();
    }

    public void preRun()
    {
        if(subOrders != null)
        {
            if(orders.order.length != 0 && subOrders.order.length != 0)
            {
                subOrders.order[0] = new Order(name, nameDE);
                subOrders.order[0].orders = subOrders;
            }
            subOrders.upOrders = orders;
            subOrders.run();
        }
    }

    public void run()
    {
    }

    public Order()
    {
        attrib = 0;
    }

    public Order(java.lang.String s, java.lang.String s1)
    {
        attrib = 0;
        name = s;
        nameDE = s1;
    }

    public Order(java.lang.String s)
    {
        this(s, s);
    }

    public Order(java.lang.String s, com.maddox.il2.game.order.Orders orders1)
    {
        this(s, s);
        subOrders = orders1;
        attrib = 2;
    }

    public Order(java.lang.String s, java.lang.String s1, com.maddox.il2.game.order.Orders orders1)
    {
        this(s, s1);
        subOrders = orders1;
        attrib = 2;
    }

    public static final int ATT_NORMAL = 0;
    public static final int ATT_GRAY = 1;
    public static final int ATT_BOLD = 2;
    public static final int ATT_BITS = 3;
    int attrib;
    protected java.lang.String name;
    protected java.lang.String nameDE;
    protected com.maddox.il2.game.order.Orders orders;
    protected com.maddox.il2.game.order.Orders subOrders;
    protected static int cmdCounter = 0;

}
