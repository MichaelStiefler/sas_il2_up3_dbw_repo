package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

public abstract class GunGeneric extends Actor
{
  public GunProperties prop;
  protected float _shotStep;
  private String hookName;
  protected int chunkIndx;
  protected static Random sndRand = new Random();
  protected MeshShared fireMesh;
  protected Eff3DActor fire;
  protected Eff3DActor smoke;
  protected Eff3DActor shells;
  protected Eff3DActor sprite;
  protected LightPointActor light;
  protected SoundFX sound;
  protected MeshShared[] _bulletTraceMesh;
  protected float[] _bulletTraceMeshLen;
  private int bulletss;
  protected int bulletNum = 0;
  protected boolean bLighting = false;
  protected boolean bPause = false;
  public float[] bulletKV;
  public float[] bulletAG;
  private boolean bStarted = false;
  private int curSkipTicks;
  private double curShotStep;
  private int curShots;
  private long lastShotTime = 0L;
  protected boolean bTickShot;
  protected Interpolater interpolater = new Interpolater();

  private static Loc loc = new Loc();
  private static Loc linit = new Loc();

  public String prop_fireMesh()
  {
    return this.prop.fireMesh; } 
  public String prop_fire() { return this.prop.fire; } 
  public String prop_sprite() { return this.prop.sprite;
  }

  public float bulletTraceMeshLen(int paramInt)
  {
    return this._bulletTraceMeshLen[paramInt];
  }
  protected int bullets() {
    return this.bulletss - hashCode(); } 
  protected void bullets(int paramInt) { this.bulletss = (paramInt + hashCode());
  }

  public void destroy()
  {
    if (isDestroyed())
      return;
    this.fireMesh = null;
    Eff3DActor.finish(this.fire); this.fire = null;
    Eff3DActor.finish(this.smoke); this.smoke = null;
    Eff3DActor.finish(this.shells); this.shells = null;
    Eff3DActor.finish(this.sprite); this.sprite = null;
    if (this.sound != null) {
      this.sound.cancel();
      this.sound = null;
    }
    bullets(0);
    if (this.light != null) {
      this.light.light.setEmit(0.0F, this.prop.emitR);
      this.bLighting = false;
    }

    super.destroy();
  }

  public String getHookName() {
    return this.hookName;
  }
  public boolean isEnablePause() { return this.prop.bEnablePause; } 
  public boolean isPause() { return this.bPause; } 
  public void setPause(boolean paramBoolean) {
    if (isEnablePause())
      this.bPause = paramBoolean; 
  }

  public float bulletMassa() {
    return this.prop.bullet[0].massa; } 
  public float bulletSpeed() { return this.prop.bullet[0].speed; } 
  public int countBullets() { return bullets(); } 
  public boolean haveBullets() { return bullets() != 0; } 
  public void loadBullets() { loadBullets(this.prop.bullets); } 
  public void loadBullets(int paramInt) {
    if ((isDestroyed()) || (!Actor.isValid(getOwner()))) {
      bullets(0);
      return;
    }
    if (getOwner().isNetMirror())
      bullets(-1);
    else
      bullets(paramInt); 
  }

  public void _loadBullets(int paramInt) {
    if ((isDestroyed()) || (!Actor.isValid(getOwner()))) {
      bullets(0);
      return;
    }
    bullets(paramInt);
  }

  public boolean isShots()
  {
    return this.interpolater.bExecuted;
  }

  public void shots(int paramInt)
  {
    shots(paramInt, 0.0F);
  }

  public void shots(int paramInt, float paramFloat)
  {
    if ((!this.interpolater.bExecuted) && (paramInt != 0))
    {
      if (bullets() == 0)
        return;
      if (isPause())
        return;
      this.curSkipTicks = (int)((paramFloat + Time.tickConstLenFs() / 2.0F) / Time.tickConstLenFs());
      long l = this.lastShotTime + ()(this._shotStep * 1000.0F) - Time.current();
      if (l > 0L) {
        int i = (int)((l + Time.tickConstLen() / 2) / Time.tickConstLen());
        if (this.curSkipTicks < i)
          this.curSkipTicks = i;
      }
      this.curShotStep = 0.0D;
      this.curShots = paramInt;
      this.interpolater.bExecuted = true;
      if (this.prop.bUseHookAsRel) {
        this.pos.setUpdateEnable(true);
        this.pos.resetAsBase();
      }
    } else if ((this.interpolater.bExecuted) && (paramInt != 0))
    {
      this.curShots = paramInt;
    } else if ((this.interpolater.bExecuted) && (paramInt == 0))
    {
      if (this.bStarted) {
        doEffects(false);
        this.bStarted = false;
      }
      this.interpolater.bExecuted = false;
      if (this.prop.bUseHookAsRel)
        this.pos.setUpdateEnable(false);
    }
  }

