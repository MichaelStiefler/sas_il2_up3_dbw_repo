package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public abstract interface HunterInterface
{
  public abstract float getReloadingTime(Aim paramAim);

  public abstract float chainFireTime(Aim paramAim);

  public abstract float probabKeepSameEnemy(Actor paramActor);

  public abstract float minTimeRelaxAfterFight();

  public abstract boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim);

  public abstract void gunStartParking(Aim paramAim);

  public abstract void gunInMove(boolean paramBoolean, Aim paramAim);

  public abstract Actor findEnemy(Aim paramAim);

  public abstract int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean);

  public abstract void singleShot(Aim paramAim);

  public abstract void startFire(Aim paramAim);

  public abstract void continueFire(Aim paramAim);

  public abstract void stopFire(Aim paramAim);
}