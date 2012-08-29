// Source File Name: Rocket90.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class Rocket90 extends Rocket {

	static {
		Class class1 = com.maddox.il2.objects.weapons.Rocket90.class;
		Property.set(class1, "mesh", "3DO/Arms/HVAR-5inch/mono.sim");
		Property.set(class1, "sprite", "3DO/Effects/Rocket/firesprite.eff");
		Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
		Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
		Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
		Property.set(class1, "emitLen", 50F);
		Property.set(class1, "emitMax", 1.0F);
		Property.set(class1, "sound", "weapon.rocket_132");
		Property.set(class1, "radius", 60F);
		Property.set(class1, "timeLife", 999.999F);
		Property.set(class1, "timeFire", 4F);
		Property.set(class1, "force", 1300F);
		Property.set(class1, "power", 50F);
		Property.set(class1, "powerType", 0);
		Property.set(class1, "kalibr", 0.132F);
		Property.set(class1, "massa", 23.1F);
		Property.set(class1, "massaEnd", 10.1F);
	}

	public Rocket90() {
	}
}
