package com.maddox.il2.game.order;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.air.Aircraft;

public class Order
{
  public static final int ATT_NORMAL = 0;
  public static final int ATT_GRAY = 1;
  public static final int ATT_BOLD = 2;
  public static final int ATT_BITS = 3;
  int attrib = 0;
  protected String name;
  protected String nameDE;
  protected Orders orders;
  protected Orders subOrders;
  protected static int cmdCounter = 0;

  public int attrib()
  {
    return this.attrib & 0x3;
  }

  public String name(int paramInt)
  {
    return paramInt == 2 ? this.nameDE : this.name;
  }
  public Aircraft Player() { return OrdersTree.curOrdersTree.Player; } 
  public Wing PlayerWing() { return OrdersTree.curOrdersTree.PlayerWing; } 
  public Squadron PlayerSquad() { return OrdersTree.curOrdersTree.PlayerSquad; } 
  public Regiment PlayerRegiment() { return OrdersTree.curOrdersTree.PlayerRegiment; } 
  public Aircraft[] CommandSet() { return OrdersTree.curOrdersTree.CommandSet; } 
  public Aircraft Wingman() {
    Aircraft localAircraft1 = Player();
    Aircraft localAircraft2;
    if (localAircraft1.aircIndex() < 3) localAircraft2 = localAircraft1.getWing().airc[(localAircraft1.aircIndex() + 1)]; else
      localAircraft2 = null;
    if ((!Actor.isAlive(localAircraft2)) || (!(localAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) || (!((Maneuver)localAircraft2.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isOk()))
    {
      return null;
    }return localAircraft2;
  }
  public void cset(Actor paramActor) { OrdersTree.curOrdersTree._cset(paramActor); } 
  public boolean isEnableVoice() { return OrdersTree.curOrdersTree._isEnableVoice(); }

  public void preRun() {
    if (this.subOrders != null) {
      if ((this.orders.order.length != 0) && (this.subOrders.order.length != 0)) {
        this.subOrders.order[0] = new Order(this.name, this.nameDE);
        this.subOrders.order[0].orders = this.subOrders;
      }
      this.subOrders.upOrders = this.orders;
      this.subOrders.run();
    }
  }

  public void run() {
  }

  public Order() {
  }

  public Order(String paramString1, String paramString2) {
    this.name = paramString1;
    this.nameDE = paramString2;
  }
  public Order(String paramString) {
    this(paramString, paramString);
  }
  public Order(String paramString, Orders paramOrders) {
    this(paramString, paramString);
    this.subOrders = paramOrders;
    this.attrib = 2;
  }
  public Order(String paramString1, String paramString2, Orders paramOrders) {
    this(paramString1, paramString2);
    this.subOrders = paramOrders;
    this.attrib = 2;
  }
}