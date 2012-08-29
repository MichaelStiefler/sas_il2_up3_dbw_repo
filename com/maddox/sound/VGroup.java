package com.maddox.sound;

import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;
import java.util.HashMap;

public class VGroup
  implements CfgInt
{
  int handle = 0;
  int value = 7;
  String name;
  IniFile ini = null;
  String sectName = null;

  static HashMap map = new HashMap();

  public VGroup(String paramString, int paramInt)
  {
    this.name = paramString;
    this.value = defaultState();
    this.handle = jniCreate(this.value, countStates(), paramInt);
    map.put(paramString, this);
  }

  public int firstState()
  {
    return 0;
  }

  public int countStates()
  {
    return CfgIntSound.stdStateNames.length;
  }

  public int defaultState()
  {
    return 7;
  }

  public String nameState(int paramInt)
  {
    return CfgIntSound.stdStateNames[paramInt];
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
    jniSet(this.handle, this.value);
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
    this.value = paramIniFile.get(paramString, this.name, defaultState());
    jniSet(this.handle, this.value);
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

  protected void finalize()
  {
    jniDestroy(this.handle);
    this.handle = 0;
  }

  public static VGroup get(String paramString)
  {
    return (VGroup)map.get(paramString);
  }

  protected static native int jniCreate(int paramInt1, int paramInt2, int paramInt3);

  protected static native void jniSet(int paramInt1, int paramInt2);

  protected static native void jniDestroy(int paramInt);
}