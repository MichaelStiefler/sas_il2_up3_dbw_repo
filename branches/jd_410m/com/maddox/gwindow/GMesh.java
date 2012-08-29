package com.maddox.gwindow;

public abstract class GMesh
{
  public GSize size;
  public static Loader loader;

  public static GMesh New(String paramString)
  {
    return loader.load(paramString);
  }
  public static GMesh NewShared(String paramString) {
    return loader.loadShared(paramString);
  }
  public static class Loader {
    public GMesh load(String paramString) {
      return null; } 
    public GMesh loadShared(String paramString) { return null;
    }
  }
}