package com.maddox.rts;

public class NetMsgDestroy extends NetMsgGuaranted
{
  public NetMsgDestroy(NetObj paramNetObj)
  {
    super(null);
    try { writeNetObj(paramNetObj);
    }
    catch (Exception localException)
    {
    }
  }
}