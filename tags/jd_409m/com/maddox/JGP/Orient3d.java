package com.maddox.JGP;

import java.io.Serializable;

public class Orient3d
  implements Serializable, Cloneable
{
  private double Yaw = 0.0D;
  private double Pitch = 0.0D;
  private double Roll = 0.0D;
  private Quat4d Q = new Quat4d();
  private Matrix3d M = new Matrix3d();
  private Matrix3d Mi = new Matrix3d();

  private boolean QuatOK = false;
  private boolean MatrixOK = false;
  private boolean MatrInvOK = false;

  private static double[] tmp = new double[3];
  private static final double PI = 3.141592653589793D;
  private static final double PI2 = 6.283185307179586D;
  private static final double PI_2 = 1.570796326794897D;
  private static final Quat4d Q1 = new Quat4d();
  private static final Orient3d Tmp = new Orient3d();

  public Orient3d()
  {
  }

  public Orient3d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    set(paramDouble1, paramDouble2, paramDouble3);
  }

  public void set(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.Yaw = paramDouble1;
    this.Pitch = paramDouble2;
    this.Roll = paramDouble3;
    this.QuatOK = (this.MatrixOK = this.MatrInvOK = 0);
  }

  public void set(Orient3d paramOrient3d)
  {
    this.Yaw = paramOrient3d.Yaw;
    this.Pitch = paramOrient3d.Pitch;
    this.Roll = paramOrient3d.Roll;
    this.QuatOK = paramOrient3d.QuatOK;
    this.MatrixOK = paramOrient3d.MatrixOK;
    this.MatrInvOK = paramOrient3d.MatrInvOK;
    if (this.QuatOK) this.Q.set(paramOrient3d.Q);
    if (this.MatrixOK) this.M.set(paramOrient3d.M);
    if (this.MatrInvOK) this.Mi.set(paramOrient3d.Mi);
  }

  public void set(double[] paramArrayOfDouble)
  {
    this.Yaw = paramArrayOfDouble[0];
    this.Pitch = paramArrayOfDouble[1];
    this.Roll = paramArrayOfDouble[2];
    this.QuatOK = (this.MatrixOK = this.MatrInvOK = 0);
  }

  public double getYaw()
  {
    return this.Yaw;
  }

  public double getPitch()
  {
    return this.Pitch;
  }

  public double getRoll()
  {
    return this.Roll;
  }

  public void get(double[] paramArrayOfDouble) {
    paramArrayOfDouble[0] = this.Yaw; paramArrayOfDouble[1] = this.Pitch; paramArrayOfDouble[2] = this.Roll;
  }
  private void makeMatrix() {
    if (this.MatrixOK) return;
    this.M.setEulers(this.Yaw, this.Pitch, this.Roll);
    this.MatrixOK = true;
  }

  private void makeMatrixInv() {
    if (this.MatrInvOK) return;
    this.Mi.setEulersInv(this.Yaw, this.Pitch, this.Roll);
    this.MatrInvOK = true;
  }

  private void makeQuat() {
    if (this.QuatOK) return;
    this.Q.setEulers(this.Yaw, this.Pitch, this.Roll);
    this.QuatOK = true;
  }

  public void add(Orient3d paramOrient3d1, Orient3d paramOrient3d2)
  {
    paramOrient3d2.makeMatrix();
    paramOrient3d1.makeMatrix();
    this.M.mul(paramOrient3d2.M, paramOrient3d1.M);

    this.M.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void add(Orient3d paramOrient3d)
  {
    add(this, paramOrient3d);
  }

  public void sub(Orient3d paramOrient3d1, Orient3d paramOrient3d2)
  {
    paramOrient3d2.makeMatrix();
    paramOrient3d1.makeMatrix();
    this.M.mulTransposeLeft(paramOrient3d2.M, paramOrient3d1.M);

    this.M.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void sub(Orient3d paramOrient3d)
  {
    sub(this, paramOrient3d);
  }

  public void increment(Orient3d paramOrient3d)
  {
    add(paramOrient3d, this);
  }

  public void increment(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    Tmp.set(paramDouble1, paramDouble2, paramDouble3);
    add(Tmp, this);
  }

  public void wrap()
  {
    this.Yaw %= 6.283185307179586D;
    this.Pitch %= 6.283185307179586D;
    this.Roll %= 6.283185307179586D;

    if (this.Pitch > 3.141592653589793D) this.Pitch -= 6.283185307179586D;
    if (this.Pitch > 1.570796326794897D) {
      this.Pitch = (3.141592653589793D - this.Pitch); this.Roll += 3.141592653589793D; this.Yaw += 3.141592653589793D;
      if (this.Roll > 3.141592653589793D) this.Roll -= 6.283185307179586D;
      if (this.Yaw > 3.141592653589793D) this.Yaw -= 6.283185307179586D;
    }
    else if (this.Pitch < -1.570796326794897D) {
      this.Pitch = (-3.141592653589793D - this.Pitch); this.Roll += 3.141592653589793D; this.Yaw += 3.141592653589793D;
      if (this.Roll > 3.141592653589793D) this.Roll -= 6.283185307179586D;
      if (this.Yaw > 3.141592653589793D) this.Yaw -= 6.283185307179586D;
    }
    if (this.Roll > 3.141592653589793D) this.Roll -= 6.283185307179586D;
    else if (this.Roll < -3.141592653589793D) this.Roll += 6.283185307179586D;
    if (this.Yaw < 0.0D) this.Yaw += 6.283185307179586D;
  }

  public void getMatrix(Matrix3d paramMatrix3d)
  {
    makeMatrix();
    paramMatrix3d.set(this.M);
  }

  public void getMatrixInv(Matrix3d paramMatrix3d)
  {
    makeMatrixInv();
    paramMatrix3d.set(this.M);
  }

  public void transform(Tuple3d paramTuple3d)
  {
    makeMatrix();
    this.M.transform(paramTuple3d);
  }

  public void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    makeMatrix();
    this.M.transform(paramTuple3d1, paramTuple3d2);
  }

  public void transformInv(Tuple3d paramTuple3d)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3d);
  }

  public void transformInv(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3d1, paramTuple3d2);
  }

  public final void interpolate(Orient3d paramOrient3d1, Orient3d paramOrient3d2, double paramDouble)
  {
    paramOrient3d1.makeQuat();
    paramOrient3d2.makeQuat();
    Q1.interpolate(paramOrient3d1.Q, paramOrient3d2.Q, paramDouble);
    Q1.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public final void interpolate(Orient3d paramOrient3d, double paramDouble)
  {
    makeQuat();
    paramOrient3d.makeQuat();
    this.Q.interpolate(paramOrient3d.Q, paramDouble);
    this.Q.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.Yaw);
    long l2 = Double.doubleToLongBits(this.Pitch);
    long l3 = Double.doubleToLongBits(this.Roll);
    l1 ^= l2 ^ l3;
    return (int)(l1 ^ l1 >> 32);
  }

  public boolean equals(Orient3d paramOrient3d)
  {
    return (paramOrient3d != null) && (this.Yaw == paramOrient3d.Yaw) && (this.Pitch == paramOrient3d.Pitch) && (this.Roll == paramOrient3d.Roll);
  }

  public boolean epsilonEquals(Orient3d paramOrient3d, double paramDouble)
  {
    return (Math.abs(paramOrient3d.Yaw - this.Yaw) <= paramDouble) && (Math.abs(paramOrient3d.Pitch - this.Pitch) <= paramDouble) && (Math.abs(paramOrient3d.Roll - this.Roll) <= paramDouble);
  }

  public String toString()
  {
    return "(" + this.Yaw + ", " + this.Pitch + ", " + this.Roll + ")";
  }
}