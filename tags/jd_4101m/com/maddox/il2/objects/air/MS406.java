package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MS406 extends MS400X
{
  public void update(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.2F, 0.0F);
    hierMesh().chunkSetLocate("OilRad_D0", xyz, ypr);
    super.update(paramFloat);
  }

  static
  {
    Class localClass = MS406.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Morane");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/MS406(fi)/hier.him");
    Property.set(localClass, "meshName_fi", "3DO/Plane/MS406(fi)/hier.him");
    Property.set(localClass, "PaintScheme_fi", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MS406(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1951.8F);

    Property.set(localClass, "FlightModel", "FlightModels/MS406.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01" });
    weaponsRegister(localClass, "default", new String[] { "MGunMAC1934 300", "MGunMAC1934 300", "MGunHispanoMkIki 60" });
    weaponsRegister(localClass, "3xMAC1934", new String[] { "MGunMAC1934 300", "MGunMAC1934 300", "MGunMAC1934i 300" });
    weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}