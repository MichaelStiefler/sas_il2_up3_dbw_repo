// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgBackgroundTaskListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            BackgroundTask

public interface MsgBackgroundTaskListener
{

    public abstract void msgBackgroundTaskStarted(com.maddox.rts.BackgroundTask backgroundtask);

    public abstract void msgBackgroundTaskStep(com.maddox.rts.BackgroundTask backgroundtask);

    public abstract void msgBackgroundTaskStoped(com.maddox.rts.BackgroundTask backgroundtask);
}
