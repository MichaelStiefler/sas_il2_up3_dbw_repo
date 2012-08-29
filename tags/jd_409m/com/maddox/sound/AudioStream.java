package com.maddox.sound;

public class AudioStream extends BaseObject
{
  protected int handle = 0;
  public static final int SC_GAIN = 1;
  public static final int SC_PITCH = 2;
  public static final int SC_DIRFACTOR = 3;

  protected AudioStream()
  {
  }

  protected AudioStream(int paramInt)
  {
    this.handle = paramInt;
  }

  public boolean isInitialized()
  {
    return this.handle != 0;
  }

  public void play()
  {
    if (this.handle != 0) jniPlay(this.handle, 0.0F, false);
  }

  public void start()
  {
    if ((this.handle != 0) && 
      (!jniIsPlaying(this.handle))) jniPlay(this.handle, 0.0F, false);
  }

  public void setPlay(boolean paramBoolean)
  {
    if (this.handle != 0)
      if (paramBoolean) {
        if (!jniIsPlaying(this.handle)) jniPlay(this.handle, 0.0F, false); 
      }
      else
        jniCancel(this.handle);
  }

  public void play(float paramFloat, boolean paramBoolean)
  {
    if (this.handle != 0) jniPlay(this.handle, paramFloat, paramBoolean);
  }

  public void stop(float paramFloat)
  {
    if (this.handle != 0) jniStop(this.handle, paramFloat);
  }

  public void stop()
  {
    if (this.handle != 0) jniStop(this.handle, 0.0F);
  }

  public void cancel()
  {
    if (this.handle != 0) jniCancel(this.handle);
  }

  public boolean isPlaying()
  {
    if (this.handle == 0) return false;
    return jniIsPlaying(this.handle);
  }

  public float getControl(int paramInt)
  {
    return this.handle != 0 ? jniGetControl(this.handle, paramInt) : 0.0F;
  }

  public void setControl(int paramInt, float paramFloat)
  {
    if (this.handle != 0) jniSetControl(this.handle, paramInt, paramFloat);
  }

  public void setVolume(float paramFloat)
  {
    if (this.handle != 0) jniSetControl(this.handle, 1, paramFloat);
  }

  public void setPitch(float paramFloat)
  {
    if (this.handle != 0) jniSetControl(this.handle, 2, paramFloat);
  }

  public void release()
  {
    if (this.handle != 0) {
      jniRelease(this.handle);
      this.handle = 0;
    }
  }

  protected void finalize() throws Throwable
  {
    if (this.handle != 0) jniRelease(this.handle);
    super.finalize();
  }

  public String getName()
  {
    return jniGetName(this.handle);
  }

  protected static native void jniPlay(int paramInt, float paramFloat, boolean paramBoolean);

  protected static native void jniStop(int paramInt, float paramFloat);

  protected static native void jniCancel(int paramInt);

  protected static native boolean jniIsPlaying(int paramInt);

  protected static native float jniGetControl(int paramInt1, int paramInt2);

  protected static native void jniSetControl(int paramInt1, int paramInt2, float paramFloat);

  protected static native void jniRelease(int paramInt);

  protected static native String jniGetName(int paramInt);
}