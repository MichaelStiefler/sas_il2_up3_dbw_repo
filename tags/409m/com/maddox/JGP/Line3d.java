// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Line3d.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector3d, Point3d

public class Line3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Line3d()
    {
        A = new Vector3d();
        P0 = new Point3d();
        A.x = A.y = A.z = 0.57735025882720947D;
    }

    public Line3d(com.maddox.JGP.Line3d line3d)
    {
        A = new Vector3d();
        P0 = new Point3d();
        A.set(line3d.A);
        P0.set(line3d.P0);
    }

    public Line3d(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        A = new Vector3d();
        P0 = new Point3d();
        set(point3d, point3d1);
    }

    public final void set(com.maddox.JGP.Line3d line3d)
    {
        A.set(line3d.A);
        P0.set(line3d.P0);
    }

    public final void set(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        A.x = point3d1.x - point3d.x;
        A.y = point3d1.y - point3d.y;
        A.z = point3d1.z - point3d.z;
        A.normalize();
        P0 = point3d;
    }

    public final double distance(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.JGP.Vector3d vector3d = new Vector3d(point3d.x - P0.x, point3d.y - P0.y, point3d.z - P0.z);
        double d = A.dot(vector3d);
        return java.lang.Math.sqrt(vector3d.lengthSquared() - d * d);
    }

    public final double distance(com.maddox.JGP.Line3d line3d)
    {
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.cross(A, line3d.A);
        try
        {
            vector3d.normalize();
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            return distance(line3d.P0);
        }
        return java.lang.Math.abs(vector3d.dot(P0) - vector3d.dot(line3d.P0));
    }

    public final double cos(com.maddox.JGP.Line3d line3d)
    {
        return A.dot(line3d.A);
    }

    public java.lang.String toString()
    {
        return "( " + A.x + "," + A.y + "," + A.z + "; " + P0.x + "," + P0.y + "," + P0.z + " )";
    }

    public static void main(java.lang.String args[])
    {
        com.maddox.JGP.Point3d point3d = new Point3d(0.0D, 0.0D, 0.0D);
        com.maddox.JGP.Point3d point3d1 = new Point3d(1.0D, 0.0D, 0.0D);
        com.maddox.JGP.Point3d point3d2 = new Point3d(0.0D, 1.0D, 0.0D);
        com.maddox.JGP.Point3d point3d3 = new Point3d(0.0D, 0.0D, 1.0D);
        com.maddox.JGP.Line3d line3d = new Line3d(point3d, point3d2);
        com.maddox.JGP.Line3d line3d1 = new Line3d(point3d1, point3d2);
        java.lang.System.out.println("Line: " + line3d1);
        java.lang.System.out.println("Point: " + point3d);
        java.lang.System.out.println("Distance: " + line3d1.distance(point3d) + "\n");
        java.lang.System.out.println("Line1: " + line3d);
        java.lang.System.out.println("Line2: " + line3d1);
        java.lang.System.out.println("Distance: " + line3d.distance(line3d1));
        java.lang.System.out.println("Cos: " + line3d.cos(line3d1));
    }

    public com.maddox.JGP.Vector3d A;
    public com.maddox.JGP.Point3d P0;
}
