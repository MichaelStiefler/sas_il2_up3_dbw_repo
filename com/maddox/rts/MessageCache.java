// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MessageCache.java

package com.maddox.rts;

import java.util.ArrayList;

// Referenced classes of package com.maddox.rts:
//            Message

public class MessageCache
{

    public com.maddox.rts.Message get()
    {
        for(int i = 0; i < cache.size(); i++)
        {
            if(!((com.maddox.rts.Message)(com.maddox.rts.Message)cache.get(icache)).busy())
                return (com.maddox.rts.Message)(com.maddox.rts.Message)cache.get(icache);
            icache = (icache + 1) % cache.size();
        }

        icache = 0;
        com.maddox.rts.Message message;
        message = (com.maddox.rts.Message)(com.maddox.rts.Message)cls.newInstance();
        message._flags |= 2;
        cache.add(message);
        return message;
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
        return null;
    }

    public MessageCache(java.lang.Class class1)
    {
        cls = class1;
        cache = new ArrayList();
        icache = 0;
    }

    private java.lang.Class cls;
    private java.util.ArrayList cache;
    private int icache;
}
