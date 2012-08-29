// Source File Name: PBY.java
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

public class PBY extends PBYX implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = PBY.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "PBY");
		Property.set(class1, "meshName", "3DO/Plane/PBY/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 2048F);
		Property.set(class1, "FlightModel", "FlightModels/PBN-1.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitPBY.class,
				CockpitPBY_Bombardier.class, CockpitPBY_TGunner.class,
				CockpitPBY_LGunner.class, CockpitPBY_RGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 3, 3,
				3, 3, 9, 9, 3, 2 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01",
				"_ExternalDev02", "_ExternalBomb05", "_ExternalBomb06" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "4X250", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4X500", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGun500lbs 1", "BombGun500lbs 1",
				"BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4X1000",
				new String[] { "MGunBrowning50t 1000", "MGunBrowning50t 1000",
						"MGunBrowning50t 1000", "BombGun1000lbs 1",
						"BombGun1000lbs 1", "BombGun1000lbs 1",
						"BombGun1000lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2xMk13a", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", null, null, null, null,
				"PylonBEAUPLN4 1", "PylonBEAUPLN4 1", "BombGunTorpMk13a 1",
				"BombGunTorpMk13a 1" });
		Aircraft.weaponsRegister(class1, "2xMk34", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", null, null, null, null,
				"PylonF4FPLN2 1", "PylonF4FPLN2 1", "BombGunTorpMk34 1",
				"BombGunTorpMk34 1" });
		Aircraft.weaponsRegister(class1, "4xMk53", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGunMk53Charge 1",
				"BombGunMk53Charge 1", "BombGunMk53Charge 1",
				"BombGunMk53Charge 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2xMk24and2xMk53", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGunMk24Flare 1",
				"BombGunMk24Flare 1", "BombGunMk53Charge 1",
				"BombGunMk53Charge 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2xMk24and2xMk13a", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGunMk24Flare 1",
				"BombGunMk24Flare 1", null, null, "PylonBEAUPLN4 1",
				"PylonBEAUPLN4 1", "BombGunTorpMk13a 1", "BombGunTorpMk13a 1" });
		Aircraft.weaponsRegister(class1, "2xMk24and2xMk34", new String[] {
				"MGunBrowning50t 1000", "MGunBrowning50t 1000",
				"MGunBrowning50t 1000", "BombGunMk24Flare 1",
				"BombGunMk24Flare 1", null, null, "PylonF4FPLN2 1",
				"PylonF4FPLN2 1", "BombGunTorpMk34 1", "BombGunTorpMk34 1" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null });
	}

	public PBY() {
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 2: // '\002'
			super.FM.turret[0].bIsOperable = false;
			break;

		case 5: // '\005'
			super.FM.turret[1].bIsOperable = false;
			break;

		case 6: // '\006'
			super.FM.turret[2].bIsOperable = false;
			break;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("HMask1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			this.hierMesh().chunkVisible("Head1_D0", false);
			break;

		case 1: // '\001'
			this.hierMesh().chunkVisible("Pilot2_D0", false);
			this.hierMesh().chunkVisible("HMask2_D0", false);
			this.hierMesh().chunkVisible("Pilot2_D1", true);
			break;

		case 2: // '\002'
			this.hierMesh().chunkVisible("Pilot3_D0", false);
			this.hierMesh().chunkVisible("HMask3_D0", false);
			this.hierMesh().chunkVisible("Pilot3_D1", true);
			break;

		case 3: // '\003'
			this.hierMesh().chunkVisible("Pilot4_D0", false);
			this.hierMesh().chunkVisible("HMask4_D0", false);
			this.hierMesh().chunkVisible("Pilot4_D1", true);
			break;

		case 5: // '\005'
			this.hierMesh().chunkVisible("Pilot6_D0", false);
			this.hierMesh().chunkVisible("HMask6_D0", false);
			this.hierMesh().chunkVisible("Pilot6_D1", true);
			break;

		case 6: // '\006'
			this.hierMesh().chunkVisible("Pilot7_D0", false);
			this.hierMesh().chunkVisible("HMask7_D0", false);
			this.hierMesh().chunkVisible("Pilot7_D1", true);
			break;
		}
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
