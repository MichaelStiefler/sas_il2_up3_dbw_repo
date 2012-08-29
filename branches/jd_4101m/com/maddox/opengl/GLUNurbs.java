package com.maddox.opengl;

public class GLUNurbs
{
  private int pData;

  public GLUNurbs()
  {
    this.pData = 0; New(); } 
  public native void Delete();

  public native void Property(int paramInt, float paramFloat);

  public native void GetProperty(int paramInt, float[] paramArrayOfFloat);

  public native void LoadSamplingMatrices(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int[] paramArrayOfInt);

  public native void BeginSurface();

  public native void EndSurface();

  public native void Surface(int paramInt1, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4, float[] paramArrayOfFloat3, int paramInt5, int paramInt6, int paramInt7);

  public native void BeginCurve();

  public native void EndCurve();

  public native void Curve(int paramInt1, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4);

  public native void BeginTrim();

  public native void EndTrim();

  public native void PwlCurve(int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3);

  private native void New();

  static { gl.loadNative();
  }
}