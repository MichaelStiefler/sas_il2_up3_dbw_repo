// Source File Name: LetovS_328.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class LetovS_328 extends Letov {

	static {
		Class class1 = LetovS_328.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "S-328");
		Property.set(class1, "meshName", "3do/Plane/LetovS-328/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar00s());
		Property.set(class1, "meshName_de", "3do/Plane/LetovS-328_DE/hier.him");
		Property.set(class1, "PaintScheme_de", new PaintSchemeBMPar00s());
		Property.set(class1, "meshName_sk", "3do/Plane/LetovS-328_SK/hier.him");
		Property.set(class1, "PaintScheme_sk", new PaintSchemeBMPar00s());
		Property.set(class1, "yearService", 1935F);
		Property.set(class1, "yearExpired", 1950F);
		Property.set(class1, "FlightModel", "FlightModels/LetovS-328.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { Cockpit_RanwersLetov.class });
		Property.set(class1, "originCountry", PaintScheme.countrySlovakia);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 10, 10, 9, 9,
				9, 9, 9, 9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01",
				"_ExternalDev02", "_ExternalDev03", "_ExternalDev04",
				"_ExternalDev05", "_ExternalDev06", "_ExternalDev07",
				"_ExternalDev08", "_ExternalBomb18", "_ExternalBomb19",
				"_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22",
				"_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03",
				"_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06",
				"_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09",
				"_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12",
				"_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15",
				"_ExternalBomb16", "_ExternalBomb17" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "8*10kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", null, null, null, null, null, "BombGun10kgCZ",
				"BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", null, null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "4*20kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", null, null, "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				null, null, null, null, null, null, null, "BombGun20kgCZ",
				"BombGun20kgCZ", null, null, "BombGun20kgCZ", "BombGun20kgCZ",
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "6*20kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", null, null, null, null, null, "BombGun20kgCZ",
				"BombGun20kgCZ", "BombGun20kgCZ", "BombGun20kgCZ", null, null,
				"BombGun20kgCZ", "BombGun20kgCZ", null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "2*20kg+6*10kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", null, null, null, null, null, "BombGun20kgCZ",
				"BombGun20kgCZ", "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", null, null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "2*50kgCZ", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, "BombGun50kgCZ", "BombGun50kgCZ", null });
		Aircraft.weaponsRegister(class1, "1*100kgCZ", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGun100kgCZ" });
		Aircraft.weaponsRegister(class1, "2*50kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, null, null, "BombGun50kg",
				"BombGun50kg", null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1*100kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, null, null, null, null, "BombGun100kg",
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1*ParaFlare", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, "BombGunParaFlare", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2*ParaFlare", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", null, null,
				null, null, null, null, "BombGunParaFlare", "BombGunParaFlare",
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1*ParaFlare+2*20kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "BombGunParaFlare", null, null, null, null,
				null, null, "BombGun20kgCZ", "BombGun20kgCZ", null, null, null,
				null, null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2*ParaFlare+6*10kg", new String[] {
				"MGunVz30sS328 400", "MGunVz30sS328 400", "MGunVz30t 420",
				"MGunVz30t 420", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "PylonS328 1", "PylonS328 1", "PylonS328 1",
				"PylonS328 1", "BombGunParaFlare", "BombGunParaFlare", null,
				null, null, null, null, "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", "BombGun10kgCZ", "BombGun10kgCZ",
				"BombGun10kgCZ", null, null, null, null, null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
	}

	public LetovS_328() {
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
