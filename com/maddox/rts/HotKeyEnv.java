// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HotKeyEnv.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.rts:
//            HotKeyCmd, HotKeyCmdEnv, HotKeyCmdMouseMove, HotKeyCmdMove, 
//            HotKeyCmdRedirect, HotKeyCmdTrackIRAngles, RTSConf, HotKeyEnvs, 
//            VK, IniFile, Time, HotKeyCmdEnvs, 
//            Mouse

public class HotKeyEnv
{

    public final java.lang.String name()
    {
        return name;
    }

    public final boolean isEnabled()
    {
        return bEnabled;
    }

    public static boolean isEnabled(java.lang.String s)
    {
        com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
        if(hotkeyenv == null)
            return false;
        else
            return hotkeyenv.bEnabled;
    }

    public final void enable(boolean flag)
    {
        bEnabled = flag;
    }

    public static void enable(java.lang.String s, boolean flag)
    {
        com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
        if(hotkeyenv == null)
        {
            return;
        } else
        {
            hotkeyenv.bEnabled = flag;
            return;
        }
    }

    private int hotKeys(int i, int j)
    {
        return (i & 0xffff) << 16 | j & 0xffff;
    }

    public void add(int i, int j, java.lang.String s)
    {
        keys.put(hotKeys(i, j), s);
    }

    public static void addHotKey(int i, int j, java.lang.String s)
    {
        com.maddox.rts.RTSConf.cur.hotKeyEnvs.cur.add(i, j, s);
    }

    public static void addHotKey(java.lang.String s, int i, int j, java.lang.String s1)
    {
        com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
        if(hotkeyenv == null)
            hotkeyenv = new HotKeyEnv(s);
        hotkeyenv.add(i, j, s1);
    }

    public void remove(int i, int j)
    {
        keys.remove(hotKeys(i, j));
    }

    public void remove(java.lang.String s)
    {
        if(s == null)
            return;
        boolean flag = true;
        while(flag) 
        {
            flag = false;
            for(com.maddox.util.HashMapIntEntry hashmapintentry = keys.nextEntry(null); hashmapintentry != null; hashmapintentry = keys.nextEntry(hashmapintentry))
            {
                int i = hashmapintentry.getKey();
                int j = i >> 16 & 0xffff;
                int k = i & 0xffff;
                java.lang.String s1 = (java.lang.String)hashmapintentry.getValue();
                if(!s.equals(s1))
                    continue;
                remove(j, k);
                flag = true;
                break;
            }

        }
    }

