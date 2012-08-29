package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;

public class BulletParabolaGeneric extends Bullet
{
  public BulletParabolaGeneric(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong);
  }

  public void move(float paramFloat)
  {
    if (this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric == null) return;

    this.jdField_p0_of_type_ComMaddoxJGPPoint3d.set(this.jdField_p1_of_type_ComMaddoxJGPPoint3d);
    this.jdField_p1_of_type_ComMaddoxJGPPoint3d.scaleAdd(paramFloat, this.jdField_speed_of_type_ComMaddoxJGPVector3d, this.jdField_p0_of_type_ComMaddoxJGPPoint3d);
    this.jdField_speed_of_type_ComMaddoxJGPVector3d.z += this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric.bulletAG[indx()] * paramFloat;
  }

  public void timeOut()
  {
  }
}