  public abstract void doStartBullet(double paramDouble);

  public boolean getTickShot()
  {
    return this.bTickShot;
  }

  protected void interpolateStep() {
    this.bTickShot = false;
    double d = Time.tickLenFs();
    while (this.curShotStep < d) {
      if ((bullets() == 0) || (this.curShots == 0) || (!Actor.isValid(getOwner())))
      {
        shots(0);
        return;
      }
      this.bTickShot = true;

      doStartBullet(this.curShotStep / d);

      this.lastShotTime = Time.current();
      this.bLighting = true;
      if (this.prop.bCannon) {
        fireCannon();
      }
      this.bulletNum += 1;
      if (this.curShots > 0) {
        this.curShots -= this.prop.bulletsCluster;
        if (this.curShots < 0) this.curShots = 0;
      }
      if (bullets() > 0) {
        bullets(bullets() - this.prop.bulletsCluster);
        if (bullets() < 0) bullets(0);
      }

      this.curShotStep += this._shotStep;
    }
    this.curShotStep -= d;
  }

  public void doEffects(boolean paramBoolean)
  {
    if (!paramBoolean) {
      Eff3DActor.setIntesity(this.fire, 0.0F);
      Eff3DActor.setIntesity(this.smoke, 0.0F);
      Eff3DActor.setIntesity(this.shells, 0.0F);
      if (this.sprite != null) this.sprite.drawing(false);
      if (!this.prop.bCannon)
        doSound(false);
    } else {
      Eff3DActor.setIntesity(this.fire, 1.0F);
      Eff3DActor.setIntesity(this.smoke, 1.0F);
      Eff3DActor.setIntesity(this.shells, 1.0F);
      if (this.sprite != null) this.sprite.drawing(true);
      if (!this.prop.bCannon)
        doSound(true);
    }
  }

  protected void fireCannon()
  {
    if (prop_fire() != null) {
      Eff3DActor.New(this.pos, 1.0F, prop_fire(), -1.0F);
    }
    if (this.prop.smoke != null) {
      Eff3DActor.New(this.pos, 1.0F, this.prop.smoke, -1.0F);
    }
    doSound(true);
  }

  public GunProperties createProperties()
  {
    return new GunProperties();
  }

  public void createdProperties() {
    if ((this.prop.fireMesh != null) && (this.prop.fireMeshDay == null))
      this.prop.fireMeshDay = this.prop.fireMesh;
    if ((this.prop.fire != null) && (this.prop.fireDay == null))
      this.prop.fireDay = this.prop.fire;
    if ((this.prop.sprite != null) && (this.prop.spriteDay == null))
      this.prop.spriteDay = this.prop.sprite;
  }

  public static GunProperties getProperties(Class paramClass)
  {
    GunProperties localGunProperties = (GunProperties)Property.value(paramClass, "_gun_properties", null);
    if (localGunProperties != null)
      return localGunProperties;
    GunGeneric localGunGeneric = null;
    try {
      localGunGeneric = (GunGeneric)paramClass.newInstance();
      Method localMethod = paramClass.getMethod("createProperties", null);
      localGunProperties = (GunProperties)localMethod.invoke(localGunGeneric, null);
      localMethod = paramClass.getMethod("createdProperties", null);
      localGunGeneric.prop = localGunProperties;
      localMethod.invoke(localGunGeneric, null);
      localGunGeneric.destroy();
    } catch (Exception localException) {
      if (Actor.isValid(localGunGeneric))
        localGunGeneric.destroy();
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      localGunProperties = null;
    }
    return localGunProperties;
  }

