package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Plane3f
  implements Serializable, Cloneable
{
  public Vector3f N = new Vector3f();
  public float D;

  public Plane3f()
  {
    this.N.jdField_x_of_type_Float = (this.N.jdField_y_of_type_Float = this.N.jdField_z_of_type_Float = 0.5773503F);
    this.D = 0.0F;
  }

  public Plane3f(Plane3f paramPlane3f) {
    this.N.jdField_x_of_type_Float = paramPlane3f.N.jdField_x_of_type_Float; this.N.jdField_y_of_type_Float = paramPlane3f.N.jdField_y_of_type_Float; this.N.jdField_z_of_type_Float = paramPlane3f.N.jdField_z_of_type_Float;
    this.D = paramPlane3f.D;
  }

  public Plane3f(Point3f paramPoint3f1, Point3f paramPoint3f2, Point3f paramPoint3f3)
  {
    set(paramPoint3f1, paramPoint3f2, paramPoint3f3);
  }

  public Plane3f(Vector3f paramVector3f, Point3f paramPoint3f)
  {
    this.N.normalize(paramVector3f);
    this.D = (-this.N.dot(paramPoint3f));
  }

  public final void set(Plane3f paramPlane3f) {
    this.N.jdField_x_of_type_Float = paramPlane3f.N.jdField_x_of_type_Float; this.N.jdField_y_of_type_Float = paramPlane3f.N.jdField_y_of_type_Float; this.N.jdField_z_of_type_Float = paramPlane3f.N.jdField_z_of_type_Float;
    this.D = paramPlane3f.D;
  }

  public final void set(Point3f paramPoint3f1, Point3f paramPoint3f2, Point3f paramPoint3f3)
  {
    float f1 = paramPoint3f2.jdField_x_of_type_Float - paramPoint3f1.jdField_x_of_type_Float; float f4 = paramPoint3f3.jdField_x_of_type_Float - paramPoint3f1.jdField_x_of_type_Float;
    float f2 = paramPoint3f2.jdField_y_of_type_Float - paramPoint3f1.jdField_y_of_type_Float; float f5 = paramPoint3f3.jdField_y_of_type_Float - paramPoint3f1.jdField_y_of_type_Float;
    float f3 = paramPoint3f2.jdField_z_of_type_Float - paramPoint3f1.jdField_z_of_type_Float; float f6 = paramPoint3f3.jdField_z_of_type_Float - paramPoint3f1.jdField_z_of_type_Float;

    this.N.jdField_x_of_type_Float = (f2 * f6 - f5 * f3);
    this.N.jdField_y_of_type_Float = (f4 * f3 - f1 * f6);
    this.N.jdField_z_of_type_Float = (f1 * f5 - f4 * f3);
    this.N.normalize();
    this.D = (-this.N.dot(paramPoint3f1));
  }

  public final void set(Vector3f paramVector3f, Point3f paramPoint3f)
  {
    this.N.normalize(paramVector3f);
    this.D = (-this.N.dot(paramPoint3f));
  }

  public final float deviation(Point3f paramPoint3f)
  {
    return this.N.dot(paramPoint3f) + this.D;
  }

  public final float distance(Point3f paramPoint3f)
  {
    return Math.abs(this.N.dot(paramPoint3f) + this.D);
  }

  public final Line3f cross(Plane3f paramPlane3f)
    throws JGPException
  {
    Line3f localLine3f = new Line3f();
    localLine3f.A.cross(this.N, paramPlane3f.N);
    try { localLine3f.A.normalize();
    } catch (RuntimeException localRuntimeException1) {
      throw new JGPException("Can't make a line from parallel planes");
    }

    float[] arrayOfFloat = { this.N.jdField_x_of_type_Float, this.N.jdField_y_of_type_Float, this.N.jdField_z_of_type_Float, -this.D, paramPlane3f.N.jdField_x_of_type_Float, paramPlane3f.N.jdField_y_of_type_Float, paramPlane3f.N.jdField_z_of_type_Float, -paramPlane3f.D, localLine3f.A.jdField_x_of_type_Float, localLine3f.A.jdField_y_of_type_Float, localLine3f.A.jdField_z_of_type_Float, 0.0F };
    try
    {
      localLine3f.P0 = NSolvef.Solve3f(arrayOfFloat);
    } catch (RuntimeException localRuntimeException2) {
      throw new JGPException("Making a line from planes: some error");
    }
    return localLine3f;
  }

  public final Point3f cross(Line3f paramLine3f)
    throws JGPException
  {
    Point3f localPoint3f = new Point3f(paramLine3f.A);
    localPoint3f.scale((this.N.dot(paramLine3f.P0) + this.D) / this.N.dot(paramLine3f.A));
    localPoint3f.sub(paramLine3f.P0, localPoint3f);
    return localPoint3f;
  }

  public final float cos(Plane3f paramPlane3f)
  {
    return this.N.dot(paramPlane3f.N);
  }

  public final float cos(Line3f paramLine3f)
  {
    return this.N.dot(paramLine3f.A);
  }

  public String toString()
  {
    return "( " + this.N.jdField_x_of_type_Float + "," + this.N.jdField_y_of_type_Float + "," + this.N.jdField_z_of_type_Float + ", " + this.D + " )";
  }

  public static void main(String[] paramArrayOfString) throws JGPException
  {
    Point3f localPoint3f1 = new Point3f(0.0F, 0.0F, 0.0F);
    Point3f localPoint3f2 = new Point3f(1.0F, 0.0F, 0.0F);
    Point3f localPoint3f3 = new Point3f(0.0F, 1.0F, 0.0F);
    Point3f localPoint3f4 = new Point3f(0.0F, 0.0F, 1.0F);
    Plane3f localPlane3f1 = new Plane3f(localPoint3f1, localPoint3f2, localPoint3f3);
    Plane3f localPlane3f2 = new Plane3f(localPoint3f2, localPoint3f4, localPoint3f3);

    System.out.println("Plane1: " + localPlane3f1);
    System.out.println("Plane2: " + localPlane3f2);

    System.out.println("Point: " + localPoint3f1 + "\n");
    System.out.println("Distance: " + localPlane3f2.distance(localPoint3f1) + "\n");
    System.out.println("PL1.cross(PL1):");
    try { localPlane3f1.cross(localPlane3f1); } catch (RuntimeException localRuntimeException) {
      System.out.println(localRuntimeException.getMessage());
    }System.out.println("PL1.cross(PL2):");
    Line3f localLine3f = localPlane3f1.cross(localPlane3f2);
    System.out.println("Line: " + localLine3f);
  }
}