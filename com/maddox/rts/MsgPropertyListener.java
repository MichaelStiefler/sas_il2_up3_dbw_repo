// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgPropertyListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Property

public interface MsgPropertyListener
{

    public abstract void msgPropertyAdded(com.maddox.rts.Property property);

    public abstract void msgPropertyRemoved(com.maddox.rts.Property property);

    public abstract void msgPropertyChanged(com.maddox.rts.Property property);
}
