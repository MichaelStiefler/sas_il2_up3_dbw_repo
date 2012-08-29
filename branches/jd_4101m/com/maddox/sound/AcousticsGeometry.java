package com.maddox.sound;

public class AcousticsGeometry extends BaseObject
{
  protected int handle;
  protected String name;

  public AcousticsGeometry(String paramString)
  {
    this.name = paramString;
    this.handle = jniCreate();
  }

  public void material(AcousticsMaterial paramAcousticsMaterial)
  {
    jniMaterial(this.handle, paramAcousticsMaterial.handle);
  }

  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    jniVertex(this.handle, paramFloat1, paramFloat2, paramFloat3);
  }

  public void triangle(int paramInt1, int paramInt2, int paramInt3)
  {
    jniTriangle(this.handle, paramInt1, paramInt2, paramInt3);
  }

  public void end()
  {
    jniEnd(this.handle);
  }

  public void enable(boolean paramBoolean)
  {
    jniEnable(this.handle, paramBoolean);
  }

  protected static native int jniCreate();

  protected static native void jniMaterial(int paramInt1, int paramInt2);

  protected static native void jniVertex(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void jniTriangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  protected static native void jniEnd(int paramInt);

  protected static native void jniEnable(int paramInt, boolean paramBoolean);
}