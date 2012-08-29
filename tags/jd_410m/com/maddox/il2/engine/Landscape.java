package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16;
import java.util.Map.Entry;

public class Landscape
{
  private int month;
  private int day;
  public LandConf config;
  public static final float WoodH = 16.0F;
  public static final int TILE = 8;
  public static final int TILEMASK = 7;
  private static boolean bNoWater = false;
  static final int CH_RATIO = 4;
  static final int CTILE = 32;
  static final int MTILE = 256;
  static final int MTILEMASK = 255;
  public static final float PixelSize = 200.0F;
  public static final float PixPerMeter = 0.005F;
  private static float[] EQNBuf = new float[4];
  private static double[] EQNBufD = new double[4];

  private static float[] _RayStart = new float[3];
  private static float[] _RayEnd = new float[3];
  private static float[] _RayHit = new float[3];
  private static double[] _RayHitD = new double[6];
  String MapName;
  private Vector3f sunRise;
  private static float[] _sunmoon = new float[6];
  public static final int MESH_STEP = 200;
  public static final float MESH_STEP_MUL = 0.005F;
  private static HashMapXY16 meshMapXY;
  private static HashMapExt meshMapRay = new HashMapExt();

  public Landscape()
  {
    this.config = new LandConf();

    this.MapName = "";

    this.sunRise = new Vector3f();
  }

  public final int WORLD2PIXX(double paramDouble)
  {
    return (int)(paramDouble * 0.00499999988824129D); } 
  public final int WORLD2PIXX(float paramFloat) { return (int)(paramFloat * 0.005F); } 
  public final int WORLD2PIXY(double paramDouble) { return (int)(getSizeYpix() - 1.0F - paramDouble * 0.00499999988824129D); } 
  public final int WORLD2PIXY(float paramFloat) { return (int)(getSizeYpix() - 1.0F - paramFloat * 0.005F); } 
  private final float WORLD2PIXXf(float paramFloat) {
    return paramFloat * 0.005F; } 
  private final float WORLD2PIXYf(float paramFloat) { return getSizeYpix() - 1.0F - paramFloat * 0.005F; } 
  public final float PIX2WORLDX(float paramFloat) { return paramFloat * 200.0F; } 
  public final float PIX2WORLDY(float paramFloat) { return (getSizeYpix() - 1 - paramFloat) * 200.0F; } 
  public static native int getPixelMapT(int paramInt1, int paramInt2);

  public static native void setPixelMapT(int paramInt1, int paramInt2, int paramInt3);

  public static native int getPixelMapH(int paramInt1, int paramInt2);

  public static native void setPixelMapH(int paramInt1, int paramInt2, int paramInt3);

  public final boolean isWater(double paramDouble1, double paramDouble2) { if (bNoWater) return false;
    return isWater((float)paramDouble1, (float)paramDouble2); } 
  private static final native boolean isWater(float paramFloat1, float paramFloat2);

  public static final native int estimateNoWater(int paramInt1, int paramInt2, int paramInt3);

  public static final native int getSizeXpix();

  public static final native int getSizeYpix();

  public float getSizeX() {
    return 200.0F * getSizeXpix(); } 
  public float getSizeY() { return 200.0F * getSizeYpix(); }

  public final double Hmax(double paramDouble1, double paramDouble2) {
    return Hmax((float)paramDouble1, (float)paramDouble2); } 
  public final double Hmin(double paramDouble1, double paramDouble2) { return Hmin((float)paramDouble1, (float)paramDouble2); }

