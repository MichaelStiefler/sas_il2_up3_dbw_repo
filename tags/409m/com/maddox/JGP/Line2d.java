// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Line2d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector2d, Point2d, JGPException, Point3d, 
//            NSolvef, NSolved, Point2f

public class Line2d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Line2d()
    {
        N = new Vector2d();
        tmpP = new Point2d();
        N.x = 1.0D;
        C = 0.0D;
    }

    public Line2d(com.maddox.JGP.Line2d line2d)
    {
        N = new Vector2d();
        tmpP = new Point2d();
        N.set(line2d.N);
        C = line2d.C;
    }

    public Line2d(com.maddox.JGP.Point2d point2d, com.maddox.JGP.Point2d point2d1)
    {
        N = new Vector2d();
        tmpP = new Point2d();
        set(point2d, point2d1);
    }

    public final void set(com.maddox.JGP.Line2d line2d)
    {
        N.set(line2d.N);
        C = line2d.C;
    }

    public final void set(com.maddox.JGP.Point2d point2d, com.maddox.JGP.Point2d point2d1)
    {
        N.x = point2d.y - point2d1.y;
        N.y = point2d1.x - point2d.x;
        N.normalize();
        C = -N.dot(point2d);
    }

    public final void set(com.maddox.JGP.Point2d point2d, com.maddox.JGP.Vector2d vector2d)
    {
        N.x = vector2d.x;
        N.y = vector2d.y;
        N.normalize();
        C = -N.dot(point2d);
    }

    public final double deviation(com.maddox.JGP.Point2d point2d)
    {
        return N.dot(point2d) + C;
    }

    public final double distance(com.maddox.JGP.Point2d point2d)
    {
        return java.lang.Math.abs(N.dot(point2d) + C);
    }

    public final void mirror(com.maddox.JGP.Point2d point2d, com.maddox.JGP.Point2d point2d1)
    {
        tmpP.scale(-2D * deviation(point2d), N);
        point2d1.add(point2d, tmpP);
    }

    public final void mirror(com.maddox.JGP.Point2d point2d)
    {
        tmpP.scale(-2D * deviation(point2d), N);
        point2d.add(tmpP);
    }

    public final void mirror(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        tmpP.set(point3d);
        tmpP.scale(-2D * deviation(tmpP), N);
        point3d1.x = point3d.x + tmpP.x;
        point3d1.y = point3d.y + tmpP.y;
        point3d1.z = point3d.z;
    }

    public final void mirror(com.maddox.JGP.Point3d point3d)
    {
        tmpP.set(point3d);
        tmpP.scale(-2D * deviation(tmpP), N);
        point3d.x += tmpP.x;
        point3d.y += tmpP.y;
    }

    public final void project(com.maddox.JGP.Point2d point2d, com.maddox.JGP.Point2d point2d1)
    {
        tmpP.scale(-deviation(point2d), N);
        point2d1.add(point2d, tmpP);
    }

    public final void project(com.maddox.JGP.Point2d point2d)
    {
        tmpP.scale(-deviation(point2d), N);
        point2d.add(tmpP);
    }

    public final double distance(com.maddox.JGP.Line2d line2d)
    {
        if(!N.equals(line2d.N))
            return 0.0D;
        else
            return java.lang.Math.abs(C - line2d.C);
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Line2d line2d)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            (float)N.x, (float)N.y, (float)(-C), (float)line2d.N.x, (float)line2d.N.y, (float)(-line2d.C)
        };
        return com.maddox.JGP.NSolvef.Solve2f(af);
    }

    public final com.maddox.JGP.Point2d crossPRE(com.maddox.JGP.Line2d line2d)
        throws com.maddox.JGP.JGPException
    {
        double ad[] = {
            N.x, N.y, -C, line2d.N.x, line2d.N.y, -line2d.C
        };
        return com.maddox.JGP.NSolved.Solve2d(ad);
    }

    public final void crossXc(double d, com.maddox.JGP.Point2d point2d)
    {
        point2d.x = d;
        double d1 = (-N.x * d - C) / N.y;
        if(java.lang.Math.abs(d1) < 9.9999996169031625E+035D)
        {
            point2d.y = d1;
            return;
        } else
        {
            point2d.y = 9.9999996169031625E+035D;
            return;
        }
    }

    public final void crossYc(double d, com.maddox.JGP.Point2d point2d)
    {
        point2d.y = d;
        double d1 = (-N.y * d - C) / N.x;
        if(java.lang.Math.abs(d1) < 9.9999996169031625E+035D)
        {
            point2d.x = d1;
            return;
        } else
        {
            point2d.x = 9.9999996169031625E+035D;
            return;
        }
    }

    public final void crossXsubYc(double d, com.maddox.JGP.Point2d point2d)
    {
        double d1 = (-N.y * d - C) / (N.x + N.y);
        if(java.lang.Math.abs(d1) < 9.9999996169031625E+035D)
        {
            point2d.x = d1;
            point2d.y = d1 + d;
            return;
        } else
        {
            point2d.x = point2d.y = 9.9999996169031625E+035D;
            return;
        }
    }

    public final void crossXaddYc(double d, com.maddox.JGP.Point2d point2d)
    {
        double d1 = (N.y * d + C) / (N.y - N.x);
        if(java.lang.Math.abs(d1) < 9.9999996169031625E+035D)
        {
            point2d.x = d1;
            point2d.y = d - d1;
            return;
        } else
        {
            point2d.x = point2d.y = 9.9999996169031625E+035D;
            return;
        }
    }

    public final boolean crossed(com.maddox.JGP.Line2d line2d)
    {
        return !N.equals(line2d.N);
    }

    public final boolean equals(com.maddox.JGP.Line2d line2d)
    {
        return N.equals(line2d.N) && C == line2d.C;
    }

    public final boolean parallel(com.maddox.JGP.Line2d line2d)
    {
        return N.equals(line2d.N);
    }

    public final double cos(com.maddox.JGP.Line2d line2d)
    {
        return N.dot(line2d.N);
    }

    public java.lang.String toString()
    {
        return "( " + N.x + "," + N.y + "; " + C + " )";
    }

    public com.maddox.JGP.Vector2d N;
    public double C;
    private com.maddox.JGP.Point2d tmpP;
}
