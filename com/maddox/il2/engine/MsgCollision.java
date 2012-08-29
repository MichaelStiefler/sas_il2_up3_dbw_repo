// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgCollision.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;

// Referenced classes of package com.maddox.il2.engine:
//            MsgCollisionListener, Actor

public class MsgCollision extends com.maddox.rts.Message
{

    public MsgCollision()
    {
        thisChunk = null;
        otherChunk = null;
    }

    public static void post(long l, com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.il2.engine.MsgCollision msgcollision = (com.maddox.il2.engine.MsgCollision)cache.get();
        msgcollision.thisChunk = s;
        msgcollision.otherChunk = s1;
        msgcollision.post(actor, l, actor1);
    }

    public static void post2(long l, com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, java.lang.String s, java.lang.String s1)
    {
        Object obj = null;
        boolean flag = (actor.flags & 0x80) != 0;
        boolean flag1 = (actor1.flags & 0x80) != 0;
        if(!flag && !flag1)
        {
            com.maddox.il2.engine.MsgCollision msgcollision = (com.maddox.il2.engine.MsgCollision)cache.get();
            msgcollision.thisChunk = s;
            msgcollision.otherChunk = s1;
            msgcollision.post(actor, l, actor1);
            msgcollision = (com.maddox.il2.engine.MsgCollision)cache.get();
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
            com.maddox.il2.engine.MsgCollision msgcollision1 = (com.maddox.il2.engine.MsgCollision)cache.get();
            msgcollision1.thisChunk = s;
            msgcollision1.otherChunk = s1;
            msgcollision1.post(actor, l, actor1);
        }
        if(flag1)
        {
            com.maddox.il2.engine.MsgCollision msgcollision2 = (com.maddox.il2.engine.MsgCollision)cache.get();
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

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgCollisionListener)
        {
            ((com.maddox.il2.engine.MsgCollisionListener)obj).msgCollision((com.maddox.il2.engine.Actor)_sender, thisChunk, otherChunk);
            return true;
        } else
        {
            return false;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public java.lang.String thisChunk;
    public java.lang.String otherChunk;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.il2.engine.MsgCollision.class);
    }
}
