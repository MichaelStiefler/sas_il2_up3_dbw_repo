package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class LI_2 extends Scheme2
  implements TypeTransport, TypeBomber
{
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurForwardAngle;
  public float fSightSetForwardAngle;
  public float fSightCurSideslip;

  public LI_2()
  {
    this.fSightCurAltitude = 300.0F;
    this.fSightCurSpeed = 50.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightSetForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
  }

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
    if (paramShot.chunkName.startsWith("Turret1"))
    {
      killPilot(paramShot.initiator, 2);
      if ((Aircraft.Pd.jdField_z_of_type_Double > 1.335000038146973D) && (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade()))
        HUD.logCenter("H E A D S H O T");
    }
    else if (paramShot.chunkName.startsWith("Turret2")) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
    }
    else if (paramShot.chunkName.startsWith("Turret3"))
    {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
    }
    else {
      if ((paramShot.chunkName.startsWith("Tail")) && (Aircraft.Pd.jdField_x_of_type_Double < -5.800000190734863D) && (Aircraft.Pd.jdField_x_of_type_Double > -6.789999961853027D) && (Aircraft.Pd.jdField_z_of_type_Double > -0.449D) && (Aircraft.Pd.jdField_z_of_type_Double < 0.1239999979734421D))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, World.Rnd().nextInt(3, 4), (int)(paramShot.mass * 1000.0F * World.Rnd().nextFloat(0.9F, 1.1F)));
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 2) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 2) && (World.Rnd().nextInt(0, 99) < 33))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
      super.msgShot(paramShot);
    }
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
    case 1:
    }
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
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    for (int i = 1; i < 4; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    default:
      break;
    case 0:
      if (f2 < -5.0F)
      {
        f2 = -5.0F;
        bool = false;
      }
      if (f2 <= 45.0F)
        break;
      f2 = 45.0F;
      bool = false; break;
    case 1:
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
      if (f2 < -30.0F)
      {
        f2 = -30.0F;
        bool = false;
      }
      if (f2 <= 60.0F)
        break;
      f2 = 60.0F;
      bool = false; break;
    case 2:
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
      if (f2 < -30.0F)
      {
        f2 = -30.0F;
        bool = false;
      }
      if (f2 <= 60.0F)
        break;
      f2 = 60.0F;
      bool = false; break;
    case 3:
      if (f1 < -5.0F)
      {
        f1 = -5.0F;
        bool = false;
      }
      if (f1 > 5.0F)
      {
        f1 = 5.0F;
        bool = false;
      }
      if (f2 < -5.0F)
      {
        f2 = -5.0F;
        bool = false;
      }
      if (f2 <= 5.0F)
        break;
      f2 = 5.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
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

  public void typeBomberAdjDistanceReset()
  {
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 0.2F;
    if (this.fSightCurForwardAngle > 75.0F)
      this.fSightCurForwardAngle = 75.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 0.2F;
    if (this.fSightCurForwardAngle < -15.0F)
      this.fSightCurForwardAngle = -15.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip += 1.0F;
    if (this.fSightCurSideslip > 45.0F)
      this.fSightCurSideslip = 45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 1.0F;
    if (this.fSightCurSideslip < -45.0F)
      this.fSightCurSideslip = -45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 300.0F)
      this.fSightCurAltitude = 300.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 650.0F)
      this.fSightCurSpeed = 650.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F)
      this.fSightCurSpeed = 50.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);
    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.fSightCurAltitude));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
    paramNetMsgGuaranted.writeFloat(this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readFloat();
    this.fSightCurSideslip = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = LI_2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Douglas");
    Property.set(localClass, "meshName", "3do/plane/Li-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Li-2.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitLI2.class, CockpitLI2_Bombardier.class, CockpitLI2_TGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xFAB250", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", null });

    Aircraft.weaponsRegister(localClass, "12xPara", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunPara 12" });

    Aircraft.weaponsRegister(localClass, "5xCargoA", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunCargoA 5" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}