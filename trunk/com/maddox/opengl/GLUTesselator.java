package com.maddox.opengl;

public class GLUTesselator
{
  private int pData;

  public GLUTesselator()
  {
    this.pData = 0; New(); } 
  public native void Delete();

  public native void Vertex(double[] paramArrayOfDouble, float[] paramArrayOfFloat);

  public native void Vertex(double[] paramArrayOfDouble, int[] paramArrayOfInt);

  public native void Vertex(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  public native void BeginPolygon();

  public native void EndPolygon();

  public native void NextContour(int paramInt);

  private native void New();

  static { gl.loadNative();
  }
}