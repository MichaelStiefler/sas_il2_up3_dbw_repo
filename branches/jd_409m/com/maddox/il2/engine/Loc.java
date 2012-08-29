package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;

public class Loc
{
  protected Point3d P;
  protected Orient O;
  private static Matrix3d M3 = new Matrix3d();

  private static final Tuple3d tup = new Point3d();

  public Loc()
  {
    this.P = new Point3d();
    this.O = new Orient();
  }

  public Loc(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3) {
    this();
    set(paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2, paramFloat3);
  }

  public Loc(Loc paramLoc) {
    this();
    set(paramLoc);
  }

  public Loc(Tuple3d paramTuple3d, Orient paramOrient) {
    this();
    set(paramTuple3d, paramOrient);
  }

  public Loc(Tuple3d paramTuple3d) {
    this();
    set(paramTuple3d);
  }

  public Loc(Orient paramOrient) {
    this();
    set(paramOrient);
  }

  public Loc(double[] paramArrayOfDouble) {
    this();
    set(paramArrayOfDouble);
  }

  public void set(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.P.set(paramDouble1, paramDouble2, paramDouble3);
    this.O.set(paramFloat1, paramFloat2, paramFloat3);
  }

  public void set(Loc paramLoc)
  {
    this.P.set(paramLoc.P);
    this.O.set(paramLoc.O);
  }

  public void set(Tuple3d paramTuple3d, Orient paramOrient)
  {
    this.P.set(paramTuple3d);
    this.O.set(paramOrient);
  }

  public void set(Tuple3d paramTuple3d)
  {
    this.P.set(paramTuple3d);
  }

  public void set(Orient paramOrient)
  {
    this.O.set(paramOrient);
  }

  public void set(double[] paramArrayOfDouble)
  {
    this.P.set(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2]);
    this.O.set((float)paramArrayOfDouble[3], (float)paramArrayOfDouble[4], (float)paramArrayOfDouble[5]);
  }

  public float getAzimut() {
    return this.O.getAzimut();
  }
  public float getTangage() {
    return this.O.getTangage();
  }
  public float getKren() {
    return this.O.getKren();
  }
  public double getX() { return this.P.jdField_x_of_type_Double; } 
  public double getY() { return this.P.jdField_y_of_type_Double; } 
  public double getZ() { return this.P.jdField_z_of_type_Double; } 
  public Point3d getPoint() {
    return this.P; } 
  public Orient getOrient() { return this.O; }

  public void get(Tuple3d paramTuple3d) {
    paramTuple3d.set(this.P);
  }
  public void get(Orient paramOrient) {
    paramOrient.set(this.O);
  }

  public void get(Tuple3d paramTuple3d, Orient paramOrient) {
    paramTuple3d.set(this.P);
    paramOrient.set(this.O);
  }

  public void get(double[] paramArrayOfDouble)
  {
    paramArrayOfDouble[0] = this.P.jdField_x_of_type_Double; paramArrayOfDouble[1] = this.P.jdField_y_of_type_Double; paramArrayOfDouble[2] = this.P.jdField_z_of_type_Double;
    paramArrayOfDouble[3] = this.O.getAzimut(); paramArrayOfDouble[4] = this.O.getTangage(); paramArrayOfDouble[5] = this.O.getKren();
  }

  public void add(Loc paramLoc1, Loc paramLoc2)
  {
    paramLoc2.transform(paramLoc1.P, this.P);
    this.O.add(paramLoc1.O, paramLoc2.O);
  }

  public void add(Loc paramLoc)
  {
    add(this, paramLoc);
  }

  public void add(Tuple3d paramTuple3d)
  {
    this.P.add(paramTuple3d);
  }

  public void sub(Loc paramLoc1, Loc paramLoc2)
  {
    paramLoc2.transformInv(paramLoc1.P, this.P);
    this.O.sub(paramLoc1.O, paramLoc2.O);
  }

  public void sub(Loc paramLoc)
  {
    sub(this, paramLoc);
  }

  public void wrap()
  {
    this.O.wrap();
  }

  public void getMatrix(Matrix4d paramMatrix4d)
  {
    this.O.getMatrix(M3);
    paramMatrix4d.set(M3);
    paramMatrix4d.m03 = this.P.jdField_x_of_type_Double;
    paramMatrix4d.m13 = this.P.jdField_y_of_type_Double;
    paramMatrix4d.m23 = this.P.jdField_z_of_type_Double;
  }

  public void getOrientMatrix(Matrix3d paramMatrix3d)
  {
    this.O.getMatrix(paramMatrix3d);
  }

  public void getOrientMatrixInv(Matrix3d paramMatrix3d)
  {
    this.O.getMatrixInv(paramMatrix3d);
  }

  public void transform(Point3d paramPoint3d)
  {
    this.O.transform(paramPoint3d);
    paramPoint3d.add(this.P);
  }

  public void transform(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    this.O.transform(paramPoint3d1, paramPoint3d2);
    paramPoint3d2.add(this.P);
  }

  public void transform(Vector3d paramVector3d)
  {
    this.O.transform(paramVector3d);
  }

  public void transform(Vector3d paramVector3d1, Vector3d paramVector3d2)
  {
    this.O.transform(paramVector3d1, paramVector3d2);
  }

  public void transformInv(Point3d paramPoint3d)
  {
    paramPoint3d.sub(this.P);
    this.O.transformInv(paramPoint3d);
  }

  public void transformInv(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    tup.sub(paramPoint3d1, this.P);
    this.O.transformInv(tup, paramPoint3d2);
  }

  public void transformInv(Vector3d paramVector3d)
  {
    this.O.transformInv(paramVector3d);
  }

  public void transformInv(Vector3d paramVector3d1, Vector3d paramVector3d2)
  {
    this.O.transformInv(paramVector3d1, paramVector3d2);
  }

  public final void interpolate(Loc paramLoc1, Loc paramLoc2, float paramFloat)
  {
    this.P.interpolate(paramLoc1.P, paramLoc2.P, paramFloat);
    this.O.interpolate(paramLoc1.O, paramLoc2.O, paramFloat);
  }

  public final void interpolate(Loc paramLoc, float paramFloat)
  {
    this.P.interpolate(paramLoc.P, paramFloat);
    this.O.interpolate(paramLoc.O, paramFloat);
  }

  public final void interpolate(Loc paramLoc1, Loc paramLoc2, double paramDouble)
  {
    this.P.interpolate(paramLoc1.P, paramLoc2.P, paramDouble);
    this.O.interpolate(paramLoc1.O, paramLoc2.O, (float)paramDouble);
  }

  public final void interpolate(Loc paramLoc, double paramDouble)
  {
    this.P.interpolate(paramLoc.P, paramDouble);
    this.O.interpolate(paramLoc.O, (float)paramDouble);
  }

  public int hashCode()
  {
    return this.P.hashCode() ^ this.O.hashCode();
  }

  public boolean equals(Loc paramLoc)
  {
    return (this.P.equals(paramLoc.P)) && (this.O.equals(paramLoc.O));
  }

  public boolean epsilonEquals(Loc paramLoc, float paramFloat)
  {
    return (this.P.epsilonEquals(paramLoc.P, paramFloat)) && (this.O.epsilonEquals(paramLoc.O, paramFloat));
  }

  public String toString()
  {
    return "( " + this.P + "," + this.O + " ) ";
  }

  public void orient(Vector3d paramVector3d)
  {
    this.O.orient(paramVector3d);
  }
}