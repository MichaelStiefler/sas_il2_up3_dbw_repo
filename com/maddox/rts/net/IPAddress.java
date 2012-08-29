// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IPAddress.java

package com.maddox.rts.net;

import com.maddox.rts.NetAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class IPAddress extends com.maddox.rts.NetAddress
{

    public IPAddress()
    {
    }

    public java.net.InetAddress ip()
    {
        return ip;
    }

    public static com.maddox.rts.net.IPAddress fromIP(java.net.InetAddress inetaddress)
        throws java.net.UnknownHostException
    {
        com.maddox.rts.net.IPAddress ipaddress = (com.maddox.rts.net.IPAddress)ipHash.get(inetaddress);
        if(ipaddress == null)
        {
            ipaddress = new IPAddress();
            ipaddress.create(inetaddress.getHostAddress());
        }
        return ipaddress;
    }

    public boolean equals(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.net.IPAddress)
            return ip.equals(((com.maddox.rts.net.IPAddress)obj).ip);
        else
            return false;
    }

    public java.lang.String getHostName()
    {
        return ip.getHostName();
    }

    public byte[] getAddress()
    {
        return ip.getAddress();
    }

    public java.lang.String getHostAddress()
    {
        return ip.getHostAddress();
    }

    public java.lang.String toString()
    {
        return getHostAddress();
    }

    public void create(java.lang.String s)
        throws java.net.UnknownHostException
    {
        ip = java.net.InetAddress.getByName(s);
        ipHash.put(ip, this);
    }

    public com.maddox.rts.NetAddress getLocalHost()
        throws java.net.UnknownHostException
    {
        java.net.InetAddress inetaddress = java.net.InetAddress.getLocalHost();
        return com.maddox.rts.net.IPAddress.fromIP(inetaddress);
    }

    public com.maddox.rts.NetAddress getByName(java.lang.String s)
        throws java.net.UnknownHostException
    {
        java.net.InetAddress inetaddress = java.net.InetAddress.getByName(s);
        return com.maddox.rts.net.IPAddress.fromIP(inetaddress);
    }

    public com.maddox.rts.NetAddress[] getAllByName(java.lang.String s)
        throws java.net.UnknownHostException
    {
        java.net.InetAddress ainetaddress[] = java.net.InetAddress.getAllByName(s);
        int i = ainetaddress.length;
        if(i == 0)
            return null;
        com.maddox.rts.NetAddress anetaddress[] = new com.maddox.rts.NetAddress[i];
        for(int j = 0; j < i; j++)
            anetaddress[j] = com.maddox.rts.net.IPAddress.fromIP(ainetaddress[j]);

        return anetaddress;
    }

    private java.net.InetAddress ip;
    private static java.util.HashMap ipHash = new HashMap();

}
