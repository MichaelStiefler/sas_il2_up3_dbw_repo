// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Ray3f.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Line3f, Point3f, Plane3f, JGPException, 
//            Vector3f

public class Ray3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Ray3f()
    {
        L0 = new Line3f();
        P0 = new Point3f();
        PL = new Plane3f();
    }

    public Ray3f(com.maddox.JGP.Ray3f ray3f)
    {
        L0 = new Line3f();
        P0 = new Point3f();
        PL = new Plane3f();
        L0.set(ray3f.L0);
        P0.set(ray3f.P0);
        PL.set(ray3f.PL);
    }

    public Ray3f(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        L0 = new Line3f();
        P0 = new Point3f();
        PL = new Plane3f();
        set(point3f, point3f1);
    }

    public Ray3f(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Point3f point3f)
    {
        L0 = new Line3f();
        P0 = new Point3f();
        PL = new Plane3f();
        set(vector3f, point3f);
    }

    public final void set(com.maddox.JGP.Ray3f ray3f)
    {
        L0.set(ray3f.L0);
        P0.set(ray3f.P0);
        PL.set(ray3f.PL);
    }

    public final void set(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        P0.set(point3f);
        L0.set(point3f, point3f1);
        PL.N = L0.A;
        PL.D = -PL.N.dot(point3f);
    }

    public final void set(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Point3f point3f)
    {
        P0.set(point3f);
        L0.A.set(vector3f);
        L0.A.normalize();
        L0.P0.set(point3f);
        PL.N = L0.A;
        PL.D = -PL.N.dot(point3f);
    }

    public final float distance(com.maddox.JGP.Point3f point3f)
    {
        return L0.distance(point3f);
    }

    public final float deviationFB(com.maddox.JGP.Point3f point3f)
    {
        return PL.N.dot(point3f) + PL.D;
    }

    public final float cos(com.maddox.JGP.Ray3f ray3f)
    {
        return PL.N.dot(ray3f.PL.N);
    }

    public final float cos(com.maddox.JGP.Plane3f plane3f)
    {
        return PL.N.dot(plane3f.N);
    }

    public final com.maddox.JGP.Point3f cross(com.maddox.JGP.Plane3f plane3f)
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3f point3f = plane3f.cross(L0);
        if(deviationFB(point3f) >= 0.0F)
            return point3f;
        else
            throw new JGPException("Ray and Line are'nt crossed");
    }

    public java.lang.String toString()
    {
        return "( " + PL.N + "," + P0 + " )";
    }

    public static void main(java.lang.String args[])
        throws com.maddox.JGP.JGPException
    {
        com.maddox.JGP.Point3f point3f = new Point3f(0.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f1 = new Point3f(1.0F, 0.0F, 0.0F);
        com.maddox.JGP.Point3f point3f2 = new Point3f(0.0F, 1.0F, 0.0F);
        com.maddox.JGP.Point3f point3f3 = new Point3f(0.0F, 0.0F, 1.0F);
        com.maddox.JGP.Ray3f ray3f = new Ray3f(point3f, point3f1);
        com.maddox.JGP.Ray3f ray3f1 = new Ray3f(point3f1, point3f3);
        com.maddox.JGP.Plane3f plane3f = new Plane3f(point3f1, point3f2, point3f3);
        java.lang.System.out.println("Ray1: " + ray3f);
        java.lang.System.out.println("Ray2: " + ray3f1);
        java.lang.System.out.println("Point: " + point3f + "\n");
        java.lang.System.out.println("Ray2/Point distance: " + ray3f1.distance(point3f) + "\n");
        java.lang.System.out.println("\nPlane: " + plane3f);
        java.lang.System.out.println("Ray2/Plane crosspoint: " + ray3f1.cross(plane3f) + "\n");
    }

    public com.maddox.JGP.Line3f L0;
    public com.maddox.JGP.Point3f P0;
    public com.maddox.JGP.Plane3f PL;
}
