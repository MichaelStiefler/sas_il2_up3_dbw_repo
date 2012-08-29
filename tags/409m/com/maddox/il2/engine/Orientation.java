// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orientation.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Quat4f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;

// Referenced classes of package com.maddox.il2.engine:
//            Orient

public class Orientation extends com.maddox.il2.engine.Orient
{

    public Orientation()
    {
        Q = new Quat4f();
        M = new Matrix3d();
        Mi = new Matrix3d();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public Orientation(float f, float f1, float f2)
    {
        Q = new Quat4f();
        M = new Matrix3d();
        Mi = new Matrix3d();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
        set(f, f1, f2);
    }

    public void set(float f, float f1, float f2)
    {
        super.set(f, f1, f2);
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public void setYPR(float f, float f1, float f2)
    {
        super.setYPR(f, f1, f2);
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public void setYaw(float f)
    {
        super.setYaw(f);
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public void set(com.maddox.il2.engine.Orient orient)
    {
        super.set(orient);
        if(orient instanceof com.maddox.il2.engine.Orientation)
        {
            com.maddox.il2.engine.Orientation orientation = (com.maddox.il2.engine.Orientation)orient;
            QuatOK = orientation.QuatOK;
            MatrixOK = orientation.MatrixOK;
            MatrInvOK = orientation.MatrInvOK;
            if(QuatOK)
                Q.set(orientation.Q);
            if(MatrixOK)
                M.set(orientation.M);
            if(MatrInvOK)
                Mi.set(orientation.Mi);
        } else
        {
            QuatOK = false;
            MatrixOK = false;
            MatrInvOK = false;
        }
    }

    private void makeMatrix()
    {
        if(MatrixOK)
        {
            return;
        } else
        {
            makeMatrix(M);
            MatrixOK = true;
            return;
        }
    }

    private void makeMatrixInv()
    {
        if(MatrInvOK)
        {
            return;
        } else
        {
            makeMatrixInv(Mi);
            MatrInvOK = true;
            return;
        }
    }

    private void makeQuat()
    {
        if(QuatOK)
        {
            return;
        } else
        {
            makeQuat(Q);
            QuatOK = true;
            return;
        }
    }

    public void add(com.maddox.il2.engine.Orient orient, com.maddox.il2.engine.Orient orient1)
    {
        super.add(orient, orient1);
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void sub(com.maddox.il2.engine.Orient orient, com.maddox.il2.engine.Orient orient1)
    {
        super.sub(orient, orient1);
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void getMatrix(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrix();
        matrix3d.set(M);
    }

    public void getMatrixInv(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrixInv();
        matrix3d.set(M);
    }

    public void getQuat(com.maddox.JGP.Quat4f quat4f)
    {
        makeQuat();
        quat4f.set(Q);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d)
    {
        makeMatrix();
        M.transform(tuple3d);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        makeMatrix();
        M.transform(tuple3f);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        makeMatrix();
        M.transform(tuple3d, tuple3d1);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        makeMatrix();
        M.transform(tuple3f, tuple3f1);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d)
    {
        makeMatrixInv();
        Mi.transform(tuple3d);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f)
    {
        makeMatrixInv();
        Mi.transform(tuple3f);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        makeMatrixInv();
        Mi.transform(tuple3d, tuple3d1);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        makeMatrixInv();
        Mi.transform(tuple3f, tuple3f1);
    }

    public void interpolate(com.maddox.il2.engine.Orient orient, com.maddox.il2.engine.Orient orient1, float f)
    {
        super.interpolate(orient, orient1, f);
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void interpolate(com.maddox.il2.engine.Orient orient, float f)
    {
        super.interpolate(orient, f);
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    private com.maddox.JGP.Quat4f Q;
    private com.maddox.JGP.Matrix3d M;
    private com.maddox.JGP.Matrix3d Mi;
    private boolean QuatOK;
    private boolean MatrixOK;
    private boolean MatrInvOK;
}
