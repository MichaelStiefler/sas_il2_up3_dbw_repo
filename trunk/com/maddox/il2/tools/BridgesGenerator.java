package com.maddox.il2.tools;

import com.maddox.TexImage.TexImage;
import java.io.PrintStream;

public class BridgesGenerator
{
  public static final int HIGHWAY = 128;
  public static final int RAIL = 64;
  public static final int ROAD = 32;
  public static final int CH_RATIO = 4;
  public static final int CH2 = 2;
  public static final int WATERLEV = 250;
  private static TexImage T = new TexImage();
  private static final int MXL = 8;
  private static int dx;
  private static int dy;
  private static int len;
  private static boolean prn = true;
  private static final int MAXBRIDGE = 1000;
  private static int num;
  private static int[] coords = new int[4000];
  private static byte[] type = new byte[1000];

  static TexImage W = new TexImage();

  private static int WintI(int paramInt1, int paramInt2)
  {
    return W.intI(paramInt1 * 4 + 2, paramInt2 * 4 + 2);
  }

  private static int WintL(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = paramInt3 > paramInt1 ? 1 : -1; if (paramInt3 == paramInt1) j = 0;
    int k = paramInt4 > paramInt2 ? 1 : -1; if (paramInt4 == paramInt2) k = 0;
    int i = 255;
    paramInt1 = paramInt1 * 4 + 2; paramInt2 = paramInt2 * 4 + 2;
    paramInt3 = paramInt3 * 4 + 2; paramInt4 = paramInt4 * 4 + 2;
    for (; (paramInt2 != paramInt4) || (paramInt1 != paramInt3); paramInt1 += j) {
      if (i > W.intI(paramInt1, paramInt2)) i = W.intI(paramInt1, paramInt2);
      paramInt2 += k;
    }

    return i;
  }

  private static int getlen(int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; (i < 50) && 
      (paramInt1 >= 0) && (paramInt2 >= 0) && (paramInt1 < T.sx) && (paramInt2 < T.sy) && 
      ((T.I(paramInt1, paramInt2) & paramInt3) != 0); paramInt2 += dy)
    {
      T.I(paramInt1, paramInt2, T.I(paramInt1, paramInt2) & (paramInt3 ^ 0xFFFFFFFF));

      i++; paramInt1 += dx;
    }

