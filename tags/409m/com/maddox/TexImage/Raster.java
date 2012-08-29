// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Raster.java

package com.maddox.TexImage;


// Referenced classes of package com.maddox.TexImage:
//            Rasterizable

public class Raster
{

    public Raster()
    {
    }

    public static void line(int i, int j, int k, int l, com.maddox.TexImage.Rasterizable rasterizable)
    {
        int i1 = k - i;
        int j1 = l - j;
        byte byte0;
        if(i1 < 0)
        {
            i1 = -i1;
            byte0 = -1;
        } else
        {
            byte0 = 1;
        }
        byte byte1;
        if(j1 < 0)
        {
            j1 = -j1;
            byte1 = -1;
        } else
        {
            byte1 = 1;
        }
        if(i1 > j1)
        {
            int k1 = i1 >> 1;
            while(i != k) 
            {
                rasterizable.pixel(i, j);
                i += byte0;
                k1 += j1;
                if(k1 > i1)
                {
                    j += byte1;
                    k1 -= i1;
                }
            }
        } else
        {
            int l1 = j1 >> 1;
            while(j != l) 
            {
                rasterizable.pixel(i, j);
                j += byte1;
                l1 += i1;
                if(l1 > j1)
                {
                    i += byte0;
                    l1 -= j1;
                }
            }
        }
        rasterizable.pixel(k, l);
    }

    public static void line(int i, int j, int k, int l, int i1, int j1, com.maddox.TexImage.Rasterizable rasterizable)
    {
        int k1 = l - i;
        int l1 = i1 - j;
        int i2 = j1 - k;
        byte byte0;
        if(k1 < 0)
        {
            k1 = -k1;
            byte0 = -1;
        } else
        {
            byte0 = 1;
        }
        byte byte1;
        if(l1 < 0)
        {
            l1 = -l1;
            byte1 = -1;
        } else
        {
            byte1 = 1;
        }
        byte byte2;
        if(i2 < 0)
        {
            i2 = -i2;
            byte2 = -1;
        } else
        {
            byte2 = 1;
        }
        if(k1 > java.lang.Math.max(l1, i2))
        {
            int j2 = k1 >> 1;
            int i3 = k1 >> 1;
            while(i != l) 
            {
                rasterizable.pixel(i, j, k);
                i += byte0;
                j2 += l1;
                i3 += i2;
                if(j2 > k1)
                {
                    j += byte1;
                    j2 -= k1;
                }
                if(i3 > k1)
                {
                    k += byte2;
                    i3 -= k1;
                }
            }
        } else
        if(l1 > java.lang.Math.max(k1, i2))
        {
            int k2 = l1 >> 1;
            int j3 = l1 >> 1;
            while(j != i1) 
            {
                rasterizable.pixel(i, j, k);
                j += byte1;
                k2 += k1;
                j3 += i2;
                if(k2 > l1)
                {
                    i += byte0;
                    k2 -= l1;
                }
                if(j3 > l1)
                {
                    k += byte2;
                    j3 -= l1;
                }
            }
        } else
        {
            int l2 = i2 >> 1;
            int k3 = i2 >> 1;
            while(k != j1) 
            {
                rasterizable.pixel(i, j, k);
                k += byte2;
                l2 += k1;
                k3 += l1;
                if(l2 > i2)
                {
                    i += byte0;
                    l2 -= i2;
                }
                if(k3 > i2)
                {
                    j += byte1;
                    k3 -= i2;
                }
            }
        }
        rasterizable.pixel(l, i1, j1);
    }
}
