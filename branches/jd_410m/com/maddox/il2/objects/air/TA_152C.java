package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class TA_152C extends FW_190
  implements TypeX4Carrier
{
  public boolean bToFire = false;
  private long tX4Prev = 0L;

  private float kangle = 0.0F;

  private float deltaAzimuth = 0.0F;
  private float deltaTangage = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveWheelSink() {
    resetYPRmodifier();
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearL2a_D0", xyz, ypr);
    xyz[1] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearR2a_D0", xyz, ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) || (!paramBoolean) || (!(this.FM instanceof Pilot)))
    {
      return;
    }
    Pilot localPilot = (Pilot)this.FM;
    if ((localPilot.get_maneuver() == 63) && (localPilot.target != null)) {
      Point3d localPoint3d = new Point3d(localPilot.target.Loc);
      localPoint3d.sub(this.FM.Loc);
      this.FM.Or.transformInv(localPoint3d);
      if (((localPoint3d.x > 4000.0D) && (localPoint3d.x < 5500.0D)) || ((localPoint3d.x > 100.0D) && (localPoint3d.x < 5000.0D) && (World.Rnd().nextFloat() < 0.33F)))
      {
        if (Time.current() > this.tX4Prev + 10000L) {
          this.bToFire = true;
          this.tX4Prev = Time.current();
        }
      }
    }
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 15; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public void typeX4CAdjSidePlus()
  {
    this.deltaAzimuth = 1.0F;
  }

  public void typeX4CAdjSideMinus() {
    this.deltaAzimuth = -1.0F;
  }

  public void typeX4CAdjAttitudePlus() {
    this.deltaTangage = 1.0F;
  }

  public void typeX4CAdjAttitudeMinus() {
    this.deltaTangage = -1.0F;
  }

  public void typeX4CResetControls() {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth() {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage() {
    return this.deltaTangage;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ta.152");
    Property.set(localClass, "meshName", "3DO/Plane/Ta-152C/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ta-152C.fmd");
    Property.set(localClass, "cockpitClass", CockpitTA_152C.class);
    Property.set(localClass, "LOSElevation", 0.755F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalBomb02", "_ExternalBomb03" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x4", new String[] { "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "PylonETC250", "PylonETC250", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", null, null });

    weaponsRegister(localClass, "2xSC250", new String[] { "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "PylonETC250", "PylonETC250", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}