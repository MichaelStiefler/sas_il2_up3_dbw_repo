package com.maddox.JGP;

import java.io.Serializable;

public class GVector
  implements Serializable, Cloneable
{
  private int elementCount;
  private double[] elementData;

  public GVector(int paramInt)
  {
    this.elementCount = paramInt;
    this.elementData = new double[paramInt];
  }

  public GVector(double[] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length);
    System.arraycopy(paramArrayOfDouble, 0, this.elementData, 0, this.elementCount);
  }

  public GVector(GVector paramGVector)
  {
    this(paramGVector.elementCount);
    System.arraycopy(paramGVector.elementData, 0, this.elementData, 0, this.elementCount);
  }

  public GVector(Tuple2f paramTuple2f)
  {
    this(2);
    set(paramTuple2f);
  }

  public GVector(Tuple3f paramTuple3f)
  {
    this(3);
    set(paramTuple3f);
  }

  public GVector(Tuple3d paramTuple3d)
  {
    this(3);
    set(paramTuple3d);
  }

  public GVector(Tuple4f paramTuple4f)
  {
    this(4);
    set(paramTuple4f);
  }

  public GVector(Tuple4d paramTuple4d)
  {
    this(4);
    set(paramTuple4d);
  }

  public GVector(double[] paramArrayOfDouble, int paramInt)
  {
    this(paramInt);
    System.arraycopy(paramArrayOfDouble, 0, this.elementData, 0, this.elementCount);
  }

  public final double norm()
  {
    return Math.sqrt(normSquared());
  }

  public final double normSquared()
  {
    double d = 0.0D;
    for (int i = 0; i < this.elementCount; i++) {
      d += this.elementData[i] * this.elementData[i];
    }
    return d;
  }

  public final void normalize(GVector paramGVector)
  {
    set(paramGVector);
    normalize();
  }

  public final void normalize()
  {
    double d = norm();

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] /= d;
  }

  public final void scale(double paramDouble, GVector paramGVector)
  {
    set(paramGVector);
    scale(paramDouble);
  }

  public final void scale(double paramDouble)
  {
    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] *= paramDouble;
  }

  public final void scaleAdd(double paramDouble, GVector paramGVector1, GVector paramGVector2)
  {
    double[] arrayOfDouble1 = paramGVector1.elementData;
    double[] arrayOfDouble2 = paramGVector2.elementData;

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] = (paramDouble * arrayOfDouble1[i] + arrayOfDouble2[i]);
  }

  public final void add(GVector paramGVector)
  {
    double[] arrayOfDouble = paramGVector.elementData;

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] += arrayOfDouble[i];
  }

  public final void add(GVector paramGVector1, GVector paramGVector2)
  {
    set(paramGVector1);
    add(paramGVector2);
  }

  public final void sub(GVector paramGVector)
  {
    double[] arrayOfDouble = paramGVector.elementData;

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] -= arrayOfDouble[i];
  }

  public final void sub(GVector paramGVector1, GVector paramGVector2)
  {
    set(paramGVector1);
    sub(paramGVector2);
  }

  public final void mul(GMatrix paramGMatrix, GVector paramGVector)
  {
    double[] arrayOfDouble = paramGVector.elementData;
    int i = paramGVector.elementCount;
    int j = paramGMatrix.getNumCol();
    int k = paramGMatrix.getNumRow();

    for (int m = 0; m < this.elementCount; m++) {
      double d = 0.0D;
      for (int n = 0; n < j; n++) {
        d += paramGMatrix.getElement(m, n) * arrayOfDouble[n];
      }
      this.elementData[m] = d;
    }
  }

  public final void mul(GVector paramGVector, GMatrix paramGMatrix)
  {
    double[] arrayOfDouble = paramGVector.elementData;
    int i = paramGVector.elementCount;
    int j = paramGMatrix.getNumCol();
    int k = paramGMatrix.getNumRow();

    for (int m = 0; m < this.elementCount; m++) {
      double d = 0.0D;
      for (int n = 0; n < k; n++) {
        d += paramGMatrix.getElement(n, m) * arrayOfDouble[n];
      }
      this.elementData[m] = d;
    }
  }

  public final void negate()
  {
    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] = (-this.elementData[i]);
  }

  public final void zero()
  {
    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] = 0.0D;
  }

  public final void setSize(int paramInt)
  {
    if (this.elementCount < paramInt) {
      double[] arrayOfDouble = this.elementData;
      this.elementData = new double[paramInt];
      System.arraycopy(arrayOfDouble, 0, this.elementData, 0, this.elementCount);
    }

    this.elementCount = paramInt;
  }

  public final void set(double[] paramArrayOfDouble)
  {
    System.arraycopy(paramArrayOfDouble, 0, this.elementData, 0, this.elementCount);
  }

  public final void set(GVector paramGVector)
  {
    System.arraycopy(paramGVector.elementData, 0, this.elementData, 0, this.elementCount);
  }

  public final void set(Tuple2f paramTuple2f)
  {
    this.elementData[0] = paramTuple2f.x;
    this.elementData[1] = paramTuple2f.y;
  }

  public final void set(Tuple3f paramTuple3f)
  {
    this.elementData[0] = paramTuple3f.x;
    this.elementData[1] = paramTuple3f.y;
    this.elementData[2] = paramTuple3f.z;
  }

  public final void set(Tuple3d paramTuple3d)
  {
    this.elementData[0] = paramTuple3d.x;
    this.elementData[1] = paramTuple3d.y;
    this.elementData[2] = paramTuple3d.z;
  }

  public final void set(Tuple4f paramTuple4f)
  {
    this.elementData[0] = paramTuple4f.x;
    this.elementData[1] = paramTuple4f.y;
    this.elementData[2] = paramTuple4f.z;
    this.elementData[3] = paramTuple4f.w;
  }

  public final void set(Tuple4d paramTuple4d)
  {
    this.elementData[0] = paramTuple4d.x;
    this.elementData[1] = paramTuple4d.y;
    this.elementData[2] = paramTuple4d.z;
    this.elementData[3] = paramTuple4d.w;
  }

  public final int getSize()
  {
    return this.elementCount;
  }

  public final double getElement(int paramInt)
  {
    return this.elementData[paramInt];
  }

  public final void setElement(int paramInt, double paramDouble)
  {
    this.elementData[paramInt] = paramDouble;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("(");
    for (int i = 0; i < this.elementCount - 1; i++) {
      localStringBuffer.append(this.elementData[i]);
      localStringBuffer.append(",");
    }
    localStringBuffer.append(this.elementData[(this.elementCount - 1)]);
    localStringBuffer.append(")");
    return localStringBuffer.toString();
  }

  public int hashCode()
  {
    int i = 0;
    for (int j = 0; j < this.elementCount; j++) {
      long l = Double.doubleToLongBits(this.elementData[j]);
      i ^= (int)(l ^ l >> 32);
    }
    return i;
  }

  public boolean equals(GVector paramGVector)
  {
    if (paramGVector == null)
      return false;
    if (this.elementCount != paramGVector.elementCount)
      return false;
    double[] arrayOfDouble = paramGVector.elementData;
    for (int i = 0; i < this.elementCount; i++) {
      if (this.elementData[i] != arrayOfDouble[i])
        return false;
    }
    return true;
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof GVector)) && (equals((GVector)paramObject));
  }

  public boolean epsilonEquals(GVector paramGVector, double paramDouble)
  {
    if (this.elementCount != paramGVector.elementCount)
      return false;
    double[] arrayOfDouble = paramGVector.elementData;
    for (int i = 0; i < this.elementCount; i++) {
      if (Math.abs(this.elementData[i] - arrayOfDouble[i]) > paramDouble)
        return false;
    }
    return true;
  }

  public final double dot(GVector paramGVector)
  {
    double[] arrayOfDouble = paramGVector.elementData;
    double d = 0.0D;
    for (int i = 0; i < this.elementCount; i++)
      d += this.elementData[i] * arrayOfDouble[i];
    return d;
  }

  public final void SVDBackSolve(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3, GVector paramGVector)
  {
    int i = paramGMatrix1.getNumRow();
    int j = paramGMatrix3.getNumRow();
    double[] arrayOfDouble = new double[j];

    for (int k = 0; k < j; k++) {
      double d1 = 0.0D;
      double d3 = paramGMatrix2.getElement(k, k);
      if (d3 != 0.0D) {
        for (int i1 = 0; i1 < i; i1++)
          d1 += paramGMatrix1.getElement(i1, k) * paramGVector.elementData[i1];
        d1 /= d3;
      }
      arrayOfDouble[k] = d1;
    }
    for (int m = 0; m < j; m++) {
      double d2 = 0.0D;
      for (int n = 0; n < j; n++)
        d2 += paramGMatrix3.getElement(m, n) * arrayOfDouble[n];
      this.elementData[m] = d2;
    }
  }

  public final void LUDBackSolve(GMatrix paramGMatrix, GVector paramGVector1, GVector paramGVector2)
  {
    int i = this.elementCount;
    double[] arrayOfDouble1 = paramGVector2.elementData;
    double[] arrayOfDouble2 = this.elementData;
    double[] arrayOfDouble3 = paramGVector1.elementData;

    for (int j = 0; j < i; j++)
    {
      arrayOfDouble2[j] = arrayOfDouble3[(int)arrayOfDouble1[j]];
    }

    int k = -1;
    for (int m = 0; m < i; m++) {
      double d1 = arrayOfDouble2[m];
      if (0 <= k)
        for (int i1 = k; i1 <= m - 1; i1++)
          d1 -= paramGMatrix.getElement(m, i1) * arrayOfDouble2[i1];
      else if (d1 != 0.0D)
      {
        k = m;
      }
      arrayOfDouble2[m] = d1;
    }

    for (int n = i - 1; n >= 0; n--) {
      double d2 = arrayOfDouble2[n];
      for (int i2 = n + 1; i2 < i; i2++) {
        d2 -= paramGMatrix.getElement(n, i2) * arrayOfDouble2[i2];
      }

      arrayOfDouble2[n] = (d2 / paramGMatrix.getElement(n, n));
    }
  }

  public final double angle(GVector paramGVector)
  {
    return Math.acos(dot(paramGVector) / norm() / paramGVector.norm());
  }

  /** @deprecated */
  public final void interpolate(GVector paramGVector1, GVector paramGVector2, float paramFloat)
  {
    interpolate(paramGVector1, paramGVector2, paramFloat);
  }

  /** @deprecated */
  public final void interpolate(GVector paramGVector, float paramFloat)
  {
    interpolate(paramGVector, paramFloat);
  }

  public final void interpolate(GVector paramGVector1, GVector paramGVector2, double paramDouble)
  {
    double[] arrayOfDouble1 = paramGVector1.elementData;
    double[] arrayOfDouble2 = paramGVector2.elementData;

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] = (arrayOfDouble1[i] + paramDouble * (arrayOfDouble2[i] - arrayOfDouble1[i]));
  }

  public final void interpolate(GVector paramGVector, double paramDouble)
  {
    double[] arrayOfDouble = paramGVector.elementData;

    for (int i = 0; i < this.elementCount; i++)
      this.elementData[i] += paramDouble * (arrayOfDouble[i] - this.elementData[i]);
  }
}