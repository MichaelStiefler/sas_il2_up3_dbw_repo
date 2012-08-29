// Source File Name: IL_4_DB3B.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class IL_4_DB3B extends IL_4 implements TypeBomber {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = IL_4_DB3B.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "DB-3");
		Property.set(class1, "meshName", "3DO/Plane/DB-3B/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
		Property.set(class1, "yearService", 1936F);
		Property.set(class1, "yearExpired", 1948F);
		Property.set(class1, "FlightModel", "FlightModels/DB-3B.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitDB3B.class,
				CockpitDB3B_Bombardier.class, CockpitDB3B_FGunner.class,
				CockpitDB3B_TGunner.class, CockpitDB3B_BGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 3, 3,
				3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01",
				"_BombSpawn02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "10fab50", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null,
				null, null, null, "BombGunFAB50 5", "BombGunFAB50 5" });
		Aircraft.weaponsRegister(class1, "10fab100", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null,
				null, null, null, "BombGunFAB100 5", "BombGunFAB100 5" });
		Aircraft.weaponsRegister(class1, "3fab250", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB250", "BombGunNull", "BombGunFAB250",
				"BombGunFAB250", null, null });
		Aircraft.weaponsRegister(class1, "3fab25010fab50", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB250", "BombGunNull", "BombGunFAB250",
				"BombGunFAB250", "BombGunFAB50 5", "BombGunFAB50 5" });
		Aircraft.weaponsRegister(class1, "3fab25010fab100", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB250", "BombGunNull", "BombGunFAB250",
				"BombGunFAB250", "BombGunFAB100 5", "BombGunFAB100 5" });
		Aircraft.weaponsRegister(class1, "1fab500", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB500", null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1fab5002fab250", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB500", "BombGunNull", "BombGunFAB250",
				"BombGunFAB250", null, null });
		Aircraft.weaponsRegister(class1, "3fab500", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB500", "BombGunNull", "BombGunFAB500",
				"BombGunFAB500", null, null });
		Aircraft.weaponsRegister(class1, "3fab50010fab50", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB500", "BombGunNull", "BombGunFAB500",
				"BombGunFAB500", "BombGunFAB50 5", "BombGunFAB50 5" });
		Aircraft.weaponsRegister(class1, "1fab1000", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB1000", null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1fab100010fab50", new String[] {
				"MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200",
				"BombGunFAB1000", "BombGunNull", null, null, "BombGunFAB50 5",
				"BombGunFAB50 5" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null });
	}

	public IL_4_DB3B() {
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	protected void hitBone(String s, Shot shot, Point3d point3d) {
		if (!s.startsWith("xxarmor")) {
			super.hitBone(s, shot, point3d);
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
