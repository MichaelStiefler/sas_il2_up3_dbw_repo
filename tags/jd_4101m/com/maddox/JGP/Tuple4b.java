package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple4b
  implements Serializable, Cloneable
{
  public byte x;
  public byte y;
  public byte z;
  public byte w;

  public Tuple4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
  {
    this.x = paramByte1;
    this.y = paramByte2;
    this.z = paramByte3;
    this.w = paramByte4;
  }

  public Tuple4b(byte[] paramArrayOfByte)
  {
    this.x = paramArrayOfByte[0];
    this.y = paramArrayOfByte[1];
    this.z = paramArrayOfByte[2];
    this.w = paramArrayOfByte[3];
  }

  public Tuple4b(Tuple4b paramTuple4b)
  {
    this.x = paramTuple4b.x;
    this.y = paramTuple4b.y;
    this.z = paramTuple4b.z;
    this.w = paramTuple4b.w;
  }

  public Tuple4b()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 0;
  }

  public final void set(Tuple4b paramTuple4b)
  {
    this.x = paramTuple4b.x;
    this.y = paramTuple4b.y;
    this.z = paramTuple4b.z;
    this.w = paramTuple4b.w;
  }

  public final void set(byte[] paramArrayOfByte)
  {
    this.x = paramArrayOfByte[0];
    this.y = paramArrayOfByte[1];
    this.z = paramArrayOfByte[2];
    this.w = paramArrayOfByte[3];
  }

  public final void get(byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = this.x;
    paramArrayOfByte[1] = this.y;
    paramArrayOfByte[2] = this.z;
    paramArrayOfByte[3] = this.w;
  }

  public final void get(Tuple4b paramTuple4b)
  {
    paramTuple4b.x = this.x;
    paramTuple4b.y = this.y;
    paramTuple4b.z = this.z;
    paramTuple4b.w = this.w;
  }

  public int hashCode()
  {
    return this.x | this.y << 8 | this.z << 16 | this.w << 24;
  }

  public boolean equals(Tuple4b paramTuple4b)
  {
    return (paramTuple4b != null) && (this.x == paramTuple4b.x) && (this.y == paramTuple4b.y) && (this.z == paramTuple4b.z) && (this.w == paramTuple4b.w);
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
  }
}