// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdKickN.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmdKickN extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        java.util.ArrayList arraylist = new ArrayList();
        fillUsers(map, arraylist);
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)arraylist.get(i);
            if(!netuser.isMaster())
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(netuser);
        }

        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private void fillUsers(java.util.Map map, java.util.List list)
    {
        java.util.ArrayList arraylist = new ArrayList();
        java.util.HashMap hashmap = new HashMap();
        if(map.containsKey("_$$"))
        {
            int i = com.maddox.rts.Cmd.nargs(map, "_$$");
            for(int j = 0; j < i; j++)
            {
                java.lang.String s = com.maddox.rts.Cmd.arg(map, "_$$", j);
                if(s.charAt(0) >= '0' && s.charAt(0) <= '9')
                {
                    int k = com.maddox.rts.Cmd.arg(map, "_$$", j, 1000, 0, 1000);
                    if(k > 0 && k <= com.maddox.rts.NetEnv.hosts().size())
                    {
                        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(k - 1);
                        if(!hashmap.containsKey(netuser))
                        {
                            hashmap.put(netuser, null);
                            arraylist.add(netuser);
                        }
                    }
                }
            }

        }
        list.addAll(arraylist);
    }

    public CmdKickN()
    {
        _properties.put("NAME", "kick#");
        _levelAccess = 1;
    }
}
