package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;

public class Explosion
{
  public static final int POWER_SPLASH = 0;
  public static final int POWER_SPLINTERS = 1;
  public static final int POWER_NAPALM = 2;
  public static final float SPLINTER_MASS = 0.015F;
  private static final float SPLINTERS_K = 0.9F;
  public String chunkName;
  public Point3d p = new Point3d();
  public float radius;
  public Actor initiator;
  public float power;
  public int powerType;
  private float nSplinters;
  public boolean bNuke;

  void computeSplinterParams(float paramFloat)
  {
    float f = paramFloat * 0.9F;
    this.nSplinters = (f / 0.015F);
    if (this.nSplinters < 0.5F)
      this.nSplinters = 0.0F;
  }

  public float computeSplinterSpeed(float paramFloat) {
    if (paramFloat <= 0.01F)
      return 650.0F;
    if (paramFloat >= this.radius)
      return 150.0F;
    float f = paramFloat / this.radius;
    return 650.0F * (1.0F - f) + 150.0F * f;
  }

  public void computeSplintersHit(Point3d paramPoint3d, float paramFloat1, float paramFloat2, float[] paramArrayOfFloat)
  {
    float f1 = (float)paramPoint3d.distance(this.p) - paramFloat1;
    if (f1 <= 0.001F) {
      paramArrayOfFloat[0] = (this.nSplinters * 0.5F);
      paramArrayOfFloat[1] = computeSplinterSpeed(f1);
    }
    float f2 = 3.141593F * paramFloat1 * paramFloat1;
    float f3 = 12.566371F * f1 * f1;
    float f4 = this.nSplinters * f2 / f3;
    if (f4 >= this.nSplinters * 0.5F)
      f4 = this.nSplinters * 0.5F;
    paramArrayOfFloat[0] = f4;
    paramArrayOfFloat[1] = computeSplinterSpeed(f1);
  }

  public boolean isMirage() {
    if (!Actor.isValid(this.initiator))
      return true;
    return this.initiator.isNetMirror();
  }

  public float receivedPower(ActorMesh paramActorMesh) {
    float f1 = paramActorMesh.collisionR();
    float f2 = (float)paramActorMesh.pos.getAbsPoint().distance(this.p);
    f2 -= f1;
    if (f2 >= this.radius)
      return 0.0F;
    float f3 = 1.0F - f2 / this.radius;
    f3 *= f3;
    if (f3 >= 1.0F)
      return this.power;
    return f3 * this.power;
  }

  public float receivedTNT_1meter(float paramFloat) {
    if (paramFloat >= this.radius)
      return 0.0F;
    if ((paramFloat < 1.0F) || (this.bNuke))
      return this.power;
    return this.power / (paramFloat * paramFloat);
  }

  public float receivedTNT_1meter(Point3d paramPoint3d, float paramFloat) {
    float f = (float)paramPoint3d.distance(this.p) - paramFloat;
    return receivedTNT_1meter(f);
  }

  public float receivedTNT_1meter(ActorMesh paramActorMesh) {
    float f = (float)paramActorMesh.pos.getAbsPoint().distance(this.p) - paramActorMesh.collisionR();

    return receivedTNT_1meter(f);
  }

  public float receivedTNTpower(ActorMesh paramActorMesh) {
    float f1 = paramActorMesh.collisionR();
    float f2 = (float)paramActorMesh.pos.getAbsPoint().distance(this.p) - f1;
    if (f2 <= 0.0F)
      return 0.5F * this.power;
    float f3 = 1.0F / (float)Math.pow(f2, 1.200000047683716D);

    if (f3 <= 0.0F)
      return 0.0F;
    if (f3 >= 0.5F)
      f3 = 0.5F;
    return f3 * this.power;
  }

  public static boolean killable(ActorMesh paramActorMesh, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1;
    if (f1 <= paramFloat2)
      return false;
    if (f1 >= paramFloat3)
      return true;
    float f2 = (f1 - paramFloat2) / (paramFloat3 - paramFloat2);
    paramFloat4 += (1.0F - paramFloat4) * f2;
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat4;
  }
}