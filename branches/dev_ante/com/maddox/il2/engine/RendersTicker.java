package com.maddox.il2.engine;

import com.maddox.rts.MessageQueue;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

class RendersTicker
  implements MsgTimeOutListener
{
  private Renders renders;
  private MsgTimeOut msgTimeOut;

  public void msgTimeOut(Object paramObject)
  {
    this.msgTimeOut.post();
    if ((Time.isPaused()) || (Time.tickOffset() != 0.0F))
      if (this.renders.maxFps <= 0.0F) {
        this.renders.paint();
        this.renders.prevTimePaint = Time.real();
      } else {
        long l = Time.real();
        if (l >= this.renders.prevTimePaint + this.renders.stepTimePaint) {
          this.renders.paint();
          this.renders.prevTimePaint = l;
        }
      }
  }

  public void destroy()
  {
    if (this.msgTimeOut != null) {
      RTSConf.cur.queueRealTime.remove(this.msgTimeOut);
      RTSConf.cur.queueRealTimeNextTick.remove(this.msgTimeOut);
      this.msgTimeOut = null;
    }
  }

  public RendersTicker(Renders paramRenders) {
    this.renders = paramRenders;
    this.msgTimeOut = new MsgTimeOut();
    this.msgTimeOut.setListener(this);
    this.msgTimeOut.setNotCleanAfterSend();
    this.msgTimeOut.setFlags(104);
    this.msgTimeOut.setTickPos(2147483647);
    this.msgTimeOut.post();
  }
}