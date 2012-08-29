package com.maddox.sound;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CmdSound extends Cmd
{
  public static final String LIST = "list";
  public static final String SELECT = "select";
  public static final String SET = "set";
  public static final String SAVE = "save";
  public SoundPreset prs = null;

  public CmdSound()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("list", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("set", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("save", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("select", null);
    this._properties.put("NAME", "acc");
    this._levelAccess = 0;
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    if (!paramMap.isEmpty())
    {
      if (paramMap.containsKey("_$$")) {
        System.out.println("Unknown command :" + Cmd.arg(paramMap, "_$$", 0));
      }
      else
      {
        Object localObject;
        if (paramMap.containsKey("list")) {
          localObject = SoundPreset.map.nextEntry(null);
          while (localObject != null) {
            SoundPreset localSoundPreset = (SoundPreset)((Map.Entry)localObject).getValue();
            System.out.println("preset : " + localSoundPreset.name);
            localObject = SoundPreset.map.nextEntry((Map.Entry)localObject);
          }
        }
        if (paramMap.containsKey("select")) {
          localObject = Cmd.arg(paramMap, "select", 0);
          this.prs = ((SoundPreset)SoundPreset.map.get(localObject));
        }
        if (paramMap.containsKey("set")) {
          if (this.prs == null)
            System.out.println("sound preset not selected");
          else {
            this.prs.set(Cmd.arg(paramMap, "set", 0), Cmd.arg(paramMap, "set", 1));
          }
        }
      }
    }

    return CmdEnv.RETURN_OK;
  }
}