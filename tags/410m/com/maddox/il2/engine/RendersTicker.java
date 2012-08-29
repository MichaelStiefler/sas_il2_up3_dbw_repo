// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Renders.java

package com.maddox.il2.engine;

import com.maddox.rts.MessageQueue;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            Renders

class RendersTicker
    implements com.maddox.rts.MsgTimeOutListener
{

    public void msgTimeOut(java.lang.Object obj)
    {
        msgTimeOut.post();
        if(com.maddox.rts.Time.isPaused() || com.maddox.rts.Time.tickOffset() != 0.0F)
            if(renders.maxFps <= 0.0F)
            {
                renders.paint();
                renders.prevTimePaint = com.maddox.rts.Time.real();
            } else
            {
                long l = com.maddox.rts.Time.real();
                if(l >= renders.prevTimePaint + renders.stepTimePaint)
                {
                    renders.paint();
                    renders.prevTimePaint = l;
                }
            }
    }

    public void destroy()
    {
        if(msgTimeOut != null)
        {
            com.maddox.rts.RTSConf.cur.queueRealTime.remove(msgTimeOut);
            com.maddox.rts.RTSConf.cur.queueRealTimeNextTick.remove(msgTimeOut);
            msgTimeOut = null;
        }
    }

    public RendersTicker(com.maddox.il2.engine.Renders renders1)
    {
        renders = renders1;
        msgTimeOut = new MsgTimeOut();
        msgTimeOut.setListener(this);
        msgTimeOut.setNotCleanAfterSend();
        msgTimeOut.setFlags(104);
        msgTimeOut.setTickPos(0x7fffffff);
        msgTimeOut.post();
    }

    private com.maddox.il2.engine.Renders renders;
    private com.maddox.rts.MsgTimeOut msgTimeOut;
}
