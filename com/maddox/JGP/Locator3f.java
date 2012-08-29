// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Locator3f.java

package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Point3f, Orient3f, Vector3f, Matrix3f, 
//            Tuple3f, Matrix4f

public class Locator3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Locator3f()
    {
        P = new Point3f();
        O = new Orient3f();
    }

    public void set(float f, float f1, float f2, float f3, float f4, float f5)
    {
        P.set(f, f1, f2);
        O.set(f3, f4, f5);
    }

    public void set(com.maddox.JGP.Locator3f locator3f)
    {
        P.set(locator3f.P);
        O.set(locator3f.O);
    }

    public void set(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Orient3f orient3f)
    {
        P.set(tuple3f);
        O.set(orient3f);
    }

    public void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        P.set(tuple3f);
    }

    public void set(com.maddox.JGP.Orient3f orient3f)
    {
        O.set(orient3f);
    }

    public void set(float af[])
    {
        P.set(af[0], af[1], af[2]);
        O.set(af[3], af[4], af[5]);
    }

    public float getYaw()
    {
        return O.getYaw();
    }

    public float getPitch()
    {
        return O.getPitch();
    }

    public float getRoll()
    {
        return O.getRoll();
    }

    public float getX()
    {
        return P.x;
    }

    public float getY()
    {
        return P.y;
    }

    public float getZ()
    {
        return P.z;
    }

    public void get(com.maddox.JGP.Tuple3f tuple3f)
    {
        tuple3f.set(P);
    }

    public void get(com.maddox.JGP.Orient3f orient3f)
    {
        orient3f.set(O);
    }

    public void get(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Orient3f orient3f)
    {
        tuple3f.set(P);
        orient3f.set(O);
    }

    public void get(float af[])
    {
        af[0] = P.x;
        af[1] = P.y;
        af[2] = P.z;
        af[3] = O.getYaw();
        af[4] = O.getPitch();
        af[5] = O.getRoll();
    }

    public void add(com.maddox.JGP.Locator3f locator3f, com.maddox.JGP.Locator3f locator3f1)
    {
        locator3f1.transform(locator3f.P, P);
        O.add(locator3f.O, locator3f1.O);
    }

    public void add(com.maddox.JGP.Locator3f locator3f)
    {
        add(this, locator3f);
    }

    public void sub(com.maddox.JGP.Locator3f locator3f, com.maddox.JGP.Locator3f locator3f1)
    {
        locator3f1.transformInv(locator3f.P, P);
        O.sub(locator3f.O, locator3f1.O);
    }

    public void sub(com.maddox.JGP.Locator3f locator3f)
    {
        sub(this, locator3f);
    }

    public void wrap()
    {
        O.wrap();
    }

    public void getMatrix(com.maddox.JGP.Matrix4f matrix4f)
    {
        O.getMatrix(M3);
        matrix4f.set(M3);
        matrix4f.m03 = P.x;
        matrix4f.m13 = P.y;
        matrix4f.m23 = P.z;
    }

    public void getOrientMatrix(com.maddox.JGP.Matrix3f matrix3f)
    {
        O.getMatrix(matrix3f);
    }

    public void getOrientMatrixInv(com.maddox.JGP.Matrix3f matrix3f)
    {
        O.getMatrixInv(matrix3f);
    }

    public void transform(com.maddox.JGP.Point3f point3f)
    {
        O.transform(point3f);
        point3f.add(P);
    }

    public void transform(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        O.transform(point3f, point3f1);
        point3f1.add(P);
    }

    public void transform(com.maddox.JGP.Vector3f vector3f)
    {
        O.transform(vector3f);
    }

    public void transform(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Vector3f vector3f1)
    {
        O.transform(vector3f, vector3f1);
    }

    public void transformInv(com.maddox.JGP.Point3f point3f)
    {
        point3f.sub(P);
        O.transformInv(point3f);
    }

    public void transformInv(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        tup.sub(point3f, P);
        O.transformInv(tup, point3f1);
    }

    public void transformInv(com.maddox.JGP.Vector3f vector3f)
    {
        O.transformInv(vector3f);
    }

    public void transformInv(com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Vector3f vector3f1)
    {
        O.transformInv(tup, vector3f1);
    }

    public final void interpolate(com.maddox.JGP.Locator3f locator3f, com.maddox.JGP.Locator3f locator3f1, float f)
    {
        P.interpolate(locator3f.P, locator3f1.P, f);
        O.interpolate(locator3f.O, locator3f1.O, f);
    }

    public final void interpolate(com.maddox.JGP.Locator3f locator3f, float f)
    {
        P.interpolate(locator3f.P, f);
        O.interpolate(locator3f.O, f);
    }

    public int hashCode()
    {
        return P.hashCode() ^ O.hashCode();
    }

    public boolean equals(com.maddox.JGP.Locator3f locator3f)
    {
        return P.equals(locator3f.P) && O.equals(locator3f.O);
    }

    public boolean epsilonEquals(com.maddox.JGP.Locator3f locator3f, float f)
    {
        return P.epsilonEquals(locator3f.P, f) && O.epsilonEquals(locator3f.O, f);
    }

    public java.lang.String toString()
    {
        return "( " + P + "," + O + " ) ";
    }

    public static void main(java.lang.String args[])
    {
        com.maddox.JGP.Locator3f locator3f = new Locator3f();
        com.maddox.JGP.Locator3f locator3f1 = new Locator3f();
        com.maddox.JGP.Locator3f locator3f2 = new Locator3f();
        com.maddox.JGP.Vector3f vector3f = new Vector3f();
        locator3f.set(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        locator3f1.set(1.0F, 0.0F, 0.0F, PI / 4F, 0.2F, 0.3F);
        java.lang.System.out.println("Slave:" + locator3f);
        java.lang.System.out.println("Master:" + locator3f1);
        vector3f.set(1.0F, 1.0F, 1.0F);
        java.lang.System.out.println("v0:" + vector3f);
        locator3f1.transform(vector3f);
        java.lang.System.out.println("v1:" + vector3f);
        locator3f1.transformInv(vector3f);
        java.lang.System.out.println("v2:" + vector3f);
        locator3f2.add(locator3f, locator3f1);
        java.lang.System.out.println("Master+Slave:" + locator3f2);
        locator3f2.sub(locator3f1);
        java.lang.System.out.println("Master+Slave-Slave:" + locator3f2);
    }

    private com.maddox.JGP.Point3f P;
    private com.maddox.JGP.Orient3f O;
    private static com.maddox.JGP.Matrix3f M3 = new Matrix3f();
    static float PI = 3.141593F;
    private static final com.maddox.JGP.Tuple3f tup = new Point3f();
    private static com.maddox.JGP.Tuple3f P1 = new Point3f();
    private static com.maddox.JGP.Orient3f O1 = new Orient3f();
    private static final com.maddox.JGP.Locator3f Tmp = new Locator3f();

}
