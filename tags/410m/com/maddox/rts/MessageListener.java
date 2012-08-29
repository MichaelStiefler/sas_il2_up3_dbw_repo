// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MessageListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message

public interface MessageListener
{

    public abstract java.lang.Object getParentListener(com.maddox.rts.Message message);
}
