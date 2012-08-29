package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Ray3f
  implements Serializable, Cloneable
{
  public Line3f L0 = new Line3f();

  public Point3f P0 = new Point3f();

  public Plane3f PL = new Plane3f();

  public Ray3f() {
  }

  public Ray3f(Ray3f paramRay3f) {
    this.L0.set(paramRay3f.L0);
    this.P0.set(paramRay3f.P0);
    this.PL.set(paramRay3f.PL);
  }

  public Ray3f(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    set(paramPoint3f1, paramPoint3f2);
  }

  public Ray3f(Vector3f paramVector3f, Point3f paramPoint3f)
  {
    set(paramVector3f, paramPoint3f);
  }

  public final void set(Ray3f paramRay3f) {
    this.L0.set(paramRay3f.L0);
    this.P0.set(paramRay3f.P0);
    this.PL.set(paramRay3f.PL);
  }

  public final void set(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    this.P0.set(paramPoint3f1);
    this.L0.set(paramPoint3f1, paramPoint3f2);
    this.PL.N = this.L0.A;
    this.PL.D = (-this.PL.N.dot(paramPoint3f1));
  }

  public final void set(Vector3f paramVector3f, Point3f paramPoint3f)
  {
    this.P0.set(paramPoint3f);
    this.L0.A.set(paramVector3f);
    this.L0.A.normalize();
    this.L0.P0.set(paramPoint3f);
    this.PL.N = this.L0.A;
    this.PL.D = (-this.PL.N.dot(paramPoint3f));
  }

  public final float distance(Point3f paramPoint3f)
  {
    return this.L0.distance(paramPoint3f);
  }

  public final float deviationFB(Point3f paramPoint3f)
  {
    return this.PL.N.dot(paramPoint3f) + this.PL.D;
  }

  public final float cos(Ray3f paramRay3f)
  {
    return this.PL.N.dot(paramRay3f.PL.N);
  }

  public final float cos(Plane3f paramPlane3f)
  {
    return this.PL.N.dot(paramPlane3f.N);
  }

  public final Point3f cross(Plane3f paramPlane3f)
    throws JGPException
  {
    Point3f localPoint3f = paramPlane3f.cross(this.L0);
    if (deviationFB(localPoint3f) >= 0.0F) return localPoint3f;
    throw new JGPException("Ray and Line are'nt crossed");
  }

  public String toString()
  {
    return "( " + this.PL.N + "," + this.P0 + " )";
  }

  public static void main(String[] paramArrayOfString) throws JGPException
  {
    Point3f localPoint3f1 = new Point3f(0.0F, 0.0F, 0.0F);
    Point3f localPoint3f2 = new Point3f(1.0F, 0.0F, 0.0F);
    Point3f localPoint3f3 = new Point3f(0.0F, 1.0F, 0.0F);
    Point3f localPoint3f4 = new Point3f(0.0F, 0.0F, 1.0F);
    Ray3f localRay3f1 = new Ray3f(localPoint3f1, localPoint3f2);
    Ray3f localRay3f2 = new Ray3f(localPoint3f2, localPoint3f4);
    Plane3f localPlane3f = new Plane3f(localPoint3f2, localPoint3f3, localPoint3f4);
    System.out.println("Ray1: " + localRay3f1);
    System.out.println("Ray2: " + localRay3f2);

    System.out.println("Point: " + localPoint3f1 + "\n");
    System.out.println("Ray2/Point distance: " + localRay3f2.distance(localPoint3f1) + "\n");
    System.out.println("\nPlane: " + localPlane3f);
    System.out.println("Ray2/Plane crosspoint: " + localRay3f2.cross(localPlane3f) + "\n");
  }
}