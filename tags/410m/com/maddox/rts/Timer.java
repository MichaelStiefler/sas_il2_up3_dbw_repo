// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Timer.java

package com.maddox.rts;

import java.util.ArrayList;

// Referenced classes of package com.maddox.rts:
//            MsgTimerParam, MsgTimer, MessageCache, MsgTimeOut, 
//            MsgTimeOutListener, MsgAddListenerListener, MsgRemoveListenerListener, Time

public final class Timer
    implements com.maddox.rts.MsgTimeOutListener, com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public void msgTimeOut(java.lang.Object obj)
    {
        long l;
        if(bRealTime)
            l = com.maddox.rts.Time.endReal();
        else
            l = com.maddox.rts.Time.tickNext() - 1L;
        long l1;
        if(bRealTime)
            l1 = com.maddox.rts.Time.currentReal();
        else
            l1 = com.maddox.rts.Time.current();
        int i = 0;
        do
        {
            if(i >= listeners.size())
                break;
            if(process(i, l1, l))
                i++;
        } while(true);
        ticker.post();
    }

    private boolean process(int i, long l, long l1)
    {
        java.lang.Object obj = listeners.get(i);
        com.maddox.rts.MsgTimerParam msgtimerparam = (com.maddox.rts.MsgTimerParam)params.get(i);
        if(msgtimerparam.nextTime > l1)
            return true;
        if(msgtimerparam.bSkip)
            for(; msgtimerparam.nextTime < l && msgtimerparam.nextTime + (long)msgtimerparam.stepTime < l && msgtimerparam.curCount != 1; msgtimerparam.curCount--)
                msgtimerparam.nextTime += msgtimerparam.stepTime;

        if(msgtimerparam.curCount != 0)
        {
            for(int j = 10; msgtimerparam.curCount != 0 && msgtimerparam.nextTime <= l1 && j > 0; j--)
            {
                com.maddox.rts.MsgTimer msgtimer = (com.maddox.rts.MsgTimer)cache.get();
                msgtimer.param = msgtimerparam;
                if(bRealTime)
                    msgtimer.setFlags(64);
                else
                    msgtimer.setFlags(0);
                msgtimer.post(obj, msgtimerparam.nextTime, msgtimerparam.tickPos, this);
                msgtimerparam.nextTime += msgtimerparam.stepTime;
                msgtimerparam.curCount--;
            }

        }
        if(msgtimerparam.curCount == 0)
        {
            remove(i);
            return false;
        } else
        {
            return true;
        }
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(obj == null || !(obj1 instanceof com.maddox.rts.MsgTimerParam))
            return;
        if(find(obj, obj1) >= 0)
            return;
        com.maddox.rts.MsgTimerParam msgtimerparam = (com.maddox.rts.MsgTimerParam)obj1;
        if(msgtimerparam.countPost == 0 || msgtimerparam.stepTime <= 0)
            return;
        long l;
        if(bRealTime)
            l = com.maddox.rts.Time.endReal();
        else
            l = com.maddox.rts.Time.tickNext() - 1L;
        long l1;
        if(bRealTime)
            l1 = com.maddox.rts.Time.currentReal();
        else
            l1 = com.maddox.rts.Time.current();
        msgtimerparam.curCount = msgtimerparam.countPost;
        msgtimerparam.nextTime = msgtimerparam.startTime;
        if((msgtimerparam.bSkipBegin || msgtimerparam.bSkip) && msgtimerparam.nextTime < l1)
        {
            int i = (int)((l1 - msgtimerparam.nextTime) / (long)msgtimerparam.stepTime);
            msgtimerparam.nextTime += i * msgtimerparam.stepTime;
            if(msgtimerparam.curCount > 0)
            {
                msgtimerparam.curCount -= i;
                if(msgtimerparam.curCount <= 0)
                    return;
            } else
            {
                msgtimerparam.curCount -= i;
            }
        }
        int j = add(obj, obj1);
        if(msgtimerparam.nextTime <= l)
            process(j, l1, l);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        if(obj != null && obj1 != null)
        {
            int i = find(obj, obj1);
            if(i >= 0)
                remove(i);
        }
    }

    private int find(java.lang.Object obj, java.lang.Object obj1)
    {
        int i = 0;
        for(int j = listeners.size(); i < j; i++)
            if(obj.equals(listeners.get(i)) && obj1.equals(params.get(i)))
                return i;

        return -1;
    }

    private int add(java.lang.Object obj, java.lang.Object obj1)
    {
        int i = listeners.size();
        listeners.add(obj);
        params.add(obj1);
        return i;
    }

    private void remove(int i)
    {
        listeners.remove(i);
        params.remove(i);
    }

    protected void resetGameClear()
    {
        listeners.clear();
        params.clear();
    }

    protected void resetGameCreate()
    {
        ticker.post();
    }

    protected Timer(boolean flag, int i)
    {
        if(cache == null)
            cache = new MessageCache(com.maddox.rts.MsgTimer.class);
        listeners = new ArrayList();
        params = new ArrayList();
        bRealTime = flag;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(i);
        ticker.setNotCleanAfterSend();
        ticker.setListener(this);
        if(bRealTime)
            ticker.setFlags(72);
        else
            ticker.setFlags(8);
        ticker.post();
    }

    public static final int MAX_POST = 10;
    private static com.maddox.rts.MessageCache cache = null;
    private com.maddox.rts.MsgTimeOut ticker;
    private boolean bRealTime;
    private java.util.ArrayList listeners;
    private java.util.ArrayList params;

}
