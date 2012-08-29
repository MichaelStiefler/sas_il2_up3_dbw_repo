// Source File Name: R_5.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.*;
import java.io.IOException;

public class R_5 extends R_5xyz implements TypeBomber {

	public R_5() {
	}

	public boolean typeBomberToggleAutomation() {
		return false;
	}

	public void typeBomberAdjAltitudeReset() {
	}

	public void typeBomberAdjAltitudePlus() {
	}

	public void typeBomberAdjAltitudeMinus() {
	}

	public void typeBomberAdjVelocityReset() {
	}

	public void typeBomberAdjVelocityPlus() {
	}

	public void typeBomberAdjVelocityMinus() {
	}

	public void typeBomberAdjDiveAngleReset() {
	}

	public void typeBomberAdjDiveAnglePlus() {
	}

	public void typeBomberAdjDiveAngleMinus() {
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
	}

	protected void mydebug(String s) {
		System.out.println(s);
	}

	static {
		Class class1 = CLASS.THIS();
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "R-5");
		Property.set(class1, "meshName", "3do/plane/R-5/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFCSPar08());
		Property.set(class1, "yearService", 1931F);
		Property.set(class1, "yearExpired", 1944F);
		Property.set(
				class1,
				"cockpitClass",
				new Class[] { com.maddox.il2.objects.air.CockpitI_16TYPE6.class });
		Property.set(class1, "FlightModel", "FlightModels/R-5.fmd");
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 10, 10, 0, 0, 0,
				0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_MGUN07", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05",
				"_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08",
				"_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11",
				"_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14",
				"_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17",
				"_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20",
				"_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23",
				"_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26",
				"_ExternalDev01", "_ExternalDev02", "_ExternalDev03",
				"_ExternalDev04", "_ExternalDev05" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "Gunpods", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500",
				"MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200",
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, "PylonR5GPodL 1",
				"PylonR5GPodR 1", null, null, null });
		Aircraft.weaponsRegister(class1, "Gunpods+8x10+2x100", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500",
				"MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200",
				null, null, null, null, null, null, null, null,
				"BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", null,
				"BombGunAO10S 1", null, "BombGunAO10S 1", null,
				"BombGunAO10S 1", null, "BombGunAO10S 1", null,
				"BombGunAO10S 1", null, "BombGunAO10S 1", null,
				"BombGunAO10S 1", null, "PylonR5GPodL 1", "PylonR5GPodR 1",
				"PylonR5BombRackL 1", "PylonR5BombRackR 1",
				"PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "Gunpods+16x10+2x100", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500",
				"MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200",
				null, null, null, null, null, null, null, null,
				"BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1",
				"BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1",
				"BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1",
				"BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1",
				"BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1",
				"BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1",
				"PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1",
				"PylonR5BombRackR 1", "PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "Gunpods+4x50+2x50", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500",
				"MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200",
				null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				"PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1",
				"PylonR5BombRackR 1", "PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "8x50+2x100", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null,
				null, null, null, "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "PylonR5BombRackL 1",
				"PylonR5BombRackR 1", "PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "2x100+2x100", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null,
				null, null, null, null, null, null, null, null, null,
				"BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1",
				"BombGunFAB100 1", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, "PylonR5BombRackL 1", "PylonR5BombRackR 1",
				"PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "2x100+2x50+2x100", new String[] {
				"MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null,
				null, null, null, null, null, null, null, "BombGunFAB50 1",
				"BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1",
				"BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "PylonR5BombRackL 1",
				"PylonR5BombRackR 1", "PylonR5BombRackC 1" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null });
	}
}
