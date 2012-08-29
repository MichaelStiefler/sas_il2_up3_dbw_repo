// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorPosMoveInit.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            ActorPosMove, RendersMain, Actor, Loc, 
//            ActorPos, Hook

public class ActorPosMoveInit extends com.maddox.il2.engine.ActorPosMove
{

    public void inValidate(boolean flag)
    {
        if(flag)
            flg &= -2;
        flg |= 2;
    }

    protected void validateRender()
    {
        renderTick = com.maddox.il2.engine.RendersMain.frame() - 1;
        super.validateRender();
    }

    protected void setBase(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook, boolean flag, boolean flag1)
    {
        base = actor;
        baseHook = hook;
        inValidate(true);
    }

    public void resetAsBase()
    {
        if(!com.maddox.il2.engine.Actor.isValid(base))
        {
            reset();
            return;
        }
        if((flg & 1) == 0)
            validate();
        if(baseHook != null)
        {
            prevLabs.set(L);
            baseHook.computePos(base, base.pos.getCurrent(), prevLabs);
        } else
        {
            prevLabs.add(L, base.pos.getCurrent());
        }
        curLabs.set(prevLabs);
        if(baseHook != null)
        {
            prevLabs.set(L);
            baseHook.computePos(base, base.pos.getPrev(), prevLabs);
        } else
        {
            prevLabs.add(L, base.pos.getPrev());
        }
    }

    public void reset()
    {
        updateCurrent();
        prevLabs.set(curLabs);
    }

    protected void updateCurrent()
    {
        prevLabs.set(curLabs);
        if((flg & 2) != 0)
        {
            getAbs(curLabs);
            flg &= -3;
        }
    }

    protected void drawingChange(boolean flag)
    {
    }

    protected void collideChange(boolean flag)
    {
    }

    protected void dreamFireChange(boolean flag)
    {
    }

    protected void addChildren(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void removeChildren(com.maddox.il2.engine.Actor actor)
    {
    }

    public void destroy()
    {
    }

    public ActorPosMoveInit()
    {
    }
}
