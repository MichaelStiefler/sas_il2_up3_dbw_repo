package com.maddox.sound;

import com.maddox.rts.SectFile;

public class ModControl extends SoundControl
{
  public void load(SectFile paramSectFile, String paramString)
  {
    boolean bool = this.handle != 0;
    if (bool) {
      String str = paramSectFile.get(paramString, "file", (String)null);
      float f1 = paramSectFile.get(paramString, "pitch", 1.0F);
      float f2 = paramSectFile.get(paramString, "min", 0.3F);
      float f3 = paramSectFile.get(paramString, "max", 1.0F);
      bool = jniSet(this.handle, str, f1, f2, f3);
      if (!bool) printf("ERROR loading sound control " + paramString);
    }
  }

  protected int getClassId()
  {
    return 2;
  }

  protected static native boolean jniSet(int paramInt, String paramString, float paramFloat1, float paramFloat2, float paramFloat3);
}