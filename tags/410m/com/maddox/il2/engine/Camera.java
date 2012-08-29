// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Camera.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, ActorPosMove, Orient, GObj

public abstract class Camera extends com.maddox.il2.engine.Actor
{

    public void set(float f, float f1)
    {
        ZNear = f;
        ZFar = f1;
    }

    public abstract boolean activate(float f, int i, int j, int k, int l, int i1, int j1, 
            int k1, int l1, int i2, int j2);

    protected final boolean activate(float f, int i, int j, int k, int l, int i1, int j1)
    {
        return activate(f, i, j, k, l, i1, j1, k, l, i1, j1);
    }

    public boolean isSphereVisible(float f, float f1, float f2, float f3)
    {
        return com.maddox.il2.engine.Camera.isSphereVisibleF(f, f1, f2, f3);
    }

    public boolean isSphereVisible(double d, double d1, double d2, float f)
    {
        return com.maddox.il2.engine.Camera.isSphereVisibleD(d, d1, d2, f);
    }

    public boolean isSphereVisible(float af[], float f)
    {
        return isSphereVisible(af[0], af[1], af[2], f);
    }

    public boolean isSphereVisible(double ad[], float f)
    {
        return isSphereVisible(ad[0], ad[1], ad[2], f);
    }

    public boolean isSphereVisible(com.maddox.JGP.Point3f point3f, float f)
    {
        return isSphereVisible(point3f.x, point3f.y, point3f.z, f);
    }

    public boolean isSphereVisible(com.maddox.JGP.Point3d point3d, float f)
    {
        return isSphereVisible(point3d.x, point3d.y, point3d.z, f);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected Camera()
    {
        flags |= 0x6000;
        ZNear = 0.0F;
        ZFar = 1.0F;
        pos = new ActorPosMove(this);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public void activateWorldMode(int i)
    {
        com.maddox.il2.engine.Camera.ActivateWorldMode(i);
    }

    public void deactivateWorldMode()
    {
        com.maddox.il2.engine.Camera.DeactivateWorldMode();
    }

    public static native void SetTargetSpeed(float f, float f1, float f2);

    public static native void GetUniformDistParams(float af[]);

    protected static native void ActivateWorldMode(int i);

    protected static native void DeactivateWorldMode();

    protected static native void GetVirtOrigin(float af[]);

    protected static native boolean SetViewportCrop(float f, int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1, int i2, int j2);

    protected static native void SetCameraPos(double ad[]);

    protected static native void SetOrtho2D(float f, float f1, float f2, float f3, float f4, float f5);

    protected static native void SetOrtho(float f, float f1, float f2);

    protected static native void SetZOrder(float f);

    protected static native void SetFOV(float f, float f1, float f2);

    protected static native boolean isSphereVisibleF(float f, float f1, float f2, float f3);

    protected static native boolean isSphereVisibleD(double d, double d1, double d2, float f);

    public float ZNear;
    public float ZFar;
    public float XOffset;
    public float YOffset;
    protected static float tmpOr[] = new float[2];
    protected static float tmp[] = new float[16];
    protected static double tmpd[] = new double[16];
    protected static com.maddox.JGP.Point3d tmpP = new Point3d();
    protected static com.maddox.il2.engine.Orient tmpO = new Orient();

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
