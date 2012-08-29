package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class Swordfish1 extends Swordfish
{
  public boolean typeBomberToggleAutomation()
  {
    return false;
  }
  public void typeBomberAdjDistanceReset() {
  }
  public void typeBomberAdjDistancePlus() {
  }
  public void typeBomberAdjDistanceMinus() {
  }
  public void typeBomberAdjSideslipReset() {
  }
  public void typeBomberAdjSideslipPlus() {
  }
  public void typeBomberAdjSideslipMinus() {
  }
  public void typeBomberAdjAltitudeReset() {
  }
  public void typeBomberAdjAltitudePlus() {
  }
  public void typeBomberAdjAltitudeMinus() {
  }
  public void typeBomberAdjSpeedReset() {
  }
  public void typeBomberAdjSpeedPlus() {
  }
  public void typeBomberAdjSpeedMinus() {
  }
  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Swordfish");

    Property.set(localClass, "meshName", "3DO/Plane/Swordfish1(multi)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());

    Property.set(localClass, "meshName_gb", "3DO/Plane/Swordfish1(gb)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar02f());

    Property.set(localClass, "meshName_rn", "3DO/Plane/Swordfish1(gb)/hier.him");

    Property.set(localClass, "PaintScheme_rn", new PaintSchemeBMPar02f());

    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Swordfish.fmd");
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);

    Property.set(localClass, "cockpitClass", new Class[] { CockpitSwordfish.class, CockpitSwordfish_TAG.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_turret1", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb01", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalBomb27", "_ExternalBomb28", "_ExternalBomb29", "_ExternalBomb30", "_ExternalBomb31", "_ExternalBomb32", "_ExternalBomb33" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 35;

      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);

      for (int j = 3; j < 25; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "1_1xTorpedo";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
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
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(3, "BombGunTorpMk13", 1);
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "2_3x500lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
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
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "3_1x500lb+4x250lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
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
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4_1x500lb+8x100lb";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
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
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(3, "BombGun50kg", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "5_3x500lb+8xflare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6_1xtorpedo+8xflare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[24] = new Aircraft._WeaponSlot(3, "BombGunTorpMk13", 1);
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;

      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "7_1x500lb+4x250lb+8xflare";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[20] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[21] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[22] = new Aircraft._WeaponSlot(2, "BombGunNull", 1);
      arrayOf_WeaponSlot[23] = new Aircraft._WeaponSlot(2, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "8_1x500lb+8xFlare_AI";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunVikkersKs", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(10, "MGunVikkersKt", 300);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
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
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      arrayOf_WeaponSlot[26] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
      arrayOf_WeaponSlot[27] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[28] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[29] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[30] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[31] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[32] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[33] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      arrayOf_WeaponSlot[34] = new Aircraft._WeaponSlot(3, "BombGunParaFlareUK", 1);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      for (j = 0; j < 25; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}