package com.maddox.JGP;

import java.io.Serializable;

public class Vector3d extends Tuple3d
  implements Serializable, Cloneable
{
  public Vector3d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramDouble1, paramDouble2, paramDouble3);
  }

  public Vector3d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Vector3d(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Vector3d(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Vector3d()
  {
  }

  public final void cross(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    set(paramTuple3d1.jdField_y_of_type_Double * paramTuple3d2.jdField_z_of_type_Double - paramTuple3d1.jdField_z_of_type_Double * paramTuple3d2.jdField_y_of_type_Double, paramTuple3d1.jdField_z_of_type_Double * paramTuple3d2.jdField_x_of_type_Double - paramTuple3d1.jdField_x_of_type_Double * paramTuple3d2.jdField_z_of_type_Double, paramTuple3d1.jdField_x_of_type_Double * paramTuple3d2.jdField_y_of_type_Double - paramTuple3d1.jdField_y_of_type_Double * paramTuple3d2.jdField_x_of_type_Double);
  }

  public final double normalize(Tuple3d paramTuple3d)
  {
    set(paramTuple3d);
    return normalize();
  }

  public final double normalize()
  {
    double d = Math.max(length(), 1.0E-075D);
    this.jdField_x_of_type_Double /= d;
    this.jdField_y_of_type_Double /= d;
    this.jdField_z_of_type_Double /= d;
    return d;
  }

  public final double dot(Tuple3d paramTuple3d)
  {
    return this.jdField_x_of_type_Double * paramTuple3d.jdField_x_of_type_Double + this.jdField_y_of_type_Double * paramTuple3d.jdField_y_of_type_Double + this.jdField_z_of_type_Double * paramTuple3d.jdField_z_of_type_Double;
  }

  public final double lengthSquared()
  {
    return this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double + this.jdField_z_of_type_Double * this.jdField_z_of_type_Double;
  }

  public final double length()
  {
    return Math.sqrt(lengthSquared());
  }

  public final double angle(Vector3d paramVector3d)
  {
    double d1 = this.jdField_y_of_type_Double * paramVector3d.jdField_z_of_type_Double - this.jdField_z_of_type_Double * paramVector3d.jdField_y_of_type_Double;
    double d2 = this.jdField_z_of_type_Double * paramVector3d.jdField_x_of_type_Double - this.jdField_x_of_type_Double * paramVector3d.jdField_z_of_type_Double;
    double d3 = this.jdField_x_of_type_Double * paramVector3d.jdField_y_of_type_Double - this.jdField_y_of_type_Double * paramVector3d.jdField_x_of_type_Double;
    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

    return Math.abs(Math.atan2(d4, dot(paramVector3d)));
  }
}