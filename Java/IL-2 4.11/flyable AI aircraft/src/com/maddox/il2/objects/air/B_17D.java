// Source File Name: B_17D.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-06
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class B_17D extends B_17 implements TypeBomber {

	public B_17D() {
		bSightAutomation = false;
		bSightBombDump = false;
		fSightCurDistance = 0.0F;
		fSightCurForwardAngle = 0.0F;
		fSightCurSideslip = 0.0F;
		fSightCurAltitude = 3000F;
		fSightCurSpeed = 200F;
		fSightCurReadyness = 0.0F;
	}

	protected void moveBayDoor(float f) {
		hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
		hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85F * f, 0.0F);
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 2: // '\002'
			FM.turret[0].bIsOperable = false;
			FM.turret[1].bIsOperable = false;
			FM.turret[2].bIsOperable = false;
			break;

		case 6: // '\006'
			FM.turret[3].bIsOperable = false;
			break;

		case 7: // '\007'
			FM.turret[4].bIsOperable = false;
			break;

		case 8: // '\b'
			FM.turret[5].bIsOperable = false;
			break;
		}
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			if (f < -11F) {
				f = -11F;
				flag = false;
			}
			if (f > 11F) {
				f = 11F;
				flag = false;
			}
			if (f1 < -14F) {
				f1 = -14F;
				flag = false;
			}
			if (f1 > 14F) {
				f1 = 14F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -26F) {
				f = -26F;
				flag = false;
			}
			if (f > 0.0F) {
				f = 0.0F;
				flag = false;
			}
			if (f1 < -14F) {
				f1 = -14F;
				flag = false;
			}
			if (f1 > 14F) {
				f1 = 14F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -11F) {
				f = -11F;
				flag = false;
			}
			if (f > 11F) {
				f = 11F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 0.0F) {
				f1 = 0.0F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f < -12F) {
				f = -12F;
				flag = false;
			}
			if (f > 12F) {
				f = 12F;
				flag = false;
			}
			if (f1 < -45F) {
				f1 = -45F;
				flag = false;
			}
			if (f1 > 2.0F) {
				f1 = 2.0F;
				flag = false;
			}
			break;

		case 4: // '\004'
			if (f < -41F) {
				f = -41F;
				flag = false;
			}
			if (f > 45F) {
				f = 45F;
				flag = false;
			}
			if (f1 < -10F) {
				f1 = -10F;
				flag = false;
			}
			if (f1 > 45F) {
				f1 = 45F;
				flag = false;
			}
			break;

		case 5: // '\005'
			if (f < -45F) {
				f = -45F;
				flag = false;
			}
			if (f > 53F) {
				f = 53F;
				flag = false;
			}
			if (f1 < -10F) {
				f1 = -10F;
				flag = false;
			}
			if (f1 > 45F) {
				f1 = 45F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	private static final float toMeters(float f) {
		return 0.3048F * f;
	}

	private static final float toMetersPerSecond(float f) {
		return 0.4470401F * f;
	}

	public boolean typeBomberToggleAutomation() {
		bSightAutomation = !bSightAutomation;
		bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (bSightAutomation ? "ON" : "OFF"));
		return bSightAutomation;
	}

	public void typeBomberAdjDistanceReset() {
		fSightCurDistance = 0.0F;
		fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjDistancePlus() {
		fSightCurForwardAngle++;
		if (fSightCurForwardAngle > 85F)
			fSightCurForwardAngle = 85F;
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) fSightCurForwardAngle) });
		if (bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjDistanceMinus() {
		fSightCurForwardAngle--;
		if (fSightCurForwardAngle < 0.0F)
			fSightCurForwardAngle = 0.0F;
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) fSightCurForwardAngle) });
		if (bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjSideslipReset() {
		fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSideslipPlus() {
		fSightCurSideslip += 0.1F;
		if (fSightCurSideslip > 3F)
			fSightCurSideslip = 3F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer((int) (fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipMinus() {
		fSightCurSideslip -= 0.1F;
		if (fSightCurSideslip < -3F)
			fSightCurSideslip = -3F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer((int) (fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjAltitudeReset() {
		fSightCurAltitude = 3000F;
	}

	public void typeBomberAdjAltitudePlus() {
		fSightCurAltitude += 50F;
		if (fSightCurAltitude > 50000F)
			fSightCurAltitude = 50000F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) fSightCurAltitude) });
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudeMinus() {
		fSightCurAltitude -= 50F;
		if (fSightCurAltitude < 1000F)
			fSightCurAltitude = 1000F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) fSightCurAltitude) });
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
	}

	public void typeBomberAdjSpeedReset() {
		fSightCurSpeed = 200F;
	}

	public void typeBomberAdjSpeedPlus() {
		fSightCurSpeed += 10F;
		if (fSightCurSpeed > 450F)
			fSightCurSpeed = 450F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedMinus() {
		fSightCurSpeed -= 10F;
		if (fSightCurSpeed < 100F)
			fSightCurSpeed = 100F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) fSightCurSpeed) });
	}

	public void typeBomberUpdate(float f) {
		if ((double) Math.abs(FM.Or.getKren()) > 4.5D) {
			fSightCurReadyness -= 0.0666666F * f;
			if (fSightCurReadyness < 0.0F)
				fSightCurReadyness = 0.0F;
		}
		if (fSightCurReadyness < 1.0F)
			fSightCurReadyness += 0.0333333F * f;
		else if (bSightAutomation) {
			fSightCurDistance -= toMetersPerSecond(fSightCurSpeed) * f;
			if (fSightCurDistance < 0.0F) {
				fSightCurDistance = 0.0F;
				typeBomberToggleAutomation();
			}
			fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(fSightCurDistance / toMeters(fSightCurAltitude)));
			if ((double) fSightCurDistance < (double) toMetersPerSecond(fSightCurSpeed)
					* Math.sqrt(toMeters(fSightCurAltitude) * 0.2038736F))
				bSightBombDump = true;
			if (bSightBombDump)
				if (FM.isTick(3, 0)) {
					if (FM.CT.Weapons[3] != null
							&& FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null
							&& FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1]
									.haveBullets()) {
						FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else {
					FM.CT.WeaponControl[3] = false;
				}
		}
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeByte((bSightAutomation ? 1 : 0)
				| (bSightBombDump ? 2 : 0));
		netmsgguaranted.writeFloat(fSightCurDistance);
		netmsgguaranted.writeByte((int) fSightCurForwardAngle);
		netmsgguaranted.writeByte((int) ((fSightCurSideslip + 3F) * 33.33333F));
		netmsgguaranted.writeFloat(fSightCurAltitude);
		netmsgguaranted.writeByte((int) (fSightCurSpeed / 2.5F));
		netmsgguaranted.writeByte((int) (fSightCurReadyness * 200F));
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		int i = netmsginput.readUnsignedByte();
		bSightAutomation = (i & 1) != 0;
		bSightBombDump = (i & 2) != 0;
		fSightCurDistance = netmsginput.readFloat();
		fSightCurForwardAngle = netmsginput.readUnsignedByte();
		fSightCurSideslip = -3F + (float) netmsginput.readUnsignedByte()
				/ 33.33333F;
		fSightCurAltitude = netmsginput.readFloat();
		fSightCurSpeed = (float) netmsginput.readUnsignedByte() * 2.5F;
		fSightCurReadyness = (float) netmsginput.readUnsignedByte() / 200F;
	}

	public static boolean bChangedPit = false;
	private boolean bSightAutomation;
	private boolean bSightBombDump;
	private float fSightCurDistance;
	public float fSightCurForwardAngle;
	public float fSightCurSideslip;
	public float fSightCurAltitude;
	public float fSightCurSpeed;
	public float fSightCurReadyness;

	static {
		Class class1 = com.maddox.il2.objects.air.B_17D.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B-17");
		Property.set(class1, "meshName", "3DO/Plane/B-17D(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
		Property.set(class1, "meshName_us", "3DO/Plane/B-17D(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
		Property.set(class1, "noseart", 1);
		Property.set(class1, "yearService", 1941F);
		Property.set(class1, "yearExpired", 2800.9F);
		Property.set(class1, "FlightModel", "FlightModels/B-17D.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				com.maddox.il2.objects.air.CockpitB17D.class,
				com.maddox.il2.objects.air.CockpitB17D_Bombardier.class,
				com.maddox.il2.objects.air.CockpitB17D_BGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 13,
				14, 15, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_MGUN07", "_BombSpawn01", "_BombSpawn02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 600",
				"MGunBrowning50t 600", null, null });
		Aircraft.weaponsRegister(class1, "20x100", new String[] {
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 600",
				"MGunBrowning50t 600", "BombGunFAB50 10", "BombGunFAB50 10" });
		Aircraft.weaponsRegister(class1, "14x300", new String[] {
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 600",
				"MGunBrowning50t 600", "BombGun300lbs 7", "BombGun300lbs 7" });
		Aircraft.weaponsRegister(class1, "4x1000", new String[] {
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 600",
				"MGunBrowning50t 600", "BombGun1000lbs 2", "BombGun1000lbs 2" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null });
	}
}
