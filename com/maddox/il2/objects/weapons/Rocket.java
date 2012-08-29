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
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class Rocket extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener
{
  private static final boolean DEBUG_TYPE = false;
  protected long noGDelay = 1000L;

  private static Point3d p = new Point3d();

  private boolean endedSmoke = false;
  protected Eff3DActor smoke;
  protected Eff3DActor sprite;
  protected Actor flame;
  protected LightPointActor light;
  protected String soundName;
  protected long timeFire;
  protected long timeLife;
  protected Vector3d speed = new Vector3d();
  private float S;
  protected float M;
  private float DM;
  private float P;

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (paramActor == getOwner())
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((getOwner() == World.getPlayerAircraft()) && (!(paramActor instanceof ActorLand))) {
      World.cur().scoreCounter.rocketsHit += 1;
      if ((Mission.isNet()) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer())) {
        Chat.sendLogRnd(3, "gore_rocketed", (Aircraft)getOwner(), (Aircraft)paramActor);
      }
    }
    doExplosion(paramActor, paramString2);
  }

  protected void doExplosion(Actor paramActor, String paramString) {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);

    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "power", 1000.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 0.0F);
    getSpeed(this.speed);
    Vector3f localVector3f = new Vector3f(this.speed);

    if (f2 <= 0.0F) {
      MsgShot.send(paramActor, paramString, p, localVector3f, this.M, getOwner(), f1, 1, 0.0D);
    } else {
      MsgShot.send(paramActor, paramString, p, localVector3f, this.M, getOwner(), (float)(0.5F * this.M * this.speed.lengthSquared()), 0, 0.0D);
      MsgExplosion.send(paramActor, paramString, p, getOwner(), this.M, f1, i, f2);
    }

    Explosions.generateRocket(paramActor, p, f1, i, f2);

    destroy();
  }

  protected void doExplosionAir()
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "power", 1000.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 150.0F);

    MsgExplosion.send(null, null, p, getOwner(), this.M, f1, i, f2);

    Explosions.AirFlak(p, 0);
  }

  public boolean interpolateStep()
  {
    return true;
  }

  protected void endSmoke()
  {
    if (this.endedSmoke) return;

    this.endedSmoke = true;
    if (this.light != null)
      this.light.light.setEmit(0.0F, 1.0F);
    Eff3DActor.finish(this.smoke);
    Eff3DActor.finish(this.sprite);
    ObjState.destroy(this.flame);
    stopSounds();
  }

  public void destroy()
  {
    endSmoke();
    super.destroy();

    this.smoke = null;
    this.sprite = null;
    this.flame = null;
    this.light = null;
    this.soundName = null;
  }

  protected void setThrust(float paramFloat)
  {
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
    }
    super.getSpeed(this.speed);
    this.S = (float)(3.141592653589793D * paramFloat1 * paramFloat1 / 4.0D);
    this.M = paramFloat2;
    if (paramFloat4 > 0.0F) this.DM = ((paramFloat2 - paramFloat3) / (paramFloat4 / Time.tickConstLenFs())); else
      this.DM = 0.0F;
    this.P = paramFloat5;

    this.timeFire = ()(paramFloat4 * 1000.0F + 0.5D);
    this.timeLife = ()(paramFloat6 * 1000.0F + 0.5D);
  }

  public void start(float paramFloat) {
    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "kalibr", 0.082F);
    if (paramFloat <= 0.0F)
      paramFloat = Property.floatValue(localClass, "timeLife", 45.0F);
    init(f1, Property.floatValue(localClass, "massa", 6.8F), Property.floatValue(localClass, "massaEnd", 2.52F), Property.floatValue(localClass, "timeFire", 4.0F), Property.floatValue(localClass, "force", 500.0F), paramFloat);

    setOwner(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base(), false, false, false);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(null, null, true);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent());

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Aircraft.tmpOr);
    float f2 = 0.5F * Property.floatValue(localClass, "maxDeltaAngle", 0.0F);
    Aircraft.tmpOr.increment(World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2, f2), 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(Aircraft.tmpOr);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelOrient().transformInv(this.speed);
    this.speed.y /= 3.0D;
    this.speed.z /= 3.0D;
    this.speed.x += 200.0D;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelOrient().transform(this.speed);
    collide(true);
    interpPut(new Interpolater(), null, Time.current(), null);

    if (getOwner() == World.getPlayerAircraft()) {
      World.cur().scoreCounter.rocketsFire += 1;
    }
    if (!Config.isUSE_RENDER()) return;

    Hook localHook = null;
    String str = Property.stringValue(localClass, "sprite", null);
    if (str != null) {
      if (localHook == null) {
        localHook = findHook("_SMOKE");
      }
      this.sprite = Eff3DActor.New(this, localHook, null, f1, str, -1.0F);
      if (this.sprite != null)
        this.sprite.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
    }
    str = Property.stringValue(localClass, "flame", null);
    if (str != null) {
      if (localHook == null) {
        localHook = findHook("_SMOKE");
      }
      this.flame = new ActorSimpleMesh(str);
      if (this.flame != null) {
        ((ActorSimpleMesh)this.flame).mesh().setScale(f1);
        this.flame.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this, localHook, false);
        this.flame.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
        this.flame.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
      }
    }
    str = Property.stringValue(localClass, "smoke", null);
    if (str != null) {
      if (localHook == null) {
        localHook = findHook("_SMOKE");
      }
      this.smoke = Eff3DActor.New(this, localHook, null, 1.0F, str, -1.0F);
      if (this.smoke != null) {
        this.smoke.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
      }
    }
    this.light = new LightPointActor(new LightPointWorld(), new Point3d());
    this.light.light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
    this.light.light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));
    this.draw.lightMap().put("light", this.light);

    this.soundName = Property.stringValue(localClass, "sound", null);
    if (this.soundName != null)
      newSound(this.soundName, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public Rocket() {
    setMesh(MeshShared.get(Property.stringValue(getClass(), "mesh", null)));
    this.flags |= 224;
    collide(false);
    drawing(true);
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (this.jdField_timeBegin_of_type_Long + Rocket.this.timeLife < Time.current())
      {
        Rocket.this.doExplosionAir();
        Rocket.this.postDestroy(); Rocket.this.collide(false); Rocket.this.drawing(false);
        return false;
      }
      if (this.jdField_timeBegin_of_type_Long + Rocket.this.timeFire < Time.current()) {
        Rocket.this.endSmoke();
        Rocket.access$002(Rocket.this, 0.0F);
      } else {
        Rocket.this.M -= Rocket.this.DM;
      }
      if (Rocket.this.interpolateStep())
      {
        Ballistics.update(this.actor, Rocket.this.M, Rocket.this.S, Rocket.this.P, this.jdField_timeBegin_of_type_Long + Rocket.this.noGDelay < Time.current());
      }
      return true;
    }
  }
}