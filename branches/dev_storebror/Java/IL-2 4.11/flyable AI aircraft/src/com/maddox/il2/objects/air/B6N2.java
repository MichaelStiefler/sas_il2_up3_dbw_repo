// Source File Name: B6N2.java
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

public class B6N2 extends B6N implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = B6N2.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B6N");
		Property.set(class1, "meshName", "3DO/Plane/B6N2(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
		Property.set(class1, "meshName_ja", "3DO/Plane/B6N2(ja)/hier.him");
		Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1946.5F);
		Property.set(class1, "FlightModel", "FlightModels/B6N2.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitB6N2.class,
				CockpitB6N2_Bombardier.class, CockpitB6N2_TGunner.class,
				CockpitB6N2_BGunner.class });
		Property.set(class1, "LOSElevation", 0.7394F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 9, 3, 9, 3,
				9, 3, 3, 9, 9, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_ExternalDev01", "_ExternalBomb01",
				"_ExternalDev02", "_ExternalBomb02", "_ExternalDev06",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalDev04",
				"_ExternalDev05", "_ExternalBomb06", "_ExternalBomb07",
				"_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10",
				"_ExternalBomb11" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "6x30", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null,
				null, null, null, null, "PylonB6NPLN1", "PylonB6NPLN1",
				"BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1",
				"BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun30kgJ 1" });
		Aircraft.weaponsRegister(class1, "6x50", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null,
				null, null, null, null, "PylonB6NPLN1", "PylonB6NPLN1",
				"BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1",
				"BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1" });
		Aircraft.weaponsRegister(class1, "1x250", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null,
				"PylonB5NPLN1", "BombGun250kgJ 1", null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x250", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null, null,
				null, "PylonB6NPLN1", "BombGun250kgJ 1", "BombGun250kgJ 1",
				null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x500", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null,
				"PylonB5NPLN1", "BombGun500kgJ 1", null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x600", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", null, null,
				"PylonB5NPLN1", "BombGun600kgJ 1", null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x91", new String[] {
				"MGunVikkersKt 500", "MGunVikkersKt 500", "PylonB5NPLN0",
				"BombGunTorpMk13 1", null, null, null, null, null, null, null,
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null });
	}

	public B6N2() {
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 50F;
		if (this.fSightCurAltitude < 300F) {
			this.fSightCurAltitude = 300F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 50F;
		if (this.fSightCurAltitude > 50000F) {
			this.fSightCurAltitude = 50000F;
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
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeFloat(this.fSightCurAltitude);
		netmsgguaranted.writeFloat(this.fSightCurSpeed);
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
