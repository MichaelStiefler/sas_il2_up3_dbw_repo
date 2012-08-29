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

  public BulletAntiAirGeneric(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong, float paramFloat, boolean paramBoolean)
  {
    super(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);
    this.explodeAtHeight = paramFloat;
    this.time_explode = paramBoolean;
  }

  public void move(float paramFloat)
  {
    if (this.gun == null) {
      destroy();
      return;
    }

    this.p0.set(this.p1);
    this.p1.scaleAdd(paramFloat, this.speed, this.p0);

    if (this.explodeAtHeight > 0.0F) {
      if ((float)this.p1.z >= this.explodeAtHeight) {
        timeOut();
        destroy();
        return;
      }
    }
    else this.speed.z += this.gun.bulletAG[indx()] * paramFloat;
  }

  public void timeOut()
  {
    if ((this.explodeAtHeight <= 0.0F) && (!this.time_explode)) {
      return;
    }

    if (this.gun == null) {
      return;
    }

    BulletProperties localBulletProperties = properties();

    tmpP.interpolate(this.p0, this.p1, Time.tickOffset());
    MsgExplosion.send(null, null, tmpP, this.owner, localBulletProperties.massa, localBulletProperties.power, localBulletProperties.powerType, localBulletProperties.powerRadius);

    explodeInAir_Effect(tmpP);
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