// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Sun.java

package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CfgInt;

// Referenced classes of package com.maddox.il2.engine:
//            Config, EffClouds, Engine, Landscape, 
//            LandConf, RenderContext

public class Sun
{

    public Sun()
    {
        ToLight = new Vector3f();
        SunV = new Vector3f();
        ToSun = new Vector3f();
        ToMoon = new Vector3f();
        MoonPhase = 0.5F;
        SunV.set(0.0F, 0.0F, -1F);
        ToSun.set(SunV);
        ToSun.negate();
        ToMoon.set(ToSun);
        ToMoon.negate();
        ToLight.set(ToSun);
        Ambient = 0.5F;
        Diffuze = 0.5F;
        Specular = 1.0F;
        Red = 1.0F;
        Green = 1.0F;
        Blue = 1.0F;
    }

    public void activate()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            com.maddox.il2.engine.Sun.setNative(ToLight.x, ToLight.y, ToLight.z, Ambient, Diffuze, Specular, Red, Green, Blue);
    }

    public void set(com.maddox.JGP.Vector3f vector3f)
    {
        SunV.set(vector3f);
        ToSun.set(SunV);
        ToSun.negate();
        ToLight.set(ToSun);
    }

    public void setLight(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Ambient = f;
        Diffuze = f1;
        Specular = f2;
        Red = f3;
        Green = f4;
        Blue = f5;
    }

    public void setAstronomic(int i, int j, int k, int l, int i1, int j1)
    {
        setAstronomic(i, j, k, (float)l + (float)i1 / 60F + (float)j1 / 3600F);
    }

    public void setAstronomic(int i, int j, int k, float f)
    {
        float f2 = (float)(90 - i) * DEG2RAD;
        float f3 = (float)java.lang.Math.cos(f2);
        float f4 = (float)java.lang.Math.sin(f2);
        float f1 = (float)((j * 30 + k) - 80) * DEG2RAD;
        float f5 = (6.283185F * f) / 24F;
        float f6 = (float)java.lang.Math.sin(f5);
        float f7 = (float)java.lang.Math.cos(f5);
        float f8 = (float)java.lang.Math.sin(22.5F * DEG2RAD * (float)java.lang.Math.sin(f1));
        ToSun.x = f6;
        ToSun.y = f7 * f3 + f8 * f4;
        ToSun.z = f8 * f3 - f7 * f4;
        ToSun.normalize();
        SunV.x = -ToSun.x;
        SunV.y = -ToSun.y;
        SunV.z = -ToSun.z;
        MoonPhase = ((float)(j * 30 + k) % 29.53F) / 29.53F;
        f1 = (float)((j * 30 + k) - 80) * DEG2RAD;
        f5 = 6.283185F * (-MoonPhase + f / 24F);
        f6 = (float)java.lang.Math.sin(f5);
        f7 = (float)java.lang.Math.cos(f5);
        f8 = (float)java.lang.Math.sin(22.5F * DEG2RAD * (float)java.lang.Math.sin(f1));
        ToMoon.x = f6;
        ToMoon.y = f7 * f3 + f8 * f4;
        ToMoon.z = f8 * f3 - f7 * f4;
        ToMoon.normalize();
        boolean flag = true;
        float f10 = 1.0F - 2.0F * java.lang.Math.abs(MoonPhase - 0.5F);
        float f11 = (ToMoon.z - 0.1F) * 7F;
        if(f11 < 0.0F)
            f11 = 0.0F;
        if(f11 > 1.0F)
            f11 = 1.0F;
        float f12 = -1F + 0.15F * (f10 - 0.2F) * f11;
        float f9 = (ToSun.z + 0.11F) * 4F;
        if(f9 >= 1.0F)
            f9 = 1.0F;
        else
        if(f9 <= f12)
        {
            if(ToMoon.z > 0.1F && f10 > 0.2F)
                flag = false;
            f9 = f12;
        }
        f9 = f9 * 0.5F + 0.5F;
        float f13 = 0.2F + f9 * 1.25F;
        Diffuze = 0.05F + f9 * 0.95F;
        Specular = 0.4F + f9 * 0.6F;
        Ambient = f13 - Diffuze;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().clouds != null && com.maddox.il2.game.Main3D.cur3D().clouds.type() > 4)
            {
                float f14 = 0.2F + f9 * 0.7F;
                Diffuze = 0.05F + f9 * 0.95F * 0.4F;
                Specular = 0.4F + f9 * 0.6F * 0.5F;
                Ambient = f14 - Diffuze;
            }
            if(com.maddox.il2.engine.Engine.land().config.camouflage.equalsIgnoreCase("WINTER") && com.maddox.il2.engine.RenderContext.cfgHardwareShaders.get() > 0)
            {
                float f15 = (ToSun.z - 0.1F) * 7F;
                if(f15 < 0.0F)
                    f15 = 0.0F;
                if(f15 > 1.0F)
                    f15 = 1.0F;
                Ambient *= 1.0F * (1.0F - f15) + 1.7F * f15;
                Diffuze *= 1.0F * (1.0F - f15) + 0.6F * f15;
            }
        }
        if(flag)
        {
            Red = Green = Blue = 1.0F;
            float f16 = 1.0F - java.lang.Math.abs(ToSun.z) * 4F;
            if(f16 > 0.0F)
            {
                Green = 1.0F - 0.6F * f16;
                Blue = 1.0F - 0.7F * f16;
            }
            ToLight.set(ToSun);
            if(ToSun.z < 0.0F)
            {
                ToLight.z = 0.0F;
                ToLight.normalize();
            }
        } else
        {
            Red = 0.7F * f10;
            Green = Blue = f10;
            ToLight.set(ToMoon);
        }
        activate();
    }

    private static native void setNative(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7, 
            float f8);

    public com.maddox.JGP.Vector3f ToLight;
    public com.maddox.JGP.Vector3f SunV;
    public com.maddox.JGP.Vector3f ToSun;
    public com.maddox.JGP.Vector3f ToMoon;
    public float Ambient;
    public float Diffuze;
    public float Specular;
    public float Red;
    public float Green;
    public float Blue;
    public float MoonPhase;
    private static float DEG2RAD = 0.01745329F;

}
