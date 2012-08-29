package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class Camera3D extends Camera
{
  private float FOV;
  private float aspect;
  private int viewPortWidth;

  public float FOV()
  {
    return this.FOV; } 
  public float aspect() { return this.aspect; }

  protected void setViewPortWidth(int paramInt)
  {
    this.viewPortWidth = paramInt;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.FOV = paramFloat1;

    this.jdField_ZNear_of_type_Float = paramFloat2; this.jdField_ZFar_of_type_Float = paramFloat3;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.FOV = paramFloat1;

    this.jdField_ZNear_of_type_Float = paramFloat2; this.jdField_ZFar_of_type_Float = paramFloat3;
    this.aspect = paramFloat4;
  }

  public void set(float paramFloat)
  {
    this.FOV = paramFloat;
  }

  public void set(float paramFloat1, float paramFloat2)
  {
    this.FOV = paramFloat1;

    this.aspect = paramFloat2;
  }

  public boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    if (this.FOV <= 0.0F) {
      return false;
    }
    Camera.SetZOrder(Render.current().getZOrder());

    Camera.SetViewportCrop(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);

    Camera.SetFOV(this.FOV, this.jdField_ZNear_of_type_Float, this.jdField_ZFar_of_type_Float);

    this.pos.getRender(Camera.tmpP, Camera.tmpO);
    Camera.tmpd[0] = Camera.tmpP.x; Camera.tmpd[1] = Camera.tmpP.y; Camera.tmpd[2] = Camera.tmpP.z;
    Camera.tmpd[3] = (-Camera.tmpO.azimut());
    Camera.tmpd[4] = Camera.tmpO.tangage();
    Camera.tmpd[5] = (-Camera.tmpO.kren());
    Camera.SetCameraPos(Camera.tmpd);
    Camera.GetVirtOrigin(Camera.tmpOr);
    this.XOffset = Camera.tmpOr[0];
    this.YOffset = Camera.tmpOr[1];

    return true;
  }

  public Camera3D()
  {
    this.FOV = 90.0F;

    this.aspect = 1.333333F;
    this.viewPortWidth = 640;
  }
}