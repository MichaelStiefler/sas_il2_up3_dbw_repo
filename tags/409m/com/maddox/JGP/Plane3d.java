// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Plane3d.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector3d, Line3d, JGPException, Point3d, 
//            NSolved

public class Plane3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Plane3d()
    {
        N = new Vector3d();
        N.x = N.y = N.z = 0.57735025882720947D;
        D = 0.0D;
    }

    public Plane3d(com.maddox.JGP.Plane3d plane3d)
    {
        N = new Vector3d();
        N.x = plane3d.N.x;
        N.y = plane3d.N.y;
        N.z = plane3d.N.z;
        D = plane3d.D;
    }

    public Plane3d(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2)
    {
        N = new Vector3d();
        set(point3d, point3d1, point3d2);
    }

    public Plane3d(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Point3d point3d)
    {
        N = new Vector3d();
        N.normalize(vector3d);
        D = -N.dot(point3d);
    }

    public final void set(com.maddox.JGP.Plane3d plane3d)
    {
        N.x = plane3d.N.x;
        N.y = plane3d.N.y;
        N.z = plane3d.N.z;
        D = plane3d.D;
    }

    public final void set(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2)
    {
        double d = point3d1.x - point3d.x;
        double d3 = point3d2.x - point3d.x;
        double d1 = point3d1.y - point3d.y;
        double d4 = point3d2.y - point3d.y;
        double d2 = point3d1.z - point3d.z;
        double d5 = point3d2.z - point3d.z;
        N.x = d1 * d5 - d4 * d2;
        N.y = d3 * d2 - d * d5;
        N.z = d * d4 - d3 * d2;
        N.normalize();
        D = -N.dot(point3d);
    }

    public final void set(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Point3d point3d)
    {
        N.normalize(vector3d);
        D = -N.dot(point3d);
    }

    public final double deviation(com.maddox.JGP.Point3d point3d)
    {
        return N.dot(point3d) + D;
    }

    public final double distance(com.maddox.JGP.Point3d point3d)
    {
        return java.lang.Math.abs(N.dot(point3d) + D);
    }

    public final com.maddox.JGP.Line3d cross(com.maddox.JGP.Plane3d plane3d)
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Line3d line3d = new Line3d();
        line3d.A.cross(N, plane3d.N);
        try
        {
            line3d.A.normalize();
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            throw new JGPException("Can't make a line from parallel planes");
        }
        double ad[] = {
            N.x, N.y, N.z, -D, plane3d.N.x, plane3d.N.y, plane3d.N.z, -plane3d.D, line3d.A.x, line3d.A.y, 
            line3d.A.z, 0.0D
        };
        try
        {
            line3d.P0 = com.maddox.JGP.NSolved.Solve3d(ad);
        }
        catch(java.lang.RuntimeException runtimeexception1)
        {
            throw new JGPException("Making a line from planes: some error");
        }
        return line3d;
    }

    public final com.maddox.JGP.Point3d cross(com.maddox.JGP.Line3d line3d)
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3d point3d = new Point3d(line3d.A);
        point3d.scale((N.dot(line3d.P0) + D) / N.dot(line3d.A));
        point3d.sub(line3d.P0, point3d);
        return point3d;
    }

    public final double cos(com.maddox.JGP.Plane3d plane3d)
    {
        return N.dot(plane3d.N);
    }

    public final double cos(com.maddox.JGP.Line3d line3d)
    {
        return N.dot(line3d.A);
    }

    public java.lang.String toString()
    {
        return "( " + N.x + "," + N.y + "," + N.z + ", " + D + " )";
    }

    public static void main(java.lang.String args[])
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3d point3d = new Point3d(0.0D, 0.0D, 0.0D);
        com.maddox.JGP.Point3d point3d1 = new Point3d(1.0D, 0.0D, 0.0D);
        com.maddox.JGP.Point3d point3d2 = new Point3d(0.0D, 1.0D, 0.0D);
        com.maddox.JGP.Point3d point3d3 = new Point3d(0.0D, 0.0D, 1.0D);
        com.maddox.JGP.Plane3d plane3d = new Plane3d(point3d, point3d1, point3d2);
        com.maddox.JGP.Plane3d plane3d1 = new Plane3d(point3d1, point3d3, point3d2);
        java.lang.System.out.println("Plane1: " + plane3d);
        java.lang.System.out.println("Plane2: " + plane3d1);
        java.lang.System.out.println("Point: " + point3d + "\n");
        java.lang.System.out.println("Distance: " + plane3d1.distance(point3d) + "\n");
        java.lang.System.out.println("PL1.cross(PL1):");
        try
        {
            plane3d.cross(plane3d);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            java.lang.System.out.println(runtimeexception.getMessage());
        }
        java.lang.System.out.println("PL1.cross(PL2):");
        com.maddox.JGP.Line3d line3d = plane3d.cross(plane3d1);
        java.lang.System.out.println("Line: " + line3d);
    }

    public com.maddox.JGP.Vector3d N;
    public double D;
}
