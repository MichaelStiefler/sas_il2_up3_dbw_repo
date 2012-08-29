package com.maddox.util;

public class HashXY16
{
  private static int[] tbl = new int[256];

  public static int key(int paramInt1, int paramInt2)
  {
    return tbl[((paramInt1 & 0xF) << 4 | paramInt2 & 0xF)] | tbl[(paramInt1 & 0xF0 | (paramInt2 & 0xF0) >> 4)] << 8 | (paramInt1 & 0xFF00) << 24 | (paramInt2 & 0xFF00) << 16;
  }

  static
  {
    int i = 0;
    for (int j = 0; j < 16; j++) {
      int k = (j & 0x1) << 1 | (j & 0x2) << 2 | (j & 0x4) << 3 | (j & 0x8) << 4;
      for (int m = 0; m < 16; m++) {
        int n = (m & 0x1) << 0 | (m & 0x2) << 1 | (m & 0x4) << 2 | (m & 0x8) << 3;
        tbl[(i++)] = (k | n);
      }
    }
  }
}