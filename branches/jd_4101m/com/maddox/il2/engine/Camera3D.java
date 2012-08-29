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

    this.ZNear = paramFloat2; this.ZFar = paramFloat3;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.FOV = paramFloat1;

    this.ZNear = paramFloat2; this.ZFar = paramFloat3;
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
    SetZOrder(Render.current().getZOrder());

    SetViewportCrop(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);

    SetFOV(this.FOV, this.ZNear, this.ZFar);

    this.pos.getRender(tmpP, tmpO);
    tmpd[0] = tmpP.x; tmpd[1] = tmpP.y; tmpd[2] = tmpP.z;
    tmpd[3] = (-tmpO.azimut());
    tmpd[4] = tmpO.tangage();
    tmpd[5] = (-tmpO.kren());
    SetCameraPos(tmpd);
    GetVirtOrigin(tmpOr);
    this.XOffset = tmpOr[0];
    this.YOffset = tmpOr[1];

    return true;
  }

  public Camera3D()
  {
    this.FOV = 90.0F;

    this.aspect = 1.333333F;
    this.viewPortWidth = 640;
  }
}