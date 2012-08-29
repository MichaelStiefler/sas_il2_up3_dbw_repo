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
    return Name(this.jdField_cppObj_of_type_Int);
  }

  public boolean isPermanent()
  {
    return IsPermanent(this.jdField_cppObj_of_type_Int);
  }

  public boolean isEnabled()
  {
    return true;
  }

  public void load(IniFile paramIniFile, String paramString)
  {
    CfgTools.load(this, paramIniFile, paramString);
    this.jdField_iniFile_of_type_ComMaddoxRtsIniFile = paramIniFile;
    this.jdField_iniSect_of_type_JavaLangString = paramString;
  }

  public void save()
  {
    save(this.jdField_iniFile_of_type_ComMaddoxRtsIniFile, this.jdField_iniSect_of_type_JavaLangString);
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
    SetState(this.jdField_cppObj_of_type_Int, this.state);
    return i;
  }

  public int applyStatus()
  {
    int i = GetState(this.jdField_cppObj_of_type_Int);
    if (this.state == i) return 0;
    if (!isEnabledState(this.state))
      return 0;
    int j = GetStateStatus(this.jdField_cppObj_of_type_Int, this.state) | GetStateStatus(this.jdField_cppObj_of_type_Int, i);
    return j;
  }

  public void reset()
  {
    this.state = GetState(this.jdField_cppObj_of_type_Int);
  }

  public int firstState()
  {
    return FirstState(this.jdField_cppObj_of_type_Int);
  }

  public int countStates()
  {
    return CountStates(this.jdField_cppObj_of_type_Int);
  }

  public int defaultState()
  {
    return DefaultState(this.jdField_cppObj_of_type_Int);
  }

  public String nameState(int paramInt)
  {
    if (paramInt != clamp(paramInt, firstState(), countStates()))
      return "Unknown";
    return NameState(this.jdField_cppObj_of_type_Int, paramInt);
  }

  public boolean isEnabledState(int paramInt)
  {
    if (paramInt != clamp(paramInt, firstState(), countStates()))
      return false;
    return (GetStateStatus(this.jdField_cppObj_of_type_Int, paramInt) & 0x2) != 0;
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