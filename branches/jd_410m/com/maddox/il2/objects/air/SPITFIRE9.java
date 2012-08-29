package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;

public abstract class SPITFIRE9 extends SPITFIRE
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { super.update(paramFloat);
    hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    if (this.kangle > 1.0F) this.kangle = 1.0F;
  }
}