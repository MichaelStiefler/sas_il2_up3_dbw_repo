package com.maddox.netphone;

import java.util.Vector;

public class HBitStr extends MixBase
{
  protected BitStream bs;
  protected MixChannel mc;
  protected MixChannel pOut;

  public HBitStr(MixChannel paramMixChannel1, MixChannel paramMixChannel2)
  {
    this.mc = paramMixChannel1;
    this.pOut = paramMixChannel2;

    Vector localVector = this.mc.inList;

    if (localVector.isEmpty()) {
      this.bs = new BitStream(paramMixChannel1.rd.getMaxStreamSize());
    } else {
      HBitStr localHBitStr = (HBitStr)localVector.firstElement();
      this.bs = new BitStream(localHBitStr.bs);
    }

    localVector.add(this);
    this.pOut.outList.add(this);
  }

  protected void destroy()
  {
    if (this.mc != null) {
      this.mc.inList.remove(this);
      this.pOut.outList.remove(this);
      this.bs.destroy();
      this.bs = null;
      this.mc = null;
    }
  }

  protected void finalize()
  {
    destroy();
  }
}