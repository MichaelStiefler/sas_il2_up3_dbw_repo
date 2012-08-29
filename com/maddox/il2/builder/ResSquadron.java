// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ResSquadron.java

package com.maddox.il2.builder;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            PathAir

public class ResSquadron extends com.maddox.il2.engine.Actor
{

    public int index()
    {
        int i = java.lang.Character.getNumericValue(name().charAt(name().length() - 1)) - java.lang.Character.getNumericValue('0');
        if(i < 0)
            i = 0;
        if(i > 3)
            i = 3;
        return i;
    }

    public com.maddox.il2.ai.Regiment regiment()
    {
        return (com.maddox.il2.ai.Regiment)getOwner();
    }

    public static com.maddox.il2.builder.ResSquadron New(java.lang.String s)
    {
        com.maddox.il2.builder.ResSquadron ressquadron = (com.maddox.il2.builder.ResSquadron)com.maddox.il2.engine.Actor.getByName(s);
        if(ressquadron != null)
            return ressquadron;
        else
            return new ResSquadron(s);
    }

    public java.lang.Object[] getAttached(java.lang.Object aobj[])
    {
        return attached.toArray(aobj);
    }

    public int getAttachedCount()
    {
        return attached.size();
    }

    public void attach(com.maddox.il2.builder.PathAir pathair)
    {
        attached.add(pathair);
    }

    public void detach(com.maddox.il2.builder.PathAir pathair)
    {
        int i = attached.indexOf(pathair);
        if(i >= 0)
            attached.remove(i);
        if(attached.size() == 0)
            destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ResSquadron(java.lang.String s)
    {
        attached = new ArrayList();
        setName(s);
        com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s.substring(0, s.length() - 1));
        setOwner(regiment1);
        setArmy(regiment1.getArmy());
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    private java.util.ArrayList attached;
}
