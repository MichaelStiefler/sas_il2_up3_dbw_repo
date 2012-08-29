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

    this.LN.N.x = (paramPoint2f2.x - paramPoint2f1.x);
    this.LN.N.y = (paramPoint2f2.y - paramPoint2f1.y);
    this.LN.N.normalize();
    this.LN.C = (-this.LN.N.dot(paramPoint2f1));

    this.LN2.N.x = (-this.LN.N.x);
    this.LN2.N.y = (-this.LN.N.y);
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
    float[] arrayOfFloat = { this.LA.N.x, this.LA.N.y, -this.LA.C, paramEdge2f.LA.N.x, paramEdge2f.LA.N.y, -paramEdge2f.LA.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if ((Projected(localPoint2f)) && (paramEdge2f.Projected(localPoint2f))) return localPoint2f;
    throw new JGPException("Edges not crossed");
  }

  public final Point2f cross(Line2f paramLine2f) throws JGPException
  {
    float[] arrayOfFloat = { this.LA.N.x, this.LA.N.y, -this.LA.C, paramLine2f.N.x, paramLine2f.N.y, -paramLine2f.C };

    Point2f localPoint2f = NSolvef.Solve2f(arrayOfFloat);
    if (Projected(localPoint2f)) return localPoint2f;
    throw new JGPException("Edge and Line not crossed");
  }

  public String toString()
  {
    try
    {
      return "( " + this.LN.N.x + "," + this.LN.N.y + "," + this.LA.cross(this.LN) + "," + this.LA.cross(this.LN2) + " )"; } catch (Exception localException) {
    }
    return localException.toString();
  }
}