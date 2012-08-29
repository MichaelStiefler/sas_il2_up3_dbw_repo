// Source File Name: Swordfish1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class Swordfish1 extends Swordfish {

	static {
		Class class1 = Swordfish1.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Swordfish");
		Property.set(class1, "meshName", "3DO/Plane/Swordfish1(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
		Property.set(class1, "meshName_gb", "3DO/Plane/Swordfish1(gb)/hier.him");
		Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02f());
		Property.set(class1, "meshName_rn", "3DO/Plane/Swordfish1(gb)/hier.him");
		Property.set(class1, "PaintScheme_rn", new PaintSchemeBMPar02f());
		Property.set(class1, "yearService", 1936F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/Swordfish.fmd");
		Property.set(class1, "originCountry", PaintScheme.countryBritain);
		Property.set(class1, "cockpitClass", new Class[] {
				CockpitSwordfish.class, CockpitSwordfish_TAG.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 10, 3, 3, 3, 3,
				3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3,
				3, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_turret1", "_ExternalBomb02", "_ExternalBomb03",
				"_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06",
				"_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09",
				"_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12",
				"_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15",
				"_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18",
				"_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21",
				"_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb01",
				"_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26",
				"_ExternalBomb27", "_ExternalBomb28", "_ExternalBomb29",
				"_ExternalBomb30", "_ExternalBomb31", "_ExternalBomb32",
				"_ExternalBomb33" });
		weaponsRegister(class1, "default", new String[] { "MGunVikkersKs 300",
				"MGunVikkersKt 600", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null });
		weaponsRegister(class1, "1_1xTorpedo", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGunTorpMk12", null, null, null, null, null, null, null,
				null, null, null });
		weaponsRegister(class1, "2_3x500lb", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE",
				"BombGun500lbsE", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGun500lbsE", null, null,
				null, null, null, null, null, null });
		weaponsRegister(class1, "3_1x500lb+4x250lb", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null,
				"BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE",
				"BombGun250lbsE", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun500lbsE", null, null, null, null, null, null, null,
				null, null, null });
		weaponsRegister(class1, "4_1x500lb+8x100lb", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGun500lbsE", "BombGunNull", "BombGun50kg", "BombGun50kg",
				"BombGun50kg", "BombGun50kg", "BombGun50kg", "BombGun50kg",
				"BombGun50kg", "BombGun50kg" });
		weaponsRegister(class1, "5_3x500lb+8xflare", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", "BombGun500lbsE",
				"BombGun500lbsE", null, null, null, null, "BombGunParaFlareUK",
				"BombGunNull", "BombGunNull", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunNull", "BombGunNull",
				"BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull",
				"BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunNull", "BombGunNull", "BombGunParaFlareUK", null,
				"BombGun500lbsE", null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "6_1xtorpedo+8xflare", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null,
				null, null, null, "BombGunParaFlareUK", "BombGunNull",
				"BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunNull", "BombGunNull", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunNull", "BombGunNull",
				"BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull",
				"BombGunNull", "BombGunParaFlareUK", "BombGunTorpMk12", null,
				null, null, null, null, null, null, null, null, null });
		weaponsRegister(class1, "7_1x500lb+4x250lb+8xflare", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null,
				"BombGun250lbsE", "BombGun250lbsE", "BombGun250lbsE",
				"BombGun250lbsE", "BombGunParaFlareUK", "BombGunNull",
				"BombGunNull", "BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunNull", "BombGunNull", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunNull", "BombGunNull",
				"BombGunParaFlareUK", "BombGunParaFlareUK", "BombGunNull",
				"BombGunNull", "BombGunParaFlareUK", null, "BombGun500lbsE",
				null, null, null, null, null, null, null, null, null });
		weaponsRegister(class1, "8_1x500lb+8xFlare_AI", new String[] {
				"MGunVikkersKs 300", "MGunVikkersKt 600", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGun500lbsE", "BombGunNull", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunParaFlareUK", "BombGunParaFlareUK",
				"BombGunParaFlareUK" });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
	}

	public Swordfish1() {
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
