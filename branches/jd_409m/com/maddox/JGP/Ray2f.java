package com.maddox.JGP;

import java.io.PrintStream;
import java.io.Serializable;

public class Ray2f
  implements Serializable, Cloneable
{
  public Line2f LA = new Line2f();

  public Line2f LN = new Line2f();

  public Ray2f()
  {
  }

  public Ray2f(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    set(paramPoint2f1, paramPoint2f2);
  }

  public final void set(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    this.LN.N.jdField_x_of_type_Float = (paramPoint2f2.jdField_x_of_type_Float - paramPoint2f1.jdField_x_of_type_Float);
    this.LN.N.jdField_y_of_type_Float = (paramPoint2f2.jdField_y_of_type_Float - paramPoint2f1.jdField_y_of_type_Float);
    this.LN.N.normalize();
    this.LN.C = (-this.LN.N.dot(paramPoint2f1));

    this.LA.set(paramPoint2f1, paramPoint2f2);
  }

  public final void set(Point2f paramPoint2f, Vector2f paramVector2f)
  {
    this.LN.N.set(paramVector2f);
    this.LN.N.normalize();
    this.LN.C = (-this.LN.N.dot(paramPoint2f));

    this.LA.set(paramPoint2f, paramVector2f);
  }

  public final float deviationLR(Point2f paramPoint2f)
  {
    return this.LA.deviation(paramPoint2f);
  }

  public final float deviationFB(Point2f paramPoint2f)
  {
    return this.LN.deviation(paramPoint2f);
  }

  public final Point2f cross(Ray2f paramRay2f)
    throws JGPException
  {
    float[] arrayOfFloat = { this.LA.N.jdField_x_of_type_Float, this.LA.N.jdField_y_of_type_Float, -this.LA.C, paramRay2f.LA.N.jdField_x_of_type_Float, paramRay2f.LA.N.jdField_y_of_type_Float, -paramRay2f.LA.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if ((deviationFB(localPoint2f) >= 0.0F) && (paramRay2f.deviationFB(localPoint2f) >= 0.0F)) return localPoint2f;
    throw new JGPException("Rays are not crossed");
  }

  public final Point2f cross(Line2f paramLine2f)
    throws JGPException
  {
    float[] arrayOfFloat = { this.LA.N.jdField_x_of_type_Float, this.LA.N.jdField_y_of_type_Float, -this.LA.C, paramLine2f.N.jdField_x_of_type_Float, paramLine2f.N.jdField_y_of_type_Float, -paramLine2f.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if (deviationFB(localPoint2f) >= 0.0F) return localPoint2f;
    throw new JGPException("Ray and Line are'nt crossed");
  }

  public final boolean crossed(Ray2f paramRay2f)
  {
    if (this.LA.N.equals(paramRay2f.LA.N)) return false; try {
      cross(paramRay2f); } catch (Exception localException) {
      return false;
    }return true;
  }

  public final boolean equals(Ray2f paramRay2f)
  {
    return (this.LA.equals(paramRay2f.LA)) && (this.LN.C == paramRay2f.LN.C);
  }

  public final boolean parallel(Ray2f paramRay2f)
  {
    return this.LA.N.equals(paramRay2f.LA.N);
  }

  public final float cos(Ray2f paramRay2f)
  {
    return this.LA.N.dot(paramRay2f.LA.N);
  }

  public String toString()
  {
    try
    {
      return "( " + this.LN.N.jdField_x_of_type_Float + "," + this.LN.N.jdField_y_of_type_Float + "," + this.LA.cross(this.LN) + " )"; } catch (Exception localException) {
    }
    return localException.toString();
  }

  public static void main(String[] paramArrayOfString) throws JGPException
  {
    Point2f localPoint2f1 = new Point2f(0.0F, 0.0F);
    Point2f localPoint2f2 = new Point2f(0.0F, 1.0F);
    Point2f localPoint2f3 = new Point2f(1.0F, 1.0F);
    Point2f localPoint2f4 = new Point2f(1.0F, 0.0F);
    Ray2f localRay2f1 = new Ray2f(localPoint2f1, localPoint2f3);
    Ray2f localRay2f2 = new Ray2f(localPoint2f2, localPoint2f4);
    System.out.println("Ray1: " + localRay2f1);
    System.out.println("Ray2: " + localRay2f2);

    Point2f localPoint2f5 = localRay2f1.cross(localRay2f2);
    System.out.println("CrossPoint: " + localPoint2f5 + "\n");

    localRay2f2 = new Ray2f(localPoint2f1, localPoint2f4);
    System.out.println("Ray1: " + localRay2f1);
    System.out.println("Horiz: " + localRay2f2);
    localPoint2f5 = localRay2f1.cross(localRay2f2);
    System.out.println("CrossPoint: " + localPoint2f5);
    System.out.println("Cos: " + localRay2f1.cos(localRay2f2));
  }
}