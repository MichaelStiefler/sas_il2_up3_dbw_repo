package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;

public class GunEmpty extends Gun
{
  private static GunEmpty empty = new GunEmpty();

  public BulletEmitter detach(HierMesh paramHierMesh, int paramInt)
  {
    return this;
  }
  public void initRealisticGunnery(boolean paramBoolean) {
  }
  public boolean isEnablePause() { return false; } 
  public float bulletMassa() { return 0.0F; } 
  public float bulletSpeed() { return 0.0F; } 
  public int countBullets() { return 0; } 
  public boolean haveBullets() { return false; } 
  public void loadBullets() {  }

  public void loadBullets(int paramInt) {  }

  public Class bulletClass() { return null; } 
  public void setBulletClass(Class paramClass) {
  }
  public boolean isShots() { return false; } 
  public void shots(int paramInt, float paramFloat) {
  }
  public void shots(int paramInt) {  }

  public String getHookName() { return "Body"; } 
  public void set(Actor paramActor, String paramString, Loc paramLoc) {
  }
  public void set(Actor paramActor, String paramString1, String paramString2) {
  }
  public void set(Actor paramActor, String paramString) {
  }
  private GunEmpty() { this.flags |= 16388;
    this.pos = new ActorPosMove(this); }

  public static GunEmpty get() {
    return empty;
  }
}