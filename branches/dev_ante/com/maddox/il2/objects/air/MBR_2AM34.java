package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class MBR_2AM34 extends Scheme1
  implements TypeTransport, TypeBomber, TypeSailPlane
{
  private Point3d tmpp = new Point3d();
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 11:
      cutFM(17, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor);
      cutFM(18, paramInt2, paramActor);
      this.FM.cut(18, paramInt2, paramActor);
      return super.cutFM(paramInt1, paramInt2, paramActor);
    case 19:
      this.FM.Gears.bIsSail = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 45.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat) {
    super.update(paramFloat);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 2; j++)
        if (this.FM.Gears.clpGearEff[i][j] != null) {
          this.tmpp.set(this.FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
          this.tmpp.z = 0.01D;
          this.FM.Gears.clpGearEff[i][j].pos.setAbs(this.tmpp);
          this.FM.Gears.clpGearEff[i][j].pos.reset();
        }
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 2:
      this.FM.turret[1].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Gore0_D0", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("Gore1_D0", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat) {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    float f3;
    switch (paramInt) {
    case 0:
      f3 = Math.abs(f1);
      if (f3 < 114.0F) {
        if (f2 < -33.0F) {
          f2 = -33.0F;
          bool = false;
        }
      } else if (f3 < 153.0F) {
        if (f2 < -24.0F)
          f2 = -24.0F;
        if (f2 < -61.962002F + 0.333F * f1)
          bool = false;
        if ((f2 < -19.111F + 0.185185F * f1) && (f2 > 40.0F - 0.333F * f1))
        {
          bool = false;
        }
      } else if (f3 < 168.0F) {
        if (f2 < -97.666F + 0.481482F * f1)
          f2 = -97.666F + 0.481482F * f1;
        if (f2 < -19.111F + 0.185185F * f1)
          bool = false;
      } else {
        if (f2 < -97.666F + 0.481482F * f1)
          f2 = -97.666F + 0.481482F * f1;
        bool = false;
      }
      if (f2 <= 60.0F) break;
      f2 = 60.0F;
      bool = false; break;
    case 1:
      f3 = Math.abs(f1);
      if (f3 < 2.0F)
        bool = false;
      if (f3 < 6.5F) {
        if (f2 < -4.0F)
          f2 = -4.0F;
        if (f2 < 17.666F - 3.333F * f1)
          bool = false;
      } else if (f3 < 45.0F) {
        if (f2 < 1.232F - 0.805F * f1) {
          f2 = 1.232F - 0.805F * f1;
          bool = false;
        }
      } else if (f3 < 105.0F) {
        if (f2 < -35.0F) {
          f2 = -35.0F;
          bool = false;
        }
      } else if (f2 < 2.2F) {
        f2 = 2.2F;
        bool = false;
      }
      if (f1 > 161.0F) {
        f1 = 161.0F;
        bool = false;
      }
      if (f1 < -161.0F) {
        f1 = -161.0F;
        bool = false;
      }
      if (f2 > 56.0F)
        bool = false;
      if (f2 <= 48.0F) break;
      f2 = 48.0F; break;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void msgShot(Shot paramShot) {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("WingLIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
    {
      this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    }if ((paramShot.chunkName.startsWith("WingRIn")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
    {
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    }if ((paramShot.chunkName.startsWith("Engine1")) && (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
    {
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    }if (paramShot.chunkName.startsWith("Pilot1"))
      killPilot(paramShot.initiator, 0);
    if (paramShot.chunkName.startsWith("Pilot2"))
      killPilot(paramShot.initiator, 1);
    if (paramShot.chunkName.startsWith("Pilot3"))
      killPilot(paramShot.initiator, 2);
    super.msgShot(paramShot);
  }

  public boolean typeBomberToggleAutomation() {
    return false;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 0.2F;
    if (this.fSightCurForwardAngle > 75.0F)
      this.fSightCurForwardAngle = 75.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 0.2F;
    if (this.fSightCurForwardAngle < -15.0F)
      this.fSightCurForwardAngle = -15.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 1.0F;
    if (this.fSightCurSideslip > 45.0F)
      this.fSightCurSideslip = 45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 1.0F;
    if (this.fSightCurSideslip < -45.0F)
      this.fSightCurSideslip = -45.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 300.0F)
      this.fSightCurAltitude = 300.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 520.0F)
      this.fSightCurSpeed = 520.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F)
      this.fSightCurSpeed = 50.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);

    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.fSightCurAltitude));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
    paramNetMsgGuaranted.writeFloat(this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readFloat();
    this.fSightCurSideslip = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = MBR_2AM34.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MBR-2");
    Property.set(localClass, "meshName", "3DO/Plane/MBR-2-AM-34(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_ru", "3DO/Plane/MBR-2-AM-34/hier.him");

    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/MBR-2-AM-34.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitMBR_2AM34.class, CockpitMBR_2AM34_Bombardier.class, CockpitMBR_2AM34_NGunner.class, CockpitMBR_2AM34_TGunner.class });

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xAO10", new String[] { "MGunShKASt 750", "MGunShKASt 750", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1" });

    Aircraft.weaponsRegister(localClass, "6xFAB50", new String[] { "MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null });

    Aircraft.weaponsRegister(localClass, "8xFAB50", new String[] { "MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100" });

    Aircraft.weaponsRegister(localClass, "2xFAB250", new String[] { "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, "BombGunFAB250", "BombGunFAB250" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}