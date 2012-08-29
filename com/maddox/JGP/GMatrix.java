package com.maddox.JGP;

import java.io.Serializable;

public class GMatrix
  implements Serializable, Cloneable
{
  private double[] elementData;
  private int nRow;
  private int nCol;

  public GMatrix(int paramInt1, int paramInt2)
  {
    this.nRow = paramInt1;
    this.nCol = paramInt2;
    this.elementData = new double[paramInt1 * paramInt2];
    setIdentity();
  }

  public GMatrix(int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    this.nRow = paramInt1;
    this.nCol = paramInt2;
    this.elementData = new double[paramInt1 * paramInt2];
    set(paramArrayOfDouble);
  }

  public GMatrix(GMatrix paramGMatrix)
  {
    this.nRow = paramGMatrix.nRow;
    this.nCol = paramGMatrix.nCol;
    int i = this.nRow * this.nCol;
    this.elementData = new double[i];
    System.arraycopy(paramGMatrix.elementData, 0, this.elementData, 0, i);
  }

  public final void mul(GMatrix paramGMatrix)
  {
    mul(this, paramGMatrix);
  }

  public final void mul(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    double[] arrayOfDouble = new double[this.nCol * this.nRow];
    for (int i = 0; i < this.nRow; i++) {
      for (int j = 0; j < this.nCol; j++) {
        double d = 0.0D;
        for (int k = 0; k < paramGMatrix1.nCol; k++)
          d += paramGMatrix1.elementData[(i * paramGMatrix1.nCol + k)] * paramGMatrix2.elementData[(k * paramGMatrix2.nCol + j)];
        arrayOfDouble[(i * this.nCol + j)] = d;
      }
    }
    this.elementData = arrayOfDouble;
  }

  public final void mul(GVector paramGVector1, GVector paramGVector2)
  {
    for (int i = 0; i < this.nRow; i++)
      for (int j = 0; j < this.nCol; j++)
        this.elementData[(i * this.nCol + j)] = (paramGVector1.getElement(i) * paramGVector2.getElement(j));
  }

  public final void add(GMatrix paramGMatrix)
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      this.elementData[i] += paramGMatrix.elementData[i];
  }

  public final void add(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      paramGMatrix1.elementData[i] += paramGMatrix1.elementData[i];
  }

  public final void sub(GMatrix paramGMatrix)
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      this.elementData[i] -= paramGMatrix.elementData[i];
  }

  public final void sub(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      paramGMatrix1.elementData[i] -= paramGMatrix2.elementData[i];
  }

  public final void negate()
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      this.elementData[i] = (-this.elementData[i]);
  }

  public final void negate(GMatrix paramGMatrix)
  {
    set(paramGMatrix);
    negate();
  }

  public final void setIdentity()
  {
    setZero();
    int i = this.nRow < this.nCol ? this.nRow : this.nCol;
    for (int j = 0; j < i; j++)
      this.elementData[(j * this.nCol + j)] = 1.0D;
  }

  public final void setZero()
  {
    for (int i = 0; i < this.nRow * this.nCol; i++)
      this.elementData[i] = 0.0D;
  }

  public final void identityMinus()
  {
    negate();
    int i = this.nRow < this.nCol ? this.nRow : this.nCol;
    for (int j = 0; j < i; j++)
      this.elementData[(j * this.nCol + j)] += 1.0D;
  }

  public final void invert()
  {
    int i = this.nRow;

    GMatrix localGMatrix = new GMatrix(i, i);
    GVector localGVector1 = new GVector(i);
    GVector localGVector2 = new GVector(i);
    GVector localGVector3 = new GVector(i);
    LUD(localGMatrix, localGVector1);
    for (int j = 0; j < i; j++) {
      localGVector3.zero();
      localGVector3.setElement(j, 1.0D);
      localGVector2.LUDBackSolve(localGMatrix, localGVector3, localGVector1);
      setColumn(j, localGVector2);
    }
  }

  public final void invert(GMatrix paramGMatrix)
  {
    set(paramGMatrix);
    invert();
  }

  public final void copySubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, GMatrix paramGMatrix)
  {
    for (int i = 0; i < paramInt3; i++)
      for (int j = 0; j < paramInt4; j++)
        paramGMatrix.elementData[((i + paramInt5) * this.nCol + (j + paramInt6))] = this.elementData[((i + paramInt1) * this.nCol + (j + paramInt2))];
  }

  public final void setSize(int paramInt1, int paramInt2)
  {
    int i = this.nRow;
    int j = this.nCol;
    int k = this.nRow * this.nCol;
    this.nRow = paramInt1;
    this.nCol = paramInt2;
    int m = paramInt1 * paramInt2;
    double[] arrayOfDouble = this.elementData;

    if (j == paramInt2)
    {
      if (paramInt1 <= i) {
        return;
      }

      this.elementData = new double[m];

      System.arraycopy(arrayOfDouble, 0, this.elementData, 0, k);
    }
    else
    {
      this.elementData = new double[m];
      setZero();
      for (int n = 0; n < i; n++)
        System.arraycopy(arrayOfDouble, n * j, this.elementData, n * paramInt2, j);
    }
  }

  public final void set(double[] paramArrayOfDouble)
  {
    int i = this.nRow * this.nCol;
    System.arraycopy(paramArrayOfDouble, 0, this.elementData, 0, i);
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    this.elementData[0] = paramMatrix3f.m00;
    this.elementData[1] = paramMatrix3f.m01;
    this.elementData[2] = paramMatrix3f.m02;
    this.elementData[this.nCol] = paramMatrix3f.m10;
    this.elementData[(this.nCol + 1)] = paramMatrix3f.m11;
    this.elementData[(this.nCol + 2)] = paramMatrix3f.m12;
    this.elementData[(2 * this.nCol)] = paramMatrix3f.m20;
    this.elementData[(2 * this.nCol + 1)] = paramMatrix3f.m21;
    this.elementData[(2 * this.nCol + 2)] = paramMatrix3f.m22;
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    this.elementData[0] = paramMatrix3d.m00;
    this.elementData[1] = paramMatrix3d.m01;
    this.elementData[2] = paramMatrix3d.m02;
    this.elementData[this.nCol] = paramMatrix3d.m10;
    this.elementData[(this.nCol + 1)] = paramMatrix3d.m11;
    this.elementData[(this.nCol + 2)] = paramMatrix3d.m12;
    this.elementData[(2 * this.nCol)] = paramMatrix3d.m20;
    this.elementData[(2 * this.nCol + 1)] = paramMatrix3d.m21;
    this.elementData[(2 * this.nCol + 2)] = paramMatrix3d.m22;
  }

  public final void set(Matrix4f paramMatrix4f)
  {
    this.elementData[0] = paramMatrix4f.m00;
    this.elementData[1] = paramMatrix4f.m01;
    this.elementData[2] = paramMatrix4f.m02;
    this.elementData[3] = paramMatrix4f.m03;
    this.elementData[this.nCol] = paramMatrix4f.m10;
    this.elementData[(this.nCol + 1)] = paramMatrix4f.m11;
    this.elementData[(this.nCol + 2)] = paramMatrix4f.m12;
    this.elementData[(this.nCol + 3)] = paramMatrix4f.m13;
    this.elementData[(2 * this.nCol)] = paramMatrix4f.m20;
    this.elementData[(2 * this.nCol + 1)] = paramMatrix4f.m21;
    this.elementData[(2 * this.nCol + 2)] = paramMatrix4f.m22;
    this.elementData[(2 * this.nCol + 3)] = paramMatrix4f.m23;
    this.elementData[(3 * this.nCol)] = paramMatrix4f.m30;
    this.elementData[(3 * this.nCol + 1)] = paramMatrix4f.m31;
    this.elementData[(3 * this.nCol + 2)] = paramMatrix4f.m32;
    this.elementData[(3 * this.nCol + 3)] = paramMatrix4f.m33;
  }

  public final void set(Matrix4d paramMatrix4d)
  {
    this.elementData[0] = paramMatrix4d.m00;
    this.elementData[1] = paramMatrix4d.m01;
    this.elementData[2] = paramMatrix4d.m02;
    this.elementData[3] = paramMatrix4d.m03;
    this.elementData[this.nCol] = paramMatrix4d.m10;
    this.elementData[(this.nCol + 1)] = paramMatrix4d.m11;
    this.elementData[(this.nCol + 2)] = paramMatrix4d.m12;
    this.elementData[(this.nCol + 3)] = paramMatrix4d.m13;
    this.elementData[(2 * this.nCol)] = paramMatrix4d.m20;
    this.elementData[(2 * this.nCol + 1)] = paramMatrix4d.m21;
    this.elementData[(2 * this.nCol + 2)] = paramMatrix4d.m22;
    this.elementData[(2 * this.nCol + 3)] = paramMatrix4d.m23;
    this.elementData[(3 * this.nCol)] = paramMatrix4d.m30;
    this.elementData[(3 * this.nCol + 1)] = paramMatrix4d.m31;
    this.elementData[(3 * this.nCol + 2)] = paramMatrix4d.m32;
    this.elementData[(3 * this.nCol + 3)] = paramMatrix4d.m33;
  }

  public final void set(GMatrix paramGMatrix)
  {
    System.arraycopy(paramGMatrix.elementData, 0, this.elementData, 0, this.nRow * this.nCol);
  }

  public final int getNumRow()
  {
    return this.nRow;
  }

  public final int getNumCol()
  {
    return this.nCol;
  }

  public final double getElement(int paramInt1, int paramInt2)
  {
    return this.elementData[(paramInt1 * this.nCol + paramInt2)];
  }

  public final void setElement(int paramInt1, int paramInt2, double paramDouble)
  {
    this.elementData[(paramInt1 * this.nCol + paramInt2)] = paramDouble;
  }

  public final void getRow(int paramInt, double[] paramArrayOfDouble)
  {
    System.arraycopy(this.elementData, paramInt * this.nCol, paramArrayOfDouble, 0, this.nCol);
  }

  public final void getRow(int paramInt, GVector paramGVector)
  {
    for (int i = 0; i < this.nCol; i++)
      paramGVector.setElement(i, this.elementData[(paramInt * this.nCol + i)]);
  }

  public final void getColumn(int paramInt, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < this.nRow; i++)
      paramArrayOfDouble[i] = this.elementData[(i * this.nCol + paramInt)];
  }

  public final void getColumn(int paramInt, GVector paramGVector)
  {
    for (int i = 0; i < this.nRow; i++)
      paramGVector.setElement(i, this.elementData[(i * this.nCol + paramInt)]);
  }

  public final void get(Matrix3d paramMatrix3d)
  {
    paramMatrix3d.m00 = this.elementData[0];
    paramMatrix3d.m01 = this.elementData[1];
    paramMatrix3d.m02 = this.elementData[2];
    paramMatrix3d.m10 = this.elementData[this.nCol];
    paramMatrix3d.m11 = this.elementData[(this.nCol + 1)];
    paramMatrix3d.m12 = this.elementData[(this.nCol + 2)];
    paramMatrix3d.m20 = this.elementData[(2 * this.nCol)];
    paramMatrix3d.m21 = this.elementData[(2 * this.nCol + 1)];
    paramMatrix3d.m22 = this.elementData[(2 * this.nCol + 2)];
  }

  public final void get(Matrix3f paramMatrix3f)
  {
    paramMatrix3f.m00 = (float)this.elementData[0];
    paramMatrix3f.m01 = (float)this.elementData[1];
    paramMatrix3f.m02 = (float)this.elementData[2];
    paramMatrix3f.m10 = (float)this.elementData[this.nCol];
    paramMatrix3f.m11 = (float)this.elementData[(this.nCol + 1)];
    paramMatrix3f.m12 = (float)this.elementData[(this.nCol + 2)];
    paramMatrix3f.m20 = (float)this.elementData[(2 * this.nCol)];
    paramMatrix3f.m21 = (float)this.elementData[(2 * this.nCol + 1)];
    paramMatrix3f.m22 = (float)this.elementData[(2 * this.nCol + 2)];
  }

  public final void get(Matrix4d paramMatrix4d)
  {
    paramMatrix4d.m00 = this.elementData[0];
    paramMatrix4d.m01 = this.elementData[1];
    paramMatrix4d.m02 = this.elementData[2];
    paramMatrix4d.m03 = this.elementData[3];
    paramMatrix4d.m10 = this.elementData[this.nCol];
    paramMatrix4d.m11 = this.elementData[(this.nCol + 1)];
    paramMatrix4d.m12 = this.elementData[(this.nCol + 2)];
    paramMatrix4d.m13 = this.elementData[(this.nCol + 3)];
    paramMatrix4d.m20 = this.elementData[(2 * this.nCol)];
    paramMatrix4d.m21 = this.elementData[(2 * this.nCol + 1)];
    paramMatrix4d.m22 = this.elementData[(2 * this.nCol + 2)];
    paramMatrix4d.m23 = this.elementData[(2 * this.nCol + 3)];
    paramMatrix4d.m30 = this.elementData[(3 * this.nCol)];
    paramMatrix4d.m31 = this.elementData[(3 * this.nCol + 1)];
    paramMatrix4d.m32 = this.elementData[(3 * this.nCol + 2)];
    paramMatrix4d.m33 = this.elementData[(3 * this.nCol + 3)];
  }

  public final void get(Matrix4f paramMatrix4f)
  {
    paramMatrix4f.m00 = (float)this.elementData[0];
    paramMatrix4f.m01 = (float)this.elementData[1];
    paramMatrix4f.m02 = (float)this.elementData[2];
    paramMatrix4f.m03 = (float)this.elementData[3];
    paramMatrix4f.m10 = (float)this.elementData[this.nCol];
    paramMatrix4f.m11 = (float)this.elementData[(this.nCol + 1)];
    paramMatrix4f.m12 = (float)this.elementData[(this.nCol + 2)];
    paramMatrix4f.m13 = (float)this.elementData[(this.nCol + 3)];
    paramMatrix4f.m20 = (float)this.elementData[(2 * this.nCol)];
    paramMatrix4f.m21 = (float)this.elementData[(2 * this.nCol + 1)];
    paramMatrix4f.m22 = (float)this.elementData[(2 * this.nCol + 2)];
    paramMatrix4f.m23 = (float)this.elementData[(2 * this.nCol + 3)];
    paramMatrix4f.m30 = (float)this.elementData[(3 * this.nCol)];
    paramMatrix4f.m31 = (float)this.elementData[(3 * this.nCol + 1)];
    paramMatrix4f.m32 = (float)this.elementData[(3 * this.nCol + 2)];
    paramMatrix4f.m33 = (float)this.elementData[(3 * this.nCol + 3)];
  }

  public final void get(GMatrix paramGMatrix)
  {
    System.arraycopy(this.elementData, 0, paramGMatrix.elementData, 0, this.nRow * this.nCol);
  }

  public final void setRow(int paramInt, double[] paramArrayOfDouble)
  {
    System.arraycopy(paramArrayOfDouble, 0, this.elementData, paramInt * this.nCol, this.nCol);
  }

  public final void setRow(int paramInt, GVector paramGVector)
  {
    for (int i = 0; i < this.nCol; i++)
      this.elementData[(paramInt * this.nCol + i)] = paramGVector.getElement(i);
  }

  public final void setColumn(int paramInt, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < this.nRow; i++)
      this.elementData[(i * this.nCol + paramInt)] = paramArrayOfDouble[i];
  }

  public final void setColumn(int paramInt, GVector paramGVector)
  {
    int i = paramGVector.getSize();
    for (int j = 0; j < this.nRow; j++)
      this.elementData[(j * this.nCol + paramInt)] = paramGVector.getElement(j);
  }

  public final void mulTransposeBoth(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    mul(paramGMatrix2, paramGMatrix1);
    transpose();
  }

  public final void mulTransposeRight(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    for (int i = 0; i < this.nRow; i++)
      for (int j = 0; j < this.nCol; j++) {
        double d = 0.0D;
        for (int k = 0; k < paramGMatrix1.nCol; k++)
          d += paramGMatrix1.elementData[(i * this.nCol + k)] * paramGMatrix2.elementData[(j * paramGMatrix2.nCol + k)];
        this.elementData[(i * this.nCol + j)] = d;
      }
  }

  public final void mulTransposeLeft(GMatrix paramGMatrix1, GMatrix paramGMatrix2)
  {
    transpose(paramGMatrix1);
    mul(paramGMatrix2);
  }

  public final void transpose()
  {
    for (int i = 0; i < this.nRow; i++)
      for (int j = i + 1; j < this.nCol; j++) {
        double d = this.elementData[(i * this.nCol + j)];
        this.elementData[(i * this.nCol + j)] = this.elementData[(j * this.nCol + i)];
        this.elementData[(j * this.nCol + i)] = d;
      }
  }

  public final void transpose(GMatrix paramGMatrix)
  {
    set(paramGMatrix);
    transpose();
  }

  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer("[");
    localStringBuffer.append(str);

    for (int i = 0; i < this.nRow; i++) {
      localStringBuffer.append("  [");
      for (int j = 0; j < this.nCol; j++) {
        if (0 < j)
          localStringBuffer.append("\t");
        localStringBuffer.append(this.elementData[(i * this.nCol + j)]);
      }
      if (i + 1 < this.nRow) {
        localStringBuffer.append("]");
        localStringBuffer.append(str);
      } else {
        localStringBuffer.append("] ]");
      }
    }
    return localStringBuffer.toString();
  }

  public int hashCode()
  {
    int i = 0;
    for (int j = 0; j < this.nRow * this.nCol; j++) {
      long l = Double.doubleToLongBits(this.elementData[j]);
      i ^= (int)(l ^ l >> 32);
    }
    return i;
  }

  public boolean equals(GMatrix paramGMatrix)
  {
    if (paramGMatrix == null)
      return false;
    if (paramGMatrix.nRow != this.nRow)
      return false;
    if (paramGMatrix.nCol != this.nCol)
      return false;
    for (int i = 0; i < this.nRow; i++)
      for (int j = 0; j < this.nCol; j++)
        if (this.elementData[(i * this.nCol + j)] != paramGMatrix.elementData[(i * this.nCol + j)])
          return false;
    return true;
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof GMatrix)) && (equals((GMatrix)paramObject));
  }

  /** @deprecated */
  public boolean epsilonEquals(GMatrix paramGMatrix, float paramFloat)
  {
    if (paramGMatrix.nRow != this.nRow)
      return false;
    if (paramGMatrix.nCol != this.nCol)
      return false;
    for (int i = 0; i < this.nRow; i++)
      for (int j = 0; j < this.nCol; j++)
        if (paramFloat < Math.abs(this.elementData[(i * this.nCol + j)] - paramGMatrix.elementData[(i * this.nCol + j)]))
        {
          return false;
        }
    return true;
  }

  public boolean epsilonEquals(GMatrix paramGMatrix, double paramDouble)
  {
    if (paramGMatrix.nRow != this.nRow)
      return false;
    if (paramGMatrix.nCol != this.nCol)
      return false;
    for (int i = 0; i < this.nRow; i++)
      for (int j = 0; j < this.nCol; j++)
        if (paramDouble < Math.abs(this.elementData[(i * this.nCol + j)] - paramGMatrix.elementData[(i * this.nCol + j)]))
        {
          return false;
        }
    return true;
  }

  public final double trace()
  {
    int i = this.nRow < this.nCol ? this.nRow : this.nCol;
    double d = 0.0D;
    for (int j = 0; j < i; j++)
      d += this.elementData[(j * this.nCol + j)];
    return d;
  }

  public final void setScale(double paramDouble)
  {
    setZero();
    int i = this.nRow < this.nCol ? this.nRow : this.nCol;
    for (int j = 0; j < i; j++)
      this.elementData[(j * this.nCol + j)] = paramDouble;
  }

  private void setDiag(int paramInt, double paramDouble) {
    this.elementData[(paramInt * this.nCol + paramInt)] = paramDouble;
  }
  private double getDiag(int paramInt) {
    return this.elementData[(paramInt * this.nCol + paramInt)];
  }

  private double dpythag(double paramDouble1, double paramDouble2) {
    double d1 = Math.abs(paramDouble1);
    double d2 = Math.abs(paramDouble2);
    if (d1 > d2) {
      if (d1 == 0.0D)
        return 0.0D;
      d3 = d2 / d1;
      if (Math.abs(d3) <= Double.MIN_VALUE)
        return d1;
      return d1 * Math.sqrt(1.0D + d3 * d3);
    }
    if (d2 == 0.0D)
      return 0.0D;
    double d3 = d1 / d2;
    if (Math.abs(d3) <= Double.MIN_VALUE)
      return d2;
    return d2 * Math.sqrt(1.0D + d3 * d3);
  }

  public final int SVD(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3)
  {
    int i = this.nRow;
    double d1 = this.nCol;
    int j = i > d1 ? i : d1;
    double[] arrayOfDouble1 = paramGMatrix1.elementData;
    double[] arrayOfDouble2 = paramGMatrix3.elementData;
    int i3 = 0; int i4 = 0;

    double[] arrayOfDouble3 = new double[d1];

    get(paramGMatrix1);
    int n;
    for (int k = i; k < j; k++) {
      for (n = d1; n < j; k++)
        arrayOfDouble1[(k * d1 + n)] = 0.0D;
    }
    paramGMatrix2.setZero();
    double d2;
    double d8;
    double d5 = d8 = d2 = 0.0D;
    double d7;
    double d4;
    double d6;
    for (k = 0; k < d1; k++) {
      i3 = k + 1;
      arrayOfDouble3[k] = (d8 * d5);
      d5 = d7 = d8 = 0.0D;
      if (k < i) {
        for (i2 = k; i2 < i; i2++) d8 += Math.abs(arrayOfDouble1[(i2 * i + k)]);
        if (d8 != 0.0D) {
          for (i2 = k; i2 < i; i2++) {
            arrayOfDouble1[(i2 * i + k)] /= d8;
            d7 += arrayOfDouble1[(i2 * i + k)] * arrayOfDouble1[(i2 * i + k)];
          }
          d4 = arrayOfDouble1[(k * i + k)];

          d5 = d4 < 0.0D ? Math.sqrt(d7) : -Math.sqrt(d7);
          d6 = d4 * d5 - d7;
          arrayOfDouble1[(k * i + k)] = (d4 - d5);
          for (n = i3; n < d1; n++) {
            d7 = 0.0D; for (i2 = k; i2 < i; i2++) d7 += arrayOfDouble1[(i2 * i + k)] * arrayOfDouble1[(i2 * i + n)];
            d4 = d7 / d6;
            for (i2 = k; i2 < i; i2++) arrayOfDouble1[(i2 * i + n)] += d4 * arrayOfDouble1[(i2 * i + k)];
          }
          for (i2 = k; i2 < i; i2++) arrayOfDouble1[(i2 * i + k)] *= d8;
        }
      }
      paramGMatrix2.setDiag(k, d8 * d5);
      d5 = d7 = d8 = 0.0D;
      if ((k < i) && (k != d1 - 1)) {
        for (i2 = i3; i2 < d1; i2++) d8 += Math.abs(arrayOfDouble1[(k * i + i2)]);
        if (d8 != 0.0D) {
          for (i2 = i3; i2 < d1; i2++) {
            arrayOfDouble1[(k * i + i2)] /= d8;
            d7 += arrayOfDouble1[(k * i + i2)] * arrayOfDouble1[(k * i + i2)];
          }
          d4 = arrayOfDouble1[(k * i + i3)];

          d5 = d4 < 0.0D ? Math.sqrt(d7) : -Math.sqrt(d7);
          d6 = d4 * d5 - d7;
          arrayOfDouble1[(k * i + i3)] = (d4 - d5);
          for (i2 = i3; i2 < d1; i2++) arrayOfDouble3[i2] = (arrayOfDouble1[(k * i + i2)] / d6);
          for (n = i3; n < i; n++) {
            d7 = 0.0D; for (i2 = i3; i2 < d1; i2++) d7 += arrayOfDouble1[(n * i + i2)] * arrayOfDouble1[(k * i + i2)];
            for (i2 = i3; i2 < d1; i2++) arrayOfDouble1[(n * i + i2)] += d7 * arrayOfDouble3[i2];
          }
          for (i2 = i3; i2 < d1; i2++) arrayOfDouble1[(k * i + i2)] *= d8;
        }
      }

      d12 = Math.abs(paramGMatrix2.getDiag(k)) + Math.abs(arrayOfDouble3[k]);
      if (d12 > d2)
        d2 = d12;
    }
    for (k = d1 - 1; k >= 0; k--) {
      if (k < d1 - 1) {
        if (d5 != 0.0D) {
          for (n = i3; n < d1; n++)
            arrayOfDouble2[(n * d1 + k)] = (arrayOfDouble1[(k * i + n)] / arrayOfDouble1[(k * i + i3)] / d5);
          for (n = i3; n < d1; n++) {
            d7 = 0.0D; for (i2 = i3; i2 < d1; i2++) d7 += arrayOfDouble1[(k * i + i2)] * arrayOfDouble2[(i2 * d1 + n)];
            for (i2 = i3; i2 < d1; i2++) arrayOfDouble2[(i2 * d1 + n)] += d7 * arrayOfDouble2[(i2 * d1 + k)];
          }
        }
        for (n = i3; n < d1; n++)
        {
          double tmp1104_1103 = 0.0D; arrayOfDouble2[(n * d1 + k)] = tmp1104_1103; arrayOfDouble2[(k * d1 + n)] = tmp1104_1103;
        }
      }
      arrayOfDouble2[(k * d1 + k)] = 1.0D;
      d5 = arrayOfDouble3[k];
      i3 = k;
    }

    double d12 = i < d1 ? i : d1;
    for (k = d12 - 1; k >= 0; k--) {
      i3 = k + 1;
      d5 = paramGMatrix2.getDiag(k);
      for (n = i3; n < d1; n++) arrayOfDouble1[(k * i + n)] = 0.0D;
      if (d5 != 0.0D) {
        d5 = 1.0D / d5;
        for (n = i3; n < d1; n++) {
          d7 = 0.0D; for (i2 = i3; i2 < i; i2++) d7 += arrayOfDouble1[(i2 * i + k)] * arrayOfDouble1[(i2 * i + n)];
          d4 = d7 / arrayOfDouble1[(k * i + k)] * d5;
          for (i2 = k; i2 < i; i2++) arrayOfDouble1[(i2 * i + n)] += d4 * arrayOfDouble1[(i2 * i + k)];
        }
        for (n = k; n < i; n++) arrayOfDouble1[(n * i + k)] *= d5; 
      } else {
        for (n = k; n < i; n++) arrayOfDouble1[(n * i + k)] = 0.0D; 
      }
      arrayOfDouble1[(k * i + k)] += 1.0D;
    }
    for (int i2 = d1 - 1; i2 >= 0; i2--) {
      for (int m = 1; m <= 30; m++) {
        i5 = 1;
        for (i3 = i2; i3 >= 0; i3--) {
          i4 = i3 - 1;
          if (Math.abs(arrayOfDouble3[i3]) + d2 == d2) {
            i5 = 0;
            break;
          }
          if (Math.abs(paramGMatrix2.getDiag(i4)) + d2 == d2) break;
        }
        if (i5 != 0) {
          d3 = 0.0D;
          d7 = 1.0D;
          for (k = i3; k <= i2; k++) {
            d4 = d7 * arrayOfDouble3[k];
            arrayOfDouble3[k] = (d3 * arrayOfDouble3[k]);
            if (Math.abs(d4) + d2 == d2) break;
            d5 = paramGMatrix2.getDiag(k);
            d6 = dpythag(d4, d5);
            paramGMatrix2.setDiag(k, d6);
            d6 = 1.0D / d6;
            d3 = d5 * d6;
            d7 = -d4 * d6;
            for (n = 0; n < i; n++) {
              d10 = arrayOfDouble1[(n * i + i4)];
              d11 = arrayOfDouble1[(n * i + k)];
              arrayOfDouble1[(n * i + i4)] = (d10 * d3 + d11 * d7);
              arrayOfDouble1[(n * i + k)] = (d11 * d3 - d10 * d7);
            }
          }
        }
        double d11 = paramGMatrix2.getDiag(i2);
        if (i3 == i2) {
          if (d11 >= 0.0D) break;
          paramGMatrix2.setDiag(i2, -d11);
          for (n = 0; n < d1; n++) arrayOfDouble2[(n * d1 + i2)] = (-arrayOfDouble2[(n * d1 + i2)]); break;
        }

        if (m == 30) {
          return 0;
        }
        double d9 = paramGMatrix2.getDiag(i3);
        i4 = i2 - 1;
        double d10 = paramGMatrix2.getDiag(i4);
        d5 = arrayOfDouble3[i4];
        d6 = arrayOfDouble3[i2];
        d4 = ((d10 - d11) * (d10 + d11) + (d5 - d6) * (d5 + d6)) / (2.0D * d6 * d10);
        d5 = dpythag(d4, 1.0D);

        d4 = ((d9 - d11) * (d9 + d11) + d6 * (d10 / (d4 + (d4 >= 0.0D ? Math.abs(d5) : -Math.abs(d5))) - d6)) / d9;

        double d3 = d7 = 1.0D;
        for (n = i3; n <= i4; n++) {
          k = n + 1;
          d5 = arrayOfDouble3[k];
          d10 = paramGMatrix2.getDiag(k);
          d6 = d7 * d5;
          d5 = d3 * d5;
          d11 = dpythag(d4, d6);
          arrayOfDouble3[n] = d11;
          d3 = d4 / d11;
          d7 = d6 / d11;
          d4 = d9 * d3 + d5 * d7;
          d5 = d5 * d3 - d9 * d7;
          d6 = d10 * d7;
          d10 *= d3;
          for (int i1 = 0; i1 < d1; i1++) {
            d9 = arrayOfDouble2[(i1 * d1 + n)];
            d11 = arrayOfDouble2[(i1 * d1 + k)];
            arrayOfDouble2[(i1 * d1 + n)] = (d9 * d3 + d11 * d7);
            arrayOfDouble2[(i1 * d1 + k)] = (d11 * d3 - d9 * d7);
          }
          d11 = dpythag(d4, d6);
          paramGMatrix2.setDiag(n, d11);
          if (d11 != 0.0D) {
            d11 = 1.0D / d11;
            d3 = d4 * d11;
            d7 = d6 * d11;
          }
          d4 = d3 * d5 + d7 * d10;
          d9 = d3 * d10 - d7 * d5;
          for (i1 = 0; i1 < i; i1++) {
            d10 = arrayOfDouble1[(i1 * i + n)];
            d11 = arrayOfDouble1[(i1 * i + k)];
            arrayOfDouble1[(i1 * i + n)] = (d10 * d3 + d11 * d7);
            arrayOfDouble1[(i1 * i + k)] = (d11 * d3 - d10 * d7);
          }
        }
        arrayOfDouble3[i3] = 0.0D;
        arrayOfDouble3[i2] = d4;
        paramGMatrix2.setDiag(i2, d9);
      }

    }

    int i5 = 0;
    for (k = 0; k < d1; k++) {
      if (paramGMatrix2.getDiag(k) > 0.0D)
        i5++;
    }
    return i5;
  }

  private void swapRows(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.nCol; i++) {
      double d = this.elementData[(paramInt1 * this.nCol + i)];
      this.elementData[(paramInt1 * this.nCol + i)] = this.elementData[(paramInt2 * this.nCol + i)];
      this.elementData[(paramInt2 * this.nCol + i)] = d;
    }
  }

  public final int LUD(GMatrix paramGMatrix, GVector paramGVector)
  {
    int i = this.nRow;

    if (this != paramGMatrix) {
      paramGMatrix.set(this);
    }
    int j = 1;
    double[] arrayOfDouble = paramGMatrix.elementData;

    for (int k = 0; k < i; k++) {
      paramGVector.setElement(k, k);
    }

    for (int m = 0; m < i; m++)
    {
      double d3;
      for (int i1 = 0; i1 < m; i1++) {
        d3 = arrayOfDouble[(i1 * i + m)];
        for (i2 = 0; i2 < i1; i2++) {
          if ((arrayOfDouble[(i1 * i + i2)] != 0.0D) && (arrayOfDouble[(i2 * i + m)] != 0.0D))
            d3 -= arrayOfDouble[(i1 * i + i2)] * arrayOfDouble[(i2 * i + m)];
        }
        arrayOfDouble[(i1 * i + m)] = d3;
      }
      double d1 = 0.0D;
      int n = m;
      double d2;
      for (int i2 = m; i2 < i; i2++) {
        d3 = arrayOfDouble[(i2 * i + m)];
        for (int i3 = 0; i3 < m; i3++) {
          if ((arrayOfDouble[(i2 * i + i3)] != 0.0D) && (arrayOfDouble[(i3 * i + m)] != 0.0D))
            d3 -= arrayOfDouble[(i2 * i + i3)] * arrayOfDouble[(i3 * i + m)];
        }
        arrayOfDouble[(i2 * i + m)] = d3;
        d2 = Math.abs(d3);
        if (d2 >= d1) {
          d1 = d2;
          n = i2;
        }
      }

      if (m != n)
      {
        paramGMatrix.swapRows(n, m);
        double d4 = paramGVector.getElement(n);
        paramGVector.setElement(n, paramGVector.getElement(m));
        paramGVector.setElement(m, d4);
        j = -j;
      }

      if (m != i - 1) {
        d2 = 1.0D / arrayOfDouble[(m * i + m)];
        for (int i4 = m + 1; i4 < i; i4++) {
          arrayOfDouble[(i4 * i + m)] *= d2;
        }
      }
    }

    return j;
  }
}