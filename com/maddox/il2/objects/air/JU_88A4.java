package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class JU_88A4 extends JU_88
  implements TypeBomber, TypeDiveBomber, TypeScout
{
  public static boolean bChangedPit = false;
  public int diveMechStage;
  public boolean bNDives;
  private boolean bDropsBombs;
  private long dropStopTime;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;
  public float fDiveRecoveryAlt;
  public float fDiveVelocity;
  public float fDiveAngle;

  public JU_88A4()
  {
    this.diveMechStage = 0;
    this.bNDives = false;
    this.bDropsBombs = false;
    this.dropStopTime = -1L;
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 850.0F;
    this.fSightCurSpeed = 150.0F;
    this.fSightCurReadyness = 0.0F;
    this.fDiveRecoveryAlt = 850.0F;
    this.fDiveVelocity = 150.0F;
    this.fDiveAngle = 70.0F;
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -85.0F * paramFloat, 0.0F);
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

  public void update(float paramFloat)
  {
    updateJU87D5(paramFloat);
    updateJU87(paramFloat);
    super.update(paramFloat);
    if ((Pitot.Indicator((float)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed()) > 70.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.01D) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl != 0.0F))
    {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
      Actor localActor = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.actor;
      World.cur();
      if (localActor == World.getPlayerAircraft())
        HUD.log("FlapsRaised");
    }
  }

  public void updateJU87(float paramFloat)
  {
    if ((this == World.getPlayerAircraft()) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)))
      if (((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isRealMode())
      {
        switch (this.diveMechStage)
        {
        case 0:
          if ((this.bNDives) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl == 1.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double > this.fDiveRecoveryAlt))
          {
            this.diveMechStage += 1;
            this.bNDives = false;
          }
          else {
            this.bNDives = (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl != 1.0F);
          }
          break;
        case 1:
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(-0.25F);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator = -0.25F;
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl != 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] == 0) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] == null) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].countBullets() != 0)))
            break;
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl == 0.0F)
            this.diveMechStage += 1;
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] == null) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].countBullets() != 0)) break;
          this.diveMechStage += 1; break;
        case 2:
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(0.95F);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator = 0.95F;
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl != 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() <= 0.0F)) break;
          this.diveMechStage += 1; break;
        case 3:
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(0.0F);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator = 0.0F;
          this.diveMechStage = 0;
        }
      }
      else
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setTrimElevatorControl(0.0F);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator = 0.0F;
      }
    if ((this.bDropsBombs) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(3, 0)) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets()))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
  }

  public void updateJU87D5(float paramFloat)
  {
    this.fDiveAngle = (-this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());
    if (this.fDiveAngle > 89.0F)
      this.fDiveAngle = 89.0F;
    if (this.fDiveAngle < 10.0F)
      this.fDiveAngle = 10.0F;
  }

  protected void moveAirBrake(float paramFloat)
  {
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
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
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));
    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset()
  {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F)
      this.fSightCurSideslip = 3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F)
      this.fSightCurSideslip = -3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 850.0F;
    typeDiveBomberAdjAltitudeReset();
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    typeDiveBomberAdjAltitudePlus();
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 500.0F)
      this.fSightCurAltitude = 500.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    typeDiveBomberAdjAltitudeMinus();
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 150.0F;
    typeDiveBomberAdjVelocityReset();
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 700.0F)
      this.fSightCurSpeed = 700.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });

    typeDiveBomberAdjVelocityPlus();
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });

    typeDiveBomberAdjVelocityMinus();
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) > 4.5D)
    {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    }
    else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F)
      {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));
      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F))
        this.bSightBombDump = true;
      if (this.bSightBombDump)
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(3, 0))
        {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets()))
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));
    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);
    this.fSightCurAltitude = (this.fDiveRecoveryAlt = paramNetMsgInput.readFloat());
    this.fSightCurSpeed = (this.fDiveVelocity = paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
    this.fDiveRecoveryAlt += 10.0F;
    if (this.fDiveRecoveryAlt > 10000.0F)
      this.fDiveRecoveryAlt = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fDiveRecoveryAlt) });
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
    this.fDiveRecoveryAlt -= 10.0F;
    if (this.fDiveRecoveryAlt < 500.0F)
      this.fDiveRecoveryAlt = 500.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fDiveRecoveryAlt) });
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
    this.fDiveVelocity += 10.0F;
    if (this.fDiveVelocity > 700.0F)
      this.fDiveVelocity = 700.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fDiveVelocity) });
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
    this.fDiveVelocity -= 10.0F;
    if (this.fDiveVelocity < 150.0F)
      this.fDiveVelocity = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fDiveVelocity) });
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1600.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    f = paramFloat >= 0.5F ? Math.abs(Math.min(1.0F - paramFloat, 0.1F)) : Math.abs(Math.min(paramFloat, 0.1F));
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 1200.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -1200.0F * f, 0.0F);
    if (paramFloat < 0.5F)
    {
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 900.0F * f, 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(paramFloat, -65.0F, 65.0F, 65.0F, -65.0F), 0.0F);
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
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ju-88");
    Property.set(localClass, "meshName", "3DO/Plane/Ju-88A-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-88A-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_88A4.class, CockpitJU_88A4_Bombardier.class, CockpitJU_88A4_Bombardier2.class, CockpitJU_88A4_NGunner.class, CockpitJU_88A4_RGunner.class, CockpitJU_88A4_BGunner.class });

    Property.set(localClass, "LOSElevation", 1.0976F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 13, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "28xSC50", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC50 14", "BombGunSC50 14" });

    Aircraft.weaponsRegister(localClass, "28xSC50_2xSC250", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 14", "BombGunSC50 14" });

    Aircraft.weaponsRegister(localClass, "18xSC50_2xSC500", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, "BombGunSC500 1", "BombGunSC500 1", "BombGunSC50 14", "BombGunSC50 14" });

    Aircraft.weaponsRegister(localClass, "10xSC50", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC50 5", "BombGunSC50 5" });

    Aircraft.weaponsRegister(localClass, "10xSC50_2xSC250", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 5", "BombGunSC50 5" });

    Aircraft.weaponsRegister(localClass, "4xSC250", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xSC500", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xSC500", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xAB500", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB500 1", "BombGunAB500 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xAB500", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB500 1", "BombGunAB500 1", "BombGunAB500 1", "BombGunAB500 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xSC1000", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC1000 1", "BombGunSC1000 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xAB1000", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB1000 1", "BombGunAB1000 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xSC1800", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC1800 1", "BombGunSC1800 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xSC2000", new String[] { "MGunMG81t 375", "MGunMG81t 0", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC2000 1", "BombGunSC2000 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { "MGunMG81t 000", "MGunMG81t 000", "MGunMG81t 000", null, null, null, null, null, null, null, null });
  }
}