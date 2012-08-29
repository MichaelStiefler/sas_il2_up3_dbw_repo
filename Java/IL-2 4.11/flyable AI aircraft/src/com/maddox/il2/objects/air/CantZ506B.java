// Source File Name: CantZ506B.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.*;
import java.io.IOException;

public class CantZ506B extends CantZ506 implements TypeBomber, TypeTransport,
		TypeSailPlane {

	public CantZ506B() {
		super.fSightCurAltitude = 850F;
		super.fSightCurSpeed = 150F;
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
			if (f1 < -4F) {
				f1 = -4F;
				flag = false;
			}
			if (f1 > 75F) {
				f1 = 75F;
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

	public boolean typeBomberToggleAutomation() {
		return false;
	}

	public void typeBomberAdjDistanceReset() {
		super.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjDistancePlus() {
		super.fSightCurForwardAngle += 0.4F;
		if (super.fSightCurForwardAngle > 75F)
			super.fSightCurForwardAngle = 75F;
	}

	public void typeBomberAdjDistanceMinus() {
		super.fSightCurForwardAngle -= 0.4F;
		if (super.fSightCurForwardAngle < -15F)
			super.fSightCurForwardAngle = -15F;
	}

	public void typeBomberAdjSideslipReset() {
		super.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSideslipPlus() {
		super.fSightCurSideslip += 0.5D;
		if (super.thisWeaponsName.startsWith("1x")) {
			if (super.fSightCurSideslip > 40F)
				super.fSightCurSideslip = 40F;
			HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  "
					+ super.fSightCurSideslip);
		} else {
			if (super.fSightCurSideslip > 10F)
				super.fSightCurSideslip = 10F;
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip "
					+ super.fSightCurSideslip);
		}
	}

	public void typeBomberAdjSideslipMinus() {
		super.fSightCurSideslip -= 0.5D;
		if (super.thisWeaponsName.startsWith("1x")) {
			if (super.fSightCurSideslip < -40F)
				super.fSightCurSideslip = -40F;
			HUD.log(AircraftHotKeys.hudLogWeaponId, "Torpedo Angle  "
					+ super.fSightCurSideslip);
		} else {
			if (super.fSightCurSideslip < -10F)
				super.fSightCurSideslip = -10F;
			HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip "
					+ super.fSightCurSideslip);
		}
	}

	public void typeBomberAdjAltitudeReset() {
		super.fSightCurAltitude = 300F;
	}

	public void typeBomberAdjAltitudePlus() {
		super.fSightCurAltitude += 10F;
		if (super.fSightCurAltitude > 6000F)
			super.fSightCurAltitude = 6000F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) super.fSightCurAltitude) });
	}

	public void typeBomberAdjAltitudeMinus() {
		super.fSightCurAltitude -= 10F;
		if (super.fSightCurAltitude < 300F)
			super.fSightCurAltitude = 300F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) super.fSightCurAltitude) });
	}

	public void typeBomberAdjSpeedReset() {
		super.fSightCurSpeed = 50F;
	}

	public void typeBomberAdjSpeedPlus() {
		super.fSightCurSpeed += 5F;
		if (super.fSightCurSpeed > 650F)
			super.fSightCurSpeed = 650F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) super.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedMinus() {
		super.fSightCurSpeed -= 5F;
		if (super.fSightCurSpeed < 50F)
			super.fSightCurSpeed = 50F;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) super.fSightCurSpeed) });
	}

	public void typeBomberUpdate(float f) {
		double d = ((double) super.fSightCurSpeed / 3.6000000000000001D)
				* Math.sqrt((double) super.fSightCurAltitude * 0.20387359799999999D);
		d -= (double) (super.fSightCurAltitude * super.fSightCurAltitude) * 1.419E-005D;
		super.fSightSetForwardAngle = (float) Math.atan(d
				/ (double) super.fSightCurAltitude);
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeFloat(super.fSightCurAltitude);
		netmsgguaranted.writeFloat(super.fSightCurSpeed);
		netmsgguaranted.writeFloat(super.fSightCurForwardAngle);
		netmsgguaranted.writeFloat(super.fSightCurSideslip);
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		super.fSightCurAltitude = netmsginput.readFloat();
		super.fSightCurSpeed = netmsginput.readFloat();
		super.fSightCurForwardAngle = netmsginput.readFloat();
		super.fSightCurSideslip = netmsginput.readFloat();
	}

	static {
		Class class1 = CLASS.THIS();
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "CantZ");
		Property.set(class1, "meshName", "3do/plane/CantZ506B(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar09());
		Property.set(class1, "meshName_it", "3do/plane/CantZ506B(IT)/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/CantZ506.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				com.maddox.il2.objects.air.CockpitCantZ.class,
				com.maddox.il2.objects.air.CockpitCantZ_Bombardier.class,
				com.maddox.il2.objects.air.CockpitCantZ_TGunner.class,
				com.maddox.il2.objects.air.CockpitCantZ_RGunner.class,
				com.maddox.il2.objects.air.CockpitCantZ_BGunner.class });
		Property.set(class1, "LOSElevation", 0.73425F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 3,
				3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01",
				"_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05",
				"_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09",
				"_BombSpawn10", "_BombSpawn11", "_BombSpawn12", "_BombSpawn13",
				"_BombSpawn14", "_BombSpawn15", "_BombSpawn16", "_BombSpawn17",
				"_BombSpawn18", "_BombSpawn19", "_BombSpawn20" });
		weaponsRegister(class1, "default", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "12x50", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", "BombGunIT_50Kg 1",
				"BombGunIT_50Kg 1", "BombGunIT_50Kg 1", "BombGunIT_50Kg 1",
				"BombGunIT_50Kg 1", "BombGunIT_50Kg 1", "BombGunIT_50Kg 1",
				"BombGunIT_50Kg 1", "BombGunIT_50Kg 1", "BombGunIT_50Kg 1",
				"BombGunIT_50Kg 1", "BombGunIT_50Kg 1", null, null, null, null,
				null, null, null, null });
		weaponsRegister(class1, "6x100", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", null, null, null,
				"BombGunIT_100Kg 1", "BombGunIT_100Kg 1", "BombGunIT_100Kg 1",
				"BombGunIT_100Kg 1", "BombGunIT_100Kg 1", "BombGunIT_100Kg 1",
				null, null, null, null, null, null, null, null, null, null,
				null });
		weaponsRegister(class1, "2x250", new String[] { "MGunScotti127s 350",
				"MGunBredaSAFATSM127s 350", "MGunBredaSAFATSM77s 500",
				"MGunBredaSAFATSM77s 500", null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				"BombGunIT_250Kg", null, "BombGunIT_250Kg", null });
		weaponsRegister(class1, "2x250+3x100", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, null, null, null,
				"BombGunIT_100Kg 1", "BombGunIT_100Kg 1", "BombGunIT_100Kg 1",
				"BombGunIT_250Kg", null, "BombGunIT_250Kg", null, null, null,
				null, null });
		weaponsRegister(class1, "3x100+1x500", new String[] {
				"MGunScotti127s 350", "MGunBredaSAFATSM127s 350",
				"MGunBredaSAFATSM77s 500", "MGunBredaSAFATSM77s 500", null,
				null, null, null, null, null, "BombGunIT_100Kg 1",
				"BombGunIT_100Kg 1", "BombGunIT_100Kg 1", null, null, null,
				null, null, "BombGunIT_500Kg 1", null, null, null, null, null });
		weaponsRegister(class1, "none", new String[] { null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null });
	}
}
