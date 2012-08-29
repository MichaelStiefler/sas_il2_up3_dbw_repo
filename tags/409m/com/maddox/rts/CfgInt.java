// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgInt.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Cfg

public interface CfgInt
    extends com.maddox.rts.Cfg
{

    public abstract int firstState();

    public abstract int countStates();

    public abstract int defaultState();

    public abstract java.lang.String nameState(int i);

    public abstract boolean isEnabledState(int i);

    public abstract int get();

    public abstract void set(int i);
}
