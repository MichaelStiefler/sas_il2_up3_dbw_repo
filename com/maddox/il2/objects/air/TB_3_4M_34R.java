package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class TB_3_4M_34R extends TB_3
{
  public static boolean bChangedPit = false;

  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1]; float f3 = Math.abs(f1);
    switch (paramInt) {
    case 0:
      if (f2 < -47.0F) {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F) {
        f2 = 47.0F;
        bool = false;
      }

      if (f3 < 147.0F) {
        if (f2 < 0.5964912F * f3 - 117.68421F) {
          f2 = 0.5964912F * f3 - 117.68421F;
          bool = false;
        }
      } else if (f3 < 157.0F) {
        if (f2 < 0.3F * f3 - 74.099998F) {
          f2 = 0.3F * f3 - 74.099998F;
          bool = false;
        }
      }
      else if (f2 < 0.217391F * f3 - 61.130436F) {
        f2 = 0.217391F * f3 - 61.130436F;
        bool = false;
      }

      if (f3 < 110.0F) break;
      if (f3 < 115.0F) {
        if ((f2 >= -5.0F) || (f2 <= -20.0F)) break;
        bool = false;
      }
      else if (f3 < 160.0F) {
        if (f2 >= -5.0F) break;
        bool = false;
      }
      else {
        if (f2 >= 15.0F) break;
        bool = false; } break;
    case 1:
      if (f2 < -47.0F) {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F) {
        f2 = 47.0F;
        bool = false;
      }

      if (f1 < -38.0F) {
        if (f2 < -32.0F) {
          f2 = -32.0F;
          bool = false;
        }
      } else if (f1 < -16.0F) {
        if (f2 < 0.5909091F * f1 - 9.545455F) {
          f2 = 0.5909091F * f1 - 9.545455F;
          bool = false;
        }
      } else if (f1 < 35.0F) {
        if (f2 < -19.0F) {
          f2 = -19.0F;
          bool = false;
        }
      } else if (f1 < 44.0F) {
        if (f2 < -3.111111F * f1 + 89.888885F) {
          f2 = -3.111111F * f1 + 89.888885F;
          bool = false;
        }
      } else if (f1 < 139.0F) {
        if (f2 < -47.0F) {
          f2 = -47.0F;
          bool = false;
        }
      } else if (f1 < 150.0F) {
        if (f2 < 1.363636F * f1 - 236.54546F) {
          f2 = 1.363636F * f1 - 236.54546F;
          bool = false;
        }
      }
      else if (f2 < -32.0F) {
        f2 = -32.0F;
        bool = false;
      }

      if (f1 < -175.7F) {
        if (f2 >= 80.800003F) break;
        bool = false;
      }
      else if (f1 < -82.0F) {
        if (f2 >= -16.0F) break;
        bool = false;
      }
      else if (f1 < 24.0F) {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < 32.0F) {
        if (f2 >= -8.3F) break;
        bool = false;
      }
      else if (f1 < 80.0F) {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else if (f1 < 174.0F) {
        if (f2 >= 0.5F * f1 - 87.0F) break;
        bool = false;
      }
      else if (f1 < 178.7F) {
        if (f2 >= 0.0F) break;
        bool = false;
      }
      else {
        if (f2 >= 80.800003F) break;
        bool = false; } break;
    case 2:
      if (f2 < -47.0F) {
        f2 = -47.0F;
        bool = false;
      }
      if (f2 > 47.0F) {
        f2 = 47.0F;
        bool = false;
      }
      if (f1 < -90.0F) {
        bool = false;
      }
      if (f1 <= 90.0F) break;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      for (int i = 0; i < 4; i++) {
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[i] > 3) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[i].getReadyness() < 0.1F)) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.repairEngine(i);
        }
      }
      for (int j = 0; j < 4; j++) {
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[j] <= 3) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[4] >= 50.0F) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[7] >= 50.0F) || (World.Rnd().nextFloat() >= 0.1F))
        {
          continue;
        }
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.repairTank(j);
      }
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[0], 0.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, -this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelAngles[1], 0.0F);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    setExplosion(paramExplosion);
    if ((paramExplosion.chunkName != null) && 
      ((paramExplosion.chunkName.startsWith("Wing")) || (paramExplosion.chunkName.startsWith("Tail"))) && (paramExplosion.chunkName.endsWith("D3")) && (paramExplosion.power < 0.014F)) {
      return;
    }

    super.msgExplosion(paramExplosion);
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset() {
  }

  public void typeBomberAdjDistancePlus() {
  }

  public void typeBomberAdjDistanceMinus() {
  }

  public void typeBomberAdjSideslipReset() {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 5000.0F) {
      this.fSightCurAltitude = 5000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 300.0F) {
      this.fSightCurAltitude = 300.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset() {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 350.0F) {
      this.fSightCurSpeed = 350.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus() {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F) {
      this.fSightCurSpeed = 50.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = TB_3_4M_34R.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "TB-3");
    Property.set(localClass, "meshName", "3DO/Plane/TB-3-4M-34R/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());

    Property.set(localClass, "yearService", 1933.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/TB-3-4M-34R.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTB_3.class, CockpitTB_3_Bombardier3.class, CockpitTB_3_NGunner.class, CockpitTB_3_TGunner3.class, CockpitTB_3_RGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 11, 12, 12, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "14fab50", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 7", "BombGunFAB50 7" });

    Aircraft.weaponsRegister(localClass, "28fab50", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 14", "BombGunFAB50 14" });

    Aircraft.weaponsRegister(localClass, "14fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 7", "BombGunFAB100 7" });

    Aircraft.weaponsRegister(localClass, "28fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 14", "BombGunFAB100 14" });

    Aircraft.weaponsRegister(localClass, "8fab250", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4fab500", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null });

    Aircraft.weaponsRegister(localClass, "4fab50028fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB100 14", "BombGunFAB100 14" });

    Aircraft.weaponsRegister(localClass, "4fab5004fab250", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8fab500", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab10002fab500", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", null, null });

    Aircraft.weaponsRegister(localClass, "2fab10002fab50028fab50", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB50 14", "BombGunFAB50 14" });

    Aircraft.weaponsRegister(localClass, "2fab10002fab50014fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB100 7", "BombGunFAB100 7" });

    Aircraft.weaponsRegister(localClass, "2fab10002fab50020fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB100 10", "BombGunFAB100 10" });

    Aircraft.weaponsRegister(localClass, "2fab10004fab50014fab50", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, "PylonPE8_FAB250", "PylonPE8_FAB250", null, null, null, null, "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "PylonDer16TB3Fake", "PylonDer16TB3Fake", null, null, "BombGunFAB1000", "BombGunFAB1000", null, null, "BombGunFAB50 7", "BombGunFAB50 7" });

    Aircraft.weaponsRegister(localClass, "2fab10004fab25020fab100", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, "PylonPE8_FAB250", "PylonPE8_FAB250", null, null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "PylonDer16TB3Fake", "PylonDer16TB3Fake", null, null, "BombGunFAB1000", "BombGunFAB1000", null, null, "BombGunFAB100 10", "BombGunFAB100 10" });

    Aircraft.weaponsRegister(localClass, "4fab1000", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null });

    Aircraft.weaponsRegister(localClass, "4fab100014fab50", new String[] { "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB50 7", "BombGunFAB50 7" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}