package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

public class Emitter extends BaseObject
{
  protected String name = null;
  protected int handle = 0;
  protected ArrayList samples = null;
  protected float speed;
  protected float sRand;
  protected float vRand;
  protected float pRand;
  protected float spl;

  public Emitter(String paramString, int paramInt)
  {
    this.name = paramString;
    this.handle = jniCreate(paramInt);
  }

  public void load(SectFile paramSectFile, String paramString) throws Exception
  {
    this.speed = paramSectFile.get(paramString, "speed", 1.0F);
    this.sRand = paramSectFile.get(paramString, "srand", 0.0F);
    this.vRand = paramSectFile.get(paramString, "vrand", 0.0F);
    this.pRand = paramSectFile.get(paramString, "prand", 0.0F);
    this.spl = paramSectFile.get(paramString, "spl", 0.0F);

    int i = paramSectFile.get(paramString, "num", 0);
    if (i > 0) {
      this.samples = new ArrayList(i);
      for (int j = 0; j < i; j++) {
        String str = paramSectFile.get(paramString, "sample" + j, (String)null);
        if (str == null) throw new Exception("No name: " + paramString);
        Sample localSample = new Sample(str);
        localSample.load(paramSectFile, paramString + "." + str);
        this.samples.add(localSample);
        jniAddSample(this.handle, localSample.handle);
      }
    }
    jniSet(this.handle, this.speed, this.sRand, this.vRand, this.pRand, this.spl);
  }

  protected static native int jniCreate(int paramInt);

  protected static native void jniSet(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5);

  protected static native void jniAddSample(int paramInt1, int paramInt2);
}