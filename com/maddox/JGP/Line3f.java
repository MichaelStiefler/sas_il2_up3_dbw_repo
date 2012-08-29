package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Line3f
  implements Serializable, Cloneable
{
  public Vector3f A = new Vector3f();

  public Point3f P0 = new Point3f();

  public Line3f()
  {
    this.A.jdField_x_of_type_Float = (this.A.jdField_y_of_type_Float = this.A.jdField_z_of_type_Float = 0.5773503F);
  }

  public Line3f(Line3f paramLine3f)
  {
    this.A.set(paramLine3f.A); this.P0.set(paramLine3f.P0);
  }

  public Line3f(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    set(paramPoint3f1, paramPoint3f2);
  }

  public final void set(Line3f paramLine3f)
  {
    this.A.set(paramLine3f.A); this.P0.set(paramLine3f.P0);
  }

  public final void set(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    this.A.jdField_x_of_type_Float = (paramPoint3f2.jdField_x_of_type_Float - paramPoint3f1.jdField_x_of_type_Float);
    this.A.jdField_y_of_type_Float = (paramPoint3f2.jdField_y_of_type_Float - paramPoint3f1.jdField_y_of_type_Float);
    this.A.jdField_z_of_type_Float = (paramPoint3f2.jdField_z_of_type_Float - paramPoint3f1.jdField_z_of_type_Float);
    this.A.normalize();
    this.P0 = paramPoint3f1;
  }

  public final float distance(Point3f paramPoint3f)
  {
    Vector3f localVector3f = new Vector3f(paramPoint3f.jdField_x_of_type_Float - this.P0.jdField_x_of_type_Float, paramPoint3f.jdField_y_of_type_Float - this.P0.jdField_y_of_type_Float, paramPoint3f.jdField_z_of_type_Float - this.P0.jdField_z_of_type_Float);

    float f = this.A.dot(localVector3f);
    return (float)Math.sqrt(localVector3f.lengthSquared() - f * f);
  }

  public final float distance(Line3f paramLine3f)
  {
    Vector3f localVector3f = new Vector3f();
    localVector3f.cross(this.A, paramLine3f.A);
    try { localVector3f.normalize();
    } catch (RuntimeException localRuntimeException) {
      return distance(paramLine3f.P0);
    }
    return Math.abs(localVector3f.dot(this.P0) - localVector3f.dot(paramLine3f.P0));
  }

  public final float cos(Line3f paramLine3f)
  {
    return this.A.dot(paramLine3f.A);
  }

  public String toString()
  {
    return "( " + this.A.jdField_x_of_type_Float + "," + this.A.jdField_y_of_type_Float + "," + this.A.jdField_z_of_type_Float + "; " + this.P0.jdField_x_of_type_Float + "," + this.P0.jdField_y_of_type_Float + "," + this.P0.jdField_z_of_type_Float + " )";
  }

  public static void main(String[] paramArrayOfString)
  {
    Point3f localPoint3f1 = new Point3f(0.0F, 0.0F, 0.0F);
    Point3f localPoint3f2 = new Point3f(1.0F, 0.0F, 0.0F);
    Point3f localPoint3f3 = new Point3f(0.0F, 1.0F, 0.0F);
    Point3f localPoint3f4 = new Point3f(0.0F, 0.0F, 1.0F);
    Line3f localLine3f1 = new Line3f(localPoint3f1, localPoint3f3);
    Line3f localLine3f2 = new Line3f(localPoint3f2, localPoint3f3);
    System.out.println("Line: " + localLine3f2);

    System.out.println("Point: " + localPoint3f1);
    System.out.println("Distance: " + localLine3f2.distance(localPoint3f1) + "\n");

    System.out.println("Line1: " + localLine3f1);
    System.out.println("Line2: " + localLine3f2);
    System.out.println("Distance: " + localLine3f1.distance(localLine3f2));
    System.out.println("Cos: " + localLine3f1.cos(localLine3f2));
  }
}