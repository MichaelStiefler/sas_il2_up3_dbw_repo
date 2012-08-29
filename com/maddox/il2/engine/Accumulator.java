// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Accumulator.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Actor

public interface Accumulator
{

    public abstract void clear();

    public abstract boolean add(com.maddox.il2.engine.Actor actor, double d);
}
