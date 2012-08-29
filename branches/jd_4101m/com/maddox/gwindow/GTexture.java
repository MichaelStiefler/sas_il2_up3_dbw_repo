package com.maddox.gwindow;

public abstract class GTexture
{
  public GSize size;
  public static Loader loader;

  public static GTexture New(String paramString)
  {
    return loader.load(paramString);
  }
  public static class Loader {
    public GTexture load(String paramString) {
      return null;
    }
  }
}