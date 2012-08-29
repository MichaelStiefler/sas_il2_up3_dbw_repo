package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16Hash;
import java.util.Map.Entry;

public class LightEnvXY extends LightEnv
{
  public static final int STEP = 256;
  public static final int SMALL_SIZE = 64;
  private int countLights;
  private HashMapXY16Hash mapXY;
  private static Object flgCenter = new Object();

  public int prepareForRender(Point3d paramPoint3d, float paramFloat)
  {
    int i;
    int j;
    if (paramFloat <= 64.0F) {
      i = (int)paramPoint3d.jdField_x_of_type_Double / 256;
      j = (int)paramPoint3d.jdField_y_of_type_Double / 256;
      HashMapExt localHashMapExt1 = this.mapXY.get(j, i);
      if (localHashMapExt1 == null)
        return 0;
      LightPoint.curStamp += 1;
      this.countLights = 0;
      pointsStamped(localHashMapExt1);
    } else {
      i = (int)(paramPoint3d.jdField_x_of_type_Double - paramFloat) / 256;
      j = (int)(paramPoint3d.jdField_x_of_type_Double + paramFloat) / 256;
      int k = (int)(paramPoint3d.jdField_y_of_type_Double - paramFloat) / 256;
      int m = (int)(paramPoint3d.jdField_y_of_type_Double + paramFloat) / 256;
      LightPoint.curStamp += 1;
      this.countLights = 0;
      for (int n = k; n <= m; n++) {
        for (int i1 = i; i1 <= j; i1++) {
          HashMapExt localHashMapExt2 = this.mapXY.get(n, i1);
          if (localHashMapExt2 != null)
            pointsStamped(localHashMapExt2);
        }
      }
    }
    return this.countLights;
  }
  private void pointsStamped(HashMapExt paramHashMapExt) {
    Map.Entry localEntry = paramHashMapExt.nextEntry(null);
    while (localEntry != null) {
      LightPoint localLightPoint = (LightPoint)localEntry.getKey();
      if (localLightPoint.stamp != LightPoint.curStamp) {
        localLightPoint.stamp = LightPoint.curStamp;
        localLightPoint.addToRender();
        this.countLights += 1;
      }
      localEntry = paramHashMapExt.nextEntry(localEntry);
    }
  }

  protected void changedPos(LightPoint paramLightPoint, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if ((paramLightPoint.I <= 0.0F) || (paramLightPoint.R <= 0.0F)) return;
    remove(paramLightPoint);
    add(paramLightPoint, (int)paramDouble1, (int)paramDouble2, paramLightPoint.I, paramLightPoint.R);
  }

  protected void changedEmit(LightPoint paramLightPoint, float paramFloat1, float paramFloat2) {
    remove(paramLightPoint);
    if ((paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F)) return;
    add(paramLightPoint, paramLightPoint.IX, paramLightPoint.IY, paramFloat1, paramFloat2);
  }

  protected void add(LightPoint paramLightPoint) {
    if ((paramLightPoint.I <= 0.0F) || (paramLightPoint.R <= 0.0F)) return;
    add(paramLightPoint, paramLightPoint.IX, paramLightPoint.IY, paramLightPoint.I, paramLightPoint.R);
  }

  protected void remove(LightPoint paramLightPoint) {
    if ((paramLightPoint.I > 0.0F) && (paramLightPoint.R > 0.0F)) {
      int i = (int)paramLightPoint.R;
      int j = paramLightPoint.IX;
      int k = paramLightPoint.IY;
      int m = (j - i) / 256;
      int n = (j + i) / 256;
      int i1 = (k - i) / 256;
      int i2 = (k + i) / 256;
      for (k = i1; k <= i2; k++)
        for (j = m; j <= n; j++)
          this.mapXY.remove(k, j, paramLightPoint);
    }
  }

  private void add(LightPoint paramLightPoint, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    int i = (int)paramFloat2;
    int j = (paramInt1 - i) / 256;
    int k = (paramInt1 + i) / 256;
    int m = (paramInt2 - i) / 256;
    int n = (paramInt2 + i) / 256;
    paramInt1 /= 256;
    paramInt2 /= 256;
    for (int i1 = m; i1 <= n; i1++)
      for (int i2 = j; i2 <= k; i2++)
        this.mapXY.put(i1, i2, paramLightPoint, (i2 == paramInt1) && (i1 == paramInt2) ? flgCenter : null);
  }

  public void clear()
  {
    this.mapXY.clear();
  }

  public LightEnvXY()
  {
    this.mapXY = new HashMapXY16Hash(7);
  }
  public LightEnvXY(Sun paramSun) {
    super(paramSun);
    this.mapXY = new HashMapXY16Hash(7);
  }
}