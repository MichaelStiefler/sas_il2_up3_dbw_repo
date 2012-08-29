// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Line2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector2f, Point2f, JGPException, Point3f, 
//            NSolvef

public class Line2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Line2f()
    {
        N = new Vector2f();
        tmpP = new Point2f();
        N.x = 1.0F;
        C = 0.0F;
    }

    public Line2f(com.maddox.JGP.Line2f line2f)
    {
        N = new Vector2f();
        tmpP = new Point2f();
        N.set(line2f.N);
        C = line2f.C;
    }

    public Line2f(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        N = new Vector2f();
        tmpP = new Point2f();
        set(point2f, point2f1);
    }

    public final void set(com.maddox.JGP.Line2f line2f)
    {
        N.set(line2f.N);
        C = line2f.C;
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        N.x = point2f.y - point2f1.y;
        N.y = point2f1.x - point2f.x;
        N.normalize();
        C = -N.dot(point2f);
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Vector2f vector2f)
    {
        N.x = vector2f.x;
        N.y = vector2f.y;
        N.normalize();
        C = -N.dot(point2f);
    }

    public final float deviation(com.maddox.JGP.Point2f point2f)
    {
        return N.dot(point2f) + C;
    }

    public final float distance(com.maddox.JGP.Point2f point2f)
    {
        return java.lang.Math.abs(N.dot(point2f) + C);
    }

    public final void mirror(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        tmpP.scale(-2F * deviation(point2f), N);
        point2f1.add(point2f, tmpP);
    }

    public final void mirror(com.maddox.JGP.Point2f point2f)
    {
        tmpP.scale(-2F * deviation(point2f), N);
        point2f.add(tmpP);
    }

    public final void mirror(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        tmpP.set(point3f);
        tmpP.scale(-2F * deviation(tmpP), N);
        point3f1.x = point3f.x + tmpP.x;
        point3f1.y = point3f.y + tmpP.y;
        point3f1.z = point3f.z;
    }

    public final void mirror(com.maddox.JGP.Point3f point3f)
    {
        tmpP.set(point3f);
        tmpP.scale(-2F * deviation(tmpP), N);
        point3f.x += tmpP.x;
        point3f.y += tmpP.y;
    }

    public final void project(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        tmpP.scale(-deviation(point2f), N);
        point2f1.add(point2f, tmpP);
    }

    public final void project(com.maddox.JGP.Point2f point2f)
    {
        tmpP.scale(-deviation(point2f), N);
        point2f.add(tmpP);
    }

    public final float distance(com.maddox.JGP.Line2f line2f)
    {
        if(!N.equals(line2f.N))
            return 0.0F;
        else
            return java.lang.Math.abs(C - line2f.C);
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Line2f line2f)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            N.x, N.y, -C, line2f.N.x, line2f.N.y, -line2f.C
        };
        return com.maddox.JGP.NSolvef.Solve2f(af);
    }

    public final void crossXc(float f, com.maddox.JGP.Point2f point2f)
    {
        point2f.x = f;
        float f1 = (-N.x * f - C) / N.y;
        if(java.lang.Math.abs(f1) < 1E+036F)
        {
            point2f.y = f1;
            return;
        } else
        {
            point2f.y = 1E+036F;
            return;
        }
    }

    public final void crossYc(float f, com.maddox.JGP.Point2f point2f)
    {
        point2f.y = f;
        float f1 = (-N.y * f - C) / N.x;
        if(java.lang.Math.abs(f1) < 1E+036F)
        {
            point2f.x = f1;
            return;
        } else
        {
            point2f.x = 1E+036F;
            return;
        }
    }

    public final void crossXsubYc(float f, com.maddox.JGP.Point2f point2f)
    {
        float f1 = (-N.y * f - C) / (N.x + N.y);
        if(java.lang.Math.abs(f1) < 1E+036F)
        {
            point2f.x = f1;
            point2f.y = f1 + f;
            return;
        } else
        {
            point2f.x = point2f.y = 1E+036F;
            return;
        }
    }

    public final void crossXaddYc(float f, com.maddox.JGP.Point2f point2f)
    {
        float f1 = (N.y * f + C) / (N.y - N.x);
        if(java.lang.Math.abs(f1) < 1E+036F)
        {
            point2f.x = f1;
            point2f.y = f - f1;
            return;
        } else
        {
            point2f.x = point2f.y = 1E+036F;
            return;
        }
    }

    public final boolean crossed(com.maddox.JGP.Line2f line2f)
    {
        return !N.equals(line2f.N);
    }

    public final boolean equals(com.maddox.JGP.Line2f line2f)
    {
        return N.equals(line2f.N) && C == line2f.C;
    }

    public final boolean parallel(com.maddox.JGP.Line2f line2f)
    {
        return N.equals(line2f.N);
    }

    public final float cos(com.maddox.JGP.Line2f line2f)
    {
        return N.dot(line2f.N);
    }

    public java.lang.String toString()
    {
        return "( " + N.x + "," + N.y + "; " + C + " )";
    }

    public com.maddox.JGP.Vector2f N;
    public float C;
    private com.maddox.JGP.Point2f tmpP;
}
