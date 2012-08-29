package com.maddox.rts;

public class MsgTimerParam
{
  public int id = 0;

  public int tickPos = 0;

  public long startTime = 0L;

  public int countPost = -1;

  public int stepTime = 100;

  public boolean bSkip = false;

  public boolean bSkipBegin = true;
  public long nextTime;
  public int curCount;

  public MsgTimerParam()
  {
  }

  public MsgTimerParam(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.id = paramInt1;
    this.tickPos = paramInt2;
    this.startTime = paramLong;
    this.countPost = paramInt3;
    this.stepTime = paramInt4;
    this.bSkip = paramBoolean1;
    this.bSkipBegin = paramBoolean2;
  }
}