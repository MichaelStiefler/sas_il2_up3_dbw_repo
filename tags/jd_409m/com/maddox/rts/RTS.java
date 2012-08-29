package com.maddox.rts;

import java.io.InputStream;
import java.io.PrintStream;

public final class RTS extends LDRCallBack
{
  private int[] getSwTbl(int paramInt)
  {
    if (paramInt < 0) paramInt = -paramInt;
    int i = paramInt % 16 + 14;
    int j = paramInt % Finger.kTable.length;
    if (i < 0)
      i = -i % 16;
    if (i < 10)
      i = 10;
    if (j < 0)
      j = -j % Finger.kTable.length;
    int[] arrayOfInt = new int[i];
    for (int k = 0; k < i; k++)
      arrayOfInt[k] = Finger.kTable[((j + k) % Finger.kTable.length)];
    return arrayOfInt;
  }

  protected final byte[] load(String paramString)
  {
    try {
      int i = Finger.Int("sdw" + paramString + "cwc2w9e");
      Finger.Int(getSwTbl(i));
      SFSInputStream localSFSInputStream = new SFSInputStream(Finger.LongFN(0L, "cod/" + i));
      byte[] arrayOfByte = new byte[localSFSInputStream.available()];
      localSFSInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      return arrayOfByte; } catch (Exception localException) {
    }
    return null;
  }

  protected final InputStream open(String paramString)
  {
    try {
      return new SFSInputStream(paramString); } catch (Exception localException) {
    }
    return null;
  }

  public static void cppErrPrint(String paramString)
  {
    System.err.print(paramString);
  }
  public static native int version();

  public static native void setPostProcessCmd(String paramString);

  public static native int interf();

  public static final void loadNative() { SFSInputStream.loadNative(); }

  static
  {
    loadNative();
  }
}