// Source File Name: BombMk53Charge.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk53Charge extends Bomb {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombMk53Charge.class;
		Property.set(class1, "mesh", "3DO/Arms/Mk53_Charge/mono.sim");
		Property.set(class1, "radius", 90F);
		Property.set(class1, "power", 90F);
		Property.set(class1, "powerType", 0);
		Property.set(class1, "kalibr", 0.32F);
		Property.set(class1, "massa", 148F);
		Property.set(class1, "sound", "weapon.bomb_mid");
	}

	public BombMk53Charge() {
	}
}
