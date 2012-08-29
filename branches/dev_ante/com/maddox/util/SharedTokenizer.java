package com.maddox.util;

public class SharedTokenizer
{
  private static char[] buf = new char[''];
  private static int ptr;
  private static int end;
  private static int start;
  private static int stop;

  public static void set(String paramString)
  {
    int i = paramString.length();
    if (i > buf.length)
      buf = new char[i];
    paramString.getChars(0, i, buf, 0);
    ptr = 0; end = i;

    while (ptr < end) {
      if (buf[ptr] > ' ') break; ptr += 1;
    }

    while (end > ptr) {
      if (buf[(end - 1)] > ' ') break; end -= 1;
    }
  }

  public static String getGap()
  {
    if (ptr >= end) return null;
    start = ptr;
    stop = end;
    ptr = end;
    while (stop > start) {
      if (buf[(stop - 1)] > ' ') break;
      stop -= 1;
    }
    if (stop <= start)
      return null;
    return new String(buf, start, stop - start);
  }

  public static boolean hasMoreTokens() {
    return ptr < end;
  }

  public static int countTokens() {
    int i = 0;
    int j = ptr;
    while (j < end) {
      i++;
      while (j < end) {
        if (buf[j] <= ' ') break; j++;
      }

      while (j < end) {
        if (buf[j] > ' ') break; j++;
      }
    }

    return i;
  }

  private static void nextWord() {
    start = ptr;
    while (ptr < end) {
      if (buf[ptr] <= ' ') break; ptr += 1;
    }

    stop = ptr;
    while (ptr < end) {
      if (buf[ptr] > ' ') break; ptr += 1;
    }
  }

  public static void _nextWord() {
    nextWord();
  }

  public static String nextToken() {
    if (ptr >= end) return null;
    nextWord();
    return new String(buf, start, stop - start);
  }

  public static String next() {
    return nextToken();
  }

  public static String next(String paramString) {
    String str = nextToken();
    if (str != null)
      return str;
    return paramString;
  }

  public static String _getString() {
    if (start >= end) return null;
    return new String(buf, start, stop - start);
  }
  public static int _getInt() throws NumberFormatException {
    if (start >= end) {
      throw new NumberFormatException();
    }
    int i = 10;
    int j = 0;
    int k = 0;
    int m = start; int n = stop;
    int i1;
    if (buf[m] == '-') {
      k = 1;
      i1 = -2147483648;
      m++;
    } else {
      i1 = -2147483647;
    }
    int i2 = i1 / i;
    int i3;
    if (m < n) {
      i3 = Character.digit(buf[(m++)], i);
      if (i3 < 0) {
        throw new NumberFormatException();
      }
      j = -i3;
    }

    while (m < n)
    {
      i3 = Character.digit(buf[(m++)], i);
      if (i3 < 0) {
        throw new NumberFormatException();
      }
      if (j < i2) {
        throw new NumberFormatException();
      }
      j *= i;
      if (j < i1 + i3) {
        throw new NumberFormatException();
      }
      j -= i3;
    }
    if (k != 0) {
      if (m > start + 1) {
        return j;
      }
      throw new NumberFormatException();
    }

    return -j;
  }

  public static int next(int paramInt)
  {
    if (ptr >= end) return paramInt;
    nextWord();

    int i = 10;
    int j = 0;
    int k = 0;
    int m = start; int n = stop;
    int i1;
    if (buf[m] == '-') {
      k = 1;
      i1 = -2147483648;
      m++;
    } else {
      i1 = -2147483647;
    }
    int i2 = i1 / i;
    int i3;
    if (m < n) {
      i3 = Character.digit(buf[(m++)], i);
      if (i3 < 0) {
        return paramInt;
      }
      j = -i3;
    }

    while (m < n)
    {
      i3 = Character.digit(buf[(m++)], i);
      if (i3 < 0) {
        return paramInt;
      }
      if (j < i2) {
        return paramInt;
      }
      j *= i;
      if (j < i1 + i3) {
        return paramInt;
      }
      j -= i3;
    }
    if (k != 0) {
      if (m > start + 1) {
        return j;
      }
      return paramInt;
    }

    return -j;
  }

  public static int next(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = next(paramInt1);
    if (i < paramInt2) i = paramInt2;
    if (i > paramInt3) i = paramInt3;
    return i;
  }

  public static double next(double paramDouble) {
    if (ptr >= end) return paramDouble; try
    {
      return Double.parseDouble(next()); } catch (Exception localException) {
    }
    return paramDouble;
  }

  public static double next(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = next(paramDouble1);
    if (d < paramDouble2) d = paramDouble2;
    if (d > paramDouble3) d = paramDouble3;
    return d;
  }

  public static boolean next(boolean paramBoolean) {
    return next(paramBoolean ? 1 : 0) != 0;
  }
}