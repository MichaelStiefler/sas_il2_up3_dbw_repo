package com.maddox.il2.engine;

import com.maddox.rts.Cfg;
import com.maddox.rts.CfgTools;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.IniFile;

public class CfgGObj extends GObj
{
  public static final int ENABLED = 2;
  protected IniFile iniFile;
  protected String iniSect;

  public IniFile loadedSectFile()
  {
    return this.iniFile;
  }

  public String loadedSectName()
  {
    return this.iniSect;
  }

  public void applyExtends(int paramInt)
  {
    ApplyExtends(paramInt);
  }

  public static void ApplyExtends(int paramInt) {
    if ((paramInt & 0x4) != 0) {
      CmdEnv.top().exec("timeout 0 fobj *.tga RELOAD");
      try {
        Engine.land().ReLoadMap();
      }
      catch (Exception localException)
      {
      }
    }
  }

  protected static Cfg findByName(String paramString) {
    Cfg localCfg = FindByName(paramString);
    if (localCfg == null)
      throw new GObjException("CfgGObj " + paramString + " not found");
    CfgTools.register(localCfg);
    return localCfg;
  }
  private static native Cfg FindByName(String paramString);

  protected int clamp(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 < paramInt2) paramInt1 = paramInt2;
    if (paramInt1 >= paramInt2 + paramInt3) paramInt1 = paramInt2 + paramInt3 - 1;
    return paramInt1;
  }

  public CfgGObj(int paramInt) {
    super(paramInt);
  }
}