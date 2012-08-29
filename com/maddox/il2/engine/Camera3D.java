// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Camera3D.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Camera, Render, ActorPos, Orient

public class Camera3D extends com.maddox.il2.engine.Camera
{

    public float FOV()
    {
        return FOV;
    }

    public float aspect()
    {
        return aspect;
    }

    protected void setViewPortWidth(int i)
    {
        viewPortWidth = i;
    }

    public void set(float f, float f1, float f2)
    {
        FOV = f;
        ZNear = f1;
        ZFar = f2;
    }

    public void set(float f, float f1, float f2, float f3)
    {
        FOV = f;
        ZNear = f1;
        ZFar = f2;
        aspect = f3;
    }

    public void set(float f)
    {
        FOV = f;
    }

    public void set(float f, float f1)
    {
        FOV = f;
        aspect = f1;
    }

    public boolean activate(float f, int i, int j, int k, int l, int i1, int j1, 
            int k1, int l1, int i2, int j2)
    {
        if(FOV <= 0.0F)
        {
            return false;
        } else
        {
            com.maddox.il2.engine.Camera3D.SetZOrder(com.maddox.il2.engine.Render.current().getZOrder());
            com.maddox.il2.engine.Camera3D.SetViewportCrop(f, i, j, k, l, i1, j1, k1, l1, i2, j2);
            com.maddox.il2.engine.Camera3D.SetFOV(FOV, ZNear, ZFar);
            pos.getRender(tmpP, tmpO);
            tmpd[0] = tmpP.x;
            tmpd[1] = tmpP.y;
            tmpd[2] = tmpP.z;
            tmpd[3] = -tmpO.azimut();
            tmpd[4] = tmpO.tangage();
            tmpd[5] = -tmpO.kren();
            com.maddox.il2.engine.Camera3D.SetCameraPos(tmpd);
            com.maddox.il2.engine.Camera3D.GetVirtOrigin(tmpOr);
            XOffset = tmpOr[0];
            YOffset = tmpOr[1];
            return true;
        }
    }

    public Camera3D()
    {
        FOV = 90F;
        aspect = 1.333333F;
        viewPortWidth = 640;
    }

    private float FOV;
    private float aspect;
    private int viewPortWidth;
}
