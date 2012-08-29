// Source File Name: MOSQUITO4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class MOSQUITO4 extends MOSQUITO implements TypeBomber {
	public static boolean bChangedPit = false;
	private float calibDistance;
	private boolean bSightAutomation;
	private boolean bSightBombDump;
	private float fSightCurDistance;
	public float fSightCurForwardAngle;
	public float fSightCurSideslip;
	public float fSightCurAltitude;
	public float fSightCurSpeed;
	public float fSightCurReadyness;
	public float fSightSetForwardAngle;
	static final float[] calibrationScale = { 0.0F, 0.2F, 0.4F, 0.66F, 0.86F,
			1.05F, 1.2F, 1.6F };

	public MOSQUITO4() {
		this.calibDistance = 0.0F;
		this.bSightAutomation = false;
		this.bSightBombDump = false;
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
		this.fSightCurAltitude = 3000.0F;
		this.fSightCurSpeed = 200.0F;
		this.fSightCurReadyness = 0.0F;
	}

	private static final float toMeters(float paramFloat) {
		return 0.3048F * paramFloat;
	}

	private static final float toMetersPerSecond(float paramFloat) {
		return 0.4470401F * paramFloat;
	}

	public boolean typeBomberToggleAutomation() {
		this.bSightAutomation = (!this.bSightAutomation);
		this.bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (this.bSightAutomation ? "ON" : "OFF"));
		return this.bSightAutomation;
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle += 1.0F;
		if (this.fSightCurForwardAngle > 85.0F)
			this.fSightCurForwardAngle = 85.0F;
		this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float) Math
				.tan(Math.toRadians(this.fSightCurForwardAngle)));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });

		if (this.bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle -= 1.0F;
		if (this.fSightCurForwardAngle < 0.0F)
			this.fSightCurForwardAngle = 0.0F;
		this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float) Math
				.tan(Math.toRadians(this.fSightCurForwardAngle)));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });

		if (this.bSightAutomation)
			typeBomberToggleAutomation();
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip += 0.1F;
		if (this.fSightCurSideslip > 3.0F)
			this.fSightCurSideslip = 3.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer(
						(int) (this.fSightCurSideslip * 10.0F)) });
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip -= 0.1F;
		if (this.fSightCurSideslip < -3.0F)
			this.fSightCurSideslip = -3.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
				new Object[] { new Integer(
						(int) (this.fSightCurSideslip * 10.0F)) });
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 3000.0F;
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 50.0F;
		if (this.fSightCurAltitude > 50000.0F)
			this.fSightCurAltitude = 50000.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) this.fSightCurAltitude) });

		this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float) Math
				.tan(Math.toRadians(this.fSightCurForwardAngle)));
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 50.0F;
		if (this.fSightCurAltitude < 1000.0F)
			this.fSightCurAltitude = 1000.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) this.fSightCurAltitude) });

		this.fSightCurDistance = (toMeters(this.fSightCurAltitude) * (float) Math
				.tan(Math.toRadians(this.fSightCurForwardAngle)));
	}

	public void typeBomberAdjSpeedReset() {
		this.fSightCurSpeed = 200.0F;
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 10.0F;
		if (this.fSightCurSpeed > 450.0F)
			this.fSightCurSpeed = 450.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 10.0F;
		if (this.fSightCurSpeed < 100.0F)
			this.fSightCurSpeed = 100.0F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberUpdate(float paramFloat) {
		if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
			this.fSightCurReadyness -= 0.0666666F * paramFloat;
			if (this.fSightCurReadyness < 0.0F)
				this.fSightCurReadyness = 0.0F;
		}
		if (this.fSightCurReadyness < 1.0F) {
			this.fSightCurReadyness += 0.0333333F * paramFloat;
		} else if (this.bSightAutomation) {
			this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed)
					* paramFloat;
			if (this.fSightCurDistance < 0.0F) {
				this.fSightCurDistance = 0.0F;
				typeBomberToggleAutomation();
			}
			this.fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(this.fSightCurDistance
							/ toMeters(this.fSightCurAltitude)));
			this.calibDistance = (toMetersPerSecond(this.fSightCurSpeed) * floatindex(
					Aircraft.cvt(toMeters(this.fSightCurAltitude), 0.0F,
							7000.0F, 0.0F, 7.0F), calibrationScale));
			if (this.fSightCurDistance < this.calibDistance
					+ toMetersPerSecond(this.fSightCurSpeed)
					* Math.sqrt(toMeters(this.fSightCurAltitude) * 0.203874F))
				this.bSightBombDump = true;
			if (this.bSightBombDump)
				if (this.FM.isTick(3, 0)) {
					if ((this.FM.CT.Weapons[3] != null)
							&& (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null)
							&& (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)]
									.haveBullets())) {
						this.FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else
					this.FM.CT.WeaponControl[3] = false;
		}
	}

	protected float floatindex(float paramFloat, float[] paramArrayOfFloat) {
		int i = (int) paramFloat;
		if (i >= paramArrayOfFloat.length - 1)
			return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
		if (i < 0)
			return paramArrayOfFloat[0];
		if (i == 0) {
			if (paramFloat > 0.0F) {
				return paramArrayOfFloat[0] + paramFloat
						* (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
			}
			return paramArrayOfFloat[0];
		}

		return paramArrayOfFloat[i] + paramFloat % i
				* (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
			throws IOException {
		paramNetMsgGuaranted.writeByte((this.bSightAutomation ? 1 : 0)
				| (this.bSightBombDump ? 2 : 0));
		paramNetMsgGuaranted.writeFloat(this.fSightCurDistance);
		paramNetMsgGuaranted.writeByte((int) this.fSightCurForwardAngle);
		paramNetMsgGuaranted
				.writeByte((int) ((this.fSightCurSideslip + 3.0F) * 33.333328F));
		paramNetMsgGuaranted.writeFloat(this.fSightCurAltitude);
		paramNetMsgGuaranted.writeByte((int) (this.fSightCurSpeed / 2.5F));
		paramNetMsgGuaranted
				.writeByte((int) (this.fSightCurReadyness * 200.0F));
	}

	public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
			throws IOException {
		int i = paramNetMsgInput.readUnsignedByte();
		this.bSightAutomation = ((i & 0x1) != 0);
		this.bSightBombDump = ((i & 0x2) != 0);
		this.fSightCurDistance = paramNetMsgInput.readFloat();
		this.fSightCurForwardAngle = paramNetMsgInput.readUnsignedByte();
		this.fSightCurSideslip = (-3.0F + paramNetMsgInput.readUnsignedByte() / 33.333328F);
		this.fSightCurAltitude = paramNetMsgInput.readFloat();
		this.fSightCurSpeed = (paramNetMsgInput.readUnsignedByte() * 2.5F);
		this.fSightCurReadyness = (paramNetMsgInput.readUnsignedByte() / 200.0F);
	}

	static {
		Class localClass = MOSQUITO4.class;
		new NetAircraft.SPAWN(localClass);
		Property.set(localClass, "iconFar_shortClassName", "Mosquito");
		Property.set(localClass, "meshName",
				"3DO/Plane/Mosquito_B_MkIV(Multi1)/hier.him");
		Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
		Property.set(localClass, "meshName_gb",
				"3DO/Plane/Mosquito_B_MkIV(GB)/hier.him");
		Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar06());
		Property.set(localClass, "yearService", 1941.0F);
		Property.set(localClass, "yearExpired", 1946.5F);
		Property.set(localClass, "FlightModel",
				"FlightModels/Mosquito-BMkIV.fmd");
		Property.set(localClass, "cockpitClass", new Class[] {
				CockpitMosquito4.class, CockpitMOSQUITO4_Bombardier.class });

		Property.set(localClass, "LOSElevation", 0.76315F);
		Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 3, 3, 3, 3 });

		Aircraft.weaponHooksRegister(localClass,
				new String[] { "_Clip04", "_BombSpawn01", "_BombSpawn02",
						"_BombSpawn03", "_BombSpawn04" });

		Aircraft.weaponsRegister(localClass, "default", new String[] { null,
				null, null, null, null });

		Aircraft.weaponsRegister(localClass, "4x250", new String[] { null,
				"BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1",
				"BombGun250lbsE 1" });

		Aircraft.weaponsRegister(localClass, "4x500", new String[] { null,
				"BombGun500lbsE 1", "BombGun500lbsE 1", "BombGun500lbsE 1",
				"BombGun500lbsE 1" });

		Aircraft.weaponsRegister(localClass, "none", new String[] { null, null,
				null, null, null });
	}
}