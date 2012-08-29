// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannel.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetMsgGuaranted

class NetChannelGMsgOutput
{

    NetChannelGMsgOutput()
    {
    }

    public int sequenceNum;
    public int objIndex;
    public byte iObjects[];
    public long timeLastSend;
    public com.maddox.rts.NetMsgGuaranted msg;
}
