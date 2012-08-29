package com.maddox.JGP;

import java.io.Serializable;

public class TexCoord2f extends Tuple2f
  implements Serializable, Cloneable
{
  public TexCoord2f(float paramFloat1, float paramFloat2)
  {
    super(paramFloat1, paramFloat2);
  }

  public TexCoord2f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public TexCoord2f(TexCoord2f paramTexCoord2f)
  {
    super(paramTexCoord2f);
  }

  public TexCoord2f(Tuple2f paramTuple2f)
  {
    super(paramTuple2f);
  }

  public TexCoord2f()
  {
  }
}