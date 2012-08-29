package com.maddox.JGP;

import java.io.Serializable;

public class Triangle2f
  implements Serializable, Cloneable
{
  public Point2f[] P = new Point2f[3];

  public final void set(Triangle2f paramTriangle2f)
  {
    try
    {
      this.P[0].set(paramTriangle2f.P[0]); this.P[1].set(paramTriangle2f.P[1]); this.P[2].set(paramTriangle2f.P[2]);
    } catch (Exception localException) {
      this.P[0] = new Point2f(paramTriangle2f.P[0].jdField_x_of_type_Float, paramTriangle2f.P[0].jdField_y_of_type_Float);
      this.P[1] = new Point2f(paramTriangle2f.P[1].jdField_x_of_type_Float, paramTriangle2f.P[1].jdField_y_of_type_Float);
      this.P[2] = new Point2f(paramTriangle2f.P[2].jdField_x_of_type_Float, paramTriangle2f.P[2].jdField_y_of_type_Float);
    }
  }

  public final void set(Triangle3f paramTriangle3f)
  {
    try {
      this.P[0].set(paramTriangle3f.P[0]); this.P[1].set(paramTriangle3f.P[1]); this.P[2].set(paramTriangle3f.P[2]);
    } catch (Exception localException) {
      this.P[0] = new Point2f(paramTriangle3f.P[0].jdField_x_of_type_Float, paramTriangle3f.P[0].jdField_y_of_type_Float);
      this.P[1] = new Point2f(paramTriangle3f.P[1].jdField_x_of_type_Float, paramTriangle3f.P[1].jdField_y_of_type_Float);
      this.P[2] = new Point2f(paramTriangle3f.P[2].jdField_x_of_type_Float, paramTriangle3f.P[2].jdField_y_of_type_Float);
    }
  }

  public final void share(Triangle2f paramTriangle2f)
  {
    this.P[0] = paramTriangle2f.P[0]; this.P[1] = paramTriangle2f.P[1]; this.P[2] = paramTriangle2f.P[2];
  }

  public final void set(Point2f paramPoint2f1, Point2f paramPoint2f2, Point2f paramPoint2f3)
  {
    try {
      this.P[0].set(paramPoint2f1); this.P[1].set(paramPoint2f2); this.P[2].set(paramPoint2f3);
    } catch (Exception localException) {
      this.P[0] = new Point2f(paramPoint2f1);
      this.P[1] = new Point2f(paramPoint2f2);
      this.P[2] = new Point2f(paramPoint2f3);
    }
  }

  public final void share(Point2f paramPoint2f1, Point2f paramPoint2f2, Point2f paramPoint2f3)
  {
    this.P[0] = paramPoint2f1; this.P[1] = paramPoint2f2; this.P[2] = paramPoint2f3;
  }

  public final boolean normalesIN()
  {
    Point2f localPoint2f = new Point2f((this.P[0].jdField_x_of_type_Float + this.P[1].jdField_x_of_type_Float + this.P[2].jdField_x_of_type_Float) * 0.3333333F, (this.P[0].jdField_y_of_type_Float + this.P[1].jdField_y_of_type_Float + this.P[2].jdField_y_of_type_Float) * 0.3333333F);

    if (new Line2f(this.P[0], this.P[1]).deviation(localPoint2f) > 0.0F) return true;
    if (new Line2f(this.P[1], this.P[2]).deviation(localPoint2f) > 0.0F) return true;
    if (new Line2f(this.P[2], this.P[0]).deviation(localPoint2f) > 0.0F) return true;
    localPoint2f = this.P[0]; this.P[0] = this.P[1]; this.P[1] = localPoint2f;
    return false;
  }

  public String toString()
  {
    return "( " + this.P[0] + "," + this.P[1] + "," + this.P[2] + " )";
  }
}