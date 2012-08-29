// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   gl.java

package com.maddox.opengl;

import java.util.StringTokenizer;

public final class gl
{

    public gl()
    {
    }

    public static final native void Accum(int i, float f);

    public static final native void AlphaFunc(int i, float f);

    public static final native boolean AreTexturesResident(int i, int ai[], boolean aflag[]);

    public static final native void ArrayElement(int i);

    public static final native void Begin(int i);

    public static final native void BindTexture(int i, int j);

    public static final native void Bitmap(int i, int j, float f, float f1, float f2, float f3, byte abyte0[]);

    public static final native void BlendFunc(int i, int j);

    public static final native void CallList(int i);

    public static final native void CallLists(int i, int j, byte abyte0[]);

    public static final native void CallLists(int i, int j, short aword0[]);

    public static final native void CallLists(int i, int j, int ai[]);

    public static final native void Clear(int i);

    public static final native void ClearAccum(float f, float f1, float f2, float f3);

    public static final native void ClearColor(float f, float f1, float f2, float f3);

    public static final native void ClearDepth(double d);

    public static final native void ClearIndex(float f);

    public static final native void ClearStencil(int i);

    public static final native void ClipPlane(int i, double ad[]);

    public static final native void Color3b(byte byte0, byte byte1, byte byte2);

    public static final native void Color3bv(byte abyte0[]);

    public static final native void Color3d(double d, double d1, double d2);

    public static final native void Color(double d, double d1, double d2);

    public static final native void Color3dv(double ad[]);

    public static final native void Color3f(float f, float f1, float f2);

    public static final native void Color(float f, float f1, float f2);

    public static final native void Color3fv(float af[]);

    public static final native void Color3i(int i, int j, int k);

    public static final native void Color3iv(int ai[]);

    public static final native void Color3s(short word0, short word1, short word2);

    public static final native void Color3sv(short aword0[]);

    public static final native void Color3ub(byte byte0, byte byte1, byte byte2);

    public static final native void Color(byte byte0, byte byte1, byte byte2);

    public static final native void Color3ubv(byte abyte0[]);

    public static final native void Color3ui(int i, int j, int k);

    public static final native void Color(int i, int j, int k);

    public static final native void Color3uiv(int ai[]);

    public static final native void Color3us(short word0, short word1, short word2);

    public static final native void Color(short word0, short word1, short word2);

    public static final native void Color3usv(short aword0[]);

    public static final native void Color4b(byte byte0, byte byte1, byte byte2, byte byte3);

    public static final native void Color4bv(byte abyte0[]);

    public static final native void Color4d(double d, double d1, double d2, double d3);

    public static final native void Color(double d, double d1, double d2, double d3);

    public static final native void Color4dv(double ad[]);

    public static final native void Color4f(float f, float f1, float f2, float f3);

    public static final native void Color(float f, float f1, float f2, float f3);

    public static final native void Color4fv(float af[]);

    public static final native void Color4i(int i, int j, int k, int l);

    public static final native void Color4iv(int ai[]);

    public static final native void Color4s(short word0, short word1, short word2, short word3);

    public static final native void Color4sv(short aword0[]);

    public static final native void Color4ub(byte byte0, byte byte1, byte byte2, byte byte3);

    public static final native void Color(byte byte0, byte byte1, byte byte2, byte byte3);

    public static final native void Color4ubv(byte abyte0[]);

    public static final native void Color4ui(int i, int j, int k, int l);

    public static final native void Color(int i, int j, int k, int l);

    public static final native void Color4uiv(int ai[]);

    public static final native void Color4us(short word0, short word1, short word2, short word3);

    public static final native void Color(short word0, short word1, short word2, short word3);

    public static final native void Color4usv(short aword0[]);

    public static final native void Color(byte abyte0[]);

    public static final native void Color(short aword0[]);

    public static final native void Color(int ai[]);

    public static final native void Color(float af[]);

    public static final native void Color(double ad[]);

    public static final native void ColorMask(boolean flag, boolean flag1, boolean flag2, boolean flag3);

    public static final native void ColorMaterial(int i, int j);

    public static final native void ColorPointer(int i, int j, int k, byte abyte0[]);

