package com.maddox.sound;

import com.maddox.rts.SectFile;

public class Preset extends BaseObject
{
  protected String name;
  protected int handle = 0;
  protected int flags = 0;

  protected Preset(String paramString)
  {
    this.name = paramString;
  }

  public void load(SectFile paramSectFile, String paramString) throws Exception
  {
  }

  public void save(SectFile paramSectFile, String paramString) throws Exception
  {
  }

  protected void finalize() throws Throwable
  {
    if (this.handle != 0) jniDestroy(this.handle);
    super.finalize();
  }

  protected int createObject()
  {
    if (!BaseObject.enabled) return 0;
    return jniCreateObject(this.handle, this.flags);
  }

  protected int getObject()
  {
    if (!BaseObject.enabled) return 0;
    int i = jniGetFreeObject(this.handle, this.flags);
    if (i == 0) i = createObject();
    return i;
  }

  protected static native int jniGet(String paramString);

  protected static native void jniDestroy(int paramInt);

  protected static native int jniCreateObject(int paramInt1, int paramInt2);

  protected static native int jniGetFreeObject(int paramInt1, int paramInt2);
}