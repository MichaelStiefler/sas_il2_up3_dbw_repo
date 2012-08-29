package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class BF_109F4 extends BF_109
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }
    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    if (this.kangle > 1.0F) this.kangle = 1.0F;
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if ((paramFloat <= f1) || (paramFloat == 1.0F)) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1) {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearC3_D0", 70.0F * paramFloat, 0.0F, 0.0F);
    if (paramFloat > 0.99F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
    if (paramFloat < 0.01F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat) {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if ((paramFloat <= f1) || (paramFloat == 1.0F)) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1) {
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    hierMesh().chunkSetAngles("GearC3_D0", 70.0F * paramFloat, 0.0F, 0.0F);
    if (paramFloat > 0.99F) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = BF_109F4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109F-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_sk", "3DO/Plane/Bf-109F-4(sk)/hier.him");
    Property.set(localClass, "PaintScheme_sk", new PaintSchemeFMPar03());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-109F-4.fmd");
    Property.set(localClass, "cockpitClass", CockpitBF_109F2.class);
    Property.set(localClass, "LOSElevation", 0.74205F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 1, 9, 9, 1, 1, 9, 9, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_ExternalDev02", "_ExternalDev03", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalDev01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1sc250", new String[] { "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", null, "BombGunSC250 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc50", new String[] { "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC50Bf109 1", null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1" });

    Aircraft.weaponsRegister(localClass, "droptank", new String[] { "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", null, null, null, null, "PylonETC900 1", "FuelTankGun_Type_D 1", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xMG151_pods", new String[] { "MGunMG17si 500", "MGunMG17si 500", null, "MGunMG15120MGki 200", "PylonMG15120 1", "PylonMG15120 1", "MGunMG151k 135", "MGunMG151k 135", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}