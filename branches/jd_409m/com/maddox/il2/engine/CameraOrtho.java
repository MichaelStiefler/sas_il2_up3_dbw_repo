package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class CameraOrtho extends Camera
{
  public float left;
  public float right;
  public float bottom;
  public float top;

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.left = paramFloat1; this.right = paramFloat2; this.bottom = paramFloat3; this.top = paramFloat4;
    this.jdField_ZNear_of_type_Float = paramFloat5; this.jdField_ZFar_of_type_Float = paramFloat6;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.left = paramFloat1; this.right = paramFloat2; this.bottom = paramFloat3; this.top = paramFloat4;
  }

  public boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    Camera.SetViewportCrop(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);

    Camera.SetOrtho(this.right - this.left, this.jdField_ZNear_of_type_Float, this.jdField_ZFar_of_type_Float);

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

  public CameraOrtho() {
    this.left = 0.0F; this.right = 1.0F; this.bottom = 0.0F; this.top = 1.0F;
  }
}