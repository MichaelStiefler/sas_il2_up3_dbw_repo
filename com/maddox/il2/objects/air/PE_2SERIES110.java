package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class PE_2SERIES110 extends PE_2
  implements TypeBomber, TypeDiveBomber, TypeTransport
{
  private long tme = 0L;
  private Gun gun3;
  private Gun gun4;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length != 0) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
    }
    this.gun3 = getGunByHookName("_MGUN03");
    this.gun4 = getGunByHookName("_MGUN04");
  }

  public void update(float paramFloat)
  {
    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(5000L, 20000L));
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length != 0) {
        this.gun3.loadBullets(Math.min(this.gun3.countBullets(), this.gun4.countBullets()));
        this.gun4.loadBullets(this.gun3.countBullets());
        Actor localActor = null;
        for (int i = 1; i < 4; i++) { if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[i].bIsOperable) continue; localActor = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[i].target; }
        for (int j = 1; j < 4; j++) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[j].target = localActor;
        if (localActor == null) {
          setRadist(0);
        }
        else if (Actor.isValid(localActor)) {
          this.pos.getAbs(Aircraft.tmpLoc2); localActor.pos.getAbs(Aircraft.tmpLoc3); Aircraft.tmpLoc2.transformInv(Aircraft.tmpLoc3.getPoint());
          if (Aircraft.tmpLoc3.getPoint().x < -Math.abs(Aircraft.tmpLoc3.getPoint().jdField_y_of_type_Double))
            setRadist(1);
          else if (Aircraft.tmpLoc3.getPoint().jdField_y_of_type_Double < 0.0D)
            setRadist(2);
          else {
            setRadist(3);
          }
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
      if (f1 < -110.0F) { f1 = -110.0F; bool = false; }
      if (f1 > 88.0F) { f1 = 88.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 55.0F) break; f2 = 55.0F; bool = false; break;
    case 1:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 0.0F) break; f2 = 0.0F; bool = false; break;
    case 2:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false; break;
    case 3:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  private void setRadist(int paramInt)
  {
    hierMesh().chunkVisible("Pilot3_D0", false);
    hierMesh().chunkVisible("Pilot3a_D0", false);
    hierMesh().chunkVisible("Pilot3b_D0", false);
    hierMesh().chunkVisible("Pilot3c_D0", false);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot3_D0", true);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = true;
      break;
    case 1:
      hierMesh().chunkVisible("Pilot3a_D0", true);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = true;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3b_D0", true);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = true;
      hierMesh().chunkVisible("Turret3B_D0", true);
      hierMesh().chunkVisible("Turret4B_D0", false);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot3c_D0", true);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = true;
      hierMesh().chunkVisible("Turret3B_D0", false);
      hierMesh().chunkVisible("Turret4B_D0", true);
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
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
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
    Class localClass = PE_2SERIES110.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-2");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-2series110/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());

    Property.set(localClass, "yearService", 1942.7F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-2series110.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitPE2_110.class, CockpitPE2_Bombardier.class, CockpitPE2_110_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.76315F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 10, 11, 12, 13, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON02", "_CANNON01", "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6fab50", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "6fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "2fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab2502fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "4fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab500", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab5002fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}