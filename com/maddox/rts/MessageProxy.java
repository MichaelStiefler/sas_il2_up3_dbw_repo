// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MessageProxy.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message

public interface MessageProxy
{

    public abstract java.lang.Object getListener(com.maddox.rts.Message message);
}
