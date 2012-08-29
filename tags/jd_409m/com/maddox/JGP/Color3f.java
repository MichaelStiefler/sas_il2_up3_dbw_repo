package com.maddox.JGP;

import java.io.Serializable;

public class Color3f extends Tuple3f
  implements Serializable, Cloneable
{
  public Color3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2, paramFloat3);
  }

  public Color3f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Color3f(Color3f paramColor3f)
  {
    super(paramColor3f);
  }

  public Color3f(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Color3f(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Color3f()
  {
  }
}