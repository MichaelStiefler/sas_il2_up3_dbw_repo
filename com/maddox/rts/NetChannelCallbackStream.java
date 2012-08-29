// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannelCallbackStream.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetChannelOutStream, NetMsgGuaranted, NetChannelInStream, NetMsgInput

public interface NetChannelCallbackStream
{

    public abstract boolean netChannelCallback(com.maddox.rts.NetChannelOutStream netchanneloutstream, com.maddox.rts.NetMsgGuaranted netmsgguaranted);

    public abstract void netChannelCallback(com.maddox.rts.NetChannelInStream netchannelinstream, com.maddox.rts.NetMsgGuaranted netmsgguaranted);

    public abstract boolean netChannelCallback(com.maddox.rts.NetChannelInStream netchannelinstream, com.maddox.rts.NetMsgInput netmsginput);
}
