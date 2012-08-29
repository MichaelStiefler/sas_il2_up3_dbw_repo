// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GsmIO.java

package com.maddox.netphone;


// Referenced classes of package com.maddox.netphone:
//            CodecIO, BitStream

public class GsmIO extends com.maddox.netphone.CodecIO
{

    public GsmIO()
    {
        irms = 0;
        assigned = false;
        data = new byte[33];
    }

    protected int gsmGain()
    {
        int k = 0;
        k += 6;
        int i = (data[k] & 0x1f) << 1;
        k++;
        i |= data[k] >> 7 & 1;
        int j = i;
        k += 6;
        i = (data[k] & 0x1f) << 1;
        k++;
        i |= data[k] >> 7 & 1;
        k += 6;
        j += i;
        i = (data[k] & 0x1f) << 1;
        k++;
        i |= data[k] >> 7 & 1;
        k += 6;
        j += i;
        i = (data[k] & 0x1f) << 1;
        k++;
        i |= data[k] >> 7 & 1;
        j += i;
        return j;
    }

    public int getRms()
    {
        return irms;
    }

    public int getMaxStreamSize()
    {
        return 4096;
    }

    public int getMaxBlockSize()
    {
        return 264;
    }

    public int getMinOutSpace()
    {
        return 240;
    }

    public void copy(com.maddox.netphone.CodecIO codecio)
    {
        for(int i = 0; i < 33; i++)
            data[i] = ((com.maddox.netphone.GsmIO)codecio).data[i];

        irms = ((com.maddox.netphone.GsmIO)codecio).irms;
        assigned = true;
    }

    public void reset()
    {
        assigned = false;
        irms = -32768;
    }

    public boolean isEmpty()
    {
        return !assigned;
    }

    public int write(com.maddox.netphone.BitStream bitstream)
    {
        for(int i = 0; i < 33; i++)
            bitstream.put(data[i], 8);

        return 264;
    }

    public int read(com.maddox.netphone.BitStream bitstream)
    {
        bitstream.errClear();
        for(int i = 0; i < 33; i++)
            data[i] = (byte)bitstream.get(8);

        irms = gsmGain();
        assigned = true;
        return bitstream.getError();
    }

    protected byte data[];
    private boolean assigned;
    private int irms;
}
