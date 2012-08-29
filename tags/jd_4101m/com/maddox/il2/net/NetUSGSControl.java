package com.maddox.il2.net;

import com.maddox.rts.NetControl;
import com.maddox.rts.NetControlReal;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetObj;
import java.util.List;

public class NetUSGSControl
  implements NetControlReal
{
  private NetControl control;

  public void msgNewClient(NetObj paramNetObj, int paramInt1, String paramString, int paramInt2)
  {
    this.control.doRequest(paramNetObj, paramInt1, "NM");
  }

  public void msgAnswer(NetObj paramNetObj, int paramInt, String paramString)
  {
    if (paramString.indexOf("NM") >= 0) {
      int i = paramString.indexOf('"', paramString.indexOf("NM"));
      int j = paramString.lastIndexOf('"');
      if ((i >= 0) && (i + 1 < j)) {
        String str = paramString.substring(i + 1, j);
        if (str.equals(NetEnv.host().shortName())) {
          this.control.doNak(paramNetObj, paramInt, "user alredy connected");
          return;
        }
        List localList = NetEnv.hosts();
        for (int k = 0; k < localList.size(); k++) {
          if (str.equals(((NetHost)localList.get(k)).shortName())) {
            this.control.doNak(paramNetObj, paramInt, "user alredy connected");
            return;
          }
        }
        this.control.doAsk(paramNetObj, paramInt);
        return;
      }
    }
    this.control.doNak(paramNetObj, paramInt, "unknown user");
  }

  public void destroy() {
    if (this.control != null) {
      this.control.destroy();
      this.control = null;
    }
  }

  public NetUSGSControl() {
    this.control = new NetControl(this);
  }
}