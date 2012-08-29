// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Target.java

package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;

// Referenced classes of package com.maddox.il2.ai:
//            Target

class TDestroyBridge extends com.maddox.il2.ai.Target
{

    public void destroy()
    {
        super.destroy();
        actor = null;
    }

    protected boolean checkActorDied(com.maddox.il2.engine.Actor actor1)
    {
        if(actor == actor1)
        {
            setTaskCompleteFlag(true);
            setDiedFlag(true);
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean checkTimeoutOff()
    {
        setDiedFlag(true);
        return true;
    }

    public TDestroyBridge(int i, int j, java.lang.String s)
    {
        super(i, j);
        actor = com.maddox.il2.engine.Actor.getByName(s);
    }

    com.maddox.il2.engine.Actor actor;
}
