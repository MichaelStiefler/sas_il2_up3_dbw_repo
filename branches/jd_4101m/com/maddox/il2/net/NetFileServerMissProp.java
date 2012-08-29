package com.maddox.il2.net;

import com.maddox.rts.net.NetFileServerDef;

public class NetFileServerMissProp extends NetFileServerDef
{
  public int compressMethod()
  {
    return 2; } 
  public int compressBlockSize() { return 32768; } 
  public String primaryPath() { return "missions"; } 
  public String alternativePath() { return "missions/Net/Cache"; }

  public NetFileServerMissProp(int paramInt) {
    super(paramInt);
  }
}