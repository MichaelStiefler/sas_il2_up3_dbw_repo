package com.maddox.JGP;

import java.io.Serializable;

public class Line2d
  implements Serializable, Cloneable
{
  public Vector2d N = new Vector2d();
  public double C;
  private Point2d tmpP = new Point2d();

  public Line2d()
  {
    this.N.x = 1.0D; this.C = 0.0D;
  }

  public Line2d(Line2d paramLine2d)
  {
    this.N.set(paramLine2d.N); this.C = paramLine2d.C;
  }

  public Line2d(Point2d paramPoint2d1, Point2d paramPoint2d2)
  {
    set(paramPoint2d1, paramPoint2d2);
  }

  public final void set(Line2d paramLine2d)
  {
    this.N.set(paramLine2d.N); this.C = paramLine2d.C;
  }

  public final void set(Point2d paramPoint2d1, Point2d paramPoint2d2)
  {
    this.N.x = (paramPoint2d1.y - paramPoint2d2.y);
    this.N.y = (paramPoint2d2.x - paramPoint2d1.x);
    this.N.normalize();
    this.C = (-this.N.dot(paramPoint2d1));
  }

  public final void set(Point2d paramPoint2d, Vector2d paramVector2d)
  {
    this.N.x = paramVector2d.x; this.N.y = paramVector2d.y;
    this.N.normalize();
    this.C = (-this.N.dot(paramPoint2d));
  }

  public final double deviation(Point2d paramPoint2d)
  {
    return this.N.dot(paramPoint2d) + this.C;
  }

  public final double distance(Point2d paramPoint2d)
  {
    return Math.abs(this.N.dot(paramPoint2d) + this.C);
  }

  public final void mirror(Point2d paramPoint2d1, Point2d paramPoint2d2)
  {
    this.tmpP.scale(-2.0D * deviation(paramPoint2d1), this.N);
    paramPoint2d2.add(paramPoint2d1, this.tmpP);
  }

  public final void mirror(Point2d paramPoint2d)
  {
    this.tmpP.scale(-2.0D * deviation(paramPoint2d), this.N);
    paramPoint2d.add(this.tmpP);
  }

  public final void mirror(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    this.tmpP.set(paramPoint3d1);
    this.tmpP.scale(-2.0D * deviation(this.tmpP), this.N);
    paramPoint3d1.x += this.tmpP.x;
    paramPoint3d1.y += this.tmpP.y;
    paramPoint3d2.z = paramPoint3d1.z;
  }

  public final void mirror(Point3d paramPoint3d)
  {
    this.tmpP.set(paramPoint3d);
    this.tmpP.scale(-2.0D * deviation(this.tmpP), this.N);
    paramPoint3d.x += this.tmpP.x;
    paramPoint3d.y += this.tmpP.y;
  }

  public final void project(Point2d paramPoint2d1, Point2d paramPoint2d2)
  {
    this.tmpP.scale(-deviation(paramPoint2d1), this.N);
    paramPoint2d2.add(paramPoint2d1, this.tmpP);
  }

  public final void project(Point2d paramPoint2d)
  {
    this.tmpP.scale(-deviation(paramPoint2d), this.N);
    paramPoint2d.add(this.tmpP);
  }

  public final double distance(Line2d paramLine2d)
  {
    if (!this.N.equals(paramLine2d.N)) return 0.0D;
    return Math.abs(this.C - paramLine2d.C);
  }

  public final Point2f cross(Line2d paramLine2d) throws JGPException
  {
    float[] arrayOfFloat = { (float)this.N.x, (float)this.N.y, (float)(-this.C), (float)paramLine2d.N.x, (float)paramLine2d.N.y, (float)(-paramLine2d.C) };

    return NSolvef.Solve2f(arrayOfFloat);
  }

  public final Point2d crossPRE(Line2d paramLine2d) throws JGPException
  {
    double[] arrayOfDouble = { this.N.x, this.N.y, -this.C, paramLine2d.N.x, paramLine2d.N.y, -paramLine2d.C };

    return NSolved.Solve2d(arrayOfDouble);
  }

  public final void crossXc(double paramDouble, Point2d paramPoint2d)
  {
    paramPoint2d.x = paramDouble;
    double d = (-this.N.x * paramDouble - this.C) / this.N.y;
    if (Math.abs(d) < 9.999999616903163E+035D) { paramPoint2d.y = d; return; }
    paramPoint2d.y = 9.999999616903163E+035D;
  }

  public final void crossYc(double paramDouble, Point2d paramPoint2d)
  {
    paramPoint2d.y = paramDouble;
    double d = (-this.N.y * paramDouble - this.C) / this.N.x;
    if (Math.abs(d) < 9.999999616903163E+035D) { paramPoint2d.x = d; return; }
    paramPoint2d.x = 9.999999616903163E+035D;
  }

  public final void crossXsubYc(double paramDouble, Point2d paramPoint2d)
  {
    double d = (-this.N.y * paramDouble - this.C) / (this.N.x + this.N.y);
    if (Math.abs(d) < 9.999999616903163E+035D) { paramPoint2d.x = d; paramPoint2d.y = (d + paramDouble); return; }
    paramPoint2d.x = (paramPoint2d.y = 9.999999616903163E+035D);
  }

  public final void crossXaddYc(double paramDouble, Point2d paramPoint2d)
  {
    double d = (this.N.y * paramDouble + this.C) / (this.N.y - this.N.x);
    if (Math.abs(d) < 9.999999616903163E+035D) { paramPoint2d.x = d; paramPoint2d.y = (paramDouble - d); return; }
    paramPoint2d.x = (paramPoint2d.y = 9.999999616903163E+035D);
  }

  public final boolean crossed(Line2d paramLine2d)
  {
    return !this.N.equals(paramLine2d.N);
  }

  public final boolean equals(Line2d paramLine2d)
  {
    return (this.N.equals(paramLine2d.N)) && (this.C == paramLine2d.C);
  }

  public final boolean parallel(Line2d paramLine2d)
  {
    return this.N.equals(paramLine2d.N);
  }

  public final double cos(Line2d paramLine2d)
  {
    return this.N.dot(paramLine2d.N);
  }

  public String toString()
  {
    return "( " + this.N.x + "," + this.N.y + "; " + this.C + " )";
  }
}