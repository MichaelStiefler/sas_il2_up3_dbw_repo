// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgGuaranted.java

package com.maddox.rts;

import java.io.IOException;

// Referenced classes of package com.maddox.rts:
//            NetMsgOutput, NetMsgInput

public class NetMsgGuaranted extends com.maddox.rts.NetMsgOutput
{

    public boolean isRequiredAsk()
    {
        return bRequiredAsk;
    }

    public void setRequiredAsk(boolean flag)
    {
        bRequiredAsk = flag;
    }

    public NetMsgGuaranted()
    {
        bRequiredAsk = false;
    }

    public NetMsgGuaranted(byte abyte0[])
    {
        super(abyte0);
        bRequiredAsk = false;
    }

    public NetMsgGuaranted(int i)
    {
        super(i);
        bRequiredAsk = false;
    }

    public NetMsgGuaranted(com.maddox.rts.NetMsgInput netmsginput, int i)
        throws java.io.IOException
    {
        super(netmsginput, i);
        bRequiredAsk = false;
    }

    private boolean bRequiredAsk;
}
