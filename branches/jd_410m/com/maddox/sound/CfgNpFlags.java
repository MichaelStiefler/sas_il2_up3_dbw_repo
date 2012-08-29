package com.maddox.sound;

import com.maddox.rts.IniFile;

public class CfgNpFlags extends SoundFlags
{
  protected static final String emphName = "Preemphasis";
  protected static final String latnName = "RadioLatency";
  protected static final String agcName = "AGC";

  public int countFlags()
  {
    return 3;
  }

  public String nameFlag(int paramInt)
  {
    switch (paramInt) { case 0:
      return "Enabled";
    case 1:
      return "PTTMode";
    case 2:
      return "PlayClicks";
    }
    return null;
  }

  public String name()
  {
    return "RadioFlags";
  }

  public void load(IniFile paramIniFile, String paramString)
  {
    super.load(paramIniFile, paramString);

    int i = paramIniFile.get(paramString, "Preemphasis", 85);
    AudioDevice.setControl(506, i);
    i = paramIniFile.get(paramString, "RadioLatency", 50);
    AudioDevice.setControl(507, i);
    AudioDevice.setAGC(paramIniFile.get(paramString, "AGC", true));
  }

  public void save(IniFile paramIniFile, String paramString)
  {
    super.save(paramIniFile, paramString);
    paramIniFile.set(paramString, "Preemphasis", AudioDevice.getControl(506));
    paramIniFile.set(paramString, "RadioLatency", AudioDevice.getControl(507));
    paramIniFile.set(paramString, "AGC", AudioDevice.getAGC());
  }

  public int apply(int paramInt)
  {
    switch (paramInt) {
    case 1:
      AudioDevice.setPTTMode((this.value & 0x2) != 0);
      break;
    case 2:
      AudioDevice.setPhoneFX((this.value & 0x4) != 0);
    }

    return 0;
  }

  public int apply()
  {
    for (int i = 0; i < countFlags(); i++) apply(i);
    return 0;
  }
}