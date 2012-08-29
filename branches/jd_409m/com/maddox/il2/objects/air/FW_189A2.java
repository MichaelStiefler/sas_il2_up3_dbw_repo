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
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class FW_189A2 extends Scheme3
  implements TypeScout, TypeBomber
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 115.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 115.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 90.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -110.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -40.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if (paramShot.chunkName.startsWith("WingLIn")) {
      if (World.Rnd().nextFloat() < 0.048F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 0);
      if ((Aircraft.v1.jdField_x_of_type_Double < 0.25D) && (World.Rnd().nextFloat() < 0.25F) && (World.Rnd().nextFloat(0.01F, 0.121F) < paramShot.mass)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, (int)(1.0F + paramShot.mass * 26.08F));
      }
    }
    if (paramShot.chunkName.startsWith("WingRIn")) {
      if (World.Rnd().nextFloat() < 0.048F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(0, 1);
      if ((Aircraft.v1.jdField_x_of_type_Double < 0.25D) && (World.Rnd().nextFloat() < 0.25F) && (World.Rnd().nextFloat(0.01F, 0.121F) < paramShot.mass)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, (int)(1.0F + paramShot.mass * 26.08F));
      }
    }

    if (paramShot.chunkName.startsWith("Engine1")) {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, (int)(1.0F + paramShot.mass * 20.700001F));
      if (World.Rnd().nextFloat() < 0.01F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 5);
    }
    if (paramShot.chunkName.startsWith("Engine2")) {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, (int)(1.0F + paramShot.mass * 20.700001F));
      if (World.Rnd().nextFloat() < 0.01F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 5);
    }

    if (paramShot.chunkName.startsWith("Pilot1")) {
      if ((Aircraft.v1.jdField_x_of_type_Double > -0.5D) || (paramShot.power * -Aircraft.v1.jdField_x_of_type_Double > 12800.0D)) {
        killPilot(paramShot.initiator, 0);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
        if ((Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && 
          (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
      }

      paramShot.chunkName = "CF_D0";
    }
    if (paramShot.chunkName.startsWith("Pilot2")) {
      killPilot(paramShot.initiator, 1);
      if ((Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && 
        (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");

      paramShot.chunkName = "CF_D0";
    }
    if (paramShot.chunkName.startsWith("Pilot3")) {
      killPilot(paramShot.initiator, 2);
      if ((Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && 
        (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");

      paramShot.chunkName = "CF_D0";
    }

    if ((paramShot.chunkName.startsWith("Tail1")) && 
      (World.Rnd().nextFloat() < 0.05F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, (int)(1.0F + paramShot.mass * 26.08F));
    }

    if ((paramShot.chunkName.startsWith("Tail2")) && 
      (World.Rnd().nextFloat() < 0.05F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, (int)(1.0F + paramShot.mass * 26.08F));
    }

    if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] == 4) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] == 4)) && (World.Rnd().nextInt(0, 99) < 33)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfBMP(false, paramShot.initiator);
    }

    super.msgShot(paramShot);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      if (World.Rnd().nextFloat() < 0.66F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 6);
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      if (World.Rnd().nextFloat() < 0.66F) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 6);
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
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

  public void doKillPilot(int paramInt)
  {
    if (paramInt == 1) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      hierMesh().chunkVisible("Turret2A_D0", false);
      hierMesh().chunkVisible("Turret2B_D0", false);
      hierMesh().chunkVisible("Turret2B_D1", true);
    }
    if (paramInt == 2) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      hierMesh().chunkVisible("Turret1A_D0", false);
      hierMesh().chunkVisible("Turret1B_D0", false);
      hierMesh().chunkVisible("Turret1B_D1", true);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f2 < 40.0F) { f2 = 40.0F; bool = false; }
      if (f2 <= 96.0F) break; f2 = 96.0F; bool = false; break;
    case 1:
      if (f1 < -75.0F) { f1 = -75.0F; bool = false; }
      if (f1 > 85.0F) { f1 = 85.0F; bool = false; }
      if (f2 < 4.0F) { f2 = 4.0F; bool = false; }
      if (f2 <= 80.0F) break; f2 = 80.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
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
    Class localClass = FW_189A2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Fw-189");
    Property.set(localClass, "meshName", "3do/plane/Fw-189A-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1941.6F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fw-189A-2.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 3, 3, 10, 10, 11, 11 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17k 660", "MGunMG17k 660", null, null, null, null, "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 1500", "MGunMG81t 1500" });

    Aircraft.weaponsRegister(localClass, "4xSC50", new String[] { "MGunMG17k 660", "MGunMG17k 660", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 1500", "MGunMG81t 1500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}