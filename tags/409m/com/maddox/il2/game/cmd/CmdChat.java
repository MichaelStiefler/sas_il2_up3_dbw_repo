// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdChat.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.Army;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdChat extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.il2.game.Main.cur().chat == null)
            return null;
        if(com.maddox.rts.Cmd.nargs(map, "_$$") == 0)
            if(map.containsKey("BUFSIZE"))
            {
                if(com.maddox.rts.Cmd.nargs(map, "BUFSIZE") == 0)
                {
                    INFO_HARD("Chat Buffer Size = " + com.maddox.il2.game.Main.cur().chat.getMaxBuflen());
                } else
                {
                    int i = com.maddox.rts.Cmd.arg(map, "BUFSIZE", 0, com.maddox.il2.game.Main.cur().chat.getMaxBuflen(), 1, 1000);
                    com.maddox.il2.game.Main.cur().chat.setMaxBufLen(i);
                }
                return com.maddox.rts.CmdEnv.RETURN_OK;
            } else
            {
                return null;
            }
        java.util.ArrayList arraylist = new ArrayList();
        fillUsers(map, arraylist);
        if(arraylist.size() == 0)
            return null;
        java.lang.String s = com.maddox.rts.Cmd.args(map, "_$$");
        if(arraylist.size() == com.maddox.rts.NetEnv.hosts().size())
            com.maddox.il2.game.Main.cur().chat.send(com.maddox.rts.NetEnv.host(), s, null);
        else
            com.maddox.il2.game.Main.cur().chat.send(com.maddox.rts.NetEnv.host(), s, arraylist);
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private void fillUsers(java.util.Map map, java.util.List list)
    {
        if(map.containsKey("ALL"))
        {
            for(int i = 0; i < com.maddox.rts.NetEnv.hosts().size(); i++)
                list.add(com.maddox.rts.NetEnv.hosts().get(i));

            return;
        }
        if(map.containsKey("MY_ARMY"))
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            int k = netuser.getArmy();
            for(int l = 0; l < com.maddox.rts.NetEnv.hosts().size(); l++)
            {
                com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(l);
                if(netuser1.getArmy() == k)
                    list.add(netuser1);
            }

            return;
        }
        if(!map.containsKey("TO") && !map.containsKey("TO#") && !map.containsKey("ARMY"))
        {
            for(int j = 0; j < com.maddox.rts.NetEnv.hosts().size(); j++)
                list.add(com.maddox.rts.NetEnv.hosts().get(j));

            return;
        }
        java.util.ArrayList arraylist = new ArrayList();
        java.util.HashMap hashmap = new HashMap();
        if(map.containsKey("TO"))
        {
            int i1 = com.maddox.rts.Cmd.nargs(map, "TO");
            if(i1 == 1 && "*".equals(com.maddox.rts.Cmd.arg(map, "TO", 0)))
            {
                for(int l1 = 0; l1 < com.maddox.rts.NetEnv.hosts().size(); l1++)
                    list.add(com.maddox.rts.NetEnv.hosts().get(l1));

                return;
            }
            for(int i2 = 0; i2 < i1; i2++)
            {
                java.lang.String s = com.maddox.rts.Cmd.arg(map, "TO", i2);
                for(int i3 = 0; i3 < com.maddox.rts.NetEnv.hosts().size(); i3++)
                {
                    com.maddox.il2.net.NetUser netuser2 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(i3);
                    java.lang.String s3 = netuser2.uniqueName();
                    if(!s.equals(s3))
                        continue;
                    if(!hashmap.containsKey(netuser2))
                    {
                        hashmap.put(netuser2, null);
                        arraylist.add(netuser2);
                    }
                    break;
                }

            }

        }
        if(map.containsKey("TO#"))
        {
            int j1 = com.maddox.rts.Cmd.nargs(map, "TO#");
            for(int j2 = 0; j2 < j1; j2++)
            {
                java.lang.String s1 = com.maddox.rts.Cmd.arg(map, "TO#", j2);
                if(s1.charAt(0) >= '0' && s1.charAt(0) <= '9')
                {
                    int j3 = com.maddox.rts.Cmd.arg(map, "TO#", j2, 1000, 0, 1000);
                    if(j3 > 0 && j3 <= com.maddox.rts.NetEnv.hosts().size())
                    {
                        com.maddox.il2.net.NetUser netuser3 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(j3 - 1);
                        if(!hashmap.containsKey(netuser3))
                        {
                            hashmap.put(netuser3, null);
                            arraylist.add(netuser3);
                        }
                    }
                }
            }

        }
        if(map.containsKey("ARMY"))
        {
            int k1 = com.maddox.rts.Cmd.nargs(map, "ARMY");
            for(int k2 = 0; k2 < k1; k2++)
            {
                int l2 = -1;
                java.lang.String s2 = com.maddox.rts.Cmd.arg(map, "ARMY", k2);
                if(s2.charAt(0) >= '0' && s2.charAt(0) <= '9')
                {
                    l2 = com.maddox.rts.Cmd.arg(map, "ARMY", k2, 1000, 0, 1000);
                    if(l2 >= com.maddox.il2.ai.Army.amountNet())
                        continue;
                } else
                {
                    for(l2 = 0; l2 < com.maddox.il2.ai.Army.amountNet(); l2++)
                        if(com.maddox.il2.ai.Army.name(l2).equals(s2))
                            break;

                    if(l2 == com.maddox.il2.ai.Army.amountNet())
                        continue;
                }
                for(int k3 = 0; k3 < com.maddox.rts.NetEnv.hosts().size(); k3++)
                {
                    com.maddox.il2.net.NetUser netuser4 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(k3);
                    if(netuser4.getArmy() == l2 && !hashmap.containsKey(netuser4))
                    {
                        hashmap.put(netuser4, null);
                        arraylist.add(netuser4);
                    }
                }

            }

        }
        list.addAll(arraylist);
    }

    public CmdChat()
    {
        param.put("ALL", null);
        param.put("MY_ARMY", null);
        param.put("ARMY", null);
        param.put("TO", null);
        param.put("TO#", null);
        param.put("BUFSIZE", null);
        _properties.put("NAME", "chat");
        _levelAccess = 1;
    }

    public static final java.lang.String ALL = "ALL";
    public static final java.lang.String MY_ARMY = "MY_ARMY";
    public static final java.lang.String ARMY = "ARMY";
    public static final java.lang.String TO = "TO";
    public static final java.lang.String TON = "TO#";
    public static final java.lang.String BUFSIZE = "BUFSIZE";
}
