package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;

public class BombGun extends Interpolate
  implements BulletEmitter
{
  protected boolean ready = true;
  protected Bomb bomb;
  protected boolean bHide = false;
  protected boolean bExternal = true;
  protected boolean bCassette = false;
  protected HookNamed hook;
  protected Class bulletClass = null;
  protected int bulletsFull;
  private int bulletss;
  protected int shotStep;
  protected float bombDelay = 0.0F;
  protected SoundFX sound;
  protected float bulletMassa = 0.048F;
  private int curShotStep;
  private int curShots;
  protected boolean bTickShot;
  protected int numBombs = 0;

  private static Loc loc = new Loc();

  public void doDestroy()
  {
    this.ready = false;
    if (this.bomb != null) { this.bomb.destroy(); this.bomb = null; }
  }

  private boolean nameEQ(HierMesh paramHierMesh, int paramInt1, int paramInt2) {
    if (paramHierMesh == null) return false;

    paramHierMesh.setCurChunk(paramInt1); String str1 = paramHierMesh.chunkName();
    paramHierMesh.setCurChunk(paramInt2); String str2 = paramHierMesh.chunkName();

    int j = Math.min(str1.length(), str2.length());
    for (int i = 0; i < j; i++) {
      int k = str1.charAt(i);
      if (k == 95) return true;
      if (k != str2.charAt(i)) return false;
    }
    return true;
  }

  public BulletEmitter detach(HierMesh paramHierMesh, int paramInt) {
    if (!this.ready)
      return GunEmpty.get();
    if ((paramInt == -1) || (nameEQ(paramHierMesh, paramInt, this.hook.chunkNum()))) {
      this.ready = false;
      this.jdField_bExecuted_of_type_Boolean = true;
      return GunEmpty.get();
    }
    return this;
  }

  protected int bullets()
  {
    return this.jdField_actor_of_type_ComMaddoxIl2EngineActor != null ? this.bulletss - this.jdField_actor_of_type_ComMaddoxIl2EngineActor.hashCode() : 0; } 
  protected void bullets(int paramInt) { if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != null) this.bulletss = (paramInt + this.jdField_actor_of_type_ComMaddoxIl2EngineActor.hashCode()); else this.bulletss = 0;
  }

  public void hide(boolean paramBoolean)
  {
    this.bHide = paramBoolean;
    if (this.bHide) {
      if ((Actor.isValid(this.bomb)) && (this.bExternal))
        this.bomb.drawing(false);
    }
    else if ((Actor.isValid(this.bomb)) && (this.bExternal))
      this.bomb.drawing(true); 
  }

  public boolean isHide() {
    return this.bHide;
  }
  public boolean isEnablePause() { return false; } 
  public boolean isPause() { return false; } 
  public void setPause(boolean paramBoolean) {
  }
  public void setBombDelay(float paramFloat) {
    this.bombDelay = paramFloat;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }

  public boolean isExternal() {
    return this.bExternal;
  }
  public boolean isCassette() {
    return this.bCassette;
  }

  public float bulletMassa()
  {
    return this.bulletMassa;
  }
  public int countBullets() { return bullets(); } 
  public boolean haveBullets() { return bullets() != 0; } 
  public void loadBullets() { loadBullets(this.bulletsFull); } 
  public void _loadBullets(int paramInt) {
    loadBullets(paramInt);
  }
  public void loadBullets(int paramInt) {
    bullets(paramInt);
    if (bullets() != 0) {
      if (!Actor.isValid(this.bomb))
        newBomb();
    }
    else if (Actor.isValid(this.bomb)) {
      this.bomb.destroy();
      this.bomb = null;
    }
  }

  public Class bulletClass() {
    return this.bulletClass;
  }
  public void setBulletClass(Class paramClass) { this.bulletClass = paramClass;
    this.bulletMassa = Property.floatValue(this.bulletClass, "massa", this.bulletMassa);
  }

  public boolean isShots()
  {
    return this.jdField_bExecuted_of_type_Boolean;
  }

  public void shots(int paramInt, float paramFloat) {
    shots(paramInt);
  }

  public void shots(int paramInt)
  {
    if (isHide()) return;
    if ((isCassette()) && (paramInt != 0)) paramInt = bullets();
    if ((bullets() == -1) && (paramInt == -1)) paramInt = 25;

    if ((!this.jdField_bExecuted_of_type_Boolean) && (paramInt != 0))
    {
      if (bullets() == 0)
        return;
      if ((this.bomb instanceof FuelTank)) bullets(1);
      this.curShotStep = 0;
      this.curShots = paramInt;
      this.jdField_bExecuted_of_type_Boolean = true;
    } else if ((this.jdField_bExecuted_of_type_Boolean) && (paramInt != 0))
    {
      this.curShots = paramInt;
    } else if ((this.jdField_bExecuted_of_type_Boolean) && (paramInt == 0))
    {
      this.jdField_bExecuted_of_type_Boolean = false;
    }
  }

  protected void interpolateStep()
  {
    this.bTickShot = false;
    if (this.curShotStep == 0) {
      if ((bullets() == 0) || (this.curShots == 0) || (!Actor.isValid(this.jdField_actor_of_type_ComMaddoxIl2EngineActor))) {
        shots(0);
        return;
      }
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Aircraft)) {
        FlightModel localFlightModel = ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).FM;
        if (localFlightModel.getOverload() < 0.0F)
          return;
      }
      this.bTickShot = true;
      if (this.bomb != null) {
        this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(true);
        this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
        this.bomb.start();

        if (this.sound != null) this.sound.play();

        this.bomb = null;
      }
      if (this.curShots > 0) this.curShots -= 1;
      if (bullets() > 0) bullets(bullets() - 1);
      if (bullets() != 0) newBomb();
      this.curShotStep = this.shotStep;
    }
    this.curShotStep -= 1;
  }

  public boolean tick() {
    interpolateStep();
    return this.ready;
  }

  private void newBomb()
  {
    try
    {
      this.bomb = ((Bomb)this.bulletClass.newInstance());
      this.bomb.index = (this.numBombs++);
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.hook, false);
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
      if (!this.bExternal) this.bomb.drawing(false); else
        this.bomb.visibilityAsBase(true);
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(false);
      this.bomb.delayExplosion = this.bombDelay;
    } catch (Exception localException) {
    }
  }

  public String getHookName() {
    return this.hook.name();
  }

  public void set(Actor paramActor, String paramString, Loc paramLoc)
  {
    set(paramActor, paramString);
  }

  public void set(Actor paramActor, String paramString1, String paramString2)
  {
    set(paramActor, paramString1);
  }

  public void set(Actor paramActor, String paramString)
  {
    this.jdField_actor_of_type_ComMaddoxIl2EngineActor = paramActor;
    Class localClass = getClass();

    this.bExternal = Property.containsValue(localClass, "external");
    this.bCassette = Property.containsValue(localClass, "cassette");

    this.bulletClass = ((Class)Property.value(localClass, "bulletClass", null));
    bullets(Property.intValue(localClass, "bullets", 1));
    this.bulletsFull = bullets();

    setBulletClass(this.bulletClass);

    float f = Property.floatValue(localClass, "shotFreq", 0.5F);
    if (f < 0.001F) f = 0.001F;
    this.shotStep = (int)((1.0F / f + Time.tickConstLenFs() / 2.0F) / Time.tickConstLenFs());
    if (this.shotStep <= 0) this.shotStep = 1;

    this.hook = ((HookNamed)paramActor.findHook(paramString));
    newBomb();

    String str = Property.stringValue(getClass(), "sound", null);
    if (str != null) {
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(loc);
      loc.sub(this.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());

      this.sound = this.jdField_actor_of_type_ComMaddoxIl2EngineActor.newSound(str, false);
      if (this.sound != null) {
        SoundFX localSoundFX = this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getRootFX();
        if (localSoundFX != null) {
          this.sound.setParent(localSoundFX);
          this.sound.setPosition(loc.getPoint());
        }
      }
    }

    this.jdField_actor_of_type_ComMaddoxIl2EngineActor.interpPut(this, null, -1L, null);
  }
}