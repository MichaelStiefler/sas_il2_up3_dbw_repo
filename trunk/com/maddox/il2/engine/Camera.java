package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.rts.Message;

public abstract class Camera extends Actor
{
  public float ZNear;
  public float ZFar;
  public float XOffset;
  public float YOffset;
  protected static float[] tmpOr = new float[2];
  protected static float[] tmp = new float[16];
  protected static double[] tmpd = new double[16];
  protected static Point3d tmpP = new Point3d();
  protected static Orient tmpO = new Orient();

  public void set(float paramFloat1, float paramFloat2)
  {
    this.ZNear = paramFloat1; this.ZFar = paramFloat2;
  }

  public abstract boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  protected final boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return activate(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  public boolean isSphereVisible(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return isSphereVisibleF(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public boolean isSphereVisible(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat)
  {
    return isSphereVisibleD(paramDouble1, paramDouble2, paramDouble3, paramFloat);
  }

  public boolean isSphereVisible(float[] paramArrayOfFloat, float paramFloat)
  {
    return isSphereVisible(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramFloat);
  }

  public boolean isSphereVisible(double[] paramArrayOfDouble, float paramFloat) {
    return isSphereVisible(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2], paramFloat);
  }

  public boolean isSphereVisible(Point3f paramPoint3f, float paramFloat)
  {
    return isSphereVisible(paramPoint3f.x, paramPoint3f.y, paramPoint3f.z, paramFloat);
  }

  public boolean isSphereVisible(Point3d paramPoint3d, float paramFloat)
  {
    return isSphereVisible(paramPoint3d.x, paramPoint3d.y, paramPoint3d.z, paramFloat);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  protected Camera() {
    this.flags |= 24576;
    this.ZNear = 0.0F;
    this.ZFar = 1.0F;
    this.pos = new ActorPosMove(this);
  }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  public void activateWorldMode(int paramInt) {
    ActivateWorldMode(paramInt);
  }

  public void deactivateWorldMode() {
    DeactivateWorldMode();
  }

  public static native void SetTargetSpeed(float paramFloat1, float paramFloat2, float paramFloat3);

  public static native void GetUniformDistParams(float[] paramArrayOfFloat);

  protected static native void ActivateWorldMode(int paramInt);

  protected static native void DeactivateWorldMode();

  protected static native void GetVirtOrigin(float[] paramArrayOfFloat);

  protected static native boolean SetViewportCrop(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  protected static native void SetCameraPos(double[] paramArrayOfDouble);

  protected static native void SetOrtho2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected static native void SetOrtho(float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void SetZOrder(float paramFloat);

  protected static native void SetFOV(float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native boolean isSphereVisibleF(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  protected static native boolean isSphereVisibleD(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);

  static
  {
    GObj.loadNative();
  }
}