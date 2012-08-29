package com.maddox.rts;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class KryptoOutputFilter extends FilterOutputStream
{
  private int[] key = { 255, 170 };
  private int sw = 0;

  public void kryptoResetSwitch() { this.sw = 0; } 
  public int[] kryptoGetKey() { return this.key; } 
  public void kryptoSetKey(int[] paramArrayOfInt) {
    this.key = paramArrayOfInt;
    if ((this.key != null) && (this.key.length == 0))
      this.key = null;
    this.sw = 0;
  }

  public KryptoOutputFilter(OutputStream paramOutputStream)
  {
    super(paramOutputStream);
    this.sw = 0;
  }

  public KryptoOutputFilter(OutputStream paramOutputStream, int[] paramArrayOfInt)
  {
    super(paramOutputStream);
    this.key = paramArrayOfInt;
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length == 0))
      this.key = null;
    this.sw = 0;
  }

  public void write(int paramInt)
    throws IOException
  {
    if (this.key != null) {
      this.sw = ((this.sw + 1) % this.key.length);
      this.out.write(paramInt ^ this.key[this.sw]);
    } else {
      this.out.write(paramInt);
    }
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.key != null)
      for (int i = 0; i < paramInt2; i++) {
        this.sw = ((this.sw + 1) % this.key.length);
        this.out.write(paramArrayOfByte[(paramInt1 + i)] ^ this.key[this.sw]);
      }
    else
      this.out.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}