// Source File Name: B_24J100.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-04
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class B_24J100 extends B_24 implements TypeBomber, TypeX4Carrier,
		TypeGuidedBombCarrier {

	private static final float toMeters(float f) {
		return 0.3048F * f;
	}

	private static final float toMetersPerSecond(float f) {
		return 0.4470401F * f;
	}

	public boolean bToFire;

	private float deltaAzimuth;

	private float deltaTangage;

	private boolean isGuidingBomb;

	private boolean isMasterAlive;

	public static boolean bChangedPit = false;

	private boolean bSightAutomation;

	private boolean bSightBombDump;

	public float fSightCurDistance;

	public float fSightCurForwardAngle;

	public float fSightCurSideslip;

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurReadyness;

	private float calibDistance;

	static final float calibrationScale[] = { 0.0F, 0.2F, 0.4F, 0.66F, 0.86F,
			1.05F, 1.2F, 1.6F };

	static {
		Class class1 = B_24J100.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B-24");
		Property.set(class1, "meshName",
				"3DO/Plane/B-24J-100-CF(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
		Property.set(class1, "meshName_us",
				"3DO/Plane/B-24J-100-CF(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
		Property.set(class1, "noseart", 1);
		Property.set(class1, "yearService", 1943.5F);
		Property.set(class1, "yearExpired", 2800.9F);
		Property.set(class1, "FlightModel", "FlightModels/B-24J.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				CockpitB_24J100.class, CockpitB_24J100_Bombardier.class,
				CockpitB_24J100_FGunner.class, CockpitB_24J100_TGunner.class,
				CockpitB_24J100_AGunner.class, CockpitB_24J100_BGunner.class,
				CockpitB_24J100_RGunner.class, CockpitB_24J100_LGunner.class });
		weaponTriggersRegister(class1, new int[] { 10, 10, 11, 11, 12, 12, 13,
				14, 15, 15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
		weaponHooksRegister(class1, new String[] { "_MGUN01", "_MGUN02",
				"_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07",
				"_MGUN08", "_MGUN09", "_MGUN10", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05",
				"_BombSpawn06", "_BombSpawn07", "_BombSpawn08",
				"_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03",
				"_ExternalBomb04" });
		weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50t 365", "MGunBrowning50t 365",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null,
				null, null, null, null, null, null, null, null, null });
		weaponsRegister(class1, "16x500", new String[] { "MGunBrowning50t 365",
				"MGunBrowning50t 365", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 500",
				"MGunBrowning50t 500", "BombGun500lbs 2", "BombGun500lbs 2",
				"BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2",
				"BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", null,
				null, null, null });
		weaponsRegister(class1, "16xRazon",
				new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365",
						"MGunBrowning50t 610", "MGunBrowning50t 610",
						"MGunBrowning50t 610", "MGunBrowning50t 610",
						"MGunBrowning50t 375", "MGunBrowning50t 375",
						"MGunBrowning50t 500", "MGunBrowning50t 500",
						"RocketGunRazon 2", "RocketGunRazon 2",
						"RocketGunRazon 2", "RocketGunRazon 2",
						"RocketGunRazon 2", "RocketGunRazon 2",
						"RocketGunRazon 2", "RocketGunRazon 2", null, null,
						null, null });
		weaponsRegister(class1, "8xRazon", new String[] {
				"MGunBrowning50t 365", "MGunBrowning50t 365",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 500", "MGunBrowning50t 500",
				"RocketGunRazon 2", "RocketGunRazon 2", "RocketGunRazon 2",
				"RocketGunRazon 2", null, null, null, null, null, null, null,
				null });
		weaponsRegister(class1, "2xBat", new String[] { "MGunBrowning50t 365",
				"MGunBrowning50t 365", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 500",
				"MGunBrowning50t 500", null, null, null, null, null, null,
				null, null, "RocketGunBat 1", "BombGunNull 1", "BombGunNull 1",
				"RocketGunBat 1" });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null });
	}

	public B_24J100() {
		this.bToFire = false;
		this.deltaAzimuth = 0.0F;
		this.deltaTangage = 0.0F;
		this.isGuidingBomb = false;
		this.bSightAutomation = false;
		this.bSightBombDump = false;
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
		this.fSightCurAltitude = 3000F;
		this.fSightCurSpeed = 200F;
		this.fSightCurReadyness = 0.0F;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 19: // '\023'
			this.killPilot(this, 4);
			break;
		}
		return super.cutFM(i, j, actor);
	}

	protected float floatindex(float f, float af[]) {
		int i = (int) f;
		if (i >= (af.length - 1)) {
			return af[af.length - 1];
		}
		if (i < 0) {
			return af[0];
		}
		if (i == 0) {
			if (f > 0.0F) {
				return af[0] + (f * (af[1] - af[0]));
			} else {
				return af[0];
			}
		} else {
			return af[i] + ((f % i) * (af[i + 1] - af[i]));
		}
	}

	protected void mydebug(String s) {
		System.out.println(s);
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.AS.wantBeaconsNet(true);
		if (this.thisWeaponsName.endsWith("Bat")) {
			this.hierMesh().chunkVisible("BatWingRackR_D0", true);
			this.hierMesh().chunkVisible("BatWingRackL_D0", true);
			return;
		} else {
			return;
		}
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			if (f < -85F) {
				f = -85F;
				flag = false;
			}
			if (f > 85F) {
				f = 85F;
				flag = false;
			}
			if (f1 < -32F) {
				f1 = -32F;
				flag = false;
			}
			if (f1 > 46F) {
				f1 = 46F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f1 < -0F) {
				f1 = -0F;
				flag = false;
			}
			if (f1 > 20F) {
				f1 = 20F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f1 < -70F) {
				f1 = -70F;
				flag = false;
			}
			if (f1 > 7F) {
				f1 = 7F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f < -35F) {
				f = -35F;
				flag = false;
			}
			if (f > 64F) {
				f = 64F;
				flag = false;
			}
			if (f1 < -37F) {
				f1 = -37F;
				flag = false;
			}
			if (f1 > 50F) {
				f1 = 50F;
				flag = false;
			}
			break;

		case 4: // '\004'
			if (f < -67F) {
				f = -67F;
				flag = false;
			}
			if (f > 34F) {
				f = 34F;
				flag = false;
			}
			if (f1 < -37F) {
				f1 = -37F;
				flag = false;
			}
			if (f1 > 50F) {
				f1 = 50F;
				flag = false;
			}
			break;

		case 5: // '\005'
			if (f < -85F) {
				f = -85F;
				flag = false;
			}
			if (f > 85F) {
				f = 85F;
				flag = false;
			}
			if (f1 < -32F) {
				f1 = -32F;
				flag = false;
			}
			if (f1 > 46F) {
				f1 = 46F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 50F;
		if (this.fSightCurAltitude < 1000F) {
			this.fSightCurAltitude = 1000F;
		}
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
					new Object[] { new Integer((int) this.fSightCurAltitude) });
		}
		this.fSightCurDistance = toMeters(this.fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 50F;
		if (this.fSightCurAltitude > 50000F) {
			this.fSightCurAltitude = 50000F;
		}
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
					new Object[] { new Integer((int) this.fSightCurAltitude) });
		}
		this.fSightCurDistance = toMeters(this.fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 3000F;
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle--;
		if (this.fSightCurForwardAngle < 0.0F) {
			this.fSightCurForwardAngle = 0.0F;
		}
		this.fSightCurDistance = toMeters(this.fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId,
					"BombsightElevation",
					new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		}
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle++;
		if (this.fSightCurForwardAngle > 85F) {
			this.fSightCurForwardAngle = 85F;
		}
		this.fSightCurDistance = toMeters(this.fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId,
					"BombsightElevation",
					new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		}
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		if (!this.isGuidingBomb) {
			this.fSightCurSideslip -= 0.1F;
			if (this.fSightCurSideslip < -3F) {
				this.fSightCurSideslip = -3F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
					new Object[] { new Integer(
							(int) (this.fSightCurSideslip * 10F)) });
		}
	}

	public void typeBomberAdjSideslipPlus() {
		if (!this.isGuidingBomb) {
			this.fSightCurSideslip += 0.1F;
			if (this.fSightCurSideslip > 3F) {
				this.fSightCurSideslip = 3F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip",
					new Object[] { new Integer(
							(int) (this.fSightCurSideslip * 10F)) });
		}
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 10F;
		if (this.fSightCurSpeed < 100F) {
			this.fSightCurSpeed = 100F;
		}
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
					new Object[] { new Integer((int) this.fSightCurSpeed) });
		}
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 10F;
		if (this.fSightCurSpeed > 450F) {
			this.fSightCurSpeed = 450F;
		}
		if (!this.isGuidingBomb) {
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
					new Object[] { new Integer((int) this.fSightCurSpeed) });
		}
	}

	public void typeBomberAdjSpeedReset() {
		this.fSightCurSpeed = 200F;
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		int i = netmsginput.readUnsignedByte();
		this.bSightAutomation = (i & 1) != 0;
		this.bSightBombDump = (i & 2) != 0;
		this.fSightCurDistance = netmsginput.readFloat();
		this.fSightCurForwardAngle = netmsginput.readUnsignedByte();
		this.fSightCurSideslip = -3F
				+ (netmsginput.readUnsignedByte() / 33.33333F);
		this.fSightCurAltitude = netmsginput.readFloat();
		this.fSightCurSpeed = netmsginput.readUnsignedByte() * 2.5F;
		this.fSightCurReadyness = netmsginput.readUnsignedByte() / 200F;
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeByte((this.bSightAutomation ? 1 : 0)
				| (this.bSightBombDump ? 2 : 0));
		netmsgguaranted.writeFloat(this.fSightCurDistance);
		netmsgguaranted.writeByte((int) this.fSightCurForwardAngle);
		netmsgguaranted
				.writeByte((int) ((this.fSightCurSideslip + 3F) * 33.33333F));
		netmsgguaranted.writeFloat(this.fSightCurAltitude);
		netmsgguaranted.writeByte((int) (this.fSightCurSpeed / 2.5F));
		netmsgguaranted.writeByte((int) (this.fSightCurReadyness * 200F));
	}

	public boolean typeBomberToggleAutomation() {
		this.bSightAutomation = !this.bSightAutomation;
		this.bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (this.bSightAutomation ? "ON" : "OFF"));
		return this.bSightAutomation;
	}

	public void typeBomberUpdate(float f) {
		if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
			this.fSightCurReadyness -= 0.0666666F * f;
			if (this.fSightCurReadyness < 0.0F) {
				this.fSightCurReadyness = 0.0F;
			}
		}
		if (this.fSightCurReadyness < 1.0F) {
			this.fSightCurReadyness += 0.0333333F * f;
		} else if (this.bSightAutomation) {
			this.fSightCurDistance -= toMetersPerSecond(this.fSightCurSpeed)
					* f;
			if (this.fSightCurDistance < 0.0F) {
				this.fSightCurDistance = 0.0F;
				this.typeBomberToggleAutomation();
			}
			this.fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(this.fSightCurDistance
							/ toMeters(this.fSightCurAltitude)));
			this.calibDistance = toMetersPerSecond(this.fSightCurSpeed)
					* this.floatindex(Aircraft.cvt(
							toMeters(this.fSightCurAltitude), 0.0F, 7000F,
							0.0F, 7F), calibrationScale);
			if (this.fSightCurDistance < (this.calibDistance + (toMetersPerSecond(this.fSightCurSpeed) * Math
					.sqrt(toMeters(this.fSightCurAltitude) * 0.2038736F)))) {
				this.bSightBombDump = true;
			}
			if (this.bSightBombDump) {
				if (this.FM.isTick(3, 0)) {
					if ((this.FM.CT.Weapons[3] != null)
							&& (this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1] != null)
							&& this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1]
									.haveBullets()) {
						this.FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else {
					this.FM.CT.WeaponControl[3] = false;
				}
			}
		}
	}

	public boolean typeGuidedBombCgetIsGuiding() {
		return this.isGuidingBomb;
	}

	public boolean typeGuidedBombCisMasterAlive() {
		return this.isMasterAlive;
	}

	public void typeGuidedBombCsetIsGuiding(boolean flag) {
		this.isGuidingBomb = flag;
	}

	public void typeGuidedBombCsetMasterAlive(boolean flag) {
		this.isMasterAlive = flag;
	}

	public void typeX4CAdjAttitudeMinus() {
		this.deltaTangage = -0.002F;
	}

	public void typeX4CAdjAttitudePlus() {
		this.deltaTangage = 0.002F;
	}

	public void typeX4CAdjSideMinus() {
		this.deltaAzimuth = -0.002F;
	}

	public void typeX4CAdjSidePlus() {
		this.deltaAzimuth = 0.002F;
	}

	public float typeX4CgetdeltaAzimuth() {
		return this.deltaAzimuth;
	}

	public float typeX4CgetdeltaTangage() {
		return this.deltaTangage;
	}

	public void typeX4CResetControls() {
		this.deltaAzimuth = this.deltaTangage = 0.0F;
	}
}
