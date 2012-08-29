// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Triangle3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Point3f, Triangle2f, Point2f

public class Triangle3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Triangle3f()
    {
        P = new com.maddox.JGP.Point3f[3];
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
            P[0] = new Point3f(triangle3f.P[0]);
            P[1] = new Point3f(triangle3f.P[1]);
            P[2] = new Point3f(triangle3f.P[2]);
        }
    }

    public final void set(com.maddox.JGP.Triangle2f triangle2f)
    {
        try
        {
            P[0].set(triangle2f.P[0].x, triangle2f.P[0].y, 0.0F);
            P[1].set(triangle2f.P[1].x, triangle2f.P[1].y, 0.0F);
            P[2].set(triangle2f.P[2].x, triangle2f.P[2].y, 0.0F);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point3f(triangle2f.P[0].x, triangle2f.P[0].y, 0.0F);
            P[1] = new Point3f(triangle2f.P[1].x, triangle2f.P[1].y, 0.0F);
            P[2] = new Point3f(triangle2f.P[2].x, triangle2f.P[2].y, 0.0F);
        }
    }

    public final void set2(com.maddox.JGP.Triangle2f triangle2f)
    {
        try
        {
            P[0].set(triangle2f.P[0].x, triangle2f.P[0].y, P[0].z);
            P[1].set(triangle2f.P[1].x, triangle2f.P[1].y, P[1].z);
            P[2].set(triangle2f.P[2].x, triangle2f.P[2].y, P[2].z);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point3f(triangle2f.P[0].x, triangle2f.P[0].y, 0.0F);
            P[1] = new Point3f(triangle2f.P[1].x, triangle2f.P[1].y, 0.0F);
            P[2] = new Point3f(triangle2f.P[2].x, triangle2f.P[2].y, 0.0F);
        }
    }

    public final void share(com.maddox.JGP.Triangle3f triangle3f)
    {
        P[0] = triangle3f.P[0];
        P[1] = triangle3f.P[1];
        P[2] = triangle3f.P[2];
    }

    public final void set(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1, com.maddox.JGP.Point3f point3f2)
    {
        try
        {
            P[0].set(point3f);
            P[1].set(point3f1);
            P[2].set(point3f2);
        }
        catch(java.lang.Exception exception)
        {
            P[0] = new Point3f(point3f);
            P[1] = new Point3f(point3f1);
            P[2] = new Point3f(point3f2);
        }
    }

    public final void share(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1, com.maddox.JGP.Point3f point3f2)
    {
        P[0] = point3f;
        P[1] = point3f1;
        P[2] = point3f2;
    }

    public java.lang.String toString()
    {
        return "( " + P[0] + "," + P[1] + "," + P[2] + " )";
    }

    public com.maddox.JGP.Point3f P[];
}
