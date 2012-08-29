// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdMission.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetControlLock;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import java.io.PrintStream;
import java.util.HashMap;

public class CmdMission extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.lang.String s)
    {
        int i = s.indexOf("LOAD");
        int j = s.indexOf("BEGIN");
        int k = s.indexOf("END");
        int l = s.indexOf("DESTROY");
        if(i >= 0)
        {
            if(!com.maddox.rts.NetEnv.isActive())
            {
                ERR_HARD("net environment NOT active");
                return null;
            }
            if(com.maddox.rts.NetEnv.cur().control != null)
            {
                if(com.maddox.rts.NetEnv.cur().control instanceof com.maddox.rts.NetControlLock)
                {
                    ERR_HARD("net in progress connecting");
                    return null;
                }
                if(com.maddox.rts.NetEnv.cur().control.isMirror())
                {
                    ERR_HARD("this host is alredy client");
                    return null;
                }
            } else
            {
                ERR_HARD("this host is alredy client");
                return null;
            }
            java.lang.String s1 = "";
            int i1 = s.length();
            if(j > i && j < i1)
                i1 = j;
            if(k > i && k < i1)
                i1 = k;
            if(l > i && l < i1)
                i1 = l;
            if(i1 > i + "LOAD".length())
            {
                s1 = s.substring(i + "LOAD".length(), i1);
                int j1;
                for(j1 = 0; j1 < s1.length() && s1.charAt(j1) <= ' '; j1++);
                for(i1 = s1.length(); i1 > 0 && s1.charAt(i1 - 1) <= ' '; i1--);
                if(j1 > 0 || i1 < s1.length())
                    if(j1 < i1)
                        s1 = s1.substring(j1, i1);
                    else
                        s1 = "";
            }
            try
            {
                com.maddox.il2.game.Mission.load(s1);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                ERR_HARD(s1 + "  NOT loaded [" + exception + "]");
                return null;
            }
        }
        if(j >= 0)
        {
            if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
            {
                ERR_HARD("Mission NOT loaded");
                return null;
            }
            if(com.maddox.il2.game.Mission.isNet() && !com.maddox.il2.game.Mission.isServer())
            {
                ERR_HARD("Mission begining only from server");
                return null;
            }
            com.maddox.il2.game.Mission.cur().doBegin();
        }
        if(k >= 0)
        {
            if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
            {
                ERR_HARD("Mission NOT loaded");
                return null;
            }
            if(com.maddox.il2.game.Mission.isNet() && com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                return null;
            } else
            {
                com.maddox.il2.game.Mission.cur().doEnd();
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        if(l >= 0)
        {
            if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
            {
                ERR_HARD("Mission NOT loaded");
                return null;
            }
            if(com.maddox.il2.game.Mission.isNet() && com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                return null;
            } else
            {
                com.maddox.il2.game.Mission.cur().destroy();
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        if(com.maddox.il2.game.Mission.cur() == null || com.maddox.il2.game.Mission.cur().isDestroyed())
        {
            INFO_HARD("Mission NOT loaded");
        } else
        {
            com.maddox.il2.game.Mission.cur();
            if(com.maddox.il2.game.Mission.isPlaying())
                INFO_HARD("Mission: " + com.maddox.il2.game.Mission.cur().name() + " is Playing");
            else
                INFO_HARD("Mission: " + com.maddox.il2.game.Mission.cur().name() + " is Loaded");
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public boolean isRawFormat()
    {
        return true;
    }

    public CmdMission()
    {
        _properties.put("NAME", "mission");
        _levelAccess = 1;
    }

    public static final java.lang.String LOAD = "LOAD";
    public static final java.lang.String BEGIN = "BEGIN";
    public static final java.lang.String END = "END";
    public static final java.lang.String DESTROY = "DESTROY";
}
