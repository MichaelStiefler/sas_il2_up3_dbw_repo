// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Squadron.java

package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.ai:
//            Regiment, Wing

public class Squadron extends com.maddox.il2.engine.Actor
{

    public int indexInRegiment()
    {
        char c = name().charAt(name().length() - 1);
        return c - 48;
    }

    public com.maddox.il2.ai.Regiment regiment()
    {
        return (com.maddox.il2.ai.Regiment)getOwner();
    }

    public int getWingsNumber()
    {
        int j = 0;
        for(int i = 0; i < wing.length; i++)
            if(wing[i] != null)
                j++;

        return j;
    }

    public static com.maddox.il2.ai.Squadron New(java.lang.String s)
    {
        com.maddox.il2.ai.Squadron squadron = (com.maddox.il2.ai.Squadron)com.maddox.il2.engine.Actor.getByName(s);
        if(squadron != null)
            return squadron;
        else
            return new Squadron(s);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Squadron(java.lang.String s)
    {
        wing = new com.maddox.il2.ai.Wing[4];
        setName(s);
        int i = indexInRegiment();
        if(i < 0 || i > 3)
            throw new RuntimeException("Squadron '" + s + "' NOT valid");
        java.lang.String s1 = s.substring(0, s.length() - 1);
        com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s1);
        if(regiment1 == null)
        {
            throw new RuntimeException("Regiment '" + s1 + "' NOT found");
        } else
        {
            setOwner(regiment1);
            setArmy(regiment1.getArmy());
            return;
        }
    }

    protected Squadron()
    {
        wing = new com.maddox.il2.ai.Wing[4];
    }

    public com.maddox.il2.ai.Wing wing[];
}
