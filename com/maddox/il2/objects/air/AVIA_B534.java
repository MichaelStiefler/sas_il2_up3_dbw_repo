package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;

public class AVIA_B534 extends Avia_B5xx
{
  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f1 = Atmosphere.temperature((float)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 273.14999F;
    float f2 = Pitot.Indicator((float)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH());
    if (f2 < 0.0F)
      f2 = 0.0F;
    float f3 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator() * paramFloat * f2 / (f2 + 50.0F) * (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut - f1) / 256.0F;

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut -= f3;
  }

  static
  {
    Class localClass = AVIA_B534.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B-534");
    Property.set(localClass, "meshName_sk", "3DO/Plane/AviaB-534/hier.him");
    Property.set(localClass, "PaintScheme_sk", new PaintSchemeFMPar00s());
    Property.set(localClass, "meshName_de", "3DO/Plane/AviaB-534(de)/hier.him");
    Property.set(localClass, "PaintScheme_de", new PaintSchemeFMPar00s());
    Property.set(localClass, "meshName_hu", "3DO/Plane/AviaB-534(hu)/hier.him");
    Property.set(localClass, "PaintScheme_hu", new PaintSchemeFMPar00s());
    Property.set(localClass, "meshName", "3DO/Plane/AviaB-534(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00s());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1950.0F);
    Property.set(localClass, "FlightModel", "FlightModels/AviaB-534.fmd");
    Property.set(localClass, "originCountry", PaintScheme.countrySlovakia);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitAVIA_B534.class });

    Property.set(localClass, "LOSElevation", 0.66F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6*10kg", new String[] { "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1", null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ" });

    Aircraft.weaponsRegister(localClass, "4*20kg", new String[] { "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "MGunVz30syn 300", "PylonS328 1", "PylonS328 1", null, null, "PylonS328 1", "PylonS328 1", "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}