package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public abstract interface UnitInterface
{
  public abstract void startMove();

  public abstract void forceReaskMove();

  public abstract UnitInPackedForm Pack();

  public abstract UnitData GetUnitData();

  public abstract float HeightAboveLandSurface();

  public abstract float SpeedAverage();

  public abstract float BestSpace();

  public abstract float CommandInterval();

  public abstract float StayInterval();

  public abstract void absoluteDeath(Actor paramActor);
}