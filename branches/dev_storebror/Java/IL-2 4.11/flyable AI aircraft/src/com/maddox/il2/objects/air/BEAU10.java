// Source File Name: BEAU10.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BEAU10 extends BEAU implements TypeFighter, TypeStormovik {

	static {
		Class class1 = BEAU10.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Beaufighter");
		Property.set(class1, "meshName",
				"3DO/Plane/BeaufighterMk10(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "meshName_gb",
				"3DO/Plane/BeaufighterMk10(GB)/hier.him");
		Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1965.5F);
		Property.set(class1, "FlightModel", "FlightModels/BeaufighterMkX.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitBEAU21.class,
				CockpitBEAU10Gun.class });
		Property.set(class1, "LOSElevation", 0.7394F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 1, 1,
				1, 1, 9, 9, 3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 9, 3, 9,
				3, 10, 0, 0 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01",
				"_ExternalBomb01", "_ExternalDev04", "_ExternalDev05",
				"_ExternalRock01", "_ExternalRock02", "_ExternalRock03",
				"_ExternalRock04", "_ExternalRock05", "_ExternalRock06",
				"_ExternalRock07", "_ExternalRock08", "_ExternalDev06",
				"_ExternalBomb04", "_ExternalDev07", "_ExternalBomb05",
				"_MGUN09", "_MGUN10", "_MGUN11" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2xfuse250", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "PylonSpitC", "BombGun250lbsE 1",
				"PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2xfuse500", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "PylonSpitC", "BombGun500lbsE 1",
				"PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2x250f2x250w", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun250lbsE 1",
				"BombGun250lbsE 1", null, null, null, null, null, null, null,
				null, null, null, null, null, "PylonSpitC", "BombGun250lbsE 1",
				"PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2x500f2x250w", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun250lbsE 1",
				"BombGun250lbsE 1", null, null, null, null, null, null, null,
				null, null, null, null, null, "PylonSpitC", "BombGun500lbsE 1",
				"PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2x500f2x500w", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun500lbsE 1",
				"BombGun500lbsE 1", null, null, null, null, null, null, null,
				null, null, null, null, null, "PylonSpitC", "BombGun500lbsE 1",
				"PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "8x60rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", null, null, null,
				null, "MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "8x90rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3",
				"RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90",
				"RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90",
				null, null, null, null, "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2xfuse2508x60rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "PylonSpitC",
				"BombGun250lbsE 1", "PylonSpitC", "BombGun250lbsE 1",
				"MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2xfuse5008x60rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "PylonSpitC",
				"BombGun500lbsE 1", "PylonSpitC", "BombGun500lbsE 1",
				"MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "2xfuse2508x90rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3",
				"RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90",
				"RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90",
				"PylonSpitC", "BombGun250lbsE 1", "PylonSpitC",
				"BombGun250lbsE 1", "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "1xtorp", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, "PylonBEAUPLN4", "BombGunTorpMk13Brit 1", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "1xtorp2x250", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"PylonBEAUPLN1", "PylonBEAUPLN1", "BombGun250lbsE 1",
				"BombGun250lbsE 1", "PylonBEAUPLN4", "BombGunTorpMk13Brit 1",
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "MGunVikkersKt 1500",
				"MGunBrowning303k 350", "MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "1xtorp8x60rock", new String[] {
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunBrowning50k 350", "MGunBrowning50k 350",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250",
				"MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null,
				null, null, "PylonBEAUPLN4", "BombGunTorpMk13Brit 1",
				"PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU",
				"RocketGunHVAR5BEAU", null, null, null, null,
				"MGunVikkersKt 1500", "MGunBrowning303k 350",
				"MGunBrowning303k 350" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null });
	}

	public BEAU10() {
	}
}