package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class HE_219UHU extends HE_219
{
  private boolean bKeelUp;

  public HE_219UHU()
  {
    this.bKeelUp = true;
  }

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, -10.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, -10.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    for (int i = 2; i < 8; i++) {
      hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xxcannon04"))
    {
      debuggunnery("Armament System: Left Wing Cannon: Disabled..");
      this.FM.AS.setJamBullets(1, 1);
      getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
      return;
    }
    if (paramString.startsWith("xxcannon05"))
    {
      debuggunnery("Armament System: Right Wing Cannon: Disabled..");
      this.FM.AS.setJamBullets(1, 2);
      getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
      return;
    }

    super.hitBone(paramString, paramShot, paramPoint3d);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "He219");
    Property.set(localClass, "meshName", "3DO/Plane/He-219Uhu/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/He-219.fmd:HE219UHU");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitHE_219.class });

    Property.set(localClass, "LOSElevation", 1.00705F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN02", "_MGUN03", "_MGUN01", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn01", "_BombSpawn03", "_BombSpawn01", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 18;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120si", 300);
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
      for (int j = 18; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xMG151and2xMk103";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMK103ki", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMK103ki", 300);
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
      for (int k = 18; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2xMG151and2xMk108";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG15120si", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 300);
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
      for (int m = 18; m < i; m++) {
        arrayOf_WeaponSlot[m] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int n = 0; n < i; n++) {
        arrayOf_WeaponSlot[n] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}