package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.Time;

public class Wind extends FMMath
{
  Vector3f steady = new Vector3f();
  float top;
  float turbulence;
  float gust;
  float velocityTop;
  float velocityTrans = 0.0F;
  float wTransitionAlt;
  float velocity10m;
  float velocityH;
  float velocityM;
  float velocityL;
  public boolean noWind = false;

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f = paramFloat2 * 3.141593F / 180.0F;
    this.steady.set((float)(-Math.sin(f)), (float)(-Math.cos(f)), 0.0F);
    this.top = (paramFloat1 + 300.0F);
    this.wTransitionAlt = (this.top / 2.0F);
    this.velocity10m = paramFloat3;
    this.velocityH = (this.velocity10m / 18000.0F);
    this.velocityM = (this.velocity10m / 9000.0F);
    this.velocityL = (this.velocity10m / 3000.0F);
    this.turbulence = paramFloat5;
    this.gust = paramFloat4;
    this.velocityTrans = (this.velocity10m * this.wTransitionAlt / 3000.0F + this.velocity10m);
    this.velocityTop = (this.velocity10m * (this.top - this.wTransitionAlt) / 9000.0F + this.velocityTrans);

    this.noWind = (paramFloat3 == 0.0F);
  }

  public void getVector(Point3d paramPoint3d, Vector3d paramVector3d)
  {
    float f1 = (float)Engine.cur.land.HQ(paramPoint3d.x, paramPoint3d.y);
    float f2 = (float)(paramPoint3d.z - f1);
    float f3 = 1.0F - f2 / this.top;

    paramVector3d.set(this.steady);
    paramVector3d.scale(windVelocity(f2));
    if (f2 > this.top)
      return;
    float f4;
    if (this.gust > 0.0F) {
      if (this.gust > 7.0F) {
        f4 = (float)Math.sin(0.005F * (float)Time.current() / 6.0F);

        if (f4 > 0.75F) {
          paramVector3d.scale(0.25F + f4);
        }
      }
      if (this.gust > 11.0F) {
        f4 = (float)Math.sin(0.005F * (float)Time.current() / 14.2F);

        if (f4 > 0.16F) {
          paramVector3d.scale(0.872F + f4 * 0.8F);
        }
      }
      if (this.gust > 9.0F) {
        f4 = (float)Math.sin(0.005F * (float)Time.current() / 39.84F);

        if (f4 > 0.86F) {
          paramVector3d.scale(0.14F + f4);
        }
      }
      if (this.gust > 9.0F) {
        f4 = (float)Math.sin(0.005F * (float)Time.current() / 12.3341F);

        if (f4 > 0.5F) {
          paramVector3d.scale(1.0F + f4 * 0.5F);
        }
      }
    }

    if (Engine.land().isWater(paramPoint3d.x, paramPoint3d.y)) {
      paramVector3d.z += 2.119999885559082D * (paramPoint3d.z > 250.0D ? 1.0D : paramPoint3d.z / 250.0D) * (float)Math.cos(World.getTimeofDay() * 2.0F * 3.141593F * 0.0416667F);
    }

    if (Atmosphere.temperature(0.0F) > 297.0F) {
      paramVector3d.z += 1.0F * f3;
    }

    paramVector3d.z *= f3;

    if ((f2 < 1000.0F) && (f1 > 999.0F)) {
      f4 = Math.abs(f2 - 500.0F) * 0.002F;
      f4 *= (float)(Math.sin(0.005F * (float)Time.current() / 13.899745F) + Math.sin(0.005F * (float)Time.current() / 9.6F) + Math.sin(0.005F * (float)Time.current() / 2.112F));
      if (f4 > 0.0F) {
        paramVector3d.scale(1.0F + f4);
      }
    }

    if ((this.turbulence > 2.0F) && (f2 < 300.0F)) {
      f4 = this.turbulence * f2 / 300.0F;
      paramVector3d.add(World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4));
    }

    if ((this.turbulence > 4.0F) && (paramPoint3d.z > this.top - 400.0F) && (paramPoint3d.z < this.top)) {
      f4 = Math.abs(this.top - 200.0F - (float)paramPoint3d.z) * 0.0051F * this.turbulence;
      paramVector3d.add(World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4));
    }
  }

  public void getVectorAI(Point3d paramPoint3d, Vector3d paramVector3d)
  {
    float f1 = (float)Engine.cur.land.HQ(paramPoint3d.x, paramPoint3d.y);
    float f2 = (float)(paramPoint3d.z - f1);

    paramVector3d.set(this.steady);
    paramVector3d.scale(windVelocity(f2));
  }

  public void getVectorWeapon(Point3d paramPoint3d, Vector3d paramVector3d)
  {
    float f1 = (float)Engine.cur.land.HQ(paramPoint3d.x, paramPoint3d.y);
    float f2 = (float)(paramPoint3d.z - f1);

    paramVector3d.set(this.steady);
    paramVector3d.scale(windVelocity(f2));
  }

  public float windVelocity(float paramFloat)
  {
    float f = 0.0F;
    if (paramFloat > this.top)
      f = this.velocityTop + (paramFloat - this.top) * this.velocityH;
    else if (paramFloat > this.wTransitionAlt)
      f = this.velocityTrans + (paramFloat - this.wTransitionAlt) * this.velocityM;
    else if (paramFloat > 10.0F) {
      f = this.velocity10m + this.velocityL * paramFloat;
    }
    if (paramFloat <= 10.0F) {
      f = this.velocity10m * (paramFloat + 5.0F) / 15.0F;
    }
    return f;
  }
}