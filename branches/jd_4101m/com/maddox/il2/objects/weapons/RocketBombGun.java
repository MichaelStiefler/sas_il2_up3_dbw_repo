package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;

public class RocketBombGun extends Interpolate
  implements BulletEmitter
{
  protected boolean ready = true;
  protected RocketBomb rocket;
  protected HookNamed hook;
  protected boolean bHide = false;
  protected boolean bRocketPosRel = true;
  protected Class bulletClass = null;
  protected int bulletsFull;
  protected int bulletss;
  protected int shotStep;
  protected float bombDelay = 0.0F;
  protected SoundFX sound;
  protected boolean bExternal = true;
  private boolean bCassette = false;
  protected float timeLife = -1.0F;
  private float plusPitch = 0.0F;
  private float plusYaw = 0.0F;
  protected float bulletMassa = 0.048F;
  private int curShotStep;
  private int curShots;
  protected boolean bTickShot;
  private static Orient _tmpOr0 = new Orient();
  private static Orient _tmpOr1 = new Orient();
  private static Loc nullLoc = new Loc();
  private static Loc loc = new Loc();
  private static final boolean DEBUG = false;
  private int counter;

  public void doDestroy()
  {
    this.ready = false;
    if (this.rocket != null) {
      this.rocket.destroy();
      this.rocket = null;
    }
  }

  private boolean nameEQ(HierMesh paramHierMesh, int paramInt1, int paramInt2) {
    if (paramHierMesh == null)
      return false;
    paramHierMesh.setCurChunk(paramInt1);
    String str1 = paramHierMesh.chunkName();
    paramHierMesh.setCurChunk(paramInt2);
    String str2 = paramHierMesh.chunkName();
    int i = Math.min(str1.length(), str2.length());
    for (int j = 0; j < i; j++) {
      int k = str1.charAt(j);
      if (k == 95)
        return true;
      if (k != str2.charAt(j))
        return false;
    }
    return true;
  }

  public BulletEmitter detach(HierMesh paramHierMesh, int paramInt) {
    if (!this.ready)
      return GunEmpty.get();
    if ((paramInt == -1) || (nameEQ(paramHierMesh, paramInt, this.hook.chunkNum()))) {
      this.bExecuted = true;
      this.ready = false;
      return GunEmpty.get();
    }
    return this;
  }

  protected int bullets() {
    return this.actor != null ? this.bulletss - this.actor.hashCode() : 0;
  }

  protected void bullets(int paramInt) {
    if (this.actor != null)
      this.bulletss = (paramInt + this.actor.hashCode());
    else
      this.bulletss = 0;
  }

  public void hide(boolean paramBoolean) {
    this.bHide = paramBoolean;
    if (this.bHide) {
      if (Actor.isValid(this.rocket))
        this.rocket.drawing(false);
    } else if (Actor.isValid(this.rocket))
      this.rocket.drawing(true);
  }

  public boolean isHide() {
    return this.bHide;
  }

  public boolean isEnablePause() {
    return false;
  }

  public boolean isPause() {
    return false;
  }

  public void setPause(boolean paramBoolean)
  {
  }

  public boolean isExternal() {
    return this.bExternal;
  }

  public boolean isCassette() {
    return this.bCassette;
  }

  public void setRocketTimeLife(float paramFloat) {
    this.timeLife = paramFloat;
  }

  public float getRocketTimeLife() {
    return this.timeLife;
  }

  public float bulletMassa()
  {
    return this.bulletMassa;
  }

  public int countBullets() {
    return bullets();
  }

  public boolean haveBullets() {
    return bullets() != 0;
  }

  public void loadBullets() {
    loadBullets(this.bulletsFull);
  }

  public void _loadBullets(int paramInt) {
    loadBullets(paramInt);
  }

  public void loadBullets(int paramInt) {
    bullets(paramInt);
    if (bullets() != 0) {
      if (!Actor.isValid(this.rocket))
        newRocket();
    } else if (Actor.isValid(this.rocket)) {
      this.rocket.destroy();
      this.rocket = null;
    }
  }

  public Class bulletClass() {
    return this.bulletClass;
  }

  public void setBulletClass(Class paramClass) {
    this.bulletClass = paramClass;
    this.bulletMassa = Property.floatValue(this.bulletClass, "massa", this.bulletMassa);
  }

  public boolean isShots() {
    return this.bExecuted;
  }

  public void shots(int paramInt, float paramFloat) {
    shots(paramInt);
  }

  public void shots(int paramInt)
  {
    if (!isHide()) {
      if ((isCassette()) && (paramInt != 0))
        paramInt = bullets();
      if ((bullets() == -1) && (paramInt == -1))
        paramInt = 25;
      if ((!this.bExecuted) && (paramInt != 0)) {
        if (bullets() != 0) {
          this.curShotStep = 0;
          this.curShots = paramInt;
          this.bExecuted = true;
        }
      } else if ((this.bExecuted) && (paramInt != 0))
        this.curShots = paramInt;
      else if ((this.bExecuted) && (paramInt == 0))
        this.bExecuted = false;
    }
  }

  protected void interpolateStep()
  {
    this.bTickShot = false;
    if (this.curShotStep == 0) {
      if ((bullets() == 0) || (this.curShots == 0) || (!Actor.isValid(this.actor))) {
        shots(0);
        return;
      }
      Object localObject;
      if ((this.actor instanceof Aircraft)) {
        localObject = ((Aircraft)this.actor).FM;
        if (((FlightModel)localObject).getOverload() < 0.0F)
          return;
      }
      this.bTickShot = true;
      if (this.rocket != null) {
        this.rocket.pos.setUpdateEnable(true);
        if ((this.plusPitch != 0.0F) || (this.plusYaw != 0.0F)) {
          this.rocket.pos.getAbs(_tmpOr0);
          _tmpOr1.setYPR(this.plusYaw, this.plusPitch, 0.0F);
          _tmpOr1.add(_tmpOr0);
          this.rocket.pos.setAbs(_tmpOr1);
        }
        this.rocket.pos.resetAsBase();
        this.rocket.start(this.timeLife);
        if (Actor.isValid(this.rocket)) {
          localObject = Property.stringValue(getClass(), "sound", null);

          if (localObject != null)
            this.rocket.newSound((String)localObject, true);
        }
        this.rocket = null;
      }
      if (this.curShots > 0)
        this.curShots -= 1;
      if (bullets() > 0)
        bullets(bullets() - 1);
      if (bullets() != 0)
        newRocket();
      this.curShotStep = this.shotStep;
    }
    this.curShotStep -= 1;
  }

  public boolean tick() {
    interpolateStep();
    return this.ready;
  }

  private void newRocket() {
    try {
      this.rocket = ((RocketBomb)this.bulletClass.newInstance());
      this.rocket.pos.setBase(this.actor, this.hook, false);
      if (this.bRocketPosRel)
        this.rocket.pos.changeHookToRel();
      this.rocket.pos.resetAsBase();
      this.rocket.visibilityAsBase(true);
      if (this.bRocketPosRel)
        this.rocket.pos.setUpdateEnable(false);
      setRocketName();
    }
    catch (Exception localException)
    {
    }
  }

  void setRocketName() {
    this.rocket.setName(getHookName() + "|" + this.counter);
    this.counter += 1;
  }

  public void setHookToRel(boolean paramBoolean) {
    if (this.bRocketPosRel != paramBoolean) {
      if (Actor.isValid(this.rocket)) {
        if (paramBoolean) {
          this.rocket.pos.changeHookToRel();
          this.rocket.pos.setUpdateEnable(false);
        } else {
          this.rocket.pos.setRel(nullLoc);
          this.rocket.pos.setBase(this.rocket.pos.base(), this.hook, false);
          this.rocket.pos.setUpdateEnable(true);
        }
      }
      this.bRocketPosRel = paramBoolean;
    }
  }

  public String getHookName() {
    return this.hook.name();
  }

  public void set(Actor paramActor, String paramString, Loc paramLoc) {
    set(paramActor, paramString);
  }

  public void set(Actor paramActor, String paramString1, String paramString2) {
    set(paramActor, paramString1);
  }

  public void set(Actor paramActor, String paramString) {
    this.actor = paramActor;
    Class localClass = getClass();
    this.bCassette = Property.containsValue(localClass, "cassette");
    this.bulletClass = ((Class)Property.value(localClass, "bulletClass", null));
    bullets(Property.intValue(localClass, "bullets", 1));
    this.bulletsFull = bullets();
    setBulletClass(this.bulletClass);
    float f = Property.floatValue(localClass, "shotFreq", 0.5F);
    if (f < 0.001F)
      f = 0.001F;
    this.shotStep = (int)((1.0F / f + Time.tickConstLenFs() / 2.0F) / Time.tickConstLenFs());

    if (this.shotStep <= 0)
      this.shotStep = 1;
    this.hook = ((HookNamed)paramActor.findHook(paramString));
    newRocket();
    String str = Property.stringValue(getClass(), "sound", null);

    if (str != null) {
      this.rocket.pos.getAbs(loc);
      loc.sub(this.actor.pos.getAbs());
      this.sound = this.actor.newSound(str, false);
      if (this.sound != null) {
        SoundFX localSoundFX = this.actor.getRootFX();
        if (localSoundFX != null) {
          this.sound.setParent(localSoundFX);
          this.sound.setPosition(loc.getPoint());
        }
      }
    }
    this.actor.interpPut(this, null, -1L, null);
  }
}