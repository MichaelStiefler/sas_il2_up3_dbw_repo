// Source File Name: MC_202.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class MC_202 extends Scheme1 implements TypeFighter {

	static {
		Class class1 = MC_202.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "M.C.202");
		Property.set(class1, "meshNameDemo", "3DO/Plane/MC-202/hier.him");
		Property.set(class1, "meshName_it", "3DO/Plane/MC-202/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar01());
		Property.set(class1, "meshName", "3DO/Plane/MC-202(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "originCountry", PaintScheme.countryItaly);
		Property.set(class1, "yearService", 1942F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/MC-202.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitMC_202.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBredaSAFAT127si 370", "MGunBredaSAFAT127si 370",
				"MGunBredaSAFAT77k 500", "MGunBredaSAFAT77k 500" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null });
	}

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -88F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -88F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -100F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -100F * f, 0.0F);
		hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -114F * f, 0.0F);
		hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -114F * f, 0.0F);
		float f1 = Math.max(-f * 1500F, -80F);
		hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
		hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
	}

	public MC_202() {
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Head1_D0", false);
			this.hierMesh().chunkVisible("HMask1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			break;
		}
	}

	protected void moveFlap(float f) {
		float f1 = -45F * f;
		this.hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
		this.hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (this.FM.getAltitude() < 3000F) {
			this.hierMesh().chunkVisible("HMask1_D0", false);
		} else {
			this.hierMesh().chunkVisible("HMask1_D0",
					this.hierMesh().isChunkVisible("Pilot1_D0"));
		}
	}
}
