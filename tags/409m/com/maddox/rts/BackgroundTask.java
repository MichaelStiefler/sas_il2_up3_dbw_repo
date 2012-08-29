// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BackgroundTask.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            RTSException, RTSConf, BackgroundLoop, Listeners, 
//            MsgBackgroundTask

public class BackgroundTask
{

    public BackgroundTask()
    {
        bRun = false;
        bComplete = false;
        percentComplete = 0.0F;
        bRequestCanceling = false;
    }

    public boolean isRun()
    {
        return bRun;
    }

    public boolean isComplete()
    {
        return bComplete;
    }

    public float percentComplete()
    {
        return percentComplete;
    }

    public java.lang.String messageComplete()
    {
        return messageComplete;
    }

    public boolean isRequestCanceling()
    {
        return bRequestCanceling;
    }

    public java.lang.String messageCancel()
    {
        return messageCancel;
    }

    public static void addListener(java.lang.Object obj)
    {
        com.maddox.rts.RTSConf.cur.backgroundLoop.listeners.addListener(obj);
    }

    public static void removeListener(java.lang.Object obj)
    {
        com.maddox.rts.RTSConf.cur.backgroundLoop.listeners.removeListener(obj);
    }

    protected void beforeStartRun()
    {
        bRequestCanceling = false;
        bComplete = false;
        percentComplete = 0.0F;
        messageComplete = null;
        bRun = true;
    }

    protected void beforeRun()
    {
    }

    protected void afterRun()
    {
        bComplete = !bRequestCanceling;
        bRun = false;
    }

    protected void run()
        throws java.lang.Exception
    {
    }

    public static boolean step(float f, java.lang.String s)
    {
        com.maddox.rts.BackgroundTask backgroundtask = com.maddox.rts.RTSConf.cur.backgroundLoop.curTask;
        if(backgroundtask == null)
            return true;
        else
            return backgroundtask._step(f, s);
    }

    protected boolean _step(float f, java.lang.String s)
    {
        percentComplete = f;
        messageComplete = s;
        com.maddox.rts.MsgBackgroundTask.post(com.maddox.rts.RTSConf.cur.backgroundLoop.listeners.get(), 1, this);
        com.maddox.rts.RTSConf.cur.backgroundLoop.step();
        return !bRequestCanceling;
    }

    public static void execute(com.maddox.rts.BackgroundTask backgroundtask)
    {
        if(com.maddox.rts.BackgroundTask.isExecuted())
        {
            throw new RTSException("background task alredy executed");
        } else
        {
            com.maddox.rts.RTSConf.cur.backgroundLoop.curTask = backgroundtask;
            backgroundtask.beforeStartRun();
            return;
        }
    }

    public static void cancel(java.lang.String s)
    {
        com.maddox.rts.BackgroundTask backgroundtask = com.maddox.rts.RTSConf.cur.backgroundLoop.curTask;
        if(backgroundtask == null)
        {
            return;
        } else
        {
            backgroundtask.bRequestCanceling = true;
            backgroundtask.messageCancel = s;
            return;
        }
    }

    public static boolean isExecuted()
    {
        return com.maddox.rts.RTSConf.cur.backgroundLoop.curTask != null;
    }

    public static com.maddox.rts.BackgroundTask executed()
    {
        return com.maddox.rts.RTSConf.cur.backgroundLoop.curTask;
    }

    public static void doRun()
    {
        if(!com.maddox.rts.BackgroundTask.isExecuted())
            throw new RTSException("background task not found");
        com.maddox.rts.BackgroundTask backgroundtask = com.maddox.rts.RTSConf.cur.backgroundLoop.curTask;
        backgroundtask.beforeRun();
        com.maddox.rts.MsgBackgroundTask.post(com.maddox.rts.RTSConf.cur.backgroundLoop.listeners.get(), 0, backgroundtask);
        com.maddox.rts.RTSConf.cur.backgroundLoop.step();
        try
        {
            backgroundtask.run();
        }
        catch(java.lang.Exception exception)
        {
            if(!backgroundtask.isRequestCanceling())
            {
                com.maddox.rts.BackgroundTask _tmp = backgroundtask;
                com.maddox.rts.BackgroundTask.cancel(exception.getMessage());
            }
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        com.maddox.rts.RTSConf.cur.backgroundLoop.curTask = null;
        backgroundtask.afterRun();
        com.maddox.rts.MsgBackgroundTask.post(com.maddox.rts.RTSConf.cur.backgroundLoop.listeners.get(), 2, backgroundtask);
        com.maddox.rts.RTSConf.cur.backgroundLoop.step();
    }

    protected boolean bRun;
    protected boolean bComplete;
    protected float percentComplete;
    protected java.lang.String messageComplete;
    protected boolean bRequestCanceling;
    protected java.lang.String messageCancel;
}
