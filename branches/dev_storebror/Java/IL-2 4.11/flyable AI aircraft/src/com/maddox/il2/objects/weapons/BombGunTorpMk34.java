// Source File Name: BombGunTorpMk34.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpMk34 extends TorpedoGun {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombGunTorpMk34.class;
		Property.set(class1, "bulletClass",
				com.maddox.il2.objects.weapons.BombTorpMk34.class);
		Property.set(class1, "bullets", 1);
		Property.set(class1, "shotFreq", 0.1F);
		Property.set(class1, "external", 1);
		Property.set(class1, "sound", "weapon.bombgun_torpedo");
	}

	public BombGunTorpMk34() {
	}

	public void setBombDelay(float f) {
		this.bombDelay = 0.0F;
		if (this.bomb != null) {
			this.bomb.delayExplosion = this.bombDelay;
		}
	}
}
