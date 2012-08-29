package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedBombCarrier;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

public class RocketBomb extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener
{
  private long started;
  private long impact;
  private boolean isArmed = true;
  private static long armingTime = 5000L;
  private static final boolean DEBUG_TYPE = false;
  protected long noGDelay = -1L;
  public static Point3d p = new Point3d();
  private boolean endedSmoke = false;
  protected Eff3DActor smoke;
  protected Eff3DActor sprite;
  protected Actor flame;
  protected LightPointActor light;
  protected long timeFire;
  protected long timeLife;
  protected Vector3d speed = new Vector3d();
  private float S;
  public float M;
  public float Minit;
  public boolean isThrust = true;
  private float DM;
  private float P;
  protected SoundFX sound = null;

  static Vector3d spd = new Vector3d();
  static Orient Or = new Orient();
  float curTm;

  protected void updateSound()
  {
    if (this.sound != null) {
      this.sound.setControl(200, (float)getSpeed(null));
      if (this.curTm < 5.0F)
        this.sound.setControl(201, this.curTm);
      else if (this.curTm < 5.0F + 2 * Time.tickConstLen())
        this.sound.setControl(201, 5.0F);
    }
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean) {
    if (paramActor == getOwner())
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    this.impact = (Time.current() - this.started);

    if ((this.impact < armingTime) && (this.isArmed))
    {
      this.isArmed = false;
    }

    if ((getOwner() == World.getPlayerAircraft()) && (!(paramActor instanceof ActorLand)))
    {
      World.cur().scoreCounter.rocketsHit += 1;
      if ((Mission.isNet()) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()))
      {
        Chat.sendLogRnd(3, "gore_rocketed", (Aircraft)getOwner(), (Aircraft)paramActor);
      }

    }

