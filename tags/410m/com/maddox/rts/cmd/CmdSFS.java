// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdSFS.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.SFS;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSFS extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        if(com.maddox.rts.cmd.CmdSFS.nargs(map, "MOUNT") > 0)
        {
            java.lang.String s = com.maddox.rts.cmd.CmdSFS.arg(map, "MOUNT", 0);
            java.lang.String s2 = null;
            if(com.maddox.rts.cmd.CmdSFS.nargs(map, "MOUNT") > 1)
                s2 = com.maddox.rts.cmd.CmdSFS.arg(map, "MOUNT", 1);
            try
            {
                if(s2 != null)
                    com.maddox.rts.SFS.mountAs(s, s2, 0);
                else
                    com.maddox.rts.SFS.mount(s, 0);
            }
            catch(java.lang.Exception exception1)
            {
                bMountError = true;
                ERR_HARD("SFS library (" + s + ") NOT Mounted: " + exception1.getMessage());
                exception1.printStackTrace();
            }
            flag = true;
        }
        if(com.maddox.rts.cmd.CmdSFS.nargs(map, "UNMOUNT") > 0)
        {
            java.lang.String s1 = com.maddox.rts.cmd.CmdSFS.arg(map, "UNMOUNT", 0);
            try
            {
                com.maddox.rts.SFS.unMount(s1);
            }
            catch(java.lang.Exception exception)
            {
                ERR_HARD("SFS library (" + s1 + ") NOT UnMounted: " + exception.getMessage());
                exception.printStackTrace();
            }
            flag = true;
        }
        if(flag)
        {
            return com.maddox.rts.CmdEnv.RETURN_OK;
        } else
        {
            ERR_HARD("Bad command format");
            return null;
        }
    }

    public CmdSFS()
    {
        param.put("MOUNT", null);
        param.put("UNMOUNT", null);
        _properties.put("NAME", "sfs");
        _levelAccess = 0;
    }

    public static final java.lang.String MOUNT = "MOUNT";
    public static final java.lang.String UNMOUNT = "UNMOUNT";
    public static boolean bMountError = false;

}
