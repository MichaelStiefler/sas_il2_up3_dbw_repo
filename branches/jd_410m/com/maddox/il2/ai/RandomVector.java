package com.maddox.il2.ai;

import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.rts.Time;
import java.util.Random;

public class RandomVector
{
  private static final int MASK_ELEMS = 255;
  private static final int N_ELEMS = 256;
  private static Vector3f[] vectors;
  private static Vector3f tmp = new Vector3f();

  public static void getTimed(Vector3f paramVector3f, int paramInt)
  {
    long l1 = Time.current();
    long l2 = l1 / 500L;
    long l3 = l1 - l2 * 500L;
    float f = (float)l3 * 0.002F;

    paramInt = (int)(paramInt + l2) & 0xFF;
    int i = paramInt + 1 & 0xFF;

    paramVector3f.interpolate(vectors[paramInt], vectors[i], f);
  }

  public static void getTimed(Vector3d paramVector3d, int paramInt)
  {
    long l1 = Time.current();
    long l2 = l1 / 1000L;
    long l3 = l1 - l2 * 1000L;
    float f = (float)l3 * 0.001F;

    paramInt = (int)(paramInt + l2) & 0xFF;
    int i = paramInt + 1 & 0xFF;

    tmp.interpolate(vectors[paramInt], vectors[i], f);
    paramVector3d.set(tmp.x, tmp.y, tmp.z);
  }

  public static void getTimed(long paramLong, Vector3d paramVector3d, int paramInt)
  {
    long l1 = paramLong / 1000L;
    long l2 = paramLong - l1 * 1000L;
    float f = (float)l2 * 0.001F;

    paramInt = (int)(paramInt + l1) & 0xFF;
    int i = paramInt + 1 & 0xFF;

    tmp.interpolate(vectors[paramInt], vectors[i], f);
    paramVector3d.set(tmp.x, tmp.y, tmp.z);
  }

  public static void getTimedStepped(int paramInt1, long paramLong, Vector3d paramVector3d, int paramInt2)
  {
    long l1 = paramLong / (paramInt1 * 1000);
    long l2 = paramLong - l1 * 1000L * paramInt1;
    float f = (float)l2 * (1.0F / (paramInt1 * 1000.0F));

    paramInt2 = (int)(paramInt2 + l1) & 256 / paramInt1 - 1;
    int i = paramInt2 + paramInt1 & 0xFF;

    tmp.interpolate(vectors[paramInt2], vectors[i], f);
    paramVector3d.set(tmp.x, tmp.y, tmp.z);
  }

  static
  {
    Random localRandom = new Random(12345L);

    vectors = new Vector3f[256];

    vectors[0] = new Vector3f(0.0F, 0.0F, 0.0F);
    for (int i = 1; i < 256; i++) {
      vectors[i] = new Vector3f(localRandom.nextFloat() * 2.0F - 1.0F, localRandom.nextFloat() * 2.0F - 1.0F, localRandom.nextFloat() * 2.0F - 1.0F);

      vectors[i].scale(0.2F);
      vectors[i].add(vectors[(i - 1)]);
      if (vectors[i].length() >= 1.0F) {
        vectors[i].normalize();
      }
    }
    vectors['þ'].interpolate(vectors['ý'], vectors[0], 0.33F);
    vectors['ÿ'].interpolate(vectors['ý'], vectors[0], 0.67F);
  }
}