package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.util.HashMapExt;

public class MeshShared extends Mesh
{
  private static HashMapExt shared = new HashMapExt();
  private static HashMapExt meshes = new HashMapExt();
  public static final int MAX_RENDER_ARRAY = 2048;
  private static int[] arrayCppObjs = new int[2048];
  private static double[] arrayXYZ = new double[6144];
  private static float[] arrayATK = new float[6144];
  private static int arraySize = 0;

  public void materialReplace(int paramInt, Mat paramMat)
  {
  }

  public void materialReplace(int paramInt, String paramString)
  {
  }

  public void materialReplace(String paramString, Mat paramMat)
  {
  }

  public void materialReplace(String paramString1, String paramString2)
  {
  }

  public static MeshShared get(String paramString)
  {
    MeshShared localMeshShared = (MeshShared)shared.get(paramString);
    if (localMeshShared == null) {
      localMeshShared = new MeshShared(paramString);
      shared.put(paramString, localMeshShared);
    }
    return localMeshShared;
  }

  public MeshShared(String paramString) {
    super(0);
    Mesh localMesh = (Mesh)meshes.get(paramString);
    if (localMesh == null) {
      localMesh = new Mesh(paramString);
      meshes.put(paramString, localMesh);
    }

    this.jdField_cppObj_of_type_Int = localMesh.cppObject();
    this.collisionR = localMesh.collisionR();
    this.visibilityR = localMesh.visibilityR();
    this.jdField_bAllowsSimpleRendering_of_type_Boolean = localMesh.jdField_bAllowsSimpleRendering_of_type_Boolean;
  }
  protected void finalize() {
  }
  public void destroy() {
  }
  public static void clearAll() { shared.clear();
    meshes.clear();
  }

  public static void clearRenderArray()
  {
    arraySize = 0; } 
  public static int sizeRenderArray() { return arraySize; } 
  public boolean putToRenderArray(Loc paramLoc) {
    if ((!this.jdField_bAllowsSimpleRendering_of_type_Boolean) || (arraySize >= 2048)) {
      return false;
    }
    arrayCppObjs[arraySize] = this.jdField_cppObj_of_type_Int;
    Point3d localPoint3d = paramLoc.getPoint();
    Orient localOrient = paramLoc.getOrient();
    int i = 3 * arraySize;
    arrayXYZ[(i + 0)] = localPoint3d.x;
    arrayXYZ[(i + 1)] = localPoint3d.y;
    arrayXYZ[(i + 2)] = localPoint3d.z;
    arrayATK[(i + 0)] = localOrient.getAzimut();
    arrayATK[(i + 1)] = localOrient.getTangage();
    arrayATK[(i + 2)] = localOrient.getKren();
    arraySize += 1;
    return true;
  }
  public static void renderArray(boolean paramBoolean) {
    if (arraySize > 0) {
      RenderArray(paramBoolean, arraySize, arrayCppObjs, arrayXYZ, arrayATK);
    }

    arraySize = 0;
  }
  public static void renderArrayShadowProjective() {
    if (arraySize > 0) {
      RenderArrayShadowProjective(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
    }

    arraySize = 0;
  }
  public static void renderArrayShadowVolume() {
    if (arraySize > 0) {
      RenderArrayShadowVolume(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
    }

    arraySize = 0;
  }
  public static void renderArrayShadowVolumeHQ() {
    if (arraySize > 0) {
      RenderArrayShadowVolumeHQ(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
    }

    arraySize = 0;
  }

  private static native void RenderArray(boolean paramBoolean, int paramInt, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat);

  private static native void RenderArrayShadowProjective(int paramInt, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat);

  private static native void RenderArrayShadowVolume(int paramInt, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat);

  private static native void RenderArrayShadowVolumeHQ(int paramInt, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat);
}