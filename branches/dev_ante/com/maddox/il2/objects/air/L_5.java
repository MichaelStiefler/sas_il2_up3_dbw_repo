package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class L_5 extends Scheme1
  implements TypeScout, TypeTransport, TypeFighter
{
  private static final float[] gearL2 = { 0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F };

  public static boolean bChangedPit = false;

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1))
    {
      hierMesh().chunkVisible("GearL1_D0", false);
      hierMesh().chunkVisible("GearL11_D0", true);
      hierMesh().chunkVisible("GearR1_D0", false);
      hierMesh().chunkVisible("GearR11_D0", true);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasBrakeControl = false;
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);
      float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage(), -30.0F, 30.0F, -30.0F, 30.0F);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.9F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F))
      {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
          hierMesh().chunkSetAngles("GearL11_D0", World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F) - f);
        else
          hierMesh().chunkSetAngles("GearL11_D0", 0.0F, 0.0F, -f);
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
          hierMesh().chunkSetAngles("GearR11_D0", World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F) - f);
        else
          hierMesh().chunkSetAngles("GearR11_D0", 0.0F, 0.0F, -f);
      }
      else {
        hierMesh().chunkSetAngles("GearL11_D0", 0.0F, 0.0F, -f);
        hierMesh().chunkSetAngles("GearR11_D0", 0.0F, 0.0F, -f);
      }
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 0.5F);
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, floatindex(f, gearL2), 0.0F);
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -floatindex(f, gearL2), 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 30.0F * paramFloat, 0.0F, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 0.0F, -30.0F * paramFloat);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 0.0F, -30.0F * paramFloat);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 0.0F, 30.0F * paramFloat);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 0.0F, -30.0F * paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, 0.0F, -f);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, 0.0F, -f);
  }

  private float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1)
      return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0)
      return paramArrayOfFloat[0];
    if (i == 0)
    {
      if (paramFloat > 0.0F) {
        return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      }
      return paramArrayOfFloat[0];
    }

    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("WingLMid")) && (World.Rnd().nextFloat(0.0F, 0.121F) < paramShot.mass))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, (int)(1.0F + paramShot.mass * 18.950001F * 2.0F));
    if ((paramShot.chunkName.startsWith("WingRMid")) && (World.Rnd().nextFloat(0.0F, 0.121F) < paramShot.mass))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, (int)(1.0F + paramShot.mass * 18.950001F * 2.0F));
    if (paramShot.chunkName.startsWith("Engine"))
    {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < paramShot.mass)
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
      if ((Aircraft.v1.jdField_z_of_type_Double > 0.0D) && (World.Rnd().nextFloat() < 0.12F))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(paramShot.initiator, 0);
        if (paramShot.mass > 0.1F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 5);
      }
      if ((Aircraft.v1.x < 0.1000000014901161D) && (World.Rnd().nextFloat() < 0.57F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitOil(paramShot.initiator, 0);
    }
    if (paramShot.chunkName.startsWith("Pilot1"))
    {
      killPilot(paramShot.initiator, 0);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
      if ((Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade()))
        HUD.logCenter("H E A D S H O T");
    }
    else if (paramShot.chunkName.startsWith("Pilot2"))
    {
      killPilot(paramShot.initiator, 1);
      if ((Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade()))
        HUD.logCenter("H E A D S H O T");
    }
    else {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] == 4) && (World.Rnd().nextInt(0, 99) < 33))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
      super.msgShot(paramShot);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 34:
      return super.cutFM(35, paramInt2, paramActor);
    case 37:
      return super.cutFM(38, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Head2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
    }
  }

  static
  {
    Class localClass = L_5.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "L-5");
    Property.set(localClass, "meshName", "3DO/Plane/Sentinel/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/Sentinel(US)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar04());
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
    Property.set(localClass, "yearService", 1940.9F);
    Property.set(localClass, "yearExpired", 1955.3F);
    Property.set(localClass, "FlightModel", "FlightModels/L5Sentinel.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitL_5.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 9, 9, 9, 9, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 38;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 6; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x2.36in_rockets";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(9, "Pylon_L5_Rk", 1);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(9, "Pylon_L5_Rk", 1);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "RocketGun4andHalfInch", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "2x2.36in_rocket_markers";
      arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(9, "Pylon_L5_Rk", 1);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(9, "Pylon_L5_Rk", 1);
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "RocketGunWPL5", 1);
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(2, "RocketGunWPL5", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
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