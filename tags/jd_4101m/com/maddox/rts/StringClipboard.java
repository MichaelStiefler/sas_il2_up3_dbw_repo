package com.maddox.rts;

public final class StringClipboard
{
  public static void copy(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return;
    Copy(paramString);
  }

  public static String paste() {
    String str = Paste();
    if (str == null) str = "";
    return str; } 
  private static native void Copy(String paramString);

  private static native String Paste();

  static { RTS.loadNative();
  }
}