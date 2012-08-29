// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetControlReal.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetObj

public interface NetControlReal
{

    public abstract void msgNewClient(com.maddox.rts.NetObj netobj, int i, java.lang.String s, int j);

    public abstract void msgAnswer(com.maddox.rts.NetObj netobj, int i, java.lang.String s);
}
