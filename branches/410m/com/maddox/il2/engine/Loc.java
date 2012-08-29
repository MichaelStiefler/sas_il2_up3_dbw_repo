// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Loc.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;

// Referenced classes of package com.maddox.il2.engine:
//            Orient

public class Loc
{

    public Loc()
    {
        P = new Point3d();
        O = new Orient();
    }

    public Loc(double d, double d1, double d2, float f, 
            float f1, float f2)
    {
        this();
        set(d, d1, d2, f, f1, f2);
    }

    public Loc(com.maddox.il2.engine.Loc loc)
    {
        this();
        set(loc);
    }

    public Loc(com.maddox.JGP.Tuple3d tuple3d, com.maddox.il2.engine.Orient orient1)
    {
        this();
        set(tuple3d, orient1);
    }

    public Loc(com.maddox.JGP.Tuple3d tuple3d)
    {
        this();
        set(tuple3d);
    }

    public Loc(com.maddox.il2.engine.Orient orient1)
    {
        this();
        set(orient1);
    }

    public Loc(double ad[])
    {
        this();
        set(ad);
    }

    public void set(double d, double d1, double d2, float f, 
            float f1, float f2)
    {
        P.set(d, d1, d2);
        O.set(f, f1, f2);
    }

    public void set(com.maddox.il2.engine.Loc loc)
    {
        P.set(loc.P);
        O.set(loc.O);
    }

    public void set(com.maddox.JGP.Tuple3d tuple3d, com.maddox.il2.engine.Orient orient1)
    {
        P.set(tuple3d);
        O.set(orient1);
    }

    public void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        P.set(tuple3d);
    }

    public void set(com.maddox.il2.engine.Orient orient1)
    {
        O.set(orient1);
    }

    public void set(double ad[])
    {
        P.set(ad[0], ad[1], ad[2]);
        O.set((float)ad[3], (float)ad[4], (float)ad[5]);
    }

    public float getAzimut()
    {
        return O.getAzimut();
    }

    public float getTangage()
    {
        return O.getTangage();
    }

    public float getKren()
    {
        return O.getKren();
    }

    public double getX()
    {
        return P.x;
    }

    public double getY()
    {
        return P.y;
    }

    public double getZ()
    {
        return P.z;
    }

    public com.maddox.JGP.Point3d getPoint()
    {
        return P;
    }

    public com.maddox.il2.engine.Orient getOrient()
    {
        return O;
    }

    public void get(com.maddox.JGP.Tuple3d tuple3d)
    {
        tuple3d.set(P);
    }

    public void get(com.maddox.il2.engine.Orient orient1)
    {
        orient1.set(O);
    }

    public void get(com.maddox.JGP.Tuple3d tuple3d, com.maddox.il2.engine.Orient orient1)
    {
        tuple3d.set(P);
        orient1.set(O);
    }

    public void get(double ad[])
    {
        ad[0] = P.x;
        ad[1] = P.y;
        ad[2] = P.z;
        ad[3] = O.getAzimut();
        ad[4] = O.getTangage();
        ad[5] = O.getKren();
    }

    public void add(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        loc1.transform(loc.P, P);
        O.add(loc.O, loc1.O);
    }

    public void add(com.maddox.il2.engine.Loc loc)
    {
        add(this, loc);
    }

    public void add(com.maddox.JGP.Tuple3d tuple3d)
    {
        P.add(tuple3d);
    }

    public void sub(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        loc1.transformInv(loc.P, P);
        O.sub(loc.O, loc1.O);
    }

    public void sub(com.maddox.il2.engine.Loc loc)
    {
        sub(this, loc);
    }

    public void wrap()
    {
        O.wrap();
    }

    public void getMatrix(com.maddox.JGP.Matrix4d matrix4d)
    {
        O.getMatrix(M3);
        matrix4d.set(M3);
        matrix4d.m03 = P.x;
        matrix4d.m13 = P.y;
        matrix4d.m23 = P.z;
    }

    public void getOrientMatrix(com.maddox.JGP.Matrix3d matrix3d)
    {
        O.getMatrix(matrix3d);
    }

    public void getOrientMatrixInv(com.maddox.JGP.Matrix3d matrix3d)
    {
        O.getMatrixInv(matrix3d);
    }

    public void transform(com.maddox.JGP.Point3d point3d)
    {
        O.transform(point3d);
        point3d.add(P);
    }

    public void transform(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        O.transform(point3d, point3d1);
        point3d1.add(P);
    }

    public void transform(com.maddox.JGP.Vector3d vector3d)
    {
        O.transform(vector3d);
    }

    public void transform(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1)
    {
        O.transform(vector3d, vector3d1);
    }

    public void transformInv(com.maddox.JGP.Point3d point3d)
    {
        point3d.sub(P);
        O.transformInv(point3d);
    }

    public void transformInv(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        tup.sub(point3d, P);
        O.transformInv(tup, point3d1);
    }

    public void transformInv(com.maddox.JGP.Vector3d vector3d)
    {
        O.transformInv(vector3d);
    }

    public void transformInv(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1)
    {
        O.transformInv(vector3d, vector3d1);
    }

    public final void interpolate(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1, float f)
    {
        P.interpolate(loc.P, loc1.P, f);
        O.interpolate(loc.O, loc1.O, f);
    }

    public final void interpolate(com.maddox.il2.engine.Loc loc, float f)
    {
        P.interpolate(loc.P, f);
        O.interpolate(loc.O, f);
    }

    public final void interpolate(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1, double d)
    {
        P.interpolate(loc.P, loc1.P, d);
        O.interpolate(loc.O, loc1.O, (float)d);
    }

    public final void interpolate(com.maddox.il2.engine.Loc loc, double d)
    {
        P.interpolate(loc.P, d);
        O.interpolate(loc.O, (float)d);
    }

    public int hashCode()
    {
        return P.hashCode() ^ O.hashCode();
    }

    public boolean equals(com.maddox.il2.engine.Loc loc)
    {
        return P.equals(loc.P) && O.equals(loc.O);
    }

    public boolean epsilonEquals(com.maddox.il2.engine.Loc loc, float f)
    {
        return P.epsilonEquals(loc.P, f) && O.epsilonEquals(loc.O, f);
    }

    public java.lang.String toString()
    {
        return "( " + P + "," + O + " ) ";
    }

    public void orient(com.maddox.JGP.Vector3d vector3d)
    {
        O.orient(vector3d);
    }

    protected com.maddox.JGP.Point3d P;
    protected com.maddox.il2.engine.Orient O;
    private static com.maddox.JGP.Matrix3d M3 = new Matrix3d();
    private static final com.maddox.JGP.Tuple3d tup = new Point3d();

}
