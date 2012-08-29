package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.BombSC50;
import com.maddox.rts.Property;

public class BF_109E7 extends BF_109
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F));
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F));
    }
    hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    if (this.kangle > 1.0F) this.kangle = 1.0F;
    super.update(paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++)
      {
        if (!(arrayOfObject[i] instanceof BombSC50))
          continue;
        hierMesh().chunkVisible("Rack", false);
        hierMesh().chunkVisible("ETC50", true);
      }
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat <= f1) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1) {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F);
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
    if (paramFloat <= f1) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1) {
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat) {
    if (paramFloat > 77.5F) { paramFloat = 77.5F; this.FM.Gears.steerAngle = paramFloat; }
    if (paramFloat < -77.5F) { paramFloat = -77.5F; this.FM.Gears.steerAngle = paramFloat; }
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = BF_109E7.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3do/plane/Bf-109E-7/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_sk", "3do/plane/Bf-109E-7(sk)/hier.him");
    Property.set(localClass, "PaintScheme_sk", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Bf-109E-7.fmd");
    Property.set(localClass, "cockpitClass", CockpitBF_109Ex.class);
    Property.set(localClass, "LOSElevation", 0.74985F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 3, 3, 3, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalDev01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, null });

    weaponsRegister(localClass, "4xSC50", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null });

    weaponsRegister(localClass, "1xSC250", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", "BombGunSC250 1", null, null, null, null, null });

    weaponsRegister(localClass, "1xDROPTANK", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, "FuelTankGun_Type_D" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}