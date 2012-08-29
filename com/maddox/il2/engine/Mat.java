// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mat.java

package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.engine:
//            FObj, Pre, Render, LightEnv, 
//            Sun

public class Mat extends com.maddox.il2.engine.FObj
{

    public Mat(int i)
    {
        super(i);
    }

    public boolean isValidLayer(int i)
    {
        return com.maddox.il2.engine.Mat.IsValidLayer(cppObj, i);
    }

    public void setLayer(int i)
    {
        com.maddox.il2.engine.Mat.SetLayer(cppObj, i);
    }

    public boolean get(short word0)
    {
        return com.maddox.il2.engine.Mat.Get(cppObj, word0);
    }

    public boolean set(short word0, boolean flag)
    {
        return com.maddox.il2.engine.Mat.Set(cppObj, word0, flag);
    }

    public int get(int i)
    {
        return com.maddox.il2.engine.Mat.Get(cppObj, i);
    }

    public int set(int i, int j)
    {
        return com.maddox.il2.engine.Mat.Set(cppObj, i, j);
    }

    public float get(byte byte0)
    {
        return com.maddox.il2.engine.Mat.Get(cppObj, byte0);
    }

    public float set(byte byte0, float f)
    {
        return com.maddox.il2.engine.Mat.Set(cppObj, byte0, f);
    }

    public java.lang.String get(char c)
    {
        return com.maddox.il2.engine.Mat.Get(cppObj, c);
    }

    public void set(char c, java.lang.String s)
    {
        com.maddox.il2.engine.Mat.Set(cppObj, c, s);
    }

    public int preRender(float f, float f1, float f2, float f3)
    {
        return com.maddox.il2.engine.Mat.PreRender(cppObj, f, f1, f2, f3);
    }

    public int preRender()
    {
        return com.maddox.il2.engine.Mat.PreRender(cppObj);
    }

