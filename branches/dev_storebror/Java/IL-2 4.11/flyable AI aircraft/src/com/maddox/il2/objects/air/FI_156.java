// Source File Name: FI_156.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class FI_156 extends Scheme1 implements TypeScout, TypeTransport {

	private static final float gearL2[] = { 0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F };

	private static final float gearL4[] = { 0.0F, 7.5F, 15F, 22F, 29F, 35.5F };

	private static final float gearL5[] = { 0.0F, 1.5F, 4F, 7.5F, 10F, 11.5F };

	static {
		Class class1 = FI_156.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Fi-156");
		Property.set(class1, "meshName", "3do/plane/Fi-156/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "originCountry", PaintScheme.countryGermany);
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1956F);
		Property.set(class1, "FlightModel", "FlightModels/Fi-156B-2.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitFI_156.class,
				CockpitFI_156_Gunner.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01" });
		Aircraft.weaponsRegister(class1, "default",
				new String[] { "MGunMG15t 750" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null });
	}

	public static void moveGear(HierMesh hiermesh, float f) {
	}

	public FI_156() {
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 34: // '"'
			return super.cutFM(35, j, actor);

		case 37: // '%'
			return super.cutFM(38, j, actor);
		}
		return super.cutFM(i, j, actor);
	}

	public void doKillPilot(int i) {
		if (i == 1) {
			this.FM.turret[0].bIsOperable = false;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		default:
			break;

		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Head1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			this.hierMesh().chunkVisible("HMask1_D0", false);
			if (!this.FM.AS.bIsAboutToBailout) {
				this.hierMesh().chunkVisible("Gore1_D0", true);
			}
			break;

		case 1: // '\001'
			this.hierMesh().chunkVisible("Pilot2_D0", false);
			this.hierMesh().chunkVisible("Pilot2_D1", true);
			this.hierMesh().chunkVisible("HMask2_D0", false);
			if (!this.FM.AS.bIsAboutToBailout) {
				this.hierMesh().chunkVisible("Gore2_D0", true);
			}
			break;
		}
	}

	private float floatindex(float f, float af[]) {
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

	protected void moveFlap(float f) {
		float f1 = -60F * f;
		this.hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
		this.hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void moveWheelSink() {
		this.resetYPRmodifier();
		Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F,
				0.5F, 0.0F, 0.5F);
		this.hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
		float f = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.5F,
				0.0F, 5F);
		this.hierMesh().chunkSetAngles("GearL2_D0", 0.0F,
				this.floatindex(f, gearL2), 0.0F);
		this.hierMesh().chunkSetAngles("GearL4_D0", 0.0F,
				this.floatindex(f, gearL4), 0.0F);
		this.hierMesh().chunkSetAngles("GearL5_D0", 0.0F,
				this.floatindex(f, gearL5), 0.0F);
		Aircraft.xyz[2] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F,
				0.5F, 0.0F, 0.5F);
		this.hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
		f = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 5F);
		this.hierMesh().chunkSetAngles("GearR2_D0", 0.0F,
				-this.floatindex(f, gearL2), 0.0F);
		this.hierMesh().chunkSetAngles("GearR4_D0", 0.0F,
				-this.floatindex(f, gearL4), 0.0F);
		this.hierMesh().chunkSetAngles("GearR5_D0", 0.0F,
				-this.floatindex(f, gearL5), 0.0F);
	}

	public void msgShot(Shot shot) {
		this.setShot(shot);
		if (shot.chunkName.startsWith("WingLMid")
				&& (World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)) {
			this.FM.AS.hitTank(shot.initiator, 0,
					(int) (1.0F + (shot.mass * 18.95F * 2.0F)));
		}
		if (shot.chunkName.startsWith("WingRMid")
				&& (World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)) {
			this.FM.AS.hitTank(shot.initiator, 1,
					(int) (1.0F + (shot.mass * 18.95F * 2.0F)));
		}
		if (shot.chunkName.startsWith("Engine")) {
			if (World.Rnd().nextFloat(0.0F, 1.0F) < shot.mass) {
				this.FM.AS.hitEngine(shot.initiator, 0, 1);
			}
			if ((Aircraft.v1.z > 0.0D) && (World.Rnd().nextFloat() < 0.12F)) {
				this.FM.AS.setEngineDies(shot.initiator, 0);
				if (shot.mass > 0.1F) {
					this.FM.AS.hitEngine(shot.initiator, 0, 5);
				}
			}
			if ((Aircraft.v1.x < 0.10000000149011612D)
					&& (World.Rnd().nextFloat() < 0.57F)) {
				this.FM.AS.hitOil(shot.initiator, 0);
			}
		}
		if (shot.chunkName.startsWith("Pilot1")) {
			this.killPilot(shot.initiator, 0);
			this.FM.setCapableOfBMP(false, shot.initiator);
			if ((Aircraft.Pd.z > 0.5D)
					&& (shot.initiator == World.getPlayerAircraft())
					&& World.cur().isArcade()) {
				HUD.logCenter("H E A D S H O T");
			}
		} else if (shot.chunkName.startsWith("Pilot2")) {
			this.killPilot(shot.initiator, 1);
			if ((Aircraft.Pd.z > 0.5D)
					&& (shot.initiator == World.getPlayerAircraft())
					&& World.cur().isArcade()) {
				HUD.logCenter("H E A D S H O T");
			}
		} else {
			if (shot.chunkName.startsWith("Turret")) {
				this.FM.turret[0].bIsOperable = false;
			}
			if ((this.FM.AS.astateEngineStates[0] == 4)
					&& (World.Rnd().nextInt(0, 99) < 33)) {
				this.FM.setCapableOfBMP(false, shot.initiator);
			}
			super.msgShot(shot);
		}
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		for (int i = 1; i < 3; i++) {
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
		if (f < -45F) {
			f = -45F;
			flag = false;
		}
		if (f > 45F) {
			f = 45F;
			flag = false;
		}
		if (f1 < -45F) {
			f1 = -45F;
			flag = false;
		}
		if (f1 > 20F) {
			f1 = 20F;
			flag = false;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}
}
