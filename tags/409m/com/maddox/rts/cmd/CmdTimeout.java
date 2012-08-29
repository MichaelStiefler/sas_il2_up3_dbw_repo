// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdTimeout.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;

// Referenced classes of package com.maddox.rts.cmd:
//            CmdTimeoutAction

public class CmdTimeout extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.lang.String s)
    {
        com.maddox.util.QuoteTokenizer quotetokenizer = new QuoteTokenizer(s);
        if(!quotetokenizer.hasMoreTokens())
        {
            ERR_HARD("timeout not present");
            return null;
        }
        java.lang.String s1 = quotetokenizer.nextToken();
        int i = 0;
        try
        {
            i = java.lang.Integer.parseInt(s1);
        }
        catch(java.lang.Exception exception)
        {
            ERR_HARD("bad format timeout: " + s1);
            return null;
        }
        new CmdTimeoutAction(cmdenv, quotetokenizer.getGap(), i);
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public boolean isRawFormat()
    {
        return true;
    }

    public CmdTimeout()
    {
        _properties.put("NAME", "timeout");
        _levelAccess = 1;
    }
}