    public static com.maddox.il2.engine.Mat New(java.lang.String s)
    {
        com.maddox.il2.engine.Mat mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s);
        if(mat != null && com.maddox.il2.engine.Pre.isRegister())
            com.maddox.il2.engine.Pre.load(s);
        return mat;
    }

    public static com.maddox.il2.engine.Mat New(java.lang.String s, java.lang.String s1)
    {
        com.maddox.il2.engine.Mat mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s, s1);
        if(mat != null && com.maddox.il2.engine.Pre.isRegister())
            com.maddox.il2.engine.Pre.load(mat.Name());
        return mat;
    }

    public void updateImage(int i, int j, int k, byte abyte0[])
    {
        com.maddox.il2.engine.Mat.UpdateImage(cppObj, i, j, k, abyte0);
    }

    public void grabFromScreen(int i, int j, int k, int l)
    {
        com.maddox.il2.engine.Mat.GrabFromScreen(cppObj, i, j, k, l);
    }

    public static native boolean tgaInfo(java.lang.String s, int ai[]);

    public static int[] loadTextureAsArrayFromTga(java.lang.String s, int i, int j, int k, int ai[])
    {
        return com.maddox.il2.engine.Mat.LoadTextureAsArrayFromTga(s, i, j, k, 0, ai);
        java.lang.Exception exception;
        exception;
        java.lang.System.err.println(exception);
        exception.printStackTrace();
        return null;
    }

    public static int[] loadTextureAsArrayFromTgaB(java.lang.String s, int i, int j, int k, int l, int ai[])
    {
        return com.maddox.il2.engine.Mat.LoadTextureAsArrayFromTga(s, i, j, k, l, ai);
        java.lang.Exception exception;
        exception;
        java.lang.System.err.println(exception);
        exception.printStackTrace();
        return null;
    }

    public static native int[] LoadTextureAsArrayFromTga(java.lang.String s, int i, int j, int k, int l, int ai[]);

    public static native void enableDeleteTextureID(boolean flag);

    public float AverageLightf()
    {
        com.maddox.il2.engine.Sun sun = com.maddox.il2.engine.Render.currentLightEnv().sun();
        float f = get((byte)24) + get((byte)20) * sun.Ambient + get((byte)21) * sun.Diffuze * 0.5F;
        if(f > 1.0F)
            f = 1.0F;
        return f;
    }

    public float SunLightf(com.maddox.JGP.Vector3f vector3f)
    {
        com.maddox.il2.engine.Sun sun = com.maddox.il2.engine.Render.currentLightEnv().sun();
        float f = -vector3f.dot(sun.SunV);
        if(f < 0.0F)
            f = 0.0F;
        float f1 = get((byte)24) + get((byte)20) * sun.Ambient + get((byte)21) * sun.Diffuze * f;
        if(f1 > 1.0F)
            f1 = 1.0F;
        return f1;
    }

    public static int LightColor(int i, float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.0F)
            f = 1.0F;
        int j = (int)((float)(i & 0xff) * f);
        int k = (int)((float)(i >> 8 & 0xff) * f);
        int l = (int)((float)(i >> 16 & 0xff) * f);
        return i & 0xff000000 | j | k << 8 | l << 16;
    }

    public static int LightColor(int i, int j)
    {
        int k = (i & 0xff) * j >> 8;
        int l = (i >> 8 & 0xff) * j >> 8;
        int i1 = (i >> 16 & 0xff) * j >> 8;
        return i & 0xff000000 | k | l << 8 | i1 << 16;
    }

    public static native void setGrayScaleLoading(boolean flag);

    public static native boolean isGrayScaleLoading();

    private static int BIT(int i)
    {
        return 1 << i;
    }

    private static native boolean IsValidLayer(int i, int j);

    private static native void SetLayer(int i, int j);

    private static native boolean Get(int i, short word0);

    private static native int Get(int i, int j);

    private static native float Get(int i, byte byte0);

    private static native java.lang.String Get(int i, char c);

    private static native boolean Set(int i, short word0, boolean flag);

    private static native int Set(int i, int j, int k);

    private static native float Set(int i, byte byte0, float f);

    private static native int Set(int i, char c, java.lang.String s);

    private static native int PreRender(int i, float f, float f1, float f2, float f3);

    private static native int PreRender(int i);

    private static native void UpdateImage(int i, int j, int k, int l, byte abyte0[]);

    private static native void GrabFromScreen(int i, int j, int k, int l, int i1);

    public static final int WRAPU = com.maddox.il2.engine.Mat.BIT(0);
    public static final int WRAPV = com.maddox.il2.engine.Mat.BIT(1);
    public static final int WRAPUV = com.maddox.il2.engine.Mat.BIT(0) | com.maddox.il2.engine.Mat.BIT(1);
    public static final int MINLINEAR = com.maddox.il2.engine.Mat.BIT(2);
    public static final int MAGLINEAR = com.maddox.il2.engine.Mat.BIT(3);
    public static final int MIPMAP = com.maddox.il2.engine.Mat.BIT(4);
    public static final int BLEND = com.maddox.il2.engine.Mat.BIT(5);
    public static final int BLENDADD = com.maddox.il2.engine.Mat.BIT(6);
    public static final int TESTA = com.maddox.il2.engine.Mat.BIT(7);
    public static final int TRANSPBORDER = com.maddox.il2.engine.Mat.BIT(8);
    public static final int TESTZ = com.maddox.il2.engine.Mat.BIT(9);
    public static final int TESTZEQUAL = com.maddox.il2.engine.Mat.BIT(10);
    public static final int NOWRITEZ = com.maddox.il2.engine.Mat.BIT(11);
    public static final int IMAGERESIDENT = com.maddox.il2.engine.Mat.BIT(13);
    public static final int MODULATE = com.maddox.il2.engine.Mat.BIT(14);
    public static final int NOTEXTURE = com.maddox.il2.engine.Mat.BIT(15);
    public static final int ANIMATEPALETTE = com.maddox.il2.engine.Mat.BIT(16);
    public static final int ANIMATESKIPPEDFRAMES = com.maddox.il2.engine.Mat.BIT(17);
    public static final int NODEGRADATION = com.maddox.il2.engine.Mat.BIT(22);
    public static final int DEPTHOFFSET = com.maddox.il2.engine.Mat.BIT(12);
    public static final int DOUBLESIDE = com.maddox.il2.engine.Mat.BIT(18);
    public static final int SHOULDSORT = com.maddox.il2.engine.Mat.BIT(19);
    public static final int DROPSHADOW = com.maddox.il2.engine.Mat.BIT(20);
    public static final int NATIVETIMER = com.maddox.il2.engine.Mat.BIT(21);
    public static final int TEXTURE_NOIMAGE = 0;
    public static final int TEXTURE_ALPHA = 1;
    public static final int TEXTURE_ALPHALM = 2;
    public static final int TEXTURE_RGB = 3;
    public static final int TEXTURE_RGBA = 4;
    public static final int TEXTURE_PALETTE = 5;
    public static final int TEXTURE_MIPMAP = 0x10000;
    public static final int TEXTURE_TRANSPBORDERA = 0x20000;
    public static final int TEXTURE_NODEGRADATION = 0x80000;
    public static final int TEXTURE_NOCOMPRESS16BIT = 0x100000;
    public static final int TEXTURE_NOCOMPRESSARB = 0x200000;
    public static final int TEXTURE_COMPRESSMAJORALPHA = 0x400000;
    public static final int TEXTURE_GRAYSCALEMODE = 0x800000;
    public static final int TEXTURE_ANIMATED = 256;
    public static final int TEXTURE_ANIMATEDPROCEDURE = 768;
    public static final int TEXTURE_ANIMATEDLIST = 1280;
    public static final short fEnable = 0;
    public static final int fFlags = 0;
    public static final int fType = 1;
    public static final int fFrames = 2;
    public static final byte fVisDistanceNear = 0;
    public static final byte fVisDistanceFar = 1;
    public static final byte fSX = 2;
    public static final byte fSY = 3;
    public static final byte fFrame = 4;
    public static final byte fTime = 5;
    public static final byte fFrameInc = 6;
    public static final byte fScaleRed = 7;
    public static final byte fScaleGreen = 8;
    public static final byte fScaleBlue = 9;
    public static final byte fScaleAlpha = 10;
    public static final byte fUAdd = 11;
    public static final byte fVAdd = 12;
    public static final byte fUMul = 13;
    public static final byte fVMul = 14;
    public static final byte fAmbient = 20;
    public static final byte fDiffuse = 21;
    public static final byte fSpecular = 22;
    public static final byte fSpecularPow = 23;
    public static final byte fShine = 24;
    public static final byte Ka = 20;
    public static final byte Kd = 21;
    public static final byte Ks = 22;
    public static final byte pow = 23;
    public static final byte Ke = 24;
    public static final char fTextureName = 0;

}
