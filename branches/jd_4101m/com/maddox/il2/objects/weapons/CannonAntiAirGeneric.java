package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public abstract class CannonAntiAirGeneric extends CannonMidrangeGeneric
  implements BulletAimer
{
  static final float MIN_AT_HEIGHT_EXPLODE = 250.0F;
  protected float explodeAtHeight;

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

    localGunProperties.sound = "weapon.zenitka_85";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 8.0F;
    localGunProperties.emitTime = 0.3F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 11000.0F;

    localGunProperties.shotFreq = 999.0F;

    localGunProperties.traceFreq = 0;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    for (int i = 0; i < 2; i++) {
      localGunProperties.bullet[i].massa = 0.001F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 10.0F;

      localGunProperties.bullet[i].power = (i == 0 ? 0.7F : 0.0F);
      localGunProperties.bullet[i].powerType = 1;
      localGunProperties.bullet[i].powerRadius = 140.0F;
      localGunProperties.bullet[i].timeLife = (i == 0 ? 20.0F : 6.0F);

      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = null;
      localGunProperties.bullet[i].traceColor = 0;
    }

    float f = Specify(localGunProperties);

    CannonMidrangeGeneric.autocomputeSplintersRadiuses(localGunProperties.bullet);

    if (f > 0.0F) {
      if (f <= 20.0F) f = 20.0F;
      if (f >= 70.0F) f = 70.0F;
      f = (f - 20.0F) / 50.0F;
      localGunProperties.maxDeltaAngle = (0.3F - f * 0.2F);
    } else {
      localGunProperties.maxDeltaAngle = 0.2F;
    }

    return localGunProperties;
  }

  protected abstract float Specify(GunProperties paramGunProperties);

  private float straightTravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f = (float)paramPoint3d1.distance(paramPoint3d2);
    if ((f < this.prop.aimMinDist) || (f > this.prop.aimMaxDist)) {
      return -1.0F;
    }
    return f / this.prop.bullet[0].speed;
  }

  private boolean straightFireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    float f = (float)paramPoint3d1.distance(paramPoint3d2);
    if ((f < this.prop.aimMinDist) || (f > this.prop.aimMaxDist)) {
      return false;
    }
    paramVector3d.set(paramPoint3d2);
    paramVector3d.sub(paramPoint3d1);
    paramVector3d.scale(1.0F / f);
    return true;
  }

  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    if ((this.bulletTypeIdx > 0) || ((float)(paramPoint3d2.z - paramPoint3d1.z) < 250.0F))
    {
      return super.TravelTime(paramPoint3d1, paramPoint3d2);
    }
    return straightTravelTime(paramPoint3d1, paramPoint3d2);
  }

  public boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    boolean bool;
    if ((this.bulletTypeIdx > 0) || ((float)(paramPoint3d2.z - paramPoint3d1.z) < 250.0F))
    {
      bool = super.FireDirection(paramPoint3d1, paramPoint3d2, paramVector3d);
      this.explodeAtHeight = -1.0F;
    } else {
      bool = straightFireDirection(paramPoint3d1, paramPoint3d2, paramVector3d);
      this.explodeAtHeight = (float)paramPoint3d2.z;
    }

    return bool;
  }
}