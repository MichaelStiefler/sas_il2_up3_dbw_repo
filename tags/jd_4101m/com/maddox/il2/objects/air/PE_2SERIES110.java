package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class PE_2SERIES110 extends PE_2
  implements TypeBomber, TypeDiveBomber, TypeTransport
{
  private long tme = 0L;
  private Gun gun3;
  private Gun gun4;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.FM.turret.length != 0) {
      this.FM.turret[1].bIsOperable = false;
      this.FM.turret[2].bIsOperable = false;
      this.FM.turret[3].bIsOperable = false;
    }
    this.gun3 = getGunByHookName("_MGUN03");
    this.gun4 = getGunByHookName("_MGUN04");
  }

  public void update(float paramFloat)
  {
    if (Time.current() > this.tme) {
      this.tme = (Time.current() + World.Rnd().nextLong(5000L, 20000L));
      if (this.FM.turret.length != 0) {
        this.gun3.loadBullets(Math.min(this.gun3.countBullets(), this.gun4.countBullets()));
        this.gun4.loadBullets(this.gun3.countBullets());
        Actor localActor = null;
        for (int i = 1; i < 4; i++) { if (!this.FM.turret[i].bIsOperable) continue; localActor = this.FM.turret[i].target; }
        for (i = 1; i < 4; i++) this.FM.turret[i].target = localActor;
        if (localActor == null) {
          setRadist(0);
        }
        else if (Actor.isValid(localActor)) {
          this.pos.getAbs(tmpLoc2); localActor.pos.getAbs(tmpLoc3); tmpLoc2.transformInv(tmpLoc3.getPoint());
          if (tmpLoc3.getPoint().x < -Math.abs(tmpLoc3.getPoint().y))
            setRadist(1);
          else if (tmpLoc3.getPoint().y < 0.0D)
            setRadist(2);
          else {
            setRadist(3);
          }
        }
      }
    }

    super.update(paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -110.0F) { f1 = -110.0F; bool = false; }
      if (f1 > 88.0F) { f1 = 88.0F; bool = false; }
      if (f2 < -1.0F) { f2 = -1.0F; bool = false; }
      if (f2 <= 55.0F) break; f2 = 55.0F; bool = false; break;
    case 1:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 0.0F) break; f2 = 0.0F; bool = false; break;
    case 2:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false; break;
    case 3:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  private void setRadist(int paramInt)
  {
    hierMesh().chunkVisible("Pilot3_D0", false);
    hierMesh().chunkVisible("Pilot3a_D0", false);
    hierMesh().chunkVisible("Pilot3b_D0", false);
    hierMesh().chunkVisible("Pilot3c_D0", false);
    this.FM.turret[1].bIsOperable = false;
    this.FM.turret[2].bIsOperable = false;
    this.FM.turret[3].bIsOperable = false;
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot3_D0", true);
      this.FM.turret[1].bIsOperable = true;
      break;
    case 1:
      hierMesh().chunkVisible("Pilot3a_D0", true);
      this.FM.turret[1].bIsOperable = true;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3b_D0", true);
      this.FM.turret[2].bIsOperable = true;
      hierMesh().chunkVisible("Turret3B_D0", true);
      hierMesh().chunkVisible("Turret4B_D0", false);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot3c_D0", true);
      this.FM.turret[3].bIsOperable = true;
      hierMesh().chunkVisible("Turret3B_D0", false);
      hierMesh().chunkVisible("Turret4B_D0", true);
    }
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 1:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 2:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
      this.FM.turret[3].setHealth(paramFloat);
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
    if (this.fSightCurForwardAngle > 75.0F) {
      this.fSightCurForwardAngle = 75.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus() {
    this.fSightCurForwardAngle -= 0.2F;
    if (this.fSightCurForwardAngle < -15.0F) {
      this.fSightCurForwardAngle = -15.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset() {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 1.0F;
    if (this.fSightCurSideslip > 45.0F) {
      this.fSightCurSideslip = 45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus() {
    this.fSightCurSideslip -= 1.0F;
    if (this.fSightCurSideslip < -45.0F) {
      this.fSightCurSideslip = -45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset() {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 6000.0F) {
      this.fSightCurAltitude = 6000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus() {
    this.fSightCurAltitude -= 10.0F;
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
    if (this.fSightCurSpeed > 650.0F) {
      this.fSightCurSpeed = 650.0F;
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
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);
    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.fSightCurAltitude));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSpeed);
    paramNetMsgGuaranted.writeFloat(this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readFloat();
    this.fSightCurSideslip = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = PE_2SERIES110.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Pe-2");
    Property.set(localClass, "meshName", "3DO/Plane/Pe-2series110/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());

    Property.set(localClass, "yearService", 1942.7F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Pe-2series110.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitPE2_110.class, CockpitPE2_Bombardier.class, CockpitPE2_110_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.76315F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 10, 11, 12, 13, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_CANNON02", "_CANNON01", "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6fab50", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });

    weaponsRegister(localClass, "6fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "2fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab2502fab100", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, "BombGunFAB100", "BombGunFAB100" });

    weaponsRegister(localClass, "4fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab500", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2fab5002fab250", new String[] { "MGunShKASki 450", "MGunUBki 150", "MGunUBt 200", "MGunUBt 200", "MGunShKASt 450", "MGunShKASt 450", "BombGunFAB500", "BombGunFAB500", "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}