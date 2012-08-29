package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;

public class SPITFIRE5 extends SPITFIRE
{
  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.FM.isPlayers()) {
      RealFlightModel localRealFlightModel = (RealFlightModel)this.FM;
      if (localRealFlightModel.RealMode)
        this.FM.producedAM.z -= 25.0F * this.FM.EI.engines[0].getControlRadiator() * localRealFlightModel.indSpeed;
    }
  }
}