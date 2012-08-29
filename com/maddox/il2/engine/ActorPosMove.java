// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorPosMove.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            ActorPos, Actor, HookRender, ActorException, 
//            Loc, Orient, Engine, Hook, 
//            InterpolateAdapter, RendersMain, MsgBase, DrawEnv, 
//            CollideEnv, DreamEnv

public class ActorPosMove extends com.maddox.il2.engine.ActorPos
{

    private com.maddox.il2.engine.Loc nextReference()
    {
        curReference = (curReference + 1) % 256;
        return bufReference[curReference];
    }

    public void setUpdateEnable(boolean flag)
    {
        if(flag)
        {
            flg &= 0xffffffef;
            if((flg & 2) != 0)
                com.maddox.il2.engine.Engine.cur.posChanged.add(actor);
        } else
        {
            flg |= 0x10;
        }
    }

    public boolean isUpdateEnable()
    {
        return (flg & 0x10) == 0;
    }

    public void inValidate(boolean flag)
    {
        if(flag)
        {
            if((flg & 2) == 0)
            {
                if((flg & 0x10) == 0)
                    com.maddox.il2.engine.Engine.cur.posChanged.add(actor);
                flg |= 2;
            }
            flg &= -2;
            if(baseAttached != null)
            {
                int k = baseAttached.size();
                for(int i = 0; i < k; i++)
                {
                    com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)baseAttached.get(i);
                    if(actor1 == actor1.pos.actor())
                        actor1.pos.inValidate(true);
                }

            }
        } else
        if(baseAttached != null)
        {
            int l = baseAttached.size();
            for(int j = 0; j < l; j++)
            {
                com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)baseAttached.get(j);
                if(actor2 == actor2.pos.actor() && actor2.pos.baseHook() != null)
                    actor2.pos.inValidate(true);
            }

        }
    }

    private void inValidateValid(boolean flag)
    {
        if((flg & 2) == 0)
        {
            if((flg & 0x10) == 0)
                com.maddox.il2.engine.Engine.cur.posChanged.add(actor);
            flg |= 2;
        }
        if(flag && baseAttached != null)
        {
            int j = baseAttached.size();
            for(int i = 0; i < j; i++)
            {
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)baseAttached.get(i);
                if(actor1 == actor1.pos.actor())
                    actor1.pos.inValidate(true);
            }

        }
    }

    protected void validate()
    {
        if(base == null)
            Labs.set(L);
        else
        if(baseHook != null)
        {
            Labs.set(L);
            baseHook.computePos(base, base.pos.getAbs(), Labs);
        } else
        {
            Labs.add(L, base.pos.getAbs());
        }
        if(!com.maddox.il2.engine.InterpolateAdapter.isProcess())
            flg |= 1;
    }

    protected void validatePrev()
    {
        int i = com.maddox.rts.Time.tickCounter();
        if(i == curTick)
        {
            return;
        } else
        {
            curTick = i;
            prevLabs.set(curLabs);
            return;
        }
    }

    protected void validateRender()
    {
        if(renderTick == com.maddox.il2.engine.RendersMain.frame())
            return;
        if(base != null && baseHook != null && (baseHook instanceof com.maddox.il2.engine.HookRender) && (flg & 4) == 0)
        {
            if((flg & 1) == 0)
                validate();
            renderLabs.set(L);
            if(!((com.maddox.il2.engine.HookRender)baseHook).computeRenderPos(base, base.pos.getRender(), renderLabs))
                renderLabs.interpolate(curLabs, Labs, com.maddox.rts.Time.tickOffset());
        } else
        if(com.maddox.rts.Time.isPaused())
        {
            if((flg & 1) == 0)
                validate();
            renderLabs.set(Labs);
        } else
        if((flg & 2) != 0)
        {
            if(base != null && baseHook == null && (flg & 4) == 0)
            {
                renderLabs.add(L, base.pos.getRender());
            } else
            {
                if((flg & 1) == 0)
                    validate();
                renderLabs.interpolate(curLabs, Labs, com.maddox.rts.Time.tickOffset());
            }
        } else
        {
            renderLabs.set(curLabs);
        }
        if((flg & 1) != 0)
            renderTick = com.maddox.il2.engine.RendersMain.frame();
    }

    public com.maddox.il2.engine.Actor actor()
    {
        return actor;
    }

    public com.maddox.il2.engine.Actor homeBase()
    {
        com.maddox.il2.engine.Actor actor1 = base;
        if(actor1 == null)
            return null;
        do
        {
            com.maddox.il2.engine.Actor actor2 = actor1.pos.base();
            if(actor2 != null)
                actor1 = actor2;
            else
                return actor1;
        } while(true);
    }

    public com.maddox.il2.engine.Actor base()
    {
        return base;
    }

    public java.lang.Object baseHook()
    {
        return baseHook;
    }

    public java.lang.Object[] getBaseAttached()
    {
        if(baseAttached != null)
            return baseAttached.toArray();
        else
            return null;
    }

    public java.lang.Object[] getBaseAttached(java.lang.Object aobj[])
    {
        if(baseAttached != null)
            return baseAttached.toArray(aobj);
        else
            return null;
    }

    protected java.util.List getListBaseAttached()
    {
        return baseAttached;
    }

    protected void setBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag, boolean flag1, boolean flag2)
    {
        if(actor1 != base || hook != baseHook)
        {
            if(actor1 != null && !com.maddox.il2.engine.Actor.isValid(actor1))
                throw new ActorException("new base is destroyed");
            com.maddox.il2.engine.Actor actor2 = base;
            com.maddox.il2.engine.Hook hook1 = baseHook;
            if(flag && (flg & 1) == 0)
                validate();
            if(actor2 != actor1 && com.maddox.il2.engine.Actor.isValid(actor2))
            {
                actor2.pos.removeChildren(actor);
                com.maddox.il2.engine.MsgBase.detach(actor2, actor);
            }
            base = actor1;
            baseHook = hook;
            if(flag)
            {
                Labs.get(tmpBaseP, tmpBaseO);
                setAbs(tmpBaseP, tmpBaseO);
            } else
            {
                inValidate(true);
            }
            if(actor2 != actor1 && actor1 != null)
            {
                actor1.pos.addChildren(actor);
                com.maddox.il2.engine.MsgBase.attach(actor1, actor);
            }
            if(flag1)
                com.maddox.il2.engine.MsgBase.change(actor, actor1, hook, actor2, hook1);
            int i = actor.flags;
            if((i & 3) == 3)
            {
                if(actor2 == null && actor1 != null)
                    com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor);
                else
                if(actor2 != null && actor1 == null && !flag2)
                    com.maddox.il2.engine.Engine.cur.drawEnv.add(actor);
                setUpdateFlag();
            }
        }
    }

    protected void setBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag, boolean flag1)
    {
        setBase(actor1, hook, flag, flag1, false);
    }

    public void setBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag)
    {
        setBase(actor1, hook, flag, false);
    }

    public void changeBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag)
    {
        setBase(actor1, hook, flag, true);
    }

    public void changeHookToRel()
    {
        if(!com.maddox.il2.engine.Actor.isValid(base))
            throw new ActorException("base is empty or destroyed");
        if(baseHook == null)
            throw new ActorException("hook is empty");
        if((flg & 1) == 0)
            validate();
        baseHook.computePos(base, base.pos.getAbs(), L);
        L.sub(base.pos.getAbs());
        baseHook = null;
    }

    public void getRel(com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public void getRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        L.get(point3d, orient);
    }

    public void getRel(com.maddox.JGP.Point3d point3d)
    {
        L.get(point3d);
    }

    public void getRel(com.maddox.il2.engine.Orient orient)
    {
        L.get(orient);
    }

    public com.maddox.il2.engine.Loc getRel()
    {
        return L;
    }

    public com.maddox.JGP.Point3d getRelPoint()
    {
        return L.getPoint();
    }

    public com.maddox.il2.engine.Orient getRelOrient()
    {
        return L.getOrient();
    }

    public void setRel(com.maddox.il2.engine.Loc loc)
    {
        L.set(loc);
        inValidate(true);
        flg |= 4;
    }

    public void setRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        L.set(point3d, orient);
        inValidate(true);
        flg |= 4;
    }

    public void setRel(com.maddox.JGP.Point3d point3d)
    {
        L.set(point3d);
        inValidate(true);
        flg |= 4;
    }

    public void setRel(com.maddox.il2.engine.Orient orient)
    {
        L.set(orient);
        inValidate(true);
        flg |= 4;
    }

    public void getAbs(com.maddox.il2.engine.Loc loc)
    {
        if((flg & 1) == 0)
            validate();
        loc.set(Labs);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        if((flg & 1) == 0)
            validate();
        Labs.get(point3d, orient);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d)
    {
        if((flg & 1) == 0)
            validate();
        Labs.get(point3d);
    }

    public void getAbs(com.maddox.il2.engine.Orient orient)
    {
        if((flg & 1) == 0)
            validate();
        Labs.get(orient);
    }

    public com.maddox.il2.engine.Loc getAbs()
    {
        if((flg & 1) == 0)
            validate();
        return Labs;
    }

    public com.maddox.JGP.Point3d getAbsPoint()
    {
        if((flg & 1) == 0)
            validate();
        return Labs.getPoint();
    }

    public com.maddox.il2.engine.Orient getAbsOrient()
    {
        if((flg & 1) == 0)
            validate();
        return Labs.getOrient();
    }

    private void setAbsBased(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        if(baseHook != null)
        {
            Labs.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            baseHook.computePos(base, base.pos.getAbs(), Labs);
        } else
        {
            base.pos.getAbs(Labs);
        }
        L.sub(loc, Labs);
        flg |= 4;
        Labs.set(loc);
        inValidateValid(flag);
    }

    public void setAbs(com.maddox.il2.engine.Loc loc)
    {
        if(base == null)
        {
            setRel(loc);
        } else
        {
            boolean flag = (flg & 1) != 0;
            if(!flag)
                validate();
            setAbsBased(loc, flag);
        }
    }

    public void setAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        if(base == null)
        {
            setRel(point3d, orient);
        } else
        {
            boolean flag = (flg & 1) != 0;
            if(!flag)
                validate();
            tmpAbsL.set(point3d, orient);
            setAbsBased(tmpAbsL, flag);
        }
    }

    public void setAbs(com.maddox.JGP.Point3d point3d)
    {
        if(base == null)
        {
            setRel(point3d);
        } else
        {
            boolean flag = (flg & 1) != 0;
            if(!flag)
                validate();
            Labs.get(tmpAbsO);
            tmpAbsL.set(point3d, tmpAbsO);
            setAbsBased(tmpAbsL, flag);
        }
    }

    public void setAbs(com.maddox.il2.engine.Orient orient)
    {
        if(base == null)
        {
            setRel(orient);
        } else
        {
            boolean flag = (flg & 1) != 0;
            if(!flag)
                validate();
            Labs.get(tmpAbsP);
            tmpAbsL.set(tmpAbsP, orient);
            setAbsBased(tmpAbsL, flag);
        }
    }

    public boolean isChanged()
    {
        return (flg & 2) != 0;
    }

    public void getCurrent(com.maddox.il2.engine.Loc loc)
    {
        loc.set(curLabs);
    }

    public void getCurrent(com.maddox.JGP.Point3d point3d)
    {
        curLabs.get(point3d);
    }

    public com.maddox.il2.engine.Loc getCurrent()
    {
        return curLabs;
    }

    public com.maddox.JGP.Point3d getCurrentPoint()
    {
        return curLabs.getPoint();
    }

    public com.maddox.il2.engine.Orient getCurrentOrient()
    {
        return curLabs.getOrient();
    }

    public void getPrev(com.maddox.il2.engine.Loc loc)
    {
        validatePrev();
        loc.set(prevLabs);
    }

    public com.maddox.il2.engine.Loc getPrev()
    {
        validatePrev();
        return prevLabs;
    }

    public com.maddox.il2.engine.Loc getRender()
    {
        validateRender();
        return renderLabs;
    }

    public void getRender(com.maddox.il2.engine.Loc loc)
    {
        validateRender();
        loc.set(renderLabs);
    }

    public void getRender(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        validateRender();
        renderLabs.get(point3d, orient);
    }

    public void getRender(com.maddox.JGP.Point3d point3d)
    {
        validateRender();
        renderLabs.get(point3d);
    }

    public void getTime(long l, com.maddox.il2.engine.Loc loc)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
        {
            validatePrev();
            loc.interpolate(prevLabs, curLabs, ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        } else
        if(l == com.maddox.rts.Time.tick())
            loc.set(curLabs);
        else
        if(l < com.maddox.rts.Time.tick())
        {
            validatePrev();
            loc.interpolate(prevLabs, curLabs, ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        } else
        if(l == com.maddox.rts.Time.tickNext())
        {
            if((flg & 1) == 0)
                validate();
            loc.set(Labs);
        } else
        {
            if((flg & 1) == 0)
                validate();
            loc.interpolate(curLabs, Labs, (float)(l - com.maddox.rts.Time.tick()) / com.maddox.rts.Time.tickLenFms());
        }
    }

    public void getTime(long l, com.maddox.JGP.Point3d point3d)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
        {
            validatePrev();
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        } else
        if(l == com.maddox.rts.Time.tick())
            curLabs.get(point3d);
        else
        if(l < com.maddox.rts.Time.tick())
        {
            validatePrev();
            point3d.interpolate(prevLabs.getPoint(), curLabs.getPoint(), ((float)(l - com.maddox.rts.Time.tick()) + com.maddox.rts.Time.tickLenFms()) / com.maddox.rts.Time.tickLenFms());
        } else
        if(l == com.maddox.rts.Time.tickNext())
        {
            if((flg & 1) == 0)
                validate();
            Labs.get(point3d);
        } else
        {
            if((flg & 1) == 0)
                validate();
            point3d.interpolate(curLabs.getPoint(), Labs.getPoint(), (float)(l - com.maddox.rts.Time.tick()) / com.maddox.rts.Time.tickLenFms());
        }
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
        if((flg & 8) != 0)
        {
            int i = actor.flags;
            if((i & 1) != 0 && ((i & 2) == 0 || base == null))
                com.maddox.il2.engine.Engine.cur.drawEnv.changedPos(actor, curLabs.getPoint(), prevLabs.getPoint());
            if((i & 0x30) == 16)
                com.maddox.il2.engine.Engine.cur.collideEnv.changedPos(actor, curLabs.getPoint(), prevLabs.getPoint());
            if((i & 0x200) == 512)
                com.maddox.il2.engine.Engine.cur.dreamEnv.changedListenerPos(actor, curLabs.getPoint(), prevLabs.getPoint());
            if((i & 0x100) == 256)
                com.maddox.il2.engine.Engine.cur.dreamEnv.changedFirePos(actor, curLabs.getPoint(), prevLabs.getPoint());
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
        curTick = com.maddox.rts.Time.tickCounter();
        renderTick = 0;
    }

    public void reset()
    {
        updateCurrent();
        prevLabs.set(curLabs);
        int i = com.maddox.il2.engine.Engine.cur.posChanged.indexOf(actor);
        if(i >= 0)
            com.maddox.il2.engine.Engine.cur.posChanged.remove(i);
        renderTick = 0;
    }

    protected void updateCurrent()
    {
        com.maddox.il2.engine.Loc loc = prevLabs;
        prevLabs = curLabs;
        curLabs = loc;
        if((flg & 2) != 0)
        {
            getAbs(curLabs);
            if((flg & 8) != 0)
            {
                int i = actor.flags;
                if((i & 1) != 0 && ((i & 2) == 0 || base == null))
                    com.maddox.il2.engine.Engine.cur.drawEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
                if((i & 0x30) == 16)
                    com.maddox.il2.engine.Engine.cur.collideEnv.changedPos(actor, prevLabs.getPoint(), curLabs.getPoint());
                if((i & 0x200) == 512)
                    com.maddox.il2.engine.Engine.cur.dreamEnv.changedListenerPos(actor, prevLabs.getPoint(), curLabs.getPoint());
                if((i & 0x100) == 256)
                    com.maddox.il2.engine.Engine.cur.dreamEnv.changedFirePos(actor, prevLabs.getPoint(), curLabs.getPoint());
            }
            flg &= -7;
        } else
        {
            curLabs.set(prevLabs);
        }
        curTick = com.maddox.rts.Time.tickCounter();
    }

    private void setUpdateFlag()
    {
        boolean flag = false;
        int i = actor.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base == null))
            flag = true;
        if((i & 0x30) == 16)
            flag = true;
        if((i & 0x200) == 512)
            flag = true;
        if((i & 0x100) == 256)
            flag = true;
        if(flag)
            flg |= 8;
        else
            flg &= -9;
    }

    public double speed(com.maddox.JGP.Vector3d vector3d)
    {
        if(com.maddox.il2.engine.InterpolateAdapter.isProcess())
        {
            validatePrev();
            com.maddox.JGP.Point3d point3d = prevLabs.getPoint();
            com.maddox.JGP.Point3d point3d2 = curLabs.getPoint();
            double d = point3d2.x - point3d.x;
            double d2 = point3d2.y - point3d.y;
            double d4 = point3d2.z - point3d.z;
            double d6 = 1.0F / com.maddox.rts.Time.tickLenFs();
            if(vector3d != null)
                vector3d.set(d * d6, d2 * d6, d4 * d6);
            return java.lang.Math.sqrt(d * d + d2 * d2 + d4 * d4) * d6;
        }
        if((flg & 1) == 0)
            validate();
        com.maddox.JGP.Point3d point3d1 = Labs.getPoint();
        com.maddox.JGP.Point3d point3d3 = curLabs.getPoint();
        double d1 = point3d1.x - point3d3.x;
        double d3 = point3d1.y - point3d3.y;
        double d5 = point3d1.z - point3d3.z;
        double d7 = 1.0F / com.maddox.rts.Time.tickLenFs();
        if(vector3d != null)
            vector3d.set(d1 * d7, d3 * d7, d5 * d7);
        return java.lang.Math.sqrt(d1 * d1 + d3 * d3 + d5 * d5) * d7;
    }

    protected void drawingChange(boolean flag)
    {
        if(flag)
        {
            if(actor.isVisibilityAsBase() && base != null)
                com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor);
            else
                com.maddox.il2.engine.Engine.cur.drawEnv.add(actor);
        } else
        {
            com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor);
        }
        setUpdateFlag();
    }

    protected void collideChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.collideEnv.add(actor);
        else
            com.maddox.il2.engine.Engine.cur.collideEnv.remove(actor);
        setUpdateFlag();
    }

    protected void dreamFireChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.dreamEnv().addFire(actor);
        else
            com.maddox.il2.engine.Engine.dreamEnv().removeFire(actor);
        setUpdateFlag();
    }

    protected void addChildren(com.maddox.il2.engine.Actor actor1)
    {
        if(baseAttached == null)
            baseAttached = new ArrayList();
        baseAttached.add(actor1);
    }

    protected void removeChildren(com.maddox.il2.engine.Actor actor1)
    {
        baseAttached.remove(baseAttached.indexOf(actor1));
    }

    public void destroy()
    {
        if(actor == null)
            return;
        clearEnvs(actor);
        if(baseAttached != null)
            while(baseAttached.size() > 0) 
            {
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)baseAttached.get(0);
                if(actor1 == actor1.pos.actor())
                    actor1.pos.changeBase(null, null, true);
                else
                    baseAttached.remove(0);
            }
        setBase(null, null, true, true, true);
        actor = null;
    }

    protected ActorPosMove()
    {
        actor = null;
        base = null;
        baseHook = null;
        baseAttached = null;
        L = new Loc();
        Labs = new Loc();
        flg = 0;
        curTick = 0;
        curLabs = new Loc();
        prevLabs = new Loc();
        renderLabs = new Loc();
        renderTick = -1;
    }

    public ActorPosMove(com.maddox.il2.engine.Actor actor1)
    {
        actor = null;
        base = null;
        baseHook = null;
        baseAttached = null;
        L = new Loc();
        Labs = new Loc();
        flg = 0;
        curTick = 0;
        curLabs = new Loc();
        prevLabs = new Loc();
        renderLabs = new Loc();
        renderTick = -1;
        actor = actor1;
        initEnvs(actor);
        setUpdateFlag();
    }

    public ActorPosMove(com.maddox.il2.engine.Actor actor1, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        actor = null;
        base = null;
        baseHook = null;
        baseAttached = null;
        L = new Loc();
        Labs = new Loc();
        flg = 0;
        curTick = 0;
        curLabs = new Loc();
        prevLabs = new Loc();
        renderLabs = new Loc();
        renderTick = -1;
        actor = actor1;
        L.set(point3d, orient);
        curLabs.set(L);
        prevLabs.set(L);
        initEnvs(actor);
        setUpdateFlag();
    }

    public ActorPosMove(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Loc loc)
    {
        actor = null;
        base = null;
        baseHook = null;
        baseAttached = null;
        L = new Loc();
        Labs = new Loc();
        flg = 0;
        curTick = 0;
        curLabs = new Loc();
        prevLabs = new Loc();
        renderLabs = new Loc();
        renderTick = -1;
        actor = actor1;
        L.set(loc);
        curLabs.set(L);
        prevLabs.set(L);
        initEnvs(actor);
        setUpdateFlag();
    }

    public ActorPosMove(com.maddox.il2.engine.ActorPos actorpos)
    {
        this(actorpos.actor(), actorpos.getAbs());
        java.lang.Object aobj[] = actorpos.getBaseAttached();
        if(aobj != null && aobj.length > 0)
        {
            baseAttached = new ArrayList();
            for(int i = 0; i < aobj.length; i++)
                baseAttached.add(aobj[i]);

        }
        setUpdateFlag();
    }

    private static final boolean DEBUG_REFERENCE = false;
    private static final int SIZE_REFERENCES = 256;
    private static int curReference = 0;
    private static com.maddox.il2.engine.Loc bufReference[] = new com.maddox.il2.engine.Loc[256];
    private com.maddox.il2.engine.Actor actor;
    protected com.maddox.il2.engine.Actor base;
    protected com.maddox.il2.engine.Hook baseHook;
    private java.util.List baseAttached;
    protected com.maddox.il2.engine.Loc L;
    protected com.maddox.il2.engine.Loc Labs;
    public static final int ABS_VALID = 1;
    public static final int ABS_CHANGED = 2;
    public static final int REL_CHANGED = 4;
    public static final int UPDATE_ENV = 8;
    public static final int UPDATE_DISABLED = 16;
    protected int flg;
    protected int curTick;
    protected com.maddox.il2.engine.Loc curLabs;
    protected com.maddox.il2.engine.Loc prevLabs;
    protected com.maddox.il2.engine.Loc renderLabs;
    protected int renderTick;
    protected static com.maddox.JGP.Point3d tmpBaseP = new Point3d();
    protected static com.maddox.il2.engine.Orient tmpBaseO = new Orient();
    private static com.maddox.il2.engine.Loc tmpAbsL = new Loc();
    private static com.maddox.JGP.Point3d tmpAbsP = new Point3d();
    private static com.maddox.il2.engine.Orient tmpAbsO = new Orient();

}
