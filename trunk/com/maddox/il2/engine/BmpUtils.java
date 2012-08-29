package com.maddox.il2.engine;

public class BmpUtils
{
  public static native int squareSizeBMP8Pal(String paramString);

  public static boolean checkBMP8Pal(String paramString, int paramInt1, int paramInt2)
  {
    int i = RectSizeBMP8Pal(paramString);
    if (i == -1) return false;
    return (paramInt1 == (i & 0xFFFF)) && (paramInt2 == (i >> 16 & 0xFFFF));
  }

  public static boolean bmp8Scale05(String paramString1, String paramString2)
  {
    boolean bool = BMP8Scale05(paramString1, paramString2);
    return bool;
  }

  public static boolean bmp8PalToTGA3(String paramString1, String paramString2)
  {
    boolean bool = BMP8PalToTGA3(paramString1, paramString2);
    if (bool)
      tryReloadFObj(paramString2);
    return bool;
  }

  public static boolean bmp8Pal192x256ToTGA3(String paramString1, String paramString2)
  {
    boolean bool = BMP8Pal192x256ToTGA3(paramString1, paramString2);
    if (bool)
      tryReloadFObj(paramString2);
    return bool;
  }

  public static boolean bmp8PalToTGA4(String paramString1, String paramString2)
  {
    boolean bool = BMP8PalToTGA4(paramString1, paramString2);
    if (bool)
      tryReloadFObj(paramString2);
    return bool;
  }

  public static boolean bmp8PalTo2TGA4(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = BMP8PalTo2TGA4(paramString1, paramString2, paramString3);
    if (bool) {
      tryReloadFObj(paramString2);
      tryReloadFObj(paramString3);
    }
    return bool;
  }

  public static boolean bmp8PalTo4TGA4(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = BMP8PalTo4TGA4(paramString1, paramString2, paramString3);
    if (bool) {
      tryReloadFObj(paramString3 + "/skin1o.tga");
      tryReloadFObj(paramString3 + "/skin1p.tga");
      tryReloadFObj(paramString3 + "/skin1q.tga");
    }
    return bool;
  }

  private static void tryReloadFObj(String paramString) {
    if (FObj.Exist(paramString)) {
      int i = FObj.GetFObj(paramString);
      if (i != 0) {
        FObj.ReLoad(i);
        GObj.Unlink(i); }  }  } 
  private static native int RectSizeBMP8Pal(String paramString);

  private static native boolean BMP8Scale05(String paramString1, String paramString2);

  private static native boolean BMP8PalToTGA3(String paramString1, String paramString2);

  private static native boolean BMP8Pal192x256ToTGA3(String paramString1, String paramString2);

  private static native boolean BMP8PalToTGA4(String paramString1, String paramString2);

  private static native boolean BMP8PalTo2TGA4(String paramString1, String paramString2, String paramString3);

  private static native boolean BMP8PalTo4TGA4(String paramString1, String paramString2, String paramString3);

  static { GObj.loadNative();
  }
}