// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Shot.java

package com.maddox.il2.ai;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;

// Referenced classes of package com.maddox.il2.ai:
//            AnglesFork

public class Shot
{

    public Shot()
    {
        p = new Point3d();
        v = new Vector3d();
        bodyNormal = new Vector3f();
    }

    public boolean isMirage()
    {
        if(!com.maddox.il2.engine.Actor.isValid(initiator))
            return true;
        else
            return initiator.isNetMirror();
    }

    public float powerToTNT()
    {
        return power * 2.4E-007F;
    }

    public static float panzerThickness(com.maddox.il2.engine.Orient orient, com.maddox.JGP.Vector3d vector3d, boolean flag, float f, float f1, float f2, float f3, float f4, 
            float f5)
    {
        double d1 = vector3d.length();
        double d;
        if(d1 <= 9.9999997473787516E-005D)
        {
            d = flag ? f4 : f2;
        } else
        {
            double d2 = vector3d.x * vector3d.x + vector3d.y * vector3d.y;
            double d3 = vector3d.z * vector3d.z;
            if(d3 >= d2 * 0.5D * 0.5D)
            {
                double d4 = java.lang.Math.abs(vector3d.z) / d1;
                d = (double)(flag ? f5 : f3) / java.lang.Math.sqrt(d4);
            } else
            {
                float f6 = com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.atan2(vector3d.y, vector3d.x));
                com.maddox.il2.ai.AnglesFork anglesfork = new AnglesFork(f6, orient.getYaw());
                f6 = anglesfork.getAbsDiffDeg();
                if(f6 <= 45F)
                    d = flag ? f4 : f2;
                else
                if(f6 >= 135F)
                {
                    d = flag ? f4 : f;
                } else
                {
                    d = flag ? f4 : f1;
                    f6 = 90F - f6;
                }
                float f7 = com.maddox.JGP.Geom.cosDeg(f6);
                d /= (float)java.lang.Math.sqrt(java.lang.Math.abs(f7));
            }
        }
        return (float)d;
    }

    public static final int POWER_SHELL = 0;
    public static final int POWER_CUMULATIVE = 1;
    public static final int POWER_AP = 2;
    public static final int POWER_API = 3;
    public static final int BODY_GROUND = 0;
    public static final int BODY_WATER = 1;
    public static final int BODY_METALL = 2;
    public static final int BODY_WOOD = 3;
    public static final int BODY_ROCK = 4;
    public java.lang.String chunkName;
    public com.maddox.JGP.Point3d p;
    public double tickOffset;
    public com.maddox.JGP.Vector3d v;
    public com.maddox.il2.engine.Actor initiator;
    public float power;
    public float mass;
    public int powerType;
    public int bodyMaterial;
    public com.maddox.JGP.Vector3f bodyNormal;
}
