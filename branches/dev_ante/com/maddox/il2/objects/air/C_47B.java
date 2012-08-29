package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class C_47B extends Scheme2
  implements TypeTransport, TypeBomber
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -120.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -120.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("WingLOut")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 6.0D))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingROut")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 6.0D))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, 1);
    if ((paramShot.chunkName.startsWith("WingLIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 1.940000057220459D))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 1.940000057220459D))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Nose")) && (Aircraft.Pd.jdField_x_of_type_Double > 4.900000095367432D) && (Aircraft.Pd.jdField_z_of_type_Double > -0.09000000357627869D) && (World.Rnd().nextFloat() < 0.1F))
      if (Aircraft.Pd.jdField_y_of_type_Double > 0.0D)
      {
        killPilot(paramShot.initiator, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
      }
      else {
        killPilot(paramShot.initiator, 1);
      }
    if ((paramShot.chunkName.startsWith("Tail")) && (Aircraft.Pd.jdField_x_of_type_Double < -5.800000190734863D) && (Aircraft.Pd.jdField_x_of_type_Double > -6.789999961853027D) && (Aircraft.Pd.jdField_z_of_type_Double > -0.449D) && (Aircraft.Pd.jdField_z_of_type_Double < 0.1239999979734421D))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, World.Rnd().nextInt(3, 4), (int)(paramShot.mass * 1000.0F * World.Rnd().nextFloat(0.9F, 1.1F)));
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 2) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 2) && (World.Rnd().nextInt(0, 99) < 33))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
    super.msgShot(paramShot);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
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
    for (int i = 1; i < 3; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    default:
      break;
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      break;
    case 35:
      if (World.Rnd().nextFloat() >= 0.25F) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, World.Rnd().nextInt(2, 6)); break;
    case 38:
      if (World.Rnd().nextFloat() >= 0.25F) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, World.Rnd().nextInt(2, 6));
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

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
    Class localClass = C_47B.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Skytrain");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/C-47(USA)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/C-47(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/C-47(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar04());
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 2999.8999F);
    Property.set(localClass, "FlightModel", "FlightModels/C-47B.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitC47.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_BombSpawn01" });

    ArrayList localArrayList = new ArrayList();
    Property.set(localClass, "weaponsList", localArrayList);
    HashMapInt localHashMapInt = new HashMapInt();
    Property.set(localClass, "weaponsMap", localHashMapInt);
    int i = 1;
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot1 = new Aircraft._WeaponSlot[i];
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot2;
    int j;
    try {
      String str1 = "default";
      arrayOf_WeaponSlot2 = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot2[0] = null;
      for (j = 1; j < i; j++) {
        arrayOf_WeaponSlot2[j] = null;
      }
      localArrayList.add(str1);
      localHashMapInt.put(Finger.Int(str1), arrayOf_WeaponSlot2);
    }
    catch (Exception localException1)
    {
      System.out.println("Error C-47B Weapons 'default ' NOT initialized:" + localException1.getMessage());
    }
    try
    {
      String str2 = "18xPara";
      arrayOf_WeaponSlot2 = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot2[0] = new Aircraft._WeaponSlot(3, "BombGunPara", 18);
      for (j = 1; j < i; j++) {
        arrayOf_WeaponSlot2[j] = null;
      }
      localArrayList.add(str2);
      localHashMapInt.put(Finger.Int(str2), arrayOf_WeaponSlot2);
    }
    catch (Exception localException2)
    {
      System.out.println("Error C-47B Weapons '18xPara ' NOT initialized:" + localException2.getMessage());
    }
    try
    {
      String str3 = "5xCargoA";
      arrayOf_WeaponSlot2 = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot2[0] = new Aircraft._WeaponSlot(3, "BombGunCargoA", 5);
      for (j = 1; j < i; j++) {
        arrayOf_WeaponSlot2[j] = null;
      }
      localArrayList.add(str3);
      localHashMapInt.put(Finger.Int(str3), arrayOf_WeaponSlot2);
    }
    catch (Exception localException3)
    {
      System.out.println("Error C-47B Weapons '5xCargoA' NOT initialized:" + localException3.getMessage());
    }
    try
    {
      String str4 = "none";
      arrayOf_WeaponSlot2 = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot2[0] = null;
      for (j = 1; j < i; j++) {
        arrayOf_WeaponSlot2[j] = null;
      }
      localArrayList.add(str4);
      localHashMapInt.put(Finger.Int(str4), arrayOf_WeaponSlot2);
    }
    catch (Exception localException4)
    {
      System.out.println("Error C-47B Weapons 'none ' NOT initialized:" + localException4.getMessage());
    }
  }
}