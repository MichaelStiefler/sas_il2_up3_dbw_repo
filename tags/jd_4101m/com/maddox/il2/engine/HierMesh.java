package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;

public class HierMesh extends Mesh
{
  private static Matrix4d m4 = new Matrix4d();
  private static float[] arrFloat6 = new float[6];
  private static double[] arrDouble6 = new double[6];
  private static double[] Eul = new double[3];

  private static int[] shadowPairs = new int[32];

  private static float[] _chunkAngles = new float[3];

  private HashMapExt chunkMap = null;
  private static Loc _chunkLoc = new Loc();

  private int curchunk = 0;

  private static RangeRandom rnd = new RangeRandom();

  public void setPos(Point3d paramPoint3d, Orient paramOrient)
  {
    SetPosXYZATK(this.cppObj, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z, paramOrient.getAzimut(), paramOrient.getTangage(), paramOrient.getKren());
  }

  public void setPos(Loc paramLoc) {
    Point3d localPoint3d = paramLoc.getPoint();
    Orient localOrient = paramLoc.getOrient();
    SetPosXYZATK(this.cppObj, localPoint3d.x, localPoint3d.y, localPoint3d.z, localOrient.getAzimut(), localOrient.getTangage(), localOrient.getKren());
  }

  public int preRender() {
    return PreRender(this.cppObj);
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

  public void renderChunkMirror(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3) {
    RenderChunkMirror(this.cppObj, paramArrayOfDouble1, paramArrayOfDouble2, paramArrayOfDouble3);
  }

  public static void renderShadowPairs(ArrayList paramArrayList) {
    int i = paramArrayList.size();
    if (i == 0) return;
    if (shadowPairs.length < i)
      shadowPairs = new int[(i / 2 + 1) * 2];
    for (int j = 0; j < i; j++) {
      HierMesh localHierMesh = (HierMesh)paramArrayList.get(j);
      shadowPairs[j] = localHierMesh.cppObject();
    }
    RenderShadowPairs(i / 2, shadowPairs);
  }

  public int detectCollision(Loc paramLoc1, HierMesh paramHierMesh, Loc paramLoc2)
  {
    paramLoc1.get(tmp);
    paramLoc2.get(tmp2);
    return DetectCollision(this.cppObj, tmp, paramHierMesh.cppObj, tmp2);
  }
  public int detectCollision(Loc paramLoc1, Mesh paramMesh, Loc paramLoc2) {
    paramLoc1.get(tmp);
    paramLoc2.get(tmp2);
    return DetectCollisionMesh(this.cppObj, tmp, paramMesh.cppObj, tmp2);
  }

  public float detectCollisionLine(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2) {
    paramLoc.get(tmp);
    paramPoint3d1.get(ad); paramPoint3d2.get(tmp2);
    return DetectCollisionLine(this.cppObj, tmp, ad, tmp2);
  }

  public int detectCollisionLineMulti(Loc paramLoc, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    paramLoc.get(tmp);
    paramPoint3d1.get(ad); paramPoint3d2.get(tmp2);
    return DetectCollisionLineMulti(this.cppObj, tmp, ad, tmp2);
  }

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

    return DetectCollisionQuadMultiH(this.cppObj, tmp, tmp2);
  }

  public int detectCollisionPoint(Loc paramLoc, Point3d paramPoint3d)
  {
    paramLoc.get(tmp);
    paramPoint3d.get(ad);
    return DetectCollisionPoint(this.cppObj, tmp, ad);
  }
  public float detectCollisionPlane(Loc paramLoc, Vector3d paramVector3d, double paramDouble) {
    paramLoc.get(tmp);
    paramVector3d.get(tmp2); tmp2[3] = paramDouble;
    return DetectCollisionPlane(this.cppObj, tmp, tmp2);
  }
  public void setScale(float paramFloat) {
  }
  public float scale() { return 1.0F;
  }

