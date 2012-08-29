package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class RWD_8 extends Scheme1
  implements TypeScout, TypeTransport
{
  private static final float[] gearL2 = { 0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F };

  private static final float[] gearL4 = { 0.0F, 7.5F, 15.0F, 22.0F, 29.0F, 35.5F };

  private static final float[] gearL5 = { 0.0F, 1.5F, 4.0F, 7.5F, 10.0F, 11.5F };

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
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    float f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, floatindex(f, gearL2), 0.0F);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, floatindex(f, gearL4), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, floatindex(f, gearL5), 0.0F);
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    f = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -floatindex(f, gearL2), 0.0F);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, -floatindex(f, gearL4), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, -floatindex(f, gearL5), 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
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
      if (paramShot.chunkName.startsWith("Turret"))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
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

  public void doKillPilot(int paramInt)
  {
    if (paramInt == 1)
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    default:
      break;
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    if (f1 < -45.0F)
    {
      f1 = -45.0F;
      bool = false;
    }
    if (f1 > 45.0F)
    {
      f1 = 45.0F;
      bool = false;
    }
    if (f2 < -45.0F)
    {
      f2 = -45.0F;
      bool = false;
    }
    if (f2 > 20.0F)
    {
      f2 = 20.0F;
      bool = false;
    }
    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Class localClass = RWD_8.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "RWD-8");
    Property.set(localClass, "meshName", "3do/plane/RWD-8/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1956.0F);
    Property.set(localClass, "FlightModel", "FlightModels/RWD_8.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitRWD_8.class });

    Property.set(localClass, "LOSElevation", 1.01885F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 20;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303k", 0);
      for (int j = 1; j < i; j++) {
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