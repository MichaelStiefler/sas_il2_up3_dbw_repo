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
import com.maddox.il2.objects.weapons.BombGunTorpMk13;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class A_20C extends A_20
  implements TypeBomber, TypeStormovik
{
  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 3000.0F;
  public float fSightCurSpeed = 200.0F;
  public float fSightCurReadyness = 0.0F;

  private float llpos = 0.0F;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      hierMesh().chunkVisible("Blister2_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -75.0F) { f1 = -75.0F; bool = false; }
      if (f1 > 75.0F) { f1 = 75.0F; bool = false; }
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 1:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
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

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1], 0.0F, 0.288F, 0.0F, 0.288F);
    hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    resetYPRmodifier();
    Aircraft.xyz[2] = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[2], 0.0F, 0.122F, 0.0F, 0.122F);
    hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Blister1_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (paramBoolean) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[2] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[3] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);

    }

    for (int i = 1; i < 5; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && 
      ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunTorpMk13))) {
      hierMesh().chunkVisible("Bay1_D0", false);
      hierMesh().chunkVisible("Bay2_D0", false);
    }
  }

  private static final float toMeters(float paramFloat)
  {
    return 0.3048F * paramFloat;
  }
  private static final float toMetersPerSecond(float paramFloat) {
    return 0.4470401F * paramFloat;
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));
    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F) {
      this.fSightCurForwardAngle = 85.0F;
    }
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F) {
      this.fSightCurForwardAngle = 0.0F;
    }
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });
    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F) {
      this.fSightCurSideslip = 3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F) {
      this.fSightCurSideslip = -3.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 3000.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F) {
      this.fSightCurAltitude = 50000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 1000.0F) {
      this.fSightCurAltitude = 1000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new Object[] { new Integer((int)this.fSightCurAltitude) });
    this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 200.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 450.0F) {
      this.fSightCurSpeed = 450.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 100.0F) {
      this.fSightCurSpeed = 100.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
    if (Math.abs(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F) {
        this.fSightCurReadyness = 0.0F;
      }
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed) * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / toMeters(this.fSightCurAltitude)));
      if (this.fSightCurDistance < toMetersPerSecond(this.fSightCurSpeed) * Math.sqrt(toMeters(this.fSightCurAltitude) * 0.203874F)) {
        this.bSightBombDump = true;
      }
      if (this.bSightBombDump)
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTick(3, 0)) {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)] != null) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length - 1)].haveBullets())) {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
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

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
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
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bLandingLightOn) {
      if (this.llpos < 1.0F) {
        this.llpos += 0.5F * paramFloat;
        hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90.0F * this.llpos, 0.0F);
      }
    }
    else if (this.llpos > 0.0F) {
      this.llpos -= 0.5F * paramFloat;
      hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90.0F * this.llpos, 0.0F);
    }

    super.update(paramFloat);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A-20");
    Property.set(localClass, "meshName", "3DO/Plane/A-20C(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1965.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A-20C.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitA_20C.class, CockpitA_20C_Bombardier.class, CockpitA_20C_TGunner.class, CockpitA_20C_BGunner.class });

    Property.set(localClass, "LOSElevation", 0.92575F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 10, 10, 11, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN07", "_MGUN08", "_MGUN09", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGunFAB50", "BombGunFAB50", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x1008x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x1008x1002x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x3004x300", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x3004x3002x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB50", "BombGunFAB50", "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", "BombGun500lbs", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x5008x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", "BombGun500lbs", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x5004x300", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", "BombGun500lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x5002x500", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", null, null, null });

    Aircraft.weaponsRegister(localClass, "1x1000", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, null, null, "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10008x100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, "BombGunFAB50 4", "BombGunFAB50 4", "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10004x300", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, "BombGun300lbs 2", "BombGun300lbs 2", "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10002x500", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", null, null, null, null, "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1xmk13", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, null, null, "BombGunTorpMk13" });

    Aircraft.weaponsRegister(localClass, "2xfab2502xfab250", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null });

    Aircraft.weaponsRegister(localClass, "1xfab5002xfab100", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB500" });

    Aircraft.weaponsRegister(localClass, "1xfab5002xfab250", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB250", "BombGunFAB250", null, null, null, null, "BombGunFAB500" });

    Aircraft.weaponsRegister(localClass, "1xfab1000", new String[] { "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, null, null, null, "BombGunFAB1000" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}