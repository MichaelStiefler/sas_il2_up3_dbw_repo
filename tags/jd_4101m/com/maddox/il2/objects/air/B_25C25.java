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

public class B_25C25 extends B_25
  implements TypeBomber
{
  private float bpos = 1.0F;
  private float bcurpos = 1.0F;
  private long btme = -1L;

  public void update(float paramFloat) { super.update(paramFloat);
    if (!this.FM.AS.isMaster()) {
      return;
    }
    if (this.bpos == 0.0F) {
      if (this.bcurpos > this.bpos) {
        this.bcurpos -= 0.2F * paramFloat;
        if (this.bcurpos < 0.0F) {
          this.bcurpos = 0.0F;
        }
      }
      resetYPRmodifier();
      xyz[1] = (-0.31F + 0.31F * this.bcurpos);
      hierMesh().chunkSetLocate("Turret3A_D0", xyz, ypr);
    } else if (this.bpos == 1.0F) {
      if (this.bcurpos < this.bpos) {
        this.bcurpos += 0.2F * paramFloat;
        if (this.bcurpos > 1.0F) {
          this.bcurpos = 1.0F;
          this.bpos = 0.5F;
          this.FM.turret[2].bIsOperable = true;
        }
      }
      resetYPRmodifier();
      xyz[1] = (-0.3F + 0.3F * this.bcurpos);
      hierMesh().chunkSetLocate("Turret3A_D0", xyz, ypr);
    }
    if (Time.current() > this.btme) {
      this.btme = (Time.current() + World.Rnd().nextLong(5000L, 12000L));
      if (this.FM.turret[2].target == null) {
        this.FM.turret[2].bIsOperable = false;
        this.bpos = 0.0F;
      }
      if ((this.FM.turret[1].target != null) && (this.FM.AS.astatePilotStates[4] < 90))
        this.bpos = 1.0F;
    }
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

  public void typeBomberAdjSideslipReset() {
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

  public void doWoundPilot(int paramInt, float paramFloat) {
    switch (paramInt) {
    case 0:
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 4:
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -23.0F) { f1 = -23.0F; bool = false; }
      if (f1 > 23.0F) { f1 = 23.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false; break;
    case 1:
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 88.0F) break; f2 = 88.0F; bool = false; break;
    case 2:
      if (f2 < -88.0F) { f2 = -88.0F; bool = false; }
      if (f2 <= 2.0F) break; f2 = 2.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = B_25C25.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-25");
    Property.set(localClass, "meshName", "3DO/Plane/B-25C-25(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_ru", "3DO/Plane/B-25C-25(ru)/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-25C-25(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1956.6F);

    Property.set(localClass, "FlightModel", "FlightModels/B-25C.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 10, 11, 11, 12, 12, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, null, null });

    weaponsRegister(localClass, "12x100lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun50kg 6", "BombGun50kg 6" });

    weaponsRegister(localClass, "6x250lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun250lbs 3", "BombGun250lbs 3" });

    weaponsRegister(localClass, "4x500lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun500lbs 2", "BombGun500lbs 2" });

    weaponsRegister(localClass, "2x1000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGun1000lbs 1", "BombGun1000lbs 1" });

    weaponsRegister(localClass, "1x2000lbs", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", "BombGun2000lbs 1", null, null });

    weaponsRegister(localClass, "10x50kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGunFAB50 5", "BombGunFAB50 5" });

    weaponsRegister(localClass, "8x100kg", new String[] { "MGunBrowning50ki 400", "MGunBrowning50t 400", "MGunBrowning50t 450", "MGunBrowning50t 450", "MGunBrowning50tj 450", "MGunBrowning50tj 450", null, "BombGunFAB100 4", "BombGunFAB100 4" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}