// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Plane3f.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Vector3f, Line3f, JGPException, Point3f, 
//            NSolvef

public class Plane3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Plane3f()
    {
        N = new Vector3f();
        N.x = N.y = N.z = 0.5773503F;
        D = 0.0F;
    }

    public Plane3f(com.maddox.JGP.Plane3f plane3f)
    {
        N = new Vector3f();
        N.x = plane3f.N.x;
        N.y = plane3f.N.y;
        N.z = plane3f.N.z;
        D = plane3f.D;
    }

    public Plane3f(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1, com.maddox.JGP.Point3f point3f2)
    {
        N = new Vector3f();
        set(point3f, point3f1, point3f2);
    }

    public Plane3f(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Point3f point3f)
    {
        N = new Vector3f();
        N.normalize(vector3f);
        D = -N.dot(point3f);
    }

    public final void set(com.maddox.JGP.Plane3f plane3f)
    {
        N.x = plane3f.N.x;
        N.y = plane3f.N.y;
        N.z = plane3f.N.z;
        D = plane3f.D;
    }

    public final void set(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1, com.maddox.JGP.Point3f point3f2)
    {
        float f = point3f1.x - point3f.x;
        float f3 = point3f2.x - point3f.x;
        float f1 = point3f1.y - point3f.y;
        float f4 = point3f2.y - point3f.y;
        float f2 = point3f1.z - point3f.z;
        float f5 = point3f2.z - point3f.z;
        N.x = f1 * f5 - f4 * f2;
        N.y = f3 * f2 - f * f5;
        N.z = f * f4 - f3 * f2;
        N.normalize();
        D = -N.dot(point3f);
    }

    public final void set(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Point3f point3f)
    {
        N.normalize(vector3f);
        D = -N.dot(point3f);
    }

    public final float deviation(com.maddox.JGP.Point3f point3f)
    {
        return N.dot(point3f) + D;
    }

    public final float distance(com.maddox.JGP.Point3f point3f)
    {
        return java.lang.Math.abs(N.dot(point3f) + D);
    }

    public final com.maddox.JGP.Line3f cross(com.maddox.JGP.Plane3f plane3f)
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Line3f line3f = new Line3f();
        line3f.A.cross(N, plane3f.N);
        try
        {
            line3f.A.normalize();
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            throw new JGPException("Can't make a line from parallel planes");
        }
        float af[] = {
            N.x, N.y, N.z, -D, plane3f.N.x, plane3f.N.y, plane3f.N.z, -plane3f.D, line3f.A.x, line3f.A.y, 
            line3f.A.z, 0.0F
        };
        try
        {
            line3f.P0 = com.maddox.JGP.NSolvef.Solve3f(af);
        }
        catch(java.lang.RuntimeException runtimeexception1)
        {
            throw new JGPException("Making a line from planes: some error");
        }
        return line3f;
    }

    public final com.maddox.JGP.Point3f cross(com.maddox.JGP.Line3f line3f)
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3f point3f = new Point3f(line3f.A);
        point3f.scale((N.dot(line3f.P0) + D) / N.dot(line3f.A));
        point3f.sub(line3f.P0, point3f);
        return point3f;
    }

    public final float cos(com.maddox.JGP.Plane3f plane3f)
    {
        return N.dot(plane3f.N);
    }

    public final float cos(com.maddox.JGP.Line3f line3f)
    {
        return N.dot(line3f.A);
    }

    public java.lang.String toString()
    {
        return "( " + N.x + "," + N.y + "," + N.z + ", " + D + " )";
    }

    public static void main(java.lang.String args[])
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3f point3f = new Point3f(0.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f1 = new Point3f(1.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f2 = new Point3f(0.0F, 1.0F, 0.0F);
        com.maddox.JGP.Point3f point3f3 = new Point3f(0.0F, 0.0F, 1.0F);
        com.maddox.JGP.Plane3f plane3f = new Plane3f(point3f, point3f1, point3f2);
        com.maddox.JGP.Plane3f plane3f1 = new Plane3f(point3f1, point3f3, point3f2);
        java.lang.System.out.println("Plane1: " + plane3f);
        java.lang.System.out.println("Plane2: " + plane3f1);
        java.lang.System.out.println("Point: " + point3f + "\n");
        java.lang.System.out.println("Distance: " + plane3f1.distance(point3f) + "\n");
        java.lang.System.out.println("PL1.cross(PL1):");
        try
        {
            plane3f.cross(plane3f);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            java.lang.System.out.println(runtimeexception.getMessage());
        }
        java.lang.System.out.println("PL1.cross(PL2):");
        com.maddox.JGP.Line3f line3f = plane3f.cross(plane3f1);
        java.lang.System.out.println("Line: " + line3f);
    }

    public com.maddox.JGP.Vector3f N;
    public float D;
}
