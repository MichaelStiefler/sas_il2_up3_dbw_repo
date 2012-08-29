package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Quat4f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;

public class Orient
{
  protected float Yaw = 0.0F;
  protected float Pitch = 0.0F;
  protected float Roll = 0.0F;

  protected static Matrix3d M_ = new Matrix3d();
  protected static Matrix3d Mi_ = new Matrix3d();
  protected static Matrix3d M_0 = new Matrix3d();
  protected static Matrix3d M_1 = new Matrix3d();
  protected static Quat4f Q_0 = new Quat4f();
  protected static Quat4f Q_1 = new Quat4f();
  protected static final float PI = 3.141593F;
  private static float[] tmpf = new float[3];
  private static double[] tmpd = new double[3];

  private static final Orient Tmp = new Orient();
  private static final float Circ = 360.0F;
  private static final float Circ_2 = 180.0F;
  private static final float Circ_4 = 90.0F;
  private static final Quat4f Q1 = new Quat4f();

  private static final Vector3d Vt = new Vector3d();
  private static final Vector3d VV = new Vector3d();

  protected static float DEG2RAD(float paramFloat)
  {
    return paramFloat * 0.01745329F; } 
  protected static float RAD2DEG(float paramFloat) { return paramFloat * 57.295776F; } 
  public Orient() {
  }

  public Orient(float paramFloat1, float paramFloat2, float paramFloat3) {
    set(paramFloat1, paramFloat2, paramFloat3);
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.Yaw = (-paramFloat1);
    this.Pitch = (-paramFloat2);
    this.Roll = paramFloat3;
  }

  public void setYPR(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.Yaw = paramFloat1;
    this.Pitch = (-paramFloat2);
    this.Roll = (-paramFloat3);
  }

  public void set(Orient paramOrient)
  {
    this.Yaw = paramOrient.Yaw;
    this.Pitch = paramOrient.Pitch;
    this.Roll = paramOrient.Roll;
  }
  public final float azimut() {
    return getAzimut(); } 
  public final float kren() { return getKren(); } 
  public final float tangage() { return getTangage(); }

  public float getAzimut() {
    return 360.0F - this.Yaw;
  }
  public float getTangage() {
    return -this.Pitch;
  }
  public float getKren() {
    return this.Roll;
  }
  public float getYaw() { return this.Yaw; } 
  public void setYaw(float paramFloat) { this.Yaw = paramFloat; } 
  public float getPitch() { return 360.0F - this.Pitch; } 
  public float getRoll() { return 360.0F - this.Roll; }

  public void get(float[] paramArrayOfFloat) {
    paramArrayOfFloat[0] = (360.0F - this.Yaw); paramArrayOfFloat[1] = (-this.Pitch); paramArrayOfFloat[2] = this.Roll;
  }

  public void getYPR(float[] paramArrayOfFloat) {
    wrap();
    paramArrayOfFloat[0] = this.Yaw; paramArrayOfFloat[1] = (360.0F - this.Pitch); paramArrayOfFloat[2] = (360.0F - this.Roll);
  }
  protected void makeMatrix(Matrix3d paramMatrix3d) {
    paramMatrix3d.setEulers(DEG2RAD(this.Yaw), DEG2RAD(this.Pitch), DEG2RAD(this.Roll)); } 
  protected void makeMatrixInv(Matrix3d paramMatrix3d) { paramMatrix3d.setEulersInv(DEG2RAD(this.Yaw), DEG2RAD(this.Pitch), DEG2RAD(this.Roll)); } 
  protected void makeQuat(Quat4f paramQuat4f) { paramQuat4f.setEulers(DEG2RAD(this.Yaw), DEG2RAD(this.Pitch), DEG2RAD(this.Roll));
  }

  public void add(Orient paramOrient1, Orient paramOrient2)
  {
    paramOrient2.getMatrix(M_0);
    paramOrient1.getMatrix(M_1);
    M_.mul(M_0, M_1);

    M_.getEulers(tmpd);
    this.Yaw = RAD2DEG((float)tmpd[0]);
    this.Pitch = RAD2DEG((float)tmpd[1]);
    this.Roll = RAD2DEG((float)tmpd[2]);
  }

  public void increment(Orient paramOrient)
  {
    add(paramOrient, this);
  }

  public void add(Orient paramOrient)
  {
    add(this, paramOrient);
  }

  public void sub(Orient paramOrient1, Orient paramOrient2)
  {
    paramOrient2.getMatrix(M_0);
    paramOrient1.getMatrix(M_1);
    M_.mulTransposeLeft(M_0, M_1);

    M_.getEulers(tmpd);
    this.Yaw = RAD2DEG((float)tmpd[0]);
    this.Pitch = RAD2DEG((float)tmpd[1]);
    this.Roll = RAD2DEG((float)tmpd[2]);
  }

  public void increment(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    Tmp.set(paramFloat1, paramFloat2, paramFloat3);
    add(Tmp, this);
  }

  public void sub(Orient paramOrient)
  {
    sub(this, paramOrient);
  }

