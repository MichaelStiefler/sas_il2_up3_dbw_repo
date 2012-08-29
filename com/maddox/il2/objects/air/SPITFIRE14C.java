package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SPITFIRE14C extends SPITFIRE
  implements TypeBNZFighter
{
  private float flapps = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.0F, 0.6F, 0.0F, -95.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.2F, 1.0F, 0.0F, -95.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -75.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.09F, 0.0F, -75.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink() {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);

    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);

    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void moveCockpitDoor(float paramFloat) {
    resetYPRmodifier();
    try {
      if (this.FM.CT.bMoveSideDoor) {
        hierMesh().chunkSetAngles("Blister2_D0", 0.0F, 170.0F * paramFloat, 0.0F);
        return;
      }
    } catch (Throwable localThrowable) {
    }
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.55F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    float f = (float)Math.sin(Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 3.141593F));

    hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9.0F * f);
    hierMesh().chunkSetAngles("Head1_D0", 12.0F * f, 0.0F, 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void update(float paramFloat) {
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
    Class localClass = SPITFIRE14C.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Spit");
    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkXIVC(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SpitfireXIVC.fmd");

    Property.set(localClass, "cockpitClass", CockpitSpit14C.class);

    Property.set(localClass, "LOSElevation", 0.5926F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev01", "_ExternalBomb01" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 9;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "30gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit30New", 1);

      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "45gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit45New", 1);

      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "90gal";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankSpit90New", 1);

      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "250lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);

      for (j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 350);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunHispanoMkIk", 120);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "PylonSpitC", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);

      for (j = 9; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}