package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Sample extends BaseObject
{
  protected String name;
  protected int handle;
  protected int iniFlags = 0;
  protected int envFlags = -1;
  protected int engFlags = -1;
  protected int usrFlags = -1;
  protected float iniGain = 1.0F;
  protected float iniPitch = 1.0F;
  protected float iniSpl = 0.0F;
  protected float autoPeriod = 0.0F;

  protected ArrayList controls = null;
  public static final int TYPE_SINGLE = 0;
  public static final int TYPE_STREAM = 1;
  public static final int TYPE_MUSIC = 2;
  public static final int LM_STANDARD = 0;
  public static final int LM_CACHE = 1;

  public Sample(String paramString)
  {
    this.name = paramString;
    this.handle = jniCreate("samples/" + paramString, 0);
    if (this.handle != 0) jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod);
  }

  public Sample(String paramString, int paramInt1, int paramInt2)
  {
    this.name = paramString;
    this.handle = jniCreate("samples/" + paramString, 0);
    this.envFlags = paramInt1;
    this.usrFlags = paramInt2;
    if (this.handle != 0) jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod);
  }

  public Sample(String paramString, int paramInt)
  {
    this.name = paramString;
    this.handle = jniCreate("samples/" + paramString, paramInt);
    if (this.handle != 0) jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod);
  }

  public Sample(String paramString1, String paramString2)
  {
    this.name = paramString2;
    if (paramString1 == null) this.handle = jniCreate(paramString2, 0); else
      this.handle = jniCreate(paramString1 + paramString2, 0);
    if (this.handle != 0) jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod); 
  }

  public Sample(SectFile paramSectFile, String paramString)
  {
    try
    {
      this.name = paramSectFile.get(paramString, "file", (String)null);
      if (this.name == null) throw new Exception();
      this.handle = jniCreate("samples/" + this.name, 0);
      load(paramSectFile, paramString);
    }
    catch (Exception localException) {
      errmsg("cannot load sample: " + this.name);
      this.handle = 0;
    }
  }

  public boolean exists()
  {
    return this.handle != 0;
  }

  public boolean isInfinite()
  {
    return (this.iniFlags & 0x1) != 0;
  }

  public void setInfinite(boolean paramBoolean)
  {
    if (paramBoolean) this.iniFlags |= 1; else
      this.iniFlags &= -2;
    jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod);
  }

  public void addEnv(int paramInt)
  {
    if (this.envFlags == -1) this.envFlags = 0;
    this.envFlags |= 1 << paramInt;
  }

  public void remEnv(int paramInt)
  {
    this.envFlags &= (1 << paramInt ^ 0xFFFFFFFF);
  }

  public void addUsr(int paramInt)
  {
    if (this.usrFlags == -1) this.usrFlags = 0;
    this.usrFlags |= 1 << paramInt;
  }

  public void remUsr(int paramInt)
  {
    this.usrFlags &= (1 << paramInt ^ 0xFFFFFFFF);
  }

  public void load(SectFile paramSectFile, String paramString)
    throws Exception
  {
    this.iniGain = paramSectFile.get(paramString, "gain", 1.0F);
    this.iniPitch = paramSectFile.get(paramString, "pitch", 1.0F);
    this.iniSpl = paramSectFile.get(paramString, "spl", 0.0F);
    this.autoPeriod = paramSectFile.get(paramString, "period", 0.0F);
    if (paramSectFile.get(paramString, "infinite", false)) this.iniFlags = 1;
    String str1 = paramSectFile.get(paramString, "env", (String)null);
    StringTokenizer localStringTokenizer;
    int i;
    if (str1 != null) {
      localStringTokenizer = new StringTokenizer(str1);
      while (localStringTokenizer.hasMoreTokens()) {
        i = Integer.parseInt(localStringTokenizer.nextToken());
        addEnv(i);
      }
    }
    str1 = paramSectFile.get(paramString, "usr", (String)null);
    if (str1 != null) {
      localStringTokenizer = new StringTokenizer(str1);
      while (localStringTokenizer.hasMoreTokens()) {
        i = Integer.parseInt(localStringTokenizer.nextToken());
        addUsr(i);
      }
    }
    jniSet(this.handle, this.iniFlags, this.envFlags, this.engFlags, this.usrFlags, this.iniGain, this.iniPitch, this.iniSpl, this.autoPeriod);

    str1 = paramSectFile.get(paramString, "controls", (String)null);
    if (str1 != null) {
      this.controls = new ArrayList();
      localStringTokenizer = new StringTokenizer(str1);
      while (localStringTokenizer.hasMoreTokens()) {
        String str2 = localStringTokenizer.nextToken();
        SoundControl localSoundControl = ControlInfo.get(str2, jniGetControlList(this.handle));
        if (localSoundControl != null)
        {
          localSoundControl.load(paramSectFile, paramString + "." + str2);
        }
      }
    }
  }

  public void save(SectFile paramSectFile)
  {
  }

  public AudioStream get()
  {
    return new AudioStream(jniCreateStream(this.handle));
  }

  protected native int jniCreate(String paramString, int paramInt);

  protected native void jniSet(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  protected native int jniGetControlList(int paramInt);

  protected native int jniCreateStream(int paramInt);
}