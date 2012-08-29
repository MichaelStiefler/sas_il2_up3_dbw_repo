// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgTimer.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgTimerListener, MsgTimerParam

public class MsgTimer extends com.maddox.rts.Message
{

    public MsgTimer()
    {
        param = null;
    }

    public MsgTimer(com.maddox.rts.MsgTimerParam msgtimerparam)
    {
        param = msgtimerparam;
    }

    public void clean()
    {
        super.clean();
        param = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgTimerListener)
        {
            int i = (int)((_time - param.startTime) / (long)param.stepTime);
            boolean flag = _time + (long)param.stepTime == param.nextTime;
            boolean flag1 = param.countPost == i + 1;
            ((com.maddox.rts.MsgTimerListener)obj).msgTimer(param, i, flag, flag1);
            return true;
        } else
        {
            return false;
        }
    }

    public com.maddox.rts.MsgTimerParam param;
}
