package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class FW_190G8 extends FW_190
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
    Class localClass = FW_190G8.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190G-8/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/fw-190a-8_1_65Ata.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitFW_190F8.class });

    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 9;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      for (int j = 9; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "4sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[8] = null;
      for (int k = 9; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      for (int m = 9; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc2504sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      for (int n = 9; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1ab250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1);
      for (int i1 = 9; i1 < i; i1++) {
        arrayOf_WeaponSlot[i1] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i2 = 9; i2 < i; i2++) {
        arrayOf_WeaponSlot[i2] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc5004sc50";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i3 = 9; i3 < i; i3++) {
        arrayOf_WeaponSlot[i3] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc5002sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
      for (int i4 = 9; i4 < i; i4++) {
        arrayOf_WeaponSlot[i4] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1ab500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1);
      for (int i5 = 9; i5 < i; i5++) {
        arrayOf_WeaponSlot[i5] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sd500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSD500", 1);
      for (int i6 = 9; i6 < i; i6++) {
        arrayOf_WeaponSlot[i6] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC1000", 1);
      for (int i7 = 9; i7 < i; i7++) {
        arrayOf_WeaponSlot[i7] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "1sc10002sc250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGs", 200);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC1000", 1);
      for (int i8 = 9; i8 < i; i8++) {
        arrayOf_WeaponSlot[i8] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int i9 = 0; i9 < i; i9++) {
        arrayOf_WeaponSlot[i9] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}