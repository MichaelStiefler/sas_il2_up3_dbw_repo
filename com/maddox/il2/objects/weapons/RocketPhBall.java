package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class RocketPhBall extends Rocket
{
  private static Point3d p = new Point3d();

  public void start(float paramFloat, int paramInt)
  {
    float f = 6.0F + World.Rnd().nextFloat(0.0F, 6.0F);
    super.start(f, paramInt);
    drawing(false);
    if (World.Rnd().nextFloat() < 0.1F) {
      Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousBall.eff", f);
    }
    switch (World.Rnd().nextInt(0, 3)) {
    case 0:
      Eff3DActor.New(this, null, new Loc(), 1.0F, "Effects/Smokes/SmokeBlack_BuletteTrail.eff", f);
      break;
    case 1:
    case 2:
      Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Tracers/TrailCurved.eff", f);
    }
  }

  protected void doExplosion(Actor paramActor, String paramString)
  {
    this.pos.getTime(Time.current(), p);

    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "power", 1000.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 0.0F);
    getSpeed(this.speed);
    Vector3f localVector3f = new Vector3f(this.speed);
    localVector3f.normalize();
    localVector3f.scale(850.0F);

    MsgShot.send(paramActor, paramString, p, localVector3f, this.M, getOwner(), (float)(0.5F * this.M * this.speed.lengthSquared()), 3, 0.0D);
    MsgExplosion.send(paramActor, paramString, p, getOwner(), this.M, f1, i, f2);

    destroy();
  }

  protected void doExplosionAir()
  {
  }

  static
  {
    Class localClass = RocketPhBall.class;
    Property.set(localClass, "mesh", "3DO/Arms/Null/mono.sim");

    Property.set(localClass, "sprite", (Object)null);
    Property.set(localClass, "flame", (Object)null);
    Property.set(localClass, "smoke", (Object)null);
    Property.set(localClass, "emitColor", new Color3f(0.0F, 0.0F, 0.0F));
    Property.set(localClass, "emitLen", 0.0F);
    Property.set(localClass, "emitMax", 0.0F);

    Property.set(localClass, "sound", (Object)null);

    Property.set(localClass, "radius", 0.1F);
    Property.set(localClass, "timeLife", 999.99902F);
    Property.set(localClass, "timeFire", 0.0F);
    Property.set(localClass, "force", 0.0F);

    Property.set(localClass, "power", 0.01485F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.001F);
    Property.set(localClass, "massa", 0.01485F);
    Property.set(localClass, "massaEnd", 0.01485F);
  }
}