  public void wrap()
  {
    this.Yaw = ((this.Yaw + 2880.0F) % 360.0F);
    this.Pitch = ((this.Pitch + 2880.0F) % 360.0F);
    this.Roll = ((this.Roll + 2880.0F) % 360.0F);

    if (this.Pitch > 180.0F) this.Pitch -= 360.0F;
    if (this.Pitch > 90.0F) {
      this.Pitch = (180.0F - this.Pitch); this.Roll += 180.0F; this.Yaw += 180.0F;
    }
    else if (this.Pitch < -90.0F) {
      this.Pitch = (-180.0F - this.Pitch); this.Roll += 180.0F; this.Yaw += 180.0F;
    }
    while (this.Roll > 180.0F) this.Roll -= 360.0F;
    while (this.Yaw > 180.0F) this.Yaw -= 360.0F; 
  }

  public void wrap360()
  {
    this.Yaw %= 360.0F;
    this.Pitch %= 360.0F;
    this.Roll %= 360.0F;
  }

  public void getMatrix(Matrix3d paramMatrix3d)
  {
    makeMatrix(paramMatrix3d);
  }

  public void getMatrixInv(Matrix3d paramMatrix3d)
  {
    makeMatrixInv(paramMatrix3d);
  }

  public void getQuat(Quat4f paramQuat4f) {
    makeQuat(paramQuat4f);
  }

  public void transform(Tuple3d paramTuple3d)
  {
    getMatrix(M_);
    M_.transform(paramTuple3d);
  }

  public void transform(Tuple3f paramTuple3f)
  {
    getMatrix(M_);
    M_.transform(paramTuple3f);
  }

  public void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    getMatrix(M_);
    M_.transform(paramTuple3d1, paramTuple3d2);
  }

  public void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    getMatrix(M_);
    M_.transform(paramTuple3f1, paramTuple3f2);
  }

  public void transformInv(Tuple3d paramTuple3d)
  {
    getMatrixInv(Mi_);
    Mi_.transform(paramTuple3d);
  }

  public void transformInv(Tuple3f paramTuple3f)
  {
    getMatrixInv(Mi_);
    Mi_.transform(paramTuple3f);
  }

  public void transformInv(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    getMatrixInv(Mi_);
    Mi_.transform(paramTuple3d1, paramTuple3d2);
  }

  public void transformInv(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    getMatrixInv(Mi_);
    Mi_.transform(paramTuple3f1, paramTuple3f2);
  }

  public void interpolate(Orient paramOrient1, Orient paramOrient2, float paramFloat)
  {
    paramOrient1.getQuat(Q_0);
    paramOrient2.getQuat(Q_1);
    Q1.interpolate(Q_0, Q_1, paramFloat);
    Q1.getEulers(tmpf);
    this.Yaw = RAD2DEG(tmpf[0]);
    this.Pitch = RAD2DEG(tmpf[1]);
    this.Roll = RAD2DEG(tmpf[2]);
  }

  public void interpolate(Orient paramOrient, float paramFloat)
  {
    getQuat(Q_0);
    paramOrient.getQuat(Q_1);
    Q_0.interpolate(Q_1, paramFloat);
    Q_0.getEulers(tmpf);
    this.Yaw = RAD2DEG(tmpf[0]);
    this.Pitch = RAD2DEG(tmpf[1]);
    this.Roll = RAD2DEG(tmpf[2]);
  }

  public int hashCode()
  {
    int i = Float.floatToIntBits(this.Yaw);
    int j = Float.floatToIntBits(this.Pitch);
    int k = Float.floatToIntBits(this.Roll);
    return i ^ j ^ k;
  }

  public boolean equals(Orient paramOrient)
  {
    return (paramOrient != null) && (this.Yaw == paramOrient.Yaw) && (this.Pitch == paramOrient.Pitch) && (this.Roll == paramOrient.Roll);
  }

  public boolean epsilonEquals(Orient paramOrient, float paramFloat)
  {
    return (Math.abs(paramOrient.Yaw - this.Yaw) <= paramFloat) && (Math.abs(paramOrient.Pitch - this.Pitch) <= paramFloat) && (Math.abs(paramOrient.Roll - this.Roll) <= paramFloat);
  }

  public String toString()
  {
    return "(" + this.Yaw + ", " + this.Pitch + ", " + this.Roll + ")";
  }

  public void orient(Vector3f paramVector3f)
  {
    VV.set(paramVector3f);
    orient(VV);
  }

  public void orient(Vector3d paramVector3d)
  {
    float f1 = (float)Math.cos(DEG2RAD(this.Yaw));
    float f2 = (float)Math.sin(DEG2RAD(this.Yaw));

    Vt.z = paramVector3d.z;
    Vt.x = (paramVector3d.x * f1 + paramVector3d.y * f2);
    Vt.y = (paramVector3d.y * f1 - paramVector3d.x * f2);

    setYPR(this.Yaw, -RAD2DEG((float)Math.atan2(Vt.x, Vt.z)), RAD2DEG((float)Math.asin(Vt.y)));
  }

  public void setAT0(Vector3f paramVector3f)
  {
    set(-RAD2DEG((float)Math.atan2(paramVector3f.y, paramVector3f.x)), RAD2DEG((float)Math.atan2(paramVector3f.z, Math.sqrt(paramVector3f.x * paramVector3f.x + paramVector3f.y * paramVector3f.y))), 0.0F);
  }

  public void setAT0(Vector3d paramVector3d)
  {
    set(-RAD2DEG((float)Math.atan2(paramVector3d.y, paramVector3d.x)), RAD2DEG((float)Math.atan2(paramVector3d.z, Math.sqrt(paramVector3d.x * paramVector3d.x + paramVector3d.y * paramVector3d.y))), 0.0F);
  }
}