package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class SPITFIRE8CLP extends SPITFIRE
{
  private float flapps = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, cvt(paramFloat, 0.0F, 0.6F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, cvt(paramFloat, 0.2F, 1.0F, 0.0F, -95.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.01F, 0.99F, 0.0F, -75.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F) {
      this.flapps = f;
      hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20.0F * f, 0.0F);
      hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20.0F * f, 0.0F);
    }
  }

  static
  {
    Class localClass = SPITFIRE8CLP.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Spit");
    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkVIIICLP(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Spitfire-LF-VIII-M66-18-CW.fmd");
    Property.set(localClass, "cockpitClass", CockpitSpit8.class);
    Property.set(localClass, "LOSElevation", 0.5926F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIk 120", "MGunHispanoMkIk 120", null });

    weaponsRegister(localClass, "30gal", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIk 120", "MGunHispanoMkIk 120", "FuelTankGun_TankSpit30" });

    weaponsRegister(localClass, "45gal", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIk 120", "MGunHispanoMkIk 120", "FuelTankGun_TankSpit45" });

    weaponsRegister(localClass, "90gal", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIk 120", "MGunHispanoMkIk 120", "FuelTankGun_TankSpit90" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}