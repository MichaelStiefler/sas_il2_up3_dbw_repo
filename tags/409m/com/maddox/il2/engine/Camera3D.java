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
            com.maddox.il2.engine.Camera.SetZOrder(com.maddox.il2.engine.Render.current().getZOrder());
            com.maddox.il2.engine.Camera.SetViewportCrop(f, i, j, k, l, i1, j1, k1, l1, i2, j2);
            com.maddox.il2.engine.Camera.SetFOV(FOV, ZNear, ZFar);
            pos.getRender(com.maddox.il2.engine.Camera.tmpP, com.maddox.il2.engine.Camera.tmpO);
            com.maddox.il2.engine.Camera.tmpd[0] = com.maddox.il2.engine.Camera.tmpP.x;
            com.maddox.il2.engine.Camera.tmpd[1] = com.maddox.il2.engine.Camera.tmpP.y;
            com.maddox.il2.engine.Camera.tmpd[2] = com.maddox.il2.engine.Camera.tmpP.z;
            com.maddox.il2.engine.Camera.tmpd[3] = -com.maddox.il2.engine.Camera.tmpO.azimut();
            com.maddox.il2.engine.Camera.tmpd[4] = com.maddox.il2.engine.Camera.tmpO.tangage();
            com.maddox.il2.engine.Camera.tmpd[5] = -com.maddox.il2.engine.Camera.tmpO.kren();
            com.maddox.il2.engine.Camera.SetCameraPos(com.maddox.il2.engine.Camera.tmpd);
            com.maddox.il2.engine.Camera.GetVirtOrigin(com.maddox.il2.engine.Camera.tmpOr);
            XOffset = com.maddox.il2.engine.Camera.tmpOr[0];
            YOffset = com.maddox.il2.engine.Camera.tmpOr[1];
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