package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

public class SamplePool extends BaseObject
{
  protected int handle;
  protected ArrayList sources = null;

  public SamplePool()
  {
    this.handle = jniCreate();
  }

  public SamplePool(String paramString)
  {
    try {
      SectFile localSectFile = new SectFile("presets/sounds/" + paramString + ".prs");
      this.handle = jniCreate();
      if (this.handle != 0) load(localSectFile); 
    }
    catch (Exception localException)
    {
      errmsg("Cannot load sample pool: " + paramString);
    }
  }

  public void load(SectFile paramSectFile) throws Exception
  {
    int i = paramSectFile.sectionIndex("samples");
    if (i >= 0) {
      if (this.sources == null) this.sources = new ArrayList(32);
      for (int j = 0; j < paramSectFile.vars(i); j++) {
        String str = paramSectFile.line(i, j);
        Sample localSample = new Sample(str);
        localSample.load(paramSectFile, "sample." + str);
        this.sources.add(localSample);
        jniAddSample(this.handle, localSample.handle);
      }
    } else {
      throw new Exception("Cannot load preset");
    }
  }

  protected static native int jniCreate();

  protected static native void jniAddSample(int paramInt1, int paramInt2);
}