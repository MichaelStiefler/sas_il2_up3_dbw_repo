// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNetListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetMsgInput, NetChannel

public interface MsgNetListener
{

    public abstract void msgNet(com.maddox.rts.NetMsgInput netmsginput);

    public abstract void msgNetNewChannel(com.maddox.rts.NetChannel netchannel);

    public abstract void msgNetDelChannel(com.maddox.rts.NetChannel netchannel);
}
