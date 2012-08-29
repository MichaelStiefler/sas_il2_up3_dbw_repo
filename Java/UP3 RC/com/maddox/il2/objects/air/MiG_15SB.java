// Source File Name: MiG_15SB.java
// Author:           
// Last Modified by: Storebror 2011-06-03
package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.rts.*;

public class MiG_15SB extends Mig_15F
        implements TypeStormovik {

  public MiG_15SB() {
  }

  public void update(float f) {
    super.update(f);
    if (FM.getSpeedKMH() > 1100.00F) {
      FM.CT.AirBrakeControl = 1.0F;
      World.cur();
    }
  }

  static {
    Class var_class = com.maddox.il2.objects.air.MiG_15SB.class;
    new NetAircraft.SPAWN(var_class);
    Property.set(var_class, "iconFar_shortClassName", "MiG-15");
    Property.set(var_class, "meshName_ru", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_ru", new PaintSchemeFCSPar1956());
    Property.set(var_class, "meshName_sk", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_sk", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName_ro", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_ro", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName_hu", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme_hu", new PaintSchemeFMPar1956());
    Property.set(var_class, "meshName", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(var_class, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(var_class, "yearService", 1949.9F);
    Property.set(var_class, "yearExpired", 1960.3F);
    Property.set(var_class, "FlightModel", "FlightModels/MiG-15F.fmd");
    Property.set(var_class, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitMig_15F.class
            });
    Property.set(var_class, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(var_class, new int[]{
              1, 0, 0, 9, 9, 9, 9, 9, 9, 9,
              9, 3, 3, 3, 3, 3, 3, 3, 3, 3,
              3, 3, 3, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
              2
            });
    com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(var_class, new java.lang.String[]{
              "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07",
              "_ExternalDev08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04", "_ExternalBomb03", "_ExternalBomb05",
              "_ExternalBomb06", "_ExternalBomb06", "_ExternalBomb05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07",
              "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17",
              "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27",
              "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalRock37",
              "_ExternalRock38", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock41", "_ExternalRock42", "_ExternalRock42", "_ExternalRock43", "_ExternalRock43", "_ExternalRock44",
              "_ExternalRock44"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "default", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xdroptanks", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15 1", "PylonMiG15 1", null, null, null,
              null, "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x100+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15 1", "PylonMiG15 1", null, null, null,
              null, "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x100+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15 1", "PylonMiG15 1", null, null, null,
              null, "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x250m46+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15 1", "PylonMiG15 1", null, null, null,
              null, "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x250m46+2xdt+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2x250m46+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4x250m46+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15 1", "PylonMiG15 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", "BombGunFAB250m46 1", "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55+2xdt+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55+2xdt+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xSR55", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonSR55 1",
              "PylonSR55 1", null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xSR55+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonSR55 1",
              "PylonSR55 1", null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1",
              "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a+2xdt+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a+2xdt+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xSR55a2a+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xSR55a2a", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonSR55 1", "PylonSR55 1", null, null, "PylonSR55 1",
              "PylonSR55 1", null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xSR55a2a+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonSR55 1", "PylonSR55 1", null, null, "PylonSR55 1",
              "PylonSR55 1", null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1", "RocketGunLR55Salvo 1",
              "RocketGunLR55Salvo 1", "RocketGunLR55 1", "RocketGunLR55 1", null, null, null, null, null, null, null,
              null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130+2xdt+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130+2xdt+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130+2x100", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB100 1",
              "BombGunNull 1", "BombGunFAB100 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "2xLR130+2x250m46", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15 1",
              "PylonMiG15 1", null, null, null, null, null, null, null, null, "BombGunFAB250m46 1",
              "BombGunNull 1", "BombGunFAB250m46 1", "BombGunNull 1", null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xLR130", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15LR130 1",
              "PylonMiG15LR130 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "4xLR130+2xdt", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", "PylonMiG15LR130 1", "PylonMiG15LR130 1", null, null, "PylonMiG15LR130 1",
              "PylonMiG15LR130 1", null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1", "RocketGunNull 1", "RocketGunLR130 1",
              "RocketGunNull 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(var_class, "none", new java.lang.String[]{
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null,
              null
            });
  }
}