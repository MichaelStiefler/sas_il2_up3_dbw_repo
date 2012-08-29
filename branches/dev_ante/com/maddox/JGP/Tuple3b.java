package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple3b
  implements Serializable, Cloneable
{
  public byte x;
  public byte y;
  public byte z;

  public Tuple3b(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    this.x = paramByte1;
    this.y = paramByte2;
    this.z = paramByte3;
  }

  public Tuple3b(byte[] paramArrayOfByte)
  {
    this.x = paramArrayOfByte[0];
    this.y = paramArrayOfByte[1];
    this.z = paramArrayOfByte[2];
  }

  public Tuple3b(Tuple3b paramTuple3b)
  {
    this.x = paramTuple3b.x;
    this.y = paramTuple3b.y;
    this.z = paramTuple3b.z;
  }

  public Tuple3b()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  public final void set(Tuple3b paramTuple3b)
  {
    this.x = paramTuple3b.x;
    this.y = paramTuple3b.y;
    this.z = paramTuple3b.z;
  }

  public final void set(byte[] paramArrayOfByte)
  {
    this.x = paramArrayOfByte[0];
    this.y = paramArrayOfByte[1];
    this.z = paramArrayOfByte[2];
  }

  public final void get(byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = this.x;
    paramArrayOfByte[1] = this.y;
    paramArrayOfByte[2] = this.z;
  }

  public final void get(Tuple3b paramTuple3b)
  {
    paramTuple3b.x = this.x;
    paramTuple3b.y = this.y;
    paramTuple3b.z = this.z;
  }

  public int hashCode()
  {
    return this.x | this.y << 8 | this.z << 16;
  }

  public boolean equals(Tuple3b paramTuple3b)
  {
    return (paramTuple3b != null) && (this.x == paramTuple3b.x) && (this.y == paramTuple3b.y) && (this.z == paramTuple3b.z);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Tuple3b)) && (equals((Tuple3b)paramObject));
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ", " + this.z + ")";
  }
}