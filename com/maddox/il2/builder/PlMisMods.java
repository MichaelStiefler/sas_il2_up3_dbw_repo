package com.maddox.il2.builder;

import com.maddox.rts.Property;
import com.maddox.rts.SectFile;

public class PlMisMods extends Plugin
{
  String[] sMods;
  int iModsLines = 0;
  String[] sWeather;
  int iWeatherLines = 0;

  public boolean save(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionAdd("Weather");

    for (int j = 0; j < this.iWeatherLines; j++)
    {
      paramSectFile.lineAdd(i, this.sWeather[j]);
    }

    i = paramSectFile.sectionAdd("Mods");
    for (j = 0; j < this.iModsLines; j++)
    {
      paramSectFile.lineAdd(i, this.sMods[j]);
    }

    return true;
  }

  public void load(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("Weather");
    int j;
    int k;
    if (i >= 0)
    {
      j = paramSectFile.vars(i);
      this.sWeather = new String[j];
      this.iWeatherLines = j;
      for (k = 0; k < j; k++)
      {
        this.sWeather[k] = paramSectFile.line(i, k);
      }
    }

    i = paramSectFile.sectionIndex("Mods");
    if (i >= 0)
    {
      j = paramSectFile.vars(i);
      this.sMods = new String[j];
      this.iModsLines = j;
      for (k = 0; k < j; k++)
      {
        this.sMods[k] = paramSectFile.line(i, k);
      }
    }
  }

  public void deleteAll()
  {
    this.sMods = null;
    this.iModsLines = 0;

    this.sWeather = null;
    this.iWeatherLines = 0;
  }

  static
  {
    Property.set(PlMisMods.class, "name", "MisMods");
  }
}