package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.HashMap;

public class Acoustics extends BaseObject
{
  protected AcousticsPreset prs = null;
  protected int handle = 0;
  protected int rcaps = 0;
  protected int envNum = 0;
  protected HashMap objects = null;
  protected Reverb[] reverbs = null;
  protected float eaxMix = -1.0F;
  protected float occlusion = 0.0F;
  protected float occlLF = 0.0F;
  protected float obstruction = 0.0F;
  protected float obstrLF = 0.0F;
  protected float eaxRoom = 0.0F;

  public ReverbFXRoom globFX = null;
  private static final String cmn = "common";

  public Acoustics(String paramString)
  {
    this.prs = AcousticsPreset.get(paramString);
    if (this.prs.ini != null) {
      this.handle = jniCreate();
      load(this.prs.ini);
    }
    this.prs.list.add(this);
    if (SoundListener.acc == null) SoundListener.setAcoustics(this);
  }

  public Acoustics(AcousticsPreset paramAcousticsPreset)
  {
    if (enabled) {
      this.handle = jniCreate();
      load(paramAcousticsPreset.ini);
    }
    if (SoundListener.acc == null) SoundListener.setAcoustics(this);
  }

  public int getEnvNum()
  {
    return this.envNum;
  }

  public void setParent(Acoustics paramAcoustics)
  {
    jniSetParent(this.handle, paramAcoustics.handle);
  }

  public void setPosition(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    jniSetPosition(this.handle, paramDouble1, paramDouble2, paramDouble3);
  }

  public void setVelocity(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    jniSetVelocity(this.handle, paramFloat1, paramFloat2, paramFloat3);
  }

  public void setOrientation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    jniSetOrientation(this.handle, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }

  public float getControl(int paramInt)
  {
    return jniGetControl(this.handle, paramInt);
  }

  public void setControl(int paramInt, float paramFloat)
  {
    jniSetControl(this.handle, paramInt, paramFloat);
  }

  public AcousticsGeometry getObject(String paramString)
  {
    return this.objects == null ? null : (AcousticsGeometry)this.objects.get(paramString);
  }

  public void add(AcousticsGeometry paramAcousticsGeometry)
  {
    if (this.objects == null) this.objects = new HashMap();
    this.objects.put(paramAcousticsGeometry.name, paramAcousticsGeometry);
  }

  public void add(Reverb paramReverb)
  {
    if (this.reverbs == null) {
      this.reverbs = new Reverb[4];
      for (int i = 0; i < 4; i++) this.reverbs[i] = null;
    }
    this.reverbs[paramReverb.getEngine()] = paramReverb;
  }

  public boolean load(SectFile paramSectFile)
  {
    if (!enabled) return true;
    if (!paramSectFile.sectionExist("common")) {
      errmsg("invalid acoustics preset format.");
      return false;
    }
    this.envNum = paramSectFile.get("common", "envnum", 0);

    this.eaxMix = paramSectFile.get("common", "eaxMix", -1.0F);
    this.occlusion = paramSectFile.get("common", "occlusion", 0.0F);
    this.occlLF = paramSectFile.get("common", "occlLF", 0.0F);
    this.obstruction = paramSectFile.get("common", "obstruction", 0.0F);
    this.obstrLF = paramSectFile.get("common", "obstrLF", 0.0F);
    this.eaxRoom = paramSectFile.get("common", "eaxRoom", 0.0F);

    jniSetup(this.handle, this.envNum);
    jniSetEAX(this.handle, this.eaxMix, this.occlusion, this.occlLF, this.eaxRoom, this.obstruction, this.obstrLF);
    boolean bool = paramSectFile.get("common", "hcontrol", false);
    Reverb localReverb;
    if (paramSectFile.sectionExist("eax1")) {
      localReverb = new Reverb(0);
      if (bool) localReverb.rfx = new ReverbFX(localReverb);
      if (!localReverb.load(paramSectFile)) return false;
      add(localReverb);
    }
    if (paramSectFile.sectionExist("eax2")) {
      localReverb = new Reverb(1);
      if (bool) localReverb.rfx = new ReverbFX(localReverb);
      if (!localReverb.load(paramSectFile)) return false;
      add(localReverb);
    }
    return true;
  }

  public boolean save()
  {
    return save(this.prs.ini);
  }

  public boolean save(SectFile paramSectFile)
  {
    paramSectFile.set("common", "envnum", this.envNum);
    for (int i = 0; i < this.reverbs.length; i++) {
      if (this.reverbs[i] == null) continue; this.reverbs[i].save(paramSectFile);
    }
    paramSectFile.saveFile();
    return true;
  }

  protected void finalize() throws Throwable
  {
    if (this.handle != 0) jniDestroy(this.handle);
    super.finalize();
  }

  protected void flush(float paramFloat)
  {
    if (this.reverbs != null) {
      int i = AudioDevice.getAcousticsCaps();
      Reverb localReverb;
      if (((i & 0x4) != 0) && (this.reverbs[1] != null)) {
        localReverb = this.reverbs[1];
        localReverb.apply();
        if (localReverb.rfx != null) localReverb.rfx.tick(paramFloat);
        if (this.globFX != null) this.globFX.tick(localReverb);

      }
      else if (((i & 0x8) != 0) && (this.reverbs[2] != null)) {
        localReverb = this.reverbs[2];
        localReverb.apply();
        if (localReverb.rfx != null) localReverb.rfx.tick(paramFloat);
        if (this.globFX != null) this.globFX.tick(localReverb);

      }
      else if (((i & 0x2) != 0) && (this.reverbs[0] != null)) {
        localReverb = this.reverbs[0];
        localReverb.apply();
        if (localReverb.rfx != null) localReverb.rfx.tick(paramFloat);
        if (this.globFX != null) this.globFX.tick(localReverb);
      }
    }
  }

  protected static native int jniCreate();

  protected static native void jniDestroy(int paramInt);

  protected static native void jniSetParent(int paramInt1, int paramInt2);

  protected static native void jniSetup(int paramInt1, int paramInt2);

  protected static native void jniSetEAX(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected static native void jniSetPosition(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3);

  protected static native void jniSetVelocity(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);

  protected static native void jniSetOrientation(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected static native float jniGetControl(int paramInt1, int paramInt2);

  protected static native void jniSetControl(int paramInt1, int paramInt2, float paramFloat);
}