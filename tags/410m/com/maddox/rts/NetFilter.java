// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFilter.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetChannel, NetMsgFiltered

public interface NetFilter
{

    public abstract float filterNetMessage(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered);

    public abstract void filterNetMessagePosting(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered);

    public abstract boolean filterEnableAdd(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetFilter netfilter);
}
