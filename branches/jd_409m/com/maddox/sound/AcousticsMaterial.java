package com.maddox.sound;

public class AcousticsMaterial extends BaseObject
{
  protected String name;
  protected int handle;

  public AcousticsMaterial(String paramString)
  {
    this.name = paramString;
    this.handle = jniCreate();
  }

  public void setTransmittence(float paramFloat1, float paramFloat2)
  {
    jniTransmittence(this.handle, paramFloat1, paramFloat2);
  }

  public void setReflectance(float paramFloat1, float paramFloat2)
  {
    jniReflectance(this.handle, paramFloat1, paramFloat2);
  }

  protected static native int jniCreate();

  protected static native void jniTransmittence(int paramInt, float paramFloat1, float paramFloat2);

  protected static native void jniReflectance(int paramInt, float paramFloat1, float paramFloat2);
}