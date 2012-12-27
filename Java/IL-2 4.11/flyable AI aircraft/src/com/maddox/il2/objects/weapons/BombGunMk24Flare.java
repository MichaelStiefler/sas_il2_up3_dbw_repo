// Source File Name: BombGunMk24Flare.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk24Flare extends BombGun {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombGunMk24Flare.class;
		Property.set(class1, "bulletClass",
				com.maddox.il2.objects.weapons.BombMk24Flare.class);
		Property.set(class1, "bullets", 1);
		Property.set(class1, "shotFreq", 3F);
		Property.set(class1, "external", 1);
		Property.set(class1, "sound", "weapon.bombgun_AO10");
	}

	public BombGunMk24Flare() {
	}
}