package com.maddox.JGP;

import java.io.Serializable;

public class Color4f extends Tuple4f
  implements Serializable, Cloneable
{
  public Color4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Color4f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Color4f(Color4f paramColor4f)
  {
    super(paramColor4f);
  }

  public Color4f(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Color4f(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Color4f()
  {
  }
}