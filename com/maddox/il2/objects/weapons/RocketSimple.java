package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class RocketSimple extends Rocket
{
  private static Point3d p = new Point3d();
  private static Vector3f dir = new Vector3f();
  private static Orient or = new Orient();

  public void start(float paramFloat)
  {
    Class localClass = getClass();
    float f = Property.floatValue(localClass, "kalibr", 0.082F);
    this.jdField_timeLife_of_type_Long = 0L;
    init(f, Property.floatValue(localClass, "massa", 6.8F), Property.floatValue(localClass, "massaEnd", 2.52F), Property.floatValue(localClass, "timeFire", 4.0F), Property.floatValue(localClass, "force", 500.0F), (float)this.jdField_timeLife_of_type_Long);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(or);
    dir.set(1.0F, 0.0F, 0.0F);
    or.transform(dir);
    dir.scale(paramFloat);
    this.speed.set(dir);

    collide(true);
    interpPut(new Interpolater(), null, Time.current(), null);

    if (getOwner() == World.getPlayerAircraft()) {
      World.cur().scoreCounter.rocketsFire += 1;
    }
    Hook localHook = findHook("_SMOKE");
    String str = Property.stringValue(localClass, "sprite", null);
    if (str != null) {
      this.jdField_sprite_of_type_ComMaddoxIl2EngineEff3DActor = Eff3DActor.New(this, localHook, null, f, str, -1.0F);
      if (this.jdField_sprite_of_type_ComMaddoxIl2EngineEff3DActor != null)
        this.jdField_sprite_of_type_ComMaddoxIl2EngineEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
    }
    str = Property.stringValue(localClass, "flame", null);
    if (str != null) {
      this.jdField_flame_of_type_ComMaddoxIl2EngineActor = new ActorSimpleMesh(str);
      if (this.jdField_flame_of_type_ComMaddoxIl2EngineActor != null) {
        ((ActorSimpleMesh)this.jdField_flame_of_type_ComMaddoxIl2EngineActor).mesh().setScale(f);
        this.jdField_flame_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this, localHook, false);
        this.jdField_flame_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
        this.jdField_flame_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
      }
    }
    str = Property.stringValue(localClass, "smoke", null);
    if (str != null) {
      this.jdField_smoke_of_type_ComMaddoxIl2EngineEff3DActor = Eff3DActor.New(this, localHook, null, 1.0F, str, -1.0F);
      if (this.jdField_smoke_of_type_ComMaddoxIl2EngineEff3DActor != null) {
        this.jdField_smoke_of_type_ComMaddoxIl2EngineEff3DActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
      }
    }
    this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d());
    this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor.light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
    this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor.light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));
    this.draw.lightMap().put("light", this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor);

    this.jdField_soundName_of_type_JavaLangString = Property.stringValue(localClass, "sound", null);
    if (this.jdField_soundName_of_type_JavaLangString != null)
      newSound(this.jdField_soundName_of_type_JavaLangString, true);
  }

  public RocketSimple(Point3d paramPoint3d, Orient paramOrient, Actor paramActor)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d, paramOrient);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    setOwner(paramActor);
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (this.timeBegin + RocketSimple.this.timeFire < Time.current()) {
        RocketSimple.this.endSmoke();
      }

      Ballistics.update(this.actor, RocketSimple.this.M, 0.0F, 0.0F, true);
      return true;
    }
  }
}