package com.maddox.opengl;

public final class glu
{
  public static final native void Ortho2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Perspective(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void LookAt(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9);

  public static final native int Project(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int[] paramArrayOfInt, double[] paramArrayOfDouble3, double[] paramArrayOfDouble4, double[] paramArrayOfDouble5);

  public static final native int UnProject(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int[] paramArrayOfInt, double[] paramArrayOfDouble3, double[] paramArrayOfDouble4, double[] paramArrayOfDouble5);

  public static final native int ScaleImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte1, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfByte2);

  public static final native void PickMatrix(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int[] paramArrayOfInt);

  public static final native int Build1DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte);

  public static final native int Build1DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, short[] paramArrayOfShort);

  public static final native int Build1DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static final native int Build1DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOfFloat);

  public static final native int Build2DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfByte);

  public static final native int Build2DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short[] paramArrayOfShort);

  public static final native int Build2DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt);

  public static final native int Build2DMipmaps(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOfFloat);

  public static final native String ErrorString(int paramInt);

  public static final native String GetString(int paramInt);

  public static final GLUNurbs NewNurbsRenderer()
  {
    return new GLUNurbs();
  }
  public static final void DeleteNurbsRenderer(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.Delete();
  }
  public static final void NurbsProperty(GLUNurbs paramGLUNurbs, int paramInt, float paramFloat) {
    paramGLUNurbs.Property(paramInt, paramFloat);
  }
  public static final void GetNurbsProperty(GLUNurbs paramGLUNurbs, int paramInt, float[] paramArrayOfFloat) {
    paramGLUNurbs.GetProperty(paramInt, paramArrayOfFloat);
  }
  public static final void LoadSamplingMatrices(GLUNurbs paramGLUNurbs, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int[] paramArrayOfInt) {
    paramGLUNurbs.LoadSamplingMatrices(paramArrayOfFloat1, paramArrayOfFloat2, paramArrayOfInt);
  }
  public static final void BeginSurface(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.BeginSurface();
  }
  public static final void EndSurface(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.EndSurface();
  }
  public static final void NurbsSurface(GLUNurbs paramGLUNurbs, int paramInt1, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4, float[] paramArrayOfFloat3, int paramInt5, int paramInt6, int paramInt7) {
    paramGLUNurbs.Surface(paramInt1, paramArrayOfFloat1, paramInt2, paramArrayOfFloat2, paramInt3, paramInt4, paramArrayOfFloat3, paramInt5, paramInt6, paramInt7);
  }
  public static final void BeginCurve(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.BeginCurve();
  }
  public static final void EndCurve(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.EndCurve();
  }
  public static final void NurbsCurve(GLUNurbs paramGLUNurbs, int paramInt1, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3, int paramInt4) {
    paramGLUNurbs.Curve(paramInt1, paramArrayOfFloat1, paramInt2, paramArrayOfFloat2, paramInt3, paramInt4);
  }
  public static final void BeginTrim(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.BeginTrim();
  }
  public static final void EndTrim(GLUNurbs paramGLUNurbs) {
    paramGLUNurbs.EndTrim();
  }
  public static final void PwlCurve(GLUNurbs paramGLUNurbs, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3) {
    paramGLUNurbs.PwlCurve(paramInt1, paramArrayOfFloat, paramInt2, paramInt3);
  }

  public static final GLUQuadric NewQuadric()
  {
    return new GLUQuadric();
  }
  public static final void DeleteQuadric(GLUQuadric paramGLUQuadric) {
    paramGLUQuadric.Delete();
  }
  public static final void QuadricDrawStyle(GLUQuadric paramGLUQuadric, int paramInt) {
    paramGLUQuadric.DrawStyle(paramInt);
  }
  public static final void QuadricNormals(GLUQuadric paramGLUQuadric, int paramInt) {
    paramGLUQuadric.Normals(paramInt);
  }
  public static final void QuadricOrientation(GLUQuadric paramGLUQuadric, int paramInt) {
    paramGLUQuadric.Orientation(paramInt);
  }
  public static final void QuadricTexture(GLUQuadric paramGLUQuadric, boolean paramBoolean) {
    paramGLUQuadric.Texture(paramBoolean);
  }
  public static final void Sphere(GLUQuadric paramGLUQuadric, double paramDouble, int paramInt1, int paramInt2) {
    paramGLUQuadric.Sphere(paramDouble, paramInt1, paramInt2);
  }
  public static final void Cylinder(GLUQuadric paramGLUQuadric, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2) {
    paramGLUQuadric.Cylinder(paramDouble1, paramDouble2, paramDouble3, paramInt1, paramInt2);
  }
  public static final void Disk(GLUQuadric paramGLUQuadric, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2) {
    paramGLUQuadric.Disk(paramDouble1, paramDouble2, paramInt1, paramInt2);
  }
  public static final void PartialDisk(GLUQuadric paramGLUQuadric, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4) {
    paramGLUQuadric.PartialDisk(paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4);
  }

  public static final GLUTesselator NewTess()
  {
    return new GLUTesselator();
  }
  public static final void DeleteTess(GLUTesselator paramGLUTesselator) {
    paramGLUTesselator.Delete();
  }
  public static final void TessVertex(GLUTesselator paramGLUTesselator, double[] paramArrayOfDouble, float[] paramArrayOfFloat) {
    paramGLUTesselator.Vertex(paramArrayOfDouble, paramArrayOfFloat);
  }
  public static final void TessVertex(GLUTesselator paramGLUTesselator, double[] paramArrayOfDouble, int[] paramArrayOfInt) {
    paramGLUTesselator.Vertex(paramArrayOfDouble, paramArrayOfInt);
  }
  public static final void TessVertex(GLUTesselator paramGLUTesselator, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2) {
    paramGLUTesselator.Vertex(paramArrayOfDouble1, paramArrayOfDouble2);
  }
  public static final void BeginPolygon(GLUTesselator paramGLUTesselator) {
    paramGLUTesselator.BeginPolygon();
  }
  public static final void EndPolygon(GLUTesselator paramGLUTesselator) {
    paramGLUTesselator.EndPolygon();
  }
  public static final void NextContour(GLUTesselator paramGLUTesselator, int paramInt) {
    paramGLUTesselator.NextContour(paramInt);
  }

  static
  {
    gl.loadNative();
  }
}