package com.maddox.util;

import java.util.StringTokenizer;

public class NumberTokenizer extends StringTokenizer
{
  public NumberTokenizer(String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramString2, paramBoolean);
  }
  public NumberTokenizer(String paramString1, String paramString2) {
    super(paramString1, paramString2);
  }
  public NumberTokenizer(String paramString) {
    super(paramString);
  }

  public String next()
  {
    try {
      return nextToken(); } catch (Exception localException) {
    }
    return null;
  }

  public String next(String paramString) {
    try {
      return nextToken(); } catch (Exception localException) {
    }
    return paramString;
  }

  public int nextInteger()
  {
    return Integer.parseInt(nextToken());
  }
  public int next(int paramInt) {
    try {
      return Integer.parseInt(nextToken()); } catch (Exception localException) {
    }
    return paramInt;
  }

  public int next(int paramInt1, int paramInt2, int paramInt3) {
    int i = next(paramInt1);
    if (i < paramInt2) i = paramInt2;
    if (i > paramInt3) i = paramInt3;
    return i;
  }

  public float nextFloat() {
    return Float.parseFloat(nextToken());
  }
  public float next(float paramFloat) {
    try {
      return Float.parseFloat(nextToken()); } catch (Exception localException) {
    }
    return paramFloat;
  }

  public float next(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = next(paramFloat1);
    if (f < paramFloat2) f = paramFloat2;
    if (f > paramFloat3) f = paramFloat3;
    return f;
  }

  public double nextDouble() {
    return Double.parseDouble(nextToken());
  }
  public double next(double paramDouble) {
    try {
      return Double.parseDouble(nextToken()); } catch (Exception localException) {
    }
    return paramDouble;
  }

  public double next(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d = next(paramDouble1);
    if (d < paramDouble2) d = paramDouble2;
    if (d > paramDouble3) d = paramDouble3;
    return d;
  }

  public boolean nextBoolean() {
    return nextInteger() != 0;
  }

  public boolean next(boolean paramBoolean) {
    return next(paramBoolean ? 1 : 0) != 0;
  }
}