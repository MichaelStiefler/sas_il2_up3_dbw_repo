package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;
import com.maddox.sound.SoundFX;

public class EffClouds extends GObj
  implements Destroy
{
  private boolean bShow = true;
  private int type;
  private float height;
  protected SoundFX sound = null;
  protected float vmax = 1.0F;

  private static float[] farr3 = new float[3];

  public boolean isShow()
  {
    return this.bShow; } 
  public void setShow(boolean paramBoolean) { this.bShow = paramBoolean; } 
  public int type() {
    return this.type; } 
  public float height() { return this.height;
  }

  public void soundUpdate(double paramDouble)
  {
    if (this.sound != null) {
      float f1 = (float)paramDouble; float f2 = 1.0F; float f3 = 200.0F;
      if (f1 > this.height) {
        f1 -= this.height;
        if (f1 < f3) f2 = 1.0F - f1 / f3; else
          f2 = 0.0F;
      }
      this.sound.setVolume(f2 * this.vmax);
    }
  }

  protected void setRainSound(int paramInt)
  {
    int i = Engine.land().config.month;
    int j = (i <= 10) && (i >= 4) && (paramInt >= 5) ? 1 : 0;
    if (j != 0) {
      if (this.sound == null) this.sound = new SoundFX("objects.rain");
      if (this.sound != null) {
        this.sound.setUsrFlag(paramInt - 5);
        this.sound.play();
      }
    }
    else if (this.sound != null) {
      this.sound.clear();
      this.sound.cancel();
      this.sound = null;
    }
  }

  public void setType(int paramInt)
  {
    if (this.jdField_cppObj_of_type_Int == 0) {
      setRainSound(0);
      return;
    }
    this.type = paramInt;
    SetType(this.jdField_cppObj_of_type_Int, paramInt);
    setRainSound(paramInt);
  }

  public void setHeight(float paramFloat) {
    if (this.jdField_cppObj_of_type_Int == 0) return;
    this.height = paramFloat;
    SetHeight(this.jdField_cppObj_of_type_Int, paramFloat);
  }

  public boolean getRandomCloudPos(Point3d paramPoint3d) {
    if (this.jdField_cppObj_of_type_Int == 0) return false;
    boolean bool = GetRandomCloudPos(this.jdField_cppObj_of_type_Int, farr3);
    if (!bool) return false;
    paramPoint3d.jdField_x_of_type_Double = farr3[0]; paramPoint3d.jdField_y_of_type_Double = farr3[1]; paramPoint3d.jdField_z_of_type_Double = farr3[2];
    return true;
  }

  public float getVisibility(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    return GetVisibility(this.jdField_cppObj_of_type_Int, (float)paramPoint3d1.jdField_x_of_type_Double, (float)paramPoint3d1.jdField_y_of_type_Double, (float)paramPoint3d1.jdField_z_of_type_Double, (float)paramPoint3d2.jdField_x_of_type_Double, (float)paramPoint3d2.jdField_y_of_type_Double, (float)paramPoint3d2.jdField_z_of_type_Double);
  }

  public int preRender()
  {
    if ((!this.bShow) || (this.jdField_cppObj_of_type_Int == 0)) return 0;
    return PreRender(this.jdField_cppObj_of_type_Int);
  }

  public void render() {
    if ((!this.bShow) || (this.jdField_cppObj_of_type_Int == 0)) return;
    Render(this.jdField_cppObj_of_type_Int);
  }

  public void destroy() {
    setRainSound(0);
    if (!isDestroyed()) {
      Destroy(this.jdField_cppObj_of_type_Int);
      this.jdField_cppObj_of_type_Int = 0;
    }
  }

  public boolean isDestroyed() {
    return this.jdField_cppObj_of_type_Int == 0;
  }
  public EffClouds(boolean paramBoolean, int paramInt, float paramFloat) {
    super(0);
    this.height = paramFloat;
    this.type = paramInt;
    int i = paramInt;
    if (paramBoolean) i |= 16;
    this.jdField_cppObj_of_type_Int = Load(i, paramFloat);
    if (this.jdField_cppObj_of_type_Int == 0) throw new GObjException("EffClouds not created");
    setRainSound(paramInt);
  }

  public EffClouds(int paramInt) {
    super(paramInt); } 
  private native int Load(int paramInt, float paramFloat);

  private native void Destroy(int paramInt);

  private native void SetType(int paramInt1, int paramInt2);

  private native void SetHeight(int paramInt, float paramFloat);

  private native boolean GetRandomCloudPos(int paramInt, float[] paramArrayOfFloat);

  private native float GetVisibility(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  private native int PreRender(int paramInt);

  private native void Render(int paramInt);

  static { GObj.loadNative();
  }
}