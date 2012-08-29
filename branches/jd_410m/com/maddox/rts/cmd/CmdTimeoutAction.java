package com.maddox.rts.cmd;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

class CmdTimeoutAction extends Message
{
  CmdEnv env;
  String cmd;

  CmdTimeoutAction(CmdEnv paramCmdEnv, String paramString, long paramLong)
  {
    this.env = paramCmdEnv;
    this.cmd = paramString;
    post(64, this, Time.currentReal() + paramLong);
  }
  public boolean invokeListener(Object paramObject) {
    this.env.exec(this.cmd);
    return true;
  }
}