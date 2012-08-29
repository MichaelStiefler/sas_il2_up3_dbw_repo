package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;

public class Locator extends Loc
{
  public Locator()
  {
    this.P = new Point3d();
    this.O = new Orientation();
  }

  public Locator(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3) {
    this();
    set(paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2, paramFloat3);
  }

  public Locator(Loc paramLoc) {
    this();
    set(paramLoc);
  }

  public Locator(Tuple3d paramTuple3d, Orient paramOrient) {
    this();
    set(paramTuple3d, paramOrient);
  }

  public Locator(Tuple3d paramTuple3d) {
    this();
    set(paramTuple3d);
  }

  public Locator(Orient paramOrient) {
    this();
    set(paramOrient);
  }

  public Locator(double[] paramArrayOfDouble) {
    this();
    set(paramArrayOfDouble);
  }
}