// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GVector.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            GMatrix, Tuple2f, Tuple3f, Tuple3d, 
//            Tuple4f, Tuple4d

public class GVector
    implements java.io.Serializable, java.lang.Cloneable
{

    public GVector(int i)
    {
        elementCount = i;
        elementData = new double[i];
    }

    public GVector(double ad[])
    {
        this(ad.length);
        java.lang.System.arraycopy(ad, 0, elementData, 0, elementCount);
    }

    public GVector(com.maddox.JGP.GVector gvector)
    {
        this(gvector.elementCount);
        java.lang.System.arraycopy(gvector.elementData, 0, elementData, 0, elementCount);
    }

    public GVector(com.maddox.JGP.Tuple2f tuple2f)
    {
        this(2);
        set(tuple2f);
    }

    public GVector(com.maddox.JGP.Tuple3f tuple3f)
    {
        this(3);
        set(tuple3f);
    }

    public GVector(com.maddox.JGP.Tuple3d tuple3d)
    {
        this(3);
        set(tuple3d);
    }

    public GVector(com.maddox.JGP.Tuple4f tuple4f)
    {
        this(4);
        set(tuple4f);
    }

    public GVector(com.maddox.JGP.Tuple4d tuple4d)
    {
        this(4);
        set(tuple4d);
    }

    public GVector(double ad[], int i)
    {
        this(i);
        java.lang.System.arraycopy(ad, 0, elementData, 0, elementCount);
    }

    public final double norm()
    {
        return java.lang.Math.sqrt(normSquared());
    }

    public final double normSquared()
    {
        double d = 0.0D;
        for(int i = 0; i < elementCount; i++)
            d += elementData[i] * elementData[i];

        return d;
    }

    public final void normalize(com.maddox.JGP.GVector gvector)
    {
        set(gvector);
        normalize();
    }

    public final void normalize()
    {
        double d = norm();
        for(int i = 0; i < elementCount; i++)
            elementData[i] /= d;

    }

    public final void scale(double d, com.maddox.JGP.GVector gvector)
    {
        set(gvector);
        scale(d);
    }

    public final void scale(double d)
    {
        for(int i = 0; i < elementCount; i++)
            elementData[i] *= d;

    }

    public final void scaleAdd(double d, com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1)
    {
        double ad[] = gvector.elementData;
        double ad1[] = gvector1.elementData;
        for(int i = 0; i < elementCount; i++)
            elementData[i] = d * ad[i] + ad1[i];

    }

    public final void add(com.maddox.JGP.GVector gvector)
    {
        double ad[] = gvector.elementData;
        for(int i = 0; i < elementCount; i++)
            elementData[i] += ad[i];

    }

    public final void add(com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1)
    {
        set(gvector);
        add(gvector1);
    }

    public final void sub(com.maddox.JGP.GVector gvector)
    {
        double ad[] = gvector.elementData;
        for(int i = 0; i < elementCount; i++)
            elementData[i] -= ad[i];

    }

    public final void sub(com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1)
    {
        set(gvector);
        sub(gvector1);
    }

    public final void mul(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GVector gvector)
    {
        double ad[] = gvector.elementData;
        int i = gvector.elementCount;
        int j = gmatrix.getNumCol();
        int k = gmatrix.getNumRow();
        for(int l = 0; l < elementCount; l++)
        {
            double d = 0.0D;
            for(int i1 = 0; i1 < j; i1++)
                d += gmatrix.getElement(l, i1) * ad[i1];

            elementData[l] = d;
        }

    }

    public final void mul(com.maddox.JGP.GVector gvector, com.maddox.JGP.GMatrix gmatrix)
    {
        double ad[] = gvector.elementData;
        int i = gvector.elementCount;
        int j = gmatrix.getNumCol();
        int k = gmatrix.getNumRow();
        for(int l = 0; l < elementCount; l++)
        {
            double d = 0.0D;
            for(int i1 = 0; i1 < k; i1++)
                d += gmatrix.getElement(i1, l) * ad[i1];

            elementData[l] = d;
        }

    }

    public final void negate()
    {
        for(int i = 0; i < elementCount; i++)
            elementData[i] = -elementData[i];

    }

    public final void zero()
    {
        for(int i = 0; i < elementCount; i++)
            elementData[i] = 0.0D;

    }

    public final void setSize(int i)
    {
        if(elementCount < i)
        {
            double ad[] = elementData;
            elementData = new double[i];
            java.lang.System.arraycopy(ad, 0, elementData, 0, elementCount);
        }
        elementCount = i;
    }

    public final void set(double ad[])
    {
        java.lang.System.arraycopy(ad, 0, elementData, 0, elementCount);
    }

    public final void set(com.maddox.JGP.GVector gvector)
    {
        java.lang.System.arraycopy(gvector.elementData, 0, elementData, 0, elementCount);
    }

    public final void set(com.maddox.JGP.Tuple2f tuple2f)
    {
        elementData[0] = tuple2f.x;
        elementData[1] = tuple2f.y;
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        elementData[0] = tuple3f.x;
        elementData[1] = tuple3f.y;
        elementData[2] = tuple3f.z;
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        elementData[0] = tuple3d.x;
        elementData[1] = tuple3d.y;
        elementData[2] = tuple3d.z;
    }

    public final void set(com.maddox.JGP.Tuple4f tuple4f)
    {
        elementData[0] = tuple4f.x;
        elementData[1] = tuple4f.y;
        elementData[2] = tuple4f.z;
        elementData[3] = tuple4f.w;
    }

    public final void set(com.maddox.JGP.Tuple4d tuple4d)
    {
        elementData[0] = tuple4d.x;
        elementData[1] = tuple4d.y;
        elementData[2] = tuple4d.z;
        elementData[3] = tuple4d.w;
    }

    public final int getSize()
    {
        return elementCount;
    }

    public final double getElement(int i)
    {
        return elementData[i];
    }

    public final void setElement(int i, double d)
    {
        elementData[i] = d;
    }

    public java.lang.String toString()
    {
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("(");
        for(int i = 0; i < elementCount - 1; i++)
        {
            stringbuffer.append(elementData[i]);
            stringbuffer.append(",");
        }

        stringbuffer.append(elementData[elementCount - 1]);
        stringbuffer.append(")");
        return stringbuffer.toString();
    }

    public int hashCode()
    {
        int i = 0;
        for(int j = 0; j < elementCount; j++)
        {
            long l = java.lang.Double.doubleToLongBits(elementData[j]);
            i ^= (int)(l ^ l >> 32);
        }

        return i;
    }

    public boolean equals(com.maddox.JGP.GVector gvector)
    {
        if(gvector == null)
            return false;
        if(elementCount != gvector.elementCount)
            return false;
        double ad[] = gvector.elementData;
        for(int i = 0; i < elementCount; i++)
            if(elementData[i] != ad[i])
                return false;

        return true;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.GVector) && equals((com.maddox.JGP.GVector)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.GVector gvector, double d)
    {
        if(elementCount != gvector.elementCount)
            return false;
        double ad[] = gvector.elementData;
        for(int i = 0; i < elementCount; i++)
            if(java.lang.Math.abs(elementData[i] - ad[i]) > d)
                return false;

        return true;
    }

    public final double dot(com.maddox.JGP.GVector gvector)
    {
        double ad[] = gvector.elementData;
        double d = 0.0D;
        for(int i = 0; i < elementCount; i++)
            d += elementData[i] * ad[i];

        return d;
    }

    public final void SVDBackSolve(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1, com.maddox.JGP.GMatrix gmatrix2, com.maddox.JGP.GVector gvector)
    {
        int i = gmatrix.getNumRow();
        int j = gmatrix2.getNumRow();
        double ad[] = new double[j];
        for(int k = 0; k < j; k++)
        {
            double d = 0.0D;
            double d2 = gmatrix1.getElement(k, k);
            if(d2 != 0.0D)
            {
                for(int j1 = 0; j1 < i; j1++)
                    d += gmatrix.getElement(j1, k) * gvector.elementData[j1];

                d /= d2;
            }
            ad[k] = d;
        }

        for(int l = 0; l < j; l++)
        {
            double d1 = 0.0D;
            for(int i1 = 0; i1 < j; i1++)
                d1 += gmatrix2.getElement(l, i1) * ad[i1];

            elementData[l] = d1;
        }

    }

    public final void LUDBackSolve(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1)
    {
        int i = elementCount;
        double ad[] = gvector1.elementData;
        double ad1[] = elementData;
        double ad2[] = gvector.elementData;
        for(int j = 0; j < i; j++)
            ad1[j] = ad2[(int)ad[j]];

        int k = -1;
        for(int l = 0; l < i; l++)
        {
            double d = ad1[l];
            if(0 <= k)
            {
                for(int j1 = k; j1 <= l - 1; j1++)
                    d -= gmatrix.getElement(l, j1) * ad1[j1];

            } else
            if(d != 0.0D)
                k = l;
            ad1[l] = d;
        }

        for(int i1 = i - 1; i1 >= 0; i1--)
        {
            double d1 = ad1[i1];
            for(int k1 = i1 + 1; k1 < i; k1++)
                d1 -= gmatrix.getElement(i1, k1) * ad1[k1];

            ad1[i1] = d1 / gmatrix.getElement(i1, i1);
        }

    }

    public final double angle(com.maddox.JGP.GVector gvector)
    {
        return java.lang.Math.acos(dot(gvector) / norm() / gvector.norm());
    }

    /**
     * @deprecated Method interpolate is deprecated
     */

    public final void interpolate(com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1, float f)
    {
        interpolate(gvector, gvector1, f);
    }

    /**
     * @deprecated Method interpolate is deprecated
     */

    public final void interpolate(com.maddox.JGP.GVector gvector, float f)
    {
        interpolate(gvector, f);
    }

    public final void interpolate(com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1, double d)
    {
        double ad[] = gvector.elementData;
        double ad1[] = gvector1.elementData;
        for(int i = 0; i < elementCount; i++)
            elementData[i] = ad[i] + d * (ad1[i] - ad[i]);

    }

    public final void interpolate(com.maddox.JGP.GVector gvector, double d)
    {
        double ad[] = gvector.elementData;
        for(int i = 0; i < elementCount; i++)
            elementData[i] += d * (ad[i] - elementData[i]);

    }

    private int elementCount;
    private double elementData[];
}
