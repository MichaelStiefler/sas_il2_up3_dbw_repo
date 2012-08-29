package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;
import java.io.PrintStream;

public class Mat extends FObj
{
  public static final int WRAPU = BIT(0);

  public static final int WRAPV = BIT(1);

  public static final int WRAPUV = BIT(0) | BIT(1);

  public static final int MINLINEAR = BIT(2);

  public static final int MAGLINEAR = BIT(3);

  public static final int MIPMAP = BIT(4);

  public static final int BLEND = BIT(5);

  public static final int BLENDADD = BIT(6);

  public static final int TESTA = BIT(7);

  public static final int TRANSPBORDER = BIT(8);

  public static final int TESTZ = BIT(9);

  public static final int TESTZEQUAL = BIT(10);

  public static final int NOWRITEZ = BIT(11);

  public static final int IMAGERESIDENT = BIT(13);

  public static final int MODULATE = BIT(14);

  public static final int NOTEXTURE = BIT(15);

  public static final int ANIMATEPALETTE = BIT(16);

  public static final int ANIMATESKIPPEDFRAMES = BIT(17);

  public static final int NODEGRADATION = BIT(22);

  public static final int DEPTHOFFSET = BIT(12);

  public static final int DOUBLESIDE = BIT(18);

  public static final int SHOULDSORT = BIT(19);

  public static final int DROPSHADOW = BIT(20);

  public static final int NATIVETIMER = BIT(21);
  public static final int TEXTURE_NOIMAGE = 0;
  public static final int TEXTURE_ALPHA = 1;
  public static final int TEXTURE_ALPHALM = 2;
  public static final int TEXTURE_RGB = 3;
  public static final int TEXTURE_RGBA = 4;
  public static final int TEXTURE_PALETTE = 5;
  public static final int TEXTURE_MIPMAP = 65536;
  public static final int TEXTURE_TRANSPBORDERA = 131072;
  public static final int TEXTURE_NODEGRADATION = 524288;
  public static final int TEXTURE_NOCOMPRESS16BIT = 1048576;
  public static final int TEXTURE_NOCOMPRESSARB = 2097152;
  public static final int TEXTURE_COMPRESSMAJORALPHA = 4194304;
  public static final int TEXTURE_GRAYSCALEMODE = 8388608;
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
  public static final char fTextureName = '\000';

  public Mat(int paramInt)
  {
    super(paramInt);
  }
  public boolean isValidLayer(int paramInt) { return IsValidLayer(this.jdField_cppObj_of_type_Int, paramInt); } 
  public void setLayer(int paramInt) { SetLayer(this.jdField_cppObj_of_type_Int, paramInt); }

  public boolean get(short paramShort) {
    return Get(this.jdField_cppObj_of_type_Int, paramShort); } 
  public boolean set(short paramShort, boolean paramBoolean) { return Set(this.jdField_cppObj_of_type_Int, paramShort, paramBoolean);
  }

  public int get(int paramInt)
  {
    return Get(this.jdField_cppObj_of_type_Int, paramInt); } 
  public int set(int paramInt1, int paramInt2) { return Set(this.jdField_cppObj_of_type_Int, paramInt1, paramInt2);
  }

  public float get(byte paramByte)
  {
    return Get(this.jdField_cppObj_of_type_Int, paramByte); } 
  public float set(byte paramByte, float paramFloat) { return Set(this.jdField_cppObj_of_type_Int, paramByte, paramFloat); }

  public String get(char paramChar)
  {
    return Get(this.jdField_cppObj_of_type_Int, paramChar); } 
  public void set(char paramChar, String paramString) { Set(this.jdField_cppObj_of_type_Int, paramChar, paramString); }

