package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;

public class Mesh extends GObj
{
  protected static float[] af = new float[3];
  protected static double[] ad = new double[3];
  protected static double[] ad1 = new double[3];
  protected static double[] ad2 = new double[3];
  protected static double[] ad3 = new double[3];
  protected static double[] tmp = new double[16];
  protected static double[] tmp2 = new double[16];
  private static float[] dimns = new float[6];

  protected boolean bAllowsSimpleRendering = false;
  protected float visibilityR;
  protected float collisionR;

  public boolean isAllowsSimpleRendering()
  {
    return this.bAllowsSimpleRendering;
  }

  public void setPos(Point3d paramPoint3d, Orient paramOrient) {
    SetPosXYZATK(this.cppObj, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z, paramOrient.getAzimut(), paramOrient.getTangage(), paramOrient.getKren());
  }

  public void setPos(Loc paramLoc) {
    Point3d localPoint3d = paramLoc.getPoint();
    Orient localOrient = paramLoc.getOrient();
    SetPosXYZATK(this.cppObj, localPoint3d.x, localPoint3d.y, localPoint3d.z, localOrient.getAzimut(), localOrient.getTangage(), localOrient.getKren());
  }

  public final int preRender(Point3d paramPoint3d) {
    return PreRenderXYZ(this.cppObj, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
  }

  public int preRender() {
    return PreRender(this.cppObj);
  }

  public void setFastShadowVisibility(int paramInt)
  {
    if (Config.isUSE_RENDER())
      SetFastShadowVisibility(this.cppObj, paramInt);
  }

  public void render() {
    Render(this.cppObj);
  }
  public void renderShadowProjective() {
    RenderShadowProjective(this.cppObj);
  }
  public void renderShadowVolume() {
    RenderShadowVolume(this.cppObj);
  }
  public void renderShadowVolumeHQ() {
    RenderShadowVolumeHQ(this.cppObj);
  }

  public int detectCollision(Loc paramLoc1, Mesh paramMesh, Loc paramLoc2) {
    paramLoc1.get(tmp);
    paramLoc2.get(tmp2);
    return DetectCollision(this.cppObj, tmp, paramMesh.cppObj, tmp2);
  }
  public float detectCollisionLine(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2) {
    paramLoc.get(tmp);
    paramPoint3d1.get(ad1);
    paramPoint3d2.get(ad2);
    return DetectCollisionLine(this.cppObj, tmp, ad1, ad2);
  }

  public int detectCollisionLineMulti(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    paramLoc.get(tmp);
    paramPoint3d1.get(ad1);
    paramPoint3d2.get(ad2);
    return DetectCollisionLineMulti(this.cppObj, tmp, ad1, ad2);
  }

  public static native String collisionNameMulti(int paramInt1, int paramInt2);

  public static native float collisionDistMulti(int paramInt);

  public float detectCollisionQuad(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4)
  {
    paramLoc.get(tmp);

    tmp2[0] = paramPoint3d1.x;
    tmp2[1] = paramPoint3d1.y;
    tmp2[2] = paramPoint3d1.z;

    tmp2[3] = paramPoint3d2.x;
    tmp2[4] = paramPoint3d2.y;
    tmp2[5] = paramPoint3d2.z;

    tmp2[6] = paramPoint3d3.x;
    tmp2[7] = paramPoint3d3.y;
    tmp2[8] = paramPoint3d3.z;

    tmp2[9] = paramPoint3d4.x;
    tmp2[10] = paramPoint3d4.y;
    tmp2[11] = paramPoint3d4.z;

    return DetectCollisionQuad(this.cppObj, tmp, tmp2);
  }

  public int detectCollision_Quad_Multi(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4)
  {
    paramLoc.get(tmp);

    tmp2[0] = paramPoint3d1.x;
    tmp2[1] = paramPoint3d1.y;
    tmp2[2] = paramPoint3d1.z;

    tmp2[3] = paramPoint3d2.x;
    tmp2[4] = paramPoint3d2.y;
    tmp2[5] = paramPoint3d2.z;

    tmp2[6] = paramPoint3d3.x;
    tmp2[7] = paramPoint3d3.y;
    tmp2[8] = paramPoint3d3.z;

    tmp2[9] = paramPoint3d4.x;
    tmp2[10] = paramPoint3d4.y;
    tmp2[11] = paramPoint3d4.z;

    return DetectCollisionQuadMultiM(this.cppObj, tmp, tmp2);
  }

  public int detectCollisionPoint(Loc paramLoc, Point3d paramPoint3d) {
    paramLoc.get(tmp);
    paramPoint3d.get(ad);
    return DetectCollisionPoint(this.cppObj, tmp, ad);
  }
  public float detectCollisionPlane(Loc paramLoc, Vector3d paramVector3d, double paramDouble) {
    paramLoc.get(tmp);
    paramVector3d.get(tmp2); tmp2[3] = paramDouble;
    return DetectCollisionPlane(this.cppObj, tmp, tmp2);
  }
  public static native String collisionChunk(int paramInt);

  public void setScale(float paramFloat) {
    SetScale(this.cppObj, paramFloat);
  }

  public float scale() {
    return Scale(this.cppObj);
  }

  public void setScaleXYZ(float paramFloat1, float paramFloat2, float paramFloat3) {
    SetScaleXYZ(this.cppObj, paramFloat1, paramFloat2, paramFloat3);
  }

  public void scaleXYZ(float[] paramArrayOfFloat) {
    ScaleXYZ(this.cppObj, paramArrayOfFloat);
  }

  public float visibilityR() {
    return this.visibilityR;
  }

  public float collisionR()
  {
    return this.collisionR;
  }

  public float getUniformMaxDist()
  {
    return GetUniformMaxDist(this.cppObj);
  }
  public void setCurChunk(int paramInt) {
  }

  public int getCurChunk() {
    return 0;
  }
  public int frames() {
    return Frames(this.cppObj);
  }
  public void setFrame(int paramInt) {
    SetFrame(this.cppObj, paramInt);
  }
  public void setFrame(int paramInt1, int paramInt2, float paramFloat) {
    SetFrame(this.cppObj, paramInt1, paramInt2, paramFloat);
  }
  public void setFrameFromRange(int paramInt1, int paramInt2, float paramFloat) {
    SetFrameFromRange(this.cppObj, paramInt1, paramInt2, paramFloat);
  }

  public void getBoundBox(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length < 6) {
      throw new GObjException("Internal error: wrong array size");
    }
    GetBoundBox(this.cppObj, paramArrayOfFloat);
  }

