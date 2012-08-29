package com.maddox.il2.engine;

import com.maddox.rts.RTS;
import java.io.PrintStream;

public class GObj
  implements GObjInstance
{
  protected int cppObj = 0;

  private static boolean libLoaded = false;

  public GObj(int paramInt)
  {
    this.cppObj = paramInt;
  }

  public int cppObject()
  {
    return this.cppObj;
  }

  public int LinkCount()
  {
    return LinkCount(this.cppObj);
  }

  public GObj Clone()
  {
    return Clone(this.cppObj);
  }

  public boolean LinkLock()
  {
    return LinkLock(this.cppObj);
  }

  public void SetLinkLock(boolean paramBoolean)
  {
    SetLinkLock(this.cppObj, paramBoolean);
  }

  public String getCppClassName()
  {
    return getCppClassName(this.cppObj);
  }

  protected void finalize()
  {
    if (this.cppObj != 0) Finalize(this.cppObj);
  }

  public static native void Finalize(int paramInt);

  public static native int LinkCount(int paramInt);

  public static native void Unlink(int paramInt);

  public static native GObj Clone(int paramInt);

  public static native boolean LinkLock(int paramInt);

  public static native void SetLinkLock(int paramInt, boolean paramBoolean);

  public static native String getCppClassName(int paramInt);

  public static native Object getJavaObject(int paramInt);

  public static native void DeleteCppObjects();

  public static native int version();

  private static native void setInterf(int paramInt);

  public static final void loadNative()
  {
    if (!libLoaded) {
      System.loadLibrary(Config.engineDllName());
      setInterf(RTS.interf());
      libLoaded = true;
      int i = RTS.version();
      System.out.println("RTS Version " + (i >> 16) + "." + (i & 0xFFFF));
      i = version();
      System.out.println("Core Version " + (i >> 16) + "." + (i & 0xFFFF));
    }
  }
}