// Source File Name: Fulmar1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class Fulmar1 extends Fulmar {

	static {
		Class class1 = Fulmar1.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Fulmar");
		Property.set(class1, "meshName", "3DO/Plane/Fulmar1(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "meshName_gb", "3DO/Plane/Fulmar1(gb)/hier.him");
		Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02f());
		Property.set(class1, "meshName_rn", "3DO/Plane/Fulmar1(gb)/hier.him");
		Property.set(class1, "PaintScheme_rn", new PaintSchemeFMPar02f());
		Property.set(class1, "yearService", 1936F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitFulmar.class });
		Property.set(class1, "FlightModel", "FlightModels/FulmarMkI.fmd");
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 0, 0,
				0, 0, 3, 3, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_MGUN07", "_MGUN08", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalDev01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750", null, null,
				null });
		Aircraft.weaponsRegister(class1, "2x250lb", new String[] {
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"BombGun250lbsE 1", "BombGun250lbsE 1", null });
		Aircraft.weaponsRegister(class1, "1xDropTank", new String[] {
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750",
				"MGunBrowning303k 750", "MGunBrowning303k 750", null, null,
				"FuelTankGun_Tank60gal 1" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null });
	}

	public Fulmar1() {
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
