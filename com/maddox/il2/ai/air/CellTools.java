// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CellTools.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import java.io.Serializable;
import java.util.ArrayList;

public class CellTools
    implements java.io.Serializable
{

    public CellTools()
    {
    }

    public static boolean findIntersect(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3, com.maddox.JGP.Point2d point2d)
    {
        double d = 0.0D;
        double d2 = 0.0D;
        double d4 = (point3d1.y - point3d.y) * (point3d3.x - point3d2.x) - (point3d3.y - point3d2.y) * (point3d1.x - point3d.x);
        double d5 = (point3d2.y - point3d.y) * (point3d1.x - point3d.x) - (point3d1.y - point3d.y) * (point3d2.x - point3d.x);
        double d6 = (point3d2.y - point3d.y) * (point3d3.x - point3d2.x) - (point3d3.y - point3d2.y) * (point3d2.x - point3d.x);
        if(d4 != 0.0D)
        {
            double d1 = d5 / d4;
            double d3 = d6 / d4;
            if(d1 <= 1.0D && d1 >= 0.0D && d3 >= 0.0D && d3 <= 1.0D)
            {
                if(point2d != null)
                {
                    point2d.x = point3d.x + (point3d1.x - point3d.x) * d3;
                    point2d.y = point3d.y + (point3d1.y - point3d.y) * d3;
                }
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return true;
        }
    }

    public static boolean findIntersect(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        double d = 0.0D;
        double d2 = 0.0D;
        double d4 = (point3d1.y - point3d.y) * (point3d3.x - point3d2.x) - (point3d3.y - point3d2.y) * (point3d1.x - point3d.x);
        double d5 = (point3d2.y - point3d.y) * (point3d1.x - point3d.x) - (point3d1.y - point3d.y) * (point3d2.x - point3d.x);
        double d6 = (point3d2.y - point3d.y) * (point3d3.x - point3d2.x) - (point3d3.y - point3d2.y) * (point3d2.x - point3d.x);
        if(d4 != 0.0D)
        {
            double d1 = d5 / d4;
            double d3 = d6 / d4;
            return d1 <= 1.0D && d1 >= 0.0D && d3 >= 0.0D && d3 <= 1.0D;
        } else
        {
            return true;
        }
    }

    public static double intersectLineSphere(double d, double d1, double d2, double d3, 
            double d4, double d5, double d6)
    {
        double d7 = d6 * d6;
        double d8 = d2 - d;
        double d9 = d3 - d1;
        double d10 = d8 * d8 + d9 * d9;
        if(d10 < 9.9999999999999995E-007D)
            return d7 < (d - d4) * (d - d4) + (d1 - d5) * (d1 - d5) ? -1D : 0.0D;
        double d11 = ((d4 - d) * d8 + (d5 - d1) * d9) / d10;
        if(d11 >= 0.0D && d11 <= 1.0D)
        {
            double d12 = d + d11 * d8;
            double d14 = d1 + d11 * d9;
            double d16 = (d12 - d4) * (d12 - d4) + (d14 - d5) * (d14 - d5);
            double d17 = d7 - d16;
            if(d17 < 0.0D)
                return -1D;
            d11 -= java.lang.Math.sqrt(d17 / d10);
            if(d11 < 0.0D)
                d11 = 0.0D;
            return d11;
        }
        double d13 = (d2 - d4) * (d2 - d4) + (d3 - d5) * (d3 - d5);
        double d15 = (d - d4) * (d - d4) + (d1 - d5) * (d1 - d5);
        if(d13 <= d7 || d15 <= d7)
            return d13 >= d15 ? 0.0D : 1.0D;
        else
            return -1D;
    }

    public static boolean belongsToPoly(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        double d = (((point3d3.x - point3d.x) * (point3d1.y - point3d.y)) / (point3d1.x - point3d.x) + point3d.y) - point3d3.y;
        double d1 = (((point3d2.x - point3d.x) * (point3d1.y - point3d.y)) / (point3d1.x - point3d.x) + point3d.y) - point3d2.y;
        if(d * d1 >= 0.0D)
            flag = true;
        d = (((point3d3.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d3.y;
        d1 = (((point3d.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d.y;
        if(d * d1 >= 0.0D)
            flag1 = true;
        d = (((point3d3.x - point3d2.x) * (point3d.y - point3d2.y)) / (point3d.x - point3d2.x) + point3d2.y) - point3d3.y;
        d1 = (((point3d1.x - point3d2.x) * (point3d.y - point3d2.y)) / (point3d.x - point3d2.x) + point3d2.y) - point3d1.y;
        if(d * d1 >= 0.0D)
            flag2 = true;
        return flag && flag1 && flag2;
    }

    public static boolean belongsToQuad(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3, com.maddox.JGP.Point3d point3d4)
    {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        double d = (((point3d4.x - point3d.x) * (point3d1.y - point3d.y)) / (point3d1.x - point3d.x) + point3d.y) - point3d4.y;
        double d1 = (((point3d2.x - point3d.x) * (point3d1.y - point3d.y)) / (point3d1.x - point3d.x) + point3d.y) - point3d2.y;
        if(d * d1 >= 0.0D)
            flag = true;
        d = (((point3d4.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d4.y;
        d1 = (((point3d.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d.y;
        if(d * d1 >= 0.0D)
            flag1 = true;
        d = (((point3d4.x - point3d2.x) * (point3d3.y - point3d2.y)) / (point3d3.x - point3d2.x) + point3d2.y) - point3d4.y;
        d1 = (((point3d.x - point3d2.x) * (point3d3.y - point3d2.y)) / (point3d3.x - point3d2.x) + point3d2.y) - point3d.y;
        if(d * d1 >= 0.0D)
            flag2 = true;
        d = (((point3d4.x - point3d3.x) * (point3d.y - point3d3.y)) / (point3d.x - point3d3.x) + point3d3.y) - point3d4.y;
        d1 = (((point3d1.x - point3d3.x) * (point3d.y - point3d3.y)) / (point3d.x - point3d3.x) + point3d3.y) - point3d1.y;
        if(d * d1 >= 0.0D)
            flag3 = true;
        return flag && flag1 && flag2 && flag3;
    }

    public static boolean belongsToComplex(java.util.ArrayList arraylist, com.maddox.JGP.Point3d point3d)
    {
        char ac[] = new char[arraylist.size()];
        for(int l = 0; l < arraylist.size(); l++)
        {
            int i = l;
            int j = l >= arraylist.size() - 1 ? 0 : l + 1;
            int k = j >= arraylist.size() - 1 ? 0 : j + 1;
            com.maddox.JGP.Point3d point3d1 = (com.maddox.JGP.Point3d)arraylist.get(i);
            com.maddox.JGP.Point3d point3d2 = (com.maddox.JGP.Point3d)arraylist.get(j);
            com.maddox.JGP.Point3d point3d3 = (com.maddox.JGP.Point3d)arraylist.get(k);
            double d = (((point3d.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d.y;
            double d1 = (((point3d3.x - point3d1.x) * (point3d2.y - point3d1.y)) / (point3d2.x - point3d1.x) + point3d1.y) - point3d3.y;
            ac[l] = (char)(d * d1 < 0.0D ? 0 : 1);
        }

        for(int i1 = 0; i1 < arraylist.size(); i1++)
            if(ac[i1] == 0)
                return false;

        return true;
    }
}
