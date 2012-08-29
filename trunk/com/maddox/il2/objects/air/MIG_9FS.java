package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MIG_9FS extends MIG_9
{
  private int nCN37 = -1;

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if ((this.FM.AS.isMaster()) && 
      (this.FM.CT.Weapons[1] != null) && (this.FM.CT.Weapons[1][0] != null)) {
      if (this.FM.CT.Weapons[1][0].countBullets() < this.nCN37) {
        if (World.Rnd().nextFloat() < cvt(this.FM.getAltitude(), 3000.0F, 7000.0F, 0.0F, 0.1F)) {
          this.FM.EI.engines[0].setEngineStops(this);
        }
        if (World.Rnd().nextFloat() < cvt(this.FM.getAltitude(), 3000.0F, 7000.0F, 0.0F, 0.1F)) {
          this.FM.EI.engines[1].setEngineStops(this);
        }
      }
      this.nCN37 = this.FM.CT.Weapons[1][0].countBullets();
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "MiG-9");
    Property.set(localClass, "meshName", "3DO/Plane/MiG-9FS(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "meshName_ru", "3DO/Plane/MiG-9FS(ru)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar06());

    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1956.0F);

    Property.set(localClass, "FlightModel", "FlightModels/MiG-9.fmd");
    Property.set(localClass, "cockpitClass", CockpitMIG_9.class);
    Property.set(localClass, "LOSElevation", 0.75635F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03" });

    weaponsRegister(localClass, "default", new String[] { "MGunVYak 80", "MGunVYak 80", "MGunN37ki 40" });

    weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}