  public void getDimensions(Vector3f paramVector3f) {
    getBoundBox(dimns);
    paramVector3f.x = (dimns[3] - dimns[0]);
    paramVector3f.y = (dimns[4] - dimns[1]);
    paramVector3f.z = (dimns[5] - dimns[2]);
  }

  public int hooks() {
    return Hooks(this.cppObj);
  }
  public int hookFind(String paramString) {
    return HookFind(this.cppObj, paramString);
  }

  public String hookName(int paramInt) {
    return HookName(this.cppObj, paramInt);
  }

  public void hookMatrix(int paramInt, Matrix4d paramMatrix4d) {
    HookMatrix(this.cppObj, paramInt, tmp);
    paramMatrix4d.set(tmp);
  }

  public int hookFaceCollisFind(Matrix4d paramMatrix4d1, int[] paramArrayOfInt, Matrix4d paramMatrix4d2)
  {
    return hookFaceFind(paramMatrix4d1, paramMatrix4d2);
  }

  public int hookFaceFind(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
    tmp[0] = paramMatrix4d1.m00; tmp[4] = paramMatrix4d1.m01; tmp[8] = paramMatrix4d1.m02; tmp[12] = paramMatrix4d1.m03;
    tmp[1] = paramMatrix4d1.m10; tmp[5] = paramMatrix4d1.m11; tmp[9] = paramMatrix4d1.m12; tmp[13] = paramMatrix4d1.m13;
    tmp[2] = paramMatrix4d1.m20; tmp[6] = paramMatrix4d1.m21; tmp[10] = paramMatrix4d1.m22; tmp[14] = paramMatrix4d1.m23;
    tmp[3] = paramMatrix4d1.m30; tmp[7] = paramMatrix4d1.m31; tmp[11] = paramMatrix4d1.m32; tmp[15] = paramMatrix4d1.m33;
    int i = HookFaceFind(this.cppObj, tmp, tmp2);
    if (i != -1) {
      paramMatrix4d2.set(tmp2);
    }
    return i;
  }

