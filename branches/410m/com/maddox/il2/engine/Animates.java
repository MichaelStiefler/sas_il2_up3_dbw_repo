// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Animates.java

package com.maddox.il2.engine;

import java.util.HashMap;

// Referenced classes of package com.maddox.il2.engine:
//            AnimateMove

public class Animates
{

    public void add(com.maddox.il2.engine.AnimateMove animatemove)
    {
        map.put(animatemove.name, animatemove);
    }

    public com.maddox.il2.engine.AnimateMove get(java.lang.String s)
    {
        return (com.maddox.il2.engine.AnimateMove)map.get(s);
    }

    public void created()
    {
    }

    public Animates()
    {
        map = new HashMap();
        created();
    }

    public java.util.HashMap map;
}
