// Source File Name: TBM3AVENGER3.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.*;
import com.maddox.il2.game.*;
import com.maddox.rts.*;
import java.io.IOException;

public class TBM3AVENGER3 extends TBF {

	public TBM3AVENGER3() {
		bSightAutomation = false;
		bSightBombDump = false;
		fSightCurDistance = 0.0F;
		fSightCurForwardAngle = 0.0F;
		fSightCurSideslip = 0.0F;
		fSightCurAltitude = 3000F;
		fSightCurSpeed = 200F;
		fSightCurReadyness = 0.0F;
		flapps = 0.0F;
	}

	protected void moveFlap(float f) {
		float f1 = -38F * f;
		hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
		hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
		hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
		hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
	}

	protected void moveWingFold(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, -100F * f, 0.0F);
		hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, -100F * f, 0.0F);
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (FM.isPlayers())
			bChangedPit = true;
	}

	protected void nextCUTLevel(String s, int i, Actor actor) {
		super.nextCUTLevel(s, i, actor);
		if (FM.isPlayers())
			bChangedPit = true;
	}

	public void moveCockpitDoor(float f) {
		resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.625F);
		Aircraft.xyz[2] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.06845F);
		Aircraft.ypr[2] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 1.0F);
		hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
		resetYPRmodifier();
		Aircraft.xyz[2] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.13F);
		Aircraft.ypr[2] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -8F);
		hierMesh().chunkSetLocate("Pilot1_D0", Aircraft.xyz, Aircraft.ypr);
		if (Config.isUSE_RENDER()) {
			if (Main3D.cur3D().cockpits != null
					&& Main3D.cur3D().cockpits[0] != null)
				Main3D.cur3D().cockpits[0].onDoorMoved(f);
			setDoorSnd(f);
		}
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			if (f1 > 89F) {
				f1 = 89F;
				flag = false;
			}
			if (f1 < -30F) {
				f1 = -30F;
				flag = false;
			}
			float f2 = Math.abs(f);
			if (f1 < Aircraft.cvt(f2, 137F, 180F, -1F, 46F))
				f1 = Aircraft.cvt(f2, 137F, 180F, -1F, 46F);
			break;

		case 1: // '\001'
			if (f < -23F) {
				f = -23F;
				flag = false;
			}
			if (f > 39F) {
				f = 39F;
				flag = false;
			}
			if (f1 < -60F) {
				f1 = -60F;
				flag = false;
			}
			if (f1 > 31F) {
				f1 = 31F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	public void update(float f) {
		super.update(f);
		float f1 = FM.EI.engines[0].getControlRadiator();
		if (Math.abs(flapps - f1) > 0.01F) {
			flapps = f1;
			for (int i = 1; i < 9; i++)
				hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F,
						22.2F * f1, 0.0F);

		}
	}

	private static final float toMeters(float f) {
		return 0.3048F * f;
	}

	private static final float toMetersPerSecond(float f) {
		return 0.4470401F * f;
	}

	public boolean typeBomberToggleAutomation() {
		bSightAutomation = !bSightAutomation;
		bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (bSightAutomation ? "ON" : "OFF"));
		return bSightAutomation;
	}

	public void typeBomberAdjDistanceReset() {
		fSightCurDistance = 0.0F;
		fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjDistancePlus() {
		fSightCurForwardAngle++;
		if (fSightCurForwardAngle > 87F)
			fSightCurForwardAngle = 87F;
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) fSightCurForwardAngle) });
		if (bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjDistanceMinus() {
		fSightCurForwardAngle--;
		if (fSightCurForwardAngle < -45F)
			fSightCurForwardAngle = -45F;
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) fSightCurForwardAngle) });
		if (bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjSideslipReset() {
		fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSideslipPlus() {
		fSightCurSideslip += 0.1F;
		if (fSightCurSideslip > 3F)
			fSightCurSideslip = 3F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer((int) (fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipMinus() {
		fSightCurSideslip -= 0.1F;
		if (fSightCurSideslip < -3F)
			fSightCurSideslip = -3F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer((int) (fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjAltitudeReset() {
		fSightCurAltitude = 3000F;
	}

	public void typeBomberAdjAltitudePlus() {
		fSightCurAltitude += 10F;
		if (fSightCurAltitude > 50000F)
			fSightCurAltitude = 50000F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) fSightCurAltitude) });
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudeMinus() {
		fSightCurAltitude -= 10F;
		if (fSightCurAltitude < 500F)
			fSightCurAltitude = 500F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) fSightCurAltitude) });
		fSightCurDistance = toMeters(fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(fSightCurForwardAngle));
	}

	public void typeBomberAdjSpeedReset() {
		fSightCurSpeed = 200F;
	}

	public void typeBomberAdjSpeedPlus() {
		fSightCurSpeed += 2.0F;
		if (fSightCurSpeed > 450F)
			fSightCurSpeed = 450F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedMinus() {
		fSightCurSpeed -= 2.0F;
		if (fSightCurSpeed < 100F)
			fSightCurSpeed = 100F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) fSightCurSpeed) });
	}

	public void typeBomberUpdate(float f) {
		if ((double) Math.abs(FM.Or.getKren()) > 4.5D) {
			fSightCurReadyness -= 0.0666666F * f;
			if (fSightCurReadyness < 0.0F)
				fSightCurReadyness = 0.0F;
		}
		if (fSightCurReadyness < 1.0F)
			fSightCurReadyness += 0.0333333F * f;
		else if (bSightAutomation) {
			fSightCurDistance -= toMetersPerSecond(fSightCurSpeed) * f;
			if (fSightCurDistance < 0.0F) {
				fSightCurDistance = 0.0F;
				typeBomberToggleAutomation();
			}
			fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(fSightCurDistance / toMeters(fSightCurAltitude)));
			if ((double) fSightCurDistance < (double) toMetersPerSecond(fSightCurSpeed)
					* Math.sqrt(toMeters(fSightCurAltitude) * 0.2038736F))
				bSightBombDump = true;
			if (bSightBombDump)
				if (FM.isTick(3, 0)) {
					if (FM.CT.Weapons[3] != null
							&& FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null
							&& FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1]
									.haveBullets()) {
						FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else {
					FM.CT.WeaponControl[3] = false;
				}
		}
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeByte((bSightAutomation ? 1 : 0)
				| (bSightBombDump ? 2 : 0));
		netmsgguaranted.writeFloat(fSightCurDistance);
		netmsgguaranted.writeByte((int) fSightCurForwardAngle);
		netmsgguaranted.writeByte((int) ((fSightCurSideslip + 3F) * 33.33333F));
		netmsgguaranted.writeFloat(fSightCurAltitude);
		netmsgguaranted.writeByte((int) (fSightCurSpeed / 2.5F));
		netmsgguaranted.writeByte((int) (fSightCurReadyness * 200F));
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		int i = netmsginput.readUnsignedByte();
		bSightAutomation = (i & 1) != 0;
		bSightBombDump = (i & 2) != 0;
		fSightCurDistance = netmsginput.readFloat();
		fSightCurForwardAngle = netmsginput.readUnsignedByte();
		fSightCurSideslip = -3F + (float) netmsginput.readUnsignedByte()
				/ 33.33333F;
		fSightCurAltitude = netmsginput.readFloat();
		fSightCurSpeed = (float) netmsginput.readUnsignedByte() * 2.5F;
		fSightCurReadyness = (float) netmsginput.readUnsignedByte() / 200F;
	}

	public static boolean bChangedPit = false;
	private boolean bSightAutomation;
	private boolean bSightBombDump;
	private float fSightCurDistance;
	public float fSightCurForwardAngle;
	public float fSightCurSideslip;
	public float fSightCurAltitude;
	public float fSightCurSpeed;
	public float fSightCurReadyness;
	private float flapps;

	static {
		Class class1 = com.maddox.il2.objects.air.TBM3AVENGER3.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "TBF");
		Property.set(class1, "meshName",
				"3DO/Plane/TBM-3(A_MkIII)(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
		Property.set(class1, "meshName_gb",
				"3DO/Plane/TBM-3(A_MkIII)(GB)/hier.him");
		Property.set(class1, "PaintScheme_gb", new PaintSchemeFCSPar02());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/TBM-3.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				com.maddox.il2.objects.air.CockpitTBX1.class,
				com.maddox.il2.objects.air.CockpitTBX1_TGunner.class,
				com.maddox.il2.objects.air.CockpitTBX1_BGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 10, 11, 2, 2,
				2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01",
				"_ExternalRock02", "_ExternalRock03", "_ExternalRock04",
				"_ExternalRock05", "_ExternalRock06", "_ExternalRock07",
				"_ExternalRock08", "_BombSpawn01", "_BombSpawn02",
				"_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06",
				"_BombSpawn07" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "8xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "8xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "4x100", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1",
				"BombGun100Lbs 1" });
		Aircraft.weaponsRegister(class1, "4x1008xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, null, null,
				"BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1",
				"BombGun100Lbs 1" });
		Aircraft.weaponsRegister(class1, "4x1008xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null,
				"BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1",
				"BombGun100Lbs 1" });
		Aircraft.weaponsRegister(class1, "2x250", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x2508xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x2508xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null,
				"BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4x250", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1" });
		Aircraft.weaponsRegister(class1, "2x500", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun500lbs 1",
				"BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x5008xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun500lbs 1",
				"BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x5008xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null,
				"BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4x500", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1",
				"BombGun500lbs 1" });
		Aircraft.weaponsRegister(class1, "2x1000", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun1000lbs 1",
				"BombGun1000lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x1600", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGun1600lbs 1", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x2000", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGun2000lbs 1", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1xmk13", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGunTorpMk13 1", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null });
	}
}
