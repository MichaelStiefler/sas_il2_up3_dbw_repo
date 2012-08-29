package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.IconDraw;

public class PAirdrome extends PPoint
{
  public static final int RUNAWAY = 0;
  public static final int TAXI = 1;
  public static final int STAY = 2;
  public static final String[] types = { "RUNAWAY", "TAXI", "STAY" };
  private int type;

  public void setType(int paramInt)
  {
    this.type = paramInt;
    setIcon();
  }
  public int type() {
    return this.type;
  }
  public PAirdrome(Path paramPath, PPoint paramPPoint, Point3d paramPoint3d, int paramInt) {
    super(paramPath, paramPPoint, paramPoint3d);
    setType(paramInt);
  }

  private void setIcon() {
    String str = null;
    switch (this.type) { case 0:
      str = "takeoff"; break;
    case 1:
      str = "normfly"; break;
    case 2:
      str = "gattack"; break;
    default:
      return;
    }
    this.icon = IconDraw.get("icons/" + str + ".mat");
  }
}