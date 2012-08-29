package com.maddox.sound;

import com.maddox.rts.SectFile;

public class BoundsControl extends SoundControl
{
  public void load(SectFile paramSectFile, String paramString)
  {
    boolean bool = this.jdField_handle_of_type_Int != 0;
    if (bool) {
      float f1 = paramSectFile.get(paramString, "minlo", 0.0F);
      float f2 = paramSectFile.get(paramString, "minhi", 0.0F);
      float f3 = paramSectFile.get(paramString, "maxlo", 0.0F);
      float f4 = paramSectFile.get(paramString, "maxhi", 0.0F);
      float f5 = paramSectFile.get(paramString, "value", 0.0F);
      float f6 = paramSectFile.get(paramString, "pmin", 0.0F);
      float f7 = paramSectFile.get(paramString, "pmax", 0.0F);
      bool = jniSet(this.jdField_handle_of_type_Int, f1, f2, f3, f4, f5, f6, f7);
      if (!bool) BaseObject.printf("ERROR loading sound control " + paramString);
    }
  }

  protected int getClassId()
  {
    return 1;
  }

  protected static native boolean jniSet(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);
}