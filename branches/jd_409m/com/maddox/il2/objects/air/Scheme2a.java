package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;

public abstract class Scheme2a extends Scheme2
{
  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 17:
      super.cutFM(11, paramInt2, paramActor);
    case 18:
      super.cutFM(12, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }
}