  private void loadProperties() {
    if (this.prop == null) {
      this.prop = ((GunProperties)Property.value(getClass(), "_gun_properties", null));

      if (this.prop == null) {
        this.prop = createProperties();
        createdProperties();
        Property.set(getClass(), "_gun_properties", this.prop);
        this.prop.calculateSteps();
      }
      int i = this.prop.bullet.length;
      this._bulletTraceMesh = new MeshShared[i];
      this._bulletTraceMeshLen = new float[i];
      for (int j = 0; j < i; j++) {
        if (this.prop.bullet[j].traceMesh != null) {
          this._bulletTraceMesh[j] = MeshShared.get(this.prop.bullet[j].traceMesh);
          this._bulletTraceMeshLen[j] = this._bulletTraceMesh[j].visibilityR();
          if (this._bulletTraceMeshLen[j] < 1.0F)
            this._bulletTraceMeshLen[j] = 1.0F;
        }
        else {
          this._bulletTraceMeshLen[j] = 10.0F;
        }
      }
    }
    float f = this.prop.shotFreq;
    if (this.prop.shotFreqDeviation > 0.0F)
      f += World.Rnd().nextFloat(-this.prop.shotFreqDeviation * f, this.prop.shotFreqDeviation * f);
    if (f < 0.001F) f = 0.001F;
    this._shotStep = (this.prop.bulletsCluster / f);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  public void init()
  {
    int i = this.prop.bullet.length;
    this.bulletAG = new float[i];
    this.bulletKV = new float[i];
    initRealisticGunnery(true);
  }
  public void initRealisticGunnery(boolean paramBoolean) {
    int i = this.prop.bullet.length;
    for (int j = 0; j < i; j++)
      if (paramBoolean) {
        this.bulletAG[j] = -9.8F;
        this.bulletKV[j] = (-1.346275F * this.prop.bullet[j].kalibr * this.prop.bullet[j].kalibr / (8.0F * this.prop.bullet[j].massa));
      }
      else {
        this.bulletAG[j] = 0.0F;
        this.bulletKV[j] = 0.0F;
      }
  }

  public void set(Actor paramActor, String paramString, Loc paramLoc)
  {
    set(paramActor, paramString);
    if ((paramLoc != null) && (this.shells != null)) {
      this.shells.pos.setRel(paramLoc);
      this.shells.pos.setBase(paramActor, null, false);
      this.shells.visibilityAsBase(true);
      this.shells.pos.setUpdateEnable(false);
    }
  }

  public void set(Actor paramActor, String paramString1, String paramString2) {
    set(paramActor, paramString1);
    if ((paramString2 != null) && (this.shells != null))
    {
      try
      {
        this.shells.pos.setBase(paramActor, paramActor.findHook(paramString2), false);
        this.shells.visibilityAsBase(true);
        if (this.prop.bUseHookAsRel)
        {
          this.shells.pos.changeHookToRel();
          this.shells.pos.setUpdateEnable(false);
        }
      }
      catch (Exception localException)
      {
        this.shells = null;
      }
    }
  }

  protected void doSound(boolean paramBoolean)
  {
    if (this.sound != null)
      if (paramBoolean) this.sound.play(); else
        this.sound.stop();
  }

  public void set(Actor paramActor, String paramString)
  {
    loadProperties();
    this.hookName = paramString;
    this.pos = new ActorPosMove(this);
    setOwner(paramActor);
    Object localObject;
    if (paramString != null) {
      localObject = paramActor.findHook(paramString);
      this.pos.setBase(paramActor, (Hook)localObject, false);
      this.chunkIndx = ((Hook)localObject).chunkNum();
    } else {
      this.pos.setBase(paramActor, null, false);
      this.chunkIndx = -1;
    }
    this.pos.reset();

    if (this.prop.bUseHookAsRel) {
      this.pos.changeHookToRel();
    }
    if (prop_fireMesh() != null) {
      this.fireMesh = new MeshShared(prop_fireMesh());
    }

    if (!this.prop.bCannon)
    {
      if (prop_fire() != null) {
        this.fire = Eff3DActor.New(this.pos, 1.0F, prop_fire(), -1.0F);
        if (this.fire != null) {
          this.fire.setUseIntensityAsSwitchDraw(true);
          Eff3DActor.setIntesity(this.fire, 0.0F);
        }

      }

      if (this.prop.smoke != null) {
        this.smoke = Eff3DActor.New(this.pos, 1.0F, this.prop.smoke, -1.0F);
        if (this.smoke != null) {
          this.smoke.setUseIntensityAsSwitchDraw(true);
          Eff3DActor.setIntesity(this.smoke, 0.0F);
        }
      }

    }

    if (this.prop.sound != null) {
      this.pos.getAbs(loc);
      loc.sub(paramActor.pos.getAbs());

      if (this.prop.sound != null) {
        this.sound = paramActor.newSound(this.prop.sound, false);
        if (this.sound != null) {
          localObject = paramActor.getRootFX();
          if (localObject != null) {
            this.sound.setParent((SoundFX)localObject);
          }
          this.sound.setPosition(loc.getPoint());
        }
      }

    }

    if (prop_sprite() != null) {
      this.sprite = Eff3DActor.New(this.pos, 1.0F, prop_sprite(), -1.0F);
      if (this.sprite != null) {
        this.sprite.drawing(false);
      }
    }

    if ((this.prop.emitColor != null) && (this.prop.emitR > 0.0F)) {
      this.pos.getAbs(loc);
      loc.sub(paramActor.pos.getAbs());
      this.light = new LightPointActor(new LightPoint(), loc.getPoint());
      this.light.light.setColor(this.prop.emitColor.x, this.prop.emitColor.y, this.prop.emitColor.z);
      this.light.light.setEmit(0.0F, this.prop.emitR);
      paramActor.draw.lightMap().put(getClass().getName() + paramString, this.light);
    }

    if (this.prop.shells != null)
    {
      if ((this.prop.shells.equals("3DO/Effects/GunShells/GunShells.eff")) && (this.prop.bullet[0].kalibr > 0.0002F))
      {
        this.prop.shells = "3DO/Effects/GunShells/MediumShells.eff";
      }

      this.shells = Eff3DActor.NewPosMove(linit, 1.0F, this.prop.shells, -1.0F);
      if (this.shells != null)
      {
        this.shells.setUseIntensityAsSwitchDraw(true);
        Eff3DActor.setIntesity(this.shells, 0.0F);
        this.shells.pos.setBase(this, null, false);
      }
    }

    if ((this.fireMesh != null) || (this.light != null)) {
      this.draw = new Draw();
      visibilityAsBase(true);
      drawing(true);
    }

    init();

    paramActor.interpPut(this.interpolater, null, -1L, null);
    if (this.prop.bUseHookAsRel)
      this.pos.setUpdateEnable(false);
  }

  class Draw extends ActorDraw
  {
    Draw()
    {
    }

    public int preRender(Actor paramActor)
    {
      if ((GunGeneric.this.bLighting) && (GunGeneric.this.light != null)) {
        if (!Actor.isValid(GunGeneric.this.getOwner())) {
          GunGeneric.this.light.light.setEmit(0.0F, GunGeneric.this.prop.emitR);
          GunGeneric.this.bLighting = false;
        } else {
          long l1 = Time.current();
          long l2 = GunGeneric.this.lastShotTime + ()(GunGeneric.this.prop.emitTime * 1000.0F);
          if (l2 <= l1) {
            GunGeneric.this.light.light.setEmit(0.0F, GunGeneric.this.prop.emitR);
            GunGeneric.this.bLighting = false;
          }
          else {
            float f1 = 0.525F - World.Sun().Ambient;
            float f2 = GunGeneric.this.prop.emitI * f1;
            if (l1 > GunGeneric.this.lastShotTime) {
              float f3 = 2.0F * (float)(l1 - GunGeneric.this.lastShotTime) / (float)(l2 - GunGeneric.this.lastShotTime);
              if (f3 < 1.0F)
                f2 *= f3;
            }
            GunGeneric.this.light.light.setEmit(f2, GunGeneric.this.prop.emitR * f1);
            if (!GunGeneric.this.prop.bUseHookAsRel)
            {
              GunGeneric.this.pos.getAbs(GunGeneric.loc);
              GunGeneric.loc.sub(GunGeneric.this.getOwner().pos.getAbs());
              GunGeneric.loc.get(GunGeneric.this.light.relPos);
            }
          }
        }
      }
      if (!GunGeneric.this.bStarted)
        return 0;
      if (GunGeneric.this.fireMesh != null) {
        GunGeneric.this.pos.getRender(GunGeneric.loc);
        return GunGeneric.this.fireMesh.preRender(GunGeneric.loc.getPoint());
      }
      return 0;
    }
    public void render(Actor paramActor) {
      GunGeneric.this.pos.getRender(GunGeneric.loc);
      if (!GunGeneric.this.fireMesh.putToRenderArray(GunGeneric.loc)) {
        GunGeneric.this.fireMesh.setPos(GunGeneric.loc);
        GunGeneric.this.fireMesh.render();
      }
    }
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public void doDestroy()
    {
      GunGeneric.this.destroy();
    }
    public boolean tick() {
      if (GunGeneric.this.curSkipTicks > 0) {
        GunGeneric.access$010(GunGeneric.this);
      } else {
        if (!GunGeneric.this.bStarted) {
          GunGeneric.this.doEffects(true);
          GunGeneric.access$102(GunGeneric.this, true);
        }
        GunGeneric.this.interpolateStep();
      }
      return true;
    }
  }
}