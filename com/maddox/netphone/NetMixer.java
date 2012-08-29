// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMixer.java

package com.maddox.netphone;

import com.maddox.il2.engine.TextScr;
import java.util.Iterator;
import java.util.Vector;

// Referenced classes of package com.maddox.netphone:
//            MixBase, GsmIO, LpcmIO, MixChannel, 
//            HBitStr, BitStream, CodecIO

public class NetMixer extends com.maddox.netphone.MixBase
{

    public NetMixer(int i)
    {
        channels = new Vector();
        cid = 1;
        oneSig = false;
        print("NETMIXER::INIT, Codec = " + i);
        codec = i;
        if(i == 2)
        {
            inStr = new GsmIO();
            outStr = new GsmIO();
        } else
        {
            inStr = new LpcmIO();
            outStr = new LpcmIO();
        }
    }

    public void destroy()
    {
        com.maddox.netphone.MixChannel mixchannel;
        for(; !channels.isEmpty(); mixchannel.destroy())
            mixchannel = (com.maddox.netphone.MixChannel)channels.firstElement();

        print("NETMIXER::DESTROY");
    }

    public com.maddox.netphone.MixChannel newChannel(boolean flag)
    {
        com.maddox.netphone.MixChannel mixchannel = new MixChannel(this, cid++, codec);
        mixchannel.isVirtual = flag;
        print("NETMIXER::ADDCH");
        return mixchannel;
    }

    public void tick()
    {
        for(java.util.Iterator iterator = channels.iterator(); iterator.hasNext();)
        {
            com.maddox.netphone.MixChannel mixchannel = (com.maddox.netphone.MixChannel)iterator.next();
            boolean flag = false;
            if(mixchannel.wasReset())
            {
                com.maddox.netphone.HBitStr hbitstr;
                for(java.util.Iterator iterator1 = mixchannel.outList.iterator(); iterator1.hasNext(); hbitstr.bs.clear())
                    hbitstr = (com.maddox.netphone.HBitStr)iterator1.next();

            } else
            {
                if(mixchannel.act)
                    do
                    {
                        java.util.Iterator iterator2 = mixchannel.outList.iterator();
                        boolean flag1 = true;
                        boolean flag2 = true;
                        while(iterator2.hasNext()) 
                        {
                            com.maddox.netphone.HBitStr hbitstr2 = (com.maddox.netphone.HBitStr)iterator2.next();
                            if(hbitstr2.mc.act && !hbitstr2.mc.isVirtual)
                            {
                                int j = hbitstr2.bs.bitlen();
                                if(j >= 1)
                                {
                                    if(hbitstr2.bs.probe(1, 0) > 0)
                                        flag1 = flag1 && j >= inStr.getMaxBlockSize();
                                } else
                                {
                                    flag1 = false;
                                }
                                flag2 = false;
                            }
                        }
                        if(!flag1 || flag2)
                            break;
                        iterator2 = mixchannel.outList.iterator();
                        outStr.reset();
                        while(iterator2.hasNext()) 
                        {
                            com.maddox.netphone.HBitStr hbitstr3 = (com.maddox.netphone.HBitStr)iterator2.next();
                            if(!hbitstr3.mc.act || hbitstr3.mc.isVirtual || hbitstr3.bs.get(1) == 0)
                                continue;
                            if(inStr.read(hbitstr3.bs) != 0)
                            {
                                int i = 1000 + hbitstr3.bs.bitlen();
                                break;
                            }
                            int k = inStr.getRms();
                            if(usePriority)
                                k *= (46 + hbitstr3.mc.priority) / 50;
                            if(k > outStr.getRms())
                                outStr.copy(inStr);
                        }
                        if(outStr.isEmpty())
                        {
                            mixchannel.slCnt++;
                        } else
                        {
                            mixchannel.out.put(1, 1);
                            outStr.write(mixchannel.out);
                            mixchannel.slCnt = 0;
                        }
                    } while(true);
                for(java.util.Iterator iterator3 = mixchannel.inList.iterator(); iterator3.hasNext();)
                {
                    com.maddox.netphone.HBitStr hbitstr1 = (com.maddox.netphone.HBitStr)iterator3.next();
                    if(!hbitstr1.pOut.act)
                        hbitstr1.bs.reset();
                }

            }
        }

    }

    public void printState(int i)
    {
        for(java.util.Iterator iterator = channels.iterator(); iterator.hasNext();)
        {
            com.maddox.netphone.MixChannel mixchannel = (com.maddox.netphone.MixChannel)iterator.next();
            com.maddox.il2.engine.TextScr.output(100, i, mixchannel.act + " - " + java.lang.Integer.toString(mixchannel.getDataLength()));
            i += 20;
        }

    }

    public static final int prDefault = 4;
    public static final int prLevel = 50;
    protected java.util.Vector channels;
    protected int cid;
    protected boolean oneSig;
    protected com.maddox.netphone.CodecIO inStr;
    protected com.maddox.netphone.CodecIO outStr;
    protected int codec;
    protected static boolean usePriority = false;

}
