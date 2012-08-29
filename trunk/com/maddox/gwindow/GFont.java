package com.maddox.gwindow;

public abstract class GFont
{
  public float height;
  public float descender;
  private static GSize textSize = new GSize();
  public static Loader loader;

  public GFont()
  {
    this.height = 10.0F;
    this.descender = -2.0F;
  }
  public void size(String paramString, GSize paramGSize) {
  }
  public void size(String paramString, int paramInt1, int paramInt2, GSize paramGSize) {
  }
  public void size(char[] paramArrayOfChar, int paramInt1, int paramInt2, GSize paramGSize) {  }

  public GSize size(String paramString) { size(paramString, textSize);
    return textSize; }

  public GSize size(String paramString, int paramInt1, int paramInt2) {
    size(paramString, paramInt1, paramInt2, textSize);
    return textSize;
  }
  public GSize size(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
    size(paramArrayOfChar, paramInt1, paramInt2, textSize);
    return textSize;
  }

  public int len(String paramString, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    return 0; } 
  public int len(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) { return 0; } 
  public int len(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) { return 0; } 
  public void resolutionChanged() {
  }

  public static GFont New(String paramString) {
    return loader.load(paramString);
  }
  public static class Loader {
    public GFont load(String paramString) {
      return null;
    }
  }
}