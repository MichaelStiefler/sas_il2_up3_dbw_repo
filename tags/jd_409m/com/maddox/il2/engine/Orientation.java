package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Quat4f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;

public class Orientation extends Orient
{
  private Quat4f Q = new Quat4f();
  private Matrix3d M = new Matrix3d();
  private Matrix3d Mi = new Matrix3d();

  private boolean QuatOK = false;
  private boolean MatrixOK = false;
  private boolean MatrInvOK = false;

  public Orientation() {
  }
  public Orientation(float paramFloat1, float paramFloat2, float paramFloat3) {
    set(paramFloat1, paramFloat2, paramFloat3);
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super.set(paramFloat1, paramFloat2, paramFloat3);
    this.QuatOK = false;
    this.MatrixOK = false;
    this.MatrInvOK = false;
  }

  public void setYPR(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super.setYPR(paramFloat1, paramFloat2, paramFloat3);
    this.QuatOK = false;
    this.MatrixOK = false;
    this.MatrInvOK = false;
  }

  public void setYaw(float paramFloat) {
    super.setYaw(paramFloat);
    this.QuatOK = false;
    this.MatrixOK = false;
    this.MatrInvOK = false;
  }

  public void set(Orient paramOrient)
  {
    super.set(paramOrient);
    if ((paramOrient instanceof Orientation)) {
      Orientation localOrientation = (Orientation)paramOrient;
      this.QuatOK = localOrientation.QuatOK;
      this.MatrixOK = localOrientation.MatrixOK;
      this.MatrInvOK = localOrientation.MatrInvOK;
      if (this.QuatOK) this.Q.set(localOrientation.Q);
      if (this.MatrixOK) this.M.set(localOrientation.M);
      if (this.MatrInvOK) this.Mi.set(localOrientation.Mi); 
    }
    else {
      this.QuatOK = false;
      this.MatrixOK = false;
      this.MatrInvOK = false;
    }
  }

  private void makeMatrix() {
    if (this.MatrixOK) return;
    makeMatrix(this.M);
    this.MatrixOK = true;
  }

  private void makeMatrixInv() {
    if (this.MatrInvOK) return;
    makeMatrixInv(this.Mi);
    this.MatrInvOK = true;
  }

  private void makeQuat() {
    if (this.QuatOK) return;
    makeQuat(this.Q);
    this.QuatOK = true;
  }

  public void add(Orient paramOrient1, Orient paramOrient2)
  {
    super.add(paramOrient1, paramOrient2);

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void sub(Orient paramOrient1, Orient paramOrient2)
  {
    super.sub(paramOrient1, paramOrient2);

    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
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

  public void getQuat(Quat4f paramQuat4f) {
    makeQuat();
    paramQuat4f.set(this.Q);
  }

  public void transform(Tuple3d paramTuple3d)
  {
    makeMatrix();
    this.M.transform(paramTuple3d);
  }

  public void transform(Tuple3f paramTuple3f)
  {
    makeMatrix();
    this.M.transform(paramTuple3f);
  }

  public void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    makeMatrix();
    this.M.transform(paramTuple3d1, paramTuple3d2);
  }

  public void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    makeMatrix();
    this.M.transform(paramTuple3f1, paramTuple3f2);
  }

  public void transformInv(Tuple3d paramTuple3d)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3d);
  }

  public void transformInv(Tuple3f paramTuple3f)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3f);
  }

  public void transformInv(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3d1, paramTuple3d2);
  }

  public void transformInv(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    makeMatrixInv();
    this.Mi.transform(paramTuple3f1, paramTuple3f2);
  }

  public void interpolate(Orient paramOrient1, Orient paramOrient2, float paramFloat)
  {
    super.interpolate(paramOrient1, paramOrient2, paramFloat);
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }

  public void interpolate(Orient paramOrient, float paramFloat)
  {
    super.interpolate(paramOrient, paramFloat);
    this.MatrixOK = (this.QuatOK = this.MatrInvOK = 0);
  }
}