// Source File Name: Bomb500lbsDC.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb500lbsDC extends Bomb {

	static {
		Class class1 = com.maddox.il2.objects.weapons.Bomb500lbsDC.class;
		Property.set(class1, "mesh", "3DO/Arms/500LbsBomb/mono.sim");
		Property.set(class1, "radius", 50F);
		Property.set(class1, "power", 125F);
		Property.set(class1, "powerType", 0);
		Property.set(class1, "kalibr", 0.32F);
		Property.set(class1, "massa", 250F);
		Property.set(class1, "sound", "weapon.bomb_mid");
	}

	public Bomb500lbsDC() {
	}
}
