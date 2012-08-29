package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class TA_152C0 extends FW_190
  implements TypeFighter
{
  public boolean bToFire;
  private float kangle;

  public TA_152C0()
  {
    this.bToFire = false;
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
    if (this.FM.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearL2a_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearR2a_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -f, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 15; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static Class _mthclass$(String paramString)
  {
    try
    {
      return Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ta.152");
    Property.set(localClass, "meshName", "3DO/Plane/Ta-152C0/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Ta-152C0.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTA_152C0.class });

    Property.set(localClass, "LOSElevation", 0.755F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalBomb02", "_ExternalBomb03" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 15;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 5; j < i; j++) {
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