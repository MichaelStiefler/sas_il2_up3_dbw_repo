// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CameraOrtho2D.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Camera, ActorPos, Orient

public class CameraOrtho2D extends com.maddox.il2.engine.Camera
{

    public void set(float f, float f1, float f2, float f3)
    {
        left = f;
        right = f1;
        bottom = f2;
        top = f3;
    }

    public boolean isSphereVisible(float f, float f1, float f2, float f3)
    {
        return isSphereVisible(f, f1, f2, f3);
    }

    public boolean isSphereVisible(double d, double d1, double d2, float f)
    {
        double d3 = f;
        double d4 = worldXOffset + (double)(right - left) / worldScale;
        double d5 = worldYOffset + (double)(top - bottom) / worldScale;
        return d + d3 >= worldXOffset && d - d3 <= d4 && d1 + d3 >= worldYOffset && d1 - d3 <= d5;
    }

    public boolean activate(float f, int i, int j, int k, int l, int i1, int j1, 
            int k1, int l1, int i2, int j2)
    {
        com.maddox.il2.engine.Camera.SetViewportCrop(f, i, j, k, l, i1, j1, k1, l1, i2, j2);
        com.maddox.il2.engine.Camera.SetOrtho2D(left, right, bottom, top, ZNear, ZFar);
        pos.getRender(com.maddox.il2.engine.Camera.tmpP, com.maddox.il2.engine.Camera.tmpO);
        com.maddox.il2.engine.Camera.tmpd[0] = com.maddox.il2.engine.Camera.tmpP.x;
        com.maddox.il2.engine.Camera.tmpd[1] = com.maddox.il2.engine.Camera.tmpP.y;
        com.maddox.il2.engine.Camera.tmpd[2] = com.maddox.il2.engine.Camera.tmpP.z;
        com.maddox.il2.engine.Camera.tmpd[3] = 90F - com.maddox.il2.engine.Camera.tmpO.azimut();
        com.maddox.il2.engine.Camera.tmpd[4] = com.maddox.il2.engine.Camera.tmpO.tangage();
        com.maddox.il2.engine.Camera.tmpd[5] = -com.maddox.il2.engine.Camera.tmpO.kren();
        com.maddox.il2.engine.Camera.SetCameraPos(com.maddox.il2.engine.Camera.tmpd);
        com.maddox.il2.engine.Camera.GetVirtOrigin(com.maddox.il2.engine.Camera.tmpOr);
        XOffset = com.maddox.il2.engine.Camera.tmpOr[0];
        YOffset = com.maddox.il2.engine.Camera.tmpOr[1];
        return true;
    }

    public CameraOrtho2D()
    {
        left = 0.0F;
        right = 1.0F;
        bottom = 0.0F;
        top = 1.0F;
        worldXOffset = worldYOffset = 0.0D;
        worldScale = 1.0D;
        ZNear = -1F;
        ZFar = 1.0F;
    }

    public float left;
    public float right;
    public float bottom;
    public float top;
    public double worldXOffset;
    public double worldYOffset;
    public double worldScale;
}
