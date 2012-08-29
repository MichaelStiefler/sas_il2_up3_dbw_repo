// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LpcmIO.java

package com.maddox.netphone;


// Referenced classes of package com.maddox.netphone:
//            CodecIO, BitStream

public class LpcmIO extends com.maddox.netphone.CodecIO
{

    public LpcmIO()
    {
        isVoiced = false;
        ipit = 0;
        irms = 0;
        irc = new int[12];
        assigned = false;
    }

    public int getRms()
    {
        return irms;
    }

    public int getMaxStreamSize()
    {
        return 512;
    }

    public int getMaxBlockSize()
    {
        return 55;
    }

    public int getMinOutSpace()
    {
        return 320;
    }

    public void copy(com.maddox.netphone.CodecIO codecio)
    {
        com.maddox.netphone.LpcmIO lpcmio = (com.maddox.netphone.LpcmIO)codecio;
        isVoiced = lpcmio.isVoiced;
        ipit = lpcmio.ipit;
        irms = lpcmio.irms;
        for(int i = 0; i < 12; i++)
            irc[i] = lpcmio.irc[i];

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
        bitstream.put(isVoiced ? 1 : 0, 1);
        byte byte0;
        if(isVoiced)
        {
            bitstream.put(ipit, bittab[0]);
            byte0 = 10;
        } else
        {
            bitstream.put(ipit, 2);
            byte0 = 4;
        }
        bitstream.put(irms, bittab[1]);
        for(int i = 0; i < byte0; i++)
            bitstream.put(irc[i] & 0x7fff, bittab[i + 2]);

        return 54;
    }

    public int read(com.maddox.netphone.BitStream bitstream)
    {
        bitstream.errClear();
        isVoiced = bitstream.get(1) != 0;
        if(isVoiced)
            ipit = bitstream.get(bittab[0]);
        else
            ipit = bitstream.get(2);
        irms = bitstream.get(bittab[1]);
        byte byte0 = ((byte)(isVoiced ? 10 : 4));
        for(int i = 0; i < byte0; i++)
        {
            int j = bitstream.get(bittab[i + 2]);
            int l = 1 << bittab[i + 2] - 1;
            if((j & l) != 0)
                j -= l << 1;
            irc[i] = j;
        }

        if(!isVoiced)
        {
            for(int k = 4; k < 10; k++)
                irc[k] = 0;

        }
        assigned = true;
        return bitstream.getError();
    }

    private boolean isVoiced;
    private int ipit;
    private int irms;
    private int irc[];
    private boolean assigned;
    protected static final int bittab[] = {
        7, 5, 5, 5, 5, 5, 4, 4, 4, 4, 
        3, 2
    };

}
