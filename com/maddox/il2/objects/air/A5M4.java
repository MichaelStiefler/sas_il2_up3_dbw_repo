package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class A5M4 extends A5M
{
  public static boolean bChangedPit = false;
  private float flapps = 0.0F;

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
    Class localClass = A5M4.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "A5M");
    Property.set(localClass, "meshName", "3DO/Plane/A5M4(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A5M4(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());
    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/A5M4.fmd");
    Property.set(localClass, "cockpitClass", CockpitA5M4.class);

    Property.set(localClass, "LOSElevation", 0.7498F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVikkersKs 500", "MGunVikkersKs 500", null, null });

    Aircraft.weaponsRegister(localClass, "1xdt", new String[] { "MGunVikkersKs 500", "MGunVikkersKs 500", "PylonA5MPLN1", "FuelTankGun_TankA5M" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}