    public static final native void ColorPointer(int i, int j, int k, short aword0[]);

    public static final native void ColorPointer(int i, int j, int k, int ai[]);

    public static final native void ColorPointer(int i, int j, int k, float af[]);

    public static final native void ColorPointer(int i, int j, int k, double ad[]);

    public static final native void CopyPixels(int i, int j, int k, int l, int i1);

    public static final native void CopyTexImage1D(int i, int j, int k, int l, int i1, int j1, int k1);

    public static final native void CopyTexImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1);

    public static final native void CopyTexSubImage1D(int i, int j, int k, int l, int i1, int j1);

    public static final native void CopyTexSubImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1);

    public static final native void CullFace(int i);

    public static final native void DeleteLists(int i, int j);

    public static final native void DeleteTextures(int i, int ai[]);

    public static final native void DepthFunc(int i);

    public static final native void DepthMask(boolean flag);

    public static final native void DepthRange(double d, double d1);

    public static final native void Disable(int i);

    public static final native void DisableClientState(int i);

    public static final native void DrawArrays(int i, int j, int k);

    public static final native void DrawBuffer(int i);

    public static final native void DrawElements(int i, int j, int k, byte abyte0[]);

    public static final native void DrawElements(int i, int j, int k, short aword0[]);

    public static final native void DrawElements(int i, int j, int k, int ai[]);

    public static final native void DrawPixels(int i, int j, int k, int l, byte abyte0[]);

    public static final native void DrawPixels(int i, int j, int k, int l, short aword0[]);

    public static final native void DrawPixels(int i, int j, int k, int l, int ai[]);

    public static final native void DrawPixels(int i, int j, int k, int l, float af[]);

    public static final native void EdgeFlag(boolean flag);

    public static final native void EdgeFlagPointer(int i, boolean aflag[]);

    public static final native void EdgeFlagv(boolean aflag[]);

    public static final native void Enable(int i);

    public static final native void EnableClientState(int i);

    public static final native void End();

    public static final native void EndList();

    public static final native void EvalCoord1d(double d);

    public static final native void EvalCoord(double d);

    public static final native void EvalCoord1dv(double ad[]);

    public static final native void EvalCoord1f(float f);

    public static final native void EvalCoord(float f);

    public static final native void EvalCoord1fv(float af[]);

    public static final native void EvalCoord2d(double d, double d1);

    public static final native void EvalCoord(double d, double d1);

    public static final native void EvalCoord2dv(double ad[]);

    public static final native void EvalCoord2f(float f, float f1);

    public static final native void EvalCoord(float f, float f1);

    public static final native void EvalCoord2fv(float af[]);

    public static final native void EvalCoord(double ad[]);

    public static final native void EvalCoord(float af[]);

    public static final native void EvalMesh1(int i, int j, int k);

    public static final native void EvalMesh2(int i, int j, int k, int l, int i1);

    public static final native void EvalPoint1(int i);

    public static final native void EvalPoint2(int i, int j);

    public static final native void FeedbackBuffer(int i, int j, float af[]);

    public static final native void Finish();

    public static final native void Flush();

    public static final native void Fogf(int i, float f);

    public static final native void Fog(int i, float f);

    public static final native void Fogfv(int i, float af[]);

    public static final native void Fog(int i, float af[]);

    public static final native void Fogi(int i, int j);

    public static final native void Fog(int i, int j);

    public static final native void Fogiv(int i, int ai[]);

    public static final native void Fog(int i, int ai[]);

    public static final native void FrontFace(int i);

    public static final native void Frustum(double d, double d1, double d2, double d3, 
            double d4, double d5);

    public static final native int GenLists(int i);

    public static final native void GenTextures(int i, int ai[]);

    public static final native void GetBooleanv(int i, boolean aflag[]);

    public static final native void GetClipPlane(int i, double ad[]);

    public static final native void GetDoublev(int i, double ad[]);

    public static final native int GetError();

    public static final native void GetFloatv(int i, float af[]);

    public static final native void GetIntegerv(int i, int ai[]);

    public static final native void GetLightfv(int i, int j, float af[]);

    public static final native void GetLight(int i, int j, float af[]);

    public static final native void GetLightiv(int i, int j, int ai[]);

    public static final native void GetLight(int i, int j, int ai[]);

    public static final native void GetMapdv(int i, int j, double ad[]);

    public static final native void GetMap(int i, int j, double ad[]);

    public static final native void GetMapfv(int i, int j, float af[]);

    public static final native void GetMap(int i, int j, float af[]);

    public static final native void GetMapiv(int i, int j, int ai[]);

    public static final native void GetMap(int i, int j, int ai[]);

    public static final native void GetMaterialfv(int i, int j, float af[]);

    public static final native void GetMaterial(int i, int j, float af[]);

    public static final native void GetMaterialiv(int i, int j, int ai[]);

    public static final native void GetMaterial(int i, int j, int ai[]);

    public static final native void GetPixelMapfv(int i, float af[]);

    public static final native void GetPixelMap(int i, float af[]);

    public static final native void GetPixelMapuiv(int i, int ai[]);

    public static final native void GetPixelMap(int i, int ai[]);

    public static final native void GetPixelMapusv(int i, short aword0[]);

    public static final native void GetPixelMap(int i, short aword0[]);

    public static final native void GetPolygonStipple(byte abyte0[]);

    public static final native java.lang.String GetString(int i);

    public static final native void GetTexEnvfv(int i, int j, float af[]);

    public static final native void GetTexEnv(int i, int j, float af[]);

    public static final native void GetTexEnviv(int i, int j, int ai[]);

    public static final native void GetTexEnv(int i, int j, int ai[]);

    public static final native void GetTexGendv(int i, int j, double ad[]);

    public static final native void GetTexGen(int i, int j, double ad[]);

    public static final native void GetTexGenfv(int i, int j, float af[]);

    public static final native void GetTexGen(int i, int j, float af[]);

    public static final native void GetTexGeniv(int i, int j, int ai[]);

    public static final native void GetTexGen(int i, int j, int ai[]);

    public static final native void GetTexImage(int i, int j, int k, int l, byte abyte0[]);

    public static final native void GetTexImage(int i, int j, int k, int l, short aword0[]);

    public static final native void GetTexImage(int i, int j, int k, int l, int ai[]);

    public static final native void GetTexImage(int i, int j, int k, int l, float af[]);

    public static final native void GetTexLevelParameterfv(int i, int j, int k, float af[]);

    public static final native void GetTexLevelParameter(int i, int j, int k, float af[]);

    public static final native void GetTexLevelParameteriv(int i, int j, int k, int ai[]);

    public static final native void GetTexLevelParameter(int i, int j, int k, int ai[]);

    public static final native void GetTexParameterfv(int i, int j, float af[]);

    public static final native void GetTexParameter(int i, int j, float af[]);

    public static final native void GetTexParameteriv(int i, int j, int ai[]);

    public static final native void GetTexParameter(int i, int j, int ai[]);

    public static final native void Hint(int i, int j);

    public static final native void IndexMask(int i);

    public static final native void IndexPointer(int i, int j, byte abyte0[]);

    public static final native void IndexPointer(int i, int j, short aword0[]);

    public static final native void IndexPointer(int i, int j, int ai[]);

    public static final native void IndexPointer(int i, int j, float af[]);

    public static final native void IndexPointer(int i, int j, double ad[]);

    public static final native void Indexd(double d);

    public static final native void Index(double d);

    public static final native void Indexdv(double ad[]);

    public static final native void Index(double ad[]);

    public static final native void Indexf(float f);

    public static final native void Index(float f);

    public static final native void Indexfv(float af[]);

    public static final native void Index(float af[]);

    public static final native void Indexi(int i);

    public static final native void Index(int i);

    public static final native void Indexiv(int ai[]);

    public static final native void Index(int ai[]);

    public static final native void Indexs(short word0);

    public static final native void Index(short word0);

    public static final native void Indexsv(short aword0[]);

    public static final native void Index(short aword0[]);

    public static final native void Indexub(byte byte0);

    public static final native void Index(byte byte0);

    public static final native void Indexubv(byte abyte0[]);

    public static final native void Index(byte abyte0[]);

    public static final native void InitNames();

    public static final native void InterleavedArrays(int i, int j, float af[]);

    public static final native boolean IsEnabled(int i);

    public static final native boolean IsList(int i);

    public static final native boolean IsTexture(int i);

    public static final native void LightModelf(int i, float f);

    public static final native void LightMode(int i, float f);

    public static final native void LightModelfv(int i, float af[]);

    public static final native void LightModel(int i, float af[]);

    public static final native void LightModeli(int i, int j);

    public static final native void LightModel(int i, int j);

    public static final native void LightModeliv(int i, int ai[]);

    public static final native void LightModel(int i, int ai[]);

    public static final native void Lightf(int i, int j, float f);

    public static final native void Light(int i, int j, float f);

    public static final native void Lightfv(int i, int j, float af[]);

    public static final native void Light(int i, int j, float af[]);

    public static final native void Lighti(int i, int j, int k);

    public static final native void Light(int i, int j, int k);

    public static final native void Lightiv(int i, int j, int ai[]);

    public static final native void Light(int i, int j, int ai[]);

    public static final native void LineStipple(int i, short word0);

    public static final native void LineWidth(float f);

    public static final native void ListBase(int i);

    public static final native void LoadIdentity();

    public static final native void LoadMatrixd(double ad[]);

    public static final native void LoadMatrix(double ad[]);

    public static final native void LoadMatrixf(float af[]);

    public static final native void LoadMatrix(float af[]);

    public static final native void LoadName(int i);

    public static final native void LogicOp(int i);

    public static final native void Map1d(int i, double d, double d1, int j, int k, double ad[]);

    public static final native void Map1(int i, double d, double d1, int j, int k, double ad[]);

    public static final native void Map1f(int i, float f, float f1, int j, int k, float af[]);

    public static final native void Map1(int i, float f, float f1, int j, int k, float af[]);

    public static final native void Map2d(int i, double d, double d1, int j, int k, double d2, double d3, int l, int i1, double ad[]);

    public static final native void Map2(int i, double d, double d1, int j, int k, double d2, double d3, int l, int i1, double ad[]);

    public static final native void Map2f(int i, float f, float f1, int j, int k, float f2, float f3, int l, 
            int i1, float af[]);

    public static final native void Map2(int i, float f, float f1, int j, int k, float f2, float f3, int l, 
            int i1, float af[]);

    public static final native void MapGrid1d(int i, double d, double d1);

    public static final native void MapGrid1(int i, double d, double d1);

    public static final native void MapGrid1f(int i, float f, float f1);

    public static final native void MapGrid1(int i, float f, float f1);

    public static final native void MapGrid2d(int i, double d, double d1, int j, double d2, 
            double d3);

    public static final native void MapGrid2(int i, double d, double d1, int j, double d2, 
            double d3);

    public static final native void MapGrid2f(int i, float f, float f1, int j, float f2, float f3);

    public static final native void MapGrid2(int i, float f, float f1, int j, float f2, float f3);

    public static final native void Materialf(int i, int j, float f);

    public static final native void Material(int i, int j, float f);

    public static final native void Materialfv(int i, int j, float af[]);

    public static final native void Material(int i, int j, float af[]);

    public static final native void Materiali(int i, int j, int k);

    public static final native void Material(int i, int j, int k);

    public static final native void Materialiv(int i, int j, int ai[]);

    public static final native void Material(int i, int j, int ai[]);

    public static final native void MatrixMode(int i);

    public static final native void MultMatrixd(double ad[]);

    public static final native void MultMatrix(double ad[]);

    public static final native void MultMatrixf(float af[]);

    public static final native void MultMatrix(float af[]);

    public static final native void NewList(int i, int j);

    public static final native void Normal3b(byte byte0, byte byte1, byte byte2);

    public static final native void Normal3(byte byte0, byte byte1, byte byte2);

    public static final native void Normal3bv(byte abyte0[]);

    public static final native void Normal3(byte abyte0[]);

    public static final native void Normal3d(double d, double d1, double d2);

    public static final native void Normal3(double d, double d1, double d2);

    public static final native void Normal3dv(double ad[]);

    public static final native void Normal3(double ad[]);

    public static final native void Normal3f(float f, float f1, float f2);

    public static final native void Normal3(float f, float f1, float f2);

    public static final native void Normal3fv(float af[]);

    public static final native void Normal3(float af[]);

    public static final native void Normal3i(int i, int j, int k);

    public static final native void Normal3(int i, int j, int k);

    public static final native void Normal3iv(int ai[]);

    public static final native void Normal3(int ai[]);

    public static final native void Normal3s(short word0, short word1, short word2);

    public static final native void Normal3(short word0, short word1, short word2);

    public static final native void Normal3sv(short aword0[]);

    public static final native void Normal3(short aword0[]);

    public static final native void NormalPointer(int i, int j, byte abyte0[]);

    public static final native void NormalPointer(int i, int j, short aword0[]);

    public static final native void NormalPointer(int i, int j, int ai[]);

    public static final native void NormalPointer(int i, int j, float af[]);

    public static final native void NormalPointer(int i, int j, double ad[]);

    public static final native void Ortho(double d, double d1, double d2, double d3, 
            double d4, double d5);

    public static final native void PassThrough(float f);

    public static final native void PixelMapfv(int i, int j, float af[]);

    public static final native void PixelMap(int i, int j, float af[]);

    public static final native void PixelMapuiv(int i, int j, int ai[]);

    public static final native void PixelMap(int i, int j, int ai[]);

    public static final native void PixelMapusv(int i, int j, short aword0[]);

    public static final native void PixelMap(int i, int j, short aword0[]);

    public static final native void PixelStoref(int i, float f);

    public static final native void PixelStore(int i, float f);

    public static final native void PixelStorei(int i, int j);

    public static final native void PixelStore(int i, int j);

    public static final native void PixelTransferf(int i, float f);

    public static final native void PixelTransfer(int i, float f);

    public static final native void PixelTransferi(int i, int j);

    public static final native void PixelTransfer(int i, int j);

    public static final native void PixelZoom(float f, float f1);

    public static final native void PointSize(float f);

    public static final native void PolygonMode(int i, int j);

    public static final native void PolygonOffset(float f, float f1);

    public static final native void PolygonStipple(byte abyte0[]);

    public static final native void PopAttrib();

    public static final native void PopClientAttrib();

    public static final native void PopMatrix();

    public static final native void PopName();

    public static final native void PrioritizeTextures(int i, int ai[], float af[]);

    public static final native void PushAttrib(int i);

    public static final native void PushClientAttrib(int i);

    public static final native void PushMatrix();

    public static final native void PushName(int i);

    public static final native void RasterPos2d(double d, double d1);

    public static final native void RasterPos(double d, double d1);

    public static final native void RasterPos2dv(double ad[]);

    public static final native void RasterPos2f(float f, float f1);

    public static final native void RasterPos(float f, float f1);

    public static final native void RasterPos2fv(float af[]);

    public static final native void RasterPos2i(int i, int j);

    public static final native void RasterPos(int i, int j);

    public static final native void RasterPos2iv(int ai[]);

    public static final native void RasterPos2s(short word0, short word1);

    public static final native void RasterPos(short word0, short word1);

    public static final native void RasterPos2sv(short aword0[]);

    public static final native void RasterPos3d(double d, double d1, double d2);

    public static final native void RasterPos(double d, double d1, double d2);

    public static final native void RasterPos3dv(double ad[]);

    public static final native void RasterPos3f(float f, float f1, float f2);

    public static final native void RasterPos(float f, float f1, float f2);

    public static final native void RasterPos3fv(float af[]);

    public static final native void RasterPos3i(int i, int j, int k);

    public static final native void RasterPos(int i, int j, int k);

    public static final native void RasterPos3iv(int ai[]);

    public static final native void RasterPos3s(short word0, short word1, short word2);

    public static final native void RasterPos(short word0, short word1, short word2);

    public static final native void RasterPos3sv(short aword0[]);

    public static final native void RasterPos4d(double d, double d1, double d2, double d3);

    public static final native void RasterPos(double d, double d1, double d2, double d3);

    public static final native void RasterPos4dv(double ad[]);

    public static final native void RasterPos4f(float f, float f1, float f2, float f3);

    public static final native void RasterPos(float f, float f1, float f2, float f3);

    public static final native void RasterPos4fv(float af[]);

    public static final native void RasterPos4i(int i, int j, int k, int l);

    public static final native void RasterPos(int i, int j, int k, int l);

    public static final native void RasterPos4iv(int ai[]);

    public static final native void RasterPos4s(short word0, short word1, short word2, short word3);

    public static final native void RasterPos(short word0, short word1, short word2, short word3);

    public static final native void RasterPos4sv(short aword0[]);

    public static final native void RasterPos(double ad[]);

    public static final native void RasterPos(float af[]);

    public static final native void RasterPos(int ai[]);

    public static final native void RasterPos(short aword0[]);

    public static final native void ReadBuffer(int i);

    public static final native void ReadPixels(int i, int j, int k, int l, int i1, int j1, byte abyte0[]);

    public static final native void ReadPixels(int i, int j, int k, int l, int i1, int j1, short aword0[]);

    public static final native void ReadPixels(int i, int j, int k, int l, int i1, int j1, int ai[]);

    public static final native void ReadPixels(int i, int j, int k, int l, int i1, int j1, float af[]);

    public static final native void Rectd(double d, double d1, double d2, double d3);

    public static final native void Rect(double d, double d1, double d2, double d3);

    public static final native void Rectdv(double ad[], double ad1[]);

    public static final native void Rect(double ad[], double ad1[]);

    public static final native void Rectf(float f, float f1, float f2, float f3);

    public static final native void Rect(float f, float f1, float f2, float f3);

    public static final native void Rectfv(float af[], float af1[]);

    public static final native void Rect(float af[], float af1[]);

    public static final native void Recti(int i, int j, int k, int l);

    public static final native void Rect(int i, int j, int k, int l);

    public static final native void Rectiv(int ai[], int ai1[]);

    public static final native void Rect(int ai[], int ai1[]);

    public static final native void Rects(short word0, short word1, short word2, short word3);

    public static final native void Rect(short word0, short word1, short word2, short word3);

    public static final native void Rectsv(short aword0[], short aword1[]);

    public static final native void Rect(short aword0[], short aword1[]);

    public static final native int RenderMode(int i);

    public static final native void Rotated(double d, double d1, double d2, double d3);

    public static final native void Rotate(double d, double d1, double d2, double d3);

    public static final native void Rotatef(float f, float f1, float f2, float f3);

    public static final native void Rotate(float f, float f1, float f2, float f3);

    public static final native void Scaled(double d, double d1, double d2);

    public static final native void Scale(double d, double d1, double d2);

    public static final native void Scalef(float f, float f1, float f2);

    public static final native void Scale(float f, float f1, float f2);

    public static final native void Scissor(int i, int j, int k, int l);

    public static final native void SelectBuffer(int i, int ai[]);

    public static final native void ShadeModel(int i);

    public static final native void StencilFunc(int i, int j, int k);

    public static final native void StencilMask(int i);

    public static final native void StencilOp(int i, int j, int k);

    public static final native void TexCoord1d(double d);

    public static final native void TexCoord(double d);

    public static final native void TexCoord1dv(double ad[]);

    public static final native void TexCoord1f(float f);

    public static final native void TexCoord(float f);

    public static final native void TexCoord1fv(float af[]);

    public static final native void TexCoord1i(int i);

    public static final native void TexCoord(int i);

    public static final native void TexCoord1iv(int ai[]);

    public static final native void TexCoord1s(short word0);

    public static final native void TexCoord(short word0);

    public static final native void TexCoord1sv(short aword0[]);

    public static final native void TexCoord2d(double d, double d1);

    public static final native void TexCoord(double d, double d1);

    public static final native void TexCoord2dv(double ad[]);

    public static final native void TexCoord2f(float f, float f1);

    public static final native void TexCoord(float f, float f1);

    public static final native void TexCoord2fv(float af[]);

    public static final native void TexCoord2i(int i, int j);

    public static final native void TexCoord(int i, int j);

    public static final native void TexCoord2iv(int ai[]);

    public static final native void TexCoord2s(short word0, short word1);

    public static final native void TexCoord(short word0, short word1);

    public static final native void TexCoord2sv(short aword0[]);

    public static final native void TexCoord3d(double d, double d1, double d2);

    public static final native void TexCoord(double d, double d1, double d2);

    public static final native void TexCoord3dv(double ad[]);

    public static final native void TexCoord3f(float f, float f1, float f2);

    public static final native void TexCoord(float f, float f1, float f2);

    public static final native void TexCoord3fv(float af[]);

    public static final native void TexCoord3i(int i, int j, int k);

    public static final native void TexCoord(int i, int j, int k);

    public static final native void TexCoord3iv(int ai[]);

    public static final native void TexCoord3s(short word0, short word1, short word2);

    public static final native void TexCoord(short word0, short word1, short word2);

    public static final native void TexCoord3sv(short aword0[]);

    public static final native void TexCoord4d(double d, double d1, double d2, double d3);

    public static final native void TexCoord(double d, double d1, double d2, double d3);

    public static final native void TexCoord4dv(double ad[]);

    public static final native void TexCoord4f(float f, float f1, float f2, float f3);

    public static final native void TexCoord(float f, float f1, float f2, float f3);

    public static final native void TexCoord4fv(float af[]);

    public static final native void TexCoord4i(int i, int j, int k, int l);

    public static final native void TexCoord(int i, int j, int k, int l);

    public static final native void TexCoord4iv(int ai[]);

    public static final native void TexCoord4s(short word0, short word1, short word2, short word3);

    public static final native void TexCoord(short word0, short word1, short word2, short word3);

    public static final native void TexCoord4sv(short aword0[]);

    public static final native void TexCoord(double ad[]);

    public static final native void TexCoord(float af[]);

    public static final native void TexCoord(int ai[]);

    public static final native void TexCoord(short aword0[]);

    public static final native void TexCoordPointer(int i, int j, int k, short aword0[]);

    public static final native void TexCoordPointer(int i, int j, int k, int ai[]);

    public static final native void TexCoordPointer(int i, int j, int k, float af[]);

    public static final native void TexCoordPointer(int i, int j, int k, double ad[]);

    public static final native void TexEnvf(int i, int j, float f);

    public static final native void TexEnv(int i, int j, float f);

    public static final native void TexEnvfv(int i, int j, float af[]);

    public static final native void TexEnv(int i, int j, float af[]);

    public static final native void TexEnvi(int i, int j, int k);

    public static final native void TexEnv(int i, int j, int k);

    public static final native void TexEnviv(int i, int j, int ai[]);

    public static final native void TexEnv(int i, int j, int ai[]);

    public static final native void TexGend(int i, int j, double d);

    public static final native void TexGen(int i, int j, double d);

    public static final native void TexGendv(int i, int j, double ad[]);

    public static final native void TexGen(int i, int j, double ad[]);

    public static final native void TexGenf(int i, int j, float f);

    public static final native void TexGen(int i, int j, float f);

    public static final native void TexGenfv(int i, int j, float af[]);

    public static final native void TexGen(int i, int j, float af[]);

    public static final native void TexGeni(int i, int j, int k);

    public static final native void TexGen(int i, int j, int k);

    public static final native void TexGeniv(int i, int j, int ai[]);

    public static final native void TexGen(int i, int j, int ai[]);

    public static final native void TexImage1D(int i, int j, int k, int l, int i1, int j1, int k1, byte abyte0[]);

    public static final native void TexImage1D(int i, int j, int k, int l, int i1, int j1, int k1, short aword0[]);

    public static final native void TexImage1D(int i, int j, int k, int l, int i1, int j1, int k1, int ai[]);

    public static final native void TexImage1D(int i, int j, int k, int l, int i1, int j1, int k1, float af[]);

    public static final native void TexImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            byte abyte0[]);

    public static final native void TexImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            short aword0[]);

    public static final native void TexImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            int ai[]);

    public static final native void TexImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            float af[]);

    public static final native void TexParameterf(int i, int j, float f);

    public static final native void TexParameter(int i, int j, float f);

    public static final native void TexParameterfv(int i, int j, float af[]);

    public static final native void TexParameter(int i, int j, float af[]);

    public static final native void TexParameteri(int i, int j, int k);

    public static final native void TexParameter(int i, int j, int k);

    public static final native void TexParameteriv(int i, int j, int ai[]);

    public static final native void TexParameter(int i, int j, int ai[]);

    public static final native void TexSubImage1D(int i, int j, int k, int l, int i1, int j1, byte abyte0[]);

    public static final native void TexSubImage1D(int i, int j, int k, int l, int i1, int j1, short aword0[]);

    public static final native void TexSubImage1D(int i, int j, int k, int l, int i1, int j1, int ai[]);

    public static final native void TexSubImage1D(int i, int j, int k, int l, int i1, int j1, float af[]);

    public static final native void TexSubImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            byte abyte0[]);

    public static final native void TexSubImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            short aword0[]);

    public static final native void TexSubImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            int ai[]);

    public static final native void TexSubImage2D(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
            float af[]);

    public static final native void Translated(double d, double d1, double d2);

    public static final native void Translate(double d, double d1, double d2);

    public static final native void Translatef(float f, float f1, float f2);

    public static final native void Translate(float f, float f1, float f2);

    public static final native void Vertex2d(double d, double d1);

    public static final native void Vertex(double d, double d1);

    public static final native void Vertex2dv(double ad[]);

    public static final native void Vertex2f(float f, float f1);

    public static final native void Vertex(float f, float f1);

    public static final native void Vertex2fv(float af[]);

    public static final native void Vertex2i(int i, int j);

    public static final native void Vertex(int i, int j);

    public static final native void Vertex2iv(int ai[]);

    public static final native void Vertex2s(short word0, short word1);

    public static final native void Vertex(short word0, short word1);

    public static final native void Vertex2sv(short aword0[]);

    public static final native void Vertex3d(double d, double d1, double d2);

    public static final native void Vertex(double d, double d1, double d2);

    public static final native void Vertex3dv(double ad[]);

    public static final native void Vertex3f(float f, float f1, float f2);

    public static final native void Vertex(float f, float f1, float f2);

    public static final native void Vertex3fv(float af[]);

    public static final native void Vertex3i(int i, int j, int k);

    public static final native void Vertex(int i, int j, int k);

    public static final native void Vertex3iv(int ai[]);

    public static final native void Vertex3s(short word0, short word1, short word2);

    public static final native void Vertex(short word0, short word1, short word2);

    public static final native void Vertex3sv(short aword0[]);

    public static final native void Vertex4d(double d, double d1, double d2, double d3);

    public static final native void Vertex(double d, double d1, double d2, double d3);

    public static final native void Vertex4dv(double ad[]);

    public static final native void Vertex4f(float f, float f1, float f2, float f3);

    public static final native void Vertex(float f, float f1, float f2, float f3);

    public static final native void Vertex4fv(float af[]);

    public static final native void Vertex4i(int i, int j, int k, int l);

    public static final native void Vertex(int i, int j, int k, int l);

    public static final native void Vertex4iv(int ai[]);

    public static final native void Vertex4s(short word0, short word1, short word2, short word3);

    public static final native void Vertex(short word0, short word1, short word2, short word3);

    public static final native void Vertex4sv(short aword0[]);

    public static final native void Vertex(double ad[]);

    public static final native void Vertex(float af[]);

    public static final native void Vertex(int ai[]);

    public static final native void Vertex(short aword0[]);

    public static final native void VertexPointer(int i, int j, int k, short aword0[]);

    public static final native void VertexPointer(int i, int j, int k, int ai[]);

    public static final native void VertexPointer(int i, int j, int k, float af[]);

    public static final native void VertexPointer(int i, int j, int k, double ad[]);

    public static final native void Viewport(int i, int j, int k, int l);

    public static final boolean extExist(java.lang.String s)
    {
        java.lang.String s1 = com.maddox.opengl.gl.GetString(7939);
        for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s1, " "); stringtokenizer.hasMoreTokens();)
        {
            java.lang.String s2 = stringtokenizer.nextToken();
            if(s2.equals(s))
                return true;
        }

        return false;
    }

    public static final void loadNative()
    {
        if(!libLoaded)
        {
            java.lang.System.loadLibrary("jgl");
            libLoaded = true;
        }
    }

    private static boolean libLoaded = false;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
