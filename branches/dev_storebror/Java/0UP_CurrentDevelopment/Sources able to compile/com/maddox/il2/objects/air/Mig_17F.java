// Source File Name: Mig_17F.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.rts.Property;

public class Mig_17F extends Mig_17
        implements TypeGSuit {

  public Mig_17F() {
  }
  /**
   * G-Force Resistance, Tolerance and Recovery parmeters.
   * See TypeGSuit.GFactors Private fields implementation
   * for further details.
   */
  private static final float NEG_G_TOLERANCE_FACTOR = 1.0F;
  private static final float NEG_G_TIME_FACTOR = 1.0F;
  private static final float NEG_G_RECOVERY_FACTOR = 1.0F;
  private static final float POS_G_TOLERANCE_FACTOR = 1.8F;
  private static final float POS_G_TIME_FACTOR = 1.5F;
  private static final float POS_G_RECOVERY_FACTOR = 1.0F;

  public void getGFactors(GFactors theGFactors) {
    theGFactors.setGFactors(
            NEG_G_TOLERANCE_FACTOR,
            NEG_G_TIME_FACTOR,
            NEG_G_RECOVERY_FACTOR,
            POS_G_TOLERANCE_FACTOR,
            POS_G_TIME_FACTOR,
            POS_G_RECOVERY_FACTOR);
  }

  public void update(float f1) {
    super.update(f1);
    typeFighterAceMakerRangeFinder();
    if (FM.AS.isMaster() && Config.isUSE_RENDER()) {
      if (FM.EI.engines[0].getPowerOutput() > 0.50F
              && FM.EI.engines[0].getStage() == 6) {
        if (FM.EI.engines[0].getPowerOutput() > 0.50F) {
          if (FM.EI.engines[0].getPowerOutput() > 1.001F) {
            FM.AS.setSootState(this, 0, 5);
          } else {
            FM.AS.setSootState(this, 0, 3);
          }
        }
      } else {
        FM.AS.setSootState(this, 0, 0);
      }
    }
  }

  static {
    Class class1 = com.maddox.il2.objects.air.Mig_17F.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "MiG-17");
    Property.set(class1, "meshName_ru", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar1956());
    Property.set(class1, "meshName_sk", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_ro", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_hu", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName", "3DO/Plane/MiG-17F(Multi1)/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(class1, "yearService", 1952.11F);
    Property.set(class1, "yearExpired", 1960.3F);
    Property.set(class1, "FlightModel", "FlightModels/MiG-17F.fmd");
    Property.set(class1, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitMig_17.class
            });
    Property.set(class1, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[]{
              1, 0, 0, 9, 9
            });
    com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[]{
              "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDroptanks", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[]{
              null, null, null, null, null
            });
  }
}