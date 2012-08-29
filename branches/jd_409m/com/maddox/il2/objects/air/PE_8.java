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

public class PE_8 extends Scheme4
  implements TypeTransport, TypeBomber
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 800.0F, -50.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);

    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, 14.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 55.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 55.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", -paramFloat, 0.0F, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 25:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 26:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 27:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 28:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 29:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 65.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 65.0F * paramFloat, 0.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Engine3")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 2, 1);
    if ((paramShot.chunkName.startsWith("Engine4")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 3, 1);
    }
    if (paramShot.chunkName.startsWith("CF")) {
      if ((Aircraft.Pd.jdField_x_of_type_Double > 4.550000190734863D) && (Aircraft.Pd.jdField_x_of_type_Double < 7.150000095367432D) && (Aircraft.Pd.jdField_z_of_type_Double > 0.5799999833106995D))
      {
        if (World.Rnd().nextFloat() < 0.233F) {
          if (Aircraft.Pd.jdField_z_of_type_Double > 1.210000038146973D) {
            killPilot(paramShot.initiator, 0);
            if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
          }
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 0, (int)(paramShot.power * 0.004F));
          }
        }
        if (World.Rnd().nextFloat() < 0.233F) {
          if (Aircraft.Pd.jdField_z_of_type_Double > 1.210000038146973D) {
            killPilot(paramShot.initiator, 1);
            if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
          }
          else {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 1, (int)(paramShot.power * 0.004F));
          }
        }
      }
      if ((Aircraft.Pd.jdField_x_of_type_Double > 9.529999732971191D) && (Aircraft.Pd.jdField_z_of_type_Double < 0.1400000005960465D) && (Aircraft.Pd.jdField_z_of_type_Double > -0.6299999952316284D)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 2, (int)(paramShot.power * 0.002F));
      }
      if ((Aircraft.Pd.jdField_x_of_type_Double > 2.474999904632568D) && (Aircraft.Pd.jdField_x_of_type_Double < 4.489999771118164D) && (Aircraft.Pd.jdField_z_of_type_Double > 0.6100000143051148D) && 
        (paramShot.power * Math.sqrt(Aircraft.v1.jdField_y_of_type_Double * Aircraft.v1.jdField_y_of_type_Double + Aircraft.v1.jdField_z_of_type_Double * Aircraft.v1.jdField_z_of_type_Double) > 11900.0D) && (World.Rnd().nextFloat() < 0.45F))
      {
        for (int i = 0; i < 4; i++) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramShot.initiator, i, 0);
      }

    }

    if (paramShot.chunkName.startsWith("Turret1")) {
      if (Aircraft.Pd.jdField_z_of_type_Double > 0.03344999998807907D) {
        killPilot(paramShot.initiator, 2);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 2, (int)(paramShot.power * 0.004F));
      }
      paramShot.chunkName = ("CF_D" + chunkDamageVisible("CF"));
    }

    if (paramShot.chunkName.startsWith("Turret2")) {
      if (World.Rnd().nextBoolean())
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 4, (int)(paramShot.power * 0.004F));
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      }
    }

    if (paramShot.chunkName.startsWith("Turret3")) {
      if (Aircraft.Pd.jdField_z_of_type_Double > 0.3044500052928925D) {
        killPilot(paramShot.initiator, 7);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 7, (int)(paramShot.power * 0.002F));
      }
      paramShot.chunkName = ("Tail1_D" + chunkDamageVisible("Tail1"));
    }

    if (paramShot.chunkName.startsWith("Turret4")) {
      if (Aircraft.Pd.jdField_z_of_type_Double > -0.9954000115394592D) {
        killPilot(paramShot.initiator, 5);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 5, (int)(paramShot.power * 0.002F));
      }
      return;
    }

    if (paramShot.chunkName.startsWith("Turret5")) {
      if (Aircraft.Pd.jdField_z_of_type_Double > -0.9954000115394592D) {
        killPilot(paramShot.initiator, 6);
        if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T"); 
      }
      else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 6, (int)(paramShot.power * 0.002F));
      }
      return;
    }

    super.msgShot(paramShot);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 3:
      break;
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 6:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
      break;
    case 7:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 8:
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("Head2_D0", false);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("Head3_D0", false);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      hierMesh().chunkVisible("Head4_D0", false);
      break;
    case 5:
      hierMesh().chunkVisible("Pilot6_D0", false);
      hierMesh().chunkVisible("HMask6_D0", false);
      hierMesh().chunkVisible("Pilot6_D1", true);
      hierMesh().chunkVisible("Head5_D0", false);
      break;
    case 6:
      hierMesh().chunkVisible("Pilot7_D0", false);
      hierMesh().chunkVisible("HMask7_D0", false);
      hierMesh().chunkVisible("Pilot7_D1", true);
      hierMesh().chunkVisible("Head6_D0", false);
      break;
    case 7:
      hierMesh().chunkVisible("Pilot8_D0", false);
      hierMesh().chunkVisible("HMask8_D0", false);
      hierMesh().chunkVisible("Pilot8_D1", true);
      hierMesh().chunkVisible("Head7_D0", false);
    case 4:
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -76.0F) { f1 = -76.0F; bool = false; }
      if (f1 > 76.0F) { f1 = 76.0F; bool = false; }
      if (f2 < -47.0F) { f2 = -47.0F; bool = false; }
      if (f2 <= 47.0F) break; f2 = 47.0F; bool = false; break;
    case 1:
      float f3 = Math.abs(f1);
      if (f2 > 50.0F) { f2 = 50.0F; bool = false; }
      if (f3 < 1.0F) {
        if (f2 >= 17.0F) break; f2 = 17.0F; bool = false;
      } else if (f3 < 4.5F) {
        if (f2 >= 0.71429F - 0.71429F * f3) break; f2 = 0.71429F - 0.71429F * f3; bool = false;
      } else if (f3 < 29.5F) {
        if (f2 >= -2.5F) break; f2 = -2.5F; bool = false;
      } else if (f3 < 46.0F) {
        if (f2 >= 52.0303F - 1.84848F * f3) break; f2 = 52.0303F - 1.84848F * f3; bool = false;
      } else if (f3 < 89.0F) {
        if (f2 >= -70.735184F + 0.80232F * f3) break; f2 = -70.735184F + 0.80232F * f3; bool = false;
      } else if (f3 < 147.0F) {
        if (f2 >= 1.5F) break; f2 = 1.5F; bool = false;
      } else if (f3 < 162.0F) {
        if (f2 >= -292.5F + 2.0F * f3) break; f2 = -292.5F + 2.0F * f3; bool = false;
      } else {
        if (f2 >= 31.5F) break; f2 = 31.5F; bool = false; } break;
    case 2:
      if (f1 < -87.0F) { f1 = -87.0F; bool = false; }
      if (f1 > 87.0F) { f1 = 87.0F; bool = false; }
      if (f2 < -78.0F) { f2 = -78.0F; bool = false; }
      if (f2 <= 67.0F) break; f2 = 67.0F; bool = false; break;
    case 3:
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 > 16.0F) { f2 = 16.0F; bool = false; }
      if (f1 < -60.0F) {
        f1 = -60.0F;
        bool = false;
        if (f2 <= -11.5F) break; f2 = -11.5F;
      } else if (f1 < -13.5F) {
        if (f2 <= 3.9836F + 0.25806F * f1) break; f2 = 3.9836F + 0.25806F * f1; bool = false;
      } else if (f1 < -10.5F) {
        if (f2 <= 16.25005F + 1.16667F * f1) break; f2 = 16.25005F + 1.16667F * f1; bool = false;
      } else if (f1 < 14.0F) {
        if (f2 <= 5.0F) break; bool = false;
      } else if (f1 < 80.0F) {
        if (f2 <= 8.0F) break; bool = false;
      } else {
        f1 = 80.0F;
        bool = false;
      }
      break;
    case 4:
      f1 = -f1;
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 > 16.0F) { f2 = 16.0F; bool = false; }
      if (f1 < -60.0F) {
        f1 = -60.0F;
        bool = false;
        if (f2 > -11.5F) f2 = -11.5F; 
      }
      else if (f1 < -13.5F) {
        if (f2 > 3.9836F + 0.25806F * f1) { f2 = 3.9836F + 0.25806F * f1; bool = false; }
      } else if (f1 < -10.5F) {
        if (f2 > 16.25005F + 1.16667F * f1) { f2 = 16.25005F + 1.16667F * f1; bool = false; }
      } else if (f1 < 14.0F) {
        if (f2 > 5.0F) bool = false; 
      }
      else if (f1 < 80.0F) {
        if (f2 > 8.0F) bool = false; 
      }
      else {
        f1 = 80.0F;
        bool = false;
      }
      f1 = -f1;
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
    Class localClass = PE_8.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-8");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-8/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-8.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 3, 9, 3, 3, 9, 3, 3, 9, 3, 3, 9, 3, 3, 9, 3, 3, 9, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalDev01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev02", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalDev03", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalDev04", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalDev05", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_ExternalDev06", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11", "_ExternalDev07", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14", "_BombSpawn15", "_ExternalDev08", "_BombSpawn16", "_BombSpawn17", "_BombSpawn18", "_BombSpawn19", "_ExternalDev09", "_BombSpawn20", "_BombSpawn21", "_BombSpawn22", "_BombSpawn23", "_ExternalDev10", "_BombSpawn24", "_BombSpawn25", "_BombSpawn26", "_BombSpawn27", "_ExternalDev11", "_ExternalBomb19", "_ExternalBomb20", "_ExternalDev12", "_ExternalBomb21", "_ExternalBomb22", "_ExternalDev13", "_BombSpawn28", "_BombSpawn29", "_ExternalDev14", "_BombSpawn30", "_BombSpawn31", "_ExternalDev15", "_BombSpawn32", "_BombSpawn33", "_ExternalDev16", "_BombSpawn34", "_BombSpawn35" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "40fab100", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, null, "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12fab250", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250" });

    Aircraft.weaponsRegister(localClass, "6fab500", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonPE8_FAB250", "BombGunFAB500", "BombGunFAB500", "PylonPE8_FAB250", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4fab1000", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB1000", "BombGunFAB1000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab2000", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, "BombGunFAB2000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab20002fab1000", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB2000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab5000", new String[] { "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, "BombGunFAB5000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}