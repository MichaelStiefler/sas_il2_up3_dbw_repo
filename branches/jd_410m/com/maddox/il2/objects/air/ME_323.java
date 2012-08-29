package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class ME_323 extends Scheme7
  implements TypeTransport, TypeBomber
{
  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 1:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false; break;
    case 2:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 3:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 4:
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 5:
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 6:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xengine")) {
      i = paramString.charAt(7) - '1';
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.hitEngine(paramShot.initiator, i, 1);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 3:
      return false;
    case 4:
      return false;
    case 5:
      return false;
    case 6:
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    this.FM.Gears.lgear = true;
    this.FM.Gears.rgear = true;
    super.update(paramFloat);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 2:
      this.FM.turret[6].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[5].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 6:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 7:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 8:
      this.FM.turret[3].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      if (!hierMesh().isChunkVisible("Blister1_D0")) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (!hierMesh().isChunkVisible("Blister1_D0")) break;
      hierMesh().chunkVisible("Gore2_D0", true); break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      break;
    case 4:
      hierMesh().chunkVisible("Pilot5_D0", false);
      hierMesh().chunkVisible("Pilot5_D1", true);
    }
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ("CF_D3".equals(paramShot.chunkName)) return;

    super.msgShot(paramShot);
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
    Class localClass = ME_323.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me-323");
    Property.set(localClass, "meshName", "3Do/Plane/Me-323/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-323.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 15, 16, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG131t 650", "MGunMG131t 650", "MGunMG131t 525", "MGunMG131t 525", "MGunMG15120t 350", "MGunMG15120t 350", "MGunMG15t 350", null });

    weaponsRegister(localClass, "32xPara", new String[] { "MGunMG131t 650", "MGunMG131t 650", "MGunMG131t 525", "MGunMG131t 525", "MGunMG15120t 350", "MGunMG15120t 350", "MGunMG15t 350", "BombGunPara 32" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}