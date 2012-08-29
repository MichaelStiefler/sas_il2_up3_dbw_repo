package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SPITFIRE2A extends SPITFIRE
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      bChangedPit = true;
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.55F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    float f = (float)Math.sin(Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 3.141593F));
    hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9.0F * f);
    hierMesh().chunkSetAngles("Head1_D0", 12.0F * f, 0.0F, 0.0F);
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  static
  {
    Class localClass = SPITFIRE2A.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Spit");
    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkIIa(Multi1)/Spitfire2a.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SpitfireIIa.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitSPIT2.class });

    Property.set(localClass, "LOSElevation", 0.5926F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN07", "_MGUN08", "_MGUN10", "_MGUN11" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 300);
      for (int j = 8; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int k = 0; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}