// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BmpUtils.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            FObj, GObj

public class BmpUtils
{

    public static native int squareSizeBMP8Pal(java.lang.String s);

    public static boolean checkBMP8Pal(java.lang.String s, int i, int j)
    {
        int k = com.maddox.il2.engine.BmpUtils.RectSizeBMP8Pal(s);
        if(k == -1)
            return false;
        else
            return i == (k & 0xffff) && j == (k >> 16 & 0xffff);
    }

    public static boolean bmp8Scale05(java.lang.String s, java.lang.String s1)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8Scale05(s, s1);
        return flag;
    }

    public static boolean bmp8PalToTGA3(java.lang.String s, java.lang.String s1)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8PalToTGA3(s, s1);
        if(flag)
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s1);
        return flag;
    }

    public static boolean bmp8Pal192x256ToTGA3(java.lang.String s, java.lang.String s1)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8Pal192x256ToTGA3(s, s1);
        if(flag)
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s1);
        return flag;
    }

    public static boolean bmp8PalToTGA4(java.lang.String s, java.lang.String s1)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8PalToTGA4(s, s1);
        if(flag)
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s1);
        return flag;
    }

    public static boolean bmp8PalTo2TGA4(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8PalTo2TGA4(s, s1, s2);
        if(flag)
        {
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s1);
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s2);
        }
        return flag;
    }

    public static boolean bmp8PalTo4TGA4(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        boolean flag = com.maddox.il2.engine.BmpUtils.BMP8PalTo4TGA4(s, s1, s2);
        if(flag)
        {
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s2 + "/skin1o.tga");
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s2 + "/skin1p.tga");
            com.maddox.il2.engine.BmpUtils.tryReloadFObj(s2 + "/skin1q.tga");
        }
        return flag;
    }

    private static void tryReloadFObj(java.lang.String s)
    {
        if(com.maddox.il2.engine.FObj.Exist(s))
        {
            int i = com.maddox.il2.engine.FObj.GetFObj(s);
            if(i != 0)
            {
                com.maddox.il2.engine.FObj.ReLoad(i);
                com.maddox.il2.engine.GObj.Unlink(i);
            }
        }
    }

    private static native int RectSizeBMP8Pal(java.lang.String s);

    private static native boolean BMP8Scale05(java.lang.String s, java.lang.String s1);

    private static native boolean BMP8PalToTGA3(java.lang.String s, java.lang.String s1);

    private static native boolean BMP8Pal192x256ToTGA3(java.lang.String s, java.lang.String s1);

    private static native boolean BMP8PalToTGA4(java.lang.String s, java.lang.String s1);

    private static native boolean BMP8PalTo2TGA4(java.lang.String s, java.lang.String s1, java.lang.String s2);

    private static native boolean BMP8PalTo4TGA4(java.lang.String s, java.lang.String s1, java.lang.String s2);

    private BmpUtils()
    {
    }

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
