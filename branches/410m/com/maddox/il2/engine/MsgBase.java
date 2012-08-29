// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgBase.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            MsgBaseListener, Actor, Hook

public class MsgBase extends com.maddox.rts.Message
{

    public MsgBase()
    {
        _id = -1;
        _hook = null;
        _oldBase = null;
        _oldHook = null;
    }

    public void clean()
    {
        super.clean();
        _hook = null;
        _oldBase = null;
        _oldHook = null;
    }

    protected static void attach(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(com.maddox.rts.RTSConf.isResetGame())
            return;
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgBase.cacheGet(0, null, null, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
            com.maddox.il2.engine.MsgBase.cacheGet(0, null, null, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void detach(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(com.maddox.rts.RTSConf.isResetGame())
            return;
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgBase.cacheGet(1, null, null, null).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
            com.maddox.il2.engine.MsgBase.cacheGet(1, null, null, null).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    protected static void change(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Actor actor2, com.maddox.il2.engine.Hook hook1)
    {
        if(com.maddox.rts.RTSConf.isResetGame())
            return;
        if(actor.isRealTime())
            com.maddox.il2.engine.MsgBase.cacheGet(2, hook, actor2, hook1).post(64, actor, com.maddox.rts.Time.currentReal(), actor1);
        else
            com.maddox.il2.engine.MsgBase.cacheGet(2, hook, actor2, hook1).post(0, actor, com.maddox.rts.Time.current(), actor1);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgBaseListener)
        {
            switch(_id)
            {
            case 0: // '\0'
                ((com.maddox.il2.engine.MsgBaseListener)obj).msgBaseAttach((com.maddox.il2.engine.Actor)_sender);
                break;

            case 1: // '\001'
                ((com.maddox.il2.engine.MsgBaseListener)obj).msgBaseDetach((com.maddox.il2.engine.Actor)_sender);
                break;

            case 2: // '\002'
                ((com.maddox.il2.engine.MsgBaseListener)obj).msgBaseChange((com.maddox.il2.engine.Actor)_sender, _hook, _oldBase, _oldHook);
                break;
            }
            return true;
        } else
        {
            return false;
        }
    }

    private static com.maddox.il2.engine.MsgBase cacheGet(int i, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook1)
    {
        com.maddox.il2.engine.MsgBase msgbase = (com.maddox.il2.engine.MsgBase)cache.get();
        msgbase._id = i;
        msgbase._hook = hook;
        msgbase._oldBase = actor;
        msgbase._oldHook = hook1;
        return msgbase;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int _id;
    private com.maddox.il2.engine.Hook _hook;
    private com.maddox.il2.engine.Actor _oldBase;
    private com.maddox.il2.engine.Hook _oldHook;
    public static final int ATTACH = 0;
    public static final int DETACH = 1;
    public static final int CHANGE = 2;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.il2.engine.MsgBase.class);
    }
}
