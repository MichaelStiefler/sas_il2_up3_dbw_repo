// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdFile.java

package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.SFSReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdFile extends com.maddox.rts.Cmd
{

    private void saveParams(com.maddox.rts.CmdEnv cmdenv, java.lang.Object aobj[])
    {
        for(int i = 0; i < 10; i++)
        {
            java.lang.String s = java.lang.Integer.toString(i);
            if(cmdenv.existAtom(s, false))
            {
                aobj[i] = cmdenv.atom(s);
                cmdenv.delAtom(s);
            } else
            if(cmdenv.existAtom(s, true))
                cmdenv.setAtom(s, null);
        }

    }

    private void restoreParams(com.maddox.rts.CmdEnv cmdenv, java.lang.Object aobj[])
    {
        for(int i = 0; i < 10; i++)
        {
            java.lang.String s = java.lang.Integer.toString(i);
            if(aobj[i] != null)
                cmdenv.setAtom(s, aobj[i]);
            else
            if(cmdenv.existAtom(s, false))
                cmdenv.delAtom(s);
        }

    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        java.lang.String s = null;
        java.lang.Object aobj[] = new java.lang.Object[10];
        com.maddox.rts.CmdEnv cmdenv1 = cmdenv;
        saveParams(cmdenv1, aobj);
        if(map.containsKey("_$$"))
        {
            java.util.List list = (java.util.List)map.get("_$$");
            if(list.size() <= 11)
            {
                s = (java.lang.String)list.get(0);
                for(int i = 1; i < list.size(); i++)
                {
                    java.lang.String s1 = (java.lang.String)list.get(i);
                    cmdenv.setAtom(java.lang.Integer.toString(i - 1), s1);
                }

            } else
            {
                restoreParams(cmdenv1, aobj);
                ERR_HARD("Bad command format");
                return null;
            }
        } else
        {
            restoreParams(cmdenv1, aobj);
            ERR_HARD("Bad command format");
            return null;
        }
        java.lang.Object obj = null;
        java.lang.Object obj1 = null;
        boolean flag = true;
        if(!map.containsKey("CURENV"))
        {
            cmdenv = new CmdEnv(cmdenv);
            flag = false;
        } else
        {
            obj = cmdenv.atom(null, "_$$0");
            obj1 = cmdenv.atom(null, "_$$1");
        }
        java.lang.Object obj2 = null;
        boolean flag1 = map.containsKey("BREAK");
        boolean flag2 = cmdenv.flag("fast");
        boolean flag4 = false;
        if(!flag2)
            flag4 = cmdenv.flag("echo");
        try
        {
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            Object obj3 = null;
            do
            {
                java.lang.String s2 = bufferedreader.readLine();
                if(s2 == null)
                    break;
                if(s2.length() <= 0 || s2.startsWith("#"))
                    continue;
                stringbuffer.append(s2);
                if(flag4 && stringbuffer.charAt(0) != '@')
                    if(flag)
                    {
                        com.maddox.rts.CmdEnv _tmp = cmdenv;
                        INFO_SOFT((com.maddox.rts.CmdEnv.top().curNumCmd() + 1) + ">" + s2);
                    } else
                    {
                        INFO_SOFT('>' + s2);
                    }
                boolean flag5 = false;
                for(int j = stringbuffer.length() - 1; j >= 0; j--)
                {
                    if(stringbuffer.charAt(j) == '\\')
                    {
                        stringbuffer.setCharAt(j, ' ');
                        flag5 = true;
                        break;
                    }
                    if(stringbuffer.charAt(j) != ' ')
                        break;
                }

                if(flag5)
                    continue;
                if(stringbuffer.charAt(0) == '@')
                    stringbuffer.setCharAt(0, ' ');
                obj2 = cmdenv.exec(getStr(stringbuffer));
                if(flag1 && obj2 == null)
                {
                    ERR_HARD("Execute file is breaked");
                    break;
                }
                stringbuffer.delete(0, stringbuffer.length());
                boolean flag3 = cmdenv.flag("fast");
                if(flag3)
                    flag4 = false;
                else
                    flag4 = cmdenv.flag("echo");
            } while(true);
            bufferedreader.close();
        }
        catch(java.io.IOException ioexception)
        {
            ERR_HARD("File " + s + " not found");
            obj2 = null;
        }
        if(flag)
        {
            cmdenv.setAtom(null, "_$$0", obj);
            cmdenv.setAtom(null, "_$$1", obj1);
        }
        restoreParams(cmdenv1, aobj);
        return obj2;
    }

    private java.lang.String getStr(java.lang.StringBuffer stringbuffer)
    {
        for(int i = 0; i < stringbuffer.length(); i++)
            if(stringbuffer.charAt(i) == '\\' && i + 1 < stringbuffer.length())
                switch(stringbuffer.charAt(i + 1))
                {
                case 110: // 'n'
                    stringbuffer.setCharAt(i, '\n');
                    stringbuffer.deleteCharAt(i + 1);
                    break;

                case 114: // 'r'
                    stringbuffer.setCharAt(i, '\r');
                    stringbuffer.deleteCharAt(i + 1);
                    break;

                case 116: // 't'
                    stringbuffer.setCharAt(i, '\t');
                    stringbuffer.deleteCharAt(i + 1);
                    break;

                case 102: // 'f'
                    stringbuffer.setCharAt(i, '\f');
                    stringbuffer.deleteCharAt(i + 1);
                    break;
                }

        return stringbuffer.toString();
    }

    public CmdFile()
    {
        param.put("BREAK", null);
        param.put("CURENV", null);
        _properties.put("NAME", "file");
        _levelAccess = 1;
    }

    public static final java.lang.String BREAK = "BREAK";
    public static final java.lang.String CURENV = "CURENV";
}
