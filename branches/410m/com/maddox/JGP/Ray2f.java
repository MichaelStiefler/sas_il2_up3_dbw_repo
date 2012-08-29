// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Ray2f.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Line2f, JGPException, Point2f, Vector2f, 
//            NSolvef

public class Ray2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Ray2f()
    {
        LA = new Line2f();
        LN = new Line2f();
    }

    public Ray2f(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        LA = new Line2f();
        LN = new Line2f();
        set(point2f, point2f1);
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1)
    {
        LN.N.x = point2f1.x - point2f.x;
        LN.N.y = point2f1.y - point2f.y;
        LN.N.normalize();
        LN.C = -LN.N.dot(point2f);
        LA.set(point2f, point2f1);
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Vector2f vector2f)
    {
        LN.N.set(vector2f);
        LN.N.normalize();
        LN.C = -LN.N.dot(point2f);
        LA.set(point2f, vector2f);
    }

    public final float deviationLR(com.maddox.JGP.Point2f point2f)
    {
        return LA.deviation(point2f);
    }

    public final float deviationFB(com.maddox.JGP.Point2f point2f)
    {
        return LN.deviation(point2f);
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Ray2f ray2f)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            LA.N.x, LA.N.y, -LA.C, ray2f.LA.N.x, ray2f.LA.N.y, -ray2f.LA.C
        };
        com.maddox.JGP.Point2f point2f = com.maddox.JGP.NSolvef.Solve2f(af);
        if(deviationFB(point2f) >= 0.0F && ray2f.deviationFB(point2f) >= 0.0F)
            return point2f;
        else
            throw new JGPException("Rays are not crossed");
    }

    public final com.maddox.JGP.Point2f cross(com.maddox.JGP.Line2f line2f)
        throws com.maddox.JGP.JGPException
    {
        float af[] = {
            LA.N.x, LA.N.y, -LA.C, line2f.N.x, line2f.N.y, -line2f.C
        };
        com.maddox.JGP.Point2f point2f = com.maddox.JGP.NSolvef.Solve2f(af);
        if(deviationFB(point2f) >= 0.0F)
            return point2f;
        else
            throw new JGPException("Ray and Line are'nt crossed");
    }

    public final boolean crossed(com.maddox.JGP.Ray2f ray2f)
    {
        if(LA.N.equals(ray2f.LA.N))
            return false;
        try
        {
            cross(ray2f);
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        return true;
    }

    public final boolean equals(com.maddox.JGP.Ray2f ray2f)
    {
        return LA.equals(ray2f.LA) && LN.C == ray2f.LN.C;
    }

    public final boolean parallel(com.maddox.JGP.Ray2f ray2f)
    {
        return LA.N.equals(ray2f.LA.N);
    }

    public final float cos(com.maddox.JGP.Ray2f ray2f)
    {
        return LA.N.dot(ray2f.LA.N);
    }

    public java.lang.String toString()
    {
        return "( " + LN.N.x + "," + LN.N.y + "," + LA.cross(LN) + " )";
        java.lang.Exception exception;
        exception;
        return exception.toString();
    }

    public static void main(java.lang.String args[])
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point2f point2f = new Point2f(0.0F, 0.0F);
        com.maddox.JGP.Point2f point2f1 = new Point2f(0.0F, 1.0F);
        com.maddox.JGP.Point2f point2f2 = new Point2f(1.0F, 1.0F);
        com.maddox.JGP.Point2f point2f3 = new Point2f(1.0F, 0.0F);
        com.maddox.JGP.Ray2f ray2f = new Ray2f(point2f, point2f2);
        com.maddox.JGP.Ray2f ray2f1 = new Ray2f(point2f1, point2f3);
        java.lang.System.out.println("Ray1: " + ray2f);
        java.lang.System.out.println("Ray2: " + ray2f1);
        com.maddox.JGP.Point2f point2f4 = ray2f.cross(ray2f1);
        java.lang.System.out.println("CrossPoint: " + point2f4 + "\n");
        ray2f1 = new Ray2f(point2f, point2f3);
        java.lang.System.out.println("Ray1: " + ray2f);
        java.lang.System.out.println("Horiz: " + ray2f1);
        point2f4 = ray2f.cross(ray2f1);
        java.lang.System.out.println("CrossPoint: " + point2f4);
        java.lang.System.out.println("Cos: " + ray2f.cos(ray2f1));
    }

    public com.maddox.JGP.Line2f LA;
    public com.maddox.JGP.Line2f LN;
}
