package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class A6M2N extends A6M
  implements TypeSailPlane
{
  private static Point3d tmpp = new Point3d();

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
  }

  public void moveWheelSink()
  {
  }

  public void moveSteering(float paramFloat)
  {
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    super.hitBone(paramString, paramShot, paramPoint3d);

    if (paramString.startsWith("xgearc")) {
      hitChunk("GearC2", paramShot);
    }
    if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    }
    if (paramString.startsWith("xgearr"))
      hitChunk("GearR2", paramShot);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 3; i++) for (int j = 0; j < 2; j++)
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpGearEff[i][j] != null) {
          tmpp.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpGearEff[i][j].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
          tmpp.z = 0.01D;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpGearEff[i][j].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(tmpp);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.clpGearEff[i][j].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        }
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("GearC11_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  static
  {
    Class localClass = A6M2N.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A6M");

    Property.set(localClass, "meshName", "3DO/Plane/A6M2-N(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A6M2-N(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A6M2N.fmd");
    Property.set(localClass, "cockpitClass", CockpitA6M2N.class);
    Property.set(localClass, "LOSElevation", 1.01885F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15sipzl 1000", "MGunMG15sipzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x60", new String[] { "MGunMG15sipzl 1000", "MGunMG15sipzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", "BombGun50kg" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}