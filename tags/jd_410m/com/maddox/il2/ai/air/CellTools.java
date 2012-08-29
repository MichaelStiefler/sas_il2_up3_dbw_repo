package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import java.io.Serializable;
import java.util.ArrayList;

public class CellTools
  implements Serializable
{
  public static boolean findIntersect(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4, Point2d paramPoint2d)
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = (paramPoint3d2.y - paramPoint3d1.y) * (paramPoint3d4.x - paramPoint3d3.x) - (paramPoint3d4.y - paramPoint3d3.y) * (paramPoint3d2.x - paramPoint3d1.x);
    double d4 = (paramPoint3d3.y - paramPoint3d1.y) * (paramPoint3d2.x - paramPoint3d1.x) - (paramPoint3d2.y - paramPoint3d1.y) * (paramPoint3d3.x - paramPoint3d1.x);
    double d5 = (paramPoint3d3.y - paramPoint3d1.y) * (paramPoint3d4.x - paramPoint3d3.x) - (paramPoint3d4.y - paramPoint3d3.y) * (paramPoint3d3.x - paramPoint3d1.x);
    if (d3 != 0.0D) {
      d1 = d4 / d3;
      d2 = d5 / d3;
      if ((d1 <= 1.0D) && (d1 >= 0.0D) && (d2 >= 0.0D) && (d2 <= 1.0D)) {
        if (paramPoint2d != null) {
          paramPoint2d.x = (paramPoint3d1.x + (paramPoint3d2.x - paramPoint3d1.x) * d2);
          paramPoint2d.y = (paramPoint3d1.y + (paramPoint3d2.y - paramPoint3d1.y) * d2);
        }
        return true;
      }
      return false;
    }
    return true;
  }

  public static boolean findIntersect(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4)
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = (paramPoint3d2.y - paramPoint3d1.y) * (paramPoint3d4.x - paramPoint3d3.x) - (paramPoint3d4.y - paramPoint3d3.y) * (paramPoint3d2.x - paramPoint3d1.x);
    double d4 = (paramPoint3d3.y - paramPoint3d1.y) * (paramPoint3d2.x - paramPoint3d1.x) - (paramPoint3d2.y - paramPoint3d1.y) * (paramPoint3d3.x - paramPoint3d1.x);
    double d5 = (paramPoint3d3.y - paramPoint3d1.y) * (paramPoint3d4.x - paramPoint3d3.x) - (paramPoint3d4.y - paramPoint3d3.y) * (paramPoint3d3.x - paramPoint3d1.x);
    if (d3 != 0.0D) {
      d1 = d4 / d3;
      d2 = d5 / d3;
      return (d1 <= 1.0D) && (d1 >= 0.0D) && (d2 >= 0.0D) && (d2 <= 1.0D);
    }

    return true;
  }

  public static double intersectLineSphere(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
  {
    double d1 = paramDouble7 * paramDouble7;
    double d2 = paramDouble3 - paramDouble1;
    double d3 = paramDouble4 - paramDouble2;
    double d4 = d2 * d2 + d3 * d3;
    if (d4 < 1.0E-006D) {
      if (d1 >= (paramDouble1 - paramDouble5) * (paramDouble1 - paramDouble5) + (paramDouble2 - paramDouble6) * (paramDouble2 - paramDouble6)) return 0.0D;
      return -1.0D;
    }
    double d5 = ((paramDouble5 - paramDouble1) * d2 + (paramDouble6 - paramDouble2) * d3) / d4;
    if ((d5 >= 0.0D) && (d5 <= 1.0D)) {
      d6 = paramDouble1 + d5 * d2;
      d7 = paramDouble2 + d5 * d3;

      double d8 = (d6 - paramDouble5) * (d6 - paramDouble5) + (d7 - paramDouble6) * (d7 - paramDouble6);
      double d9 = d1 - d8;
      if (d9 < 0.0D)
        return -1.0D;
      d5 -= Math.sqrt(d9 / d4);
      if (d5 < 0.0D) d5 = 0.0D;
      return d5;
    }
    double d6 = (paramDouble3 - paramDouble5) * (paramDouble3 - paramDouble5) + (paramDouble4 - paramDouble6) * (paramDouble4 - paramDouble6);
    double d7 = (paramDouble1 - paramDouble5) * (paramDouble1 - paramDouble5) + (paramDouble2 - paramDouble6) * (paramDouble2 - paramDouble6);
    if ((d6 <= d1) || (d7 <= d1)) {
      if (d6 < d7) return 1.0D;
      return 0.0D;
    }
    return -1.0D;
  }

  public static boolean belongsToPoly(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4)
  {
    int i = 0; int j = 0; int k = 0;

    double d1 = (paramPoint3d4.x - paramPoint3d1.x) * (paramPoint3d2.y - paramPoint3d1.y) / (paramPoint3d2.x - paramPoint3d1.x) + paramPoint3d1.y - paramPoint3d4.y;
    double d2 = (paramPoint3d3.x - paramPoint3d1.x) * (paramPoint3d2.y - paramPoint3d1.y) / (paramPoint3d2.x - paramPoint3d1.x) + paramPoint3d1.y - paramPoint3d3.y;
    if (d1 * d2 >= 0.0D) i = 1;

    d1 = (paramPoint3d4.x - paramPoint3d2.x) * (paramPoint3d3.y - paramPoint3d2.y) / (paramPoint3d3.x - paramPoint3d2.x) + paramPoint3d2.y - paramPoint3d4.y;
    d2 = (paramPoint3d1.x - paramPoint3d2.x) * (paramPoint3d3.y - paramPoint3d2.y) / (paramPoint3d3.x - paramPoint3d2.x) + paramPoint3d2.y - paramPoint3d1.y;
    if (d1 * d2 >= 0.0D) j = 1;

    d1 = (paramPoint3d4.x - paramPoint3d3.x) * (paramPoint3d1.y - paramPoint3d3.y) / (paramPoint3d1.x - paramPoint3d3.x) + paramPoint3d3.y - paramPoint3d4.y;
    d2 = (paramPoint3d2.x - paramPoint3d3.x) * (paramPoint3d1.y - paramPoint3d3.y) / (paramPoint3d1.x - paramPoint3d3.x) + paramPoint3d3.y - paramPoint3d2.y;
    if (d1 * d2 >= 0.0D) k = 1;

    return (i == 1) && (j == 1) && (k == 1);
  }

  public static boolean belongsToQuad(Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4, Point3d paramPoint3d5)
  {
    int i = 0; int j = 0; int k = 0; int m = 0;

    double d1 = (paramPoint3d5.x - paramPoint3d1.x) * (paramPoint3d2.y - paramPoint3d1.y) / (paramPoint3d2.x - paramPoint3d1.x) + paramPoint3d1.y - paramPoint3d5.y;
    double d2 = (paramPoint3d3.x - paramPoint3d1.x) * (paramPoint3d2.y - paramPoint3d1.y) / (paramPoint3d2.x - paramPoint3d1.x) + paramPoint3d1.y - paramPoint3d3.y;
    if (d1 * d2 >= 0.0D) i = 1;

    d1 = (paramPoint3d5.x - paramPoint3d2.x) * (paramPoint3d3.y - paramPoint3d2.y) / (paramPoint3d3.x - paramPoint3d2.x) + paramPoint3d2.y - paramPoint3d5.y;
    d2 = (paramPoint3d1.x - paramPoint3d2.x) * (paramPoint3d3.y - paramPoint3d2.y) / (paramPoint3d3.x - paramPoint3d2.x) + paramPoint3d2.y - paramPoint3d1.y;
    if (d1 * d2 >= 0.0D) j = 1;

    d1 = (paramPoint3d5.x - paramPoint3d3.x) * (paramPoint3d4.y - paramPoint3d3.y) / (paramPoint3d4.x - paramPoint3d3.x) + paramPoint3d3.y - paramPoint3d5.y;
    d2 = (paramPoint3d1.x - paramPoint3d3.x) * (paramPoint3d4.y - paramPoint3d3.y) / (paramPoint3d4.x - paramPoint3d3.x) + paramPoint3d3.y - paramPoint3d1.y;
    if (d1 * d2 >= 0.0D) k = 1;

    d1 = (paramPoint3d5.x - paramPoint3d4.x) * (paramPoint3d1.y - paramPoint3d4.y) / (paramPoint3d1.x - paramPoint3d4.x) + paramPoint3d4.y - paramPoint3d5.y;
    d2 = (paramPoint3d2.x - paramPoint3d4.x) * (paramPoint3d1.y - paramPoint3d4.y) / (paramPoint3d1.x - paramPoint3d4.x) + paramPoint3d4.y - paramPoint3d2.y;
    if (d1 * d2 >= 0.0D) m = 1;

    return (i == 1) && (j == 1) && (k == 1) && (m == 1);
  }

  public static boolean belongsToComplex(ArrayList paramArrayList, Point3d paramPoint3d)
  {
    char[] arrayOfChar = new char[paramArrayList.size()];

    for (int m = 0; m < paramArrayList.size(); m++)
    {
      int i = m;
      int j = m < paramArrayList.size() - 1 ? m + 1 : 0;
      int k = j < paramArrayList.size() - 1 ? j + 1 : 0;
      Point3d localPoint3d1 = (Point3d)paramArrayList.get(i);
      Point3d localPoint3d2 = (Point3d)paramArrayList.get(j);
      Point3d localPoint3d3 = (Point3d)paramArrayList.get(k);

      double d1 = (paramPoint3d.x - localPoint3d1.x) * (localPoint3d2.y - localPoint3d1.y) / (localPoint3d2.x - localPoint3d1.x) + localPoint3d1.y - paramPoint3d.y;
      double d2 = (localPoint3d3.x - localPoint3d1.x) * (localPoint3d2.y - localPoint3d1.y) / (localPoint3d2.x - localPoint3d1.x) + localPoint3d1.y - localPoint3d3.y;
      arrayOfChar[m] = (char)(d1 * d2 >= 0.0D ? 1 : 0);
    }

    for (m = 0; m < paramArrayList.size(); m++) {
      if (arrayOfChar[m] == 0) return false;
    }

    return true;
  }
}