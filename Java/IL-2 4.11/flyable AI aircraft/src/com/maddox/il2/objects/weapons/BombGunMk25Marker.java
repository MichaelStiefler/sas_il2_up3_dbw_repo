// Source File Name: BombGunMk25Marker.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk25Marker extends BombGun {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombGunMk25Marker.class;
		Property.set(class1, "bulletClass",
				com.maddox.il2.objects.weapons.BombMk25Marker.class);
		Property.set(class1, "bullets", 1);
		Property.set(class1, "shotFreq", 2.0F);
		Property.set(class1, "external", 1);
		Property.set(class1, "sound", "weapon.bombgun_phball");
	}

	public BombGunMk25Marker() {
	}

	public void setBombDelay(float f) {
		this.bombDelay = 0.0F;
		if (this.bomb != null) {
			this.bomb.delayExplosion = this.bombDelay;
		}
	}
}
