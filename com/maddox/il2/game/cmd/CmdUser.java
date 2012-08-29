// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdUser.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserLeft;
import com.maddox.il2.net.NetUserStat;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdUser extends com.maddox.rts.Cmd
{

    protected void INFO_HARD(java.lang.String s)
    {
        if(bEventLog)
            com.maddox.il2.ai.EventLog.type(false, s);
        else
            super.INFO_HARD(s);
    }

    private boolean isDServer(com.maddox.il2.net.NetUser netuser)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return false;
        if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
            return false;
        else
            return com.maddox.il2.game.Main.cur().netServerParams.host() == netuser;
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        java.util.ArrayList arraylist = new ArrayList();
        fillUsers(map, arraylist, false);
        if(arraylist.size() == 0 && !com.maddox.rts.Cmd.exist(map, "STAT"))
            return null;
        bEventLog = com.maddox.rts.Cmd.exist(map, "EVENTLOG");
        if(com.maddox.rts.Cmd.exist(map, "STAT"))
        {
            fillUsers(map, arraylist, true);
            for(int i = 0; i < arraylist.size(); i++)
            {
                java.lang.Object obj = arraylist.get(i);
                boolean flag = false;
                com.maddox.il2.net.NetUserStat netuserstat;
                java.lang.String s;
                if(obj instanceof com.maddox.il2.net.NetUser)
                {
                    com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)obj;
                    if(isDServer(netuser1))
                        continue;
                    netuserstat = netuser1.curstat();
                    s = netuser1.uniqueName();
                } else
                {
                    com.maddox.il2.net.NetUserLeft netuserleft = (com.maddox.il2.net.NetUserLeft)obj;
                    netuserstat = netuserleft.stat;
                    s = netuserleft.uniqueName;
                    flag = true;
                }
                INFO_HARD("-------------------------------------------------------");
                INFO_HARD("Name: \t" + s);
                INFO_HARD("Score: \t" + (flag ? 0 : (int)netuserstat.score));
                INFO_HARD("State: \t" + playerState(netuserstat, flag));
                INFO_HARD("Enemy Aircraft Kill: \t" + netuserstat.enemyKill[0]);
                INFO_HARD("Enemy Static Aircraft Kill: \t" + netuserstat.enemyKill[8]);
                INFO_HARD("Enemy Tank Kill: \t" + netuserstat.enemyKill[1]);
                INFO_HARD("Enemy Car Kill: \t" + netuserstat.enemyKill[2]);
                INFO_HARD("Enemy Artillery Kill: \t" + netuserstat.enemyKill[3]);
                INFO_HARD("Enemy AAA Kill: \t" + netuserstat.enemyKill[4]);
                INFO_HARD("Enemy Wagon Kill: \t" + netuserstat.enemyKill[6]);
                INFO_HARD("Enemy Ship Kill: \t" + netuserstat.enemyKill[7]);
                INFO_HARD("Friend Aircraft Kill: \t" + netuserstat.friendKill[0]);
                INFO_HARD("Friend Static Aircraft Kill: \t" + netuserstat.friendKill[8]);
                INFO_HARD("Friend Tank Kill: \t" + netuserstat.friendKill[1]);
                INFO_HARD("Friend Car Kill: \t" + netuserstat.friendKill[2]);
                INFO_HARD("Friend Artillery Kill: \t" + netuserstat.friendKill[3]);
                INFO_HARD("Friend AAA Kill: \t" + netuserstat.friendKill[4]);
                INFO_HARD("Friend Wagon Kill: \t" + netuserstat.friendKill[6]);
                INFO_HARD("Friend Ship Kill: \t" + netuserstat.friendKill[7]);
                INFO_HARD("Fire Bullets: \t\t" + netuserstat.bulletsFire);
                INFO_HARD("Hit Bullets: \t\t" + netuserstat.bulletsHit);
                INFO_HARD("Hit Air Bullets: \t" + netuserstat.bulletsHitAir);
                INFO_HARD("Fire Roskets: \t\t" + netuserstat.rocketsFire);
                INFO_HARD("Hit Roskets: \t\t" + netuserstat.rocketsHit);
                INFO_HARD("Fire Bombs: \t\t" + netuserstat.bombFire);
                INFO_HARD("Hit Bombs: \t\t" + netuserstat.bombHit);
            }

            INFO_HARD("-------------------------------------------------------");
        } else
        {
            INFO_HARD(" N       Name           Ping    Score   Army        Aircraft");
            for(int j = 0; j < arraylist.size(); j++)
            {
                java.lang.StringBuffer stringbuffer = new StringBuffer();
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)arraylist.get(j);
                if(!isDServer(netuser))
                {
                    int k = com.maddox.rts.NetEnv.hosts().indexOf(netuser);
                    stringbuffer.append(" ");
                    if(k >= 0)
                        stringbuffer.append(k + 1);
                    else
                        stringbuffer.append("0");
                    for(; stringbuffer.length() < 8; stringbuffer.append(" "));
                    stringbuffer.append(netuser.uniqueName());
                    for(; stringbuffer.length() < 24; stringbuffer.append(" "));
                    stringbuffer.append(" ");
                    stringbuffer.append(netuser.ping);
                    for(; stringbuffer.length() < 32; stringbuffer.append(" "));
                    stringbuffer.append(" ");
                    stringbuffer.append((int)netuser.curstat().score);
                    for(; stringbuffer.length() < 40; stringbuffer.append(" "));
                    stringbuffer.append("(");
                    stringbuffer.append(netuser.getArmy());
                    stringbuffer.append(")");
                    stringbuffer.append(com.maddox.il2.ai.Army.name(netuser.getArmy()));
                    for(; stringbuffer.length() < 52; stringbuffer.append(" "));
                    com.maddox.il2.objects.air.Aircraft aircraft = netuser.findAircraft();
                    if(com.maddox.il2.engine.Actor.isValid(aircraft))
                    {
                        stringbuffer.append(aircraft.typedName());
                        stringbuffer.append(" ");
                        for(; stringbuffer.length() < 64; stringbuffer.append(" "));
                        stringbuffer.append(com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName"));
                    }
                    INFO_HARD(stringbuffer.toString());
                }
            }

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private java.lang.String playerState(com.maddox.il2.net.NetUserStat netuserstat, boolean flag)
    {
        if(flag)
            return "Left the Game";
        if((netuserstat.curPlayerState & 1) != 0)
            return "KIA";
        if((netuserstat.curPlayerState & 0x10) != 0)
            return "Captured";
        if((netuserstat.curPlayerState & 8) != 0)
            return "Emergency Landed";
        if((netuserstat.curPlayerState & 4) != 0)
            return "Landed at Airfield";
        if((netuserstat.curPlayerState & 2) != 0)
            return "Hit the Silk";
        if((netuserstat.curPlayerState & 0x20) != 0)
            return "Selects Aircraft";
        else
            return "In Flight";
    }

    private void fillUsers(java.util.Map map, java.util.List list, boolean flag)
    {
        boolean flag1 = false;
        if(!map.containsKey("_$$") && !map.containsKey("#") && !map.containsKey("ARMY"))
            flag1 = true;
        if(map.containsKey("_$$") && com.maddox.rts.Cmd.nargs(map, "_$$") == 1 && "*".equals(com.maddox.rts.Cmd.arg(map, "_$$", 0)))
            flag1 = true;
        if(flag1)
        {
            if(flag)
            {
                list.addAll(com.maddox.il2.net.NetUserLeft.all);
            } else
            {
                list.add(com.maddox.rts.NetEnv.host());
                for(int i = 0; i < com.maddox.rts.NetEnv.hosts().size(); i++)
                    list.add(com.maddox.rts.NetEnv.hosts().get(i));

            }
            return;
        }
        java.util.ArrayList arraylist = new ArrayList();
        java.util.HashMap hashmap = new HashMap();
        if(map.containsKey("_$$"))
        {
            int j = com.maddox.rts.Cmd.nargs(map, "_$$");
            for(int i1 = 0; i1 < j; i1++)
            {
                java.lang.String s = com.maddox.rts.Cmd.arg(map, "_$$", i1);
                if(flag)
                {
                    for(int i2 = 0; i2 < com.maddox.il2.net.NetUserLeft.all.size(); i2++)
                    {
                        com.maddox.il2.net.NetUserLeft netuserleft = (com.maddox.il2.net.NetUserLeft)com.maddox.il2.net.NetUserLeft.all.get(i2);
                        if(s.equals(netuserleft.uniqueName) && !hashmap.containsKey(netuserleft))
                        {
                            hashmap.put(netuserleft, null);
                            arraylist.add(netuserleft);
                        }
                    }

                } else
                {
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                    if(s.equals(netuser.uniqueName()))
                    {
                        if(!hashmap.containsKey(netuser))
                        {
                            hashmap.put(netuser, null);
                            arraylist.add(netuser);
                        }
                    } else
                    {
                        for(int k2 = 0; k2 < com.maddox.rts.NetEnv.hosts().size(); k2++)
                        {
                            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(k2);
                            java.lang.String s3 = netuser1.uniqueName();
                            if(!s.equals(s3))
                                continue;
                            if(!hashmap.containsKey(netuser1))
                            {
                                hashmap.put(netuser1, null);
                                arraylist.add(netuser1);
                            }
                            break;
                        }

                    }
                }
            }

        }
        if(map.containsKey("#"))
        {
            int k = com.maddox.rts.Cmd.nargs(map, "#");
            for(int j1 = 0; j1 < k; j1++)
            {
                java.lang.String s1 = com.maddox.rts.Cmd.arg(map, "#", j1);
                if(s1.charAt(0) >= '0' && s1.charAt(0) <= '9')
                {
                    int j2 = com.maddox.rts.Cmd.arg(map, "#", j1, 1000, 0, 1000);
                    if(flag)
                    {
                        int l2 = j2 - 1 - com.maddox.rts.NetEnv.hosts().size();
                        if(l2 >= 0 && l2 < com.maddox.il2.net.NetUserLeft.all.size())
                        {
                            com.maddox.il2.net.NetUserLeft netuserleft1 = (com.maddox.il2.net.NetUserLeft)com.maddox.il2.net.NetUserLeft.all.get(l2);
                            if(!hashmap.containsKey(netuserleft1))
                            {
                                hashmap.put(netuserleft1, null);
                                arraylist.add(netuserleft1);
                            }
                        }
                    } else
                    if(j2 > 0 && j2 <= com.maddox.rts.NetEnv.hosts().size())
                    {
                        com.maddox.il2.net.NetUser netuser2 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(j2 - 1);
                        if(!hashmap.containsKey(netuser2))
                        {
                            hashmap.put(netuser2, null);
                            arraylist.add(netuser2);
                        }
                    } else
                    if(j2 == 0)
                    {
                        com.maddox.il2.net.NetUser netuser3 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
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
            int l = com.maddox.rts.Cmd.nargs(map, "ARMY");
            for(int k1 = 0; k1 < l; k1++)
            {
                int l1 = -1;
                java.lang.String s2 = com.maddox.rts.Cmd.arg(map, "ARMY", k1);
                if(s2.charAt(0) >= '0' && s2.charAt(0) <= '9')
                {
                    l1 = com.maddox.rts.Cmd.arg(map, "ARMY", k1, 1000, 0, 1000);
                    if(l1 >= com.maddox.il2.ai.Army.amountNet())
                        continue;
                } else
                {
                    for(l1 = 0; l1 < com.maddox.il2.ai.Army.amountNet(); l1++)
                        if(com.maddox.il2.ai.Army.name(l1).equals(s2))
                            break;

                    if(l1 == com.maddox.il2.ai.Army.amountNet())
                        continue;
                }
                if(flag)
                {
                    for(int i3 = 0; i3 < com.maddox.il2.net.NetUserLeft.all.size(); i3++)
                    {
                        com.maddox.il2.net.NetUserLeft netuserleft2 = (com.maddox.il2.net.NetUserLeft)com.maddox.il2.net.NetUserLeft.all.get(i3);
                        if(l1 == netuserleft2.army && !hashmap.containsKey(netuserleft2))
                        {
                            hashmap.put(netuserleft2, null);
                            arraylist.add(netuserleft2);
                        }
                    }

                } else
                {
                    com.maddox.il2.net.NetUser netuser4 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                    if(l1 == netuser4.getArmy())
                    {
                        if(!hashmap.containsKey(netuser4))
                        {
                            hashmap.put(netuser4, null);
                            arraylist.add(netuser4);
                        }
                    } else
                    {
                        for(int j3 = 0; j3 < com.maddox.rts.NetEnv.hosts().size(); j3++)
                        {
                            com.maddox.il2.net.NetUser netuser5 = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(j3);
                            if(l1 != netuser5.getArmy())
                                continue;
                            if(!hashmap.containsKey(netuser5))
                            {
                                hashmap.put(netuser5, null);
                                arraylist.add(netuser5);
                            }
                            break;
                        }

                    }
                }
            }

        }
        list.addAll(arraylist);
    }

    public CmdUser()
    {
        bEventLog = false;
        param.put("#", null);
        param.put("ARMY", null);
        param.put("STAT", null);
        param.put("EVENTLOG", null);
        _properties.put("NAME", "user");
        _levelAccess = 1;
    }

    private static final boolean DEBUG = false;
    public static final java.lang.String NN = "#";
    public static final java.lang.String ARMY = "ARMY";
    public static final java.lang.String STAT = "STAT";
    public static final java.lang.String EVENTLOG = "EVENTLOG";
    private boolean bEventLog;
}
