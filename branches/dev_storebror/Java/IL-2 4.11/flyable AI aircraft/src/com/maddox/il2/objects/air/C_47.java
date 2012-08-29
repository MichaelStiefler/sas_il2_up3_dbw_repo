// Source File Name: C_47.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.Property;

public class C_47 extends Scheme2 implements TypeTransport {

	static {
		Class class1 = C_47.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Douglas");
		Property.set(class1, "meshNameDemo", "3DO/Plane/C-47(USA)/hier.him");
		Property.set(class1, "meshName", "3DO/Plane/C-47(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "meshName_us", "3DO/Plane/C-47(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar04());
		Property.set(class1, "originCountry", PaintScheme.countryUSA);
		Property.set(class1, "noseart", 1);
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 2999.9F);
		Property.set(class1, "FlightModel", "FlightModels/DC-3.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitC47.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_BombSpawn01" });
		Aircraft.weaponsRegister(class1, "default", new String[] { null });
		Aircraft.weaponsRegister(class1, "28xPara",
				new String[] { "BombGunPara 28" });
		Aircraft.weaponsRegister(class1, "5xCargoA",
				new String[] { "BombGunCargoA 5" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null });
	}

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -45F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 20F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 20F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -120F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -120F * f, 0.0F);
	}

	public C_47() {
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
				((FlightModelMain) (super.FM)).AS.hitTank(this, 1, World.Rnd()
						.nextInt(2, 6));
			}
			break;

		case 38: // '&'
			if (World.Rnd().nextFloat() < 0.25F) {
				((FlightModelMain) (super.FM)).AS.hitTank(this, 2, World.Rnd()
						.nextInt(2, 6));
			}
			break;
		}
		return super.cutFM(i, j, actor);
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
		}
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void msgShot(Shot shot) {
		this.setShot(shot);
		if (shot.chunkName.startsWith("WingLOut")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(((Tuple3d) (Aircraft.Pd)).y) < 6D)) {
			((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("WingROut")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(((Tuple3d) (Aircraft.Pd)).y) < 6D)) {
			((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 3, 1);
		}
		if (shot.chunkName.startsWith("WingLIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(((Tuple3d) (Aircraft.Pd)).y) < 1.940000057220459D)) {
			((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("WingRIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
				&& (Math.abs(((Tuple3d) (Aircraft.Pd)).y) < 1.940000057220459D)) {
			((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 1);
		}
		if (shot.chunkName.startsWith("Engine1")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("Engine2")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("Nose")
				&& (((Tuple3d) (Aircraft.Pd)).x > 4.9000000953674316D)
				&& (((Tuple3d) (Aircraft.Pd)).z > -0.090000003576278687D)
				&& (World.Rnd().nextFloat() < 0.1F)) {
			if (((Tuple3d) (Aircraft.Pd)).y > 0.0D) {
				this.killPilot(shot.initiator, 0);
				super.FM.setCapableOfBMP(false, shot.initiator);
			} else {
				this.killPilot(shot.initiator, 1);
			}
		}
		if ((((FlightModelMain) (super.FM)).AS.astateEngineStates[0] > 2)
				&& (((FlightModelMain) (super.FM)).AS.astateEngineStates[1] > 2)
				&& (World.Rnd().nextInt(0, 99) < 33)) {
			super.FM.setCapableOfBMP(false, shot.initiator);
		}
		super.msgShot(shot);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		for (int i = 1; i < 3; i++) {
			if (super.FM.getAltitude() < 3000F) {
				this.hierMesh().chunkVisible("HMask" + i + "_D0", false);
			} else {
				this.hierMesh().chunkVisible("HMask" + i + "_D0",
						this.hierMesh().isChunkVisible("Pilot" + i + "_D0"));
			}
		}

	}
}
