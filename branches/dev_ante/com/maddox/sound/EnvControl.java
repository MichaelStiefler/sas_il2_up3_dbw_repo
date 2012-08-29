package com.maddox.sound;

import com.maddox.rts.SectFile;

public class EnvControl extends SoundControl
{
  public void load(SectFile paramSectFile, String paramString)
  {
    boolean bool = this.jdField_handle_of_type_Int != 0;
    if (bool) {
      float f1 = paramSectFile.get(paramString, "gain0", 1.0F);
      float f2 = paramSectFile.get(paramString, "gain1", 1.0F);
      bool = jniSet(this.jdField_handle_of_type_Int, f1, f2);
      if (!bool) BaseObject.printf("ERROR loading sound control " + paramString);
    }
  }

  protected int getClassId()
  {
    return 3;
  }

  protected static native boolean jniSet(int paramInt, float paramFloat1, float paramFloat2);
}