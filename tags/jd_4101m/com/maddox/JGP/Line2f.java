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
    this.N.x = 1.0F; this.C = 0.0F;
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
    this.N.x = (paramPoint2f1.y - paramPoint2f2.y);
    this.N.y = (paramPoint2f2.x - paramPoint2f1.x);
    this.N.normalize();
    this.C = (-this.N.dot(paramPoint2f1));
  }

  public final void set(Point2f paramPoint2f, Vector2f paramVector2f)
  {
    this.N.x = paramVector2f.x; this.N.y = paramVector2f.y;
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
    paramPoint3f1.x += this.tmpP.x;
    paramPoint3f1.y += this.tmpP.y;
    paramPoint3f2.z = paramPoint3f1.z;
  }

  public final void mirror(Point3f paramPoint3f)
  {
    this.tmpP.set(paramPoint3f);
    this.tmpP.scale(-2.0F * deviation(this.tmpP), this.N);
    paramPoint3f.x += this.tmpP.x;
    paramPoint3f.y += this.tmpP.y;
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
    float[] arrayOfFloat = { this.N.x, this.N.y, -this.C, paramLine2f.N.x, paramLine2f.N.y, -paramLine2f.C };

    return NSolvef.Solve2f(arrayOfFloat);
  }

  public final void crossXc(float paramFloat, Point2f paramPoint2f)
  {
    paramPoint2f.x = paramFloat;
    float f = (-this.N.x * paramFloat - this.C) / this.N.y;
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.y = f; return; }
    paramPoint2f.y = 1.0E+036F;
  }

  public final void crossYc(float paramFloat, Point2f paramPoint2f)
  {
    paramPoint2f.y = paramFloat;
    float f = (-this.N.y * paramFloat - this.C) / this.N.x;
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.x = f; return; }
    paramPoint2f.x = 1.0E+036F;
  }

  public final void crossXsubYc(float paramFloat, Point2f paramPoint2f)
  {
    float f = (-this.N.y * paramFloat - this.C) / (this.N.x + this.N.y);
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.x = f; paramPoint2f.y = (f + paramFloat); return; }
    paramPoint2f.x = (paramPoint2f.y = 1.0E+036F);
  }

  public final void crossXaddYc(float paramFloat, Point2f paramPoint2f)
  {
    float f = (this.N.y * paramFloat + this.C) / (this.N.y - this.N.x);
    if (Math.abs(f) < 1.0E+036F) { paramPoint2f.x = f; paramPoint2f.y = (paramFloat - f); return; }
    paramPoint2f.x = (paramPoint2f.y = 1.0E+036F);
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
    return "( " + this.N.x + "," + this.N.y + "; " + this.C + " )";
  }
}