    if (this.isArmed)
    {
      if ((paramString2.indexOf("Mast") > -1) || (paramString2.indexOf("Wire") > -1) || (paramString2.indexOf("SSC") > -1) || (paramString2.indexOf("struct") > -1))
      {
        doExplosion(paramActor, "Hull2");
      }
      else
        doExplosion(paramActor, paramString2);
    }
    else
      destroy();
  }

  protected void doExplosion(Actor paramActor, String paramString)
  {
    if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
    {
      localObject = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
      ((TypeGuidedBombCarrier)localObject).typeGuidedBombCsetIsGuiding(false);
    }

    this.pos.getTime(Time.current(), p);
    Object localObject = getClass();
    float f1 = Property.floatValue((Class)localObject, "power", 1000.0F);
    int i = Property.intValue((Class)localObject, "powerType", 0);
    float f2 = Property.floatValue((Class)localObject, "radius", 0.0F);
    getSpeed(this.speed);
    Vector3f localVector3f = new Vector3f(this.speed);
    if (f2 <= 0.0F) {
      MsgShot.send(paramActor, paramString, p, localVector3f, this.M, getOwner(), f1, 1, 0.0D);
    }
    else
    {
      MsgShot.send(paramActor, paramString, p, localVector3f, this.M, getOwner(), (float)(0.5F * this.M * this.speed.lengthSquared()), 0, 0.0D);

      MsgExplosion.send(paramActor, paramString, p, getOwner(), this.M, f1, i, f2);
    }

    Explosions.generate(paramActor, p, f1, i, f2, !Mission.isNet());
    destroy();
  }

  protected void doExplosionAir()
  {
    if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
    {
      localObject = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
      ((TypeGuidedBombCarrier)localObject).typeGuidedBombCsetIsGuiding(false);
    }

    this.pos.getTime(Time.current(), p);
    Object localObject = getClass();
    float f1 = Property.floatValue((Class)localObject, "power", 1000.0F);
    int i = Property.intValue((Class)localObject, "powerType", 0);
    float f2 = Property.floatValue((Class)localObject, "radius", 150.0F);
    MsgExplosion.send(null, null, p, getOwner(), this.M, f1, i, f2);
    Explosions.AirFlak(p, 0);
  }

  public boolean interpolateStep() {
    return true;
  }

  protected void endSmoke() {
    if (!this.endedSmoke) {
      this.endedSmoke = true;
      if (this.light != null)
        this.light.light.setEmit(0.0F, 1.0F);
      Eff3DActor.finish(this.smoke);
      Eff3DActor.finish(this.sprite);
      ObjState.destroy(this.flame);
    }
  }

  public void destroy()
  {
    if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
    {
      TypeGuidedBombCarrier localTypeGuidedBombCarrier = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
      localTypeGuidedBombCarrier.typeGuidedBombCsetIsGuiding(false);
    }

    endSmoke();
    super.destroy();
    this.smoke = null;
    this.sprite = null;
    this.flame = null;
    this.light = null;
    if (this.sound != null)
      this.sound.cancel();
  }

  protected void setThrust(float paramFloat) {
    this.P = paramFloat;
  }

  public double getSpeed(Vector3d paramVector3d) {
    if (paramVector3d != null)
      paramVector3d.set(this.speed);
    return this.speed.length();
  }

  public void setSpeed(Vector3d paramVector3d) {
    this.speed.set(paramVector3d);
  }

  protected void init(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if ((Actor.isValid(getOwner())) && (World.getPlayerAircraft() == getOwner()))
    {
      setName("_rocket_");
    }super.getSpeed(this.speed);
    this.S = (float)(3.141592653589793D * paramFloat1 * paramFloat1 / 4.0D);
    this.M = paramFloat2;
    this.Minit = this.M;
    if (paramFloat4 > 0.0F)
      this.DM = ((paramFloat2 - paramFloat3) / (paramFloat4 / Time.tickConstLenFs()));
    else
      this.DM = 0.0F;
    this.P = paramFloat5;
    this.timeFire = ()(paramFloat4 * 1000.0F + 0.5D);
    this.timeLife = ()(paramFloat6 * 1000.0F + 0.5D);
  }

  public void start(float paramFloat)
  {
    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "kalibr", 0.082F);
    if (paramFloat <= 0.0F)
      paramFloat = Property.floatValue(localClass, "timeLife", 45.0F);
    init(f1, Property.floatValue(localClass, "massa", 6.8F), Property.floatValue(localClass, "massaEnd", 2.52F), Property.floatValue(localClass, "timeFire", 4.0F), Property.floatValue(localClass, "force", 500.0F), paramFloat);

    this.curTm = 0.0F;
    setOwner(this.pos.base(), false, false, false);
    this.pos.setBase(null, null, true);
    this.pos.setAbs(this.pos.getCurrent());
    this.pos.getAbs(Aircraft.tmpOr);
    float f2 = 0.5F * Property.floatValue(localClass, "maxDeltaAngle", 0.0F);

    Aircraft.tmpOr.increment(World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2, f2), 0.0F);

    this.pos.setAbs(Aircraft.tmpOr);
    this.pos.getRelOrient().transformInv(this.speed);

    this.speed.z -= 3.5D;
    Object localObject;
    if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
    {
      localObject = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
      ((TypeGuidedBombCarrier)localObject).typeGuidedBombCsetIsGuiding(true);
    }

    this.pos.getRelOrient().transform(this.speed);
    collide(true);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (getOwner() == World.getPlayerAircraft())
      World.cur().scoreCounter.rocketsFire += 1;
    if (Config.isUSE_RENDER()) {
      localObject = null;
      String str1 = Property.stringValue(localClass, "sprite", null);
      if (str1 != null)
      {
        if (localObject == null)
          localObject = findHook("_SMOKE");
        this.sprite = Eff3DActor.New(this, (Hook)localObject, null, f1, str1, -1.0F);
        if (this.sprite != null)
          this.sprite.pos.changeHookToRel();
      }
      str1 = Property.stringValue(localClass, "flame", null);
      if (str1 != null)
      {
        if (localObject == null)
          localObject = findHook("_SMOKE");
        this.flame = new ActorSimpleMesh(str1);
        if (this.flame != null) {
          ((ActorSimpleMesh)this.flame).mesh().setScale(f1);
          this.flame.pos.setBase(this, (Hook)localObject, false);
          this.flame.pos.changeHookToRel();
          this.flame.pos.resetAsBase();
        }
      }
      str1 = Property.stringValue(localClass, "smoke", null);
      if (str1 != null)
      {
        if (localObject == null)
          localObject = findHook("_SMOKE");
        this.smoke = Eff3DActor.New(this, (Hook)localObject, null, 1.0F, str1, -1.0F);
        if (this.smoke != null)
          this.smoke.pos.changeHookToRel();
      }
      this.light = new LightPointActor(new LightPointWorld(), new Point3d());
      this.light.light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
      this.light.light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));

      this.draw.lightMap().put("light", this.light);

      if (haveSound()) {
        String str2 = Property.stringValue(localClass, "sound", null);
        if (str2 != null)
          this.sound = newSound(str2, true);
      }
    }
  }

  protected boolean haveSound() {
    return true;
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public RocketBomb() {
    setMesh(MeshShared.get(Property.stringValue(getClass(), "mesh", null)));

    this.flags |= 224;
    collide(false);
    drawing(true);
  }

  protected void mydebug(String paramString)
  {
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (this.timeBegin + RocketBomb.this.timeLife < Time.current()) {
        RocketBomb.this.doExplosionAir();
        RocketBomb.this.postDestroy();
        RocketBomb.this.collide(false);
        RocketBomb.this.drawing(false);
        return false;
      }
      if (this.timeBegin + RocketBomb.this.timeFire < Time.current()) {
        RocketBomb.this.endSmoke();
        RocketBomb.access$002(RocketBomb.this, 0.0F);
        RocketBomb.this.isThrust = false;
      } else {
        RocketBomb.this.M -= RocketBomb.this.DM;
      }if (RocketBomb.this.interpolateStep())
      {
        int i = this.timeBegin + RocketBomb.this.noGDelay < Time.current() ? 1 : 0;

        RocketBomb.this.curTm += Time.tickLenFs();
        Ballistics.updateRocketBomb(this.actor, RocketBomb.this.M, RocketBomb.this.S, RocketBomb.this.P, this.timeBegin + RocketBomb.this.noGDelay < Time.current());
        RocketBomb.this.updateSound();
      }

      return true;
    }
  }
}