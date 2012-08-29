package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class TA_152H1 extends FW_190
{
  private float kangle = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 20.0F * paramFloat, 0.0F, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearL2a_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearR2a_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 15; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Loc.z > 9000.0D) {
      if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlAfterburner()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setAfterburnerType(2);
      }
    }
    else if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlAfterburner()) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setAfterburnerType(1);
    }

    super.update(paramFloat);
  }

  static
  {
    Class localClass = TA_152H1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ta.152");
    Property.set(localClass, "meshName", "3DO/Plane/Ta-152H-1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ta-152H-1.fmd");
    Property.set(localClass, "cockpitClass", CockpitTA_152.class);
    Property.set(localClass, "LOSElevation", 0.764106F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON03", "_CANNON04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMK108k 90", "MGunMG15120s  175", "MGunMG15120s  175" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null });
  }
}