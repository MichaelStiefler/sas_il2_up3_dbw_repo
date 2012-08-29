package com.maddox.il2.net;

import java.util.ArrayList;

public class NetUserLeft
{
  public static ArrayList all = new ArrayList();
  public String uniqueName;
  public int army;
  public NetUserStat stat = new NetUserStat();

  public NetUserLeft(String paramString, int paramInt, NetUserStat paramNetUserStat) {
    this.uniqueName = paramString;
    this.army = paramInt;
    this.stat.set(paramNetUserStat);
    all.add(this);
  }
}