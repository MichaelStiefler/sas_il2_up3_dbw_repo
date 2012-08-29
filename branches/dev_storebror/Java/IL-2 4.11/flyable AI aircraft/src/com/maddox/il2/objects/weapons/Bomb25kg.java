// Source File Name: Bomb25kg.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb25kg extends Bomb {

	static {
		Class class1 = com.maddox.il2.objects.weapons.Bomb25kg.class;
		Property.set(class1, "mesh", "3DO/Arms/30kgBombJ/mono.sim");
		Property.set(class1, "radius", 20F);
		Property.set(class1, "power", 15F);
		Property.set(class1, "powerType", 0);
		Property.set(class1, "kalibr", 0.32F);
		Property.set(class1, "massa", 25F);
		Property.set(class1, "sound", "weapon.bomb_mid");
	}

	public Bomb25kg() {
	}
}
