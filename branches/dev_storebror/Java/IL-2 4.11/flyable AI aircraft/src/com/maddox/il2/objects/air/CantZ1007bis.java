// Source File Name: CantZ1007bis.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class CantZ1007bis extends CantZ1007 implements TypeBomber,
		TypeTransport {

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	static {
		Class class1 = CLASS.THIS();
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "CantZ");
		Property.set(class1, "meshName_it",
				"3do/plane/CantZ1007bis(it)/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
		Property.set(class1, "meshName",
				"3do/plane/CantZ1007bis(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar09());
		Property.set(class1, "yearService", 1940F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/CantZ1007.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitCant.class,
				CockpitCant_Bombardier.class, CockpitCant_TGunner.class,
				CockpitCant_BGunner.class });
		weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3 });
		weaponHooksRegister(class1, new String[] { "_MGUN01", "_MGUN02",
				"_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02",
				"_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06",
				"_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10",
				"_BombSpawn11", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14",
				"_BombSpawn15", "_BombSpawn16", "_BombSpawn17", "_BombSpawn18",
				"_BombSpawn19", "_BombSpawn20", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07",
				"_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10" });
		weaponsRegister(class1, "default", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "12x50", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		weaponsRegister(class1, "12x100", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "6x100", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", "BombGunIT_100Kg", "BombGunNull",
				"BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg",
				"BombGunNull", "BombGunIT_100Kg", "BombGunNull",
				"BombGunIT_100Kg", "BombGunNull", "BombGunIT_100Kg",
				"BombGunNull", null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null });
		weaponsRegister(class1, "2x250+3x100", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, "BombGunNull",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_250Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_250Kg", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null });
		weaponsRegister(class1, "2x500", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGunIT_500Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_500Kg", null, null, null, null, null, null, null,
				null, null, null });
		weaponsRegister(class1, "2x250+3x100+3x100x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, "BombGunNull",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_250Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_250Kg", null, null, null, null, "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null });
		weaponsRegister(class1, "2x250+3x100+3x50x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, "BombGunNull",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_250Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_250Kg", null, null, null, null, "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null });
		weaponsRegister(class1, "2x250+1x250x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_250Kg", null, null, null, null, null, null, null,
				null, null, null, "BombGunIT_250Kg", "BombGunIT_250Kg", null,
				null });
		weaponsRegister(class1, "2x250+2x250x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, "BombGunIT_250Kg", "BombGunNull", "BombGunNull",
				"BombGunIT_250Kg", null, null, null, null, null, null, null,
				null, null, null, "BombGunIT_250Kg", "BombGunIT_250Kg",
				"BombGunIT_250Kg", "BombGunIT_250Kg" });
		weaponsRegister(class1, "2x500+3x50x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull",
				"BombGunNull", "BombGunIT_500Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", "BombGunIT_50Kg",
				"BombGunIT_50Kg", "BombGunIT_50Kg", null, null, null, null });
		weaponsRegister(class1, "2x500+3x100x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull",
				"BombGunNull", "BombGunIT_500Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", "BombGunIT_100Kg",
				"BombGunIT_100Kg", "BombGunIT_100Kg", null, null, null, null });
		weaponsRegister(class1, "2x500+1x250x2wing", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGunIT_500Kg", "BombGunNull",
				"BombGunNull", "BombGunIT_500Kg", null, null, null, null, null,
				null, "BombGunIT_250Kg", "BombGunIT_250Kg", null, null });
		weaponsRegister(class1, "2xMotobombaFFF", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, "BombGunTorpFFF", "BombGunNull",
				"BombGunNull", "BombGunTorpFFF1", null, null, null, null, null,
				null, null, null, null, null });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null });
	}

	public CantZ1007bis() {
		this.fSightCurAltitude = 850F;
		this.fSightCurSpeed = 150F;
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 3: // '\003'
			if (f < -45F) {
				f = -45F;
				flag = false;
			}
			if (f > 60F) {
				f = 60F;
				flag = false;
			}
			if (f1 < -35F) {
				f1 = -35F;
				flag = false;
			}
			if (f1 > 35F) {
				f1 = 35F;
				flag = false;
			}
			break;

		case 0: // '\0'
			if (f1 < -7F) {
				f1 = -7F;
				flag = false;
			}
			if (f1 > 80F) {
				f1 = 80F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -40F) {
				f = -40F;
				flag = false;
			}
			if (f > 40F) {
				f = 40F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 45F) {
				f1 = 45F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -60F) {
				f = -60F;
				flag = false;
			}
			if (f > 45F) {
				f = 45F;
				flag = false;
			}
			if (f1 < -35F) {
				f1 = -35F;
				flag = false;
			}
			if (f1 > 35F) {
				f1 = 35F;
				flag = false;
			}
			break;
		}
		af[0] = f;
		af[1] = f1;
		return flag;
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
		if (this.fSightCurAltitude > 6000F) {
			this.fSightCurAltitude = 6000F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 300F;
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle -= 0.4F;
		if (this.fSightCurForwardAngle < -15F) {
			this.fSightCurForwardAngle = -15F;
		}
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle += 0.4F;
		if (this.fSightCurForwardAngle > 75F) {
			this.fSightCurForwardAngle = 75F;
		}
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip -= 0.5D;
		if (this.thisWeaponsName.startsWith("1x")) {
			if (this.fSightCurSideslip < -40F) {
				this.fSightCurSideslip = -40F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  "
					+ this.fSightCurSideslip);
		} else {
			if (this.fSightCurSideslip < -10F) {
				this.fSightCurSideslip = -10F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip "
					+ this.fSightCurSideslip);
		}
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip += 0.5D;
		if (this.thisWeaponsName.startsWith("1x")) {
			if (this.fSightCurSideslip > 40F) {
				this.fSightCurSideslip = 40F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  "
					+ this.fSightCurSideslip);
		} else {
			if (this.fSightCurSideslip > 10F) {
				this.fSightCurSideslip = 10F;
			}
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip "
					+ this.fSightCurSideslip);
		}
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
		if (this.fSightCurSpeed > 650F) {
			this.fSightCurSpeed = 650F;
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
		this.fSightSetForwardAngle = (float) Math.atan(d
				/ this.fSightCurAltitude);
	}
}
