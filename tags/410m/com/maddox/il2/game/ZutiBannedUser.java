// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiBannedUser.java

package com.maddox.il2.game;

import com.maddox.rts.Time;

public class ZutiBannedUser
{

    public ZutiBannedUser()
    {
    }

    public java.lang.String getName()
    {
        return name;
    }

    public void setName(java.lang.String s)
    {
        name = s;
    }

    public java.lang.String getIP()
    {
        return IP;
    }

    public void setIP(java.lang.String s)
    {
        IP = s;
    }

    public void setDuration(long l)
    {
        duration = l;
    }

    public void setPermanent(boolean flag)
    {
        permanent = flag;
    }

    public boolean isMatch(java.lang.String s, java.lang.String s1)
    {
        return name.trim().equalsIgnoreCase(s.trim()) && IP.trim().equalsIgnoreCase(s1.trim());
    }

    public boolean isBanned()
    {
        if(permanent)
            return true;
        return com.maddox.rts.Time.current() < duration;
    }

    private java.lang.String name;
    private java.lang.String IP;
    private long duration;
    private boolean permanent;
}
