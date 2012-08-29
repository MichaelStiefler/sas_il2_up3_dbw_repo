package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Locator3f
  implements Serializable, Cloneable
{
  private Point3f P = new Point3f();
  private Orient3f O = new Orient3f();

  private static Matrix3f M3 = new Matrix3f();

  static float PI = 3.141593F;

  private static final Tuple3f tup = new Point3f();
  private static Tuple3f P1 = new Point3f();
  private static Orient3f O1 = new Orient3f();
  private static final Locator3f Tmp = new Locator3f();

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.P.set(paramFloat1, paramFloat2, paramFloat3);
    this.O.set(paramFloat4, paramFloat5, paramFloat6);
  }

  public void set(Locator3f paramLocator3f)
  {
    this.P.set(paramLocator3f.P);
    this.O.set(paramLocator3f.O);
  }

  public void set(Tuple3f paramTuple3f, Orient3f paramOrient3f)
  {
    this.P.set(paramTuple3f);
    this.O.set(paramOrient3f);
  }

  public void set(Tuple3f paramTuple3f)
  {
    this.P.set(paramTuple3f);
  }

  public void set(Orient3f paramOrient3f)
  {
    this.O.set(paramOrient3f);
  }

  public void set(float[] paramArrayOfFloat)
  {
    this.P.set(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    this.O.set(paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5]);
  }

  public float getYaw()
  {
    return this.O.getYaw();
  }

  public float getPitch()
  {
    return this.O.getPitch();
  }

  public float getRoll()
  {
    return this.O.getRoll();
  }
  public float getX() { return this.P.x; } 
  public float getY() { return this.P.y; } 
  public float getZ() { return this.P.z; }

  public void get(Tuple3f paramTuple3f) {
    paramTuple3f.set(this.P);
  }
  public void get(Orient3f paramOrient3f) {
    paramOrient3f.set(this.O);
  }

  public void get(Tuple3f paramTuple3f, Orient3f paramOrient3f) {
    paramTuple3f.set(this.P);
    paramOrient3f.set(this.O);
  }

  public void get(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.P.x; paramArrayOfFloat[1] = this.P.y; paramArrayOfFloat[2] = this.P.z;
    paramArrayOfFloat[3] = this.O.getYaw(); paramArrayOfFloat[4] = this.O.getPitch(); paramArrayOfFloat[5] = this.O.getRoll();
  }

  public void add(Locator3f paramLocator3f1, Locator3f paramLocator3f2)
  {
    paramLocator3f2.transform(paramLocator3f1.P, this.P);
    this.O.add(paramLocator3f1.O, paramLocator3f2.O);
  }

  public void add(Locator3f paramLocator3f)
  {
    add(this, paramLocator3f);
  }

  public void sub(Locator3f paramLocator3f1, Locator3f paramLocator3f2)
  {
    paramLocator3f2.transformInv(paramLocator3f1.P, this.P);
    this.O.sub(paramLocator3f1.O, paramLocator3f2.O);
  }

  public void sub(Locator3f paramLocator3f)
  {
    sub(this, paramLocator3f);
  }

  public void wrap()
  {
    this.O.wrap();
  }

  public void getMatrix(Matrix4f paramMatrix4f)
  {
    this.O.getMatrix(M3);
    paramMatrix4f.set(M3);
    paramMatrix4f.m03 = this.P.x;
    paramMatrix4f.m13 = this.P.y;
    paramMatrix4f.m23 = this.P.z;
  }

  public void getOrientMatrix(Matrix3f paramMatrix3f)
  {
    this.O.getMatrix(paramMatrix3f);
  }

  public void getOrientMatrixInv(Matrix3f paramMatrix3f)
  {
    this.O.getMatrixInv(paramMatrix3f);
  }

  public void transform(Point3f paramPoint3f)
  {
    this.O.transform(paramPoint3f);
    paramPoint3f.add(this.P);
  }

  public void transform(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    this.O.transform(paramPoint3f1, paramPoint3f2);
    paramPoint3f2.add(this.P);
  }

  public void transform(Vector3f paramVector3f)
  {
    this.O.transform(paramVector3f);
  }

  public void transform(Vector3f paramVector3f1, Vector3f paramVector3f2)
  {
    this.O.transform(paramVector3f1, paramVector3f2);
  }

  public void transformInv(Point3f paramPoint3f)
  {
    paramPoint3f.sub(this.P);
    this.O.transformInv(paramPoint3f);
  }

  public void transformInv(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    tup.sub(paramPoint3f1, this.P);
    this.O.transformInv(tup, paramPoint3f2);
  }

  public void transformInv(Vector3f paramVector3f)
  {
    this.O.transformInv(paramVector3f);
  }

  public void transformInv(Vector3f paramVector3f1, Vector3f paramVector3f2)
  {
    this.O.transformInv(tup, paramVector3f2);
  }

  public final void interpolate(Locator3f paramLocator3f1, Locator3f paramLocator3f2, float paramFloat)
  {
    this.P.interpolate(paramLocator3f1.P, paramLocator3f2.P, paramFloat);
    this.O.interpolate(paramLocator3f1.O, paramLocator3f2.O, paramFloat);
  }

  public final void interpolate(Locator3f paramLocator3f, float paramFloat)
  {
    this.P.interpolate(paramLocator3f.P, paramFloat);
    this.O.interpolate(paramLocator3f.O, paramFloat);
  }

  public int hashCode()
  {
    return this.P.hashCode() ^ this.O.hashCode();
  }

  public boolean equals(Locator3f paramLocator3f)
  {
    return (this.P.equals(paramLocator3f.P)) && (this.O.equals(paramLocator3f.O));
  }

  public boolean epsilonEquals(Locator3f paramLocator3f, float paramFloat)
  {
    return (this.P.epsilonEquals(paramLocator3f.P, paramFloat)) && (this.O.epsilonEquals(paramLocator3f.O, paramFloat));
  }

  public String toString()
  {
    return "( " + this.P + "," + this.O + " ) ";
  }

  public static void main(String[] paramArrayOfString)
  {
    Locator3f localLocator3f1 = new Locator3f();
    Locator3f localLocator3f2 = new Locator3f();
    Locator3f localLocator3f3 = new Locator3f();
    Vector3f localVector3f = new Vector3f();

    localLocator3f1.set(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    localLocator3f2.set(1.0F, 0.0F, 0.0F, PI / 4.0F, 0.2F, 0.3F);
    System.out.println("Slave:" + localLocator3f1);
    System.out.println("Master:" + localLocator3f2);

    localVector3f.set(1.0F, 1.0F, 1.0F);
    System.out.println("v0:" + localVector3f);
    localLocator3f2.transform(localVector3f);
    System.out.println("v1:" + localVector3f);
    localLocator3f2.transformInv(localVector3f);
    System.out.println("v2:" + localVector3f);

    localLocator3f3.add(localLocator3f1, localLocator3f2);
    System.out.println("Master+Slave:" + localLocator3f3);
    localLocator3f3.sub(localLocator3f2);
    System.out.println("Master+Slave-Slave:" + localLocator3f3);
  }
}