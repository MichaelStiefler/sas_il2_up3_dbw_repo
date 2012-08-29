// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HBitStr.java

package com.maddox.netphone;

import java.util.Vector;

// Referenced classes of package com.maddox.netphone:
//            MixBase, BitStream, MixChannel, CodecIO

public class HBitStr extends com.maddox.netphone.MixBase
{

    public HBitStr(com.maddox.netphone.MixChannel mixchannel, com.maddox.netphone.MixChannel mixchannel1)
    {
        mc = mixchannel;
        pOut = mixchannel1;
        java.util.Vector vector = mc.inList;
        if(vector.isEmpty())
        {
            bs = new BitStream(mixchannel.rd.getMaxStreamSize());
        } else
        {
            com.maddox.netphone.HBitStr hbitstr = (com.maddox.netphone.HBitStr)vector.firstElement();
            bs = new BitStream(hbitstr.bs);
        }
        vector.add(this);
        pOut.outList.add(this);
    }

    protected void destroy()
    {
        if(mc != null)
        {
            mc.inList.remove(this);
            pOut.outList.remove(this);
            bs.destroy();
            bs = null;
            mc = null;
        }
    }

    protected void finalize()
    {
        destroy();
    }

    protected com.maddox.netphone.BitStream bs;
    protected com.maddox.netphone.MixChannel mc;
    protected com.maddox.netphone.MixChannel pOut;
}
