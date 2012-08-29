package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class PE_2SERIES84 extends PE_2
  implements TypeBomber, TypeDiveBomber, TypeTransport
{
  private long tme = 0L;

  public void update(float paramFloat)
  {
    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(5000L, 20000L));
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length != 0) {
        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].target == null)
          setRadist(0);
        else {
          setRadist(1);
        }
      }
    }
    super.update(paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 1:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 0.0F) break; f2 = 0.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  private void setRadist(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot3_D0", true);
      hierMesh().chunkVisible("Pilot3a_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3a_D0", true);
    }
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

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset() {
    this.jdField_fSightCurForwardAngle_of_type_Float = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.jdField_fSightCurForwardAngle_of_type_Float += 0.2F;
    if (this.jdField_fSightCurForwardAngle_of_type_Float > 75.0F) {
      this.jdField_fSightCurForwardAngle_of_type_Float = 75.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.jdField_fSightCurForwardAngle_of_type_Float * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus() {
    this.jdField_fSightCurForwardAngle_of_type_Float -= 0.2F;
    if (this.jdField_fSightCurForwardAngle_of_type_Float < -15.0F) {
      this.jdField_fSightCurForwardAngle_of_type_Float = -15.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.jdField_fSightCurForwardAngle_of_type_Float * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset() {
    this.jdField_fSightCurSideslip_of_type_Float = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.jdField_fSightCurSideslip_of_type_Float += 1.0F;
    if (this.jdField_fSightCurSideslip_of_type_Float > 45.0F) {
      this.jdField_fSightCurSideslip_of_type_Float = 45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.jdField_fSightCurSideslip_of_type_Float * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.jdField_fSightCurSideslip_of_type_Float -= 1.0F;
    if (this.jdField_fSightCurSideslip_of_type_Float < -45.0F) {
      this.jdField_fSightCurSideslip_of_type_Float = -45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.jdField_fSightCurSideslip_of_type_Float * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.jdField_fSightCurAltitude_of_type_Float = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.jdField_fSightCurAltitude_of_type_Float += 10.0F;
    if (this.jdField_fSightCurAltitude_of_type_Float > 6000.0F) {
      this.jdField_fSightCurAltitude_of_type_Float = 6000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.jdField_fSightCurAltitude_of_type_Float) });
  }

  public void typeBomberAdjAltitudeMinus() {
    this.jdField_fSightCurAltitude_of_type_Float -= 10.0F;
    if (this.jdField_fSightCurAltitude_of_type_Float < 300.0F) {
      this.jdField_fSightCurAltitude_of_type_Float = 300.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.jdField_fSightCurAltitude_of_type_Float) });
  }

  public void typeBomberAdjSpeedReset() {
    this.jdField_fSightCurSpeed_of_type_Float = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.jdField_fSightCurSpeed_of_type_Float += 5.0F;
    if (this.jdField_fSightCurSpeed_of_type_Float > 650.0F) {
      this.jdField_fSightCurSpeed_of_type_Float = 650.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.jdField_fSightCurSpeed_of_type_Float) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.jdField_fSightCurSpeed_of_type_Float -= 5.0F;
    if (this.jdField_fSightCurSpeed_of_type_Float < 50.0F) {
      this.jdField_fSightCurSpeed_of_type_Float = 50.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.jdField_fSightCurSpeed_of_type_Float) });
  }

  public void typeBomberUpdate(float paramFloat) {
    double d = this.jdField_fSightCurSpeed_of_type_Float / 3.6D * Math.sqrt(this.jdField_fSightCurAltitude_of_type_Float * 0.203873598D);
    d -= this.jdField_fSightCurAltitude_of_type_Float * this.jdField_fSightCurAltitude_of_type_Float * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.jdField_fSightCurAltitude_of_type_Float));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurAltitude_of_type_Float);
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurSpeed_of_type_Float);
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurForwardAngle_of_type_Float);
    paramNetMsgGuaranted.writeFloat(this.jdField_fSightCurSideslip_of_type_Float);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    this.jdField_fSightCurAltitude_of_type_Float = paramNetMsgInput.readFloat();
    this.jdField_fSightCurSpeed_of_type_Float = paramNetMsgInput.readFloat();
    this.jdField_fSightCurForwardAngle_of_type_Float = paramNetMsgInput.readFloat();
    this.jdField_fSightCurSideslip_of_type_Float = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = PE_2SERIES84.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-2");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-2series84/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-2series84.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitPE2_84.class, CockpitPE2_Bombardier.class, CockpitPE2_84_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.76315F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 10, 11, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON02", "_CANNON01", "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6fab50", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "6fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "2fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab2502fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "4fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab500", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab5002fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "BombGunFAB500", "BombGunFAB500", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}