  public int preRender(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return PreRender(this.jdField_cppObj_of_type_Int, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  public int preRender() {
    return PreRender(this.jdField_cppObj_of_type_Int);
  }

  public static Mat New(String paramString) {
    Mat localMat = (Mat)FObj.Get(paramString);
    if ((localMat != null) && (Pre.isRegister())) Pre.load(paramString);
    return localMat;
  }

  public static Mat New(String paramString1, String paramString2) {
    Mat localMat = (Mat)FObj.Get(paramString1, paramString2);
    if ((localMat != null) && (Pre.isRegister())) Pre.load(localMat.Name());
    return localMat;
  }

  public void updateImage(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    UpdateImage(this.jdField_cppObj_of_type_Int, paramInt1, paramInt2, paramInt3, paramArrayOfByte);
  }

  public void grabFromScreen(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    GrabFromScreen(this.jdField_cppObj_of_type_Int, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static native boolean tgaInfo(String paramString, int[] paramArrayOfInt);

  public static int[] loadTextureAsArrayFromTga(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    try
    {
      return LoadTextureAsArrayFromTga(paramString, paramInt1, paramInt2, paramInt3, 0, paramArrayOfInt);
    } catch (Exception localException) {
      System.err.println(localException);
      localException.printStackTrace();
    }
    return null;
  }

  public static int[] loadTextureAsArrayFromTgaB(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    try {
      return LoadTextureAsArrayFromTga(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
    } catch (Exception localException) {
      System.err.println(localException);
      localException.printStackTrace();
    }
    return null;
  }

  public static native int[] LoadTextureAsArrayFromTga(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  public static native void enableDeleteTextureID(boolean paramBoolean);

  public float AverageLightf()
  {
    Sun localSun = Render.currentLightEnv().sun();
    float f = get(24) + get(20) * localSun.Ambient + get(21) * localSun.Diffuze * 0.5F;
    if (f > 1.0F) f = 1.0F; return f;
  }

  public float SunLightf(Vector3f paramVector3f)
  {
    Sun localSun = Render.currentLightEnv().sun();
    float f1 = -paramVector3f.dot(localSun.SunV); if (f1 < 0.0F) f1 = 0.0F;
    float f2 = get(24) + get(20) * localSun.Ambient + get(21) * localSun.Diffuze * f1;
    if (f2 > 1.0F) f2 = 1.0F; return f2;
  }

  public static int LightColor(int paramInt, float paramFloat) {
    if (paramFloat < 0.0F) paramFloat = 0.0F; if (paramFloat > 1.0F) paramFloat = 1.0F;
    int i = (int)((paramInt & 0xFF) * paramFloat);
    int j = (int)((paramInt >> 8 & 0xFF) * paramFloat);
    int k = (int)((paramInt >> 16 & 0xFF) * paramFloat);
    return paramInt & 0xFF000000 | i | j << 8 | k << 16;
  }

  public static int LightColor(int paramInt1, int paramInt2) {
    int i = (paramInt1 & 0xFF) * paramInt2 >> 8;
    int j = (paramInt1 >> 8 & 0xFF) * paramInt2 >> 8;
    int k = (paramInt1 >> 16 & 0xFF) * paramInt2 >> 8;
    return paramInt1 & 0xFF000000 | i | j << 8 | k << 16; } 
  public static native void setGrayScaleLoading(boolean paramBoolean);

  public static native boolean isGrayScaleLoading();

  private static int BIT(int paramInt) { return 1 << paramInt;
  }

  private static native boolean IsValidLayer(int paramInt1, int paramInt2);

  private static native void SetLayer(int paramInt1, int paramInt2);

  private static native boolean Get(int paramInt, short paramShort);

  private static native int Get(int paramInt1, int paramInt2);

  private static native float Get(int paramInt, byte paramByte);

  private static native String Get(int paramInt, char paramChar);

  private static native boolean Set(int paramInt, short paramShort, boolean paramBoolean);

  private static native int Set(int paramInt1, int paramInt2, int paramInt3);

  private static native float Set(int paramInt, byte paramByte, float paramFloat);

  private static native int Set(int paramInt, char paramChar, String paramString);

  private static native int PreRender(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  private static native int PreRender(int paramInt);

  private static native void UpdateImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  private static native void GrabFromScreen(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
}