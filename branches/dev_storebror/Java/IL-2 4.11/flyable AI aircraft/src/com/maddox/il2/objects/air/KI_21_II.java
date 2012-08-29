// Source File Name: KI_21_II.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class KI_21_II extends KI_21 implements TypeBomber {

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearL6_D0", 0.0F,
				Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, -70F), 0.0F);
		hiermesh.chunkSetAngles("GearL7_D0", 0.0F,
				Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, -70F), 0.0F);
		hiermesh.chunkSetAngles("GearL10_D0", 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -38F), 0.0F);
		hiermesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
		hiermesh.chunkSetAngles("GearL13_D0", 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -157F), 0.0F);
		hiermesh.chunkSetAngles("GearR6_D0", 0.0F,
				Aircraft.cvt(f, 0.3F, 0.35F, 0.0F, -70F), 0.0F);
		hiermesh.chunkSetAngles("GearR7_D0", 0.0F,
				Aircraft.cvt(f, 0.3F, 0.35F, 0.0F, -70F), 0.0F);
		hiermesh.chunkSetAngles("GearR10_D0", 0.0F,
				Aircraft.cvt(f, 0.34F, 0.99F, 0.0F, -38F), 0.0F);
		hiermesh.chunkSetAngles("GearR11_D0", 0.0F, 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
		hiermesh.chunkSetAngles("GearR13_D0", 0.0F,
				Aircraft.cvt(f, 0.34F, 0.99F, 0.0F, -157F), 0.0F);
	}

	private boolean bSightAutomation;

	private boolean bSightBombDump;

	private float fSightCurDistance;

	public float fSightCurForwardAngle;

	public float fSightCurSideslip;

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurReadyness;

	static {
		Class class1 = KI_21_II.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Ki-21");
		Property.set(class1, "meshName", "3DO/Plane/Ki-21-II(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
		Property.set(class1, "meshName_ja", "3DO/Plane/Ki-21-II(ja)/hier.him");
		Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
		Property.set(class1, "yearService", 1940F);
		Property.set(class1, "yearExpired", 1948F);
		Property.set(class1, "FlightModel", "FlightModels/Ki-21-II.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				CockpitKI_21_II.class, CockpitKI_21_II_Bombardier.class,
				CockpitKI_21_II_NGunner.class, CockpitKI_21_II_TGunner.class,
				CockpitKI_21_II_BGunner.class });
		Property.set(class1, "LOSElevation", 0.7394F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 3, 3,
				3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02",
				"_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06",
				"_BombSpawn07", "_BombSpawn08", "_BombSpawn09" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "20x15", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", "BombGun15kgJ 2", "BombGun15kgJ 2",
				"BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2",
				"BombGun15kgJ 2", "BombGun15kgJ 3", "BombGun15kgJ 3",
				"BombGun15kgJ 2" });
		Aircraft.weaponsRegister(class1, "16x50", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", "BombGun50kgJ 1", "BombGun50kgJ 1",
				"BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 2",
				"BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 3",
				"BombGun50kgJ 3" });
		Aircraft.weaponsRegister(class1, "9x100", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", "BombGun100kgJ", "BombGun100kgJ",
				"BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ",
				"BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ",
				"BombGun100kgJ" });
		Aircraft.weaponsRegister(class1, "4x250", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", "BombGun250kgJ", "BombGun250kgJ", null,
				null, "BombGun250kgJ", "BombGun250kgJ", null, null, null });
		Aircraft.weaponsRegister(class1, "2x500", new String[] {
				"MGunBrowning303t 750", "MGunBrowning303t 750",
				"MGunBrowning303t 750", null, "BombGun500kgJ", null, null,
				"BombGun500kgJ", null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null });
	}

	public KI_21_II() {
		this.bSightAutomation = false;
		this.bSightBombDump = false;
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
		this.fSightCurAltitude = 850F;
		this.fSightCurSpeed = 150F;
		this.fSightCurReadyness = 0.0F;
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.hierMesh().chunkSetAngles("Blister4_D0", 0.0F, -45F, 0.0F);
		this.hierMesh().chunkSetAngles("Blister5_D0", 0.0F, -45F, 0.0F);
		this.hierMesh().chunkSetAngles("Blister6_D0", 0.0F, -45F, 0.0F);
		this.hierMesh().chunkSetAngles("Turret3C_D0", -45F, 0.0F, 0.0F);
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			if (f < -35F) {
				f = -35F;
				flag = false;
			}
			if (f > 35F) {
				f = 35F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 30F) {
				f1 = 30F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -45F) {
				f = -45F;
				flag = false;
			}
			if (f > 45F) {
				f = 45F;
				flag = false;
			}
			if (f1 < -5F) {
				f1 = -5F;
				flag = false;
			}
			if (f1 > 60F) {
				f1 = 60F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -20F) {
				f = -20F;
				flag = false;
			}
			if (f > 20F) {
				f = 20F;
				flag = false;
			}
			if (f1 < -89F) {
				f1 = -89F;
				flag = false;
			}
			if (f1 > -12F) {
				f1 = -12F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f < -10F) {
				f = -10F;
				flag = false;
			}
			if (f > 10F) {
				f = 10F;
				flag = false;
			}
			if (f1 < -10F) {
				f1 = -10F;
				flag = false;
			}
			if (f1 > 10F) {
				f1 = 10F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 10F;
		if (this.fSightCurAltitude < 850F) {
			this.fSightCurAltitude = 850F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 10F;
		if (this.fSightCurAltitude > 10000F) {
			this.fSightCurAltitude = 10000F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 850F;
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle--;
		if (this.fSightCurForwardAngle < 0.0F) {
			this.fSightCurForwardAngle = 0.0F;
		}
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle++;
		if (this.fSightCurForwardAngle > 85F) {
			this.fSightCurForwardAngle = 85F;
		}
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip -= 0.1F;
		if (this.fSightCurSideslip < -3F) {
			this.fSightCurSideslip = -3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip += 0.1F;
		if (this.fSightCurSideslip > 3F) {
			this.fSightCurSideslip = 3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 10F;
		if (this.fSightCurSpeed < 150F) {
			this.fSightCurSpeed = 150F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 10F;
		if (this.fSightCurSpeed > 600F) {
			this.fSightCurSpeed = 600F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedReset() {
		this.fSightCurSpeed = 150F;
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		int i = netmsginput.readUnsignedByte();
		this.bSightAutomation = (i & 1) != 0;
		this.bSightBombDump = (i & 2) != 0;
		this.fSightCurDistance = netmsginput.readFloat();
		this.fSightCurForwardAngle = netmsginput.readUnsignedByte();
		this.fSightCurSideslip = -3F
				+ (netmsginput.readUnsignedByte() / 33.33333F);
		this.fSightCurAltitude = netmsginput.readFloat();
		this.fSightCurSpeed = netmsginput.readUnsignedByte() * 2.5F;
		this.fSightCurReadyness = netmsginput.readUnsignedByte() / 200F;
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeByte((this.bSightAutomation ? 1 : 0)
				| (this.bSightBombDump ? 2 : 0));
		netmsgguaranted.writeFloat(this.fSightCurDistance);
		netmsgguaranted.writeByte((int) this.fSightCurForwardAngle);
		netmsgguaranted
				.writeByte((int) ((this.fSightCurSideslip + 3F) * 33.33333F));
		netmsgguaranted.writeFloat(this.fSightCurAltitude);
		netmsgguaranted.writeByte((int) (this.fSightCurSpeed / 2.5F));
		netmsgguaranted.writeByte((int) (this.fSightCurReadyness * 200F));
	}

	public boolean typeBomberToggleAutomation() {
		this.bSightAutomation = !this.bSightAutomation;
		this.bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (this.bSightAutomation ? "ON" : "OFF"));
		return this.bSightAutomation;
	}

	public void typeBomberUpdate(float f) {
		if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
			this.fSightCurReadyness -= 0.0666666F * f;
			if (this.fSightCurReadyness < 0.0F) {
				this.fSightCurReadyness = 0.0F;
			}
		}
		if (this.fSightCurReadyness < 1.0F) {
			this.fSightCurReadyness += 0.0333333F * f;
		} else if (this.bSightAutomation) {
			this.fSightCurDistance -= (this.fSightCurSpeed / 3.6F) * f;
			if (this.fSightCurDistance < 0.0F) {
				this.fSightCurDistance = 0.0F;
				this.typeBomberToggleAutomation();
			}
			this.fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(this.fSightCurDistance / this.fSightCurAltitude));
			if (this.fSightCurDistance < ((this.fSightCurSpeed / 3.6F) * Math
					.sqrt(this.fSightCurAltitude * 0.2038736F))) {
				this.bSightBombDump = true;
			}
			if (this.bSightBombDump) {
				if (this.FM.isTick(3, 0)) {
					if ((this.FM.CT.Weapons[3] != null)
							&& (this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1] != null)
							&& this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1]
									.haveBullets()) {
						this.FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else {
					this.FM.CT.WeaponControl[3] = false;
				}
			}
		}
	}
}
