// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdTrackIRAngles.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, Time, HotKeyCmdEnv, RTSConf, 
//            HotKeyCmdEnvs

public abstract class HotKeyCmdTrackIRAngles extends com.maddox.rts.HotKeyCmd
{

    public void angles(float f, float f1, float f2)
    {
    }

    public final void setAngles(float f, float f1, float f2)
    {
        _yaw = f;
        _pitch = f1;
        _roll = f2;
    }

    public final void doAngles()
    {
        bActive = true;
        angles(_yaw, _pitch, _roll);
        bActive = false;
    }

    public HotKeyCmdTrackIRAngles(boolean flag, java.lang.String s)
    {
        super(flag, s);
    }

    public void _exec(float f, float f1, float f2)
    {
        boolean flag = com.maddox.rts.Time.isPaused();
        if(com.maddox.rts.Time.isPaused() && !isRealTime())
            return;
        if(!hotKeyCmdEnv.isEnabled())
        {
            return;
        } else
        {
            setAngles(f, f1, f2);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
            doAngles();
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
            return;
        }
    }

    protected float _yaw;
    protected float _pitch;
    protected float _roll;
}
