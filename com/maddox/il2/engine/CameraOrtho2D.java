package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class CameraOrtho2D extends Camera
{
  public float left;
  public float right;
  public float bottom;
  public float top;
  public double worldXOffset;
  public double worldYOffset;
  public double worldScale;

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.left = paramFloat1; this.right = paramFloat2; this.bottom = paramFloat3; this.top = paramFloat4;
  }

  public boolean isSphereVisible(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return isSphereVisible(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public boolean isSphereVisible(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat)
  {
    double d1 = paramFloat;
    double d2 = this.worldXOffset + (this.right - this.left) / this.worldScale;
    double d3 = this.worldYOffset + (this.top - this.bottom) / this.worldScale;

    return (paramDouble1 + d1 >= this.worldXOffset) && (paramDouble1 - d1 <= d2) && (paramDouble2 + d1 >= this.worldYOffset) && (paramDouble2 - d1 <= d3);
  }

  public boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    Camera.SetViewportCrop(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);

    Camera.SetOrtho2D(this.left, this.right, this.bottom, this.top, this.jdField_ZNear_of_type_Float, this.jdField_ZFar_of_type_Float);

    this.pos.getRender(Camera.tmpP, Camera.tmpO);
    Camera.tmpd[0] = Camera.tmpP.x; Camera.tmpd[1] = Camera.tmpP.y; Camera.tmpd[2] = Camera.tmpP.z;
    Camera.tmpd[3] = (90.0F - Camera.tmpO.azimut());
    Camera.tmpd[4] = Camera.tmpO.tangage();
    Camera.tmpd[5] = (-Camera.tmpO.kren());
    Camera.SetCameraPos(Camera.tmpd);
    Camera.GetVirtOrigin(Camera.tmpOr);
    this.XOffset = Camera.tmpOr[0];
    this.YOffset = Camera.tmpOr[1];

    return true;
  }

  public CameraOrtho2D() {
    this.left = 0.0F; this.right = 1.0F; this.bottom = 0.0F; this.top = 1.0F;
    this.worldXOffset = (this.worldYOffset = 0.0D);
    this.worldScale = 1.0D;
    this.jdField_ZNear_of_type_Float = -1.0F;
    this.jdField_ZFar_of_type_Float = 1.0F;
  }
}