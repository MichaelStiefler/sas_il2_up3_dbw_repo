package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class A6M5B extends A6M
{
  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmorg")) {
        getEnergyPastArmor(World.Rnd().nextFloat(44.0F, 46.0F), paramShot);
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        debugprintln(this, "*** Armor Glass: Hit..");
        if (paramShot.power <= 0.0F) {
          debugprintln(this, "*** Armor Glass: Bullet Stopped..");
          if (World.Rnd().nextFloat() < 0.5F) {
            doRicochetBack(paramShot);
          }
        }
        return;
      }
      if (paramString.startsWith("xxammor")) {
        if (World.Rnd().nextFloat() < 0.01F) {
          debuggunnery("Armament: Machine Gun Chain Broken..");
          this.FM.AS.setJamBullets(0, 0);
        }
        return;
      }
      if (paramString.startsWith("xxtank")) {
        int i = paramString.charAt(6) - '1';
        if (i < 3) {
          if ((getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.45F)) {
            if (this.FM.AS.astateTankStates[i] == 0) {
              debuggunnery("Fuel Tank (" + i + "): Pierced..");
              this.FM.AS.hitTank(paramShot.initiator, i, 2);
              this.FM.AS.doSetTankState(paramShot.initiator, i, 2);
            }
            if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.995F)) {
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
              debuggunnery("Fuel Tank (" + i + "): Hit..");
            }
          }
          return;
        }
      }
    }

    super.hitBone(paramString, paramShot, paramPoint3d);
  }

  static
  {
    Class localClass = A6M5B.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A6M");
    Property.set(localClass, "meshName", "3DO/Plane/A6M5b(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A6M5b(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());

    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A6M5b.fmd");
    Property.set(localClass, "cockpitClass", CockpitA6M5b.class);
    Property.set(localClass, "LOSElevation", 1.01885F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 9, 9, 3, 9, 9, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xwdt", new String[] { "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", "FuelTankGun_Tank0Wooden", null, null, null, null, null, null });

    weaponsRegister(localClass, "1xdt", new String[] { "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", "FuelTankGun_Tank0", null, null, null, null, null, null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, null });

    weaponsRegister(localClass, "2x60", new String[] { "MGunMG131s 230", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", "BombGun50kg" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}