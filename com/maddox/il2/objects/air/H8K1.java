package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class H8K1 extends H8K
  implements TypeBomber
{
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;

  public H8K1()
  {
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 850.0F;
    this.fSightCurSpeed = 150.0F;
    this.fSightCurReadyness = 0.0F;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3)
      {
        if (World.Rnd().nextFloat() < 0.02F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
        if (World.Rnd().nextFloat() < 0.02F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      }
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3)
      {
        if (World.Rnd().nextFloat() < 0.02F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
        if (World.Rnd().nextFloat() < 0.02F)
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);
      }
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[1] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[2] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[3] > 5) && (World.Rnd().nextFloat() < 0.02F))
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);
    }
    int i;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
    {
      for (i = 1; i < 7; i++) {
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      }
    }
    else
      for (i = 1; i < 7; i++)
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot1_D0"));
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
      if (f1 < -35.0F)
      {
        f1 = -35.0F;
        bool = false;
      }
      if (f1 > 35.0F)
      {
        f1 = 35.0F;
        bool = false;
      }
      if (f2 < -25.0F)
      {
        f2 = -25.0F;
        bool = false;
      }
      if (f2 <= 25.0F)
        break;
      f2 = 25.0F;
      bool = false; break;
    case 1:
      if (f1 < -22.0F)
      {
        f1 = -22.0F;
        bool = false;
      }
      if (f1 > 22.0F)
      {
        f1 = 22.0F;
        bool = false;
      }
      if (f2 < -57.0F)
      {
        f2 = -57.0F;
        bool = false;
      }
      if (f2 <= 33.0F)
        break;
      f2 = 33.0F;
      bool = false; break;
    case 2:
      if (f1 < -22.0F)
      {
        f1 = -22.0F;
        bool = false;
      }
      if (f1 > 22.0F)
      {
        f1 = 22.0F;
        bool = false;
      }
      if (f2 < -57.0F)
      {
        f2 = -57.0F;
        bool = false;
      }
      if (f2 <= 33.0F)
        break;
      f2 = 33.0F;
      bool = false; break;
    case 3:
      if (f2 < 0.0F)
      {
        f2 = 0.0F;
        bool = false;
      }
      if (f2 <= 50.0F)
        break;
      f2 = 50.0F;
      bool = false; break;
    case 4:
      if (f1 < -25.0F)
      {
        f1 = -25.0F;
        bool = false;
      }
      if (f1 > 25.0F)
      {
        f1 = 25.0F;
        bool = false;
      }
      if (f2 < -25.0F)
      {
        f2 = -25.0F;
        bool = false;
      }
      if (f2 <= 25.0F)
        break;
      f2 = 25.0F;
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
    case 33:
      hitProp(0, paramInt2, paramActor);
      break;
    case 36:
      hitProp(1, paramInt2, paramActor);
      break;
    case 34:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 0, 2); break;
    case 37:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 2);
      if (World.Rnd().nextInt(0, 99) >= 66) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, 1, 2); break;
    case 19:
      killPilot(paramActor, 6);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    if (paramInt <= 5)
    {
      hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
      hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
      hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
      hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
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
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F)
      this.fSightCurAltitude = 850.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 600.0F)
      this.fSightCurSpeed = 600.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getKren()) > 4.5D)
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
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  static
  {
    Class localClass = H8K1.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "H8K");
    Property.set(localClass, "meshName", "3DO/Plane/H8K1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar04());
    Property.set(localClass, "meshName_ja", "3DO/Plane/H8K1(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 2048.0F);
    Property.set(localClass, "FlightModel", "FlightModels/H8K1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitH8K1.class, CockpitH8K1_Bombardier.class, CockpitH8K1_NGunner.class, CockpitH8K1_AGunner.class, CockpitH8K1_TGunner.class });

    Property.set(localClass, "LOSElevation", 1.4078F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "16x60", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, null, null, null, null, null, "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ" });

    Aircraft.weaponsRegister(localClass, "8x250", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x4512", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", "BombGun4512", "BombGun4512", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}