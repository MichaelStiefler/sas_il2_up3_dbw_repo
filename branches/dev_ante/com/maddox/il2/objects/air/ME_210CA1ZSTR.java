package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class ME_210CA1ZSTR extends ME_210
  implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored
{
  public static boolean bChangedPit = false;

  protected void moveBayDoor(float paramFloat)
  {
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  static
  {
    Class localClass = ME_210CA1ZSTR.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Me-210");
    Property.set(localClass, "meshName", "3DO/Plane/Me-210Ca-1Zerstorer/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitME_210CA1ZSTR.class });

    Property.set(localClass, "LOSElevation", 0.66895F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 10, 10, 1 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_CANNON03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "MGunPaK40 42" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}