  public int hookFaceFind(Matrix4d paramMatrix4d1, int[] paramArrayOfInt, Matrix4d paramMatrix4d2)
  {
    return hookFaceFind(paramMatrix4d1, paramMatrix4d2);
  }

  public void hookFaceMatrix(int paramInt1, int paramInt2, Matrix4d paramMatrix4d) {
    HookFaceMatrix(this.cppObj, paramInt1, paramInt2, tmp);
    paramMatrix4d.set(tmp);
  }

  public int materials()
  {
    return Materials(this.cppObj);
  }

  public int materialFind(String paramString, int paramInt1, int paramInt2) {
    return MaterialFind(this.cppObj, paramString, paramInt1, paramInt2);
  }

  public int materialFind(String paramString, int paramInt) {
    return MaterialFind(this.cppObj, paramString, paramInt, -1);
  }

  public int materialFind(String paramString) {
    return MaterialFind(this.cppObj, paramString, 0, -1);
  }

  public Mat material(int paramInt) {
    return (Mat)Material(this.cppObj, paramInt);
  }

  public void materialReplace(int paramInt, Mat paramMat) {
    MaterialReplace(this.cppObj, paramInt, paramMat.cppObject());
  }

  public void materialReplace(int paramInt, String paramString) {
    MaterialReplace(this.cppObj, paramInt, paramString);
  }

  public void materialReplace(String paramString, Mat paramMat) {
    MaterialReplace(this.cppObj, paramString, paramMat.cppObject());
  }

  public void materialReplaceToNull(String paramString) {
    MaterialReplace(this.cppObj, paramString, 0);
  }

  public void materialReplace(String paramString1, String paramString2) {
    MaterialReplace(this.cppObj, paramString1, paramString2);
  }

  public void makeAllMaterialsDarker(float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    int i = materials();
    for (int j = 0; j < i; j++) {
      Mat localMat1 = material(j);
      if (localMat1 != null) {
        String str1 = localMat1.Name();

        if ((str1 == null) || (str1.length() < 5) || (!str1.toUpperCase().endsWith(".MAT")))
        {
          continue;
        }

        String str2 = str1.substring(0, str1.length() - 4) + "_dark.mat";

        Mat localMat2 = null;
        if (Mat.Exist(str2)) {
          localMat2 = (Mat)Mat.Get(str2);
        }
        if (localMat2 == null)
        {
          localMat2 = (Mat)localMat1.Clone();
          if (localMat2 == null)
            continue;
          localMat2.Rename(str2);

          localMat2.set(20, localMat2.get(20) * paramFloat1);
          localMat2.set(21, localMat2.get(21) * paramFloat2);
          localMat2.set(22, 0.0F);
          localMat2.set(23, 0.0F);
          localMat2.set(24, 0.0F);
        }

        materialReplace(j, localMat2);
      }
    }
  }

  public static boolean isSimilar(Mesh paramMesh1, Mesh paramMesh2)
  {
    if (paramMesh1 == paramMesh2) return true;
    return IsSimilar(paramMesh1.cppObj, paramMesh2.cppObj) != 0;
  }

  protected float heightMapMeshGetHeight(double paramDouble1, double paramDouble2) {
    return HeightMapMeshGetHeight(this.cppObj, paramDouble1, paramDouble2);
  }
  protected boolean heightMapMeshGetNormal(double paramDouble1, double paramDouble2, float[] paramArrayOfFloat) {
    return HeightMapMeshGetNormal(this.cppObj, paramDouble1, paramDouble2, paramArrayOfFloat) == 1;
  }
  protected boolean heightMapMeshGetPlane(double paramDouble1, double paramDouble2, double[] paramArrayOfDouble) {
    return HeightMapMeshGetPlane(this.cppObj, paramDouble1, paramDouble2, paramArrayOfDouble) == 1;
  }
  protected float heightMapMeshGetRayHit(double[] paramArrayOfDouble) {
    return HeightMapMeshGetRayHit(this.cppObj, paramArrayOfDouble);
  }

  public void destroy() {
    if (this.cppObj != 0) {
      Finalize(this.cppObj);
      this.cppObj = 0;
    }
  }

