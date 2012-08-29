// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdMaxPing.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdMaxPing extends com.maddox.rts.Cmd
    implements com.maddox.rts.MsgTimeOutListener
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMirror())
            return null;
        if(map.containsKey("DELAY"))
            delay = com.maddox.il2.game.cmd.CmdMaxPing.arg(map, "DELAY", 0, 5, 0, 60);
        if(map.containsKey("WARNINGS"))
            num = com.maddox.il2.game.cmd.CmdMaxPing.arg(map, "WARNINGS", 0, 3, 0, 100);
        if(map.containsKey("_$$"))
        {
            maxping = com.maddox.il2.game.cmd.CmdMaxPing.arg(map, "_$$", 0, 3000, 0, 30000);
        } else
        {
            INFO_HARD(" maxping  " + maxping + " ms");
            INFO_HARD(" delay    " + delay + " s");
            INFO_HARD(" warnings " + num);
        }
        checkTimeMsg();
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(delay <= 0)
            return;
        if(maxping <= 0)
            return;
        msg.post(delay);
        for(int i = 0; i < com.maddox.rts.NetEnv.hosts().size(); i++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(i);
            if(!com.maddox.il2.engine.Actor.isAlive(netuser.findAircraft()))
                continue;
            com.maddox.rts.NetChannel netchannel = netuser.masterChannel();
            int j = netchannel.ping();
            if(j <= maxping)
                continue;
            int k = 0;
            if(!com.maddox.rts.Property.containsValue(netuser, "maxpingCounter"))
                com.maddox.rts.Property.set(netuser, "maxpingCounter", k);
            else
                k = com.maddox.rts.Property.intValue(netuser, "maxpingCounter");
            if(++k > num)
            {
                com.maddox.il2.net.Chat.sendLog(0, "user_timeouts", netuser, null);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(netuser);
            } else
            {
                com.maddox.rts.Property.set(netuser, "maxpingCounter", k);
                java.util.ArrayList arraylist = new ArrayList();
                arraylist.add(netuser);
                java.lang.String s = "Your ping (" + j + ") is larger than allowed (" + maxping + ").";
                com.maddox.il2.game.Main.cur().chat.send(null, s, arraylist, (byte)0, false);
            }
        }

    }

    private void checkTimeMsg()
    {
        if(msg == null)
        {
            msg = new MsgTimeOut();
            msg.setListener(this);
            msg.setNotCleanAfterSend();
            msg.setFlags(64);
        }
        if(delay <= 0)
            return;
        if(maxping <= 0)
            return;
        if(!msg.busy())
            msg.post(delay);
    }

    public CmdMaxPing()
    {
        maxping = 3000;
        delay = 10;
        num = 3;
        param.put("DELAY", null);
        param.put("WARNINGS", null);
        _properties.put("NAME", "maxping");
        _levelAccess = 1;
    }

    public static final java.lang.String DELAY = "DELAY";
    public static final java.lang.String NUM = "WARNINGS";
    private int maxping;
    private int delay;
    private int num;
    private com.maddox.rts.MsgTimeOut msg;
}
