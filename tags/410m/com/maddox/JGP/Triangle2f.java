// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Triangle2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Point2f, Line2f, Triangle3f, Point3f

public class Triangle2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Triangle2f()
    {
        P = new com.maddox.JGP.Point2f[3];
    }

    public final void set(com.maddox.JGP.Triangle2f triangle2f)
    {
        try
        {
            P[0].set(triangle2f.P[0]);
            P[1].set(triangle2f.P[1]);
            P[2].set(triangle2f.P[2]);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point2f(triangle2f.P[0].x, triangle2f.P[0].y);
            P[1] = new Point2f(triangle2f.P[1].x, triangle2f.P[1].y);
            P[2] = new Point2f(triangle2f.P[2].x, triangle2f.P[2].y);
        }
    }

    public final void set(com.maddox.JGP.Triangle3f triangle3f)
    {
        try
        {
            P[0].set(triangle3f.P[0]);
            P[1].set(triangle3f.P[1]);
            P[2].set(triangle3f.P[2]);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point2f(triangle3f.P[0].x, triangle3f.P[0].y);
            P[1] = new Point2f(triangle3f.P[1].x, triangle3f.P[1].y);
            P[2] = new Point2f(triangle3f.P[2].x, triangle3f.P[2].y);
        }
    }

    public final void share(com.maddox.JGP.Triangle2f triangle2f)
    {
        P[0] = triangle2f.P[0];
        P[1] = triangle2f.P[1];
        P[2] = triangle2f.P[2];
    }

    public final void set(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1, com.maddox.JGP.Point2f point2f2)
    {
        try
        {
            P[0].set(point2f);
            P[1].set(point2f1);
            P[2].set(point2f2);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point2f(point2f);
            P[1] = new Point2f(point2f1);
            P[2] = new Point2f(point2f2);
        }
    }

    public final void share(com.maddox.JGP.Point2f point2f, com.maddox.JGP.Point2f point2f1, com.maddox.JGP.Point2f point2f2)
    {
        P[0] = point2f;
        P[1] = point2f1;
        P[2] = point2f2;
    }

    public final boolean normalesIN()
    {
        com.maddox.JGP.Point2f point2f = new Point2f((P[0].x + P[1].x + P[2].x) * 0.3333333F, (P[0].y + P[1].y + P[2].y) * 0.3333333F);
        if((new Line2f(P[0], P[1])).deviation(point2f) > 0.0F)
            return true;
        if((new Line2f(P[1], P[2])).deviation(point2f) > 0.0F)
            return true;
        if((new Line2f(P[2], P[0])).deviation(point2f) > 0.0F)
        {
            return true;
        } else
        {
            com.maddox.JGP.Point2f point2f1 = P[0];
            P[0] = P[1];
            P[1] = point2f1;
            return false;
        }
    }

    public java.lang.String toString()
    {
        return "( " + P[0] + "," + P[1] + "," + P[2] + " )";
    }

    public com.maddox.JGP.Point2f P[];
}
