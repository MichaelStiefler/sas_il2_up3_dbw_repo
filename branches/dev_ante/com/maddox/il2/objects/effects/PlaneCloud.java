package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.opengl.gl;

public class PlaneCloud
{
  public static final float HEIGHT_MIN = 1.0F;
  public static final float HEIGHT_MAX = 25000.0F;
  private boolean bShow;
  private static final float Scale0 = 1.3825F;
  private static final float Scale1 = 82.927498F;
  private Mat mat0;
  private Mat mat1;
  private float z0;
  private float z1;
  private static final int MAXCOUNT = 50;
  private Point3d p;
  private Orient o;
  private float[] xyzuv;
  private int[] colorDS;
  private int[] Clip;
  private int[] indexes;
  private float[] Mtx;
  int Color;
  int LightColor;
  Vector3f SurfNorm;

  public boolean isShow()
  {
    return this.bShow;
  }

  public void setShow(boolean flag)
  {
    this.bShow = flag;
  }

  public void setHeight(float f, float f1)
  {
    this.z0 = f;
    this.z1 = f1;
  }

  public float getHeightMin()
  {
    return this.z0;
  }

  public float getHeightMax()
  {
    this.z1 += 1400.0F;
    return this.z1;
  }

  public void preRender()
  {
    if (!this.bShow)
    {
      return;
    }

    this.mat0.preRender();
    this.mat1.preRender();
  }

  private void CalcLightColor(Mat mat)
  {
    this.LightColor = Mat.LightColor(this.Color, mat.SunLightf(this.SurfNorm));
  }

  public void renderFar()
  {
    Render.drawSetMaterial(this.mat1, (float)this.p.x, (float)this.p.y, (float)this.p.z, 60000.0F);
    CalcLightColor(this.mat1);
    if (this.z0 > 0.0F)
    {
      draw(((int)(this.p.x / 4000.0D) - 1 - 9) * 4000, ((int)(this.p.y / 4000.0D) - 1 - 9) * 4000, 1.59375E-005F, 12000.0F, this.z0);
    }
    if (this.z1 > 0.0F)
    {
      draw(((int)(this.p.x / 4000.0D) - 1 - 9) * 4000, ((int)(this.p.y / 4000.0D) - 1 - 9) * 4000, 3.864583E-005F, 12000.0F, this.z1);
    }
  }

  public void render()
  {
    if (!this.bShow)
    {
      return;
    }
    Camera3D camera3d = (Camera3D)Render.currentCamera();
    camera3d.activateWorldMode(0);
    gl.GetFloatv(2982, this.Mtx);
    camera3d.deactivateWorldMode();
    camera3d.pos.getRender(this.p, this.o);
    Render.drawBeginTriangleLists(0);
    Render.drawSetMaterial(this.mat0, (float)this.p.x, (float)this.p.y, (float)this.p.z, 60000.0F);
    CalcLightColor(this.mat0);
    if (this.z0 > 0.0F)
    {
      draw0(((int)(this.p.x / 4000.0D) - 1) * 4000, ((int)(this.p.y / 4000.0D) - 1) * 4000, 1.59375E-005F, 1000.0F, 12, this.z0);
    }
    if (this.z1 > 0.0F)
    {
      draw0(((int)(this.p.x / 4000.0D) - 1) * 4000, ((int)(this.p.y / 4000.0D) - 1) * 4000, 3.864583E-005F, 1000.0F, 12, this.z1);
    }
    renderFar();
    Render.drawEnd();
  }

