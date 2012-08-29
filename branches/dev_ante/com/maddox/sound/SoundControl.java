package com.maddox.sound;

import com.maddox.rts.SectFile;

public class SoundControl extends BaseObject
{
  public static final int BOUNDS_CONTROL = 1;
  public static final int MOD_CONTROL = 2;
  public static final int ENV_CONTROL = 3;
  protected int handle = 0;

  protected int getClassId()
  {
    return 0;
  }

  protected void init(int paramInt1, int paramInt2)
  {
    this.handle = jniCreate(paramInt1, paramInt2, getClassId());
  }

  public void load(SectFile paramSectFile, String paramString)
  {
  }

  protected static native int jniCreate(int paramInt1, int paramInt2, int paramInt3);
}