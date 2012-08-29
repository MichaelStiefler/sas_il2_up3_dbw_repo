package com.maddox.il2.objects.vehicles.radios;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

public class RotatingYGBeacon extends BeaconGeneric
{
  public RotatingYGBeacon()
  {
    startRotate();
  }

  public void startRotate()
  {
    interpPut(new Move(), "move", Time.current(), null);
  }
  class Move extends Interpolate {
    Move() {
    }

    public boolean tick() {
      if (RotatingYGBeacon.this.isAlive())
      {
        float f = BeaconGeneric.cvt((float)Time.current() % 30000.0F, 0.0F, 30000.0F, 0.0F, 360.0F);
        RotatingYGBeacon.this.hierMesh().chunkSetAngles("Head_D0", -f - RotatingYGBeacon.this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
      }
      return true;
    }
  }
}