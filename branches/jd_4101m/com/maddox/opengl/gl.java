package com.maddox.opengl;

import java.util.StringTokenizer;

public final class gl
{
  private static boolean libLoaded = false;

  public static final native void Accum(int paramInt, float paramFloat);

  public static final native void AlphaFunc(int paramInt, float paramFloat);

  public static final native boolean AreTexturesResident(int paramInt, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean);

  public static final native void ArrayElement(int paramInt);

  public static final native void Begin(int paramInt);

  public static final native void BindTexture(int paramInt1, int paramInt2);

  public static final native void Bitmap(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, byte[] paramArrayOfByte);

  public static final native void BlendFunc(int paramInt1, int paramInt2);

  public static final native void CallList(int paramInt);

  public static final native void CallLists(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public static final native void CallLists(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  public static final native void CallLists(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void Clear(int paramInt);

  public static final native void ClearAccum(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void ClearColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void ClearDepth(double paramDouble);

  public static final native void ClearIndex(float paramFloat);

  public static final native void ClearStencil(int paramInt);

  public static final native void ClipPlane(int paramInt, double[] paramArrayOfDouble);

  public static final native void Color3b(byte paramByte1, byte paramByte2, byte paramByte3);

  public static final native void Color3bv(byte[] paramArrayOfByte);

  public static final native void Color3d(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Color(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Color3dv(double[] paramArrayOfDouble);

  public static final native void Color3f(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Color(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Color3fv(float[] paramArrayOfFloat);

  public static final native void Color3i(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Color3iv(int[] paramArrayOfInt);

  public static final native void Color3s(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Color3sv(short[] paramArrayOfShort);

  public static final native void Color3ub(byte paramByte1, byte paramByte2, byte paramByte3);

  public static final native void Color(byte paramByte1, byte paramByte2, byte paramByte3);

  public static final native void Color3ubv(byte[] paramArrayOfByte);

  public static final native void Color3ui(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Color(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Color3uiv(int[] paramArrayOfInt);

  public static final native void Color3us(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Color(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Color3usv(short[] paramArrayOfShort);

  public static final native void Color4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);

  public static final native void Color4bv(byte[] paramArrayOfByte);

  public static final native void Color4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Color(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Color4dv(double[] paramArrayOfDouble);

  public static final native void Color4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Color4fv(float[] paramArrayOfFloat);

  public static final native void Color4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Color4iv(int[] paramArrayOfInt);

  public static final native void Color4s(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Color4sv(short[] paramArrayOfShort);

  public static final native void Color4ub(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);

  public static final native void Color(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);

  public static final native void Color4ubv(byte[] paramArrayOfByte);

  public static final native void Color4ui(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Color(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Color4uiv(int[] paramArrayOfInt);

  public static final native void Color4us(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Color(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Color4usv(short[] paramArrayOfShort);

  public static final native void Color(byte[] paramArrayOfByte);

  public static final native void Color(short[] paramArrayOfShort);

  public static final native void Color(int[] paramArrayOfInt);

  public static final native void Color(float[] paramArrayOfFloat);

  public static final native void Color(double[] paramArrayOfDouble);

  public static final native void ColorMask(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);

  public static final native void ColorMaterial(int paramInt1, int paramInt2);

  public static final native void ColorPointer(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native void ColorPointer(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native void ColorPointer(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void ColorPointer(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void ColorPointer(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  public static final native void CopyPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native void CopyTexImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);

  public static final native void CopyTexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);

  public static final native void CopyTexSubImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native void CopyTexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);

  public static final native void CullFace(int paramInt);

  public static final native void DeleteLists(int paramInt1, int paramInt2);

  public static final native void DeleteTextures(int paramInt, int[] paramArrayOfInt);

  public static final native void DepthFunc(int paramInt);

  public static final native void DepthMask(boolean paramBoolean);

  public static final native void DepthRange(double paramDouble1, double paramDouble2);

  public static final native void Disable(int paramInt);

  public static final native void DisableClientState(int paramInt);

  public static final native void DrawArrays(int paramInt1, int paramInt2, int paramInt3);

  public static final native void DrawBuffer(int paramInt);

  public static final native void DrawElements(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native void DrawElements(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native void DrawElements(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void DrawPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  public static final native void DrawPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfShort);

  public static final native void DrawPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  public static final native void DrawPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOfFloat);

  public static final native void EdgeFlag(boolean paramBoolean);

  public static final native void EdgeFlagPointer(int paramInt, boolean[] paramArrayOfBoolean);

  public static final native void EdgeFlagv(boolean[] paramArrayOfBoolean);

  public static final native void Enable(int paramInt);

  public static final native void EnableClientState(int paramInt);

  public static final native void End();

  public static final native void EndList();

  public static final native void EvalCoord1d(double paramDouble);

  public static final native void EvalCoord(double paramDouble);

  public static final native void EvalCoord1dv(double[] paramArrayOfDouble);

  public static final native void EvalCoord1f(float paramFloat);

  public static final native void EvalCoord(float paramFloat);

  public static final native void EvalCoord1fv(float[] paramArrayOfFloat);

  public static final native void EvalCoord2d(double paramDouble1, double paramDouble2);

  public static final native void EvalCoord(double paramDouble1, double paramDouble2);

  public static final native void EvalCoord2dv(double[] paramArrayOfDouble);

  public static final native void EvalCoord2f(float paramFloat1, float paramFloat2);

  public static final native void EvalCoord(float paramFloat1, float paramFloat2);

  public static final native void EvalCoord2fv(float[] paramArrayOfFloat);

  public static final native void EvalCoord(double[] paramArrayOfDouble);

  public static final native void EvalCoord(float[] paramArrayOfFloat);

  public static final native void EvalMesh1(int paramInt1, int paramInt2, int paramInt3);

  public static final native void EvalMesh2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native void EvalPoint1(int paramInt);

  public static final native void EvalPoint2(int paramInt1, int paramInt2);

  public static final native void FeedbackBuffer(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void Finish();

  public static final native void Flush();

  public static final native void Fogf(int paramInt, float paramFloat);

  public static final native void Fog(int paramInt, float paramFloat);

  public static final native void Fogfv(int paramInt, float[] paramArrayOfFloat);

  public static final native void Fog(int paramInt, float[] paramArrayOfFloat);

  public static final native void Fogi(int paramInt1, int paramInt2);

  public static final native void Fog(int paramInt1, int paramInt2);

  public static final native void Fogiv(int paramInt, int[] paramArrayOfInt);

  public static final native void Fog(int paramInt, int[] paramArrayOfInt);

  public static final native void FrontFace(int paramInt);

  public static final native void Frustum(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);

  public static final native int GenLists(int paramInt);

  public static final native void GenTextures(int paramInt, int[] paramArrayOfInt);

  public static final native void GetBooleanv(int paramInt, boolean[] paramArrayOfBoolean);

  public static final native void GetClipPlane(int paramInt, double[] paramArrayOfDouble);

  public static final native void GetDoublev(int paramInt, double[] paramArrayOfDouble);

  public static final native int GetError();

  public static final native void GetFloatv(int paramInt, float[] paramArrayOfFloat);

  public static final native void GetIntegerv(int paramInt, int[] paramArrayOfInt);

  public static final native void GetLightfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetLight(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetLightiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetLight(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetMapdv(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void GetMap(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void GetMapfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetMap(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetMapiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetMap(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetMaterialfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetMaterial(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetMaterialiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetMaterial(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetPixelMapfv(int paramInt, float[] paramArrayOfFloat);

  public static final native void GetPixelMap(int paramInt, float[] paramArrayOfFloat);

  public static final native void GetPixelMapuiv(int paramInt, int[] paramArrayOfInt);

  public static final native void GetPixelMap(int paramInt, int[] paramArrayOfInt);

  public static final native void GetPixelMapusv(int paramInt, short[] paramArrayOfShort);

  public static final native void GetPixelMap(int paramInt, short[] paramArrayOfShort);

  public static final native void GetPolygonStipple(byte[] paramArrayOfByte);

  public static final native String GetString(int paramInt);

  public static final native void GetTexEnvfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexEnv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexEnviv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetTexEnv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetTexGendv(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void GetTexGen(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void GetTexGenfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexGen(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexGeniv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetTexGen(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetTexImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  public static final native void GetTexImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfShort);

  public static final native void GetTexImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  public static final native void GetTexImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOfFloat);

  public static final native void GetTexLevelParameterfv(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void GetTexLevelParameter(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void GetTexLevelParameteriv(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void GetTexLevelParameter(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void GetTexParameterfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexParameter(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void GetTexParameteriv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void GetTexParameter(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void Hint(int paramInt1, int paramInt2);

  public static final native void IndexMask(int paramInt);

  public static final native void IndexPointer(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public static final native void IndexPointer(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  public static final native void IndexPointer(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void IndexPointer(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void IndexPointer(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void Indexd(double paramDouble);

  public static final native void Index(double paramDouble);

  public static final native void Indexdv(double[] paramArrayOfDouble);

  public static final native void Index(double[] paramArrayOfDouble);

  public static final native void Indexf(float paramFloat);

  public static final native void Index(float paramFloat);

  public static final native void Indexfv(float[] paramArrayOfFloat);

  public static final native void Index(float[] paramArrayOfFloat);

  public static final native void Indexi(int paramInt);

  public static final native void Index(int paramInt);

  public static final native void Indexiv(int[] paramArrayOfInt);

  public static final native void Index(int[] paramArrayOfInt);

  public static final native void Indexs(short paramShort);

  public static final native void Index(short paramShort);

  public static final native void Indexsv(short[] paramArrayOfShort);

  public static final native void Index(short[] paramArrayOfShort);

  public static final native void Indexub(byte paramByte);

  public static final native void Index(byte paramByte);

  public static final native void Indexubv(byte[] paramArrayOfByte);

  public static final native void Index(byte[] paramArrayOfByte);

  public static final native void InitNames();

  public static final native void InterleavedArrays(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native boolean IsEnabled(int paramInt);

  public static final native boolean IsList(int paramInt);

  public static final native boolean IsTexture(int paramInt);

  public static final native void LightModelf(int paramInt, float paramFloat);

  public static final native void LightMode(int paramInt, float paramFloat);

  public static final native void LightModelfv(int paramInt, float[] paramArrayOfFloat);

  public static final native void LightModel(int paramInt, float[] paramArrayOfFloat);

  public static final native void LightModeli(int paramInt1, int paramInt2);

  public static final native void LightModel(int paramInt1, int paramInt2);

  public static final native void LightModeliv(int paramInt, int[] paramArrayOfInt);

  public static final native void LightModel(int paramInt, int[] paramArrayOfInt);

  public static final native void Lightf(int paramInt1, int paramInt2, float paramFloat);

  public static final native void Light(int paramInt1, int paramInt2, float paramFloat);

  public static final native void Lightfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void Light(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void Lighti(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Light(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Lightiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void Light(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void LineStipple(int paramInt, short paramShort);

  public static final native void LineWidth(float paramFloat);

  public static final native void ListBase(int paramInt);

  public static final native void LoadIdentity();

  public static final native void LoadMatrixd(double[] paramArrayOfDouble);

  public static final native void LoadMatrix(double[] paramArrayOfDouble);

  public static final native void LoadMatrixf(float[] paramArrayOfFloat);

  public static final native void LoadMatrix(float[] paramArrayOfFloat);

  public static final native void LoadName(int paramInt);

  public static final native void LogicOp(int paramInt);

  public static final native void Map1d(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  public static final native void Map1(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  public static final native void Map1f(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void Map1(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void Map2d(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double paramDouble3, double paramDouble4, int paramInt4, int paramInt5, double[] paramArrayOfDouble);

  public static final native void Map2(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, double paramDouble3, double paramDouble4, int paramInt4, int paramInt5, double[] paramArrayOfDouble);

  public static final native void Map2f(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float paramFloat3, float paramFloat4, int paramInt4, int paramInt5, float[] paramArrayOfFloat);

  public static final native void Map2(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3, float paramFloat3, float paramFloat4, int paramInt4, int paramInt5, float[] paramArrayOfFloat);

  public static final native void MapGrid1d(int paramInt, double paramDouble1, double paramDouble2);

  public static final native void MapGrid1(int paramInt, double paramDouble1, double paramDouble2);

  public static final native void MapGrid1f(int paramInt, float paramFloat1, float paramFloat2);

  public static final native void MapGrid1(int paramInt, float paramFloat1, float paramFloat2);

  public static final native void MapGrid2d(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3, double paramDouble4);

  public static final native void MapGrid2(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3, double paramDouble4);

  public static final native void MapGrid2f(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3, float paramFloat4);

  public static final native void MapGrid2(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3, float paramFloat4);

  public static final native void Materialf(int paramInt1, int paramInt2, float paramFloat);

  public static final native void Material(int paramInt1, int paramInt2, float paramFloat);

  public static final native void Materialfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void Material(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void Materiali(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Material(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Materialiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void Material(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void MatrixMode(int paramInt);

  public static final native void MultMatrixd(double[] paramArrayOfDouble);

  public static final native void MultMatrix(double[] paramArrayOfDouble);

  public static final native void MultMatrixf(float[] paramArrayOfFloat);

  public static final native void MultMatrix(float[] paramArrayOfFloat);

  public static final native void NewList(int paramInt1, int paramInt2);

  public static final native void Normal3b(byte paramByte1, byte paramByte2, byte paramByte3);

  public static final native void Normal3(byte paramByte1, byte paramByte2, byte paramByte3);

  public static final native void Normal3bv(byte[] paramArrayOfByte);

  public static final native void Normal3(byte[] paramArrayOfByte);

  public static final native void Normal3d(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Normal3(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Normal3dv(double[] paramArrayOfDouble);

  public static final native void Normal3(double[] paramArrayOfDouble);

  public static final native void Normal3f(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Normal3(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Normal3fv(float[] paramArrayOfFloat);

  public static final native void Normal3(float[] paramArrayOfFloat);

  public static final native void Normal3i(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Normal3(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Normal3iv(int[] paramArrayOfInt);

  public static final native void Normal3(int[] paramArrayOfInt);

  public static final native void Normal3s(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Normal3(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Normal3sv(short[] paramArrayOfShort);

  public static final native void Normal3(short[] paramArrayOfShort);

  public static final native void NormalPointer(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public static final native void NormalPointer(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  public static final native void NormalPointer(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void NormalPointer(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void NormalPointer(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void Ortho(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);

  public static final native void PassThrough(float paramFloat);

  public static final native void PixelMapfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void PixelMap(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void PixelMapuiv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void PixelMap(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void PixelMapusv(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  public static final native void PixelMap(int paramInt1, int paramInt2, short[] paramArrayOfShort);

  public static final native void PixelStoref(int paramInt, float paramFloat);

  public static final native void PixelStore(int paramInt, float paramFloat);

  public static final native void PixelStorei(int paramInt1, int paramInt2);

  public static final native void PixelStore(int paramInt1, int paramInt2);

  public static final native void PixelTransferf(int paramInt, float paramFloat);

  public static final native void PixelTransfer(int paramInt, float paramFloat);

  public static final native void PixelTransferi(int paramInt1, int paramInt2);

  public static final native void PixelTransfer(int paramInt1, int paramInt2);

  public static final native void PixelZoom(float paramFloat1, float paramFloat2);

  public static final native void PointSize(float paramFloat);

  public static final native void PolygonMode(int paramInt1, int paramInt2);

  public static final native void PolygonOffset(float paramFloat1, float paramFloat2);

  public static final native void PolygonStipple(byte[] paramArrayOfByte);

  public static final native void PopAttrib();

  public static final native void PopClientAttrib();

  public static final native void PopMatrix();

  public static final native void PopName();

  public static final native void PrioritizeTextures(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat);

  public static final native void PushAttrib(int paramInt);

  public static final native void PushClientAttrib(int paramInt);

  public static final native void PushMatrix();

  public static final native void PushName(int paramInt);

  public static final native void RasterPos2d(double paramDouble1, double paramDouble2);

  public static final native void RasterPos(double paramDouble1, double paramDouble2);

  public static final native void RasterPos2dv(double[] paramArrayOfDouble);

  public static final native void RasterPos2f(float paramFloat1, float paramFloat2);

  public static final native void RasterPos(float paramFloat1, float paramFloat2);

  public static final native void RasterPos2fv(float[] paramArrayOfFloat);

  public static final native void RasterPos2i(int paramInt1, int paramInt2);

  public static final native void RasterPos(int paramInt1, int paramInt2);

  public static final native void RasterPos2iv(int[] paramArrayOfInt);

  public static final native void RasterPos2s(short paramShort1, short paramShort2);

  public static final native void RasterPos(short paramShort1, short paramShort2);

  public static final native void RasterPos2sv(short[] paramArrayOfShort);

  public static final native void RasterPos3d(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void RasterPos(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void RasterPos3dv(double[] paramArrayOfDouble);

  public static final native void RasterPos3f(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void RasterPos(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void RasterPos3fv(float[] paramArrayOfFloat);

  public static final native void RasterPos3i(int paramInt1, int paramInt2, int paramInt3);

  public static final native void RasterPos(int paramInt1, int paramInt2, int paramInt3);

  public static final native void RasterPos3iv(int[] paramArrayOfInt);

  public static final native void RasterPos3s(short paramShort1, short paramShort2, short paramShort3);

  public static final native void RasterPos(short paramShort1, short paramShort2, short paramShort3);

  public static final native void RasterPos3sv(short[] paramArrayOfShort);

  public static final native void RasterPos4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void RasterPos(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void RasterPos4dv(double[] paramArrayOfDouble);

  public static final native void RasterPos4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void RasterPos(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void RasterPos4fv(float[] paramArrayOfFloat);

  public static final native void RasterPos4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void RasterPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void RasterPos4iv(int[] paramArrayOfInt);

  public static final native void RasterPos4s(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void RasterPos(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void RasterPos4sv(short[] paramArrayOfShort);

  public static final native void RasterPos(double[] paramArrayOfDouble);

  public static final native void RasterPos(float[] paramArrayOfFloat);

  public static final native void RasterPos(int[] paramArrayOfInt);

  public static final native void RasterPos(short[] paramArrayOfShort);

  public static final native void ReadBuffer(int paramInt);

  public static final native void ReadPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfByte);

  public static final native void ReadPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short[] paramArrayOfShort);

  public static final native void ReadPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt);

  public static final native void ReadPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOfFloat);

  public static final native void Rectd(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Rect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Rectdv(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  public static final native void Rect(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  public static final native void Rectf(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Rectfv(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);

  public static final native void Rect(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);

  public static final native void Recti(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Rect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Rectiv(int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native void Rect(int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native void Rects(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Rect(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Rectsv(short[] paramArrayOfShort1, short[] paramArrayOfShort2);

  public static final native void Rect(short[] paramArrayOfShort1, short[] paramArrayOfShort2);

  public static final native int RenderMode(int paramInt);

  public static final native void Rotated(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Rotatef(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Scaled(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Scale(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Scalef(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Scale(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Scissor(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void SelectBuffer(int paramInt, int[] paramArrayOfInt);

  public static final native void ShadeModel(int paramInt);

  public static final native void StencilFunc(int paramInt1, int paramInt2, int paramInt3);

  public static final native void StencilMask(int paramInt);

  public static final native void StencilOp(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexCoord1d(double paramDouble);

  public static final native void TexCoord(double paramDouble);

  public static final native void TexCoord1dv(double[] paramArrayOfDouble);

  public static final native void TexCoord1f(float paramFloat);

  public static final native void TexCoord(float paramFloat);

  public static final native void TexCoord1fv(float[] paramArrayOfFloat);

  public static final native void TexCoord1i(int paramInt);

  public static final native void TexCoord(int paramInt);

  public static final native void TexCoord1iv(int[] paramArrayOfInt);

  public static final native void TexCoord1s(short paramShort);

  public static final native void TexCoord(short paramShort);

  public static final native void TexCoord1sv(short[] paramArrayOfShort);

  public static final native void TexCoord2d(double paramDouble1, double paramDouble2);

  public static final native void TexCoord(double paramDouble1, double paramDouble2);

  public static final native void TexCoord2dv(double[] paramArrayOfDouble);

  public static final native void TexCoord2f(float paramFloat1, float paramFloat2);

  public static final native void TexCoord(float paramFloat1, float paramFloat2);

  public static final native void TexCoord2fv(float[] paramArrayOfFloat);

  public static final native void TexCoord2i(int paramInt1, int paramInt2);

  public static final native void TexCoord(int paramInt1, int paramInt2);

  public static final native void TexCoord2iv(int[] paramArrayOfInt);

  public static final native void TexCoord2s(short paramShort1, short paramShort2);

  public static final native void TexCoord(short paramShort1, short paramShort2);

  public static final native void TexCoord2sv(short[] paramArrayOfShort);

  public static final native void TexCoord3d(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void TexCoord(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void TexCoord3dv(double[] paramArrayOfDouble);

  public static final native void TexCoord3f(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void TexCoord(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void TexCoord3fv(float[] paramArrayOfFloat);

  public static final native void TexCoord3i(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexCoord(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexCoord3iv(int[] paramArrayOfInt);

  public static final native void TexCoord3s(short paramShort1, short paramShort2, short paramShort3);

  public static final native void TexCoord(short paramShort1, short paramShort2, short paramShort3);

  public static final native void TexCoord3sv(short[] paramArrayOfShort);

  public static final native void TexCoord4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void TexCoord(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void TexCoord4dv(double[] paramArrayOfDouble);

  public static final native void TexCoord4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void TexCoord(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void TexCoord4fv(float[] paramArrayOfFloat);

  public static final native void TexCoord4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void TexCoord(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void TexCoord4iv(int[] paramArrayOfInt);

  public static final native void TexCoord4s(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void TexCoord(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void TexCoord4sv(short[] paramArrayOfShort);

  public static final native void TexCoord(double[] paramArrayOfDouble);

  public static final native void TexCoord(float[] paramArrayOfFloat);

  public static final native void TexCoord(int[] paramArrayOfInt);

  public static final native void TexCoord(short[] paramArrayOfShort);

  public static final native void TexCoordPointer(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native void TexCoordPointer(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void TexCoordPointer(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void TexCoordPointer(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  public static final native void TexEnvf(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexEnv(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexEnvfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexEnv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexEnvi(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexEnv(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexEnviv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexEnv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexGend(int paramInt1, int paramInt2, double paramDouble);

  public static final native void TexGen(int paramInt1, int paramInt2, double paramDouble);

  public static final native void TexGendv(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void TexGen(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  public static final native void TexGenf(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexGen(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexGenfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexGen(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexGeni(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexGen(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexGeniv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexGen(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfByte);

  public static final native void TexImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short[] paramArrayOfShort);

  public static final native void TexImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfInt);

  public static final native void TexImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float[] paramArrayOfFloat);

  public static final native void TexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfByte);

  public static final native void TexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, short[] paramArrayOfShort);

  public static final native void TexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int[] paramArrayOfInt);

  public static final native void TexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float[] paramArrayOfFloat);

  public static final native void TexParameterf(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexParameter(int paramInt1, int paramInt2, float paramFloat);

  public static final native void TexParameterfv(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexParameter(int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static final native void TexParameteri(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexParameter(int paramInt1, int paramInt2, int paramInt3);

  public static final native void TexParameteriv(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexParameter(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native void TexSubImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfByte);

  public static final native void TexSubImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short[] paramArrayOfShort);

  public static final native void TexSubImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt);

  public static final native void TexSubImage1D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOfFloat);

  public static final native void TexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfByte);

  public static final native void TexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, short[] paramArrayOfShort);

  public static final native void TexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int[] paramArrayOfInt);

  public static final native void TexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float[] paramArrayOfFloat);

  public static final native void Translated(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Translate(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Translatef(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Translate(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Vertex2d(double paramDouble1, double paramDouble2);

  public static final native void Vertex(double paramDouble1, double paramDouble2);

  public static final native void Vertex2dv(double[] paramArrayOfDouble);

  public static final native void Vertex2f(float paramFloat1, float paramFloat2);

  public static final native void Vertex(float paramFloat1, float paramFloat2);

  public static final native void Vertex2fv(float[] paramArrayOfFloat);

  public static final native void Vertex2i(int paramInt1, int paramInt2);

  public static final native void Vertex(int paramInt1, int paramInt2);

  public static final native void Vertex2iv(int[] paramArrayOfInt);

  public static final native void Vertex2s(short paramShort1, short paramShort2);

  public static final native void Vertex(short paramShort1, short paramShort2);

  public static final native void Vertex2sv(short[] paramArrayOfShort);

  public static final native void Vertex3d(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Vertex(double paramDouble1, double paramDouble2, double paramDouble3);

  public static final native void Vertex3dv(double[] paramArrayOfDouble);

  public static final native void Vertex3f(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Vertex(float paramFloat1, float paramFloat2, float paramFloat3);

  public static final native void Vertex3fv(float[] paramArrayOfFloat);

  public static final native void Vertex3i(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Vertex(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Vertex3iv(int[] paramArrayOfInt);

  public static final native void Vertex3s(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Vertex(short paramShort1, short paramShort2, short paramShort3);

  public static final native void Vertex3sv(short[] paramArrayOfShort);

  public static final native void Vertex4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Vertex(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public static final native void Vertex4dv(double[] paramArrayOfDouble);

  public static final native void Vertex4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void Vertex4fv(float[] paramArrayOfFloat);

  public static final native void Vertex4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Vertex(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native void Vertex4iv(int[] paramArrayOfInt);

  public static final native void Vertex4s(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Vertex(short paramShort1, short paramShort2, short paramShort3, short paramShort4);

  public static final native void Vertex4sv(short[] paramArrayOfShort);

  public static final native void Vertex(double[] paramArrayOfDouble);

  public static final native void Vertex(float[] paramArrayOfFloat);

  public static final native void Vertex(int[] paramArrayOfInt);

  public static final native void Vertex(short[] paramArrayOfShort);

  public static final native void VertexPointer(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native void VertexPointer(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native void VertexPointer(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat);

  public static final native void VertexPointer(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  public static final native void Viewport(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final boolean extExist(String paramString)
  {
    String str1 = GetString(7939);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, " ");
    while (localStringTokenizer.hasMoreTokens()) {
      String str2 = localStringTokenizer.nextToken();
      if (str2.equals(paramString))
        return true;
    }
    return false;
  }

  public static final void loadNative()
  {
    if (!libLoaded) {
      System.loadLibrary("jgl");
      libLoaded = true;
    }
  }

  static {
    loadNative();
  }
}