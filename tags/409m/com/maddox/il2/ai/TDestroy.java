// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Target.java

package com.maddox.il2.ai;

import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;

// Referenced classes of package com.maddox.il2.ai:
//            Target, Wing

class TDestroy extends com.maddox.il2.ai.Target
{

    public void destroy()
    {
        super.destroy();
        actor = null;
    }

    boolean checkActor()
    {
        if(actor != null && alive > 0)
            return true;
        actor = com.maddox.il2.engine.Actor.getByName(nameTarget);
        if(actor != null && alive == -1)
        {
            if((actor instanceof com.maddox.il2.ai.ground.ChiefGround) && ((com.maddox.il2.ai.ground.ChiefGround)actor).isPacked() && actor.isAlive())
                return false;
            int i = actor.getOwnerAttachedCount();
            if(i > 0)
            {
                alive = i - java.lang.Math.round((float)(i * destructLevel) / 100F);
                if(alive >= i)
                    alive = i - 1;
            }
        }
        return actor != null;
    }

    protected boolean checkPeriodic()
    {
        if(!checkActor())
            return false;
        if(!com.maddox.il2.engine.Actor.isValid(actor))
        {
            setTaskCompleteFlag(true);
            setDiedFlag(true);
            return true;
        }
        int i = actor.getOwnerAttachedCount();
        if(i == 0 && !(actor instanceof com.maddox.il2.ai.Wing) || alive == -1)
            return false;
        int j = 0;
        int k = 0;
        for(int l = 0; l < i; l++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)actor.getOwnerAttached(l);
            if(com.maddox.il2.engine.Actor.isAlive(actor1))
            {
                j++;
                if(actor1.isTaskComplete())
                    k++;
            }
        }

        if(j <= alive)
        {
            setTaskCompleteFlag(true);
            setDiedFlag(true);
            return true;
        }
        if(k > alive)
        {
            setDiedFlag(true);
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean checkActorDied(com.maddox.il2.engine.Actor actor1)
    {
        if(!checkActor())
            return false;
        if(actor == actor1)
        {
            setTaskCompleteFlag(true);
            setDiedFlag(true);
            return true;
        } else
        {
            return checkPeriodic();
        }
    }

    protected boolean checkTaskComplete(com.maddox.il2.engine.Actor actor1)
    {
        if(!checkActor())
            return false;
        if(actor1 == actor)
        {
            setDiedFlag(true);
            return true;
        } else
        {
            return checkPeriodic();
        }
    }

    protected boolean checkTimeoutOff()
    {
        setDiedFlag(true);
        return true;
    }

    public TDestroy(int i, int j, java.lang.String s, int k)
    {
        super(i, j);
        alive = -1;
        nameTarget = s;
        destructLevel = k;
    }

    java.lang.String nameTarget;
    com.maddox.il2.engine.Actor actor;
    int destructLevel;
    int alive;
}
