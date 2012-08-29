// Source File Name: B_25G1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-04
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.ai.World;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class B_25G1 extends B_25 implements TypeBomber, TypeStormovik,
		TypeStormovikArmored {

	private float bpos;

	private float bcurpos;

	private long btme;

	public static boolean bChangedPit = false;

	public float fSightCurForwardAngle;

	public float fSightCurSideslip;

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurReadyness;

	static {
		Class class1 = B_25G1.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "B-25");
		Property.set(class1, "meshName", "3DO/Plane/B-25G-1(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
		Property.set(class1, "meshName_us", "3DO/Plane/B-25G-1(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar03());
		Property.set(class1, "noseart", 1);
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1956.6F);
		Property.set(class1, "FlightModel", "FlightModels/B-25G.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitB25G1.class,
				CockpitB25G1_TGunner.class });
		weaponTriggersRegister(class1, new int[] { 0, 0, 1, 11, 11, 12, 12, 9,
				9, 9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });
		weaponHooksRegister(class1, new String[] { "_MGUN07", "_MGUN08",
				"_CANNON01", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_ExternalDev01", "_ExternalDev02", "_ExternalDev03",
				"_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05",
				"_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08",
				"_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03", "_BombSpawn01", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03", "_ExternalDev05",
				"_ExternalDev06", "_ExternalDev07", "_ExternalDev08",
				"_ExternalDev09", "_ExternalDev10", "_ExternalDev11",
				"_ExternalDev12", "_ExternalRock01", "_ExternalRock02",
				"_ExternalRock03", "_ExternalRock04", "_ExternalRock05",
				"_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });
		weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null });
		weaponsRegister(class1, "12x100lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGun50kg 6", "BombGun50kg 6", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		weaponsRegister(class1, "3x250lbs3x500lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1",
				"BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "3x250lbs2x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGun1000lbs 1", "BombGun1000lbs 1", null, null,
				"BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null });
		weaponsRegister(class1, "3x250lbs1x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1",
				"BombGunNull 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "6x250lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGun250lbs 3", "BombGun250lbs 3", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "8x250lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2",
				"PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2",
				"BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "4x500lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGun500lbs 2", "BombGun500lbs 2", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "4x500lbs1x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null,
				null, null, "BombGun500lbs 2", "BombGun500lbs 2", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "6x500lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGun500lbs 3", "BombGun500lbs 3", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "2x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "3x1000lbs", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, "BombGun1000lbs 1", "BombGunNull 1", null, null,
				null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "10x50kg", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGunFAB50 5", "BombGunFAB50 5", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "8x100kg", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null,
				"BombGunFAB100 4", "BombGunFAB100 4", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "2x250kg6x100kg", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", "PylonB25PLN2",
				"PylonB25PLN2", "PylonB25PLN2", "PylonB25PLN2", null, null,
				null, null, null, "BombGunFAB250 1", "BombGunFAB250 1", null,
				null, null, null, null, null, null, null, null,
				"BombGunFAB100 3", "BombGunFAB100 3", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
		weaponsRegister(class1, "8xHVAR", new String[] {
				"MGunBrowning50ki 400", "MGunBrowning50ki 400", "MGunM4_75 21",
				"MGunBrowning50t 400", "MGunBrowning50t 400",
				"MGunBrowning50tj 400", "MGunBrowning50tj 400", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				"PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL",
				"PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL", "PylonB25RAIL",
				"RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
				"RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1",
				"RocketGunHVAR5 1", "RocketGunHVAR5 1" });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
	}

	public B_25G1() {
		this.bpos = 1.0F;
		this.bcurpos = 1.0F;
		this.btme = -1L;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
		this.fSightCurAltitude = 3000F;
		this.fSightCurSpeed = 200F;
		this.fSightCurReadyness = 0.0F;
		bChangedPit = false;
	}

	public void doWoundPilot(int i, float f) {
		switch (i) {
		case 3: // '\003'
			this.FM.turret[1].setHealth(f);
			break;

		case 4: // '\004'
			this.FM.turret[2].setHealth(f);
			break;
		}
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.AS.wantBeaconsNet(true);
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			return false;

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
