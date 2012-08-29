package com.maddox.rts;

import java.io.IOException;
import java.util.List;

public class NetMsgFiltered extends NetMsgOutput
{
  protected long _time;
  protected long _timeStamp;
  protected float _prior;
  private boolean bIncludeTime = false;

  protected float prior = 0.5F;
  private Object filterArg;

  public void setFilterArg(Object paramObject)
    throws IOException
  {
    checkLock();
    this.filterArg = paramObject;
  }

  public Object filterArg() {
    return this.filterArg;
  }

  public void setIncludeTime(boolean paramBoolean)
    throws IOException
  {
    checkLock();
    this.bIncludeTime = paramBoolean;
  }

  public boolean isIncludeTime()
  {
    return this.bIncludeTime;
  }

  public void setPrior(float paramFloat) throws IOException
  {
    checkLock();
    if (paramFloat < 0.0F) paramFloat = 0.0F;
    if (paramFloat > 1.0F) paramFloat = 1.0F;
    this.prior = paramFloat;
  }

  public float prior() {
    return this.prior;
  }
  public float lastDinamicPrior() { return this._prior;
  }

  public void unLock()
  {
    if (!isLocked()) return;
    NetEnv.cur(); List localList = NetEnv.channels();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetChannel localNetChannel = (NetChannel)localList.get(j);
      if (localNetChannel.unLockMessage(this))
        return;
    }
  }

  public void unLock(NetChannel paramNetChannel)
  {
    if (!isLocked()) return;
    if ((paramNetChannel != null) && 
      (paramNetChannel.unLockMessage(this))) {
      return;
    }
    unLock();
  }

  public void unLockAndClear()
    throws IOException
  {
    unLock();
    clear();
  }

  public void unLockAndSet(NetMsgInput paramNetMsgInput, int paramInt)
    throws IOException
  {
    unLock();
    clear();
    writeMsg(paramNetMsgInput, paramInt);
  }

  public NetMsgFiltered()
  {
  }

  public NetMsgFiltered(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public NetMsgFiltered(int paramInt)
  {
    super(paramInt);
  }

  public NetMsgFiltered(NetMsgInput paramNetMsgInput, int paramInt) throws IOException
  {
    super(paramNetMsgInput, paramInt);
  }
}