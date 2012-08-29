package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class ME_210CA1ZSTR extends ME_210
  implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored
{
  protected void moveBayDoor(float paramFloat)
  {
  }

  static
  {
    Class localClass = ME_210CA1ZSTR.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me-210");
    Property.set(localClass, "meshName", "3DO/Plane/Me-210Ca-1Zerstorer/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-210Ca-1.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 10, 10, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_CANNON03" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "MGunBofors40 29" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}