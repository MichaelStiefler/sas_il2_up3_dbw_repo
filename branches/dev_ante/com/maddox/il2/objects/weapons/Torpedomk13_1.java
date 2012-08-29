package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorMesh;
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

public class Torpedomk13_1 extends Bomb
{
  Actor Other;
  String OtherChunk;
  String ThisChunk;
  boolean flow;
  private float velocity;
  private long travelTime;
  private long started;
  static Vector3d spd = new Vector3d();
  static Orient Or = new Orient();
  static Point3d P = new Point3d();

  public void msgCollision(Actor actor, String string, String string_0_)
  {
    double randf = Math.random() * 100.0D;

    this.Other = actor;
    this.OtherChunk = string_0_;
    if ((actor instanceof ActorLand)) {
      if (this.flow) {
        doExplosion(actor, string_0_);
        return;
      }
      surface();
      if (World.land().isWater(P.x, P.y)) {
        return;
      }
    }
    if ((randf >= 34.0D) && (randf < 41.0D)) {
      destroy();
    }
    else
      doExplosion(actor, string_0_);
  }

  private void surface()
  {
    Class var_class = getClass();
    double randi = Math.random() * 100.0D;
    this.travelTime = 
      (()Property.floatValue(var_class, "traveltime", 1.0F) * 
      1000L);

    this.pos.getAbs(P, Or);
    this.flow = true;
    getSpeed(spd);
    if (World.land().isWater(P.x, P.y))
    {
      if (spd.z < -0.119999997317791D)
        Explosions.RS82_Water(P, 4.0F, 1.0F);
      double d = spd.length();
      if (d > 0.001D)
        d = spd.z / spd.length();
      else {
        d = -1.0D;
      }
      if ((d > -0.23D) && (randi >= 73.0D) && (randi <= 78.0D)) {
        this.travelTime = 8000L;
      }
      if ((d > -0.23D) && (randi >= 87.0D)) {
        this.travelTime = 20000L;
      }
      if ((d > -0.23D) && (randi <= 22.0D)) {
        this.velocity = 0.2F;
      }
      if (d < -0.23D) {
        this.velocity = 0.05F;
      }

    }

    spd.z = 0.0D;
    setSpeed(spd);
    P.z = 0.0D;
    float[] fs = new float[3];
    Or.getYPR(fs);
    Or.setYPR(fs[0], 0.0F, fs[2]);
    this.pos.setAbs(P, Or);
    this.flags &= -65;
    drawing(false);
    Eff3DActor.New(this, null, null, 1.0F, 
      "3DO/Effects/Tracers/533mmTorpedo/Wave.eff", -1.0F);
    Eff3DActor.New(this, null, null, 1.0F, 
      "3DO/Effects/Tracers/533mmTorpedo/Line.eff", -1.0F);
  }

  public void interpolateTick() {
    float f = Time.tickLenFs();
    this.pos.getAbs(P);
    if (P.z <= -0.1000000014901161D)
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
      this.pos.getAbs(P);
      P.x += spd.x * f;
      P.y += spd.y * f;
      this.pos.setAbs(P);
      if ((Time.current() > this.started + this.travelTime) || 
        (!World.land().isWater(P.x, P.y)))
        sendexplosion();
    }
    updateSound();
  }

  private void sendexplosion() {
    MsgCollision.post(Time.current(), this, this.Other, null, this.OtherChunk);
  }

  public void start()
  {
    Class var_class = getClass();
    init(Property.floatValue(var_class, "kalibr", 1.0F), 
      Property.floatValue(var_class, "massa", 1.0F));
    this.started = Time.current();
    this.velocity = Property.floatValue(var_class, "velocity", 1.0F);
    this.travelTime = 
      (()Property.floatValue(var_class, "traveltime", 1.0F) * 
      1000L);
    setOwner(this.pos.base(), false, false, false);
    this.pos.setBase(null, null, true);
    this.pos.setAbs(this.pos.getCurrent());
    getSpeed(spd);
    this.pos.getAbs(P, Or);
    Vector3d vector3d = 
      new Vector3d(
      Property.floatValue(var_class, 
      "startingspeed", 0.0F), 
      0.0D, 0.0D);
    Or.transform(vector3d);
    spd.add(vector3d);
    setSpeed(spd);
    collide(true);
    interpPut(new Bomb.Interpolater(this), null, Time.current(), null);
    drawing(true);
    if (Property.containsValue(var_class, "emitColor")) {
      LightPointActor lightpointactor = 
        new LightPointActor(new LightPointWorld(), new Point3d());
      lightpointactor.light.setColor((Color3f)
        Property.value(var_class, 
        "emitColor", 
        new Color3f(1.0F, 
        1.0F, 
        0.5F)));
      lightpointactor.light.setEmit(
        Property.floatValue(var_class, 
        "emitMax", 1.0F), 
        Property.floatValue(var_class, 
        "emitLen", 
        50.0F));
      this.draw.lightMap().put("light", lightpointactor);
    }
    this.sound = 
      newSound(Property.stringValue(var_class, "sound", null), false);
    if (this.sound != null)
      this.sound.play();
  }
}