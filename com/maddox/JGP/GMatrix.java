// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GMatrix.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            GVector, Matrix3f, Matrix3d, Matrix4f, 
//            Matrix4d

public class GMatrix
    implements java.io.Serializable, java.lang.Cloneable
{

    public GMatrix(int i, int j)
    {
        nRow = i;
        nCol = j;
        elementData = new double[i * j];
        setIdentity();
    }

    public GMatrix(int i, int j, double ad[])
    {
        nRow = i;
        nCol = j;
        elementData = new double[i * j];
        set(ad);
    }

    public GMatrix(com.maddox.JGP.GMatrix gmatrix)
    {
        nRow = gmatrix.nRow;
        nCol = gmatrix.nCol;
        int i = nRow * nCol;
        elementData = new double[i];
        java.lang.System.arraycopy(gmatrix.elementData, 0, elementData, 0, i);
    }

    public final void mul(com.maddox.JGP.GMatrix gmatrix)
    {
        mul(this, gmatrix);
    }

    public final void mul(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        double ad[] = new double[nCol * nRow];
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
            {
                double d = 0.0D;
                for(int k = 0; k < gmatrix.nCol; k++)
                    d += gmatrix.elementData[i * gmatrix.nCol + k] * gmatrix1.elementData[k * gmatrix1.nCol + j];

                ad[i * nCol + j] = d;
            }

        }

        elementData = ad;
    }

    public final void mul(com.maddox.JGP.GVector gvector, com.maddox.JGP.GVector gvector1)
    {
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
                elementData[i * nCol + j] = gvector.getElement(i) * gvector1.getElement(j);

        }

    }

    public final void add(com.maddox.JGP.GMatrix gmatrix)
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] += gmatrix.elementData[i];

    }

    public final void add(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] = gmatrix.elementData[i] + gmatrix.elementData[i];

    }

    public final void sub(com.maddox.JGP.GMatrix gmatrix)
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] -= gmatrix.elementData[i];

    }

    public final void sub(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] = gmatrix.elementData[i] - gmatrix1.elementData[i];

    }

    public final void negate()
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] = -elementData[i];

    }

    public final void negate(com.maddox.JGP.GMatrix gmatrix)
    {
        set(gmatrix);
        negate();
    }

    public final void setIdentity()
    {
        setZero();
        int i = nRow >= nCol ? nCol : nRow;
        for(int j = 0; j < i; j++)
            elementData[j * nCol + j] = 1.0D;

    }

    public final void setZero()
    {
        for(int i = 0; i < nRow * nCol; i++)
            elementData[i] = 0.0D;

    }

    public final void identityMinus()
    {
        negate();
        int i = nRow >= nCol ? nCol : nRow;
        for(int j = 0; j < i; j++)
            elementData[j * nCol + j]++;

    }

    public final void invert()
    {
        int i = nRow;
        com.maddox.JGP.GMatrix gmatrix = new GMatrix(i, i);
        com.maddox.JGP.GVector gvector = new GVector(i);
        com.maddox.JGP.GVector gvector1 = new GVector(i);
        com.maddox.JGP.GVector gvector2 = new GVector(i);
        LUD(gmatrix, gvector);
        for(int j = 0; j < i; j++)
        {
            gvector2.zero();
            gvector2.setElement(j, 1.0D);
            gvector1.LUDBackSolve(gmatrix, gvector2, gvector);
            setColumn(j, gvector1);
        }

    }

    public final void invert(com.maddox.JGP.GMatrix gmatrix)
    {
        set(gmatrix);
        invert();
    }

    public final void copySubMatrix(int i, int j, int k, int l, int i1, int j1, com.maddox.JGP.GMatrix gmatrix)
    {
        for(int k1 = 0; k1 < k; k1++)
        {
            for(int l1 = 0; l1 < l; l1++)
                gmatrix.elementData[(k1 + i1) * nCol + (l1 + j1)] = elementData[(k1 + i) * nCol + (l1 + j)];

        }

    }

    public final void setSize(int i, int j)
    {
        int k = nRow;
        int l = nCol;
        int i1 = nRow * nCol;
        nRow = i;
        nCol = j;
        int j1 = i * j;
        double ad[] = elementData;
        if(l == j)
        {
            if(i <= k)
                return;
            elementData = new double[j1];
            java.lang.System.arraycopy(ad, 0, elementData, 0, i1);
        } else
        {
            elementData = new double[j1];
            setZero();
            for(int k1 = 0; k1 < k; k1++)
                java.lang.System.arraycopy(ad, k1 * l, elementData, k1 * j, l);

        }
    }

    public final void set(double ad[])
    {
        int i = nRow * nCol;
        java.lang.System.arraycopy(ad, 0, elementData, 0, i);
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
    {
        elementData[0] = matrix3f.m00;
        elementData[1] = matrix3f.m01;
        elementData[2] = matrix3f.m02;
        elementData[nCol] = matrix3f.m10;
        elementData[nCol + 1] = matrix3f.m11;
        elementData[nCol + 2] = matrix3f.m12;
        elementData[2 * nCol] = matrix3f.m20;
        elementData[2 * nCol + 1] = matrix3f.m21;
        elementData[2 * nCol + 2] = matrix3f.m22;
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
    {
        elementData[0] = matrix3d.m00;
        elementData[1] = matrix3d.m01;
        elementData[2] = matrix3d.m02;
        elementData[nCol] = matrix3d.m10;
        elementData[nCol + 1] = matrix3d.m11;
        elementData[nCol + 2] = matrix3d.m12;
        elementData[2 * nCol] = matrix3d.m20;
        elementData[2 * nCol + 1] = matrix3d.m21;
        elementData[2 * nCol + 2] = matrix3d.m22;
    }

    public final void set(com.maddox.JGP.Matrix4f matrix4f)
    {
        elementData[0] = matrix4f.m00;
        elementData[1] = matrix4f.m01;
        elementData[2] = matrix4f.m02;
        elementData[3] = matrix4f.m03;
        elementData[nCol] = matrix4f.m10;
        elementData[nCol + 1] = matrix4f.m11;
        elementData[nCol + 2] = matrix4f.m12;
        elementData[nCol + 3] = matrix4f.m13;
        elementData[2 * nCol] = matrix4f.m20;
        elementData[2 * nCol + 1] = matrix4f.m21;
        elementData[2 * nCol + 2] = matrix4f.m22;
        elementData[2 * nCol + 3] = matrix4f.m23;
        elementData[3 * nCol] = matrix4f.m30;
        elementData[3 * nCol + 1] = matrix4f.m31;
        elementData[3 * nCol + 2] = matrix4f.m32;
        elementData[3 * nCol + 3] = matrix4f.m33;
    }

    public final void set(com.maddox.JGP.Matrix4d matrix4d)
    {
        elementData[0] = matrix4d.m00;
        elementData[1] = matrix4d.m01;
        elementData[2] = matrix4d.m02;
        elementData[3] = matrix4d.m03;
        elementData[nCol] = matrix4d.m10;
        elementData[nCol + 1] = matrix4d.m11;
        elementData[nCol + 2] = matrix4d.m12;
        elementData[nCol + 3] = matrix4d.m13;
        elementData[2 * nCol] = matrix4d.m20;
        elementData[2 * nCol + 1] = matrix4d.m21;
        elementData[2 * nCol + 2] = matrix4d.m22;
        elementData[2 * nCol + 3] = matrix4d.m23;
        elementData[3 * nCol] = matrix4d.m30;
        elementData[3 * nCol + 1] = matrix4d.m31;
        elementData[3 * nCol + 2] = matrix4d.m32;
        elementData[3 * nCol + 3] = matrix4d.m33;
    }

    public final void set(com.maddox.JGP.GMatrix gmatrix)
    {
        java.lang.System.arraycopy(gmatrix.elementData, 0, elementData, 0, nRow * nCol);
    }

    public final int getNumRow()
    {
        return nRow;
    }

    public final int getNumCol()
    {
        return nCol;
    }

    public final double getElement(int i, int j)
    {
        return elementData[i * nCol + j];
    }

    public final void setElement(int i, int j, double d)
    {
        elementData[i * nCol + j] = d;
    }

    public final void getRow(int i, double ad[])
    {
        java.lang.System.arraycopy(elementData, i * nCol, ad, 0, nCol);
    }

    public final void getRow(int i, com.maddox.JGP.GVector gvector)
    {
        for(int j = 0; j < nCol; j++)
            gvector.setElement(j, elementData[i * nCol + j]);

    }

    public final void getColumn(int i, double ad[])
    {
        for(int j = 0; j < nRow; j++)
            ad[j] = elementData[j * nCol + i];

    }

    public final void getColumn(int i, com.maddox.JGP.GVector gvector)
    {
        for(int j = 0; j < nRow; j++)
            gvector.setElement(j, elementData[j * nCol + i]);

    }

    public final void get(com.maddox.JGP.Matrix3d matrix3d)
    {
        matrix3d.m00 = elementData[0];
        matrix3d.m01 = elementData[1];
        matrix3d.m02 = elementData[2];
        matrix3d.m10 = elementData[nCol];
        matrix3d.m11 = elementData[nCol + 1];
        matrix3d.m12 = elementData[nCol + 2];
        matrix3d.m20 = elementData[2 * nCol];
        matrix3d.m21 = elementData[2 * nCol + 1];
        matrix3d.m22 = elementData[2 * nCol + 2];
    }

    public final void get(com.maddox.JGP.Matrix3f matrix3f)
    {
        matrix3f.m00 = (float)elementData[0];
        matrix3f.m01 = (float)elementData[1];
        matrix3f.m02 = (float)elementData[2];
        matrix3f.m10 = (float)elementData[nCol];
        matrix3f.m11 = (float)elementData[nCol + 1];
        matrix3f.m12 = (float)elementData[nCol + 2];
        matrix3f.m20 = (float)elementData[2 * nCol];
        matrix3f.m21 = (float)elementData[2 * nCol + 1];
        matrix3f.m22 = (float)elementData[2 * nCol + 2];
    }

    public final void get(com.maddox.JGP.Matrix4d matrix4d)
    {
        matrix4d.m00 = elementData[0];
        matrix4d.m01 = elementData[1];
        matrix4d.m02 = elementData[2];
        matrix4d.m03 = elementData[3];
        matrix4d.m10 = elementData[nCol];
        matrix4d.m11 = elementData[nCol + 1];
        matrix4d.m12 = elementData[nCol + 2];
        matrix4d.m13 = elementData[nCol + 3];
        matrix4d.m20 = elementData[2 * nCol];
        matrix4d.m21 = elementData[2 * nCol + 1];
        matrix4d.m22 = elementData[2 * nCol + 2];
        matrix4d.m23 = elementData[2 * nCol + 3];
        matrix4d.m30 = elementData[3 * nCol];
        matrix4d.m31 = elementData[3 * nCol + 1];
        matrix4d.m32 = elementData[3 * nCol + 2];
        matrix4d.m33 = elementData[3 * nCol + 3];
    }

    public final void get(com.maddox.JGP.Matrix4f matrix4f)
    {
        matrix4f.m00 = (float)elementData[0];
        matrix4f.m01 = (float)elementData[1];
        matrix4f.m02 = (float)elementData[2];
        matrix4f.m03 = (float)elementData[3];
        matrix4f.m10 = (float)elementData[nCol];
        matrix4f.m11 = (float)elementData[nCol + 1];
        matrix4f.m12 = (float)elementData[nCol + 2];
        matrix4f.m13 = (float)elementData[nCol + 3];
        matrix4f.m20 = (float)elementData[2 * nCol];
        matrix4f.m21 = (float)elementData[2 * nCol + 1];
        matrix4f.m22 = (float)elementData[2 * nCol + 2];
        matrix4f.m23 = (float)elementData[2 * nCol + 3];
        matrix4f.m30 = (float)elementData[3 * nCol];
        matrix4f.m31 = (float)elementData[3 * nCol + 1];
        matrix4f.m32 = (float)elementData[3 * nCol + 2];
        matrix4f.m33 = (float)elementData[3 * nCol + 3];
    }

    public final void get(com.maddox.JGP.GMatrix gmatrix)
    {
        java.lang.System.arraycopy(elementData, 0, gmatrix.elementData, 0, nRow * nCol);
    }

    public final void setRow(int i, double ad[])
    {
        java.lang.System.arraycopy(ad, 0, elementData, i * nCol, nCol);
    }

    public final void setRow(int i, com.maddox.JGP.GVector gvector)
    {
        for(int j = 0; j < nCol; j++)
            elementData[i * nCol + j] = gvector.getElement(j);

    }

    public final void setColumn(int i, double ad[])
    {
        for(int j = 0; j < nRow; j++)
            elementData[j * nCol + i] = ad[j];

    }

    public final void setColumn(int i, com.maddox.JGP.GVector gvector)
    {
        int j = gvector.getSize();
        for(int k = 0; k < nRow; k++)
            elementData[k * nCol + i] = gvector.getElement(k);

    }

    public final void mulTransposeBoth(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        mul(gmatrix1, gmatrix);
        transpose();
    }

    public final void mulTransposeRight(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
            {
                double d = 0.0D;
                for(int k = 0; k < gmatrix.nCol; k++)
                    d += gmatrix.elementData[i * nCol + k] * gmatrix1.elementData[j * gmatrix1.nCol + k];

                elementData[i * nCol + j] = d;
            }

        }

    }

    public final void mulTransposeLeft(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1)
    {
        transpose(gmatrix);
        mul(gmatrix1);
    }

    public final void transpose()
    {
        for(int i = 0; i < nRow; i++)
        {
            for(int j = i + 1; j < nCol; j++)
            {
                double d = elementData[i * nCol + j];
                elementData[i * nCol + j] = elementData[j * nCol + i];
                elementData[j * nCol + i] = d;
            }

        }

    }

    public final void transpose(com.maddox.JGP.GMatrix gmatrix)
    {
        set(gmatrix);
        transpose();
    }

    public java.lang.String toString()
    {
        java.lang.String s = java.lang.System.getProperty("line.separator");
        java.lang.StringBuffer stringbuffer = new StringBuffer("[");
        stringbuffer.append(s);
        for(int i = 0; i < nRow; i++)
        {
            stringbuffer.append("  [");
            for(int j = 0; j < nCol; j++)
            {
                if(0 < j)
                    stringbuffer.append("\t");
                stringbuffer.append(elementData[i * nCol + j]);
            }

            if(i + 1 < nRow)
            {
                stringbuffer.append("]");
                stringbuffer.append(s);
            } else
            {
                stringbuffer.append("] ]");
            }
        }

        return stringbuffer.toString();
    }

    public int hashCode()
    {
        int i = 0;
        for(int j = 0; j < nRow * nCol; j++)
        {
            long l = java.lang.Double.doubleToLongBits(elementData[j]);
            i ^= (int)(l ^ l >> 32);
        }

        return i;
    }

    public boolean equals(com.maddox.JGP.GMatrix gmatrix)
    {
        if(gmatrix == null)
            return false;
        if(gmatrix.nRow != nRow)
            return false;
        if(gmatrix.nCol != nCol)
            return false;
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
                if(elementData[i * nCol + j] != gmatrix.elementData[i * nCol + j])
                    return false;

        }

        return true;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.GMatrix) && equals((com.maddox.JGP.GMatrix)obj);
    }

    /**
     * @deprecated Method epsilonEquals is deprecated
     */

    public boolean epsilonEquals(com.maddox.JGP.GMatrix gmatrix, float f)
    {
        if(gmatrix.nRow != nRow)
            return false;
        if(gmatrix.nCol != nCol)
            return false;
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
                if((double)f < java.lang.Math.abs(elementData[i * nCol + j] - gmatrix.elementData[i * nCol + j]))
                    return false;

        }

        return true;
    }

    public boolean epsilonEquals(com.maddox.JGP.GMatrix gmatrix, double d)
    {
        if(gmatrix.nRow != nRow)
            return false;
        if(gmatrix.nCol != nCol)
            return false;
        for(int i = 0; i < nRow; i++)
        {
            for(int j = 0; j < nCol; j++)
                if(d < java.lang.Math.abs(elementData[i * nCol + j] - gmatrix.elementData[i * nCol + j]))
                    return false;

        }

        return true;
    }

    public final double trace()
    {
        int i = nRow >= nCol ? nCol : nRow;
        double d = 0.0D;
        for(int j = 0; j < i; j++)
            d += elementData[j * nCol + j];

        return d;
    }

    public final void setScale(double d)
    {
        setZero();
        int i = nRow >= nCol ? nCol : nRow;
        for(int j = 0; j < i; j++)
            elementData[j * nCol + j] = d;

    }

    private void setDiag(int i, double d)
    {
        elementData[i * nCol + i] = d;
    }

    private double getDiag(int i)
    {
        return elementData[i * nCol + i];
    }

    private double dpythag(double d, double d1)
    {
        double d2 = java.lang.Math.abs(d);
        double d3 = java.lang.Math.abs(d1);
        if(d2 > d3)
        {
            if(d2 == 0.0D)
                return 0.0D;
            double d4 = d3 / d2;
            if(java.lang.Math.abs(d4) <= 4.9406564584124654E-324D)
                return d2;
            else
                return d2 * java.lang.Math.sqrt(1.0D + d4 * d4);
        }
        if(d3 == 0.0D)
            return 0.0D;
        double d5 = d2 / d3;
        if(java.lang.Math.abs(d5) <= 4.9406564584124654E-324D)
            return d3;
        else
            return d3 * java.lang.Math.sqrt(1.0D + d5 * d5);
    }

    public final int SVD(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GMatrix gmatrix1, com.maddox.JGP.GMatrix gmatrix2)
    {
        int i = nRow;
        int j = nCol;
        int k = i <= j ? j : i;
        double ad[] = gmatrix.elementData;
        double ad1[] = gmatrix2.elementData;
        int k10 = 0;
        int j11 = 0;
        double ad2[] = new double[j];
        get(gmatrix);
        for(int l = i; l < k; l++)
        {
            for(int l2 = j; l2 < k;)
            {
                ad[l * j + l2] = 0.0D;
                l++;
            }

        }

        gmatrix1.setZero();
        double d;
        double d25;
        double d9 = d25 = d = 0.0D;
        for(int i1 = 0; i1 < j; i1++)
        {
            k10 = i1 + 1;
            ad2[i1] = d25 * d9;
            double d19;
            d9 = d19 = d25 = 0.0D;
            if(i1 < i)
            {
                for(int k6 = i1; k6 < i; k6++)
                    d25 += java.lang.Math.abs(ad[k6 * i + i1]);

                if(d25 != 0.0D)
                {
                    for(int l6 = i1; l6 < i; l6++)
                    {
                        ad[l6 * i + i1] /= d25;
                        d19 += ad[l6 * i + i1] * ad[l6 * i + i1];
                    }

                    double d3 = ad[i1 * i + i1];
                    d9 = d3 >= 0.0D ? -java.lang.Math.sqrt(d19) : java.lang.Math.sqrt(d19);
                    double d14 = d3 * d9 - d19;
                    ad[i1 * i + i1] = d3 - d9;
                    for(int i3 = k10; i3 < j; i3++)
                    {
                        d19 = 0.0D;
                        for(int i7 = i1; i7 < i; i7++)
                            d19 += ad[i7 * i + i1] * ad[i7 * i + i3];

                        double d4 = d19 / d14;
                        for(int j7 = i1; j7 < i; j7++)
                            ad[j7 * i + i3] += d4 * ad[j7 * i + i1];

                    }

                    for(int k7 = i1; k7 < i; k7++)
                        ad[k7 * i + i1] *= d25;

                }
            }
            gmatrix1.setDiag(i1, d25 * d9);
            d9 = d19 = d25 = 0.0D;
            if(i1 < i && i1 != j - 1)
            {
                for(int l7 = k10; l7 < j; l7++)
                    d25 += java.lang.Math.abs(ad[i1 * i + l7]);

                if(d25 != 0.0D)
                {
                    for(int i8 = k10; i8 < j; i8++)
                    {
                        ad[i1 * i + i8] /= d25;
                        d19 += ad[i1 * i + i8] * ad[i1 * i + i8];
                    }

                    double d5 = ad[i1 * i + k10];
                    d9 = d5 >= 0.0D ? -java.lang.Math.sqrt(d19) : java.lang.Math.sqrt(d19);
                    double d15 = d5 * d9 - d19;
                    ad[i1 * i + k10] = d5 - d9;
                    for(int j8 = k10; j8 < j; j8++)
                        ad2[j8] = ad[i1 * i + j8] / d15;

                    for(int j3 = k10; j3 < i; j3++)
                    {
                        double d20 = 0.0D;
                        for(int k8 = k10; k8 < j; k8++)
                            d20 += ad[j3 * i + k8] * ad[i1 * i + k8];

                        for(int l8 = k10; l8 < j; l8++)
                            ad[j3 * i + l8] += d20 * ad2[l8];

                    }

                    for(int i9 = k10; i9 < j; i9++)
                        ad[i1 * i + i9] *= d25;

                }
            }
            double d35 = java.lang.Math.abs(gmatrix1.getDiag(i1)) + java.lang.Math.abs(ad2[i1]);
            if(d35 > d)
                d = d35;
        }

        for(int j1 = j - 1; j1 >= 0; j1--)
        {
            if(j1 < j - 1)
            {
                if(d9 != 0.0D)
                {
                    for(int k3 = k10; k3 < j; k3++)
                        ad1[k3 * j + j1] = ad[j1 * i + k3] / ad[j1 * i + k10] / d9;

                    for(int l3 = k10; l3 < j; l3++)
                    {
                        double d21 = 0.0D;
                        for(int j9 = k10; j9 < j; j9++)
                            d21 += ad[j1 * i + j9] * ad1[j9 * j + l3];

                        for(int k9 = k10; k9 < j; k9++)
                            ad1[k9 * j + l3] += d21 * ad1[k9 * j + j1];

                    }

                }
                for(int i4 = k10; i4 < j; i4++)
                    ad1[j1 * j + i4] = ad1[i4 * j + j1] = 0.0D;

            }
            ad1[j1 * j + j1] = 1.0D;
            d9 = ad2[j1];
            k10 = j1;
        }

        int k11 = i >= j ? j : i;
        for(int k1 = k11 - 1; k1 >= 0; k1--)
        {
            int l10 = k1 + 1;
            double d10 = gmatrix1.getDiag(k1);
            for(int j4 = l10; j4 < j; j4++)
                ad[k1 * i + j4] = 0.0D;

            if(d10 != 0.0D)
            {
                d10 = 1.0D / d10;
                for(int k4 = l10; k4 < j; k4++)
                {
                    double d22 = 0.0D;
                    for(int l9 = l10; l9 < i; l9++)
                        d22 += ad[l9 * i + k1] * ad[l9 * i + k4];

                    double d6 = (d22 / ad[k1 * i + k1]) * d10;
                    for(int i10 = k1; i10 < i; i10++)
                        ad[i10 * i + k4] += d6 * ad[i10 * i + k1];

                }

                for(int l4 = k1; l4 < i; l4++)
                    ad[l4 * i + k1] *= d10;

            } else
            {
                for(int i5 = k1; i5 < i; i5++)
                    ad[i5 * i + k1] = 0.0D;

            }
            ad[k1 * i + k1]++;
        }

label0:
        for(int j10 = j - 1; j10 >= 0; j10--)
        {
            int k2 = 1;
            do
            {
                if(k2 > 30)
                    continue label0;
                boolean flag = true;
                int i11 = j10;
                do
                {
                    if(i11 < 0)
                        break;
                    j11 = i11 - 1;
                    if(java.lang.Math.abs(ad2[i11]) + d == d)
                    {
                        flag = false;
                        break;
                    }
                    if(java.lang.Math.abs(gmatrix1.getDiag(j11)) + d == d)
                        break;
                    i11--;
                } while(true);
                if(flag)
                {
                    double d1 = 0.0D;
                    double d23 = 1.0D;
                    int l1 = i11;
                    do
                    {
                        if(l1 > j10)
                            break;
                        double d7 = d23 * ad2[l1];
                        ad2[l1] = d1 * ad2[l1];
                        if(java.lang.Math.abs(d7) + d == d)
                            break;
                        double d11 = gmatrix1.getDiag(l1);
                        double d16 = dpythag(d7, d11);
                        gmatrix1.setDiag(l1, d16);
                        d16 = 1.0D / d16;
                        d1 = d11 * d16;
                        d23 = -d7 * d16;
                        for(int j5 = 0; j5 < i; j5++)
                        {
                            double d27 = ad[j5 * i + j11];
                            double d31 = ad[j5 * i + l1];
                            ad[j5 * i + j11] = d27 * d1 + d31 * d23;
                            ad[j5 * i + l1] = d31 * d1 - d27 * d23;
                        }

                        l1++;
                    } while(true);
                }
                double d32 = gmatrix1.getDiag(j10);
                if(i11 == j10)
                {
                    if(d32 >= 0.0D)
                        continue label0;
                    gmatrix1.setDiag(j10, -d32);
                    for(int k5 = 0; k5 < j; k5++)
                        ad1[k5 * j + j10] = -ad1[k5 * j + j10];

                    continue label0;
                }
                if(k2 == 30)
                    return 0;
                double d26 = gmatrix1.getDiag(i11);
                j11 = j10 - 1;
                double d28 = gmatrix1.getDiag(j11);
                double d12 = ad2[j11];
                double d17 = ad2[j10];
                double d8 = ((d28 - d32) * (d28 + d32) + (d12 - d17) * (d12 + d17)) / (2D * d17 * d28);
                d12 = dpythag(d8, 1.0D);
                d8 = ((d26 - d32) * (d26 + d32) + d17 * (d28 / (d8 + (d8 < 0.0D ? -java.lang.Math.abs(d12) : java.lang.Math.abs(d12))) - d17)) / d26;
                double d24;
                double d2 = d24 = 1.0D;
                for(int l5 = i11; l5 <= j11; l5++)
                {
                    int i2 = l5 + 1;
                    double d13 = ad2[i2];
                    double d29 = gmatrix1.getDiag(i2);
                    double d18 = d24 * d13;
                    d13 = d2 * d13;
                    double d33 = dpythag(d8, d18);
                    ad2[l5] = d33;
                    d2 = d8 / d33;
                    d24 = d18 / d33;
                    d8 = d26 * d2 + d13 * d24;
                    d13 = d13 * d2 - d26 * d24;
                    d18 = d29 * d24;
                    d29 *= d2;
                    for(int i6 = 0; i6 < j; i6++)
                    {
                        d26 = ad1[i6 * j + l5];
                        d33 = ad1[i6 * j + i2];
                        ad1[i6 * j + l5] = d26 * d2 + d33 * d24;
                        ad1[i6 * j + i2] = d33 * d2 - d26 * d24;
                    }

                    d33 = dpythag(d8, d18);
                    gmatrix1.setDiag(l5, d33);
                    if(d33 != 0.0D)
                    {
                        d33 = 1.0D / d33;
                        d2 = d8 * d33;
                        d24 = d18 * d33;
                    }
                    d8 = d2 * d13 + d24 * d29;
                    d26 = d2 * d29 - d24 * d13;
                    for(int j6 = 0; j6 < i; j6++)
                    {
                        double d30 = ad[j6 * i + l5];
                        double d34 = ad[j6 * i + i2];
                        ad[j6 * i + l5] = d30 * d2 + d34 * d24;
                        ad[j6 * i + i2] = d34 * d2 - d30 * d24;
                    }

                }

                ad2[i11] = 0.0D;
                ad2[j10] = d8;
                gmatrix1.setDiag(j10, d26);
                k2++;
            } while(true);
        }

        int l11 = 0;
        for(int j2 = 0; j2 < j; j2++)
            if(gmatrix1.getDiag(j2) > 0.0D)
                l11++;

        return l11;
    }

    private void swapRows(int i, int j)
    {
        for(int k = 0; k < nCol; k++)
        {
            double d = elementData[i * nCol + k];
            elementData[i * nCol + k] = elementData[j * nCol + k];
            elementData[j * nCol + k] = d;
        }

    }

    public final int LUD(com.maddox.JGP.GMatrix gmatrix, com.maddox.JGP.GVector gvector)
    {
        int i = nRow;
        if(this != gmatrix)
            gmatrix.set(this);
        int j = 1;
        double ad[] = gmatrix.elementData;
        for(int k = 0; k < i; k++)
            gvector.setElement(k, k);

        for(int l = 0; l < i; l++)
        {
            for(int j1 = 0; j1 < l; j1++)
            {
                double d3 = ad[j1 * i + l];
                for(int i2 = 0; i2 < j1; i2++)
                    if(ad[j1 * i + i2] != 0.0D && ad[i2 * i + l] != 0.0D)
                        d3 -= ad[j1 * i + i2] * ad[i2 * i + l];

                ad[j1 * i + l] = d3;
            }

            double d = 0.0D;
            int i1 = l;
            for(int k1 = l; k1 < i; k1++)
            {
                double d4 = ad[k1 * i + l];
                for(int j2 = 0; j2 < l; j2++)
                    if(ad[k1 * i + j2] != 0.0D && ad[j2 * i + l] != 0.0D)
                        d4 -= ad[k1 * i + j2] * ad[j2 * i + l];

                ad[k1 * i + l] = d4;
                double d1 = java.lang.Math.abs(d4);
                if(d1 >= d)
                {
                    d = d1;
                    i1 = k1;
                }
            }

            if(l != i1)
            {
                gmatrix.swapRows(i1, l);
                double d5 = gvector.getElement(i1);
                gvector.setElement(i1, gvector.getElement(l));
                gvector.setElement(l, d5);
                j = -j;
            }
            if(l == i - 1)
                continue;
            double d2 = 1.0D / ad[l * i + l];
            for(int l1 = l + 1; l1 < i; l1++)
                ad[l1 * i + l] *= d2;

        }

        return j;
    }

    private double elementData[];
    private int nRow;
    private int nCol;
}
