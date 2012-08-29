package com.maddox.rts;

public abstract interface CmdArea
{
  public abstract Object atom(String paramString);

  public abstract boolean setAtom(String paramString, Object paramObject);

  public abstract boolean delAtom(String paramString);

  public abstract boolean existAtom(String paramString, boolean paramBoolean);
}