package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.tools.Bridge;

public class PathFind
{
  public static final float ROAD_WIDTH = 20.0F;
  public static final int SIZE = 4;
  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  public static final int B = 3;
  public static final int MOVER_VEHICLE = 0;
  public static final int MOVER_TROOPER = 1;
  public static final int MOVER_SHIP = 2;
  public static final int MOVER_TRAIN = 3;
  public static final int MOVER_BUILDING = 4;
  private static PNodes[] startPoint = new PNodes[2];
  private static double[] startPosX = new double[2];
  private static double[] startPosY = new double[2];
  protected static Bridge[] b;
  protected static TexImage tShip;
  protected static TexImage tNoShip;
  protected static int curType = -1;

  private static boolean libLoaded = false;

  public static void unloadMap()
  {
    if (curType >= 0)
      UnloadMap();
    b = null;
    tShip = null;
    tNoShip = null;
    curType = -1;
  }

  private static void setMap(int paramInt) {
    int i = 0;
    if (paramInt == 2)
      i = curType != 2 ? 1 : 0;
    else if (paramInt == 3)
      i = curType < 0 ? 1 : 0;
    else {
      i = (curType == 2) || (curType < 0) ? 1 : 0;
    }
    if (i != 0) {
      TexImage localTexImage = tNoShip;
      if (paramInt == 2) localTexImage = tShip;
      if (curType >= 0)
        UnloadMap();
      setMap(localTexImage.image, localTexImage.sx, localTexImage.sy, b);
      curType = paramInt;
    }
  }

  private static void setMap(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Bridge[] paramArrayOfBridge)
  {
     tmp9_8 = null; startPoint[1] = tmp9_8; startPoint[0] = tmp9_8;
    int i = Engine.land().config.outsideMapCell;

    if ((paramArrayOfBridge == null) || (paramArrayOfBridge.length == 0)) {
      SetMap(paramArrayOfByte, i, paramInt1, paramInt2, 200.0F, 20.0F, null, 0);
    } else {
      int[] arrayOfInt = new int[paramArrayOfBridge.length * 5];
      int j = 0; for (int k = 0; k < paramArrayOfBridge.length; k++) {
        arrayOfInt[(j + 0)] = paramArrayOfBridge[k].x1;
        arrayOfInt[(j + 1)] = paramArrayOfBridge[k].y1;
        arrayOfInt[(j + 2)] = paramArrayOfBridge[k].x2;
        arrayOfInt[(j + 3)] = paramArrayOfBridge[k].y2;
        arrayOfInt[(j + 4)] = paramArrayOfBridge[k].type;

        j += 5;
      }

      SetMap(paramArrayOfByte, i, paramInt1, paramInt2, 200.0F, 20.0F, arrayOfInt, paramArrayOfBridge.length);
    }
  }

  public static boolean setMoverType(int paramInt) {
    setMap(paramInt);
    return SetMoverType(paramInt);
  }

  public static boolean setStartPoint(int paramInt, PNodes paramPNodes) {
    Point3d localPoint3d = paramPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    if ((paramPNodes == startPoint[paramInt]) && (localPoint3d.jdField_x_of_type_Double == startPosX[paramInt]) && (localPoint3d.jdField_y_of_type_Double == startPosY[paramInt])) return true;
    if (SetStartPoint(paramInt, (float)localPoint3d.jdField_x_of_type_Double, (float)localPoint3d.jdField_y_of_type_Double)) {
      startPosX[paramInt] = localPoint3d.jdField_x_of_type_Double;
      startPosY[paramInt] = localPoint3d.jdField_y_of_type_Double;
      startPoint[paramInt] = paramPNodes;
      return true;
    }
    startPoint[paramInt] = null;
    return false;
  }

  public static boolean isPointReacheable(int paramInt, PNodes paramPNodes)
  {
    Point3d localPoint3d = paramPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    return IsPointReacheable(paramInt, (float)localPoint3d.jdField_x_of_type_Double, (float)localPoint3d.jdField_y_of_type_Double);
  }

  public static float[] buildPath(int paramInt, PNodes paramPNodes) {
    Point3d localPoint3d = paramPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    float[] arrayOfFloat = BuildPath(paramInt, (float)localPoint3d.jdField_x_of_type_Double, (float)localPoint3d.jdField_y_of_type_Double);
    if (arrayOfFloat == null) return null;
    for (int i = 0; i < arrayOfFloat.length / 4; i++) {
      Engine.land(); arrayOfFloat[(i * 4 + 2)] = Landscape.HQ(arrayOfFloat[(i * 4 + 0)], arrayOfFloat[(i * 4 + 1)]);
    }
    return arrayOfFloat; } 
  private static native void SetMap(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, int[] paramArrayOfInt, int paramInt4);

  private static native void UnloadMap();

  private static native boolean SetMoverType(int paramInt);

  private static native boolean SetStartPoint(int paramInt, float paramFloat1, float paramFloat2);

  private static native boolean IsPointReacheable(int paramInt, float paramFloat1, float paramFloat2);

  private static native float[] BuildPath(int paramInt, float paramFloat1, float paramFloat2);

  public static native int codeAtPos(float paramFloat1, float paramFloat2);

  public static native int getV();

  public static native int getI();

  public static final void loadNative() { if (!libLoaded) {
      System.loadLibrary("pathfind");
      libLoaded = true;
    }
  }

  static
  {
    loadNative();
  }
}