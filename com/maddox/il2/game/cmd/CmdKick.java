// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdKick.java

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

public class CmdKick extends com.maddox.rts.Cmd
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
            int i = com.maddox.il2.game.cmd.CmdKick.nargs(map, "_$$");
label0:
            for(int j = 0; j < i; j++)
            {
                java.lang.String s = com.maddox.il2.game.cmd.CmdKick.arg(map, "_$$", j);
                int k = 0;
                do
                {
                    if(k >= com.maddox.rts.NetEnv.hosts().size())
                        continue label0;
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(k);
                    java.lang.String s1 = netuser.uniqueName();
                    if(s.equals(s1))
                    {
                        if(!hashmap.containsKey(netuser))
                        {
                            hashmap.put(netuser, null);
                            arraylist.add(netuser);
                        }
                        continue label0;
                    }
                    k++;
                } while(true);
            }

        }
        list.addAll(arraylist);
    }

    public CmdKick()
    {
        _properties.put("NAME", "kick");
        _levelAccess = 1;
    }
}
