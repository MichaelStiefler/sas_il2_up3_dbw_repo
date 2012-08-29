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
  public static final float HEIGHT_MIN = 500.0F;
  public static final float HEIGHT_MAX = 4100.0F;
  private boolean bShow = true;
  private static final float Scale0 = 0.3825F;
  private static final float Scale1 = 0.9275F;
  private Mat mat0;
  private Mat mat1;
  private float z0;
  private float z1;
  private static final int MAXCOUNT = 12;
  private Point3d p = new Point3d();
  private Orient o = new Orient();
  private float[] xyzuv = new float[845];
  private int[] colorDS = new int[338];

  private int[] Clip = new int['©'];
  private int[] indexes = new int[864];
  private float[] Mtx = new float[16];

  int Color = -1;
  int LightColor;
  Vector3f SurfNorm = new Vector3f(0.0F, 0.0F, 1.0F);

  public boolean isShow() { return this.bShow; } 
  public void setShow(boolean paramBoolean) { this.bShow = paramBoolean; }

  public void setHeight(float paramFloat1, float paramFloat2) {
    this.z0 = paramFloat1;
    this.z1 = paramFloat2;
  }
  public float getHeightMin() {
    return this.z0;
  }
  public float getHeightMax() {
    return this.z1;
  }

  public void preRender() {
    if (!this.bShow) return;
    this.mat0.preRender();
    this.mat1.preRender();
  }

  private void CalcLightColor(Mat paramMat)
  {
    this.LightColor = Mat.LightColor(this.Color, paramMat.SunLightf(this.SurfNorm));
  }

  public void renderFar()
  {
    Render.drawSetMaterial(this.mat1, (float)this.p.jdField_x_of_type_Double, (float)this.p.jdField_y_of_type_Double, (float)this.p.jdField_z_of_type_Double, 60000.0F);
    CalcLightColor(this.mat1);
    if (this.z0 > 0.0F)
      draw(((int)(this.p.jdField_x_of_type_Double / 4000.0D) - 1 - 9) * 4000, ((int)(this.p.jdField_y_of_type_Double / 4000.0D) - 1 - 9) * 4000, 1.59375E-005F, 12000.0F, this.z0);
    if (this.z1 > 0.0F)
      draw(((int)(this.p.jdField_x_of_type_Double / 4000.0D) - 1 - 9) * 4000, ((int)(this.p.jdField_y_of_type_Double / 4000.0D) - 1 - 9) * 4000, 3.864583E-005F, 12000.0F, this.z1);
  }

  public void render()
  {
    if (!this.bShow) return;
    Camera3D localCamera3D = (Camera3D)Render.currentCamera();

    localCamera3D.activateWorldMode(0);
    gl.GetFloatv(2982, this.Mtx);
    localCamera3D.deactivateWorldMode();

    localCamera3D.pos.getRender(this.p, this.o);
    Render.drawBeginTriangleLists(0);

    Render.drawSetMaterial(this.mat0, (float)this.p.jdField_x_of_type_Double, (float)this.p.jdField_y_of_type_Double, (float)this.p.jdField_z_of_type_Double, 60000.0F);
    CalcLightColor(this.mat0);

    if (this.z0 > 0.0F)
      draw0(((int)(this.p.jdField_x_of_type_Double / 4000.0D) - 1) * 4000, ((int)(this.p.jdField_y_of_type_Double / 4000.0D) - 1) * 4000, 1.59375E-005F, 1000.0F, 12, this.z0);
    if (this.z1 > 0.0F) {
      draw0(((int)(this.p.jdField_x_of_type_Double / 4000.0D) - 1) * 4000, ((int)(this.p.jdField_y_of_type_Double / 4000.0D) - 1) * 4000, 3.864583E-005F, 1000.0F, 12, this.z1);
    }
    renderFar();
    Render.drawEnd();
  }

  private void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    int i = 0; int j = 0;
    float f1 = paramFloat2;
    float f2 = 1.225E+009F;
    float f3 = 3.6E+009F;
    float f4 = 1.0F / (f3 - f2);
    float f5 = paramFloat5 <= this.z0 ? 0.0F : 8000.0F;
    this.LightColor &= 16777215;

    float f6 = paramFloat1 * paramFloat3 % 1.0F;
    float f7 = paramFloat2 * paramFloat3 % 1.0F;
    float f8 = paramFloat4 * paramFloat3;
    int n;
    for (int k = 0; k <= 9; k++) {
      float f9 = paramFloat1;
      for (n = 0; n <= 9; n++)
      {
        float f10 = (f9 - paramFloat1 - paramFloat4 * 4.0F) * (f9 - paramFloat1 - paramFloat4 * 4.0F) + (f1 - paramFloat2 - paramFloat4 * 4.0F) * (f1 - paramFloat2 - paramFloat4 * 4.0F);
        float f11 = 1.0F;
        if (f10 > f2)
        {
          f11 = (f3 - f10) * f4;
        }
        this.xyzuv[(i++)] = f9; this.xyzuv[(i++)] = f1; this.xyzuv[(i++)] = (paramFloat5 * f11 + f5 * (1.0F - f11));
        this.xyzuv[(i++)] = (f6 + n * f8); this.xyzuv[(i++)] = (f7 + k * f8);
        int i1 = (int)(f11 * 255.0F); if (i1 < 0) i1 = 0;

        this.colorDS[(j++)] = (this.LightColor | i1 << 24); this.colorDS[(j++)] = 0;
        f9 += paramFloat4;
      }
      f1 += paramFloat4;
    }
    i = 0;
    for (int m = 0; m < 9; m++) {
      for (n = 0; n < 9; n++) {
        if ((m == 3) && (n == 3))
          continue;
        this.indexes[(i++)] = (m * 10 + n);
        this.indexes[(i++)] = (m * 10 + n + 1);
        this.indexes[(i++)] = ((m + 1) * 10 + n + 1);
        this.indexes[(i++)] = ((m + 1) * 10 + n + 1);
        this.indexes[(i++)] = ((m + 1) * 10 + n);
        this.indexes[(i++)] = (m * 10 + n);
      }
    }

    Render.drawTriangleList(this.xyzuv, this.colorDS, 100, this.indexes, i / 3);
  }

  private void draw0(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, float paramFloat5)
  {
    Camera3D localCamera3D = (Camera3D)Render.currentCamera();
    float f1 = this.Mtx[2]; float f2 = this.Mtx[6]; float f3 = this.Mtx[10]; float f4 = this.Mtx[14];
    float f5 = localCamera3D.ZNear;

    int i = 0; int j = 0;
    float f6 = paramFloat1 * paramFloat3 % 1.0F;
    float f7 = paramFloat2 * paramFloat3 % 1.0F;
    float f8 = paramFloat4 * paramFloat3;
    float f9 = paramFloat2;
    float f10 = -(f5 + paramFloat5 * f3 + f4);
    int i1;
    for (int k = 0; k <= paramInt; k++) {
      float f11 = paramFloat1;
      float f12 = f10 - f9 * f2;
      for (i1 = 0; i1 <= paramInt; i1++) {
        this.xyzuv[(i + 0)] = f11; this.xyzuv[(i + 1)] = f9; this.xyzuv[(i + 2)] = paramFloat5;
        this.xyzuv[(i + 3)] = (f6 + i1 * f8); this.xyzuv[(i + 4)] = (f7 + k * f8);
        this.Clip[(j++)] = (f11 * f1 <= f12 ? 1 : 0);
        i += 5;

        f11 += paramFloat4;
      }

      f9 += paramFloat4;
    }
    i = 0;
    int m = 0;
    for (int n = 0; n < paramInt; n++) {
      for (i1 = 0; i1 < paramInt; i1++) {
        int i2 = n * (paramInt + 1) + i1; int i3 = i2 + paramInt + 1;
        if ((this.Clip[i2] | this.Clip[(i2 + 1)] | this.Clip[i3] | this.Clip[(i3 + 1)]) != 0) {
          this.indexes[(i + 0)] = i2;
          this.indexes[(i + 1)] = (i2 + 1);
          this.indexes[(i + 2)] = (i3 + 1);
          this.indexes[(i + 3)] = (i3 + 1);
          this.indexes[(i + 4)] = i3;
          this.indexes[(i + 5)] = i2;
          i += 6; m += 2;
        }
      }
    }
    Render.drawTriangleList(this.xyzuv, (paramInt + 1) * (paramInt + 1), this.LightColor, 0, this.indexes, m);
  }

  public PlaneCloud(float paramFloat1, float paramFloat2)
  {
    this.z0 = paramFloat1;
    this.z1 = paramFloat2;

    this.mat0 = Mat.New("3do/Effects/PlaneCloud/Cloud0.mat");
    this.mat1 = Mat.New("3do/Effects/PlaneCloud/Cloud1.mat");
  }
}