package com.maddox.rts;

class MsgEvent
{
  public static final int FINGER = 0;
  public long time;
  public int id;
  public int p1;
  public int p2;
  public int p3;
  public int p4;
  public int p5;
  public int p6;
  public int p7;
  public int p8;
  public int p9;
  public String arg0 = null;
  public String arg1 = null;

  public MsgEvent() {
    this.p1 = (this.p2 = this.p3 = this.p4 = this.p5 = this.p6 = this.p7 = this.p8 = this.p9 = -1);
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = paramInt3;
    this.p3 = (this.p4 = this.p5 = this.p6 = this.p7 = this.p8 = this.p9 = -1);
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = paramInt3;
    this.p3 = paramInt4;
    this.p4 = (this.p5 = this.p6 = this.p7 = this.p8 = this.p9 = -1);
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = paramInt3;
    this.p3 = paramInt4;
    this.p4 = paramInt5;
    this.p5 = paramInt6;
    this.p6 = paramInt7;
    this.p7 = paramInt8;
    this.p8 = paramInt9;
    this.p9 = paramInt10;
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = paramInt3;
    this.p3 = paramInt4;
    this.p4 = paramInt5;
    this.p5 = paramInt6;
    this.p6 = paramInt7;
    this.p7 = paramInt8;
    this.p8 = paramInt9;
    this.p9 = -1;
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = paramInt3;
    this.p3 = paramInt4;
    this.p4 = paramInt5;
    this.p5 = (this.p6 = this.p7 = this.p8 = this.p9 = -1);
  }

  public MsgEvent(long paramLong, int paramInt1, int paramInt2) {
    this.time = paramLong;
    this.id = paramInt1;
    this.p1 = paramInt2;
    this.p2 = 0;
    this.p3 = (this.p4 = this.p5 = this.p6 = this.p7 = this.p8 = this.p9 = -1);
  }
  public MsgEvent(long paramLong, int paramInt1, int[] paramArrayOfInt, int paramInt2) {
    this.time = paramLong;
    this.p1 = (this.p2 = this.p3 = this.p4 = this.p5 = this.p6 = this.p7 = this.p8 = this.p9 = -1);
    this.id = paramInt1; if (paramInt2 <= 0) return;
    this.p1 = paramArrayOfInt[0]; if (paramInt2 <= 1) return;
    this.p2 = paramArrayOfInt[1]; if (paramInt2 <= 2) return;
    this.p3 = paramArrayOfInt[2]; if (paramInt2 <= 3) return;
    this.p4 = paramArrayOfInt[3]; if (paramInt2 <= 4) return;
    this.p5 = paramArrayOfInt[4]; if (paramInt2 <= 5) return;
    this.p6 = paramArrayOfInt[5]; if (paramInt2 <= 6) return;
    this.p7 = paramArrayOfInt[6]; if (paramInt2 <= 7) return;
    this.p8 = paramArrayOfInt[7]; if (paramInt2 <= 8) return;
    this.p9 = paramArrayOfInt[8];
  }
}