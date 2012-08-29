// Source File Name: KI_45_TEI.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Regiment;
import com.maddox.rts.Property;

public class KI_45_TEI extends KI_45 implements TypeJazzPlayer {

	private static final Vector3d ATTACK_VECTOR = new Vector3d(-190D, 0.0D,
			-300D);

	static {
		Class class1 = KI_45_TEI.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Ki-45");
		Property.set(class1, "meshName", "3do/plane/Ki-45(Multi1)/TEI_hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "meshName_ja", "3do/plane/Ki-45(ja)/TEI_hier.him");
		Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
		Property.set(class1, "yearService", 1941F);
		Property.set(class1, "yearExpired", 1945F);
		Property.set(class1, "FlightModel", "FlightModels/Ki-45-Tei.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitKI_45_TEI.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 1, 1, 10 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_CANNON02",
				"_CANNON03", "_CANNON04", "_MGUN01" });
	}

	public static String getSkinPrefix(String s, Regiment regiment) {
		return "TEI_";
	}

	public KI_45_TEI() {
	}

	public Vector3d getAttackVector() {
		return ATTACK_VECTOR;
	}

	public boolean hasCourseWeaponBullets() {
		return (this.FM.CT.Weapons[0] != null)
				&& (this.FM.CT.Weapons[0][0] != null)
				&& (this.FM.CT.Weapons[0][0].countBullets() != 0);
	}

	public boolean hasSlantedWeaponBullets() {
		return ((this.FM.CT.Weapons[1] != null)
				&& (this.FM.CT.Weapons[1][0] != null)
				&& (this.FM.CT.Weapons[1][1] != null) && (this.FM.CT.Weapons[1][0]
				.countBullets() != 0))
				|| (this.FM.CT.Weapons[1][1].countBullets() != 0);
	}
}
