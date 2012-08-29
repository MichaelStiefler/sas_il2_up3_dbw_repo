// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgCollisionRequest.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            MsgCollisionRequestListener, Actor

public class MsgCollisionRequest extends com.maddox.rts.Message
{

    public MsgCollisionRequest()
    {
    }

    protected static boolean on(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        msg._sender = actor1;
        res[0] = true;
        msg.send(actor);
        msg._sender = null;
        if(!res[0])
        {
            return false;
        } else
        {
            msg._sender = actor;
            res[0] = true;
            msg.send(actor1);
            msg._sender = null;
            return res[0];
        }
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgCollisionRequestListener)
        {
            ((com.maddox.il2.engine.MsgCollisionRequestListener)obj).msgCollisionRequest((com.maddox.il2.engine.Actor)_sender, res);
            return true;
        } else
        {
            return false;
        }
    }

    private static com.maddox.il2.engine.MsgCollisionRequest msg = new MsgCollisionRequest();
    private static boolean res[] = new boolean[1];

}
