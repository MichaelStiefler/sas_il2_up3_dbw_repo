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

public class CmdRadio extends Cmd
{
  public static final String NONE = "NONE";
  public static final String COMMON = "COMMON";
  public static final String ARMY = "ARMY";
  public static final String LIST = "LIST";
  public static final String TEST = "TEST";
  public static final String MODE = "MODE";
  public static final String LEVEL = "LEVEL";
  public static final String LATENCY = "LATENCY";
  public static final String CODEC = "CODEC";
  public static final String PTT = "PTT";
  public static final String AGC = "AGC";
  public static final String HRCH = "HRCH";
  public static final String HELP = "HELP";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null)
      return null;
    String str;
    if (exist(paramMap, "_$$")) {
      str = args(paramMap, "_$$");
      ((NetUser)NetEnv.host()).setRadio(str, RadioChannel.getCurrentCodec());
    }
    else if (exist(paramMap, "NONE")) {
      ((NetUser)NetEnv.host()).setRadio(null, 0);
    }
    else if (exist(paramMap, "COMMON")) {
      ((NetUser)NetEnv.host()).setRadio(" 0", 1);
    }
    else if (exist(paramMap, "ARMY")) {
      ((NetUser)NetEnv.host()).setRadio(" " + ((NetUser)NetEnv.host()).getArmy(), 1);
    }
    else if (exist(paramMap, "LIST")) {
      str = ((NetUser)NetEnv.host()).radio();
      if (str != null) {
        List localList = NetEnv.hosts();
        for (int m = 0; m < localList.size(); m++) {
          NetUser localNetUser2 = (NetUser)localList.get(m);
          if (str.equals(localNetUser2.radio())) {
            INFO_HARD("  " + (m + 1) + ". " + localNetUser2.uniqueName());
          }
        }
      }
    }
    else if (exist(paramMap, "TEST")) {
      if (arg(paramMap, "TEST", 0) == null) {
        System.out.println("  Test mode now " + (RadioChannel.tstLoop ? "ON" : "OFF"));
      }
      else if (arg(paramMap, "TEST", 0).compareToIgnoreCase("on") == 0) {
        RadioChannel.tstLoop = true;
        System.out.println("  Warning : radio chatter now in test mode!");
      }
      else if (arg(paramMap, "TEST", 0).compareToIgnoreCase("off") == 0) {
        RadioChannel.tstLoop = false;
        System.out.println("  Radio chatter test mode now OFF");
      }
      else {
        System.out.println("  Invalid argument : " + arg(paramMap, "LEVEL", 0));
      }

    }
    else if (exist(paramMap, "LEVEL")) {
      if (arg(paramMap, "LEVEL", 0) == null) {
        System.out.println("  Voice activation level is " + AudioDevice.getControl(505));
        System.out.println("  valid values are 10..100");
      } else {
        try {
          int i = Integer.parseInt(arg(paramMap, "LEVEL", 0));
          if ((i < 10) && (i > 100)) throw new Error();
          AudioDevice.setControl(505, i);
        }
        catch (Error localError1) {
          System.out.println("  ERROR: invalid argument (" + arg(paramMap, "LEVEL", 0) + ") : number 10..100 expected");
        }
      }

    }
    else if (exist(paramMap, "LATENCY")) {
      if (arg(paramMap, "LATENCY", 0) == null) {
        System.out.println("  Voice playback latency " + AudioDevice.getControl(507));
        System.out.println("  valid values are 1..20");
      } else {
        try {
          int j = Integer.parseInt(arg(paramMap, "LATENCY", 0));
          if ((j < 1) && (j > 20)) throw new Error();
          AudioDevice.setControl(507, j);
        }
        catch (Error localError2) {
          System.out.println("  ERROR: invalid argument (" + arg(paramMap, "LATENCY", 0) + ") : number 1..20 expected");
        }
      }

    }
    else if (exist(paramMap, "CODEC")) {
      if (arg(paramMap, "CODEC", 0) == null)
        System.out.println("  Current codec is " + RadioChannel.getCurrentCodecName());
      else {
        try {
          int k = 0;
          if (arg(paramMap, "CODEC", 0).compareToIgnoreCase("lpc") == 0) k = 1;
          else if (arg(paramMap, "CODEC", 0).compareToIgnoreCase("hq") == 0) k = 2;
          else
            throw new Error();
          RadioChannel.setCurrentCodec(k);
        }
        catch (Error localError3) {
          System.out.println("  ERROR: invalid argument (" + arg(paramMap, "CODEC", 0) + ") : 'lpc' or 'hq' expected");
        }
      }

    }
    else if (exist(paramMap, "MODE")) {
      if (arg(paramMap, "MODE", 0) == null)
        System.out.println("  Radio PTT mode is " + (AudioDevice.getPTTMode() ? "auto" : "manual"));
      else {
        try {
          if (arg(paramMap, "MODE", 0).compareToIgnoreCase("auto") == 0) AudioDevice.setPTTMode(true);
          else if (arg(paramMap, "MODE", 0).compareToIgnoreCase("manual") == 0) AudioDevice.setPTTMode(false);
          else
            throw new Error();
        }
        catch (Error localError4) {
          System.out.println("  ERROR: invalid argument (" + arg(paramMap, "MODE", 0) + ") : 'auto' or 'manual' expected");
        }
      }

    }
    else if (exist(paramMap, "PTT")) {
      try {
        if (arg(paramMap, "PTT", 0) != null)
        {
          if (arg(paramMap, "PTT", 0).compareToIgnoreCase("on") == 0) AudioDevice.setPTT(true);
          else if (arg(paramMap, "PTT", 0).compareToIgnoreCase("off") == 0) AudioDevice.setPTT(false);
          else
            throw new Error();
        }
      } catch (Error localError5) {
        System.out.println("  ERROR: invalid argument (" + arg(paramMap, "PTT", 0) + ") : 'on' or 'off' expected");
      }

    }
    else if (exist(paramMap, "AGC")) {
      try {
        if (arg(paramMap, "AGC", 0) == null) {
          System.out.println("  Microphone AGC is " + (AudioDevice.getAGC() ? "on" : "off"));
        }
        else if (arg(paramMap, "AGC", 0).compareToIgnoreCase("on") == 0) AudioDevice.setAGC(true);
        else if (arg(paramMap, "AGC", 0).compareToIgnoreCase("off") == 0) AudioDevice.setAGC(false);
        else
          throw new Error();
      }
      catch (Error localError6) {
        System.out.println("  ERROR: invalid argument (" + arg(paramMap, "AGC", 0) + ") : 'on' or 'off' expected");
      }

    }
    else if (exist(paramMap, "HRCH")) {
      try {
        if (arg(paramMap, "HRCH", 0) == null) {
          System.out.println("High baudrate channels : " + (RadioChannelSpawn.getUseHRChannels() ? "enabled" : "disabled"));
        }
        else if (arg(paramMap, "HRCH", 0).compareToIgnoreCase("on") == 0) RadioChannelSpawn.useHRChannels(true);
        else if (arg(paramMap, "HRCH", 0).compareToIgnoreCase("off") == 0) RadioChannelSpawn.useHRChannels(false);
        else
          throw new Error();
      }
      catch (Error localError7) {
        System.out.println("  ERROR: invalid argument (" + arg(paramMap, "HRCH", 0) + ") : 'on' or 'off' expected");
      }

    }
    else if (exist(paramMap, "HELP")) {
      System.out.println("--- Radio chatter commands ---");
      System.out.println("--- Command : values - description ---");
      System.out.println("TEST    : on - test (hear) microphone input (network transmission disabled), off - end test");
      System.out.println("LEVEL   : 1.0..5.0 number - voice activation level for auto voice activation (see MODE)");
      System.out.println("LATENCY : 0.2..2.0 number - output buffer latency in seconds, bigger values increases");
      System.out.println("          delay in messages and decrases drop-outs in audio");
      System.out.println("CODEC   : lpc or hq - codec type for new channels; lpc - for dialup connection or hq - for LAN");
      System.out.println("          one channel cannot have multiple codecs for different users");
      System.out.println("MODE    : auto or manual - PTT mode - 'press to talk' or auto voice activation(see LEVEL)");
      System.out.println("PTT     : on or off - in auto mode 'on' mutes voice input, in manual - enables, until called 'radio PTT off'");
      System.out.println("AGC     : on or off - usage of automatic gain control of microphone");
      System.out.println("HRCH    : on or off - enable/disable high baudrate codec on this computer");
      System.out.println("--- end ---");
    }
    else {
      NetUser localNetUser1 = (NetUser)NetEnv.host();
      if (localNetUser1.isRadioNone())
        INFO_HARD("  Radio is NONE");
      else if (localNetUser1.isRadioCommon())
        INFO_HARD("  Radio is COMMON");
      else if (localNetUser1.isRadioArmy())
        INFO_HARD("  Radio is ARMY");
      else
        INFO_HARD("  Radio is " + localNetUser1.radio() + "    [type 'radio HELP' or see readme for more info]");
    }
    return CmdEnv.RETURN_OK;
  }

  public CmdRadio()
  {
    this.param.put("NONE", null);
    this.param.put("COMMON", null);
    this.param.put("ARMY", null);
    this.param.put("LIST", null);
    this.param.put("TEST", null);
    this.param.put("LEVEL", null);
    this.param.put("LATENCY", null);
    this.param.put("CODEC", null);
    this.param.put("MODE", null);
    this.param.put("PTT", null);
    this.param.put("AGC", null);
    this.param.put("HRCH", null);
    this.param.put("HELP", null);
    this._properties.put("NAME", "radio");
    this._levelAccess = 1;
  }
}