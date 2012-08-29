// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetUserLeft.java

package com.maddox.il2.net;

import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.net:
//            NetUserStat

public class NetUserLeft
{

    public NetUserLeft(java.lang.String s, int i, com.maddox.il2.net.NetUserStat netuserstat)
    {
        stat = new NetUserStat();
        uniqueName = s;
        army = i;
        stat.set(netuserstat);
        all.add(this);
    }

    public static java.util.ArrayList all = new ArrayList();
    public java.lang.String uniqueName;
    public int army;
    public com.maddox.il2.net.NetUserStat stat;

}
