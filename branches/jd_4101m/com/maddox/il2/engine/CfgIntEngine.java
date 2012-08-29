package com.maddox.il2.engine;

import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

class CfgIntEngine extends CfgGObj
  implements CfgInt
{
  private int state;

  public String name()
  {
    return Name(this.cppObj);
  }

  public boolean isPermanent()
  {
    return IsPermanent(this.cppObj);
  }

  public boolean isEnabled()
  {
    return true;
  }

  public void load(IniFile paramIniFile, String paramString)
  {
    CfgTools.load(this, paramIniFile, paramString);
    this.iniFile = paramIniFile;
    this.iniSect = paramString;
  }

  public void save()
  {
    save(this.iniFile, this.iniSect);
  }

  public void save(IniFile paramIniFile, String paramString)
  {
    CfgTools.save(true, this, paramIniFile, paramString);
  }

  public int apply()
  {
    if (!isEnabledState(this.state))
      return 0;
    int i = applyStatus();
    SetState(this.cppObj, this.state);
    return i;
  }

  public int applyStatus()
  {
    int i = GetState(this.cppObj);
    if (this.state == i) return 0;
    if (!isEnabledState(this.state))
      return 0;
    int j = GetStateStatus(this.cppObj, this.state) | GetStateStatus(this.cppObj, i);
    return j;
  }

  public void reset()
  {
    this.state = GetState(this.cppObj);
  }

  public int firstState()
  {
    return FirstState(this.cppObj);
  }

  public int countStates()
  {
    return CountStates(this.cppObj);
  }

  public int defaultState()
  {
    return DefaultState(this.cppObj);
  }

  public String nameState(int paramInt)
  {
    if (paramInt != clamp(paramInt, firstState(), countStates()))
      return "Unknown";
    return NameState(this.cppObj, paramInt);
  }

  public boolean isEnabledState(int paramInt)
  {
    if (paramInt != clamp(paramInt, firstState(), countStates()))
      return false;
    return (GetStateStatus(this.cppObj, paramInt) & 0x2) != 0;
  }

  public int get()
  {
    return this.state;
  }

  public void set(int paramInt)
  {
    this.state = clamp(paramInt, firstState(), countStates());
  }

  public CfgIntEngine(int paramInt) {
    super(paramInt);
    this.state = defaultState();
  }

  private static native String Name(int paramInt);

  private static native boolean IsPermanent(int paramInt);

  private static native int FirstState(int paramInt);

  private static native int CountStates(int paramInt);

  private static native int DefaultState(int paramInt);

  private static native String NameState(int paramInt1, int paramInt2);

  private static native int GetStateStatus(int paramInt1, int paramInt2);

  private static native int GetState(int paramInt);

  private static native int SetState(int paramInt1, int paramInt2);
}