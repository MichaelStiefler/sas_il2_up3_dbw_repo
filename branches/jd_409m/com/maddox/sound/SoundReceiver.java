package com.maddox.sound;

import java.io.PrintStream;

public class SoundReceiver extends BaseObject
{
  protected int handle = 0;

  protected static native int jniInit(int paramInt);

  protected static native void jniDone(int paramInt);

  protected static native void jniPut(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  protected static native void jniReset(int paramInt);

  public SoundReceiver(int paramInt) { if (BaseObject.enabled) this.handle = jniInit(paramInt);
  }

  public void destroy()
  {
    if (this.handle != 0) {
      jniDone(this.handle);
      this.handle = 0;
      if (RadioChannel.tstMode > 0) System.out.println("Radio : receiver destroyed.");
    }
  }

  protected void finalize()
  {
    destroy();
  }

  public void put(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.handle != 0) jniPut(this.handle, paramArrayOfByte, paramInt);
  }

  public void reset()
  {
    if (this.handle != 0) jniReset(this.handle); 
  }

  static
  {
    AudioDevice.loadNative();
  }
}