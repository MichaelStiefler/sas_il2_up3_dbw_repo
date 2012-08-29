// Source File Name: LI_2.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class LI_2 extends Scheme2 implements TypeTransport, TypeBomber {

	static {
		Class class1 = LI_2.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Douglas");
		Property.set(class1, "meshName", "3do/plane/Li-2/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "originCountry", PaintScheme.countryRussia);
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/Li-2.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitC47.class,
				CockpitLI2_TGunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 3,
				3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_BombSpawn01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150",
				"MGunShKASt 950", null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4xFAB250", new String[] {
				"MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150",
				"MGunShKASt 950", "BombGunFAB250 1", "BombGunFAB250 1",
				"BombGunFAB250 1", "BombGunFAB250 1", null });
		Aircraft.weaponsRegister(class1, "12xPara", new String[] {
				"MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150",
				"MGunShKASt 950", null, null, null, null, "BombGunPara 12" });
		Aircraft.weaponsRegister(class1, "5xCargoA", new String[] {
				"MGunUBt 350", "MGunShKASt 150", "MGunShKASt 150",
				"MGunShKASt 950", null, null, null, null, "BombGunCargoA 5" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null });
	}

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -45F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 20F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 20F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -120F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -120F * f, 0.0F);
	}

	public LI_2() {
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		default:
			break;

		case 13: // '\r'
			this.killPilot(this, 0);
			this.killPilot(this, 1);
			break;

		case 35: // '#'
			if (World.Rnd().nextFloat() < 0.25F) {
				this.FM.AS.hitTank(this, 1, World.Rnd().nextInt(2, 6));
			}
			break;

		case 38: // '&'
			if (World.Rnd().nextFloat() < 0.25F) {
				this.FM.AS.hitTank(this, 2, World.Rnd().nextInt(2, 6));
			}
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.FM.turret[3].bIsOperable = false;
			break;

		case 2: // '\002'
			this.FM.turret[0].bIsOperable = false;
			break;

		case 3: // '\003'
			this.FM.turret[1].bIsOperable = false;
			break;

		case 4: // '\004'
			this.FM.turret[2].bIsOperable = false;
			break;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Head1_D0", false);
			this.hierMesh().chunkVisible("HMask1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			break;

		case 1: // '\001'
			this.hierMesh().chunkVisible("Pilot2_D0", false);
			this.hierMesh().chunkVisible("HMask2_D0", false);
			this.hierMesh().chunkVisible("Pilot2_D1", true);
			break;

		case 2: // '\002'
			this.hierMesh().chunkVisible("Pilot3_D0", false);
			this.hierMesh().chunkVisible("HMask3_D0", false);
			this.hierMesh().chunkVisible("Pilot3_D1", true);
			break;
		}
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void msgShot(Shot shot) {
		this.setShot(shot);
		if (shot.chunkName.startsWith("WingLOut")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(Aircraft.Pd.y) < 6D)) {
			this.FM.AS.hitTank(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("WingROut")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(Aircraft.Pd.y) < 6D)) {
			this.FM.AS.hitTank(shot.initiator, 3, 1);
		}
		if (shot.chunkName.startsWith("WingLIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(Aircraft.Pd.y) < 1.940000057220459D)) {
			this.FM.AS.hitTank(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("WingRIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(Aircraft.Pd.y) < 1.940000057220459D)) {
			this.FM.AS.hitTank(shot.initiator, 2, 1);
		}
		if (shot.chunkName.startsWith("Engine1")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitEngine(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("Engine2")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitEngine(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("Nose")
				&& (Aircraft.Pd.x > 4.9000000953674316D)
				&& (Aircraft.Pd.z > -0.090000003576278687D)
				&& (World.Rnd().nextFloat() < 0.1F)) {
			if (Aircraft.Pd.y > 0.0D) {
				this.killPilot(shot.initiator, 0);
				this.FM.setCapableOfBMP(false, shot.initiator);
			} else {
				this.killPilot(shot.initiator, 1);
			}
		}
		if (shot.chunkName.startsWith("Turret1")) {
			this.killPilot(shot.initiator, 2);
			if ((Aircraft.Pd.z > 1.3350000381469727D)
					&& (shot.initiator == World.getPlayerAircraft())
					&& World.cur().isArcade()) {
				HUD.logCenter("H E A D S H O T");
			}
			return;
		}
		if (shot.chunkName.startsWith("Turret2")) {
			this.FM.turret[1].bIsOperable = false;
			return;
		}
		if (shot.chunkName.startsWith("Turret3")) {
			this.FM.turret[2].bIsOperable = false;
			return;
		}
		if (shot.chunkName.startsWith("Tail")
				&& (Aircraft.Pd.x < -5.8000001907348633D)
				&& (Aircraft.Pd.x > -6.7899999618530273D)
				&& (Aircraft.Pd.z > -0.44900000000000001D)
				&& (Aircraft.Pd.z < 0.12399999797344208D)) {
			this.FM.AS.hitPilot(shot.initiator, World.Rnd().nextInt(3, 4),
					(int) (shot.mass * 1000F * World.Rnd()
							.nextFloat(0.9F, 1.1F)));
		}
		if ((this.FM.AS.astateEngineStates[0] > 2)
				&& (this.FM.AS.astateEngineStates[1] > 2)
				&& (World.Rnd().nextInt(0, 99) < 33)) {
			this.FM.setCapableOfBMP(false, shot.initiator);
		}
		super.msgShot(shot);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		for (int i = 1; i < 4; i++) {
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
			if (f1 < -5F) {
				f1 = -5F;
				flag = false;
			}
			if (f1 > 45F) {
				f1 = 45F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -45F) {
				f = -45F;
				flag = false;
			}
			if (f > 45F) {
				f = 45F;
				flag = false;
			}
			if (f1 < -30F) {
				f1 = -30F;
				flag = false;
			}
			if (f1 > 60F) {
				f1 = 60F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -45F) {
				f = -45F;
				flag = false;
			}
			if (f > 45F) {
				f = 45F;
				flag = false;
			}
			if (f1 < -30F) {
				f1 = -30F;
				flag = false;
			}
			if (f1 > 60F) {
				f1 = 60F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f < -5F) {
				f = -5F;
				flag = false;
			}
			if (f > 5F) {
				f = 5F;
				flag = false;
			}
			if (f1 < -5F) {
				f1 = -5F;
				flag = false;
			}
			if (f1 > 5F) {
				f1 = 5F;
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
}
