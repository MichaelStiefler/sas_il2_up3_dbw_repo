package com.maddox.TexImage;

public class Raster
{
  public static void line(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rasterizable paramRasterizable)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    int k;
    if (i < 0)
    {
      i = -i;
      k = -1;
    }
    else {
      k = 1;
    }
    int m;
    if (j < 0)
    {
      j = -j;
      m = -1;
    }
    else {
      m = 1;
    }
    int n;
    if (i > j)
    {
      n = i >> 1;
      while (paramInt1 != paramInt3)
      {
        paramRasterizable.pixel(paramInt1, paramInt2);
        paramInt1 += k;
        n += j;
        if (n <= i)
          continue;
        paramInt2 += m;
        n -= i;
      }

    }
    else
    {
      n = j >> 1;
      while (paramInt2 != paramInt4)
      {
        paramRasterizable.pixel(paramInt1, paramInt2);
        paramInt2 += m;
        n += i;
        if (n <= j) {
          continue;
        }
        paramInt1 += k;
        n -= j;
      }
    }

    paramRasterizable.pixel(paramInt3, paramInt4);
  }

  public static void line(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Rasterizable paramRasterizable)
  {
    int i = paramInt4 - paramInt1;
    int j = paramInt5 - paramInt2;
    int k = paramInt6 - paramInt3;
    int m;
    if (i < 0)
    {
      i = -i;
      m = -1;
    }
    else {
      m = 1;
    }
    int n;
    if (j < 0)
    {
      j = -j;
      n = -1;
    }
    else {
      n = 1;
    }
    int i1;
    if (k < 0) {
      k = -k;
      i1 = -1;
    } else {
      i1 = 1;
    }
    int i2;
    int i3;
    if (i > Math.max(j, k))
    {
      i2 = i >> 1;
      i3 = i >> 1;
      while (paramInt1 != paramInt4)
      {
        paramRasterizable.pixel(paramInt1, paramInt2, paramInt3);
        paramInt1 += m;
        i2 += j;
        i3 += k;
        if (i2 > i)
        {
          paramInt2 += n;
          i2 -= i;
        }
        if (i3 <= i)
          continue;
        paramInt3 += i1;
        i3 -= i;
      }

    }
    else if (j > Math.max(i, k))
    {
      i2 = j >> 1;
      i3 = j >> 1;
      while (paramInt2 != paramInt5)
      {
        paramRasterizable.pixel(paramInt1, paramInt2, paramInt3);
        paramInt2 += n;
        i2 += i;
        i3 += k;
        if (i2 > j)
        {
          paramInt1 += m;
          i2 -= j;
        }
        if (i3 <= j)
          continue;
        paramInt3 += i1;
        i3 -= j;
      }

    }
    else
    {
      i2 = k >> 1;
      i3 = k >> 1;
      while (paramInt3 != paramInt6)
      {
        paramRasterizable.pixel(paramInt1, paramInt2, paramInt3);
        paramInt3 += i1;
        i2 += i;
        i3 += j;
        if (i2 > k)
        {
          paramInt1 += m;
          i2 -= k;
        }
        if (i3 <= k)
          continue;
        paramInt2 += n;
        i3 -= k;
      }
    }

    paramRasterizable.pixel(paramInt4, paramInt5, paramInt6);
  }
}