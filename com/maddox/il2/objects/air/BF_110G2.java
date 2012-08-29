package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class BF_110G2 extends BF_110
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f2 < -19.0F) { f2 = -19.0F; bool = false; }
      if (f2 > 30.0F) { f2 = 30.0F; bool = false;
      }
      float f3;
      if (f2 < 0.0F)
        f3 = Aircraft.cvt(f2, -19.0F, 0.0F, 20.0F, 30.0F);
      else if (f2 < 12.0F)
        f3 = Aircraft.cvt(f2, 0.0F, 12.0F, 30.0F, 35.0F);
      else {
        f3 = Aircraft.cvt(f2, 12.0F, 30.0F, 35.0F, 40.0F);
      }
      if (f1 < 0.0F) {
        if (f1 < -f3) {
          f1 = -f3;
          bool = false;
        }
      }
      else if (f1 > f3) {
        f1 = f3;
        bool = false;
      }

      if ((Math.abs(f1) <= 17.799999F) || (Math.abs(f1) >= 25.0F) || (f2 >= -12.0F)) break;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = BF_110G2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Bf-110");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-110G-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-110G-2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_110G.class, CockpitBF_110G_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.66895F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 10, 10, 9, 9, 9, 3, 3, 3, 3, 3, 3, 0, 0, 1, 9, 9, 9, 9, 2, 2, 2, 2, 9, 9, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_CANNON01", "_CANNON02", "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "m1", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ab250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc500", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ab500", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB500", "BombGunAB500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sd500", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc2504sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ab2504sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB250", "BombGunAB250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc5004sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC500", "BombGunSC500", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2ab5004sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB500", "BombGunAB500", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2sd5004sc50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSD500", "BombGunSD500", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "m5", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, null, null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "m1m3", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", "PylonBF110R3", null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "m1m5", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, null, "MGunMG15120k 200", "MGunMG15120k 200", null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r4", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", null, null, null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r1r7", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", null, null, null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r3", new String[] { null, null, null, null, null, null, "MGunMG15120MGk 350", "MGunMG15120MGk 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3m1", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3m2sc250", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3m2ab250", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3m2sc500", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC500", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3m2sd500", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSD500", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r3r7m1", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r3r7m2sc250", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r3r7m2ab250", new String[] { null, null, null, null, null, null, "MGunMG15120MGki 350", "MGunMG15120MGki 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r3r7m5", new String[] { null, null, null, null, null, null, "MGunMG15120MGk 350", "MGunMG15120MGk 300", "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, null, null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r4_", new String[] { null, null, null, null, null, null, null, null, "MGunMK108ki 135", "MGunMK108ki 135", "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R4", null, null, null, null, null, null, null, null, "MGunBK37BF110G2 72", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "r7", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m1", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, "PylonBF110R3", null, null, null, null, null, null, "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m2sc250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunSC250", "BombGunSC250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m2ab250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", null, null, null, "BombGunAB250", "BombGunAB250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m3", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGk 350", "MGunMG15120MGk 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m2m3sc", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunSC250", "BombGunSC250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "r7m2m3ab", new String[] { "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMG15120MGki 350", "MGunMG15120MGki 300", null, null, null, null, "MGunMG81t 800", "MGunMG81t 800", "PylonETC71", "PylonETC71", null, "BombGunAB250", "BombGunAB250", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null, null, null, null, null, null, null, null, null, null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}