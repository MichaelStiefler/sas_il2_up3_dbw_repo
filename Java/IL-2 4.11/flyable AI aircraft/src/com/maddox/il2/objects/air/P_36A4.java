// Source File Name: P_36A4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class P_36A4 extends P_36 {

	public static boolean bChangedPit = false;

	private float kangle;

	static {
		Class class1 = P_36A4.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "P-36");
		Property.set(class1, "meshName", "3DO/Plane/Hawk75A-4(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "meshName_us", "3DO/Plane/Hawk75A-4(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeFCSPar02());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/P-36A-4.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitP_36.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 0, 0 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50si 200", "MGunBrowning50si 200",
				"MGunBrowning303k 500", "MGunBrowning303k 500",
				"MGunBrowning303k 500", "MGunBrowning303k 500" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null });
	}

	public P_36A4() {
		this.kangle = 0.0F;
	}

	public void moveCockpitDoor(float f) {
		this.resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.63F);
		this.hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz,
				Aircraft.ypr);
		if (Config.isUSE_RENDER()) {
			if ((Main3D.cur3D().cockpits != null)
					&& (Main3D.cur3D().cockpits[0] != null)) {
				Main3D.cur3D().cockpits[0].onDoorMoved(f);
			}
			this.setDoorSnd(f);
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

	public void update(float f) {
		for (int i = 1; i < 9; i++) {
			this.hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F,
					-10F * this.kangle, 0.0F);
		}

		this.kangle = (0.95F * this.kangle)
				+ (0.05F * this.FM.EI.engines[0].getControlRadiator());
		super.update(f);
	}
}
