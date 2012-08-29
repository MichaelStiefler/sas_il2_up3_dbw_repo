package com.maddox.rts;

public class CfgIntValue
  implements CfgInt
{
  private String name;
  private int[] state;
  private int first;
  private int count;
  private int def;
  protected IniFile iniFile;
  protected String iniSect;

  public int firstState()
  {
    return this.first; } 
  public int countStates() { return this.count; } 
  public int defaultState() { return this.def; }

  public void set(int paramInt) {
    this.state[0] = clamp(paramInt, firstState(), countStates());
  }
  public int get() { return this.state[0]; } 
  public int apply() {
    return 0; } 
  public void reset() {  }

  public int applyStatus() { return 0; } 
  public void applyExtends(int paramInt) {
  }

  public boolean isEnabledState(int paramInt) {
    return paramInt == clamp(paramInt, firstState(), countStates());
  }

  public String nameState(int paramInt) {
    return "Unknown"; } 
  public String name() { return this.name; } 
  public boolean isPermanent() { return true; } 
  public boolean isEnabled() { return true; }

  public void load(IniFile paramIniFile, String paramString) {
    CfgTools.load(this, paramIniFile, paramString);
    this.iniFile = paramIniFile;
    this.iniSect = paramString;
  }
  public void save() {
    save(this.iniFile, this.iniSect);
  }
  public void save(IniFile paramIniFile, String paramString) {
    CfgTools.save(true, this, paramIniFile, paramString);
  }
  public IniFile loadedSectFile() {
    return this.iniFile;
  }
  public String loadedSectName() {
    return this.iniSect;
  }

  protected int clamp(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 < paramInt2) paramInt1 = paramInt2;
    if (paramInt1 >= paramInt2 + paramInt3) paramInt1 = paramInt2 + paramInt3 - 1;
    return paramInt1;
  }
  public CfgIntValue(String paramString, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3) {
    this.name = paramString;
    this.state = paramArrayOfInt;
    this.first = paramInt1;
    this.count = paramInt2;
    int tmp33_31 = paramInt3; this.def = tmp33_31; this.state[0] = tmp33_31;

    CfgTools.register(this);
  }
}