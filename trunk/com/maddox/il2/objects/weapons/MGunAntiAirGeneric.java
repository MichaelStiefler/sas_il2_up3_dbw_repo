package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.rts.Time;

public abstract class MGunAntiAirGeneric extends CannonMidrangeGeneric
  implements BulletAimer
{
  protected long explAddTimeT;

  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.weaponType = 2;

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = false;

    localGunProperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = null;

    localGunProperties.sound = "weapon.zenitka_20";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.1F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 2000.0F;

    localGunProperties.shotFreq = 1.0F;

    localGunProperties.traceFreq = 2;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 2;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    for (int i = 0; i < 2; i++) {
      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
      localGunProperties.bullet[i].traceColor = 16843009;

      localGunProperties.bullet[i].massa = 0.001F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 10.0F;

      localGunProperties.bullet[i].power = (i == 0 ? 0.001F : 0.0F);
      localGunProperties.bullet[i].powerType = 1;
      localGunProperties.bullet[i].powerRadius = 10.0F;
      localGunProperties.bullet[i].timeLife = (i == 0 ? 9.0F : 5.0F);
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
    float f = super.TravelTime(paramPoint3d1, paramPoint3d2);
    if (this.bulletTypeIdx > 0) {
      return f;
    }

    if (f > this.prop.bullet[0].timeLife + this.prop.bullet[0].addExplTime) {
      return -1.0F;
    }
    return f;
  }

  public static final float Rnd(float paramFloat1, float paramFloat2)
  {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    this.explAddTimeT = 0L;

    if (this.bulletTypeIdx == 0) {
      float f = this.prop.bullet[0].addExplTime;
      if (f > 0.0F) {
        this.explAddTimeT = SecsToTicks(Rnd(-f, f));
        if (this.explAddTimeT == 0L) {
          this.explAddTimeT = 1L;
        }
      }
    }

    return super.FireDirection(paramPoint3d1, paramPoint3d2, paramVector3d);
  }
}