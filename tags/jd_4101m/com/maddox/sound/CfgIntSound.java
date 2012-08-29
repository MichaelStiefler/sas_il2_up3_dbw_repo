package com.maddox.sound;

import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;

public class CfgIntSound
  implements CfgInt
{
  int code;
  int value;
  String name;
  int defState;
  String[] stateNames;
  boolean needApply;
  IniFile ini;
  String sectName;
  public static String[] stdStateNames = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" };

  public CfgIntSound(int paramInt1, String paramString, String[] paramArrayOfString, int paramInt2)
  {
    if (paramArrayOfString == null) paramArrayOfString = stdStateNames;
    this.code = paramInt1;
    this.name = paramString;
    this.stateNames = paramArrayOfString;
    this.defState = paramInt2;
    this.value = AudioDevice.getControl(paramInt1);
    this.needApply = false;
    this.ini = null;
    this.sectName = null;
  }

  public int firstState()
  {
    return 0;
  }

  public int countStates()
  {
    return this.stateNames.length;
  }

  public int defaultState()
  {
    return this.defState;
  }

  public String nameState(int paramInt)
  {
    return this.stateNames[paramInt];
  }

  public boolean isEnabledState(int paramInt)
  {
    return true;
  }

  public int get()
  {
    return this.value;
  }

  public void set(int paramInt)
  {
    this.value = paramInt;
    if (this.code == 1) AudioDevice._cur_en = paramInt > 0;
    this.needApply = (!AudioDevice.setControl(this.code, this.value));
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
    return ((AudioDevice._cur_en) || (this.code == 1)) && (AudioDevice.isControlEnabled(this.code));
  }

  public void load(IniFile paramIniFile, String paramString)
  {
    this.ini = paramIniFile;
    this.sectName = paramString;
    this.value = paramIniFile.get(paramString, this.name, this.defState);
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
    paramIniFile.set(paramString, this.name, this.value);
  }

  public int apply()
  {
    AudioDevice.setControl(this.code, this.value);
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