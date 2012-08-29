// Source File Name: RocketGun90.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGun90 extends RocketGun {

	static {
		Class class1 = com.maddox.il2.objects.weapons.RocketGun90.class;
		Property.set(class1, "bulletClass",
				com.maddox.il2.objects.weapons.Rocket90.class);
		Property.set(class1, "bullets", 1);
		Property.set(class1, "shotFreq", 1.0F);
		Property.set(class1, "sound", "weapon.rocketgun_132");
	}

	public RocketGun90() {
	}

	public void setRocketTimeLife(float f) {
		this.timeLife = -1F;
	}
}
