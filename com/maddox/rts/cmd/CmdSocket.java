// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSocket.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetControlLock;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdSocket extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.rts.Cmd.nargs(map, "_$$") != 1)
        {
            if(com.maddox.rts.Cmd.exist(map, "LISTENER"))
            {
                if(com.maddox.rts.NetEnv.cur().connect == null)
                {
                    ERR_HARD("Listener object not available");
                    return null;
                }
                if(com.maddox.rts.Cmd.nargs(map, "LISTENER") == 0)
                    INFO_HARD(" Listener is " + (com.maddox.rts.NetEnv.cur().connect.isBindEnable() ? "enable" : "disable"));
                else
                    com.maddox.rts.NetEnv.cur().connect.bindEnable(com.maddox.rts.Cmd.arg(map, "LISTENER", 0, 0) != 0);
                return com.maddox.rts.NetEnv.cur().connect;
            }
            if(com.maddox.rts.Cmd.exist(map, "JOIN") && cmdenv.levelAccess() == 0)
            {
                if(com.maddox.rts.NetEnv.cur().connect == null)
                {
                    ERR_HARD("Join object not available");
                    return null;
                }
                if(com.maddox.rts.Cmd.exist(map, "BREAK"))
                    com.maddox.rts.NetEnv.cur().connect.joinBreak();
                else
                    INFO_HARD(" join " + (com.maddox.rts.NetEnv.cur().connect.isJoinProcess() ? "is processed" : "not active"));
                return com.maddox.rts.NetEnv.cur().connect;
            }
            java.util.ArrayList arraylist = new ArrayList();
            if(com.maddox.rts.Property.vars(arraylist, "netProtocol"))
            {
                INFO_HARD("The availablis protocols:");
                int i = arraylist.size();
                for(int j = 0; j < i; j++)
                {
                    java.lang.String s1 = (java.lang.String)arraylist.get(j);
                    java.lang.Class class3 = getProtocolClass(s1);
                    int k = com.maddox.rts.Property.intValue(class3, "maxChannels", 64);
                    double d = com.maddox.rts.Property.doubleValue(class3, "maxSpeed", 10D);
                    INFO_HARD("  " + s1 + " maxChannels: " + k + " maxSpeed: " + (int)(d * 1000D) + " bytes/sec");
                }

                return arraylist;
            } else
            {
                INFO_HARD("There are no availablis protocols");
                return null;
            }
        }
        java.lang.String s = com.maddox.rts.Cmd.arg(map, "_$$", 0);
        if(s == null)
        {
            ERR_HARD("Unknown name of the protocol");
            return null;
        }
        java.lang.Class class1 = getProtocolClass(s);
        if(class1 == null)
            return null;
        com.maddox.rts.NetSocket netsocket = null;
        try
        {
            netsocket = (com.maddox.rts.NetSocket)class1.newInstance();
        }
        catch(java.lang.Exception exception)
        {
            ERR_HARD("The class of the protocol is not 'NetSocket': " + class1.getName());
            return null;
        }
        java.lang.Class class2 = netsocket.addressClass();
        com.maddox.rts.NetAddress netaddress = null;
        if(com.maddox.rts.Cmd.nargs(map, "LOCALHOST") > 0)
            try
            {
                netaddress = (com.maddox.rts.NetAddress)class2.newInstance();
                netaddress.create(com.maddox.rts.Cmd.arg(map, "LOCALHOST", 0));
            }
            catch(java.lang.Exception exception1)
            {
                ERR_HARD("Bad LOCALHOST: " + com.maddox.rts.Cmd.arg(map, "LOCALHOST", 0));
                return null;
            }
        else
            try
            {
                netaddress = (com.maddox.rts.NetAddress)class2.newInstance();
                netaddress = netaddress.getLocalHost();
            }
            catch(java.lang.Exception exception2)
            {
                ERR_HARD("Bad LocalHost address");
                return null;
            }
        com.maddox.rts.NetAddress netaddress1 = null;
        if(com.maddox.rts.Cmd.nargs(map, "HOST") > 0)
            try
            {
                netaddress1 = (com.maddox.rts.NetAddress)class2.newInstance();
                netaddress1.create(com.maddox.rts.Cmd.arg(map, "HOST", 0));
            }
            catch(java.lang.Exception exception3)
            {
                ERR_HARD("Bad HOST: " + com.maddox.rts.Cmd.arg(map, "HOST", 0));
                return null;
            }
        int l = com.maddox.rts.Cmd.arg(map, "LOCALPORT", 0, -1);
        int i1 = com.maddox.rts.Cmd.arg(map, "PORT", 0, -1);
        int j1 = com.maddox.rts.Cmd.arg(map, "CHANNELS", 0, com.maddox.rts.Property.intValue(class1, "maxChannels", 64));
        double d1 = com.maddox.rts.Property.doubleValue(class1, "maxSpeed", 10D);
        if(com.maddox.rts.Cmd.exist(map, "SPEED"))
            d1 = com.maddox.rts.Cmd.arg(map, "SPEED", 0, d1 * 1000D) / 1000D;
        if(com.maddox.rts.Cmd.exist(map, "CREATE"))
        {
            com.maddox.rts.NetAddress netaddress2 = netaddress;
            if(netaddress2 == null)
                netaddress2 = netaddress1;
            int k1 = l;
            if(k1 == -1)
                k1 = i1;
            com.maddox.rts.NetSocket netsocket3 = findSocket(netaddress2, k1);
            if(netsocket3 != null)
            {
                if(netsocket3.maxChannels == 0 || com.maddox.rts.Cmd.exist(map, "CHANNELS"))
                    netsocket3.maxChannels = j1;
                if(com.maddox.rts.Cmd.exist(map, "SPEED"))
                    netsocket3.setMaxSpeed(d1);
                INFO_SOFT("Socket alredy exist");
                return netsocket3;
            }
            try
            {
                if(k1 == -1)
                    netsocket.open(0, netaddress2);
                else
                    netsocket.open(k1, netaddress2);
            }
            catch(java.lang.Exception exception5)
            {
                ERR_HARD(exception5.toString());
                return null;
            }
            netsocket.maxChannels = j1;
            netsocket.setMaxSpeed(d1);
            com.maddox.rts.NetEnv.addSocket(netsocket);
            return netsocket;
        }
        if(com.maddox.rts.Cmd.exist(map, "DESTROY"))
        {
            com.maddox.rts.NetSocket netsocket1 = findSocket(netaddress, l);
            if(netsocket1 == null)
            {
                ERR_HARD("Socket not found");
                return null;
            }
            if(com.maddox.rts.Cmd.exist(map, "HOST"))
            {
                java.util.List list1 = com.maddox.rts.NetEnv.channels();
                int j2 = list1.size();
                for(int i3 = 0; i3 < j2; i3++)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list1.get(i3);
                    if(netsocket1.equals(netchannel.socket()) && netchannel.remoteAddress().equals(netaddress1) && netchannel.remotePort() == i1)
                        netchannel.destroy();
                }

            } else
            {
                netsocket1.maxChannels = 0;
                java.util.List list2 = com.maddox.rts.NetEnv.channels();
                int k2 = list2.size();
                for(int j3 = 0; j3 < k2; j3++)
                {
                    com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)list2.get(j3);
                    if(netsocket1.equals(netchannel1.socket()))
                        netchannel1.destroy();
                }

            }
            return netsocket1;
        }
        if(com.maddox.rts.Cmd.exist(map, "JOIN") && cmdenv.levelAccess() == 0)
        {
            if(com.maddox.rts.NetEnv.cur().connect == null)
            {
                ERR_HARD("Join object not available");
                return null;
            }
            if(com.maddox.rts.NetEnv.cur().control != null && (com.maddox.rts.NetEnv.cur().control instanceof com.maddox.rts.NetControlLock))
            {
                ERR_HARD("Previous join not ended");
                return null;
            }
            if(com.maddox.rts.NetEnv.cur().connect.isJoinProcess())
            {
                ERR_HARD("Previous join not ended");
                return null;
            }
            if(i1 == -1)
                i1 = l;
            if(i1 == -1)
            {
                ERR_HARD("PORT not defined");
                return null;
            }
            if(com.maddox.rts.Cmd.nargs(map, "HOST") != 1)
            {
                ERR_HARD("HOST not defined");
                return null;
            }
            com.maddox.rts.NetSocket netsocket2 = findSocket(netaddress, l);
            if(netsocket2 != null)
            {
                netsocket = netsocket2;
                if(com.maddox.rts.Cmd.exist(map, "CHANNELS"))
                    netsocket2.maxChannels = j1;
                if(com.maddox.rts.Cmd.exist(map, "SPEED"))
                    netsocket2.setMaxSpeed(d1);
            } else
            {
                try
                {
                    netsocket.open(l != -1 ? l : 0, netaddress);
                }
                catch(java.lang.Exception exception4)
                {
                    ERR_HARD(exception4.toString());
                    return null;
                }
                netsocket.maxChannels = j1;
                netsocket.setMaxSpeed(d1);
                com.maddox.rts.NetEnv.addSocket(netsocket);
            }
            com.maddox.rts.NetEnv.cur().connect.join(netsocket, netaddress1, i1);
            return netsocket;
        }
        if(com.maddox.rts.Cmd.exist(map, "CHANNELS") || com.maddox.rts.Cmd.exist(map, "SPEED"))
        {
            com.maddox.rts.NetAddress netaddress3 = netaddress;
            if(netaddress3 == null)
                netaddress3 = netaddress1;
            int l1 = l;
            if(l1 == -1)
                l1 = i1;
            com.maddox.rts.NetSocket netsocket4 = findSocket(netaddress3, l1);
            if(netsocket4 == null)
            {
                if(com.maddox.rts.Cmd.exist(map, "CHANNELS"))
                    com.maddox.rts.Property.set(class1, "maxChannels", j1);
                if(com.maddox.rts.Cmd.exist(map, "SPEED"))
                    com.maddox.rts.Property.set(class1, "maxSpeed", d1);
                return null;
            }
            if(com.maddox.rts.Cmd.exist(map, "CHANNELS"))
                netsocket4.maxChannels = j1;
            if(com.maddox.rts.Cmd.exist(map, "SPEED"))
                netsocket4.setMaxSpeed(d1);
            return netsocket4;
        }
        java.util.List list = com.maddox.rts.NetEnv.socketsBlock();
        int i2 = list.size();
        for(int l2 = 0; l2 < i2; l2++)
        {
            com.maddox.rts.NetSocket netsocket5 = (com.maddox.rts.NetSocket)list.get(l2);
            INFO_HARD(" " + netsocket5.getLocalAddress() + ":" + netsocket5.getLocalPort() + " " + netsocket5.countChannels + "(" + netsocket5.maxChannels + ") " + (int)(netsocket5.getMaxSpeed() * 1000D) + " bytes/sec");
            java.util.List list3 = com.maddox.rts.NetEnv.channels();
            int l3 = list3.size();
            for(int i4 = 0; i4 < l3; i4++)
            {
                com.maddox.rts.NetChannel netchannel2 = (com.maddox.rts.NetChannel)list3.get(i4);
                if(netsocket5.equals(netchannel2.socket()))
                    INFO_HARD("  " + netchannel2.id() + ":" + (netchannel2.isInitRemote() ? " <- " : " -> ") + netchannel2.remoteAddress() + ":" + netchannel2.remotePort() + " " + (int)(netchannel2.getMaxSpeed() * 1000D) + " bytes/sec");
            }

        }

        list = com.maddox.rts.NetEnv.socketsNoBlock();
        i2 = list.size();
        for(int k3 = 0; k3 < i2; k3++)
        {
            com.maddox.rts.NetSocket netsocket6 = (com.maddox.rts.NetSocket)list.get(k3);
            INFO_HARD(" " + netsocket6.getLocalAddress() + ":" + netsocket6.getLocalPort() + " " + netsocket6.countChannels + "(" + netsocket6.maxChannels + ") " + (int)(netsocket6.getMaxSpeed() * 1000D) + " bytes/sec");
            java.util.List list4 = com.maddox.rts.NetEnv.channels();
            int j4 = list4.size();
            for(int k4 = 0; k4 < j4; k4++)
            {
                com.maddox.rts.NetChannel netchannel3 = (com.maddox.rts.NetChannel)list4.get(k4);
                if(netsocket6.equals(netchannel3.socket()))
                    INFO_HARD("  " + netchannel3.id() + ":" + (netchannel3.isInitRemote() ? " <- " : " -> ") + netchannel3.remoteAddress() + ":" + netchannel3.remotePort() + " " + (int)(netchannel3.getMaxSpeed() * 1000D) + " bytes/sec");
            }

        }

        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private com.maddox.rts.NetSocket findSocket(com.maddox.rts.NetAddress netaddress, int i)
    {
        java.util.List list = com.maddox.rts.NetEnv.socketsBlock();
        int j = list.size();
        if(netaddress == null)
        {
            for(int k = 0; k < j; k++)
            {
                com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)list.get(k);
                if(i == netsocket.getLocalPort())
                    return netsocket;
            }

        } else
        {
            for(int l = 0; l < j; l++)
            {
                com.maddox.rts.NetSocket netsocket1 = (com.maddox.rts.NetSocket)list.get(l);
                if(netaddress.equals(netsocket1.getLocalAddress()) && i == netsocket1.getLocalPort())
                    return netsocket1;
            }

        }
        list = com.maddox.rts.NetEnv.socketsNoBlock();
        j = list.size();
        if(netaddress == null)
        {
            for(int i1 = 0; i1 < j; i1++)
            {
                com.maddox.rts.NetSocket netsocket2 = (com.maddox.rts.NetSocket)list.get(i1);
                if(i == netsocket2.getLocalPort())
                    return netsocket2;
            }

        } else
        {
            for(int j1 = 0; j1 < j; j1++)
            {
                com.maddox.rts.NetSocket netsocket3 = (com.maddox.rts.NetSocket)list.get(j1);
                if(netaddress.equals(netsocket3.getLocalAddress()) && i == netsocket3.getLocalPort())
                    return netsocket3;
            }

        }
        return null;
    }

    private java.lang.Class getProtocolClass(java.lang.String s)
    {
        java.lang.String s1 = com.maddox.rts.Property.stringValue(s, "className", null);
        if(s1 == null)
        {
            ERR_HARD("The class of the protocol is not found");
            return null;
        }
        s1 = "com.maddox.rts.net." + s1;
        java.lang.Class class1 = null;
        try
        {
            class1 = java.lang.Class.forName(s1);
        }
        catch(java.lang.Exception exception)
        {
            ERR_HARD("The class of the protocol is not found: " + s1);
            return null;
        }
        return class1;
    }

    public CmdSocket()
    {
        param.put("CREATE", null);
        param.put("CHANNELS", null);
        param.put("SPEED", null);
        param.put("DESTROY", null);
        param.put("LOCALHOST", null);
        param.put("LOCALPORT", null);
        param.put("HOST", null);
        param.put("PORT", null);
        param.put("LISTENER", null);
        param.put("JOIN", null);
        param.put("BREAK", null);
        _properties.put("NAME", "socket");
        _levelAccess = 1;
    }

    public static final java.lang.String CREATE = "CREATE";
    public static final java.lang.String DESTROY = "DESTROY";
    public static final java.lang.String MAXCHANNELS = "CHANNELS";
    public static final java.lang.String MAXSPEED = "SPEED";
    public static final java.lang.String LOCALHOST = "LOCALHOST";
    public static final java.lang.String LOCALPORT = "LOCALPORT";
    public static final java.lang.String HOST = "HOST";
    public static final java.lang.String PORT = "PORT";
    public static final java.lang.String LISTENER = "LISTENER";
    public static final java.lang.String JOIN = "JOIN";
    public static final java.lang.String BREAK = "BREAK";
}
