package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class P_38DroopSnoot extends P_38
  implements TypeBomber, TypeStormovik
{
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;
  private float llpos;

  public P_38DroopSnoot()
  {
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 3000.0F;
    this.fSightCurSpeed = 200.0F;
    this.fSightCurReadyness = 0.0F;
    this.llpos = 0.0F;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      hierMesh().chunkVisible("Blister2_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 2:
      this.FM.turret[1].bIsOperable = false;
    }
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
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.122F, 0.0F, 0.122F);
    hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Blister1_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);
    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateEngineStates[2] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateEngineStates[3] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 3, 1);
    }
    for (int i = 1; i < 5; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  private static final float toMeters(float paramFloat)
  {
    return 0.3048F * paramFloat;
  }

  private static final float toMetersPerSecond(float paramFloat)
  {
    return 0.4470401F * paramFloat;
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
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
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
    this.fSightCurAltitude = 3000.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F)
      this.fSightCurAltitude = 50000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F)
      this.fSightCurAltitude = 1000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 450.0F)
      this.fSightCurSpeed = 450.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 100.0F)
      this.fSightCurSpeed = 100.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D)
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
      this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed) * paramFloat;
      if (this.fSightCurDistance < 0.0F)
      {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / toMeters(this.fSightCurAltitude)));
      if (this.fSightCurDistance < toMetersPerSecond(this.fSightCurSpeed) * Math.sqrt(toMeters(this.fSightCurAltitude) * 0.203874F))
        this.bSightBombDump = true;
      if (this.bSightBombDump)
        if (this.FM.isTick(3, 0))
        {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.FM.CT.WeaponControl[3] = false;
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

  public void update(float paramFloat)
  {
    if (this.FM.AS.bLandingLightOn)
    {
      if (this.llpos < 1.0F)
      {
        this.llpos += 0.5F * paramFloat;
        hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90.0F * this.llpos, 0.0F);
      }
    }
    else if (this.llpos > 0.0F)
    {
      this.llpos -= 0.5F * paramFloat;
      hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90.0F * this.llpos, 0.0F);
    }
    super.update(paramFloat);
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
    Class localClass = P_38DroopSnoot.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-38");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-38DroopSnoot(Multi1)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/P-38DroopSnoot(USA)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-38DroopSnoot(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar04());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/P-38DroopSnoot.fmd");

    Property.set(localClass, "cockpitClass", new Class[] { CockpitP_38DS.class, CockpitP_38DS_Bombardier.class });

    Property.set(localClass, "LOSElevation", 0.69215F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 1, 1, 1, 1, 9, 9, 3, 3, 9, 9, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev03", "_ExternalDev04", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalDev07", "_ExternalDev08", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalDev09", "_ExternalDev10", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb03", "_ExternalBomb06", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb05", "_ExternalBomb04" });

    ArrayList localArrayList = new ArrayList();
    Property.set(localClass, "weaponsList", localArrayList);
    HashMapInt localHashMapInt = new HashMapInt();
    Property.set(localClass, "weaponsMap", localHashMapInt);
    int i = 67;
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
    String str;
    try
    {
      str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
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
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;

      for (int j = 67; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException1)
    {
      System.out.println("Error P38DroopSnoot Weapons 'default ' NOT initialized:" + localException1.getMessage());
    }

    try
    {
      str = "1xdroptank1x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;

      for (int k = 67; k < i; k++)
        arrayOf_WeaponSlot[k] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException2)
    {
      System.out.println("Error P38DroopSnoot Weapons '1xdroptank1x1000 ' NOT initialized:" + localException2.getMessage());
    }

    try
    {
      str = "1xdroptank1x1600";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;

      for (int m = 67; m < i; m++)
        arrayOf_WeaponSlot[m] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException3)
    {
      System.out.println("Error P38DroopSnoot Weapons '1xdroptank1x1600 ' NOT initialized:" + localException3.getMessage());
    }

    try
    {
      str = "1xdroptank1x2000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;

      for (int n = 67; n < i; n++)
        arrayOf_WeaponSlot[n] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException4)
    {
      System.out.println("Error P38DroopSnoot Weapons '1xdroptank1x2000 ' NOT initialized:" + localException4.getMessage());
    }

    try
    {
      str = "droptanks2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
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
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[60] = null;
      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[63] = null;
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[65] = null;
      arrayOf_WeaponSlot[66] = null;

      for (int i1 = 67; i1 < i; i1++)
        arrayOf_WeaponSlot[i1] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException5)
    {
      System.out.println("Error P38DroopSnoot 'droptanks 2x500 ' NOT initialized:" + localException5.getMessage());
    }

    try
    {
      str = "droptanks4x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
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
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i2 = 67; i2 < i; i2++)
        arrayOf_WeaponSlot[i2] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException6)
    {
      System.out.println("Error P38DroopSnoot Weapons 'droptanks 4x500 ' NOT initialized:" + localException6.getMessage());
    }

    try
    {
      str = "1xdroptank5x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_TankP38", 1);
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
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
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i3 = 67; i3 < i; i3++)
        arrayOf_WeaponSlot[i3] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException7)
    {
      System.out.println("Error P_38DroopSnoot Weapons '1xdroptank5x500 ' NOT initialized:" + localException7.getMessage());
    }

    try
    {
      str = "2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i4 = 59; i4 < i; i4++)
        arrayOf_WeaponSlot[i4] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException8)
    {
      System.out.println("Error P_38DroopSnoot Weapons '2x500 ' NOT initialized:" + localException8.getMessage());
    }

    try
    {
      str = "2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i5 = 59; i5 < i; i5++)
        arrayOf_WeaponSlot[i5] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException9)
    {
      System.out.println("Error P_38DroopSnoot Weapons '2x1000 ' NOT initialized:" + localException9.getMessage());
    }

    try
    {
      str = "4x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i6 = 67; i6 < i; i6++)
        arrayOf_WeaponSlot[i6] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException10)
    {
      System.out.println("Error P38DroopSnoot '4x500 ' NOT initialized:" + localException10.getMessage());
    }

    try
    {
      str = "6x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i7 = 67; i7 < i; i7++)
        arrayOf_WeaponSlot[i7] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException11)
    {
      System.out.println("Error P38DroopSnoot Weapons '6x500 ' NOT initialized:" + localException11.getMessage());
    }

    try
    {
      str = "2x10002x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = null;
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = null;
      arrayOf_WeaponSlot[62] = null;
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = null;
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i8 = 67; i8 < i; i8++)
        arrayOf_WeaponSlot[i8] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException12)
    {
      System.out.println("Error P38DroopSnoot Weapons '2x10002x500 ' NOT initialized:" + localException12.getMessage());
    }

    try
    {
      str = "2x10004x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;
      arrayOf_WeaponSlot[59] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[60] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[61] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[62] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[63] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[64] = new Aircraft._WeaponSlot(9, "PylonP38PLN2", 1);
      arrayOf_WeaponSlot[65] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arrayOf_WeaponSlot[66] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      for (int i9 = 67; i9 < i; i9++)
        arrayOf_WeaponSlot[i9] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException13)
    {
      System.out.println("Error P38DroopSnoot Weapons '2x10004x500 ' NOT initialized:" + localException13.getMessage());
    }

    try
    {
      str = "2x1600";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun1600lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i10 = 59; i10 < i; i10++)
        arrayOf_WeaponSlot[i10] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException14)
    {
      System.out.println("Error P_38DroopSnoot Weapons '2x1600 ' NOT initialized:" + localException14.getMessage());
    }

    try
    {
      str = "2x2000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun2000lbs", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i11 = 59; i11 < i; i11++)
        arrayOf_WeaponSlot[i11] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException15)
    {
      System.out.println("Error P_38DroopSnoot Weapons '2x2000 ' NOT initialized:" + localException15.getMessage());
    }

    try
    {
      str = "2x175Napalm";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = null;
      arrayOf_WeaponSlot[5] = null;
      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGun175Napalm", 1);
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGun175Napalm", 1);
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      arrayOf_WeaponSlot[40] = null;
      arrayOf_WeaponSlot[41] = null;
      arrayOf_WeaponSlot[42] = null;
      arrayOf_WeaponSlot[43] = null;
      arrayOf_WeaponSlot[44] = null;
      arrayOf_WeaponSlot[45] = null;
      arrayOf_WeaponSlot[46] = null;
      arrayOf_WeaponSlot[47] = null;
      arrayOf_WeaponSlot[48] = null;
      arrayOf_WeaponSlot[49] = null;
      arrayOf_WeaponSlot[50] = null;
      arrayOf_WeaponSlot[51] = null;
      arrayOf_WeaponSlot[52] = null;
      arrayOf_WeaponSlot[53] = null;
      arrayOf_WeaponSlot[54] = null;
      arrayOf_WeaponSlot[55] = null;
      arrayOf_WeaponSlot[56] = null;
      arrayOf_WeaponSlot[57] = null;
      arrayOf_WeaponSlot[58] = null;

      for (int i12 = 59; i12 < i; i12++)
        arrayOf_WeaponSlot[i12] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException16)
    {
      System.out.println("Error P_38DroopSnoot Weapons '2x154Napalm ' NOT initialized:" + localException16.getMessage());
    }
  }
}