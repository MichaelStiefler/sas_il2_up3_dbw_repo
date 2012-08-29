package com.maddox.rts;

public final class Compress
{
  public static final int NONE = 0;
  public static final int LZSS = 1;
  public static final int ZIP = 2;

  public static final int code(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    if (paramInt1 == 0) return paramInt2;
    return Code(paramInt1, paramArrayOfByte, paramInt2);
  }
  public static final boolean decode(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
    if (paramInt1 == 0) return true;
    return Decode(paramInt1, paramArrayOfByte, paramInt2);
  }
  private static native int Code(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  private static native boolean Decode(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  static { RTS.loadNative();
  }
}