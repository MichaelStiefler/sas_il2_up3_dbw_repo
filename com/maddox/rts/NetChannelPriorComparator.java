// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannel.java

package com.maddox.rts;

import java.util.Comparator;

// Referenced classes of package com.maddox.rts:
//            NetMsgFiltered

class NetChannelPriorComparator
    implements java.util.Comparator
{

    NetChannelPriorComparator()
    {
    }

    public int compare(java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.NetMsgFiltered netmsgfiltered = (com.maddox.rts.NetMsgFiltered)obj;
        com.maddox.rts.NetMsgFiltered netmsgfiltered1 = (com.maddox.rts.NetMsgFiltered)obj1;
        if(netmsgfiltered._prior > netmsgfiltered1._prior)
            return -1;
        if(netmsgfiltered._prior == netmsgfiltered1._prior)
        {
            if(netmsgfiltered._time > netmsgfiltered1._time)
                return -1;
            if(netmsgfiltered._time == netmsgfiltered1._time && netmsgfiltered._timeStamp > netmsgfiltered1._timeStamp)
                return -1;
        }
        return 1;
    }
}
