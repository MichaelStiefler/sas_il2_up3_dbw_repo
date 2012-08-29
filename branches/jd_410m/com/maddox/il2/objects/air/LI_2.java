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
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class LI_2 extends Scheme2
  implements TypeTransport, TypeBomber
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 20.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -120.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -120.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLOut")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Pd.y) < 6.0D)) {
      this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    }
    if ((paramShot.chunkName.startsWith("WingROut")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Pd.y) < 6.0D)) {
      this.FM.AS.hitTank(paramShot.initiator, 3, 1);
    }
    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Pd.y) < 1.940000057220459D)) {
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    }
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F) && (Math.abs(Pd.y) < 1.940000057220459D)) {
      this.FM.AS.hitTank(paramShot.initiator, 2, 1);
    }
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    }
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
    }
    if ((paramShot.chunkName.startsWith("Nose")) && 
      (Pd.x > 4.900000095367432D) && (Pd.z > -0.09000000357627869D) && 
      (World.Rnd().nextFloat() < 0.1F)) {
      if (Pd.y > 0.0D) {
        killPilot(paramShot.initiator, 0);
        this.FM.setCapableOfBMP(false, paramShot.initiator);
      } else {
        killPilot(paramShot.initiator, 1);
      }

    }

    if (paramShot.chunkName.startsWith("Turret1")) {
      killPilot(paramShot.initiator, 2);
      if ((Pd.z > 1.335000038146973D) && 
        (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");

      return;
    }

    if (paramShot.chunkName.startsWith("Turret2")) {
      this.FM.turret[1].bIsOperable = false;
      return;
    }

    if (paramShot.chunkName.startsWith("Turret3")) {
      this.FM.turret[2].bIsOperable = false;
      return;
    }

    if ((paramShot.chunkName.startsWith("Tail")) && 
      (Pd.x < -5.800000190734863D) && (Pd.x > -6.789999961853027D) && (Pd.z > -0.449D) && (Pd.z < 0.1239999979734421D)) {
      this.FM.AS.hitPilot(paramShot.initiator, World.Rnd().nextInt(3, 4), (int)(paramShot.mass * 1000.0F * World.Rnd().nextFloat(0.9F, 1.1F)));
    }

    if ((this.FM.AS.astateEngineStates[0] > 2) && (this.FM.AS.astateEngineStates[1] > 2) && (World.Rnd().nextInt(0, 99) < 33)) {
      this.FM.setCapableOfBMP(false, paramShot.initiator);
    }

    super.msgShot(paramShot);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[2].setHealth(paramFloat);
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
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 4; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 1:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 2:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 3:
      if (f1 < -5.0F) { f1 = -5.0F; bool = false; }
      if (f1 > 5.0F) { f1 = 5.0F; bool = false; }
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 5.0F) break; f2 = 5.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      break;
    case 35:
      if (World.Rnd().nextFloat() >= 0.25F) break; this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(2, 6)); break;
    case 38:
      if (World.Rnd().nextFloat() >= 0.25F) break; this.FM.AS.hitTank(this, 2, World.Rnd().nextInt(2, 6));
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
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
    Class localClass = LI_2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Douglas");
    Property.set(localClass, "meshName", "3do/plane/Li-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Li-2.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01" });
    weaponsRegister(localClass, "default", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, null });
    weaponsRegister(localClass, "4xFAB250", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunFAB250 1", null });
    weaponsRegister(localClass, "12xPara", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunPara 12" });
    weaponsRegister(localClass, "5xCargoA", new String[] { "MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150", "MGunShKASt 950", null, null, null, null, "BombGunCargoA 5" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}