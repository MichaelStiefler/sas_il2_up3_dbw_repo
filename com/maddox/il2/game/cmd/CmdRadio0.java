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

public class CmdRadio0 extends Cmd
{
  public static final String NEW = "NEW";
  public static final String KILL = "KILL";
  public static final String SET = "SET";
  public static final String LIST = "LIST";
  public static final String INFO = "INFO";
  public static final String TEST = "TEST";
  public static final String STAT = "STAT";

  public CmdRadio0()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("NEW", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("KILL", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SET", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("LIST", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("INFO", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TEST", null);
    this._properties.put("NAME", "radio");
    this._levelAccess = 0;
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap) {
    int i = 0;
    if (paramMap.isEmpty()) {
      Chat.radioSpawn.printInfo();
    }
    else if (paramMap.containsKey("_$$")) {
      System.out.println("Unknown command :" + Cmd.arg(paramMap, "_$$", 0));
    }
    else
    {
      String str;
      if (paramMap.containsKey("NEW")) {
        str = Cmd.arg(paramMap, "NEW", 0);
        if (str == null) System.out.println("ERROR: no name"); else
          Chat.radioSpawn.create(str, RadioChannel.getCurrentCodec());
      }
      if (paramMap.containsKey("KILL")) {
        str = Cmd.arg(paramMap, "KILL", 0);
        if (str == null) System.out.println("ERROR: no name"); else
          Chat.radioSpawn.kill(str);
      }
      if (paramMap.containsKey("SET")) {
        str = Cmd.arg(paramMap, "SET", 0);
        Chat.radioSpawn.set(str);
      }
      if (paramMap.containsKey("LIST")) {
        Chat.radioSpawn.list();
      }
      if (paramMap.containsKey("INFO")) {
        Chat.radioSpawn.printInfo();
      }
      if (paramMap.containsKey("TEST")) {
        str = Cmd.arg(paramMap, "TEST", 0);
        if (str == null) {
          System.out.println("TEST MODE: " + RadioChannel.tstMode + " loop: " + RadioChannel.tstLoop);
        }
        else if (str.equals("brief")) { RadioChannel.tstMode = 1;
        }
        else if (str.equals("block")) { RadioChannel.tstMode = 2;
        }
        else if (str.equals("mixer")) { RadioChannel.tstMode = 3;
        }
        else if (str.equals("loop")) {
          RadioChannel.tstLoop = true;
          System.out.println("WARNING: Radio chatter started in TEST MODE");
        }
        else if (str.equals("norm")) {
          RadioChannel.tstLoop = false;
          RadioChannel.tstMode = 0;
        } else {
          System.out.println("ERROR: invalid command" + str);
        }
      }
      if (paramMap.containsKey("STAT")) {
        AudioDevice.inputStat();
      }
    }

    return CmdEnv.RETURN_OK;
  }
}