package com.maddox.opengl;

public class GLUQuadric
{
  private int pData;

  public GLUQuadric()
  {
    this.pData = 0; New(); } 
  public native void Delete();

  public native void DrawStyle(int paramInt);

  public native void Normals(int paramInt);

  public native void Orientation(int paramInt);

  public native void Texture(boolean paramBoolean);

  public native void Sphere(double paramDouble, int paramInt1, int paramInt2);

  public native void Cylinder(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2);

  public native void Disk(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);

  public native void PartialDisk(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4);

  private native void New();

  static { gl.loadNative();
  }
}