  public void setScaleXYZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
  }

  public void scaleXYZ(float[] paramArrayOfFloat)
  {
    float tmp9_8 = (paramArrayOfFloat[2] = 1.0F); paramArrayOfFloat[1] = tmp9_8; paramArrayOfFloat[0] = tmp9_8;
  }
  public float getUniformMaxDist() {
    return GetUniformMaxDist(this.cppObj);
  }

  public int chunks() {
    return Chunks(this.cppObj);
  }

  public boolean isChunkVisible(String paramString) {
    ChunkState localChunkState = chunkState(paramString);
    if (localChunkState == null) return false;
    return localChunkState.bVisible;
  }
  public void chunkVisible(String paramString, boolean paramBoolean) {
    ChunkState localChunkState = chunkState(paramString);
    if (localChunkState == null) return;
    if (localChunkState.bVisible == paramBoolean) return;
    localChunkState.bVisible = paramBoolean;
    setCurChunk(localChunkState.indx);
    SetChunkVisibility(this.cppObj, paramBoolean ? 1 : 0);
  }
  public void chunkSetAngles(String paramString, float[] paramArrayOfFloat) {
    chunkSetAngles(paramString, paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
  }
  public void chunkSetAngles(String paramString, float paramFloat1, float paramFloat2, float paramFloat3) {
    ChunkState localChunkState = chunkState(paramString);
    if (localChunkState == null) return;
    if ((localChunkState.yaw == paramFloat1) && (localChunkState.pitch == paramFloat2) && (localChunkState.roll == paramFloat3)) return;
    float tmp52_51 = paramFloat1; localChunkState.yaw = tmp52_51; _chunkAngles[0] = tmp52_51;
    float tmp64_63 = paramFloat2; localChunkState.pitch = tmp64_63; _chunkAngles[1] = tmp64_63;
    float tmp77_75 = paramFloat3; localChunkState.roll = tmp77_75; _chunkAngles[2] = tmp77_75;
    setCurChunk(localChunkState.indx);
    SetChunkAngles(this.cppObj, _chunkAngles);
  }

  public void chunkSetLocate(String paramString, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2) {
    ChunkState localChunkState = chunkState(paramString);
    if (localChunkState == null) return;
    if ((localChunkState.yaw == paramArrayOfFloat2[0]) && (localChunkState.pitch == paramArrayOfFloat2[1]) && (localChunkState.roll == paramArrayOfFloat2[2]) && (localChunkState.x == paramArrayOfFloat1[0]) && (localChunkState.y == paramArrayOfFloat1[1]) && (localChunkState.z == paramArrayOfFloat1[2]))
      return;
    localChunkState.yaw = paramArrayOfFloat2[0]; localChunkState.pitch = paramArrayOfFloat2[1]; localChunkState.roll = paramArrayOfFloat2[2];
    localChunkState.x = paramArrayOfFloat1[0]; localChunkState.y = paramArrayOfFloat1[1]; localChunkState.z = paramArrayOfFloat1[2];
    setCurChunk(localChunkState.indx);
    SetChunkLocate(this.cppObj, paramArrayOfFloat1, paramArrayOfFloat2);
  }

  private ChunkState chunkState(String paramString)
  {
    if (this.chunkMap == null)
      this.chunkMap = new HashMapExt();
    ChunkState localChunkState = (ChunkState)this.chunkMap.get(paramString);
    if (localChunkState == null) {
      localChunkState = new ChunkState(paramString);
      this.chunkMap.put(paramString, localChunkState);
    }
    if (localChunkState.bExist) return localChunkState;
    return null;
  }

  public void setCurChunk(int paramInt)
  {
    this.curchunk = paramInt;
    SetCurChunk(this.cppObj, paramInt);
  }
  public void setCurChunk(String paramString) {
    this.curchunk = SetCurChunkByName(this.cppObj, paramString);
  }
  public int getCurChunk() {
    return this.curchunk;
  }

  public int chunkFind(String paramString) {
    return ChunkFind(this.cppObj, paramString);
  }

  public int chunkFindCheck(String paramString) {
    return ChunkFindCheck(this.cppObj, paramString);
  }

  public String chunkName() {
    return ChunkName(this.cppObj);
  }
  public float getChunkVisibilityR() {
    return GetChunkVisibilityR(this.cppObj);
  }

  public boolean isChunkVisible() {
    return GetChunkVisibility(this.cppObj) != 0;
  }

  public void chunkVisible(boolean paramBoolean) {
    SetChunkVisibility(this.cppObj, paramBoolean ? 1 : 0);
  }

  public void chunkSetAngles(float[] paramArrayOfFloat) {
    SetChunkAngles(this.cppObj, paramArrayOfFloat);
  }

  public void chunkSetLocate(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2) {
    SetChunkLocate(this.cppObj, paramArrayOfFloat1, paramArrayOfFloat2);
  }

  public void getChunkLTM(Matrix4d paramMatrix4d)
  {
    GetChunkLTM(this.cppObj, tmp);
    paramMatrix4d.set(tmp);
  }

  public void getChunkCurVisBoundBox(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    GetChunkCurVisBoundBox(this.cppObj, arrFloat6);
    paramPoint3f1.set(arrFloat6[0], arrFloat6[1], arrFloat6[2]);
    paramPoint3f2.set(arrFloat6[3], arrFloat6[4], arrFloat6[5]);
  }

  public void getChunkLocObj(Loc paramLoc)
  {
    getChunkLTM(m4);
    m4.getEulers(Eul);
    Eul[0] *= -57.299999237060547D;
    Eul[1] *= -57.299999237060547D;
    Eul[2] *= 57.299999237060547D;
    paramLoc.set(m4.m03, m4.m13, m4.m23, (float)Eul[0], (float)Eul[1], (float)Eul[2]);
  }

  public float getChunkMass()
  {
    return 0.0F;
  }

  public int frames()
  {
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

  public int hooks() {
    return Hooks(this.cppObj);
  }

  public int hookFind(String paramString) {
    return HookFind(this.cppObj, paramString);
  }

  public int hookParentChunk(String paramString)
  {
    int i = HookFind(this.cppObj, paramString) >> 16;
    if (i <= 0) {
      return -1;
    }
    return i - 1;
  }

  public String hookName(int paramInt)
  {
    return HookName(this.cppObj, paramInt);
  }

  public void hookMatrix(int paramInt, Matrix4d paramMatrix4d) {
    HookMatrix(this.cppObj, paramInt, tmp);
    paramMatrix4d.set(tmp);
  }

  public int chunkByHookNamed(int paramInt) {
    return ChunkByHookNamed(this.cppObj, paramInt);
  }

  public int hookFaceCollisFind(Matrix4d paramMatrix4d1, int[] paramArrayOfInt, Matrix4d paramMatrix4d2)
  {
    tmp[0] = paramMatrix4d1.m00; tmp[4] = paramMatrix4d1.m01; tmp[8] = paramMatrix4d1.m02; tmp[12] = paramMatrix4d1.m03;
    tmp[1] = paramMatrix4d1.m10; tmp[5] = paramMatrix4d1.m11; tmp[9] = paramMatrix4d1.m12; tmp[13] = paramMatrix4d1.m13;
    tmp[2] = paramMatrix4d1.m20; tmp[6] = paramMatrix4d1.m21; tmp[10] = paramMatrix4d1.m22; tmp[14] = paramMatrix4d1.m23;
    tmp[3] = paramMatrix4d1.m30; tmp[7] = paramMatrix4d1.m31; tmp[11] = paramMatrix4d1.m32; tmp[15] = paramMatrix4d1.m33;
    int i = HookFaceCollisFind(this.cppObj, tmp, paramArrayOfInt, tmp2);
    if (i != -1) {
      paramMatrix4d2.set(tmp2);
    }
    return i;
  }

  public int hookChunkFaceFind(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
    tmp[0] = paramMatrix4d1.m00; tmp[4] = paramMatrix4d1.m01; tmp[8] = paramMatrix4d1.m02; tmp[12] = paramMatrix4d1.m03;
    tmp[1] = paramMatrix4d1.m10; tmp[5] = paramMatrix4d1.m11; tmp[9] = paramMatrix4d1.m12; tmp[13] = paramMatrix4d1.m13;
    tmp[2] = paramMatrix4d1.m20; tmp[6] = paramMatrix4d1.m21; tmp[10] = paramMatrix4d1.m22; tmp[14] = paramMatrix4d1.m23;
    tmp[3] = paramMatrix4d1.m30; tmp[7] = paramMatrix4d1.m31; tmp[11] = paramMatrix4d1.m32; tmp[15] = paramMatrix4d1.m33;
    int i = HookChunkFaceFind(this.cppObj, tmp, tmp2);
    if (i != -1) {
      paramMatrix4d2.set(tmp2);
    }
    return i;
  }

  public int hookFaceFind(Matrix4d paramMatrix4d1, int[] paramArrayOfInt, Matrix4d paramMatrix4d2) {
    tmp[0] = paramMatrix4d1.m00; tmp[4] = paramMatrix4d1.m01; tmp[8] = paramMatrix4d1.m02; tmp[12] = paramMatrix4d1.m03;
    tmp[1] = paramMatrix4d1.m10; tmp[5] = paramMatrix4d1.m11; tmp[9] = paramMatrix4d1.m12; tmp[13] = paramMatrix4d1.m13;
    tmp[2] = paramMatrix4d1.m20; tmp[6] = paramMatrix4d1.m21; tmp[10] = paramMatrix4d1.m22; tmp[14] = paramMatrix4d1.m23;
    tmp[3] = paramMatrix4d1.m30; tmp[7] = paramMatrix4d1.m31; tmp[11] = paramMatrix4d1.m32; tmp[15] = paramMatrix4d1.m33;
    int i = HookFaceFind(this.cppObj, tmp, paramArrayOfInt, tmp2);
    if (i != -1) {
      paramMatrix4d2.set(tmp2);
    }
    return i;
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

  public int materialFindInChunk(String paramString, int paramInt) {
    return MaterialFindInChunk(this.cppObj, paramString, paramInt);
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

  public int[] hideSubTrees(String paramString)
  {
    this.chunkMap = null;
    return HideChunksInSubtrees(this.cppObj, paramString);
  }

  public int[] getSubTrees(String paramString)
  {
    this.chunkMap = null;
    return GetChunksInSubtrees(this.cppObj, paramString);
  }

  public int[] getSubTreesSpec(String paramString)
  {
    this.chunkMap = null;
    return GetChunksInSubtreesSpec(this.cppObj, paramString);
  }

  public float poseCRC() {
    return PoseCRC(this.cppObj);
  }

  public boolean isVisualRayHit(Point3d paramPoint3d, Vector3d paramVector3d, double paramDouble1, double paramDouble2, Matrix4d paramMatrix4d)
  {
    tmp[0] = paramMatrix4d.m00; tmp[4] = paramMatrix4d.m01; tmp[8] = paramMatrix4d.m02; tmp[12] = paramMatrix4d.m03;
    tmp[1] = paramMatrix4d.m10; tmp[5] = paramMatrix4d.m11; tmp[9] = paramMatrix4d.m12; tmp[13] = paramMatrix4d.m13;
    tmp[2] = paramMatrix4d.m20; tmp[6] = paramMatrix4d.m21; tmp[10] = paramMatrix4d.m22; tmp[14] = paramMatrix4d.m23;
    tmp[3] = paramMatrix4d.m30; tmp[7] = paramMatrix4d.m31; tmp[11] = paramMatrix4d.m32; tmp[15] = paramMatrix4d.m33;

    arrDouble6[0] = paramPoint3d.x;
    arrDouble6[1] = paramPoint3d.y;
    arrDouble6[2] = paramPoint3d.z;

    arrDouble6[3] = paramVector3d.x;
    arrDouble6[4] = paramVector3d.y;
    arrDouble6[5] = paramVector3d.z;

    return isVisualRayHit(this.cppObj, arrDouble6, paramDouble1, paramDouble2, tmp) != 0;
  }

  public int ApplyDecal_test(float paramFloat1, float paramFloat2, boolean paramBoolean, Loc paramLoc, int paramInt)
  {
    paramLoc.getMatrix(m4);
    tmp[0] = m4.m00; tmp[4] = m4.m01; tmp[8] = m4.m02; tmp[12] = m4.m03;
    tmp[1] = m4.m10; tmp[5] = m4.m11; tmp[9] = m4.m12; tmp[13] = m4.m13;
    tmp[2] = m4.m20; tmp[6] = m4.m21; tmp[10] = m4.m22; tmp[14] = m4.m23;
    tmp[3] = m4.m30; tmp[7] = m4.m31; tmp[11] = m4.m32; tmp[15] = m4.m33;

    float[] arrayOfFloat = new float[3];
    arrayOfFloat[0] = 0.0F;
    arrayOfFloat[1] = 0.0F;
    arrayOfFloat[2] = 0.0F;

    int[] arrayOfInt = new int[chunks()];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = i;
    }

    i = ApplyDecal(this.cppObj, rnd.nextInt(0, 2), paramFloat1, paramFloat2, paramBoolean ? 1 : 0, tmp, arrayOfFloat, paramInt, arrayOfInt, arrayOfInt.length);

    System.out.println("-- applyDec: " + i + "(chIdx:" + paramInt + ")");

    return i;
  }

  public int grabDecalsFromChunk(int paramInt) {
    if (paramInt < 0) {
      return 0;
    }
    return GrabDecalsFromChunk(this.cppObj, paramInt);
  }

  public int applyGrabbedDecalsToChunk(int paramInt) {
    if (paramInt < 0) {
      return 0;
    }
    return ApplyGrabbedDecalsToChunk(this.cppObj, paramInt);
  }

  public HierMesh(String paramString)
  {
    super(0);
    if (paramString == null)
      throw new GObjException("Meshname is empty");
    this.cppObj = Load(paramString);

    if (this.cppObj == 0)
      throw new GObjException("HierMesh " + paramString + " not created");
    this.collisionR = CollisionR(this.cppObj);
    this.visibilityR = VisibilityR(this.cppObj);
    Pre.load(paramString);
  }

  public HierMesh(HierMesh paramHierMesh, int paramInt) {
    super(0);
    if (paramHierMesh == null) {
      throw new GObjException("HierMesh is empty");
    }
    paramHierMesh.setCurChunk(paramInt);
    this.cppObj = LoadSubtree(paramHierMesh.cppObj);

    if (this.cppObj == 0) {
      throw new GObjException("HierMesh (sub) not created");
    }
    this.collisionR = CollisionR(this.cppObj);
    this.visibilityR = VisibilityR(this.cppObj);
  }

  public HierMesh(HierMesh paramHierMesh, int paramInt, Loc paramLoc)
  {
    super(0);
    if (paramHierMesh == null) {
      throw new GObjException("HierMesh is empty");
    }
    paramHierMesh.setCurChunk(paramInt);

    paramLoc.getMatrix(m4);
    tmp[0] = m4.m00; tmp[4] = m4.m01; tmp[8] = m4.m02; tmp[12] = m4.m03;
    tmp[1] = m4.m10; tmp[5] = m4.m11; tmp[9] = m4.m12; tmp[13] = m4.m13;
    tmp[2] = m4.m20; tmp[6] = m4.m21; tmp[10] = m4.m22; tmp[14] = m4.m23;
    tmp[3] = m4.m30; tmp[7] = m4.m31; tmp[11] = m4.m32; tmp[15] = m4.m33;
    this.cppObj = LoadSubtreeLoc(paramHierMesh.cppObj, tmp);

    if (this.cppObj == 0) {
      throw new GObjException("HierMesh (sub, loc) not created");
    }
    this.collisionR = CollisionR(this.cppObj);
    this.visibilityR = VisibilityR(this.cppObj); } 
  private native void GetChunkCurVisBoundBox(int paramInt, float[] paramArrayOfFloat);

  private native int Load(String paramString);

  private native int LoadSubtree(int paramInt);

  private native int LoadSubtreeLoc(int paramInt, double[] paramArrayOfDouble);

  private native int[] HideChunksInSubtrees(int paramInt, String paramString);

  private native int[] GetChunksInSubtrees(int paramInt, String paramString);

  private native int[] GetChunksInSubtreesSpec(int paramInt, String paramString);

  private native void SetPosXYZATK(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3);

  private native int PreRender(int paramInt);

  private native void Render(int paramInt);

  private native void RenderShadowProjective(int paramInt);

  private native void RenderShadowVolume(int paramInt);

  private native void RenderShadowVolumeHQ(int paramInt);

  private native int DetectCollision(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, double[] paramArrayOfDouble2);

  private native int DetectCollisionMesh(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, double[] paramArrayOfDouble2);

  private native float DetectCollisionLine(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  private native int DetectCollisionLineMulti(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  private native float DetectCollisionQuad(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native int DetectCollisionQuadMultiH(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native int DetectCollisionPoint(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native float DetectCollisionPlane(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native float VisibilityR(int paramInt);

  private native float CollisionR(int paramInt);

  private native float GetUniformMaxDist(int paramInt);

  private native int Chunks(int paramInt);

  private native void SetCurChunk(int paramInt1, int paramInt2);

  private native int SetCurChunkByName(int paramInt, String paramString);

  private native int ChunkFind(int paramInt, String paramString);

  private native int ChunkFindCheck(int paramInt, String paramString);

  private native String ChunkName(int paramInt);

  private native float GetChunkVisibilityR(int paramInt);

  private native int GetChunkVisibility(int paramInt);

  private native void SetChunkVisibility(int paramInt1, int paramInt2);

  private native void SetChunkAngles(int paramInt, float[] paramArrayOfFloat);

  private native void SetChunkLocate(int paramInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);

  private native int Frames(int paramInt);

  private native void SetFrame(int paramInt1, int paramInt2);

  private native void SetFrame(int paramInt1, int paramInt2, int paramInt3, float paramFloat);

  private native void SetFrameFromRange(int paramInt1, int paramInt2, int paramInt3, float paramFloat);

  private native int Hooks(int paramInt);

  private native int HookFind(int paramInt, String paramString);

  private native String HookName(int paramInt1, int paramInt2);

  private native void HookMatrix(int paramInt1, int paramInt2, double[] paramArrayOfDouble);

  private native void GetChunkLTM(int paramInt, double[] paramArrayOfDouble);

  private native int ChunkByHookNamed(int paramInt1, int paramInt2);

  private native int HookFaceCollisFind(int paramInt, double[] paramArrayOfDouble1, int[] paramArrayOfInt, double[] paramArrayOfDouble2);

  private native int HookFaceFind(int paramInt, double[] paramArrayOfDouble1, int[] paramArrayOfInt, double[] paramArrayOfDouble2);

  private native int HookChunkFaceFind(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);

  private native void HookFaceMatrix(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble);

  private native int Materials(int paramInt);

  private native int MaterialFind(int paramInt1, String paramString, int paramInt2, int paramInt3);

  private native int MaterialFindInChunk(int paramInt1, String paramString, int paramInt2);

  private native Object Material(int paramInt1, int paramInt2);

  private native void MaterialReplace(int paramInt1, int paramInt2, int paramInt3);

  private native void MaterialReplace(int paramInt1, int paramInt2, String paramString);

  private native void MaterialReplace(int paramInt1, String paramString, int paramInt2);

  private native void MaterialReplace(int paramInt, String paramString1, String paramString2);

  private native float PoseCRC(int paramInt);

  private native int isVisualRayHit(int paramInt, double[] paramArrayOfDouble1, double paramDouble1, double paramDouble2, double[] paramArrayOfDouble2);

  private native int ApplyDecal(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3, double[] paramArrayOfDouble, float[] paramArrayOfFloat, int paramInt4, int[] paramArrayOfInt, int paramInt5);

  private native int GrabDecalsFromChunk(int paramInt1, int paramInt2);

  private native int ApplyGrabbedDecalsToChunk(int paramInt1, int paramInt2);

  private native void RenderChunkMirror(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3);

  private static native void RenderShadowPairs(int paramInt, int[] paramArrayOfInt);

  static { GObj.loadNative();
  }

  class ChunkState
  {
    public boolean bExist;
    public int indx;
    public boolean bVisible;
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    public float roll;

    public ChunkState(String arg2)
    {
      try
      {
        String str;
        this.indx = HierMesh.this.chunkFind(str);
      } catch (Exception localException) {
        this.bExist = false;
        return;
      }
      this.bExist = true;
      HierMesh.this.setCurChunk(this.indx);
      this.bVisible = HierMesh.this.isChunkVisible();
      HierMesh.this.getChunkLocObj(HierMesh._chunkLoc);
      this.x = (float)HierMesh._chunkLoc.getX(); this.y = (float)HierMesh._chunkLoc.getY(); this.z = (float)HierMesh._chunkLoc.getZ();
      this.yaw = HierMesh._chunkLoc.getOrient().getYaw();
      this.pitch = HierMesh._chunkLoc.getOrient().getPitch();
      this.roll = HierMesh._chunkLoc.getOrient().getRoll();
    }
  }
}