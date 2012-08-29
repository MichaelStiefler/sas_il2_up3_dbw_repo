// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdChannel.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdChannel extends com.maddox.rts.Cmd
{
    class Stat
        implements com.maddox.rts.MsgTimeOutListener
    {

        private void step()
        {
            long l = com.maddox.rts.Time.currentReal();
            if(prevTime > 0L)
            {
                double d = (double)(l - prevTime) / 1000D;
                if(d > 0.0D)
                {
                    double d1 = (double)(ch.statNumSendGMsgs - statNumSendGMsgs) / d;
                    double d2 = (double)(ch.statSizeSendGMsgs - statSizeSendGMsgs) / d;
                    double d3 = (double)(ch.statNumSendFMsgs - statNumSendFMsgs) / d;
                    double d4 = (double)(ch.statSizeSendFMsgs - statSizeSendFMsgs) / d;
                    double d5 = (double)((ch.statNumSendGMsgs + ch.statNumSendGMsgs) - statNumSendGMsgs - statNumSendFMsgs) / d;
                    double d6 = (double)((ch.statSizeSendGMsgs + 3 * ch.statNumSendGMsgs + ch.statSizeSendFMsgs + ch.statHSizeSendFMsgs) - statSizeSendGMsgs - 3 * statNumSendGMsgs - statSizeSendFMsgs - statHSizeSendFMsgs) / d;
                    double d7 = (double)(ch.statNumReseivedMsgs - statNumReseivedMsgs) / d;
                    double d8 = (double)((ch.statSizeReseivedMsgs + ch.statHSizeReseivedMsgs) - statSizeReseivedMsgs - statHSizeReseivedMsgs) / d;
                    java.lang.System.out.println("ch " + ch.id() + ": ping: " + ch.ping() + "ms  > " + (int)d6 + "b/s  < " + (int)d8 + "b/s " + ch.gSendQueueLenght() + "/" + ch.gSendQueueSize());
                }
            }
            prevTime = l;
            statNumSendGMsgs = ch.statNumSendGMsgs;
            statSizeSendGMsgs = ch.statSizeSendGMsgs;
            statNumSendFMsgs = ch.statNumSendFMsgs;
            statSizeSendFMsgs = ch.statSizeSendFMsgs;
            statHSizeSendFMsgs = ch.statHSizeSendFMsgs;
            statNumFilteredMsgs = ch.statNumFilteredMsgs;
            statSizeFilteredMsgs = ch.statSizeFilteredMsgs;
            statNumReseivedMsgs = ch.statNumReseivedMsgs;
            statSizeReseivedMsgs = ch.statSizeReseivedMsgs;
            statHSizeReseivedMsgs = ch.statHSizeReseivedMsgs;
        }

        public void msgTimeOut(java.lang.Object obj)
        {
            if(ch.isDestroying())
            {
                destroy();
                return;
            }
            if(!stat.containsKey(ch))
            {
                return;
            } else
            {
                step();
                com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + (long)timeStep, this, null);
                return;
            }
        }

        public void destroy()
        {
            stat.remove(ch);
        }

        public void timeStep(int i)
        {
            timeStep = i;
        }

        private int timeStep;
        private com.maddox.rts.NetChannel ch;
        private long prevTime;
        private int statNumSendGMsgs;
        private int statSizeSendGMsgs;
        private int statNumSendFMsgs;
        private int statSizeSendFMsgs;
        private int statHSizeSendFMsgs;
        private int statNumFilteredMsgs;
        private int statSizeFilteredMsgs;
        private int statNumReseivedMsgs;
        private int statSizeReseivedMsgs;
        private int statHSizeReseivedMsgs;

        public Stat(com.maddox.rts.NetChannel netchannel, int i)
        {
            timeStep = i;
            ch = netchannel;
            stat.put(netchannel, this);
            step();
            com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + (long)i, this, null);
        }
    }


    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = com.maddox.rts.cmd.CmdChannel.exist(map, "SOCKET");
        int i = -1;
        if(com.maddox.rts.cmd.CmdChannel.nargs(map, "_$$") == 1)
        {
            i = com.maddox.rts.cmd.CmdChannel.arg(map, "_$$", 0, -1);
            if(i == -1)
            {
                ERR_HARD("Unknown number of channel");
                return null;
            }
            com.maddox.rts.NetChannel netchannel = com.maddox.rts.NetEnv.getChannel(i);
            if(netchannel == null)
            {
                ERR_HARD("Channel: " + i + " not found");
                return null;
            }
            if(com.maddox.rts.cmd.CmdChannel.exist(map, "DESTROY"))
            {
                netchannel.destroy("Connection lost.");
                return netchannel;
            }
            if(com.maddox.rts.cmd.CmdChannel.exist(map, "SPEED") || com.maddox.rts.cmd.CmdChannel.exist(map, "TIMEOUT") || com.maddox.rts.cmd.CmdChannel.exist(map, "STAT"))
            {
                if(com.maddox.rts.cmd.CmdChannel.nargs(map, "SPEED") == 1)
                {
                    double d = (double)com.maddox.rts.cmd.CmdChannel.arg(map, "SPEED", 0, 1000) / 1000D;
                    netchannel.setMaxSpeed(d);
                }
                if(com.maddox.rts.cmd.CmdChannel.nargs(map, "TIMEOUT") == 1)
                {
                    int j = com.maddox.rts.cmd.CmdChannel.arg(map, "TIMEOUT", 0, 131);
                    netchannel.setMaxTimeout(j * 1000);
                }
                if(com.maddox.rts.cmd.CmdChannel.nargs(map, "STAT") == 1)
                {
                    int k = com.maddox.rts.cmd.CmdChannel.arg(map, "STAT", 0, -1) * 1000;
                    if(k <= 0)
                    {
                        com.maddox.rts.cmd.Stat stat1 = (com.maddox.rts.cmd.Stat)stat.get(netchannel);
                        if(stat1 != null)
                            stat1.destroy();
                    } else
                    {
                        com.maddox.rts.cmd.Stat stat2 = (com.maddox.rts.cmd.Stat)stat.get(netchannel);
                        if(stat2 == null)
                            new Stat(netchannel, k);
                        else
                            stat2.timeStep(k);
                    }
                }
                return netchannel;
            }
        }
        if(i != -1)
        {
            com.maddox.rts.NetChannel netchannel1 = com.maddox.rts.NetEnv.getChannel(i);
            if(netchannel1 == null)
            {
                ERR_HARD("Channel: " + i + " not found");
                return null;
            }
            info(netchannel1, flag);
        } else
        {
            java.util.List list = com.maddox.rts.NetEnv.channels();
            int l = list.size();
            for(int i1 = 0; i1 < l; i1++)
            {
                com.maddox.rts.NetChannel netchannel2 = (com.maddox.rts.NetChannel)list.get(i1);
                info(netchannel2, flag);
            }

        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private void info(com.maddox.rts.NetChannel netchannel, boolean flag)
    {
        java.lang.String s = "READY";
        if(netchannel.isDestroyed())
            s = "DESTROYED";
        else
        if(netchannel.isDestroying())
            s = "DESTROYING";
        else
        if(netchannel.isIniting())
            s = "INITING";
        java.lang.String s1 = (netchannel.isPublic() ? "P" : "p") + (netchannel.isGlobal() ? "G" : "g") + (netchannel.isRealTime() ? "T" : "t");
        INFO_HARD(" " + netchannel.id() + ": [" + s + "." + s1 + "] ping: " + netchannel.ping() + "ms timeout: " + netchannel.getCurTimeout() / 1000 + "/" + netchannel.getMaxTimeout() / 1000 + "s speed: " + (int)(netchannel.getMaxSpeed() * 1000D) + "b/s");
        if(flag)
            INFO_HARD("    " + netchannel.socket().getLocalAddress() + ":" + netchannel.socket().getLocalPort() + (netchannel.isInitRemote() ? " <- " : " -> ") + netchannel.remoteAddress() + ":" + netchannel.remotePort());
    }

    public CmdChannel()
    {
        stat = new HashMap();
        param.put("DESTROY", null);
        param.put("SPEED", null);
        param.put("TIMEOUT", null);
        param.put("STAT", null);
        param.put("SOCKET", null);
        _properties.put("NAME", "channel");
        _levelAccess = 1;
    }

    public static final java.lang.String DESTROY = "DESTROY";
    public static final java.lang.String MAXSPEED = "SPEED";
    public static final java.lang.String TIMEOUT = "TIMEOUT";
    public static final java.lang.String STAT = "STAT";
    public static final java.lang.String SOCKET = "SOCKET";
    private java.util.HashMap stat;

}
