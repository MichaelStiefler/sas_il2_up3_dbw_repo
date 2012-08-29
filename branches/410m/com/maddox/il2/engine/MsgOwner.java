// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgOwner.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            MsgOwnerListener, Actor

public class MsgOwner extends com.maddox.rts.Message
{

    public MsgOwner()
    {
        _id = -1;
        _oldOwner = null;
    }

    public void clean()
    {
        super.clean();
        _oldOwner = null;
    }

    protected static void attach(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgOwner.cacheGet(0, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
        if(!com.maddox.rts.RTSConf.isResetGame())
            com.maddox.il2.engine.MsgOwner.cacheGet(0, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void detach(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgOwner.cacheGet(1, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
        if(!com.maddox.rts.RTSConf.isResetGame())
            com.maddox.il2.engine.MsgOwner.cacheGet(1, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void died(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgOwner.cacheGet(3, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
        if(!com.maddox.rts.RTSConf.isResetGame())
            com.maddox.il2.engine.MsgOwner.cacheGet(3, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void taskComplete(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgOwner.cacheGet(4, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
        if(!com.maddox.rts.RTSConf.isResetGame())
            com.maddox.il2.engine.MsgOwner.cacheGet(4, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void change(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Actor actor2)
    {
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgOwner.cacheGet(2, actor2).post(64, actor1, com.maddox.rts.Time.currentReal(), actor);
        else
        if(!com.maddox.rts.RTSConf.isResetGame())
            com.maddox.il2.engine.MsgOwner.cacheGet(2, actor2).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgOwnerListener)
        {
            switch(_id)
            {
            case 0: // '\0'
                ((com.maddox.il2.engine.MsgOwnerListener)obj).msgOwnerAttach((com.maddox.il2.engine.Actor)_sender);
                break;

            case 1: // '\001'
                ((com.maddox.il2.engine.MsgOwnerListener)obj).msgOwnerDetach((com.maddox.il2.engine.Actor)_sender);
                break;

            case 3: // '\003'
                ((com.maddox.il2.engine.MsgOwnerListener)obj).msgOwnerDied((com.maddox.il2.engine.Actor)_sender);
                break;

            case 4: // '\004'
                ((com.maddox.il2.engine.MsgOwnerListener)obj).msgOwnerTaskComplete((com.maddox.il2.engine.Actor)_sender);
                break;

            case 2: // '\002'
                ((com.maddox.il2.engine.MsgOwnerListener)obj).msgOwnerChange((com.maddox.il2.engine.Actor)_sender, _oldOwner);
                break;
            }
            return true;
        } else
        {
            return false;
        }
    }

    private static com.maddox.il2.engine.MsgOwner cacheGet(int i, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.MsgOwner msgowner = (com.maddox.il2.engine.MsgOwner)cache.get();
        msgowner._id = i;
        msgowner._oldOwner = actor;
        return msgowner;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int _id;
    private com.maddox.il2.engine.Actor _oldOwner;
    private static final int ATTACH = 0;
    private static final int DETACH = 1;
    private static final int CHANGE = 2;
    private static final int DEAD = 3;
    private static final int TASK_COMPLETE = 4;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.il2.engine.MsgOwner.class);
    }
}
