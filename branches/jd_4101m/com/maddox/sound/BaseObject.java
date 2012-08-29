package com.maddox.sound;

import com.maddox.rts.Cpu86ID;
import com.maddox.rts.RTS;
import java.io.PrintStream;

public class BaseObject
{
  private static final String DLL_NAME = "mg_snd";
  protected static boolean enabled = true;
  protected static boolean loaded = false;

  public static boolean isEnabled()
  {
    return enabled;
  }

  public static void setEnabled(boolean paramBoolean)
  {
    enabled = paramBoolean;
  }

  private String mkErrMsg(String paramString)
  {
    return "ERROR [" + toString() + "] " + paramString;
  }

  protected static void printf(String paramString)
  {
    System.out.println(paramString);
  }

  protected void errmsg(String paramString)
  {
    System.out.println(mkErrMsg(paramString));
  }

  protected void raise(String paramString) throws Exception
  {
    System.out.println(mkErrMsg(paramString));
    throw new Exception(paramString); } 
  protected static native int libVersion(int paramInt);

  protected static native void libInit(int paramInt);

  protected static native void initRTS(int paramInt);

  protected static void loadNative() { if (!loaded) {
      String str = "standard";
      loaded = true;
      if (Cpu86ID.isSSE()) {
        System.loadLibrary("mg_snd_sse");
        str = "P IV";
      } else {
        System.loadLibrary("mg_snd");
      }int i = libVersion(101);
      libInit(Cpu86ID.get());
      initRTS(RTS.interf());
      printf("Sound: Native library (build " + i / 100 + "." + i % 100 + ", target - " + str + ") loaded.");
    } }

  static
  {
    loadNative();
  }
}