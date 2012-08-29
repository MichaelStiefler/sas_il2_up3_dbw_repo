package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class IL_4_IL4 extends IL_4
  implements TypeBomber
{
  public float fSightCurAltitude = 300.0F;
  public float fSightCurSpeed = 50.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightSetForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1100.0F, -75.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -f, 0.0F);
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 25:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 26:
      this.FM.turret[1].bIsOperable = false;
      break;
    case 27:
      this.FM.turret[2].bIsOperable = false;
      break;
    case 28:
      this.FM.turret[3].bIsOperable = false;
      break;
    case 29:
      this.FM.turret[4].bIsOperable = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doKillPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 3:
      break;
    case 4:
      this.FM.turret[1].bIsOperable = false;
      break;
    case 5:
      this.FM.turret[3].bIsOperable = false;
      break;
    case 6:
      this.FM.turret[4].bIsOperable = false;
      break;
    case 7:
      this.FM.turret[2].bIsOperable = false;
    }
  }

  public boolean typeBomberToggleAutomation()
  {
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
    if (this.fSightCurSpeed > 650.0F)
      this.fSightCurSpeed = 650.0F;
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
    Class localClass = IL_4_IL4.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "DB-3");
    Property.set(localClass, "meshName", "3DO/Plane/Il-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar04());
    Property.set(localClass, "yearService", 1936.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Il-4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitIL4.class, CockpitIL4_Bombardier.class, CockpitIL4_FGunner.class, CockpitIL4_TGunner.class, CockpitIL4_BGunner.class });

    Property.set(localClass, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "10fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "10fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null, null, null, null, "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "3fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab25010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "3fab25010fab100", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB250", "BombGunNull", "BombGunFAB250", "BombGunFAB250", "BombGunFAB100 5", "BombGunFAB100 5" });

    Aircraft.weaponsRegister(localClass, "1fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab5002fab250", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB250", "BombGunFAB250", null, null });

    Aircraft.weaponsRegister(localClass, "3fab500", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", null, null });

    Aircraft.weaponsRegister(localClass, "3fab50010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB500", "BombGunNull", "BombGunFAB500", "BombGunFAB500", "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "1fab1000", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1fab100010fab50", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunFAB1000", "BombGunNull", null, null, "BombGunFAB50 5", "BombGunFAB50 5" });

    Aircraft.weaponsRegister(localClass, "torp1", new String[] { "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}