package com.maddox.il2.engine;

import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

class CfgFlagsEngine extends CfgGObj
  implements CfgFlags
{
  private int flags;

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
    if (!isEnabled())
      return 0;
    int i = FirstFlag(this.cppObj);
    int j = CountFlags(this.cppObj);
    int k = 0;
    for (int m = 0; m < j; m++) {
      if (IsEnabledFlag(this.cppObj, i)) {
        boolean bool1 = Get(this.cppObj, i);
        boolean bool2 = (this.flags & 1 << m) != 0;
        if (bool2 != bool1) {
          k |= GetFlagStatus(this.cppObj, i);
          Set(this.cppObj, i, bool2);
        }
      }
      i++;
    }
    return k;
  }

  public int applyStatus()
  {
    int i = FirstFlag(this.cppObj);
    int j = CountFlags(this.cppObj);
    int k = 0;
    for (int m = 0; m < j; m++) {
      if (IsEnabledFlag(this.cppObj, i)) {
        boolean bool1 = Get(this.cppObj, i);
        boolean bool2 = (this.flags & 1 << m) != 0;
        if (bool2 != bool1)
          k |= GetFlagStatus(this.cppObj, i);
      }
      i++;
    }
    return k;
  }

  public void reset()
  {
    int i = FirstFlag(this.cppObj);
    int j = CountFlags(this.cppObj);
    this.flags = 0;
    for (int k = 0; k < j; k++) {
      if (IsEnabledFlag(this.cppObj, i)) {
        if (Get(this.cppObj, i))
          this.flags |= 1 << k;
      }
      else if (GetDefaultFlag(this.cppObj, i)) {
        this.flags |= 1 << k;
      }
      i++;
    }
  }

  public int firstFlag()
  {
    return FirstFlag(this.cppObj);
  }

  public int countFlags()
  {
    return CountFlags(this.cppObj);
  }

  public boolean defaultFlag(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if (i != paramInt)
      return false;
    return GetDefaultFlag(this.cppObj, i);
  }

  public String nameFlag(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if (i != paramInt)
      return "Unknown";
    return NameFlag(this.cppObj, i);
  }

  public boolean isPermanentFlag(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if (i != paramInt)
      return false;
    return IsPermanentFlag(this.cppObj, i);
  }

  public boolean isEnabledFlag(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if (i != paramInt)
      return false;
    return IsEnabledFlag(this.cppObj, i);
  }

  public boolean get(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if (i != paramInt)
      return false;
    return Get(this.cppObj, i);
  }

  public void set(int paramInt, boolean paramBoolean)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if ((i == paramInt) && (IsEnabledFlag(this.cppObj, i)))
      if (paramBoolean) this.flags |= 1 << i - firstFlag(); else
        this.flags &= (1 << i - firstFlag() ^ 0xFFFFFFFF);
  }

  public int apply(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if ((i != paramInt) || (!IsEnabledFlag(this.cppObj, i)))
      return 0;
    boolean bool1 = Get(this.cppObj, i);
    boolean bool2 = (this.flags & 1 << i - firstFlag()) != 0;
    if (bool2 == bool1) return 0;
    int j = GetFlagStatus(this.cppObj, i);
    Set(this.cppObj, i, bool2);
    return j;
  }

  public int applyStatus(int paramInt)
  {
    int i = clamp(paramInt, firstFlag(), countFlags());
    if ((i != paramInt) || (!IsEnabledFlag(this.cppObj, i)))
      return 0;
    boolean bool1 = Get(this.cppObj, i);
    boolean bool2 = (this.flags & 1 << i - firstFlag()) != 0;
    if (bool2 == bool1) return 0;
    int j = GetFlagStatus(this.cppObj, i);
    return j;
  }

  public CfgFlagsEngine(int paramInt) {
    super(paramInt);
    reset();
  }

  private static native String Name(int paramInt);

  private static native boolean IsPermanent(int paramInt);

  private static native int FirstFlag(int paramInt);

  private static native int CountFlags(int paramInt);

  private static native boolean GetDefaultFlag(int paramInt1, int paramInt2);

  private static native String NameFlag(int paramInt1, int paramInt2);

  private static native boolean IsPermanentFlag(int paramInt1, int paramInt2);

  private static native boolean IsEnabledFlag(int paramInt1, int paramInt2);

  private static native int GetFlagStatus(int paramInt1, int paramInt2);

  private static native boolean Get(int paramInt1, int paramInt2);

  private static native int Set(int paramInt1, int paramInt2, boolean paramBoolean);
}