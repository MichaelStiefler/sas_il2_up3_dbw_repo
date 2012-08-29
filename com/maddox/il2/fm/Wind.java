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
  float velocity;
  float top;
  float turbulence;
  float gust;

  public void set(int paramInt, float paramFloat, String paramString)
  {
    this.velocity = (0.25F + paramInt * paramInt * 0.12F);
    float f = (paramString.charAt(0) - '@') * 3.141593F;
    this.steady.set((float)Math.sin(f), (float)Math.cos(f), 0.0F);
    this.top = (paramFloat + 300.0F);
    this.turbulence = (paramInt > 2 ? paramInt : 0.0F);
    this.gust = (paramInt > 3 ? paramInt * 2.0F : 0.0F);
  }

  public void getVector(Point3d paramPoint3d, Vector3d paramVector3d)
  {
    float f1 = (float)Engine.cur.land.HQ(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double);
    float f2 = (float)(paramPoint3d.jdField_z_of_type_Double - f1);
    float f3 = 1.0F - f2 / this.top;

    if (f2 > this.top) {
      paramVector3d.set(0.0D, 0.0D, 0.0D);
      return;
    }

    paramVector3d.set(this.steady);
    paramVector3d.scale(this.velocity);
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

    if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) {
      paramVector3d.jdField_z_of_type_Double += 2.119999885559082D * (paramPoint3d.jdField_z_of_type_Double > 250.0D ? 1.0D : paramPoint3d.jdField_z_of_type_Double / 250.0D) * (float)Math.cos(World.getTimeofDay() * 2.0F * 3.141593F * 0.0416667F);
    }

    if (Atmosphere.temperature(0.0F) > 297.0F) {
      paramVector3d.jdField_z_of_type_Double += 1.0F * f3;
    }

    paramVector3d.scale(f3);

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

    if ((this.turbulence > 4.0F) && (paramPoint3d.jdField_z_of_type_Double > this.top - 400.0F) && (paramPoint3d.jdField_z_of_type_Double < this.top)) {
      f4 = Math.abs(this.top - 200.0F - (float)paramPoint3d.jdField_z_of_type_Double) * 0.0051F * this.turbulence;
      paramVector3d.add(World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4, f4));
    }
  }
}