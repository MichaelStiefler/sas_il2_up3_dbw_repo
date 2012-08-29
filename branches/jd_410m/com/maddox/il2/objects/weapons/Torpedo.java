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
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

public class Torpedo extends Bomb
{
  Actor Other;
  String OtherChunk;
  String ThisChunk;
  boolean flow;
  private float velocity;
  private long travelTime;
  private long started;
  private float gyroAngle = 0.0F;
  private float gyroTargetAngle = 0.0F;
  private float turnSpeed = 0.1F;
  private int spreadAngle = 0;
  private long timeHitWater;
  private long timeTravelInWater;
  private float impactAngleMin;
  private float impactAngleMax;
  private float impactSpeed;
  private float armingTime;
  boolean hasHitLand = false;
  boolean doLandExplosion = false;

  static Vector3d spd = new Vector3d();
  static Orient Or = new Orient();
  static Point3d P = new Point3d();

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    Class localClass = getClass();
    this.armingTime = (float)(()Property.floatValue(localClass, "armingTime", 1.0F) * 1000L);
    this.timeTravelInWater = (Time.current() - this.timeHitWater);

    this.Other = paramActor;
    this.OtherChunk = paramString2;

    if (((this instanceof BombTorpFFF)) && (((BombTorpFFF)this).bOnChute2))
    {
      ((BombTorpFFF)this).bOnChute2 = false;
      if (!(paramActor instanceof ActorLand))
      {
        doExplosion(paramActor, paramString2);
        return;
      }

      if (World.land().isWater(P.x, P.y)) {
        return;
      }

      doExplosion(paramActor, paramString2);
      return;
    }

