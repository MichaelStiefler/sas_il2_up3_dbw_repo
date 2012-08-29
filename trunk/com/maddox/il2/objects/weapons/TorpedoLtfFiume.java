package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

public class TorpedoLtfFiume extends Bomb
{
  Actor Other;
  String OtherChunk;
  String ThisChunk;
  boolean flow;
  private float velocity;
  private long travelTime;
  private long started;
  private long impact;
  private long i;
  static Vector3d spd = new Vector3d();
  static Orient Or = new Orient();
  static Point3d P = new Point3d();

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    this.impact = (Time.current() - this.started);
    this.Other = paramActor;
    this.OtherChunk = paramString2;
    if ((paramActor instanceof ActorLand)) {
      if (this.flow) {
        doExplosion(paramActor, paramString2);
        return;
      }
      surface();
      if (World.land().isWater(P.jdField_x_of_type_Double, P.jdField_y_of_type_Double))
        return;
    }
    if ((this.impact < 5000L) && (!(paramActor instanceof ActorLand)))
    {
      this.Other = null;
      this.OtherChunk = "";
      collide(false);
    }
    else {
      doExplosion(paramActor, paramString2);
    }
  }

  private void surface() {
    Class localClass = getClass();

    this.travelTime = (()Property.floatValue(localClass, "traveltime", 1.0F) * 1000L);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(P, Or);
    this.flow = true;
    getSpeed(spd);
    if (World.land().isWater(P.jdField_x_of_type_Double, P.jdField_y_of_type_Double))
    {
      if (spd.jdField_z_of_type_Double < -0.119999997317791D)
        Explosions.RS82_Water(P, 4.0F, 1.0F);
      double d = spd.length();
      if (d > 0.001D)
        d = spd.jdField_z_of_type_Double / spd.length();
      else
        d = -1.0D;
      if (d < -0.57D)
        sendexplosion();
    }
    spd.jdField_z_of_type_Double = 0.0D;
    setSpeed(spd);
    P.jdField_z_of_type_Double = 0.0D;
    float[] arrayOfFloat = new float[3];
    Or.getYPR(arrayOfFloat);
    Or.setYPR(arrayOfFloat[0], 0.0F, arrayOfFloat[2]);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(P, Or);
    this.flags &= -65;
    drawing(false);
    Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/Line.eff", -1.0F);

    Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/wave.eff", -1.0F);
  }

  public void interpolateTick()
  {
    float f = Time.tickLenFs();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(P);
    if (P.jdField_z_of_type_Double <= -0.1000000014901161D)
      surface();
    if (!this.flow) {
      Ballistics.update(this, this.M, this.S);
    } else {
      getSpeed(spd);
      if (spd.length() > this.velocity)
        spd.scale(0.9900000095367432D);
      else if (spd.length() < this.velocity)
        spd.scale(1.009999990463257D);
      setSpeed(spd);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(P);
      P.jdField_x_of_type_Double += spd.jdField_x_of_type_Double * f;
      P.jdField_y_of_type_Double += spd.jdField_y_of_type_Double * f;
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(P);
      if ((Time.current() > this.started + this.travelTime) || (!World.land().isWater(P.jdField_x_of_type_Double, P.jdField_y_of_type_Double)))
      {
        sendexplosion();
      }
    }
    updateSound();
  }

  private void sendexplosion() {
    MsgCollision.post(Time.current(), this, this.Other, null, this.OtherChunk);
  }

  public void start() {
    Class localClass = getClass();
    init(Property.floatValue(localClass, "kalibr", 1.0F), Property.floatValue(localClass, "massa", 1.0F));

    this.started = Time.current();
    this.velocity = Property.floatValue(localClass, "velocity", 1.0F);
    this.travelTime = (()Property.floatValue(localClass, "traveltime", 1.0F) * 1000L);

    setOwner(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base(), false, false, false);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(null, null, true);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent());
    getSpeed(spd);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(P, Or);
    Vector3d localVector3d = new Vector3d(Property.floatValue(localClass, "startingspeed", 0.0F), 0.0D, 0.0D);

    Or.transform(localVector3d);
    spd.add(localVector3d);
    setSpeed(spd);
    collide(true);
    interpPut(new Bomb.Interpolater(this), null, Time.current(), null);
    drawing(true);
    if (Property.containsValue(localClass, "emitColor")) {
      LightPointActor localLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d());

      localLightPointActor.light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));

      localLightPointActor.light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));

      this.draw.lightMap().put("light", localLightPointActor);
    }
    this.jdField_sound_of_type_ComMaddoxSoundSoundFX = newSound(Property.stringValue(localClass, "sound", null), false);

    if (this.jdField_sound_of_type_ComMaddoxSoundSoundFX != null)
      this.jdField_sound_of_type_ComMaddoxSoundSoundFX.play();
  }
}