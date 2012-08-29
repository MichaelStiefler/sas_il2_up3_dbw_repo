package com.maddox.JGP;

import java.io.Serializable;

public class Color4b extends Tuple4b
  implements Serializable, Cloneable
{
  public Color4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
  {
    super(paramByte1, paramByte2, paramByte3, paramByte4);
  }

  public Color4b(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public Color4b(Color4b paramColor4b)
  {
    super(paramColor4b);
  }

  public Color4b(Tuple4b paramTuple4b)
  {
    super(paramTuple4b);
  }

  public Color4b()
  {
  }
}