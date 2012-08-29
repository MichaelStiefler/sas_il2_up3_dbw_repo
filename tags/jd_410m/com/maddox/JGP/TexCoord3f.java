package com.maddox.JGP;

import java.io.Serializable;

public class TexCoord3f extends Tuple3f
  implements Serializable, Cloneable
{
  public TexCoord3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2, paramFloat3);
  }

  public TexCoord3f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public TexCoord3f(TexCoord3f paramTexCoord3f)
  {
    super(paramTexCoord3f);
  }

  public TexCoord3f()
  {
  }
}