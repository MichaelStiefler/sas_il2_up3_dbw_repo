// Source File Name: MBR_2AM34.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class MBR_2AM34 extends Scheme1 implements TypeTransport, TypeBomber,
		TypeSailPlane {

	public static void moveGear(HierMesh hiermesh, float f) {
	}

	private Point3d tmpp;

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurForwardAngle;

	public float fSightSetForwardAngle;

	public float fSightCurSideslip;

	static {
		Class class1 = MBR_2AM34.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "MBR-2");
		Property.set(class1, "meshName",
				"3DO/Plane/MBR-2-AM-34(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "meshName_ru", "3DO/Plane/MBR-2-AM-34/hier.him");
		Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
		Property.set(class1, "originCountry", PaintScheme.countryRussia);
		Property.set(class1, "yearService", 1937F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/MBR-2-AM-34.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitMBR_2AM34.class,
						CockpitMBR_2AM34_Bombardier.class,
						CockpitMBR_2AM34_NGunner.class,
						CockpitMBR_2AM34_TGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 3, 3, 3, 3,
				3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05",
				"_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", null, null, null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "8xAO10", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", "BombGunAO10 1",
				"BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1",
				"BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1",
				"BombGunAO10 1" });
		Aircraft.weaponsRegister(class1, "6xFAB50", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50", null, null });
		Aircraft.weaponsRegister(class1, "8xFAB50", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50",
				"BombGunFAB50", "BombGunFAB50", "BombGunFAB50" });
		Aircraft.weaponsRegister(class1, "2xFAB100", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", null, null, null, null,
				null, null, "BombGunFAB100", "BombGunFAB100" });
		Aircraft.weaponsRegister(class1, "2xFAB250", new String[] {
				"MGunShKASt 750", "MGunShKASt 750", null, null, null, null,
				null, null, "BombGunFAB250", "BombGunFAB250" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null });
	}

	public MBR_2AM34() {
		this.tmpp = new Point3d();
		this.fSightCurAltitude = 300F;
		this.fSightCurSpeed = 50F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightSetForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 33: // '!'
			return super.cutFM(34, j, actor);

		case 36: // '$'
			return super.cutFM(37, j, actor);

		case 11: // '\013'
			this.cutFM(17, j, actor);
			this.FM.cut(17, j, actor);
			this.cutFM(18, j, actor);
			this.FM.cut(18, j, actor);
			return super.cutFM(i, j, actor);

		case 19: // '\023'
			this.FM.Gears.bIsSail = false;
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 1: // '\001'
			this.FM.turret[0].bIsOperable = false;
			break;

		case 2: // '\002'
			this.FM.turret[1].bIsOperable = false;
			break;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			this.hierMesh().chunkVisible("Gore0_D0", true);
			break;

		case 1: // '\001'
			this.hierMesh().chunkVisible("Pilot2_D0", false);
			this.hierMesh().chunkVisible("Pilot2_D1", true);
			this.hierMesh().chunkVisible("Gore1_D0", true);
			break;

		case 2: // '\002'
			this.hierMesh().chunkVisible("Pilot3_D0", false);
			this.hierMesh().chunkVisible("Pilot3_D1", true);
			this.hierMesh().chunkVisible("Gore2_D0", true);
			break;
		}
	}

	protected void moveElevator(float f) {
		this.hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 45F * f, 0.0F);
		this.hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 45F * f, 0.0F);
	}

	protected void moveFlap(float f) {
		this.hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
		this.hierMesh().chunkSetAngles("Flap04_D0", 0.0F, -45F * f, 0.0F);
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void msgShot(Shot shot) {
		this.setShot(shot);
		if (shot.chunkName.startsWith("WingLIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitTank(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("WingRIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitTank(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("Engine1")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitEngine(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("Pilot1")) {
			this.killPilot(shot.initiator, 0);
		}
		if (shot.chunkName.startsWith("Pilot2")) {
			this.killPilot(shot.initiator, 1);
		}
		if (shot.chunkName.startsWith("Pilot3")) {
			this.killPilot(shot.initiator, 2);
		}
		super.msgShot(shot);
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			float f2 = Math.abs(f);
			if (f2 < 114F) {
				if (f1 < -33F) {
					f1 = -33F;
					flag = false;
				}
			} else if (f2 < 153F) {
				if (f1 < -24F) {
					f1 = -24F;
				}
				if (f1 < (-61.962F + (0.333F * f))) {
					flag = false;
				}
				if ((f1 < (-19.111F + (0.185185F * f)))
						&& (f1 > (40F - (0.333F * f)))) {
					flag = false;
				}
			} else if (f2 < 168F) {
				if (f1 < (-97.666F + (0.481482F * f))) {
					f1 = -97.666F + (0.481482F * f);
				}
				if (f1 < (-19.111F + (0.185185F * f))) {
					flag = false;
				}
			} else {
				if (f1 < (-97.666F + (0.481482F * f))) {
					f1 = -97.666F + (0.481482F * f);
				}
				flag = false;
			}
			if (f1 > 60F) {
				f1 = 60F;
				flag = false;
			}
			break;

		case 1: // '\001'
			float f3 = Math.abs(f);
			if (f3 < 2.0F) {
				flag = false;
			}
			if (f3 < 6.5F) {
				if (f1 < -4F) {
					f1 = -4F;
				}
				if (f1 < (17.666F - (3.333F * f))) {
					flag = false;
				}
			} else if (f3 < 45F) {
				if (f1 < (1.232F - (0.805F * f))) {
					f1 = 1.232F - (0.805F * f);
					flag = false;
				}
			} else if (f3 < 105F) {
				if (f1 < -35F) {
					f1 = -35F;
					flag = false;
				}
			} else if (f1 < 2.2F) {
				f1 = 2.2F;
				flag = false;
			}
			if (f > 161F) {
				f = 161F;
				flag = false;
			}
			if (f < -161F) {
				f = -161F;
				flag = false;
			}
			if (f1 > 56F) {
				flag = false;
			}
			if (f1 > 48F) {
				f1 = 48F;
			}
			break;
		}
		af[0] = -f;
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

	public void update(float f) {
		super.update(f);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (this.FM.Gears.clpGearEff[i][j] != null) {
					this.tmpp.set(this.FM.Gears.clpGearEff[i][j].pos
							.getAbsPoint());
					this.tmpp.z = 0.01D;
					this.FM.Gears.clpGearEff[i][j].pos.setAbs(this.tmpp);
					this.FM.Gears.clpGearEff[i][j].pos.reset();
				}
			}

		}

	}
}
