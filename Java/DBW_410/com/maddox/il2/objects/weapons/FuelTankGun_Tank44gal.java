package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Tank44gal extends FuelTankGun


{
static Class class$com$maddox$il2$objects$weapons$FuelTankGun_Tank44gal;
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
		    = ((class$com$maddox$il2$objects$weapons$FuelTankGun_Tank44gal
			== null)
		       ? (class$com$maddox$il2$objects$weapons$FuelTankGun_Tank44gal
			  = (class$
			     ("com.maddox.il2.objects.weapons.FuelTankGun_Tank44gal")))
		       : class$com$maddox$il2$objects$weapons$FuelTankGun_Tank44gal);
		Property.set
		    (var_class, "bulletClass",
		     ((Object)
		      (class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal == null
		       ? (class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal
			  = (class$
			     ("com.maddox.il2.objects.weapons.FuelTank_Tank44gal")))
		       : class$com$maddox$il2$objects$weapons$FuelTank_Tank44gal)));
		Property.set(var_class, "bullets", 1);
		Property.set(var_class, "shotFreq", 0.25F);
		Property.set(var_class, "external", 1);
	    }
	}
