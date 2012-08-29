// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdTimeout.java

package com.maddox.rts.cmd;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

class CmdTimeoutAction extends com.maddox.rts.Message
{

    CmdTimeoutAction(com.maddox.rts.CmdEnv cmdenv, java.lang.String s, long l)
    {
        env = cmdenv;
        cmd = s;
        post(64, this, com.maddox.rts.Time.currentReal() + l);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        env.exec(cmd);
        return true;
    }

    com.maddox.rts.CmdEnv env;
    java.lang.String cmd;
}