  private void draw(float f, float f1, float f2, float f3, float f4)
  {
    int i = 0;
    int j = 0;
    float f5 = f1;
    float f6 = 1.225E+009F;
    float f7 = 3.6E+009F;
    float f8 = 1.0F / (f7 - f6);
    float f9 = f4 > this.z0 ? 20000.0F : 0.0F;
    this.LightColor &= 16777215;
    float f10 = f * f2 % 1.0F;
    float f11 = f1 * f2 % 1.0F;
    float f12 = f3 * f2;
    for (int k = 0; k <= 9; k++)
    {
      float f13 = f;
      for (int i1 = 0; i1 <= 9; i1++)
      {
        float f14 = (f13 - f - f3 * 4.0F) * (f13 - f - f3 * 4.0F) + (f5 - f1 - f3 * 4.0F) * (f5 - f1 - f3 * 4.0F);
        float f15 = 1.0F;
        if (f14 > f6)
        {
          f15 = (f7 - f14) * f8;
        }
        this.xyzuv[(i++)] = f13;
        this.xyzuv[(i++)] = f5;
        this.xyzuv[(i++)] = (f4 * f15 + f9 * (1.0F - f15));
        this.xyzuv[(i++)] = (f10 + i1 * f12);
        this.xyzuv[(i++)] = (f11 + k * f12);
        int k1 = (int)(f15 * 255.0F);
        if (k1 < 0)
        {
          k1 = 0;
        }
        this.colorDS[(j++)] = (this.LightColor | k1 << 24);
        this.colorDS[(j++)] = 0;
        f13 += f3;
      }

      f5 += f3;
    }

    i = 0;
    for (int l = 0; l < 9; l++)
    {
      for (int j1 = 0; j1 < 9; j1++)
      {
        if ((l == 3) && (j1 == 3))
          continue;
        this.indexes[(i++)] = (l * 10 + j1);
        this.indexes[(i++)] = (l * 10 + j1 + 1);
        this.indexes[(i++)] = ((l + 1) * 10 + j1 + 1);
        this.indexes[(i++)] = ((l + 1) * 10 + j1 + 1);
        this.indexes[(i++)] = ((l + 1) * 10 + j1);
        this.indexes[(i++)] = (l * 10 + j1);
      }

    }

    Render.drawTriangleList(this.xyzuv, this.colorDS, 100, this.indexes, i / 3);
  }

  private void draw0(float f, float f1, float f2, float f3, int i, float f4)
  {
    Camera3D camera3d = (Camera3D)Render.currentCamera();
    float f5 = this.Mtx[2];
    float f6 = this.Mtx[6];
    float f7 = this.Mtx[10];
    float f8 = this.Mtx[14];
    float f9 = camera3d.ZNear;
    int j = 0;
    int k = 0;
    float f10 = f * f2 % 1.0F;
    float f11 = f1 * f2 % 1.0F;
    float f12 = f3 * f2;
    float f13 = f1;
    float f14 = -(f9 + f4 * f7 + f8);
    for (int l = 0; l <= i; l++)
    {
      float f15 = f;
      float f16 = f14 - f13 * f6;
      for (int k1 = 0; k1 <= i; k1++)
      {
        this.xyzuv[(j + 0)] = f15;
        this.xyzuv[(j + 1)] = f13;
        this.xyzuv[(j + 2)] = f4;
        this.xyzuv[(j + 3)] = (f10 + k1 * f12);
        this.xyzuv[(j + 4)] = (f11 + l * f12);
        this.Clip[(k++)] = (f15 * f5 > f16 ? 0 : 1);
        j += 5;
        f15 += f3;
      }

      f13 += f3;
    }

    j = 0;
    int i1 = 0;
    for (int j1 = 0; j1 < i; j1++)
    {
      for (int l1 = 0; l1 < i; l1++)
      {
        int i2 = j1 * (i + 1) + l1;
        int j2 = i2 + i + 1;
        if ((this.Clip[i2] | this.Clip[(i2 + 1)] | this.Clip[j2] | this.Clip[(j2 + 1)]) == 0)
          continue;
        this.indexes[(j + 0)] = i2;
        this.indexes[(j + 1)] = (i2 + 1);
        this.indexes[(j + 2)] = (j2 + 1);
        this.indexes[(j + 3)] = (j2 + 1);
        this.indexes[(j + 4)] = j2;
        this.indexes[(j + 5)] = i2;
        j += 6;
        i1 += 2;
      }

    }

    Render.drawTriangleList(this.xyzuv, (i + 1) * (i + 1), this.LightColor, 0, this.indexes, i1);
  }

  public PlaneCloud(float f, float f1)
  {
    this.bShow = true;
    this.p = new Point3d();
    this.o = new Orient();
    this.xyzuv = new float[845];
    this.colorDS = new int[338];
    this.Clip = new int['©'];
    this.indexes = new int[864];
    this.Mtx = new float[16];
    this.Color = -1;
    this.SurfNorm = new Vector3f(0.0F, 0.0F, 1.0F);
    this.z0 = f;
    this.z1 = f1;
    this.mat0 = Mat.New("3do/Effects/PlaneCloud/Cloud0.mat");
    this.mat1 = Mat.New("3do/Effects/PlaneCloud/Cloud1.mat");
  }
}