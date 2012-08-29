// Source File Name: F_86D45.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.air;

import java.util.ArrayList;

import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.weapons.GunNull;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGunK5M;
import com.maddox.rts.Property;

public class F_86D45 extends F_86D {

  public F_86D45() {
    rocketsList = new ArrayList();
  }

  public void onAircraftLoaded() {
    super.onAircraftLoaded();
    FM.CT.bHasBayDoors = true;
    this.setGunNullOwner();
    rocketsList.clear();
    createRocketsList();
  }

  protected void moveBayDoor(float f) {
    this.resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(f, 0.01F, 1.0F, 0.0F, -0.22F);
    this.hierMesh().chunkSetLocate("Launcher_D0", Aircraft.xyz,
            Aircraft.ypr);
  }

  private void setGunNullOwner() {
    try {
      for (int l = 0; l < FM.CT.Weapons.length; l++) {
        if (FM.CT.Weapons[l] != null) {
          for (int l1 = 0; l1 < FM.CT.Weapons[l].length; l1++) {
            if (FM.CT.Weapons[l][l1] != null) {
              if (FM.CT.Weapons[l][l1] instanceof GunNull) {
                ((GunNull) FM.CT.Weapons[l][l1]).setOwner(this);
              }
            }
          }
        }
      }
    } catch (Exception exception) {
    }
  }

  public void createRocketsList() {
    for (int i = 0; i < ((FlightModelMain) (super.FM)).CT.Weapons.length; i++) {
      if (((FlightModelMain) (super.FM)).CT.Weapons[i] != null) {
        for (int j = 0; j < ((FlightModelMain) (super.FM)).CT.Weapons[i].length; j++) {
          if (((FlightModelMain) (super.FM)).CT.Weapons[i][j] instanceof RocketGun) {
            rocketsList.add(((FlightModelMain) (super.FM)).CT.Weapons[i][j]);
          }
        }

      }
    }

  }

  public void shootRocket() {
    if (rocketsList.isEmpty()) {
      return;
    } else {
      ((RocketGunK5M) rocketsList.remove(0)).shots(1);
      return;
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    Maneuver maneuver = (Maneuver) FM;
    if ((!FM.isPlayers() || !(FM instanceof RealFlightModel) || !((RealFlightModel) FM).isRealMode()) && (FM instanceof Maneuver)) {
      if (((Maneuver) FM).get_maneuver() == Maneuver.FVSB_FROM_AHEAD || ((Maneuver) FM).get_maneuver() == Maneuver.ATTACK_BOMBER && (maneuver.target != null)) {
        FM.CT.BayDoorControl = 1.0F;
      } else if ((maneuver.target == null) || (this.rocketsList.isEmpty())) {
        FM.CT.BayDoorControl = 0.0F;
        maneuver.set_maneuver(3);
      }
    }
  }

  public void update(float f) {
    super.update(f);
  }
  private ArrayList rocketsList;

  static {
    Class localClass = com.maddox.il2.objects.air.F_86D45.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "F-86D");
    Property.set(localClass, "meshName", "3DO/Plane/F_86D(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "meshName_us", "3DO/Plane/F_86D(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1949.9F);
    Property.set(localClass, "yearExpired", 1960.3F);
    Property.set(localClass, "FlightModel", "FlightModels/F-86D-45.fmd:F-86D");
    Property.set(localClass, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitF_86K.class
            });
    Property.set(localClass, "LOSElevation", 0.725F);
    Aircraft.weaponTriggersRegister(localClass, new int[]{
              9, 9, 9, 9, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 0, 1
            });
    Aircraft.weaponHooksRegister(localClass, new String[]{
              "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06",
              "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16",
              "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_MGUN01", "_MGUN02"
            });
  }
}