    paramInt1 -= dx; paramInt2 -= dy;
    if ((i > 0) && (i < 8)) {
      if (prn) System.out.print("" + paramInt3 + ", " + paramInt1 + "," + paramInt2 + ",");
      if (num < coords.length) {
        coords[(num++)] = paramInt1;
        coords[(num++)] = paramInt2;
      }
    }
    return i;
  }

  private static void conn8(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = BridgesGenerator.len = 0;
    if ((T.I(paramInt1 - 1, paramInt2 - 1) & paramInt3) != 0) i |= 1;
    if ((T.I(paramInt1, paramInt2 - 1) & paramInt3) != 0) i |= 2;
    if ((T.I(paramInt1 + 1, paramInt2 - 1) & paramInt3) != 0) i |= 4;
    if ((T.I(paramInt1 + 1, paramInt2) & paramInt3) != 0) i |= 8;
    if ((T.I(paramInt1 + 1, paramInt2 + 1) & paramInt3) != 0) i |= 16;
    if ((T.I(paramInt1, paramInt2 + 1) & paramInt3) != 0) i |= 32;
    if ((T.I(paramInt1 - 1, paramInt2 + 1) & paramInt3) != 0) i |= 64;
    if ((T.I(paramInt1 - 1, paramInt2) & paramInt3) != 0) i |= 128;

    switch (i) { default:
      return;
    case 1:
      dx = -1; dy = -1; break;
    case 2:
      dx = 0; dy = -1; break;
    case 4:
      dx = 1; dy = -1; break;
    case 8:
      dx = 1; dy = 0; break;
    case 16:
      dx = 1; dy = 1; break;
    case 32:
      dx = 0; dy = 1; break;
    case 64:
      dx = -1; dy = 1; break;
    case 128:
      dx = -1; dy = 0;
    }
    len = getlen(paramInt1, paramInt2, paramInt3);
  }

  private static void comp(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((T.I(paramInt1, paramInt2) & paramInt3) == 0) return;
    conn8(paramInt1, paramInt2, paramInt3);
    if ((len > 0) && (len < 8)) {
      if (prn) System.out.println(" " + paramInt1 + "," + paramInt2);
      type[(num / 4)] = (byte)paramInt3;
      if (num < coords.length) {
        coords[(num++)] = paramInt1;
        coords[(num++)] = paramInt2;
      }
    }
  }

  private static void printList(TexImage paramTexImage)
  {
    num = 0;
    if (T != paramTexImage) T.set(paramTexImage);
    int i;
    for (int j = 0; j < T.sy; j++) {
      for (i = 0; i < T.sx; i++) {
        if (((T.intI(i, j) & 0xE0) == 0) || ((T.intI(i, j) & 0x1C) != 28))
          T.I(i, j, 0);
        else
          T.I(i, j, T.intI(i, j) & 0xE0);
      }
    }
    for (j = 1; j < T.sy - 1; j++)
      for (i = 1; i < T.sx - 1; i++) {
        if ((T.I(i, j) & 0x80) != 0) comp(i, j, 128);
        if ((T.I(i, j) & 0x40) != 0) comp(i, j, 64);
        if ((T.I(i, j) & 0x20) == 0) continue; comp(i, j, 32);
      }
  }

  public static Bridge[] getBridgesArray(TexImage paramTexImage)
  {
    prn = false;
    printList(paramTexImage);
    prn = true;

    int i = num / 4;
    Bridge[] arrayOfBridge = new Bridge[i];

    for (int j = 0; j < i; j++) {
      Bridge localBridge = arrayOfBridge[j] =  = new Bridge();
      int k = j * 4;
      localBridge.x1 = coords[k];
      localBridge.y1 = coords[(k + 1)];
      localBridge.x2 = coords[(k + 2)];
      localBridge.y2 = coords[(k + 3)];
      localBridge.type = (type[j] & 0xFF);
    }
    return arrayOfBridge;
  }

  public static void shorting(Bridge paramBridge)
  {
    int i = paramBridge.x1; int j = paramBridge.x2; int k = paramBridge.y1; int m = paramBridge.y2;
    int n = j - i; int i1 = m - k;

    if (n > 0) n = 1; else if (n < 0) n = -1;
    if (i1 > 0) i1 = 1; else if (i1 < 0) i1 = -1; while (true)
    {
      int i4 = 0;
      int i2 = Math.max(Math.abs(i - j), Math.abs(k - m));
      if (i2 <= 1) break;
      int i3 = T.I(i, k);
      if (((i3 & 0xE0) != 0) && ((i3 & 0x1C) == 28) && (WintL(i, k, i + n, k + i1) >= 250)) {
        T.I(i, k, T.I(i, k) & 0xFFFFFFE3);
        i += n; k += i1;
        i4++;
      }
      i2 = Math.max(Math.abs(i - j), Math.abs(k - m));
      if (i2 <= 1) break;
      i3 = T.I(j, m);
      if (((i3 & 0xE0) != 0) && ((i3 & 0x1C) == 28) && (WintL(j, m, j - n, m - i1) >= 250)) {
        T.I(j, m, T.I(j, m) & 0xFFFFFFE3);
        j -= n; m -= i1;
        i4++;
      }
      if (i4 != 0) continue;
    }
    paramBridge.x1 = i; paramBridge.x2 = j; paramBridge.y1 = k; paramBridge.y2 = m;
  }

  public static void longing(Bridge paramBridge) {
    int i = paramBridge.x1; int j = paramBridge.x2; int k = paramBridge.y1; int m = paramBridge.y2;
    int n = j - i; int i1 = m - k;

    if (n > 0) n = 1; else if (n < 0) n = -1;
    if (i1 > 0) i1 = 1; else if (i1 < 0) i1 = -1;

    if (WintI(i, k) < 130)
    {
      i -= n; k -= i1;
      if ((T.I(i, k) & 0xE0) != 0)
        T.I(i, k, T.I(i, k) | 0x1C);
    }
    if (WintI(j, m) < 130) {
      j += n; m += i1;
      if ((T.I(j, m) & 0xE0) != 0) {
        T.I(j, m, T.I(j, m) | 0x1C);
      }
    }
    paramBridge.x1 = i; paramBridge.x2 = j; paramBridge.y1 = k; paramBridge.y2 = m;
  }

  public static void deleting(Bridge paramBridge) {
    int i = paramBridge.x1; int j = paramBridge.x2; int k = paramBridge.y1; int m = paramBridge.y2;
    int n = j - i; int i1 = m - k;

    if (n > 0) n = 1; else if (n < 0) n = -1;
    if (i1 > 0) i1 = 1; else if (i1 < 0) i1 = -1;
    for (; (k != m) || (i != j); i += n) {
      T.I(i, k, T.I(i, k) & 0xFFFFFFE3);

      k += i1;
    }
  }

  public static boolean exists(Bridge paramBridge)
  {
    return WintL(paramBridge.x1, paramBridge.y1, paramBridge.x2, paramBridge.y2) < 180;
  }

  public static void main(String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length != 1) {
      System.out.println("Usage: Bridges filename.tga");
      return;
    }
    T.LoadTGA(paramArrayOfString[0]);
    Bridge[] arrayOfBridge = getBridgesArray(T);
    T.LoadTGA(paramArrayOfString[0]);
    W.LoadTGA(ch_name(paramArrayOfString[0], "WaterBig.tga"));
    for (int i = 0; i < arrayOfBridge.length; i++)
    {
      shorting(arrayOfBridge[i]);
      longing(arrayOfBridge[i]);
      System.out.println(arrayOfBridge[i]);
    }
    T.SaveTGA(paramArrayOfString[0] + "~");
  }

  private static String ch_name(String paramString1, String paramString2) {
    int i = paramString1.lastIndexOf('\\');
    System.out.println(paramString1.substring(0, i + 1) + paramString2);
    return paramString1.substring(0, i + 1) + paramString2;
  }

  private static void wipeBridge(Bridge paramBridge)
  {
    int i = paramBridge.x1; int j = paramBridge.x2; int k = paramBridge.y1; int m = paramBridge.y2;
    int n = j - i; int i1 = m - k;

    if (n > 0) n = 1; else if (n < 0) n = -1;
    if (i1 > 0) i1 = 1; else if (i1 < 0) i1 = -1;
    i *= 4; k *= 4;
    j *= 4; m *= 4;
    for (; (k != m) || (i != j); i += n) {
      W.I(i, k, 0);

      k += i1;
    }
  }
}