  public static final float H(float paramFloat1, float paramFloat2) {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cH(paramFloat1, paramFloat2);
        float f2 = localMeshCell.m.cHQ(paramFloat1, paramFloat2);
        return f2 > f1 ? f2 : f1;
      }
    }
    return cH(paramFloat1, paramFloat2);
  }
  public static final float Hmax(float paramFloat1, float paramFloat2) {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        return localMeshCell.hMax;
      }
    }
    return cHmax(paramFloat1, paramFloat2);
  }
  public static final float Hmin(float paramFloat1, float paramFloat2) {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        return localMeshCell.hMin;
      }
    }
    return cHmin(paramFloat1, paramFloat2); } 
  private static final native float cH(float paramFloat1, float paramFloat2);

  private static final native float cHmax(float paramFloat1, float paramFloat2);

  private static final native float cHmin(float paramFloat1, float paramFloat2);

  public final double HQ_Air(double paramDouble1, double paramDouble2) { return HQ_Air((float)paramDouble1, (float)paramDouble2); }

  public static final float HQ_Air(float paramFloat1, float paramFloat2)
  {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ_Air(paramFloat1, paramFloat2);
        float f2 = localMeshCell.m.cHQ(paramFloat1, paramFloat2);
        return f2 > f1 ? f2 : f1;
      }
    }
    return cHQ_Air(paramFloat1, paramFloat2);
  }
  private static final native float cHQ_Air(float paramFloat1, float paramFloat2);

  public final double HQ_ForestHeightHere(double paramDouble1, double paramDouble2) {
    return HQ_forestHeightHere((float)paramDouble1, (float)paramDouble2);
  }

  public static final float HQ_forestHeightHere(float paramFloat1, float paramFloat2)
  {
    return cHQ_forestHeightHere(paramFloat1, paramFloat2);
  }
  private static final native float cHQ_forestHeightHere(float paramFloat1, float paramFloat2);

  public final int HQ_RoadTypeHere(double paramDouble1, double paramDouble2) {
    return HQRoadTypeHere((float)paramDouble1, (float)paramDouble2);
  }
  public static final native int HQRoadTypeHere(float paramFloat1, float paramFloat2);

  public final double HQ(double paramDouble1, double paramDouble2) {
    return HQ((float)paramDouble1, (float)paramDouble2);
  }

  public static final float HQ(float paramFloat1, float paramFloat2) {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ(paramFloat1, paramFloat2);
        float f2 = localMeshCell.m.cHQ(paramFloat1, paramFloat2);
        return f2 > f1 ? f2 : f1;
      }
    }
    return cHQ(paramFloat1, paramFloat2);
  }

  private static final native float cHQ(float paramFloat1, float paramFloat2);

  public final void N(double paramDouble1, double paramDouble2, Vector3f paramVector3f)
  {
    if (meshMapXY != null) {
      int i = (int)(paramDouble1 * 0.00499999988824129D);
      int j = (int)(paramDouble2 * 0.00499999988824129D);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ((float)paramDouble1, (float)paramDouble2);
        float f2 = localMeshCell.m.cHQ(paramDouble1, paramDouble2);
        if ((f2 >= f1) && 
          (localMeshCell.m.cNormal(paramDouble1, paramDouble2, EQNBuf))) {
          paramVector3f.set(EQNBuf);
          return;
        }
      }

    }

    EQNBuf[0] = (float)paramDouble1; EQNBuf[1] = (float)paramDouble2; EQNBuf[2] = -1.0F;
    cEQN(EQNBuf);
    paramVector3f.set(EQNBuf);
  }

  public final void N(double paramDouble1, double paramDouble2, Vector3d paramVector3d) {
    if (meshMapXY != null) {
      int i = (int)(paramDouble1 * 0.00499999988824129D);
      int j = (int)(paramDouble2 * 0.00499999988824129D);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ((float)paramDouble1, (float)paramDouble2);
        float f2 = localMeshCell.m.cHQ(paramDouble1, paramDouble2);
        if ((f2 >= f1) && 
          (localMeshCell.m.cNormal(paramDouble1, paramDouble2, EQNBuf))) {
          paramVector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
          return;
        }
      }

    }

    EQNBuf[0] = (float)paramDouble1; EQNBuf[1] = (float)paramDouble2; EQNBuf[2] = -1.0F;
    cEQN(EQNBuf);
    paramVector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
  }

  public final void N(float paramFloat1, float paramFloat2, Vector3f paramVector3f)
  {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ(paramFloat1, paramFloat2);
        float f2 = localMeshCell.m.cHQ(paramFloat1, paramFloat2);
        if ((f2 >= f1) && 
          (localMeshCell.m.cNormal(paramFloat1, paramFloat2, EQNBuf))) {
          paramVector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
          return;
        }
      }

    }

    EQNBuf[0] = paramFloat1; EQNBuf[1] = paramFloat2; EQNBuf[2] = -1.0F;
    cEQN(EQNBuf);
    paramVector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
  }

  public final double EQN(double paramDouble1, double paramDouble2, Vector3d paramVector3d)
  {
    if (meshMapXY != null) {
      int i = (int)(paramDouble1 * 0.00499999988824129D);
      int j = (int)(paramDouble2 * 0.00499999988824129D);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ((float)paramDouble1, (float)paramDouble2);
        float f2 = localMeshCell.m.cHQ(paramDouble1, paramDouble2);
        if ((f2 >= f1) && 
          (localMeshCell.m.cPlane(paramDouble1, paramDouble2, EQNBufD))) {
          paramVector3d.set(EQNBufD[0], EQNBufD[1], EQNBufD[2]);
          return EQNBufD[3];
        }
      }

    }

    EQNBuf[0] = (float)paramDouble1; EQNBuf[1] = (float)paramDouble2; EQNBuf[2] = 1.0F;
    cEQN(EQNBuf);
    paramVector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
    return EQNBuf[3];
  }

  public final float EQN(float paramFloat1, float paramFloat2, Vector3f paramVector3f)
  {
    if (meshMapXY != null) {
      int i = (int)(paramFloat1 * 0.005F);
      int j = (int)(paramFloat2 * 0.005F);
      MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
      if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) {
        float f1 = cHQ(paramFloat1, paramFloat2);
        float f2 = localMeshCell.m.cHQ(paramFloat1, paramFloat2);
        if ((f2 >= f1) && 
          (localMeshCell.m.cPlane(paramFloat1, paramFloat2, EQNBufD))) {
          paramVector3f.set((float)EQNBufD[0], (float)EQNBufD[1], (float)EQNBufD[2]);
          return (float)EQNBufD[3];
        }
      }

    }

    EQNBuf[0] = paramFloat1; EQNBuf[1] = paramFloat2; EQNBuf[2] = 1.0F;
    cEQN(EQNBuf);
    paramVector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
    return EQNBuf[3];
  }

  public static boolean rayHitHQ(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3) {
    _RayStart[0] = (float)paramPoint3d1.x; _RayStart[1] = (float)paramPoint3d1.y; _RayStart[2] = (float)paramPoint3d1.z;
    _RayEnd[0] = (float)paramPoint3d2.x; _RayEnd[1] = (float)paramPoint3d2.y; _RayEnd[2] = (float)paramPoint3d2.z;
    boolean bool = cRayHitHQ(_RayStart, _RayEnd, _RayHit);
    paramPoint3d3.x = _RayHit[0]; paramPoint3d3.y = _RayHit[1]; paramPoint3d3.z = _RayHit[2];

    if (meshMakeMapRay(paramPoint3d1, paramPoint3d2)) {
      float f1 = 1.0F;
      if (bool) {
        double d1 = paramPoint3d1.distance(paramPoint3d2);
        double d2 = paramPoint3d1.distance(paramPoint3d3);
        if (d1 > 0.0D)
          f1 = (float)(d2 / d1);
      }
      _RayHitD[0] = paramPoint3d1.x; _RayHitD[1] = paramPoint3d1.y; _RayHitD[2] = paramPoint3d1.z;
      _RayHitD[3] = paramPoint3d2.x; _RayHitD[4] = paramPoint3d2.y; _RayHitD[5] = paramPoint3d2.z;
      int i = 0;
      Map.Entry localEntry = meshMapRay.nextEntry(null);
      while (localEntry != null) {
        ActorLandMesh localActorLandMesh = (ActorLandMesh)localEntry.getKey();
        float f2 = localActorLandMesh.cRayHit(_RayHitD);
        if ((f2 >= 0.0F) && (f2 < f1)) {
          f1 = f2;
          i = 1;
        }
        localEntry = meshMapRay.nextEntry(localEntry);
      }
      meshMapRay.clear();
      if (i != 0) {
        paramPoint3d3.interpolate(paramPoint3d1, paramPoint3d2, f1);
        bool = true;
      }
    }

    return bool;
  }

  private static final native void cEQN(float[] paramArrayOfFloat);

  public void setRoadsFunDrawing(boolean paramBoolean)
  {
    cSetRoadsFunDrawing(paramBoolean);
  }

  public static native int getFogAverageRGBA();

  public static native float getDynamicFogAlpha();

  public static native int getDynamicFogRGB();

  public void renderBridgeRoad(Mat paramMat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat)
  {
    cRenderBridgeRoad(paramMat.cppObject(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramFloat);
  }

  public void LoadMap(String paramString, int[] paramArrayOfInt)
    throws Exception
  {
    LoadMap(paramString, paramArrayOfInt, true);
  }

  public void LoadMap(String paramString, int[] paramArrayOfInt, int paramInt1, int paramInt2)
    throws Exception
  {
    LoadMap(paramString, paramArrayOfInt, true, paramInt1, paramInt2);
  }

  public void UnLoadMap() {
    if (meshMapXY != null)
      meshMapXY.clear();
    meshMapXY = null;
    cUnloadMap();
  }

  public void ReLoadMap()
    throws Exception
  {
    if ("".equals(this.MapName)) return;
    LoadMap(this.MapName, null, false, this.month, this.day);
  }

  private void LoadMap(String paramString, int[] paramArrayOfInt, boolean paramBoolean)
    throws Exception
  {
    LoadMap(paramString, paramArrayOfInt, paramBoolean, this.config.month, 15);
  }

  private void LoadMap(String paramString, int[] paramArrayOfInt, boolean paramBoolean, int paramInt1, int paramInt2) throws Exception
  {
    this.month = paramInt1;
    this.day = paramInt2;

    if (meshMapXY != null)
      meshMapXY.clear();
    meshMapXY = null;
    int i = 0;
    if (paramArrayOfInt != null) i = paramArrayOfInt.length / 2;
    this.MapName = paramString;
    this.config.set("maps/" + this.MapName);

    bNoWater = "ICE".equals(this.config.zutiWaterState);

    World.Sun().resetCalendar();
    World.Sun().setAstronomic(this.config.declin, this.month, this.day, World.getTimeofDay());
    if (Config.isUSE_RENDER()) {
      setAstronomic(this.config.declin, this.month, this.day, World.getTimeofDay(), World.Sun().moonPhase);
    }
    if (!cLoadMap(paramString, paramArrayOfInt, i, paramBoolean))
      throw new RuntimeException("Landscape '" + paramString + "' loading error");
  }

  public void cubeFullUpdate() {
    Actor localActor = Actor.getByName("camera");
    if (Actor.isValid(localActor))
      preRender((float)localActor.pos.getAbsPoint().z, true);
  }

  public void cubeFullUpdate(float paramFloat) {
    preRender(paramFloat, true);
  }

  public int nightTime(float paramFloat, int paramInt)
  {
    float f2 = (float)Math.toRadians(90 - this.config.declin);
    float f3 = (float)Math.cos(f2);
    float f4 = (float)Math.sin(f2);
    float f1 = (float)Math.toRadians(this.config.month * 30 + 15 - 80);

    int i = 0;
    int j = 0;
    while (true) {
      float f5 = 6.283186F * paramFloat / 24.0F;

      float f6 = (float)Math.sin(f5);
      float f7 = (float)Math.cos(f5);
      float f8 = (float)Math.sin((float)Math.toRadians(22.5D) * (float)Math.sin(f1));

      this.sunRise.set(f6, f7 * f3 + f8 * f4, f8 * f3 - f7 * f4);
      this.sunRise.normalize();
      int k = 600;
      if (j + k > paramInt) k = paramInt - j;
      if (k == 0) break;
      j += k;
      if (this.sunRise.z < -0.1F) i += k;
      paramFloat += k / 3600.0F;
      if (paramFloat >= 24.0F) paramFloat -= 24.0F;
    }
    return i;
  }

  public void preRender(float paramFloat, boolean paramBoolean) {
    Sun localSun = World.Sun();
    if ((paramBoolean) || (cIsCubeUpdated()))
    {
      localSun.setAstronomic(this.config.declin, this.month, this.day, World.getTimeofDay());
      setAstronomic(this.config.declin, this.month, this.day, World.getTimeofDay(), localSun.moonPhase);
    }

    _sunmoon[0] = localSun.ToSun.x; _sunmoon[1] = localSun.ToSun.y; _sunmoon[2] = localSun.ToSun.z;
    _sunmoon[3] = localSun.ToMoon.x; _sunmoon[4] = localSun.ToMoon.y; _sunmoon[5] = localSun.ToMoon.z;

    cPreRender(paramFloat / 2.0F, paramBoolean, _sunmoon);
  }

  public int render0(boolean paramBoolean)
  {
    return cRender0(paramBoolean ? 1 : 0);
  }
  public void render1(boolean paramBoolean) {
    cRender1(paramBoolean ? 1 : 0);
  }
  public int ObjectsReflections_Begin(int paramInt) {
    return cRefBeg(paramInt); } 
  public void ObjectsReflections_End() { cRefEnd();
  }

  public static native int getFogRGBA(float paramFloat1, float paramFloat2, float paramFloat3);

  public static native int ComputeVisibilityOfLandCells(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int[] paramArrayOfInt);

  public static native int Compute2DBoundBoxOfVisibleLand(float paramFloat, int paramInt1, int paramInt2, float[] paramArrayOfFloat);

  public static native void MarkStaticActorsCells(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static native void MarkActorCellWithTrees(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4);

  private static native boolean cIsCubeUpdated();

  private static native void cSetRoadsFunDrawing(boolean paramBoolean);

  private static native void cPreRender(float paramFloat, boolean paramBoolean, float[] paramArrayOfFloat);

  private static native int cRender0(int paramInt);

  private static native void cRender1(int paramInt);

  private static native boolean cLoadMap(String paramString, int[] paramArrayOfInt, int paramInt, boolean paramBoolean);

  private static native void cUnloadMap();

  private static native void cRenderBridgeRoad(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat);

  private static native int cRefBeg(int paramInt);

  private static native int cRefEnd();

  private static native boolean cRayHitHQ(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3);

  private static native void setAstronomic(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2);

  public static boolean isExistMeshs()
  {
    return meshMapXY != null;
  }
  public static boolean isExistMesh(int paramInt1, int paramInt2) { return meshMapXY.get(paramInt2, paramInt1) != null; }

  public static void meshAdd(ActorLandMesh paramActorLandMesh)
  {
    double d1 = paramActorLandMesh.mesh().visibilityR();
    Point3d localPoint3d = paramActorLandMesh.pos.getAbsPoint();
    int i = (int)((localPoint3d.x - d1 - 200.0D) / 200.0D);
    int j = (int)((localPoint3d.x + d1 + 200.0D) / 200.0D);
    int k = (int)((localPoint3d.y - d1 - 200.0D) / 200.0D);
    int m = (int)((localPoint3d.y + d1 + 200.0D) / 200.0D);
    float f1 = 10000.0F;
    float f2 = -10000.0F;
    for (int n = k; n < m; n++)
      for (int i1 = i; i1 < j; i1++) {
        int i2 = 0;
        double d2 = 25.0D;
        for (int i3 = 1; i3 < 8; i3++) {
          double d3 = n * 200.0D + i3 * d2;
          for (int i4 = 1; i4 < 8; i4++) {
            double d4 = i1 * 200.0D + i4 * d2;
            float f7 = paramActorLandMesh.cHQ((float)d4, (float)d3);
            if (f7 > -10000.0F) {
              i2 = 1;
              if (f7 > f2) f2 = f7;
              if (f7 >= f1) continue; f1 = f7;
            }
          }
        }
        if (i2 != 0) {
          if (meshMapXY == null)
            meshMapXY = new HashMapXY16();
          float f3 = i1 * 200.0F + 100.0F;
          float f4 = n * 200.0F + 100.0F;
          float f5 = cHmax(f3, f4);
          float f6 = cHmax(f3, f4);
          if (f6 > f2) f2 = f6;
          if (f5 > f1) f1 = f5;
          meshMapXY.put(n, i1, new MeshCell(paramActorLandMesh, f1, f2));
        }
      }
  }

  private static boolean meshMakeMapRay(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    if (meshMapXY == null) return false;
    int i = (int)paramPoint3d1.x / 200;
    int j = (int)paramPoint3d1.y / 200;
    int k = Math.abs((int)paramPoint3d2.x / 200 - i) + Math.abs((int)paramPoint3d2.y / 200 - j) + 1;
    if (k > 1000) {
      return false;
    }
    MeshCell localMeshCell = (MeshCell)meshMapXY.get(j, i);
    if ((localMeshCell != null) && (Actor.isValid(localMeshCell.m))) meshMapRay.put(localMeshCell.m, localMeshCell.m);
    if (k > 1) {
      int m = 1; if (paramPoint3d2.x < paramPoint3d1.x) m = -1;
      int n = 1; if (paramPoint3d2.y < paramPoint3d1.y) n = -1;
      double d1;
      double d2;
      int i1;
      if (Math.abs(paramPoint3d2.x - paramPoint3d1.x) >= Math.abs(paramPoint3d2.y - paramPoint3d1.y)) {
        d1 = Math.abs(paramPoint3d1.y % 200.0D);
        d2 = 200.0D * (paramPoint3d2.y - paramPoint3d1.y) / Math.abs(paramPoint3d2.x - paramPoint3d1.x);
        if (d2 >= 0.0D)
          for (i1 = 1; i1 < k; i1++) {
            if (d1 < 200.0D) { i += m; d1 += d2; } else {
              j += n; d1 -= 200.0D;
            }localMeshCell = (MeshCell)meshMapXY.get(j, i);
            if ((localMeshCell == null) || (!Actor.isValid(localMeshCell.m))) continue; meshMapRay.put(localMeshCell.m, localMeshCell.m);
          }
        else
          for (i1 = 1; i1 < k; i1++) {
            if (d1 > 0.0D) { i += m; d1 += d2; } else {
              j += n; d1 += 200.0D;
            }localMeshCell = (MeshCell)meshMapXY.get(j, i);
            if ((localMeshCell == null) || (!Actor.isValid(localMeshCell.m))) continue; meshMapRay.put(localMeshCell.m, localMeshCell.m);
          }
      }
      else
      {
        d1 = Math.abs(paramPoint3d1.x % 200.0D);
        d2 = 200.0D * (paramPoint3d2.x - paramPoint3d1.x) / Math.abs(paramPoint3d2.y - paramPoint3d1.y);
        if (d2 >= 0.0D)
          for (i1 = 1; i1 < k; i1++) {
            if (d1 < 200.0D) { j += n; d1 += d2; } else {
              i += m; d1 -= 200.0D;
            }localMeshCell = (MeshCell)meshMapXY.get(j, i);
            if ((localMeshCell == null) || (!Actor.isValid(localMeshCell.m))) continue; meshMapRay.put(localMeshCell.m, localMeshCell.m);
          }
        else {
          for (i1 = 1; i1 < k; i1++) {
            if (d1 > 0.0D) { j += n; d1 += d2; } else {
              i += m; d1 += 200.0D;
            }localMeshCell = (MeshCell)meshMapXY.get(j, i);
            if ((localMeshCell == null) || (!Actor.isValid(localMeshCell.m))) continue; meshMapRay.put(localMeshCell.m, localMeshCell.m);
          }
        }
      }
    }

    return !meshMapRay.isEmpty();
  }

  static class MeshCell
  {
    ActorLandMesh m;
    float hMin;
    float hMax;

    MeshCell(ActorLandMesh paramActorLandMesh, float paramFloat1, float paramFloat2)
    {
      this.m = paramActorLandMesh; this.hMin = paramFloat1; this.hMax = paramFloat2;
    }
  }
}