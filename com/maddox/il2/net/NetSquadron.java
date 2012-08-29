// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetSquadron.java

package com.maddox.il2.net;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;

public class NetSquadron extends com.maddox.il2.ai.Squadron
{

    public int indexInRegiment()
    {
        return indexInRegiment;
    }

    public NetSquadron(com.maddox.il2.ai.Regiment regiment, int i)
    {
        indexInRegiment = 0;
        indexInRegiment = i;
        setOwner(regiment);
        setArmy(regiment.getArmy());
    }

    private int indexInRegiment;
}
