package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class Breda65 extends Scheme1
  implements TypeScout, TypeBomber, TypeStormovik
{
  public static boolean bChangedPit = false;
  private float flapps;

  public Breda65()
  {
    this.flapps = 0.0F;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

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

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    }
    else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      int i = 0;
      int j;
      if (paramString.endsWith("a"))
      {
        i = 1;
        j = paramString.charAt(6) - '1';
      }
      else if (paramString.endsWith("b"))
      {
        i = 2;
        j = paramString.charAt(6) - '1';
      }
      else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    if (paramArrayOfFloat[0] < -142.0F)
    {
      paramArrayOfFloat[0] = -142.0F;
      bool = false;
    }
    else if (paramArrayOfFloat[0] > 142.0F)
    {
      paramArrayOfFloat[0] = 142.0F;
      bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F)
    {
      paramArrayOfFloat[1] = 45.0F;
      bool = false;
    }
    if (!bool)
      return false;
    float f = Math.abs(paramArrayOfFloat[0]);
    if ((f < 2.5F) && (paramArrayOfFloat[1] < 20.799999F))
    {
      paramArrayOfFloat[1] = 20.799999F;
      return false;
    }
    if ((f < 21.0F) && (paramArrayOfFloat[1] < 16.1F))
    {
      paramArrayOfFloat[1] = 16.1F;
      return false;
    }
    if ((f < 41.0F) && (paramArrayOfFloat[1] < -8.5F))
    {
      paramArrayOfFloat[1] = -8.5F;
      return false;
    }
    if ((f < 103.0F) && (paramArrayOfFloat[1] < -45.0F))
    {
      paramArrayOfFloat[1] = -45.0F;
      return false;
    }
    if ((f < 180.0F) && (paramArrayOfFloat[1] < -7.8F))
    {
      paramArrayOfFloat[1] = -7.8F;
      return false;
    }

    return true;
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127k", 750);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127k", 750);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(10, "MGunBredaSAFATt127st", 250);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = Breda65.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Breda");
    Property.set(localClass, "meshName", "3DO/Plane/R-10/Breda65bis.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryItaly);
    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Breda65.fmd");
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 5;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      for (int j = 3; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "8XSC50";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC50", 4);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "6XSC70";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(3, "BombGunSC70", 3);
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