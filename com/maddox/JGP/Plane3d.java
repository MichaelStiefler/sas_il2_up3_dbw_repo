package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Plane3d
  implements Serializable, Cloneable
{
  public Vector3d N = new Vector3d();
  public double D;

  public Plane3d()
  {
    this.N.jdField_x_of_type_Double = (this.N.jdField_y_of_type_Double = this.N.jdField_z_of_type_Double = 0.5773502588272095D);
    this.D = 0.0D;
  }

  public Plane3d(Plane3d paramPlane3d) {
    this.N.jdField_x_of_type_Double = paramPlane3d.N.jdField_x_of_type_Double; this.N.jdField_y_of_type_Double = paramPlane3d.N.jdField_y_of_type_Double; this.N.jdField_z_of_type_Double = paramPlane3d.N.jdField_z_of_type_Double;
    this.D = paramPlane3d.D;
  }

  public Plane3d(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3)
  {
    set(paramPoint3d1, paramPoint3d2, paramPoint3d3);
  }

  public Plane3d(Vector3d paramVector3d, Point3d paramPoint3d)
  {
    this.N.normalize(paramVector3d);
    this.D = (-this.N.dot(paramPoint3d));
  }

  public final void set(Plane3d paramPlane3d) {
    this.N.jdField_x_of_type_Double = paramPlane3d.N.jdField_x_of_type_Double; this.N.jdField_y_of_type_Double = paramPlane3d.N.jdField_y_of_type_Double; this.N.jdField_z_of_type_Double = paramPlane3d.N.jdField_z_of_type_Double;
    this.D = paramPlane3d.D;
  }

  public final void set(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3)
  {
    double d1 = paramPoint3d2.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double; double d4 = paramPoint3d3.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double;
    double d2 = paramPoint3d2.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double; double d5 = paramPoint3d3.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double;
    double d3 = paramPoint3d2.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double; double d6 = paramPoint3d3.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double;

    this.N.jdField_x_of_type_Double = (d2 * d6 - d5 * d3);
    this.N.jdField_y_of_type_Double = (d4 * d3 - d1 * d6);
    this.N.jdField_z_of_type_Double = (d1 * d5 - d4 * d3);
    this.N.normalize();
    this.D = (-this.N.dot(paramPoint3d1));
  }

  public final void set(Vector3d paramVector3d, Point3d paramPoint3d)
  {
    this.N.normalize(paramVector3d);
    this.D = (-this.N.dot(paramPoint3d));
  }

  public final double deviation(Point3d paramPoint3d)
  {
    return this.N.dot(paramPoint3d) + this.D;
  }

  public final double distance(Point3d paramPoint3d)
  {
    return Math.abs(this.N.dot(paramPoint3d) + this.D);
  }

  public final Line3d cross(Plane3d paramPlane3d)
    throws JGPException
  {
    Line3d localLine3d = new Line3d();
    localLine3d.A.cross(this.N, paramPlane3d.N);
    try { localLine3d.A.normalize();
    } catch (RuntimeException localRuntimeException1) {
      throw new JGPException("Can't make a line from parallel planes");
    }

    double[] arrayOfDouble = { this.N.jdField_x_of_type_Double, this.N.jdField_y_of_type_Double, this.N.jdField_z_of_type_Double, -this.D, paramPlane3d.N.jdField_x_of_type_Double, paramPlane3d.N.jdField_y_of_type_Double, paramPlane3d.N.jdField_z_of_type_Double, -paramPlane3d.D, localLine3d.A.jdField_x_of_type_Double, localLine3d.A.jdField_y_of_type_Double, localLine3d.A.jdField_z_of_type_Double, 0.0D };
    try
    {
      localLine3d.P0 = NSolved.Solve3d(arrayOfDouble);
    } catch (RuntimeException localRuntimeException2) {
      throw new JGPException("Making a line from planes: some error");
    }
    return localLine3d;
  }

  public final Point3d cross(Line3d paramLine3d)
    throws JGPException
  {
    Point3d localPoint3d = new Point3d(paramLine3d.A);
    localPoint3d.scale((this.N.dot(paramLine3d.P0) + this.D) / this.N.dot(paramLine3d.A));
    localPoint3d.sub(paramLine3d.P0, localPoint3d);
    return localPoint3d;
  }

  public final double cos(Plane3d paramPlane3d)
  {
    return this.N.dot(paramPlane3d.N);
  }

  public final double cos(Line3d paramLine3d)
  {
    return this.N.dot(paramLine3d.A);
  }

  public String toString()
  {
    return "( " + this.N.jdField_x_of_type_Double + "," + this.N.jdField_y_of_type_Double + "," + this.N.jdField_z_of_type_Double + ", " + this.D + " )";
  }

  public static void main(String[] paramArrayOfString) throws JGPException
  {
    Point3d localPoint3d1 = new Point3d(0.0D, 0.0D, 0.0D);
    Point3d localPoint3d2 = new Point3d(1.0D, 0.0D, 0.0D);
    Point3d localPoint3d3 = new Point3d(0.0D, 1.0D, 0.0D);
    Point3d localPoint3d4 = new Point3d(0.0D, 0.0D, 1.0D);
    Plane3d localPlane3d1 = new Plane3d(localPoint3d1, localPoint3d2, localPoint3d3);
    Plane3d localPlane3d2 = new Plane3d(localPoint3d2, localPoint3d4, localPoint3d3);

    System.out.println("Plane1: " + localPlane3d1);
    System.out.println("Plane2: " + localPlane3d2);

    System.out.println("Point: " + localPoint3d1 + "\n");
    System.out.println("Distance: " + localPlane3d2.distance(localPoint3d1) + "\n");
    System.out.println("PL1.cross(PL1):");
    try { localPlane3d1.cross(localPlane3d1); } catch (RuntimeException localRuntimeException) {
      System.out.println(localRuntimeException.getMessage());
    }System.out.println("PL1.cross(PL2):");
    Line3d localLine3d = localPlane3d1.cross(localPlane3d2);
    System.out.println("Line: " + localLine3d);
  }
}