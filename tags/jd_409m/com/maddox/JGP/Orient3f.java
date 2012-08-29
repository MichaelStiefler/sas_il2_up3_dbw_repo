package com.maddox.JGP;

import java.io.Serializable;

public class Orient3f
  implements Serializable, Cloneable
{
  private float Yaw = 0.0F;
  private float Pitch = 0.0F;
  private float Roll = 0.0F;
  private Quat4f Q = new Quat4f();
  private Matrix3f M = new Matrix3f();
  private Matrix3f Mi = new Matrix3f();

  private boolean QuatOK = false;
  private boolean MatrixOK = false;
  private boolean MatrInvOK = false;

  private static float[] tmp = new float[3];
  private static final float PI = 3.141593F;
  private static final float PI2 = 6.283186F;
  private static final float PI_2 = 1.570796F;
  private static final Quat4f Q1 = new Quat4f();
  private static final Orient3f Tmp = new Orient3f();

  public Orient3f()
  {
  }

  public Orient3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    set(paramFloat1, paramFloat2, paramFloat3);
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.Yaw = paramFloat1;
    this.Pitch = paramFloat2;
    this.Roll = paramFloat3;
    this.QuatOK = (this.MatrixOK = this.MatrInvOK = 0);
  }

  public void set(Orient3f paramOrient3f)
  {
    this.Yaw = paramOrient3f.Yaw;
    this.Pitch = paramOrient3f.Pitch;
    this.Roll = paramOrient3f.Roll;
    this.QuatOK = paramOrient3f.QuatOK;
    this.MatrixOK = paramOrient3f.MatrixOK;
    this.MatrInvOK = paramOrient3f.MatrInvOK;
    if (this.QuatOK) this.Q.set(paramOrient3f.Q);
    if (this.MatrixOK) this.M.set(paramOrient3f.M);
    if (this.MatrInvOK) this.Mi.set(paramOrient3f.Mi);
  }

  public void set(float[] paramArrayOfFloat)
  {
    this.Yaw = paramArrayOfFloat[0];
    this.Pitch = paramArrayOfFloat[1];
    this.Roll = paramArrayOfFloat[2];
    this.QuatOK = (this.MatrixOK = this.MatrInvOK = 0);
  }

  public float getYaw()
  {
    return this.Yaw;
  }

  public float getPitch()
  {
    return this.Pitch;
  }

  public float getRoll()
  {
    return this.Roll;
  }

  public void get(float[] paramArrayOfFloat) {
    paramArrayOfFloat[0] = this.Yaw; paramArrayOfFloat[1] = this.Pitch; paramArrayOfFloat[2] = this.Roll;
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

  public void add(Orient3f paramOrient3f1, Orient3f paramOrient3f2)
  {
    paramOrient3f2.makeMatrix();
    paramOrient3f1.makeMatrix();
    this.M.mul(paramOrient3f2.M, paramOrient3f1.M);

    this.M.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void add(Orient3f paramOrient3f)
  {
    add(this, paramOrient3f);
  }

  public void sub(Orient3f paramOrient3f1, Orient3f paramOrient3f2)
  {
    paramOrient3f2.makeMatrix();
    paramOrient3f1.makeMatrix();
    this.M.mulTransposeLeft(paramOrient3f2.M, paramOrient3f1.M);

    this.M.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void sub(Orient3f paramOrient3f)
  {
    sub(this, paramOrient3f);
  }

  public void increment(Orient3f paramOrient3f)
  {
    add(paramOrient3f, this);
  }

  public void increment(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    Tmp.set(paramFloat1, paramFloat2, paramFloat3);
    add(Tmp, this);
  }

  public void wrap()
  {
    this.Yaw %= 6.283186F;
    this.Pitch %= 6.283186F;
    this.Roll %= 6.283186F;

    if (this.Pitch > 3.141593F) this.Pitch -= 6.283186F;
    if (this.Pitch > 1.570796F) {
      this.Pitch = (3.141593F - this.Pitch); this.Roll += 3.141593F; this.Yaw += 3.141593F;
      if (this.Roll > 3.141593F) this.Roll -= 6.283186F;
      if (this.Yaw > 3.141593F) this.Yaw -= 6.283186F;
    }
    else if (this.Pitch < -1.570796F) {
      this.Pitch = (-3.141593F - this.Pitch); this.Roll += 3.141593F; this.Yaw += 3.141593F;
      if (this.Roll > 3.141593F) this.Roll -= 6.283186F;
      if (this.Yaw > 3.141593F) this.Yaw -= 6.283186F;
    }
    if (this.Roll > 3.141593F) this.Roll -= 6.283186F;
    else if (this.Roll < -3.141593F) this.Roll += 6.283186F;
    if (this.Yaw < 0.0F) this.Yaw += 6.283186F;
  }

  public void getMatrix(Matrix3f paramMatrix3f)
  {
    makeMatrix();
    paramMatrix3f.set(this.M);
  }

  public void getMatrixInv(Matrix3f paramMatrix3f)
  {
    makeMatrixInv();
    paramMatrix3f.set(this.M);
  }

  public void transform(Tuple3f paramTuple3f)
  {
    makeMatrix();
    this.M.transform(paramTuple3f);
  }

  public void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    makeMatrix();
    this.M.transform(paramTuple3f1, paramTuple3f2);
  }

  public void transformInv(Tuple3f paramTuple3f)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3f);
  }

  public void transformInv(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3f1, paramTuple3f2);
  }

  public final void interpolate(Orient3f paramOrient3f1, Orient3f paramOrient3f2, float paramFloat)
  {
    paramOrient3f1.makeQuat();
    paramOrient3f2.makeQuat();
    Q1.interpolate(paramOrient3f1.Q, paramOrient3f2.Q, paramFloat);
    Q1.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public final void interpolate(Orient3f paramOrient3f, float paramFloat)
  {
    makeQuat();
    paramOrient3f.makeQuat();
    this.Q.interpolate(paramOrient3f.Q, paramFloat);
    this.Q.getEulers(tmp);
    this.Yaw = tmp[0];
    this.Pitch = tmp[1];
    this.Roll = tmp[2];
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public int hashCode()
  {
    int i = Float.floatToIntBits(this.Yaw);
    int j = Float.floatToIntBits(this.Pitch);
    int k = Float.floatToIntBits(this.Roll);
    return i ^ j ^ k;
  }

  public boolean equals(Orient3f paramOrient3f)
  {
    return (paramOrient3f != null) && (this.Yaw == paramOrient3f.Yaw) && (this.Pitch == paramOrient3f.Pitch) && (this.Roll == paramOrient3f.Roll);
  }

  public boolean epsilonEquals(Orient3f paramOrient3f, float paramFloat)
  {
    return (Math.abs(paramOrient3f.Yaw - this.Yaw) <= paramFloat) && (Math.abs(paramOrient3f.Pitch - this.Pitch) <= paramFloat) && (Math.abs(paramOrient3f.Roll - this.Roll) <= paramFloat);
  }

  public String toString()
  {
    return "(" + this.Yaw + ", " + this.Pitch + ", " + this.Roll + ")";
  }
}