    if ((paramActor instanceof ActorLand)) {
      if (this.flow) { doExplosion(paramActor, paramString2); return; }
      surface();

      if (World.land().isWater(P.x, P.y)) return;
    }
    Aircraft localAircraft;
    if (((float)this.timeTravelInWater < this.armingTime) && (!(paramActor instanceof ActorLand)))
    {
      if ((getOwner() instanceof Aircraft))
      {
        localAircraft = (Aircraft)getOwner();
        if (localAircraft.FM.isPlayers())
          HUD.log(AircraftHotKeys.hudLogWeaponId, "TorpedoDidNotArm");
      }
      destroy();
    }
    else if ((this.hasHitLand) || (!this.flow)) {
      Explosions.Explode10Kg_Land(P, 2.0F, 2.0F);
    }
    else {
      if ((getOwner() instanceof Aircraft))
      {
        localAircraft = (Aircraft)getOwner();
        if (localAircraft.FM.isPlayers())
          HUD.log(AircraftHotKeys.hudLogWeaponId, "TorpedoHit");
      }
      doExplosion(paramActor, paramString2);
    }
  }

  private void surface()
  {
    Class localClass = getClass();
    int i = 0;

    this.travelTime = (()Property.floatValue(localClass, "traveltime", 1.0F) * 1000L);
    this.impactAngleMin = Property.floatValue(localClass, "impactAngleMin", 1.0F);
    this.impactAngleMax = Property.floatValue(localClass, "impactAngleMax", 1.0F);
    this.impactSpeed = Property.floatValue(localClass, "impactSpeed", 1.0F);

    this.timeHitWater = Time.current();
    float f1;
    float f2;
    if ((this instanceof BombTorpFFF1))
    {
      this.turnSpeed = 0.12F;
      f1 = (float)spd.length();
      f2 = 5.2F / f1;
      spd.scale(f2);
      setSpeed(spd);
    }
    else if ((this instanceof BombTorpFFF))
    {
      this.turnSpeed = 0.1F;
      f1 = (float)spd.length();
      f2 = 5.0F / f1;
      spd.scale(f2);
      setSpeed(spd);
    }
    else if ((this instanceof BombParaTorp))
    {
      this.turnSpeed = 0.1F;
      f1 = (float)spd.length();
      f2 = 5.0F / f1;
      spd.scale(f2);
      setSpeed(spd);
    }

    this.pos.getAbs(P, Or);
    this.flow = true;
    getSpeed(spd);
    if (World.land().isWater(P.x, P.y))
    {
      if (spd.z < -0.119999997317791D)
      {
        Explosions.RS82_Water(P, 4.0F, 1.0F);
      }
      double d = spd.length();

      Aircraft localAircraft = null;
      int k = 0;

      if (((getOwner() instanceof Aircraft)) && (!(this instanceof BombParaTorp)))
      {
        k = 1;

        localAircraft = (Aircraft)getOwner();
        if ((localAircraft.FM.isPlayers()) || (localAircraft.isNetPlayer()))
        {
          k = 1;
        }
        else
        {
          this.impactAngleMin *= 0.8F;
          this.impactAngleMax *= 1.2F;
          this.impactSpeed *= 1.2F;
        }

      }

      if ((d > this.impactSpeed) && (k != 0))
      {
        if ((localAircraft != null) && 
          (localAircraft.FM.isPlayers())) {
          HUD.log(AircraftHotKeys.hudLogWeaponId, "TorpedoBrokenOnEntryIntoWater");
        }
        i = 1;
        destroy();
      }
      else
      {
        if (d > 0.001D)
          d = spd.z / spd.length();
        else
          d = -1.0D;
        float f3 = 180.0F * (float)Math.abs(Math.asin(d)) / 3.14159F;

        if ((k != 0) && ((f3 > this.impactAngleMax) || (d < -0.9900000095367432D)))
        {
          if ((localAircraft != null) && 
            (localAircraft.FM.isPlayers())) {
            HUD.log(AircraftHotKeys.hudLogWeaponId, "TorpedoFailedEntryIntoWater");
          }
          i = 1;
          destroy();
        }
        if ((k != 0) && (f3 < this.impactAngleMin))
        {
          if ((localAircraft != null) && 
            (localAircraft.FM.isPlayers())) {
            HUD.log(AircraftHotKeys.hudLogWeaponId, "TorpedoFailedEntryIntoWater");
          }
          i = 1;
          destroy();
        }

      }

    }
    else
    {
      i = 1;
      this.hasHitLand = true;
      destroy();
    }
    if (i == 0)
    {
      if ((getOwner() instanceof TypeHasToKG))
      {
        localObject = (Aircraft)getOwner();
        this.gyroTargetAngle = ((Aircraft)localObject).FM.AS.getGyroAngle();
        this.spreadAngle = ((Aircraft)localObject).FM.AS.getSpreadAngle();
        int j = Property.intValue(localClass, "spreadDirection", 0);
        this.gyroTargetAngle += this.spreadAngle / 2.0F * j;
      }

      getSpeed(spd);
      spd.z = 0.0D;
      setSpeed(spd);
      P.z = 0.0D;
      Object localObject = new float[3];
      Or.getYPR(localObject);
      Or.setYPR(localObject[0], 0.0F, localObject[2]);
      this.pos.setAbs(P, Or);
      this.flags &= -65;
      drawing(false);
      if ((this instanceof BombParaTorp))
      {
        Eff3DActor.New(this, null, null, 0.5F, "3DO/Effects/Tracers/533mmTorpedo/CirclingLine.eff", -1.0F);
      }
      else
      {
        Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/Line.eff", -1.0F);

        Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/wave.eff", -1.0F);
      }
    }
  }

  public void interpolateTick()
  {
    float f1 = Time.tickLenFs();
    this.pos.getAbs(P);
    if (P.z <= -0.1000000014901161D) surface();

    if (!this.flow) { Ballistics.update(this, this.M, this.S);
    }
    else
    {
      getSpeed(spd);
      if (spd.length() > this.velocity) spd.scale(0.9900000095367432D);
      else if (spd.length() < this.velocity) spd.scale(1.009999990463257D);
      setSpeed(spd);
      this.pos.getAbs(P);

      float f2 = this.gyroTargetAngle - this.gyroAngle;

      if ((this instanceof BombTorpFFF1))
      {
        this.turnSpeed -= 7.8E-006F;
        if (this.turnSpeed < 0.0084F) {
          this.turnSpeed = 0.0084F;
        }

        f2 = -0.5F;
      }
      else if ((this instanceof BombParaTorp))
      {
        this.turnSpeed -= 7.E-006F;
        if (this.turnSpeed < 0.008F) {
          this.turnSpeed = 0.008F;
        }

        f2 = 0.5F;
      }

      if (f2 != 0.0F)
      {
        if (f2 < 0.0F)
          this.gyroAngle -= this.turnSpeed;
        else if (f2 > 0.0F) {
          this.gyroAngle += this.turnSpeed;
        }
        float f3 = -(float)Math.toRadians(this.gyroAngle);
        float f4 = (float)Math.cos(Math.abs(f3));
        float f5 = (float)Math.sin(f3);
        float f6 = (float)(spd.x * f4 - spd.y * f5);
        float f7 = (float)(spd.x * f5 + spd.y * f4);
        spd.x = f6;
        spd.y = f7;
      }

      P.x += spd.x * f1;
      P.y += spd.y * f1;
      this.pos.setAbs(P);
      if ((Time.current() > this.started + this.travelTime) || (!World.land().isWater(P.x, P.y))) {
        sendexplosion();
      }
    }

    updateSound();
  }

  private void sendexplosion() {
    MsgCollision.post(Time.current(), this, this.Other, null, this.OtherChunk);
  }

  public void start()
  {
    Class localClass = getClass();
    init(Property.floatValue(localClass, "kalibr", 1.0F), Property.floatValue(localClass, "massa", 1.0F));

    this.started = Time.current();
    this.velocity = Property.floatValue(localClass, "velocity", 1.0F);
    this.travelTime = (()Property.floatValue(localClass, "traveltime", 1.0F) * 1000L);

    setOwner(this.pos.base(), false, false, false);

    this.pos.setBase(null, null, true);
    this.pos.setAbs(this.pos.getCurrent());

    getSpeed(spd); this.pos.getAbs(P, Or);
    Vector3d localVector3d = new Vector3d(Property.floatValue(localClass, "startingspeed", 0.0F), 0.0D, 0.0D);
    Or.transform(localVector3d); spd.add(localVector3d); setSpeed(spd);

    collide(true);
    interpPut(new Bomb.Interpolater(this), null, Time.current(), null);
    drawing(true);

    if (Property.containsValue(localClass, "emitColor")) {
      LightPointActor localLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d());
      localLightPointActor.light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
      localLightPointActor.light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));
      this.draw.lightMap().put("light", localLightPointActor);
    }

    this.sound = newSound(Property.stringValue(localClass, "sound", null), false);
    if (this.sound != null)
    {
      this.sound.play();
    }
  }
}