// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgBackgroundTask.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgBackgroundTaskListener, BackgroundTask, Time

public class MsgBackgroundTask extends com.maddox.rts.Message
{

    public MsgBackgroundTask()
    {
    }

    public static void post(java.lang.Object aobj[], int i, com.maddox.rts.BackgroundTask backgroundtask)
    {
        if(aobj == null)
        {
            return;
        } else
        {
            com.maddox.rts.MsgBackgroundTask msgbackgroundtask = new MsgBackgroundTask();
            msgbackgroundtask.id = i;
            msgbackgroundtask.post(64, ((java.lang.Object) (aobj)), com.maddox.rts.Time.currentReal(), backgroundtask);
            return;
        }
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgBackgroundTaskListener)
        {
            switch(id)
            {
            case 0: // '\0'
                ((com.maddox.rts.MsgBackgroundTaskListener)obj).msgBackgroundTaskStarted((com.maddox.rts.BackgroundTask)_sender);
                break;

            case 1: // '\001'
                ((com.maddox.rts.MsgBackgroundTaskListener)obj).msgBackgroundTaskStep((com.maddox.rts.BackgroundTask)_sender);
                break;

            case 2: // '\002'
                ((com.maddox.rts.MsgBackgroundTaskListener)obj).msgBackgroundTaskStoped((com.maddox.rts.BackgroundTask)_sender);
                break;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public static final int START = 0;
    public static final int STEP = 1;
    public static final int STOP = 2;
    protected int id;
}
