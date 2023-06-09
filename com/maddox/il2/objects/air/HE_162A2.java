package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class HE_162A2 extends HE_162
{
  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -50.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -50.0F * paramFloat, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
    if (this.FM.CT.getGear() > 0.99F) {
      ypr[1] = (40.0F * this.FM.CT.getRudder());
    }
    hierMesh().chunkSetLocate("GearC25_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearC27_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearC28_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30.0F), 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 17:
      return super.cutFM(11, paramInt2, paramActor);
    case 18:
      return super.cutFM(12, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "He-162");
    Property.set(localClass, "meshName", "3DO/Plane/He-162A-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1944.2F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/He-162A-2.fmd");
    Property.set(localClass, "cockpitClass", CockpitHE_162A2.class);
    Property.set(localClass, "LOSElevation", 0.5099F);

    weaponTriggersRegister(localClass, new int[] { 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15120k 120", "MGunMG15120k 120" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}