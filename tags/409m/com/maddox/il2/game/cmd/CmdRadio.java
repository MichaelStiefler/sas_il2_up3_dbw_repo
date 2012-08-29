// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdRadio.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.RadioChannel;
import com.maddox.sound.RadioChannelSpawn;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdRadio extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(com.maddox.rts.Cmd.exist(map, "_$$"))
        {
            java.lang.String s = com.maddox.rts.Cmd.args(map, "_$$");
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(s, com.maddox.sound.RadioChannel.getCurrentCodec());
        } else
        if(com.maddox.rts.Cmd.exist(map, "NONE"))
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(null, 0);
        else
        if(com.maddox.rts.Cmd.exist(map, "COMMON"))
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(" 0", 1);
        else
        if(com.maddox.rts.Cmd.exist(map, "ARMY"))
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setRadio(" " + ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).getArmy(), 1);
        else
        if(com.maddox.rts.Cmd.exist(map, "LIST"))
        {
            java.lang.String s1 = ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio();
            if(s1 != null)
            {
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                for(int k = 0; k < list.size(); k++)
                {
                    com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(k);
                    if(s1.equals(netuser1.radio()))
                        INFO_HARD("  " + (k + 1) + ". " + netuser1.uniqueName());
                }

            }
        } else
        if(com.maddox.rts.Cmd.exist(map, "TEST"))
        {
            if(com.maddox.rts.Cmd.arg(map, "TEST", 0) == null)
                java.lang.System.out.println("  Test mode now " + (com.maddox.sound.RadioChannel.tstLoop ? "ON" : "OFF"));
            else
            if(com.maddox.rts.Cmd.arg(map, "TEST", 0).compareToIgnoreCase("on") == 0)
            {
                com.maddox.sound.RadioChannel.tstLoop = true;
                java.lang.System.out.println("  Warning : radio chatter now in test mode!");
            } else
            if(com.maddox.rts.Cmd.arg(map, "TEST", 0).compareToIgnoreCase("off") == 0)
            {
                com.maddox.sound.RadioChannel.tstLoop = false;
                java.lang.System.out.println("  Radio chatter test mode now OFF");
            } else
            {
                java.lang.System.out.println("  Invalid argument : " + com.maddox.rts.Cmd.arg(map, "LEVEL", 0));
            }
        } else
        if(com.maddox.rts.Cmd.exist(map, "LEVEL"))
        {
            if(com.maddox.rts.Cmd.arg(map, "LEVEL", 0) == null)
            {
                java.lang.System.out.println("  Voice activation level is " + com.maddox.sound.AudioDevice.getControl(505));
                java.lang.System.out.println("  valid values are 10..100");
            } else
            {
                try
                {
                    int i = java.lang.Integer.parseInt(com.maddox.rts.Cmd.arg(map, "LEVEL", 0));
                    if(i < 10 && i > 100)
                        throw new Error();
                    com.maddox.sound.AudioDevice.setControl(505, i);
                }
                catch(java.lang.Error error)
                {
                    java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "LEVEL", 0) + ") : number 10..100 expected");
                }
            }
        } else
        if(com.maddox.rts.Cmd.exist(map, "LATENCY"))
        {
            if(com.maddox.rts.Cmd.arg(map, "LATENCY", 0) == null)
            {
                java.lang.System.out.println("  Voice playback latency " + com.maddox.sound.AudioDevice.getControl(507));
                java.lang.System.out.println("  valid values are 1..20");
            } else
            {
                try
                {
                    int j = java.lang.Integer.parseInt(com.maddox.rts.Cmd.arg(map, "LATENCY", 0));
                    if(j < 1 && j > 20)
                        throw new Error();
                    com.maddox.sound.AudioDevice.setControl(507, j);
                }
                catch(java.lang.Error error1)
                {
                    java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "LATENCY", 0) + ") : number 1..20 expected");
                }
            }
        } else
        if(com.maddox.rts.Cmd.exist(map, "CODEC"))
        {
            if(com.maddox.rts.Cmd.arg(map, "CODEC", 0) == null)
                java.lang.System.out.println("  Current codec is " + com.maddox.sound.RadioChannel.getCurrentCodecName());
            else
                try
                {
                    byte byte0 = 0;
                    if(com.maddox.rts.Cmd.arg(map, "CODEC", 0).compareToIgnoreCase("lpc") == 0)
                        byte0 = 1;
                    else
                    if(com.maddox.rts.Cmd.arg(map, "CODEC", 0).compareToIgnoreCase("hq") == 0)
                        byte0 = 2;
                    else
                        throw new Error();
                    com.maddox.sound.RadioChannel.setCurrentCodec(byte0);
                }
                catch(java.lang.Error error2)
                {
                    java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "CODEC", 0) + ") : 'lpc' or 'hq' expected");
                }
        } else
        if(com.maddox.rts.Cmd.exist(map, "MODE"))
        {
            if(com.maddox.rts.Cmd.arg(map, "MODE", 0) == null)
                java.lang.System.out.println("  Radio PTT mode is " + (com.maddox.sound.AudioDevice.getPTTMode() ? "auto" : "manual"));
            else
                try
                {
                    if(com.maddox.rts.Cmd.arg(map, "MODE", 0).compareToIgnoreCase("auto") == 0)
                        com.maddox.sound.AudioDevice.setPTTMode(true);
                    else
                    if(com.maddox.rts.Cmd.arg(map, "MODE", 0).compareToIgnoreCase("manual") == 0)
                        com.maddox.sound.AudioDevice.setPTTMode(false);
                    else
                        throw new Error();
                }
                catch(java.lang.Error error3)
                {
                    java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "MODE", 0) + ") : 'auto' or 'manual' expected");
                }
        } else
        if(com.maddox.rts.Cmd.exist(map, "PTT"))
            try
            {
                if(com.maddox.rts.Cmd.arg(map, "PTT", 0) != null)
                    if(com.maddox.rts.Cmd.arg(map, "PTT", 0).compareToIgnoreCase("on") == 0)
                        com.maddox.sound.AudioDevice.setPTT(true);
                    else
                    if(com.maddox.rts.Cmd.arg(map, "PTT", 0).compareToIgnoreCase("off") == 0)
                        com.maddox.sound.AudioDevice.setPTT(false);
                    else
                        throw new Error();
            }
            catch(java.lang.Error error4)
            {
                java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "PTT", 0) + ") : 'on' or 'off' expected");
            }
        else
        if(com.maddox.rts.Cmd.exist(map, "AGC"))
            try
            {
                if(com.maddox.rts.Cmd.arg(map, "AGC", 0) == null)
                    java.lang.System.out.println("  Microphone AGC is " + (com.maddox.sound.AudioDevice.getAGC() ? "on" : "off"));
                else
                if(com.maddox.rts.Cmd.arg(map, "AGC", 0).compareToIgnoreCase("on") == 0)
                    com.maddox.sound.AudioDevice.setAGC(true);
                else
                if(com.maddox.rts.Cmd.arg(map, "AGC", 0).compareToIgnoreCase("off") == 0)
                    com.maddox.sound.AudioDevice.setAGC(false);
                else
                    throw new Error();
            }
            catch(java.lang.Error error5)
            {
                java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "AGC", 0) + ") : 'on' or 'off' expected");
            }
        else
        if(com.maddox.rts.Cmd.exist(map, "HRCH"))
            try
            {
                if(com.maddox.rts.Cmd.arg(map, "HRCH", 0) == null)
                    java.lang.System.out.println("High baudrate channels : " + (com.maddox.sound.RadioChannelSpawn.getUseHRChannels() ? "enabled" : "disabled"));
                else
                if(com.maddox.rts.Cmd.arg(map, "HRCH", 0).compareToIgnoreCase("on") == 0)
                    com.maddox.sound.RadioChannelSpawn.useHRChannels(true);
                else
                if(com.maddox.rts.Cmd.arg(map, "HRCH", 0).compareToIgnoreCase("off") == 0)
                    com.maddox.sound.RadioChannelSpawn.useHRChannels(false);
                else
                    throw new Error();
            }
            catch(java.lang.Error error6)
            {
                java.lang.System.out.println("  ERROR: invalid argument (" + com.maddox.rts.Cmd.arg(map, "HRCH", 0) + ") : 'on' or 'off' expected");
            }
        else
        if(com.maddox.rts.Cmd.exist(map, "HELP"))
        {
            java.lang.System.out.println("--- Radio chatter commands ---");
            java.lang.System.out.println("--- Command : values - description ---");
            java.lang.System.out.println("TEST    : on - test (hear) microphone input (network transmission disabled), off - end test");
            java.lang.System.out.println("LEVEL   : 1.0..5.0 number - voice activation level for auto voice activation (see MODE)");
            java.lang.System.out.println("LATENCY : 0.2..2.0 number - output buffer latency in seconds, bigger values increases");
            java.lang.System.out.println("          delay in messages and decrases drop-outs in audio");
            java.lang.System.out.println("CODEC   : lpc or hq - codec type for new channels; lpc - for dialup connection or hq - for LAN");
            java.lang.System.out.println("          one channel cannot have multiple codecs for different users");
            java.lang.System.out.println("MODE    : auto or manual - PTT mode - 'press to talk' or auto voice activation(see LEVEL)");
            java.lang.System.out.println("PTT     : on or off - in auto mode 'on' mutes voice input, in manual - enables, until called 'radio PTT off'");
            java.lang.System.out.println("AGC     : on or off - usage of automatic gain control of microphone");
            java.lang.System.out.println("HRCH    : on or off - enable/disable high baudrate codec on this computer");
            java.lang.System.out.println("--- end ---");
        } else
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser.isRadioNone())
                INFO_HARD("  Radio is NONE");
            else
            if(netuser.isRadioCommon())
                INFO_HARD("  Radio is COMMON");
            else
            if(netuser.isRadioArmy())
                INFO_HARD("  Radio is ARMY");
            else
                INFO_HARD("  Radio is " + netuser.radio() + "    [type 'radio HELP' or see readme for more info]");
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    public CmdRadio()
    {
        param.put("NONE", null);
        param.put("COMMON", null);
        param.put("ARMY", null);
        param.put("LIST", null);
        param.put("TEST", null);
        param.put("LEVEL", null);
        param.put("LATENCY", null);
        param.put("CODEC", null);
        param.put("MODE", null);
        param.put("PTT", null);
        param.put("AGC", null);
        param.put("HRCH", null);
        param.put("HELP", null);
        _properties.put("NAME", "radio");
        _levelAccess = 1;
    }

    public static final java.lang.String NONE = "NONE";
    public static final java.lang.String COMMON = "COMMON";
    public static final java.lang.String ARMY = "ARMY";
    public static final java.lang.String LIST = "LIST";
    public static final java.lang.String TEST = "TEST";
    public static final java.lang.String MODE = "MODE";
    public static final java.lang.String LEVEL = "LEVEL";
    public static final java.lang.String LATENCY = "LATENCY";
    public static final java.lang.String CODEC = "CODEC";
    public static final java.lang.String PTT = "PTT";
    public static final java.lang.String AGC = "AGC";
    public static final java.lang.String HRCH = "HRCH";
    public static final java.lang.String HELP = "HELP";
}
