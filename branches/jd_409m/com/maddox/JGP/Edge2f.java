package com.maddox.JGP;

import java.io.Serializable;

public class Edge2f
  implements Serializable, Cloneable
{
  public Line2f LA = new Line2f();

  public Line2f LN = new Line2f();

  public Line2f LN2 = new Line2f();

  public Edge2f()
  {
  }

  public Edge2f(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    set(paramPoint2f1, paramPoint2f2);
  }

  public final void set(Point2f paramPoint2f1, Point2f paramPoint2f2)
  {
    this.LA.set(paramPoint2f1, paramPoint2f2);

    this.LN.N.jdField_x_of_type_Float = (paramPoint2f2.jdField_x_of_type_Float - paramPoint2f1.jdField_x_of_type_Float);
    this.LN.N.jdField_y_of_type_Float = (paramPoint2f2.jdField_y_of_type_Float - paramPoint2f1.jdField_y_of_type_Float);
    this.LN.N.normalize();
    this.LN.C = (-this.LN.N.dot(paramPoint2f1));

    this.LN2.N.jdField_x_of_type_Float = (-this.LN.N.jdField_x_of_type_Float);
    this.LN2.N.jdField_y_of_type_Float = (-this.LN.N.jdField_y_of_type_Float);
    this.LN2.C = (-this.LN.N.dot(paramPoint2f2));
  }

  public final float deviation(Point2f paramPoint2f)
  {
    return this.LA.deviation(paramPoint2f);
  }

  public final boolean Projected(Point2f paramPoint2f)
  {
    return (this.LN.deviation(paramPoint2f) >= 0.0F) && (this.LN2.deviation(paramPoint2f) >= 0.0F);
  }

  public final Point2f cross(Edge2f paramEdge2f) throws JGPException
  {
    float[] arrayOfFloat = { this.LA.N.jdField_x_of_type_Float, this.LA.N.jdField_y_of_type_Float, -this.LA.C, paramEdge2f.LA.N.jdField_x_of_type_Float, paramEdge2f.LA.N.jdField_y_of_type_Float, -paramEdge2f.LA.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if ((Projected(localPoint2f)) && (paramEdge2f.Projected(localPoint2f))) return localPoint2f;
    throw new JGPException("Edges not crossed");
  }

  public final Point2f cross(Line2f paramLine2f) throws JGPException
  {
    float[] arrayOfFloat = { this.LA.N.jdField_x_of_type_Float, this.LA.N.jdField_y_of_type_Float, -this.LA.C, paramLine2f.N.jdField_x_of_type_Float, paramLine2f.N.jdField_y_of_type_Float, -paramLine2f.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if (Projected(localPoint2f)) return localPoint2f;
    throw new JGPException("Edge and Line not crossed");
  }

  public String toString()
  {
    try
    {
      return "( " + this.LN.N.jdField_x_of_type_Float + "," + this.LN.N.jdField_y_of_type_Float + "," + this.LA.cross(this.LN) + "," + this.LA.cross(this.LN2) + " )"; } catch (Exception localException) {
    }
    return localException.toString();
  }
}