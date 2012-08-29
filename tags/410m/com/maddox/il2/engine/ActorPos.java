// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorPos.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Hook, ActorException, Engine, 
//            DrawEnv, CollideEnv, DreamEnv, Loc, 
//            Orient

public abstract class ActorPos
    implements com.maddox.rts.Destroy
{

    public ActorPos()
    {
    }

    public void inValidate()
    {
        inValidate(true);
    }

    public void inValidate(boolean flag)
    {
    }

    public void setUpdateEnable(boolean flag)
    {
    }

    public boolean isUpdateEnable()
    {
        return true;
    }

    public com.maddox.il2.engine.Actor actor()
    {
        return null;
    }

    public void actorChanged()
    {
        com.maddox.il2.engine.Actor actor1 = actor();
        if(!com.maddox.il2.engine.Actor.isValid(actor1))
            return;
        java.lang.Object aobj[] = getBaseAttached();
        if(aobj == null)
            return;
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor2 == null)
                break;
            if(com.maddox.il2.engine.Actor.isValid(actor2) && actor2.pos.actor() == actor2 && actor2.pos.baseHook() != null && (actor2.pos.baseHook() instanceof com.maddox.il2.engine.Hook))
                ((com.maddox.il2.engine.Hook)(com.maddox.il2.engine.Hook)actor2.pos.baseHook()).baseChanged(actor1);
        }

        inValidate(false);
    }

    public com.maddox.il2.engine.Actor homeBase()
    {
        return null;
    }

    public com.maddox.il2.engine.Actor base()
    {
        return null;
    }

    public java.lang.Object baseHook()
    {
        return null;
    }

    public java.lang.Object[] getBaseAttached()
    {
        return null;
    }

    public java.lang.Object[] getBaseAttached(java.lang.Object aobj[])
    {
        return null;
    }

    protected java.util.List getListBaseAttached()
    {
        return null;
    }

    public void setBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag)
    {
        throw new ActorException("method 'setBase' not implemented");
    }

    public void changeBase(com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, boolean flag)
    {
        throw new ActorException("method 'changeBase' not implemented");
    }

    public void changeHookToRel()
    {
        throw new ActorException("method 'changeHookToRel' not implemented");
    }

    public void getRel(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getRel' not implemented");
    }

    public void getRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'getRel' not implemented");
    }

    public void getRel(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'getRel' not implemented");
    }

    public void getRel(com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'getRel' not implemented");
    }

    public com.maddox.il2.engine.Loc getRel()
    {
        throw new ActorException("method 'getRel' not implemented");
    }

    public com.maddox.JGP.Point3d getRelPoint()
    {
        throw new ActorException("method 'getRelPoint' not implemented");
    }

    public com.maddox.il2.engine.Orient getRelOrient()
    {
        throw new ActorException("method 'getRelOrient' not implemented");
    }

    public void setRel(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'setRel' not implemented");
    }

    public void setRel(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'setRel' not implemented");
    }

    public void setRel(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'setRel' not implemented");
    }

    public void setRel(com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'setRel' not implemented");
    }

    public void getAbs(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getAbs' not implemented");
    }

    public void getAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'getAbs' not implemented");
    }

    public void getAbs(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'getAbs' not implemented");
    }

    public void getAbs(com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'getAbs' not implemented");
    }

    public com.maddox.il2.engine.Loc getAbs()
    {
        throw new ActorException("method 'getAbs' not implemented");
    }

    public com.maddox.JGP.Point3d getAbsPoint()
    {
        throw new ActorException("method 'getAbsPoint' not implemented");
    }

    public com.maddox.il2.engine.Orient getAbsOrient()
    {
        throw new ActorException("method 'getAbsOrient' not implemented");
    }

    public void setAbs(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'setAbs' not implemented");
    }

    public void setAbs(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'setAbs' not implemented");
    }

    public void setAbs(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'setAbs' not implemented");
    }

    public void setAbs(com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'setAbs' not implemented");
    }

    public void reset()
    {
        throw new ActorException("method 'reset' not implemented");
    }

    public void resetAsBase()
    {
        throw new ActorException("methodAs 'reset' not implemented");
    }

    public boolean isChanged()
    {
        throw new ActorException("method 'isChanged' not implemented");
    }

    public void getCurrent(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getCurrent' not implemented");
    }

    public void getCurrent(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'getCurrent' not implemented");
    }

    public com.maddox.il2.engine.Loc getCurrent()
    {
        throw new ActorException("method 'getCurrent' not implemented");
    }

    public com.maddox.JGP.Point3d getCurrentPoint()
    {
        throw new ActorException("method 'getCurrentPoint' not implemented");
    }

    public com.maddox.il2.engine.Orient getCurrentOrient()
    {
        throw new ActorException("method 'getCurrentOrient' not implemented");
    }

    public void getPrev(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getPrev' not implemented");
    }

    public com.maddox.il2.engine.Loc getPrev()
    {
        throw new ActorException("method 'getPrev' not implemented");
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

    public com.maddox.il2.engine.Loc getRender()
    {
        throw new ActorException("method 'getRender' not implemented");
    }

    public void getRender(com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getRender' not implemented");
    }

    public void getRender(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        throw new ActorException("method 'getRender' not implemented");
    }

    public void getRender(com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'getRender' not implemented");
    }

    public void getTime(long l, com.maddox.il2.engine.Loc loc)
    {
        throw new ActorException("method 'getTime' not implemented");
    }

    public void getTime(long l, com.maddox.JGP.Point3d point3d)
    {
        throw new ActorException("method 'getTime' not implemented");
    }

    protected void updateCurrent()
    {
        throw new ActorException("method 'updateCurrent' not implemented");
    }

    public double speed(com.maddox.JGP.Vector3d vector3d)
    {
        if(vector3d != null)
            vector3d.set(0.0D, 0.0D, 0.0D);
        return 0.0D;
    }

    protected void addChildren(com.maddox.il2.engine.Actor actor1)
    {
        throw new ActorException("method 'addChildren' not implemented");
    }

    protected void removeChildren(com.maddox.il2.engine.Actor actor1)
    {
        throw new ActorException("method 'removeChildren' not implemented");
    }

    public boolean isDestroyed()
    {
        return actor() == null;
    }

    public void destroy()
    {
    }

    protected void clearEnvs(com.maddox.il2.engine.Actor actor1)
    {
        int i = actor1.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base() == null))
            com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.remove(actor1);
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
            com.maddox.il2.engine.Engine.cur.drawEnv.add(actor1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.add(actor1);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.addListener(actor1);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.dreamEnv().addFire(actor1);
    }
}
