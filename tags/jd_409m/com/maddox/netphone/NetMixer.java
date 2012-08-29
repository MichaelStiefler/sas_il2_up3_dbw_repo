package com.maddox.netphone;

import com.maddox.il2.engine.TextScr;
import java.util.Iterator;
import java.util.Vector;

public class NetMixer extends MixBase
{
  public static final int prDefault = 4;
  public static final int prLevel = 50;
  protected Vector channels = new Vector();
  protected int cid = 1;

  protected boolean oneSig = false;
  protected CodecIO inStr;
  protected CodecIO outStr;
  protected int codec;
  protected static boolean usePriority = false;

  public NetMixer(int paramInt)
  {
    print("NETMIXER::INIT, Codec = " + paramInt);
    this.codec = paramInt;
    if (paramInt == 2) {
      this.inStr = new GsmIO();
      this.outStr = new GsmIO();
    } else {
      this.inStr = new LpcmIO();
      this.outStr = new LpcmIO();
    }
  }

  public void destroy()
  {
    while (!this.channels.isEmpty()) {
      MixChannel localMixChannel = (MixChannel)this.channels.firstElement();
      localMixChannel.destroy();
    }
    print("NETMIXER::DESTROY");
  }

  public MixChannel newChannel(boolean paramBoolean)
  {
    MixChannel localMixChannel = new MixChannel(this, this.cid++, this.codec);
    localMixChannel.isVirtual = paramBoolean;
    print("NETMIXER::ADDCH");
    return localMixChannel;
  }

  public void tick()
  {
    Iterator localIterator1 = this.channels.iterator();

    while (localIterator1.hasNext()) {
      MixChannel localMixChannel = (MixChannel)localIterator1.next();
      int i = 0;
      Iterator localIterator2;
      if (localMixChannel.wasReset()) {
        localIterator2 = localMixChannel.outList.iterator();
        while (localIterator2.hasNext()) {
          HBitStr localHBitStr1 = (HBitStr)localIterator2.next();
          localHBitStr1.bs.clear();
        }
      }
      else {
        if (localMixChannel.act) {
          while (true)
          {
            localIterator2 = localMixChannel.outList.iterator();
            int j = 1;
            int k = 1;
            HBitStr localHBitStr3;
            int m;
            while (localIterator2.hasNext()) {
              localHBitStr3 = (HBitStr)localIterator2.next();
              if ((localHBitStr3.mc.act) && (!localHBitStr3.mc.isVirtual)) {
                m = localHBitStr3.bs.bitlen();
                if (m >= 1) {
                  if (localHBitStr3.bs.probe(1, 0) > 0) j = (j != 0) && (m >= this.inStr.getMaxBlockSize()) ? 1 : 0; 
                }
                else {
                  j = 0;
                }
                k = 0;
              }
            }
            if ((j == 0) || (k != 0)) break;
            localIterator2 = localMixChannel.outList.iterator();
            this.outStr.reset();
            while (localIterator2.hasNext()) {
              localHBitStr3 = (HBitStr)localIterator2.next();
              if ((!localHBitStr3.mc.act) || (localHBitStr3.mc.isVirtual) || 
                (localHBitStr3.bs.get(1) == 0)) continue;
              if (this.inStr.read(localHBitStr3.bs) != 0) {
                i = 1000 + localHBitStr3.bs.bitlen();
                break;
              }
              m = this.inStr.getRms();
              if (usePriority) {
                m *= (46 + localHBitStr3.mc.priority) / 50;
              }
              if (m > this.outStr.getRms()) {
                this.outStr.copy(this.inStr);
              }

            }

            if (this.outStr.isEmpty())
            {
              localMixChannel.slCnt += 1; continue;
            }

            localMixChannel.out.put(1, 1);
            this.outStr.write(localMixChannel.out);
            localMixChannel.slCnt = 0;
          }
        }

        localIterator2 = localMixChannel.inList.iterator();
        while (localIterator2.hasNext()) {
          HBitStr localHBitStr2 = (HBitStr)localIterator2.next();

          if (localHBitStr2.pOut.act) continue; localHBitStr2.bs.reset();
        }
      }
    }
  }

  public void printState(int paramInt) {
    Iterator localIterator = this.channels.iterator();

    while (localIterator.hasNext()) {
      MixChannel localMixChannel = (MixChannel)localIterator.next();
      TextScr.output(100, paramInt, localMixChannel.act + " - " + Integer.toString(localMixChannel.getDataLength()));
      paramInt += 20;
    }
  }
}