    public int find(java.lang.String s)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = keys.nextEntry(null); hashmapintentry != null; hashmapintentry = keys.nextEntry(hashmapintentry))
        {
            java.lang.String s1 = (java.lang.String)hashmapintentry.getValue();
            if(s.equals(s1))
            {
                int i = hashmapintentry.getKey();
                return i;
            }
        }

        return 0;
    }

    public java.lang.String get(int i, int j)
    {
        return (java.lang.String)keys.get(hotKeys(i, j));
    }

    public final com.maddox.util.HashMapInt all()
    {
        return keys;
    }

    public static final void setCurrentEnv(java.lang.String s)
    {
        com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
        if(hotkeyenv == null)
            hotkeyenv = new HotKeyEnv(s);
        com.maddox.rts.RTSConf.cur.hotKeyEnvs.cur = hotkeyenv;
    }

    public static final com.maddox.rts.HotKeyEnv currentEnv()
    {
        return com.maddox.rts.RTSConf.cur.hotKeyEnvs.cur;
    }

    public static final java.util.List allEnv()
    {
        return com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst;
    }

    public static final com.maddox.rts.HotKeyEnv env(java.lang.String s)
    {
        return (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.get(s);
    }

    public static java.lang.String key2Text(int i)
    {
        if(i >= 601)
            return "User" + (i - 601);
        else
            return com.maddox.rts.VK.getKeyText(i);
    }

    public static int text2Key(java.lang.String s)
    {
        if(s.startsWith("User"))
        {
            java.lang.String s1 = s.substring("User".length());
            return java.lang.Integer.parseInt(s1) + 601;
        } else
        {
            return com.maddox.rts.VK.getKeyFromText(s);
        }
    }

    public static void fromIni(java.lang.String s, com.maddox.rts.IniFile inifile, java.lang.String s1)
    {
        java.lang.String as[] = inifile.getVariables(s1);
        com.maddox.rts.HotKeyEnv.setCurrentEnv(s);
        if(as == null)
            return;
        for(int i = 0; i < as.length; i++)
        {
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(as[i]);
            java.lang.String s2 = inifile.getValue(s1, as[i]);
            if(s2.length() > 0 && stringtokenizer.hasMoreTokens())
            {
                java.lang.String s3 = stringtokenizer.nextToken();
                int j = com.maddox.rts.HotKeyEnv.text2Key(s3);
                if(j == 0)
                    java.lang.System.err.println("INI: HotKey '" + as[i] + "' is unknown");
                else
                if(stringtokenizer.hasMoreTokens())
                {
                    java.lang.String s4 = stringtokenizer.nextToken();
                    int k = com.maddox.rts.HotKeyEnv.text2Key(s4);
                    if(k == 0)
                        java.lang.System.err.println("INI: HotKey '" + as[i] + "' is unknown");
                    else
                        com.maddox.rts.HotKeyEnv.addHotKey(j, k, s2);
                } else
                {
                    com.maddox.rts.HotKeyEnv.addHotKey(0, j, s2);
                }
            }
        }

    }

    public static void toIni(java.lang.String s, com.maddox.rts.IniFile inifile, java.lang.String s1)
    {
        com.maddox.rts.HotKeyEnv hotkeyenv = com.maddox.rts.HotKeyEnv.env(s);
        if(hotkeyenv == null)
        {
            java.lang.System.err.println("INI: HotKey environment '" + s + "' not present");
            return;
        }
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hotkeyenv.keys.nextEntry(null); hashmapintentry != null; hashmapintentry = hotkeyenv.keys.nextEntry(hashmapintentry))
        {
            int i = hashmapintentry.getKey();
            int j = i >> 16 & 0xffff;
            int k = i & 0xffff;
            java.lang.String s2 = (java.lang.String)hashmapintentry.getValue();
            if(k >= 601)
                inifile.setValue(s1, "User" + (k - 601), s2);
            else
            if(j != 0)
                inifile.setValue(s1, com.maddox.rts.HotKeyEnv.key2Text(j) + " " + com.maddox.rts.HotKeyEnv.key2Text(k), s2);
            else
                inifile.setValue(s1, com.maddox.rts.HotKeyEnv.key2Text(k), s2);
        }

    }

    public static void tick(boolean flag)
    {
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
        {
            com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getKey();
            if(hotkeycmd.isTickInTime(flag))
                hotkeycmd.play();
        }

    }

    private final boolean startCmd(boolean flag, int i, int j)
    {
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        int k = hotKeys(i, j);
        java.lang.String s = (java.lang.String)keys.get(k);
        if(s != null)
        {
            com.maddox.rts.HotKeyCmd hotkeycmd = hotKeyCmdEnv.get(s);
            if(hotkeycmd != null && !hotkeycmd.isActive() && hotkeycmd.isEnabled() && hotkeycmd.isRealTime() == flag && (!com.maddox.rts.Time.isPaused() || !hotkeycmd.isDisableIfTimePaused()))
            {
                hashmapext.put(hotkeycmd, null);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, true);
                hotkeycmd.start(i, j);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, false);
                return true;
            }
        }
        return false;
    }

    public static final void keyPress(boolean flag, int i, boolean flag1)
    {
        int j = flag ? 1 : 0;
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.RTSConf.cur.hotKeyEnvs.keyState[j];
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        boolean flag2 = hashmapint.containsKey(i);
        if(flag1 && !flag2)
        {
            hashmapint.put(i, null);
            boolean flag3 = false;
            for(int k = 0; k < com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.size(); k++)
            {
                com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.get(k);
                if(hotkeyenv.bEnabled && hotkeyenv.hotKeyCmdEnv.isEnabled())
                {
                    for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
                    {
                        for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(hashmapintentry); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
                            if(hotkeyenv.startCmd(flag, hashmapintentry.getKey(), hashmapintentry1.getKey()) || hotkeyenv.startCmd(flag, hashmapintentry1.getKey(), hashmapintentry.getKey()))
                                flag3 = true;

                    }

                }
            }

            if(!flag3)
            {
                for(int i1 = 0; i1 < com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.size(); i1++)
                {
                    com.maddox.rts.HotKeyEnv hotkeyenv1 = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.get(i1);
                    if(hotkeyenv1.bEnabled && hotkeyenv1.hotKeyCmdEnv.isEnabled())
                    {
                        for(com.maddox.util.HashMapIntEntry hashmapintentry2 = hashmapint.nextEntry(null); hashmapintentry2 != null; hashmapintentry2 = hashmapint.nextEntry(hashmapintentry2))
                            hotkeyenv1.startCmd(flag, 0, hashmapintentry2.getKey());

                    }
                }

            }
        } else
        if(!flag1 && flag2)
        {
            for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
            {
                com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getKey();
                if((hotkeycmd.modifierKey == i || hotkeycmd.key == i) && hotkeycmd.isRealTime() == flag)
                    removed.add(hotkeycmd);
            }

            for(int l = 0; l < removed.size(); l++)
            {
                com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)removed.get(l);
                hashmapext.remove(hotkeycmd1);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, true);
                hotkeycmd1.stop();
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, false);
            }

            removed.clear();
            hashmapint.remove(i);
        }
    }

    public static final void keyPress(boolean flag, int i, int j, boolean flag1)
    {
        int k = flag ? 1 : 0;
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.RTSConf.cur.hotKeyEnvs.keyState[k];
        com.maddox.util.HashMapExt hashmapext = com.maddox.rts.RTSConf.cur.hotKeyEnvs.active;
        int l = i | j << 16;
        boolean flag2 = hashmapint.containsKey(l);
        if(flag1 && !flag2)
        {
            hashmapint.put(l, null);
            for(int i1 = 0; i1 < com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.size(); i1++)
            {
                com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.get(i1);
                if(hotkeyenv.bEnabled && hotkeyenv.hotKeyCmdEnv.isEnabled())
                {
                    hotkeyenv.startCmd(flag, i, j);
                    hotkeyenv.startCmd(flag, j, i);
                }
            }

        } else
        if(!flag1 && flag2)
        {
            for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
            {
                com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getKey();
                if((hotkeycmd.modifierKey == i && hotkeycmd.key == j || hotkeycmd.modifierKey == j && hotkeycmd.key == i) && hotkeycmd.isRealTime() == flag)
                    removed.add(hotkeycmd);
            }

            for(int j1 = 0; j1 < removed.size(); j1++)
            {
                com.maddox.rts.HotKeyCmd hotkeycmd1 = (com.maddox.rts.HotKeyCmd)removed.get(j1);
                hashmapext.remove(hotkeycmd1);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, true);
                hotkeycmd1.stop();
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd1, false, false);
            }

            removed.clear();
            hashmapint.remove(l);
        }
    }

    public static final void mouseMove(boolean flag, int i, int j, int k)
    {
        for(int l = 0; l < com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.size(); l++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.get(l);
            if(hotkeycmdenv.isEnabled() && hotkeycmdenv.hotKeyEnv().bEnabled)
            {
                com.maddox.util.HashMapExt hashmapext = hotkeycmdenv.all();
                for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                {
                    com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getValue();
                    if((hotkeycmd instanceof com.maddox.rts.HotKeyCmdMouseMove) && hotkeycmd.isEnabled() && hotkeycmd.isRealTime() == flag && (!com.maddox.rts.Time.isPaused() || !hotkeycmd.isDisableIfTimePaused()))
                    {
                        com.maddox.rts.HotKeyCmdMouseMove hotkeycmdmousemove = (com.maddox.rts.HotKeyCmdMouseMove)hotkeycmd;
                        hotkeycmdmousemove.setMove(i, j, k);
                        if(com.maddox.rts.Mouse.adapter().isInvert())
                            hotkeycmdmousemove.prepareInvert();
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdmousemove, true, true);
                        hotkeycmdmousemove.doMove();
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdmousemove, true, false);
                    }
                }

            }
        }

    }

    private final void doCmdMove(boolean flag, int i, int j, int k)
    {
        int l = hotKeys(i, j);
        java.lang.String s = (java.lang.String)keys.get(l);
        if(s != null)
        {
            com.maddox.rts.HotKeyCmd hotkeycmd = hotKeyCmdEnv.get(s);
            if(hotkeycmd != null && (hotkeycmd instanceof com.maddox.rts.HotKeyCmdMove) && hotkeycmd.isEnabled() && hotkeycmd.isRealTime() == flag && (!com.maddox.rts.Time.isPaused() || !hotkeycmd.isDisableIfTimePaused()))
            {
                ((com.maddox.rts.HotKeyCmdMove)hotkeycmd).setMove(k);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, true);
                hotkeycmd.start(i, j);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, true, false);
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, true);
                hotkeycmd.stop();
                com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmd, false, false);
            }
        }
    }

    public static final void joyMove(boolean flag, int i, int j, int k)
    {
        for(int l = 0; l < com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.size(); l++)
        {
            com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.get(l);
            if(hotkeyenv.bEnabled && hotkeyenv.hotKeyCmdEnv.isEnabled())
            {
                hotkeyenv.doCmdMove(flag, i, j, k);
                hotkeyenv.doCmdMove(flag, j, i, k);
            }
        }

    }

    public static final void mouseAbsMove(boolean flag, int i, int j, int k)
    {
        if(k == 0)
            return;
        for(int l = 0; l < com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.size(); l++)
        {
            com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.get(l);
            if(hotkeyenv.bEnabled && hotkeyenv.hotKeyCmdEnv.isEnabled())
            {
                hotkeyenv.doCmdMove(flag, 530, 0, k);
                hotkeyenv.doCmdMove(flag, 0, 530, k);
            }
        }

    }

    public static final void keyRedirect(boolean flag, int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1, int i2)
    {
        for(int j2 = 0; j2 < com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.size(); j2++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.get(j2);
            com.maddox.util.HashMapExt hashmapext = hotkeycmdenv.all();
            for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
            {
                com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getValue();
                if((hotkeycmd instanceof com.maddox.rts.HotKeyCmdRedirect) && hotkeycmd.isEnabled() && hotkeycmd.isRealTime() == flag && (!com.maddox.rts.Time.isPaused() || !hotkeycmd.isDisableIfTimePaused()))
                {
                    com.maddox.rts.HotKeyCmdRedirect hotkeycmdredirect = (com.maddox.rts.HotKeyCmdRedirect)hotkeycmd;
                    if(i == hotkeycmdredirect.idRedirect())
                    {
                        hotkeycmdredirect.setRedirect(j, k, l, i1, j1, k1, l1, i2);
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdredirect, true, true);
                        hotkeycmdredirect.doRedirect();
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdredirect, true, false);
                    }
                }
            }

        }

    }

    public static final void trackIRAngles(boolean flag, float f, float f1, float f2)
    {
        for(int i = 0; i < com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.size(); i++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.lst.get(i);
            if(hotkeycmdenv.isEnabled() && hotkeycmdenv.hotKeyEnv().bEnabled)
            {
                com.maddox.util.HashMapExt hashmapext = hotkeycmdenv.all();
                for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                {
                    com.maddox.rts.HotKeyCmd hotkeycmd = (com.maddox.rts.HotKeyCmd)entry.getValue();
                    if((hotkeycmd instanceof com.maddox.rts.HotKeyCmdTrackIRAngles) && hotkeycmd.isEnabled() && hotkeycmd.isRealTime() == flag && (!com.maddox.rts.Time.isPaused() || !hotkeycmd.isDisableIfTimePaused()))
                    {
                        com.maddox.rts.HotKeyCmdTrackIRAngles hotkeycmdtrackirangles = (com.maddox.rts.HotKeyCmdTrackIRAngles)hotkeycmd;
                        hotkeycmdtrackirangles.setAngles(f, f1, f2);
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdtrackirangles, true, true);
                        hotkeycmdtrackirangles.doAngles();
                        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.post(hotkeycmdtrackirangles, true, false);
                    }
                }

            }
        }

    }

    public com.maddox.rts.HotKeyCmdEnv hotKeyCmdEnv()
    {
        return hotKeyCmdEnv;
    }

    protected HotKeyEnv(java.lang.String s)
    {
        bEnabled = true;
        name = s;
        keys = new HashMapInt();
        com.maddox.rts.RTSConf.cur.hotKeyEnvs.envs.put(s, this);
        com.maddox.rts.RTSConf.cur.hotKeyEnvs.lst.add(this);
        hotKeyCmdEnv = (com.maddox.rts.HotKeyCmdEnv)com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.envs.get(s);
        if(hotKeyCmdEnv == null)
            hotKeyCmdEnv = new HotKeyCmdEnv(s);
    }

    public static final java.lang.String DEFAULT = "default";
    private static java.util.ArrayList removed = new ArrayList();
    private java.lang.String name;
    private boolean bEnabled;
    private com.maddox.util.HashMapInt keys;
    private com.maddox.rts.HotKeyCmdEnv hotKeyCmdEnv;

}
