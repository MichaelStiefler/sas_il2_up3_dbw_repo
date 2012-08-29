package com.maddox.rts;

public abstract interface ConsoleOut
{
  public abstract void type(String paramString);

  public abstract void flush();
}