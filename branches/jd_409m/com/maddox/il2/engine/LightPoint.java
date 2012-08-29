package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;

public class LightPoint
  implements Destroy
{
  protected float I = 0.0F;
  protected float R = 0.0F;
  protected int IX = 0;
  protected int IY = 0;

  protected int cppObj = 0;

  protected int stamp = 0;

  protected static int curStamp = 0;

  private static float[] tmpf = new float[3];

  public LightPoint()
  {
    if (!Config.isUSE_RENDER()) return;
    this.cppObj = New();
    if (this.cppObj == 0)
      throw new GObjException("LightPoint not created");
    setPos(0.0D, 0.0D, 0.0D);
  }

  public void setPos(double paramDouble1, double paramDouble2, double paramDouble3) {
    if (!Config.isUSE_RENDER()) return;
    this.IX = (int)paramDouble1; this.IY = (int)paramDouble2;
    setPos(this.cppObj, (float)paramDouble1, (float)paramDouble2, (float)paramDouble3);
  }
  public void setPos(double[] paramArrayOfDouble) {
    if (!Config.isUSE_RENDER()) return;
    this.IX = (int)paramArrayOfDouble[0]; this.IY = (int)paramArrayOfDouble[1];
    setPos(this.cppObj, (float)paramArrayOfDouble[0], (float)paramArrayOfDouble[1], (float)paramArrayOfDouble[2]);
  }
  public void setPos(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    this.IX = (int)paramPoint3d.jdField_x_of_type_Double; this.IY = (int)paramPoint3d.jdField_y_of_type_Double;
    setPos(this.cppObj, (float)paramPoint3d.jdField_x_of_type_Double, (float)paramPoint3d.jdField_y_of_type_Double, (float)paramPoint3d.jdField_z_of_type_Double);
  }
  public void getPos(double[] paramArrayOfDouble) {
    if (!Config.isUSE_RENDER()) return;
    getPos(this.cppObj, tmpf);
    paramArrayOfDouble[0] = tmpf[0]; paramArrayOfDouble[1] = tmpf[1]; paramArrayOfDouble[2] = tmpf[2];
  }
  public void getPos(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    getPos(this.cppObj, tmpf);
    paramPoint3d.jdField_x_of_type_Double = tmpf[0]; paramPoint3d.jdField_y_of_type_Double = tmpf[1]; paramPoint3d.jdField_z_of_type_Double = tmpf[2];
  }

  public void setColor(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (!Config.isUSE_RENDER()) return;
    setColor(this.cppObj, paramFloat1, paramFloat2, paramFloat3);
  }
  public void setColor(float[] paramArrayOfFloat) {
    if (!Config.isUSE_RENDER()) return;
    setColor(this.cppObj, paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
  }
  public void setColor(Color3f paramColor3f) {
    if (!Config.isUSE_RENDER()) return;
    setColor(this.cppObj, paramColor3f.jdField_x_of_type_Float, paramColor3f.jdField_y_of_type_Float, paramColor3f.jdField_z_of_type_Float);
  }
  public void getColor(float[] paramArrayOfFloat) {
    if (!Config.isUSE_RENDER()) return;
    getColor(this.cppObj, paramArrayOfFloat);
  }
  public void getColor(Color3f paramColor3f) {
    if (!Config.isUSE_RENDER()) return;
    getColor(this.cppObj, tmpf);
    paramColor3f.jdField_x_of_type_Float = tmpf[0]; paramColor3f.jdField_y_of_type_Float = tmpf[1]; paramColor3f.jdField_z_of_type_Float = tmpf[2];
  }

  public void setEmit(float paramFloat1, float paramFloat2) {
    this.I = paramFloat1; this.R = paramFloat2;
    if (!Config.isUSE_RENDER()) return;
    setEmit(this.cppObj, paramFloat1, paramFloat2);
  }
  public void getEmit(float[] paramArrayOfFloat) {
    paramArrayOfFloat[0] = this.I; paramArrayOfFloat[1] = this.R;
  }
  public float getI() {
    return this.I;
  }
  public float getR() {
    return this.R;
  }
  public boolean isDestroyed() {
    return this.cppObj == 0;
  }
  public void destroy() { if (!isDestroyed())
      Finalize(this.cppObj);
    this.cppObj = 0; }

  public void addToRender()
  {
    if (!Config.isUSE_RENDER()) return;
    if (this.cppObj == 0) {
      return;
    }
    addToRender(this.cppObj); } 
  private static native void setPos(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  private static native void setColor(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  private static native void setEmit(int paramInt, float paramFloat1, float paramFloat2);

  private static native void getPos(int paramInt, float[] paramArrayOfFloat);

  private static native void getColor(int paramInt, float[] paramArrayOfFloat);

  private static native void getEmit(int paramInt, float[] paramArrayOfFloat);

  public static native void setOffset(float paramFloat1, float paramFloat2, float paramFloat3);

  private static native void addToRender(int paramInt);

  public static native void clearRender();

  protected void finalize() { if (this.cppObj != 0) Finalize(this.cppObj); this.cppObj = 0; } 
  private static native void Finalize(int paramInt);

  private static native int New();

  static { GObj.loadNative();
  }
}