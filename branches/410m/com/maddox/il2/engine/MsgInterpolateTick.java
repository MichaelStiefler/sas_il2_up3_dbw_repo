// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgInterpolateTick.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            MsgInterpolateTickListener, Actor

public class MsgInterpolateTick extends com.maddox.rts.Message
{

    protected static void send(com.maddox.il2.engine.Actor actor)
    {
        msg.send(actor);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgInterpolateTickListener)
        {
            ((com.maddox.il2.engine.MsgInterpolateTickListener)obj).msgInterpolateTick();
            return true;
        } else
        {
            return false;
        }
    }

    private MsgInterpolateTick()
    {
    }

    private static com.maddox.il2.engine.MsgInterpolateTick msg = new MsgInterpolateTick();

}
