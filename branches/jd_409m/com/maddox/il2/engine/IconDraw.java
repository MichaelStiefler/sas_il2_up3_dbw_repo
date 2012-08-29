package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.rts.Property;

public class IconDraw
{
  private static Point3d _point = new Point3d();

  private static float[] _xyzuv = new float[20];
  private static int[] _xyzuvIndx = { 0, 1, 2, 2, 3, 0 };

  protected static int color = -1;
  protected static Color4f colorf = new Color4f(1.0F, 1.0F, 1.0F, 1.0F);

  protected static int dx = 32;
  protected static int dy = 32;
  protected static float r = dx / 2;

  public static boolean preRender(Actor paramActor)
  {
    if (!Actor.isValid(paramActor)) return false;
    if (paramActor.icon == null) return false;
    if (paramActor.icon.preRender() == 0) return false;
    paramActor.pos.getRender(_point);
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    return localCameraOrtho2D.isSphereVisible(_point, (float)(r / localCameraOrtho2D.worldScale));
  }

  public static void render(Actor paramActor) {
    if (!Actor.isValid(paramActor)) return;
    if (paramActor.icon == null) return;
    paramActor.pos.getRender(_point);
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    float f1 = (float)((_point.x - localCameraOrtho2D.worldXOffset) * localCameraOrtho2D.worldScale - dx / 2);
    float f2 = (float)((_point.y - localCameraOrtho2D.worldYOffset) * localCameraOrtho2D.worldScale - dy / 2);
    Render.drawTile(f1, f2, dx, dy, 0.0F, paramActor.icon, color, 0.0F, 1.0F, 1.0F, -1.0F);
  }

  public static void render(Actor paramActor, double paramDouble1, double paramDouble2)
  {
    render(paramActor, (float)paramDouble1, (float)paramDouble2);
  }
  public static void render(Actor paramActor, float paramFloat1, float paramFloat2) {
    if (!Actor.isValid(paramActor)) return;
    if (paramActor.icon == null) return;
    Render.drawTile(paramFloat1 - dx / 2, paramFloat2 - dy / 2, dx, dy, 0.0F, paramActor.icon, color, 0.0F, 1.0F, 1.0F, -1.0F);
  }
  public static void render(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (!Actor.isValid(paramActor)) return;
    if (paramActor.icon == null) return;
    Render.drawTile(paramFloat1 - dx * paramFloat3, paramFloat2 - dy * paramFloat4, dx, dy, 0.0F, paramActor.icon, color, 0.0F, 1.0F, 1.0F, -1.0F);
  }
  public static void render(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3) {
    if (!Actor.isValid(paramActor)) return;
    if (paramActor.icon == null) return;
    render(paramActor.icon, paramFloat1, paramFloat2, paramFloat3);
  }

