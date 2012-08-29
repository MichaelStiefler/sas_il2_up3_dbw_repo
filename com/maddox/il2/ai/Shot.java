package com.maddox.il2.ai;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;

public class Shot
{
  public static final int POWER_SHELL = 0;
  public static final int POWER_CUMULATIVE = 1;
  public static final int POWER_AP = 2;
  public static final int POWER_API = 3;
  public static final int BODY_GROUND = 0;
  public static final int BODY_WATER = 1;
  public static final int BODY_METALL = 2;
  public static final int BODY_WOOD = 3;
  public static final int BODY_ROCK = 4;
  public String chunkName;
  public Point3d p = new Point3d();
  public double tickOffset;
  public Vector3d v = new Vector3d();
  public Actor initiator;
  public float power;
  public float mass;
  public int powerType;
  public int bodyMaterial;
  public Vector3f bodyNormal = new Vector3f();

  public boolean isMirage()
  {
    if (!Actor.isValid(this.initiator)) return true;
    return this.initiator.isNetMirror();
  }

  public float powerToTNT()
  {
    return this.power * 2.4E-007F;
  }

  public static float panzerThickness(Orient paramOrient, Vector3d paramVector3d, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    double d2 = paramVector3d.length();
    double d1;
    if (d2 <= 9.999999747378752E-005D) {
      d1 = paramFloat3;
    } else {
      double d3 = paramVector3d.jdField_x_of_type_Double * paramVector3d.jdField_x_of_type_Double + paramVector3d.jdField_y_of_type_Double * paramVector3d.jdField_y_of_type_Double;
      double d4 = paramVector3d.jdField_z_of_type_Double * paramVector3d.jdField_z_of_type_Double;
      if (d4 >= d3 * 0.5D * 0.5D) {
        double d5 = Math.abs(paramVector3d.jdField_z_of_type_Double) / d2;
        d1 = (paramBoolean ? paramFloat6 : paramFloat4) / Math.sqrt(d5);
      }
      else
      {
        float f1 = Geom.RAD2DEG((float)Math.atan2(paramVector3d.jdField_y_of_type_Double, paramVector3d.jdField_x_of_type_Double));
        AnglesFork localAnglesFork = new AnglesFork(f1, paramOrient.getYaw());
        f1 = localAnglesFork.getAbsDiffDeg();

        if (f1 <= 45.0F) {
          d1 = paramFloat3;
        } else if (f1 >= 135.0F) {
          d1 = paramFloat1;
        } else {
          d1 = paramFloat2;
          f1 = 90.0F - f1;
        }
        float f2 = Geom.cosDeg(f1);
        d1 /= (float)Math.sqrt(Math.abs(f2));
      }
    }
    return (float)d1;
  }
}