package com.maddox.rts;

import com.maddox.il2.builder.PathFind;

public final class Cpu86ID
{
  public static final int _CPUID = 1;
  public static final int _STD_FEATURES = 2;
  public static final int _EXT_FEATURES = 4;
  public static final int _TSC = 16;
  public static final int _MMX = 32;
  public static final int _CMOV = 64;
  public static final int _3DNOW = 128;
  public static final int _3DNOWEXT = 256;
  public static final int _MMXEXT = 512;
  public static final int _SSEFP = 1024;
  public static final int _K6_MTRR = 2048;
  public static final int _P6_MTRR = 4096;
  public static final int _SSE2 = 8192;
  public static final int CPU_VENDOR_UNKNOWN = 0;
  public static final int CPU_VENDOR_INTEL = 1;
  public static final int CPU_VENDOR_AMD = 2;

  public static native int get();

  private static native int getvendor();

  public static int getVendor()
  {
    return getvendor();
  }

  public static boolean isMMX()
  {
    int i = get();
    if ((i & 0x1) == 0) return false;
    return (i & 0x220) != 0;
  }

  public static boolean isSSE()
  {
    int i = get();
    if ((i & 0x1) == 0) return false;
    return (i & 0x400) != 0;
  }

  public static boolean isSSE2()
  {
    int i = PathFind.getI();
    if ((i & 0x1) == 0) return false;
    return (i & 0x2000) != 0;
  }

  public static boolean is3DNOW()
  {
    int i = get();
    if ((i & 0x1) == 0) return false;
    return (i & 0x180) != 0;
  }

  public static native int getMask();

  static {
    RTS.loadNative();
  }
}