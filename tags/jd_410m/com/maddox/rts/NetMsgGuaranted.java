package com.maddox.rts;

import java.io.IOException;

public class NetMsgGuaranted extends NetMsgOutput
{
  private boolean bRequiredAsk = false;

  public boolean isRequiredAsk() {
    return this.bRequiredAsk;
  }
  public void setRequiredAsk(boolean paramBoolean) {
    this.bRequiredAsk = paramBoolean;
  }

  public NetMsgGuaranted()
  {
  }

  public NetMsgGuaranted(byte[] paramArrayOfByte) {
    super(paramArrayOfByte);
  }

  public NetMsgGuaranted(int paramInt)
  {
    super(paramInt);
  }

  public NetMsgGuaranted(NetMsgInput paramNetMsgInput, int paramInt) throws IOException
  {
    super(paramNetMsgInput, paramInt);
  }
}