package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CfgInt;
import java.util.GregorianCalendar;

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
  public float moonPhase = 0.5F;

  private static GregorianCalendar missionDate = null;
  private float tod = 0.0F;
  private static final float MAX_DARKNESS = 0.095F;
  public float darkness = 1.0F;
  public float sunMultiplier = 1.0F;

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
      setNative(this.ToLight.x, this.ToLight.y, this.ToLight.z, this.Ambient, this.Diffuze, this.Specular, this.Red, this.Green, this.Blue);
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
    setAstronomic(paramInt1, paramInt2, paramInt3, paramFloat, 1.0F, 1.0F, 1.0F);
  }

  public void resetCalendar()
  {
    missionDate = null;
  }

  public void setAstronomic(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f2 = (90 - paramInt1) * DEG2RAD;
    float f3 = (float)Math.cos(f2);
    float f4 = (float)Math.sin(f2);

    float f1 = (paramInt2 * 30 + paramInt3 - 80) * DEG2RAD;
    float f5 = 6.283186F * paramFloat1 / 24.0F;

    float f6 = (float)Math.sin(f5);
    float f7 = (float)Math.cos(f5);
    float f8 = (float)Math.sin(22.5F * DEG2RAD * (float)Math.sin(f1));

    this.ToSun.x = f6;
    this.ToSun.y = (f7 * f3 + f8 * f4);
    this.ToSun.z = (f8 * f3 - f7 * f4);
    this.ToSun.normalize();

    this.SunV.x = (-this.ToSun.x);
    this.SunV.y = (-this.ToSun.y);
    this.SunV.z = (-this.ToSun.z);

    int i = Mission.curYear();
    int j = (int)Math.floor(paramFloat1);
    int k = (int)((paramFloat1 - j) * 60.0F);
    int m = 0;

    if (missionDate == null)
    {
      missionDate = new GregorianCalendar(i, paramInt2 - 1, paramInt3, j, k, m);
    }
    else if (paramFloat1 != this.tod)
    {
      missionDate.roll(13, true);
    }
    this.tod = paramFloat1;

    double[] arrayOfDouble = new double[10];
    arrayOfDouble = MoonPhase.phase(missionDate);
    this.moonPhase = ((float)arrayOfDouble[2] / 29.530001F);

    f1 = (paramInt2 * 30 + paramInt3 - 80) * DEG2RAD;
    f5 = 6.283186F * (-this.moonPhase + paramFloat1 / 24.0F);

    f6 = (float)Math.sin(f5);
    f7 = (float)Math.cos(f5);
    f8 = (float)Math.sin(22.5F * DEG2RAD * (float)Math.sin(f1));

    this.ToMoon.x = f6;
    this.ToMoon.y = (f7 * f3 + f8 * f4);
    this.ToMoon.z = (f8 * f3 - f7 * f4);
    this.ToMoon.normalize();

    int n = 1;

    float f10 = 1.0F - 2.0F * Math.abs(this.moonPhase - 0.5F);

    float f11 = (this.ToMoon.z - 0.1F) * 7.0F;
    if (f11 < 0.0F) f11 = 0.0F;
    if (f11 > 1.0F) f11 = 1.0F;
    float f12 = -1.0F + 0.15F * (f10 - 0.2F) * f11;

    float f9 = (this.ToSun.z + 0.11F) * 4.0F;
    if (f9 >= 1.0F) {
      f9 = 1.0F;
    }
    else if (f9 <= f12) {
      if ((this.ToMoon.z > 0.1F) && (f10 > 0.2F)) {
        n = 0;
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
        f14 = (this.ToSun.z - 0.1F) * 7.0F;
        if (f14 < 0.0F) f14 = 0.0F;
        if (f14 > 1.0F) f14 = 1.0F;
        this.Ambient *= (1.0F * (1.0F - f14) + 1.7F * f14);
        this.Diffuze *= (1.0F * (1.0F - f14) + 0.6F * f14);
      }

      f14 = 1.0F;

      this.darkness = (0.095F + f10 * 0.666F);
      this.sunMultiplier = cvt(this.ToSun.z, -0.6F, 0.0F, this.darkness, 1.0F);
      f14 = cvt(this.sunMultiplier, this.darkness, 1.0F, 0.5F, 1.0F);

      this.Ambient *= paramFloat2 * this.sunMultiplier;
      this.Specular *= paramFloat3 * f14;
      this.Diffuze *= paramFloat4 * this.sunMultiplier;
    }

    if (n != 0) {
      this.Red = (this.Green = this.Blue = 1.0F);
      f14 = 1.0F - Math.abs(this.ToSun.z) * 4.0F;
      if (f14 > 0.0F) {
        this.Green = (1.0F - 0.6F * f14);
        this.Blue = (1.0F - 0.7F * f14);
      }
      this.ToLight.set(this.ToSun);
      if (this.ToSun.z < 0.0F) {
        this.ToLight.z = 0.0F;
        this.ToLight.normalize();
      }
    } else {
      this.Red = (0.7F * f10);
      this.Green = (this.Blue = f10);
      this.ToLight.set(this.ToMoon);
    }

    activate();
  }

  private static float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }

  private static native void setNative(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);
}