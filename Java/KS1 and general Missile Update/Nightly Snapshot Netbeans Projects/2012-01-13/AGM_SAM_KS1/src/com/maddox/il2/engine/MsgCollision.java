// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode 
// Source File Name:   MsgCollision.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;

// Referenced classes of package com.maddox.il2.engine:
//            MsgCollisionListener, Actor

public class MsgCollision extends Message
{

    public MsgCollision()
    {
        thisChunk = null;
        otherChunk = null;
    }

    public static void post(long l, Actor actor, Actor actor1, String s, String s1)
    {
        com.maddox.il2.ai.EventLog.type("MsgCollision post a1="
                + actor.getClass().getName()
                + " a2="
                + actor1.getClass().getName()
                + " s=" + s
                + " s1=" + s1);
        MsgCollision msgcollision = (MsgCollision)cache.get();
        msgcollision.thisChunk = s;
        msgcollision.otherChunk = s1;
        msgcollision.post(actor, l, actor1);
    }

    public static void post2(long l, Actor actor, Actor actor1, String s, String s1)
    {
        com.maddox.il2.ai.EventLog.type("MsgCollision post2 a1="
                + actor.getClass().getName()
                + " a2="
                + actor1.getClass().getName()
                + " s=" + s
                + " s1=" + s1);
        Object obj = null;
        boolean flag = (actor.flags & 0x80) != 0;
        boolean flag1 = (actor1.flags & 0x80) != 0;
        if(!flag && !flag1)
        {
            MsgCollision msgcollision = (MsgCollision)cache.get();
            msgcollision.thisChunk = s;
            msgcollision.otherChunk = s1;
            msgcollision.post(actor, l, actor1);
            msgcollision = (MsgCollision)cache.get();
            msgcollision.thisChunk = s1;
            msgcollision.otherChunk = s;
            msgcollision.post(actor1, l, actor);
            return;
        }
        if(flag && flag1)
            if((actor1.flags & 0x20) != 0)
                flag = false;
            else
                flag1 = false;
        if(flag)
        {
            MsgCollision msgcollision1 = (MsgCollision)cache.get();
            msgcollision1.thisChunk = s;
            msgcollision1.otherChunk = s1;
            msgcollision1.post(actor, l, actor1);
        }
        if(flag1)
        {
            MsgCollision msgcollision2 = (MsgCollision)cache.get();
            msgcollision2.thisChunk = s1;
            msgcollision2.otherChunk = s;
            msgcollision2.post(actor1, l, actor);
        }
    }

    public void clean()
    {
        thisChunk = null;
        otherChunk = null;
        super.clean();
    }

    public boolean invokeListener(Object obj)
    {
        if(obj instanceof MsgCollisionListener)
        {
            ((MsgCollisionListener)obj).msgCollision((Actor)_sender, thisChunk, otherChunk);
            com.maddox.il2.ai.EventLog.type("MsgCollision invokeListener " + _sender.getClass().getName());
            return true;
        } else
        {
            return false;
        }
    }

    public String thisChunk;
    public String otherChunk;
    private static MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.il2.engine.MsgCollision.class);
    }
}
