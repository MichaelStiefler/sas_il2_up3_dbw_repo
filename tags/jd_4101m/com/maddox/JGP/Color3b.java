package com.maddox.JGP;

import java.io.Serializable;

public class Color3b extends Tuple3b
  implements Serializable, Cloneable
{
  public Color3b(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    super(paramByte1, paramByte2, paramByte3);
  }

  public Color3b(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public Color3b(Color3b paramColor3b)
  {
    super(paramColor3b);
  }

  public Color3b(Tuple3b paramTuple3b)
  {
    super(paramTuple3b);
  }

  public Color3b()
  {
  }
}