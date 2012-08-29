// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmd.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            HotKeyCmdEnv, Time, RTSConf, HotKeyEnvs, 
//            HotKeyCmdEnvs, MsgAction

public abstract class HotKeyCmd
{

    public com.maddox.rts.HotKeyCmdEnv hotKeyCmdEnv()
    {
        return hotKeyCmdEnv;
    }

    public int recordId()
    {
        return recordId;
    }

    protected void setRecordId(int i)
    {
        recordId = i;
        mapRecorded.put(recordId, this);
    }

    public java.lang.String name()
    {
        return sName;
    }

    public boolean isDisableIfTimePaused()
    {
        return false;
    }

    public boolean isRealTime()
    {
        return bRealTime;
    }

    public boolean isTickInTime(boolean flag)
    {
        return flag == bRealTime;
    }

    public boolean isActive()
    {
        return bActive;
    }

    public boolean isEnabled()
    {
        return bEnabled;
    }

    public void enable(boolean flag)
    {
        bEnabled = flag;
    }

    public void begin()
    {
    }

    public void tick()
    {
    }

    public void end()
    {
    }

    public final void start(int i, int j)
    {
        modifierKey = i;
        key = j;
        bActive = true;
        try
        {
            begin();
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public final void play()
    {
        if(bActive)
            try
            {
                tick();
            }
            catch(java.lang.Exception exception)
            {
                exception.printStackTrace();
            }
    }

    public final void stop()
    {
        bActive = false;
        try
        {
            end();
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected final void _cancel()
    {
        bActive = false;
    }

    public void created()
    {
    }

    public HotKeyCmd(boolean flag, java.lang.String s)
    {
        this(flag, s, s);
    }

    public HotKeyCmd(boolean flag, java.lang.String s, java.lang.String s1)
    {
        bActive = false;
        bEnabled = true;
        bRealTime = true;
        recordId = 0;
        bRealTime = flag;
        sName = s;
        sortingName = s1;
        created();
    }

    public static com.maddox.rts.HotKeyCmd getByRecordedId(int i)
    {
        return (com.maddox.rts.HotKeyCmd)mapRecorded.get(i);
    }

    public static int exec(double d, java.lang.String s, java.lang.String s1)
    {
        return com.maddox.rts.HotKeyCmd.exec(false, false, d, s, s1);
    }

    public static int exec(java.lang.String s, java.lang.String s1)
    {
        int i = com.maddox.rts.HotKeyCmd.exec(true, true, 0.0D, s, s1);
        if(i > 0)
            com.maddox.rts.HotKeyCmd.exec(true, false, 0.0D, s, s1);
        return i;
    }

    public static int exec(boolean flag, java.lang.String s, java.lang.String s1)
    {
        return com.maddox.rts.HotKeyCmd.exec(true, flag, 0.0D, s, s1);
    }

    private static int exec(boolean flag, boolean flag1, double d, java.lang.String s, java.lang.String s1)
    {
        int i = 0;
        boolean flag2 = com.maddox.rts.Time.isPaused();
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        java.util.List list = com.maddox.rts.HotKeyCmdEnv.allEnv();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)list.get(j);
            if(hotkeycmdenv.isEnabled() && (s == null || s.equals(hotkeycmdenv.name())))
            {
                com.maddox.rts.HotKeyCmd hotkeycmd = hotkeycmdenv.get(s1);
                if(hotkeycmd != null && hotkeycmd.isEnabled() && (!flag2 || hotkeycmd.isRealTime()))
                    if(flag)
                    {
                        if(flag1)
                        {
                            if(!hotkeycmd.isActive())
                            {
                                hashmapext.put(hotkeycmd, null);
                                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, true);
                                hotkeycmd.start(0, 0);
                                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, false);
                                i++;
                            }
                        } else
                        if(hotkeycmd.isActive())
                        {
                            hashmapext.remove(hotkeycmd);
                            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, true);
                            hotkeycmd.stop();
                            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, false);
                            i++;
                        }
                    } else
                    if(!hotkeycmd.isActive())
                    {
                        hashmapext.put(hotkeycmd, null);
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, true);
                        hotkeycmd.start(0, 0);
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, false);
                        if(d > 0.0D)
                        {
                            new com.maddox.rts.MsgAction(hotkeycmd.isRealTime() ? 64 : 0, d, hotkeycmd) {

                                public void doAction(java.lang.Object obj)
                                {
                                    com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)obj;
                                    if(hotkeycmd1.isActive())
                                    {
                                        com.maddox.rts.RTSConf.cur.hotKeyEnvs.active.remove(hotkeycmd1);
                                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, true);
                                        hotkeycmd1.stop();
                                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, false);
                                    }
                                }

                            }
;
                        } else
                        {
                            hashmapext.remove(hotkeycmd);
                            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, true);
                            hotkeycmd.stop();
                            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, false);
                        }
                        i++;
                    }
            }
        }

        return i;
    }

    public void _exec(boolean flag)
    {
        if(com.maddox.rts.Time.isPaused() && !isRealTime())
            return;
        if(!hotKeyCmdEnv.isEnabled())
            return;
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        if(flag)
        {
            if(!isActive())
            {
                hashmapext.put(this, null);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
                start(0, 0);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
            }
        } else
        if(isActive())
        {
            hashmapext.remove(this);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, false, true);
            stop();
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(this, false, false);
        }
    }

    protected com.maddox.rts.HotKeyCmdEnv hotKeyCmdEnv;
    protected java.lang.String sName;
    public java.lang.String sortingName;
    protected boolean bActive;
    protected boolean bEnabled;
    protected boolean bRealTime;
    public int modifierKey;
    public int key;
    private int recordId;
    static com.maddox.util.HashMapInt mapRecorded = new HashMapInt();

}
