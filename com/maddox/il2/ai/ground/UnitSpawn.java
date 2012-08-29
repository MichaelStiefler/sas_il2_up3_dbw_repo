package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public abstract interface UnitSpawn
{
  public abstract Actor unitSpawn(int paramInt1, int paramInt2, Actor paramActor);

  public abstract Class unitClass();
}