package com.maddox.netphone;

public class LpcmIO extends CodecIO
{
  private boolean isVoiced = false;
  private int ipit = 0; private int irms = 0;
  private int[] irc = new int[12];
  private boolean assigned;
  protected static final int[] bittab = { 7, 5, 5, 5, 5, 5, 4, 4, 4, 4, 3, 2 };

  public LpcmIO()
  {
    this.assigned = false;
  }

  public int getRms()
  {
    return this.irms;
  }

  public int getMaxStreamSize()
  {
    return 512;
  }

  public int getMaxBlockSize()
  {
    return 55;
  }

  public int getMinOutSpace()
  {
    return 320;
  }

  public void copy(CodecIO paramCodecIO)
  {
    LpcmIO localLpcmIO = (LpcmIO)paramCodecIO;
    this.isVoiced = localLpcmIO.isVoiced;
    this.ipit = localLpcmIO.ipit;
    this.irms = localLpcmIO.irms;
    for (int i = 0; i < 12; i++) this.irc[i] = localLpcmIO.irc[i];
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
    paramBitStream.put(this.isVoiced ? 1 : 0, 1);
    int i;
    if (this.isVoiced) {
      paramBitStream.put(this.ipit, bittab[0]);
      i = 10;
    } else {
      paramBitStream.put(this.ipit, 2);
      i = 4;
    }

    paramBitStream.put(this.irms, bittab[1]);

    for (int j = 0; j < i; j++) {
      paramBitStream.put(this.irc[j] & 0x7FFF, bittab[(j + 2)]);
    }

    return 54;
  }

  public int read(BitStream paramBitStream)
  {
    paramBitStream.errClear();

    this.isVoiced = (paramBitStream.get(1) != 0);

    if (this.isVoiced) this.ipit = paramBitStream.get(bittab[0]); else {
      this.ipit = paramBitStream.get(2);
    }
    this.irms = paramBitStream.get(bittab[1]);

    int i = this.isVoiced ? 10 : 4;
    int k;
    for (int j = 0; j < i; j++) {
      k = paramBitStream.get(bittab[(j + 2)]);
      int m = 1 << bittab[(j + 2)] - 1;
      if ((k & m) != 0) k -= (m << 1);
      this.irc[j] = k;
    }

    if (!this.isVoiced) {
      for (k = 4; k < 10; k++) this.irc[k] = 0;
    }

    this.assigned = true;

    return paramBitStream.getError();
  }
}