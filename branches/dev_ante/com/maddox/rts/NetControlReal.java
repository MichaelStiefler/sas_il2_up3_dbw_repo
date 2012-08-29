package com.maddox.rts;

public abstract interface NetControlReal
{
  public abstract void msgNewClient(NetObj paramNetObj, int paramInt1, String paramString, int paramInt2);

  public abstract void msgAnswer(NetObj paramNetObj, int paramInt, String paramString);
}