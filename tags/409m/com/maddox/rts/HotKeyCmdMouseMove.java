// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdMouseMove.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, Time, HotKeyCmdEnv, RTSConf, 
//            HotKeyCmdEnvs

public abstract class HotKeyCmdMouseMove extends com.maddox.rts.HotKeyCmd
{

    public void move(int i, int j, int k)
    {
    }

    public void prepareInvert()
    {
        _dy = -_dy;
    }

    public final void setMove(int i, int j, int k)
    {
        _dx = i;
        _dy = j;
        _dz = k;
    }

    public final void doMove()
    {
        bActive = true;
        move(_dx, _dy, _dz);
        bActive = false;
    }

    public HotKeyCmdMouseMove(boolean flag, java.lang.String s)
    {
        super(flag, s);
    }

    public void _exec(int i, int j, int k)
    {
        boolean flag = com.maddox.rts.Time.isPaused();
        if(com.maddox.rts.Time.isPaused() && !isRealTime())
            return;
        if(!hotKeyCmdEnv.isEnabled())
        {
            return;
        } else
        {
            setMove(i, j, k);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
            doMove();
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
            return;
        }
    }

    protected int _dx;
    protected int _dy;
    protected int _dz;
}
