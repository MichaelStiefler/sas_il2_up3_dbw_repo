package com.maddox.sound;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CmdAcoustics extends Cmd
{
  public static final String LIST = "list";
  public static final String SELECT = "select";
  public static final String SET = "set";
  public static final String SAVE = "save";
  protected Reverb rev = null;

  public CmdAcoustics()
  {
    this.param.put("list", null);
    this.param.put("set", null);
    this.param.put("save", null);
    this.param.put("select", null);
    this._properties.put("NAME", "acc");
    this._levelAccess = 0;
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    if (!paramMap.isEmpty())
    {
      if (paramMap.containsKey("_$$")) {
        System.out.println("Unknown command :" + arg(paramMap, "_$$", 0));
      }
      else
      {
        Object localObject;
        if (paramMap.containsKey("list")) {
          localObject = AcousticsPreset.map.nextEntry(null);
          while (localObject != null) {
            AcousticsPreset localAcousticsPreset = (AcousticsPreset)((Map.Entry)localObject).getValue();
            System.out.println("preset : " + localAcousticsPreset.name + " , objects : " + localAcousticsPreset.list.size());
            localObject = AcousticsPreset.map.nextEntry((Map.Entry)localObject);
          }
        }
        if (paramMap.containsKey("select")) {
          localObject = arg(paramMap, "select", 0);
          this.rev = null;
          if (SoundListener.acc.reverbs != null) {
            if (((String)localObject).compareToIgnoreCase("eax1") == 0) { this.rev = SoundListener.acc.reverbs[0];
            }
            else if (((String)localObject).compareToIgnoreCase("eax2") == 0) { this.rev = SoundListener.acc.reverbs[1];
            } else {
              System.out.println("Invalid reverb name : " + (String)localObject);
              return CmdEnv.RETURN_OK;
            }
          }
          if (SoundListener.acc.reverbs == null) {
            System.out.println("Listener reverb " + (String)localObject + " is null");
          }
        }
        if (paramMap.containsKey("set")) {
          this.rev.set(arg(paramMap, "set", 0), arg(paramMap, "set", 1));
        }
        if (paramMap.containsKey("save"))
          if (SoundListener.acc == null) System.out.println("Listener acoustics is null"); else
            SoundListener.acc.save();
      }
    }
    return CmdEnv.RETURN_OK;
  }
}