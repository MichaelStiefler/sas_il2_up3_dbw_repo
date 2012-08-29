package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombSD4HL extends Bomb
{
  protected boolean haveSound()
  {
    return this.index % 10 == 0;
  }

  protected void doExplosion(Actor paramActor, String paramString)
  {
    Class localClass = getClass();
    Point3d localPoint3d = new Point3d();
    this.pos.getTime(Time.current(), localPoint3d);
    float f1 = Property.floatValue(localClass, "power", 0.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 0.0F);

    MsgShot.send(paramActor, paramString, localPoint3d, new Vector3f(0.0F, 0.0F, -600.0F), this.M, getOwner(), f1, 1, 0.0D);

    Explosions.generate(paramActor, localPoint3d, f1, i, f2);

    destroy();
  }

  static
  {
    Class localClass = BombSD4HL.class;
    Property.set(localClass, "mesh", "3DO/Arms/SD-4HL/mono.sim");
    Property.set(localClass, "radius", 0.6F);
    Property.set(localClass, "power", 0.345384F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.152F);
    Property.set(localClass, "massa", 4.264F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}