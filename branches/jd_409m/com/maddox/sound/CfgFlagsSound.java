package com.maddox.sound;

import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

public class CfgFlagsSound
  implements CfgFlags
{
  String name;
  CfgFlagsInfo[] flags;
  boolean[] applyState;
  String sectName = null;
  IniFile ini = null;

  public CfgFlagsSound(String paramString, CfgFlagsInfo[] paramArrayOfCfgFlagsInfo)
  {
    this.name = paramString;
    this.flags = paramArrayOfCfgFlagsInfo;
    this.applyState = new boolean[paramArrayOfCfgFlagsInfo.length];
    for (int i = 0; i < paramArrayOfCfgFlagsInfo.length; i++) {
      this.applyState[i] = true;
      paramArrayOfCfgFlagsInfo[i].value = (AudioDevice.getControl(paramArrayOfCfgFlagsInfo[i].code) != 0);
    }
  }

  public int firstFlag()
  {
    return 0;
  }

  public int countFlags()
  {
    return this.flags.length;
  }

  public boolean defaultFlag(int paramInt)
  {
    return this.flags[paramInt].isDefault;
  }

  public String nameFlag(int paramInt)
  {
    return this.flags[paramInt].name;
  }

  public boolean isPermanentFlag(int paramInt)
  {
    return true;
  }

  public boolean isEnabledFlag(int paramInt)
  {
    return (AudioDevice.isControlEnabled(this.flags[paramInt].code)) && (AudioDevice._cur_en);
  }

  public boolean get(int paramInt)
  {
    return this.flags[paramInt].value;
  }

  public void set(int paramInt, boolean paramBoolean)
  {
    this.flags[paramInt].value = paramBoolean;
  }

  public int apply(int paramInt)
  {
    this.applyState[paramInt] = AudioDevice.setControl(this.flags[paramInt].code, this.flags[paramInt].value ? 1 : 0);
    return 0;
  }

  public boolean needApply()
  {
    int i = 0;
    for (int j = 0; j < this.flags.length; j++) {
      if (this.applyState[j] == 0) {
        i = 1;
        break;
      }
    }
    return i;
  }

  public int applyStatus(int paramInt)
  {
    return 0;
  }

  public String name()
  {
    return this.name;
  }

  public boolean isPermanent()
  {
    return true;
  }

  public boolean isEnabled()
  {
    return true;
  }

  public void load(IniFile paramIniFile, String paramString)
  {
    this.ini = paramIniFile;
    this.sectName = paramString;
    CfgTools.load(this, paramIniFile, paramString);
  }

  public IniFile loadedSectFile()
  {
    return this.ini;
  }

  public String loadedSectName()
  {
    return this.sectName;
  }

  public void save()
  {
    save(this.ini, this.sectName);
  }

  public void save(IniFile paramIniFile, String paramString)
  {
    this.ini = paramIniFile;
    this.sectName = paramString;
    CfgTools.save(false, this, paramIniFile, paramString);
  }

  public int apply()
  {
    for (int i = 0; i < this.flags.length; i++) {
      apply(i);
    }
    return 0;
  }

  public int applyStatus()
  {
    return 0;
  }

  public void applyExtends(int paramInt)
  {
  }

  public void reset()
  {
  }
}