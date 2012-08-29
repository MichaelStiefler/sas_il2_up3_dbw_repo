package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class BF_109G8_DZZ extends BF_109
  implements TypeBNZFighter, TypeScout
{
  private float kangle;

  public BF_109G8_DZZ()
  {
    this.kangle = 0.0F;
  }

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F)
    {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }
    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator());
    if (this.kangle > 1.0F)
      this.kangle = 1.0F;
    super.update(paramFloat);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D0", false);
      else
        hierMesh().chunkVisible("CF_D0", true);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D1", false);
      hierMesh().chunkVisible("CF_D2", false);
      hierMesh().chunkVisible("CF_D3", false);
    }
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("Blister1_D0", false);
      else
        hierMesh().chunkVisible("Blister1_D0", true);
      Point3d localPoint3d = World.getPlayerAircraft().pos.getAbsPoint();
      if (localPoint3d.z - World.land().HQ(localPoint3d.x, localPoint3d.y) < 0.009999999776482582D)
        hierMesh().chunkVisible("CF_D0", true);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.bIsAboutToBailout)
        hierMesh().chunkVisible("Blister1_D0", false);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if ((paramFloat <= f1) || (paramFloat == 1.0F))
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1)
    {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F)
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
    if (paramFloat < 0.01F)
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat)
  {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;
    if ((paramFloat <= f1) || (paramFloat == 1.0F))
    {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;
    if (paramFloat >= 1.0F - f1)
    {
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F)
    {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = BF_109G8_DZZ.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3do/plane/Bf-109G-6Late/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-109G-6Late.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_109G6LATE.class });

    Property.set(localClass, "LOSElevation", 0.7498F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      for (int j = 5; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1H";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R3";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R6";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1R6";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1R6H";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R3R6";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200);
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "mgonly";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      for (int k = 5; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1v2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1Hv2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R3v2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R6v2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1R6v2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R1R6Hv2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "R3R6v2";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135);
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonETC900", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonMG15120", 1);
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int m = 0; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}