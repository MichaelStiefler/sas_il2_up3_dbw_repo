package com.maddox.rts;

import java.io.InputStream;

public final class LDRres extends ClassLoader
{
  private static ClassLoader rldr = null;

  public static final ClassLoader loader()
  {
    if (rldr != null)
      return rldr;
    rldr = LDR.resLoader();
    if (rldr != null)
      return rldr;
    rldr = new LDRres();
    return rldr;
  }

  public InputStream getResourceAsStream(String paramString) {
    try {
      return new SFSInputStream(paramString); } catch (Exception localException) {
    }
    return super.getResourceAsStream(paramString);
  }
}