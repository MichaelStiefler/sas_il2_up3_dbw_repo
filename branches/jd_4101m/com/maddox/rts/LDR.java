package com.maddox.rts;

import java.io.InputStream;

public final class LDR extends ClassLoader
{
  private String rtsLib = null;

  private static ClassLoader rldr = null;
  private static LDRCallBack ldr = null;

  protected static final ClassLoader resLoader()
  {
    return rldr;
  }

  protected synchronized Class loadClass(String paramString, boolean paramBoolean)
    throws ClassNotFoundException
  {
    Class localClass = findLoadedClass(paramString);
    if (localClass == null) {
      if ((ldr != null) && (paramString.startsWith("com.maddox"))) {
        byte[] arrayOfByte = ldr.load(paramString);
        if (arrayOfByte != null)
          localClass = defineClass(null, arrayOfByte, 0, arrayOfByte.length);
        if (localClass == null)
          throw new ClassNotFoundException(paramString);
      } else {
        localClass = super.loadClass(paramString, false);
      }
    }
    if (paramBoolean)
      resolveClass(localClass);
    return localClass;
  }

  public InputStream getResourceAsStream(String paramString)
  {
    InputStream localInputStream = null;
    if (ldr != null)
      localInputStream = ldr.open(paramString);
    if (localInputStream == null)
      localInputStream = super.getResourceAsStream(paramString);
    return localInputStream;
  }

  protected String findLibrary(String paramString)
  {
    if ("rts".equals(paramString)) {
      return this.rtsLib;
    }
    return null;
  }

  private void link(String paramString)
  {
    this.rtsLib = paramString;
  }

  private void set(Object paramObject)
  {
    ldr = (LDRCallBack)paramObject;
    try {
      Class.forName("com.maddox.il2.game.Main", true, this);
    }
    catch (Exception localException)
    {
    }
  }

  private LDR()
  {
    rldr = this;
  }
}