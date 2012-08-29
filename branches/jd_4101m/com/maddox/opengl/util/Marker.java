package com.maddox.opengl.util;

import com.maddox.opengl.gl;

public class Marker
{
  private static int[] codes = { 0, 1, 3, 2, 4, 5, 7, 6, 0, 2, 6, 4, 1, 3, 7, 5, 0, 1, 5, 4, 2, 3, 7, 6 };

  private static boolean[] v = new boolean[2];
  private static float[] xx = new float[8]; private static float[] yy = new float[8]; private static float[] zz = new float[8];
  private static float[] bb = new float[8]; private static float[] gg = new float[8]; private static float[] rr = new float[8];

  public static void cube(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int k = 0;
    paramFloat4 = (float)(paramFloat4 * 0.5D);
    for (int i = 0; i < 8; i++) {
      if ((i & 0x1) != 0) xx[i] = (paramFloat1 + paramFloat4); else xx[i] = (paramFloat1 - paramFloat4);
      if ((i & 0x2) != 0) yy[i] = (paramFloat2 + paramFloat4); else yy[i] = (paramFloat2 - paramFloat4);
      if ((i & 0x4) != 0) zz[i] = (paramFloat3 + paramFloat4); else zz[i] = (paramFloat3 - paramFloat4);
    }
    gl.GetBooleanv(3553, v);
    if (v[0] != 0) {
      gl.Disable(3553);
      k = 1;
    }
    gl.Begin(7);
    for (i = 0; i < 24; i++) {
      int j = codes[i];
      gl.Vertex3f(xx[j], yy[j], zz[j]);
    }
    gl.End();
    if (k != 0) gl.Enable(3553);
  }

  public static void cubeXYZ(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int k = 0;
    paramFloat4 = (float)(paramFloat4 * 0.5D);
    for (int i = 0; i < 8; i++) {
      if ((i & 0x1) != 0) { xx[i] = (paramFloat1 + paramFloat4); bb[i] = 1.0F; } else {
        xx[i] = (paramFloat1 - paramFloat4); bb[i] = 0.0F;
      }if ((i & 0x2) != 0) { yy[i] = (paramFloat2 + paramFloat4); gg[i] = 1.0F; } else {
        yy[i] = (paramFloat2 - paramFloat4); gg[i] = 0.0F;
      }if ((i & 0x4) != 0) { zz[i] = (paramFloat3 + paramFloat4); rr[i] = 1.0F; } else {
        zz[i] = (paramFloat3 - paramFloat4); rr[i] = 0.0F;
      }
    }
    gl.GetBooleanv(3553, v);
    if (v[0] != 0) {
      gl.Disable(3553);
      k = 1;
    }
    gl.Begin(7);
    for (i = 0; i < 24; i++) {
      int j = codes[i];
      gl.Color3f(rr[j], gg[j], bb[j]);
      gl.Vertex3f(xx[j], yy[j], zz[j]);
    }
    gl.End();
    if (k != 0) gl.Enable(3553);
    gl.Color3f(1.0F, 1.0F, 1.0F);
  }

  public static void triangleZ(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i = 0;
    paramFloat4 = (float)(paramFloat4 * 0.5D);
    float f = paramFloat4 * 0.5F;
    gl.GetBooleanv(3553, v);
    if (v[0] != 0) {
      gl.Disable(3553);
      i = 1;
    }
    gl.Begin(4);
    gl.Vertex3f(paramFloat1, paramFloat2 + paramFloat4, paramFloat3);
    gl.Vertex3f(paramFloat1 + paramFloat4, paramFloat2 - f, paramFloat3);
    gl.Vertex3f(paramFloat1 - paramFloat4, paramFloat2 - f, paramFloat3);
    gl.End();
    if (i != 0) gl.Enable(3553);
  }
}