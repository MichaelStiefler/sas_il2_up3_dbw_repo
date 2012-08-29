package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombPTAB25 extends Bomb
{
  protected boolean haveSound()
  {
    return this.jdField_index_of_type_Int % 20 == 0;
  }

  protected void doExplosion(Actor paramActor, String paramString) {
    Class localClass = getClass();
    Point3d localPoint3d = new Point3d();
    this.pos.getTime(Time.current(), localPoint3d);
    float f1 = Property.floatValue(localClass, "power", 0.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 0.0F);

    MsgShot.send(paramActor, paramString, localPoint3d, new Vector3f(0.0F, 0.0F, -600.0F), this.M, getOwner(), f1, 1, 0.0D);

    if (this.jdField_index_of_type_Int % 20 == 0) Explosions.generate(paramActor, localPoint3d, f1, i, f2);

    destroy();
  }

  static
  {
    Class localClass = BombPTAB25.class;
    Property.set(localClass, "mesh", "3do/arms/PTAB-25/mono.sim");
    Property.set(localClass, "radius", 0.01F);
    Property.set(localClass, "power", 1.9F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.1253F);
    Property.set(localClass, "massa", 2.95F);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}