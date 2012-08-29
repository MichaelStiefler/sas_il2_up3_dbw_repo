package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Line3d
  implements Serializable, Cloneable
{
  public Vector3d A = new Vector3d();

  public Point3d P0 = new Point3d();

  public Line3d()
  {
    this.A.jdField_x_of_type_Double = (this.A.jdField_y_of_type_Double = this.A.jdField_z_of_type_Double = 0.5773502588272095D);
  }

  public Line3d(Line3d paramLine3d)
  {
    this.A.set(paramLine3d.A); this.P0.set(paramLine3d.P0);
  }

  public Line3d(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    set(paramPoint3d1, paramPoint3d2);
  }

  public final void set(Line3d paramLine3d)
  {
    this.A.set(paramLine3d.A); this.P0.set(paramLine3d.P0);
  }

  public final void set(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    this.A.jdField_x_of_type_Double = (paramPoint3d2.jdField_x_of_type_Double - paramPoint3d1.jdField_x_of_type_Double);
    this.A.jdField_y_of_type_Double = (paramPoint3d2.jdField_y_of_type_Double - paramPoint3d1.jdField_y_of_type_Double);
    this.A.jdField_z_of_type_Double = (paramPoint3d2.jdField_z_of_type_Double - paramPoint3d1.jdField_z_of_type_Double);
    this.A.normalize();
    this.P0 = paramPoint3d1;
  }

  public final double distance(Point3d paramPoint3d)
  {
    Vector3d localVector3d = new Vector3d(paramPoint3d.jdField_x_of_type_Double - this.P0.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double - this.P0.jdField_y_of_type_Double, paramPoint3d.jdField_z_of_type_Double - this.P0.jdField_z_of_type_Double);

    double d = this.A.dot(localVector3d);
    return Math.sqrt(localVector3d.lengthSquared() - d * d);
  }

  public final double distance(Line3d paramLine3d)
  {
    Vector3d localVector3d = new Vector3d();
    localVector3d.cross(this.A, paramLine3d.A);
    try { localVector3d.normalize();
    } catch (RuntimeException localRuntimeException) {
      return distance(paramLine3d.P0);
    }
    return Math.abs(localVector3d.dot(this.P0) - localVector3d.dot(paramLine3d.P0));
  }

  public final double cos(Line3d paramLine3d)
  {
    return this.A.dot(paramLine3d.A);
  }

  public String toString()
  {
    return "( " + this.A.jdField_x_of_type_Double + "," + this.A.jdField_y_of_type_Double + "," + this.A.jdField_z_of_type_Double + "; " + this.P0.jdField_x_of_type_Double + "," + this.P0.jdField_y_of_type_Double + "," + this.P0.jdField_z_of_type_Double + " )";
  }

  public static void main(String[] paramArrayOfString)
  {
    Point3d localPoint3d1 = new Point3d(0.0D, 0.0D, 0.0D);
    Point3d localPoint3d2 = new Point3d(1.0D, 0.0D, 0.0D);
    Point3d localPoint3d3 = new Point3d(0.0D, 1.0D, 0.0D);
    Point3d localPoint3d4 = new Point3d(0.0D, 0.0D, 1.0D);
    Line3d localLine3d1 = new Line3d(localPoint3d1, localPoint3d3);
    Line3d localLine3d2 = new Line3d(localPoint3d2, localPoint3d3);
    System.out.println("Line: " + localLine3d2);

    System.out.println("Point: " + localPoint3d1);
    System.out.println("Distance: " + localLine3d2.distance(localPoint3d1) + "\n");

    System.out.println("Line1: " + localLine3d1);
    System.out.println("Line2: " + localLine3d2);
    System.out.println("Distance: " + localLine3d1.distance(localLine3d2));
    System.out.println("Cos: " + localLine3d1.cos(localLine3d2));
  }
}