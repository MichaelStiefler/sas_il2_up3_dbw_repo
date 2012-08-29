package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class KI_21_II extends KI_21
  implements TypeBomber
{
  private boolean bSightAutomation = false;
  private boolean bSightBombDump = false;
  private float fSightCurDistance = 0.0F;
  public float fSightCurForwardAngle = 0.0F;
  public float fSightCurSideslip = 0.0F;
  public float fSightCurAltitude = 850.0F;
  public float fSightCurSpeed = 150.0F;
  public float fSightCurReadyness = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.06F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.06F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL10_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.75F, 0.0F, -38.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.75F, 0.0F, -45.0F));

    paramHierMesh.chunkSetAngles("GearL13_D0", 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.75F, 0.0F, -157.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(paramFloat, 0.3F, 0.35F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(paramFloat, 0.3F, 0.35F, 0.0F, -70.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR10_D0", 0.0F, Aircraft.cvt(paramFloat, 0.34F, 0.99F, 0.0F, -38.0F), 0.0F);

    paramHierMesh.chunkSetAngles("GearR11_D0", 0.0F, 0.0F, Aircraft.cvt(paramFloat, 0.05F, 0.75F, 0.0F, -45.0F));

    paramHierMesh.chunkSetAngles("GearR13_D0", 0.0F, Aircraft.cvt(paramFloat, 0.34F, 0.99F, 0.0F, -157.0F), 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat) {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) {
        f1 = -35.0F;
        bool = false;
      }
      if (f1 > 35.0F) {
        f1 = 35.0F;
        bool = false;
      }
      if (f2 < -25.0F) {
        f2 = -25.0F;
        bool = false;
      }
      if (f2 <= 30.0F) break;
      f2 = 30.0F;
      bool = false; break;
    case 1:
      if (f1 < -45.0F) {
        f1 = -45.0F;
        bool = false;
      }
      if (f1 > 45.0F) {
        f1 = 45.0F;
        bool = false;
      }
      if (f2 < -5.0F) {
        f2 = -5.0F;
        bool = false;
      }
      if (f2 <= 60.0F) break;
      f2 = 60.0F;
      bool = false; break;
    case 2:
      if (f1 < -20.0F) {
        f1 = -20.0F;
        bool = false;
      }
      if (f1 > 20.0F) {
        f1 = 20.0F;
        bool = false;
      }
      if (f2 < -89.0F) {
        f2 = -89.0F;
        bool = false;
      }
      if (f2 <= -12.0F) break;
      f2 = -12.0F;
      bool = false; break;
    case 3:
      if (f1 < -10.0F) {
        f1 = -10.0F;
        bool = false;
      }
      if (f1 > 10.0F) {
        f1 = 10.0F;
        bool = false;
      }
      if (f2 < -10.0F) {
        f2 = -10.0F;
        bool = false;
      }
      if (f2 <= 10.0F) break;
      f2 = 10.0F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void onAircraftLoaded() {
    super.onAircraftLoaded();
    hierMesh().chunkSetAngles("Blister4_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Blister5_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Blister6_D0", 0.0F, -45.0F, 0.0F);
    hierMesh().chunkSetAngles("Turret3C_D0", -45.0F, 0.0F, 0.0F);
  }

  public boolean typeBomberToggleAutomation() {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));

    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset() {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus() {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus() {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < 0.0F)
      this.fSightCurForwardAngle = 0.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));

    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset() {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus() {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F)
      this.fSightCurSideslip = 3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F)
      this.fSightCurSideslip = -3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus() {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F)
      this.fSightCurAltitude = 850.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus() {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 600.0F)
      this.fSightCurSpeed = 600.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float paramFloat)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
      this.fSightCurReadyness -= 0.0666666F * paramFloat;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * paramFloat;
    } else if (this.bSightAutomation) {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * paramFloat;
      if (this.fSightCurDistance < 0.0F) {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));

      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F))
      {
        this.bSightBombDump = true;
      }if (this.bSightBombDump)
        if (this.FM.isTick(3, 0)) {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.FM.CT.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));

    paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
    paramNetMsgGuaranted.writeByte((int)this.fSightCurForwardAngle);
    paramNetMsgGuaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));

    paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    paramNetMsgGuaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    int i = paramNetMsgInput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = paramNetMsgInput.readFloat();
    this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);

    this.fSightCurAltitude = paramNetMsgInput.readFloat();
    this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
  }

  static
  {
    Class localClass = KI_21_II.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ki-21");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-21-II(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-21-II(ja)/hier.him");

    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Ki-21-II.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitKI_21_II.class, CockpitKI_21_II_Bombardier.class, CockpitKI_21_II_NGunner.class, CockpitKI_21_II_TGunner.class, CockpitKI_21_II_BGunner.class });

    Property.set(localClass, "LOSElevation", 0.7394F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "20x15", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 3", "BombGun15kgJ 3", "BombGun15kgJ 2" });

    Aircraft.weaponsRegister(localClass, "16x50", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 3", "BombGun50kgJ 3" });

    Aircraft.weaponsRegister(localClass, "9x100", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ" });

    Aircraft.weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun250kgJ", "BombGun250kgJ", null, null, "BombGun250kgJ", "BombGun250kgJ", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, "BombGun500kgJ", null, null, "BombGun500kgJ", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}