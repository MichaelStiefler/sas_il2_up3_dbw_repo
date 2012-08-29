// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Eff3DActor.java

package com.maddox.il2.engine;

import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Spawn;
import com.maddox.rts.State;
import com.maddox.rts.States;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Eff3D, ActorPosStaticEff3D, ActorPosMove, 
//            Loc, ActorPosMoveInit, MsgBaseListener, Config, 
//            ActorPos, Engine, Hook, ActorSpawn, 
//            ActorSpawnArg

public class Eff3DActor extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.engine.MsgBaseListener
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                return null;
            } else
            {
                com.maddox.il2.engine.Loc loc = actorspawnarg.getAbsLoc();
                com.maddox.il2.engine.Eff3D.spawnSetCommonFields(actorspawnarg, loc);
                com.maddox.il2.engine.Eff3D eff3d = com.maddox.il2.engine.Eff3D.New();
                com.maddox.il2.engine.Eff3DActor eff3dactor = eff3d.NewActor(loc);
                actorspawnarg.set(eff3dactor);
                return eff3dactor;
            }
        }

        public SPAWN()
        {
        }
    }

    public class Finish extends com.maddox.rts.State
    {

        public void begin(int i)
        {
            float f = ((com.maddox.il2.engine.Eff3D)draw).timeFinish();
            boolean flag = ((com.maddox.il2.engine.Eff3D)draw).isTimeReal();
            if(f <= 0.0F)
            {
                destroy();
                return;
            } else
            {
                ((com.maddox.il2.engine.Eff3D)draw).finish();
                com.maddox.rts.MsgDestroy.Post(flag ? 64 : 0, (flag ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current()) + (long)(f * 1000F), superObj());
                return;
            }
        }

        public Finish(java.lang.Object obj)
        {
            super(obj);
        }
    }

    public class Ready extends com.maddox.rts.State
    {

        public void begin(int i)
        {
            float f = ((com.maddox.il2.engine.Eff3D)draw).timeLife();
            boolean flag = ((com.maddox.il2.engine.Eff3D)draw).isTimeReal();
            if(f >= 0.0F)
                new com.maddox.rts.MsgAction(flag ? 64 : 0, (flag ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current()) + (long)(f * 1000F)) {

                    public void doAction(java.lang.Object obj)
                    {
                        if(states != null && states.getState() == 0)
                            states.setState(1);
                    }

                }
;
        }


        public Ready(java.lang.Object obj)
        {
            super(obj);
        }
    }


    public boolean isUseIntensityAsSwitchDraw()
    {
        return bUseIntensityAsSwitchDraw;
    }

    public void setUseIntensityAsSwitchDraw(boolean flag)
    {
        bUseIntensityAsSwitchDraw = flag;
    }

    public void msgBaseAttach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void msgBaseDetach(com.maddox.il2.engine.Actor actor)
    {
    }

    public void msgBaseChange(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook1)
    {
        if(actor == null && actor1 != null && actor1.isDestroyed())
            _finish();
    }

    public void _finish()
    {
        ((com.maddox.il2.engine.Eff3D)draw).setIntesity(0.0F);
        states.setState(1);
    }

    public static void finish(com.maddox.il2.engine.Eff3DActor eff3dactor)
    {
        if(com.maddox.il2.engine.Actor.isValid(eff3dactor))
            eff3dactor._finish();
    }

    public void _setIntesity(float f)
    {
        if(states.getState() == 0)
        {
            ((com.maddox.il2.engine.Eff3D)draw).setIntesity(f);
            if(bUseIntensityAsSwitchDraw)
            {
                if(f != 0.0F)
                {
                    drawing(true);
                    syncObj = null;
                    return;
                }
                float f1 = ((com.maddox.il2.engine.Eff3D)draw).timeFinish();
                if(f1 <= 0.0F)
                {
                    drawing(false);
                } else
                {
                    boolean flag = ((com.maddox.il2.engine.Eff3D)draw).isTimeReal();
                    syncObj = new Object();
                    new com.maddox.rts.MsgAction(flag ? 64 : 0, (flag ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current()) + (long)(f1 * 1000F), syncObj) {

                        public void doAction(java.lang.Object obj)
                        {
                            if(obj != syncObj || states.getState() != 0)
                            {
                                return;
                            } else
                            {
                                drawing(false);
                                return;
                            }
                        }

                    }
;
                }
            }
        }
    }

    public static void setIntesity(com.maddox.il2.engine.Eff3DActor eff3dactor, float f)
    {
        if(com.maddox.il2.engine.Actor.isValid(eff3dactor))
            eff3dactor._setIntesity(f);
    }

    public float _getIntesity()
    {
        if(states.getState() == 0)
            return ((com.maddox.il2.engine.Eff3D)draw).getIntesity();
        else
            return 0.0F;
    }

    public static float getIntesity(com.maddox.il2.engine.Eff3DActor eff3dactor)
    {
        if(com.maddox.il2.engine.Actor.isValid(eff3dactor))
            return eff3dactor._getIntesity();
        else
            return 0.0F;
    }

    public static com.maddox.il2.engine.Eff3DActor New(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Loc loc, float f, java.lang.String s, float f1)
    {
        return com.maddox.il2.engine.Eff3DActor.New(actor, hook, loc, f, s, f1, false);
    }

    public static com.maddox.il2.engine.Eff3DActor New(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Loc loc, float f, java.lang.String s, float f1, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return null;
        apos.setBase(actor, hook, false);
        if(loc != null)
            apos.setRel(loc);
        apos.resetAsBase();
        apos.getRender(lres);
        apos.setBase(null, null, false);
        if(loc != null)
            apos.setRel(lempty);
        com.maddox.il2.engine.Eff3DActor eff3dactor = com.maddox.il2.engine.Eff3DActor.NewPosMove(lres, f, s, f1);
        eff3dactor.pos.setBase(actor, hook, false);
        if(loc != null)
            eff3dactor.pos.setRel(loc);
        else
            eff3dactor.pos.setRel(lempty);
        eff3dactor.pos.resetAsBase();
        if(flag)
        {
            eff3dactor.pos.changeHookToRel();
            eff3dactor.pos.setUpdateEnable(false);
        }
        return eff3dactor;
    }

    public static com.maddox.il2.engine.Eff3DActor NewPosMove(com.maddox.il2.engine.Loc loc, float f, java.lang.String s, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return null;
        } else
        {
            com.maddox.il2.engine.Eff3D.initSetLocator(loc);
            com.maddox.il2.engine.Eff3D.initSetSize(f);
            com.maddox.il2.engine.Eff3D.initSetParamFileName(s);
            com.maddox.il2.engine.Eff3D.initSetProcessTime(f1);
            com.maddox.il2.engine.Eff3D eff3d = com.maddox.il2.engine.Eff3D.New();
            _isStaticPos = false;
            com.maddox.il2.engine.Eff3DActor eff3dactor = eff3d.NewActor(loc);
            _isStaticPos = true;
            return eff3dactor;
        }
    }

    public static com.maddox.il2.engine.Eff3DActor New(com.maddox.il2.engine.Loc loc, float f, java.lang.String s, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return null;
        } else
        {
            com.maddox.il2.engine.Eff3D.initSetLocator(loc);
            com.maddox.il2.engine.Eff3D.initSetSize(f);
            com.maddox.il2.engine.Eff3D.initSetParamFileName(s);
            com.maddox.il2.engine.Eff3D.initSetProcessTime(f1);
            com.maddox.il2.engine.Eff3D eff3d = com.maddox.il2.engine.Eff3D.New();
            return eff3d.NewActor(loc);
        }
    }

    public static com.maddox.il2.engine.Eff3DActor New(com.maddox.il2.engine.ActorPos actorpos, float f, java.lang.String s, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return null;
        } else
        {
            com.maddox.il2.engine.Eff3D.initSetLocator(actorpos.getAbs());
            com.maddox.il2.engine.Eff3D.initSetSize(f);
            com.maddox.il2.engine.Eff3D.initSetParamFileName(s);
            com.maddox.il2.engine.Eff3D.initSetProcessTime(f1);
            com.maddox.il2.engine.Eff3D eff3d = com.maddox.il2.engine.Eff3D.New();
            return eff3d.NewActor(actorpos);
        }
    }

    protected Eff3DActor(com.maddox.il2.engine.Eff3D eff3d, com.maddox.il2.engine.Loc loc)
    {
        bUseIntensityAsSwitchDraw = false;
        syncObj = null;
        draw = eff3d;
        if(_isStaticPos)
            pos = new ActorPosStaticEff3D(this, loc);
        else
            pos = new ActorPosMove(this, loc);
        states = new States(new java.lang.Object[] {
            new Ready(this), new Finish(this)
        });
        states.setState(0);
        drawing(true);
        com.maddox.il2.engine.Engine.cur.allEff3DActors.put(this, null);
    }

    protected Eff3DActor(com.maddox.il2.engine.Eff3D eff3d, com.maddox.il2.engine.ActorPos actorpos)
    {
        bUseIntensityAsSwitchDraw = false;
        syncObj = null;
        draw = eff3d;
        pos = actorpos;
        states = new States(new java.lang.Object[] {
            new Ready(this), new Finish(this)
        });
        states.setState(0);
        flags |= 3;
        actorpos.base().pos.addChildren(this);
        com.maddox.il2.engine.Engine.cur.allEff3DActors.put(this, null);
    }

    protected Eff3DActor()
    {
        bUseIntensityAsSwitchDraw = false;
        syncObj = null;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        com.maddox.il2.engine.Engine.cur.allEff3DActors.remove(this);
        super.destroy();
        draw = null;
        if(pos instanceof com.maddox.il2.engine.ActorPosStaticEff3D)
            pos = null;
        syncObj = null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int STATE_READY = 0;
    public static final int STATE_FINISH = 1;
    protected boolean bUseIntensityAsSwitchDraw;
    protected java.lang.Object syncObj;
    private static com.maddox.il2.engine.Loc lres = new Loc();
    private static com.maddox.il2.engine.Loc lempty = new Loc();
    private static com.maddox.il2.engine.ActorPosMoveInit apos = new ActorPosMoveInit();
    protected static boolean _isStaticPos = true;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.engine.Eff3DActor.class, new SPAWN());
    }
}
