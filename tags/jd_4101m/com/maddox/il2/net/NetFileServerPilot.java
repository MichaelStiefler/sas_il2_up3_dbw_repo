package com.maddox.il2.net;

import com.maddox.il2.engine.Config;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetObj;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.rts.net.NetFileServerDef;

public class NetFileServerPilot extends NetFileServerDef
{
  public int compressMethod()
  {
    return 2; } 
  public int compressBlockSize() { return 32768; } 
  public String primaryPath() { return "PaintSchemes/Pilots"; } 
  public String alternativePath() { return "PaintSchemes/NetCache"; }

  public void doRequest(NetFileRequest paramNetFileRequest) {
    if ((Config.cur.netSkinDownload) || ((paramNetFileRequest.owner() != null) && ((paramNetFileRequest.owner().masterChannel() instanceof NetChannelInStream)))) {
      super.doRequest(paramNetFileRequest);
    } else {
      paramNetFileRequest.setState(-2);
      paramNetFileRequest.doAnswer();
    }
  }

  public NetFileServerPilot(int paramInt) {
    super(paramInt);
  }
}