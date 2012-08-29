// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BitStream.java

package com.maddox.netphone;

import java.util.Iterator;
import java.util.Vector;

// Referenced classes of package com.maddox.netphone:
//            MixBase, BsData

public class BitStream extends com.maddox.netphone.MixBase
{

    public BitStream(int i)
    {
        rp = 0;
        bdt = null;
        err = 0;
        if(i <= 0)
        {
            error("Invalid argument");
            return;
        }
        int j;
        for(j = 1; j < i; j *= 2);
        i = j;
        bdt = new BsData(i);
        rp = bdt.wp;
        bdt.links.add(this);
    }

    public BitStream(com.maddox.netphone.BitStream bitstream)
    {
        rp = 0;
        bdt = null;
        err = 0;
        if(bitstream == null)
        {
            error("Invalid parameter");
            return;
        } else
        {
            bdt = bitstream.bdt;
            rp = bdt.wp;
            bdt.links.add(this);
            return;
        }
    }

    public void clear()
    {
        if(bdt != null)
        {
            bdt.wp = 0;
            bdt.maxlen = 0;
            bdt.rdflag = false;
            for(java.util.Iterator iterator = bdt.links.iterator(); iterator.hasNext();)
            {
                com.maddox.netphone.BitStream bitstream = (com.maddox.netphone.BitStream)iterator.next();
                bitstream.rp = bdt.wp;
            }

        } else
        {
            rp = 0;
        }
    }

    public void destroy()
    {
        if(bdt != null)
        {
            bdt.links.remove(this);
            bdt = null;
        }
    }

    public int bitlen()
    {
        return bdt.wp - rp & bdt.size - 1;
    }

    public void reset()
    {
        rp = bdt.wp;
    }

    public int len(int i)
    {
        return bitlen() / i;
    }

    public int size()
    {
        return (bitlen() + 7) / 8;
    }

    protected int bitspace()
    {
        if(bdt.rdflag)
        {
            int j = 0;
            for(java.util.Iterator iterator = bdt.links.iterator(); iterator.hasNext();)
            {
                com.maddox.netphone.BitStream bitstream = (com.maddox.netphone.BitStream)iterator.next();
                int i = bitstream.bitlen();
                if(i > j)
                    j = i;
            }

            bdt.maxlen = j;
            bdt.rdflag = false;
        }
        return bdt.size - bdt.maxlen - 1;
    }

    public int space(int i)
    {
        return bitspace() / i;
    }

    public int alignSpace()
    {
        int i = bitspace() / 8;
        return i <= 1 ? 0 : i - 1;
    }

    public int put(int i, int j)
    {
        if(j <= 0)
            return 0;
        int k = bitspace();
        if(k < j)
        {
            error("No space - put");
            return 0;
        }
        for(int l = 0; l < j; l++)
        {
            if((i & 1 << l) != 0)
                bdt.data[bdt.wp >>> 3] |= 1 << (bdt.wp & 7);
            else
                bdt.data[bdt.wp >>> 3] &= ~(1 << (bdt.wp & 7));
            bdt.wp = bdt.wp + 1 & bdt.size - 1;
        }

        bdt.maxlen += j;
        return j;
    }

    public int putBegin(int i, int j)
    {
        if(j <= 0)
            return 0;
        if(bitspace() < j)
        {
            err = 1;
            error("No space - putBegin");
            return 0;
        }
        for(int k = j - 1; k >= 0; k--)
        {
            rp = rp - 1 & bdt.size - 1;
            if((i & 1 << k) != 0)
                bdt.data[rp >>> 3] |= 1 << (rp & 7);
            else
                bdt.data[rp >>> 3] &= ~(1 << (rp & 7));
        }

        bdt.maxlen += j;
        bdt.rdflag = true;
        return j;
    }

    public int get(int i)
    {
        int j = 0;
        if(bitlen() < i)
        {
            err = 2;
            error("No data - get");
        }
        if(err != 0)
            return 0;
        for(int k = 0; k < i; k++)
        {
            if((bdt.data[rp >>> 3] & 1 << (rp & 7)) != 0)
                j |= 1 << k;
            rp = rp + 1 & bdt.size - 1;
        }

        bdt.rdflag = true;
        return j;
    }

    public int probe(int i, int j)
    {
        if(bitlen() < i)
        {
            err = 2;
            error("No data - probe");
            return 0;
        }
        if(err != 0)
            return 0;
        int k = 0;
        int l = rp + j & bdt.size - 1;
        for(int i1 = 0; i1 < i; i1++)
        {
            if((bdt.data[l >>> 3] & 1 << (l & 7)) != 0)
                k |= 1 << i1;
            l = l + 1 & bdt.size + 1;
        }

        return k;
    }

    public int putBytes(byte abyte0[], int i)
    {
        if(space(8) < i)
            i = space(8);
        for(int j = 0; j < i; j++)
            put(abyte0[j], 8);

        return i;
    }

    public int getBytes(byte abyte0[], int i)
    {
        if(len(8) < i)
            i = len(8);
        for(int j = 0; j < i; j++)
            abyte0[j] = (byte)get(8);

        return i;
    }

    public void skip(int i)
    {
        if(bitlen() >= i)
            rp = rp + i & bdt.size - 1;
        else
            error("No data - skip");
    }

    public int getAligned(byte abyte0[], int i)
    {
        if(abyte0 == null || i <= 0)
            return 0;
        int j = i * 8;
        int k = bitlen() + 3;
        if(k > j)
        {
            putBegin(0, 3);
        } else
        {
            int l = k % 8;
            i = k / 8;
            if(l > 0)
            {
                l = 8 - l;
                i++;
            }
            putBegin(l, 3);
            put(0, l);
        }
        return getBytes(abyte0, i);
    }

    public int putAligned(byte abyte0[], int i)
    {
        if(abyte0 == null || i <= 0)
            return 0;
        byte byte0 = abyte0[0];
        int j = byte0 & 7;
        int k = i * 8 - j - 3;
        int l = k <= 5 ? k : 5;
        if(bitspace() < k)
            return 0;
        put(byte0 >>> 3, l);
        k -= l;
        for(int j1 = 1; k > 0; j1++)
        {
            int i1 = k <= 8 ? k : 8;
            put(abyte0[j1], i1);
            k -= i1;
        }

        return i;
    }

    public int getError()
    {
        int i = err;
        err = 0;
        return i;
    }

    public void errClear()
    {
        err = 0;
    }

    public static final int BSERR_OK = 0;
    public static final int BSERR_NODATA = 2;
    protected int rp;
    protected com.maddox.netphone.BsData bdt;
    protected int err;
}
