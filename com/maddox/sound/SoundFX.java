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
    this.jdField_handle_of_type_Int = paramSoundPreset.createObject();
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
      if (paramBoolean) jniSetAuto(this.jdField_handle_of_type_Int);
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
    if ((this.jdField_handle_of_type_Int != 0) && (paramAcoustics != null)) jniSetAcoustics(this.jdField_handle_of_type_Int, paramAcoustics.jdField_handle_of_type_Int);
  }

  public void setParent(SoundFX paramSoundFX)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetParent(this.jdField_handle_of_type_Int, paramSoundFX != null ? paramSoundFX.jdField_handle_of_type_Int : 0);
  }

  public int getCurDelay()
  {
    return this.jdField_handle_of_type_Int == 0 ? 0 : jniCurDelay(this.jdField_handle_of_type_Int);
  }

  public void add(AudioStream paramAudioStream)
  {
    if (this.jdField_handle_of_type_Int != 0) jniAdd(this.jdField_handle_of_type_Int, paramAudioStream.jdField_handle_of_type_Int);
  }

  public void remove(AudioStream paramAudioStream)
  {
    if (this.jdField_handle_of_type_Int != 0) jniRemove(this.jdField_handle_of_type_Int, paramAudioStream.jdField_handle_of_type_Int);
  }

  public void clear()
  {
    if (this.jdField_handle_of_type_Int != 0) jniClear(this.jdField_handle_of_type_Int);
  }

  public void play(Sample paramSample)
  {
    if ((paramSample != null) && (this.jdField_handle_of_type_Int != 0)) jniAddSample(this.jdField_handle_of_type_Int, paramSample.jdField_handle_of_type_Int, 0, 1.0F, 1.0F);
  }

  public void play(Sample paramSample, int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((paramSample != null) && (this.jdField_handle_of_type_Int != 0)) jniAddSample(this.jdField_handle_of_type_Int, paramSample.jdField_handle_of_type_Int, paramInt, paramFloat1, paramFloat2);
  }

  public void play(SamplePool paramSamplePool)
  {
    if ((paramSamplePool != null) && (this.jdField_handle_of_type_Int != 0)) jniPlayPool(this.jdField_handle_of_type_Int, paramSamplePool.jdField_handle_of_type_Int, 0, 1.0F, 1.0F);
  }

  public void play(SamplePool paramSamplePool, int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((paramSamplePool != null) && (this.jdField_handle_of_type_Int != 0)) jniPlayPool(this.jdField_handle_of_type_Int, paramSamplePool.jdField_handle_of_type_Int, paramInt, paramFloat1, paramFloat2);
  }

  public void play(Point3d paramPoint3d)
  {
    if (this.jdField_handle_of_type_Int != 0) {
      jniSetPosition(this.jdField_handle_of_type_Int, paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double, paramPoint3d.jdField_z_of_type_Double);
      AudioStream.jniPlay(this.jdField_handle_of_type_Int, 0.0F, false);
    }
  }

  public int getCaps()
  {
    return this.jdField_handle_of_type_Int != 0 ? jniGetCaps(this.jdField_handle_of_type_Int) : -1;
  }

  public void setPosition(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetPosition(this.jdField_handle_of_type_Int, paramDouble1, paramDouble2, paramDouble3);
  }

  public void setVelocity(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetVelocity(this.jdField_handle_of_type_Int, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setOrientation(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetOrientation(this.jdField_handle_of_type_Int, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setTop(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetTop(this.jdField_handle_of_type_Int, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setPosition(Point3d paramPoint3d)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetPosition(this.jdField_handle_of_type_Int, paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double, paramPoint3d.jdField_z_of_type_Double);
  }

  public void setPosition(Point3f paramPoint3f)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetPosition(this.jdField_handle_of_type_Int, paramPoint3f.x, paramPoint3f.y, paramPoint3f.z);
  }

  public void setUsrFlag(int paramInt)
  {
    if (this.jdField_handle_of_type_Int != 0) jniSetUsrFlag(this.jdField_handle_of_type_Int, paramInt);
  }

  public boolean isDestroyed()
  {
    return this.jdField_handle_of_type_Int == 0;
  }

  public void destroy()
  {
    if (this.jdField_handle_of_type_Int != 0) {
      remove();
      AudioStream.jniRelease(this.jdField_handle_of_type_Int);
      this.jdField_handle_of_type_Int = 0;
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