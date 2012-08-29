package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class FW_190A9165ATA extends FW_190
  implements TypeFighter, TypeBNZFighter
{
  public void update(float paramFloat)
  {
    afterburnerhud();
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  protected void afterburnerhud()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlAfterburner()))
    {
      HUD.logRightBottom("Erhöhte Notleistung ENABLED!");
    }
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = FW_190A9165ATA.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190A-9(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/fw-190a-9_1_65Ata.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190A8.class });

    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 3, 9, 9, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 9, 9, 1, 1, 9, 9, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalDev02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev05", "_ExternalDev06", "_CANNON09", "_CANNON10", "_ExternalDev07", "_ExternalDev08", "_CANNON11", "_CANNON12", "_ExternalDev09", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 27;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      for (int j = 27; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      for (int k = 27; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r14mg15120";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "PylonMG15120x2", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(9, "PylonMG15120x2", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(1, "MGunMG15120MGkh", 140);
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      for (int m = 27; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r22mk108";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(1, "MGunMK108kh", 55);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(1, "MGunMK108kh", 55);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      for (int n = 27; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r32mk103";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
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
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(9, "PylonMk103", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(9, "PylonMk103", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 35);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(1, "MGunMK103kh", 35);
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      for (int i1 = 27; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "r62wfrgr21";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 475);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunMG15120MGs", 250);
      arrayOf_WeaponSlot[4] = null;
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
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21", 1);
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
      for (int i2 = 27; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "None";
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