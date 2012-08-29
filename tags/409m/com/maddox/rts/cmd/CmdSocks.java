// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSocks.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.net.SocksUdpSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSocks extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = true;
        if(com.maddox.rts.Cmd.exist(map, "ON"))
        {
            com.maddox.rts.net.SocksUdpSocket.setProxyEnable(true);
            flag = false;
        } else
        if(com.maddox.rts.Cmd.exist(map, "OFF"))
        {
            com.maddox.rts.net.SocksUdpSocket.setProxyEnable(false);
            flag = false;
        }
        if(com.maddox.rts.Cmd.exist(map, "HOST"))
        {
            if(com.maddox.rts.Cmd.nargs(map, "HOST") > 0)
                com.maddox.rts.net.SocksUdpSocket.setProxyHost(com.maddox.rts.Cmd.arg(map, "HOST", 0));
            else
                com.maddox.rts.net.SocksUdpSocket.setProxyHost(null);
            flag = false;
        }
        if(com.maddox.rts.Cmd.exist(map, "PORT") && com.maddox.rts.Cmd.nargs(map, "PORT") > 0)
        {
            com.maddox.rts.net.SocksUdpSocket.setProxyPort(com.maddox.rts.Cmd.arg(map, "PORT", 0, 1080));
            flag = false;
        }
        if(com.maddox.rts.Cmd.exist(map, "USER"))
        {
            if(com.maddox.rts.Cmd.nargs(map, "USER") > 0)
                com.maddox.rts.net.SocksUdpSocket.setProxyUser(com.maddox.rts.Cmd.arg(map, "USER", 0));
            else
                com.maddox.rts.net.SocksUdpSocket.setProxyUser(null);
            flag = false;
        }
        if(com.maddox.rts.Cmd.exist(map, "PWD"))
        {
            if(com.maddox.rts.Cmd.nargs(map, "PWD") > 0)
                com.maddox.rts.net.SocksUdpSocket.setProxyPassword(com.maddox.rts.Cmd.arg(map, "PWD", 0));
            else
                com.maddox.rts.net.SocksUdpSocket.setProxyPassword(null);
            flag = false;
        }
        if(flag)
        {
            INFO_HARD(" Proxy socks is " + (com.maddox.rts.net.SocksUdpSocket.isProxyEnable() ? "enable" : "disable"));
            INFO_HARD("   HOST " + (com.maddox.rts.net.SocksUdpSocket.getProxyHost() == null ? "UNKNOWN" : com.maddox.rts.net.SocksUdpSocket.getProxyHost()));
            INFO_HARD("   PORT " + com.maddox.rts.net.SocksUdpSocket.getProxyPort());
            if(com.maddox.rts.net.SocksUdpSocket.getProxyUser() != null)
                INFO_HARD("   USER " + com.maddox.rts.net.SocksUdpSocket.getProxyUser());
            if(com.maddox.rts.net.SocksUdpSocket.getProxyPassword() != null)
                INFO_HARD("   PWD " + com.maddox.rts.net.SocksUdpSocket.getProxyPassword());
        }
        return null;
    }

    public CmdSocks()
    {
        param.put("HOST", null);
        param.put("PORT", null);
        param.put("USER", null);
        param.put("PWD", null);
        param.put("ON", null);
        param.put("OFF", null);
        _properties.put("NAME", "socks");
        _levelAccess = 1;
    }

    public static final java.lang.String HOST = "HOST";
    public static final java.lang.String PORT = "PORT";
    public static final java.lang.String USER = "USER";
    public static final java.lang.String PWD = "PWD";
    public static final java.lang.String ON = "ON";
    public static final java.lang.String OFF = "OFF";
}
