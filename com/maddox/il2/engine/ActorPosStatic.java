// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorPosStatic.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            ActorPos, Actor, ActorException, Loc, 
//            Engine, DrawEnv, CollideEnv, DreamEnv, 
//            Orient

public class ActorPosStatic extends com.maddox.il2.engine.ActorPos
{

    public com.maddox.il2.engine.Actor actor()
    {
        return actor;
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

    public void inValidate(boolean flag)
    {
        if(baseAttached != null)
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

    public void setBase(com.maddox.il2.engine.Actor actor1, java.lang.Object obj, long l, boolean flag)
    {
        throw new ActorException("static position not changed base");
    }

    public void changeBase(com.maddox.il2.engine.Actor actor1, java.lang.Object obj, long l, boolean flag)
    {
        throw new ActorException("static position not changed base");
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

    protected void changePosInEnvs(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        inValidate(true);
        int i = actor.flags;
        if((i & 1) == 1)
            com.maddox.il2.engine.Engine.cur.drawEnv.changedPosStatic(actor, point3d, point3d1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.changedPosStatic(actor, point3d, point3d1);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.changedListenerPos(actor, point3d, point3d1);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.cur.dreamEnv.changedFirePos(actor, point3d, point3d1);
    }

    public void setRel(com.maddox.il2.engine.Loc loc)
    {
        changePosInEnvs(L.getPoint(), loc.getPoint());
        L.set(loc);
    }

    public void setRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        changePosInEnvs(L.getPoint(), point3d);
        L.set(point3d, orient);
    }

    public void setRel(com.maddox.JGP.Point3d point3d)
    {
        changePosInEnvs(L.getPoint(), point3d);
        L.set(point3d);
    }

    public void setRel(com.maddox.il2.engine.Orient orient)
    {
        L.set(orient);
    }

    public void getAbs(com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        L.get(point3d, orient);
    }

    public void getAbs(com.maddox.JGP.Point3d point3d)
    {
        L.get(point3d);
    }

    public void getAbs(com.maddox.il2.engine.Orient orient)
    {
        L.get(orient);
    }

    public com.maddox.il2.engine.Loc getAbs()
    {
        return L;
    }

    public com.maddox.JGP.Point3d getAbsPoint()
    {
        return L.getPoint();
    }

    public com.maddox.il2.engine.Orient getAbsOrient()
    {
        return L.getOrient();
    }

    public void setAbs(com.maddox.il2.engine.Loc loc)
    {
        setRel(loc);
    }

    public void setAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        setRel(point3d, orient);
    }

    public void setAbs(com.maddox.JGP.Point3d point3d)
    {
        setRel(point3d);
    }

    public void setAbs(com.maddox.il2.engine.Orient orient)
    {
        setRel(orient);
    }

    public void reset()
    {
    }

    public void resetAsBase()
    {
    }

    public boolean isChanged()
    {
        return false;
    }

    public void getCurrent(com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public void getCurrent(com.maddox.JGP.Point3d point3d)
    {
        L.get(point3d);
    }

    public com.maddox.il2.engine.Loc getCurrent()
    {
        return L;
    }

    public com.maddox.JGP.Point3d getCurrentPoint()
    {
        return L.getPoint();
    }

    public com.maddox.il2.engine.Orient getCurrentOrient()
    {
        return L.getOrient();
    }

    public void getPrev(com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public com.maddox.il2.engine.Loc getPrev()
    {
        return L;
    }

    protected void drawingChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.drawEnv.addStatic(actor);
        else
            com.maddox.il2.engine.Engine.cur.drawEnv.removeStatic(actor);
    }

    protected void collideChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.collideEnv.addStatic(actor);
        else
            com.maddox.il2.engine.Engine.cur.collideEnv.removeStatic(actor);
    }

    protected void dreamFireChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.dreamEnv().addFire(actor);
        else
            com.maddox.il2.engine.Engine.dreamEnv().removeFire(actor);
    }

    public com.maddox.il2.engine.Loc getRender()
    {
        return L;
    }

    public void getRender(com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public void getRender(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        L.get(point3d, orient);
    }

    public void getRender(com.maddox.JGP.Point3d point3d)
    {
        L.get(point3d);
    }

    public void getTime(long l, com.maddox.il2.engine.Loc loc)
    {
        loc.set(L);
    }

    public void getTime(long l, com.maddox.JGP.Point3d point3d)
    {
        L.get(point3d);
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

    protected void clearEnvs(com.maddox.il2.engine.Actor actor1)
    {
        int i = actor1.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base() == null))
            com.maddox.il2.engine.Engine.cur.drawEnv.removeStatic(actor1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.removeStatic(actor1);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.removeListener(actor1);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.dreamEnv().removeFire(actor1);
        int j = com.maddox.il2.engine.Engine.cur.posChanged.indexOf(actor1);
        if(j >= 0)
            com.maddox.il2.engine.Engine.cur.posChanged.remove(j);
    }

    protected void initEnvs(com.maddox.il2.engine.Actor actor1)
    {
        actor1.pos = this;
        int i = actor1.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base() == null))
            com.maddox.il2.engine.Engine.cur.drawEnv.addStatic(actor1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.addStatic(actor1);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.addListener(actor1);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.dreamEnv().addFire(actor1);
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
        actor = null;
    }

    public ActorPosStatic(com.maddox.il2.engine.Actor actor1)
    {
        actor = null;
        baseAttached = null;
        L = new Loc();
        actor = actor1;
        initEnvs(actor);
    }

    public ActorPosStatic(com.maddox.il2.engine.Actor actor1, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        actor = null;
        baseAttached = null;
        L = new Loc();
        actor = actor1;
        L.set(point3d, orient);
        initEnvs(actor);
    }

    public ActorPosStatic(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Loc loc)
    {
        actor = null;
        baseAttached = null;
        L = new Loc();
        actor = actor1;
        L.set(loc);
        initEnvs(actor);
    }

    private com.maddox.il2.engine.Actor actor;
    private java.util.List baseAttached;
    private com.maddox.il2.engine.Loc L;
}
