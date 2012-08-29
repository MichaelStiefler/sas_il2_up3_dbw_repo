// Source File Name: TU_2S.java
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

public class TU_2S extends TU_2 implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = TU_2S.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Tu-2");
		Property.set(class1, "meshName", "3DO/Plane/Tu-2S/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
		Property.set(class1, "yearService", 1942.5F);
		Property.set(class1, "yearExpired", 1956.6F);
		Property.set(class1, "FlightModel", "FlightModels/Tu-2S.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitTU_2S.class,
				CockpitTU_2S_Bombardier.class, CockpitTU_2S_TGunner.class,
				CockpitTU_2S_RTGunner.class, CockpitTU_2S_BGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 1, 1, 10, 11, 12,
				3, 3, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_CANNON01",
				"_CANNON02", "_MGUN01", "_MGUN02", "_MGUN03",
				"_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03",
				"_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04",
				"_BombSpawn05", "_BombSpawn06" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "6fab50", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "8fab50", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB50", "BombGunFAB50",
				null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "6fab100", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, null,
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "8fab100", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB100", "BombGunFAB100",
				null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "1fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB250",
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250",
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab2506fab100", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250",
				null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "3fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250",
				"BombGunFAB250", null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, null, null, null,
				"BombGunFAB250", "BombGunFAB250", "BombGunFAB250",
				"BombGunFAB250" });
		Aircraft.weaponsRegister(class1, "6fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250",
				null, null, null, "BombGunFAB250", "BombGunFAB250",
				"BombGunFAB250", "BombGunFAB250" });
		Aircraft.weaponsRegister(class1, "1fab500", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB500",
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab500", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab5006fab50", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "2fab5006fab100", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "2fab5004fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				null, null, null, "BombGunFAB250", "BombGunFAB250",
				"BombGunFAB250", "BombGunFAB250" });
		Aircraft.weaponsRegister(class1, "3fab500", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				"BombGunFAB500", null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4fab500",
				new String[] { "MGunShVAKk 250", "MGunShVAKk 250",
						"MGunUBt 550", "MGunUBt 450", "MGunUBt 350",
						"BombGunFAB500", "BombGunFAB500", null,
						"BombGunFAB500", "BombGunFAB500", null, null, null,
						null });
		Aircraft.weaponsRegister(class1, "6fab500", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500",
				null, null, null, "BombGunFAB500", "BombGunFAB500",
				"BombGunFAB500", "BombGunFAB500" });
		Aircraft.weaponsRegister(class1, "1fab1000", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB1000",
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1fab10002fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250",
				"BombGunFAB1000", null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab1000", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB1000",
				"BombGunFAB1000", null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2fab10006fab50",
				new String[] { "MGunShVAKk 250", "MGunShVAKk 250",
						"MGunUBt 550", "MGunUBt 450", "MGunUBt 350",
						"BombGunFAB1000", "BombGunFAB1000", null,
						"BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
						"BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "2fab10006fab100", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB1000",
				"BombGunFAB1000", null, "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB100",
				"BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "2fab10002fab250", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB1000",
				"BombGunFAB1000", null, "BombGunFAB250", "BombGunFAB250", null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "2fab10001fab500", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB1000",
				"BombGunFAB1000", "BombGunFAB500", null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "3fab1000", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", "BombGunFAB1000",
				"BombGunFAB1000", "BombGunFAB1000", null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "1fab2000", new String[] {
				"MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550",
				"MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB2000",
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
	}

	public TU_2S() {
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	protected void moveBayDoor(float f) {
		this.hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
		this.hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 85F * f, 0.0F);
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 50F;
		if (this.fSightCurAltitude < 1000F) {
			this.fSightCurAltitude = 1000F;
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
