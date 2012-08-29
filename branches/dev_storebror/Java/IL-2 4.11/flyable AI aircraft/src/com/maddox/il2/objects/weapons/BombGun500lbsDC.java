// Source File Name: BombGun500lbsDC.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun500lbsDC extends BombGun {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombGun500lbsDC.class;
		Property.set(class1, "bulletClass",
				com.maddox.il2.objects.weapons.Bomb500lbsDC.class);
		Property.set(class1, "bullets", 1);
		Property.set(class1, "shotFreq", 2.0F);
		Property.set(class1, "external", 1);
		Property.set(class1, "sound", "weapon.bombgun");
	}

	public BombGun500lbsDC() {
	}
}
