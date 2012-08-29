package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MeshShared;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

public class Pylon extends ActorMesh
  implements BulletEmitter
{
  private String hookName;
  private int chunkIndx;

  public BulletEmitter detach(HierMesh paramHierMesh, int paramInt)
  {
    if (isDestroyed())
      return GunEmpty.get();
    if ((paramInt == -1) || (paramInt == this.chunkIndx)) {
      destroy();
      return GunEmpty.get();
    }
    return this;
  }

  public boolean isEnablePause()
  {
    return false; } 
  public boolean isPause() { return false; } 
  public void setPause(boolean paramBoolean) {
  }
  public float bulletMassa() { return 0.0F; } 
  public int countBullets() {
    return 0; } 
  public boolean haveBullets() { return false; } 
  public void loadBullets() {
  }
  public void loadBullets(int paramInt) {  }

  public void _loadBullets(int paramInt) {  }

  public Class bulletClass() { return null; } 
  public void setBulletClass(Class paramClass) {
  }
  public boolean isShots() { return false; } 
  public void shots(int paramInt, float paramFloat) {
  }
  public void shots(int paramInt) {  }

  public String getHookName() { return this.hookName; }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public Pylon() {
    setMesh(MeshShared.get(Property.stringValue(getClass(), "mesh", null)));
    collide(false);
    drawing(true);
  }

  public void set(Actor paramActor, String paramString, Loc paramLoc) {
    set(paramActor, paramString);
  }

  public void set(Actor paramActor, String paramString1, String paramString2) {
    set(paramActor, paramString1);
  }

  public void set(Actor paramActor, String paramString) {
    this.hookName = paramString;
    setOwner(paramActor);
    if (paramString != null) {
      Hook localHook = paramActor.findHook(paramString);
      this.pos.setBase(paramActor, localHook, false);
      this.pos.changeHookToRel();
      this.chunkIndx = localHook.chunkNum();
    } else {
      this.pos.setBase(paramActor, null, false);
      this.chunkIndx = -1;
    }
    visibilityAsBase(true);
    this.pos.setUpdateEnable(false);
    this.pos.reset();
  }
}