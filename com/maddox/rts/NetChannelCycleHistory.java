package com.maddox.rts;

class NetChannelCycleHistory
{
  public int[] seq;
  public long[] time;
  public int head;
  public int tail;

  private void expand()
  {
    int[] arrayOfInt = this.seq;
    long[] arrayOfLong = this.time;
    int i = this.seq.length - 1;
    this.seq = new int[arrayOfInt.length * 2];
    this.time = new long[arrayOfInt.length * 2];
    int j = this.tail;
    this.head = (this.tail = 0);
    while (i-- >= 0) {
      int k = this.head++ & this.seq.length - 1;
      int m = j++ & arrayOfInt.length - 1;
      this.seq[k] = arrayOfInt[m];
      this.time[k] = arrayOfLong[m];
    }
  }

  public double speed(long paramLong1, long paramLong2, double paramDouble) {
    if (this.head == this.tail) return paramDouble;
    int i = this.head;
    long l = paramLong2;
    int j = 0;
    int k = 1;
    do {
      if (this.time[(i & this.seq.length - 1)] < paramLong1) k = 0;
      l = this.time[(i & this.seq.length - 1)];
      j += (this.seq[(i & this.seq.length - 1)] >> 16);

      i--; } while ((i >= this.tail) && (k != 0));

    l = paramLong2 - l;
    if (l <= 0L) return paramDouble;
    return j / l;
  }

  public void put(int paramInt1, int paramInt2, long paramLong) {
    if (this.head - this.tail == this.seq.length - 1) expand();
    int i = this.head++ & this.seq.length - 1;
    this.seq[i] = (paramInt1 & 0x3FFF | (paramInt2 & 0xFFFF) << 16);
    this.time[i] = paramLong;
  }

  public int getIndex(int paramInt) {
    paramInt &= 16383;
    int i = this.head;
    do {
      if ((this.seq[(i & this.seq.length - 1)] & 0x3FFF) == paramInt) {
        this.tail = i;
        return i & this.seq.length - 1;
      }
      i--; } while (i >= this.tail);

    return -1;
  }
  public long getTime(int paramInt) {
    return this.time[paramInt];
  }

  public NetChannelCycleHistory(int paramInt)
  {
    this.seq = new int[paramInt];
    this.time = new long[paramInt];
  }
}