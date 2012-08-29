package com.maddox.sound;

import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;

public class SoundFlags
  implements CfgFlags
{
  IniFile ini = null;
  String sect = null;
  int value = 0;

  public String name()
  {
    return null; } 
  public int firstFlag() { return 0; } 
  public int countFlags() { return 0; } 
  public String nameFlag(int paramInt) { return null; } 
  public boolean defaultFlag(int paramInt) { return false; } 
  public boolean isPermanentFlag(int paramInt) { return true; }

  public boolean isEnabledFlag(int paramInt) {
    if (Config.cur != null) return Config.cur.isSoundUse();
    return true;
  }
  public boolean get(int paramInt) {
    return (this.value & 1 << paramInt) != 0;
  }
  public void set(int paramInt, boolean paramBoolean) {
    paramInt = 1 << paramInt;
    if (paramBoolean) this.value |= paramInt; else
      this.value &= (paramInt ^ 0xFFFFFFFF); 
  }
  public int applyStatus(int paramInt) {
    return 0; } 
  public boolean isPermanent() { return true; }

  public boolean isEnabled() {
    if (Config.cur != null) return Config.cur.isSoundUse();
    return true;
  }
  public void load(IniFile paramIniFile, String paramString) {
    this.ini = paramIniFile;
    this.sect = paramString;
    CfgTools.load(this, paramIniFile, paramString);
  }
  public IniFile loadedSectFile() {
    return this.ini;
  }
  public String loadedSectName() {
    return this.sect;
  }
  public void save() {
    save(this.ini, this.sect);
  }
  public void save(IniFile paramIniFile, String paramString) {
    CfgTools.save(false, this, paramIniFile, paramString);
  }
  public int apply() {
    return 0;
  }
  public int apply(int paramInt) {
    return 0;
  }
  public int applyStatus() {
    return 0;
  }

  public void applyExtends(int paramInt)
  {
  }

  public void reset()
  {
  }
}