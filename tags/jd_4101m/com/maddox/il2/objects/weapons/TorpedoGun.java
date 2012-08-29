package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.GunProperties;
import com.maddox.rts.Property;

public class TorpedoGun extends BombGun
{
  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f1 = (float)paramPoint3d1.distance(paramPoint3d2);
    Class localClass1 = getClass();
    Class localClass2 = (Class)Property.value(localClass1, "bulletClass", null);
    float f2 = Property.floatValue(localClass2, "velocity", 1.0F);
    float f3 = Property.floatValue(localClass2, "traveltime", 1.0F);
    if (f1 > f2 * f3) {
      return -1.0F;
    }
    return f1 / f2;
  }

  public GunProperties createProperties() {
    GunProperties localGunProperties = new GunProperties();
    localGunProperties.weaponType = 16;
    return localGunProperties;
  }
}