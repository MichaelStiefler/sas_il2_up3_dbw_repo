package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class KI_46_OTSU extends KI_46
  implements TypeFighter
{
  public boolean bChangedPit = true;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      this.bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      this.bChangedPit = true;
  }

  static
  {
    Class localClass = KI_46_OTSU.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ki-46");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-46(Otsu)(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Ki-46-IIIKai.fmd");

    Property.set(localClass, "cockpitClass", CockpitKI_46_OTSU.class);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo5ki 200", "MGunHo5ki 200" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}