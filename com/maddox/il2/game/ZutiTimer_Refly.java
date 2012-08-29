// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiTimer_Refly.java

package com.maddox.il2.game;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.maddox.il2.game:
//            ZutiSupportMethods

public class ZutiTimer_Refly extends java.util.TimerTask
{

    public ZutiTimer_Refly(float f)
    {
        timer = null;
        counter = 0;
        reflyDelay = 0.0F;
        reflyDelay = f;
        timer = new Timer();
        timer.schedule(this, 0L, 1000L);
    }

    public void run()
    {
        try
        {
            if((float)counter > reflyDelay - 1.0F)
            {
                com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = true;
                stop();
            }
        }
        catch(java.lang.Exception exception) { }
        counter++;
    }

    public void stop()
    {
        cancel();
    }

    public java.lang.String getRemaimingTime()
    {
        int i = (int)reflyDelay - counter;
        int j = i / 60;
        int k = i % 60;
        java.lang.String s = "";
        java.lang.String s1 = "";
        if(j < 10)
            s = "0" + j;
        else
            s = "" + j;
        if(k < 10)
            s1 = "0" + k;
        else
            s1 = "" + k;
        return s + ":" + s1;
    }

    private java.util.Timer timer;
    private int counter;
    private float reflyDelay;
}
