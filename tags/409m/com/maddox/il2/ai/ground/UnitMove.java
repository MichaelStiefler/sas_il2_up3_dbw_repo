// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitMove.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;

public class UnitMove
{

    UnitMove(float f, com.maddox.JGP.Vector3f vector3f)
    {
        pos = null;
        totalTime = f;
        normal = new Vector3f(vector3f);
        dontrun = false;
    }

    public UnitMove(float f, com.maddox.JGP.Point3d point3d, float f1, com.maddox.JGP.Vector3f vector3f, float f2)
    {
        pos = new Point3d(point3d);
        pos.z += f;
        totalTime = f1;
        normal = new Vector3f(vector3f);
        if(f2 > 0.0F)
        {
            dontrun = true;
            walkSpeed = f2;
        } else
        {
            dontrun = false;
        }
    }

    public boolean IsLandAligned()
    {
        return normal.z < 0.0F;
    }

    public com.maddox.JGP.Point3d pos;
    public float totalTime;
    public com.maddox.JGP.Vector3f normal;
    public boolean dontrun;
    public float walkSpeed;
}
