package com.maddox.netphone;

public class GsmIO extends CodecIO
{
  protected byte[] data;
  private boolean assigned;
  private int irms = 0;

  public GsmIO()
  {
    this.assigned = false;
    this.data = new byte[33];
  }

  protected int gsmGain()
  {
    int k = 0;

    k += 6;
    int i = (this.data[k] & 0x1F) << 1;
    k++;
    i |= this.data[k] >> 7 & 0x1;
    int j = i;
    k += 6;
    i = (this.data[k] & 0x1F) << 1;
    k++;
    i |= this.data[k] >> 7 & 0x1;
    k += 6;
    j += i;
    i = (this.data[k] & 0x1F) << 1;
    k++;
    i |= this.data[k] >> 7 & 0x1;
    k += 6;
    j += i;
    i = (this.data[k] & 0x1F) << 1;
    k++;
    i |= this.data[k] >> 7 & 0x1;
    j += i;

    return j;
  }

  public int getRms()
  {
    return this.irms;
  }

  public int getMaxStreamSize()
  {
    return 4096;
  }

  public int getMaxBlockSize()
  {
    return 264;
  }

  public int getMinOutSpace()
  {
    return 240;
  }

  public void copy(CodecIO paramCodecIO)
  {
    for (int i = 0; i < 33; i++) this.data[i] = ((GsmIO)paramCodecIO).data[i];
    this.irms = ((GsmIO)paramCodecIO).irms;
    this.assigned = true;
  }

  public void reset()
  {
    this.assigned = false;
    this.irms = -32768;
  }

  public boolean isEmpty()
  {
    return !this.assigned;
  }

  public int write(BitStream paramBitStream)
  {
    for (int i = 0; i < 33; i++) paramBitStream.put(this.data[i], 8);
    return 264;
  }

  public int read(BitStream paramBitStream)
  {
    paramBitStream.errClear();
    for (int i = 0; i < 33; i++) this.data[i] = (byte)paramBitStream.get(8);
    this.irms = gsmGain();
    this.assigned = true;
    return paramBitStream.getError();
  }
}