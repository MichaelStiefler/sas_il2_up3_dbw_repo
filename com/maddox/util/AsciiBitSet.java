package com.maddox.util;

import java.util.BitSet;

public class AsciiBitSet
{
  private static int[] fromAscii = new int[''];
  private static int[] toAscii = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 45, 43 };

  public static String save(BitSet paramBitSet, int paramInt)
  {
    int i = 0;
    int j = (paramInt + 5) / 6;
    StringBuffer localStringBuffer = new StringBuffer(j);
    for (int k = 0; k < j; k++) {
      int m = 0;
      for (int n = 0; (n < 6) && (i < paramInt); i++) {
        if (paramBitSet.get(i))
          m |= 1 << n;
        n++;
      }

      localStringBuffer.append((char)toAscii[m]);
    }
    return localStringBuffer.toString();
  }
  public static String save(byte[] paramArrayOfByte, int paramInt) {
    int i = 0;
    int j = (paramInt + 5) / 6;
    StringBuffer localStringBuffer = new StringBuffer(j);
    for (int k = 0; k < j; k++) {
      int m = 0;
      for (int n = 0; (n < 6) && (i < paramInt); i++) {
        int i1 = i >> 8;
        int i2 = 1 << (i & 0xFF);
        if ((paramArrayOfByte[i1] & i2) != 0)
          m |= 1 << n;
        n++;
      }

      localStringBuffer.append((char)toAscii[m]);
    }
    return localStringBuffer.toString();
  }

  public static String save(int paramInt) {
    int i = 0;
    int j = 6;
    StringBuffer localStringBuffer = new StringBuffer(j);
    for (int k = 0; k < j; k++) {
      int m = 0;
      for (int n = 0; (n < 6) && (i < 32); i++) {
        int i1 = 1 << i;
        if ((paramInt & i1) != 0)
          m |= 1 << n;
        n++;
      }

      localStringBuffer.append((char)toAscii[m]);
    }
    while ((localStringBuffer.length() > 1) && (localStringBuffer.charAt(localStringBuffer.length() - 1) == '0'))
      localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
    return localStringBuffer.toString();
  }

  public static int load(String paramString) {
    int i = 0;
    int j = 0;
    int k = 6;
    if (k > paramString.length())
      k = paramString.length();
    for (int m = 0; m < k; m++) {
      int n = fromAscii[(paramString.charAt(m) & 0x7F)];
      for (int i1 = 0; (i1 < 6) && (j < 32); j++) {
        if ((n & 1 << i1) != 0)
          i |= 1 << j;
        i1++;
      }

    }

    return i;
  }

  public static void load(String paramString, BitSet paramBitSet, int paramInt) {
    int i = 0;
    int j = (paramInt + 5) / 6;
    for (int k = 0; k < j; k++) {
      int m = fromAscii[(paramString.charAt(k) & 0x7F)];
      for (int n = 0; (n < 6) && (i < paramInt); i++) {
        if ((m & 1 << n) != 0)
          paramBitSet.set(i);
        else
          paramBitSet.clear(i);
        n++;
      }
    }
  }

  public static byte[] load(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    int i = (paramInt + 7) / 8;
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < i))
      paramArrayOfByte = new byte[i];
    int j = 0;
    int k = (paramInt + 5) / 6;
    for (int m = 0; m < k; m++) {
      int n = fromAscii[(paramString.charAt(m) & 0x7F)];
      for (int i1 = 0; (i1 < 6) && (j < paramInt); j++) {
        int i2 = j >> 8;
        int i3 = 1 << (j & 0xFF);
        if ((n & 1 << i1) != 0)
        {
          int tmp104_102 = i2;
          byte[] tmp104_101 = paramArrayOfByte; tmp104_101[tmp104_102] = (byte)(tmp104_101[tmp104_102] | i3);
        }
        else
        {
          int tmp117_115 = i2;
          byte[] tmp117_114 = paramArrayOfByte; tmp117_114[tmp117_115] = (byte)(tmp117_114[tmp117_115] & (i3 ^ 0xFFFFFFFF));
        }
        i1++;
      }

    }

    return paramArrayOfByte;
  }

  static
  {
    for (int i = 0; i < toAscii.length; i++)
      fromAscii[toAscii[i]] = i;
  }
}