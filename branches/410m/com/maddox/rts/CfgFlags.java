// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgFlags.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Cfg

public interface CfgFlags
    extends com.maddox.rts.Cfg
{

    public abstract int firstFlag();

    public abstract int countFlags();

    public abstract boolean defaultFlag(int i);

    public abstract java.lang.String nameFlag(int i);

    public abstract boolean isPermanentFlag(int i);

    public abstract boolean isEnabledFlag(int i);

    public abstract boolean get(int i);

    public abstract void set(int i, boolean flag);

    public abstract int apply(int i);

    public abstract int applyStatus(int i);
}
