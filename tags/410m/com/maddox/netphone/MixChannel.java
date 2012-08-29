// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MixChannel.java

package com.maddox.netphone;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

// Referenced classes of package com.maddox.netphone:
//            MixBase, BitStream, GsmIO, LpcmIO, 
//            HBitStr, CodecIO, NetMixer

public class MixChannel extends com.maddox.netphone.MixBase
{

    protected MixChannel(com.maddox.netphone.NetMixer netmixer, int i, int j)
    {
        outCache = new ArrayList();
        inList = new Vector();
        outList = new Vector();
        act = false;
        priority = 4;
        isVirtual = false;
        wasReset = false;
        bitBuf = new BitStream(128);
        owner = netmixer;
        iid = i;
        slCnt = 0;
        if(j == 2)
            rd = new GsmIO();
        else
            rd = new LpcmIO();
        out = new BitStream(rd.getMaxStreamSize());
        com.maddox.netphone.MixChannel mixchannel;
        for(java.util.Iterator iterator = netmixer.channels.iterator(); iterator.hasNext(); new HBitStr(mixchannel, this))
        {
            mixchannel = (com.maddox.netphone.MixChannel)iterator.next();
            new HBitStr(this, mixchannel);
        }

        netmixer.channels.add(this);
    }

    public void destroy()
    {
        owner.channels.remove(this);
        com.maddox.netphone.HBitStr hbitstr;
        for(; !inList.isEmpty(); hbitstr.destroy())
            hbitstr = (com.maddox.netphone.HBitStr)inList.firstElement();

        com.maddox.netphone.HBitStr hbitstr1;
        for(; !outList.isEmpty(); hbitstr1.destroy())
            hbitstr1 = (com.maddox.netphone.HBitStr)outList.firstElement();

        out.destroy();
        bitBuf.destroy();
        print("NETMIXER::DELCH");
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int i)
    {
        priority = i;
    }

    public boolean isActive()
    {
        return act;
    }

    public boolean wasReset()
    {
        boolean flag = wasReset;
        wasReset = false;
        return flag;
    }

    public void setActive(boolean flag)
    {
        if(flag && !act)
        {
            com.maddox.netphone.HBitStr hbitstr;
            for(java.util.Iterator iterator = inList.iterator(); iterator.hasNext(); hbitstr.bs.clear())
                hbitstr = (com.maddox.netphone.HBitStr)iterator.next();

            out.clear();
            wasReset = true;
        }
        act = flag;
    }

    public int getFreeSpace()
    {
        if(!act || inList.isEmpty())
        {
            return 0;
        } else
        {
            com.maddox.netphone.HBitStr hbitstr = (com.maddox.netphone.HBitStr)inList.firstElement();
            return hbitstr.bs.space(8);
        }
    }

    public int put(byte abyte0[], int i)
    {
        if(!act || inList.isEmpty())
        {
            return 0;
        } else
        {
            com.maddox.netphone.HBitStr hbitstr = (com.maddox.netphone.HBitStr)inList.firstElement();
            return hbitstr.bs.putAligned(abyte0, i);
        }
    }

    public int putNA(byte abyte0[], int i)
    {
        if(!act || inList.isEmpty())
        {
            return 0;
        } else
        {
            com.maddox.netphone.HBitStr hbitstr = (com.maddox.netphone.HBitStr)inList.firstElement();
            return hbitstr.bs.putBytes(abyte0, i);
        }
    }

    public int getDataLength()
    {
        int i = out.bitlen();
        return i <= 0 ? 0 : (i + 3 + 8) / 8;
    }

    public int getNALength()
    {
        return out.len(8);
    }

    public int get(byte abyte0[], int i)
    {
        int j = (i - 8) * 8;
        if(out.bitlen() == 0)
            return 0;
        if(out.bitlen() <= j)
            return out.getAligned(abyte0, i);
        while(out.bitlen() > 0 && bitBuf.bitlen() < rd.getMinOutSpace()) 
        {
            int l = out.get(1);
            if(l == 0)
            {
                bitBuf.put(0, 1);
            } else
            {
                bitBuf.put(1, 1);
                rd.read(out);
                rd.write(bitBuf);
            }
        }
        int k = bitBuf.getAligned(abyte0, i);
        if(bitBuf.bitlen() > 0)
        {
            java.lang.System.out.println("Netmixer internal error at output, len = " + bitBuf.bitlen());
            bitBuf.clear();
        }
        return k;
    }

    public int getNA(byte abyte0[], int i)
    {
        return out.getBytes(abyte0, i);
    }

    public boolean isWait()
    {
        return slCnt > 0;
    }

    protected com.maddox.netphone.BitStream out;
    protected com.maddox.netphone.BitStream bitBuf;
    protected com.maddox.netphone.NetMixer owner;
    protected com.maddox.netphone.CodecIO rd;
    protected java.util.ArrayList outCache;
    protected java.util.Vector inList;
    protected java.util.Vector outList;
    protected boolean act;
    protected int iid;
    protected int priority;
    protected int slCnt;
    protected boolean isVirtual;
    private boolean wasReset;
}
