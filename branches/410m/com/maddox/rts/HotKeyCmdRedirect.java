// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdRedirect.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, Time, HotKeyCmdEnv, RTSConf, 
//            HotKeyCmdEnvs

public abstract class HotKeyCmdRedirect extends com.maddox.rts.HotKeyCmd
{

    public int idRedirect()
    {
        return idRedirect;
    }

    public void redirect(int ai[])
    {
    }

    public final void setRedirect(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1)
    {
        _r[0] = i;
        _r[1] = j;
        _r[2] = k;
        _r[3] = l;
        _r[4] = i1;
        _r[5] = j1;
        _r[6] = k1;
        _r[7] = l1;
    }

    public final void doRedirect()
    {
        bActive = true;
        redirect(_r);
        bActive = false;
    }

    public HotKeyCmdRedirect(boolean flag, java.lang.String s, int i)
    {
        super(flag, s);
        _r = new int[8];
        idRedirect = i;
    }

    protected void _exec(int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1)
    {
        boolean flag = com.maddox.rts.Time.isPaused();
        if(com.maddox.rts.Time.isPaused() && !isRealTime())
            return;
        if(!hotKeyCmdEnv.isEnabled())
        {
            return;
        } else
        {
            setRedirect(i, j, k, l, i1, j1, k1, l1);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
            doRedirect();
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
            return;
        }
    }

    private int idRedirect;
    protected int _r[];
}
