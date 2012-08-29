package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank44gal extends FuelTank


{
static Class class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal;
static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
    
    static {
	Class var_class
	    = (class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal == null
	       ? (class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal
		  = (class$
		     ("com.maddox.il2.objects.weapons.FuelTank_Tank44gal")))
	       : class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal);
	Property.set(var_class, "mesh", "3DO/Arms/Tank44gal/mono.sim");
	Property.set(var_class, "kalibr", 0.6F);
	Property.set(var_class, "massa", 158.4F);
    }
}
