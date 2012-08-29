// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyCmdEnvs.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            HotKeyCmdEnv, HotKeyCmd, MsgHotKeyCmd, MsgAddListenerListener, 
//            MsgRemoveListenerListener, RTSConf, HotKeyEnvs

public final class HotKeyCmdEnvs
    implements com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{

    public java.lang.Object[] getListeners()
    {
        return listeners.toArray();
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        listeners.add(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        int i = listeners.indexOf(obj);
        if(i >= 0)
            listeners.remove(i);
    }

    protected void post(com.maddox.rts.HotKeyCmd hotkeycmd, boolean flag, boolean flag1)
    {
        int i = listeners.size();
        if(i == 0)
            return;
        msg.bStart = flag;
        msg.bBefore = flag1;
        for(int j = 0; j < i; j++)
            msg.send(listeners.get(j), hotkeycmd);

    }

    public void endAllCmd()
    {
        for(int i = 0; i < lst.size(); i++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)lst.get(i);
            if(!hotkeycmdenv.isEnabled())
                continue;
            com.maddox.util.HashMapExt hashmapext = hotkeycmdenv.all();
            for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
            {
                com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getValue();
                if(hotkeycmd.isActive())
                    hotkeycmd.stop();
            }

        }

        com.maddox.rts.RTSConf.cur.hotKeyEnvs.active.clear();
    }

    protected HotKeyCmdEnvs()
    {
        msg = new MsgHotKeyCmd();
        envs = new HashMap();
        lst = new ArrayList();
        listeners = new ArrayList();
    }

    protected java.util.HashMap envs;
    private java.util.ArrayList listeners;
    private com.maddox.rts.MsgHotKeyCmd msg;
    protected java.util.ArrayList lst;
    protected com.maddox.rts.HotKeyCmdEnv cur;
}
