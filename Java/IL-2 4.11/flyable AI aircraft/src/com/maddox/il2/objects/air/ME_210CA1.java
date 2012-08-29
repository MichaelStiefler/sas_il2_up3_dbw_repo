// Source File Name: ME_210CA1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class ME_210CA1 extends ME_210 implements TypeFighter, TypeBNZFighter,
		TypeStormovik, TypeStormovikArmored, TypeDiveBomber {

	public static boolean bChangedPit = false;

	static {
		Class class1 = ME_210CA1.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Me-210");
		Property.set(class1, "meshName", "3DO/Plane/Me-210Ca-1/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitME_210CA1.class });
		Property.set(class1, "LOSElevation", 0.66895F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1, 10, 10,
				3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04",
				"_BombSpawn01", "_BombSpawn02", "_BombSpawn03" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", null,
				null, null });
		Aircraft.weaponsRegister(class1, "2sc250", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500",
				"BombGunSC250", "BombGunSC250", null });
		Aircraft.weaponsRegister(class1, "2ab250", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500",
				"BombGunAB250", "BombGunAB250", null });
		Aircraft.weaponsRegister(class1, "2sc500", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500",
				"BombGunSC500", "BombGunSC500", null });
		Aircraft.weaponsRegister(class1, "2ab500", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500",
				"BombGunAB500", "BombGunAB500", null });
		Aircraft.weaponsRegister(class1, "2sd500", new String[] {
				"MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325",
				"MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500",
				"BombGunSD500", "BombGunSD500", null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null });
	}

	public ME_210CA1() {
	}

	protected void nextCUTLevel(String s, int i, Actor actor) {
		super.nextCUTLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			bChangedPit = true;
		}
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			bChangedPit = true;
		}
	}

	public void typeDiveBomberAdjAltitudeMinus() {
	}

	public void typeDiveBomberAdjAltitudePlus() {
	}

	public void typeDiveBomberAdjAltitudeReset() {
	}

	public void typeDiveBomberAdjDiveAngleMinus() {
	}

	public void typeDiveBomberAdjDiveAnglePlus() {
	}

	public void typeDiveBomberAdjDiveAngleReset() {
	}

	public void typeDiveBomberAdjVelocityMinus() {
	}

	public void typeDiveBomberAdjVelocityPlus() {
	}

	public void typeDiveBomberAdjVelocityReset() {
	}

	public void typeDiveBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
	}

	public void typeDiveBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
	}

	public boolean typeDiveBomberToggleAutomation() {
		return false;
	}
}
