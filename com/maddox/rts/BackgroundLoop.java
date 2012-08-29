// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BackgroundLoop.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Listeners, RTSConf, BackgroundTask

public class BackgroundLoop
{

    public BackgroundLoop()
    {
        listeners = new Listeners();
    }

    protected void step()
    {
        com.maddox.rts.RTSConf.cur.loopMsgs();
    }

    protected void setThisAsCurrent()
    {
        com.maddox.rts.BackgroundLoop backgroundloop = com.maddox.rts.RTSConf.cur.backgroundLoop;
        com.maddox.rts.RTSConf.cur.backgroundLoop = this;
        if(backgroundloop != null)
        {
            java.lang.Object aobj[] = backgroundloop.listeners.get();
            if(aobj != null)
            {
                for(int i = 0; i < aobj.length; i++)
                {
                    backgroundloop.listeners.removeListener(aobj[i]);
                    listeners.addListener(aobj[i]);
                }

            }
            curTask = backgroundloop.curTask;
        }
    }

    protected com.maddox.rts.BackgroundTask curTask;
    protected com.maddox.rts.Listeners listeners;
}
