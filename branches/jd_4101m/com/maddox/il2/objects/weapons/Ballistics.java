package com.maddox.il2.objects.weapons;

import com.maddox.JGP.AxisAngle4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Wind;
import com.maddox.rts.Time;

public class Ballistics
{
  private static Point3d pos = new Point3d();
  private static Orient or = new Orient();
  private static Orient orW = new Orient();
  private static Vector3d v = new Vector3d();
  private static Vector3d dir = new Vector3d();
  private static Vector3d dirC = new Vector3d();
  private static Vector3d dirW = new Vector3d();
  private static Vector3d force = new Vector3d();
  private static AxisAngle4d axAn = new AxisAngle4d();

  private static float KD(float paramFloat)
  {
    return 1.0F + paramFloat * (-9.59387E-005F + paramFloat * (3.53118E-009F + paramFloat * -5.83556E-014F));
  }

  private static float KF(float paramFloat)
  {
    return (608.5F + (-1.81327F + 0.0016511F * paramFloat) * paramFloat) * paramFloat;
  }

  public static void updateBomb(Bomb paramBomb, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = Time.tickLenFs();

    float f2 = (float)paramBomb.getSpeed(v);

    paramBomb.pos.getAbs(pos, or);
    dirC.set(1.0D, 0.0D, 0.0D);
    or.transform(dirC);

    if (f2 < 0.001F) {
      dirW.set(0.0D, 0.0D, -1.0D);
    } else {
      dirW.set(v);
      dirW.scale(1.0F / f2);
    }

    float f3 = (float)dirC.dot(dirW);
    float f4;
    if (f2 > 330.0F)
      f4 = KF(f2) * KD((float)pos.z) * paramFloat2;
    else {
      f4 = 0.06F * paramFloat2 * 1.225F * KD((float)pos.z) * f2 * f2 / 2.0F;
    }
    float f5 = f4 / paramFloat1;

    dir.scale(6.0F * f3, dirC);
    dir.x += -7.0D * dirW.x;
    dir.y += -7.0D * dirW.y;
    dir.z += -7.0D * dirW.z;
    dir.scale(f5 * f1);

    if (!World.cur().diffCur.Realistic_Gunnery) {
      dir.set(0.0D, 0.0D, 0.0D);
    }

    v.add(dir);
    v.z -= f1 * Atmosphere.g();
    paramBomb.setSpeed(v);

    pos.x += v.x * f1;
    pos.y += v.y * f1;
    pos.z += v.z * f1;

    if ((paramFloat1 > 35.0F) && 
      (World.cur().diffCur.Wind_N_Turbulence) && (World.cur().diffCur.Realistic_Gunnery)) {
      Vector3d localVector3d = new Vector3d();
      World.wind().getVectorWeapon(pos, localVector3d);
      pos.x += localVector3d.x * f1;
      pos.y += localVector3d.y * f1;
    }

    if (paramBomb.curTm > 0.35F)
    {
      float f6 = (float)Math.sqrt(1.0F - f3 * f3);
      if (f3 <= -0.996F) {
        f6 = 0.08F;
      }

      float f7 = f4 * 0.07F * f6;

      force.set(dirW);
      force.scale(-f7);

      dirW.set(dirC);
      dirW.scale(-paramFloat4);

      dirW.cross(dirW, force);
      dirW.scale(1.0F / paramFloat3);
      dirW.scale(f1);

      paramBomb.rotAxis.add(dirW);

      paramBomb.rotAxis.scale(0.96D);
    }

    axAn.set(paramBomb.rotAxis);
    axAn.angle *= f1;
    axAn.rotateRightHanded(dirC);

    or.setAT0(dirC);

    paramBomb.pos.setAbs(pos, or);
  }

  public static void updateRocketBomb(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    float f1 = Time.tickLenFs();
    float f2 = (float)paramActor.getSpeed(v);
    paramActor.pos.getAbs(pos, or);
    dir.set(1.0D, 0.0D, 0.0D);
    or.transform(dir);
    float f3;
    if (f2 > 330.0F)
      f3 = KF(f2) * KD((float)pos.z) * paramFloat2;
    else {
      f3 = 0.2F * paramFloat2 * 1.225F * KD((float)pos.z) * f2 * f2 / 2.0F;
    }
    float f4 = (paramFloat3 - f3) / paramFloat1;
    dir.scale(f4 * f1);
    v.add(dir);
    if (paramBoolean)
      v.z -= f1 * Atmosphere.g();
    paramActor.setSpeed(v);
    pos.x += v.x * f1;
    pos.y += v.y * f1;
    pos.z += v.z * f1;
    if (paramFloat3 < 1000000.0F)
      or.setAT0(v);
    paramActor.pos.setAbs(pos, or);
  }

  public static void update(Actor paramActor, float paramFloat1, float paramFloat2)
  {
    update(paramActor, paramFloat1, paramFloat2, 0.0F, true);
  }

  public static void update(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    float f4 = Time.tickLenFs();
    float f1 = (float)paramActor.getSpeed(v);
    paramActor.pos.getAbs(pos, or);
    dir.set(1.0D, 0.0D, 0.0D); or.transform(dir);
    float f2;
    if (f1 > 330.0F) f2 = KF(f1) * KD((float)pos.z) * paramFloat2;
    else {
      f2 = 0.2F * paramFloat2 * 1.225F * KD((float)pos.z) * f1 * f1 / 2.0F;
    }
    float f3 = (paramFloat3 - f2) / paramFloat1;
    dir.scale(f3 * f4);
    v.add(dir);
    if (paramBoolean) v.z -= f4 * Atmosphere.g();
    paramActor.setSpeed(v);
    pos.x += v.x * f4;
    pos.y += v.y * f4;
    pos.z += v.z * f4;

    if ((World.cur().diffCur.Wind_N_Turbulence) && (World.cur().diffCur.Realistic_Gunnery)) {
      Vector3d localVector3d = new Vector3d();
      World.wind().getVectorWeapon(pos, localVector3d);
      pos.x += localVector3d.x * f4;
      pos.y += localVector3d.y * f4;
    }

    if (paramFloat3 < 1.0F)
      or.setAT0(v);
    paramActor.pos.setAbs(pos, or);
  }

  public static void update(Point3d paramPoint3d, Orient paramOrient, Vector3d paramVector3d, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean, float paramFloat4)
  {
    float f1 = (float)paramVector3d.length();
    dir.set(1.0D, 0.0D, 0.0D); paramOrient.transform(dir);
    float f2;
    if (f1 > 330.0F) f2 = KF(f1) * KD((float)paramPoint3d.z) * paramFloat2;
    else {
      f2 = 0.2F * paramFloat2 * 1.225F * KD((float)paramPoint3d.z) * f1 * f1 / 2.0F;
    }
    float f3 = (paramFloat3 - f2) / paramFloat1;
    dir.scale(f3 * paramFloat4);
    paramVector3d.add(dir);
    if (paramBoolean) paramVector3d.z -= paramFloat4 * Atmosphere.g();
    paramPoint3d.x += paramVector3d.x * paramFloat4;
    paramPoint3d.y += paramVector3d.y * paramFloat4;
    paramPoint3d.z += paramVector3d.z * paramFloat4;
    if (paramFloat3 < 1.0F)
      paramOrient.setAT0(paramVector3d);
  }
}