// Source File Name: R_10.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class R_10 extends Scheme1 implements TypeScout, TypeBomber,
		TypeStormovik {

	public static boolean bChangedPit = false;

	static {
		Class class1 = R_10.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "R-10");
		Property.set(class1, "meshName", "3DO/Plane/R-10/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "originCountry", PaintScheme.countryRussia);
		Property.set(class1, "yearService", 1940F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/R-10.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitR_10.class,
				CockpitR10_TGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 10, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASk 750", "MGunShKASk 750", "MGunShKASt 250", null,
				null });
		Aircraft.weaponsRegister(class1, "6xFAB50", new String[] {
				"MGunShKASk 750", "MGunShKASk 750", "MGunShKASt 250",
				"BombGunFAB50 3", "BombGunFAB50 3" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null });
	}

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 90F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 90F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -90F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -90F * f, 0.0F);
	}

	public R_10() {
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
		}
	}

	protected void hitBone(String s, Shot shot, Point3d point3d) {
		if (s.startsWith("xpilot") || s.startsWith("xhead")) {
			byte byte0 = 0;
			int i;
			if (s.endsWith("a")) {
				byte0 = 1;
				i = s.charAt(6) - 49;
			} else if (s.endsWith("b")) {
				byte0 = 2;
				i = s.charAt(6) - 49;
			} else {
				i = s.charAt(5) - 49;
			}
			this.hitFlesh(i, shot, byte0);
		}
	}

	protected void moveBayDoor(float f) {
		this.hierMesh().chunkSetAngles("Bay1_D0", 0.0F, -90F * f, 0.0F);
		this.hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
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

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (this.FM.getAltitude() < 3000F) {
			this.hierMesh().chunkVisible("HMask1_D0", false);
			this.hierMesh().chunkVisible("HMask2_D0", false);
		} else {
			this.hierMesh().chunkVisible("HMask1_D0",
					this.hierMesh().isChunkVisible("Pilot1_D0"));
			this.hierMesh().chunkVisible("HMask2_D0",
					this.hierMesh().isChunkVisible("Pilot2_D0"));
		}
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		if (af[0] < -142F) {
			af[0] = -142F;
			flag = false;
		} else if (af[0] > 142F) {
			af[0] = 142F;
			flag = false;
		}
		if (af[1] > 45F) {
			af[1] = 45F;
			flag = false;
		}
		if (!flag) {
			return false;
		}
		float f = Math.abs(af[0]);
		if ((f < 2.5F) && (af[1] < 20.8F)) {
			af[1] = 20.8F;
			return false;
		}
		if ((f < 21F) && (af[1] < 16.1F)) {
			af[1] = 16.1F;
			return false;
		}
		if ((f < 41F) && (af[1] < -8.5F)) {
			af[1] = -8.5F;
			return false;
		}
		if ((f < 103F) && (af[1] < -45F)) {
			af[1] = -45F;
			return false;
		}
		if ((f < 180F) && (af[1] < -7.8F)) {
			af[1] = -7.8F;
			return false;
		} else {
			return true;
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
