package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class B_25G1 extends B_25
  implements TypeBomber, TypeStormovik, TypeStormovikArmored
{
  private float bpos = 1.0F;
  private float bcurpos = 1.0F;
  private long btme = -1L;

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.FM.AS.isMaster()) {
      if (this.bpos == 0.0F) {
        if (this.bcurpos > this.bpos) {
          this.bcurpos -= 0.2F * paramFloat;
          if (this.bcurpos < 0.0F)
            this.bcurpos = 0.0F;
        }
        resetYPRmodifier();
        Aircraft.xyz[1] = (-0.31F + 0.31F * this.bcurpos);
        hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
      }
      else if (this.bpos == 1.0F) {
        if (this.bcurpos < this.bpos) {
          this.bcurpos += 0.2F * paramFloat;
          if (this.bcurpos > 1.0F) {
            this.bcurpos = 1.0F;
            this.bpos = 0.5F;
            this.FM.turret[2].bIsOperable = true;
          }
        }
        resetYPRmodifier();
        Aircraft.xyz[1] = (-0.3F + 0.3F * this.bcurpos);
        hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz, Aircraft.ypr);
      }

      if (Time.current() > this.btme) {
        this.btme = (Time.current() + World.Rnd().nextLong(5000L, 12000L));
        if (this.FM.turret[2].target == null) {
          this.FM.turret[2].bIsOperable = false;
          this.bpos = 0.0F;
        }
        if ((this.FM.turret[1].target != null) && (this.FM.AS.astatePilotStates[4] < 90))
        {
          this.bpos = 1.0F;
        }
      }
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat) {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      return false;
    case 1:
      if (f2 < 0.0F) {
        f2 = 0.0F;
        bool = false;
      }
      if (f2 <= 88.0F) break;
      f2 = 88.0F;
      bool = false; break;
    case 2:
      if (f2 < -88.0F) {
        f2 = -88.0F;
        bool = false;
      }
      if (f2 <= 2.0F) break;
      f2 = 2.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeBomberToggleAutomation() {
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

  public void typeBomberAdjSideslipPlus()
  {
  }

  public void typeBomberAdjSideslipMinus()
  {
  }

  public void typeBomberAdjAltitudeReset()
  {
  }

  public void typeBomberAdjAltitudePlus()
  {
  }

  public void typeBomberAdjAltitudeMinus()
  {
  }

  public void typeBomberAdjSpeedReset()
  {
  }

  public void typeBomberAdjSpeedPlus()
  {
  }

  public void typeBomberAdjSpeedMinus()
  {
  }

  public void typeBomberUpdate(float paramFloat)
  {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
    case 1:
      break;
    case 2:
      break;
    case 3:
      this.FM.turret[1].bIsOperable = false;
      break;
    case 4:
      this.FM.turret[2].bIsOperable = false;
    }
  }

  static
  {
    Class localClass = B_25G1.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "B-25");
    Property.set(localClass, "meshName", "3DO/Plane/B-25G-1(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-25G-1(USA)/hier.him");

    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar03());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1956.6F);
    Property.set(localClass, "FlightModel", "FlightModels/B-25G.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitB25G1.class, CockpitB25G1_TGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 11, 11, 12, 12, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN07", "_MGUN08", "_CANNON01", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "12x100lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun50kg 6", "BombGun50kg 6", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x250lbs3x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x250lbs2x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x250lbs1x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6x250lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun250lbs 3", "BombGun250lbs 3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8x250lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4x500lbs1x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 3", "BombGun500lbs 3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "10x50kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8x100kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 4", "BombGunFAB100 4", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250kg6x100kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", null, null, null, null, null, "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null, null, null, null, null, "BombGunFAB100 3", "BombGunFAB100 3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xHVAR", new String[] { "MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}