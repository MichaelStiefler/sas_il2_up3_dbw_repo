package com.maddox.JGP;

import java.io.Serializable;

public class Line2f
  implements Serializable, Cloneable
{
  public Vector2f N = new Vector2f();
  public float C;
  private Point2f tmpP = new Point2f();

  public Line2f()
  {
    this.N.jdField_x_of_type_Float = 1.0F; this.C = 0.0F;
  }

  public Line2f(Line2f paramLine2f)
  {
    this.N.set(paramLine2f.N); this.C = paramLine2f.C;
  }

  public Line2f(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    set(paramPoint2f1, paramPoint2f2);
  }

  public final void set(Line2f paramLine2f)
  {
    this.N.set(paramLine2f.N); this.C = paramLine2f.C;
  }

  public final void set(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    this.N.jdField_x_of_type_Float = (paramPoint2f1.jdField_y_of_type_Float - paramPoint2f2.jdField_y_of_type_Float);
    this.N.jdField_y_of_type_Float = (paramPoint2f2.jdField_x_of_type_Float - paramPoint2f1.jdField_x_of_type_Float);
    this.N.normalize();
    this.C = (-this.N.dot(paramPoint2f1));
  }

  public final void set(Point2f paramPoint2f, Vector2f paramVector2f)
  {
    this.N.jdField_x_of_type_Float = paramVector2f.jdField_x_of_type_Float; this.N.jdField_y_of_type_Float = paramVector2f.jdField_y_of_type_Float;
    this.N.normalize();
    this.C = (-this.N.dot(paramPoint2f));
  }

  public final float deviation(Point2f paramPoint2f)
  {
    return this.N.dot(paramPoint2f) + this.C;
  }

  public final float distance(Point2f paramPoint2f)
  {
    return Math.abs(this.N.dot(paramPoint2f) + this.C);
  }

  public final void mirror(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    this.tmpP.scale(-2.0F * deviation(paramPoint2f1), this.N);
    paramPoint2f2.add(paramPoint2f1, this.tmpP);
  }

  public final void mirror(Point2f paramPoint2f)
  {
    this.tmpP.scale(-2.0F * deviation(paramPoint2f), this.N);
    paramPoint2f.add(this.tmpP);
  }

  public final void mirror(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    this.tmpP.set(paramPoint3f1);
    this.tmpP.scale(-2.0F * deviation(this.tmpP), this.N);
    paramPoint3f2.jdField_x_of_type_Float = (paramPoint3f1.jdField_x_of_type_Float + this.tmpP.jdField_x_of_type_Float);
    paramPoint3f2.jdField_y_of_type_Float = (paramPoint3f1.jdField_y_of_type_Float + this.tmpP.jdField_y_of_type_Float);
    paramPoint3f2.jdField_z_of_type_Float = paramPoint3f1.jdField_z_of_type_Float;
  }

  public final void mirror(Point3f paramPoint3f)
  {
    this.tmpP.set(paramPoint3f);
    this.tmpP.scale(-2.0F * deviation(this.tmpP), this.N);
    paramPoint3f.jdField_x_of_type_Float += this.tmpP.jdField_x_of_type_Float;
    paramPoint3f.jdField_y_of_type_Float += this.tmpP.jdField_y_of_type_Float;
  }

  public final void project(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    this.tmpP.scale(-deviation(paramPoint2f1), this.N);
    paramPoint2f2.add(paramPoint2f1, this.tmpP);
  }

  public final void project(Point2f paramPoint2f)
  {
    this.tmpP.scale(-deviation(paramPoint2f), this.N);
    paramPoint2f.add(this.tmpP);
  }

  public final float distance(Line2f paramLine2f)
  {
    if (!this.N.equals(paramLine2f.N)) return 0.0F;
    return Math.abs(this.C - paramLine2f.C);
  }

  public final Point2f cross(Line2f paramLine2f) throws JGPException
  {
    float[] arrayOfFloat = { this.N.jdField_x_of_type_Float, this.N.jdField_y_of_type_Float, -this.C, paramLine2f.N.jdField_x_of_type_Float, paramLine2f.N.jdField_y_of_type_Float, -paramLine2f.C };

    return NSolvef.Solve2f(arrayOfFloat);
  }

  public final void crossXc(float paramFloat, Point2f paramPoint2f)
  {
    paramPoint2f.jdField_x_of_type_Float = paramFloat;
    float f = (-this.N.jdField_x_of_type_Float * paramFloat - this.C) / this.N.jdField_y_of_type_Float;
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.jdField_y_of_type_Float = f; return; }
    paramPoint2f.jdField_y_of_type_Float = 1.0E+036F;
  }

  public final void crossYc(float paramFloat, Point2f paramPoint2f)
  {
    paramPoint2f.jdField_y_of_type_Float = paramFloat;
    float f = (-this.N.jdField_y_of_type_Float * paramFloat - this.C) / this.N.jdField_x_of_type_Float;
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.jdField_x_of_type_Float = f; return; }
    paramPoint2f.jdField_x_of_type_Float = 1.0E+036F;
  }

  public final void crossXsubYc(float paramFloat, Point2f paramPoint2f)
  {
    float f = (-this.N.jdField_y_of_type_Float * paramFloat - this.C) / (this.N.jdField_x_of_type_Float + this.N.jdField_y_of_type_Float);
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.jdField_x_of_type_Float = f; paramPoint2f.jdField_y_of_type_Float = (f + paramFloat); return; }
    paramPoint2f.jdField_x_of_type_Float = (paramPoint2f.jdField_y_of_type_Float = 1.0E+036F);
  }

  public final void crossXaddYc(float paramFloat, Point2f paramPoint2f)
  {
    float f = (this.N.jdField_y_of_type_Float * paramFloat + this.C) / (this.N.jdField_y_of_type_Float - this.N.jdField_x_of_type_Float);
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.jdField_x_of_type_Float = f; paramPoint2f.jdField_y_of_type_Float = (paramFloat - f); return; }
    paramPoint2f.jdField_x_of_type_Float = (paramPoint2f.jdField_y_of_type_Float = 1.0E+036F);
  }

  public final boolean crossed(Line2f paramLine2f)
  {
    return !this.N.equals(paramLine2f.N);
  }

  public final boolean equals(Line2f paramLine2f)
  {
    return (this.N.equals(paramLine2f.N)) && (this.C == paramLine2f.C);
  }

  public final boolean parallel(Line2f paramLine2f)
  {
    return this.N.equals(paramLine2f.N);
  }

  public final float cos(Line2f paramLine2f)
  {
    return this.N.dot(paramLine2f.N);
  }

  public String toString()
  {
    return "( " + this.N.jdField_x_of_type_Float + "," + this.N.jdField_y_of_type_Float + "; " + this.C + " )";
  }
}