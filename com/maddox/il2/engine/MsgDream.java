// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgDream.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            MsgDreamListener, Actor

public class MsgDream extends com.maddox.rts.Message
{

    protected MsgDream()
    {
    }

    protected void send(boolean flag, com.maddox.il2.engine.Actor actor)
    {
        _bWakeup = flag;
        send(actor);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgDreamListener)
        {
            ((com.maddox.il2.engine.MsgDreamListener)obj).msgDream(_bWakeup);
            return true;
        } else
        {
            return false;
        }
    }

    private boolean _bWakeup;
}
