// Source File Name: B_25C25.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-04
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class B_25C25 extends B_25 implements TypeBomber {

	private static final float toMeters(float f) {
		return 0.3048F * f;
	}

	private static final float toMetersPerSecond(float f) {
		return 0.4470401F * f;
	}

	private float bpos;

	private float bcurpos;

	private long btme;

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
		Class class1 = B_25C25.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B-25");
		Property.set(class1, "meshName", "3DO/Plane/B-25C-25(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "meshName_ru", "3DO/Plane/B-25C-25(ru)/hier.him");
		Property.set(class1, "PaintScheme_ru", new PaintSchemeBMPar02());
		Property.set(class1, "meshName_us", "3DO/Plane/B-25C-25(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar02());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1956.6F);
		Property.set(class1, "FlightModel", "FlightModels/B-25C.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitB25C25.class,
				CockpitB25C25_Bombardier.class, CockpitB25C25_FGunner.class,
				CockpitB25C25_TGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		weaponTriggersRegister(class1, new int[] { 0, 10, 11, 11, 12, 12, 3, 3,
				3 });
		weaponHooksRegister(class1, new String[] { "_MGUN01", "_MGUN02",
				"_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03" });
		weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null, null,
				null });
		weaponsRegister(class1, "12x100lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGun50kg 6", "BombGun50kg 6" });
		weaponsRegister(class1, "6x250lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGun250lbs 3", "BombGun250lbs 3" });
		weaponsRegister(class1, "4x500lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGun500lbs 2", "BombGun500lbs 2" });
		weaponsRegister(class1, "2x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGun1000lbs 1", "BombGun1000lbs 1" });
		weaponsRegister(class1, "1x2000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450",
				"BombGun2000lbs 1", null, null });
		weaponsRegister(class1, "10x50kg", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGunFAB50 5", "BombGunFAB50 5" });
		weaponsRegister(class1, "8x100kg", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50t 400",
				"MGunBrowning50t 450", "MGunBrowning50t 450",
				"MGunBrowning50tj 450", "MGunBrowning50tj 450", null,
				"BombGunFAB100 4", "BombGunFAB100 4" });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null });
	}

	public B_25C25() {
		this.bpos = 1.0F;
		this.bcurpos = 1.0F;
		this.btme = -1L;
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
			this.FM.turret[2].setHealth(f);
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
		for (int i = 1; i < 6; i++) {
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
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 15F) {
				f1 = 15F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f1 < 0.0F) {
				f1 = 0.0F;
				flag = false;
			}
			if (f1 > 88F) {
				f1 = 88F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f1 < -88F) {
				f1 = -88F;
				flag = false;
			}
			if (f1 > 2.0F) {
				f1 = 2.0F;
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

	public void update(float f) {
		super.update(f);
		if (this.FM.AS.isMaster()) {
			if (this.bpos == 0.0F) {
				if (this.bcurpos > this.bpos) {
					this.bcurpos -= 0.2F * f;
					if (this.bcurpos < 0.0F) {
						this.bcurpos = 0.0F;
					}
				}
				this.resetYPRmodifier();
				Aircraft.xyz[1] = -0.31F + (0.31F * this.bcurpos);
				this.hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz,
						Aircraft.ypr);
			} else if (this.bpos == 1.0F) {
				if (this.bcurpos < this.bpos) {
					this.bcurpos += 0.2F * f;
					if (this.bcurpos > 1.0F) {
						this.bcurpos = 1.0F;
						this.bpos = 0.5F;
						this.FM.turret[2].bIsOperable = true;
					}
				}
				this.resetYPRmodifier();
				Aircraft.xyz[1] = -0.3F + (0.3F * this.bcurpos);
				this.hierMesh().chunkSetLocate("Turret3A_D0", Aircraft.xyz,
						Aircraft.ypr);
			}
			if (Time.current() > this.btme) {
				this.btme = Time.current()
						+ World.Rnd().nextLong(5000L, 12000L);
				if (this.FM.turret[2].target == null) {
					this.FM.turret[2].bIsOperable = false;
					this.bpos = 0.0F;
				}
				if ((this.FM.turret[1].target != null)
						&& (this.FM.AS.astatePilotStates[4] < 90)) {
					this.bpos = 1.0F;
				}
			}
		}
	}
}
