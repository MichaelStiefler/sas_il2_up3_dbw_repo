// Source File Name: FW_200C3U4.java
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

public class FW_200C3U4 extends FW_200 implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = FW_200C3U4.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "FW200");
		Property.set(class1, "meshName", "3DO/Plane/FW-200C-3U4/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
		Property.set(class1, "yearService", 1941.1F);
		Property.set(class1, "yearExpired", 1949F);
		Property.set(class1, "FlightModel", "FlightModels/FW-200C-3U4.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitFW200.class,
				CockpitFW200_Bombardier.class, CockpitFW200_FGunner.class,
				CockpitFW200_BGunner.class, CockpitFW200_TGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 14,
				15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1,
				new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04",
						"_MGUN05", "_MGUN06", "_BombSpawn01", "_BombSpawn02",
						"_ExternalBomb01", "_ExternalBomb02",
						"_ExternalBomb03", "_ExternalBomb04", "_BombSpawn03",
						"_BombSpawn04", "_BombSpawn05", "_BombSpawn06",
						"_BombSpawn07", "_BombSpawn08", "_BombSpawn09",
						"_BombSpawn10", "_BombSpawn11", "_BombSpawn12",
						"_BombSpawn13", "_BombSpawn14" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "12sc50", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, null, null, "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50" });
		Aircraft.weaponsRegister(class1, "12sc502sc250", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC250", "BombGunSC250", null, null, "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50" });
		Aircraft.weaponsRegister(class1, "12sc504sc250", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });
		Aircraft.weaponsRegister(class1, "12sc504sc500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });
		Aircraft.weaponsRegister(class1, "12sc502sc1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC1000", "BombGunSC1000", null, null, "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50" });
		Aircraft.weaponsRegister(class1, "12sc504sc1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC1000", "BombGunSC1000", "BombGunSC1000",
				"BombGunSC1000", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50",
				"BombGunSC50" });
		Aircraft.weaponsRegister(class1, "2sc250", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC250", "BombGunSC250", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "4sc250", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC250", "BombGunSC250", null, null, "BombGunSC250",
				"BombGunSC250", null, null, null, null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "6sc250", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC250", "BombGunSC250", "BombGunSC250", "BombGunSC250",
				"BombGunSC250", "BombGunSC250", null, null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2sc500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC500", "BombGunSC500", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "4sc500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC500", "BombGunSC500", null, null, "BombGunSC500",
				"BombGunSC500", null, null, null, null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "6sc500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSC500", "BombGunSC500", "BombGunSC500", "BombGunSC500",
				"BombGunSC500", "BombGunSC500", null, null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2sd500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSD500", "BombGunSD500", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "4sd500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSD500", "BombGunSD500", null, null, "BombGunSD500",
				"BombGunSD500", null, null, null, null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "6sd500", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125",
				"BombGunSD500", "BombGunSD500", "BombGunSD500", "BombGunSD500",
				"BombGunSD500", "BombGunSD500", null, null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2sc1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, "BombGunSC1000", "BombGunSC1000", null, null, null,
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4sc1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunSC1000", "BombGunSC1000", "BombGunSC1000",
				"BombGunSC1000", null, null, null, null, null, null, null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2sc1800", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, "BombGunSC1800", "BombGunSC1800", null, null, null,
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2ab1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, "BombGunAB1000", "BombGunAB1000", null, null, null,
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4ab1000", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				"BombGunAB1000", "BombGunAB1000", "BombGunAB1000",
				"BombGunAB1000", null, null, null, null, null, null, null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2pc1600", new String[] {
				"MGunMG15t 1125", "MGunMGFFt 300", "MGunMG15t 1125",
				"MGunMG15t 450", "MGunMG15t 450", "MGunMG15t 1125", null, null,
				null, null, "BombGunPC1600", "BombGunPC1600", null, null, null,
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
	}

	public FW_200C3U4() {
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
		if (this.fSightCurAltitude > 6000F) {
			this.fSightCurAltitude = 6000F;
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
		if (this.fSightCurSpeed > 650F) {
			this.fSightCurSpeed = 650F;
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
