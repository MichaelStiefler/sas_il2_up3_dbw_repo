package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;

public abstract class CannonRocketSimple extends Gun
  implements BulletAimer
{
  private float timeToFly;
  private static Point3d tmpP = new Point3d();
  private static Orient tmpO = new Orient();

  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.weaponType = 8;

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = false;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = null;
    localGunProperties.sprite = null;
    localGunProperties.smoke = null;
    localGunProperties.shells = null;

    localGunProperties.sound = "weapon.Cannon75";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 7.0F;
    localGunProperties.emitTime = 0.35F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 8000.0F;

    localGunProperties.shotFreq = 999.0F;

    localGunProperties.traceFreq = 0;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    for (int i = 0; i < 1; i++) {
      localGunProperties.bullet[i].massa = 1.0F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 220.0F;

      localGunProperties.bullet[i].power = 0.0F;

      localGunProperties.bullet[i].powerRadius = 140.0F;
      localGunProperties.bullet[i].timeLife = 10.0F;

      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = null;
      localGunProperties.bullet[i].traceColor = 0;
    }

    localGunProperties.maxDeltaAngle = 0.4F;

    Specify(localGunProperties);

    return localGunProperties;
  }

  protected abstract void Specify(GunProperties paramGunProperties);

  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f1 = (float)(paramPoint3d2.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double);
    if (Math.abs(f1) > this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMaxDist) {
      return -1.0F;
    }

    float f4 = (float)(paramPoint3d2.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double);
    float f5 = (float)(paramPoint3d2.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double);
    float f6 = (float)Math.sqrt(f4 * f4 + f5 * f5);
    if ((f6 < this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMinDist) || (f6 > this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMaxDist)) {
      return -1.0F;
    }
    float f2 = f6;
    float f3 = f1;

    f4 = -Atmosphere.g() / 2.0F;
    f5 = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[0].speed;

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
    float f3 = (float)(paramPoint3d2.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double);
    float f4 = (float)(paramPoint3d2.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double);
    float f5 = (float)(paramPoint3d2.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double);
    float f6 = (float)Math.sqrt(f3 * f3 + f4 * f4);
    float f1 = f6;
    float f2 = (float)(paramPoint3d2.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double);

    if (Math.abs(f1) < 0.01D) {
      paramVector3d.set(0.0D, 0.0D, 1.0D);
      if (f2 < 0.0F) paramVector3d.negate();
      return true;
    }

    paramVector3d.set(f3, f4, 0.0D);
    paramVector3d.scale(1.0F / f1);

    float f7 = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[0].speed;

    if (this.timeToFly >= 1.0E-004F) {
      float f10 = -Atmosphere.g() / 2.0F;
      float f9 = (f2 - f10 * this.timeToFly * this.timeToFly) / this.timeToFly;
      float f8 = f1 / this.timeToFly;
      paramVector3d.jdField_x_of_type_Double *= f8;
      paramVector3d.jdField_y_of_type_Double *= f8;
      paramVector3d.jdField_z_of_type_Double = f9;
      paramVector3d.normalize();
    } else {
      return false;
    }

    return true;
  }

  public void doStartBullet(double paramDouble)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpP);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpO);
    if (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle > 0.0F) {
      float f1 = World.Rnd().nextFloat(-this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle);
      float f2 = World.Rnd().nextFloat(-this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle);
      tmpO.increment(f1, f2, 0.0F);
    }
    launch(tmpP, tmpO, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[0].speed, getOwner());
  }

  public abstract void launch(Point3d paramPoint3d, Orient paramOrient, float paramFloat, Actor paramActor);
}