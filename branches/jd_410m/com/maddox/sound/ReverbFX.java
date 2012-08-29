package com.maddox.sound;

import com.maddox.rts.SectFile;

public class ReverbFX extends BaseObject
{
  Reverb owner;
  float roomMin = 0.0F;
  float bound = 1000.0F;

  public ReverbFX(Reverb paramReverb)
  {
    this.owner = paramReverb;
  }

  public void load(SectFile paramSectFile)
  {
    String str = "hcontrol." + this.owner.getEngineName();
    this.roomMin = paramSectFile.get(str, "min", 0.0F);
    this.bound = paramSectFile.get(str, "bound", 1000.0F);
  }

  public void save(SectFile paramSectFile)
  {
  }

  public void tick(float paramFloat)
  {
    if (paramFloat < this.bound)
      this.owner.set(100, this.roomMin + (1.0F - this.roomMin) * (this.bound - paramFloat) / this.bound);
    else
      this.owner.set(100, this.roomMin);
  }
}