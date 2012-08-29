// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyEnvs.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, HotKey, RTSConf, HotKeyCmdEnvs, 
//            HotKeyEnv

public class HotKeyEnvs
{

    public com.maddox.rts.HotKey adapter()
    {
        return hotKeyAdapter;
    }

    public void endAllActiveCmd(boolean flag)
    {
        java.util.ArrayList arraylist = new ArrayList();
        for(java.util.Map.Entry entry = active.nextEntry(null); entry != null; entry = active.nextEntry(entry))
        {
            com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getKey();
            if(hotkeycmd.bRealTime == flag && hotkeycmd.isActive() && (hotkeycmd.modifierKey != 0 || hotkeycmd.key != 0))
                arraylist.add(hotkeycmd);
        }

        for(; arraylist.size() > 0; arraylist.remove(arraylist.size() - 1))
        {
            com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)arraylist.get(arraylist.size() - 1);
            active.remove(hotkeycmd1);
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, true);
            hotkeycmd1.stop();
            com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, false);
        }

        int i = flag ? 1 : 0;
        com.maddox.rts.RTSConf.cur.hotKeyEnvs.keyState[i].clear();
    }

    protected HotKeyEnvs()
    {
        active = new HashMapExt();
        envs = new HashMap();
        lst = new ArrayList();
        hotKeyAdapter = new HotKey();
    }

    protected void resetGameClear()
    {
        java.util.ArrayList arraylist = new ArrayList();
        for(java.util.Map.Entry entry = active.nextEntry(null); entry != null; entry = active.nextEntry(entry))
        {
            com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getKey();
            arraylist.add(hotkeycmd);
        }

        keyState[0].clear();
        keyState[1].clear();
        active.clear();
        for(; arraylist.size() > 0; arraylist.remove(arraylist.size() - 1))
        {
            com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)arraylist.get(arraylist.size() - 1);
            hotkeycmd1._cancel();
        }

        hotKeyAdapter.resetGameClear();
    }

    protected void resetGameCreate()
    {
        hotKeyAdapter.resetGameCreate();
    }

    protected com.maddox.util.HashMapInt keyState[] = {
        new HashMapInt(), new HashMapInt()
    };
    protected com.maddox.util.HashMapExt active;
    protected com.maddox.rts.HotKeyEnv cur;
    protected java.util.HashMap envs;
    protected java.util.ArrayList lst;
    private com.maddox.rts.HotKey hotKeyAdapter;
}