  protected Mesh(int paramInt) {
    super(paramInt);
  }
  public Mesh(String paramString) {
    super(0);
    if (paramString == null)
      throw new GObjException("Meshname is empty");
    this.cppObj = Load(paramString);

    if (this.cppObj == 0)
      throw new GObjException("Mesh " + paramString + " not created");
    this.collisionR = CollisionR(this.cppObj);
    this.visibilityR = VisibilityR(this.cppObj);
    if (Config.isUSE_RENDER()) {
      this.bAllowsSimpleRendering = (IsAllowsSimpleRendering(this.cppObj) != 0);
    }
    Pre.load(paramString);
  }

  public Mesh(HierMesh paramHierMesh)
  {
    super(0);
    this.cppObj = CreateMeshFromHiermeshChunk(paramHierMesh.cppObj);

    if (this.cppObj == 0)
      throw new GObjException("Mesh not created from hiermesh");
    this.collisionR = CollisionR(this.cppObj);
    this.visibilityR = VisibilityR(this.cppObj);
    if (Config.isUSE_RENDER()) {
      this.bAllowsSimpleRendering = (IsAllowsSimpleRendering(this.cppObj) != 0);
    }
    Pre.load(GetFileName(this.cppObj)); } 
  private native int Load(String paramString);

  private native int CreateMeshFromHiermeshChunk(int paramInt);

  private native String GetFileName(int paramInt);

  private native void GetBoundBox(int paramInt, float[] paramArrayOfFloat);

  private native void SetPosXYZATK(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3);

  private native int PreRenderXYZ(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3);

  private native int PreRender(int paramInt);

  private native void SetFastShadowVisibility(int paramInt1, int paramInt2);

  private native void Render(int paramInt);

  private native void RenderShadowProjective(int paramInt);

  private native void RenderShadowVolume(int paramInt);

  private native void RenderShadowVolumeHQ(int paramInt);

  private native int DetectCollision(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, double[] paramArrayOfDouble2);

  private native float DetectCollisionLine(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  private native int DetectCollisionLineMulti(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  private native float DetectCollisionQuad(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native int DetectCollisionQuadMultiM(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native int DetectCollisionPoint(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native float DetectCollisionPlane(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native void SetScale(int paramInt, float paramFloat);

  private native float Scale(int paramInt);

  private native void SetScaleXYZ(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  private native void ScaleXYZ(int paramInt, float[] paramArrayOfFloat);

  private native float VisibilityR(int paramInt);

  private native float CollisionR(int paramInt);

  private native float GetUniformMaxDist(int paramInt);

  private native int Frames(int paramInt);

  private native void SetFrame(int paramInt1, int paramInt2);

  private native void SetFrame(int paramInt1, int paramInt2, int paramInt3, float paramFloat);

  private native void SetFrameFromRange(int paramInt1, int paramInt2, int paramInt3, float paramFloat);

  private native int Hooks(int paramInt);

  private native int HookFind(int paramInt, String paramString);

  private native String HookName(int paramInt1, int paramInt2);

  private native void HookMatrix(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  private native int HookFaceFind(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native void HookFaceMatrix(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  private native int Materials(int paramInt);

  private native int MaterialFind(int paramInt1, String paramString, int paramInt2, int paramInt3);

  private native Object Material(int paramInt1, int paramInt2);

  private native void MaterialReplace(int paramInt1, int paramInt2, int paramInt3);

  private native void MaterialReplace(int paramInt1, int paramInt2, String paramString);

  private native void MaterialReplace(int paramInt1, String paramString, int paramInt2);

  private native void MaterialReplace(int paramInt, String paramString1, String paramString2);

  private static native int IsSimilar(int paramInt1, int paramInt2);

  private native int IsAllowsSimpleRendering(int paramInt);

  private native float HeightMapMeshGetHeight(int paramInt, double paramDouble1, double paramDouble2);

  private native int HeightMapMeshGetNormal(int paramInt, double paramDouble1, double paramDouble2, float[] paramArrayOfFloat);

  private native int HeightMapMeshGetPlane(int paramInt, double paramDouble1, double paramDouble2, double[] paramArrayOfDouble);

  private native float HeightMapMeshGetRayHit(int paramInt, double[] paramArrayOfDouble);

  static { GObj.loadNative();
  }
}