package com.maddox.rts;

public class MsgTimer extends Message
{
  public MsgTimerParam param;

  public MsgTimer()
  {
    this.param = null;
  }

  public MsgTimer(MsgTimerParam paramMsgTimerParam)
  {
    this.param = paramMsgTimerParam;
  }

  public void clean() {
    super.clean();
    this.param = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgTimerListener)) {
      int i = (int)((this.jdField__time_of_type_Long - this.param.startTime) / this.param.stepTime);
      boolean bool1 = this.jdField__time_of_type_Long + this.param.stepTime == this.param.nextTime;
      boolean bool2 = this.param.countPost == i + 1;
      ((MsgTimerListener)paramObject).msgTimer(this.param, i, bool1, bool2);
      return true;
    }
    return false;
  }
}