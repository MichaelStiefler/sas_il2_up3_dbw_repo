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
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class TA_152C3 extends FW_190
  implements TypeX4Carrier, TypeStormovik
{
  public boolean bToFire;
  private long tX4Prev;
  private float kangle;
  private float deltaAzimuth;
  private float deltaTangage;

  public TA_152C3()
  {
    this.bToFire = false;
    this.tX4Prev = 0L;
    this.kangle = 0.0F;
    this.deltaAzimuth = 0.0F;
    this.deltaTangage = 0.0F;
  }

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

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearL2a_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearR2a_D0", Aircraft.xyz, Aircraft.ypr);
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
    if ((((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) && (((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode())) || (!paramBoolean) || (!(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)))
      return;
    Pilot localPilot = (Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
    if ((localPilot.get_maneuver() == 63) && (localPilot.jdField_target_of_type_ComMaddoxIl2FmFlightModel != null))
    {
      Point3d localPoint3d = new Point3d(localPilot.jdField_target_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      localPoint3d.sub(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.transformInv(localPoint3d);
      if (((localPoint3d.jdField_x_of_type_Double > 4000.0D) && (localPoint3d.jdField_x_of_type_Double < 5500.0D)) || ((localPoint3d.jdField_x_of_type_Double > 100.0D) && (localPoint3d.jdField_x_of_type_Double < 5000.0D) && (World.Rnd().nextFloat() < 0.33F) && (Time.current() > this.tX4Prev + 10000L)))
      {
        this.bToFire = true;
        this.tX4Prev = Time.current();
      }
    }
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 15; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public void typeX4CAdjSidePlus()
  {
    this.deltaAzimuth = 1.0F;
  }

  public void typeX4CAdjSideMinus()
  {
    this.deltaAzimuth = -1.0F;
  }

  public void typeX4CAdjAttitudePlus()
  {
    this.deltaTangage = 1.0F;
  }

  public void typeX4CAdjAttitudeMinus()
  {
    this.deltaTangage = -1.0F;
  }

  public void typeX4CResetControls()
  {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth()
  {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage()
  {
    return this.deltaTangage;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ta.152");
    Property.set(localClass, "meshName", "3DO/Plane/Ta-152C3/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Ta-152C3.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTA_152C3.class });

    Property.set(localClass, "LOSElevation", 0.755F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 3, 3, 9, 9, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb04" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 17;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int j = 17; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "SC250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[16] = null;
      for (int k = 17; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "AB250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      arrayOf_WeaponSlot[16] = null;
      for (int m = 17; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "SC500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[16] = null;
      for (int n = 17; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "droptank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonETC501FW190", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int i1 = 17; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xX4";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK103ki", 70);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG151s", 200);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonETC250", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "RocketGunX4", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      for (int i2 = 17; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i3 = 0; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}