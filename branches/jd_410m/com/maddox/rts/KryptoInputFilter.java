package com.maddox.rts;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class KryptoInputFilter extends FilterInputStream
{
  private int[] key = { 255, 170 };
  private int sw = 0;

  public void kryptoResetSwitch() {
    this.sw = 0; } 
  public int[] kryptoGetKey() { return this.key; } 
  public void kryptoSetKey(int[] paramArrayOfInt) {
    this.key = paramArrayOfInt;
    if ((this.key != null) && (this.key.length == 0))
      this.key = null;
    this.sw = 0;
  }

  public KryptoInputFilter(InputStream paramInputStream)
  {
    super(paramInputStream);
    this.sw = 0;
  }

  public KryptoInputFilter(InputStream paramInputStream, int[] paramArrayOfInt)
  {
    super(paramInputStream);
    this.key = paramArrayOfInt;
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length == 0))
      this.key = null;
    this.sw = 0;
  }

  public boolean markSupported()
  {
    return false;
  }

  public int read()
    throws IOException
  {
    int i = this.in.read();

    if (this.key == null)
      return i;
    this.sw = ((this.sw + 1) % this.key.length);
    if (i != -1)
      i ^= this.key[this.sw];
    return i;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);

    if ((this.key == null) || (i <= 0)) {
      return i;
    }
    int j = 0;
    for (; j < i; j++) {
      this.sw = ((this.sw + 1) % this.key.length);
      paramArrayOfByte[(paramInt1 + j)] = (byte)(paramArrayOfByte[(paramInt1 + j)] ^ this.key[this.sw]);
    }
    return i;
  }
}