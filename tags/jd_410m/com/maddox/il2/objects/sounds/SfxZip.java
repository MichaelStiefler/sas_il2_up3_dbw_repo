package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.Engine;
import com.maddox.sound.SoundFX;

public class SfxZip extends SoundFX
{
  public SfxZip(Point3d paramPoint3d)
  {
    super("objects.zip");
    ActorSoundListener localActorSoundListener = Engine.soundListener();
    if (localActorSoundListener != null) {
      double d1 = localActorSoundListener.getRelRhoSqr(paramPoint3d);
      double d2 = 1600.0D; double d3 = 2400.0D;
      if (d1 < d2 * d2) setUsrFlag(0);
      else if (d1 < d3 * d3) setUsrFlag(1); else
        setUsrFlag(2);
    }
    jniSetPosition(this.handle, paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
    play();
  }
}