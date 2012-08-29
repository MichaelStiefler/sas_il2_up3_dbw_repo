package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;

class CockpitBF_210$Interpolater extends InterpolateRef
{
  private final CockpitBF_210 this$0;

  CockpitBF_210$Interpolater(CockpitBF_210 paramCockpitBF_210)
  {
    this.this$0 = paramCockpitBF_210;
  }
  public boolean tick() {
    if (((BF_110G2)this.this$0.aircraft() == null) || 
      (BF_110G2.bChangedPit)) {
      this.this$0.reflectPlaneToModel();
      if ((BF_110G2)this.this$0.aircraft() != null);
      BF_110G2.bChangedPit = false;
    }
    CockpitBF_210.access$102(this.this$0, CockpitBF_210.access$200(this.this$0));
    CockpitBF_210.access$202(this.this$0, CockpitBF_210.access$300(this.this$0));
    CockpitBF_210.access$302(this.this$0, CockpitBF_210.access$100(this.this$0));
    CockpitBF_210.access$300(this.this$0).altimeter = this.this$0.fm.getAltitude();
    if (this.this$0.cockpitDimControl) {
      if (CockpitBF_210.access$300(this.this$0).dimPosition > 0.0F)
        CockpitBF_210.access$300(this.this$0).dimPosition -= 0.05F;
    } else if (CockpitBF_210.access$300(this.this$0).dimPosition < 1.0F)
      CockpitBF_210.access$300(this.this$0).dimPosition += 0.05F;
    CockpitBF_210.access$300(this.this$0).throttle1 = (0.91F * CockpitBF_210.access$200(this.this$0).throttle1 + 0.09F * this.this$0.fm.EI.engines[0].getControlThrottle());

    CockpitBF_210.access$300(this.this$0).throttle2 = (0.91F * CockpitBF_210.access$200(this.this$0).throttle2 + 0.09F * this.this$0.fm.EI.engines[1].getControlThrottle());

    CockpitBF_210.access$300(this.this$0).mix1 = (0.88F * CockpitBF_210.access$200(this.this$0).mix1 + 0.12F * this.this$0.fm.EI.engines[0].getControlMix());

    CockpitBF_210.access$300(this.this$0).mix2 = (0.88F * CockpitBF_210.access$200(this.this$0).mix2 + 0.12F * this.this$0.fm.EI.engines[1].getControlMix());

    CockpitBF_210.access$300(this.this$0).azimuth = this.this$0.fm.Or.getYaw();
    if ((CockpitBF_210.access$200(this.this$0).azimuth > 270.0F) && (CockpitBF_210.access$300(this.this$0).azimuth < 90.0F))
      CockpitBF_210.access$200(this.this$0).azimuth -= 360.0F;
    if ((CockpitBF_210.access$200(this.this$0).azimuth < 90.0F) && (CockpitBF_210.access$300(this.this$0).azimuth > 270.0F))
      CockpitBF_210.access$200(this.this$0).azimuth += 360.0F;
    CockpitBF_210.access$300(this.this$0).waypointAzimuth = ((10.0F * CockpitBF_210.access$200(this.this$0).waypointAzimuth + (this.this$0.waypointAzimuth() - CockpitBF_210.access$200(this.this$0).azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);

    CockpitBF_210.Variables localVariables = CockpitBF_210.access$300(this.this$0);
    float f1 = 0.9F * CockpitBF_210.access$200(this.this$0).radioalt;
    float f2 = 0.1F;
    float f3 = this.this$0.fm.getAltitude();
    World.cur();
    World.land();
    localVariables.radioalt = (f1 + f2 * (f3 - Landscape.HQ_Air((float)this.this$0.fm.Loc.x, (float)this.this$0.fm.Loc.y)));

    CockpitBF_210.access$300(this.this$0).vspeed = ((199.0F * CockpitBF_210.access$200(this.this$0).vspeed + this.this$0.fm.getVertSpeed()) / 200.0F);

    return true;
  }
}