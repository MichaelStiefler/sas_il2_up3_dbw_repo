// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNetAskNakListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetMsgGuaranted, NetChannel

public interface MsgNetAskNakListener
{

    public abstract void msgNetAsk(com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel);

    public abstract void msgNetNak(com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel);
}
