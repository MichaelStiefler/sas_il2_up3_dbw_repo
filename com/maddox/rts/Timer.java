package com.maddox.rts;

import java.util.ArrayList;

public final class Timer
  implements MsgTimeOutListener, MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int MAX_POST = 10;
  private static MessageCache cache = null;
  private MsgTimeOut ticker;
  private boolean bRealTime;
  private ArrayList listeners;
  private ArrayList params;

  public void msgTimeOut(Object paramObject)
  {
    long l1;
    if (this.bRealTime) l1 = Time.endReal(); else
      l1 = Time.tickNext() - 1L;
    long l2;
    if (this.bRealTime) l2 = Time.currentReal(); else {
      l2 = Time.current();
    }
    int i = 0;
    while (i < this.listeners.size()) {
      if (process(i, l2, l1))
        i++;
    }
    this.ticker.post();
  }

  private boolean process(int paramInt, long paramLong1, long paramLong2) {
    Object localObject = this.listeners.get(paramInt);
    MsgTimerParam localMsgTimerParam = (MsgTimerParam)this.params.get(paramInt);
    if (localMsgTimerParam.nextTime > paramLong2) {
      return true;
    }
    if (localMsgTimerParam.bSkip) {
      while ((localMsgTimerParam.nextTime < paramLong1) && (localMsgTimerParam.nextTime + localMsgTimerParam.stepTime < paramLong1)) {
        if (localMsgTimerParam.curCount == 1)
          break;
        localMsgTimerParam.nextTime += localMsgTimerParam.stepTime;
        localMsgTimerParam.curCount -= 1;
      }
    }
    if (localMsgTimerParam.curCount != 0) {
      int i = 10;
      while ((localMsgTimerParam.curCount != 0) && (localMsgTimerParam.nextTime <= paramLong2) && (i > 0)) {
        MsgTimer localMsgTimer = (MsgTimer)cache.get();
        localMsgTimer.param = localMsgTimerParam;
        if (this.bRealTime) localMsgTimer.setFlags(64); else
          localMsgTimer.setFlags(0);
        localMsgTimer.post(localObject, localMsgTimerParam.nextTime, localMsgTimerParam.tickPos, this);
        localMsgTimerParam.nextTime += localMsgTimerParam.stepTime;
        localMsgTimerParam.curCount -= 1;
        i--;
      }
    }
    if (localMsgTimerParam.curCount == 0) {
      remove(paramInt);
      return false;
    }
    return true;
  }

  public void msgAddListener(Object paramObject1, Object paramObject2) {
    if ((paramObject1 == null) || (!(paramObject2 instanceof MsgTimerParam)))
      return;
    if (find(paramObject1, paramObject2) >= 0)
      return;
    MsgTimerParam localMsgTimerParam = (MsgTimerParam)paramObject2;
    if ((localMsgTimerParam.countPost == 0) || (localMsgTimerParam.stepTime <= 0))
      return;
    long l1;
    if (this.bRealTime) l1 = Time.endReal(); else
      l1 = Time.tickNext() - 1L;
    long l2;
    if (this.bRealTime) l2 = Time.currentReal(); else {
      l2 = Time.current();
    }
    localMsgTimerParam.curCount = localMsgTimerParam.countPost;
    localMsgTimerParam.nextTime = localMsgTimerParam.startTime;
    if (((localMsgTimerParam.bSkipBegin) || (localMsgTimerParam.bSkip)) && (localMsgTimerParam.nextTime < l2)) {
      i = (int)((l2 - localMsgTimerParam.nextTime) / localMsgTimerParam.stepTime);
      localMsgTimerParam.nextTime += i * localMsgTimerParam.stepTime;
      if (localMsgTimerParam.curCount > 0) {
        localMsgTimerParam.curCount -= i;
        if (localMsgTimerParam.curCount <= 0)
          return;
      } else {
        localMsgTimerParam.curCount -= i;
      }
    }
    int i = add(paramObject1, paramObject2);
    if (localMsgTimerParam.nextTime <= l1)
      process(i, l2, l1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2) {
    if ((paramObject1 != null) && (paramObject2 != null)) {
      int i = find(paramObject1, paramObject2);
      if (i >= 0)
        remove(i);
    }
  }

  private int find(Object paramObject1, Object paramObject2) {
    int i = 0;
    int j = this.listeners.size();
    for (; i < j; i++)
      if ((paramObject1.equals(this.listeners.get(i))) && (paramObject2.equals(this.params.get(i))))
      {
        return i;
      }
    return -1;
  }

  private int add(Object paramObject1, Object paramObject2) {
    int i = this.listeners.size();
    this.listeners.add(paramObject1);
    this.params.add(paramObject2);
    return i;
  }

  private void remove(int paramInt) {
    this.listeners.remove(paramInt);
    this.params.remove(paramInt);
  }

  protected void resetGameClear()
  {
    this.listeners.clear();
    this.params.clear();
  }

  protected void resetGameCreate() {
    this.ticker.post();
  }

  protected Timer(boolean paramBoolean, int paramInt) {
    if (cache == null)
      cache = new MessageCache(MsgTimer.class);
    this.listeners = new ArrayList();
    this.params = new ArrayList();
    this.bRealTime = paramBoolean;
    this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(paramInt);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setListener(this);
    if (this.bRealTime) this.ticker.setFlags(72); else
      this.ticker.setFlags(8);
    this.ticker.post();
  }
}