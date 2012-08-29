package com.maddox.gwindow;

import com.maddox.util.StrMath;
import java.io.File;

public class GFileFilterName
  implements GFileFilter
{
  public String description;
  public String[] patterns;

  public boolean accept(File paramFile)
  {
    String str1 = paramFile.getName().toLowerCase();
    for (int i = 0; i < this.patterns.length; i++) {
      String str2 = this.patterns[i];
      if (StrMath.simple(str2, str1))
        return true;
    }
    return false;
  }

  public String getDescription() {
    return this.description;
  }

  public GFileFilterName(String paramString, String[] paramArrayOfString) {
    this.description = paramString;
    this.patterns = paramArrayOfString;
    for (int i = 0; i < this.patterns.length; i++)
      this.patterns[i] = this.patterns[i].toLowerCase();
  }
}