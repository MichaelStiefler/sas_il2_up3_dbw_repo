package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class P_63C extends P_39
{
  private float fSteer = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC6_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC7_D0", 0.0F, cvt(paramFloat, 0.01F, 0.12F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC8_D0", 0.0F, cvt(paramFloat, 0.01F, 0.12F, 0.0F, -80.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -85.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, -90.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -85.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2405F);
    ypr[1] = this.fSteer;
    hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearC5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -72.0F), 0.0F);
    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -40.0F), 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, 0.11675F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, -15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, -27.0F), 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, 0.11675F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, -15.0F), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, -27.0F), 0.0F);
  }

  public void moveSteering(float paramFloat)
  {
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31.0F * paramFloat, 0.0F);
    this.fSteer = (20.0F * paramFloat);
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2405F);
    ypr[1] = this.fSteer;
    hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
  }

  static
  {
    Class localClass = P_63C.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P63");
    Property.set(localClass, "meshName", "3DO/Plane/P-63C(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-63C(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.5F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-63C.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_63C.class);
    Property.set(localClass, "LOSElevation", 0.70305F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 0, 0, 9, 9, 9, 3, 3, 3, 3, 9, 9, 9, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev04", "_ExternalDev05" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", null, null, null, null, "FuelTankGun_Tank75gal2", null, null, null, null });

    weaponsRegister(localClass, "2x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null });

    weaponsRegister(localClass, "3x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null });

    weaponsRegister(localClass, "2xM2", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, null, null, null, null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_1x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", null, null, null, null, "FuelTankGun_Tank75gal2", null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_2x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_3x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_1xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", "BombGunFAB100 1", null, null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_1xFAB100_2x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_2xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_3xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_1xFAB250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", "BombGunFAB250 1", null, null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_1xFAB250_2x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB250 1", null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_2xFAB250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "2xM2_2xFAB250_1x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, "BombGunFAB250 1", "BombGunFAB250 1", "FuelTankGun_Tank75gal2", null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD" });

    weaponsRegister(localClass, "1xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null });

    weaponsRegister(localClass, "3xFAB100", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null });

    weaponsRegister(localClass, "1xFAB250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGunFAB250 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB250_1x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, "BombGunFAB250 1", "BombGunFAB250 1", "FuelTankGun_Tank75gal2", null, null, null, null });

    weaponsRegister(localClass, "3xFAB250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB250 1", "BombGunNull 1", "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGun250lbs 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null });

    weaponsRegister(localClass, "1x500", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGun500lbs 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1x500_2x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGun500lbs 1", null, null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null });

    weaponsRegister(localClass, "2x500_1x75", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, "BombGun500lbs 1", "BombGun500lbs 1", "FuelTankGun_Tank75gal2", null, null, null, null });

    weaponsRegister(localClass, "3x500", new String[] { "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGun500lbs 1", "BombGunNull 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}