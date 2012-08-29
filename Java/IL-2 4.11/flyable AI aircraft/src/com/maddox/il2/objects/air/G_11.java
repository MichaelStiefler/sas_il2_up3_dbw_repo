// Source File Name: G_11.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class G_11 extends Scheme0 implements TypeGlider, TypeTransport,
		TypeBomber {

	static {
		Class class1 = G_11.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "G-11");
		Property.set(class1, "meshName", "3DO/Plane/G-11/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "originCountry", PaintScheme.countryRussia);
		Property.set(class1, "yearService", 1936F);
		Property.set(class1, "yearExpired", 1948F);
		Property.set(class1, "FlightModel", "FlightModels/G-11.fmd");
		Property.set(class1, "gliderString", "3DO/Arms/TowString/mono.sim");
		Property.set(class1, "gliderStringLength", 160F);
		Property.set(class1, "gliderStringKx", 30F);
		Property.set(class1, "gliderStringFactor", 1.8F);
		Property.set(class1, "cockpitClass", new Class[] { CockpitG_11.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_BombSpawn01" });
		Aircraft.weaponsRegister(class1, "default", new String[] { null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null });
	}

	public G_11() {
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			this.hierMesh().chunkVisible("Head1_D0", false);
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
