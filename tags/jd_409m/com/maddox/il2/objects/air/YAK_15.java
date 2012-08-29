package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class YAK_15 extends YAK
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.6F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.6F, 0.0F, -83.5F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, -85.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.4F, 0.9F, 0.0F, -83.5F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxCannon01")) {
        if (getEnergyPastArmor(9.8F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (0) Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 0);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxCannon02")) {
        if (getEnergyPastArmor(9.8F, paramShot) > 0.0F) {
          debuggunnery("Armament: Cannon (0) Disabled..");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325001F), paramShot);
        }
        return;
      }
    }
    super.hitBone(paramString, paramShot, paramPoint3d);
  }

  public void update(float paramFloat)
  {
    if ((Config.isUSE_RENDER()) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster())) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPowerOutput() > 0.8F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6)) {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPowerOutput() > 0.95F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 0, 3);
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 0, 2);
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this, 0, 0);
      }
    }

    super.update(paramFloat);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-15(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "meshName_ru", "3DO/Plane/Yak-15(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-15.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_15.class);
    Property.set(localClass, "LOSElevation", 1.0989F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVYaki 60", "MGunVYaki 60" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}