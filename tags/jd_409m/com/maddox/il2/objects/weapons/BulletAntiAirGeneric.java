package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Time;

public abstract class BulletAntiAirGeneric extends Bullet
{
  protected float explodeAtHeight;
  protected boolean time_explode;

  public BulletAntiAirGeneric(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong, float paramFloat, boolean paramBoolean)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong);
    this.explodeAtHeight = paramFloat;
    this.time_explode = paramBoolean;
  }

  public void move(float paramFloat)
  {
    if (this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric == null) {
      destroy();
      return;
    }

    this.jdField_p0_of_type_ComMaddoxJGPPoint3d.set(this.jdField_p1_of_type_ComMaddoxJGPPoint3d);
    this.jdField_p1_of_type_ComMaddoxJGPPoint3d.scaleAdd(paramFloat, this.jdField_speed_of_type_ComMaddoxJGPVector3d, this.jdField_p0_of_type_ComMaddoxJGPPoint3d);

    if (this.explodeAtHeight > 0.0F) {
      if ((float)this.jdField_p1_of_type_ComMaddoxJGPPoint3d.z >= this.explodeAtHeight) {
        timeOut();
        destroy();
        return;
      }
    }
    else this.jdField_speed_of_type_ComMaddoxJGPVector3d.z += this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric.bulletAG[indx()] * paramFloat;
  }

  public void timeOut()
  {
    if ((this.explodeAtHeight <= 0.0F) && (!this.time_explode)) {
      return;
    }

    if (this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric == null) {
      return;
    }

    BulletProperties localBulletProperties = properties();

    Bullet.tmpP.interpolate(this.jdField_p0_of_type_ComMaddoxJGPPoint3d, this.jdField_p1_of_type_ComMaddoxJGPPoint3d, Time.tickOffset());
    MsgExplosion.send(null, null, Bullet.tmpP, this.owner, localBulletProperties.massa, localBulletProperties.power, localBulletProperties.powerType, localBulletProperties.powerRadius);

    explodeInAir_Effect(Bullet.tmpP);
  }

  protected abstract void explodeInAir_Effect(Point3d paramPoint3d);

  public void showExplosion(Actor paramActor, Point3d paramPoint3d, BulletProperties paramBulletProperties, double paramDouble)
  {
    if ((this.explodeAtHeight <= 0.0F) && (!this.time_explode))
      super.showExplosion(paramActor, paramPoint3d, paramBulletProperties, paramDouble);
    else
      explodeInAir_Effect(paramPoint3d);
  }
}