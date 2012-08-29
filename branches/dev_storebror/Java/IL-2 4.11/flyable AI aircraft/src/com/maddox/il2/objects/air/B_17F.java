// Source File Name: B_17F.java
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

public class B_17F extends B_17 implements TypeBomber {

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

	static final float calibrationScale[] = { 0.0F, 0.2F, 0.4F, 0.66F, 0.86F,
			1.05F, 1.2F, 1.6F };

	static {
		Class class1 = B_17F.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B-17");
		Property.set(class1, "meshName", "3DO/Plane/B-17F(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
		Property.set(class1, "meshName_us", "3DO/Plane/B-17F(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar03());
		Property.set(class1, "noseart", 1);
		Property.set(class1, "yearService", 1942F);
		Property.set(class1, "yearExpired", 2800.9F);
		Property.set(class1, "FlightModel", "FlightModels/B-17F.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitB17F.class,
				CockpitB17F_Bombardier.class, CockpitB17F_FGunner.class,
				CockpitB17F_TGunner.class, CockpitB17F_RGunner.class,
				CockpitB17F_LGunner.class, CockpitB17F_AGunner.class,
				CockpitB17F_BGunner.class });
		weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 13, 14, 14,
				15, 16, 17, 17, 3, 3 });
		weaponHooksRegister(class1, new String[] { "_MGUN02", "_MGUN03",
				"_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08",
				"_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_BombSpawn01",
				"_BombSpawn02" });
		weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 375",
				"MGunBrowning50t 375", "MGunBrowning50t 500",
				"MGunBrowning50t 500", "MGunBrowning50t 600",
				"MGunBrowning50t 600", "MGunBrowning50t 500",
				"MGunBrowning50t 500", null, null });
		weaponsRegister(class1, "16x500", new String[] { "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 500", "MGunBrowning50t 500",
				"MGunBrowning50t 600", "MGunBrowning50t 600",
				"MGunBrowning50t 500", "MGunBrowning50t 500",
				"BombGun500lbs 8", "BombGun500lbs 8" });
		weaponsRegister(class1, "8x1000", new String[] { "MGunBrowning50t 610",
				"MGunBrowning50t 610", "MGunBrowning50t 610",
				"MGunBrowning50t 375", "MGunBrowning50t 375",
				"MGunBrowning50t 500", "MGunBrowning50t 500",
				"MGunBrowning50t 600", "MGunBrowning50t 600",
				"MGunBrowning50t 500", "MGunBrowning50t 500",
				"BombGun1000lbs 4", "BombGun1000lbs 4" });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null });
	}

	private static final float toMeters(float f) {
		return 0.3048F * f;
	}

	private static final float toMetersPerSecond(float f) {
		return 0.4470401F * f;
	}

	public B_17F() {
		this.calibDistance = 0.0F;
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

	public void doWoundPilot(int i, float f) {
		switch (i) {
		case 2: // '\002'
			this.FM.turret[0].setHealth(f);
			break;

		case 3: // '\003'
			this.FM.turret[1].setHealth(f);
			break;

		case 4: // '\004'
			this.FM.turret[2].setHealth(f);
			break;

		case 5: // '\005'
			this.FM.turret[3].setHealth(f);
			this.FM.turret[4].setHealth(f);
			break;
		}
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

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.AS.wantBeaconsNet(true);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		for (int i = 1; i < 7; i++) {
			if (this.FM.getAltitude() < 3000F) {
				this.hierMesh().chunkVisible("HMask" + i + "_D0", false);
			} else {
				this.hierMesh().chunkVisible("HMask" + i + "_D0",
						this.hierMesh().isChunkVisible("Pilot" + i + "_D0"));
			}
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
			if (f < -23F) {
				f = -23F;
				flag = false;
			}
			if (f > 23F) {
				f = 23F;
				flag = false;
			}
			if (f1 < -15F) {
				f1 = -15F;
				flag = false;
			}
			if (f1 > 15F) {
				f1 = 15F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -20F) {
				f = -20F;
				flag = false;
			}
			if (f > 20F) {
				f = 20F;
				flag = false;
			}
			if (f1 < -20F) {
				f1 = -20F;
				flag = false;
			}
			if (f1 > 20F) {
				f1 = 20F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -20F) {
				f = -20F;
				flag = false;
			}
			if (f > 20F) {
				f = 20F;
				flag = false;
			}
			if (f1 < -20F) {
				f1 = -20F;
				flag = false;
			}
			if (f1 > 20F) {
				f1 = 20F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f1 < -3F) {
				f1 = -3F;
				flag = false;
			}
			if (f1 > 66F) {
				f1 = 66F;
				flag = false;
			}
			break;

		case 4: // '\004'
			if (f1 < -75F) {
				f1 = -75F;
				flag = false;
			}
			if (f1 > 6F) {
				f1 = 6F;
				flag = false;
			}
			break;

		case 5: // '\005'
			if (f < -70F) {
				f = -70F;
				flag = false;
			}
			if (f > 54F) {
				f = 54F;
				flag = false;
			}
			if (f1 < -17F) {
				f1 = -17F;
				flag = false;
			}
			if (f1 > 43F) {
				f1 = 43F;
				flag = false;
			}
			break;

		case 6: // '\006'
			if (f < -63F) {
				f = -63F;
				flag = false;
			}
			if (f > 15F) {
				f = 15F;
				flag = false;
			}
			if (f1 < -17F) {
				f1 = -17F;
				flag = false;
			}
			if (f1 > 43F) {
				f1 = 43F;
				flag = false;
			}
			break;

		case 7: // '\007'
			if (f < -25F) {
				f = -25F;
				flag = false;
			}
			if (f > 25F) {
				f = 25F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 25F) {
				f1 = 25F;
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
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
		this.fSightCurDistance = toMeters(this.fSightCurAltitude)
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 50F;
		if (this.fSightCurAltitude > 50000F) {
			this.fSightCurAltitude = 50000F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
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
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
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
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip -= 0.1F;
		if (this.fSightCurSideslip < -3F) {
			this.fSightCurSideslip = -3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip += 0.1F;
		if (this.fSightCurSideslip > 3F) {
			this.fSightCurSideslip = 3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 10F;
		if (this.fSightCurSpeed < 100F) {
			this.fSightCurSpeed = 100F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 10F;
		if (this.fSightCurSpeed > 450F) {
			this.fSightCurSpeed = 450F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
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
}
