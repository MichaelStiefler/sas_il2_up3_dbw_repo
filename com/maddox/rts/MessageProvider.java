// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MessageProvider.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Destroy, MessageProxy, Message

public class MessageProvider
    implements com.maddox.rts.MessageProxy
{

    public java.lang.Object getListener()
    {
        return listener;
    }

    public void setListener(java.lang.Object obj)
    {
        listener = obj;
    }

    public java.lang.Object getListener(com.maddox.rts.Message message)
    {
        if(listener != null && (listener instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)listener).isDestroyed())
            listener = null;
        return listener;
    }

    public MessageProvider(java.lang.Object obj)
    {
        listener = obj;
    }

    public MessageProvider()
    {
        listener = null;
    }

    private java.lang.Object listener;
}
