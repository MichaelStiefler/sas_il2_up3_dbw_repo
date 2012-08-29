// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Wind.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            FMMath, Atmosphere

public class Wind extends com.maddox.il2.fm.FMMath
{

    public Wind()
    {
        steady = new Vector3f();
        velocityTrans = 0.0F;
        noWind = false;
    }

    public void set(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (f1 * 3.141593F) / 180F;
        steady.set((float)(-java.lang.Math.sin(f5)), (float)(-java.lang.Math.cos(f5)), 0.0F);
        top = f + 300F;
        wTransitionAlt = top / 2.0F;
        velocity10m = f2;
        velocityH = velocity10m / 18000F;
        velocityM = velocity10m / 9000F;
        velocityL = velocity10m / 3000F;
        turbulence = f4;
        gust = f3;
        velocityTrans = (velocity10m * wTransitionAlt) / 3000F + velocity10m;
        velocityTop = (velocity10m * (top - wTransitionAlt)) / 9000F + velocityTrans;
        noWind = f2 == 0.0F;
    }

    public void getVector(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)com.maddox.il2.engine.Engine.cur.land.HQ(point3d.x, point3d.y);
        float f1 = (float)(point3d.z - (double)f);
        float f2 = 1.0F - f1 / top;
        vector3d.set(steady);
        vector3d.scale(windVelocity(f1));
        if(f1 > top)
            return;
        if(gust > 0.0F)
        {
            if(gust > 7F)
            {
                float f3 = (float)java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 6F);
                if(f3 > 0.75F)
                    vector3d.scale(0.25F + f3);
            }
            if(gust > 11F)
            {
                float f4 = (float)java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 14.2F);
                if(f4 > 0.16F)
                    vector3d.scale(0.872F + f4 * 0.8F);
            }
            if(gust > 9F)
            {
                float f5 = (float)java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 39.84F);
                if(f5 > 0.86F)
                    vector3d.scale(0.14F + f5);
            }
            if(gust > 9F)
            {
                float f6 = (float)java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 12.3341F);
                if(f6 > 0.5F)
                    vector3d.scale(1.0F + f6 * 0.5F);
            }
        }
        if(com.maddox.il2.engine.Engine.land().isWater(point3d.x, point3d.y))
            vector3d.z += 2.119999885559082D * (point3d.z <= 250D ? point3d.z / 250D : 1.0D) * (double)(float)java.lang.Math.cos(com.maddox.il2.ai.World.getTimeofDay() * 2.0F * 3.141593F * 0.04166666F);
        if(com.maddox.il2.fm.Atmosphere.temperature(0.0F) > 297F)
            vector3d.z += 1.0F * f2;
        vector3d.z *= f2;
        if(f1 < 1000F && f > 999F)
        {
            float f7 = java.lang.Math.abs(f1 - 500F) * 0.002F;
            f7 *= (float)(java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 13.89974F) + java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 9.6F) + java.lang.Math.sin((0.005F * (float)com.maddox.rts.Time.current()) / 2.112F));
            if(f7 > 0.0F)
                vector3d.scale(1.0F + f7);
        }
        if(turbulence > 2.0F && f1 < 300F)
        {
            float f8 = (turbulence * f1) / 300F;
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextFloat(-f8, f8), com.maddox.il2.ai.World.Rnd().nextFloat(-f8, f8), com.maddox.il2.ai.World.Rnd().nextFloat(-f8, f8));
        }
        if(turbulence > 4F && point3d.z > (double)(top - 400F) && point3d.z < (double)top)
        {
            float f9 = java.lang.Math.abs(top - 200F - (float)point3d.z) * 0.0051F * turbulence;
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextFloat(-f9, f9), com.maddox.il2.ai.World.Rnd().nextFloat(-f9, f9), com.maddox.il2.ai.World.Rnd().nextFloat(-f9, f9));
        }
    }

    public void getVectorAI(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)com.maddox.il2.engine.Engine.cur.land.HQ(point3d.x, point3d.y);
        float f1 = (float)(point3d.z - (double)f);
        vector3d.set(steady);
        vector3d.scale(windVelocity(f1));
    }

    public void getVectorWeapon(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)com.maddox.il2.engine.Engine.cur.land.HQ(point3d.x, point3d.y);
        float f1 = (float)(point3d.z - (double)f);
        vector3d.set(steady);
        vector3d.scale(windVelocity(f1));
    }

    public float windVelocity(float f)
    {
        float f1 = 0.0F;
        if(f > top)
            f1 = velocityTop + (f - top) * velocityH;
        else
        if(f > wTransitionAlt)
            f1 = velocityTrans + (f - wTransitionAlt) * velocityM;
        else
        if(f > 10F)
            f1 = velocity10m + velocityL * f;
        if(f <= 10F)
            f1 = (velocity10m * (f + 5F)) / 15F;
        return f1;
    }

    com.maddox.JGP.Vector3f steady;
    float top;
    float turbulence;
    float gust;
    float velocityTop;
    float velocityTrans;
    float wTransitionAlt;
    float velocity10m;
    float velocityH;
    float velocityM;
    float velocityL;
    public boolean noWind;
}
