// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Line3f.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector3f, Point3f

public class Line3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Line3f()
    {
        A = new Vector3f();
        P0 = new Point3f();
        A.x = A.y = A.z = 0.5773503F;
    }

    public Line3f(com.maddox.JGP.Line3f line3f)
    {
        A = new Vector3f();
        P0 = new Point3f();
        A.set(line3f.A);
        P0.set(line3f.P0);
    }

    public Line3f(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        A = new Vector3f();
        P0 = new Point3f();
        set(point3f, point3f1);
    }

    public final void set(com.maddox.JGP.Line3f line3f)
    {
        A.set(line3f.A);
        P0.set(line3f.P0);
    }

    public final void set(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        A.x = point3f1.x - point3f.x;
        A.y = point3f1.y - point3f.y;
        A.z = point3f1.z - point3f.z;
        A.normalize();
        P0 = point3f;
    }

    public final float distance(com.maddox.JGP.Point3f point3f)
    {
        com.maddox.JGP.Vector3f vector3f = new Vector3f(point3f.x - P0.x, point3f.y - P0.y, point3f.z - P0.z);
        float f = A.dot(vector3f);
        return (float)java.lang.Math.sqrt(vector3f.lengthSquared() - f * f);
    }

    public final float distance(com.maddox.JGP.Line3f line3f)
    {
        com.maddox.JGP.Vector3f vector3f = new Vector3f();
        vector3f.cross(A, line3f.A);
        try
        {
            vector3f.normalize();
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            return distance(line3f.P0);
        }
        return java.lang.Math.abs(vector3f.dot(P0) - vector3f.dot(line3f.P0));
    }

    public final float cos(com.maddox.JGP.Line3f line3f)
    {
        return A.dot(line3f.A);
    }

    public java.lang.String toString()
    {
        return "( " + A.x + "," + A.y + "," + A.z + "; " + P0.x + "," + P0.y + "," + P0.z + " )";
    }

    public static void main(java.lang.String args[])
    {
        com.maddox.JGP.Point3f point3f = new Point3f(0.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f1 = new Point3f(1.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f2 = new Point3f(0.0F, 1.0F, 0.0F);
        com.maddox.JGP.Point3f point3f3 = new Point3f(0.0F, 0.0F, 1.0F);
        com.maddox.JGP.Line3f line3f = new Line3f(point3f, point3f2);
        com.maddox.JGP.Line3f line3f1 = new Line3f(point3f1, point3f2);
        java.lang.System.out.println("Line: " + line3f1);
        java.lang.System.out.println("Point: " + point3f);
        java.lang.System.out.println("Distance: " + line3f1.distance(point3f) + "\n");
        java.lang.System.out.println("Line1: " + line3f);
        java.lang.System.out.println("Line2: " + line3f1);
        java.lang.System.out.println("Distance: " + line3f.distance(line3f1));
        java.lang.System.out.println("Cos: " + line3f.cos(line3f1));
    }

    public com.maddox.JGP.Vector3f A;
    public com.maddox.JGP.Point3f P0;
}
