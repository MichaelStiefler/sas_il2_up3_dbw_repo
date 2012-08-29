package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Atmosphere;

public abstract class CannonMidrangeGeneric extends Gun
  implements BulletAimer
{
  private float timeToFly;
  protected int bulletTypeIdx = 0;

  public final void setBulletType(int paramInt)
  {
    this.bulletTypeIdx = paramInt;
  }

  public int nextIndexBulletType() {
    return this.bulletTypeIdx;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletParabolaGeneric(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);
  }

  public static void autocomputeSplintersRadiuses(BulletProperties[] paramArrayOfBulletProperties) {
    for (int i = 0; i < paramArrayOfBulletProperties.length; i++) {
      if (paramArrayOfBulletProperties[i].power <= 0.0F)
      {
        continue;
      }
      if (paramArrayOfBulletProperties[i].powerType != 1)
      {
        continue;
      }
      float f1 = 110.0F;
      float f2 = 280.0F;

      float f3 = (paramArrayOfBulletProperties[i].kalibr - 0.037F) / 0.045F;

      if (f3 <= 0.0F) f3 = 0.0F;
      if (f3 >= 1.0F) f3 = 1.0F;

      paramArrayOfBulletProperties[i].powerRadius = (f1 + f3 * (f2 - f1));
    }
  }

  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.weaponType = 4;

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = false;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/88mm/CannonTank.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = null;

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 10.0F;
    localGunProperties.emitTime = 0.3F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 5000.0F;

    localGunProperties.shotFreq = 999.0F;

    localGunProperties.traceFreq = 0;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    for (int i = 0; i < localGunProperties.bullet.length; i++) {
      localGunProperties.bullet[i].massa = 0.001F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 10.0F;

      localGunProperties.bullet[i].power = 0.0F;
      if (i == 0)
        localGunProperties.bullet[i].powerType = 0;
      else {
        localGunProperties.bullet[i].powerType = 1;
      }
      localGunProperties.bullet[i].powerRadius = 140.0F;
      localGunProperties.bullet[i].timeLife = 10.0F;

      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = null;
      localGunProperties.bullet[i].traceColor = 0;
    }

    float f1 = Specify(localGunProperties);

    autocomputeSplintersRadiuses(localGunProperties.bullet);

    for (int j = 0; j < localGunProperties.bullet.length; j++) {
      float f2 = localGunProperties.aimMaxDist / (localGunProperties.bullet[j].speed * 0.707F);
      localGunProperties.bullet[j].timeLife = (f2 * 2.0F);
    }

    if (f1 > 0.0F) {
      if (f1 <= 20.0F) f1 = 20.0F;
      if (f1 >= 70.0F) f1 = 70.0F;
      f1 = (f1 - 20.0F) / 50.0F;
      localGunProperties.maxDeltaAngle = (0.3F - f1 * 0.2F);
    } else {
      localGunProperties.maxDeltaAngle = 0.2F;
    }

    return localGunProperties;
  }

  protected abstract float Specify(GunProperties paramGunProperties);

  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f1 = (float)(paramPoint3d2.z - paramPoint3d1.z);
    if (Math.abs(f1) > this.prop.aimMaxDist) {
      return -1.0F;
    }

    float f4 = (float)(paramPoint3d2.x - paramPoint3d1.x);
    float f5 = (float)(paramPoint3d2.y - paramPoint3d1.y);
    float f6 = (float)Math.sqrt(f4 * f4 + f5 * f5);
    if (f6 > this.prop.aimMaxDist) {
      return -1.0F;
    }
    if ((f6 < this.prop.aimMinDist) && 
      (Math.abs(f1) < this.prop.aimMinDist)) {
      return -1.0F;
    }

    float f2 = f6;
    float f3 = f1;

    f4 = -Atmosphere.g() / 2.0F;
    f5 = this.prop.bullet[this.bulletTypeIdx].speed;

    f6 = f4 * f4;
    float f7 = -(2.0F * f3 * f4 + f5 * f5);
    float f8 = f2 * f2 + f3 * f3;

    float f9 = f7 * f7 - 4.0F * f6 * f8;
    if (f9 < 0.0F) {
      return -1.0F;
    }

    f9 = (float)Math.sqrt(f9);
    float f10 = (-f7 + f9) / (2.0F * f6);
    float f11 = (-f7 - f9) / (2.0F * f6);
    if (f10 >= 0.0F) {
      if ((f11 >= 0.0F) && (f11 < f10))
        f10 = f11;
    }
    else if (f11 >= 0.0F)
      f10 = f11;
    else {
      return -1.0F;
    }

    this.timeToFly = (float)Math.sqrt(f10);

    return this.timeToFly;
  }

  public boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    float f1 = (float)(paramPoint3d2.x - paramPoint3d1.x);
    float f2 = (float)(paramPoint3d2.y - paramPoint3d1.y);
    float f3 = (float)(paramPoint3d2.z - paramPoint3d1.z);
    float f4 = (float)Math.sqrt(f1 * f1 + f2 * f2);
    float f5 = (float)(paramPoint3d2.z - paramPoint3d1.z);

    if (Math.abs(f4) < 0.01D) {
      paramVector3d.set(0.0D, 0.0D, 1.0D);
      if (f5 < 0.0F) {
        paramVector3d.negate();
      }
      return true;
    }

    paramVector3d.set(f1, f2, 0.0D);
    paramVector3d.scale(1.0F / f4);

    float f6 = this.prop.bullet[this.bulletTypeIdx].speed;

    if (this.timeToFly >= 1.0E-004F) {
      float f9 = -Atmosphere.g() / 2.0F;
      float f8 = (f5 - f9 * this.timeToFly * this.timeToFly) / this.timeToFly;
      float f7 = f4 / this.timeToFly;
      paramVector3d.x *= f7;
      paramVector3d.y *= f7;
      paramVector3d.z = f8;
      paramVector3d.normalize();
    } else {
      return false;
    }

    return true;
  }
}