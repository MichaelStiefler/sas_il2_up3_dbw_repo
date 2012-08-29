// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdMove.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, Time, HotKeyCmdEnv, RTSConf, 
//            HotKeyCmdEnvs

public abstract class HotKeyCmdMove extends com.maddox.rts.HotKeyCmd
{

    public int move()
    {
        return _mov;
    }

    public final void setMove(int i)
    {
        _mov = i;
    }

    public HotKeyCmdMove(boolean flag, java.lang.String s)
    {
        super(flag, s);
    }

    public HotKeyCmdMove(boolean flag, java.lang.String s, java.lang.String s1)
    {
        super(flag, s, s1);
    }

    public void _exec(int i)
    {
        boolean flag = com.maddox.rts.Time.isPaused();
        if(com.maddox.rts.Time.isPaused() && !isRealTime())
            return;
        if(!hotKeyCmdEnv.isEnabled())
        {
            return;
        } else
        {
            setMove(i);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
            start(0, 0);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
            stop();
            return;
        }
    }

    protected int _mov;
}
