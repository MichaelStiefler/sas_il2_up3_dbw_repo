package com.maddox.JGP;

import java.io.Serializable;

public class Triangle3f
  implements Serializable, Cloneable
{
  public Point3f[] P = new Point3f[3];

  public final void set(Triangle3f paramTriangle3f)
  {
    try
    {
      this.P[0].set(paramTriangle3f.P[0]); this.P[1].set(paramTriangle3f.P[1]); this.P[2].set(paramTriangle3f.P[2]);
    } catch (Exception localException) {
      this.P[0] = new Point3f(paramTriangle3f.P[0]);
      this.P[1] = new Point3f(paramTriangle3f.P[1]);
      this.P[2] = new Point3f(paramTriangle3f.P[2]);
    }
  }

  public final void set(Triangle2f paramTriangle2f)
  {
    try {
      this.P[0].set(paramTriangle2f.P[0].x, paramTriangle2f.P[0].y, 0.0F);
      this.P[1].set(paramTriangle2f.P[1].x, paramTriangle2f.P[1].y, 0.0F);
      this.P[2].set(paramTriangle2f.P[2].x, paramTriangle2f.P[2].y, 0.0F);
    } catch (Exception localException) {
      this.P[0] = new Point3f(paramTriangle2f.P[0].x, paramTriangle2f.P[0].y, 0.0F);
      this.P[1] = new Point3f(paramTriangle2f.P[1].x, paramTriangle2f.P[1].y, 0.0F);
      this.P[2] = new Point3f(paramTriangle2f.P[2].x, paramTriangle2f.P[2].y, 0.0F);
    }
  }

  public final void set2(Triangle2f paramTriangle2f)
  {
    try {
      this.P[0].set(paramTriangle2f.P[0].x, paramTriangle2f.P[0].y, this.P[0].z);
      this.P[1].set(paramTriangle2f.P[1].x, paramTriangle2f.P[1].y, this.P[1].z);
      this.P[2].set(paramTriangle2f.P[2].x, paramTriangle2f.P[2].y, this.P[2].z);
    } catch (Exception localException) {
      this.P[0] = new Point3f(paramTriangle2f.P[0].x, paramTriangle2f.P[0].y, 0.0F);
      this.P[1] = new Point3f(paramTriangle2f.P[1].x, paramTriangle2f.P[1].y, 0.0F);
      this.P[2] = new Point3f(paramTriangle2f.P[2].x, paramTriangle2f.P[2].y, 0.0F);
    }
  }

  public final void share(Triangle3f paramTriangle3f)
  {
    this.P[0] = paramTriangle3f.P[0]; this.P[1] = paramTriangle3f.P[1]; this.P[2] = paramTriangle3f.P[2];
  }

  public final void set(Point3f paramPoint3f1, Point3f paramPoint3f2, Point3f paramPoint3f3)
  {
    try {
      this.P[0].set(paramPoint3f1); this.P[1].set(paramPoint3f2); this.P[2].set(paramPoint3f3);
    } catch (Exception localException) {
      this.P[0] = new Point3f(paramPoint3f1);
      this.P[1] = new Point3f(paramPoint3f2);
      this.P[2] = new Point3f(paramPoint3f3);
    }
  }

  public final void share(Point3f paramPoint3f1, Point3f paramPoint3f2, Point3f paramPoint3f3)
  {
    this.P[0] = paramPoint3f1; this.P[1] = paramPoint3f2; this.P[2] = paramPoint3f3;
  }

  public String toString()
  {
    return "( " + this.P[0] + "," + this.P[1] + "," + this.P[2] + " )";
  }
}