  public static void render(Mat paramMat, double paramDouble1, double paramDouble2) {
    render(paramMat, (float)paramDouble1, (float)paramDouble2);
  }
  public static void render(Mat paramMat, float paramFloat1, float paramFloat2) {
    if (paramMat == null) return;
    Render.drawTile(paramFloat1 - dx / 2, paramFloat2 - dy / 2, dx, dy, 0.0F, paramMat, color, 0.0F, 1.0F, 1.0F, -1.0F);
  }
  public static void render(Mat paramMat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (paramMat == null) return;
    Render.drawTile(paramFloat1 - dx * paramFloat3, paramFloat2 - dy * paramFloat4, dx, dy, 0.0F, paramMat, color, 0.0F, 1.0F, 1.0F, -1.0F);
  }
  public static void render(Mat paramMat, float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramMat == null) return;
    float f1 = dx / 2;
    float f2 = dy / 2;
    float f3 = (float)Math.sin(paramFloat3 * 3.141592653589793D / 180.0D);
    float f4 = (float)Math.cos(paramFloat3 * 3.141592653589793D / 180.0D);
    _setXyzuv(0, paramFloat1, paramFloat2, -f1, -f2, f3, f4, 0.0F, 1.0F);
    _setXyzuv(1, paramFloat1, paramFloat2, f1, -f2, f3, f4, 1.0F, 1.0F);
    _setXyzuv(2, paramFloat1, paramFloat2, f1, f2, f3, f4, 1.0F, 0.0F);
    _setXyzuv(3, paramFloat1, paramFloat2, -f1, f2, f3, f4, 0.0F, 0.0F);
    Render.drawBeginTriangleLists(-1);
    Render.drawSetMaterial(paramMat);
    Render.drawTriangleList(_xyzuv, 4, color, 0, _xyzuvIndx, 2);
    Render.drawEnd();
  }

  private static void _setXyzuv(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    _xyzuv[(paramInt * 5 + 0)] = (paramFloat1 + paramFloat3 * paramFloat6 + paramFloat4 * paramFloat5);
    _xyzuv[(paramInt * 5 + 1)] = (paramFloat2 + paramFloat4 * paramFloat6 - paramFloat3 * paramFloat5);
    _xyzuv[(paramInt * 5 + 2)] = 0.0F;
    _xyzuv[(paramInt * 5 + 3)] = paramFloat7;
    _xyzuv[(paramInt * 5 + 4)] = paramFloat8;
  }

  public static void setColor(int paramInt)
  {
    colorf.jdField_x_of_type_Float = ((paramInt & 0xFF) / 255.0F);
    colorf.jdField_y_of_type_Float = ((paramInt >> 8 & 0xFF) / 255.0F);
    colorf.jdField_z_of_type_Float = ((paramInt >> 16 & 0xFF) / 255.0F);
    colorf.jdField_w_of_type_Float = ((paramInt >> 24 & 0xFF) / 255.0F);
    color = paramInt;
  }

  public static void setColor(int paramInt1, int paramInt2, int paramInt3) {
    colorf.jdField_x_of_type_Float = (paramInt1 / 255.0F);
    colorf.jdField_y_of_type_Float = (paramInt2 / 255.0F);
    colorf.jdField_z_of_type_Float = (paramInt3 / 255.0F);
    colorClamp();
  }
  public static void setColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    colorf.jdField_x_of_type_Float = (paramInt1 / 255.0F);
    colorf.jdField_y_of_type_Float = (paramInt2 / 255.0F);
    colorf.jdField_z_of_type_Float = (paramInt3 / 255.0F);
    colorf.jdField_w_of_type_Float = (paramInt4 / 255.0F);
    colorClamp();
  }
  public static void setColor(float paramFloat1, float paramFloat2, float paramFloat3) {
    colorf.jdField_x_of_type_Float = paramFloat1;
    colorf.jdField_y_of_type_Float = paramFloat2;
    colorf.jdField_z_of_type_Float = paramFloat3;
    colorClamp();
  }
  public static void setColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    colorf.jdField_x_of_type_Float = paramFloat1;
    colorf.jdField_y_of_type_Float = paramFloat2;
    colorf.jdField_z_of_type_Float = paramFloat3;
    colorf.jdField_w_of_type_Float = paramFloat4;
    colorClamp();
  }
  public static void setColor(Color4f paramColor4f) {
    colorf.set(paramColor4f);
    colorClamp();
  }
  public static void getColor(Color4f paramColor4f) {
    paramColor4f.set(colorf);
  }

  protected static void colorClamp() {
    if (colorf.jdField_x_of_type_Float < 0.0F) colorf.jdField_x_of_type_Float = 0.0F; if (colorf.jdField_x_of_type_Float > 1.0F) colorf.jdField_x_of_type_Float = 1.0F;
    if (colorf.jdField_y_of_type_Float < 0.0F) colorf.jdField_y_of_type_Float = 0.0F; if (colorf.jdField_y_of_type_Float > 1.0F) colorf.jdField_y_of_type_Float = 1.0F;
    if (colorf.jdField_z_of_type_Float < 0.0F) colorf.jdField_z_of_type_Float = 0.0F; if (colorf.jdField_z_of_type_Float > 1.0F) colorf.jdField_z_of_type_Float = 1.0F;
    if (colorf.jdField_w_of_type_Float < 0.0F) colorf.jdField_w_of_type_Float = 0.0F; if (colorf.jdField_w_of_type_Float > 1.0F) colorf.jdField_w_of_type_Float = 1.0F;
    color = (int)(255.0F * colorf.jdField_x_of_type_Float + 0.5D) | (int)(255.0F * colorf.jdField_y_of_type_Float + 0.5D) << 8 | (int)(255.0F * colorf.jdField_z_of_type_Float + 0.5D) << 16 | (int)(255.0F * colorf.jdField_w_of_type_Float + 0.5D) << 24;
  }

  public static void setScrSize(int paramInt1, int paramInt2)
  {
    dx = paramInt1;
    dy = paramInt2;
    if (dx > dy) r = dx / 2; else
      r = dy / 2; 
  }
  public static int scrSizeX() {
    return dx; } 
  public static int scrSizeY() { return dy;
  }

  public static Mat create(Actor paramActor)
  {
    if (Config.isUSE_RENDER()) {
      if (!Actor.isValid(paramActor)) return null;
      if (paramActor.icon != null)
        return paramActor.icon;
      Class localClass = paramActor.getClass();
      Mat localMat = (Mat)Property.value(localClass, "iconMat", null);
      int i = localMat == null ? 1 : 0;
      if (localMat == null) {
        String str = Property.stringValue(localClass, "iconName", null);
        if (str != null) try {
            localMat = Mat.New(str);
          } catch (Exception localException) {
          } 
      }
      if (localMat != null) {
        if (i != 0)
          Property.set(localClass, "iconMat", localMat);
        int j = (paramActor.pos != null) && (paramActor.draw == null) ? 1 : 0;
        paramActor.icon = localMat;
        if ((j != 0) && (paramActor.isDrawing()))
        {
          paramActor.drawing(false);
          paramActor.drawing(true);
        }
      }
      return localMat;
    }
    return null;
  }

  public static Mat get(String paramString)
  {
    if (Config.isUSE_RENDER()) {
      Mat localMat = null;
      try { localMat = Mat.New(paramString); } catch (Exception localException) {
      }return localMat;
    }
    return null;
  }
}