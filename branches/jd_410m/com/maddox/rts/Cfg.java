package com.maddox.rts;

public abstract interface Cfg
{
  public static final int RELOAD_MATERIALS = 4;
  public static final int RELOAD_MESHES = 8;
  public static final int RELOAD_OPENGL = 16;

  public abstract String name();

  public abstract boolean isPermanent();

  public abstract boolean isEnabled();

  public abstract void load(IniFile paramIniFile, String paramString);

  public abstract IniFile loadedSectFile();

  public abstract String loadedSectName();

  public abstract void save();

  public abstract void save(IniFile paramIniFile, String paramString);

  public abstract int apply();

  public abstract int applyStatus();

  public abstract void applyExtends(int paramInt);

  public abstract void reset();
}