package com.maddox.sound;

import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;

public class AcousticsPreset extends BaseObject
{
  protected String name;
  protected SectFile ini = null;
  protected ArrayList list = new ArrayList();
  protected static HashMapExt map = new HashMapExt();

  public AcousticsPreset(String paramString)
  {
    this.name = paramString;
    if (BaseObject.enabled) this.ini = new SectFile("presets/acoustics/" + paramString + ".prs", 1);
    map.put(paramString, this);
  }

  public static AcousticsPreset get(String paramString)
  {
    AcousticsPreset localAcousticsPreset = (AcousticsPreset)map.get(paramString);
    if (localAcousticsPreset == null) localAcousticsPreset = new AcousticsPreset(paramString);
    return localAcousticsPreset;
  }
}