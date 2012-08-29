package com.maddox.sound;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.rts.Destroy;

public class SoundFX extends AudioStream
  implements Destroy
{
  public static final int CAPS_POSITION = 1;
  public static final int CAPS_VELOCITY = 2;
  public static final int CAPS_OR_FRONT = 4;
  public static final int CAPS_OR_TOP = 8;
  public static final int CAPS_UPDATE = 16;
  public static final int CAPS_MASK_3D = 7;
  public static final int CAPS_RELOBJ = 256;
  public static final int CAPS_RELIST = 512;
  public static final int CAPS_MUSIC = 4096;
  public static final int FLAG_INFINITE = 1;
  public static final int FLAG_NUMADJ = 4096;
  public static final int FLAG_UNDETUNE = 8192;
  public static final int FLAG_PERMANENT = 16384;
  public static final int FLAG_SEQ = 32768;
  public static final int FLAG_PMAX = 65536;
  protected SoundList list;
  protected SoundFX prev;
  protected SoundFX next;

  public SoundFX(SoundPreset paramSoundPreset)
  {
    firstInit(paramSoundPreset);
  }

  public SoundFX(String paramString)
  {
    firstInit(SoundPreset.get(paramString));
  }

  protected void firstInit(SoundPreset paramSoundPreset)
  {
    this.list = null;
    this.prev = (this.next = null);
    this.handle = paramSoundPreset.createObject();
  }

  public SoundFX next()
  {
    return this.next;
  }

  public void insert(SoundList paramSoundList, boolean paramBoolean)
  {
    if (this.list == null) {
      this.list = paramSoundList;
      this.next = paramSoundList.first;
      if (paramSoundList.first != null) paramSoundList.first.prev = this;
      paramSoundList.first = this;
      if (paramBoolean) jniSetAuto(this.handle);
    }
  }

  public void remove()
  {
    if (this.list != null) {
      if (this.list.first == this) this.list.first = this.next;
      if (this.prev != null) this.prev.next = this.next;
      if (this.next != null) this.next.prev = this.prev;
      this.list = null;
      this.prev = (this.next = null);
    }
  }

  public void setAcoustics(Acoustics paramAcoustics)
  {
    if ((this.handle != 0) && (paramAcoustics != null)) jniSetAcoustics(this.handle, paramAcoustics.handle);
  }

  public void setParent(SoundFX paramSoundFX)
  {
    if (this.handle != 0) jniSetParent(this.handle, paramSoundFX != null ? paramSoundFX.handle : 0);
  }

  public int getCurDelay()
  {
    return this.handle == 0 ? 0 : jniCurDelay(this.handle);
  }

  public void add(AudioStream paramAudioStream)
  {
    if (this.handle != 0) jniAdd(this.handle, paramAudioStream.handle);
  }

  public void remove(AudioStream paramAudioStream)
  {
    if (this.handle != 0) jniRemove(this.handle, paramAudioStream.handle);
  }

  public void clear()
  {
    if (this.handle != 0) jniClear(this.handle);
  }

  public void play(Sample paramSample)
  {
    if ((paramSample != null) && (this.handle != 0)) jniAddSample(this.handle, paramSample.handle, 0, 1.0F, 1.0F);
  }

  public void play(Sample paramSample, int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((paramSample != null) && (this.handle != 0)) jniAddSample(this.handle, paramSample.handle, paramInt, paramFloat1, paramFloat2);
  }

  public void play(SamplePool paramSamplePool)
  {
    if ((paramSamplePool != null) && (this.handle != 0)) jniPlayPool(this.handle, paramSamplePool.handle, 0, 1.0F, 1.0F);
  }

  public void play(SamplePool paramSamplePool, int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((paramSamplePool != null) && (this.handle != 0)) jniPlayPool(this.handle, paramSamplePool.handle, paramInt, paramFloat1, paramFloat2);
  }

  public void play(Point3d paramPoint3d)
  {
    if (this.handle != 0) {
      jniSetPosition(this.handle, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
      jniPlay(this.handle, 0.0F, false);
    }
  }

  public int getCaps()
  {
    return this.handle != 0 ? jniGetCaps(this.handle) : -1;
  }

  public void setPosition(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (this.handle != 0) jniSetPosition(this.handle, paramDouble1, paramDouble2, paramDouble3);
  }

  public void setVelocity(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.handle != 0) jniSetVelocity(this.handle, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setOrientation(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.handle != 0) jniSetOrientation(this.handle, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setTop(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.handle != 0) jniSetTop(this.handle, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setPosition(Point3d paramPoint3d)
  {
    if (this.handle != 0) jniSetPosition(this.handle, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
  }

  public void setPosition(Point3f paramPoint3f)
  {
    if (this.handle != 0) jniSetPosition(this.handle, paramPoint3f.x, paramPoint3f.y, paramPoint3f.z);
  }

  public void setUsrFlag(int paramInt)
  {
    if (this.handle != 0) jniSetUsrFlag(this.handle, paramInt);
  }

  public boolean isDestroyed()
  {
    return this.handle == 0;
  }

  public void destroy()
  {
    if (this.handle != 0) {
      remove();
      jniRelease(this.handle);
      this.handle = 0;
    }
  }

  protected static native int jniGetCaps(int paramInt);

  protected static native void jniSetAuto(int paramInt);

  protected static native void jniSetAcoustics(int paramInt1, int paramInt2);

  protected static native void jniSetParent(int paramInt1, int paramInt2);

  protected static native void jniSetUsrFlag(int paramInt1, int paramInt2);

  protected static native int jniAdd(int paramInt1, int paramInt2);

  protected static native int jniRemove(int paramInt1, int paramInt2);

  protected static native int jniClear(int paramInt);

  protected static native void jniAddSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2);

  protected static native void jniPlayPool(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2);

  protected static native int jniCurDelay(int paramInt);

  protected static native void jniSetPosition(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3);

  protected static native void jniSetVelocity(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void jniSetOrientation(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void jniSetTop(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);
}