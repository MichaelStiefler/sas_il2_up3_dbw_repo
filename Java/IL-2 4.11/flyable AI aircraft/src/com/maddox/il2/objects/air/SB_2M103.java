// Source File Name: SB_2M103.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class SB_2M103 extends SB implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = SB_2M103.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "SB");
		Property.set(class1, "meshNameDemo",
				"3DO/Plane/SB-2M-103(Russian)/hier.him");
		Property.set(class1, "meshName", "3DO/Plane/SB-2M-103(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
		Property.set(class1, "meshName_ru",
				"3DO/Plane/SB-2M-103(Russian)/hier.him");
		Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar01());
		Property.set(class1, "yearService", 1938F);
		Property.set(class1, "yearExpired", 1944F);
		Property.set(class1, "FlightModel", "FlightModels/SB-2M-103.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				CockpitSB_2M103.class, CockpitSB2M103_Bombardier.class,
				CockpitSB2M103_TGunner.class, CockpitSB2M103_BGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 10, 11, 12, 3,
				3, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05",
				"_BombSpawn06", "_BombSpawn07", "_ExternalBomb01",
				"_ExternalBomb02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000",
				"MGunShKASt 500", null, null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "6xfab50", new String[] {
				"MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000",
				"MGunShKASt 500", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				null, null, null });
		Aircraft.weaponsRegister(class1, "6xfab100", new String[] {
				"MGunShKASt 700", "MGunShKASt 700", "MGunShKASt 1000",
				"MGunShKASt 300", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", null, null, null });
		Aircraft.weaponsRegister(class1, "1xfab250", new String[] {
				"MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000",
				"MGunShKASt 500", null, null, null, null, null, null,
				"BombGunFAB250", null, null });
		Aircraft.weaponsRegister(class1, "2xfab250", new String[] {
				"MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000",
				"MGunShKASt 500", null, null, null, null, null, null, null,
				"BombGunFAB250", "BombGunFAB250" });
		Aircraft.weaponsRegister(class1, "2xfab500", new String[] {
				"MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000",
				"MGunShKASt 500", null, null, null, null, null, null, null,
				"BombGunFAB500", "BombGunFAB500" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
	}

	public SB_2M103() {
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 10F;
		if (this.fSightCurAltitude < 300F) {
			this.fSightCurAltitude = 300F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 10F;
		if (this.fSightCurAltitude > 10000F) {
			this.fSightCurAltitude = 10000F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 300F;
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle -= 0.2F;
		if (this.fSightCurForwardAngle < -15F) {
			this.fSightCurForwardAngle = -15F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer(
						(int) (this.fSightCurForwardAngle * 1.0F)) });
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle += 0.2F;
		if (this.fSightCurForwardAngle > 75F) {
			this.fSightCurForwardAngle = 75F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer(
						(int) (this.fSightCurForwardAngle * 1.0F)) });
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip--;
		if (this.fSightCurSideslip < -45F) {
			this.fSightCurSideslip = -45F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer(
						(int) (this.fSightCurSideslip * 1.0F)) });
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip++;
		if (this.fSightCurSideslip > 45F) {
			this.fSightCurSideslip = 45F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer(
						(int) (this.fSightCurSideslip * 1.0F)) });
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 5F;
		if (this.fSightCurSpeed < 50F) {
			this.fSightCurSpeed = 50F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 5F;
		if (this.fSightCurSpeed > 520F) {
			this.fSightCurSpeed = 520F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedReset() {
		this.fSightCurSpeed = 50F;
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		this.fSightCurAltitude = netmsginput.readFloat();
		this.fSightCurSpeed = netmsginput.readFloat();
		this.fSightCurForwardAngle = netmsginput.readFloat();
		this.fSightCurSideslip = netmsginput.readFloat();
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeFloat(this.fSightCurAltitude);
		netmsgguaranted.writeFloat(this.fSightCurSpeed);
		netmsgguaranted.writeFloat(this.fSightCurForwardAngle);
		netmsgguaranted.writeFloat(this.fSightCurSideslip);
	}

	public boolean typeBomberToggleAutomation() {
		return false;
	}

	public void typeBomberUpdate(float f) {
		double d = (this.fSightCurSpeed / 3.6000000000000001D)
				* Math.sqrt(this.fSightCurAltitude * 0.20387359799999999D);
		d -= (this.fSightCurAltitude * this.fSightCurAltitude) * 1.419E-005D;
		this.fSightSetForwardAngle = (float) Math.toDegrees(Math.atan(d
				/ this.fSightCurAltitude));
	}
}
