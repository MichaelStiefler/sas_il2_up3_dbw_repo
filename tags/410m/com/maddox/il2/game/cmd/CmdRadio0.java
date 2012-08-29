// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdRadio0.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.net.Chat;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.RadioChannel;
import com.maddox.sound.RadioChannelSpawn;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdRadio0 extends com.maddox.rts.Cmd
{

    public CmdRadio0()
    {
        param.put("NEW", null);
        param.put("KILL", null);
        param.put("SET", null);
        param.put("LIST", null);
        param.put("INFO", null);
        param.put("TEST", null);
        _properties.put("NAME", "radio");
        _levelAccess = 0;
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        if(map.isEmpty())
            com.maddox.il2.net.Chat.radioSpawn.printInfo();
        else
        if(map.containsKey("_$$"))
        {
            java.lang.System.out.println("Unknown command :" + com.maddox.il2.game.cmd.CmdRadio0.arg(map, "_$$", 0));
        } else
        {
            if(map.containsKey("NEW"))
            {
                java.lang.String s = com.maddox.il2.game.cmd.CmdRadio0.arg(map, "NEW", 0);
                if(s == null)
                    java.lang.System.out.println("ERROR: no name");
                else
                    com.maddox.il2.net.Chat.radioSpawn.create(s, com.maddox.sound.RadioChannel.getCurrentCodec());
            }
            if(map.containsKey("KILL"))
            {
                java.lang.String s1 = com.maddox.il2.game.cmd.CmdRadio0.arg(map, "KILL", 0);
                if(s1 == null)
                    java.lang.System.out.println("ERROR: no name");
                else
                    com.maddox.il2.net.Chat.radioSpawn.kill(s1);
            }
            if(map.containsKey("SET"))
            {
                java.lang.String s2 = com.maddox.il2.game.cmd.CmdRadio0.arg(map, "SET", 0);
                com.maddox.il2.net.Chat.radioSpawn.set(s2);
            }
            if(map.containsKey("LIST"))
                com.maddox.il2.net.Chat.radioSpawn.list();
            if(map.containsKey("INFO"))
                com.maddox.il2.net.Chat.radioSpawn.printInfo();
            if(map.containsKey("TEST"))
            {
                java.lang.String s3 = com.maddox.il2.game.cmd.CmdRadio0.arg(map, "TEST", 0);
                if(s3 == null)
                    java.lang.System.out.println("TEST MODE: " + com.maddox.sound.RadioChannel.tstMode + " loop: " + com.maddox.sound.RadioChannel.tstLoop);
                else
                if(s3.equals("brief"))
                    com.maddox.sound.RadioChannel.tstMode = 1;
                else
                if(s3.equals("block"))
                    com.maddox.sound.RadioChannel.tstMode = 2;
                else
                if(s3.equals("mixer"))
                    com.maddox.sound.RadioChannel.tstMode = 3;
                else
                if(s3.equals("loop"))
                {
                    com.maddox.sound.RadioChannel.tstLoop = true;
                    java.lang.System.out.println("WARNING: Radio chatter started in TEST MODE");
                } else
                if(s3.equals("norm"))
                {
                    com.maddox.sound.RadioChannel.tstLoop = false;
                    com.maddox.sound.RadioChannel.tstMode = 0;
                } else
                {
                    java.lang.System.out.println("ERROR: invalid command" + s3);
                }
            }
            if(map.containsKey("STAT"))
                com.maddox.sound.AudioDevice.inputStat();
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public static final java.lang.String NEW = "NEW";
    public static final java.lang.String KILL = "KILL";
    public static final java.lang.String SET = "SET";
    public static final java.lang.String LIST = "LIST";
    public static final java.lang.String INFO = "INFO";
    public static final java.lang.String TEST = "TEST";
    public static final java.lang.String STAT = "STAT";
}
