package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CfgInt;

public class Sun
{
  public Vector3f ToLight = new Vector3f();

  public Vector3f SunV = new Vector3f();

  public Vector3f ToSun = new Vector3f();

  public Vector3f ToMoon = new Vector3f();
  public float Ambient;
  public float Diffuze;
  public float Specular;
  public float Red;
  public float Green;
  public float Blue;
  public float MoonPhase = 0.5F;

  private static float DEG2RAD = 0.01745329F;

  public Sun() {
    this.SunV.set(0.0F, 0.0F, -1.0F);

    this.ToSun.set(this.SunV);
    this.ToSun.negate();

    this.ToMoon.set(this.ToSun);
    this.ToMoon.negate();

    this.ToLight.set(this.ToSun);

    this.Ambient = 0.5F;
    this.Diffuze = 0.5F;
    this.Specular = 1.0F;
    this.Red = 1.0F;
    this.Green = 1.0F;
    this.Blue = 1.0F;
  }

  public void activate() {
    if (Config.isUSE_RENDER())
      setNative(this.ToLight.jdField_x_of_type_Float, this.ToLight.jdField_y_of_type_Float, this.ToLight.jdField_z_of_type_Float, this.Ambient, this.Diffuze, this.Specular, this.Red, this.Green, this.Blue);
  }

  public void set(Vector3f paramVector3f)
  {
    this.SunV.set(paramVector3f);
    this.ToSun.set(this.SunV);
    this.ToSun.negate();
    this.ToLight.set(this.ToSun);
  }

  public void setLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    this.Ambient = paramFloat1;
    this.Diffuze = paramFloat2;
    this.Specular = paramFloat3;
    this.Red = paramFloat4;
    this.Green = paramFloat5;
    this.Blue = paramFloat6;
  }

  public void setAstronomic(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setAstronomic(paramInt1, paramInt2, paramInt3, paramInt4 + paramInt5 / 60.0F + paramInt6 / 3600.0F);
  }

  public void setAstronomic(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    float f2 = (90 - paramInt1) * DEG2RAD;
    float f3 = (float)Math.cos(f2);
    float f4 = (float)Math.sin(f2);

    float f1 = (paramInt2 * 30 + paramInt3 - 80) * DEG2RAD;
    float f5 = 6.283186F * paramFloat / 24.0F;

    float f6 = (float)Math.sin(f5);
    float f7 = (float)Math.cos(f5);
    float f8 = (float)Math.sin(22.5F * DEG2RAD * (float)Math.sin(f1));

    this.ToSun.jdField_x_of_type_Float = f6;
    this.ToSun.jdField_y_of_type_Float = (f7 * f3 + f8 * f4);
    this.ToSun.jdField_z_of_type_Float = (f8 * f3 - f7 * f4);
    this.ToSun.normalize();

    this.SunV.jdField_x_of_type_Float = (-this.ToSun.jdField_x_of_type_Float);
    this.SunV.jdField_y_of_type_Float = (-this.ToSun.jdField_y_of_type_Float);
    this.SunV.jdField_z_of_type_Float = (-this.ToSun.jdField_z_of_type_Float);

    this.MoonPhase = ((paramInt2 * 30 + paramInt3) % 29.530001F / 29.530001F);
    f1 = (paramInt2 * 30 + paramInt3 - 80) * DEG2RAD;
    f5 = 6.283186F * (-this.MoonPhase + paramFloat / 24.0F);

    f6 = (float)Math.sin(f5);
    f7 = (float)Math.cos(f5);
    f8 = (float)Math.sin(22.5F * DEG2RAD * (float)Math.sin(f1));

    this.ToMoon.jdField_x_of_type_Float = f6;
    this.ToMoon.jdField_y_of_type_Float = (f7 * f3 + f8 * f4);
    this.ToMoon.jdField_z_of_type_Float = (f8 * f3 - f7 * f4);
    this.ToMoon.normalize();

    int i = 1;

    float f10 = 1.0F - 2.0F * Math.abs(this.MoonPhase - 0.5F);

    float f11 = (this.ToMoon.jdField_z_of_type_Float - 0.1F) * 7.0F;
    if (f11 < 0.0F) f11 = 0.0F;
    if (f11 > 1.0F) f11 = 1.0F;
    float f12 = -1.0F + 0.15F * (f10 - 0.2F) * f11;

    float f9 = (this.ToSun.jdField_z_of_type_Float + 0.11F) * 4.0F;
    if (f9 >= 1.0F) {
      f9 = 1.0F;
    }
    else if (f9 <= f12) {
      if ((this.ToMoon.jdField_z_of_type_Float > 0.1F) && (f10 > 0.2F)) {
        i = 0;
      }
      f9 = f12;
    }
    f9 = f9 * 0.5F + 0.5F;

    float f13 = 0.2F + f9 * 1.25F;
    this.Diffuze = (0.05F + f9 * 0.95F);
    this.Specular = (0.4F + f9 * 0.6F);
    this.Ambient = (f13 - this.Diffuze);
    float f14;
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().clouds != null) && (Main3D.cur3D().clouds.type() > 4)) {
        f13 = 0.2F + f9 * 0.7F;
        this.Diffuze = (0.05F + f9 * 0.95F * 0.4F);
        this.Specular = (0.4F + f9 * 0.6F * 0.5F);
        this.Ambient = (f13 - this.Diffuze);
      }

      if ((Engine.land().config.camouflage.equalsIgnoreCase("WINTER")) && (RenderContext.cfgHardwareShaders.get() > 0))
      {
        f14 = (this.ToSun.jdField_z_of_type_Float - 0.1F) * 7.0F;
        if (f14 < 0.0F) f14 = 0.0F;
        if (f14 > 1.0F) f14 = 1.0F;
        this.Ambient *= (1.0F * (1.0F - f14) + 1.7F * f14);
        this.Diffuze *= (1.0F * (1.0F - f14) + 0.6F * f14);
      }

    }

    if (i != 0) {
      this.Red = (this.Green = this.Blue = 1.0F);
      f14 = 1.0F - Math.abs(this.ToSun.jdField_z_of_type_Float) * 4.0F;
      if (f14 > 0.0F) {
        this.Green = (1.0F - 0.6F * f14);
        this.Blue = (1.0F - 0.7F * f14);
      }
      this.ToLight.set(this.ToSun);
      if (this.ToSun.jdField_z_of_type_Float < 0.0F) {
        this.ToLight.jdField_z_of_type_Float = 0.0F;
        this.ToLight.normalize();
      }
    } else {
      this.Red = (0.7F * f10);
      this.Green = (this.Blue = f10);
      this.ToLight.set(this.ToMoon);
    }

    activate();
  }

  private static native void setNative(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);
}