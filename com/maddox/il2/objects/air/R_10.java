package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class R_10 extends Scheme1
  implements TypeScout, TypeBomber, TypeStormovik
{
  public static boolean bChangedPit = false;
  private float flapps = 0.0F;

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.FM.turret[0].bIsOperable = false;
    }
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (this.FM.getAltitude() < 3000.0F) {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
    } else {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));

      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
    }
  }

  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d) {
    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
      }hitFlesh(j, paramShot, i);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat) {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    if (paramArrayOfFloat[0] < -142.0F) {
      paramArrayOfFloat[0] = -142.0F;
      bool = false;
    } else if (paramArrayOfFloat[0] > 142.0F) {
      paramArrayOfFloat[0] = 142.0F;
      bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F) {
      paramArrayOfFloat[1] = 45.0F;
      bool = false;
    }
    if (!bool)
      return false;
    float f = Math.abs(paramArrayOfFloat[0]);
    if ((f < 2.5F) && (paramArrayOfFloat[1] < 20.799999F)) {
      paramArrayOfFloat[1] = 20.799999F;
      return false;
    }
    if ((f < 21.0F) && (paramArrayOfFloat[1] < 16.1F)) {
      paramArrayOfFloat[1] = 16.1F;
      return false;
    }
    if ((f < 41.0F) && (paramArrayOfFloat[1] < -8.5F)) {
      paramArrayOfFloat[1] = -8.5F;
      return false;
    }
    if ((f < 103.0F) && (paramArrayOfFloat[1] < -45.0F)) {
      paramArrayOfFloat[1] = -45.0F;
      return false;
    }
    if ((f < 180.0F) && (paramArrayOfFloat[1] < -7.8F)) {
      paramArrayOfFloat[1] = -7.8F;
      return false;
    }
    return true;
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

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = R_10.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "R-10");
    Property.set(localClass, "meshName", "3DO/Plane/R-10/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1948.5F);
    Property.set(localClass, "FlightModel", "FlightModels/R-10.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitR_10.class, CockpitR10_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunShKASt 250", null, null });

    Aircraft.weaponsRegister(localClass, "6xFAB50", new String[] { "MGunShKASk 750", "MGunShKASk 750", "MGunShKASt 250", "BombGunFAB50 3", "BombGunFAB50 3" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}