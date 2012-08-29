// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Chat.java

package com.maddox.il2.net;

import com.maddox.il2.ai.World;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Spawn;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.RadioChannelSpawn;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.net:
//            ChatMessage, NetUser, NetServerParams, NetMissionTrack

public class Chat extends com.maddox.rts.NetObj
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                new Chat(netmsginput.channel(), i);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }


    public int getMaxBuflen()
    {
        return maxBufLen;
    }

    public void setMaxBufLen(int i)
    {
        if(i < 1)
            i = 1;
        maxBufLen = i;
        if(buf.size() > maxBufLen)
            stampUpdate++;
        for(; buf.size() > maxBufLen; buf.remove(buf.size() - 1));
    }

    public void clear()
    {
        buf.clear();
        stampUpdate++;
    }

    private com.maddox.il2.net.ChatMessage translateMsg(com.maddox.il2.net.ChatMessage chatmessage)
    {
        if(chatmessage.flags == 1)
        {
            if(resOrder == null)
                resOrder = java.util.ResourceBundle.getBundle("i18n/hud_order", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(chatmessage.msg);
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            boolean flag = true;
            java.lang.String s2;
            for(; stringtokenizer.hasMoreTokens(); stringbuffer.append(s2))
            {
                if(!flag)
                    stringbuffer.append(" > ");
                flag = false;
                java.lang.String s1 = stringtokenizer.nextToken();
                s2 = null;
                java.lang.String s3 = com.maddox.il2.ai.World.getPlayerLastCountry();
                if(s3 != null)
                    try
                    {
                        s2 = resOrder.getString(s1 + "_" + s3);
                    }
                    catch(java.lang.Exception exception1) { }
                if(s2 == null)
                    try
                    {
                        s2 = resOrder.getString(s1);
                    }
                    catch(java.lang.Exception exception2)
                    {
                        s2 = s1;
                    }
            }

            com.maddox.il2.net.ChatMessage chatmessage2 = new ChatMessage();
            chatmessage2.flags = chatmessage.flags;
            chatmessage2.from = chatmessage.from;
            chatmessage2.to = chatmessage.to;
            chatmessage2.msg = stringbuffer.toString();
            chatmessage = chatmessage2;
        } else
        if((chatmessage.flags & 0xe) != 0)
        {
            if(resLog == null)
                resLog = java.util.ResourceBundle.getBundle("i18n/netmessages", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
            com.maddox.il2.net.ChatMessage chatmessage1 = new ChatMessage();
            chatmessage1.flags = chatmessage.flags;
            chatmessage1.from = chatmessage.from;
            chatmessage1.to = chatmessage.to;
            int i = (chatmessage.flags & 0xe) >> 1;
            switch(i)
            {
            case 4: // '\004'
            case 5: // '\005'
            default:
                break;

            case 2: // '\002'
                if(chatmessage.param0 != null)
                    params[0] = ((com.maddox.il2.net.NetUser)chatmessage.param0).shortName();
                break;

            case 3: // '\003'
                if(chatmessage.param0 != null)
                    params[0] = ((com.maddox.il2.net.NetUser)chatmessage.param0).shortName();
                if(chatmessage.param1 != null)
                    params[1] = ((com.maddox.il2.net.NetUser)chatmessage.param1).shortName();
                break;

            case 6: // '\006'
                params[0] = chatmessage.param0;
                break;

            case 7: // '\007'
                params[0] = chatmessage.param0;
                params[1] = chatmessage.param1;
                break;
            }
            java.lang.String s = null;
            try
            {
                s = resLog.getString(chatmessage.msg);
            }
            catch(java.lang.Exception exception)
            {
                s = chatmessage.msg;
            }
            chatmessage1.msg = java.text.MessageFormat.format(s, params);
            chatmessage = chatmessage1;
            params[0] = params[1] = null;
        }
        return chatmessage;
    }

    private void addMsg(com.maddox.il2.net.ChatMessage chatmessage)
    {
        chatmessage = translateMsg(chatmessage);
        buf.add(0, chatmessage);
        stampUpdate++;
        for(; buf.size() > maxBufLen; buf.remove(buf.size() - 1));
        if(chatmessage.from != null)
            java.lang.System.out.println("Chat: " + chatmessage.from.shortName() + ": \t" + chatmessage.msg);
        else
            java.lang.System.out.println("Chat: --- " + chatmessage.msg);
    }

    public static void sendLogRnd(int i, java.lang.String s, com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        com.maddox.il2.net.Chat.sendLog(i, s + (int)(java.lang.Math.random() * 2D + 1.3999999999999999D), aircraft, aircraft1);
    }

    public static void sendLog(int i, java.lang.String s, com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        if(com.maddox.il2.game.Main.cur().chat == null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(i > com.maddox.il2.game.Main.cur().netServerParams.autoLogDetail())
            return;
        com.maddox.il2.net.NetUser netuser = null;
        if(aircraft != null && aircraft.isNetPlayer())
            netuser = aircraft.netUser();
        com.maddox.il2.net.NetUser netuser1 = null;
        if(aircraft1 != null && aircraft1.isNetPlayer())
            netuser1 = aircraft1.netUser();
        com.maddox.il2.net.Chat.sendLog(i, s, netuser, netuser1);
    }

    public static void sendLog(int i, java.lang.String s, com.maddox.il2.net.NetUser netuser, com.maddox.il2.net.NetUser netuser1)
    {
        if(com.maddox.il2.game.Main.cur().chat == null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(i > com.maddox.il2.game.Main.cur().netServerParams.autoLogDetail())
            return;
        int j = 2;
        if(netuser != null)
            j = 4;
        if(netuser1 != null)
            j = 6;
        com.maddox.il2.game.Main.cur().chat.send(null, s, null, (byte)j, netuser, netuser1, true);
    }

    public static void sendLog(int i, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        if(com.maddox.il2.game.Main.cur().chat == null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(i > com.maddox.il2.game.Main.cur().netServerParams.autoLogDetail())
            return;
        int j = 2;
        if(s1 != null)
            j = 12;
        if(s2 != null)
            j = 14;
        com.maddox.il2.game.Main.cur().chat.send(null, s, null, (byte)j, s1, s2, true);
    }

    public void send(com.maddox.rts.NetHost nethost, java.lang.String s, java.util.List list)
    {
        send(nethost, s, list, (byte)0);
    }

    public void send(com.maddox.rts.NetHost nethost, java.lang.String s, java.util.List list, byte byte0)
    {
        send(nethost, s, list, byte0, true);
    }

    public void send(com.maddox.rts.NetHost nethost, java.lang.String s, java.util.List list, byte byte0, boolean flag)
    {
        send(nethost, s, list, byte0, null, null, flag);
    }

    public void send(com.maddox.rts.NetHost nethost, java.lang.String s, java.util.List list, byte byte0, java.lang.Object obj, java.lang.Object obj1, boolean flag)
    {
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            return;
        com.maddox.il2.net.ChatMessage chatmessage = new ChatMessage();
        chatmessage.flags = byte0;
        chatmessage.from = nethost;
        chatmessage.to = list;
        if(s.length() > 80)
            s = s.substring(0, 80);
        int i = com.maddox.rts.NetMsgOutput.len255(s);
        if(list != null)
            i += list.size() * com.maddox.rts.NetMsgOutput.netObjReferenceLen();
        if(i > 250)
        {
            i -= 250;
            s = s.substring(0, s.length() - i);
        }
        chatmessage.msg = s;
        chatmessage.param0 = obj;
        chatmessage.param1 = obj1;
        if(flag)
            addMsg(chatmessage);
        if(isMirror() || isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeNetObj(chatmessage.from);
                netmsgguaranted.writeByte(chatmessage.flags);
                netmsgguaranted.write255(chatmessage.msg);
                if((chatmessage.flags & 0xe) != 0)
                {
                    int j = (chatmessage.flags & 0xe) >> 1;
                    switch(j)
                    {
                    case 2: // '\002'
                        netmsgguaranted.writeNetObj((com.maddox.rts.NetObj)obj);
                        break;

                    case 3: // '\003'
                        netmsgguaranted.writeNetObj((com.maddox.rts.NetObj)obj);
                        netmsgguaranted.writeNetObj((com.maddox.rts.NetObj)obj1);
                        break;

                    case 6: // '\006'
                        netmsgguaranted.write255((java.lang.String)obj);
                        break;

                    case 7: // '\007'
                        netmsgguaranted.write255((java.lang.String)obj);
                        netmsgguaranted.write255((java.lang.String)obj1);
                        break;
                    }
                }
                if(chatmessage.to != null)
                {
                    for(int k = 0; k < chatmessage.to.size(); k++)
                        netmsgguaranted.writeNetObj((com.maddox.rts.NetObj)chatmessage.to.get(k));

                }
                postExclude(null, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        netmsginput.reset();
        com.maddox.il2.net.ChatMessage chatmessage = new ChatMessage();
        chatmessage.from = (com.maddox.rts.NetHost)netmsginput.readNetObj();
        chatmessage.flags = (byte)netmsginput.readUnsignedByte();
        chatmessage.msg = netmsginput.read255();
        byte byte0 = 0;
        if((chatmessage.flags & 0xe) != 0)
        {
            int i = (chatmessage.flags & 0xe) >> 1;
            switch(i)
            {
            case 2: // '\002'
                chatmessage.param0 = netmsginput.readNetObj();
                byte0 = 1;
                break;

            case 3: // '\003'
                chatmessage.param0 = netmsginput.readNetObj();
                chatmessage.param1 = netmsginput.readNetObj();
                byte0 = 2;
                break;

            case 6: // '\006'
                chatmessage.param0 = netmsginput.read255();
                break;

            case 7: // '\007'
                chatmessage.param0 = netmsginput.read255();
                chatmessage.param1 = netmsginput.read255();
                break;
            }
        }
        boolean flag = false;
        int j = netmsginput.available() / com.maddox.rts.NetMsgInput.netObjReferenceLen();
        if(j == 0)
        {
            flag = true;
        } else
        {
            chatmessage.to = new ArrayList(j);
            for(int k = 0; k < j; k++)
                chatmessage.to.add(netmsginput.readNetObj());

            if(netmsginput.channel() instanceof com.maddox.rts.NetChannelInStream)
            {
                com.maddox.il2.net.NetUser netuser = com.maddox.il2.net.NetUser.findTrackWriter();
                flag = netuser == chatmessage.from || chatmessage.to.indexOf(netuser) >= 0;
            } else
            {
                flag = chatmessage.to.indexOf(com.maddox.rts.NetEnv.host()) >= 0;
            }
        }
        if(flag)
            addMsg(chatmessage);
        int l = 0;
        if(isMirror() && netmsginput.channel() != masterChannel())
            l = 1;
        if(isMirrored())
        {
            l += countMirrors();
            if(netmsginput.channel() != masterChannel())
                l--;
        }
        if(l > 0)
            postExclude(netmsginput.channel(), new NetMsgGuaranted(netmsginput, j + byte0 + 1));
        return true;
    }

    public void destroy()
    {
        if(USE_NET_PHONE)
        {
            com.maddox.sound.AudioDevice.endNetPhone();
            radioSpawn.killMasterChannels();
        }
        super.destroy();
        com.maddox.il2.game.Main.cur().chat = null;
    }

    public Chat()
    {
        super(null);
        buf = new ArrayList();
        stampUpdate = 0;
        maxBufLen = 80;
        params = new java.lang.Object[2];
        com.maddox.il2.game.Main.cur().chat = this;
        if(USE_NET_PHONE)
        {
            com.maddox.sound.AudioDevice.beginNetPhone();
            java.lang.String s = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio();
            int i = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).curCodec();
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(null, 0);
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(s, i);
        }
    }

    public Chat(com.maddox.rts.NetChannel netchannel, int i)
    {
        super(null, netchannel, i);
        buf = new ArrayList();
        stampUpdate = 0;
        maxBufLen = 80;
        params = new java.lang.Object[2];
        com.maddox.il2.game.Main.cur().chat = this;
        if(USE_NET_PHONE && !com.maddox.il2.net.NetMissionTrack.isPlaying())
            com.maddox.sound.AudioDevice.beginNetPhone();
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        return new NetMsgSpawn(this);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean USE_NET_PHONE = true;
    public static com.maddox.sound.RadioChannelSpawn radioSpawn = new RadioChannelSpawn();
    public java.util.ResourceBundle resOrder;
    public java.util.ResourceBundle resLog;
    public java.util.List buf;
    public int stampUpdate;
    private int maxBufLen;
    private java.lang.Object params[];

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.net.Chat.class, new SPAWN());
    }

}
