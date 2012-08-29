// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgDreamGlobal.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            MsgDreamGlobalListener, Actor

public class MsgDreamGlobal extends com.maddox.rts.Message
{

    protected MsgDreamGlobal()
    {
    }

    protected void sendTick(com.maddox.il2.engine.Actor actor, int i, int j)
    {
        updateTicks = i;
        updateTickCounter = j;
        send(actor);
        updateTicks = 0;
    }

    protected void send(com.maddox.il2.engine.Actor actor, boolean flag, int i, int ai[], int ai1[])
    {
        _bWakeup = flag;
        _count = i;
        _xIndx = ai;
        _yIndx = ai1;
        send(actor);
        _count = 0;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.il2.engine.MsgDreamGlobalListener)
        {
            if(updateTicks != 0)
                ((com.maddox.il2.engine.MsgDreamGlobalListener)obj).msgDreamGlobalTick(updateTicks, updateTickCounter);
            else
            if(_count != 0)
                ((com.maddox.il2.engine.MsgDreamGlobalListener)obj).msgDreamGlobal(_bWakeup, _count, _xIndx, _yIndx);
            return true;
        } else
        {
            return false;
        }
    }

    private boolean _bWakeup;
    private int _xIndx[];
    private int _yIndx[];
    private int _count;
    private int updateTicks;
    private int updateTickCounter;
}
