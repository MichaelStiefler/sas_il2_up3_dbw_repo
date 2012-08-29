// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetConnect.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetSocket, NetAddress, NetChannel

public interface NetConnect
{

    public abstract void bindEnable(boolean flag);

    public abstract boolean isBindEnable();

    public abstract void join(com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i);

    public abstract void joinBreak();

    public abstract boolean isJoinProcess();

    public abstract void msgRequest(java.lang.String s);

    public abstract void channelCreated(com.maddox.rts.NetChannel netchannel);

    public abstract void channelNotCreated(com.maddox.rts.NetChannel netchannel, java.lang.String s);

    public abstract void channelDestroying(com.maddox.rts.NetChannel netchannel, java.lang.String s);
}
