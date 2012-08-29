package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MS410 extends MS400X
{
  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("OilRad_D0", 0.0F, -20.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    super.update(paramFloat);
  }

  static
  {
    Class localClass = MS410.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Morane");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/MS410(fi)/hier.him");
    Property.set(localClass, "meshName_fi", "3DO/Plane/MS410(fi)/hier.him");
    Property.set(localClass, "PaintScheme_fi", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MS410(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1951.8F);

    Property.set(localClass, "FlightModel", "FlightModels/MS410.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15k 300", "MGunMG15k 300", "MGunMG15k 300", "MGunMG15k 300", "MGunHispanoMkIki 60" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}