package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class SU_2 extends Scheme1
  implements TypeScout, TypeBomber, TypeStormovik
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 3; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2a_D0", 0.0F, -42.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -140.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, -80.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2a_D0", 0.0F, -42.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -140.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -80.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -40.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3) {
        hitChunk("WingLIn", paramShot);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F))
        this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3) {
        hitChunk("WingRIn", paramShot);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F))
        this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    } else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.5F)) {
        this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
        if (this.FM.AS.astateEngineStates[0] < 1) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 0, 1);
        }
        if (World.Rnd().nextFloat() < paramShot.power / 960000.0F) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
        }
        getEnergyPastArmor(25.0F, paramShot);
      }
    } else if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    } else if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        this.FM.AS.setJamBullets(10, 0);
      }
      if (paramString.startsWith("xturret2")) {
        this.FM.AS.setJamBullets(11, 0);
      }
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      int i = 0;
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

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -90.0F) { f1 = -90.0F; bool = false; }
      if (f1 > 90.0F) { f1 = 90.0F; bool = false; }
      if (f2 < -5.0F) { f2 = -5.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false;
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
      break;
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
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
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 11:
    case 19:
      hierMesh().chunkVisible("Wire_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    float f = cvt(this.FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -10.0F);
    for (int i = 1; i < 17; i++) {
      hierMesh().chunkSetAngles("cowlflap" + i + "_D0", 0.0F, f, 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = SU_2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Su-2");
    Property.set(localClass, "meshName", "3DO/Plane/Su-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Su-2.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, null, null });

    weaponsRegister(localClass, "30ao10", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunAO10 15", "BombGunAO10 15" });

    weaponsRegister(localClass, "4fab50", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunFAB50 2", "BombGunFAB50 2" });

    weaponsRegister(localClass, "6fab50", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 2", "BombGunFAB50 2" });

    weaponsRegister(localClass, "4fab100", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", null, null, "BombGunFAB100 2", "BombGunFAB100 2" });

    weaponsRegister(localClass, "6fab100", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 2", "BombGunFAB100 2" });

    weaponsRegister(localClass, "2fab250", new String[] { "MGunShKASki 1700", "MGunShKASki 1700", "MGunShKASt 1000", "BombGunFAB250 1", "BombGunFAB250 1", null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}