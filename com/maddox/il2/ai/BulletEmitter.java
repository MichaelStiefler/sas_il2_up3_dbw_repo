package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;

public abstract interface BulletEmitter
{
  public abstract int countBullets();

  public abstract boolean haveBullets();

  public abstract void loadBullets();

  public abstract void loadBullets(int paramInt);

  public abstract void _loadBullets(int paramInt);

  public abstract boolean isEnablePause();

  public abstract boolean isPause();

  public abstract void setPause(boolean paramBoolean);

  public abstract float bulletMassa();

  public abstract boolean isShots();

  public abstract void shots(int paramInt);

  public abstract void shots(int paramInt, float paramFloat);

  public abstract BulletEmitter detach(HierMesh paramHierMesh, int paramInt);

  public abstract String getHookName();

  public abstract void set(Actor paramActor, String paramString, Loc paramLoc);

  public abstract void set(Actor paramActor, String paramString1, String paramString2);

  public abstract void set(Actor paramActor, String paramString);
}