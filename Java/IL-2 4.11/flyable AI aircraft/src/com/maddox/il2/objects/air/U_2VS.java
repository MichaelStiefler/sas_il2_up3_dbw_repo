// Source File Name: U_2VS.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class U_2VS extends U_2 {

	static {
		Class class1 = U_2VS.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "U-2");
		Property.set(class1, "meshName", "3do/plane/U-2VS/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "yearService", 1942F);
		Property.set(class1, "yearExpired", 1967.8F);
		Property.set(class1, "FlightModel", "FlightModels/U-2VS.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitU2VS.class,
				CockpitU2VS_TGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03",
				"_ExternalBomb04" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunUBt 250", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2ao10", new String[] { "MGunUBt 250",
				"BombGunAO10 1", "BombGunAO10 1", null, null });
		Aircraft.weaponsRegister(class1, "4ao10", new String[] { "MGunUBt 250",
				"BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1",
				"BombGunAO10 1" });
		Aircraft.weaponsRegister(class1, "2fab50", new String[] {
				"MGunUBt 250", "BombGunFAB50", "BombGunFAB50", null, null });
		Aircraft.weaponsRegister(class1, "4fab50", new String[] {
				"MGunUBt 250", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "2fab100", new String[] {
				"MGunUBt 250", "BombGunFAB100", "BombGunFAB100", null, null });
		Aircraft.weaponsRegister(class1, "2x4", new String[] { "MGunUBt 250",
				"BombGunFAB100", "BombGunFAB100", "BombGunFAB50",
				"BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null });
	}

	public U_2VS() {
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 1: // '\001'
			this.FM.turret[0].bIsOperable = false;
			break;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		default:
			break;

		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Head1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			if (!this.FM.AS.bIsAboutToBailout) {
				this.hierMesh().chunkVisible("Gore1_D0", true);
			}
			break;

		case 1: // '\001'
			this.hierMesh().chunkVisible("Pilot2_D0", false);
			this.hierMesh().chunkVisible("Pilot2_D1", true);
			if (!this.FM.AS.bIsAboutToBailout) {
				this.hierMesh().chunkVisible("Gore2_D0", true);
			}
			break;
		}
	}

	public void typeBomberAdjAltitudeMinus() {
	}

	public void typeBomberAdjAltitudePlus() {
	}

	public void typeBomberAdjAltitudeReset() {
	}

	public void typeBomberAdjDistanceMinus() {
	}

	public void typeBomberAdjDistancePlus() {
	}

	public void typeBomberAdjDistanceReset() {
	}

	public void typeBomberAdjSideslipMinus() {
	}

	public void typeBomberAdjSideslipPlus() {
	}

	public void typeBomberAdjSideslipReset() {
	}

	public void typeBomberAdjSpeedMinus() {
	}

	public void typeBomberAdjSpeedPlus() {
	}

	public void typeBomberAdjSpeedReset() {
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
	}

	public boolean typeBomberToggleAutomation() {
		return false;
	}

	public void typeBomberUpdate(float f) {
	}
}
